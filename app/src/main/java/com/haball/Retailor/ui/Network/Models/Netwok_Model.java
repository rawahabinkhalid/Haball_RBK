package com.haball.Retailor.ui.Network.Models;

public class Netwok_Model {
    private String CompanyName;
    private String Mobile;
    private String Address;
    private String Status;

    public Netwok_Model() {
    }

    public Netwok_Model(String companyName, String mobile, String address, String status) {
        CompanyName = companyName;
        Mobile = mobile;
        Address = address;
        Status = status;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
