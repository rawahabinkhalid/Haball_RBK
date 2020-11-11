package com.haball.Distributor.ui.retailer.Payment.Models;

public class Payment_View_Model {
    String ID;
    String RetailerOrderId;
    String ProductId;
    String OrderQty;
    String TotalPrice;
    String CreatedBy;
    String CreatedDate;
    String LastChangedBy;
    String LastChangedDate;
    String ProductCode;
    String ProductName;
    String ProductShortDescription;
    String UnitPrice;
    String AvailableStock;
    String Discount;
    String UOM;

    public Payment_View_Model(String ID, String retailerOrderId, String productId, String orderQty, String totalPrice, String createdBy, String createdDate, String lastChangedBy, String lastChangedDate, String productCode, String productName, String productShortDescription, String unitPrice, String availableStock, String discount, String UOM) {
        this.ID = ID;
        RetailerOrderId = retailerOrderId;
        ProductId = productId;
        OrderQty = orderQty;
        TotalPrice = totalPrice;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        ProductCode = productCode;
        ProductName = productName;
        ProductShortDescription = productShortDescription;
        UnitPrice = unitPrice;
        AvailableStock = availableStock;
        Discount = discount;
        this.UOM = UOM;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRetailerOrderId() {
        return RetailerOrderId;
    }

    public void setRetailerOrderId(String retailerOrderId) {
        RetailerOrderId = retailerOrderId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(String orderQty) {
        OrderQty = orderQty;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
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

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductShortDescription() {
        return ProductShortDescription;
    }

    public void setProductShortDescription(String productShortDescription) {
        ProductShortDescription = productShortDescription;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getAvailableStock() {
        return AvailableStock;
    }

    public void setAvailableStock(String availableStock) {
        AvailableStock = availableStock;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }
}

