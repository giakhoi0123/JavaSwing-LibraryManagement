package com.library.model;

import java.time.LocalDate;

/**
 * TicketFine Model - Phiếu Phạt
 * Represents a fine ticket issued to readers for late returns or damaged books
 * Based on ERD: {MaPP, MaDG, MaNV, MaPM, NgayLap, TienPhat_TreHan, TienPhat_HuHong, TongTien, GhiChu, TrangThaiThanhToan, NgayThanhToan}
 */
public class TicketFine {
    
    private String maPP;                    // Fine ticket ID (PK)
    private String maDG;                    // Reader ID (FK)
    private String maNV;                    // Staff ID (FK)
    private String maPM;                    // Borrow ticket ID (FK, optional)
    private LocalDate ngayLap;              // Issue date
    private double tienPhatTreHan;          // Late fine amount
    private double tienPhatHuHong;          // Damage/lost book fine amount
    private double tongTien;                // Total fine amount
    private String ghiChu;                  // Notes/reasons
    private String trangThaiThanhToan;      // Payment status: "Chưa thanh toán", "Đã thanh toán"
    private LocalDate ngayThanhToan;        // Payment date
    
    // Additional fields for display
    private String tenDocGia;               // Reader name (for display)
    private String tenNhanVien;             // Staff name (for display)
    
    /**
     * Default constructor
     */
    public TicketFine() {
        this.ngayLap = LocalDate.now();
        this.tienPhatTreHan = 0.0;
        this.tienPhatHuHong = 0.0;
        this.tongTien = 0.0;
        this.trangThaiThanhToan = "Chưa thanh toán";
    }
    
    /**
     * Full constructor
     */
    public TicketFine(String maPP, String maDG, String maNV, String maPM, 
                      LocalDate ngayLap, double tienPhatTreHan, double tienPhatHuHong, 
                      double tongTien, String ghiChu, String trangThaiThanhToan, 
                      LocalDate ngayThanhToan) {
        this.maPP = maPP;
        this.maDG = maDG;
        this.maNV = maNV;
        this.maPM = maPM;
        this.ngayLap = ngayLap;
        this.tienPhatTreHan = tienPhatTreHan;
        this.tienPhatHuHong = tienPhatHuHong;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.ngayThanhToan = ngayThanhToan;
    }
    
    // Getters and Setters
    
    public String getMaPP() {
        return maPP;
    }
    
    public void setMaPP(String maPP) {
        this.maPP = maPP;
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
    
    public String getMaPM() {
        return maPM;
    }
    
    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }
    
    public LocalDate getNgayLap() {
        return ngayLap;
    }
    
    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }
    
    public double getTienPhatTreHan() {
        return tienPhatTreHan;
    }
    
    public void setTienPhatTreHan(double tienPhatTreHan) {
        this.tienPhatTreHan = tienPhatTreHan;
        updateTongTien();
    }
    
    public double getTienPhatHuHong() {
        return tienPhatHuHong;
    }
    
    public void setTienPhatHuHong(double tienPhatHuHong) {
        this.tienPhatHuHong = tienPhatHuHong;
        updateTongTien();
    }
    
    public double getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }
    
    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }
    
    public LocalDate getNgayThanhToan() {
        return ngayThanhToan;
    }
    
    public void setNgayThanhToan(LocalDate ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
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
    
    /**
     * Auto-update total fine when component fines change
     */
    private void updateTongTien() {
        this.tongTien = this.tienPhatTreHan + this.tienPhatHuHong;
    }
    
    /**
     * Mark as paid
     */
    public void markAsPaid() {
        this.trangThaiThanhToan = "Đã thanh toán";
        this.ngayThanhToan = LocalDate.now();
    }
    
    /**
     * Check if paid
     */
    public boolean isPaid() {
        return "Đã thanh toán".equals(this.trangThaiThanhToan);
    }
    
    @Override
    public String toString() {
        return String.format("TicketFine[MaPP=%s, MaDG=%s, TongTien=%.0f, TrangThai=%s]",
            maPP, maDG, tongTien, trangThaiThanhToan);
    }
}
