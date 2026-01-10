# 🔨 Hướng dẫn Build & Deploy

## 📦 Đóng gói Project (Package)

### Bước 1: Clean & Compile
```bash
mvn clean compile
```
Lệnh này sẽ:
- ✅ Xóa folder `target/` cũ
- ✅ Compile toàn bộ source code
- ✅ Kiểm tra lỗi syntax

### Bước 2: Package thành JAR file
```bash
mvn package
```
Hoặc đầy đủ hơn:
```bash
mvn clean package
```

Kết quả: File JAR được tạo tại:
```
target/LibraryManagementSystem.jar
```

### Bước 3: Install vào local Maven repository
```bash
mvn clean install
```
Lệnh này sẽ:
- ✅ Clean project
- ✅ Compile source code
- ✅ Run tests (nếu có)
- ✅ Package thành JAR
- ✅ Install vào `~/.m2/repository/`

---

## 🚀 Chạy JAR file đã build

### Cách 1: Chạy trực tiếp
```bash
java -jar target/LibraryManagementSystem.jar
```

### Cách 2: Copy sang máy khác
1. Copy file `LibraryManagementSystem.jar` sang máy khác
2. Chạy lệnh:
```bash
java -jar LibraryManagementSystem.jar
```

**⚠️ Yêu cầu:**
- JDK/JRE 17 trở lên
- MySQL đang chạy với database `library_management`

---

## 📥 Triển khai trên máy mới (Fresh Install)

### Bước 1: Clone project
```bash
git clone [repository-url]
cd LibraryManagementSystem
```

### Bước 2: Cài đặt dependencies
```bash
mvn clean install
```
Maven sẽ tự động download:
- ✅ MySQL Connector/J 8.2.0
- ✅ FlatLaf 3.2.5
- ✅ JFreeChart 1.5.4
- ✅ Apache POI 5.2.5

### Bước 3: Cấu hình Database
1. Import database:
```bash
mysql -u root -p library_management < database/database.sql
mysql -u root -p library_management < database/test_data.sql
```

2. Cập nhật connection trong `src/com/library/util/DBConnection.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/library_management";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

### Bước 4: Build & Run
```bash
mvn clean package
java -jar target/LibraryManagementSystem.jar
```

---

## 🎯 Build Profiles (Optional)

### Development Profile
```bash
mvn clean package -Pdev
```
- Skip tests
- Fast build
- Debug logging

### Production Profile
```bash
mvn clean package -Pprod
```
- Run all tests
- Optimize JAR size
- Production logging

---

## 📊 Thông tin Build

| Command | Thời gian | Kích thước JAR | Mô tả |
|---------|-----------|----------------|-------|
| `mvn compile` | ~5s | - | Chỉ compile |
| `mvn package` | ~15s | ~30 MB | Tạo JAR với dependencies |
| `mvn install` | ~20s | ~30 MB | Package + Install local |
| `mvn clean install` | ~25s | ~30 MB | Clean + Full build |

---

## 🔧 Maven Shade Plugin

Plugin này tạo "Fat JAR" (Uber JAR) - JAR file chứa:
- ✅ Compiled classes của project
- ✅ Tất cả dependencies (MySQL, FlatLaf, JFreeChart...)
- ✅ META-INF/MANIFEST.MF với Main-Class

**Cấu hình trong pom.xml:**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <configuration>
        <transformers>
            <transformer>
                <mainClass>com.library.main.Main</mainClass>
            </transformer>
        </transformers>
        <finalName>LibraryManagementSystem</finalName>
    </configuration>
</plugin>
```

---

## 🐛 Troubleshooting Build Issues

### Lỗi: "Source option 17 is not supported"
**Giải pháp:**
```bash
# Check Java version
java -version

# Set JAVA_HOME
export JAVA_HOME=/path/to/jdk-17-or-later
mvn clean compile
```

### Lỗi: "Could not resolve dependencies"
**Giải pháp:**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Re-download dependencies
mvn clean install -U
```

### Lỗi: "Failed to execute goal maven-shade-plugin"
**Giải pháp:**
```bash
# Skip tests
mvn package -DskipTests

# Or update plugin version in pom.xml
```

### JAR file quá lớn (>50MB)
**Giải pháp:** Loại bỏ dependencies không cần thiết trong pom.xml

---

## 📁 Cấu trúc sau khi Build

```
target/
├── classes/                     # Compiled .class files
├── maven-archiver/              # Maven metadata
├── maven-status/                # Build status
├── LibraryManagementSystem.jar  # Fat JAR (30MB) ⭐
└── original-library-management-system-1.0.0.jar  # Thin JAR (chỉ project code)
```

**File cần dùng:** `LibraryManagementSystem.jar` (Fat JAR)

---

## 🚢 Deploy sang Server

### Docker (Optional)
```dockerfile
FROM openjdk:17-slim
COPY target/LibraryManagementSystem.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar"]
```

Build & Run:
```bash
docker build -t library-management .
docker run -p 8080:8080 library-management
```

---

## ✅ Checklist trước khi Deploy

- [ ] Database đã được import (schema + test data)
- [ ] DBConnection.java đã cấu hình đúng host/user/password
- [ ] JRE 17+ đã được cài đặt trên máy đích
- [ ] MySQL service đang chạy
- [ ] Port 3306 có thể truy cập
- [ ] `mvn clean package` thành công không lỗi
- [ ] Test run: `java -jar target/LibraryManagementSystem.jar`

---

**📝 Lưu ý quan trọng:**

1. **JAR file không chứa database** - Cần import schema riêng
2. **Cấu hình DB phải update** theo từng môi trường
3. **Không commit** file JAR vào Git (thêm vào .gitignore)
4. **Maven cache** lưu tại `~/.m2/repository/`

---

**Developed by Library Management Team**  
*Phạm Gia Khôi (Leader) | Võ Minh Tri | Trần Đăng Khoa | Trần Quyết Thắng | Nguyễn Hải Dương | Huỳnh Tuấn Kiệt*
