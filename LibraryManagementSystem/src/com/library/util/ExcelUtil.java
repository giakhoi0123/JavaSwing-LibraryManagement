package com.library.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.library.model.Book;

/**
 * Excel/CSV Export/Import Utility
 * Supports both .xlsx (Excel) and .csv formats
 */
public class ExcelUtil {
    
    private static final DateTimeFormatter VN_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter ISO_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Export books to Excel file
     */
    public static void exportBooksToExcel(List<Book> books, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh Sách Sách");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Mã Sách", "Tên Sách", "Mã Tác Giả", "Mã Thể Loại", "Mã NXB", 
                           "Năm XB", "Số Lượng", "Đơn Giá", "Vị Trí", "Trạng Thái"};
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 4000);
        }
        
        // Fill data rows
        int rowNum = 1;
        for (Book book : books) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(book.getMaSach());
            row.createCell(1).setCellValue(book.getTenSach());
            row.createCell(2).setCellValue(book.getMaTG() != null ? book.getMaTG() : "");
            row.createCell(3).setCellValue(book.getMaTheLoai() != null ? book.getMaTheLoai() : "");
            row.createCell(4).setCellValue(book.getMaNXB() != null ? book.getMaNXB() : "");
            row.createCell(5).setCellValue(book.getNamXB());
            row.createCell(6).setCellValue(book.getSoLuong());
            row.createCell(7).setCellValue(book.getDonGia());
            row.createCell(8).setCellValue(book.getViTri() != null ? book.getViTri() : "");
            row.createCell(9).setCellValue(book.getTrangThai() != null ? book.getTrangThai() : "");
        }
        
        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
    
    /**
     * Import books from Excel or CSV file
     * Automatically detects format based on file extension
     */
    public static List<Book> importBooksFromExcel(String filePath) throws IOException {
        if (filePath.toLowerCase().endsWith(".csv")) {
            return importBooksFromCSV(filePath);
        }
        return importBooksFromXlsx(filePath);
    }
    
    /**
     * Import books from XLSX file
     */
    private static List<Book> importBooksFromXlsx(String filePath) throws IOException {
        List<Book> books = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            // Skip header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Book book = new Book();
                book.setMaSach(getCellValueAsString(row.getCell(0)));
                book.setTenSach(getCellValueAsString(row.getCell(1)));
                book.setMaTG(getCellValueAsString(row.getCell(2)));
                book.setMaTheLoai(getCellValueAsString(row.getCell(3)));
                book.setMaNXB(getCellValueAsString(row.getCell(4)));
                book.setNamXB((int) getCellValueAsDouble(row.getCell(5)));
                book.setSoLuong((int) getCellValueAsDouble(row.getCell(6)));
                book.setDonGia(getCellValueAsDouble(row.getCell(7)));
                book.setViTri(getCellValueAsString(row.getCell(8)));
                book.setTrangThai(mapBookTrangThai(getCellValueAsString(row.getCell(9))));
                
                books.add(book);
            }
        }
        
        return books;
    }
    
    /**
     * Import books from CSV file
     */
    private static List<Book> importBooksFromCSV(String filePath) throws IOException {
        List<Book> books = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            
            String line = br.readLine(); // Skip header
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] fields = parseCSVLine(line);
                if (fields.length < 8) continue;
                
                Book book = new Book();
                book.setMaSach(fields[0].trim());
                book.setTenSach(fields[1].trim());
                book.setMaTG(fields.length > 2 ? fields[2].trim() : "");
                book.setMaTheLoai(fields.length > 3 ? fields[3].trim() : "");
                book.setMaNXB(fields.length > 4 ? fields[4].trim() : "");
                book.setNamXB(parseIntSafe(fields.length > 5 ? fields[5].trim() : "0"));
                book.setSoLuong(parseIntSafe(fields.length > 6 ? fields[6].trim() : "0"));
                book.setDonGia(parseDoubleSafe(fields.length > 7 ? fields[7].trim() : "0"));
                book.setViTri(fields.length > 8 ? fields[8].trim() : "");
                book.setTrangThai(mapBookTrangThai(fields.length > 9 ? fields[9].trim() : ""));
                
                if (!book.getMaSach().isEmpty() && !book.getTenSach().isEmpty()) {
                    books.add(book);
                }
            }
        }
        
        return books;
    }
    
    /**
     * Export readers to Excel file
     */
    public static void exportReadersToExcel(List<com.library.model.Reader> readers, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh Sách Độc Giả");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Mã ĐG", "Họ Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Email", 
                           "Địa Chỉ", "Ngày Lập Thẻ", "Trạng Thái"};
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 4000);
        }
        
        // Fill data rows
        int rowNum = 1;
        for (com.library.model.Reader reader : readers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(reader.getMaDG());
            row.createCell(1).setCellValue(reader.getHoTen());
            row.createCell(2).setCellValue(reader.getNgaySinh() != null ? reader.getNgaySinh().toString() : "");
            row.createCell(3).setCellValue(reader.getGioiTinh());
            row.createCell(4).setCellValue(reader.getSoDT());
            row.createCell(5).setCellValue(reader.getEmail() != null ? reader.getEmail() : "");
            row.createCell(6).setCellValue(reader.getDiaChi() != null ? reader.getDiaChi() : "");
            row.createCell(7).setCellValue(reader.getNgayLapThe() != null ? reader.getNgayLapThe().toString() : "");
            row.createCell(8).setCellValue(reader.getTrangThai() != null ? reader.getTrangThai() : "");
        }
        
        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
    
    /**
     * Import readers from Excel or CSV file
     * Automatically detects format based on file extension
     */
    public static List<com.library.model.Reader> importReadersFromExcel(String filePath) throws IOException {
        if (filePath.toLowerCase().endsWith(".csv")) {
            return importReadersFromCSV(filePath);
        }
        return importReadersFromXlsx(filePath);
    }
    
    /**
     * Import readers from XLSX file
     */
    private static List<com.library.model.Reader> importReadersFromXlsx(String filePath) throws IOException {
        List<com.library.model.Reader> readers = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            // Skip header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                com.library.model.Reader reader = new com.library.model.Reader();
                reader.setMaDG(getCellValueAsString(row.getCell(0)));
                reader.setHoTen(getCellValueAsString(row.getCell(1)));
                
                String dateStr = getCellValueAsString(row.getCell(2));
                if (!dateStr.isEmpty()) {
                    reader.setNgaySinh(parseDateFlexible(dateStr));
                }
                
                reader.setGioiTinh(getCellValueAsString(row.getCell(3)));
                reader.setSoDT(getCellValueAsString(row.getCell(4)));
                reader.setEmail(getCellValueAsString(row.getCell(5)));
                reader.setDiaChi(getCellValueAsString(row.getCell(6)));
                
                String regDateStr = getCellValueAsString(row.getCell(7));
                if (!regDateStr.isEmpty()) {
                    reader.setNgayLapThe(parseDateFlexible(regDateStr));
                }
                
                // Check if column 8 is NgayHetHan or TrangThai
                String col8 = getCellValueAsString(row.getCell(8));
                String col9 = row.getCell(9) != null ? getCellValueAsString(row.getCell(9)) : "";
                
                if (!col9.isEmpty()) {
                    // 10-column format: col8=NgayHetHan, col9=TrangThai
                    if (!col8.isEmpty()) {
                        reader.setNgayHetHan(parseDateFlexible(col8));
                    }
                    reader.setTrangThai(mapReaderTrangThai(col9));
                } else {
                    // 9-column format: col8=TrangThai
                    reader.setTrangThai(mapReaderTrangThai(col8));
                }
                
                readers.add(reader);
            }
        }
        
        return readers;
    }
    
    /**
     * Import readers from CSV file
     */
    private static List<com.library.model.Reader> importReadersFromCSV(String filePath) throws IOException {
        List<com.library.model.Reader> readers = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            
            String headerLine = br.readLine(); // Read header to detect format
            if (headerLine == null) return readers;
            
            String[] headers = parseCSVLine(headerLine);
            boolean hasNgayHetHan = headers.length >= 10;
            
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] fields = parseCSVLine(line);
                if (fields.length < 7) continue;
                
                com.library.model.Reader reader = new com.library.model.Reader();
                reader.setMaDG(fields[0].trim());
                reader.setHoTen(fields[1].trim());
                
                if (fields.length > 2 && !fields[2].trim().isEmpty()) {
                    reader.setNgaySinh(parseDateFlexible(fields[2].trim()));
                }
                
                reader.setGioiTinh(fields.length > 3 ? fields[3].trim() : "");
                reader.setSoDT(fields.length > 4 ? fields[4].trim() : "");
                reader.setEmail(fields.length > 5 ? fields[5].trim() : "");
                reader.setDiaChi(fields.length > 6 ? fields[6].trim() : "");
                
                if (hasNgayHetHan) {
                    // 10-column CSV: ..., NgayLapThe, NgayHetHan, TrangThai
                    if (fields.length > 7 && !fields[7].trim().isEmpty()) {
                        reader.setNgayLapThe(parseDateFlexible(fields[7].trim()));
                    }
                    if (fields.length > 8 && !fields[8].trim().isEmpty()) {
                        reader.setNgayHetHan(parseDateFlexible(fields[8].trim()));
                    }
                    reader.setTrangThai(mapReaderTrangThai(fields.length > 9 ? fields[9].trim() : ""));
                } else {
                    // 9-column CSV: ..., NgayLapThe, TrangThai
                    if (fields.length > 7 && !fields[7].trim().isEmpty()) {
                        reader.setNgayLapThe(parseDateFlexible(fields[7].trim()));
                    }
                    reader.setTrangThai(mapReaderTrangThai(fields.length > 8 ? fields[8].trim() : ""));
                }
                
                if (!reader.getMaDG().isEmpty() && !reader.getHoTen().isEmpty()) {
                    readers.add(reader);
                }
            }
        }
        
        return readers;
    }
    
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
    
    private static double getCellValueAsDouble(Cell cell) {
        if (cell == null) return 0.0;
        
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }
    
    /**
     * Parse a CSV line handling quoted fields with commas
     */
    private static String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString().trim());
        
        return fields.toArray(new String[0]);
    }
    
    /**
     * Parse date string flexibly - supports dd/MM/yyyy, yyyy-MM-dd, and other formats
     */
    private static LocalDate parseDateFlexible(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        dateStr = dateStr.trim();
        
        // Try dd/MM/yyyy format (Vietnamese)
        try {
            return LocalDate.parse(dateStr, VN_DATE_FORMAT);
        } catch (DateTimeParseException ignored) {}
        
        // Try yyyy-MM-dd format (ISO)
        try {
            return LocalDate.parse(dateStr, ISO_DATE_FORMAT);
        } catch (DateTimeParseException ignored) {}
        
        // Try ISO default
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException ignored) {}
        
        System.err.println("Không thể parse ngày: " + dateStr);
        return null;
    }
    
    /**
     * Map Vietnamese book status to DB ENUM value
     * DB: ENUM('Active', 'Inactive')
     */
    private static String mapBookTrangThai(String value) {
        if (value == null || value.trim().isEmpty()) return "Active";
        String v = value.trim().toLowerCase();
        if (v.equals("active") || v.equals("còn") || v.equals("con") || v.equals("có sẵn") || v.equals("hoạt động")) return "Active";
        if (v.equals("inactive") || v.equals("hết") || v.equals("het") || v.equals("không hoạt động")) return "Inactive";
        // If already a valid ENUM value, return as-is
        if (v.equals("active") || v.equals("inactive")) return value.trim();
        return "Active"; // default
    }
    
    /**
     * Map Vietnamese reader status to DB ENUM value
     * DB: ENUM('Active', 'Inactive', 'Suspended')
     */
    private static String mapReaderTrangThai(String value) {
        if (value == null || value.trim().isEmpty()) return "Active";
        String v = value.trim().toLowerCase();
        if (v.equals("active") || v.equals("hoạt động") || v.equals("hoat dong") || v.equals("còn") || v.equals("con")) return "Active";
        if (v.equals("inactive") || v.equals("không hoạt động") || v.equals("khong hoat dong") || v.equals("hết hạn") || v.equals("het han")) return "Inactive";
        if (v.equals("suspended") || v.equals("tạm dừng") || v.equals("tam dung") || v.equals("tạm khóa") || v.equals("bị khóa")) return "Suspended";
        return "Active"; // default
    }
    
    /**
     * Safe integer parsing
     */
    private static int parseIntSafe(String str) {
        if (str == null || str.trim().isEmpty()) return 0;
        try {
            // Handle decimal numbers like "150000.0"
            return (int) Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Safe double parsing
     */
    private static double parseDoubleSafe(String str) {
        if (str == null || str.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
