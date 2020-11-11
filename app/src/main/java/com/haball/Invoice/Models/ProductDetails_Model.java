package com.haball.Invoice.Models;

public class ProductDetails_Model {
    private String ProductCode;
    private String DeliveredQty;
    private String UnitPrice;
    private String Discount;
    private String TotalPrice;
    private String ProductName;

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getDeliveredQty() {
        return DeliveredQty;
    }

    public void setDeliveredQty(String deliveredQty) {
        DeliveredQty = deliveredQty;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public ProductDetails_Model(String productCode, String deliveredQty, String unitPrice, String discount, String totalPrice, String productName) {
        ProductCode = productCode;
        DeliveredQty = deliveredQty;
        UnitPrice = unitPrice;
        Discount = discount;
        TotalPrice = totalPrice;
        ProductName = productName;
    }
}
