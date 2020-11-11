package com.haball.Shipment.ui.main.Models;

import androidx.lifecycle.ViewModel;

public class Distributor_InvoiceModel extends ViewModel {
    private String Comments;
    private String CompanyId;
    private String ConsolidatedInvoiceID;
    private String CreatedBy;
    private String CreatedDate;
    private String Discount;
    private String DistributorId;
    private String DueDate;
    private String ID;
    private String InvoiceNumber;
    private String InvoiceType;
    private String LastChangedBy;
    private String LastChangedDate;
    private String NetPrice;
    private String OrderId;
    private String PaidAmount;
    private String PaidBy;
    private String PaidDate;
    private String ReferenceID;
    private String SalespointId;
    private String State;
    private String Status;
    private String  TotalPrice;
    private String TransportTypeDescription;
    private String  TransportTypeId;
    private String TransportTypeIdFreightCharges;

    public Distributor_InvoiceModel(String ID, String invoiceNumber, String distributorId, String companyId, String orderId, String status, String netPrice, String discount, String totalPrice, String comments, String referenceID, String paidAmount, String paidDate, String paidBy, String invoiceType, String salespointId, String createdBy, String createdDate, String lastChangedBy, String lastChangedDate, String transportTypeId, String transportTypeDescription, String transportTypeIdFreightCharges, String dueDate, String consolidatedInvoiceID, String state) {
        this.ID = ID;
        InvoiceNumber = invoiceNumber;
        DistributorId = distributorId;
        CompanyId = companyId;
        OrderId = orderId;
        Status = status;
        NetPrice = netPrice;
        Discount = discount;
        TotalPrice = totalPrice;
        Comments = comments;
        ReferenceID = referenceID;
        PaidAmount = paidAmount;
        PaidDate = paidDate;
        PaidBy = paidBy;
        InvoiceType = invoiceType;
        SalespointId = salespointId;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        TransportTypeId = transportTypeId;
        TransportTypeDescription = transportTypeDescription;
        TransportTypeIdFreightCharges = transportTypeIdFreightCharges;
        DueDate = dueDate;
        ConsolidatedInvoiceID = consolidatedInvoiceID;
        State = state;
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

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNetPrice() {
        return NetPrice;
    }

    public void setNetPrice(String netPrice) {
        NetPrice = netPrice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getPaidBy() {
        return PaidBy;
    }

    public void setPaidBy(String paidBy) {
        PaidBy = paidBy;
    }

    public String getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        InvoiceType = invoiceType;
    }

    public String getSalespointId() {
        return SalespointId;
    }

    public void setSalespointId(String salespointId) {
        SalespointId = salespointId;
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

    public String getTransportTypeId() {
        return TransportTypeId;
    }

    public void setTransportTypeId(String transportTypeId) {
        TransportTypeId = transportTypeId;
    }

    public String getTransportTypeDescription() {
        return TransportTypeDescription;
    }

    public void setTransportTypeDescription(String transportTypeDescription) {
        TransportTypeDescription = transportTypeDescription;
    }

    public String getTransportTypeIdFreightCharges() {
        return TransportTypeIdFreightCharges;
    }

    public void setTransportTypeIdFreightCharges(String transportTypeIdFreightCharges) {
        TransportTypeIdFreightCharges = transportTypeIdFreightCharges;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getConsolidatedInvoiceID() {
        return ConsolidatedInvoiceID;
    }

    public void setConsolidatedInvoiceID(String consolidatedInvoiceID) {
        ConsolidatedInvoiceID = consolidatedInvoiceID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
