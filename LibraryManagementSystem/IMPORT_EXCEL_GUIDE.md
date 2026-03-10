# 📥 Hướng Dẫn Import/Export Excel

## 🎯 Tổng Quan
Hệ thống đã được tích hợp chức năng Import và Export dữ liệu Excel cho:
- **Quản lý Sách** (BookManagementPanel)
- **Quản lý Độc Giả** (ReaderManagementPanel)

## 📍 Vị Trí Các Nút

### 1. Quản Lý Sách
Vào tab **"📚 Quản Lý Sách"**, bạn sẽ thấy 2 nút mới:
- **📥 Nhập Excel** - Import sách từ file Excel
- **📤 Xuất Excel** - Export danh sách sách ra file Excel

### 2. Quản Lý Độc Giả
Vào tab **"👥 Quản Lý Độc Giả"**, bạn sẽ thấy 2 nút mới:
- **📥 Nhập Excel** - Import độc giả từ file Excel
- **📤 Xuất Excel** - Export danh sách độc giả ra file Excel

## 📄 Định Dạng File Excel

### Định Dạng File Sách (Books)
File Excel cần có các cột sau (theo thứ tự):

| Mã Sách | Tên Sách | Mã Tác Giả | Mã Thể Loại | Mã NXB | Năm XB | Số Lượng | Đơn Giá | Vị Trí | Trạng Thái |
|---------|----------|------------|-------------|--------|---------|----------|---------|--------|------------|
| S001 | Lập Trình Java | TG001 | TL001 | NXB001 | 2023 | 10 | 150000 | K1-A1 | Còn |
| S002 | Cơ Sở Dữ Liệu | TG002 | TL001 | NXB002 | 2022 | 5 | 200000 | K1-A2 | Còn |

**Lưu ý:**
- Dòng đầu tiên là tiêu đề cột (header)
- Dữ liệu bắt đầu từ dòng thứ 2
- Các mã (Mã Tác Giả, Mã Thể Loại, Mã NXB) phải tồn tại trong database

### Định Dạng File Độc Giả (Readers)
File Excel cần có các cột sau (theo thứ tự):

| Mã ĐG | Họ Tên | Ngày Sinh | Giới Tính | SĐT | Email | Địa Chỉ | Ngày Lập Thẻ | Ngày Hết Hạn | Trạng Thái |
|-------|---------|-----------|-----------|-----|-------|---------|--------------|--------------|------------|
| DG001 | Nguyễn Văn A | 01/01/1990 | Nam | 0901234567 | nva@email.com | Hà Nội | 01/01/2024 | 01/01/2025 | Hoạt động |
| DG002 | Trần Thị B | 15/05/1995 | Nữ | 0987654321 | ttb@email.com | TP.HCM | 15/03/2024 | 15/03/2025 | Hoạt động |

**Lưu ý:**
- Định dạng ngày: `dd/MM/yyyy` (ví dụ: 01/01/2024)
- Giới tính: "Nam" hoặc "Nữ"
- Trạng thái: "Hoạt động" hoặc "Khóa"

## 🚀 Cách Sử Dụng

### Import (Nhập) Dữ Liệu

1. **Chuẩn bị file Excel:**
   - Tạo file Excel (.xlsx hoặc .xls)
   - Đảm bảo cấu trúc cột đúng như mẫu
   - Nhập dữ liệu vào các dòng tiếp theo

2. **Import vào hệ thống:**
   - Click nút **📥 Nhập Excel**
   - Chọn file Excel đã chuẩn bị
   - Click "Open"
   - Hệ thống sẽ xử lý và hiển thị kết quả:
     - ✅ Số bản ghi thành công
     - ❌ Số bản ghi lỗi (nếu có)
     - Chi tiết lỗi (nếu có)

3. **Kiểm tra kết quả:**
   - Dữ liệu mới sẽ tự động được load vào bảng
   - Kiểm tra lại dữ liệu đã import

### Export (Xuất) Dữ Liệu

1. **Xuất dữ liệu:**
   - Click nút **📤 Xuất Excel**
   - Chọn vị trí lưu file
   - Đặt tên file (mặc định: DanhSachSach.xlsx hoặc DanhSachDocGia.xlsx)
   - Click "Save"

2. **Kết quả:**
   - File Excel được tạo chứa toàn bộ dữ liệu hiện tại
   - Có thể mở bằng Excel, LibreOffice, Google Sheets

## 💡 Tips & Tricks

1. **Xuất trước khi Import:**
   - Export dữ liệu hiện tại để có file mẫu
   - Copy format và điền thêm dữ liệu mới
   - Import file đã chỉnh sửa

2. **Kiểm tra dữ liệu trước khi Import:**
   - Đảm bảo các mã tham chiếu (Mã Tác Giả, Mã Thể Loại, v.v.) đã tồn tại
   - Kiểm tra định dạng ngày tháng
   - Kiểm tra số điện thoại, email

3. **Import từng phần nhỏ:**
   - Nếu có nhiều dữ liệu, import từng batch nhỏ
   - Dễ dàng phát hiện và sửa lỗi

## ⚠️ Xử Lý Lỗi

### Lỗi thường gặp:

1. **"Không có dữ liệu để nhập"**
   - File Excel trống hoặc không đúng định dạng
   - Kiểm tra lại file Excel

2. **Lỗi "cannot find symbol" hoặc tương tự:**
   - Mã tham chiếu không tồn tại (Mã Tác Giả, Mã Thể Loại, v.v.)
   - Thêm dữ liệu tham chiếu trước khi import

3. **Lỗi định dạng ngày:**
   - Đảm bảo định dạng: dd/MM/yyyy
   - Ví dụ: 01/01/2024, không phải 1/1/2024

4. **Lỗi duplicate key:**
   - Mã Sách hoặc Mã Độc Giả đã tồn tại
   - Thay đổi mã hoặc xóa bản ghi cũ

## 📝 Ví Dụ Chi Tiết

### Ví dụ Import Sách:

1. Tạo file `sach_moi.xlsx` với nội dung:
```
Mã Sách | Tên Sách        | Mã Tác Giả | Mã Thể Loại | Mã NXB | Năm XB | Số Lượng | Đơn Giá | Vị Trí | Trạng Thái
S100    | Java Cơ Bản     | TG001      | TL001       | NXB001 | 2023   | 10       | 150000  | K1-A1  | Còn
S101    | Python Nâng Cao | TG002      | TL001       | NXB002 | 2024   | 5        | 200000  | K1-A2  | Còn
```

2. Click **📥 Nhập Excel** > Chọn file > OK

3. Xem kết quả: "Nhập thành công: 2 sách"

### Ví dụ Export Độc Giả:

1. Click **📤 Xuất Excel**
2. Chọn nơi lưu file > Đặt tên "DocGia_Backup.xlsx"
3. Click Save
4. Mở file để xem dữ liệu đã export

## 🎯 Lưu Ý Quan Trọng

- ✅ File Excel phải có extension .xlsx hoặc .xls
- ✅ Cấu trúc cột phải đúng thứ tự
- ✅ Dòng đầu tiên là header (tên cột)
- ✅ Kiểm tra dữ liệu trước khi import
- ✅ Backup dữ liệu trước khi thực hiện import lớn

## 🆘 Hỗ Trợ

Nếu gặp vấn đề:
1. Kiểm tra log trong console
2. Kiểm tra format file Excel
3. Đảm bảo các mã tham chiếu đã tồn tại trong DB
4. Export file mẫu để tham khảo format đúng
