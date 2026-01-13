package com.library.model;

/**
 * Permission/Role Entity for Staff
 * Defines what actions each staff member can perform
 */
public class Permission {
    
    // Permission types
    public static final String MANAGE_BOOKS = "MANAGE_BOOKS";
    public static final String MANAGE_READERS = "MANAGE_READERS";
    public static final String MANAGE_BORROW = "MANAGE_BORROW";
    public static final String MANAGE_RETURN = "MANAGE_RETURN";
    public static final String MANAGE_FINES = "MANAGE_FINES";
    public static final String MANAGE_STAFF = "MANAGE_STAFF";
    public static final String VIEW_STATISTICS = "VIEW_STATISTICS";
    public static final String EXPORT_DATA = "EXPORT_DATA";
    public static final String IMPORT_DATA = "IMPORT_DATA";
    
    private String maNV;
    private boolean canManageBooks;
    private boolean canManageReaders;
    private boolean canManageBorrow;
    private boolean canManageReturn;
    private boolean canManageFines;
    private boolean canManageStaff;
    private boolean canViewStatistics;
    private boolean canExportData;
    private boolean canImportData;
    
    public Permission() {
        // Default permissions for regular staff
        this.canManageBooks = false;
        this.canManageReaders = true;
        this.canManageBorrow = true;
        this.canManageReturn = true;
        this.canManageFines = true;
        this.canManageStaff = false;
        this.canViewStatistics = true;
        this.canExportData = false;
        this.canImportData = false;
    }
    
    public Permission(String maNV) {
        this();
        this.maNV = maNV;
    }
    
    /**
     * Get admin permissions (all permissions enabled)
     */
    public static Permission getAdminPermissions(String maNV) {
        Permission perm = new Permission(maNV);
        perm.setCanManageBooks(true);
        perm.setCanManageReaders(true);
        perm.setCanManageBorrow(true);
        perm.setCanManageReturn(true);
        perm.setCanManageFines(true);
        perm.setCanManageStaff(true);
        perm.setCanViewStatistics(true);
        perm.setCanExportData(true);
        perm.setCanImportData(true);
        return perm;
    }
    
    /**
     * Get librarian permissions (limited permissions)
     */
    public static Permission getLibrarianPermissions(String maNV) {
        Permission perm = new Permission(maNV);
        perm.setCanManageBooks(false);
        perm.setCanManageReaders(true);
        perm.setCanManageBorrow(true);
        perm.setCanManageReturn(true);
        perm.setCanManageFines(true);
        perm.setCanManageStaff(false);
        perm.setCanViewStatistics(true);
        perm.setCanExportData(false);
        perm.setCanImportData(false);
        return perm;
    }
    
    // Getters and Setters
    public String getMaNV() {
        return maNV;
    }
    
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    public boolean isCanManageBooks() {
        return canManageBooks;
    }
    
    public void setCanManageBooks(boolean canManageBooks) {
        this.canManageBooks = canManageBooks;
    }
    
    public boolean isCanManageReaders() {
        return canManageReaders;
    }
    
    public void setCanManageReaders(boolean canManageReaders) {
        this.canManageReaders = canManageReaders;
    }
    
    public boolean isCanManageBorrow() {
        return canManageBorrow;
    }
    
    public void setCanManageBorrow(boolean canManageBorrow) {
        this.canManageBorrow = canManageBorrow;
    }
    
    public boolean isCanManageReturn() {
        return canManageReturn;
    }
    
    public void setCanManageReturn(boolean canManageReturn) {
        this.canManageReturn = canManageReturn;
    }
    
    public boolean isCanManageFines() {
        return canManageFines;
    }
    
    public void setCanManageFines(boolean canManageFines) {
        this.canManageFines = canManageFines;
    }
    
    public boolean isCanManageStaff() {
        return canManageStaff;
    }
    
    public void setCanManageStaff(boolean canManageStaff) {
        this.canManageStaff = canManageStaff;
    }
    
    public boolean isCanViewStatistics() {
        return canViewStatistics;
    }
    
    public void setCanViewStatistics(boolean canViewStatistics) {
        this.canViewStatistics = canViewStatistics;
    }
    
    public boolean isCanExportData() {
        return canExportData;
    }
    
    public void setCanExportData(boolean canExportData) {
        this.canExportData = canExportData;
    }
    
    public boolean isCanImportData() {
        return canImportData;
    }
    
    public void setCanImportData(boolean canImportData) {
        this.canImportData = canImportData;
    }
    
    @Override
    public String toString() {
        return String.format("Permission[maNV=%s, books=%b, readers=%b, borrow=%b, return=%b, fines=%b, staff=%b, stats=%b, export=%b, import=%b]",
            maNV, canManageBooks, canManageReaders, canManageBorrow, canManageReturn, 
            canManageFines, canManageStaff, canViewStatistics, canExportData, canImportData);
    }
}
