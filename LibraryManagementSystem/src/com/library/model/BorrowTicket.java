package com.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * BorrowTicket Entity (PHIEU_MUON)
 * Represents a book borrowing transaction
 * 
 * @author Library Management System
 * @version 1.0
 */
public class BorrowTicket {
    
    private String maPM;
    private String maDG;
    private String maNV;
    private LocalDate ngayMuon;
    private LocalDate hanTra;
    private String trangThai;
    private String ghiChu;
    private LocalDate createdAt;
    
    // For display (JOIN with other tables)
    private String tenDocGia;
    private String tenNhanVien;
    private List<BorrowDetail> borrowDetails;
    
    // Constructors
    public BorrowTicket() {
        this.ngayMuon = LocalDate.now();
        this.hanTra = LocalDate.now().plusDays(14); // Default: 14 days
        this.trangThai = "Đang mượn";
        this.borrowDetails = new ArrayList<>();
    }
    
    public BorrowTicket(String maPM, String maDG, String maNV, 
                        LocalDate ngayMuon, LocalDate hanTra) {
        this.maPM = maPM;
        this.maDG = maDG;
        this.maNV = maNV;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.trangThai = "Đang mượn";
        this.borrowDetails = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getMaPM() {
        return maPM;
    }
    
    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }
    
    public String getMaDG() {
        return maDG;
    }
    
    public void setMaDG(String maDG) {
        this.maDG = maDG;
    }
    
    public String getMaNV() {
        return maNV;
    }
    
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public LocalDate getNgayMuon() {
        return ngayMuon;
    }
    
    public void setNgayMuon(LocalDate ngayMuon) {
        this.ngayMuon = ngayMuon;
    }
    
    public LocalDate getHanTra() {
        return hanTra;
    }
    
    /**
     * Alias for getHanTra() - return date
     * @return the return deadline date
     */
    public LocalDate getNgayHenTra() {
        return hanTra;
    }
    
    public void setHanTra(LocalDate hanTra) {
        this.hanTra = hanTra;
    }
    
    public String getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
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
    
    public String getTenDocGia() {
        return tenDocGia;
    }
    
    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia = tenDocGia;
    }
    
    public String getTenNhanVien() {
        return tenNhanVien;
    }
    
    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }
    
    public List<BorrowDetail> getBorrowDetails() {
        return borrowDetails;
    }
    
    public void setBorrowDetails(List<BorrowDetail> borrowDetails) {
        this.borrowDetails = borrowDetails;
    }
    
    public void addBorrowDetail(BorrowDetail detail) {
        this.borrowDetails.add(detail);
    }
    
    // Business methods
    public boolean isOverdue() {
        return "Đang mượn".equals(trangThai) && 
               hanTra != null && 
               hanTra.isBefore(LocalDate.now());
    }
    
    public boolean isReturned() {
        return "Đã trả".equals(trangThai);
    }
    
    public int getTotalBooks() {
        return borrowDetails.size();
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", maPM, tenDocGia, trangThai);
    }
}
