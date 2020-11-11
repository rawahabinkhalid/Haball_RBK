package com.haball.Distributor.ui.shipments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.haball.Distributor.ui.shipments.main.Adapters.SectionsPagerAdapter_WithoutPayment;
import com.haball.R;
import com.haball.Distributor.ui.shipments.main.Adapters.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DistributorShipment_ViewDashboard extends Fragment {

    private ShipmentsViewModel sendViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.distributor_shipment__view_dashboard_fragment, container, false);
        SharedPreferences shipmentid = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        String ShipmentStatusValue = shipmentid.getString("ShipmentStatusValue", "");
        sendViewModel =
                ViewModelProviders.of(this).get(ShipmentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_distributor_shipment__view_dashboard, container, false);
//        if(ShipmentStatusValue.equals("In Transit")) {
//            SectionsPagerAdapter_WithoutPayment sectionsPagerAdapter = new SectionsPagerAdapter_WithoutPayment(getContext(), getChildFragmentManager());
//            ViewPager viewPager = root.findViewById(R.id.view_pager2);
//            viewPager.setOffscreenPageLimit(5);
//            viewPager.setAdapter(sectionsPagerAdapter);
//            TabLayout tabs = root.findViewById(R.id.tabs2);
//            tabs.setupWithViewPager(viewPager);
//        } else {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
            ViewPager viewPager = root.findViewById(R.id.view_pager2);
            viewPager.setOffscreenPageLimit(5);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs2);
            tabs.setupWithViewPager(viewPager);
//        }

        return root;
    }



}
