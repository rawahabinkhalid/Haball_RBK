package com.haball.Retailor.ui.Network.Select_Tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.haball.R;
import com.haball.Retailor.ui.Network.Models.Netwok_Model;

/**
 * A simple {@link Fragment} subclass.
 */

public class Recieved_Fragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Netwok_Model paymentsViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_recieved_, container, false);
    }
}
