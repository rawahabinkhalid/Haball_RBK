package com.haball.Distributor;

public class DistributorPaymentsModel {
    private String PrePaidNumber,Name,PaidAmount,Status ;

    public DistributorPaymentsModel(String prePaidNumber, String name, String paidAmount, String status) {
        PrePaidNumber = prePaidNumber;
        Name = name;
        PaidAmount = paidAmount;
        Status = status;
    }

    public String getPrePaidNumber() {
        return PrePaidNumber;
    }

    public void setPrePaidNumber(String prePaidNumber) {
        PrePaidNumber = prePaidNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
