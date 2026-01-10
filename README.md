# Library Management System

## Hệ thống Quản lý Thư viện

Ứng dụng quản lý thư viện được xây dựng bằng Java Swing với giao diện hiện đại sử dụng FlatLaf.

---

## � Thành viên Nhóm

- **Phạm Gia Khôi** - Trưởng nhóm
- **Võ Minh Tri**
- **Trần Đăng Khoa**
- **Trần Quyết Thắng**
- **Nguyễn Hải Dương**
- **Huỳnh Tuấn Kiệt**

---

## �📋 Yêu cầu Hệ thống

### 1. Phần mềm cần cài đặt:
- **JDK 17** trở lên (Project sử dụng JDK 24)
- **MySQL 8.0+**
- **Maven** (quản lý dependencies)
- **IDE**: IntelliJ IDEA / Eclipse / VS Code (khuyên dùng)

### 2. Thư viện Dependencies (Maven):
```xml
<dependencies>
    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.1.0</version>
    </dependency>
    
    <!-- FlatLaf (Modern Look and Feel) -->
    <dependency>
        <groupId>com.formdev</groupId>
        <artifactId>flatlaf</artifactId>
        <version>3.5.2</version>
    </dependency>
    
    <!-- FlatLaf Extras (Icons) -->
    <dependency>
        <groupId>com.formdev</groupId>
        <artifactId>flatlaf-extras</artifactId>
        <version>3.5.2</version>
    </dependency>
</dependencies>
```

---

## 📁 Cấu trúc Project

```
LibraryManagementSystem/
├── src/com/library/
│   ├── main/               # Entry point
│   │   └── Main.java
│   ├── model/              # Entity classes (POJO)
│   │   ├── Book.java
│   │   ├── Reader.java
│   │   ├── Staff.java
│   │   ├── Author.java
│   │   ├── Category.java
│   │   ├── Publisher.java
│   │   ├── Borrow.java
│   │   ├── BorrowTicket.java
│   │   ├── Return.java
│   │   ├── ReturnDetail.java
│   │   ├── TicketFine.java
│   │   ├── PenaltyRule.java
│   │   └── Order.java
│   ├── dao/                # Data Access Layer
│   │   ├── BookDAO.java
│   │   ├── ReaderDAO.java
│   │   ├── StaffDAO.java
│   │   ├── BorrowDAO.java
│   │   ├── ReturnDAO.java
│   │   └── TicketFineDAO.java
│   ├── service/            # Business Logic
│   │   ├── IBookService.java
│   │   ├── BookService.java
│   │   ├── BorrowService.java
│   │   ├── BorrowTicketService.java
│   │   └── AuthorService.java
│   ├── manager/            # Management classes
│   │   ├── LibraryManager.java
│   │   ├── BookManager.java
│   │   ├── ReaderManager.java
│   │   └── BorrowManager.java
│   ├── view/               # GUI (Swing)
│   │   ├── MainFrame.java
│   │   ├── LoginDialog.java
│   │   ├── BookManagementPanel.java
│   │   ├── ReaderManagementPanel.java
│   │   ├── BorrowReturnPanel.java
│   │   ├── FineManagementPanel.java
│   │   └── StaffManagementPanel.java
│   └── util/               # Utilities
│       ├── DBConnection.java
│       ├── DateUtil.java
│       └── ValidationUtil.java
├── database/
│   ├── database.sql        # Schema definition
│   └── test_data.sql       # Sample data (10 records each)
├── pom.xml                 # Maven configuration
└── README.md
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
3. Import schema và test data:
   ```bash
   mysql -u root -p library_management < database/database.sql
   mysql -u root -p library_management < database/test_data.sql
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

### 1. 🏠 Trang chủ (Dashboard)
- ✅ Thống kê tổng quan: Tổng sách, sách còn, phiếu quá hạn, tổng độc giả, tổng phạt
- ✅ Biểu đồ thống kê mượn sách theo tháng
- ✅ Top 10 sách được mượn nhiều nhất

### 2. 📚 Quản lý Sách
- ✅ Thêm/Sửa/Xóa sách (Novel, Text_Book, NormalBook)
- ✅ Tìm kiếm theo: Tên sách, Tác giả, Nhà xuất bản
- ✅ Lọc theo: Thể loại, Sách còn trong kho
- ✅ Quản lý tồn kho tự động
- ✅ Thông tin chi tiết: Tác giả, Thể loại, NXB, Năm XB, Vị trí

### 3. 👥 Quản lý Độc giả
- ✅ Đăng ký độc giả mới với mã tự động (DG001, DG002...)
- ✅ Cập nhật thông tin: Họ tên, Ngày sinh, Địa chỉ, SĐT, Email
- ✅ Gia hạn thẻ thư viện
- ✅ Tìm kiếm theo: Mã, Họ tên, SĐT, Email
- ✅ Lọc: Chỉ hiển thị độc giả đang hoạt động
- ✅ Xem lịch sử mượn sách

