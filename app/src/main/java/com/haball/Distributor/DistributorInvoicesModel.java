package com.haball.Distributor;

public class DistributorInvoicesModel {
    private String ID, InvoiceNumber,CompanyName,TotalPrice,InvoiceStatusValue,State;

    public DistributorInvoicesModel(String ID, String invoiceNumber, String companyName, String totalPrice, String invoiceStatusValue, String state) {
        this.ID = ID;
        InvoiceNumber = invoiceNumber;
        CompanyName = companyName;
        TotalPrice = totalPrice;
        InvoiceStatusValue = invoiceStatusValue;
        State = state;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getInvoiceStatusValue() {
        return InvoiceStatusValue;
    }

    public void setInvoiceStatusValue(String invoiceStatusValue) {
        InvoiceStatusValue = invoiceStatusValue;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }
}

