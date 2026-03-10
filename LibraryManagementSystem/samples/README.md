# 📊 File Mẫu Excel

Thư mục này chứa các file mẫu để import vào hệ thống.

## 📁 Các File Có Sẵn

### 1. File CSV (Mở ngay được)
- **DanhSachSach_Mau.csv** - Danh sách 10 cuốn sách mẫu
- **DanhSachDocGia_Mau.csv** - Danh sách 10 độc giả mẫu

**Cách dùng file CSV:**
1. Mở file .csv bằng Excel
2. Chọn "File" > "Save As"
3. Chọn định dạng "Excel Workbook (.xlsx)"
4. Lưu lại
5. Import vào hệ thống

### 2. File Excel (.xlsx) - Tự động tạo

Để tạo file Excel mẫu:

**Cách 1: Chạy Java Generator**
```bash
cd LibraryManagementSystem
javac -cp ".:target/classes:lib/*" src/com/library/util/SampleExcelGenerator.java
java -cp ".:target/classes:lib/*" com.library.util.SampleExcelGenerator
```

**Cách 2: Từ Maven**
```bash
cd LibraryManagementSystem
mvn compile
java -cp "target/classes:$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q)" com.library.util.SampleExcelGenerator
```

**Cách 3: Dùng chức năng Export trong app**
1. Chạy ứng dụng
2. Đăng nhập
3. Vào tab Quản Lý Sách hoặc Quản Lý Độc Giả
4. Click nút "📤 Xuất Excel"
5. Lưu file
6. Dùng file đó làm template!

## 📋 Nội Dung File Mẫu

### DanhSachSach_Mau (10 sách)
- S001: Lập Trình Java Cơ Bản
- S002: Cơ Sở Dữ Liệu MySQL
- S003: Thuật Toán Lập Trình
- S004: Lập Trình Python Nâng Cao
- S005: Trí Tuệ Nhân Tạo
- S006: Machine Learning Cơ Bản
- S007: Lập Trình Web với React
- S008: Node.js Backend Development
- S009: Data Science với R
- S010: Blockchain và Cryptocurrency

### DanhSachDocGia_Mau (10 độc giả)
- DG001: Nguyễn Văn An
- DG002: Trần Thị Bình
- DG003: Lê Văn Cường
- DG004: Phạm Thị Dung
- DG005: Hoàng Văn Em
- DG006: Vũ Thị Phương
- DG007: Đặng Văn Giang
- DG008: Bùi Thị Hà
- DG009: Ngô Văn Ích
- DG010: Trương Thị Kim

## ⚠️ Lưu Ý Trước Khi Import

### Cho Sách:
Đảm bảo đã có sẵn trong database:
- Tác giả: TG001, TG002, TG003, ... TG009
- Thể loại: TL001, TL002
- Nhà xuất bản: NXB001, NXB002, NXB003

**Tạo dữ liệu tham chiếu:**
```sql
-- Tác giả
INSERT INTO author (ma_tac_gia, ten_tac_gia) VALUES 
('TG001', 'Nguyễn Văn A'),
('TG002', 'Trần Thị B'),
('TG003', 'Lê Văn C'),
('TG004', 'Phạm Thị D'),
('TG005', 'Hoàng Văn E'),
('TG006', 'Vũ Thị F'),
('TG007', 'Đặng Văn G'),
('TG008', 'Bùi Thị H'),
('TG009', 'Ngô Văn I');

-- Thể loại
INSERT INTO category (ma_the_loai, ten_the_loai) VALUES 
('TL001', 'Công Nghệ Thông Tin'),
('TL002', 'Khoa Học Dữ Liệu');

-- Nhà xuất bản
INSERT INTO publisher (ma_nxb, ten_nxb) VALUES 
('NXB001', 'NXB Thông Tin và Truyền Thông'),
('NXB002', 'NXB Khoa Học và Kỹ Thuật'),
('NXB003', 'NXB Đại Học Quốc Gia');
```

### Cho Độc Giả:
Không cần dữ liệu tham chiếu, có thể import trực tiếp!

## 🚀 Quy Trình Import Đề Xuất

1. **Tạo dữ liệu tham chiếu** (chỉ cần 1 lần):
   - Chạy SQL script tạo tác giả, thể loại, nhà xuất bản
   
2. **Import Độc Giả**:
   - Mở file: `DanhSachDocGia_Mau.csv`
   - Lưu thành .xlsx (nếu cần)
   - Import vào hệ thống
   
3. **Import Sách**:
   - Mở file: `DanhSachSach_Mau.csv`
   - Lưu thành .xlsx (nếu cần)
   - Import vào hệ thống

4. **Kiểm tra**:
   - Refresh danh sách
   - Test tìm kiếm
   - Thử mượn sách

## 💡 Tips

1. **Tạo file template riêng:**
   - Copy file mẫu
   - Xóa dữ liệu (giữ header)
   - Lưu làm template
   
2. **Sửa đổi dữ liệu:**
   - Mở file CSV/Excel
   - Thay đổi theo nhu cầu
   - Import lại
   
3. **Backup:**
   - Luôn export trước khi import
   - Giữ file backup để phòng lỗi

## 📞 Hỗ Trợ

Nếu file mẫu không hoạt động:
1. Kiểm tra encoding (UTF-8)
2. Đảm bảo có dữ liệu tham chiếu
3. Kiểm tra format ngày tháng
4. Thử export từ app để có file chuẩn
