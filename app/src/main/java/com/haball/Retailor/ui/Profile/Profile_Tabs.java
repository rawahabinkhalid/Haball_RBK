package com.haball.Retailor.ui.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haball.R;
import com.haball.Retailor.ui.Profile.ui.main.SectionsPagerAdapter;
import com.haball.Retailor.ui.Profile.ui.main.SectionsPagerAdapter_Without_Change_Password;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Profile_Tabs extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate( R.layout.fragment_profile__tabs, container, false);

        SharedPreferences dashboardRights = getContext().getSharedPreferences("Retailer_UserRights",
                Context.MODE_PRIVATE);
        boolean User_Change_Password = Boolean.parseBoolean(dashboardRights.getString("User_Change_Password", "false"));

        if(User_Change_Password) {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager7);
            viewPager.setAdapter(sectionsPagerAdapter);
            final TabLayout tabs = root.findViewById(R.id.tab7);
            tabs.setupWithViewPager(viewPager);
        } else {
            SectionsPagerAdapter_Without_Change_Password sectionsPagerAdapter = new SectionsPagerAdapter_Without_Change_Password(getActivity(), getChildFragmentManager());
            final ViewPager viewPager = root.findViewById(R.id.view_pager7);
            viewPager.setAdapter(sectionsPagerAdapter);
            final TabLayout tabs = root.findViewById(R.id.tab7);
            tabs.setupWithViewPager(viewPager);

        }
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
