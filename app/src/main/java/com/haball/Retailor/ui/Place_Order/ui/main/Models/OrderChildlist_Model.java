package com.haball.Retailor.ui.Place_Order.ui.main.Models;

public class OrderChildlist_Model {

    String ProductId;
    String ProductCode;
    String Title;
    String ProductUnitPrice;
    String ProductCategoryId;
    String UnitOFMeasure;
    String DiscountAmount;
    String PackSize;

    public OrderChildlist_Model(String productId, String productCode, String title, String productUnitPrice, String productCategoryId, String unitOFMeasure, String discountAmount, String packSize) {
        ProductId = productId;
        ProductCode = productCode;
        Title = title;
        ProductUnitPrice = productUnitPrice;
        ProductCategoryId = productCategoryId;
        UnitOFMeasure = unitOFMeasure;
        DiscountAmount = discountAmount;
        PackSize = packSize;
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getProductUnitPrice() {
        return ProductUnitPrice;
    }

    public void setProductUnitPrice(String productUnitPrice) {
        ProductUnitPrice = productUnitPrice;
    }

    public String getProductCategoryId() {
        return ProductCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        ProductCategoryId = productCategoryId;
    }

    public String getUnitOFMeasure() {
        return UnitOFMeasure;
    }

    public void setUnitOFMeasure(String unitOFMeasure) {
        UnitOFMeasure = unitOFMeasure;
    }

    public String getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }
}
