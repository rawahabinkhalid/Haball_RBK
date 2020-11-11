package com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel;

public class RetailerOrdersModel {
    private String CreatedDate;
    private String InvoiceReferenceNumber;
    private String InvoiceStatus;
    private String OrderId;
    private String OrderNumber;
    private String OrderReferenceNumber;
    private String OrderStatus;
    private String OrderStatusValue;
    private String Retailer;
    private String Submitter;
    private String TotalAmount;

    public RetailerOrdersModel(String createdDate, String invoiceReferenceNumber, String invoiceStatus, String orderId, String orderNumber, String orderReferenceNumber, String orderStatus, String orderStatusValue, String retailer, String submitter, String totalAmount) {
        CreatedDate = createdDate;
        InvoiceReferenceNumber = invoiceReferenceNumber;
        InvoiceStatus = invoiceStatus;
        OrderId = orderId;
        OrderNumber = orderNumber;
        OrderReferenceNumber = orderReferenceNumber;
        OrderStatus = orderStatus;
        OrderStatusValue = orderStatusValue;
        Retailer = retailer;
        Submitter = submitter;
        TotalAmount = totalAmount;
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

    public String getInvoiceStatus() {
        return InvoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        InvoiceStatus = invoiceStatus;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getOrderReferenceNumber() {
        return OrderReferenceNumber;
    }

    public void setOrderReferenceNumber(String orderReferenceNumber) {
        OrderReferenceNumber = orderReferenceNumber;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderStatusValue() {
        return OrderStatusValue;
    }

    public void setOrderStatusValue(String orderStatusValue) {
        OrderStatusValue = orderStatusValue;
    }

    public String getRetailer() {
        return Retailer;
    }

    public void setRetailer(String retailer) {
        Retailer = retailer;
    }

    public String getSubmitter() {
        return Submitter;
    }

    public void setSubmitter(String submitter) {
        Submitter = submitter;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
