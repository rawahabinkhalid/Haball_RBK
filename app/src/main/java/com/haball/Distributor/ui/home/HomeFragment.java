package com.haball.Distributor.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.main.SectionsPagerAdapter;
import com.haball.Distributor.ui.main.SectionsPagerAdapter_Only_Dashboard;
import com.haball.Distributor.ui.main.SectionsPagerAdapter_Only_Orders;
import com.haball.Distributor.ui.main.SectionsPagerAdapter_Only_Payments;
import com.haball.Distributor.ui.main.SectionsPagerAdapter_Without_Dashboard;
import com.haball.Distributor.ui.main.SectionsPagerAdapter_Without_Orders;
import com.haball.Distributor.ui.main.SectionsPagerAdapter_Without_Payments;
import com.haball.R;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private int myVal = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences selectedTab = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        int value = Integer.parseInt(selectedTab.getString("TabNo", "0"));
        if (myVal == -1)
            myVal = value;
        Log.i("OrderTabsFromDraft", String.valueOf(myVal));

//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
//        final ViewPager viewPager = root.findViewById(R.id.view_pager1);
//        viewPager.setOffscreenPageLimit(3);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        if (myVal != -1)
//            viewPager.setCurrentItem(myVal);
//        TabLayout tabs = root.findViewById(R.id.tabs1);
//        tabs.setupWithViewPager(viewPager);


        SharedPreferences dashboardRights = getContext().getSharedPreferences("Distributor_UserRights",
                Context.MODE_PRIVATE);
        boolean Order_View = Boolean.parseBoolean(dashboardRights.getString("Order_View", "false"));
        boolean PrepaidRequestView = Boolean.parseBoolean(dashboardRights.getString("PrepaidRequestView", "false"));
        boolean Invoice_View = Boolean.parseBoolean(dashboardRights.getString("Invoice_View", "false"));
        boolean DashBoardView = Boolean.parseBoolean(dashboardRights.getString("DashBoardView", "false"));

        if(!Order_View && !PrepaidRequestView && !Invoice_View && DashBoardView) {                      // Only Dashboard View
            SectionsPagerAdapter_Only_Dashboard sectionsPagerAdapter = new SectionsPagerAdapter_Only_Dashboard(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager1);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs1);
            tabs.setupWithViewPager(viewPager);
        } else if(!Order_View && (PrepaidRequestView || Invoice_View) && !DashBoardView) {              // Only Payment View
            SectionsPagerAdapter_Only_Payments sectionsPagerAdapter = new SectionsPagerAdapter_Only_Payments(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager1);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs1);
            tabs.setupWithViewPager(viewPager);
        } else if(Order_View && !PrepaidRequestView && !Invoice_View && !DashBoardView) {              // Only Order View
            SectionsPagerAdapter_Only_Orders sectionsPagerAdapter = new SectionsPagerAdapter_Only_Orders(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager1);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs1);
            tabs.setupWithViewPager(viewPager);
        } else if(!Order_View && (PrepaidRequestView || Invoice_View) && DashBoardView) {              // Dashboard and Payment View
            SectionsPagerAdapter_Without_Orders sectionsPagerAdapter = new SectionsPagerAdapter_Without_Orders(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager1);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs1);
            tabs.setupWithViewPager(viewPager);
        } else if(Order_View && (PrepaidRequestView || Invoice_View) && !DashBoardView) {              // Order and Payment View
            SectionsPagerAdapter_Without_Dashboard sectionsPagerAdapter = new SectionsPagerAdapter_Without_Dashboard(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager1);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs1);
            tabs.setupWithViewPager(viewPager);
        } else if(Order_View && !PrepaidRequestView && !Invoice_View && DashBoardView) {              // Dashboard and Order View
            SectionsPagerAdapter_Without_Payments sectionsPagerAdapter = new SectionsPagerAdapter_Without_Payments(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager1);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs1);
            tabs.setupWithViewPager(viewPager);
        } else if (Order_View && (PrepaidRequestView || Invoice_View) && DashBoardView) {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager1);
            viewPager.setAdapter(sectionsPagerAdapter);
            viewPager.setCurrentItem(value);
            final TabLayout tabs = root.findViewById(R.id.tabs1);
//        tabs.setPadding(0,0,0,0);
            tabs.setupWithViewPager(viewPager);
        } else {

        }





        return root;
    }

}