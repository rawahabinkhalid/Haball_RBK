package com.haball.Distributor.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haball.R;
import com.haball.Distributor.ui.profile.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Distributor_Profile extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate( R.layout.fragment_profile__tabs, container, false);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        final ViewPager viewPager = root.findViewById(R.id.view_pager7);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = root.findViewById(R.id.tab7);
        tabs.setupWithViewPager(viewPager);

//        LinearLayout tabStrip = ((LinearLayout)tabs.getChildAt(0));
//        for(int i = 0; i < tabStrip.getChildCount(); i++) {
//            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return true;
//                }
//            });
//        }
        return root;

    }

}