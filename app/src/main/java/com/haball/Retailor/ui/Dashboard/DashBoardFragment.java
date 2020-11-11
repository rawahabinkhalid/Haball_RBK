package com.haball.Retailor.ui.Dashboard;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorPaymentsAdapter;
import com.haball.Distributor.DistributorPaymentsModel;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.Distributor.ui.payments.PaymentsViewModel;
import com.haball.HaballError;
import com.haball.Payment.Payment_Amount;
import com.haball.R;
import com.haball.Retailer_Login.RetailerLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {

    private PaymentsViewModel paymentsViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String Token, DistributorId;
    ;
    private String URL = "http://175.107.203.97:4014/api/prepaidrequests/search";
    private List<RetailerPaymentModel> PaymentsList = new ArrayList<>();
    //spiner1
    private Spinner payment_retailer_spiner1;
    private List<String> payment = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments_Ret;
    private String Filter_selected, Filter_selected_value;
    //spinner2
    private Spinner payment_retailer_spiner2;
    private List<String> payment_filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_PaymentFeltter;
    private EditText edt_payment_ret;


    private Button create_payment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentsViewModel =
                ViewModelProviders.of(this).get(PaymentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard_retailor, container, false);
        //init
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_fragment_payments);
        create_payment = root.findViewById(R.id.create_payment);
        payment_retailer_spiner1 = root.findViewById(R.id.spinner_dashboard_retailor);
//        edt_payment_ret = root.findViewById(R.id.edt_payment_ret);
//        payment_retailer_spiner2 = root.findViewById(R.id.payment_retailer_spiner);

//        payment_retailer_spiner2.setVisibility(View.GONE);
//        edt_payment_ret.setVisibility(View.GONE);
//        payment.add ("Select Criteria");
//        payment.add ("Company");
//        payment.add ("Payment Id");
//        payment.add ("Amount");
//        payment.add ("Status");
//
//        arrayAdapterPayments_Ret = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, payment);
//        payment_retailer_spiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i == 0){
//                       ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
//                }
//                else{
//                    Filter_selected = payment.get(i);
//
//                    if(!Filter_selected.equals("Status"))
//                        payment_retailer_spiner2.setSelection(0);
//                    if(!edt_payment_ret.getText().equals(""))
//                        edt_payment_ret.setText("");
//
//                    if(Filter_selected.equals("Invoice No")) {
//                        Filter_selected = "ConsolidatedInvoiceNumber";
//                        payment_retailer_spiner2.setVisibility(View.GONE);
//                        edt_payment_ret.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Company")) {
//                        Filter_selected = "CompanyName";
//                        payment_retailer_spiner2.setVisibility(View.GONE);
//                        edt_payment_ret.setVisibility(View.VISIBLE);
//                    } else if(Filter_selected.equals("Created Date")) {
//                        payment_retailer_spiner2.setVisibility(View.GONE);
//                        edt_payment_ret.setVisibility(View.GONE);
//                        Toast.makeText(getContext(),"Created Date selected",Toast.LENGTH_LONG).show();
//                    } else if(Filter_selected.equals("Total Price")) {
//                        payment_retailer_spiner2.setVisibility(View.GONE);
//                        edt_payment_ret.setVisibility(View.GONE);
//                        Toast.makeText(getContext(),"Total Price selected",Toast.LENGTH_LONG).show();
//                    } else if(Filter_selected.equals("Paid Amount")) {
//                        payment_retailer_spiner2.setVisibility(View.GONE);
//                        edt_payment_ret.setVisibility(View.GONE);
//                        Toast.makeText(getContext(),"Paid Amount selected",Toast.LENGTH_LONG).show();
//                    } else if(Filter_selected.equals("Status")) {
//                        Filter_selected = "Status";
//                        payment_retailer_spiner2.setVisibility(View.VISIBLE);
//                        edt_payment_ret.setVisibility(View.GONE);
//                    } else if(Filter_selected.equals("Created By")) {
//                        Filter_selected = "CreatedBy";
//                        payment_retailer_spiner2.setVisibility(View.GONE);
//                        edt_payment_ret.setVisibility(View.VISIBLE);
//                    } else {
//                        payment_retailer_spiner2.setVisibility(View.GONE);
//                        edt_payment_ret.setVisibility(View.GONE);
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
//        arrayAdapterPayments_Ret.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterPayments_Ret.notifyDataSetChanged();
//        payment_retailer_spiner1.setAdapter(arrayAdapterPayments_Ret);
//
//        //filter payment
//        payment_filters.add ("Status");
//        payment_filters.add ("Paid");
//        payment_filters.add ("Unpaid ");
//        arrayAdapter_PaymentFeltter = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, payment_filters);
//
//        payment_retailer_spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(i == 0){
//                       ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
//                }
//                else{
//                    Filter_selected_value = String.valueOf(i-1);
//                    Log.i("Filter_selected_value",Filter_selected_value);
//                    try {
//                        fetchFilteredRetailerPayments();
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
//        arrayAdapter_PaymentFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapter_PaymentFeltter.notifyDataSetChanged();
//        payment_retailer_spiner2.setAdapter(arrayAdapter_PaymentFeltter);
//
//        edt_payment_ret.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                Log.i("text1", "check");
//                Log.i("text", String.valueOf(s));
//                Filter_selected_value = String.valueOf(s);
//                try {
//                    fetchFilteredRetailerPayments();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });


        //recyclerview
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        try {
//            fetchPaymentsData();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        // specify an adapter (see also next example)

        return root;
    }

    private void fetchPaymentsData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("CompanyName", null);
//        jsonObject.put("CreateDateFrom", null);
//        jsonObject.put("CreateDateTo", null);
//        jsonObject.put("Status", null);
//        jsonObject.put("AmountMin", null);
//        jsonObject.put("AmountMax", null);
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", 0);
        
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                try {
                    System.out.println("RESPONSE PAYMENTS" + result.getJSONArray("PrePaidRequestData"));
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerPaymentModel>>() {
                    }.getType();
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);

                    mAdapter = new RetailerPaymentAdapter(getActivity(), getContext(), PaymentsList);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchFilteredRetailerPayments() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();

        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);
        map.put(Filter_selected, Filter_selected_value);
        Log.i("Mapsssss", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("retailerPayment", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<RetailerPaymentModel>>() {
                }.getType();
                try {
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new RetailerPaymentAdapter(getActivity(), getContext(), PaymentsList);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8 ");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }


    // private void printErrorMessage(VolleyError error) {
    //     if (getContext() != null) {
    //         if (error instanceof NetworkError) {
    //             Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ServerError) {
    //             Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof AuthFailureError) {
    //             Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ParseError) {
    //             Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof NoConnectionError) {
    //             Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof TimeoutError) {
    //             Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
    //         }

    //         if (error.networkResponse != null && error.networkResponse.data != null) {
    //             try {
    //                 String message = "";
    //                 String responseBody = new String(error.networkResponse.data, "utf-8");
    //                 Log.i("responseBody", responseBody);
    //                 JSONObject data = new JSONObject(responseBody);
    //                 Log.i("data", String.valueOf(data));
    //                 Iterator<String> keys = data.keys();
    //                 while (keys.hasNext()) {
    //                     String key = keys.next();
    //                     message = message + data.get(key) + "\n";
    //                 }
    //                 Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    //             } catch (UnsupportedEncodingException e) {
    //                 e.printStackTrace();
    //             } catch (JSONException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    // }
}
