package com.library.dao;

import com.library.model.Reader;
import com.library.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Reader entity
 * Handles all database operations related to readers
 * 
 * @author Library Management System
 * @version 1.0
 */
public class ReaderDAO {
    
    private DBConnection dbConnection;
    
    public ReaderDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    /**
     * Get all readers
     * @return List of all readers
     */
    public List<Reader> getAllReaders() throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM DOC_GIA ORDER BY HoTen";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            readers.add(extractReaderFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return readers;
    }
    
    /**
     * Get active readers
     * @return List of active readers
     */
    public List<Reader> getActiveReaders() throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM DOC_GIA WHERE TrangThai = 'Active' ORDER BY HoTen";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            readers.add(extractReaderFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return readers;
    }
    
    /**
     * Get reader by ID
     * @param maDG Reader ID
     * @return Reader object or null if not found
     */
    public Reader getReaderById(String maDG) throws SQLException {
        String query = "SELECT * FROM DOC_GIA WHERE MaDG = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maDG);
        ResultSet rs = pstmt.executeQuery();
        
        Reader reader = null;
        if (rs.next()) {
            reader = extractReaderFromResultSet(rs);
        }
        
        DBConnection.closeResources(rs, pstmt);
        return reader;
    }
    
    /**
     * Search readers by keyword
     * @param keyword Search keyword
     * @return List of matching readers
     */
    public List<Reader> searchReaders(String keyword) throws SQLException {
        List<Reader> readers = new ArrayList<>();
        String query = "SELECT * FROM DOC_GIA " +
                      "WHERE HoTen LIKE ? OR MaDG LIKE ? OR SoDT LIKE ? " +
                      "ORDER BY HoTen";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        String searchPattern = "%" + keyword + "%";
        pstmt.setString(1, searchPattern);
        pstmt.setString(2, searchPattern);
        pstmt.setString(3, searchPattern);
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            readers.add(extractReaderFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, pstmt);
        return readers;
    }
    
    /**
     * Insert new reader
     * @param reader Reader object to insert
     * @return true if successful
     */
    public boolean insertReader(Reader reader) throws SQLException {
        String query = "INSERT INTO DOC_GIA (MaDG, HoTen, NgaySinh, GioiTinh, " +
                      "DiaChi, SoDT, Email, NgayLapThe, NgayHetHan, TrangThai) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, reader.getMaDG());
        pstmt.setString(2, reader.getHoTen());
        pstmt.setDate(3, reader.getNgaySinh() != null ? Date.valueOf(reader.getNgaySinh()) : null);
        pstmt.setString(4, reader.getGioiTinh());
        pstmt.setString(5, reader.getDiaChi());
        pstmt.setString(6, reader.getSoDT());
        pstmt.setString(7, reader.getEmail());
        pstmt.setDate(8, Date.valueOf(reader.getNgayLapThe()));
        pstmt.setDate(9, Date.valueOf(reader.getNgayHetHan()));
        pstmt.setString(10, reader.getTrangThai());
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Update existing reader
     * @param reader Reader object with updated data
     * @return true if successful
     */
    public boolean updateReader(Reader reader) throws SQLException {
        String query = "UPDATE DOC_GIA SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, " +
                      "DiaChi = ?, SoDT = ?, Email = ?, NgayLapThe = ?, " +
                      "NgayHetHan = ?, TrangThai = ? WHERE MaDG = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, reader.getHoTen());
        pstmt.setDate(2, reader.getNgaySinh() != null ? Date.valueOf(reader.getNgaySinh()) : null);
        pstmt.setString(3, reader.getGioiTinh());
        pstmt.setString(4, reader.getDiaChi());
        pstmt.setString(5, reader.getSoDT());
        pstmt.setString(6, reader.getEmail());
        pstmt.setDate(7, Date.valueOf(reader.getNgayLapThe()));
        pstmt.setDate(8, Date.valueOf(reader.getNgayHetHan()));
        pstmt.setString(9, reader.getTrangThai());
        pstmt.setString(10, reader.getMaDG());
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Delete reader (soft delete)
     * @param maDG Reader ID
     * @return true if successful
     */
    public boolean deleteReader(String maDG) throws SQLException {
        String query = "UPDATE DOC_GIA SET TrangThai = 'Inactive' WHERE MaDG = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maDG);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Check if reader can borrow books
     * @param maDG Reader ID
     * @return true if can borrow
     */
    public boolean canBorrow(String maDG) throws SQLException {
        String query = "SELECT TrangThai, NgayHetHan FROM DOC_GIA WHERE MaDG = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maDG);
        ResultSet rs = pstmt.executeQuery();
        
        boolean canBorrow = false;
        if (rs.next()) {
            String trangThai = rs.getString("TrangThai");
            Date ngayHetHan = rs.getDate("NgayHetHan");
            
            canBorrow = "Active".equals(trangThai) && 
                       ngayHetHan != null && 
                       !ngayHetHan.toLocalDate().isBefore(LocalDate.now());
        }
        
        DBConnection.closeResources(rs, pstmt);
        return canBorrow;
    }
    
    /**
     * Renew reader membership
     * @param maDG Reader ID
     * @param months Number of months to extend
     * @return true if successful
     */
    public boolean renewMembership(String maDG, int months) throws SQLException {
        String query = "UPDATE DOC_GIA SET NgayHetHan = DATE_ADD(NgayHetHan, INTERVAL ? MONTH) " +
                      "WHERE MaDG = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, months);
        pstmt.setString(2, maDG);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Extract Reader object from ResultSet
     * @param rs ResultSet from query
     * @return Reader object
     */
    private Reader extractReaderFromResultSet(ResultSet rs) throws SQLException {
        Reader reader = new Reader();
        reader.setMaDG(rs.getString("MaDG"));
        reader.setHoTen(rs.getString("HoTen"));
        
        Date ngaySinh = rs.getDate("NgaySinh");
        if (ngaySinh != null) {
            reader.setNgaySinh(ngaySinh.toLocalDate());
        }
        
        reader.setGioiTinh(rs.getString("GioiTinh"));
        reader.setDiaChi(rs.getString("DiaChi"));
        reader.setSoDT(rs.getString("SoDT"));
        reader.setEmail(rs.getString("Email"));
        
        Date ngayLapThe = rs.getDate("NgayLapThe");
        if (ngayLapThe != null) {
            reader.setNgayLapThe(ngayLapThe.toLocalDate());
        }
        
        Date ngayHetHan = rs.getDate("NgayHetHan");
        if (ngayHetHan != null) {
            reader.setNgayHetHan(ngayHetHan.toLocalDate());
        }
        
        reader.setTrangThai(rs.getString("TrangThai"));
        
        Date createdAt = rs.getDate("CreatedAt");
        if (createdAt != null) {
            reader.setCreatedAt(createdAt.toLocalDate());
        }
        
        return reader;
    }
}
