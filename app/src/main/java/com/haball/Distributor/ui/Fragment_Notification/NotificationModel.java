package com.haball.Distributor.ui.Fragment_Notification;

public class NotificationModel {
    String ID, AlertMessage, Subject;

    public NotificationModel(String ID, String alertMessage, String subject) {
        this.ID = ID;
        AlertMessage = alertMessage;
        Subject = subject;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAlertMessage() {
        return AlertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        AlertMessage = alertMessage;
    }
}
