# Library Management System

## Hệ thống Quản lý Thư viện

Ứng dụng quản lý thư viện được xây dựng bằng Java Swing với giao diện hiện đại sử dụng FlatLaf.

---

## 📋 Yêu cầu Hệ thống

### 1. Phần mềm cần cài đặt:
- **JDK 17** trở lên
- **MySQL 8.0+** hoặc **SQL Server 2019+**
- **IDE**: IntelliJ IDEA / Eclipse / NetBeans

### 2. Thư viện Dependencies:
- **MySQL Connector/J** (JDBC Driver)
- **FlatLaf** (Look and Feel)
- **JFreeChart** (Optional - cho biểu đồ)
- **Apache POI** (Optional - xuất Excel)

---

## 📁 Cấu trúc Project

```
LibraryManagementSystem/
├── src/
│   └── com/
│       └── library/
│           ├── main/               # Main application entry point
│           │   └── Main.java
│           ├── model/              # POJO classes (Entity)
│           │   ├── Book.java
│           │   ├── Reader.java
│           │   ├── Staff.java
│           │   ├── BorrowTicket.java
│           │   ├── BorrowDetail.java
│           │   └── ReturnTicket.java
│           ├── dao/                # Data Access Object layer
│           │   ├── BookDAO.java
│           │   ├── ReaderDAO.java
│           │   ├── StaffDAO.java
│           │   ├── BorrowDAO.java
│           │   └── ReturnDAO.java
│           ├── service/            # Business logic layer
│           │   ├── BookService.java
│           │   ├── BorrowService.java
│           │   └── AuthService.java
│           ├── view/               # GUI components (Swing)
│           │   ├── MainFrame.java
│           │   ├── LoginDialog.java
│           │   ├── DashboardPanel.java
│           │   ├── BookManagementPanel.java
│           │   ├── ReaderManagementPanel.java
│           │   └── BorrowReturnPanel.java
│           ├── controller/         # Event handlers
│           │   ├── BookController.java
│           │   ├── BorrowController.java
│           │   └── LoginController.java
│           └── util/               # Utility classes
│               ├── DBConnection.java
│               ├── DateUtil.java
│               └── ValidationUtil.java
├── database/
│   └── database.sql                # SQL script
├── lib/                            # External libraries (JAR files)
├── resources/                      # Icons, images, config files
└── README.md
```

---

## 🚀 Hướng dẫn Cài đặt

### Bước 1: Cài đặt Database

1. Mở MySQL Workbench hoặc Command Line
2. Chạy file `database/database.sql`:
   ```bash
   mysql -u root -p < database/database.sql
   ```
3. Kiểm tra database đã được tạo:
   ```sql
   USE library_management;
   SHOW TABLES;
   ```

### Bước 2: Cấu hình Kết nối Database

Mở file `src/com/library/util/DBConnection.java` và chỉnh sửa:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/library_management";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password_here";
```

### Bước 3: Thêm Thư viện (Dependencies)

#### Cách 1: Maven (pom.xml)
```xml
<dependencies>
    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    
    <!-- FlatLaf -->
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
</dependencies>
```

#### Cách 2: Tải thủ công (JAR files)
1. Tải các file JAR:
   - [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
   - [FlatLaf](https://www.formdev.com/flatlaf/)
2. Copy vào thư mục `lib/`
3. Add to Build Path trong IDE

### Bước 4: Chạy ứng dụng

```bash
# Compile
javac -d bin -cp "lib/*" src/com/library/main/Main.java

# Run
java -cp "bin:lib/*" com.library.main.Main
```

---

## 🔐 Tài khoản Mặc định

| Username | Password | Role |
|----------|----------|------|
| admin | 123456 | Admin |
| thuthu01 | 123456 | Librarian |

**⚠️ Lưu ý:** Đổi mật khẩu sau khi đăng nhập lần đầu!

---

## 📊 Chức năng Chính

### 1. Quản lý Sách
- ✅ Thêm/Sửa/Xóa sách
- ✅ Tìm kiếm theo tên, tác giả, thể loại
- ✅ Kiểm tra tồn kho

### 2. Quản lý Độc giả
- ✅ Đăng ký độc giả mới
- ✅ Gia hạn thẻ
- ✅ Xem lịch sử mượn

### 3. Mượn/Trả Sách
- ✅ Lập phiếu mượn
- ✅ Kiểm tra sách còn/hết
- ✅ Tự động tính tiền phạt (5000 VND/ngày)
- ✅ Cập nhật tồn kho tự động

### 4. Thống kê & Báo cáo
- ✅ Sách được mượn nhiều nhất
- ✅ Danh sách quá hạn
- ✅ Doanh thu tiền phạt

---

## 🎨 Giao diện (UI/UX)

- **Look and Feel:** FlatLaf (macOS/Windows 11 style)
- **Color Scheme:** Light/Dark mode support
- **Icons:** SVG icons (không vỡ ảnh)
- **Responsive:** Auto-resize components

---

## 🛠️ Công nghệ Sử dụng

| Công nghệ | Phiên bản | Mục đích |
|-----------|-----------|----------|
| Java | 17+ | Ngôn ngữ chính |
| Java Swing | Built-in | GUI Framework |
| FlatLaf | 3.2.5 | Modern Look and Feel |
| MySQL | 8.0+ | Database |
| JDBC | 8.0.33 | Database Connectivity |

---

## 📝 Quy ước Code

- **Naming Convention:** camelCase cho methods, PascalCase cho classes
- **Package Structure:** MVC pattern
- **Comments:** JavaDoc cho public methods
- **Encoding:** UTF-8 (hỗ trợ tiếng Việt)

---

## 🐛 Xử lý Lỗi thường gặp

### Lỗi kết nối Database:
```
Error: Communications link failure
```
**Giải pháp:** Kiểm tra MySQL service đang chạy

### Lỗi encoding tiếng Việt:
```
Error: Incorrect string value
```
**Giải pháp:** Thêm `?useUnicode=true&characterEncoding=UTF-8` vào connection string

### Lỗi FlatLaf không load:
```
Error: ClassNotFoundException: FlatLaf
```
**Giải pháp:** Kiểm tra JAR file trong classpath

---

## 👥 Phân quyền

| Quyền | Admin | Librarian |
|-------|-------|-----------|
| Quản lý sách | ✅ | ❌ |
| Quản lý độc giả | ✅ | ✅ |
| Mượn/Trả sách | ✅ | ✅ |
| Quản lý nhân viên | ✅ | ❌ |
| Thống kê | ✅ | ✅ (Read-only) |

---

## 📞 Hỗ trợ

- **Email:** library.support@example.com
- **GitHub:** [Repository Link]

---

## 📄 License

MIT License - Free to use for educational purposes.

---

**Developed with ❤️ by Library Management Team**
