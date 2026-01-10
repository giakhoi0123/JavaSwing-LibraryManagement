package com.library.model;

/**
 * BorrowDetail Entity (CT_PHIEU_MUON)
 * Represents details of books in a borrow ticket
 * 
 * @author Library Management System
 * @version 1.0
 */
public class BorrowDetail {
    
    private String maPM;
    private String maSach;
    private String tinhTrangLucMuon;
    private String ghiChu;
    
    // For display (JOIN with SACH)
    private String tenSach;
    private String tenTacGia;
    private String viTri;
    
    // Constructors
    public BorrowDetail() {
        this.tinhTrangLucMuon = "Tốt";
    }
    
    public BorrowDetail(String maPM, String maSach, String tinhTrangLucMuon) {
        this.maPM = maPM;
        this.maSach = maSach;
        this.tinhTrangLucMuon = tinhTrangLucMuon;
    }
    
    // Getters and Setters
    public String getMaPM() {
        return maPM;
    }
    
    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }
    
    public String getMaSach() {
        return maSach;
    }
    
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
    
    public String getTinhTrangLucMuon() {
        return tinhTrangLucMuon;
    }
    
    public void setTinhTrangLucMuon(String tinhTrangLucMuon) {
        this.tinhTrangLucMuon = tinhTrangLucMuon;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public String getTenSach() {
        return tenSach;
    }
    
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
    
    public String getTenTacGia() {
        return tenTacGia;
    }
    
    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }
    
    public String getViTri() {
        return viTri;
    }
    
    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", maSach, tenSach, tinhTrangLucMuon);
    }
}
