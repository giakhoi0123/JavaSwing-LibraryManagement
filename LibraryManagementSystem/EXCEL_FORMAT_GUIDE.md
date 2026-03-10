# 📊 Cấu Trúc File Excel Chuẩn

## 📖 File Sách (Books)

### Cấu trúc bảng:

| # | Tên Cột | Kiểu | Bắt buộc | Ví dụ | Ghi chú |
|---|----------|------|----------|-------|---------|
| 1 | Mã Sách | Text | ✅ | S001 | Unique, không trùng |
| 2 | Tên Sách | Text | ✅ | Lập Trình Java | Tên đầy đủ |
| 3 | Mã Tác Giả | Text | ✅ | TG001 | Phải tồn tại trong DB |
| 4 | Mã Thể Loại | Text | ✅ | TL001 | Phải tồn tại trong DB |
| 5 | Mã NXB | Text | ✅ | NXB001 | Phải tồn tại trong DB |
| 6 | Năm XB | Số | ✅ | 2023 | Năm xuất bản |
| 7 | Số Lượng | Số | ✅ | 10 | Số lượng có sẵn |
| 8 | Đơn Giá | Số | ✅ | 150000 | Giá bằng VNĐ |
| 9 | Vị Trí | Text | ❌ | K1-Ke1-A1 | Vị trí trong thư viện |
| 10 | Trạng Thái | Text | ❌ | Còn | "Còn" hoặc "Hết" |

### Ví dụ thực tế:

```
Mã Sách | Tên Sách              | Mã TG | Mã TL | Mã NXB | Năm | SL | Giá    | Vị Trí    | TT
--------|-----------------------|-------|-------|--------|-----|----|---------|-----------|----- 
S001    | Lập Trình Java Cơ Bản | TG001 | TL001 | NXB001 | 2023| 10 | 150000 | K1-Ke1-A1 | Còn
S002    | Python cho Mọi Người  | TG002 | TL001 | NXB002 | 2024| 5  | 200000 | K1-Ke1-A2 | Còn
```

---

## 👥 File Độc Giả (Readers)

### Cấu trúc bảng:

| # | Tên Cột | Kiểu | Bắt buộc | Ví dụ | Ghi chú |
|---|----------|------|----------|-------|---------|
| 1 | Mã ĐG | Text | ✅ | DG001 | Unique, không trùng |
| 2 | Họ Tên | Text | ✅ | Nguyễn Văn An | Họ và tên đầy đủ |
| 3 | Ngày Sinh | Date | ✅ | 15/03/1995 | Format: dd/MM/yyyy |
| 4 | Giới Tính | Text | ✅ | Nam | "Nam" hoặc "Nữ" |
| 5 | SĐT | Text | ✅ | 0901234567 | 10-11 số |
| 6 | Email | Text | ❌ | nva@email.com | Email hợp lệ |
| 7 | Địa Chỉ | Text | ❌ | 123 ABC, Q1, HCM | Địa chỉ đầy đủ |
| 8 | Ngày Lập Thẻ | Date | ✅ | 01/01/2024 | Format: dd/MM/yyyy |
| 9 | Ngày Hết Hạn | Date | ✅ | 01/01/2025 | Format: dd/MM/yyyy |
| 10 | Trạng Thái | Text | ✅ | Hoạt động | "Hoạt động" hoặc "Khóa" |

### Ví dụ thực tế:

```
Mã ĐG | Họ Tên        | Ngày Sinh  | GT  | SĐT        | Email           | Địa Chỉ       | Lập Thẻ    | Hết Hạn    | TT
------|---------------|------------|-----|------------|-----------------|---------------|------------|------------|----------
DG001 | Nguyễn Văn An | 15/03/1995 | Nam | 0901234567 | nva@email.com   | 123 ABC, Q1   | 01/01/2024 | 01/01/2025 | Hoạt động
DG002 | Trần Thị Bình | 20/07/1998 | Nữ  | 0987654321 | ttb@email.com   | 456 XYZ, HN   | 15/02/2024 | 15/02/2025 | Hoạt động
```

---

## ⚠️ CÁC LỖI THƯỜNG GẶP

### 1. Lỗi Format Ngày

❌ **SAI:**
- 1/1/2024 (thiếu số 0)
- 2024/01/01 (sai thứ tự)
- 01-01-2024 (dùng dấu gạch ngang)

✅ **ĐÚNG:**
- 01/01/2024
- 15/03/1995
- 28/12/2023

### 2. Lỗi Giới Tính

❌ **SAI:**
- "nam" (chữ thường)
- "M" (viết tắt)
- "Male"

✅ **ĐÚNG:**
- "Nam" (chữ N hoa)
- "Nữ" (chữ N hoa)

### 3. Lỗi Trạng Thái Độc Giả

❌ **SAI:**
- "Active"
- "hoạt động" (chữ thường)
- "OK"

✅ **ĐÚNG:**
- "Hoạt động" (chữ H hoa)
- "Khóa" (chữ K hoa)

### 4. Lỗi Mã Tham Chiếu

❌ **SAI:**
```
Mã Sách: S001
Mã Tác Giả: TG999  ← Không tồn tại trong DB
```

✅ **ĐÚNG:**
```
Mã Sách: S001
Mã Tác Giả: TG001  ← Đã có trong DB sau khi chạy SQL script
```

---

## 📋 CHECKLIST TRƯỚC KHI IMPORT

### Cho File Sách:
- [ ] Dòng 1 là header (tên cột)
- [ ] Có đúng 10 cột
- [ ] Mã Sách không trùng
- [ ] Mã Tác Giả, Thể Loại, NXB đã tồn tại trong DB
- [ ] Năm XB là số (ví dụ: 2023)
- [ ] Số Lượng là số dương
- [ ] Đơn Giá là số dương

### Cho File Độc Giả:
- [ ] Dòng 1 là header (tên cột)
- [ ] Có đúng 10 cột
- [ ] Mã ĐG không trùng
- [ ] Ngày sinh format: dd/MM/yyyy
- [ ] Giới tính: "Nam" hoặc "Nữ"
- [ ] SĐT: 10-11 số
- [ ] Email hợp lệ (nếu có)
- [ ] Ngày Lập Thẻ và Hết Hạn format: dd/MM/yyyy
- [ ] Trạng Thái: "Hoạt động" hoặc "Khóa"

---

## 🎯 MẸO HAY

### 1. Copy Format từ File Mẫu
Thay vì tạo file mới, copy file mẫu và sửa dữ liệu.

### 2. Dùng Excel Formula để Tạo Mã Tự Động
```
Ô A2: =CONCATENATE("S", TEXT(ROW()-1, "000"))
Kéo xuống → S001, S002, S003, ...
```

### 3. Format Date trong Excel
1. Chọn cột ngày tháng
2. Right-click > Format Cells
3. Chọn Custom
4. Type: `dd/MM/yyyy`
5. Click OK

### 4. Validate Data trước khi Import
Excel có thể check:
- Data > Data Validation
- Set rules cho từng cột
- Prevent sai format

---

## 📱 QUICK REFERENCE

| Cần | Cột Số | Ví Dụ |
|-----|--------|-------|
| Mã Sách | 1 | S001 |
| Mã Độc Giả | 1 | DG001 |
| Ngày Tháng | 3,8,9 | 01/01/2024 |
| Giới Tính | 4 | Nam / Nữ |
| Số | 6,7,8 | 2023, 10, 150000 |

---

**💾 Lưu file này để tham khảo khi tạo file Excel!**
