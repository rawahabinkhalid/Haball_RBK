package com.haball.Retailor.ui.RetailerOrder.RetailerOrdersModel;

public class RetailerViewOrderProductModel {
    String ProductId;
    String ProductCode;
    String ProductName;
    String OrderQty;
    String UnitPrice;
    String Discount;
    String TotalPrice;
    String UOM;
    String TaxValue;
    String UOMTitle;

    public RetailerViewOrderProductModel(String productId, String productCode, String productName, String orderQty, String unitPrice, String discount, String totalPrice, String UOM, String taxValue, String UOMTitle) {
        ProductId = productId;
        ProductCode = productCode;
        ProductName = productName;
        OrderQty = orderQty;
        UnitPrice = unitPrice;
        Discount = discount;
        TotalPrice = totalPrice;
        this.UOM = UOM;
        TaxValue = taxValue;
        this.UOMTitle = UOMTitle;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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

    public String getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(String orderQty) {
        OrderQty = orderQty;
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

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getTaxValue() {
        return TaxValue;
    }

    public void setTaxValue(String taxValue) {
        TaxValue = taxValue;
    }

    public String getUOMTitle() {
        return UOMTitle;
    }

    public void setUOMTitle(String UOMTitle) {
        this.UOMTitle = UOMTitle;
    }
}
