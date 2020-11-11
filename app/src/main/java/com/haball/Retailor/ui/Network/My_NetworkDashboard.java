package com.haball.Retailor.ui.Network;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haball.Distributor.ui.shipments.ShipmentsViewModel;
import com.haball.NonSwipeableViewPager;
import com.haball.R;
import com.haball.Retailor.ui.Network.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class My_NetworkDashboard extends Fragment {
    private ShipmentsViewModel sendViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.distributor_shipment__view_dashboard_fragment, container, false);
        sendViewModel =
                ViewModelProviders.of(this).get(ShipmentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_my__network_dashboard, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getChildFragmentManager());
        NonSwipeableViewPager viewPager = root.findViewById(R.id.view_pager4);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabs = root.findViewById(R.id.tabs4);
        tabs.setupWithViewPager(viewPager);

        LinearLayout tabStrip = ((LinearLayout)tabs.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        return root;

    }
}