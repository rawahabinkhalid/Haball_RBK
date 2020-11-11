package com.haball.Distributor.ui.main.invoice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.haball.Distributor.ui.main.ui.main.SectionsPagerAdapter_WithoutPayments;
import com.haball.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.haball.Distributor.ui.main.invoice.ui.main.SectionsPagerAdapter;

public class ViewInvoice extends Fragment {
    private String InvoiceStatus;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_view_order, container, false);
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
//        final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_order);
//        viewPager.setOffscreenPageLimit(3);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = root.findViewById(R.id.tabs_ret_view_order);
//        tabs.setupWithViewPager(viewPager);
//        View root = inflater.inflate(R.layout.activity_retailer_main_view_order, container, false);
//        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("OrderId",
//                Context.MODE_PRIVATE);
//        InvoiceStatus = sharedPreferences3.getString("InvoiceStatus", "");
//        String OrderStatus = sharedPreferences3.getString("Status", "");
//        String InvoiceUpload = sharedPreferences3.getString("InvoiceUpload", "");
//        Log.i("InvoiceStatus", InvoiceStatus);
//        Log.i("OrderStatus", OrderStatus);
//
////        SectionsPagerAdapter sectionsPagerAdapter = null;
//        if (InvoiceStatus.equals("null") || InvoiceStatus.equals("Pending") || OrderStatus.equals("Pending") || ((InvoiceUpload.equals("0")) && OrderStatus.equals("Cancelled"))) {
//            SectionsPagerAdapter_WithoutPayments sectionsPagerAdapter = new SectionsPagerAdapter_WithoutPayments(getActivity(), getChildFragmentManager());
//            final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_order);
//            viewPager.setOffscreenPageLimit(3);
//            viewPager.setAdapter(sectionsPagerAdapter);
//            TabLayout tabs = root.findViewById(R.id.tabs_ret_view_order);
//            tabs.setupWithViewPager(viewPager);
//        } else {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_order);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs_ret_view_order);
        tabs.setupWithViewPager(viewPager);
//        }

        return root;
    }
}