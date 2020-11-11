package com.haball.Payment;

public class ConsolidatePaymentsModel {

    private String ID, ConsolidatedInvoiceNumber, CompanyName, CreatedDate, TotalPrice, PaidAmount, Status;

    public ConsolidatePaymentsModel(String ID, String consolidatedInvoiceNumber, String companyName, String createdDate, String totalPrice, String paidAmount, String status) {
        this.ID = ID;
        ConsolidatedInvoiceNumber = consolidatedInvoiceNumber;
        CompanyName = companyName;
        CreatedDate = createdDate;
        TotalPrice = totalPrice;
        PaidAmount = paidAmount;
        Status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getConsolidatedInvoiceNumber() {
        return ConsolidatedInvoiceNumber;
    }

    public void setConsolidatedInvoiceNumber(String consolidatedInvoiceNumber) {
        ConsolidatedInvoiceNumber = consolidatedInvoiceNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
