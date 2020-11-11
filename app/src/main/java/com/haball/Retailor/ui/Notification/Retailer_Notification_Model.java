package com.haball.Retailor.ui.Notification;

public class Retailer_Notification_Model {
    String ID;
    String Subject;
    String AlertMessage;
    String Status;
    String seen;
    String ReadStatus;

    public Retailer_Notification_Model(String ID, String subject, String alertMessage, String status, String seen, String readStatus) {
        this.ID = ID;
        Subject = subject;
        AlertMessage = alertMessage;
        Status = status;
        this.seen = seen;
        ReadStatus = readStatus;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getAlertMessage() {
        return AlertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        AlertMessage = alertMessage;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getReadStatus() {
        return ReadStatus;
    }

    public void setReadStatus(String readStatus) {
        ReadStatus = readStatus;
    }
}
