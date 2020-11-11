package com.haball.Distributor.ui.retailer.RetailerPlaceOrder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.haball.Distributor.ui.home.HomeViewModel;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.Dist_Order_Summary;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Tabs.Order_Summary;
import com.haball.Loader;
import com.haball.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.SectionsPagerAdapter;

public class RetailerPlaceOrder extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_retailer_place_order, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        final ViewPager viewPager = root.findViewById(R.id.view_pager_rpoid);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs_rpoid);
        tabs.setupWithViewPager(viewPager);

        SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
        orderCheckout_editor.putString("orderCheckout", "");
        orderCheckout_editor.apply();


        SharedPreferences selectedProductsSP = getContext().getSharedPreferences("FromDraft",
                Context.MODE_PRIVATE);
        if (selectedProductsSP.getString("fromDraft", "").equals("draft")) {
            viewPager.setCurrentItem(1);

            final Loader loader = new Loader(getContext());
            loader.showLoader();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loader.hideLoader();

                            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new Order_Summary());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }, 3000);
                }
            });

            SharedPreferences orderCheckout1 = getContext().getSharedPreferences("FromDraft",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor orderCheckout_editor1 = orderCheckout1.edit();
            orderCheckout_editor1.putString("fromDraft", "");
            orderCheckout_editor1.apply();

        } else {
            SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", "");
            editor.putString("selected_products_qty", "");
            editor.apply();
//            editor.clear();


            SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("RetailerInfo",
                    Context.MODE_PRIVATE);

            SharedPreferences.Editor sharedPreferences1_editor = sharedPreferences1.edit();
            sharedPreferences1_editor.putString("RetailerCode", "");
            sharedPreferences1_editor.putString("RetailerID", "");
            sharedPreferences1_editor.putString("orderId", "0");
            sharedPreferences1_editor.apply();
//            sharedPreferences1_editor.clear();

        }


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