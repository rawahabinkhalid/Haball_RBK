package com.haball.Distributor.ui.main.ViewOrdersModel;

public class ViewOrderProductModel {
    String OrderDetailID;
    String OrderId;
    String ID;
    String ProductCode;
    String ProductName;
    String ProductShortDescription;
    String UnitPrice;
    String OrderQty;
    String AvailableStock;
    String Discount;
    String TotalPrice;
    String UOM;
    String UOMTitle;
    String CreatedBy;
    String CreatedDate;
    String LastChangedBy;
    String LastChangedDate;
    String RejectReasonId;
    String RejectReason;
    String Id;

    public ViewOrderProductModel(String orderDetailID, String orderId, String ID, String productCode, String productName, String productShortDescription, String unitPrice, String orderQty, String availableStock, String discount, String totalPrice, String UOM, String UOMTitle, String createdBy, String createdDate, String lastChangedBy, String lastChangedDate, String rejectReasonId, String rejectReason, String id) {
        OrderDetailID = orderDetailID;
        OrderId = orderId;
        this.ID = ID;
        ProductCode = productCode;
        ProductName = productName;
        ProductShortDescription = productShortDescription;
        UnitPrice = unitPrice;
        OrderQty = orderQty;
        AvailableStock = availableStock;
        Discount = discount;
        TotalPrice = totalPrice;
        this.UOM = UOM;
        this.UOMTitle = UOMTitle;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        RejectReasonId = rejectReasonId;
        RejectReason = rejectReason;
        Id = id;
    }

    public String getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        OrderDetailID = orderDetailID;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(String orderQty) {
        OrderQty = orderQty;
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

    public String getRejectReasonId() {
        return RejectReasonId;
    }

    public void setRejectReasonId(String rejectReasonId) {
        RejectReasonId = rejectReasonId;
    }

    public String getRejectReason() {
        return RejectReason;
    }

    public void setRejectReason(String rejectReason) {
        RejectReason = rejectReason;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
