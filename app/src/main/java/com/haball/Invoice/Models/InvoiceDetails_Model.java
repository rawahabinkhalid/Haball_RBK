package com.haball.Invoice.Models;

public class InvoiceDetails_Model {
    private String InvoiceNumber;
    private String CreatedDate;
    private String InvoiceTotal;
    private String PaidDate;
    private String TotalPrice;
    private String Status;
    private String State;

    public InvoiceDetails_Model() {
    }

    public InvoiceDetails_Model(String invoiceNumber, String createdDate, String invoiceTotal, String paidDate, String totalPrice, String status, String state) {
        InvoiceNumber = invoiceNumber;
        CreatedDate = createdDate;
        InvoiceTotal = invoiceTotal;
        PaidDate = paidDate;
        TotalPrice = totalPrice;
        Status = status;
        State = state;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getInvoiceTotal() {
        return InvoiceTotal;
    }

    public void setInvoiceTotal(String invoiceTotal) {
        InvoiceTotal = invoiceTotal;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
