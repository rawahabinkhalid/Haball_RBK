package com.haball.Retailor.ui.Dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.haball.R;
import com.haball.Retailor.ui.Dashboard.ui.main.SectionsPagerAdapter;
import com.haball.Retailor.ui.Dashboard.ui.main.SectionsPagerAdapter_Without_Orders;
import com.haball.Retailor.ui.Dashboard.ui.main.SectionsPagerAdapter_Without_Payments;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Dashboard_Tabs extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_dashboard__tabs, container, false);

        SharedPreferences selectedTab = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        int value = Integer.parseInt(selectedTab.getString("TabNo", "0"));
        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
        editorOrderTabsFromDraft.putString("TabNo", "0");
        editorOrderTabsFromDraft.apply();

        SharedPreferences retailerInfo = getContext().getSharedPreferences("Menu_Retailer",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
        retailerInfo_editor.putString("groupPosition", String.valueOf(0));
        retailerInfo_editor.apply();


        SharedPreferences dashboardRights = getContext().getSharedPreferences("Retailer_UserRights",
                Context.MODE_PRIVATE);
        boolean Order_View = Boolean.parseBoolean(dashboardRights.getString("Order_View", "false"));
        boolean Payment_View = Boolean.parseBoolean(dashboardRights.getString("Payment_View", "false"));

        if(!Order_View && Payment_View) {
            SectionsPagerAdapter_Without_Orders sectionsPagerAdapter = new SectionsPagerAdapter_Without_Orders(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager_ret5);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs_ret5);
            tabs.setupWithViewPager(viewPager);
        } else if(!Payment_View && Order_View) {
            SectionsPagerAdapter_Without_Payments sectionsPagerAdapter = new SectionsPagerAdapter_Without_Payments(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager_ret5);
            viewPager.setOffscreenPageLimit(3);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = root.findViewById(R.id.tabs_ret5);
            tabs.setupWithViewPager(viewPager);
        } else if (Order_View && Payment_View) {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager_ret5);
            viewPager.setAdapter(sectionsPagerAdapter);
            viewPager.setCurrentItem(value);
            final TabLayout tabs = root.findViewById(R.id.tabs_ret5);
//        tabs.setPadding(0,0,0,0);
            tabs.setupWithViewPager(viewPager);
        } else {

        }

        return root;

    }}