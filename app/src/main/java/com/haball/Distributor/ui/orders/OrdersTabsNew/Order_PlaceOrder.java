package com.haball.Distributor.ui.orders.OrdersTabsNew;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.Dist_Order_Summary;
import com.haball.Loader;
import com.haball.NonSwipeableViewPager;
import com.haball.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
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

import com.haball.Distributor.ui.orders.OrdersTabsNew.ui.main.SectionsPagerAdapter;
import com.haball.Retailor.ui.Place_Order.ui.main.Tabs.Retailer_Order_Summary;

import org.json.JSONException;

public class Order_PlaceOrder extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.activity_order__place_order, container, false);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        final ViewPager viewPager = root.findViewById(R.id.view_pager5);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = root.findViewById(R.id.tabs5);
        tabs.setupWithViewPager(viewPager);

        SharedPreferences selectedProductsSP = getContext().getSharedPreferences("FromDraft",
                Context.MODE_PRIVATE);
        if (selectedProductsSP.getString("fromDraft", "").equals("draft")) {
//            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
            viewPager.setCurrentItem(1);
            tabs.getTabAt(1);

            final Loader loader = new Loader(getContext());
            loader.showLoader();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loader.hideLoader();

                            FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }, 3000);
                }
            });

            SharedPreferences orderCheckout1 = getContext().getSharedPreferences("FromDraft_Temp",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor orderCheckout_editor1 = orderCheckout1.edit();
            orderCheckout_editor1.putString("fromDraft", "draft");
            orderCheckout_editor1.apply();

            SharedPreferences orderCheckout1_temp = getContext().getSharedPreferences("FromDraft",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor orderCheckout_editor1_temp = orderCheckout1_temp.edit();
            orderCheckout_editor1_temp.putString("fromDraft", "");
            orderCheckout_editor1_temp.apply();

        } else {
            SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
            orderCheckout_editor.putString("orderCheckout", "");
            orderCheckout_editor.apply();

            SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", "");
            editor.putString("selected_products_qty", "");
            editor.putString("selected_products_category", "");
            editor.apply();

            SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_company = companyInfo.edit();
            editor_company.putString("CompanyId", "");
            editor_company.putString("ID", "0");
            editor_company.apply();

        }

        LinearLayout tabStrip = ((LinearLayout) tabs.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
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