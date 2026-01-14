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
 * DAO for Author operations
 */
public class AuthorDAO {
    
    /**
     * Get all authors as ID-Name map
     */
    public Map<String, String> getAllAuthors() throws SQLException {
        Map<String, String> authors = new HashMap<>();
        String query = "SELECT MaTG, TenTG FROM TAC_GIA ORDER BY TenTG";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                authors.put(rs.getString("MaTG"), rs.getString("TenTG"));
            }
        }
        
        return authors;
    }
    
    /**
     * Get all author IDs
     */
    public List<String> getAllAuthorIds() throws SQLException {
        List<String> ids = new ArrayList<>();
        String query = "SELECT MaTG FROM TAC_GIA ORDER BY MaTG";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ids.add(rs.getString("MaTG"));
            }
        }
        
        return ids;
    }
    
    /**
     * Find author by name
     */
    public String findAuthorIdByName(String name) throws SQLException {
        String query = "SELECT MaTG FROM TAC_GIA WHERE TenTG = ?";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, name.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("MaTG");
                }
            }
        }
        
        return null;
    }
    
    /**
     * Generate next author ID
     */
    private String generateNextId() throws SQLException {
        String query = "SELECT MaTG FROM TAC_GIA ORDER BY MaTG DESC LIMIT 1";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                String lastId = rs.getString("MaTG");
                int number = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("TG%03d", number);
            }
        }
        
        return "TG001";
    }
    
    /**
     * Create new author automatically
     */
    public String createAuthorIfNotExists(String authorName) throws SQLException {
        // Check if author already exists
        String existingId = findAuthorIdByName(authorName);
        if (existingId != null) {
            return existingId;
        }
        
        // Generate new ID and create author
        String newId = generateNextId();
        String query = "INSERT INTO TAC_GIA (MaTG, TenTG) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newId);
            stmt.setString(2, authorName.trim());
            stmt.executeUpdate();
        }
        
        return newId;
    }
}
