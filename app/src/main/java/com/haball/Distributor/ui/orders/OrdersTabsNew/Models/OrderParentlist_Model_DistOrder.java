package com.haball.Distributor.ui.orders.OrdersTabsNew.Models;

import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Parent;

import java.util.List;
import java.util.UUID;

public class OrderParentlist_Model_DistOrder extends SimpleParent<OrderChildlist_Model_DistOrder> implements Parent<OrderChildlist_Model_DistOrder> {

    private List<Object> myCHildrenList;
    private UUID _id;
    private String CompanyId;
    private String Title;
    private String ParentId;
    private String ShortDescription;
    private String LongDescription;
    private String Status;
    private String ID;

    public OrderParentlist_Model_DistOrder(String iD, String companyId, String title, String parentId, String shortDescription, String longDescription, String status) {
        super(null);
        ID = iD;
        CompanyId = companyId;
        Title = title;
        ParentId = parentId;
        ShortDescription = shortDescription;
        LongDescription = longDescription;
        Status = status;
        _id = UUID.randomUUID();
    }

    protected OrderParentlist_Model_DistOrder(List childItemList) {
        super(childItemList);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Object> getMyCHildrenList() {
        return myCHildrenList;
    }

    public void setMyCHildrenList(List<Object> myCHildrenList) {
        this.myCHildrenList = myCHildrenList;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public void setChildList(List list) {
        myCHildrenList = list;
    }

    @Override
    public List getChildList() {
        return myCHildrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}