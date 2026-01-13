package com.library.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
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
 * Excel Export/Import Utility
 */
public class ExcelUtil {
    
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
     * Import books from Excel file
     */
    public static List<Book> importBooksFromExcel(String filePath) throws IOException {
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
                book.setTrangThai(getCellValueAsString(row.getCell(9)));
                
                books.add(book);
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
     * Import readers from Excel file
     */
    public static List<com.library.model.Reader> importReadersFromExcel(String filePath) throws IOException {
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
                    reader.setNgaySinh(LocalDate.parse(dateStr));
                }
                
                reader.setGioiTinh(getCellValueAsString(row.getCell(3)));
                reader.setSoDT(getCellValueAsString(row.getCell(4)));
                reader.setEmail(getCellValueAsString(row.getCell(5)));
                reader.setDiaChi(getCellValueAsString(row.getCell(6)));
                
                String regDateStr = getCellValueAsString(row.getCell(7));
                if (!regDateStr.isEmpty()) {
                    reader.setNgayLapThe(LocalDate.parse(regDateStr));
                }
                
                reader.setTrangThai(getCellValueAsString(row.getCell(8)));
                
                readers.add(reader);
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
}
