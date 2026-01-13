package com.library.util;

/**
 * Error Messages in Vietnamese
 * Centralized error message management for better UX
 */
public class ErrorMessages {
    
    // Database errors
    public static final String DB_CONNECTION_FAILED = "Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra kết nối.";
    public static final String DB_QUERY_FAILED = "Lỗi khi thực thi truy vấn cơ sở dữ liệu.";
    public static final String DB_INSERT_FAILED = "Không thể thêm dữ liệu vào cơ sở dữ liệu.";
    public static final String DB_UPDATE_FAILED = "Không thể cập nhật dữ liệu trong cơ sở dữ liệu.";
    public static final String DB_DELETE_FAILED = "Không thể xóa dữ liệu khỏi cơ sở dữ liệu.";
    
    // Validation errors
    public static final String FIELD_REQUIRED = "Trường này không được để trống.";
    public static final String INVALID_NUMBER = "Giá trị không hợp lệ. Vui lòng nhập số.";
    public static final String INVALID_EMAIL = "Địa chỉ email không hợp lệ.";
    public static final String INVALID_PHONE = "Số điện thoại không hợp lệ.";
    public static final String INVALID_DATE = "Ngày không hợp lệ.";
    public static final String INVALID_PRICE = "Giá tiền phải lớn hơn hoặc bằng 0.";
    public static final String INVALID_QUANTITY = "Số lượng phải lớn hơn 0.";
    
    // Book errors
    public static final String BOOK_NOT_FOUND = "Không tìm thấy sách với mã này.";
    public static final String BOOK_CODE_EXISTS = "Mã sách đã tồn tại trong hệ thống.";
    public static final String BOOK_OUT_OF_STOCK = "Sách này hiện đã hết trong kho.";
    public static final String BOOK_INSUFFICIENT_STOCK = "Số lượng sách trong kho không đủ.";
    public static final String BOOK_DELETE_FAILED = "Không thể xóa sách vì đang có phiếu mượn liên quan.";
    
    // Reader errors
    public static final String READER_NOT_FOUND = "Không tìm thấy độc giả với mã này.";
    public static final String READER_CODE_EXISTS = "Mã độc giả đã tồn tại trong hệ thống.";
    public static final String READER_INACTIVE = "Độc giả này đã bị vô hiệu hóa.";
    public static final String READER_HAS_OVERDUE_BOOKS = "Độc giả đang có sách quá hạn chưa trả.";
    public static final String READER_HAS_UNPAID_FINES = "Độc giả đang có tiền phạt chưa thanh toán.";
    public static final String READER_DELETE_FAILED = "Không thể xóa độc giả vì đang có phiếu mượn liên quan.";
    
    // Borrow errors
    public static final String BORROW_TICKET_NOT_FOUND = "Không tìm thấy phiếu mượn với mã này.";
    public static final String BORROW_LIMIT_EXCEEDED = "Độc giả đã vượt quá số lượng sách được phép mượn.";
    public static final String BORROW_BOOK_ALREADY_BORROWED = "Sách này đã được độc giả mượn trước đó.";
    public static final String BORROW_PERIOD_INVALID = "Thời gian mượn không hợp lệ.";
    public static final String BORROW_CREATE_FAILED = "Không thể tạo phiếu mượn mới.";
    
    // Return errors
    public static final String RETURN_TICKET_NOT_FOUND = "Không tìm thấy phiếu trả với mã này.";
    public static final String RETURN_BOOK_NOT_BORROWED = "Sách này chưa được mượn.";
    public static final String RETURN_ALREADY_RETURNED = "Sách này đã được trả trước đó.";
    public static final String RETURN_CREATE_FAILED = "Không thể tạo phiếu trả mới.";
    
    // Fine errors
    public static final String FINE_TICKET_NOT_FOUND = "Không tìm thấy phiếu phạt với mã này.";
    public static final String FINE_AMOUNT_INVALID = "Số tiền phạt không hợp lệ.";
    public static final String FINE_ALREADY_PAID = "Phiếu phạt này đã được thanh toán.";
    public static final String FINE_CREATE_FAILED = "Không thể tạo phiếu phạt mới.";
    
    // Staff errors
    public static final String STAFF_NOT_FOUND = "Không tìm thấy nhân viên với mã này.";
    public static final String STAFF_CODE_EXISTS = "Mã nhân viên đã tồn tại trong hệ thống.";
    public static final String STAFF_USERNAME_EXISTS = "Tên đăng nhập đã tồn tại trong hệ thống.";
    public static final String STAFF_INVALID_CREDENTIALS = "Tên đăng nhập hoặc mật khẩu không chính xác.";
    public static final String STAFF_NO_PERMISSION = "Bạn không có quyền thực hiện thao tác này.";
    public static final String STAFF_DELETE_FAILED = "Không thể xóa nhân viên này.";
    
    // Generic errors
    public static final String OPERATION_FAILED = "Không thể thực hiện thao tác này.";
    public static final String SELECTION_REQUIRED = "Vui lòng chọn một mục để thực hiện thao tác.";
    public static final String NO_DATA_FOUND = "Không tìm thấy dữ liệu.";
    public static final String EXPORT_FAILED = "Không thể xuất dữ liệu.";
    public static final String IMPORT_FAILED = "Không thể nhập dữ liệu.";
    public static final String FILE_NOT_FOUND = "Không tìm thấy tệp.";
    public static final String FILE_READ_ERROR = "Không thể đọc tệp.";
    public static final String FILE_WRITE_ERROR = "Không thể ghi tệp.";
    
    // Success messages
    public static final String OPERATION_SUCCESS = "Thao tác thực hiện thành công!";
    public static final String SAVE_SUCCESS = "Lưu dữ liệu thành công!";
    public static final String DELETE_SUCCESS = "Xóa dữ liệu thành công!";
    public static final String UPDATE_SUCCESS = "Cập nhật dữ liệu thành công!";
    public static final String EXPORT_SUCCESS = "Xuất dữ liệu thành công!";
    public static final String IMPORT_SUCCESS = "Nhập dữ liệu thành công!";
    
    // Confirmation messages
    public static final String CONFIRM_DELETE = "Bạn có chắc chắn muốn xóa?";
    public static final String CONFIRM_LOGOUT = "Bạn có chắc chắn muốn đăng xuất?";
    public static final String CONFIRM_CANCEL = "Bạn có chắc chắn muốn hủy? Dữ liệu chưa lưu sẽ bị mất.";
    
    /**
     * Format error message with details
     */
    public static String formatError(String baseMessage, String details) {
        return baseMessage + "\n\nChi tiết: " + details;
    }
    
    /**
     * Get database error message with SQL exception details
     */
    public static String getDatabaseError(Exception e) {
        String message = e.getMessage();
        if (message == null || message.isEmpty()) {
            return DB_QUERY_FAILED;
        }
        
        // Translate common SQL errors
        if (message.contains("Duplicate entry")) {
            return "Dữ liệu đã tồn tại trong hệ thống.";
        } else if (message.contains("foreign key constraint")) {
            return "Không thể xóa dữ liệu vì có dữ liệu liên quan khác.";
        } else if (message.contains("cannot be null")) {
            return "Thiếu thông tin bắt buộc.";
        } else if (message.contains("Data truncation")) {
            return "Dữ liệu quá dài hoặc không đúng định dạng.";
        }
        
        return formatError(DB_QUERY_FAILED, message);
    }
}
