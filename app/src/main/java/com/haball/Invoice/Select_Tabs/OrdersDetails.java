package com.haball.Invoice.Select_Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersDetails extends Fragment {


    public OrdersDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders_details, container, false);
    }

}
