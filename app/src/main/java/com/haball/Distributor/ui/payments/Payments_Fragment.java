package com.haball.Distributor.ui.payments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.Payment.PaymentLedgerAdapter;
import com.haball.Payment.PaymentLedgerModel;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class Payments_Fragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayoutManager layoutManager2;
    private HashMap<String, String> companies = new HashMap<>();
    private List<String> company_names = new ArrayList<>();
    private String Token;
    private String URL_PAYMENT_LEDGER_COMPANY = "https://175.107.203.97:4013/api/company/ReadActiveCompanyContract/";
    private String URL_PAYMENT_LEDGER = "https://175.107.203.97:4013/api/transactions/search";
    private String URL_PAYMENT_LEDGER_COUNT = "https://175.107.203.97:4013/api/transactions/searchCount";
    private ArrayAdapter<String> arrayAdapterPayments, arrayAdapterPaymentsFilter;
    private List<PaymentLedgerModel> paymentLedgerList = new ArrayList<>();
    private Spinner spinner_criteria;
    private ProgressDialog progressDialog;

    private PaymentsViewModel paymentsViewModel;
    private Spinner spinner_consolidate;
    private TextView tv_shipment_no_data;
    private TextView tv_select_company;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterFeltter;
    //  private Button btn_load_more;
    private String Company_selected, DistributorId;
    private String Filter_selected, Filter_selected_value;

    private RelativeLayout spinner_container1;
    private String Filter_selected1, Filter_selected2;
    private TextInputLayout search_bar;
    private int pageNumber = 0;
    private double totalPages = 0;
    private double totalEntries = 0;

    private String dateType = "";
    private int year1, year2, month1, month2, date1, date2;

    private ImageButton first_date_btn, second_date_btn;
    private LinearLayout date_filter_rl, amount_filter_rl;
    private TextView first_date, second_date;
    private EditText et_amount1, et_amount2;

    private int pageNumberOrder = 0;
    private double totalPagesOrder = 0;
    private double totalEntriesOrder = 0;
    private String fromDate, toDate, fromAmount, toAmount;

    //variables for pagination ...
    private boolean isLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previousTotal = 0;
    private int viewThreshold = 10;
    private RelativeLayout spinner_container_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private Typeface myFont;
    private Context mcontext;
    private Loader loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentsViewModel =
                ViewModelProviders.of(this).get(PaymentsViewModel.class);
        View root = inflater.inflate(R.layout.activity_payment_ledger, container, false);
        mcontext = getContext();
        company_names.add("Company ");
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        //   btn_load_more = root.findViewById(R.id.btn_load_more);

        loader = new Loader(getContext());

        SpannableString content = new SpannableString("Load More");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        // btn_load_more.setText(content);
        //  btn_load_more.setVisibility(View.GONE);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_select_company = root.findViewById(R.id.tv_select_company);
        search_bar = root.findViewById(R.id.search_bar);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);
        // DATE FILTERS ......
        date_filter_rl = root.findViewById(R.id.date_filter_rl);
        first_date = root.findViewById(R.id.first_date);
        first_date_btn = root.findViewById(R.id.first_date_btn);
        second_date = root.findViewById(R.id.second_date);
        second_date_btn = root.findViewById(R.id.second_date_btn);

        // AMOUNT FILTERS ......
        amount_filter_rl = root.findViewById(R.id.amount_filter_rl);
        et_amount1 = root.findViewById(R.id.et_amount1);
        et_amount2 = root.findViewById(R.id.et_amount2);

        spinner_container1 = root.findViewById(R.id.spinner_container1);
        spinner_container1.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);

        spinner_criteria = root.findViewById(R.id.spinner_criteria);
        arrayAdapterPayments = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, company_names) {
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
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);


        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);
        spinner_consolidate.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Ledger ID");
        consolidate_felter.add("Document Type");
        consolidate_felter.add("Date");
        consolidate_felter.add("Credit");
        consolidate_felter.add("Debit");
        consolidate_felter.add("Balance");

        arrayAdapterPaymentsFilter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, consolidate_felter);

        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_container1.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                date_filter_rl.setVisibility(View.GONE);
                amount_filter_rl.setVisibility(View.GONE);

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

                    spinner2.setSelection(0);
                    conso_edittext.setText("");

                    if (Filter_selected.equals("Ledger ID")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "DocumentNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Document Type")) {
                        Filter_selected = "DocumentType";
                        spinner_container1.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Date")) {
//                        Toast.makeText(mcontext, "Date selected", Toast.LENGTH_LONG).show();
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
                    } else if (Filter_selected.equals("Credit")) {
//                        Toast.makeText(mcontext, "Credit selected", Toast.LENGTH_LONG).show();
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "CreditAmountMin";
                        Filter_selected2 = "CreditAmountMax";
                        checkAmountChanged();
                    } else if (Filter_selected.equals("Debit")) {
//                        Toast.makeText(mcontext, "Debit selected", Toast.LENGTH_LONG).show();
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "DebitAmountMin";
                        Filter_selected2 = "DebitAmountMax";
                        checkAmountChanged();
                    } else if (Filter_selected.equals("Balance")) {
//                        Toast.makeText(mcontext, "Balance selected", Toast.LENGTH_LONG).show();
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "BalanceMin";
                        Filter_selected2 = "BalanceMax";
                        checkAmountChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        arrayAdapterPaymentsFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPaymentsFilter.notifyDataSetChanged();
        spinner_consolidate.setAdapter(arrayAdapterPaymentsFilter);

        filters.add("Document Type");
        filters.add("Invoice");
        filters.add("Prepaid ");
        filters.add("Shipment");
        arrayAdapterFeltter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_dropdown_item_1line, filters) {
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
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
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

                    Filter_selected_value = String.valueOf(i);
                    Log.i("Filter_selected_value", Filter_selected_value);
                    try {
                        fetchFilteredPaymentLedgerData(companies.get(Company_selected));
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
                        fetchFilteredPaymentLedgerData(companies.get(Company_selected));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        fetchPaymentLedgerData(companies.get(Company_selected));
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

        spinner_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_container1.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                date_filter_rl.setVisibility(View.GONE);
                amount_filter_rl.setVisibility(View.GONE);
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    spinner_consolidate.setVisibility(View.GONE);
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Company_selected = company_names.get(i);
                    spinner_consolidate.setVisibility(View.VISIBLE);
                    try {
                        fetchPaymentLedgerData(companies.get(Company_selected));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        btn_load_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pageNumber++;
//                try {
//                    performPagination(companies.get(Company_selected));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        recyclerView = root.findViewById(R.id.rv_payment_ledger);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mcontext);
        layoutManager2 = new LinearLayoutManager(mcontext);

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
//                        Toast.makeText(mcontext, pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
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
                        // btn_load_more.setVisibility(View.VISIBLE);
                    }
                }
            }

        });
        fetchCompanyNames();

        return root;
    }

    private void fetchCompanyNames() {
        loader.showLoader();

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

        URL_PAYMENT_LEDGER_COMPANY = URL_PAYMENT_LEDGER_COMPANY + DistributorId;
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_LEDGER_COMPANY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        company_names.add(jsonObject.getString("Name"));
                        companies.put(jsonObject.getString("Name"), jsonObject.getString("ID"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF companies", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
                loader.hideLoader();

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
        Volley.newRequestQueue(mcontext).add(sr);
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_criteria.setAdapter(arrayAdapterPayments);
    }

    private void fetchPaymentLedgerData(String companyId) throws JSONException {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token pl .. ", Token);

        JSONObject mapCount = new JSONObject();
        mapCount.put("Status", -1);
        mapCount.put("DistributorId", Integer.parseInt(DistributorId));
        mapCount.put("CompanyId", companyId);

        JsonObjectRequest countRequest = new JsonObjectRequest(Request.Method.POST, URL_PAYMENT_LEDGER_COUNT, mapCount, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalEntries = Double.parseDouble(String.valueOf(response.get("transactionsCount")));
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
        Volley.newRequestQueue(mcontext).add(countRequest);

        JSONObject map = new JSONObject();
        map.put("Status", -1);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", companyId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
        loader.showLoader();

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_LEDGER, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loader.hideLoader();
                Log.i(" PAYMENT LEDGER => ", "" + response.toString());
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < response.length(); i++) {
                    try {

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PaymentLedgerModel>>() {
                        }.getType();
                        paymentLedgerList = gson.fromJson(String.valueOf(response), type);

                        mAdapter = new PaymentLedgerAdapter(mcontext, paymentLedgerList);
                        recyclerView.setAdapter(mAdapter);

                        jsonObject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tv_select_company.setVisibility(View.GONE);
                if (response.length() != 0) {
                    tv_shipment_no_data.setVisibility(View.GONE);

                } else {
                    //  Toast.makeText(getContext(), "Response", Toast.LENGTH_LONG);
                    tv_shipment_no_data.setVisibility(View.VISIBLE);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
                loader.hideLoader();

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
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mcontext).add(request);
        mAdapter = new PaymentLedgerAdapter(mcontext, paymentLedgerList);
        recyclerView.setAdapter(mAdapter);
    }

    private void performPagination(String companyId) throws JSONException {

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token pl .. ", Token);

        JSONObject map = new JSONObject();
        map.put("Status", -1);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", companyId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_LEDGER, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loader.hideLoader();
                Log.i(" PAYMENT LEDGER PAGE2", "" + response.toString());
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //   btn_load_more.setVisibility(View.GONE);
                Gson gson = new Gson();
                Type type = new TypeToken<List<PaymentLedgerModel>>() {
                }.getType();
                paymentLedgerList = gson.fromJson(String.valueOf(response), type);

                ((PaymentLedgerAdapter) recyclerView.getAdapter()).addListItem(paymentLedgerList);
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mcontext).add(request);
    }


    private void fetchFilteredPaymentLedgerData(String companyId) throws JSONException {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token pl .. ", Token);

        JSONObject map = new JSONObject();
        map.put("Status", -1);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", companyId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);
        if (Filter_selected.equals("date")) {
            map.put(Filter_selected1, fromDate);
            map.put(Filter_selected2, toDate);
        } else if (Filter_selected.equals("amount")) {
            map.put(Filter_selected1, fromAmount);
            map.put(Filter_selected2, toAmount);
        } else {
            map.put(Filter_selected, Filter_selected_value);
        }
        Log.i("Map", String.valueOf(map));
        loader.showLoader();
        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_PAYMENT_LEDGER, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loader.hideLoader();
                Log.i(" PAYMENT LEDGER => ", "" + response.toString());
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Gson gson = new Gson();
                Type type = new TypeToken<List<PaymentLedgerModel>>() {
                }.getType();
                paymentLedgerList = gson.fromJson(String.valueOf(response), type);

                mAdapter = new PaymentLedgerAdapter(mcontext, paymentLedgerList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
                loader.hideLoader();

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
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mcontext).add(request);
        mAdapter = new PaymentLedgerAdapter(mcontext, paymentLedgerList);
        recyclerView.setAdapter(mAdapter);
    }

    private void openCalenderPopup(String date_type) {
        dateType = date_type;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
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
            fromDate = year1 + "-" + String.format("%02d", (month1 + 1)) + "-" + String.format("%02d", date1) + "T00:00:00.000Z";
            Log.i("fromDate", fromDate);

            first_date.setText(new StringBuilder()
                    .append(String.format("%02d", date1)).append("/").append(String.format("%02d", (month1 + 1))).append("/").append(year1));
        } else if (date_type.equals("second date")) {
            toDate = year2 + "-" + String.format("%02d", (month2 + 1)) + "-" + String.format("%02d", date2) + "T23:59:59.000Z";
            second_date.setText(new StringBuilder()
                    .append(String.format("%02d", date2)).append("/").append(String.format("%02d", (month2 + 1))).append("/").append(year2));
        }
        try {
            fetchFilteredPaymentLedgerData(companies.get(Company_selected));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void checkAmountChanged() {
        et_amount1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!String.valueOf(et_amount1.getText()).equals("") && !String.valueOf(et_amount2.getText()).equals("")) {
                    fromAmount = String.valueOf(et_amount1.getText());
                    toAmount = String.valueOf(et_amount2.getText());
                    try {
                        fetchFilteredPaymentLedgerData(companies.get(Company_selected));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        et_amount2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!String.valueOf(et_amount1.getText()).equals("") && !String.valueOf(et_amount2.getText()).equals("")) {
                    fromAmount = String.valueOf(et_amount1.getText());
                    toAmount = String.valueOf(et_amount2.getText());
                    try {
                        fetchFilteredPaymentLedgerData(companies.get(Company_selected));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

    }

    private void printErrMessage(VolleyError error) {
        if (getContext() != null) {
            if (error instanceof NetworkError) {
                Toast.makeText(mcontext, "Network Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(mcontext, "Server Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(mcontext, "Auth Failure Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(mcontext, "Parse Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(mcontext, "No Connection Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(mcontext, "Timeout Error !", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(mcontext, message, Toast.LENGTH_LONG).show();
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

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });
    }

}