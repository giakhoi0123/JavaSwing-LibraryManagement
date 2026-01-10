package com.library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.library.model.BorrowTicket;
import com.library.model.ReturnDetail;
import com.library.model.ReturnTicket;
import com.library.model.TicketFine;
import com.library.util.DBConnection;
import com.library.util.DateUtil;

/**
 * Data Access Object for Return Operations
 * Handles book returns with automatic fine calculation
 */
public class ReturnDAO {
    
    private DBConnection dbConnection;
    private BorrowDAO borrowDAO;
    private TicketFineDAO ticketFineDAO;
    
    public ReturnDAO() {
        this.dbConnection = DBConnection.getInstance();
        this.borrowDAO = new BorrowDAO();
        this.ticketFineDAO = new TicketFineDAO();
    }
    
    /**
     * Process book return with automatic fine calculation
     * @param returnTicket ReturnTicket object
     * @param returnDetails List of books being returned
     * @param staffId Staff processing the return
     * @return true if successful
     */
    public boolean processReturn(ReturnTicket returnTicket, List<ReturnDetail> returnDetails, String staffId) throws SQLException {
        Connection conn = dbConnection.getConnection();
        
        try {
            conn.setAutoCommit(false);
            
            // 1. Get borrow ticket info
            BorrowTicket borrowTicket = borrowDAO.getBorrowTicketById(returnTicket.getMaPM());
            if (borrowTicket == null) {
                throw new SQLException("Phiếu mượn không tồn tại!");
            }
            
            // 2. Calculate late fine if overdue
            double lateFine = 0;
            LocalDate ngayTra = returnTicket.getNgayTra();
            LocalDate hanTra = borrowTicket.getHanTra();
            
            if (ngayTra.isAfter(hanTra)) {
                long daysLate = DateUtil.daysBetween(hanTra, ngayTra);
                lateFine = borrowDAO.calculateLateFine(borrowTicket.getMaPM(), ngayTra);
            }
            
            // 3. Calculate damage/lost book fines
            double damageFine = 0;
            StringBuilder fineReasons = new StringBuilder();
            
            for (ReturnDetail detail : returnDetails) {
                if (detail.getTienPhatSach() > 0) {
                    damageFine += detail.getTienPhatSach();
                    fineReasons.append(String.format("Sách %s: %s (%.0f VNĐ); ",
                        detail.getMaSach(),
                        detail.getTinhTrangLucTra(),
                        detail.getTienPhatSach()));
                }
            }
            
            double totalFine = lateFine + damageFine;
            returnTicket.setTienPhat(totalFine);
            
            // 4. Insert return ticket
            String insertReturn = "INSERT INTO PHIEU_TRA (MaPT, MaPM, NgayTra, TienPhat) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt1 = conn.prepareStatement(insertReturn);
            pstmt1.setString(1, returnTicket.getMaPT());
            pstmt1.setString(2, returnTicket.getMaPM());
            pstmt1.setDate(3, Date.valueOf(returnTicket.getNgayTra()));
            pstmt1.setDouble(4, totalFine);
            pstmt1.executeUpdate();
            pstmt1.close();
            
            // 5. Insert return details
            String insertDetail = "INSERT INTO CT_PHIEU_TRA (MaPT, MaSach, TinhTrangLucTra, TienPhatSach) " +
                                 "VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt2 = conn.prepareStatement(insertDetail);
            
            for (ReturnDetail detail : returnDetails) {
                pstmt2.setString(1, returnTicket.getMaPT());
                pstmt2.setString(2, detail.getMaSach());
                pstmt2.setString(3, detail.getTinhTrangLucTra());
                pstmt2.setDouble(4, detail.getTienPhatSach());
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();
            pstmt2.close();
            
            // 6. Update borrow ticket status
            String updateBorrow = "UPDATE PHIEU_MUON SET TrangThai = 'Đã trả' WHERE MaPM = ?";
            PreparedStatement pstmt3 = conn.prepareStatement(updateBorrow);
            pstmt3.setString(1, returnTicket.getMaPM());
            pstmt3.executeUpdate();
            pstmt3.close();
            
            // 7. Update book quantities (increment)
            String updateBook = "UPDATE SACH SET SoLuong = SoLuong + 1 WHERE MaSach = ?";
            PreparedStatement pstmt4 = conn.prepareStatement(updateBook);
            
            for (ReturnDetail detail : returnDetails) {
                pstmt4.setString(1, detail.getMaSach());
                pstmt4.addBatch();
            }
            pstmt4.executeBatch();
            pstmt4.close();
            
            // 8. Create fine ticket if there are fines
            if (totalFine > 0) {
                TicketFine ticketFine = new TicketFine();
                ticketFine.setMaPP(ticketFineDAO.generateNextFineTicketId());
                ticketFine.setMaDG(borrowTicket.getMaDG());
                ticketFine.setMaNV(staffId);
                ticketFine.setMaPM(returnTicket.getMaPM());
                ticketFine.setNgayLap(LocalDate.now());
                
                // Set late fine and damage fine separately
                ticketFine.setTienPhatTreHan(lateFine);
                ticketFine.setTienPhatHuHong(damageFine);
                ticketFine.setTongTien(totalFine);
                
                // Build detailed notes
                StringBuilder ghiChu = new StringBuilder();
                if (lateFine > 0) {
                    long daysLate = DateUtil.daysBetween(hanTra, ngayTra);
                    ghiChu.append(String.format("Trả trễ %d ngày (%.0f VNĐ). ", daysLate, lateFine));
                }
                if (damageFine > 0) {
                    ghiChu.append("Chi tiết phạt sách: ").append(fineReasons.toString());
                }
                
                ticketFine.setGhiChu(ghiChu.toString());
                ticketFine.setTrangThaiThanhToan("Chưa thanh toán");
                
                ticketFineDAO.createTicketFine(ticketFine);
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }
    
    /**
     * Generate next return ticket ID
     * @return Next return ticket ID (e.g., PT001, PT002)
     */
    public String generateNextReturnTicketId() throws SQLException {
        String query = "SELECT MaPT FROM PHIEU_TRA ORDER BY MaPT DESC LIMIT 1";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        String nextId = "PT001";
        if (rs.next()) {
            String lastId = rs.getString("MaPT");
            int number = Integer.parseInt(lastId.substring(2));
            nextId = String.format("PT%03d", number + 1);
        }
        
        DBConnection.closeResources(rs, stmt);
        return nextId;
    }
    
    /**
     * Get all return tickets
     * @return List of ReturnTicket
     */
    public List<ReturnTicket> getAllReturnTickets() throws SQLException {
        List<ReturnTicket> tickets = new ArrayList<>();
        String query = "SELECT * FROM PHIEU_TRA ORDER BY NgayTra DESC";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            ReturnTicket ticket = new ReturnTicket();
            ticket.setMaPT(rs.getString("MaPT"));
            ticket.setMaPM(rs.getString("MaPM"));
            
            Date ngayTra = rs.getDate("NgayTra");
            if (ngayTra != null) {
                ticket.setNgayTra(ngayTra.toLocalDate());
            }
            
            ticket.setTienPhat(rs.getDouble("TienPhat"));
            tickets.add(ticket);
        }
        
        DBConnection.closeResources(rs, stmt);
        return tickets;
    }
    
    /**
     * Get return ticket by ID with details
     * @param maPT Return ticket ID
     * @return ReturnTicket object with details
     */
    public ReturnTicket getReturnTicketById(String maPT) throws SQLException {
        String query = "SELECT * FROM PHIEU_TRA WHERE MaPT = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maPT);
        ResultSet rs = pstmt.executeQuery();
        
        ReturnTicket ticket = null;
        if (rs.next()) {
            ticket = new ReturnTicket();
            ticket.setMaPT(rs.getString("MaPT"));
            ticket.setMaPM(rs.getString("MaPM"));
            
            Date ngayTra = rs.getDate("NgayTra");
            if (ngayTra != null) {
                ticket.setNgayTra(ngayTra.toLocalDate());
            }
            
            ticket.setTienPhat(rs.getDouble("TienPhat"));
            
            // Load details
            ticket.setReturnDetails(getReturnDetails(maPT));
        }
        
        DBConnection.closeResources(rs, pstmt);
        return ticket;
    }
    
    /**
     * Get return details for a return ticket
     * @param maPT Return ticket ID
     * @return List of ReturnDetail
     */
    private List<ReturnDetail> getReturnDetails(String maPT) throws SQLException {
        List<ReturnDetail> details = new ArrayList<>();
        String query = "SELECT * FROM CT_PHIEU_TRA WHERE MaPT = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maPT);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            ReturnDetail detail = new ReturnDetail();
            detail.setMaPT(rs.getString("MaPT"));
            detail.setMaSach(rs.getString("MaSach"));
            detail.setTinhTrangLucTra(rs.getString("TinhTrangLucTra"));
            detail.setTienPhatSach(rs.getDouble("TienPhatSach"));
            details.add(detail);
        }
        
        DBConnection.closeResources(rs, pstmt);
        return details;
    }
}
