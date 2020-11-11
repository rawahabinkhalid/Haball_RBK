package com.haball.Distributor.ui.orders.OrdersTabsNew.Models;

public class OrderChildlist_Model_DistOrder {
    String ID;
    String CompanyId;
    String CategoryId;
    String Code;
    String Title;
    String ShortDescription;
    String LongDescription;
    String UnitPrice;
    String CategoryTitle;
    String packSize;
    String UOMId;
    String UOMTitle;
    String ImageData;
    String ImageType;
    String DiscountId;
    String EffectiveDate;
    String ExpiryDate;
    String IsPercentage;
    String DiscountValue;
    String DiscountAmount;

    public OrderChildlist_Model_DistOrder(String ID, String companyId, String categoryId, String code, String title, String shortDescription, String longDescription, String unitPrice, String categoryTitle, String packSize, String UOMId, String UOMTitle, String imageData, String imageType, String discountId, String effectiveDate, String expiryDate, String isPercentage, String discountValue, String discountAmount) {
        this.ID = ID;
        CompanyId = companyId;
        CategoryId = categoryId;
        Code = code;
        Title = title;
        ShortDescription = shortDescription;
        LongDescription = longDescription;
        UnitPrice = unitPrice;
        CategoryTitle = categoryTitle;
        this.packSize = packSize;
        this.UOMId = UOMId;
        this.UOMTitle = UOMTitle;
        ImageData = imageData;
        ImageType = imageType;
        DiscountId = discountId;
        EffectiveDate = effectiveDate;
        ExpiryDate = expiryDate;
        IsPercentage = isPercentage;
        DiscountValue = discountValue;
        DiscountAmount = discountAmount;
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

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
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

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
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

    public String getImageData() {
        return ImageData;
    }

    public void setImageData(String imageData) {
        ImageData = imageData;
    }

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    public String getDiscountId() {
        return DiscountId;
    }

    public void setDiscountId(String discountId) {
        DiscountId = discountId;
    }

    public String getEffectiveDate() {
        return EffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        EffectiveDate = effectiveDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getIsPercentage() {
        return IsPercentage;
    }

    public void setIsPercentage(String isPercentage) {
        IsPercentage = isPercentage;
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
}
