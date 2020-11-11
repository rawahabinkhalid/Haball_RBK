package com.haball.Support.Support_Retailer.Model;

public class SupportDashboardModel {
    String Id, IssueType, Status, CreatedDate, Criticality, PreferredContactMethod, Description, ContactName, Email, MobileNumber;

    public String getCriticality() {
        return Criticality;
    }

    public void setCriticality(String criticality) {
        Criticality = criticality;
    }

    public String getPreferredContactMethod() {
        return PreferredContactMethod;
    }

    public void setPreferredContactMethod(String preferredContactMethod) {
        PreferredContactMethod = preferredContactMethod;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public SupportDashboardModel(String id, String issueType, String status, String createdDate, String criticality, String preferredContactMethod, String description, String contactName, String email, String mobileNumber) {
        Id = id;
        IssueType = issueType;
        Status = status;
        CreatedDate = createdDate;
        Criticality = criticality;
        PreferredContactMethod = preferredContactMethod;
        Description = description;
        ContactName = contactName;
        Email = email;
        MobileNumber = mobileNumber;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIssueType() {
        return IssueType;
    }

    public void setIssueType(String issueType) {
        IssueType = issueType;
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

    public void setCreatedDate(String cretaedDate) {
        CreatedDate = cretaedDate;
    }
}
