# Hướng dẫn Cài đặt Thư viện (Dependencies)

## Yêu cầu

- **JDK 17+** đã cài đặt
- **MySQL 8.0+** đã cài đặt và đang chạy
- **IDE**: IntelliJ IDEA / Eclipse / NetBeans (khuyến nghị IntelliJ IDEA)

---

## Cách 1: Sử dụng Maven (Khuyến nghị)

### Bước 1: Tạo file `pom.xml` trong thư mục gốc project

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.library</groupId>
    <artifactId>library-management-system</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <name>Library Management System</name>
    <description>Library Management System with Java Swing and FlatLaf</description>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <dependencies>
        <!-- MySQL Connector/J -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.2.0</version>
        </dependency>
        
        <!-- FlatLaf - Modern Look and Feel -->
        <dependency>
            <groupId>com.formdev</groupId>
            <artifactId>flatlaf</artifactId>
            <version>3.2.5</version>
        </dependency>
        
        <!-- FlatLaf Extras (SVG Icons, Themes) -->
        <dependency>
            <groupId>com.formdev</groupId>
            <artifactId>flatlaf-extras</artifactId>
            <version>3.2.5</version>
        </dependency>
        
        <!-- FlatLaf IntelliJ Themes -->
        <dependency>
            <groupId>com.formdev</groupId>
            <artifactId>flatlaf-intellij-themes</artifactId>
            <version>3.2.5</version>
        </dependency>
        
        <!-- JFreeChart (Optional - for charts) -->
        <dependency>
            <groupId>org.jfree</groupId>
            <artifactId>jfreechart</artifactId>
            <version>1.5.4</version>
        </dependency>
        
        <!-- Apache POI (Optional - for Excel export) -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.5</version>
        </dependency>
    </dependencies>
    
    <build>
        <sourceDirectory>src</sourceDirectory>
        <plugins>
            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            
            <!-- Maven Shade Plugin (Create Fat JAR) -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.library.main.Main</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### Bước 2: Import project vào IntelliJ IDEA

1. Mở IntelliJ IDEA
2. File → Open → Chọn thư mục project
3. IntelliJ sẽ tự động nhận diện `pom.xml` và tải dependencies
4. Chờ Maven tải xong các thư viện

### Bước 3: Chạy ứng dụng

```bash
# Compile
mvn clean compile

# Run
mvn exec:java -Dexec.mainClass="com.library.main.Main"

# Package (tạo JAR file)
mvn clean package
```

Sau khi package, file JAR sẽ nằm trong `target/library-management-system-1.0.0.jar`

```bash
# Chạy JAR file
java -jar target/library-management-system-1.0.0.jar
```

---

## Cách 2: Tải Thủ công (Manual Download)

### Bước 1: Tải các file JAR

Tải các file sau và lưu vào thư mục `lib/`:

1. **MySQL Connector/J**
   - Link: https://dev.mysql.com/downloads/connector/j/
   - File: `mysql-connector-j-8.2.0.jar`

2. **FlatLaf**
   - Link: https://www.formdev.com/flatlaf/
   - Files:
     - `flatlaf-3.2.5.jar`
     - `flatlaf-extras-3.2.5.jar`
     - `flatlaf-intellij-themes-3.2.5.jar`

### Bước 2: Thêm JAR files vào project

#### Trong IntelliJ IDEA:

1. File → Project Structure (Ctrl+Alt+Shift+S)
2. Modules → Dependencies
3. Click `+` → JARs or directories
4. Chọn tất cả file JAR trong thư mục `lib/`
5. Click OK

#### Trong Eclipse:

1. Right-click vào project → Properties
2. Java Build Path → Libraries
3. Add JARs / Add External JARs
4. Chọn tất cả file JAR trong thư mục `lib/`
5. Click Apply and Close

### Bước 3: Compile và chạy

#### Command Line:

```bash
# Compile
javac -d bin -cp "lib/*" src/com/library/main/Main.java src/com/library/**/*.java

# Run
java -cp "bin:lib/*" com.library.main.Main

# (Windows: thay : bằng ;)
java -cp "bin;lib/*" com.library.main.Main
```

---

## Cách 3: Sử dụng Gradle (Alternative)

### Tạo file `build.gradle`

