package com.library.util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.model.Reader;

/**
 * Utility class to generate sample Excel files for testing
 * Run this class to create sample Excel files
 */
public class SampleExcelGenerator {
    
    public static void main(String[] args) {
        System.out.println("=== Tạo File Excel Mẫu ===\n");
        
        try {
            // Generate sample books Excel
            generateSampleBooksExcel();
            
            // Generate sample readers Excel
            generateSampleReadersExcel();
            
            System.out.println("\n✅ Hoàn thành! Kiểm tra thư mục samples/");
            
        } catch (IOException e) {
            System.err.println("❌ Lỗi khi tạo file: " + e.getMessage());
            System.err.println("Chi tiết lỗi: " + e.getClass().getSimpleName());
        }
    }
    
    /**
     * Generate sample books Excel file
     */
    private static void generateSampleBooksExcel() throws IOException {
        List<Book> sampleBooks = new ArrayList<>();
        
        // Create sample books
        sampleBooks.add(new Book("S001", "Lập Trình Java Cơ Bản", "TG001", "TL001", "NXB001", 
                                  2023, 10, 150000.0, "K1-Ke1-A1"));
        sampleBooks.add(new Book("S002", "Cơ Sở Dữ Liệu MySQL", "TG002", "TL001", "NXB002", 
                                  2022, 5, 200000.0, "K1-Ke1-A2"));
        sampleBooks.add(new Book("S003", "Thuật Toán Lập Trình", "TG003", "TL001", "NXB001", 
                                  2024, 8, 180000.0, "K1-Ke2-A1"));
        sampleBooks.add(new Book("S004", "Lập Trình Python Nâng Cao", "TG004", "TL001", "NXB003", 
                                  2023, 12, 175000.0, "K1-Ke2-A2"));
        sampleBooks.add(new Book("S005", "Trí Tuệ Nhân Tạo", "TG005", "TL002", "NXB001", 
                                  2024, 6, 250000.0, "K2-Ke1-A1"));
        sampleBooks.add(new Book("S006", "Machine Learning Cơ Bản", "TG005", "TL002", "NXB002", 
                                  2023, 7, 220000.0, "K2-Ke1-A2"));
        sampleBooks.add(new Book("S007", "Lập Trình Web với React", "TG006", "TL001", "NXB003", 
                                  2024, 9, 190000.0, "K2-Ke2-A1"));
        sampleBooks.add(new Book("S008", "Node.js Backend Development", "TG007", "TL001", "NXB002", 
                                  2023, 11, 210000.0, "K2-Ke2-A2"));
        sampleBooks.add(new Book("S009", "Data Science với R", "TG008", "TL002", "NXB001", 
                                  2024, 5, 240000.0, "K3-Ke1-A1"));
        sampleBooks.add(new Book("S010", "Blockchain và Cryptocurrency", "TG009", "TL002", "NXB003", 
                                  2023, 8, 280000.0, "K3-Ke1-A2"));
        
        String outputPath = "samples/DanhSachSach_Mau.xlsx";
        ExcelUtil.exportBooksToExcel(sampleBooks, outputPath);
        System.out.println("✓ Đã tạo file: " + outputPath);
    }
    
    /**
     * Generate sample readers Excel file
     */
    private static void generateSampleReadersExcel() throws IOException {
        List<Reader> sampleReaders = new ArrayList<>();
        
        // Create sample readers
        sampleReaders.add(new Reader("DG001", "Nguyễn Văn An", 
                LocalDate.of(1995, 3, 15), "Nam", "0901234567", "nva@email.com",
                "123 Đường Lê Lợi, Quận 1, TP.HCM",
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG002", "Trần Thị Bình", 
                LocalDate.of(1998, 7, 20), "Nữ", "0987654321", "ttb@email.com",
                "456 Đường Trần Hưng Đạo, Quận Hoàn Kiếm, Hà Nội",
                LocalDate.of(2024, 2, 15), LocalDate.of(2025, 2, 15), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG003", "Lê Văn Cường", 
                LocalDate.of(1992, 12, 10), "Nam", "0912345678", "lvc@email.com",
                "789 Đường Nguyễn Huệ, Quận Hải Châu, Đà Nẵng",
                LocalDate.of(2024, 3, 20), LocalDate.of(2025, 3, 20), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG004", "Phạm Thị Dung", 
                LocalDate.of(1996, 5, 5), "Nữ", "0923456789", "ptd@email.com",
                "321 Đường Lý Thường Kiệt, Quận 10, TP.HCM",
                LocalDate.of(2024, 1, 10), LocalDate.of(2025, 1, 10), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG005", "Hoàng Văn Em", 
                LocalDate.of(1994, 9, 18), "Nam", "0934567890", "hve@email.com",
                "654 Đường Hai Bà Trưng, Quận 3, TP.HCM",
                LocalDate.of(2024, 2, 25), LocalDate.of(2025, 2, 25), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG006", "Vũ Thị Phương", 
                LocalDate.of(1997, 11, 22), "Nữ", "0945678901", "vtp@email.com",
                "987 Đường Điện Biên Phủ, Quận Ba Đình, Hà Nội",
                LocalDate.of(2024, 3, 5), LocalDate.of(2025, 3, 5), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG007", "Đặng Văn Giang", 
                LocalDate.of(1993, 1, 30), "Nam", "0956789012", "dvg@email.com",
                "147 Đường Võ Văn Tần, Quận 3, TP.HCM",
                LocalDate.of(2024, 1, 12), LocalDate.of(2025, 1, 12), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG008", "Bùi Thị Hà", 
                LocalDate.of(1999, 6, 14), "Nữ", "0967890123", "bth@email.com",
                "258 Đường Nguyễn Thị Minh Khai, Quận 1, TP.HCM",
                LocalDate.of(2024, 2, 18), LocalDate.of(2025, 2, 18), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG009", "Ngô Văn Ích", 
                LocalDate.of(1991, 4, 8), "Nam", "0978901234", "nvi@email.com",
                "369 Đường Lạc Long Quân, Quận 11, TP.HCM",
                LocalDate.of(2024, 3, 22), LocalDate.of(2025, 3, 22), "Hoạt động"));
        
        sampleReaders.add(new Reader("DG010", "Trương Thị Kim", 
                LocalDate.of(2000, 8, 25), "Nữ", "0989012345", "ttk@email.com",
                "741 Đường Cách Mạng Tháng 8, Quận Tân Bình, TP.HCM",
                LocalDate.of(2024, 1, 28), LocalDate.of(2025, 1, 28), "Hoạt động"));
        
        String outputPath = "samples/DanhSachDocGia_Mau.xlsx";
        ExcelUtil.exportReadersToExcel(sampleReaders, outputPath);
        System.out.println("✓ Đã tạo file: " + outputPath);
    }
}
