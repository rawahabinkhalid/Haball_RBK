package com.haball.Invoice;

import android.os.Bundle;

import com.haball.Distributor.ui.shipments.ShipmentsViewModel;
import com.haball.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.haball.Invoice.ui.main.SectionsPagerAdapter;

public class Distributor_Invoice_DashBoard extends Fragment {
    private ShipmentsViewModel sendViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.distributor_shipment__view_dashboard_fragment, container, false);
//        sendViewModel =
//                ViewModelProviders.of(this).get(ShipmentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_distributor__invoice__dash_board, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager3);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs3);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        return root;
    }
    }
