package com.library.dao;

import com.library.model.BorrowTicket;
import com.library.model.BorrowDetail;
import com.library.util.DBConnection;
import com.library.util.DateUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Borrow operations
 * Handles all database operations related to book borrowing
 * 
 * @author Library Management System
 * @version 1.0
 */
public class BorrowDAO {
    
    private DBConnection dbConnection;
    
    public BorrowDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    /**
     * Get all borrow tickets with reader and staff info
     * @return List of all borrow tickets
     */
    public List<BorrowTicket> getAllBorrowTickets() throws SQLException {
        List<BorrowTicket> tickets = new ArrayList<>();
        String query = "SELECT pm.*, dg.HoTen AS TenDocGia, nv.HoTen AS TenNhanVien " +
                      "FROM PHIEU_MUON pm " +
                      "INNER JOIN DOC_GIA dg ON pm.MaDG = dg.MaDG " +
                      "INNER JOIN NHAN_VIEN nv ON pm.MaNV = nv.MaNV " +
                      "ORDER BY pm.NgayMuon DESC";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            tickets.add(extractBorrowTicketFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return tickets;
    }
    
    /**
     * Get active borrow tickets (not yet returned)
     * @return List of active borrow tickets
     */
    public List<BorrowTicket> getActiveBorrowTickets() throws SQLException {
        List<BorrowTicket> tickets = new ArrayList<>();
        String query = "SELECT pm.*, dg.HoTen AS TenDocGia, nv.HoTen AS TenNhanVien " +
                      "FROM PHIEU_MUON pm " +
                      "INNER JOIN DOC_GIA dg ON pm.MaDG = dg.MaDG " +
                      "INNER JOIN NHAN_VIEN nv ON pm.MaNV = nv.MaNV " +
                      "WHERE pm.TrangThai = 'Đang mượn' " +
                      "ORDER BY pm.HanTra ASC";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            tickets.add(extractBorrowTicketFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return tickets;
    }
    
    /**
     * Get overdue borrow tickets
     * @return List of overdue tickets
     */
    public List<BorrowTicket> getOverdueBorrowTickets() throws SQLException {
        List<BorrowTicket> tickets = new ArrayList<>();
        String query = "SELECT pm.*, dg.HoTen AS TenDocGia, nv.HoTen AS TenNhanVien " +
                      "FROM PHIEU_MUON pm " +
                      "INNER JOIN DOC_GIA dg ON pm.MaDG = dg.MaDG " +
                      "INNER JOIN NHAN_VIEN nv ON pm.MaNV = nv.MaNV " +
                      "WHERE pm.TrangThai = 'Đang mượn' AND pm.HanTra < CURDATE() " +
                      "ORDER BY pm.HanTra ASC";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            tickets.add(extractBorrowTicketFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return tickets;
    }
    
    /**
     * Get borrow ticket by ID with details
     * @param maPM Borrow ticket ID
     * @return BorrowTicket object with details
     */
    public BorrowTicket getBorrowTicketById(String maPM) throws SQLException {
        String query = "SELECT pm.*, dg.HoTen AS TenDocGia, nv.HoTen AS TenNhanVien " +
                      "FROM PHIEU_MUON pm " +
                      "INNER JOIN DOC_GIA dg ON pm.MaDG = dg.MaDG " +
                      "INNER JOIN NHAN_VIEN nv ON pm.MaNV = nv.MaNV " +
                      "WHERE pm.MaPM = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maPM);
        ResultSet rs = pstmt.executeQuery();
        
        BorrowTicket ticket = null;
        if (rs.next()) {
            ticket = extractBorrowTicketFromResultSet(rs);
            // Get borrow details
            ticket.setBorrowDetails(getBorrowDetails(maPM));
        }
        
        DBConnection.closeResources(rs, pstmt);
        return ticket;
    }
    
    /**
     * Get borrow details for a ticket
     * @param maPM Borrow ticket ID
     * @return List of borrow details
     */
    public List<BorrowDetail> getBorrowDetails(String maPM) throws SQLException {
        List<BorrowDetail> details = new ArrayList<>();
        String query = "SELECT ctpm.*, s.TenSach, tg.TenTG, s.ViTri " +
                      "FROM CT_PHIEU_MUON ctpm " +
                      "INNER JOIN SACH s ON ctpm.MaSach = s.MaSach " +
                      "LEFT JOIN TAC_GIA tg ON s.MaTG = tg.MaTG " +
                      "WHERE ctpm.MaPM = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maPM);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            BorrowDetail detail = new BorrowDetail();
            detail.setMaPM(rs.getString("MaPM"));
            detail.setMaSach(rs.getString("MaSach"));
            detail.setTinhTrangLucMuon(rs.getString("TinhTrangLucMuon"));
            detail.setGhiChu(rs.getString("GhiChu"));
            detail.setTenSach(rs.getString("TenSach"));
            detail.setTenTacGia(rs.getString("TenTG"));
            detail.setViTri(rs.getString("ViTri"));
            details.add(detail);
        }
        
        DBConnection.closeResources(rs, pstmt);
        return details;
    }
    
    /**
     * Get borrow history for a reader
     * @param maDG Reader ID
     * @return List of borrow tickets
     */
    public List<BorrowTicket> getBorrowHistoryByReader(String maDG) throws SQLException {
        List<BorrowTicket> tickets = new ArrayList<>();
        String query = "SELECT pm.*, dg.HoTen AS TenDocGia, nv.HoTen AS TenNhanVien " +
                      "FROM PHIEU_MUON pm " +
                      "INNER JOIN DOC_GIA dg ON pm.MaDG = dg.MaDG " +
                      "INNER JOIN NHAN_VIEN nv ON pm.MaNV = nv.MaNV " +
                      "WHERE pm.MaDG = ? " +
                      "ORDER BY pm.NgayMuon DESC";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maDG);
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            tickets.add(extractBorrowTicketFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, pstmt);
        return tickets;
    }
    
    /**
     * Create new borrow ticket with details (Transaction)
     * @param ticket BorrowTicket object with details
     * @return true if successful
     */
    public boolean createBorrowTicket(BorrowTicket ticket) throws SQLException {
        Connection conn = dbConnection.getConnection();
        
        try {
            // Begin transaction
            dbConnection.beginTransaction();
            
            // 1. Insert PHIEU_MUON
            String insertTicketQuery = "INSERT INTO PHIEU_MUON " +
                                      "(MaPM, MaDG, MaNV, NgayMuon, HanTra, TrangThai, GhiChu) " +
                                      "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt1 = conn.prepareStatement(insertTicketQuery);
            pstmt1.setString(1, ticket.getMaPM());
            pstmt1.setString(2, ticket.getMaDG());
            pstmt1.setString(3, ticket.getMaNV());
            pstmt1.setDate(4, Date.valueOf(ticket.getNgayMuon()));
            pstmt1.setDate(5, Date.valueOf(ticket.getHanTra()));
            pstmt1.setString(6, ticket.getTrangThai());
            pstmt1.setString(7, ticket.getGhiChu());
            
            int result1 = pstmt1.executeUpdate();
            pstmt1.close();
            
            if (result1 == 0) {
                dbConnection.rollbackTransaction();
                return false;
            }
            
            // 2. Insert CT_PHIEU_MUON (details)
            String insertDetailQuery = "INSERT INTO CT_PHIEU_MUON " +
                                      "(MaPM, MaSach, TinhTrangLucMuon, GhiChu) " +
                                      "VALUES (?, ?, ?, ?)";
            
            PreparedStatement pstmt2 = conn.prepareStatement(insertDetailQuery);
            
            for (BorrowDetail detail : ticket.getBorrowDetails()) {
                pstmt2.setString(1, ticket.getMaPM());
                pstmt2.setString(2, detail.getMaSach());
                pstmt2.setString(3, detail.getTinhTrangLucMuon());
                pstmt2.setString(4, detail.getGhiChu());
                pstmt2.addBatch();
            }
            
            int[] results = pstmt2.executeBatch();
            pstmt2.close();
            
            // Check if all details were inserted
            for (int result : results) {
                if (result == 0) {
                    dbConnection.rollbackTransaction();
                    return false;
                }
            }
            
            // Commit transaction
            dbConnection.commitTransaction();
            return true;
            
        } catch (SQLException e) {
            dbConnection.rollbackTransaction();
            throw e;
        }
    }
    
    /**
     * Update borrow ticket status
     * @param maPM Borrow ticket ID
     * @param trangThai New status
     * @return true if successful
     */
    public boolean updateBorrowTicketStatus(String maPM, String trangThai) throws SQLException {
        String query = "UPDATE PHIEU_MUON SET TrangThai = ? WHERE MaPM = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, trangThai);
        pstmt.setString(2, maPM);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Generate next borrow ticket ID
     * @return Next ticket ID (e.g., PM001, PM002)
     */
    public String generateNextBorrowTicketId() throws SQLException {
        String query = "SELECT MaPM FROM PHIEU_MUON ORDER BY MaPM DESC LIMIT 1";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        String nextId = "PM001";
        if (rs.next()) {
            String lastId = rs.getString("MaPM");
            int num = Integer.parseInt(lastId.substring(2)) + 1;
            nextId = String.format("PM%03d", num);
        }
        
        DBConnection.closeResources(rs, stmt);
        return nextId;
    }
    
    /**
     * Calculate fine for overdue return
     * @param maPM Borrow ticket ID
     * @param returnDate Actual return date
     * @return Fine amount
     */
    public double calculateLateFine(String maPM, LocalDate returnDate) throws SQLException {
        String query = "SELECT HanTra FROM PHIEU_MUON WHERE MaPM = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maPM);
        ResultSet rs = pstmt.executeQuery();
        
        double fine = 0.0;
        if (rs.next()) {
            Date hanTra = rs.getDate("HanTra");
            if (hanTra != null) {
                fine = DateUtil.calculateLateFine(hanTra.toLocalDate(), returnDate);
            }
        }
        
        DBConnection.closeResources(rs, pstmt);
        return fine;
    }
    
    /**
     * Extract BorrowTicket object from ResultSet
     * @param rs ResultSet from query
     * @return BorrowTicket object
     */
    private BorrowTicket extractBorrowTicketFromResultSet(ResultSet rs) throws SQLException {
        BorrowTicket ticket = new BorrowTicket();
        ticket.setMaPM(rs.getString("MaPM"));
        ticket.setMaDG(rs.getString("MaDG"));
        ticket.setMaNV(rs.getString("MaNV"));
        
        Date ngayMuon = rs.getDate("NgayMuon");
        if (ngayMuon != null) {
            ticket.setNgayMuon(ngayMuon.toLocalDate());
        }
        
        Date hanTra = rs.getDate("HanTra");
        if (hanTra != null) {
            ticket.setHanTra(hanTra.toLocalDate());
        }
        
        ticket.setTrangThai(rs.getString("TrangThai"));
        ticket.setGhiChu(rs.getString("GhiChu"));
        ticket.setTenDocGia(rs.getString("TenDocGia"));
        ticket.setTenNhanVien(rs.getString("TenNhanVien"));
        
        Date createdAt = rs.getDate("CreatedAt");
        if (createdAt != null) {
            ticket.setCreatedAt(createdAt.toLocalDate());
        }
        
        return ticket;
    }
}
