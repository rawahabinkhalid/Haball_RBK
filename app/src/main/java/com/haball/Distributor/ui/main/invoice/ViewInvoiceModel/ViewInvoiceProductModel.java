package com.haball.Distributor.ui.main.invoice.ViewInvoiceModel;

public class ViewInvoiceProductModel {
    private String CreatedBy;
    private String CreatedDate;
    private String Discount;
    private String ID;
    private String InvoiceID;
    private String InvoiceQty;
    private String LastChangedBy;
    private String LastChangedDate;
    private String ProductCode;
    private String ProductID;
    private String ProductName;
    private String ProductShortDescription;
    private String TotalPrice;
    private String UOM;
    private String UOMTitle;
    private String UnitPrice;

    public ViewInvoiceProductModel(String createdBy, String createdDate, String discount, String ID, String invoiceID, String invoiceQty, String lastChangedBy, String lastChangedDate, String productCode, String productID, String productName, String productShortDescription, String totalPrice, String UOM, String UOMTitle, String unitPrice) {
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        Discount = discount;
        this.ID = ID;
        InvoiceID = invoiceID;
        InvoiceQty = invoiceQty;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        ProductCode = productCode;
        ProductID = productID;
        ProductName = productName;
        ProductShortDescription = productShortDescription;
        TotalPrice = totalPrice;
        this.UOM = UOM;
        this.UOMTitle = UOMTitle;
        UnitPrice = unitPrice;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        InvoiceID = invoiceID;
    }

    public String getInvoiceQty() {
        return InvoiceQty;
    }

    public void setInvoiceQty(String invoiceQty) {
        InvoiceQty = invoiceQty;
    }

    public String getLastChangedBy() {
        return LastChangedBy;
    }

    public void setLastChangedBy(String lastChangedBy) {
        LastChangedBy = lastChangedBy;
    }

    public String getLastChangedDate() {
        return LastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        LastChangedDate = lastChangedDate;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductShortDescription() {
        return ProductShortDescription;
    }

    public void setProductShortDescription(String productShortDescription) {
        ProductShortDescription = productShortDescription;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getUOMTitle() {
        return UOMTitle;
    }

    public void setUOMTitle(String UOMTitle) {
        this.UOMTitle = UOMTitle;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }
}
