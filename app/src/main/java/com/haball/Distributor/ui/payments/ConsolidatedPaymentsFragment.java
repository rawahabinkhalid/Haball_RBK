package com.haball.Distributor.ui.payments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distribution_Login.Distribution_Login;
import com.haball.HaballError;
import com.haball.Payment.ConsolidatePaymentsModel;
import com.haball.Payment.Consolidate_Fragment_Adapter;
import com.haball.Payment.DistributorPaymentRequestAdaptor;
import com.haball.Payment.DistributorPaymentRequestModel;
import com.haball.Payment.PaymentLedgerAdapter;
import com.haball.Payment.PaymentLedgerModel;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConsolidatedPaymentsFragment extends Fragment {
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
    private TextView tv_shipment_no_data;
    private List<ConsolidatePaymentsModel> ConsolidatePaymentsRequestList = new ArrayList<>();
    private String Token, DistributorId;
    private Button create_payment;
    private String URL_CONSOLIDATE_PAYMENTS = "https://175.107.203.97:4013/api/consolidatedinvoices/search";
    private String URL_CONSOLIDATE_PAYMENTS_COUNT = "https://175.107.203.97:4013/api/consolidatedinvoices/searchCount";
    private FragmentTransaction fragmentTransaction;
    private String Filter_selected, Filter_selected_value;
   // private Button btn_load_more;

    private int pageNumber = 0;
    private double totalEntries = 0;
    private double totalPages = 0;
    private RelativeLayout spinner_container_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Typeface myFont;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_consolidate_, container, false);

        recyclerView = root.findViewById(R.id.rv_consolidate);

      //  btn_load_more = root.findViewById(R.id.btn_load_more);

        SpannableString content = new SpannableString("Load More");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        btn_load_more.setText(content);
//        btn_load_more.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner2.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Invoice No");
        consolidate_felter.add("Company");
        consolidate_felter.add("Created Date");
        consolidate_felter.add("Total Price");
        consolidate_felter.add("Paid Amount");
        consolidate_felter.add("Status");
        consolidate_felter.add("Created By");

