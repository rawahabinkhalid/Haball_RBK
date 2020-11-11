package com.haball.Invoice.Select_Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haball.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealerInformation extends Fragment {


    public DealerInformation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_dealer_information, container, false);
        Log.i("dealerActivity" , "123321");
    return root;
    }

}
