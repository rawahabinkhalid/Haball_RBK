package com.haball.Shipment.ui.main.Models;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class Distributor_ShipmentModel {
    private String Comments;
    private String CompanyId;
    private String CreatedBy;
    private String CreatedDate;
    private String DeliveryDate;
    private String DeliveryNumber;
    private String Discount;
    private String DistComment;
    private JSONObject Distributor;
    private String DistributorCompanyName;
    private String DistributorCreatedDate;
    private String DistributorDealerCode;
    private String DistributorEmail;
    private String DistributorFirstName;
    private String DistributorId;
    private String DistributorLastName;
    private String DistributorMobile;
    private String DistributorPhone;
    private String DistributorsCompanyNTN;
    private String ID;
    private JSONObject Invoice;
    private String LastChangedBy;
    private String LastChangedDate;
    private String NetPrice;
    private String OrderDate;
    private String OrderId;
    private String OrderNumber;
    private String PaymentTermDescription;
    private String PaymentTermId;
    private String ReferenceID;
    private String ReturnDate;
    private String ReviseDate;
    private String SalesPointTitle;
    private String SalespointId;
    private String ShippmentDate;
    private String ShippmentNo;
    private String Status;
    private String TotalPrice;
    private String TransportTypeDescription;
    private String TransportTypeId;
    private String TransportTypeIdFreightCharges;
    private JSONObject deliveryCorp;
    private String deliveryNoteID;
    private String deliveryNoteStatus;
    private String goodsreceivenotesReceiveQty;
    private String goodsreceivenotesReceivingDate;
    private String orderStatus;
    private String ordersBillingAddress;
    private String ordersID;
    private String ordersOrderNumber;
    private String ordersShippingAddress;

    public Distributor_ShipmentModel(String comments, String companyId, String createdBy, String createdDate, String deliveryDate, String deliveryNumber, String discount, String distComment, JSONObject distributor, String distributorCompanyName, String distributorCreatedDate, String distributorDealerCode, String distributorEmail, String distributorFirstName, String distributorId, String distributorLastName, String distributorMobile, String distributorPhone, String distributorsCompanyNTN, String ID, JSONObject invoice, String lastChangedBy, String lastChangedDate, String netPrice, String orderDate, String orderId, String orderNumber, String paymentTermDescription, String paymentTermId, String referenceID, String returnDate, String reviseDate, String salesPointTitle, String salespointId, String shippmentDate, String shippmentNo, String status, String totalPrice, String transportTypeDescription, String transportTypeId, String transportTypeIdFreightCharges, JSONObject deliveryCorp, String deliveryNoteID, String deliveryNoteStatus, String goodsreceivenotesReceiveQty, String goodsreceivenotesReceivingDate, String orderStatus, String ordersBillingAddress, String ordersID, String ordersOrderNumber, String ordersShippingAddress) {
        Comments = comments;
        CompanyId = companyId;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        DeliveryDate = deliveryDate;
        DeliveryNumber = deliveryNumber;
        Discount = discount;
        DistComment = distComment;
        Distributor = distributor;
        DistributorCompanyName = distributorCompanyName;
        DistributorCreatedDate = distributorCreatedDate;
        DistributorDealerCode = distributorDealerCode;
        DistributorEmail = distributorEmail;
        DistributorFirstName = distributorFirstName;
        DistributorId = distributorId;
        DistributorLastName = distributorLastName;
        DistributorMobile = distributorMobile;
        DistributorPhone = distributorPhone;
        DistributorsCompanyNTN = distributorsCompanyNTN;
        this.ID = ID;
        Invoice = invoice;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        NetPrice = netPrice;
        OrderDate = orderDate;
        OrderId = orderId;
        OrderNumber = orderNumber;
        PaymentTermDescription = paymentTermDescription;
        PaymentTermId = paymentTermId;
        ReferenceID = referenceID;
        ReturnDate = returnDate;
        ReviseDate = reviseDate;
        SalesPointTitle = salesPointTitle;
        SalespointId = salespointId;
        ShippmentDate = shippmentDate;
        ShippmentNo = shippmentNo;
        Status = status;
        TotalPrice = totalPrice;
        TransportTypeDescription = transportTypeDescription;
        TransportTypeId = transportTypeId;
        TransportTypeIdFreightCharges = transportTypeIdFreightCharges;
        this.deliveryCorp = deliveryCorp;
        this.deliveryNoteID = deliveryNoteID;
        this.deliveryNoteStatus = deliveryNoteStatus;
        this.goodsreceivenotesReceiveQty = goodsreceivenotesReceiveQty;
        this.goodsreceivenotesReceivingDate = goodsreceivenotesReceivingDate;
        this.orderStatus = orderStatus;
        this.ordersBillingAddress = ordersBillingAddress;
        this.ordersID = ordersID;
        this.ordersOrderNumber = ordersOrderNumber;
        this.ordersShippingAddress = ordersShippingAddress;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
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

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getDeliveryNumber() {
        return DeliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        DeliveryNumber = deliveryNumber;
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

    public JSONObject getDistributor() {
        return Distributor;
    }

    public void setDistributor(JSONObject distributor) {
        Distributor = distributor;
    }

    public String getDistributorCompanyName() {
        return DistributorCompanyName;
    }

    public void setDistributorCompanyName(String distributorCompanyName) {
        DistributorCompanyName = distributorCompanyName;
    }

    public String getDistributorCreatedDate() {
        return DistributorCreatedDate;
    }

    public void setDistributorCreatedDate(String distributorCreatedDate) {
        DistributorCreatedDate = distributorCreatedDate;
    }

    public String getDistributorDealerCode() {
        return DistributorDealerCode;
    }

    public void setDistributorDealerCode(String distributorDealerCode) {
        DistributorDealerCode = distributorDealerCode;
    }

    public String getDistributorEmail() {
        return DistributorEmail;
    }

    public void setDistributorEmail(String distributorEmail) {
        DistributorEmail = distributorEmail;
    }

    public String getDistributorFirstName() {
        return DistributorFirstName;
    }

    public void setDistributorFirstName(String distributorFirstName) {
        DistributorFirstName = distributorFirstName;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public String getDistributorLastName() {
        return DistributorLastName;
    }

    public void setDistributorLastName(String distributorLastName) {
        DistributorLastName = distributorLastName;
    }

    public String getDistributorMobile() {
        return DistributorMobile;
    }

    public void setDistributorMobile(String distributorMobile) {
        DistributorMobile = distributorMobile;
    }

    public String getDistributorPhone() {
        return DistributorPhone;
    }

    public void setDistributorPhone(String distributorPhone) {
        DistributorPhone = distributorPhone;
    }

    public String getDistributorsCompanyNTN() {
        return DistributorsCompanyNTN;
    }

    public void setDistributorsCompanyNTN(String distributorsCompanyNTN) {
        DistributorsCompanyNTN = distributorsCompanyNTN;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public JSONObject getInvoice() {
        return Invoice;
    }

    public void setInvoice(JSONObject invoice) {
        Invoice = invoice;
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

    public String getNetPrice() {
        return NetPrice;
    }

    public void setNetPrice(String netPrice) {
        NetPrice = netPrice;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getPaymentTermDescription() {
        return PaymentTermDescription;
    }

    public void setPaymentTermDescription(String paymentTermDescription) {
        PaymentTermDescription = paymentTermDescription;
    }

    public String getPaymentTermId() {
        return PaymentTermId;
    }

    public void setPaymentTermId(String paymentTermId) {
        PaymentTermId = paymentTermId;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public String getReviseDate() {
        return ReviseDate;
    }

    public void setReviseDate(String reviseDate) {
        ReviseDate = reviseDate;
    }

    public String getSalesPointTitle() {
        return SalesPointTitle;
    }

    public void setSalesPointTitle(String salesPointTitle) {
        SalesPointTitle = salesPointTitle;
    }

    public String getSalespointId() {
        return SalespointId;
    }

    public void setSalespointId(String salespointId) {
        SalespointId = salespointId;
    }

    public String getShippmentDate() {
        return ShippmentDate;
    }

    public void setShippmentDate(String shippmentDate) {
        ShippmentDate = shippmentDate;
    }

    public String getShippmentNo() {
        return ShippmentNo;
    }

    public void setShippmentNo(String shippmentNo) {
        ShippmentNo = shippmentNo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getTransportTypeDescription() {
        return TransportTypeDescription;
    }

    public void setTransportTypeDescription(String transportTypeDescription) {
        TransportTypeDescription = transportTypeDescription;
    }

    public String getTransportTypeId() {
        return TransportTypeId;
    }

    public void setTransportTypeId(String transportTypeId) {
        TransportTypeId = transportTypeId;
    }

    public String getTransportTypeIdFreightCharges() {
        return TransportTypeIdFreightCharges;
    }

    public void setTransportTypeIdFreightCharges(String transportTypeIdFreightCharges) {
        TransportTypeIdFreightCharges = transportTypeIdFreightCharges;
    }

    public JSONObject getDeliveryCorp() {
        return deliveryCorp;
    }

    public void setDeliveryCorp(JSONObject deliveryCorp) {
        this.deliveryCorp = deliveryCorp;
    }

    public String getDeliveryNoteID() {
        return deliveryNoteID;
    }

    public void setDeliveryNoteID(String deliveryNoteID) {
        this.deliveryNoteID = deliveryNoteID;
    }

    public String getDeliveryNoteStatus() {
        return deliveryNoteStatus;
    }

    public void setDeliveryNoteStatus(String deliveryNoteStatus) {
        this.deliveryNoteStatus = deliveryNoteStatus;
    }

    public String getGoodsreceivenotesReceiveQty() {
        return goodsreceivenotesReceiveQty;
    }

    public void setGoodsreceivenotesReceiveQty(String goodsreceivenotesReceiveQty) {
        this.goodsreceivenotesReceiveQty = goodsreceivenotesReceiveQty;
    }

    public String getGoodsreceivenotesReceivingDate() {
        return goodsreceivenotesReceivingDate;
    }

    public void setGoodsreceivenotesReceivingDate(String goodsreceivenotesReceivingDate) {
        this.goodsreceivenotesReceivingDate = goodsreceivenotesReceivingDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrdersBillingAddress() {
        return ordersBillingAddress;
    }

    public void setOrdersBillingAddress(String ordersBillingAddress) {
        this.ordersBillingAddress = ordersBillingAddress;
    }

    public String getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(String ordersID) {
        this.ordersID = ordersID;
    }

    public String getOrdersOrderNumber() {
        return ordersOrderNumber;
    }

    public void setOrdersOrderNumber(String ordersOrderNumber) {
        this.ordersOrderNumber = ordersOrderNumber;
    }

    public String getOrdersShippingAddress() {
        return ordersShippingAddress;
    }

    public void setOrdersShippingAddress(String ordersShippingAddress) {
        this.ordersShippingAddress = ordersShippingAddress;
    }
}
