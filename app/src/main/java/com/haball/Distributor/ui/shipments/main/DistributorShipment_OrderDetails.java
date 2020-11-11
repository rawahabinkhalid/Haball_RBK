package com.haball.Distributor.ui.shipments.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.haball.R;

public class DistributorShipment_OrderDetails extends Fragment {



    public static DistributorShipment_OrderDetails newInstance() {
        return new DistributorShipment_OrderDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.distributor_shipment__view_shipment_2_fragment, container, false);
        Log.i("OrderDetails()", "OrderDetails()");
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
