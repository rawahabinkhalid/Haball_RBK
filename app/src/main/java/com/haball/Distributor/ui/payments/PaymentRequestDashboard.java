package com.haball.Distributor.ui.payments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorPaymentsAdapter;
import com.haball.Distributor.DistributorPaymentsModel;
import com.haball.HaballError;
import com.haball.Payment.DistributorPaymentRequestAdaptor;
import com.haball.Payment.DistributorPaymentRequestModel;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class PaymentRequestDashboard extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;

    private List<DistributorPaymentRequestModel> PaymentsRequestList = new ArrayList<>();
    private String Token, DistributorId;
    private Button create_payment;
    private String URL_PAYMENT_REQUESTS = "https://175.107.203.97:4013/api/prepaidrequests/search";
    private FragmentTransaction fragmentTransaction;
    private String Filter_selected, Filter_selected_value;
    private Typeface myFont;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_distributor_payment_request__criteria_selection, container, false);

        create_payment = root.findViewById(R.id.create_payment);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        recyclerView = root.findViewById(R.id.rv_payment_request);
        recyclerView.setHasFixedSize(true);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner2.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Payment ID");
        consolidate_felter.add("Company");
        consolidate_felter.add("Transaction Date");
        consolidate_felter.add("Created Date");
        consolidate_felter.add("Amount");
        consolidate_felter.add("Status");

        arrayAdapterPayments = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, consolidate_felter) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                return view;
            }
        };

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner2.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected = consolidate_felter.get(i);

                    if (!Filter_selected.equals("Status"))
                        spinner2.setSelection(0);
                    if (!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    if (Filter_selected.equals("Payment ID")) {
                        Filter_selected = "PrePaidNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        Filter_selected = "CompanyName";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Transaction Date")) {
                        // Toast.makeText(getContext(),"Transaction Date selected",Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Created Date")) {
                        // Toast.makeText(getContext(),"Created Date selected",Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Amount")) {
                        // Toast.makeText(getContext(),"Amount selected",Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        spinner2.setVisibility(View.VISIBLE);
                    }
//                    try {
//                        fetchPaymentLedgerData(companies.get(Filter_selected));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_consolidate.setAdapter(arrayAdapterPayments);

        filters.add("Status");
        filters.add("Processing Payment");
        filters.add("Unpaid ");
        filters.add("Paid");
        arrayAdapterFeltter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, filters){

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                return view;
            }
        };
        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected_value = String.valueOf(i - 2);
                    Log.i("Filter_selected_value", Filter_selected_value);
                    try {
                        fetchFilteredPaymentRequests();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterFeltter.notifyDataSetChanged();
        spinner2.setAdapter(arrayAdapterFeltter);


        conso_edittext.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.i("text1", "check");
                Log.i("text", String.valueOf(s));
                Filter_selected_value = String.valueOf(s);
                try {
                    fetchFilteredPaymentRequests();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

//        spinner_consolidate("Select Criteria","Invoice No", "Company", "Created Date", "Total Price", "Paid Amount" ,"Status","Created By");
//        spinner_consolidate.setOnItemSelectedListener(new Spinner().OnItemSelectedListener<String>() {
//            @Override public void onItemSelected(Spinner view, int position, long id, String item) {
//                if (position!=6){
//                    spinner_consolidate.setVisibility(View.GONE);
//                }
//                else{
//                    spinner2.setVisibility(View.VISIBLE);
//
//                }
//                Snackbar.make(view, "Clicked " + item , Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "id"+position, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        spinner2.setItems("Pending", "Unpaid", "Partially Paid", "Paid", "Payment Processing");
//        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//
//            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item , Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), "id"+position, Toast.LENGTH_SHORT).show();
//
//            }
//        });

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        create_payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.remove(PaymentRequestDashboard.this);
//                fragmentTransaction.add(((ViewGroup)getView().getParent()).getId(), new CreatePaymentRequestFragment());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });

        try {
            fetchPaymentRequests();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void fetchPaymentRequests() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_REQUESTS, map, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
//                loader.hideLoader();
                Log.i("Payments Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorPaymentRequestModel>>() {
                }.getType();
                PaymentsRequestList = gson.fromJson(result.toString(), type);

                mAdapter = new DistributorPaymentRequestAdaptor(getActivity(), getContext(), PaymentsRequestList);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }


    private void fetchFilteredPaymentRequests() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);
        map.put(Filter_selected, Filter_selected_value);
        Log.i("Map", String.valueOf(map));

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_REQUESTS, map, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
//                loader.hideLoader();
                Log.i("Payments Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorPaymentRequestModel>>() {
                }.getType();
                PaymentsRequestList = gson.fromJson(result.toString(), type);

                mAdapter = new DistributorPaymentRequestAdaptor(getActivity(), getContext(), PaymentsRequestList);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }


    private void printErrMessage(VolleyError error) {
        if (getContext() != null) {
            if (error instanceof NetworkError) {
                Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
            }

            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = "";
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.i("responseBody", responseBody);
                    JSONObject data = new JSONObject(responseBody);
                    Log.i("data", String.valueOf(data));
                    Iterator<String> keys = data.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        message = message + data.get(key) + "\n";
                    }
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}