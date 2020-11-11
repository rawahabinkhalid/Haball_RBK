package com.haball.Payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.haball.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentLedger extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<String> companies = new ArrayList<>();
    private String Token;
    private String URL_PAYMENT_LEDGER_COMPANY = "https://175.107.203.97:4013/api/company/ReadActiveCompanyContract/724";

    private Spinner spinner_criteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_ledger);
//        ActionBar bar = getSupportActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//
//        View customView = inflater.inflate(R.layout.action_bar_main, null);
//
//        bar.setCustomView(customView);
//        bar.setDisplayShowCustomEnabled(true);
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
//        bar.setTitle("");
//
//        spinner_criteria = findViewById(R.id.spinner_criteria);
//
//        recyclerView = findViewById(R.id.rv_payment_ledger);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        fetchCompanyNames();
//        fetchPaymentLedgerData();
//
//        mAdapter = new PaymentLedgerAdapter(PaymentLedger.this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","Rs. 50,000.00","Rs. 600,000.00");
//        recyclerView.setAdapter(mAdapter);
    }
}
