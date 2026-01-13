-- Table for Staff Permissions
CREATE TABLE IF NOT EXISTS NHAN_VIEN_QUYEN (
    MaNV VARCHAR(20) PRIMARY KEY,
    QuanLySach BOOLEAN DEFAULT FALSE,
    QuanLyDocGia BOOLEAN DEFAULT TRUE,
    QuanLyMuon BOOLEAN DEFAULT TRUE,
    QuanLyTra BOOLEAN DEFAULT TRUE,
    QuanLyPhat BOOLEAN DEFAULT TRUE,
    QuanLyNhanVien BOOLEAN DEFAULT FALSE,
    XemThongKe BOOLEAN DEFAULT TRUE,
    XuatDuLieu BOOLEAN DEFAULT FALSE,
    NhapDuLieu BOOLEAN DEFAULT FALSE,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV) ON DELETE CASCADE
);

-- Insert default admin permissions (assuming 'NV001' is admin)
INSERT INTO NHAN_VIEN_QUYEN (MaNV, QuanLySach, QuanLyDocGia, QuanLyMuon, QuanLyTra, QuanLyPhat, QuanLyNhanVien, XemThongKe, XuatDuLieu, NhapDuLieu)
VALUES ('NV001', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE)
ON DUPLICATE KEY UPDATE 
    QuanLySach = TRUE, 
    QuanLyDocGia = TRUE, 
    QuanLyMuon = TRUE, 
    QuanLyTra = TRUE, 
    QuanLyPhat = TRUE, 
    QuanLyNhanVien = TRUE, 
    XemThongKe = TRUE, 
    XuatDuLieu = TRUE, 
    NhapDuLieu = TRUE;

-- Create tables for Author, Category, Publisher if they don't exist
CREATE TABLE IF NOT EXISTS TAC_GIA (
    MaTG VARCHAR(20) PRIMARY KEY,
    TenTG VARCHAR(100) NOT NULL,
    QuocTich VARCHAR(50),
    NamSinh INT,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS THE_LOAI (
    MaTheLoai VARCHAR(20) PRIMARY KEY,
    TenTheLoai VARCHAR(100) NOT NULL,
    MoTa TEXT,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS NHA_XUAT_BAN (
    MaNXB VARCHAR(20) PRIMARY KEY,
    TenNXB VARCHAR(150) NOT NULL,
    DiaChi VARCHAR(255),
    SoDT VARCHAR(20),
    Email VARCHAR(100),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert some sample data
INSERT INTO TAC_GIA (MaTG, TenTG, QuocTich, NamSinh) VALUES
('TG001', 'Nguyễn Nhật Ánh', 'Việt Nam', 1955),
('TG002', 'Tô Hoài', 'Việt Nam', 1920),
('TG003', 'Nam Cao', 'Việt Nam', 1915),
('TG004', 'Ngô Tất Tố', 'Việt Nam', 1894),
('TG005', 'Vũ Trọng Phụng', 'Việt Nam', 1912)
ON DUPLICATE KEY UPDATE TenTG = VALUES(TenTG);

INSERT INTO THE_LOAI (MaTheLoai, TenTheLoai, MoTa) VALUES
('TL001', 'Văn học', 'Sách văn học trong và ngoài nước'),
('TL002', 'Khoa học', 'Sách khoa học công nghệ'),
('TL003', 'Lịch sử', 'Sách về lịch sử các quốc gia'),
('TL004', 'Giáo dục', 'Sách giáo khoa và tham khảo'),
('TL005', 'Kinh tế', 'Sách về kinh tế và quản trị')
ON DUPLICATE KEY UPDATE TenTheLoai = VALUES(TenTheLoai);

INSERT INTO NHA_XUAT_BAN (MaNXB, TenNXB, DiaChi, SoDT, Email) VALUES
('NXB001', 'NXB Trẻ', 'TP. Hồ Chí Minh', '0283932', 'nxbtre@nxbtre.com.vn'),
('NXB002', 'NXB Giáo dục Việt Nam', 'Hà Nội', '02438222', 'info@nxbgd.vn'),
('NXB003', 'NXB Kim Đồng', 'Hà Nội', '02439434', 'kimdong@nxbkimdong.com.vn'),
('NXB004', 'NXB Văn học', 'Hà Nội', '02438225', 'info@nxbvanhoc.com.vn'),
('NXB005', 'NXB Lao động', 'Hà Nội', '02438517', 'nxblaodong@nxblaodong.com.vn')
ON DUPLICATE KEY UPDATE TenNXB = VALUES(TenNXB);
