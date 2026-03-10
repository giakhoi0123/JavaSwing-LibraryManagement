# 🎯 HƯỚNG DẪN SỬ DỤNG FILE EXCEL MẪU

## 📦 File Mẫu Đã Tạo Sẵn

### ✅ File CSV (Sử dụng ngay)
Trong thư mục `samples/`:
- **DanhSachSach_Mau.csv** - 10 cuốn sách mẫu
- **DanhSachDocGia_Mau.csv** - 10 độc giả mẫu

### 🔧 File Generator
- **SampleExcelGenerator.java** - Tạo file .xlsx từ code

## 🚀 CÁCH SỬ DỤNG NHANH

### Bước 1: Tạo Dữ Liệu Tham Chiếu

Chạy SQL script để tạo tác giả, thể loại, nhà xuất bản:

```bash
# Mở MySQL Workbench hoặc terminal
mysql -u root -p library_db < database/sample_reference_data.sql
```

Hoặc copy và chạy nội dung file `database/sample_reference_data.sql` trong MySQL Workbench.

### Bước 2: Sử Dụng File CSV

**Cách 1: Import trực tiếp file CSV vào Excel**
1. Mở Excel
2. File > Open > Chọn `samples/DanhSachDocGia_Mau.csv`
3. File > Save As > Chọn "Excel Workbook (.xlsx)"
4. Lưu với tên mới

**Cách 2: Mở bằng Excel rồi Save As**
1. Double-click file .csv
2. Excel sẽ tự động mở
3. Chọn "Save As" > "Excel Workbook"

### Bước 3: Import Vào Hệ Thống

1. **Chạy ứng dụng**
   ```bash
   cd LibraryManagementSystem
   mvn clean package
   java -jar target/library-management-1.0.jar
   ```

2. **Đăng nhập** với tài khoản admin

3. **Import Độc Giả:**
   - Vào tab "👥 Quản Lý Độc Giả"
   - Click nút "📥 Nhập Excel"
   - Chọn file `DanhSachDocGia_Mau.xlsx` (hoặc .csv đã save)
   - Chờ import hoàn tất

4. **Import Sách:**
   - Vào tab "📚 Quản Lý Sách"
   - Click nút "📥 Nhập Excel"
   - Chọn file `DanhSachSach_Mau.xlsx` (hoặc .csv đã save)
   - Chờ import hoàn tất

## 🔨 CÁCH TẠO FILE EXCEL .XLSX

### Cách 1: Chạy Generator Java (Tốt nhất ⭐)

```bash
cd LibraryManagementSystem

# Compile
javac -cp "target/classes:lib/*" src/com/library/util/SampleExcelGenerator.java

# Run
java -cp "target/classes:lib/*" com.library.util.SampleExcelGenerator
```

File Excel sẽ được tạo trong thư mục `samples/`:
- `DanhSachSach_Mau.xlsx`
- `DanhSachDocGia_Mau.xlsx`

### Cách 2: Sử dụng Maven

```bash
cd LibraryManagementSystem
mvn compile exec:java -Dexec.mainClass="com.library.util.SampleExcelGenerator"
```

### Cách 3: Từ IDE (IntelliJ/Eclipse)

1. Mở project trong IDE
2. Tìm file `SampleExcelGenerator.java`
3. Right-click > Run 'SampleExcelGenerator.main()'
4. File được tạo trong thư mục `samples/`

## 📊 NỘI DUNG FILE MẪU

### Sách (10 cuốn)
| Mã | Tên Sách | Năm | Giá |
|---|---|---|---|
| S001 | Lập Trình Java Cơ Bản | 2023 | 150,000đ |
| S002 | Cơ Sở Dữ Liệu MySQL | 2022 | 200,000đ |
| S003 | Thuật Toán Lập Trình | 2024 | 180,000đ |
| S004 | Lập Trình Python Nâng Cao | 2023 | 175,000đ |
| S005 | Trí Tuệ Nhân Tạo | 2024 | 250,000đ |
| ... | ... | ... | ... |

### Độc Giả (10 người)
| Mã | Họ Tên | SĐT | Email |
|---|---|---|---|
| DG001 | Nguyễn Văn An | 0901234567 | nva@email.com |
| DG002 | Trần Thị Bình | 0987654321 | ttb@email.com |
| DG003 | Lê Văn Cường | 0912345678 | lvc@email.com |
| ... | ... | ... | ... |

## ⚠️ LƯU Ý QUAN TRỌNG

### Trước khi import Sách:
✅ Đã chạy SQL script tạo:
- Tác giả (TG001-TG010)
- Thể loại (TL001-TL005)
- Nhà xuất bản (NXB001-NXB005)

### Trước khi import Độc Giả:
✅ Không cần dữ liệu tham chiếu
✅ Import trực tiếp được

### Encoding:
✅ File CSV dùng UTF-8
✅ Hỗ trợ tiếng Việt có dấu

## 🎯 QUY TRÌNH ĐỀ XUẤT

1. ✅ Chạy SQL: `sample_reference_data.sql`
2. ✅ Tạo file Excel từ CSV hoặc chạy Generator
3. ✅ Import Độc Giả trước
4. ✅ Import Sách sau
5. ✅ Kiểm tra kết quả

## 💡 TIPS HỮU ÍCH

### Sửa đổi dữ liệu mẫu:
1. Mở file CSV trong Excel
2. Chỉnh sửa nội dung
3. Save As > Excel Workbook
4. Import vào hệ thống

### Tạo file template riêng:
1. Copy file mẫu
2. Xóa tất cả dữ liệu (GIỮ LẠI HEADER)
3. Lưu làm template
4. Dùng cho các lần import sau

### Export để có file chuẩn:
1. Chạy app
2. Click "📤 Xuất Excel"
3. Dùng file export làm template
4. Thêm dữ liệu mới vào
5. Import lại

## 🐛 XỬ LÝ LỖI

### Lỗi: "Không tìm thấy tác giả/thể loại/NXB"
➡️ Chạy lại SQL script `sample_reference_data.sql`

### Lỗi: "Duplicate entry"
➡️ Thay đổi mã sách/độc giả trong file Excel

### Lỗi: "Invalid date format"
➡️ Đảm bảo ngày theo format: dd/MM/yyyy (ví dụ: 01/01/2024)

### File Excel không mở được
➡️ Dùng file CSV, mở bằng Excel rồi Save As

## 📞 HỖ TRỢ

Nếu gặp vấn đề:
1. Kiểm tra đã chạy SQL script chưa
2. Kiểm tra format file Excel
3. Thử export từ app để có file mẫu chuẩn
4. Xem log trong console để debug

---

**Chúc bạn sử dụng thành công! 🎉**
