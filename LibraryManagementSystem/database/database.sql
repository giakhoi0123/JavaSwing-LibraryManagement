-- =====================================================
-- LIBRARY MANAGEMENT SYSTEM - DATABASE SCHEMA
-- Database: MySQL 8.0+ / SQL Server 2019+
-- Encoding: UTF-8
-- =====================================================

-- Drop database if exists (optional - for clean install)
-- DROP DATABASE IF EXISTS library_management;

CREATE DATABASE IF NOT EXISTS library_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE library_management;

-- =====================================================
-- TABLE: TAC_GIA (Authors)
-- =====================================================
CREATE TABLE TAC_GIA (
    MaTG VARCHAR(10) PRIMARY KEY,
    TenTG NVARCHAR(100) NOT NULL,
    GhiChu NVARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: THE_LOAI (Categories)
-- =====================================================
CREATE TABLE THE_LOAI (
    MaTheLoai VARCHAR(10) PRIMARY KEY,
    TenTheLoai NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: NHA_XUAT_BAN (Publishers)
-- =====================================================
CREATE TABLE NHA_XUAT_BAN (
    MaNXB VARCHAR(10) PRIMARY KEY,
    TenNXB NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    SoDT VARCHAR(15)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: SACH (Books)
-- =====================================================
CREATE TABLE SACH (
    MaSach VARCHAR(10) PRIMARY KEY,
    TenSach NVARCHAR(200) NOT NULL,
    MaTG VARCHAR(10),
    MaTheLoai VARCHAR(10),
    MaNXB VARCHAR(10),
    NamXB INT CHECK (NamXB >= 1900 AND NamXB <= 2100),
    SoLuong INT NOT NULL DEFAULT 0 CHECK (SoLuong >= 0),
    DonGia DOUBLE NOT NULL DEFAULT 0 CHECK (DonGia >= 0),
    ViTri VARCHAR(50),
    TrangThai ENUM('Active', 'Inactive') DEFAULT 'Active',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_sach_tacgia FOREIGN KEY (MaTG) REFERENCES TAC_GIA(MaTG) ON DELETE SET NULL,
    CONSTRAINT fk_sach_theloai FOREIGN KEY (MaTheLoai) REFERENCES THE_LOAI(MaTheLoai) ON DELETE SET NULL,
    CONSTRAINT fk_sach_nxb FOREIGN KEY (MaNXB) REFERENCES NHA_XUAT_BAN(MaNXB) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: DOC_GIA (Readers)
-- =====================================================
CREATE TABLE DOC_GIA (
    MaDG VARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh ENUM('Nam', 'Nữ', 'Khác') DEFAULT 'Khác',
    DiaChi NVARCHAR(255),
    SoDT VARCHAR(15) NOT NULL,
    Email VARCHAR(100),
    NgayLapThe DATE NOT NULL DEFAULT (CURDATE()),
    NgayHetHan DATE NOT NULL,
    TrangThai ENUM('Active', 'Inactive', 'Suspended') DEFAULT 'Active',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT chk_ngayhethan CHECK (NgayHetHan > NgayLapThe)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: NHAN_VIEN (Staff)
-- =====================================================
CREATE TABLE NHAN_VIEN (
    MaNV VARCHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh ENUM('Nam', 'Nữ', 'Khác') DEFAULT 'Khác',
    SoDT VARCHAR(15) NOT NULL,
    Email VARCHAR(100),
    ChucVu NVARCHAR(50) NOT NULL DEFAULT 'Thủ thư',
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('Admin', 'Librarian') NOT NULL DEFAULT 'Librarian',
    TrangThai ENUM('Active', 'Inactive') DEFAULT 'Active',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_username (Username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: PHIEU_MUON (Borrow Tickets)
-- =====================================================
CREATE TABLE PHIEU_MUON (
    MaPM VARCHAR(10) PRIMARY KEY,
    MaDG VARCHAR(10) NOT NULL,
    MaNV VARCHAR(10) NOT NULL,
    NgayMuon DATE NOT NULL DEFAULT (CURDATE()),
    HanTra DATE NOT NULL,
    TrangThai ENUM('Đang mượn', 'Đã trả', 'Quá hạn') DEFAULT 'Đang mượn',
    GhiChu NVARCHAR(255),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_pm_docgia FOREIGN KEY (MaDG) REFERENCES DOC_GIA(MaDG) ON DELETE CASCADE,
    CONSTRAINT fk_pm_nhanvien FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV) ON DELETE RESTRICT,
    CONSTRAINT chk_hantra CHECK (HanTra > NgayMuon)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: CT_PHIEU_MUON (Borrow Details)
-- =====================================================
CREATE TABLE CT_PHIEU_MUON (
    MaPM VARCHAR(10),
    MaSach VARCHAR(10),
    TinhTrangLucMuon ENUM('Tốt', 'Cũ', 'Hư hỏng nhẹ') DEFAULT 'Tốt',
    GhiChu NVARCHAR(255),
    
    PRIMARY KEY (MaPM, MaSach),
    CONSTRAINT fk_ctpm_phieumuon FOREIGN KEY (MaPM) REFERENCES PHIEU_MUON(MaPM) ON DELETE CASCADE,
    CONSTRAINT fk_ctpm_sach FOREIGN KEY (MaSach) REFERENCES SACH(MaSach) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: PHIEU_TRA (Return Tickets)
-- =====================================================
CREATE TABLE PHIEU_TRA (
    MaPT VARCHAR(10) PRIMARY KEY,
    MaPM VARCHAR(10) NOT NULL,
    NgayTra DATE NOT NULL DEFAULT (CURDATE()),
    TienPhat DOUBLE DEFAULT 0 CHECK (TienPhat >= 0),
    LyDoPhat NVARCHAR(255),
    MaNV VARCHAR(10) NOT NULL,
    GhiChu NVARCHAR(255),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_pt_phieumuon FOREIGN KEY (MaPM) REFERENCES PHIEU_MUON(MaPM) ON DELETE CASCADE,
    CONSTRAINT fk_pt_nhanvien FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: CT_PHIEU_TRA (Return Details)
-- =====================================================
CREATE TABLE CT_PHIEU_TRA (
    MaPT VARCHAR(10),
    MaSach VARCHAR(10),
    TinhTrangLucTra ENUM('Tốt', 'Cũ', 'Hư hỏng nhẹ', 'Hư hỏng nặng', 'Mất') DEFAULT 'Tốt',
    TienPhatSach DOUBLE DEFAULT 0 CHECK (TienPhatSach >= 0),
    GhiChu NVARCHAR(255),
    
    PRIMARY KEY (MaPT, MaSach),
    CONSTRAINT fk_ctpt_phieutra FOREIGN KEY (MaPT) REFERENCES PHIEU_TRA(MaPT) ON DELETE CASCADE,
    CONSTRAINT fk_ctpt_sach FOREIGN KEY (MaSach) REFERENCES SACH(MaSach) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLE: PHIEU_PHAT (Fine Tickets)
-- Phiếu phạt riêng biệt để quản lý các khoản phạt
-- =====================================================
CREATE TABLE PHIEU_PHAT (
    MaPP VARCHAR(10) PRIMARY KEY,
    MaDG VARCHAR(10) NOT NULL,
    MaNV VARCHAR(10) NOT NULL,
    MaPM VARCHAR(10),
    NgayLap DATE NOT NULL DEFAULT (CURDATE()),
    TienPhat_TreHan DOUBLE DEFAULT 0 CHECK (TienPhat_TreHan >= 0),
    TienPhat_HuHong DOUBLE DEFAULT 0 CHECK (TienPhat_HuHong >= 0),
    TongTien DOUBLE NOT NULL CHECK (TongTien >= 0),
    GhiChu NVARCHAR(500),
    TrangThaiThanhToan ENUM('Chưa thanh toán', 'Đã thanh toán') DEFAULT 'Chưa thanh toán',
    NgayThanhToan DATE,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_pp_docgia FOREIGN KEY (MaDG) REFERENCES DOC_GIA(MaDG) ON DELETE CASCADE,
    CONSTRAINT fk_pp_nhanvien FOREIGN KEY (MaNV) REFERENCES NHAN_VIEN(MaNV) ON DELETE RESTRICT,
    CONSTRAINT fk_pp_phieumuon FOREIGN KEY (MaPM) REFERENCES PHIEU_MUON(MaPM) ON DELETE SET NULL,
    INDEX idx_madg (MaDG),
    INDEX idx_trangthai (TrangThaiThanhToan)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TRIGGERS
-- =====================================================

-- Trigger: Tự động trừ số lượng sách khi thêm vào CT_PHIEU_MUON
DELIMITER //
CREATE TRIGGER trg_after_insert_ctpm
AFTER INSERT ON CT_PHIEU_MUON
FOR EACH ROW
BEGIN
    UPDATE SACH 
    SET SoLuong = SoLuong - 1,
        UpdatedAt = CURRENT_TIMESTAMP
    WHERE MaSach = NEW.MaSach AND SoLuong > 0;
    
    -- Kiểm tra nếu số lượng = 0 thì cập nhật trạng thái
    UPDATE SACH 
    SET TrangThai = 'Inactive'
    WHERE MaSach = NEW.MaSach AND SoLuong = 0;
END//
DELIMITER ;

-- Trigger: Tự động cộng số lượng sách khi thêm vào CT_PHIEU_TRA
DELIMITER //
CREATE TRIGGER trg_after_insert_ctpt
AFTER INSERT ON CT_PHIEU_TRA
FOR EACH ROW
BEGIN
    -- Chỉ cộng lại nếu sách không bị mất hoặc hư hỏng nặng
    IF NEW.TinhTrangLucTra NOT IN ('Mất', 'Hư hỏng nặng') THEN
        UPDATE SACH 
        SET SoLuong = SoLuong + 1,
            TrangThai = 'Active',
            UpdatedAt = CURRENT_TIMESTAMP
        WHERE MaSach = NEW.MaSach;
    END IF;
END//
DELIMITER ;

-- Trigger: Tự động cập nhật trạng thái phiếu mượn khi tạo phiếu trả
DELIMITER //
CREATE TRIGGER trg_after_insert_phieutra
AFTER INSERT ON PHIEU_TRA
FOR EACH ROW
BEGIN
    UPDATE PHIEU_MUON 
    SET TrangThai = 'Đã trả'
    WHERE MaPM = NEW.MaPM;
END//
DELIMITER ;

-- Trigger: Kiểm tra số lượng sách trước khi cho mượn
DELIMITER //
CREATE TRIGGER trg_before_insert_ctpm
BEFORE INSERT ON CT_PHIEU_MUON
FOR EACH ROW
BEGIN
    DECLARE v_soluong INT;
    
    SELECT SoLuong INTO v_soluong 
    FROM SACH 
    WHERE MaSach = NEW.MaSach;
    
    IF v_soluong <= 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Sách đã hết, không thể mượn!';
    END IF;
END//
DELIMITER ;

-- =====================================================
-- STORED PROCEDURES
-- =====================================================

-- Procedure: Tính tiền phạt trả muộn (5000 VND/ngày)
DELIMITER //
CREATE PROCEDURE sp_TinhTienPhat(
    IN p_MaPM VARCHAR(10),
    IN p_NgayTraThucTe DATE,
    OUT p_TienPhat DOUBLE
)
BEGIN
    DECLARE v_HanTra DATE;
    DECLARE v_SoNgayTre INT;
    
    SELECT HanTra INTO v_HanTra
    FROM PHIEU_MUON
    WHERE MaPM = p_MaPM;
    
    SET v_SoNgayTre = DATEDIFF(p_NgayTraThucTe, v_HanTra);
    
    IF v_SoNgayTre > 0 THEN
        SET p_TienPhat = v_SoNgayTre * 5000;
    ELSE
        SET p_TienPhat = 0;
    END IF;
END//
DELIMITER ;

-- Procedure: Lấy danh sách sách có thể mượn
DELIMITER //
CREATE PROCEDURE sp_GetAvailableBooks()
BEGIN
    SELECT s.MaSach, s.TenSach, tg.TenTG, tl.TenTheLoai, 
           nxb.TenNXB, s.NamXB, s.SoLuong, s.DonGia, s.ViTri
    FROM SACH s
    LEFT JOIN TAC_GIA tg ON s.MaTG = tg.MaTG
    LEFT JOIN THE_LOAI tl ON s.MaTheLoai = tl.MaTheLoai
    LEFT JOIN NHA_XUAT_BAN nxb ON s.MaNXB = nxb.MaNXB
    WHERE s.SoLuong > 0 AND s.TrangThai = 'Active'
    ORDER BY s.TenSach;
END//
DELIMITER ;

-- Procedure: Lấy lịch sử mượn của độc giả
DELIMITER //
CREATE PROCEDURE sp_GetBorrowHistory(IN p_MaDG VARCHAR(10))
BEGIN
    SELECT pm.MaPM, pm.NgayMuon, pm.HanTra, pm.TrangThai,
           s.MaSach, s.TenSach, ctpm.TinhTrangLucMuon,
           nv.HoTen AS NhanVienLap,
           pt.NgayTra, pt.TienPhat
    FROM PHIEU_MUON pm
    INNER JOIN CT_PHIEU_MUON ctpm ON pm.MaPM = ctpm.MaPM
    INNER JOIN SACH s ON ctpm.MaSach = s.MaSach
    INNER JOIN NHAN_VIEN nv ON pm.MaNV = nv.MaNV
    LEFT JOIN PHIEU_TRA pt ON pm.MaPM = pt.MaPM
    WHERE pm.MaDG = p_MaDG
    ORDER BY pm.NgayMuon DESC;
END//
DELIMITER ;

-- =====================================================
-- SAMPLE DATA (Optional - for testing)
-- =====================================================

-- Insert sample authors
INSERT INTO TAC_GIA (MaTG, TenTG, GhiChu) VALUES
('TG001', N'Nguyễn Nhật Ánh', N'Nhà văn nổi tiếng'),
('TG002', N'Tô Hoài', N'Nhà văn thiếu nhi'),
('TG003', N'Nam Cao', N'Nhà văn hiện thực'),
('TG004', N'Ngô Tất Tố', N'Nhà văn cách mạng'),
('TG005', N'Victor Hugo', N'Nhà văn Pháp');

-- Insert sample categories
INSERT INTO THE_LOAI (MaTheLoai, TenTheLoai, MoTa) VALUES
('TL001', N'Văn học', N'Sách văn học trong và ngoài nước'),
('TL002', N'Khoa học', N'Sách khoa học tự nhiên'),
('TL003', N'Thiếu nhi', N'Sách dành cho thiếu nhi'),
('TL004', N'Giáo khoa', N'Sách giáo khoa các cấp'),
('TL005', N'Kỹ năng sống', N'Sách kỹ năng mềm');

-- Insert sample publishers
INSERT INTO NHA_XUAT_BAN (MaNXB, TenNXB, DiaChi, SoDT) VALUES
('NXB001', N'NXB Trẻ', N'161B Lý Chính Thắng, Q.3, TP.HCM', '0283932109'),
('NXB002', N'NXB Kim Đồng', N'55 Quang Trung, Hà Nội', '0243942265'),
('NXB003', N'NXB Văn học', N'18 Nguyễn Trường Tộ, Hà Nội', '0243823823'),
('NXB004', N'NXB Giáo dục', N'81 Trần Hưng Đạo, Hà Nội', '0243822325'),
('NXB005', N'NXB Lao động', N'175 Giảng Võ, Hà Nội', '0243851586');

-- Insert sample books
INSERT INTO SACH (MaSach, TenSach, MaTG, MaTheLoai, MaNXB, NamXB, SoLuong, DonGia, ViTri) VALUES
('S001', N'Cho tôi xin một vé đi tuổi thơ', 'TG001', 'TL001', 'NXB001', 2017, 15, 80000, 'Kệ A1'),
('S002', N'Dế Mèn phiêu lưu ký', 'TG002', 'TL003', 'NXB002', 2015, 20, 65000, 'Kệ B2'),
('S003', N'Chí Phèo', 'TG003', 'TL001', 'NXB003', 2018, 10, 45000, 'Kệ A3'),
('S004', N'Tắt đèn', 'TG004', 'TL001', 'NXB003', 2016, 12, 55000, 'Kệ A4'),
('S005', N'Những người khốn khổ', 'TG005', 'TL001', 'NXB003', 2019, 8, 150000, 'Kệ C1');

-- Insert sample readers
INSERT INTO DOC_GIA (MaDG, HoTen, NgaySinh, GioiTinh, DiaChi, SoDT, Email, NgayLapThe, NgayHetHan) VALUES
('DG001', N'Trần Văn A', '2000-05-15', 'Nam', N'123 Lê Lợi, Q.1, TP.HCM', '0901234567', 'vana@gmail.com', '2024-01-01', '2025-01-01'),
('DG002', N'Nguyễn Thị B', '1998-08-20', N'Nữ', N'456 Nguyễn Huệ, Q.1, TP.HCM', '0912345678', 'thib@gmail.com', '2024-02-01', '2025-02-01'),
('DG003', N'Lê Văn C', '2001-03-10', 'Nam', N'789 Trần Hưng Đạo, Q.5, TP.HCM', '0923456789', 'vanc@gmail.com', '2024-03-01', '2025-03-01');

-- Insert sample staff (Password: 123456 - should be hashed in production)
INSERT INTO NHAN_VIEN (MaNV, HoTen, NgaySinh, GioiTinh, SoDT, Email, ChucVu, Username, Password, Role) VALUES
('NV001', N'Admin Nguyễn', '1990-01-01', 'Nam', '0987654321', 'admin@library.com', 'Quản trị viên', 'admin', '123456', 'Admin'),
('NV002', N'Thủ thư Trần', '1995-05-15', N'Nữ', '0976543210', 'thuthu@library.com', N'Thủ thư', 'thuthu01', '123456', 'Librarian');

-- Insert sample borrow tickets
INSERT INTO PHIEU_MUON (MaPM, MaDG, MaNV, NgayMuon, HanTra, TrangThai) VALUES
('PM001', 'DG001', 'NV002', '2025-01-01', '2025-01-15', N'Đang mượn'),
('PM002', 'DG002', 'NV002', '2025-01-05', '2025-01-19', N'Đang mượn');

-- Insert sample borrow details
INSERT INTO CT_PHIEU_MUON (MaPM, MaSach, TinhTrangLucMuon) VALUES
('PM001', 'S001', N'Tốt'),
('PM001', 'S002', N'Tốt'),
('PM002', 'S003', N'Tốt');

-- =====================================================
-- VIEWS (Optional - for reporting)
-- =====================================================

-- View: Thống kê sách đang mượn
CREATE VIEW v_SachDangMuon AS
SELECT s.MaSach, s.TenSach, COUNT(ctpm.MaSach) AS SoLuotMuon
FROM SACH s
INNER JOIN CT_PHIEU_MUON ctpm ON s.MaSach = ctpm.MaSach
INNER JOIN PHIEU_MUON pm ON ctpm.MaPM = pm.MaPM
WHERE pm.TrangThai = N'Đang mượn'
GROUP BY s.MaSach, s.TenSach;

-- View: Phiếu mượn quá hạn
CREATE VIEW v_PhieuMuonQuaHan AS
SELECT pm.MaPM, dg.MaDG, dg.HoTen, dg.SoDT,
       pm.NgayMuon, pm.HanTra, 
       DATEDIFF(CURDATE(), pm.HanTra) AS SoNgayTre
FROM PHIEU_MUON pm
INNER JOIN DOC_GIA dg ON pm.MaDG = dg.MaDG
WHERE pm.TrangThai = N'Đang mượn' 
  AND pm.HanTra < CURDATE();

-- =====================================================
-- INDEXES (For better performance)
-- =====================================================
CREATE INDEX idx_sach_tensach ON SACH(TenSach);
CREATE INDEX idx_docgia_hoten ON DOC_GIA(HoTen);
CREATE INDEX idx_phieumuon_ngaymuon ON PHIEU_MUON(NgayMuon);
CREATE INDEX idx_phieumuon_trangthai ON PHIEU_MUON(TrangThai);
CREATE INDEX idx_phieutra_ngaytra ON PHIEU_TRA(NgayTra);

-- =====================================================
-- END OF SCRIPT
-- =====================================================
