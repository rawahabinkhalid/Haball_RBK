package com.haball.Payment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Consolidate_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_consolidate_, container, false);
//        recyclerView = root.findViewById(R.id.rv_consolidate);
//
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new Consolidate_Fragment_Adapter(this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Shield","12/02/2020","Rs. 600,000.00","600,000.00","Paid");
//
//        recyclerView.setAdapter(mAdapter);
        return root;
    }



}
