package com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class TitleCreator {
    static TitleCreator _titleCreator;
    List<OrderParentlist_Model> _titleParents;

    public TitleCreator(Context context) {
        _titleParents = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            OrderParentlist_Model orderParentlist_model = new OrderParentlist_Model(String.format("Heading1", i));
//            _titleParents.add(orderParentlist_model);
//        }
    }


    public static TitleCreator get(Context context) {
        if (_titleCreator == null)
            _titleCreator = new TitleCreator(context);
        return _titleCreator;
    }

    public List<OrderParentlist_Model> getAll() {
            return _titleParents;
    }
}