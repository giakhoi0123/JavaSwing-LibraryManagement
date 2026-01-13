package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.util.DBConnection;

/**
 * DAO for Category operations
 */
public class CategoryDAO {
    
    /**
     * Get all categories as ID-Name map
     */
    public Map<String, String> getAllCategories() throws SQLException {
        Map<String, String> categories = new HashMap<>();
        String query = "SELECT MaTheLoai, TenTheLoai FROM THE_LOAI ORDER BY TenTheLoai";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                categories.put(rs.getString("MaTheLoai"), rs.getString("TenTheLoai"));
            }
        }
        
        return categories;
    }
    
    /**
     * Get all category IDs
     */
    public List<String> getAllCategoryIds() throws SQLException {
        List<String> ids = new ArrayList<>();
        String query = "SELECT MaTheLoai FROM THE_LOAI ORDER BY MaTheLoai";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ids.add(rs.getString("MaTheLoai"));
            }
        }
        
        return ids;
    }
}
