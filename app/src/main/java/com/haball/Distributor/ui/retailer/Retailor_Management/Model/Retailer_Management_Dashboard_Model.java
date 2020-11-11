package com.haball.Distributor.ui.retailer.Retailor_Management.Model;

public class Retailer_Management_Dashboard_Model {
    String ID, RetailerCode, CompanyName, Status, RetailerStatus, CreatedDate;
    String PrepaidStatusValue;

    public Retailer_Management_Dashboard_Model(String ID, String retailerCode, String companyName, String status, String retailerStatus, String createdDate, String prepaidStatusValue) {
        this.ID = ID;
        RetailerCode = retailerCode;
        CompanyName = companyName;
        Status = status;
        RetailerStatus = retailerStatus;
        CreatedDate = createdDate;
        PrepaidStatusValue = prepaidStatusValue;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRetailerCode() {
        return RetailerCode;
    }

    public void setRetailerCode(String retailerCode) {
        RetailerCode = retailerCode;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRetailerStatus() {
        return RetailerStatus;
    }

    public void setRetailerStatus(String retailerStatus) {
        RetailerStatus = retailerStatus;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getPrepaidStatusValue() {
        return PrepaidStatusValue;
    }

    public void setPrepaidStatusValue(String prepaidStatusValue) {
        PrepaidStatusValue = prepaidStatusValue;
    }
}
