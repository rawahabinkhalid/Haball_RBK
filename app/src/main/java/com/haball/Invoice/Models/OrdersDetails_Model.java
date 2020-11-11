package com.haball.Invoice.Models;

public class OrdersDetails_Model {
    private String OrderNumber;
    private String DistributorCompanyName;
    private String TransportTypeDescription;
    private String PaymentTermDescription;
    private String OrderDate;
    private String orderStatus;
    private String ordersShippingAddress;
    private String ordersBillingAddress;

    public OrdersDetails_Model() {
    }

    public OrdersDetails_Model(String orderNumber, String distributorCompanyName, String transportTypeDescription, String paymentTermDescription, String orderDate, String orderStatus, String ordersShippingAddress, String ordersBillingAddress) {
        OrderNumber = orderNumber;
        DistributorCompanyName = distributorCompanyName;
        TransportTypeDescription = transportTypeDescription;
        PaymentTermDescription = paymentTermDescription;
        OrderDate = orderDate;
        this.orderStatus = orderStatus;
        this.ordersShippingAddress = ordersShippingAddress;
        this.ordersBillingAddress = ordersBillingAddress;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getDistributorCompanyName() {
        return DistributorCompanyName;
    }

    public void setDistributorCompanyName(String distributorCompanyName) {
        DistributorCompanyName = distributorCompanyName;
    }

    public String getTransportTypeDescription() {
        return TransportTypeDescription;
    }

    public void setTransportTypeDescription(String transportTypeDescription) {
        TransportTypeDescription = transportTypeDescription;
    }

    public String getPaymentTermDescription() {
        return PaymentTermDescription;
    }

    public void setPaymentTermDescription(String paymentTermDescription) {
        PaymentTermDescription = paymentTermDescription;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrdersShippingAddress() {
        return ordersShippingAddress;
    }

    public void setOrdersShippingAddress(String ordersShippingAddress) {
        this.ordersShippingAddress = ordersShippingAddress;
    }

    public String getOrdersBillingAddress() {
        return ordersBillingAddress;
    }

    public void setOrdersBillingAddress(String ordersBillingAddress) {
        this.ordersBillingAddress = ordersBillingAddress;
    }
}
