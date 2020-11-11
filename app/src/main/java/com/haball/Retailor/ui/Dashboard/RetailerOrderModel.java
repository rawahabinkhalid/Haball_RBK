package com.haball.Retailor.ui.Dashboard;

public class RetailerOrderModel {
    private String ID;
    private String OrderNumber;
    private String CompanyName;
    private String TotalPrice;
    private String NetPrice;
    private String OrderStatusValue;
    private String CreatedDate;
    private String Status;
    private String InvoiceStatus;
    private int InvoiceUpload;

    public RetailerOrderModel(String ID, String orderNumber, String companyName, String totalPrice, String netPrice, String orderStatusValue, String createdDate, String status, String invoiceStatus, int invoiceUpload) {
        this.ID = ID;
        OrderNumber = orderNumber;
        CompanyName = companyName;
        TotalPrice = totalPrice;
        NetPrice = netPrice;
        OrderStatusValue = orderStatusValue;
        CreatedDate = createdDate;
        Status = status;
        InvoiceStatus = invoiceStatus;
        InvoiceUpload = invoiceUpload;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getNetPrice() {
        return NetPrice;
    }

    public void setNetPrice(String netPrice) {
        NetPrice = netPrice;
    }

    public String getOrderStatusValue() {
        return OrderStatusValue;
    }

    public void setOrderStatusValue(String orderStatusValue) {
        OrderStatusValue = orderStatusValue;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getInvoiceStatus() {
        return InvoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        InvoiceStatus = invoiceStatus;
    }

    public int getInvoiceUpload() {
        return InvoiceUpload;
    }

    public void setInvoiceUpload(int invoiceUpload) {
        InvoiceUpload = invoiceUpload;
    }
}

