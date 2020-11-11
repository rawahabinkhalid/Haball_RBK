package com.haball.Retailor.ui.Place_Order.ui.main.Models;

public class Company_Fragment_Model {
    String DealerCode;
    String CompanyName;
    String Mobile;
    String Email;
    String CNIC;
    String CompanyNTN;
    String Address;

    public Company_Fragment_Model(String dealerCode, String companyName, String mobile, String email, String CNIC, String companyNTN, String address) {
        DealerCode = dealerCode;
        CompanyName = companyName;
        Mobile = mobile;
        Email = email;
        this.CNIC = CNIC;
        CompanyNTN = companyNTN;
        Address = address;
    }

    public String getDealerCode() {
        return DealerCode;
    }

    public void setDealerCode(String dealerCode) {
        DealerCode = dealerCode;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getCompanyNTN() {
        return CompanyNTN;
    }

    public void setCompanyNTN(String companyNTN) {
        CompanyNTN = companyNTN;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
