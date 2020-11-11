package com.haball.Payment;

public class DistributorPaymentRequestModel {
    private String BankIMD;
    private String CompanyCNIC;
    private String CompanyId;
    private String CompanyName;
    private String CreatedBy;
    private String CreatedDate;
    private String DistributorCNIC;
    private String DistributorId;
    private String DistributorName;
    private String ID;
    private String isInvoice;
    private String IsTransmitted;
    private String LastChangedBy;
    private String LastChangedDate;
    private String PaidAmount;
    private String PaidDate;
    private String PrePaidNumber;
    private String PrepaidStatusValue;
    private String ReferenceID;
    private String State;
    private String Status;
    private String employeesName;

    public DistributorPaymentRequestModel(String bankIMD, String companyCNIC, String companyId, String companyName, String createdBy, String createdDate, String distributorCNIC, String distributorId, String distributorName, String ID, String isInvoice, String isTransmitted, String lastChangedBy, String lastChangedDate, String paidAmount, String paidDate, String prePaidNumber, String prepaidStatusValue, String referenceID, String state, String status, String employeesName) {
        BankIMD = bankIMD;
        CompanyCNIC = companyCNIC;
        CompanyId = companyId;
        CompanyName = companyName;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        DistributorCNIC = distributorCNIC;
        DistributorId = distributorId;
        DistributorName = distributorName;
        this.ID = ID;
        this.isInvoice = isInvoice;
        IsTransmitted = isTransmitted;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        PaidAmount = paidAmount;
        PaidDate = paidDate;
        PrePaidNumber = prePaidNumber;
        PrepaidStatusValue = prepaidStatusValue;
        ReferenceID = referenceID;
        State = state;
        Status = status;
        this.employeesName = employeesName;
    }

    public String getBankIMD() {
        return BankIMD;
    }

    public void setBankIMD(String bankIMD) {
        BankIMD = bankIMD;
    }

    public String getCompanyCNIC() {
        return CompanyCNIC;
    }

    public void setCompanyCNIC(String companyCNIC) {
        CompanyCNIC = companyCNIC;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public String getDistributorCNIC() {
        return DistributorCNIC;
    }

    public void setDistributorCNIC(String distributorCNIC) {
        DistributorCNIC = distributorCNIC;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getIsTransmitted() {
        return IsTransmitted;
    }

    public void setIsTransmitted(String isTransmitted) {
        IsTransmitted = isTransmitted;
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

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getPrePaidNumber() {
        return PrePaidNumber;
    }

    public void setPrePaidNumber(String prePaidNumber) {
        PrePaidNumber = prePaidNumber;
    }

    public String getPrepaidStatusValue() {
        return PrepaidStatusValue;
    }

    public void setPrepaidStatusValue(String prepaidStatusValue) {
        PrepaidStatusValue = prepaidStatusValue;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEmployeesName() {
        return employeesName;
    }

    public void setEmployeesName(String employeesName) {
        this.employeesName = employeesName;
    }
}
