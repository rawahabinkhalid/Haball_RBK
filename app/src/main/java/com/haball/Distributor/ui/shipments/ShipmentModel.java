package com.haball.Distributor.ui.shipments;

public class ShipmentModel {
    String DeliveryNumber;
    String CompanyName;
    String DeliveryDate;
    String ReceivingDate;
    String Quantity;
    String ShipmentStatusValue;

    public String getDeliveryNumber() {
        return DeliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        DeliveryNumber = deliveryNumber;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getReceivingDate() {
        return ReceivingDate;
    }

    public void setReceivingDate(String receivingDate) {
        ReceivingDate = receivingDate;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getShipmentStatusValue() {
        return ShipmentStatusValue;
    }

    public void setShipmentStatusValue(String shipmentStatusValue) {
        ShipmentStatusValue = shipmentStatusValue;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    String Status;

    public ShipmentModel(String deliveryNumber, String companyName, String deliveryDate, String receivingDate, String quantity, String shipmentStatusValue, String status, String ID) {
        DeliveryNumber = deliveryNumber;
        CompanyName = companyName;
        DeliveryDate = deliveryDate;
        ReceivingDate = receivingDate;
        Quantity = quantity;
        ShipmentStatusValue = shipmentStatusValue;
        Status = status;
        this.ID = ID;
    }

    String ID;

}
