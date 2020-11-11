package com.haball.Payment;

public class ProofOfPaymentModel {
    String ID, POPNumber, PaymentMode, PaymentNumber, Status, CreatedDate;


    public ProofOfPaymentModel(String ID, String POPNumber, String paymentMode, String paymentNumber, String status, String createdDate) {
        this.ID = ID;
        this.POPNumber = POPNumber;
        PaymentMode = paymentMode;
        PaymentNumber = paymentNumber;
        Status = status;
        CreatedDate = createdDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPOPNumber() {
        return POPNumber;
    }

    public void setPOPNumber(String POPNumber) {
        this.POPNumber = POPNumber;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getPaymentNumber() {
        return PaymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        PaymentNumber = paymentNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }
}
