package com.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.orders.Adapter.DistributorOrderAdapter;
import com.haball.Distributor.ui.orders.Models.OrderFragmentModel;
import com.haball.Distributor.ui.orders.Models.OrdersViewModel;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.Order.DistributorOrder_ItemSelection;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Orders_Fragment extends Fragment {

    private OrdersViewModel sendViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button create_payment;
    private List<OrderFragmentModel> OrderList;
    private String URL_ORDER = "https://175.107.203.97:4013/api/orders/search";
    private String Token, DistributorId;
    private String Filter_selected, Filter_selected_value;

    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        final View root = inflater.inflate(R.layout.activity_distributer_order, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_order_ledger);

       /* create_payment = root.findViewById(R.id.place_order_button);
        create_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DistributorOrder_ItemSelection.class);
                 startActivity(intent);
            }
        });*/
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
//
//        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
//        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
//        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
//        spinner2.setVisibility(View.GONE);
//        conso_edittext.setVisibility(View.GONE);
//        consolidate_felter.add ("Select Criteria");
//        consolidate_felter.add ("Order No");
//        consolidate_felter.add ("Company");
//        consolidate_felter.add ("Payment Term");
//        consolidate_felter.add ("Created Date");
//        consolidate_felter.add ("Amount");
//        consolidate_felter.add ("Status");
//
//        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_dropdown_item_1line, consolidate_felter);
//
//
//        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                spinner2.setVisibility(View.GONE);
//                conso_edittext.setVisibility(View.GONE);
//                if(i == 0){
//                       ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
//                }
//                else{
//                    Filter_selected = consolidate_felter.get(i);
//                    Log.i("Filter_selected", Filter_selected);
//                    if(!Filter_selected.equals("Status"))
//                        spinner2.setSelection(0);
//                    if(!Filter_selected.equals("Payment Term"))
//                        spinner2.setSelection(0);
//                    if(!conso_edittext.getText().equals(""))
//                        conso_edittext.setText("");
//
//                    if(Filter_selected.equals("Order No")) {
//                        Filter_selected = "OrderNumber";
//                        conso_edittext.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Company")) {
//                        Filter_selected = "Company";
//                        conso_edittext.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Payment Term")) {
//                        Filter_selected = "PaymentType";
//                        filters = new ArrayList<>();
//                        filters.add ("Select All");
//                        filters.add ("Pre Payment");
//                        filters.add ("Post Payment");
//                        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
//                                android.R.layout.simple_dropdown_item_1line, filters);
//                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        arrayAdapterFeltter.notifyDataSetChanged();
//                        spinner2.setAdapter(arrayAdapterFeltter);
//
//                        spinner2.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Created Date")) {
//                        Toast.makeText(getContext(),"Created Date selected",Toast.LENGTH_LONG).show();
//                    } else if(Filter_selected.equals("Amount")) {
//                        Toast.makeText(getContext(),"Amount selected",Toast.LENGTH_LONG).show();
//                    } else if(Filter_selected.equals("Status")) {
//                        Filter_selected = "Status";
//                        filters = new ArrayList<>();
//                        filters.add ("Status");
//                        filters.add ("Pending");
//                        filters.add ("Approved");
//                        filters.add ("Rejected");
//                        filters.add ("Draft");
//                        filters.add ("Cancelled");
//                        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
//                                android.R.layout.simple_dropdown_item_1line, filters);
//
//                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        arrayAdapterFeltter.notifyDataSetChanged();
//                        spinner2.setAdapter(arrayAdapterFeltter);
//
//                        spinner2.setVisibility(View.VISIBLE);
//                    }
////                    try {
////                        fetchPaymentLedgerData(companies.get(Filter_selected));
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterPayments.notifyDataSetChanged();
//        spinner_consolidate.setAdapter(arrayAdapterPayments);
//
//        Log.i("aaaa1111", String.valueOf(consolidate_felter));
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i == 0){
//                       ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
//                }
//                else{
//                    if(Filter_selected.equals("Status"))
//                        Filter_selected_value = String.valueOf(i-1);
//                    else
//                        Filter_selected_value = String.valueOf(i);
//                    if(filters.get(i).equals("Select All"))
//                        Filter_selected_value = "-1";
//                    Log.i("Filter_selected_value",Filter_selected_value);
//                    try {
//                        fetchFilteredOrders();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
////        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        arrayAdapterFeltter.notifyDataSetChanged();
////        spinner2.setAdapter(arrayAdapterFeltter);
//
//
//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                Log.i("text1", "check");
//                Log.i("text", String.valueOf(s));
//                Filter_selected_value = String.valueOf(s);
//                try {
//                    fetchFilteredOrders();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        try {
//            fetchOrders();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        // specify an adapter (see also next example)
//        mAdapter = new DistributorOrderAdapter(this,"Ghulam Rabani & Sons Traders & Distributors","1002312324251524","Invoice","Pending");
//        recyclerView.setAdapter(mAdapter);
        return root;
    }
//
//    private void fetchOrders() throws JSONException {
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token","");
//        Log.i("Token", Token);
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id","");
//        Log.i("DistributorId ", DistributorId);
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        map.put("TotalRecords", 10);
//        map.put("PageNumber", 0);
//        map.put("Status", -1);
//        map.put("OrderState", -1);
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_ORDER, map,new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                Log.i("Payments Requests", result.toString());
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderFragmentModel>>(){}.getType();
//                OrderList = gson.fromJson(result.toString(),type);
//
//                mAdapter = new DistributorOrderAdapter(getContext(),OrderList);
//                recyclerView.setAdapter(mAdapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " +Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(sr);
//    }
//
//
//    private void fetchFilteredOrders() throws JSONException {
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token","");
//        Log.i("Token", Token);
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id","");
//        Log.i("DistributorId ", DistributorId);
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        map.put("TotalRecords", 10);
//        map.put("PageNumber", 0);
////        map.put("Status", -1);
//        map.put("OrderState", -1);
//        map.put(Filter_selected, Filter_selected_value);
//        Log.i("Map", String.valueOf(map));
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_ORDER, map,new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                Log.i("Payments Requests", result.toString());
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderFragmentModel>>(){}.getType();
//                OrderList = gson.fromJson(result.toString(),type);
//
//                mAdapter = new DistributorOrderAdapter(getContext(),OrderList);
//                recyclerView.setAdapter(mAdapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " +Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(sr);
//    }
}