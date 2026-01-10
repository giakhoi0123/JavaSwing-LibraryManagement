-- =====================================================
-- INSERT TEST DATA (10 examples for each table)
-- =====================================================

USE library_management;

-- =====================================================
-- 1. TÁC GIẢ (10 authors)
-- =====================================================
INSERT INTO TAC_GIA (MaTG, TenTG, GhiChu) VALUES
('TG001', 'Nguyễn Nhật Ánh', 'Nhà văn nổi tiếng Việt Nam'),
('TG002', 'Tô Hoài', 'Tác giả Dế Mèn Phiêu Lưu Ký'),
('TG003', 'Nam Cao', 'Nhà văn hiện thực'),
('TG004', 'Ngô Tất Tố', 'Tác giả Tắt Đèn'),
('TG005', 'Vũ Trọng Phụng', 'Nhà văn phê phán'),
('TG006', 'Hồ Chí Minh', 'Chủ tịch Hồ Chí Minh'),
('TG007', 'Xuân Diệu', 'Thi sĩ lãng mạn'),
('TG008', 'Huy Cận', 'Thi sĩ hiện đại'),
('TG009', 'Tố Hữu', 'Nhà thơ cách mạng'),
('TG010', 'Nguyễn Du', 'Đại thi hào dân tộc');

-- =====================================================
-- 2. THỂ LOẠI (10 categories)
-- =====================================================
INSERT INTO THE_LOAI (MaTheLoai, TenTheLoai, MoTa) VALUES
('TL001', 'Văn học Việt Nam', 'Các tác phẩm văn học trong nước'),
('TL002', 'Văn học nước ngoài', 'Các tác phẩm dịch từ nước ngoài'),
('TL003', 'Khoa học - Công nghệ', 'Sách về khoa học và công nghệ'),
('TL004', 'Lịch sử - Chính trị', 'Sách về lịch sử và chính trị'),
('TL005', 'Kinh tế - Quản lý', 'Sách về kinh tế và quản trị'),
('TL006', 'Tâm lý - Kỹ năng sống', 'Sách phát triển bản thân'),
('TL007', 'Thiếu nhi', 'Sách dành cho trẻ em'),
('TL008', 'Giáo khoa - Tham khảo', 'Sách giáo khoa và tài liệu học tập'),
('TL009', 'Nghệ thuật - Giải trí', 'Sách về nghệ thuật và giải trí'),
('TL010', 'Y học - Sức khỏe', 'Sách về y học và chăm sóc sức khỏe');

-- =====================================================
-- 3. NHÀ XUẤT BẢN (10 publishers)
-- =====================================================
INSERT INTO NHA_XUAT_BAN (MaNXB, TenNXB, DiaChi, SoDT) VALUES
('NXB001', 'NXB Trẻ', '161B Lý Chính Thắng, Quận 3, TP.HCM', '0283932135'),
('NXB002', 'NXB Kim Đồng', '55 Quang Trung, Hà Nội', '0438222540'),
('NXB003', 'NXB Văn học', '18 Nguyễn Trường Tộ, Hà Nội', '0438223340'),
('NXB004', 'NXB Giáo dục', '81 Trần Hưng Đạo, Hà Nội', '0438220801'),
('NXB005', 'NXB Lao Động', '175 Giảng Võ, Hà Nội', '0438514791'),
('NXB006', 'NXB Thanh Niên', '64 Bà Triệu, Hà Nội', '0439436380'),
('NXB007', 'NXB Phụ Nữ', '39 Hàng Chuối, Hà Nội', '0239710717'),
('NXB008', 'NXB Tổng hợp TP.HCM', '62 Nguyễn Thị Minh Khai, Quận 1, TP.HCM', '0283822153'),
('NXB009', 'NXB Chính trị Quốc gia', '6 Bà Triệu, Hà Nội', '0438229496'),
('NXB010', 'NXB Đại học Quốc gia', '16 Hàng Chuối, Hà Nội', '0239719207');

