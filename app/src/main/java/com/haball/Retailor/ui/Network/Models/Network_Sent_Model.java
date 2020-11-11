package com.haball.Retailor.ui.Network.Models;

public class Network_Sent_Model {
    private String Mobile;
    private String Address;

    public Network_Sent_Model() {
    }

    public Network_Sent_Model(String mobile, String address) {
        Mobile = mobile;
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