### 4. 📖 Mượn/Trả Sách
- ✅ Lập phiếu mượn tự động (PM001, PM002...)
- ✅ Kiểm tra sách còn/hết tự động
- ✅ Hỗ trợ mượn nhiều sách (chi tiết phiếu mượn)
- ✅ Xử lý trả sách với kiểm tra hư hỏng
- ✅ Tự động cập nhật tồn kho
- ✅ Tính tiền phạt trễ hạn và hư hỏng

### 5. 💰 Quản lý Phạt
- ✅ Tự động tạo phiếu phạt khi trả sách trễ/hư hỏng
- ✅ Phân loại: Tiền phạt trễ hạn, Tiền phạt hư hỏng
- ✅ Tìm kiếm phiếu phạt theo độc giả, trạng thái
- ✅ Cập nhật trạng thái thanh toán
- ✅ Báo cáo tổng tiền phạt

### 6. 📈 Thống kê & Báo cáo
- ✅ Thống kê mượn sách theo tháng (biểu đồ)
- ✅ Top 10 sách được mượn nhiều nhất
- ✅ Danh sách phiếu mượn quá hạn
- ✅ Báo cáo doanh thu tiền phạt
- ✅ Thống kê theo thể loại, độc giả

### 7. 👨‍💼 Quản lý Nhân viên (Admin only)
- ✅ Thêm/Sửa/Xóa nhân viên
- ✅ Phân quyền: Admin, Nhân viên
- ✅ Quản lý tài khoản đăng nhập

---

## 🎨 Giao diện (UI/UX)

### Screenshots
- **Login Screen:** Đăng nhập với xác thực username/password
- **Dashboard:** Tổng quan hệ thống với các thống kê realtime
- **Book Management:** Giao diện quản lý sách với tìm kiếm và lọc
- **Reader Management:** Quản lý độc giả với tính năng gia hạn thẻ
- **Borrow/Return:** Xử lý mượn/trả sách trực quan
- **Fine Management:** Quản lý phạt với cập nhật thanh toán

### Thiết kế
- **Look and Feel:** FlatLaf Light (Modern, flat design)
- **Color Scheme:** 
  - Primary: `#2196F3` (Blue)
  - Success: `#4CAF50` (Green)
  - Warning: `#FF9800` (Orange)
  - Danger: `#F44336` (Red)
- **Icons:** Unicode emoji và SVG icons
- **Typography:** Segoe UI (Windows/macOS compatible)
- **Responsive:** Auto-adjust table columns và form layouts

---

## 🛠️ Công nghệ Sử dụng

| Công nghệ | Phiên bản | Mục đích |
|-----------|-----------|----------|
| Java | 24.0.2 | Ngôn ngữ lập trình chính |
| Java Swing | Built-in JDK | GUI Framework |
| FlatLaf | 3.5.2 | Modern Look and Feel |
| MySQL | 8.0+ | Hệ quản trị cơ sở dữ liệu |
| MySQL Connector/J | 9.1.0 | JDBC Driver |
| Maven | 3.x | Build tool và quản lý dependencies |

### Kiến trúc & Design Patterns
- **Architecture:** 3-tier (Presentation - Business - Data)
- **Design Patterns:**
  - Singleton (DBConnection)
  - DAO Pattern (Data Access Objects)
  - MVC Pattern (Model-View-Controller)
  - Service Layer Pattern
- **OOP Principles:** Encapsulation, Inheritance, Polymorphism, Abstraction

---

## �️ Cấu trúc Database

### ERD (Entity Relationship Diagram)
Database gồm 13 bảng chính:

1. **TAC_GIA** (Authors) - Thông tin tác giả
2. **THE_LOAI** (Categories) - Thể loại sách
3. **NHA_XUAT_BAN** (Publishers) - Nhà xuất bản
4. **SACH** (Books) - Thông tin sách
5. **DOC_GIA** (Readers) - Độc giả
6. **NHAN_VIEN** (Staff) - Nhân viên
7. **PHIEU_MUON** (Borrow Tickets) - Phiếu mượn
8. **CT_PHIEU_MUON** (Borrow Details) - Chi tiết mượn
9. **PHIEU_TRA** (Return Tickets) - Phiếu trả
10. **CT_PHIEU_TRA** (Return Details) - Chi tiết trả
11. **PHIEU_PHAT** (Fine Tickets) - Phiếu phạt
12. **QUY_DINH_PHAT** (Penalty Rules) - Quy định phạt
13. **DON_HANG** (Orders) - Đơn hàng nhập sách

