package com.haball.Distributor.ui.orders.Models;

public class OrderItemsModel {
    private String ID, CompanyId, Code, Title, UnitPrice,
            DiscountId, DiscountValue, DiscountAmount, CategoryTitle,
            ShortDescription, UOMId, UOMTitle;

    public OrderItemsModel(String ID, String companyId, String code, String title, String unitPrice, String discountId, String discountValue, String discountAmount, String categoryTitle, String shortDescription, String UOMId, String UOMTitle) {
        this.ID = ID;
        CompanyId = companyId;
        Code = code;
        Title = title;
        UnitPrice = unitPrice;
        DiscountId = discountId;
        DiscountValue = discountValue;
        DiscountAmount = discountAmount;
        CategoryTitle = categoryTitle;
        ShortDescription = shortDescription;
        this.UOMId = UOMId;
        this.UOMTitle = UOMTitle;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getDiscountId() {
        return DiscountId;
    }

    public void setDiscountId(String discountId) {
        DiscountId = discountId;
    }

    public String getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(String discountValue) {
        DiscountValue = discountValue;
    }

    public String getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getUOMId() {
        return UOMId;
    }

    public void setUOMId(String UOMId) {
        this.UOMId = UOMId;
    }

    public String getUOMTitle() {
        return UOMTitle;
    }

    public void setUOMTitle(String UOMTitle) {
        this.UOMTitle = UOMTitle;
    }
}
