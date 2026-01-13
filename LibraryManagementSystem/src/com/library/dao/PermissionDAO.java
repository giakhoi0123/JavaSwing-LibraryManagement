package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.model.Permission;
import com.library.util.DBConnection;

/**
 * DAO for Permission management
 */
public class PermissionDAO {
    
    /**
     * Get permissions for a staff member
     */
    public Permission getPermissionsByStaffId(String maNV) throws SQLException {
        String query = "SELECT * FROM NHAN_VIEN_QUYEN WHERE MaNV = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Permission perm = new Permission(maNV);
                perm.setCanManageBooks(rs.getBoolean("QuanLySach"));
                perm.setCanManageReaders(rs.getBoolean("QuanLyDocGia"));
                perm.setCanManageBorrow(rs.getBoolean("QuanLyMuon"));
                perm.setCanManageReturn(rs.getBoolean("QuanLyTra"));
                perm.setCanManageFines(rs.getBoolean("QuanLyPhat"));
                perm.setCanManageStaff(rs.getBoolean("QuanLyNhanVien"));
                perm.setCanViewStatistics(rs.getBoolean("XemThongKe"));
                perm.setCanExportData(rs.getBoolean("XuatDuLieu"));
                perm.setCanImportData(rs.getBoolean("NhapDuLieu"));
                return perm;
            }
            
            // If no permissions found, return default librarian permissions
            return Permission.getLibrarianPermissions(maNV);
        }
    }
    
    /**
     * Save or update permissions for a staff member
     */
    public boolean savePermissions(Permission permission) throws SQLException {
        // Check if permissions already exist
        String checkQuery = "SELECT COUNT(*) FROM NHAN_VIEN_QUYEN WHERE MaNV = ?";
        boolean exists = false;
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
            
            stmt.setString(1, permission.getMaNV());
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
        }
        
        if (exists) {
            return updatePermissions(permission);
        } else {
            return insertPermissions(permission);
        }
    }
    
    private boolean insertPermissions(Permission permission) throws SQLException {
        String query = "INSERT INTO NHAN_VIEN_QUYEN (MaNV, QuanLySach, QuanLyDocGia, QuanLyMuon, " +
                      "QuanLyTra, QuanLyPhat, QuanLyNhanVien, XemThongKe, XuatDuLieu, NhapDuLieu) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, permission.getMaNV());
            stmt.setBoolean(2, permission.isCanManageBooks());
            stmt.setBoolean(3, permission.isCanManageReaders());
            stmt.setBoolean(4, permission.isCanManageBorrow());
            stmt.setBoolean(5, permission.isCanManageReturn());
            stmt.setBoolean(6, permission.isCanManageFines());
            stmt.setBoolean(7, permission.isCanManageStaff());
            stmt.setBoolean(8, permission.isCanViewStatistics());
            stmt.setBoolean(9, permission.isCanExportData());
            stmt.setBoolean(10, permission.isCanImportData());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    private boolean updatePermissions(Permission permission) throws SQLException {
        String query = "UPDATE NHAN_VIEN_QUYEN SET QuanLySach = ?, QuanLyDocGia = ?, QuanLyMuon = ?, " +
                      "QuanLyTra = ?, QuanLyPhat = ?, QuanLyNhanVien = ?, XemThongKe = ?, " +
                      "XuatDuLieu = ?, NhapDuLieu = ? WHERE MaNV = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setBoolean(1, permission.isCanManageBooks());
            stmt.setBoolean(2, permission.isCanManageReaders());
            stmt.setBoolean(3, permission.isCanManageBorrow());
            stmt.setBoolean(4, permission.isCanManageReturn());
            stmt.setBoolean(5, permission.isCanManageFines());
            stmt.setBoolean(6, permission.isCanManageStaff());
            stmt.setBoolean(7, permission.isCanViewStatistics());
            stmt.setBoolean(8, permission.isCanExportData());
            stmt.setBoolean(9, permission.isCanImportData());
            stmt.setString(10, permission.getMaNV());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Delete permissions for a staff member
     */
    public boolean deletePermissions(String maNV) throws SQLException {
        String query = "DELETE FROM NHAN_VIEN_QUYEN WHERE MaNV = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, maNV);
            return stmt.executeUpdate() > 0;
        }
    }
}