### Key Features
- **Foreign Keys:** Đảm bảo tính toàn vẹn dữ liệu
- **Auto-increment:** Timestamp tự động (CreatedAt, UpdatedAt)
- **UTF-8 Support:** Hỗ trợ tiếng Việt hoàn toàn
- **Constraints:** CHECK, UNIQUE, NOT NULL
- **Indexes:** Tối ưu hiệu suất query

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

### 4. Lỗi không thấy dữ liệu trong GUI
**Nguyên nhân:** Database chưa có test data  
**Giải pháp:**
```bash
mysql -u root -p library_management < database/test_data.sql
```

### 5. Lỗi Maven version
```
Error: Source option 24 is not supported
```
**Nguyên nhân:** JDK version không khớp  
**Giải pháp:** Cài JDK 17+ và set JAVA_HOME
```bash
export JAVA_HOME=/path/to/jdk-24
mvn clean compile
```

---

## 👥 Phân quyền

| Chức năng | Admin | Nhân viên |
|-----------|-------|-----------|
| 🏠 Xem Dashboard | ✅ | ✅ |
| 📚 Quản lý Sách | ✅ | ✅ (Chỉ xem) |
| 👥 Quản lý Độc giả | ✅ | ✅ |
| 📖 Mượn/Trả sách | ✅ | ✅ |
| 💰 Quản lý Phạt | ✅ | ✅ |
| 👨‍💼 Quản lý Nhân viên | ✅ | ❌ |
| 📦 Quản lý Đơn hàng | ✅ | ❌ |
| ⚙️ Quản lý Quy định | ✅ | ❌ |
| 📈 Xem Thống kê | ✅ | ✅ (Read-only) |

---

## 🚀 Features nổi bật

### 1. Tự động tính phạt thông minh
- Tự động phát hiện sách trả trễ khi tạo phiếu trả
- Tính toán tiền phạt theo quy định (5,000 VND/ngày)
- Phân loại: Phạt trễ hạn vs Phạt hư hỏng
- Lưu lịch sử phạt đầy đủ

### 2. Quản lý tồn kho realtime
- Tự động giảm số lượng khi mượn sách
- Tự động tăng số lượng khi trả sách
- Cảnh báo sách hết hàng
- Ngăn mượn khi số lượng = 0

### 3. Tìm kiếm & Lọc mạnh mẽ
- Tìm kiếm realtime (không cần nhấn Enter)
- Lọc đa điều kiện (Loại sách + Thể loại + Còn hàng)
- Hỗ trợ tìm kiếm tiếng Việt có dấu

### 4. Giao diện thân thiện
- Flat design hiện đại
- Màu sắc nhất quán, dễ nhìn
- Responsive với màn hình khác nhau
- Shortcuts keyboard (đang phát triển)

---

## 📞 Hỗ trợ & Đóng góp

### Báo lỗi (Bug Report)
Nếu phát hiện lỗi, vui lòng tạo Issue trên GitHub với thông tin:
- Mô tả lỗi chi tiết
- Các bước tái hiện
- Screenshot (nếu có)
- Log/Error message

### Đóng góp (Contributing)
Mọi đóng góp đều được chào đón! Hãy:
1. Fork repository
2. Tạo branch mới (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Mở Pull Request

### Liên hệ
- **Leader:** Phạm Gia Khôi
- **Repository:** [https://github.com/giakhoi0123/JavaSwing-LibraryManagement]
- **Email:** Phamgiakhoi0123@gmail.com

---

## 📄 License

Dự án này được phát triển cho mục đích học tập và nghiên cứu.  
**License:** MIT License - Tự do sử dụng cho mục đích giáo dục.

```
Copyright (c) 2026 Library Management Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 🎓 Học tập & Tham khảo

### Tài liệu học Java Swing
- [Oracle Java Tutorials - Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
- [FlatLaf Documentation](https://www.formdev.com/flatlaf/)

### Design Patterns
- [DAO Pattern](https://www.baeldung.com/java-dao-pattern)
- [MVC Pattern](https://www.geeksforgeeks.org/mvc-design-pattern/)
- [Singleton Pattern](https://refactoring.guru/design-patterns/singleton/java/example)

### JDBC & MySQL
- [JDBC Tutorial](https://www.tutorialspoint.com/jdbc/index.htm)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

**⭐ Nếu dự án hữu ích, hãy cho chúng tôi một Star trên GitHub!**

**Developed with ❤️ by Library Management Team**  
*Phạm Gia Khôi | Võ Minh Tri | Trần Đăng Khoa | Trần Quyết Thắng | Nguyễn Hải Dương | Huỳnh Tuấn Kiệt*