```gradle
plugins {
    id 'java'
    id 'application'
}

group = 'com.library'
version = '1.0.0'
sourceCompatibility = '17'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.mysql:mysql-connector-j:8.2.0'
    implementation 'com.formdev:flatlaf:3.2.5'
    implementation 'com.formdev:flatlaf-extras:3.2.5'
    implementation 'com.formdev:flatlaf-intellij-themes:3.2.5'
    implementation 'org.jfree:jfreechart:1.5.4'
    implementation 'org.apache.poi:poi-ooxml:5.2.5'
}

application {
    mainClass = 'com.library.main.Main'
}

sourceSets {
    main {
        java {
            srcDirs = ['src']
        }
    }
}

tasks.named('jar') {
    manifest {
        attributes 'Main-Class': 'com.library.main.Main'
    }
}
```

### Chạy với Gradle:

```bash
# Run
./gradlew run

# Build JAR
./gradlew jar
```

---

## Kiểm tra Cài đặt

Tạo file test `TestSetup.java`:

```java
public class TestSetup {
    public static void main(String[] args) {
        System.out.println("Testing dependencies...");
        
        // Test MySQL Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL Driver OK");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL Driver NOT FOUND");
        }
        
        // Test FlatLaf
        try {
            Class.forName("com.formdev.flatlaf.FlatLightLaf");
            System.out.println("✓ FlatLaf OK");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ FlatLaf NOT FOUND");
        }
        
        System.out.println("\nAll tests completed!");
    }
}
```

Chạy:
```bash
javac -cp "lib/*" TestSetup.java
java -cp ".:lib/*" TestSetup
```

Kết quả mong đợi:
```
Testing dependencies...
✓ MySQL Driver OK
✓ FlatLaf OK

All tests completed!
```

---

## Troubleshooting

### Lỗi: ClassNotFoundException: com.mysql.cj.jdbc.Driver

**Nguyên nhân:** MySQL Connector chưa được thêm vào classpath

**Giải pháp:**
- Kiểm tra file `mysql-connector-j-8.2.0.jar` có trong `lib/`
- Thêm lại JAR vào project dependencies
- Nếu dùng Maven, chạy `mvn clean install`

### Lỗi: UnsupportedLookAndFeelException

**Nguyên nhân:** FlatLaf chưa được cài đặt đúng

**Giải pháp:**
- Kiểm tra các file FlatLaf JAR trong `lib/`
- Đảm bảo version tương thích (khuyến nghị 3.2.5)

### Lỗi: Communications link failure

**Nguyên nhân:** MySQL server không chạy hoặc sai thông tin kết nối

**Giải pháp:**
- Kiểm tra MySQL service: `systemctl status mysql` (Linux) hoặc Task Manager (Windows)
- Kiểm tra `DBConnection.java`:
  - URL: `jdbc:mysql://localhost:3306/library_management`
  - Username: `root`
  - Password: (your password)

---

## Phát triển với IDE

### IntelliJ IDEA (Khuyến nghị)

**Ưu điểm:**
- Hỗ trợ Maven/Gradle tốt nhất
- Auto-import dependencies
- Code completion mạnh
- Integrated database tools

**Cấu hình:**
1. File → Settings → Build, Execution, Deployment → Compiler
2. Check "Build project automatically"
3. File → Settings → Editor → File Encodings → UTF-8

### Eclipse

**Plugin cần thiết:**
- Maven Integration for Eclipse (m2e)
- Eclipse Java EE Developer Tools

### NetBeans

**Ưu điểm:**
- GUI Builder tích hợp sẵn
- Dễ dùng cho Swing development

---

## Xuất File Thực thi (Executable JAR)

### Sử dụng Maven:

```bash
mvn clean package
```

File output: `target/library-management-system-1.0.0.jar`

### Chạy JAR file:

```bash
java -jar target/library-management-system-1.0.0.jar
```

### Tạo Windows EXE (Optional):

Sử dụng **Launch4j**:

1. Tải Launch4j: http://launch4j.sourceforge.net/
2. Cấu hình:
   - Output file: `LibraryManagement.exe`
   - Jar: `library-management-system-1.0.0.jar`
   - Min JRE version: 17
3. Build

---

**Chúc bạn cài đặt thành công! 🎉**

Nếu gặp vấn đề, vui lòng kiểm tra:
- JDK version: `java -version` (phải >= 17)
- Maven version: `mvn -version` (nếu dùng Maven)
- MySQL status: `mysql -u root -p`
