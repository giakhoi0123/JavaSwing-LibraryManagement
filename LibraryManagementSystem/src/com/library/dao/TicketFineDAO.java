package com.library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.model.TicketFine;
import com.library.util.DBConnection;

/**
 * Data Access Object for TicketFine entity
 * Handles all database operations related to fines/penalties
 */
public class TicketFineDAO {
    
    private DBConnection dbConnection;
    
    public TicketFineDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    /**
     * Create a new fine ticket
     * @param ticketFine TicketFine object
     * @return true if successful
     */
    public boolean createTicketFine(TicketFine ticketFine) throws SQLException {
        String query = "INSERT INTO PHIEU_PHAT (MaPP, MaDG, MaNV, MaPM, NgayLap, " +
                      "TienPhat_TreHan, TienPhat_HuHong, TongTien, GhiChu, TrangThaiThanhToan) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        pstmt.setString(1, ticketFine.getMaPP());
        pstmt.setString(2, ticketFine.getMaDG());
        pstmt.setString(3, ticketFine.getMaNV());
        pstmt.setString(4, ticketFine.getMaPM());
        pstmt.setDate(5, ticketFine.getNgayLap() != null ? Date.valueOf(ticketFine.getNgayLap()) : null);
        pstmt.setDouble(6, ticketFine.getTienPhatTreHan());
        pstmt.setDouble(7, ticketFine.getTienPhatHuHong());
        pstmt.setDouble(8, ticketFine.getTongTien());
        pstmt.setString(9, ticketFine.getGhiChu());
        pstmt.setString(10, ticketFine.getTrangThaiThanhToan());
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Get all unpaid fines for a reader
     * @param maDG Reader ID
     * @return List of unpaid TicketFine
     */
    public List<TicketFine> getUnpaidFinesByReader(String maDG) throws SQLException {
        List<TicketFine> fines = new ArrayList<>();
        String query = "SELECT * FROM PHIEU_PHAT WHERE MaDG = ? AND TrangThaiThanhToan = 'Chưa thanh toán' " +
                      "ORDER BY NgayLap DESC";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maDG);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            fines.add(extractTicketFineFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, pstmt);
        return fines;
    }
    
    /**
     * Get all fines for a reader
     * @param maDG Reader ID
     * @return List of TicketFine
     */
    public List<TicketFine> getAllFinesByReader(String maDG) throws SQLException {
        List<TicketFine> fines = new ArrayList<>();
        String query = "SELECT * FROM PHIEU_PHAT WHERE MaDG = ? ORDER BY NgayLap DESC";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maDG);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            fines.add(extractTicketFineFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, pstmt);
        return fines;
    }
    
    /**
     * Mark fine as paid
     * @param maPP Fine ticket ID
     * @return true if successful
     */
    public boolean markAsPaid(String maPP) throws SQLException {
        String query = "UPDATE PHIEU_PHAT SET TrangThaiThanhToan = 'Đã thanh toán', " +
                      "NgayThanhToan = CURDATE() WHERE MaPP = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maPP);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Calculate total unpaid fines for a reader
     * @param maDG Reader ID
     * @return Total unpaid amount
     */
    public double getTotalUnpaidFines(String maDG) throws SQLException {
        String query = "SELECT SUM(TongTien) as Total FROM PHIEU_PHAT " +
                      "WHERE MaDG = ? AND TrangThaiThanhToan = 'Chưa thanh toán'";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maDG);
        ResultSet rs = pstmt.executeQuery();
        
        double total = 0;
        if (rs.next()) {
            total = rs.getDouble("Total");
        }
        
        DBConnection.closeResources(rs, pstmt);
        return total;
    }
    
    /**
     * Generate next fine ticket ID
     * @return Next fine ticket ID (e.g., PP001, PP002)
     */
    public String generateNextFineTicketId() throws SQLException {
        String query = "SELECT MaPP FROM PHIEU_PHAT ORDER BY MaPP DESC LIMIT 1";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        String nextId = "PP001";
        if (rs.next()) {
            String lastId = rs.getString("MaPP");
            int number = Integer.parseInt(lastId.substring(2));
            nextId = String.format("PP%03d", number + 1);
        }
        
        DBConnection.closeResources(rs, stmt);
        return nextId;
    }
    
    /**
     * Get total fines amount (all time)
     * @return Total fines amount
     */
    public double getTotalFinesAmount() throws SQLException {
        String query = "SELECT COALESCE(SUM(TongTien), 0) as Total FROM PHIEU_PHAT";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        double total = 0;
        if (rs.next()) {
            total = rs.getDouble("Total");
        }
        
        DBConnection.closeResources(rs, stmt);
        return total;
    }
    
    /**
     * Get all fine tickets with details
     * @return List of all TicketFine
     */
    public List<TicketFine> getAllFines() throws SQLException {
        List<TicketFine> fines = new ArrayList<>();
        String query = "SELECT pp.*, dg.HoTen as TenDocGia, nv.HoTen as TenNhanVien " +
                      "FROM PHIEU_PHAT pp " +
                      "JOIN DOC_GIA dg ON pp.MaDG = dg.MaDG " +
                      "JOIN NHAN_VIEN nv ON pp.MaNV = nv.MaNV " +
                      "ORDER BY pp.NgayLap DESC";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            TicketFine fine = extractTicketFineFromResultSet(rs);
            fine.setTenDocGia(rs.getString("TenDocGia"));
            fine.setTenNhanVien(rs.getString("TenNhanVien"));
            fines.add(fine);
        }
        
        DBConnection.closeResources(rs, stmt);
        return fines;
    }
    
    /**
     * Extract TicketFine object from ResultSet
     * @param rs ResultSet from query
     * @return TicketFine object
     */
    private TicketFine extractTicketFineFromResultSet(ResultSet rs) throws SQLException {
        TicketFine fine = new TicketFine();
        fine.setMaPP(rs.getString("MaPP"));
        fine.setMaDG(rs.getString("MaDG"));
        fine.setMaNV(rs.getString("MaNV"));
        fine.setMaPM(rs.getString("MaPM"));
        
        Date ngayLap = rs.getDate("NgayLap");
        if (ngayLap != null) {
            fine.setNgayLap(ngayLap.toLocalDate());
        }
        
        fine.setTienPhatTreHan(rs.getDouble("TienPhat_TreHan"));
        fine.setTienPhatHuHong(rs.getDouble("TienPhat_HuHong"));
        fine.setTongTien(rs.getDouble("TongTien"));
        fine.setGhiChu(rs.getString("GhiChu"));
        fine.setTrangThaiThanhToan(rs.getString("TrangThaiThanhToan"));
        
        Date ngayThanhToan = rs.getDate("NgayThanhToan");
        if (ngayThanhToan != null) {
            fine.setNgayThanhToan(ngayThanhToan.toLocalDate());
        }
        
        return fine;
    }
}
