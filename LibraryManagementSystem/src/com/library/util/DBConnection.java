package com.library.util;

import java.sql.*;

/**
 * Database Connection Utility Class
 * Singleton Pattern for managing MySQL database connections
 * 
 * @author Library Management System
 * @version 1.0
 */
public class DBConnection {
    
    private static DBConnection instance;
    private Connection connection;
    
    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Connection pool settings
    private static final String CONNECTION_PROPERTIES = 
        "?useSSL=false" +
        "&serverTimezone=Asia/Ho_Chi_Minh" +
        "&allowPublicKeyRetrieval=true" +
        "&useUnicode=true" +
        "&characterEncoding=UTF-8";
    
    /**
     * Private constructor to prevent instantiation
     */
    private DBConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DB_DRIVER);
            System.out.println("✓ MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    /**
     * Get singleton instance of DBConnection
     * @return DBConnection instance
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(
                    DB_URL + CONNECTION_PROPERTIES, 
                    DB_USER, 
                    DB_PASSWORD
                );
                System.out.println("✓ Database connected successfully");
            } catch (SQLException e) {
                System.err.println("✗ Failed to connect to database!");
                System.err.println("URL: " + DB_URL);
                System.err.println("Error: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Database connection closed");
            } catch (SQLException e) {
                System.err.println("✗ Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                // Test query
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1");
                if (rs.next()) {
                    System.out.println("✓ Database connection test passed");
                    rs.close();
                    stmt.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Database connection test failed: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Execute a query with parameters (Prepared Statement)
     * @param query SQL query
     * @param params Query parameters
     * @return ResultSet
     * @throws SQLException
     */
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Set parameters
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        return pstmt.executeQuery();
    }
    
    /**
     * Execute an update query (INSERT, UPDATE, DELETE)
     * @param query SQL query
     * @param params Query parameters
     * @return Number of affected rows
     * @throws SQLException
     */
    public int executeUpdate(String query, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Set parameters
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }
    
    /**
     * Begin transaction
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }
    
    /**
     * Commit transaction
     * @throws SQLException
     */
    public void commitTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.commit();
        conn.setAutoCommit(true);
    }
    
    /**
     * Rollback transaction
     * @throws SQLException
     */
    public void rollbackTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.rollback();
        conn.setAutoCommit(true);
    }
    
    /**
     * Close resources (ResultSet, Statement, Connection)
     * @param rs ResultSet to close
     * @param stmt Statement to close
     */
    public static void closeResources(ResultSet rs, Statement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("✗ Error closing resources: " + e.getMessage());
        }
    }
}
