package com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models;

import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Parent;

import java.util.List;
import java.util.UUID;

//public class OrderParentlist_Model extends SimpleParent<OrderChildlist_Model> {
public class OrderParentlist_Model extends SimpleParent<OrderChildlist_Model> implements Parent<OrderChildlist_Model> {
    private List<Object> myCHildrenList;
    private UUID _id;
    private String CategoryId;
    private String Title;
    private String ParentId;
    public Boolean expanded = false;

    public OrderParentlist_Model(List<Object> myCHildrenList, UUID _id, String categoryId, String title, String parentId, Boolean expanded) {
        super(null);
        this.myCHildrenList = myCHildrenList;
        this._id = _id;
        CategoryId = categoryId;
        Title = title;
        ParentId = parentId;
        this.expanded = expanded;
    }

    protected OrderParentlist_Model(List<OrderChildlist_Model> childItemList) {
        super(childItemList);
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    @Override
    public List getChildList() {
        return myCHildrenList;
    }

    @Override
    public void setChildList(List list) {
        myCHildrenList = list;
    }
}
