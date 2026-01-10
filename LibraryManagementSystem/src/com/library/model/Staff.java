package com.library.model;

import java.time.LocalDate;

/**
 * Staff Entity (NHAN_VIEN)
 * Represents library staff members
 * 
 * @author Library Management System
 * @version 1.0
 */
public class Staff {
    
    private String maNV;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String soDT;
    private String email;
    private String chucVu;
    private String username;
    private String password;
    private String role;
    private String trangThai;
    private LocalDate createdAt;
    
    // Constructors
    public Staff() {
        this.trangThai = "Active";
        this.role = "Librarian";
        this.chucVu = "Thủ thư";
    }
    
    public Staff(String maNV, String hoTen, String soDT, String chucVu,
                 String username, String password, String role) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.chucVu = chucVu;
        this.username = username;
        this.password = password;
        this.role = role;
        this.trangThai = "Active";
    }
    
    // Getters and Setters
    public String getMaNV() {
        return maNV;
    }
    
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public String getHoTen() {
        return hoTen;
    }
    
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public String getGioiTinh() {
        return gioiTinh;
    }
    
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public String getSoDT() {
        return soDT;
    }
    
    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getChucVu() {
        return chucVu;
    }
    
    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    // Business methods
    public boolean isAdmin() {
        return "Admin".equals(role);
    }
    
    public boolean isLibrarian() {
        return "Librarian".equals(role);
    }
    
    public boolean isActive() {
        return "Active".equals(trangThai);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", maNV, hoTen, chucVu);
    }
}
