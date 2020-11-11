package com.haball.Retailor.ui.Support;

public class SupportDashboardRetailerModel {
    String ID, TicketNumber, CreatedDate, IssueType, Status, BusinessName, EmailAddress, MobileNo, Criticality, ContactMethod, Comment;

    public SupportDashboardRetailerModel(String ID, String ticketNumber, String createdDate, String issueType, String status, String businessName, String emailAddress, String mobileNo, String criticality, String contactMethod, String comment) {
        this.ID = ID;
        TicketNumber = ticketNumber;
        CreatedDate = createdDate;
        IssueType = issueType;
        Status = status;
        BusinessName = businessName;
        EmailAddress = emailAddress;
        MobileNo = mobileNo;
        Criticality = criticality;
        ContactMethod = contactMethod;
        Comment = comment;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTicketNumber() {
        return TicketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        TicketNumber = ticketNumber;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
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

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getCriticality() {
        return Criticality;
    }

    public void setCriticality(String criticality) {
        Criticality = criticality;
    }

    public String getContactMethod() {
        return ContactMethod;
    }

    public void setContactMethod(String contactMethod) {
        ContactMethod = contactMethod;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
