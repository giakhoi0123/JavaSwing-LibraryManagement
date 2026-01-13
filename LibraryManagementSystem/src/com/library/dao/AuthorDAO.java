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
}