-- =====================================================
-- 4. SÁCH (10 books)
-- =====================================================
INSERT INTO SACH (MaSach, TenSach, MaTG, MaTheLoai, MaNXB, NamXB, SoLuong, DonGia, ViTri, TrangThai) VALUES
('S001', 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'TG001', 'TL001', 'NXB001', 2010, 10, 85000, 'Kệ A1', 'Active'),
('S002', 'Dế Mèn Phiêu Lưu Ký', 'TG002', 'TL007', 'NXB002', 2015, 8, 65000, 'Kệ B2', 'Active'),
('S003', 'Chí Phèo', 'TG003', 'TL001', 'NXB003', 2012, 5, 45000, 'Kệ A2', 'Active'),
('S004', 'Tắt Đèn', 'TG004', 'TL001', 'NXB003', 2011, 7, 55000, 'Kệ A3', 'Active'),
('S005', 'Số Đỏ', 'TG005', 'TL001', 'NXB003', 2013, 6, 75000, 'Kệ A4', 'Active'),
('S006', 'Nhật Ký Trong Tù', 'TG006', 'TL004', 'NXB009', 2019, 12, 95000, 'Kệ C1', 'Active'),
('S007', 'Thơ Xuân Diệu', 'TG007', 'TL001', 'NXB003', 2014, 4, 68000, 'Kệ D1', 'Active'),
('S008', 'Vội Vàng', 'TG008', 'TL001', 'NXB006', 2016, 9, 52000, 'Kệ D2', 'Active'),
('S009', 'Việt Bắc', 'TG009', 'TL001', 'NXB003', 2017, 5, 48000, 'Kệ D3', 'Active'),
('S010', 'Truyện Kiều', 'TG010', 'TL001', 'NXB003', 2018, 15, 120000, 'Kệ E1', 'Active');

-- =====================================================
-- 5. ĐỘC GIẢ (10 readers)
-- =====================================================
INSERT INTO DOC_GIA (MaDG, HoTen, NgaySinh, GioiTinh, DiaChi, SoDT, Email, NgayLapThe, NgayHetHan, TrangThai) VALUES
('DG001', 'Trần Văn An', '2000-05-15', 'Nam', '123 Lê Lợi, Q1, TP.HCM', '0901234567', 'vanan@email.com', '2024-01-01', '2025-01-01', 'Active'),
('DG002', 'Nguyễn Thị Bình', '1998-08-20', 'Nữ', '456 Nguyễn Huệ, Q1, TP.HCM', '0902345678', 'thibinh@email.com', '2024-02-01', '2025-02-01', 'Active'),
('DG003', 'Lê Hoàng Cường', '2001-03-10', 'Nam', '789 Trần Hưng Đạo, Q5, TP.HCM', '0903456789', 'hoangcuong@email.com', '2024-03-01', '2025-03-01', 'Active'),
('DG004', 'Phạm Thị Dung', '1999-12-25', 'Nữ', '321 Võ Văn Tần, Q3, TP.HCM', '0904567890', 'thidung@email.com', '2024-04-01', '2025-04-01', 'Active'),
('DG005', 'Hoàng Văn Em', '2002-07-18', 'Nam', '654 Hai Bà Trưng, Q1, TP.HCM', '0905678901', 'vanem@email.com', '2024-05-01', '2025-05-01', 'Active'),
('DG006', 'Vũ Thị Phương', '2000-11-30', 'Nữ', '987 Lý Tự Trọng, Q1, TP.HCM', '0906789012', 'thiphuong@email.com', '2024-06-01', '2025-06-01', 'Active'),
('DG007', 'Đỗ Văn Giang', '1997-04-22', 'Nam', '159 Pasteur, Q3, TP.HCM', '0907890123', 'vangiang@email.com', '2024-07-01', '2025-07-01', 'Active'),
('DG008', 'Bùi Thị Hoa', '2001-09-14', 'Nữ', '753 Điện Biên Phủ, Q3, TP.HCM', '0908901234', 'thihoa@email.com', '2024-08-01', '2025-08-01', 'Active'),
('DG009', 'Ngô Văn Ích', '2003-01-05', 'Nam', '258 Cách Mạng Tháng 8, Q10, TP.HCM', '0909012345', 'vanich@email.com', '2024-09-01', '2025-09-01', 'Active'),
('DG010', 'Trương Thị Kiều', '1996-06-28', 'Nữ', '147 Nguyễn Đình Chiểu, Q3, TP.HCM', '0900123456', 'thikieu@email.com', '2024-10-01', '2025-10-01', 'Active');

