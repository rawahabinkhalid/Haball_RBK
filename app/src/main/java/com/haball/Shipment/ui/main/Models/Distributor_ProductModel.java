package com.haball.Shipment.ui.main.Models;

import androidx.lifecycle.ViewModel;

public class Distributor_ProductModel {
    private String CorpComment;
    private String CreatedBy;
    private String CreatedDate;
    private String DeliveredQty;
    private String DeliveryNoteId;
    private String Discount;
    private String DistComment;
    private String ID;
    private String LastChangedBy;
    private String LastChangedDate;
    private String ProductCode;
    private String ProductId;
    private String ProductName;
    private String ProductShortDescription;
    private String RefundQty;
    private String RejectQty;
    private String ReshipQty;
    private String ReturnQty;
    private String ReturnReason;
    private String TotalPrice;
    private String UOM;
    private String UOMTitle;
    private String UnitPrice;
    private String BatchNumber;
    private String ExpiryDate;
    private String ProductionDate;

    public Distributor_ProductModel(String corpComment, String createdBy, String createdDate, String deliveredQty, String deliveryNoteId, String discount, String distComment, String ID, String lastChangedBy, String lastChangedDate, String productCode, String productId, String productName, String productShortDescription, String refundQty, String rejectQty, String reshipQty, String returnQty, String returnReason, String totalPrice, String UOM, String UOMTitle, String unitPrice, String batchNumber, String expiryDate, String productionDate) {
        CorpComment = corpComment;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        DeliveredQty = deliveredQty;
        DeliveryNoteId = deliveryNoteId;
        Discount = discount;
        DistComment = distComment;
        this.ID = ID;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        ProductCode = productCode;
        ProductId = productId;
        ProductName = productName;
        ProductShortDescription = productShortDescription;
        RefundQty = refundQty;
        RejectQty = rejectQty;
        ReshipQty = reshipQty;
        ReturnQty = returnQty;
        ReturnReason = returnReason;
        TotalPrice = totalPrice;
        this.UOM = UOM;
        this.UOMTitle = UOMTitle;
        UnitPrice = unitPrice;
        BatchNumber = batchNumber;
        ExpiryDate = expiryDate;
        ProductionDate = productionDate;
    }

    public String getCorpComment() {
        return CorpComment;
    }

    public void setCorpComment(String corpComment) {
        CorpComment = corpComment;
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

    public String getDeliveredQty() {
        return DeliveredQty;
    }

    public void setDeliveredQty(String deliveredQty) {
        DeliveredQty = deliveredQty;
    }

    public String getDeliveryNoteId() {
        return DeliveryNoteId;
    }

    public void setDeliveryNoteId(String deliveryNoteId) {
        DeliveryNoteId = deliveryNoteId;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getDistComment() {
        return DistComment;
    }

    public void setDistComment(String distComment) {
        DistComment = distComment;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
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

    public String getRefundQty() {
        return RefundQty;
    }

    public void setRefundQty(String refundQty) {
        RefundQty = refundQty;
    }

    public String getRejectQty() {
        return RejectQty;
    }

    public void setRejectQty(String rejectQty) {
        RejectQty = rejectQty;
    }

    public String getReshipQty() {
        return ReshipQty;
    }

    public void setReshipQty(String reshipQty) {
        ReshipQty = reshipQty;
    }

    public String getReturnQty() {
        return ReturnQty;
    }

    public void setReturnQty(String returnQty) {
        ReturnQty = returnQty;
    }

    public String getReturnReason() {
        return ReturnReason;
    }

    public void setReturnReason(String returnReason) {
        ReturnReason = returnReason;
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

    public String getUOMTitle() {
        return UOMTitle;
    }

    public void setUOMTitle(String UOMTitle) {
        this.UOMTitle = UOMTitle;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getBatchNumber() {
        return BatchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getProductionDate() {
        return ProductionDate;
    }

    public void setProductionDate(String productionDate) {
        ProductionDate = productionDate;
    }
}
