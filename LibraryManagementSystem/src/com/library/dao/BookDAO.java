package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Book entity
 * Handles all database operations related to books
 * 
 * @author Library Management System
 * @version 1.0
 */
public class BookDAO {
    
    private DBConnection dbConnection;
    
    public BookDAO() {
        this.dbConnection = DBConnection.getInstance();
    }
    
    /**
     * Get all books with author, category, and publisher info
     * @return List of all books
     */
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT s.*, tg.TenTG, tl.TenTheLoai, nxb.TenNXB " +
                      "FROM SACH s " +
                      "LEFT JOIN TAC_GIA tg ON s.MaTG = tg.MaTG " +
                      "LEFT JOIN THE_LOAI tl ON s.MaTheLoai = tl.MaTheLoai " +
                      "LEFT JOIN NHA_XUAT_BAN nxb ON s.MaNXB = nxb.MaNXB " +
                      "ORDER BY s.TenSach";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            books.add(extractBookFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return books;
    }
    
    /**
     * Get available books (SoLuong > 0 and Active)
     * @return List of available books
     */
    public List<Book> getAvailableBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT s.*, tg.TenTG, tl.TenTheLoai, nxb.TenNXB " +
                      "FROM SACH s " +
                      "LEFT JOIN TAC_GIA tg ON s.MaTG = tg.MaTG " +
                      "LEFT JOIN THE_LOAI tl ON s.MaTheLoai = tl.MaTheLoai " +
                      "LEFT JOIN NHA_XUAT_BAN nxb ON s.MaNXB = nxb.MaNXB " +
                      "WHERE s.SoLuong > 0 AND s.TrangThai = 'Active' " +
                      "ORDER BY s.TenSach";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            books.add(extractBookFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, stmt);
        return books;
    }
    
    /**
     * Get book by ID
     * @param maSach Book ID
     * @return Book object or null if not found
     */
    public Book getBookById(String maSach) throws SQLException {
        String query = "SELECT s.*, tg.TenTG, tl.TenTheLoai, nxb.TenNXB " +
                      "FROM SACH s " +
                      "LEFT JOIN TAC_GIA tg ON s.MaTG = tg.MaTG " +
                      "LEFT JOIN THE_LOAI tl ON s.MaTheLoai = tl.MaTheLoai " +
                      "LEFT JOIN NHA_XUAT_BAN nxb ON s.MaNXB = nxb.MaNXB " +
                      "WHERE s.MaSach = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maSach);
        ResultSet rs = pstmt.executeQuery();
        
        Book book = null;
        if (rs.next()) {
            book = extractBookFromResultSet(rs);
        }
        
        DBConnection.closeResources(rs, pstmt);
        return book;
    }
    
    /**
     * Search books by keyword (search in title, author, category)
     * @param keyword Search keyword
     * @return List of matching books
     */
    public List<Book> searchBooks(String keyword) throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT s.*, tg.TenTG, tl.TenTheLoai, nxb.TenNXB " +
                      "FROM SACH s " +
                      "LEFT JOIN TAC_GIA tg ON s.MaTG = tg.MaTG " +
                      "LEFT JOIN THE_LOAI tl ON s.MaTheLoai = tl.MaTheLoai " +
                      "LEFT JOIN NHA_XUAT_BAN nxb ON s.MaNXB = nxb.MaNXB " +
                      "WHERE s.TenSach LIKE ? " +
                      "   OR tg.TenTG LIKE ? " +
                      "   OR tl.TenTheLoai LIKE ? " +
                      "   OR s.MaSach LIKE ? " +
                      "ORDER BY s.TenSach";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        String searchPattern = "%" + keyword + "%";
        pstmt.setString(1, searchPattern);
        pstmt.setString(2, searchPattern);
        pstmt.setString(3, searchPattern);
        pstmt.setString(4, searchPattern);
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            books.add(extractBookFromResultSet(rs));
        }
        
        DBConnection.closeResources(rs, pstmt);
        return books;
    }
    
    /**
     * Insert new book
     * @param book Book object to insert
     * @return true if successful
     */
    public boolean insertBook(Book book) throws SQLException {
        String query = "INSERT INTO SACH (MaSach, TenSach, MaTG, MaTheLoai, MaNXB, " +
                      "NamXB, SoLuong, DonGia, ViTri, TrangThai) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, book.getMaSach());
        pstmt.setString(2, book.getTenSach());
        pstmt.setString(3, book.getMaTG());
        pstmt.setString(4, book.getMaTheLoai());
        pstmt.setString(5, book.getMaNXB());
        pstmt.setInt(6, book.getNamXB());
        pstmt.setInt(7, book.getSoLuong());
        pstmt.setDouble(8, book.getDonGia());
        pstmt.setString(9, book.getViTri());
        pstmt.setString(10, book.getTrangThai());
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Update existing book
     * @param book Book object with updated data
     * @return true if successful
     */
    public boolean updateBook(Book book) throws SQLException {
        String query = "UPDATE SACH SET TenSach = ?, MaTG = ?, MaTheLoai = ?, " +
                      "MaNXB = ?, NamXB = ?, SoLuong = ?, DonGia = ?, " +
                      "ViTri = ?, TrangThai = ? " +
                      "WHERE MaSach = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, book.getTenSach());
        pstmt.setString(2, book.getMaTG());
        pstmt.setString(3, book.getMaTheLoai());
        pstmt.setString(4, book.getMaNXB());
        pstmt.setInt(5, book.getNamXB());
        pstmt.setInt(6, book.getSoLuong());
        pstmt.setDouble(7, book.getDonGia());
        pstmt.setString(8, book.getViTri());
        pstmt.setString(9, book.getTrangThai());
        pstmt.setString(10, book.getMaSach());
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Delete book (soft delete - set TrangThai = 'Inactive')
     * @param maSach Book ID
     * @return true if successful
     */
    public boolean deleteBook(String maSach) throws SQLException {
        String query = "UPDATE SACH SET TrangThai = 'Inactive' WHERE MaSach = ?";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maSach);
        
        int result = pstmt.executeUpdate();
        pstmt.close();
        
        return result > 0;
    }
    
    /**
     * Check if book is available for borrowing
     * @param maSach Book ID
     * @return true if available
     */
    public boolean isBookAvailable(String maSach) throws SQLException {
        String query = "SELECT SoLuong FROM SACH WHERE MaSach = ? AND TrangThai = 'Active'";
        
        Connection conn = dbConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, maSach);
        ResultSet rs = pstmt.executeQuery();
        
        boolean available = false;
        if (rs.next()) {
            available = rs.getInt("SoLuong") > 0;
        }
        
        DBConnection.closeResources(rs, pstmt);
        return available;
    }
    
    /**
     * Get all distinct categories
     * @return List of category names
     */
    public List<String> getAllCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT TenTheLoai FROM THE_LOAI ORDER BY TenTheLoai";
        
        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            categories.add(rs.getString("TenTheLoai"));
        }
        
        DBConnection.closeResources(rs, stmt);
        return categories;
    }
    
    /**
     * Extract Book object from ResultSet
     * @param rs ResultSet from query
     * @return Book object
     */
    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setMaSach(rs.getString("MaSach"));
        book.setTenSach(rs.getString("TenSach"));
        book.setMaTG(rs.getString("MaTG"));
        book.setTenTG(rs.getString("TenTG"));
        book.setMaTheLoai(rs.getString("MaTheLoai"));
        book.setTenTheLoai(rs.getString("TenTheLoai"));
        book.setMaNXB(rs.getString("MaNXB"));
        book.setTenNXB(rs.getString("TenNXB"));
        book.setNamXB(rs.getInt("NamXB"));
        book.setSoLuong(rs.getInt("SoLuong"));
        book.setDonGia(rs.getDouble("DonGia"));
        book.setViTri(rs.getString("ViTri"));
        book.setTrangThai(rs.getString("TrangThai"));
        
        Date createdAt = rs.getDate("CreatedAt");
        if (createdAt != null) {
            book.setCreatedAt(createdAt.toLocalDate());
        }
        
        Date updatedAt = rs.getDate("UpdatedAt");
        if (updatedAt != null) {
            book.setUpdatedAt(updatedAt.toLocalDate());
        }
        
        return book;
    }
}
