package com.library.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.library.model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * PDF Export Utility
 */
public class PDFUtil {
    
    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    
    /**
     * Export books to PDF file
     */
    public static void exportBooksToPDF(List<Book> books, String filePath) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        
        document.open();
        
        // Title
        Paragraph title = new Paragraph("DANH SÁCH SÁCH", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // Create table
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.5f, 3, 1.5f, 1.5f, 1.5f, 1, 1, 1.5f, 1.5f});
        
        // Header
        addTableHeader(table, new String[]{"Mã Sách", "Tên Sách", "Mã TG", "Mã TL", "Mã NXB", 
                                           "Năm XB", "SL", "Đơn Giá", "Vị Trí"});
        
        // Data rows
        for (Book book : books) {
            addTableCell(table, book.getMaSach());
            addTableCell(table, book.getTenSach());
            addTableCell(table, book.getMaTG() != null ? book.getMaTG() : "");
            addTableCell(table, book.getMaTheLoai() != null ? book.getMaTheLoai() : "");
            addTableCell(table, book.getMaNXB() != null ? book.getMaNXB() : "");
            addTableCell(table, String.valueOf(book.getNamXB()));
            addTableCell(table, String.valueOf(book.getSoLuong()));
            addTableCell(table, String.format("%,.0f", book.getDonGia()));
            addTableCell(table, book.getViTri() != null ? book.getViTri() : "");
        }
        
        document.add(table);
        
        // Footer
        Paragraph footer = new Paragraph(
            "\nTổng số: " + books.size() + " sách\nNgày xuất: " + java.time.LocalDate.now(), 
            NORMAL_FONT
        );
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setSpacingBefore(10);
        document.add(footer);
        
        document.close();
    }
    
    /**
     * Export readers to PDF file
     */
    public static void exportReadersToPDF(List<Reader> readers, String filePath) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        
        document.open();
        
        // Title
        Paragraph title = new Paragraph("DANH SÁCH ĐỘC GIẢ", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // Create table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.5f, 3, 1.5f, 1, 2, 2, 1.5f});
        
        // Header
        addTableHeader(table, new String[]{"Mã ĐG", "Họ Tên", "Ngày Sinh", "GT", "SĐT", "Email", "Trạng Thái"});
        
        // Data rows
        for (Reader reader : readers) {
            addTableCell(table, reader.getMaDG());
            addTableCell(table, reader.getHoTen());
            addTableCell(table, reader.getNgaySinh() != null ? reader.getNgaySinh().toString() : "");
            addTableCell(table, reader.getGioiTinh());
            addTableCell(table, reader.getSoDT());
            addTableCell(table, reader.getEmail() != null ? reader.getEmail() : "");
            addTableCell(table, reader.getTrangThai() != null ? reader.getTrangThai() : "");
        }
        
        document.add(table);
        
        // Footer
        Paragraph footer = new Paragraph(
            "\nTổng số: " + readers.size() + " độc giả\nNgày xuất: " + java.time.LocalDate.now(), 
            NORMAL_FONT
        );
        footer.setAlignment(Element.ALIGN_RIGHT);
        footer.setSpacingBefore(10);
        document.add(footer);
        
        document.close();
    }
    
    /**
     * Export borrow ticket to PDF
     */
    public static void exportBorrowTicketToPDF(BorrowTicket ticket, List<BorrowDetail> details, 
                                               String filePath) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        
        document.open();
        
        // Title
        Paragraph title = new Paragraph("PHIẾU MƯỢN SÁCH", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // Ticket info
        document.add(new Paragraph("Mã phiếu mượn: " + ticket.getMaPM(), NORMAL_FONT));
        document.add(new Paragraph("Mã độc giả: " + ticket.getMaDG(), NORMAL_FONT));
        document.add(new Paragraph("Ngày mượn: " + ticket.getNgayMuon(), NORMAL_FONT));
        document.add(new Paragraph("Ngày hẹn trả: " + ticket.getNgayHenTra(), NORMAL_FONT));
        document.add(new Paragraph(" ", NORMAL_FONT));
        
        // Book details table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 4, 1});
        
        addTableHeader(table, new String[]{"Mã Sách", "Tên Sách", "Số Lượng"});
        
        for (BorrowDetail detail : details) {
            addTableCell(table, detail.getMaSach());
            addTableCell(table, detail.getTenSach() != null ? detail.getTenSach() : "");
            addTableCell(table, String.valueOf(detail.getSoLuongMuon()));
        }
        
        document.add(table);
        
        // Footer
        Paragraph footer = new Paragraph("\nNhân viên lập: " + ticket.getMaNV(), NORMAL_FONT);
        footer.setSpacingBefore(20);
        document.add(footer);
        
        document.close();
    }
    
    private static void addTableHeader(PdfPTable table, String[] headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }
    
    private static void addTableCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, NORMAL_FONT));
        cell.setPadding(3);
        table.addCell(cell);
    }
}
