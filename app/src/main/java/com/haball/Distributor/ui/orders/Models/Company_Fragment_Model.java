package com.haball.Distributor.ui.orders.Models;

public class Company_Fragment_Model {
    String ID,Name;

    public Company_Fragment_Model(String id, String name) {
        ID = id;
        Name = name;
    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        ID = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
