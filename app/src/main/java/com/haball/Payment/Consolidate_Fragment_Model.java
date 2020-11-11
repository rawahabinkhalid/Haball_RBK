package com.haball.Payment;

public class Consolidate_Fragment_Model {
    private String ID, InvoiceNumber, InvoiceType, TotalPrice;

    public Consolidate_Fragment_Model(String ID, String invoiceNumber, String invoiceType, String totalPrice) {
        this.ID = ID;
        InvoiceNumber = invoiceNumber;
        InvoiceType = invoiceType;
        TotalPrice = totalPrice;
    }

    public String getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        InvoiceType = invoiceType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
