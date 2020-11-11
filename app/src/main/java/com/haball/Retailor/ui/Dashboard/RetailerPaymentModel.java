package com.haball.Retailor.ui.Dashboard;

public class RetailerPaymentModel {
    String RetailerInvoiceId;
    String InvoiceNumber;
    String CompanyName;
    String Status;
    String TotalPrice;
    String IsEditable;
    String CreatedDate;
    String ID;

    public RetailerPaymentModel(String retailerInvoiceId, String invoiceNumber, String companyName, String status, String totalPrice, String isEditable, String createdDate) {
        RetailerInvoiceId = retailerInvoiceId;
        InvoiceNumber = invoiceNumber;
        CompanyName = companyName;
        Status = status;
        TotalPrice = totalPrice;
        IsEditable = isEditable;
        CreatedDate = createdDate;
    }

    public String getRetailerInvoiceId() {
        return RetailerInvoiceId;
    }

    public void setRetailerInvoiceId(String retailerInvoiceId) {
        RetailerInvoiceId = retailerInvoiceId;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getIsEditable() {
        return IsEditable;
    }

    public void setIsEditable(String isEditable) {
        IsEditable = isEditable;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getID() {
        return ID;
    }
}
