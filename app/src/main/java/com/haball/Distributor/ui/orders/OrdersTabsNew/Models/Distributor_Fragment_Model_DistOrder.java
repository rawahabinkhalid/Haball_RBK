package com.haball.Distributor.ui.orders.OrdersTabsNew.Models;

public class Distributor_Fragment_Model_DistOrder {
    String ID, Name, Code, SECP, SubscriptionType, Address1, Address2,  CountryId, CityId, ProvinceId, PostCode, URL, EmailAddress, Phone, Mobile, OtherContact, ContactPerson, NTN, Status;

    public Distributor_Fragment_Model_DistOrder(String ID, String name, String code, String SECP, String subscriptionType, String address1, String address2, String countryId, String cityId, String provinceId, String postCode, String URL, String emailAddress, String phone, String mobile, String otherContact, String contactPerson, String NTN, String status) {
        this.ID = ID;
        Name = name;
        Code = code;
        this.SECP = SECP;
        SubscriptionType = subscriptionType;
        Address1 = address1;
        Address2 = address2;
        CountryId = countryId;
        CityId = cityId;
        ProvinceId = provinceId;
        PostCode = postCode;
        this.URL = URL;
        EmailAddress = emailAddress;
        Phone = phone;
        Mobile = mobile;
        OtherContact = otherContact;
        ContactPerson = contactPerson;
        this.NTN = NTN;
        Status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getSECP() {
        return SECP;
    }

    public void setSECP(String SECP) {
        this.SECP = SECP;
    }

    public String getSubscriptionType() {
        return SubscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        SubscriptionType = subscriptionType;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getOtherContact() {
        return OtherContact;
    }

    public void setOtherContact(String otherContact) {
        OtherContact = otherContact;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getNTN() {
        return NTN;
    }

    public void setNTN(String NTN) {
        this.NTN = NTN;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
