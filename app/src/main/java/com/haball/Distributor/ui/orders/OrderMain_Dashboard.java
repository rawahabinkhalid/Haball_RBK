package com.haball.Distributor.ui.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.haball.Distributor.ui.orders.OrdersTabsLayout.Orders_Dashboard;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.R;

public class OrderMain_Dashboard extends Fragment {
    private Button btn_place_order;
    private FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_mian__dashboard, container, false);
        btn_place_order = root.findViewById(R.id.btn_place_order);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder());
                fragmentTransaction.commit();

            }
        });
        return  root;


    }
}
