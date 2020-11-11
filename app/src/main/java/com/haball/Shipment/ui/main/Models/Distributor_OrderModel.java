package com.haball.Shipment.ui.main.Models;

import androidx.lifecycle.ViewModel;

public class Distributor_OrderModel {
     private  String OrderNumber;
    private  String DistributorCompanyName;
    private  String TransportTypeDescription;
    private  String PaymentTermDescription;
    private  String CreatedDate;
    private  String orderStatus;
    private  String ordersShippingAddress;
    private  String ordersBillingAddress;

    public Distributor_OrderModel() {
    }

    public Distributor_OrderModel(String orderNumber, String distributorCompanyName, String transportTypeDescription, String paymentTermDescription, String createdDate, String orderStatus, String ordersShippingAddress, String ordersBillingAddress) {
        OrderNumber = orderNumber;
        DistributorCompanyName = distributorCompanyName;
        TransportTypeDescription = transportTypeDescription;
        PaymentTermDescription = paymentTermDescription;
        CreatedDate = createdDate;
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

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
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
