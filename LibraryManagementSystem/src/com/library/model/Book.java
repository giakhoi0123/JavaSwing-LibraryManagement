package com.library.model;

import java.time.LocalDate;

/**
 * Book Entity (SACH)
 * Represents a book in the library system
 * 
 * @author Library Management System
 * @version 1.0
 */
public class Book {
    
    private String maSach;
    private String tenSach;
    private String maTG;
    private String tenTG;          // For display (JOIN with TAC_GIA)
    private String maTheLoai;
    private String tenTheLoai;     // For display (JOIN with THE_LOAI)
    private String maNXB;
    private String tenNXB;         // For display (JOIN with NHA_XUAT_BAN)
    private int namXB;
    private int soLuong;
    private double donGia;
    private String viTri;
    private String trangThai;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
    // Constructors
    public Book() {
        this.trangThai = "Active";
        this.soLuong = 0;
        this.donGia = 0.0;
    }
    
    public Book(String maSach, String tenSach, String maTG, String maTheLoai, 
                String maNXB, int namXB, int soLuong, double donGia, String viTri) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maTG = maTG;
        this.maTheLoai = maTheLoai;
        this.maNXB = maNXB;
        this.namXB = namXB;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.viTri = viTri;
        this.trangThai = "Active";
    }
    
    // Getters and Setters
    public String getMaSach() {
        return maSach;
    }
    
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
    
    public String getTenSach() {
        return tenSach;
    }
    
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
    
    public String getMaTG() {
        return maTG;
    }
    
    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }
    
    public String getTenTG() {
        return tenTG;
    }
    
    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    }
    
    public String getMaTheLoai() {
        return maTheLoai;
    }
    
    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }
    
    public String getTenTheLoai() {
        return tenTheLoai;
    }
    
    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }
    
    public String getMaNXB() {
        return maNXB;
    }
    
    public void setMaNXB(String maNXB) {
        this.maNXB = maNXB;
    }
    
    public String getTenNXB() {
        return tenNXB;
    }
    
    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }
    
    public int getNamXB() {
        return namXB;
    }
    
    public void setNamXB(int namXB) {
        this.namXB = namXB;
    }
    
    public int getSoLuong() {
        return soLuong;
    }
    
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    public double getDonGia() {
        return donGia;
    }
    
    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    
    public String getViTri() {
        return viTri;
    }
    
    public void setViTri(String viTri) {
        this.viTri = viTri;
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
    
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Business methods
    public boolean isAvailable() {
        return soLuong > 0 && "Active".equals(trangThai);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", maSach, tenSach, tenTG);
    }
}
