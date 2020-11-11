package com.haball.Distributor.ui.retailer;

public class ViewOrderProductModel {
    private String Discount;
    private String DiscountedAmount;
    private String OrderedQty;
    private String PackSize;
    private String ProductCode;
    private String ProductId;
    private String ProductTitle;
    private String ProductUnitPrice;
    private String TaxValue;
    private String Totalamount;
    private String UOMId;
    private String UnitOFMeasure;

    public ViewOrderProductModel(String discount, String discountedAmount, String orderedQty, String packSize, String productCode, String productId, String productTitle, String productUnitPrice, String taxValue, String totalamount, String UOMId, String unitOFMeasure) {
        Discount = discount;
        DiscountedAmount = discountedAmount;
        OrderedQty = orderedQty;
        PackSize = packSize;
        ProductCode = productCode;
        ProductId = productId;
        ProductTitle = productTitle;
        ProductUnitPrice = productUnitPrice;
        TaxValue = taxValue;
        Totalamount = totalamount;
        this.UOMId = UOMId;
        UnitOFMeasure = unitOFMeasure;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getDiscountedAmount() {
        return DiscountedAmount;
    }

    public void setDiscountedAmount(String discountedAmount) {
        DiscountedAmount = discountedAmount;
    }

    public String getOrderedQty() {
        return OrderedQty;
    }

    public void setOrderedQty(String orderedQty) {
        OrderedQty = orderedQty;
    }

    public String getPackSize() {
        return PackSize;
    }

    public void setPackSize(String packSize) {
        PackSize = packSize;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public String getProductUnitPrice() {
        return ProductUnitPrice;
    }

    public void setProductUnitPrice(String productUnitPrice) {
        ProductUnitPrice = productUnitPrice;
    }

    public String getTaxValue() {
        return TaxValue;
    }

    public void setTaxValue(String taxValue) {
        TaxValue = taxValue;
    }

    public String getTotalamount() {
        return Totalamount;
    }

    public void setTotalamount(String totalamount) {
        Totalamount = totalamount;
    }

    public String getUOMId() {
        return UOMId;
    }

    public void setUOMId(String UOMId) {
        this.UOMId = UOMId;
    }

    public String getUnitOFMeasure() {
        return UnitOFMeasure;
    }

    public void setUnitOFMeasure(String unitOFMeasure) {
        UnitOFMeasure = unitOFMeasure;
    }
}
