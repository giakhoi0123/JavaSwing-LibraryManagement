package com.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ReturnTicket Entity (PHIEU_TRA)
 * Represents a book return transaction
 * 
 * @author Library Management System
 * @version 1.0
 */
public class ReturnTicket {
    
    private String maPT;
    private String maPM;
    private LocalDate ngayTra;
    private double tienPhat;
    private String lyDoPhat;
    private String maNV;
    private String ghiChu;
    private LocalDate createdAt;
    
    // For display
    private String tenNhanVien;
    private LocalDate hanTra;
    private int soNgayTre;
    private List<ReturnDetail> returnDetails;
    
    // Constructors
    public ReturnTicket() {
        this.ngayTra = LocalDate.now();
        this.tienPhat = 0.0;
        this.returnDetails = new ArrayList<>();
    }
    
    public ReturnTicket(String maPT, String maPM, LocalDate ngayTra, 
                        double tienPhat, String maNV) {
        this.maPT = maPT;
        this.maPM = maPM;
        this.ngayTra = ngayTra;
        this.tienPhat = tienPhat;
        this.maNV = maNV;
        this.returnDetails = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getMaPT() {
        return maPT;
    }
    
    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }
    
    public String getMaPM() {
        return maPM;
    }
    
    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }
    
    public LocalDate getNgayTra() {
        return ngayTra;
    }
    
    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }
    
    public double getTienPhat() {
        return tienPhat;
    }
    
    public void setTienPhat(double tienPhat) {
        this.tienPhat = tienPhat;
    }
    
    public String getLyDoPhat() {
        return lyDoPhat;
    }
    
    public void setLyDoPhat(String lyDoPhat) {
        this.lyDoPhat = lyDoPhat;
    }
    
    public String getMaNV() {
        return maNV;
    }
    
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getTenNhanVien() {
        return tenNhanVien;
    }
    
    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }
    
    public LocalDate getHanTra() {
        return hanTra;
    }
    
    public void setHanTra(LocalDate hanTra) {
        this.hanTra = hanTra;
    }
    
    public int getSoNgayTre() {
        return soNgayTre;
    }
    
    public void setSoNgayTre(int soNgayTre) {
        this.soNgayTre = soNgayTre;
    }
    
    public List<ReturnDetail> getReturnDetails() {
        return returnDetails;
    }
    
    public void setReturnDetails(List<ReturnDetail> returnDetails) {
        this.returnDetails = returnDetails;
    }
    
    public void addReturnDetail(ReturnDetail detail) {
        this.returnDetails.add(detail);
    }
    
    // Business methods
    public boolean hasLateFine() {
        return tienPhat > 0;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] Trả ngày %s - Phạt: %.0f VND", 
                           maPT, ngayTra, tienPhat);
    }
}
