-- =====================================================
-- SQL Script: Tạo dữ liệu tham chiếu cho import Excel
-- Chạy script này TRƯỚC KHI import file Excel mẫu
-- =====================================================

USE library_db;

-- =====================================================
-- 1. TÁC GIẢ (Authors)
-- =====================================================
INSERT INTO author (ma_tac_gia, ten_tac_gia, quoc_tich) VALUES 
('TG001', 'Nguyễn Văn Anh', 'Việt Nam'),
('TG002', 'Trần Thị Bình', 'Việt Nam'),
('TG003', 'Lê Văn Cường', 'Việt Nam'),
('TG004', 'Phạm Thị Dung', 'Việt Nam'),
('TG005', 'Hoàng Văn Em', 'Việt Nam'),
('TG006', 'Vũ Thị Phương', 'Việt Nam'),
('TG007', 'Đặng Văn Giang', 'Việt Nam'),
('TG008', 'Bùi Thị Hà', 'Việt Nam'),
('TG009', 'Ngô Văn Ích', 'Việt Nam'),
('TG010', 'Trương Thị Kim', 'Việt Nam')
ON DUPLICATE KEY UPDATE ten_tac_gia = VALUES(ten_tac_gia);

-- =====================================================
-- 2. THỂ LOẠI (Categories)
-- =====================================================
INSERT INTO category (ma_the_loai, ten_the_loai, mo_ta) VALUES 
('TL001', 'Công Nghệ Thông Tin', 'Sách về lập trình, CNTT'),
('TL002', 'Khoa Học Dữ Liệu', 'Sách về AI, ML, Data Science'),
('TL003', 'Mạng Máy Tính', 'Sách về network, bảo mật'),
('TL004', 'Cơ Sở Dữ Liệu', 'Sách về database, SQL'),
('TL005', 'Phát Triển Web', 'Sách về web development')
ON DUPLICATE KEY UPDATE ten_the_loai = VALUES(ten_the_loai);

-- =====================================================
-- 3. NHÀ XUẤT BẢN (Publishers)
-- =====================================================
INSERT INTO publisher (ma_nxb, ten_nxb, dia_chi, sdt, email) VALUES 
('NXB001', 'NXB Thông Tin và Truyền Thông', '18 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', '0243826..78', 'contact@mic.gov.vn'),
('NXB002', 'NXB Khoa Học và Kỹ Thuật', '70 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội', '0243942..89', 'info@nxbkhkt.vn'),
('NXB003', 'NXB Đại Học Quốc Gia', 'Đại học Quốc gia Hà Nội, 144 Xuân Thủy, Cầu Giấy', '0243754..23', 'nxb@vnu.edu.vn'),
('NXB004', 'NXB Lao Động', '175 Giảng Võ, Đống Đa, Hà Nội', '0243851..45', 'nxblaodong@gmail.com'),
('NXB005', 'NXB Trẻ', '161B Lý Chính Thắng, Q3, TP.HCM', '0283931..00', 'nxbtre@nxbtre.com.vn')
ON DUPLICATE KEY UPDATE ten_nxb = VALUES(ten_nxb);

-- =====================================================
-- Kiểm tra dữ liệu đã thêm
-- =====================================================
SELECT '=== TÁC GIẢ ===' AS '';
SELECT * FROM author WHERE ma_tac_gia LIKE 'TG%';

SELECT '=== THỂ LOẠI ===' AS '';
SELECT * FROM category WHERE ma_the_loai LIKE 'TL%';

SELECT '=== NHÀ XUẤT BẢN ===' AS '';
SELECT * FROM publisher WHERE ma_nxb LIKE 'NXB%';

-- =====================================================
-- Thông báo hoàn thành
-- =====================================================
SELECT '✅ Đã tạo xong dữ liệu tham chiếu!' AS 'TRẠNG THÁI';
SELECT 'Bây giờ bạn có thể import file Excel mẫu!' AS 'KẾ TIẾP';
