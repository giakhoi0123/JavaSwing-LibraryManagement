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
 * DAO for Publisher operations
 */
public class PublisherDAO {
    
    /**
     * Get all publishers as ID-Name map
     */
    public Map<String, String> getAllPublishers() throws SQLException {
        Map<String, String> publishers = new HashMap<>();
        String query = "SELECT MaNXB, TenNXB FROM NHA_XUAT_BAN ORDER BY TenNXB";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                publishers.put(rs.getString("MaNXB"), rs.getString("TenNXB"));
            }
        }
        
        return publishers;
    }
    
    /**
     * Get all publisher IDs
     */
    public List<String> getAllPublisherIds() throws SQLException {
        List<String> ids = new ArrayList<>();
        String query = "SELECT MaNXB FROM NHA_XUAT_BAN ORDER BY MaNXB";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ids.add(rs.getString("MaNXB"));
            }
        }
        
        return ids;
    }
}
