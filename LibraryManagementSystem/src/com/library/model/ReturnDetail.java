package com.library.model;

/**
 * ReturnDetail Entity (CT_PHIEU_TRA)
 * Represents details of books in a return ticket
 * 
 * @author Library Management System
 * @version 1.0
 */
public class ReturnDetail {
    
    private String maPT;
    private String maSach;
    private String tinhTrangLucTra;
    private double tienPhatSach;
    private String ghiChu;
    
    // For display
    private String tenSach;
    
    // Constructors
    public ReturnDetail() {
        this.tinhTrangLucTra = "Tốt";
        this.tienPhatSach = 0.0;
    }
    
    public ReturnDetail(String maPT, String maSach, String tinhTrangLucTra, double tienPhatSach) {
        this.maPT = maPT;
        this.maSach = maSach;
        this.tinhTrangLucTra = tinhTrangLucTra;
        this.tienPhatSach = tienPhatSach;
    }
    
    // Getters and Setters
    public String getMaPT() {
        return maPT;
    }
    
    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }
    
    public String getMaSach() {
        return maSach;
    }
    
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
    
    public String getTinhTrangLucTra() {
        return tinhTrangLucTra;
    }
    
    public void setTinhTrangLucTra(String tinhTrangLucTra) {
        this.tinhTrangLucTra = tinhTrangLucTra;
    }
    
    public double getTienPhatSach() {
        return tienPhatSach;
    }
    
    public void setTienPhatSach(double tienPhatSach) {
        this.tienPhatSach = tienPhatSach;
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
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", maSach, tenSach, tinhTrangLucTra);
    }
}