-- =====================================================
-- 6. PHIẾU MƯỢN (10 borrow tickets)
-- =====================================================
INSERT INTO PHIEU_MUON (MaPM, MaDG, MaNV, NgayMuon, HanTra, TrangThai, GhiChu) VALUES
('PM001', 'DG001', 'NV001', '2024-11-01', '2024-11-15', 'Đã trả', 'Đã trả đúng hạn'),
('PM002', 'DG002', 'NV001', '2024-11-05', '2024-11-19', 'Đã trả', 'Trả trễ 2 ngày'),
('PM003', 'DG003', 'NV002', '2024-11-10', '2024-11-24', 'Đang mượn', NULL),
('PM004', 'DG004', 'NV001', '2024-11-15', '2024-11-29', 'Đang mượn', NULL),
('PM005', 'DG005', 'NV002', '2024-12-01', '2024-12-15', 'Đang mượn', NULL),
('PM006', 'DG006', 'NV001', '2024-12-05', '2024-12-19', 'Đã trả', 'Đã trả đúng hạn'),
('PM007', 'DG007', 'NV002', '2024-12-10', '2024-12-24', 'Đang mượn', NULL),
('PM008', 'DG008', 'NV001', '2024-12-15', '2024-12-29', 'Quá hạn', 'Đã quá hạn 12 ngày'),
('PM009', 'DG009', 'NV002', '2024-12-20', '2025-01-03', 'Quá hạn', 'Đã quá hạn 7 ngày'),
('PM010', 'DG010', 'NV001', '2025-01-05', '2025-01-19', 'Đang mượn', NULL);

-- =====================================================
-- 7. CHI TIẾT PHIẾU MƯỢN (borrow details)
-- =====================================================
INSERT INTO CT_PHIEU_MUON (MaPM, MaSach, TinhTrangLucMuon, GhiChu) VALUES
('PM001', 'S001', 'Tốt', 'Sách mới'),
('PM001', 'S002', 'Tốt', 'Sách mới'),
('PM002', 'S003', 'Cũ', 'Sách đã cũ nhưng còn tốt'),
('PM003', 'S004', 'Tốt', NULL),
('PM003', 'S005', 'Tốt', NULL),
('PM004', 'S006', 'Tốt', NULL),
('PM005', 'S007', 'Cũ', 'Bìa hơi nhăn'),
('PM006', 'S008', 'Tốt', NULL),
('PM007', 'S009', 'Tốt', NULL),
('PM008', 'S010', 'Tốt', NULL),
('PM009', 'S001', 'Tốt', NULL),
('PM010', 'S002', 'Tốt', NULL);

-- =====================================================
-- 8. PHIẾU TRẢ (5 return tickets for returned books)
-- =====================================================
INSERT INTO PHIEU_TRA (MaPT, MaPM, NgayTra, TienPhat, LyDoPhat, MaNV, GhiChu) VALUES
('PT001', 'PM001', '2024-11-14', 0, NULL, 'NV001', 'Trả đúng hạn'),
('PT002', 'PM002', '2024-11-21', 10000, 'Trả trễ 2 ngày', 'NV001', 'Phạt 5000/ngày'),
('PT003', 'PM006', '2024-12-18', 0, NULL, 'NV002', 'Trả đúng hạn');

-- =====================================================
-- 9. CHI TIẾT PHIẾU TRẢ (return details)
-- =====================================================
INSERT INTO CT_PHIEU_TRA (MaPT, MaSach, TinhTrangLucTra, TienPhatSach, GhiChu) VALUES
('PT001', 'S001', 'Tốt', 0, 'Trả đúng tình trạng'),
('PT001', 'S002', 'Tốt', 0, 'Trả đúng tình trạng'),
('PT002', 'S003', 'Hư hỏng nhẹ', 15000, 'Bìa bị rách nhẹ'),
('PT003', 'S008', 'Tốt', 0, 'Trả đúng tình trạng');

-- =====================================================
-- 10. PHIẾU PHẠT (3 fine tickets)
-- =====================================================
INSERT INTO PHIEU_PHAT (MaPP, MaDG, MaNV, MaPM, NgayLap, TienPhat_TreHan, TienPhat_HuHong, TongTien, GhiChu, TrangThaiThanhToan, NgayThanhToan) VALUES
('PP001', 'DG002', 'NV001', 'PM002', '2024-11-21', 10000, 15000, 25000, 'Trả trễ 2 ngày (10000) + Sách hư hỏng nhẹ (15000)', 'Đã thanh toán', '2024-11-21'),
('PP002', 'DG008', 'NV001', 'PM008', '2025-01-10', 60000, 0, 60000, 'Trả trễ 12 ngày, chưa trả sách', 'Chưa thanh toán', NULL),
('PP003', 'DG009', 'NV002', 'PM009', '2025-01-10', 35000, 0, 35000, 'Trả trễ 7 ngày, chưa trả sách', 'Chưa thanh toán', NULL);
