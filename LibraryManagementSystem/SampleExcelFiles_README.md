# 📊 Tạo File Excel Mẫu

## Cách 1: Sử dụng chức năng Export (Khuyến nghị ⭐)

### Bước 1: Export dữ liệu hiện tại
1. Chạy ứng dụng
2. Đăng nhập
3. Vào tab **"📚 Quản Lý Sách"** hoặc **"👥 Quản Lý Độc Giả"**
4. Click nút **📤 Xuất Excel**
5. Lưu file với tên bạn muốn

### Bước 2: Sử dụng file export làm template
1. Mở file Excel vừa export
2. Xóa dữ liệu cũ (giữ lại header - dòng tiêu đề)
3. Thêm dữ liệu mới theo format
4. Lưu lại
5. Import vào hệ thống

## Cách 2: Tạo file Excel thủ công

### File Mẫu: Danh Sách Sách (Books)

Tạo file Excel với tên `DanhSachSach_Mau.xlsx` với cấu trúc:

**Dòng 1 (Header):**
```
Mã Sách | Tên Sách | Mã Tác Giả | Mã Thể Loại | Mã NXB | Năm XB | Số Lượng | Đơn Giá | Vị Trí | Trạng Thái
```

**Dòng 2+ (Dữ liệu mẫu):**
```
S001 | Lập Trình Java Cơ Bản | TG001 | TL001 | NXB001 | 2023 | 10 | 150000 | K1-Ke1-A1 | Còn
S002 | Cơ Sở Dữ Liệu MySQL | TG002 | TL001 | NXB002 | 2022 | 5 | 200000 | K1-Ke1-A2 | Còn
S003 | Thuật Toán Lập Trình | TG003 | TL001 | NXB001 | 2024 | 8 | 180000 | K1-Ke2-A1 | Còn
```

### File Mẫu: Danh Sách Độc Giả (Readers)

Tạo file Excel với tên `DanhSachDocGia_Mau.xlsx` với cấu trúc:

**Dòng 1 (Header):**
```
Mã ĐG | Họ Tên | Ngày Sinh | Giới Tính | SĐT | Email | Địa Chỉ | Ngày Lập Thẻ | Ngày Hết Hạn | Trạng Thái
```

**Dòng 2+ (Dữ liệu mẫu):**
```
DG001 | Nguyễn Văn An | 15/03/1995 | Nam | 0901234567 | nva@email.com | 123 Đường ABC, Quận 1, TP.HCM | 01/01/2024 | 01/01/2025 | Hoạt động
DG002 | Trần Thị Bình | 20/07/1998 | Nữ | 0987654321 | ttb@email.com | 456 Đường XYZ, Quận 2, Hà Nội | 15/02/2024 | 15/02/2025 | Hoạt động
DG003 | Lê Văn Cường | 10/12/1992 | Nam | 0912345678 | lvc@email.com | 789 Đường KLM, Quận 3, Đà Nẵng | 20/03/2024 | 20/03/2025 | Hoạt động
```

## 📋 Chi Tiết Các Trường

### Sách (Books)

| Cột | Kiểu Dữ Liệu | Bắt Buộc | Ghi Chú |
|-----|--------------|----------|---------|
| Mã Sách | Text | ✅ | Unique, ví dụ: S001, BOOK001 |
| Tên Sách | Text | ✅ | Tên đầy đủ của sách |
| Mã Tác Giả | Text | ✅ | Phải tồn tại trong bảng Author |
| Mã Thể Loại | Text | ✅ | Phải tồn tại trong bảng Category |
| Mã NXB | Text | ✅ | Phải tồn tại trong bảng Publisher |
| Năm XB | Số | ✅ | Năm xuất bản, ví dụ: 2023 |
| Số Lượng | Số | ✅ | Số lượng sách có sẵn |
| Đơn Giá | Số | ✅ | Giá sách (VNĐ) |
| Vị Trí | Text | ❌ | Vị trí sách trong thư viện |
| Trạng Thái | Text | ❌ | "Còn" hoặc "Hết" |

### Độc Giả (Readers)

| Cột | Kiểu Dữ Liệu | Bắt Buộc | Ghi Chú |
|-----|--------------|----------|---------|
| Mã ĐG | Text | ✅ | Unique, ví dụ: DG001, READER001 |
| Họ Tên | Text | ✅ | Họ và tên đầy đủ |
| Ngày Sinh | Date | ✅ | Format: dd/MM/yyyy |
| Giới Tính | Text | ✅ | "Nam" hoặc "Nữ" |
| SĐT | Text | ✅ | Số điện thoại, 10-11 số |
| Email | Text | ❌ | Địa chỉ email hợp lệ |
| Địa Chỉ | Text | ❌ | Địa chỉ đầy đủ |
| Ngày Lập Thẻ | Date | ✅ | Format: dd/MM/yyyy |
| Ngày Hết Hạn | Date | ✅ | Format: dd/MM/yyyy |
| Trạng Thái | Text | ✅ | "Hoạt động" hoặc "Khóa" |

## ⚠️ Lưu Ý Quan Trọng

### 1. Định dạng ngày tháng
- ✅ Đúng: `15/03/2024`
- ❌ Sai: `15-03-2024`, `2024/03/15`, `15/3/2024`

### 2. Mã tham chiếu
Trước khi import sách, đảm bảo đã có:
- Tác giả (Author) với mã tương ứng
- Thể loại (Category) với mã tương ứng
- Nhà xuất bản (Publisher) với mã tương ứng

**Cách kiểm tra:**
1. Mở MySQL Workbench hoặc phpMyAdmin
2. Chạy query:
```sql
SELECT * FROM author;
SELECT * FROM category;
SELECT * FROM publisher;
```
3. Lấy các mã ID để điền vào file Excel

### 3. Giá trị hợp lệ

**Giới Tính:**
- "Nam" (chữ N viết hoa)
- "Nữ" (chữ N viết hoa)

**Trạng Thái Độc Giả:**
- "Hoạt động" (chữ H viết hoa)
- "Khóa" (chữ K viết hoa)

**Trạng Thái Sách:**
- "Còn"
- "Hết"

### 4. Encoding
- Lưu file Excel với encoding UTF-8 để hiển thị tiếng Việt đúng

## 🎯 Quy Trình Import Đề Xuất

1. **Chuẩn bị dữ liệu cơ bản:**
   - Import Tác giả (Author)
   - Import Thể loại (Category)
   - Import Nhà xuất bản (Publisher)

2. **Import dữ liệu chính:**
   - Import Sách (Books)
   - Import Độc giả (Readers)

3. **Kiểm tra:**
   - Refresh danh sách trong ứng dụng
   - Kiểm tra số lượng bản ghi
   - Test tìm kiếm và lọc

## 💾 Template Download

Sau khi export lần đầu tiên, bạn sẽ có file template chính xác. Lưu file đó làm template và reuse cho các lần import sau.

## 🔍 Debug Tips

Nếu import bị lỗi:

1. **Mở file trong Excel/LibreOffice**
2. **Kiểm tra:**
   - Có đúng 10 cột cho Sách không?
   - Có đúng 10 cột cho Độc Giả không?
   - Header ở dòng 1?
   - Dữ liệu bắt đầu từ dòng 2?
3. **Kiểm tra console output** trong terminal để xem lỗi chi tiết
4. **Test với 1-2 dòng trước**, sau đó mở rộng

## 📞 Hỗ Trợ

Nếu vẫn gặp vấn đề:
1. Export dữ liệu mẫu từ hệ thống
2. So sánh với file bạn tạo
3. Copy format chính xác từ file export
4. Thử import lại