//        btn_load_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pageNumber++;
//                try {
//                    performPagination();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        arrayAdapterPayments = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, consolidate_felter){
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
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);} catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected = consolidate_felter.get(i);

                    if (!Filter_selected.equals("Status"))
                        spinner2.setSelection(0);
                    if (!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    if (Filter_selected.equals("Invoice No")) {
                        Filter_selected = "ConsolidatedInvoiceNumber";
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        Filter_selected = "CompanyName";
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Created Date")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        // Toast.makeText(getContext(), "Created Date selected", Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Total Price")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        // Toast.makeText(getContext(), "Total Price selected", Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Paid Amount")) {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
                        // Toast.makeText(getContext(), "Paid Amount selected", Toast.LENGTH_LONG).show();
                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        spinner2.setVisibility(View.VISIBLE);
                        conso_edittext.setVisibility(View.GONE);
                    } else if (Filter_selected.equals("Created By")) {
                        Filter_selected = "CreatedBy";
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else {
                        spinner2.setVisibility(View.GONE);
                        conso_edittext.setVisibility(View.GONE);
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
        filters.add("Pending");
        filters.add("Unpaid ");
        filters.add("Partially Paid");
        filters.add("Paid");
        filters.add("Payment Processing");
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
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                     } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected_value = String.valueOf(i - 1);
                    Log.i("Filter_selected_value", Filter_selected_value);
                    try {
                        fetchFilteredConsolidatePayments();
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
                if (!Filter_selected_value.equals("")) {
                    try {
                        fetchFilteredConsolidatePayments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        fetchConsolidatePayments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
//
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//
//                // Load more if we have reach the end to the recyclerView
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
//                    if (totalPages != 0 && pageNumber < totalPages) {
////                        Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollEvent = new ArrayList<>();

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                y = dy;
                if (dy <= -5) {
                    scrollEvent.add("ScrollDown");
//                            Log.i("scrolling", "Scroll Down");
                } else if (dy > 5) {
                    scrollEvent.add("ScrollUp");
//                            Log.i("scrolling", "Scroll Up");
                }
                String scroll = getScrollEvent();

                if (scroll.equals("ScrollDown")) {
                    if (spinner_container_main.getVisibility() == View.GONE) {

                        spinner_container_main.setVisibility(View.VISIBLE);
                        TranslateAnimation animate1 = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                -spinner_container_main.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate1.setDuration(250);
                        animate1.setFillAfter(true);
                        spinner_container_main.clearAnimation();
                        spinner_container_main.startAnimation(animate1);
                    }
                } else if (scroll.equals("ScrollUp")) {
                    y = 0;
                    if (spinner_container_main.getVisibility() == View.VISIBLE) {
//                                line_bottom.setVisibility(View.INVISIBLE);
                        TranslateAnimation animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                0,  // fromYDelta
                                -spinner_container_main.getHeight()); // toYDelta
                        animate.setDuration(100);
                        animate.setFillAfter(true);
                        spinner_container_main.clearAnimation();
                        spinner_container_main.startAnimation(animate);
                        spinner_container_main.setVisibility(View.GONE);
                    }
                }

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (totalPages != 0 && pageNumber < totalPages) {
//                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
                       //btn_load_more.setVisibility(View.VISIBLE);
                    }
                }
            }

        });
        try {
            fetchConsolidatePayments();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }

    private void performPagination() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject mapCount = new JSONObject();
        mapCount.put("Status", -1);
        mapCount.put("DistributorId", Integer.parseInt(DistributorId));

        JsonObjectRequest countRequest = new JsonObjectRequest(Request.Method.POST, URL_CONSOLIDATE_PAYMENTS_COUNT, mapCount, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalEntries = Double.parseDouble(String.valueOf(response.get("ConsolidateCount")));
                    totalPages = Math.ceil(totalEntries / 10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
                Log.i("onErrorResponse", "Error");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        countRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(countRequest);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_CONSOLIDATE_PAYMENTS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
//                loader.hideLoader();

           //     btn_load_more.setVisibility(View.GONE);

                Gson gson = new Gson();
                Type type = new TypeToken<List<ConsolidatePaymentsModel>>() {
                }.getType();
                ConsolidatePaymentsRequestList = gson.fromJson(result.toString(), type);
                ((Consolidate_Fragment_Adapter) recyclerView.getAdapter()).addListItem(ConsolidatePaymentsRequestList);

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

    private void fetchConsolidatePayments() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_CONSOLIDATE_PAYMENTS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
//                loader.hideLoader();
                Log.i("ConsolidatePayments", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<ConsolidatePaymentsModel>>() {
                }.getType();
                ConsolidatePaymentsRequestList = gson.fromJson(result.toString(), type);

                mAdapter = new Consolidate_Fragment_Adapter(getContext(), ConsolidatePaymentsRequestList);
                recyclerView.setAdapter(mAdapter);
                if (result.length() != 0) {
                    tv_shipment_no_data.setVisibility(View.GONE);
                } else {
                    tv_shipment_no_data.setVisibility(View.VISIBLE);
                }

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

    private void fetchFilteredConsolidatePayments() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);
        map.put(Filter_selected, Filter_selected_value);
        Log.i("Map", String.valueOf(map));
        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_CONSOLIDATE_PAYMENTS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
//                loader.hideLoader();
                Log.i("ConsolidatePayments", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<ConsolidatePaymentsModel>>() {
                }.getType();
                ConsolidatePaymentsRequestList = gson.fromJson(result.toString(), type);

                mAdapter = new Consolidate_Fragment_Adapter(getContext(), ConsolidatePaymentsRequestList);
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

    private String getScrollEvent() {
        String scroll = "";
        if (scrollEvent.size() > 0) {
            if (scrollEvent.size() > 15)
                scrollEvent = new ArrayList<>();
            if (Collections.frequency(scrollEvent, "ScrollUp") > Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollDown") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollUp") > 3)
                        scroll = "ScrollUp";
                } else {
                    scroll = "ScrollUp";
                }
            } else if (Collections.frequency(scrollEvent, "ScrollUp") < Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollUp") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollDown") > 3)
                        scroll = "ScrollDown";
                } else {
                    scroll = "ScrollDown";
                }
            }
        }
//        Log.i("distinct", scroll);
        return scroll;
    }
}
