package com.haball.Support.Support_Ditributor.Model;

public class SupportDashboardModel {
    String Id;
    String IssueType;
    String Criticality;
    String PreferredContactMethod;
    String Description;
    String ContactName;
    String Email;
    String MobileNumber;
    String Status;
    String DistributorId;
    String CreatedBy;
    String CreatedDate;
    String LastChangedBy;
    String LastChangedDate;
    String TicketNumber;

    public SupportDashboardModel(String id, String issueType, String criticality, String preferredContactMethod, String description, String contactName, String email, String mobileNumber, String status, String distributorId, String createdBy, String createdDate, String lastChangedBy, String lastChangedDate, String ticketNumber) {
        Id = id;
        IssueType = issueType;
        Criticality = criticality;
        PreferredContactMethod = preferredContactMethod;
        Description = description;
        ContactName = contactName;
        Email = email;
        MobileNumber = mobileNumber;
        Status = status;
        DistributorId = distributorId;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        TicketNumber = ticketNumber;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
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

    public String getTicketNumber() {
        return TicketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        TicketNumber = ticketNumber;
    }
}
