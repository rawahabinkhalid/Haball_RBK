package com.haball.Distributor.ui.retailer.Payment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.main.PageViewModel;
import com.haball.Distributor.ui.retailer.Payment.Adapters.PaymentDashboardAdapter;
import com.haball.Distributor.ui.retailer.Payment.Models.Dist_Retailer_Dashboard_Model;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrderDashboard;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetailerPaymentDashboard extends Fragment implements DatePickerDialog.OnDateSetListener {
    private RecyclerView rv_paymentDashBoard;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String Token, DistributorId;
    private Typeface myFont;
    ;
    private String URL = "https://175.107.203.97:4013/api/retailerprepaidrequest/search";
    private List<Dist_Retailer_Dashboard_Model> PaymentsList = new ArrayList<>();
    //spiner1

    private TextView value_unpaid_amount, value_paid_amount;

    private PageViewModel pageViewModel;
    private RelativeLayout spinner_container1, spinner_container;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter;
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    //    private Button consolidate;
    private String Filter_selected, Filter_selected1, Filter_selected2, Filter_selected_value;
    private TextInputLayout search_bar;
    private Button btn_load_more;
    private int pageNumber = 0;
    private double totalPages = 0;
    private double totalEntries = 0;

    private String dateType = "";
    private int year1, year2, month1, month2, date1, date2;

    private ImageButton first_date_btn, second_date_btn;
    private LinearLayout date_filter_rl, amount_filter_rl;
    private TextView first_date, second_date, tv_shipment_no_data;
    private EditText et_amount1, et_amount2;

    private int pageNumberOrder = 0;
    private double totalPagesOrder = 0;
    private double totalEntriesOrder = 0;
    private String fromDate="", toDate="";
    private FragmentTransaction fragmentTransaction;

    private String fromAmount="", toAmount="";
    private RelativeLayout spinner_container_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout search_rl;
    private Loader loader;

    public RetailerPaymentDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout  create_payment = root.findViewById(R.id.create_payment); for this fragment
        View rootView = inflater.inflate(R.layout.fragment_retailer_payment_dashboard, container, false);

        loader = new Loader(getContext());

        rv_paymentDashBoard = (RecyclerView) rootView.findViewById(R.id.rv_dist_payment_retailer);
        search_bar = rootView.findViewById(R.id.search_bar);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
//        consolidate = rootView.findViewById(R.id.consolidate);
        tv_shipment_no_data = rootView.findViewById(R.id.tv_shipment_no_data);
        spinner_container_main = rootView.findViewById(R.id.spinner_container_main);
        // DATE FILTERS ......
        date_filter_rl = rootView.findViewById(R.id.date_filter_rl);
        first_date = rootView.findViewById(R.id.first_date);
        first_date_btn = rootView.findViewById(R.id.first_date_btn);
        second_date = rootView.findViewById(R.id.second_date);
        second_date_btn = rootView.findViewById(R.id.second_date_btn);

        // AMOUNT FILTERS ......
        amount_filter_rl = rootView.findViewById(R.id.amount_filter_rl);
        et_amount1 = rootView.findViewById(R.id.et_amount1);
        et_amount2 = rootView.findViewById(R.id.et_amount2);

        spinner_container = rootView.findViewById(R.id.spinner_container);
        spinner_container.setVisibility(View.GONE);
        spinner_container1 = rootView.findViewById(R.id.spinner_container1);
        spinner_consolidate = (Spinner) rootView.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) rootView.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) rootView.findViewById(R.id.conso_edittext);
        search_rl = rootView.findViewById(R.id.search_rl);
        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter = new ArrayList<>();
        consolidate_felter.add(getResources().getString(R.string.select_criteria));
        consolidate_felter.add(getResources().getString(R.string.paymentid_sp));
        consolidate_felter.add(getResources().getString(R.string.company));
        consolidate_felter.add(getResources().getString(R.string.date));
        consolidate_felter.add(getResources().getString(R.string.status));
        consolidate_felter.add(getResources().getString(R.string.amount));

        arrayAdapterPayments = new ArrayAdapter<String>(rootView.getContext(),
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
//                Toast.makeText(getContext(), consolidate_felter.get(i), Toast.LENGTH_LONG).show();
                spinner_container1.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                date_filter_rl.setVisibility(View.GONE);
                amount_filter_rl.setVisibility(View.GONE);
                search_rl.setVisibility(View.GONE);

                spinner2.setSelection(0);
                conso_edittext.setText("");
                et_amount1.setText("");
                et_amount2.setText("");
                first_date.setText("DD/MM/YYYY");
                second_date.setText("DD/MM/YYYY");

                try {
                    fetchPaymentsData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected = consolidate_felter.get(i);

                    if (!Filter_selected.equals(getResources().getString(R.string.status)))
                        spinner2.setSelection(0);
                    if (!conso_edittext.getText().equals(""))
                        conso_edittext.setText("");

                    if (Filter_selected.equals(getResources().getString(R.string.paymentid_sp))) {
                        search_bar.setHint(getResources().getString(R.string.search_by) + Filter_selected);
                        Filter_selected = "InvoiceNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals(getResources().getString(R.string.company))) {
                        search_bar.setHint(getResources().getString(R.string.search_by) + Filter_selected);
                        Filter_selected = "CompanyName";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals(getResources().getString(R.string.date))) {
                        date_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "date";
                        Filter_selected1 = "DateFrom";
                        Filter_selected2 = "DateTo";
                        first_date_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openCalenderPopup("first date");
                            }
                        });
                        second_date_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openCalenderPopup("second date");
                            }
                        });
                    } else if (Filter_selected.equals(getResources().getString(R.string.amount))) {
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "PaymentAmountMin";
                        Filter_selected2 = "PaymentAmountMax";
                        checkAmountChanged();
                    } else if (Filter_selected.equals(getResources().getString(R.string.status))) {
                        Filter_selected = getResources().getString(R.string.status);
                        spinner_container1.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_consolidate.setAdapter(arrayAdapterPayments);

        filters = new ArrayList<>();
        filters.add(getResources().getString(R.string.status));
        filters.add(getResources().getString(R.string.pending));
        filters.add(getResources().getString(R.string.invoiced));
//        filters.add("Partially Paid");
        filters.add(getResources().getString(R.string.paid));
//        filters.add("Payment Processing");
        filters.add(getResources().getString(R.string.cancelled));
        arrayAdapterFeltter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_spinner_dropdown_item, filters) {
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

        // Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);

                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        fetchPaymentsData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected_value = filters.get(i);
                    // Log.i("Filter_selected_value", String.valueOf(i));

                    if (Filter_selected_value != "") {
                        try {
                            fetchFilteredRetailerPayments();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterFeltter.notifyDataSetChanged();
        spinner2.setAdapter(arrayAdapterFeltter);
//
//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                // Log.i("text1", "check");
//                // Log.i("text", String.valueOf(s));
//                Filter_selected_value = String.valueOf(s);
//                if (!Filter_selected_value.equals("")) {
//                    try {
//                        fetchFilteredRetailerPayments();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        fetchPaymentsData();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });

        conso_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    Filter_selected_value = String.valueOf(conso_edittext.getText());
                    if (!Filter_selected_value.equals("")) {
                        try {
                            fetchFilteredRetailerPayments();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            fetchPaymentsData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        rv_paymentDashBoard.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        rv_paymentDashBoard.setLayoutManager(layoutManager);
        rv_paymentDashBoard.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
//                            // Log.i("scrolling", "Scroll Down");
                } else if (dy > 5) {
                    scrollEvent.add("ScrollUp");
//                            // Log.i("scrolling", "Scroll Up");
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
                        btn_load_more.setVisibility(View.VISIBLE);
                    }
                }
            }

        });

        try {
            fetchPaymentsData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void fetchPaymentsData() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", 0);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                try {
                    System.out.println("RESPONSE PAYMENTS" + result.getJSONArray("PrePaidRequestData"));
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Dist_Retailer_Dashboard_Model>>() {
                    }.getType();
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);

                    mAdapter = new PaymentDashboardAdapter(getActivity(), getContext(), PaymentsList);
                    rv_paymentDashBoard.setAdapter(mAdapter);
                    if (PaymentsList.size() != 0) {
                        tv_shipment_no_data.setVisibility(View.GONE);
                        spinner_container.setVisibility(View.VISIBLE);
                    } else {
                        tv_shipment_no_data.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                error.printStackTrace();
                 new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
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
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        // Log.i("DistributorId ", DistributorId);
        // Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);
        if (Filter_selected.equals("date")) {
            if (!fromDate.equals("")) {
                map.put(Filter_selected1, fromDate + "T00:00:00.000Z");
                map.put("PaidDateFrom", fromDate + "T00:00:00.000Z");
            } else if (!toDate.equals("")) {
                map.put(Filter_selected1, toDate + "T00:00:00.000Z");
                map.put("PaidDateFrom", toDate + "T00:00:00.000Z");
            }
            if (!toDate.equals("")) {
                map.put(Filter_selected2, toDate + "T23:59:59.000Z");
                map.put("PaidDateTo", toDate + "T23:59:59.000Z");
            } else if (!fromDate.equals("")) {
                map.put(Filter_selected2, fromDate + "T23:59:59.000Z");
                map.put("PaidDateTo", fromDate + "T23:59:59.000Z");
            }
//            if (!fromDate.equals(""))
//                map.put("Paid" + Filter_selected1, fromDate + "T00:00:00.000Z");
//            else if (!toDate.equals(""))
//                map.put("Paid" + Filter_selected1, toDate + "T00:00:00.000Z");
//            if (!toDate.equals(""))
//                map.put("Paid" + Filter_selected2, toDate + "T23:59:59.000Z");
//            else if (!fromDate.equals(""))
//                map.put("Paid" + Filter_selected2, fromDate + "T23:59:59.000Z");
        } else if (Filter_selected.equals("amount")) {
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else {
            map.put(Filter_selected, Filter_selected_value);
        }
        // Log.i("Mapsssss", String.valueOf(map));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                // Log.i("retailerPayment", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<Dist_Retailer_Dashboard_Model>>() {
                }.getType();
                try {
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new PaymentDashboardAdapter(getActivity(), getContext(), PaymentsList);
                rv_paymentDashBoard.setAdapter(mAdapter);

                if (PaymentsList.size() != 0) {
                    tv_shipment_no_data.setVisibility(View.GONE);
                } else {
                    tv_shipment_no_data.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                error.printStackTrace();
                 new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
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

    private void openCalenderPopup(String date_type) {
        dateType = date_type;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (dateType.equals("first date")) {
            year1 = i;
            month1 = i1;
            date1 = i2;
            updateDisplay(dateType);
        } else if (dateType.equals("second date")) {
            year2 = i;
            month2 = i1;
            date2 = i2;
            updateDisplay(dateType);
        }
    }

    private void updateDisplay(String date_type) {
        if (date_type.equals("first date")) {
            fromDate = year1 + "-" + String.format("%02d", (month1 + 1)) + "-" + String.format("%02d", date1);
            // Log.i("fromDate", fromDate);

            first_date.setText(new StringBuilder()
                    .append(String.format("%02d", date1)).append("/").append(String.format("%02d", (month1 + 1))).append("/").append(year1));
        } else if (date_type.equals("second date")) {
            toDate = year2 + "-" + String.format("%02d", (month2 + 1)) + "-" + String.format("%02d", date2);
            second_date.setText(new StringBuilder()
                    .append(String.format("%02d", date2)).append("/").append(String.format("%02d", (month2 + 1))).append("/").append(year2));
        }
        try {
            fetchFilteredRetailerPayments();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkAmountChanged() {
        et_amount1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    fromAmount = String.valueOf(et_amount1.getText());

                    try {
                        fetchFilteredRetailerPayments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        et_amount2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    toAmount = String.valueOf(et_amount2.getText());
                    try {
                        fetchFilteredRetailerPayments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//        et_amount1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                final String fromAmount_main = String.valueOf(et_amount1.getText());
////                if (!String.valueOf(et_amount2.getText()).equals(""))
//
////                new java.util.Timer().schedule(
////                        new java.util.TimerTask() {
////                            @Override
////                            public void run() {
////                                // your code here
////                                getActivity().runOnUiThread(new Runnable() {
////                                    public void run() {
////                                        //your code
//                fromAmount = String.valueOf(et_amount1.getText());
//                if (fromAmount_main.equals(fromAmount)) {
//                    if (tabName.equals("Payment")) {
//                        try {
//                            fetchFilteredRetailerPayments();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else if (tabName.equals("Order")) {
//                        try {
//                            fetchFilteredOrderData();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }
////                                    }
////                                });
////                            }
////                        },
////                        2500
////                );
//            }
//        });
//
//        et_amount2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                if (!String.valueOf(et_amount2.getText()).equals(""))
//                final String toAmount_main = String.valueOf(et_amount2.getText());
//
////                new java.util.Timer().schedule(
////                        new java.util.TimerTask() {
////                            @Override
////                            public void run() {
////                                // your code here
////                                getActivity().runOnUiThread(new Runnable() {
////                                    public void run() {
////                                        //your code
//                toAmount = String.valueOf(et_amount2.getText());
//                if (toAmount_main.equals(toAmount)) {
//                    if (tabName.equals("Payment")) {
//                        try {
//                            fetchFilteredRetailerPayments();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else if (tabName.equals("Order")) {
//                        try {
//                            fetchFilteredOrderData();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }
////                                    }
////                                });
////                            }
////                        },
////                        2500
////                );
//            }
//
//        });

    }


    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new HomeFragment());
                    fragmentTransaction.commit();
                }
                return false;
            }
        });
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
//        // Log.i("distinct", scroll);
        return scroll;
    }
}

