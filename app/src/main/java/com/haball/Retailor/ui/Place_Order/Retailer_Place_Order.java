package com.haball.Retailor.ui.Place_Order;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Tabs.Order_Summary;
import com.haball.Loader;
import com.haball.NonSwipeableViewPager;
import com.haball.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
import com.haball.Retailor.ui.Place_Order.ui.main.SectionsPagerAdapter;
import com.haball.Retailor.ui.Place_Order.ui.main.Tabs.Retailer_Order_Summary;

import java.lang.reflect.Type;
import java.util.List;

public class Retailer_Place_Order extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("Place_Order", "Opened new place order");
        View root = inflater.inflate(R.layout.activity_retailer_place_order, container, false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        final ViewPager viewPager = root.findViewById(R.id.view_pager_rpoid);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs_rpoid);
        tabs.setupWithViewPager(viewPager);

        LinearLayout tabStrip = ((LinearLayout) tabs.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

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
                            fragmentTransaction.add(R.id.main_container_ret, new Retailer_Order_Summary());
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
            SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", "");
            editor.putString("selected_products_qty", "");
            editor.apply();


            SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("DealerInfo",
                    Context.MODE_PRIVATE);

            SharedPreferences.Editor sharedPreferences1_editor = sharedPreferences1.edit();
            sharedPreferences1_editor.putString("DealerCode", "");
            sharedPreferences1_editor.putString("orderId", "0");
            sharedPreferences1_editor.apply();

        }

        return root;
    }
}