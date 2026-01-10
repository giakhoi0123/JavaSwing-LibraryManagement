package com.library.model;

import java.time.LocalDate;

/**
 * Reader Entity (DOC_GIA)
 * Represents a library member/reader
 * 
 * @author Library Management System
 * @version 1.0
 */
public class Reader {
    
    private String maDG;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String soDT;
    private String email;
    private LocalDate ngayLapThe;
    private LocalDate ngayHetHan;
    private String trangThai;
    private LocalDate createdAt;
    
    // Constructors
    public Reader() {
        this.trangThai = "Active";
        this.ngayLapThe = LocalDate.now();
        this.ngayHetHan = LocalDate.now().plusYears(1);
    }
    
    public Reader(String maDG, String hoTen, LocalDate ngaySinh, String gioiTinh,
                  String diaChi, String soDT, String email) {
        this.maDG = maDG;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
        this.trangThai = "Active";
        this.ngayLapThe = LocalDate.now();
        this.ngayHetHan = LocalDate.now().plusYears(1);
    }
    
    // Getters and Setters
    public String getMaDG() {
        return maDG;
    }
    
    public void setMaDG(String maDG) {
        this.maDG = maDG;
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
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
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
    
    public LocalDate getNgayLapThe() {
        return ngayLapThe;
    }
    
    public void setNgayLapThe(LocalDate ngayLapThe) {
        this.ngayLapThe = ngayLapThe;
    }
    
    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }
    
    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
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
    public boolean isActive() {
        return "Active".equals(trangThai);
    }
    
    public boolean isExpired() {
        return ngayHetHan != null && ngayHetHan.isBefore(LocalDate.now());
    }
    
    public boolean canBorrow() {
        return isActive() && !isExpired();
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", maDG, hoTen, soDT);
    }
}
