package com.haball.Distributor.ui.retailer.Payment.Models;

public class Dist_Retailer_Dashboard_Model {
   private String InvoiceNumber;
    private String CompanyName;
    private String TotalPrice;
    private String RetailerInvoiceId;
    private String InvoiceStatusValue;
    private String IsEditable;
    String PrepaidStatusValue;
    private String CreatedDate;
    private String InvoiceReferenceNumber;
    private String OrderReferenceNumber;
    private String PaidDate;
    private String Status;

    public Dist_Retailer_Dashboard_Model(String invoiceNumber, String companyName, String totalPrice, String retailerInvoiceId, String invoiceStatusValue, String isEditable, String prepaidStatusValue, String createdDate, String invoiceReferenceNumber, String orderReferenceNumber, String paidDate, String status) {
        InvoiceNumber = invoiceNumber;
        CompanyName = companyName;
        TotalPrice = totalPrice;
        RetailerInvoiceId = retailerInvoiceId;
        InvoiceStatusValue = invoiceStatusValue;
        IsEditable = isEditable;
        PrepaidStatusValue = prepaidStatusValue;
        CreatedDate = createdDate;
        InvoiceReferenceNumber = invoiceReferenceNumber;
        OrderReferenceNumber = orderReferenceNumber;
        PaidDate = paidDate;
        Status = status;
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

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getRetailerInvoiceId() {
        return RetailerInvoiceId;
    }

    public void setRetailerInvoiceId(String retailerInvoiceId) {
        RetailerInvoiceId = retailerInvoiceId;
    }

    public String getInvoiceStatusValue() {
        return InvoiceStatusValue;
    }

    public void setInvoiceStatusValue(String invoiceStatusValue) {
        InvoiceStatusValue = invoiceStatusValue;
    }

    public String getIsEditable() {
        return IsEditable;
    }

    public void setIsEditable(String isEditable) {
        IsEditable = isEditable;
    }

    public String getPrepaidStatusValue() {
        return PrepaidStatusValue;
    }

    public void setPrepaidStatusValue(String prepaidStatusValue) {
        PrepaidStatusValue = prepaidStatusValue;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getInvoiceReferenceNumber() {
        return InvoiceReferenceNumber;
    }

    public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
        InvoiceReferenceNumber = invoiceReferenceNumber;
    }

    public String getOrderReferenceNumber() {
        return OrderReferenceNumber;
    }

    public void setOrderReferenceNumber(String orderReferenceNumber) {
        OrderReferenceNumber = orderReferenceNumber;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
