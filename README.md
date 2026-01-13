# 📚 Library Management System

## Hệ thống Quản lý Thư viện Hiện đại

<div align="center">

![Java](https://img.shields.io/badge/Java-24.0.2-orange.svg)
![Swing](https://img.shields.io/badge/Swing-GUI-blue.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)
![FlatLaf](https://img.shields.io/badge/FlatLaf-3.5.2-green.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

</div>

Ứng dụng quản lý thư viện chuyên nghiệp được xây dựng bằng Java Swing với giao diện hiện đại sử dụng FlatLaf. Hệ thống cung cấp đầy đủ các tính năng quản lý sách, độc giả, mượn/trả sách, phạt, thống kê và nhiều tính năng nâng cao khác.

### ✨ Điểm nổi bật

- 🎨 **Giao diện hiện đại**: UI/UX thân thiện với FlatLaf Look and Feel
- 🔐 **Phân quyền chi tiết**: 9 loại quyền độc lập cho từng nhân viên
- 📊 **Thống kê thông minh**: Biểu đồ, bộ lọc ngày, báo cáo đa dạng
- 💾 **Xuất/Nhập dữ liệu**: Hỗ trợ PDF và Excel
- 🔔 **Thông báo tiếng Việt**: Tất cả lỗi được dịch sang tiếng Việt
- 🎯 **Tự động hóa**: Tính phạt, cập nhật tồn kho, tạo mã tự động
- 🚀 **Hiệu năng cao**: Tối ưu query, cache thông minh

---

## 👥 Thành viên Nhóm

| Thành viên | Vai trò | Công việc chính |
|------------|---------|-----------------|
| **Phạm Gia Khôi** | 👑 Trưởng nhóm | Architecture, Core Features, UI/UX |
| **Võ Minh Tri** | 💻 Dev | Database Design, DAO Layer |
| **Trần Đăng Khoa** | 💻 Dev | Business Logic, Services |
| **Trần Quyết Thắng** | 💻 Dev | GUI Components, Views |
| **Nguyễn Hải Dương** | 💻 Dev | Testing, Documentation |
| **Huỳnh Tuấn Kiệt** | 💻 Dev | Integration, Deployment |

---

## 📋 Yêu cầu Hệ thống

### 1. Phần mềm cần cài đặt:
- **JDK 17+** (Project sử dụng JDK 24)
- **MySQL 8.0+**
- **Maven 3.6+** (quản lý dependencies)
- **IDE**: IntelliJ IDEA / Eclipse / VS Code (khuyên dùng IntelliJ)

### 2. Thư viện Dependencies (Maven):
```xml
<dependencies>
    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.2.0</version>
    </dependency>
    
    <!-- FlatLaf (Modern Look and Feel) -->
    <dependency>
        <groupId>com.formdev</groupId>
        <artifactId>flatlaf</artifactId>
        <version>3.2.5</version>
    </dependency>
    
    <!-- FlatLaf Extras (Icons) -->
    <dependency>
        <groupId>com.formdev</groupId>
        <artifactId>flatlaf-extras</artifactId>
        <version>3.2.5</version>
    </dependency>
    
    <!-- JCalendar - Date Picker -->
    <dependency>
        <groupId>com.toedter</groupId>
        <artifactId>jcalendar</artifactId>
        <version>1.4</version>
    </dependency>
    
    <!-- Apache POI - Excel Export/Import -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.5</version>
    </dependency>
    
    <!-- iText - PDF Export -->
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.3</version>
    </dependency>
</dependencies>
```

---

## 📁 Cấu trúc Project

```
LibraryManagementSystem/
├── src/com/library/
│   ├── main/                    # Entry point
│   │   └── Main.java
│   ├── model/                   # Entity classes (POJO)
│   │   ├── Book.java            # Sách
│   │   ├── Reader.java          # Độc giả
│   │   ├── Staff.java           # Nhân viên
│   │   ├── Permission.java      # Phân quyền (NEW!)
│   │   ├── BorrowTicket.java    # Phiếu mượn
│   │   ├── BorrowDetail.java    # Chi tiết mượn
│   │   ├── ReturnTicket.java    # Phiếu trả
│   │   ├── ReturnDetail.java    # Chi tiết trả
│   │   └── TicketFine.java      # Phiếu phạt
│   ├── dao/                     # Data Access Layer
│   │   ├── BookDAO.java
│   │   ├── ReaderDAO.java
│   │   ├── StaffDAO.java
│   │   ├── AuthorDAO.java       # NEW!
│   │   ├── CategoryDAO.java     # NEW!
│   │   ├── PublisherDAO.java    # NEW!
│   │   ├── PermissionDAO.java   # NEW!
│   │   ├── BorrowDAO.java
│   │   ├── ReturnDAO.java
│   │   └── TicketFineDAO.java
│   ├── view/                    # GUI (Swing)
│   │   ├── MainFrame.java       # Main window (IMPROVED!)
│   │   ├── LoginDialog.java
│   │   ├── BookDialog.java      # Add/Edit Book (IMPROVED!)
│   │   ├── ReturnDialog.java    # Return books (NEW!)
│   │   ├── BookManagementPanel.java
│   │   ├── ReaderManagementPanel.java
│   │   ├── BorrowReturnPanel.java
│   │   ├── FineManagementPanel.java
│   │   ├── StatisticsPanel.java # Statistics (IMPROVED!)
│   │   └── StaffManagementPanel.java
│   └── util/                    # Utilities
│       ├── DBConnection.java
│       ├── DateUtil.java
│       ├── ValidationUtil.java
│       ├── ErrorMessages.java   # Error messages (NEW!)
│       ├── ExcelUtil.java       # Excel export/import (NEW!)
│       └── PDFUtil.java         # PDF export (NEW!)
├── database/
│   ├── database.sql                      # Schema definition
│   ├── test_data.sql                     # Sample data
│   └── permissions_and_metadata.sql      # Permissions & metadata (NEW!)
├── docs/                        # Documentation (NEW!)
│   ├── IMPROVEMENTS.md          # Detailed improvements
│   ├── USER_GUIDE.md            # User manual
│   └── CHANGELOG.md             # Version history
├── pom.xml                      # Maven configuration
└── README.md                    # This file
```

---

## 🚀 Hướng dẫn Cài đặt

### Bước 1: Clone Project

```bash
git clone [repository-url]
cd LibraryManagementSystem
```

### Bước 2: Cài đặt Database

1. Đảm bảo MySQL đang chạy trên port 3306
2. Tạo database và import schema:
   ```bash
   mysql -u root -p
   ```
   ```sql
   CREATE DATABASE library_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
3. Import schema, test data và permissions:
   ```bash
   mysql -u root -p library_management < database/database.sql
   mysql -u root -p library_management < database/test_data.sql
   mysql -u root -p library_management < database/permissions_and_metadata.sql
   ```

### Bước 3: Cấu hình Database Connection

Mở file `src/com/library/util/DBConnection.java` và cập nhật:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/library_management";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

### Bước 4: Build với Maven

```bash
mvn clean install
```

### Bước 5: Chạy ứng dụng

**Cách 1: Từ IDE (VS Code/IntelliJ)**
- Mở file `src/com/library/main/Main.java`
- Click Run hoặc Debug

**Cách 2: Từ Terminal**
```bash
mvn exec:java -Dexec.mainClass="com.library.main.Main"
```

**Cách 3: Từ JAR file (sau khi build)**
```bash
java -cp target/classes:~/.m2/repository/com/mysql/mysql-connector-j/9.1.0/mysql-connector-j-9.1.0.jar:~/.m2/repository/com/formdev/flatlaf/3.5.2/flatlaf-3.5.2.jar:~/.m2/repository/com/formdev/flatlaf-extras/3.5.2/flatlaf-extras-3.5.2.jar com.library.main.Main
```

---

## 🔐 Tài khoản Mặc định

Sau khi import test_data.sql, sử dụng các tài khoản sau để đăng nhập:

| Username | Password | Vai trò | Mô tả |
|----------|----------|---------|-------|
| admin | admin123 | Quản trị viên | Toàn quyền hệ thống |
| NV001 | 123456 | Nhân viên | Quản lý độc giả, mượn/trả sách |

**⚠️ Quan trọng:** Đổi mật khẩu ngay sau khi đăng nhập lần đầu!

---

## 📊 Chức năng Chính

### 🎨 **1. Menu Động với Highlight** ⭐NEW
- ✅ Menu có cùng màu xám ban đầu, dễ nhìn
- ✅ Highlight màu xanh khi được chọn
- ✅ Hiệu ứng hover mượt mà
- ✅ Dễ dàng nhận biết tab đang xem

### 🏠 **2. Trang chủ (Dashboard)**
- ✅ Thống kê tổng quan: Tổng sách, sách còn, phiếu quá hạn, tổng độc giả, tổng phạt
- ✅ 6 cards thống kê với màu sắc phân biệt
- ✅ Cập nhật realtime khi có thay đổi

### 📚 **3. Quản lý Sách** ⭐IMPROVED
- ✅ Thêm/Sửa/Xóa sách với validation đầy đủ
- ✅ **ComboBox cho Tác giả/Thể loại/NXB** (không cần nhớ mã!) ⭐NEW
- ✅ Hiển thị: "MÃ - TÊN" dễ nhìn và chọn
- ✅ Tìm kiếm theo: Tên sách, Tác giả, Nhà xuất bản
- ✅ Lọc theo: Thể loại, Sách còn trong kho
- ✅ Quản lý tồn kho tự động
- ✅ Thông tin chi tiết: Tác giả, Thể loại, NXB, Năm XB, Vị trí, Đơn giá
- ✅ **Xuất danh sách ra Excel/PDF** ⭐NEW

### 👥 **4. Quản lý Độc giả**
- ✅ Đăng ký độc giả mới với mã tự động (DG001, DG002...)
- ✅ Cập nhật thông tin: Họ tên, Ngày sinh, Địa chỉ, SĐT, Email
- ✅ Gia hạn thẻ thư viện
- ✅ Tìm kiếm theo: Mã, Họ tên, SĐT, Email
- ✅ Lọc: Chỉ hiển thị độc giả đang hoạt động
- ✅ Xem lịch sử mượn sách
- ✅ **Xuất danh sách ra Excel/PDF** ⭐NEW
- ✅ **Nhập hàng loạt từ Excel** ⭐NEW

### 📖 **5. Mượn/Trả Sách** ⭐IMPROVED
- ✅ Lập phiếu mượn tự động (PM001, PM002...)
- ✅ Kiểm tra sách còn/hết tự động
- ✅ Hỗ trợ mượn nhiều sách (chi tiết phiếu mượn)
- ✅ **UI trả sách mới với tính năng nâng cao**: ⭐NEW
  - Chọn tình trạng cho từng cuốn: Bình thường, Hư hỏng nhẹ/nặng, Mất
  - Ghi chú chi tiết cho mỗi sách
  - Tự động tính tiền phạt (quá hạn + hư hỏng)
  - Button "Tính lại tiền phạt"
- ✅ Tự động cập nhật tồn kho
- ✅ **Xuất phiếu mượn/trả ra PDF** ⭐NEW

### 💰 **6. Quản lý Phạt** ⭐IMPROVED
- ✅ **Tách rõ Phiếu Trả và Phiếu Phạt**: ⭐NEW
  - Phiếu trả: Ghi nhận việc trả sách và tình trạng
  - Phiếu phạt: Tạo riêng khi có vi phạm
- ✅ Tự động tạo phiếu phạt khi:
  - Trả sách trễ hạn (5,000 VNĐ/ngày)
  - Hư hỏng nhẹ (20,000 VNĐ/cuốn)
  - Hư hỏng nặng (100,000 VNĐ/cuốn)
  - Mất sách (500,000 VNĐ/cuốn)
- ✅ Phân loại: Tiền phạt trễ hạn, Tiền phạt hư hỏng
- ✅ Tìm kiếm phiếu phạt theo độc giả, trạng thái
- ✅ Cập nhật trạng thái thanh toán
- ✅ Báo cáo tổng tiền phạt
- ✅ **Xuất báo cáo phạt ra PDF** ⭐NEW

### 📈 **7. Thống kê & Báo cáo** ⭐IMPROVED
- ✅ **Bộ lọc ngày với JDateChooser**: Chọn khoảng thời gian thống kê ⭐NEW
- ✅ **Biểu đồ cột**: Thống kê mượn sách theo thời gian ⭐NEW
- ✅ **Top 5 sách được mượn nhiều nhất** với số lượt mượn ⭐NEW
- ✅ 6 cards thống kê tổng quan
- ✅ Danh sách phiếu mượn quá hạn
- ✅ Báo cáo doanh thu tiền phạt
- ✅ Thống kê theo thể loại, độc giả
- ✅ **Xuất tất cả báo cáo ra PDF** ⭐NEW

### 👨‍💼 **8. Quản lý Nhân viên** ⭐IMPROVED
- ✅ Thêm/Sửa/Xóa nhân viên
- ✅ **Hệ thống phân quyền chi tiết với 9 loại quyền**: ⭐NEW
  1. 📚 Quản lý sách
  2. 👥 Quản lý độc giả
  3. 📖 Quản lý mượn
  4. 📥 Quản lý trả
  5. 💰 Quản lý phạt
  6. 👨‍💼 Quản lý nhân viên
  7. 📈 Xem thống kê
  8. 📥 Xuất dữ liệu
  9. 📤 Nhập dữ liệu
- ✅ Phân quyền: Admin (full quyền), Librarian (quyền hạn chế)
- ✅ Quản lý tài khoản đăng nhập
- ✅ Kiểm tra quyền trước khi thực hiện thao tác

### 💾 **9. Xuất/Nhập Dữ liệu** ⭐NEW
- ✅ **Xuất Excel**:
  - Danh sách sách với đầy đủ thông tin
  - Danh sách độc giả
  - Định dạng đẹp với header và styling
- ✅ **Nhập Excel**:
  - Import hàng loạt sách từ Excel
  - Import độc giả
  - Validation và báo lỗi chi tiết
- ✅ **Xuất PDF**:
  - Danh sách sách/độc giả với format chuyên nghiệp
  - Phiếu mượn/trả để in
  - Header, footer, và branding

### 🔔 **10. Thông báo Tiếng Việt** ⭐NEW
- ✅ Tất cả lỗi được dịch sang tiếng Việt
- ✅ Thông báo rõ ràng, dễ hiểu
- ✅ Phân loại lỗi: Database, Validation, Nghiệp vụ
- ✅ Tự động dịch lỗi SQL phổ biến:
  - "Duplicate entry" → "Dữ liệu đã tồn tại trong hệ thống"
  - "Foreign key constraint" → "Không thể xóa vì có dữ liệu liên quan"
  - "Cannot be null" → "Thiếu thông tin bắt buộc"

---

## 🎨 Giao diện (UI/UX)

### 🎯 Thiết kế Hiện đại
- **Look and Feel:** FlatLaf Light (Modern, flat design)
- **Responsive:** Auto-adjust với màn hình khác nhau
- **Icons:** Unicode emoji (📚📖👥💰📈) và SVG icons
- **Typography:** Segoe UI (Windows/macOS compatible)

### 🌈 Color Scheme
| Màu | Hex Code | Sử dụng cho |
|-----|----------|-------------|
| 🔵 Primary | `#2196F3` | Menu selected, buttons chính |
| 🟢 Success | `#4CAF50` | Thành công, available |
| 🟠 Warning | `#FF9800` | Cảnh báo, pending |
| 🔴 Danger | `#F44336` | Lỗi, overdue, delete |
| ⚫ Dark | `#424242` | Menu default, text |
| ⚪ Light | `#FFFFFF` | Background |

### ✨ Features UI nổi bật
- **Menu động**: Highlight màu xanh khi chọn, hover effect mượt
- **Cards thống kê**: 6 cards với màu sắc phân biệt, số liệu lớn rõ ràng
- **Tables**: Striped rows, sortable columns, row highlight on hover
- **Dialogs**: Modal dialogs với validation realtime
- **Date Pickers**: JDateChooser với format dd/MM/yyyy
- **ComboBoxes**: Dropdown đẹp với format "MÃ - TÊN"
- **Charts**: Biểu đồ cột đơn giản, dễ đọc

---

## 🛠️ Công nghệ Sử dụng

| Công nghệ | Phiên bản | Mục đích |
|-----------|-----------|----------|
| ☕ Java | 24.0.2 | Ngôn ngữ lập trình chính |
| 🖼️ Java Swing | Built-in JDK | GUI Framework |
| 🎨 FlatLaf | 3.2.5 | Modern Look and Feel |
| 🗄️ MySQL | 8.0+ | Hệ quản trị cơ sở dữ liệu |
| 🔌 MySQL Connector/J | 8.2.0 | JDBC Driver |
| 📦 Maven | 3.x | Build tool và dependency management |
| 📅 JCalendar | 1.4 | Date picker component |
| 📊 Apache POI | 5.2.5 | Excel export/import |
| 📄 iText PDF | 5.5.13.3 | PDF generation |

### 🏗️ Kiến trúc & Design Patterns
- **Architecture:** 3-tier (Presentation - Business - Data)
- **Design Patterns:**
  - 🔹 **Singleton**: DBConnection (đảm bảo chỉ 1 connection pool)
  - 🔹 **DAO Pattern**: Tách biệt logic truy cập database
  - 🔹 **MVC Pattern**: Model-View-Controller cho GUI
  - 🔹 **Factory Pattern**: Tạo objects (đang phát triển)
  - 🔹 **Observer Pattern**: Event handling cho UI updates
- **OOP Principles:** 
  - ✅ Encapsulation: Private fields, public getters/setters
  - ✅ Inheritance: Staff extends User, Book types
  - ✅ Polymorphism: Method overriding, Interface implementation
  - ✅ Abstraction: Abstract classes, Interfaces

---

## 🗄️ Cấu trúc Database

### 📊 ERD (Entity Relationship Diagram)
Database gồm **16 bảng chính**:

#### 📚 Quản lý Sách
1. **TAC_GIA** (Authors) - Thông tin tác giả
2. **THE_LOAI** (Categories) - Thể loại sách
3. **NHA_XUAT_BAN** (Publishers) - Nhà xuất bản
4. **SACH** (Books) - Thông tin sách

#### 👥 Quản lý Người dùng
5. **DOC_GIA** (Readers) - Độc giả
6. **NHAN_VIEN** (Staff) - Nhân viên
7. **NHAN_VIEN_QUYEN** (Staff Permissions) - Phân quyền nhân viên ⭐NEW

#### 📖 Quản lý Mượn/Trả
8. **PHIEU_MUON** (Borrow Tickets) - Phiếu mượn
9. **CT_PHIEU_MUON** (Borrow Details) - Chi tiết mượn
10. **PHIEU_TRA** (Return Tickets) - Phiếu trả
11. **CT_PHIEU_TRA** (Return Details) - Chi tiết trả

#### 💰 Quản lý Phạt
12. **PHIEU_PHAT** (Fine Tickets) - Phiếu phạt
13. **QUY_DINH_PHAT** (Penalty Rules) - Quy định phạt

#### 📦 Quản lý Đơn hàng
14. **DON_HANG** (Orders) - Đơn hàng nhập sách
15. **CT_DON_HANG** (Order Details) - Chi tiết đơn hàng

### 🔑 Key Features
- ✅ **Foreign Keys:** Đảm bảo tính toàn vẹn dữ liệu
- ✅ **Auto-increment:** Timestamp tự động (CreatedAt, UpdatedAt)
- ✅ **UTF-8 Support:** Hỗ trợ tiếng Việt hoàn toàn (utf8mb4)
- ✅ **Constraints:** CHECK, UNIQUE, NOT NULL
- ✅ **Indexes:** Tối ưu hiệu suất query
- ✅ **ON DELETE CASCADE:** Tự động xóa dữ liệu liên quan
- ✅ **DEFAULT VALUES:** Giá trị mặc định hợp lý

### 📋 Sample Data
File `test_data.sql` cung cấp:
- 10 tác giả mẫu (Nguyễn Nhật Ánh, Tô Hoài...)
- 5 thể loại (Văn học, Khoa học, Lịch sử...)
- 5 nhà xuất bản (NXB Trẻ, Kim Đồng...)
- 20+ sách mẫu
- 10 độc giả
- Admin và staff accounts
- Quy định phạt mặc định

---

## �📝 Quy ước Code

### Naming Conventions
- **Classes:** PascalCase (VD: `BookManagementPanel`, `ReaderDAO`)
- **Methods:** camelCase (VD: `loadBooksData()`, `performSearch()`)
- **Variables:** camelCase (VD: `txtSearch`, `cmbCategoryFilter`)
- **Constants:** UPPER_SNAKE_CASE (VD: `DB_URL`, `MAX_BORROW_DAYS`)
- **Packages:** lowercase (VD: `com.library.model`, `com.library.dao`)

### Code Structure
- **Package Organization:** MVC pattern với 3-tier architecture
- **Comments:** JavaDoc cho tất cả public methods
- **Exception Handling:** Try-catch với SQLException logging
- **Encoding:** UTF-8 (hỗ trợ tiếng Việt)
- **Indentation:** 4 spaces (không dùng tab)

### Best Practices
- Sử dụng PreparedStatement để tránh SQL Injection
- Close database resources trong finally block hoặc try-with-resources
- Validate input trước khi lưu vào database
- Sử dụng Service layer cho business logic phức tạp

---

## 🐛 Xử lý Lỗi thường gặp

### 1. Lỗi kết nối Database
```
Error: Communications link failure
```
**Nguyên nhân:** MySQL service không chạy hoặc port sai  
**Giải pháp:** 
- Kiểm tra MySQL đang chạy: `sudo systemctl status mysql` (Linux) hoặc MySQL Workbench
- Kiểm tra port 3306 có mở không
- Verify DB_URL, DB_USER, DB_PASSWORD trong DBConnection.java

### 2. Lỗi encoding tiếng Việt
```
Error: Incorrect string value: '\xC4\x90\xE1\xBA\xA1...'
```
**Nguyên nhân:** Database không dùng UTF-8  
**Giải pháp:** 
```sql
ALTER DATABASE library_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
Hoặc thêm vào connection string:
```java
DB_URL = "jdbc:mysql://localhost:3306/library_management?useUnicode=true&characterEncoding=UTF-8"
```

### 3. Lỗi FlatLaf không load
```
Error: ClassNotFoundException: com.formdev.flatlaf.FlatLaf
```
**Nguyên nhân:** Maven dependencies chưa được download  
**Giải pháp:**
```bash
mvn clean install
```

### 4. Lỗi JCalendar không load ⭐NEW
```
Error: ClassNotFoundException: com.toedter.calendar.JDateChooser
```
**Nguyên nhân:** Dependency JCalendar chưa có  
**Giải pháp:**
```bash
mvn clean install -U
```

### 5. Lỗi Apache POI (Excel) ⭐NEW
```
Error: NoClassDefFoundError: org.apache.poi.ss.usermodel.Workbook
```
**Nguyên nhân:** Apache POI dependencies chưa được download  
**Giải pháp:**
```bash
mvn dependency:resolve
mvn clean package
```

### 6. Lỗi iText (PDF) ⭐NEW
```
Error: ClassNotFoundException: com.itextpdf.text.Document
```
**Nguyên nhân:** iText dependency chưa có  
**Giải pháp:**
```bash
mvn clean install
```

### 7. Lỗi không thấy dữ liệu trong GUI
**Nguyên nhân:** Database chưa có test data  
**Giải pháp:**
```bash
mysql -u root -p library_management < database/test_data.sql
```

### 8. Lỗi Permissions ⭐NEW
```
Error: Table 'library_management.NHAN_VIEN_QUYEN' doesn't exist
```
**Nguyên nhân:** Chưa chạy script permissions  
**Giải pháp:**
```bash
mysql -u root -p library_management < database/permissions_and_metadata.sql
```

### 9. Lỗi Maven version
```
Error: Source option 24 is not supported
```
**Nguyên nhân:** JDK version không khớp  
**Giải pháp:** Cài JDK 17+ và set JAVA_HOME
```bash
export JAVA_HOME=/path/to/jdk-24
mvn clean compile
```

### 10. Lỗi UTF-8 encoding
**Nguyên nhân:** File không được lưu với UTF-8  
**Giải pháp:** Set encoding trong VS Code/IntelliJ:
```
File > Settings > Editor > File Encodings > UTF-8
```

---

## 👥 Phân quyền

### 🔒 Hệ thống phân quyền chi tiết ⭐NEW

Hệ thống hỗ trợ **9 quyền cơ bản**, có thể tùy chỉnh linh hoạt:

| STT | Quyền | Mã quyền | Admin | Librarian | Staff |
|-----|-------|----------|-------|-----------|-------|
| 1 | 📚 Quản lý Sách | `MANAGE_BOOKS` | ✅ | ✅ | ❌ |
| 2 | 👥 Quản lý Độc giả | `MANAGE_READERS` | ✅ | ✅ | ❌ |
| 3 | 📖 Quản lý Mượn sách | `MANAGE_BORROW` | ✅ | ✅ | ✅ |
| 4 | 📕 Quản lý Trả sách | `MANAGE_RETURN` | ✅ | ✅ | ✅ |
| 5 | 💰 Quản lý Phạt | `MANAGE_FINES` | ✅ | ✅ | ❌ |
| 6 | 👨‍💼 Quản lý Nhân viên | `MANAGE_STAFF` | ✅ | ❌ | ❌ |
| 7 | 📈 Xem Thống kê | `VIEW_STATISTICS` | ✅ | ✅ | ✅ |
| 8 | 📤 Export dữ liệu | `EXPORT_DATA` | ✅ | ✅ | ❌ |
| 9 | 📥 Import dữ liệu | `IMPORT_DATA` | ✅ | ❌ | ❌ |

### 🎯 Preset Permissions

**Admin (Full Access):**
```java
Permission.getAdminPermissions()
// All 9 permissions enabled
```

**Librarian (Standard):**
```java
Permission.getLibrarianPermissions()
// MANAGE_BOOKS, MANAGE_READERS, MANAGE_BORROW, 
// MANAGE_RETURN, VIEW_STATISTICS, EXPORT_DATA
```

**Custom Permissions:**
Có thể tạo nhân viên với bất kỳ tổ hợp quyền nào thông qua bảng `NHAN_VIEN_QUYEN`

### 🛡️ Cách sử dụng
```java
// Kiểm tra quyền trước khi thực hiện hành động
if (currentStaff.hasPermission(Permission.MANAGE_STAFF)) {
    // Cho phép quản lý nhân viên
} else {
    JOptionPane.showMessageDialog(null, 
        ErrorMessages.PERMISSION_DENIED);
}
```

---

## 🚀 Features nổi bật

### 1. 🎨 Menu Highlighting System ⭐NEW
- **Màu sắc nhất quán:** Tất cả menu items sử dụng màu mặc định #424242
- **Highlight selection:** Menu được chọn hiển thị màu xanh #2196F3
- **Visual feedback:** Người dùng luôn biết đang ở trang nào
- **Dynamic switching:** Tự động cập nhật khi chuyển trang

### 2. 📊 Advanced Statistics Dashboard ⭐NEW
- **Date Range Filter:** Chọn khoảng thời gian với JDateChooser
- **Bar Chart:** Biểu đồ cột thống kê số lượng mượn theo tháng
- **Top Books:** Top 5 sách được mượn nhiều nhất
- **Smart Analytics:** Tự động tính toán và visualize dữ liệu
- **Export Reports:** Xuất thống kê ra PDF/Excel

### 3. 🔍 Smart ComboBox Selection ⭐NEW
- **No more manual typing:** Chọn từ dropdown thay vì nhập tay
- **Format "MÃ - TÊN":** Dễ nhận diện (vd: "TG001 - Nguyễn Nhật Ánh")
- **3 ComboBoxes:**
  - 📝 Tác giả (Author)
  - 📚 Thể loại (Category)
  - 🏢 Nhà xuất bản (Publisher)
- **Searchable:** Gõ để tìm nhanh trong danh sách
- **Error prevention:** Không thể nhập sai ID

### 4. 🇻🇳 Vietnamese Error Messages ⭐NEW
- **50+ predefined messages:** Tất cả lỗi đều có message tiếng Việt
- **Categorized:** Database, Validation, Book, Reader, Borrow, Return, Fine, Staff
- **User-friendly:** Dễ hiểu, hướng dẫn cách khắc phục
- **Centralized:** File `ErrorMessages.java` quản lý tập trung
- **Example:**
  ```java
  ErrorMessages.BOOK_NOT_FOUND 
  // → "Không tìm thấy sách với mã đã cho"
  
  ErrorMessages.READER_HAS_DEBT 
  // → "Độc giả đang có nợ. Vui lòng thanh toán trước khi mượn sách mới"
  ```

### 5. 🔐 Granular Permission System ⭐NEW
- **9-level permissions:** Chi tiết đến từng chức năng
- **Role-based:** Admin, Librarian, Staff presets
- **Custom roles:** Tạo role tùy chỉnh với bất kỳ tổ hợp quyền nào
- **Database-driven:** Lưu trong bảng `NHAN_VIEN_QUYEN`
- **Runtime checks:** Kiểm tra quyền trước mọi hành động nhạy cảm
- **Security:** Ngăn chặn truy cập trái phép

### 6. 📤 Export/Import Excel & PDF ⭐NEW

**Excel Export/Import:**
- 📊 Export Books: Danh sách sách ra Excel
- 👥 Export Readers: Danh sách độc giả ra Excel
- 📥 Import Books: Nhập sách hàng loạt từ Excel
- 📥 Import Readers: Nhập độc giả từ Excel
- ✅ Validation: Kiểm tra dữ liệu trước khi import
- 🎨 Styling: Header màu xanh, borders, format currency

**PDF Export:**
- 📄 Export Books List: In danh sách sách
- 📄 Export Readers List: In danh sách độc giả
- 🎫 Borrow Tickets: In phiếu mượn
- 🎫 Return Tickets: In phiếu trả
- 🎫 Fine Tickets: In phiếu phạt
- 🏢 Professional format: Header, footer, tables, Vietnamese support

### 7. 📕 Enhanced Return Dialog ⭐NEW
- **Status per book:** Mỗi sách có trạng thái riêng khi trả
- **5 Status options:**
  - ✅ Bình thường (Normal)
  - ⚠️ Hư hỏng nhẹ (Light damage) - 20,000 VNĐ
  - ❌ Hư hỏng nặng (Heavy damage) - 100,000 VNĐ
  - 🚫 Mất sách (Lost book) - 500,000 VNĐ
  - 🔄 Chưa trả (Not returned yet)
- **Auto fine calculation:** Tự động tính phạt trễ + phạt hư hỏng
- **Detailed info:** Hiển thị tên sách, số ngày trễ, tổng phạt
- **Smart validation:** Không cho trả nếu chưa chọn trạng thái

### 8. 🎫 Separated Fine & Return Tickets ⭐IMPROVED
- **Return Ticket (PHIEU_TRA):** 
  - Chỉ lưu thông tin trả sách
  - Status sách (Bình thường/Hư hỏng/Mất)
  - Không bao gồm tiền phạt
  
- **Fine Ticket (PHIEU_PHAT):**
  - Chuyên về vi phạm và phạt
  - Liên kết với phiếu trả
  - Ghi rõ lý do phạt (Trễ hạn/Hư hỏng)
  - Tracking trạng thái thanh toán

### 9. 🔄 Auto realtime updates
- Tự động refresh data sau thao tác
- Không cần reload trang thủ công
- Sync giữa các panel

### 10. 🔍 Search & Filter nâng cao
- Tìm kiếm realtime không delay
- Lọc đa điều kiện (Type, Category, InStock)
- Hỗ trợ tiếng Việt có dấu
- Highlight kết quả tìm kiếm

---

## 📞 Hỗ trợ & Đóng góp

### 🐛 Báo lỗi (Bug Report)
Nếu phát hiện lỗi, vui lòng tạo Issue trên GitHub với thông tin:
- 📝 Mô tả lỗi chi tiết
- 🔄 Các bước tái hiện lỗi
- 📸 Screenshot (nếu có)
- 🪵 Log/Error message
- 💻 Môi trường (OS, Java version, MySQL version)

### ✨ Đề xuất tính năng (Feature Request)
Chúng tôi rất hoan nghênh ý tưởng mới! Hãy mô tả:
- 🎯 Mục đích của tính năng
- 📋 Use case cụ thể
- 🎨 Mockup/sketch (nếu có)
- 💡 Lợi ích mang lại

### 🤝 Đóng góp (Contributing)
Mọi đóng góp đều được chào đón! Quy trình:
1. 🍴 Fork repository
2. 🌿 Tạo branch mới (`git checkout -b feature/AmazingFeature`)
3. ✍️ Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. 📤 Push to branch (`git push origin feature/AmazingFeature`)
5. 🔀 Mở Pull Request với mô tả chi tiết

**Code Standards:**
- ✅ Follow existing code conventions
- ✅ Add JavaDoc comments for public methods
- ✅ Write meaningful commit messages
- ✅ Test thoroughly before PR
- ✅ Update documentation if needed

### 📧 Liên hệ
- **Team Leader:** Phạm Gia Khôi
- **GitHub:** [https://github.com/giakhoi0123/JavaSwing-LibraryManagement](https://github.com/giakhoi0123/JavaSwing-LibraryManagement)
- **Email:** Phamgiakhoi0123@gmail.com
- **Discord:** (Coming soon)

### 🎯 Roadmap (Kế hoạch tương lai)
- [ ] Mobile responsive web version
- [ ] RESTful API backend
- [ ] Email notifications (overdue, reminders)
- [ ] QR code for book scanning
- [ ] Advanced analytics with charts
- [ ] Multi-language support (English, Chinese)
- [ ] Dark mode
- [ ] Backup/Restore database from GUI

---

## 📄 License

Dự án này được phát triển cho mục đích học tập và nghiên cứu.  
**License:** MIT License - Tự do sử dụng cho mục đích giáo dục và thương mại.

```
Copyright (c) 2025 Library Management Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```

---

## 🎓 Học tập & Tham khảo

### 📚 Tài liệu Java Swing
- [Oracle Java Tutorials - Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
- [FlatLaf Documentation](https://www.formdev.com/flatlaf/)
- [JCalendar Documentation](https://toedter.com/jcalendar/)

### 🏗️ Design Patterns
- [DAO Pattern](https://www.baeldung.com/java-dao-pattern)
- [MVC Pattern](https://www.geeksforgeeks.org/mvc-design-pattern/)
- [Singleton Pattern](https://refactoring.guru/design-patterns/singleton/java/example)
- [Factory Pattern](https://refactoring.guru/design-patterns/factory-method/java/example)

### 🗄️ JDBC & MySQL
- [JDBC Tutorial](https://www.tutorialspoint.com/jdbc/index.htm)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [MySQL Performance Tuning](https://dev.mysql.com/doc/refman/8.0/en/optimization.html)

### 📊 Libraries Used
- [Apache POI - Excel Processing](https://poi.apache.org/)
- [iText - PDF Generation](https://itextpdf.com/en)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

### 🎨 UI/UX Resources
- [Material Design Guidelines](https://material.io/design)
- [Java Swing Best Practices](https://www.oracle.com/technical-resources/articles/java/mixing-swing-and-awt.html)

---

## 🏆 Acknowledgments

Xin cảm ơn:
- 👨‍🏫 Giảng viên hướng dẫn đã tận tình chỉ bảo
- 👥 Toàn bộ team members đã cống hiến hết mình
- 💻 Cộng đồng Open Source đã cung cấp các thư viện tuyệt vời
- 📚 Stack Overflow và các forums đã giúp giải quyết nhiều vấn đề
- ☕ Coffee và những đêm thức trắng debug

---

## 📈 Project Statistics

<div align="center">

| Metric | Value |
|--------|-------|
| 📁 Total Files | 40+ |
| 📝 Lines of Code | ~8,000+ |
| 🗄️ Database Tables | 16 |
| 🎨 UI Panels | 10 |
| 🔧 DAO Classes | 10 |
| 🛠️ Utility Classes | 5 |
| 📊 Features | 50+ |
| 👥 Contributors | 6 |
| ⏱️ Development Time | 3 months |

</div>

---

**⭐ Nếu dự án hữu ích, hãy cho chúng tôi một Star trên GitHub! ⭐**

<div align="center">

**Developed with ❤️ by Library Management Team**

*Phạm Gia Khôi | Võ Minh Tri | Trần Đăng Khoa | Trần Quyết Thắng | Nguyễn Hải Dương | Huỳnh Tuấn Kiệt*

---

**Made in Vietnam 🇻🇳 | 2025**

</div>
