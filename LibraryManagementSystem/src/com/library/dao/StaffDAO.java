package com.library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Staff;
import com.library.util.DBConnection;

/**
 * Data Access Object for Staff entity
 * Handles all database operations related to staff members
 * 
 * @author Library Management System
 * @version 1.0
 */
public class StaffDAO {
    
    private DBConnection dbConnection;
    
    public StaffDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    /**
     * Authenticate staff member
     * @param username Username
     * @param password Password (plain text - should be hashed in production)
     * @return Staff object if valid, null otherwise
     */
    public Staff authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM NHAN_VIEN WHERE Username = ? AND Password = ? " +
                      "AND TrangThai = 'Active'";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        
        ResultSet rs = pstmt.executeQuery();
        
        Staff staff = null;
        if (rs.next()) {
            staff = extractStaffFromResultSet(rs);
        }
        
        DBConnection.closeResources(rs, pstmt);
        return staff;
    }
    
    /**
     * Get all staff members
     * @return List of all staff
     */
    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM NHAN_VIEN ORDER BY HoTen";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            staffList.add(extractStaffFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return staffList;
    }
    
    /**
     * Get staff by ID
     * @param maNV Staff ID
     * @return Staff object or null if not found
     */
    public Staff getStaffById(String maNV) throws SQLException {
        String query = "SELECT * FROM NHAN_VIEN WHERE MaNV = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maNV);
        ResultSet rs = pstmt.executeQuery();
        
        Staff staff = null;
        if (rs.next()) {
            staff = extractStaffFromResultSet(rs);
        }
        
        DBConnection.closeResources(rs, pstmt);
        return staff;
    }
    
    /**
     * Insert new staff member
     * @param staff Staff object to insert
     * @return true if successful
     */
    public boolean insertStaff(Staff staff) throws SQLException {
        String query = "INSERT INTO NHAN_VIEN (MaNV, HoTen, NgaySinh, GioiTinh, SoDT, " +
                      "Email, ChucVu, Username, Password, Role, TrangThai) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, staff.getMaNV());
        pstmt.setString(2, staff.getHoTen());
        pstmt.setDate(3, staff.getNgaySinh() != null ? Date.valueOf(staff.getNgaySinh()) : null);
        pstmt.setString(4, staff.getGioiTinh());
        pstmt.setString(5, staff.getSoDT());
        pstmt.setString(6, staff.getEmail());
        pstmt.setString(7, staff.getChucVu());
        pstmt.setString(8, staff.getUsername());
        pstmt.setString(9, staff.getPassword());
        pstmt.setString(10, staff.getRole());
        pstmt.setString(11, staff.getTrangThai());
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Update existing staff member
     * @param staff Staff object with updated data
     * @return true if successful
     */
    public boolean updateStaff(Staff staff) throws SQLException {
        String query = "UPDATE NHAN_VIEN SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, " +
                      "SoDT = ?, Email = ?, ChucVu = ?, Username = ?, Password = ?, " +
                      "Role = ?, TrangThai = ? WHERE MaNV = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, staff.getHoTen());
        pstmt.setDate(2, staff.getNgaySinh() != null ? Date.valueOf(staff.getNgaySinh()) : null);
        pstmt.setString(3, staff.getGioiTinh());
        pstmt.setString(4, staff.getSoDT());
        pstmt.setString(5, staff.getEmail());
        pstmt.setString(6, staff.getChucVu());
        pstmt.setString(7, staff.getUsername());
        pstmt.setString(8, staff.getPassword());
        pstmt.setString(9, staff.getRole());
        pstmt.setString(10, staff.getTrangThai());
        pstmt.setString(11, staff.getMaNV());
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Check if username exists
     * @param username Username to check
     * @return true if exists
     */
    public boolean usernameExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM NHAN_VIEN WHERE Username = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        
        boolean exists = false;
        if (rs.next()) {
            exists = rs.getInt(1) > 0;
        }
        
        DBConnection.closeResources(rs, pstmt);
        return exists;
    }
    
    /**
     * Delete staff member (soft delete - set Inactive)
     * @param maNV Staff ID
     * @return true if successful
     */
    public boolean deleteStaff(String maNV) throws SQLException {
        String query = "UPDATE NHAN_VIEN SET TrangThai = 'Inactive' WHERE MaNV = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maNV);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Reset staff password
     * @param maNV Staff ID
     * @param newPassword New password
     * @return true if successful
     */
    public boolean resetPassword(String maNV, String newPassword) throws SQLException {
        String query = "UPDATE NHAN_VIEN SET Password = ? WHERE MaNV = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newPassword);
        pstmt.setString(2, maNV);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Extract Staff object from ResultSet
     * @param rs ResultSet from query
     * @return Staff object
     */
    private Staff extractStaffFromResultSet(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setMaNV(rs.getString("MaNV"));
        staff.setHoTen(rs.getString("HoTen"));
        
        Date ngaySinh = rs.getDate("NgaySinh");
        if (ngaySinh != null) {
            staff.setNgaySinh(ngaySinh.toLocalDate());
        }
        
        staff.setGioiTinh(rs.getString("GioiTinh"));
        staff.setSoDT(rs.getString("SoDT"));
        staff.setEmail(rs.getString("Email"));
        staff.setChucVu(rs.getString("ChucVu"));
        staff.setUsername(rs.getString("Username"));
        staff.setPassword(rs.getString("Password"));
        staff.setRole(rs.getString("Role"));
        staff.setTrangThai(rs.getString("TrangThai"));
        
        Date createdAt = rs.getDate("CreatedAt");
        if (createdAt != null) {
            staff.setCreatedAt(createdAt.toLocalDate());
        }
        
        return staff;
    }
}
