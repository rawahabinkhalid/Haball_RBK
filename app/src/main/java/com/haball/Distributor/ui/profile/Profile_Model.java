package com.haball.Distributor.ui.profile;

public class Profile_Model {
    private String DealerCode;
    private String CreatedDate;
    private String FirstName;
    private String LastName;
    private String CompanyName;
    private String CompanyNTN;
    private String CNIC;
    private String Mobile;
    private String Email;
    private String Phone;
    private String Address;


    public Profile_Model() {
    }


    public Profile_Model(String dealerCode, String createdDate, String firstName, String lastName, String companyName, String companyNTN, String CNIC, String mobile, String email, String phone, String address) {
        DealerCode = dealerCode;
        CreatedDate = createdDate;
        FirstName = firstName;
        LastName = lastName;
        CompanyName = companyName;
        CompanyNTN = companyNTN;
        this.CNIC = CNIC;
        Mobile = mobile;
        Email = email;
        Phone = phone;
        Address = address;
    }

    public String getDealerCode() {
        return DealerCode;
    }

    public void setDealerCode(String dealerCode) {
        DealerCode = dealerCode;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyNTN() {
        return CompanyNTN;
    }

    public void setCompanyNTN(String companyNTN) {
        CompanyNTN = companyNTN;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
