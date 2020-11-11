package com.haball.Distributor.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.haball.Distribution_Login.Distribution_Login;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.DistributorOrdersAdapter;
import com.haball.Distributor.DistributorOrdersModel;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.Payment.DistributorPaymentRequestAdaptor;
import com.haball.Payment.DistributorPaymentRequestModel;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
//import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
//import com.github.ksoichiro.android.observablescrollview.ScrollState;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter PaymentsAdapter;
    private RecyclerView.Adapter OrdersAdapter;
    private TextView tv_shipment_no_data, tv_shipment_no_data1;
    private RecyclerView.LayoutManager layoutManager;
    private String URL_DISTRIBUTOR_DASHBOARD = "https://175.107.203.97:4013/api/dashboard/ReadDistributorDashboard";
    //    private String URL_DISTRIBUTOR_PAYMENTS = "https://175.107.203.97:4013/api/dashboard/ReadDistributorPayments";
    private String URL_DISTRIBUTOR_PAYMENTS = "https://175.107.203.97:4013/api/prepaidrequests/searchall";
    private String URL_DISTRIBUTOR_PAYMENTS_COUNT = "https://175.107.203.97:4013/api/prepaidrequests/searchCount";
    private String URL_DISTRIBUTOR_ORDERS = "https://175.107.203.97:4013/api/orders/search";
    private String URL_DISTRIBUTOR_ORDERS_COUNT = "https://175.107.203.97:4013/api/orders/searchCount";
    private String URL_PAYMENT_LEDGER_COMPANY = "https://175.107.203.97:4013/api/company/ReadActiveCompanyContract/";

    private TextView tv_select_company, value_unpaid_amount, value_paid_amount;
    private List<DistributorPaymentRequestModel> PaymentsRequestList = new ArrayList<>();
    private List<DistributorOrdersModel> OrdersList = new ArrayList<>();
    private String Token, DistributorId;

    private PageViewModel pageViewModel;
    private RelativeLayout spinner_container1, spinner_container;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter;
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private Button consolidate;
    private String Filter_selected = "", Filter_selected1 = "", Filter_selected2 = "", Filter_selected_value = "";
    private RecyclerView.Adapter mAdapter;
    private TextInputLayout search_bar;
    private RelativeLayout search_rl;

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
    private String fromDate = "", toDate = "", fromAmount = "", toAmount = "";
    private FragmentTransaction fragmentTransaction;
    private String tabName;
    private RelativeLayout rv_filter, spinner_container_main;
    //    private ScrollView scroll_view_main;
//    private ObservableScrollView scroll_view_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout line_bottom;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Typeface myFont;
    //overView
    private Spinner spinner_criteria;
    private String Company_selected;
    private TextView current_balance;
    private List<String> company_names = new ArrayList<>();
    private RelativeLayout rl_overView, rl_balances;
    private Context context;
    private Loader loader;
    boolean byDefaultSelectCriteria = true;
    boolean byDefaultStatus = true;
    private boolean doubleBackToExitPressedOnce = false;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
        editorOrderTabsFromDraft.putString("TabNo", "0");
        editorOrderTabsFromDraft.apply();


        SharedPreferences dashboardRights = getContext().getSharedPreferences("Distributor_UserRights",
                Context.MODE_PRIVATE);
        boolean Order_View = Boolean.parseBoolean(dashboardRights.getString("Order_View", "false"));
        boolean PrepaidRequestView = Boolean.parseBoolean(dashboardRights.getString("PrepaidRequestView", "false"));
        boolean Invoice_View = Boolean.parseBoolean(dashboardRights.getString("Invoice_View", "false"));
        boolean DashBoardView = Boolean.parseBoolean(dashboardRights.getString("DashBoardView", "false"));

//        Log.i("SECTION NO", String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER)));
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                if (PrepaidRequestView || Invoice_View)
                    rootView = setPaymentFragmentTask(inflater, container);
                else if (Order_View)
                    rootView = setOrderFragmentTask(inflater, container);
                else
                    rootView = setDashboardFragmentTask(inflater, container);

                break;
            }

            case 2: {
                if (Order_View)
                    rootView = setOrderFragmentTask(inflater, container);
                else
                    rootView = setDashboardFragmentTask(inflater, container);

                break;
            }
            case 3: {
                rootView = setDashboardFragmentTask(inflater, container);

                break;
            }
            default:
                break;
        }
        return rootView;
    }

    private View setPaymentFragmentTask(LayoutInflater inflater, ViewGroup container) {
        tabName = "Payment";
        View rootView = inflater.inflate(R.layout.fragment_payments, container, false);
        paymentFragmentTask(rootView);
        context = getContext();
        loader = new Loader(context);
        try {
            fetchPaymentRequests();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//                scroll_view_main = rootView.findViewById(R.id.scroll_view_main);

        rv_filter = rootView.findViewById(R.id.rv_filter);
        line_bottom = rootView.findViewById(R.id.line_bottom);

        SpannableString content = new SpannableString("Load More");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//                btn_load_more.setText(content);
//                btn_load_more.setVisibility(View.GONE);


//                btn_load_more.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        pageNumber++;
//                        try {
//                            performPagination();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });


        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_payments);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

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
                    if (rv_filter.getVisibility() == View.GONE) {

                        rv_filter.setVisibility(View.VISIBLE);
                        TranslateAnimation animate1 = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                -rv_filter.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate1.setDuration(230);
                        animate1.setFillAfter(true);
                        rv_filter.clearAnimation();
                        rv_filter.startAnimation(animate1);
                    }
                } else if (scroll.equals("ScrollUp")) {
                    y = 0;
                    if (rv_filter.getVisibility() == View.VISIBLE) {
//                                line_bottom.setVisibility(View.INVISIBLE);
                        TranslateAnimation animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                0,  // fromYDelta
                                -rv_filter.getHeight()); // toYDelta
                        animate.setDuration(100);
                        animate.setFillAfter(true);
                        rv_filter.clearAnimation();
                        rv_filter.startAnimation(animate);
                        rv_filter.setVisibility(View.GONE);
                    }
                }
                if (isLastItemDisplaying(recyclerView)) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (totalPages != 0 && pageNumber < totalPages) {
//                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
                            pageNumber++;
                            try {
                                performPagination();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        return rootView;
    }


    private View setOrderFragmentTask(LayoutInflater inflater, ViewGroup container) {
        tabName = "Order";
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        orderFragmentTask(rootView);
        context = getContext();
        loader = new Loader(context);
        rv_filter = rootView.findViewById(R.id.rv_filter);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_fragment_orders);
        spinner_container_main = rootView.findViewById(R.id.spinner_container_main);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

//
//                SpannableString content = new SpannableString("Load More");
//                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//                btn_load_more.setText(content);

//                btn_load_more.setVisibility(View.GONE);

//                btn_load_more.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        pageNumberOrder++;
//                        try {
//                            performPaginationOrder();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

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
                    if (rv_filter.getVisibility() == View.GONE) {

                        rv_filter.setVisibility(View.VISIBLE);
                        TranslateAnimation animate1 = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                -rv_filter.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate1.setDuration(230);
                        animate1.setFillAfter(true);
                        rv_filter.clearAnimation();
                        rv_filter.startAnimation(animate1);
                    }
                } else if (scroll.equals("ScrollUp")) {
                    y = 0;
                    if (rv_filter.getVisibility() == View.VISIBLE) {
//                                line_bottom.setVisibility(View.INVISIBLE);
                        TranslateAnimation animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                0,  // fromYDelta
                                -rv_filter.getHeight()); // toYDelta
                        animate.setDuration(100);
                        animate.setFillAfter(true);
                        rv_filter.clearAnimation();
                        rv_filter.startAnimation(animate);
                        rv_filter.setVisibility(View.GONE);
                    }
                }
                if (isLastItemDisplaying(recyclerView)) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (totalPagesOrder != 0 && pageNumberOrder < totalPagesOrder) {
//                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
                            pageNumberOrder++;
                            try {
                                performPaginationOrder();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });


        try {
            fetchOrderData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private View setDashboardFragmentTask(LayoutInflater inflater, ViewGroup container) {
        tabName = "Dashboard";
        context = getContext();
        loader = new Loader(context);
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        spinner_criteria = rootView.findViewById(R.id.spinner_criteria);
        tv_select_company = rootView.findViewById(R.id.tv_select_company);
        rl_overView = rootView.findViewById(R.id.rl_overView);
        value_unpaid_amount = rootView.findViewById(R.id.value_unpaid_amount);
        value_paid_amount = rootView.findViewById(R.id.value_paid_amount);
        current_balance = rootView.findViewById(R.id.current_balance);
        rl_balances = rootView.findViewById(R.id.rl_balances);
        company_names.add("Select Company");
        //company_names = "";
        arrayAdapterPayments = new ArrayAdapter<String>(rootView.getContext(),
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
        spinner_criteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    rl_overView.setVisibility(View.GONE);
                    tv_select_company.setVisibility(View.VISIBLE);
                } else {
                    tv_select_company.setVisibility(View.GONE);
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Company_selected = company_names.get(i);
                    Log.i("company_debug", Company_selected);
                    rl_overView.setVisibility(View.VISIBLE);
                    if (Company_selected.equals("Continental Biscuit Ltd")) {
                        rl_balances.setVisibility(View.VISIBLE);
                        current_balance.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetchCompanyData();
        fetchDashboardData();

        return rootView;
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() > 9) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
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

    private void performPaginationOrder() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        String username = sharedPreferences.getString("username", "");
        String UserTypeId = sharedPreferences.getString("UserTypeId", "");
//        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumberOrder);
//        map.put("Status", null);
        map.put("username", username);
        map.put("usertypeid", UserTypeId);
        if (Filter_selected.equals("date")) {
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate + "T00:00:00.000Z");
            else if (!toDate.equals(""))
                map.put(Filter_selected1, toDate + "T00:00:00.000Z");
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate + "T23:59:59.000Z");
            else if (!fromDate.equals(""))
                map.put(Filter_selected2, fromDate + "T23:59:59.000Z");
        } else if (Filter_selected.equals("amount")) {
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            map.put(Filter_selected, Filter_selected_value);
        }

        Log.i("Placeholder_Order", String.valueOf(map));

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
//                btn_load_more.setVisibility(View.GONE);
                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorOrdersModel>>() {
                }.getType();
//                OrdersList = gson.fromJson(result.toString(), type);
//                ((DistributorOrdersAdapter) recyclerView.getAdapter()).addListItem(OrdersList);
                List<DistributorOrdersModel> OrdersList_temp = new ArrayList<>();
                OrdersList_temp = gson.fromJson(result.toString(), type);
                OrdersList.addAll(OrdersList_temp);
                OrdersAdapter.notifyDataSetChanged();

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
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void performPagination() throws JSONException {
        Log.i("PaymentDebug", "In Pagination");
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        String username = sharedPreferences.getString("username", "");
        String UserTypeId = sharedPreferences.getString("UserTypeId", "");
//        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
//        map.put("Status", null);
        map.put("username", username);
        map.put("usertypeid", UserTypeId);
        if (Filter_selected.equals("date")) {
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate + "T00:00:00.000Z");
            else if (!toDate.equals(""))
                map.put(Filter_selected1, toDate + "T00:00:00.000Z");
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate + "T23:59:59.000Z");
            else if (!fromDate.equals(""))
                map.put(Filter_selected2, fromDate + "T23:59:59.000Z");
        } else if (Filter_selected.equals("amount")) {
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            map.put(Filter_selected, Filter_selected_value);
        }

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_DISTRIBUTOR_PAYMENTS, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                try {
                    Log.i("Payments all", result.getJSONArray("PrePaidRequestData").toString());
//                btn_load_more.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<DistributorPaymentRequestModel>>() {
                    }.getType();
                    List<DistributorPaymentRequestModel> PaymentsRequestList_temp = new ArrayList<>();
                    PaymentsRequestList_temp = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                    PaymentsRequestList.addAll(PaymentsRequestList_temp);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                mAdapter = new DistributorPaymentRequestAdaptor(getContext(), PaymentsRequestList);
//                recyclerView.setAdapter(mAdapter);
//                tv_shipment_no_data1.setVisibility(View.GONE);

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
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void paymentFragmentTask(final View rootView) {
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        tv_shipment_no_data1 = rootView.findViewById(R.id.tv_shipment_no_data);
        search_bar = rootView.findViewById(R.id.search_bar);
        search_rl = rootView.findViewById(R.id.search_rl);
//        consolidate = rootView.findViewById(R.id.consolidate);

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
//        tv_shipment_no_data.setVisibility(View.VISIBLE);
        spinner_container1 = rootView.findViewById(R.id.spinner_container1);
        spinner_consolidate = (Spinner) rootView.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) rootView.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) rootView.findViewById(R.id.conso_edittext);
        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);

        consolidate_felter = new ArrayList<>();
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Payment ID");
        consolidate_felter.add("Company");
//        consolidate_felter.add("Transaction Date");
//        consolidate_felter.add("Created Date");
        consolidate_felter.add("Date");
        consolidate_felter.add("Status");
        consolidate_felter.add("Amount");

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

                if (!byDefaultSelectCriteria) {
                    try {
                        fetchPaymentRequests();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    byDefaultSelectCriteria = false;
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
//                if (i > 0) {
                    Filter_selected = consolidate_felter.get(i);


                    if (Filter_selected.equals("Payment ID")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "PrePaidNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                        conso_edittext.setInputType(InputType.TYPE_CLASS_NUMBER);

                    } else if (Filter_selected.equals("Company")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "CompanyName";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                        conso_edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if (Filter_selected.equals("Date")) {
                        date_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "date";
                        Filter_selected1 = "PrepaidDateFrom";
                        Filter_selected2 = "PrepaidDateTo";
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
                    } else if (Filter_selected.equals("Created Date")) {
                        date_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "date";
                        Filter_selected1 = "CreateDateFrom";
                        Filter_selected2 = "CreateDateTo";
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
                    } else if (Filter_selected.equals("Amount")) {
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "AmountMin";
                        Filter_selected2 = "AmountMax";
                        checkAmountChanged();
                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
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
        filters.add("Status");
        filters.add("Processing Payment");
        filters.add("Unpaid ");
        filters.add("Paid");
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
//        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(i)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    if (!byDefaultStatus) {
                        try {
                            fetchPaymentRequests();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    byDefaultStatus = false;
                    // if (i > 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected_value = String.valueOf(i - 2);
//                    Log.i("Filter_selected_value", String.valueOf(i));

                    if (Filter_selected_value != "") {
                        try {
                            fetchFilteredPaymentRequests();
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
////                Log.i("text1", "check");
////                Log.i("text", String.valueOf(s));
//                Filter_selected_value = String.valueOf(s);
//                if (!Filter_selected_value.equals("")) {
//                    try {
//                        fetchFilteredPaymentRequests();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        fetchPaymentRequests();
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

                if (!hasFocus) {
                    Filter_selected_value = String.valueOf(conso_edittext.getText());
                    if (!Filter_selected_value.equals("")) {
                        try {
                            fetchFilteredPaymentRequests();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            fetchPaymentRequests();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
//        consolidate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container, new RetailerPlaceOrder());
//                fragmentTransaction.commit();
        // Toast.makeText(getContext(), "Consolidate clicked", Toast.LENGTH_LONG).show();
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.remove(PaymentRequestDashboard.this);
//                        fragmentTransaction.add(((ViewGroup)getView().getParent()).getId(), new CreatePaymentRequestFragment());
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//            }
//        });
//
    }


    private void checkAmountChanged() {
        et_amount1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    fromAmount = String.valueOf(et_amount1.getText());
                    if (tabName.equals("Payment")) {
                        try {
                            fetchFilteredPaymentRequests();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (tabName.equals("Order")) {
                        try {
                            fetchFilteredOrderData();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }
            }
        });
        et_amount2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    toAmount = String.valueOf(et_amount2.getText());
                    if (tabName.equals("Payment")) {
                        try {
                            fetchFilteredPaymentRequests();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (tabName.equals("Order")) {
                        try {
                            fetchFilteredOrderData();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
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

    private void openCalenderPopup(String date_type) {
        dateType = date_type;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void orderFragmentTask(final View rootView) {
        tv_shipment_no_data = rootView.findViewById(R.id.tv_shipment_no_data);
        search_bar = rootView.findViewById(R.id.search_bar);
        search_rl = rootView.findViewById(R.id.search_rl);

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
        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);
        consolidate_felter = new ArrayList<>();
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Order No");
        consolidate_felter.add("Company");
//        consolidate_felter.add("Payment Term");
        consolidate_felter.add("Date");
        consolidate_felter.add("Status");
        consolidate_felter.add("Amount");

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

                if (!byDefaultSelectCriteria) {
                    try {
                        loader.showLoader();
                        fetchOrderData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(i)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    byDefaultSelectCriteria = false;
                    // if (i > 0) {
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

                    if (Filter_selected.equals("Order No")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "OrderNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "CompanyName";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Payment Term")) {
                        Filter_selected = "PaymentType";
                        filters = new ArrayList<>();
                        filters.add("Select All");
                        filters.add("Pre Payment");
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

                        spinner2.setAdapter(arrayAdapterFeltter);
                        spinner_container1.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Date")) {
                        date_filter_rl.setVisibility(View.VISIBLE);
//                        Toast.makeText(getContext(), "Created Date selected", Toast.LENGTH_LONG).show();
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
                    } else if (Filter_selected.equals("Amount")) {
//                        Toast.makeText(getContext(), "Amount selected", Toast.LENGTH_LONG).show();
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "AmountMin";
                        Filter_selected2 = "AmountMax";
                        checkAmountChanged();
                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        filters = new ArrayList<>();

                        filters.add("Status");
                        filters.add("Pending");
                        filters.add("Approved");
                        filters.add("Rejected");
                        filters.add("Draft");
                        filters.add("Cancelled");
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

                        spinner2.setAdapter(arrayAdapterFeltter);

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

//        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    if (!byDefaultStatus) {
                        try {
                            loader.showLoader();
                            fetchOrderData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    byDefaultStatus = false;
                }
                if (Filter_selected.equals("Status") || Filter_selected.equals("PaymentType")) {

                    if (i == 0) {
                        try {
                            ((TextView) adapterView.getChildAt(i)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                            ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            fetchOrderData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // if (i > 0) {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                            ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }

                        Filter_selected_value = String.valueOf(i - 1);
                        if (!Filter_selected_value.equals("")) {
                            try {
                                fetchFilteredOrderData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                fetchOrderData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if (Filter_selected.equals("PaymentType")) {
                    if (i == 0)
                        Filter_selected_value = String.valueOf(-1);
                    else
                        Filter_selected_value = String.valueOf(i);
                    if (!Filter_selected_value.equals("")) {
                        try {
                            fetchFilteredOrderData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

//        }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapterFeltter);
//
//        conso_edittext.addTextChangedListener(new
//
//                                                      TextWatcher() {
//
//                                                          public void afterTextChanged(Editable s) {
////                Log.i("text1", "check");
////                Log.i("text", String.valueOf(s));
//                                                              Filter_selected_value = String.valueOf(s);
//                                                              if (!Filter_selected_value.equals("")) {
//                                                                  try {
//                                                                      fetchFilteredOrderData();
//                                                                  } catch (JSONException e) {
//                                                                      e.printStackTrace();
//                                                                  }
//                                                              } else {
//                                                                  try {
//                                                                      fetchOrderData();
//                                                                  } catch (JSONException e) {
//                                                                      e.printStackTrace();
//                                                                  }
//                                                              }
//                                                          }
//
//                                                          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                                          }
//
//                                                          public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                                          }
//                                                      });


        conso_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Filter_selected_value = String.valueOf(conso_edittext.getText());
                if (!Filter_selected_value.equals("")) {
                    try {
                        fetchFilteredOrderData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        fetchOrderData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void fetchOrderData() throws JSONException {
//        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
//        Log.i("Token", Token);
        tv_shipment_no_data.setVisibility(View.GONE);


        JSONObject mapCount = new JSONObject();
        mapCount.put("Status", -1);
        mapCount.put("DistributorId", Integer.parseInt(DistributorId));

        JsonObjectRequest countRequest = new JsonObjectRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS_COUNT, mapCount, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalEntriesOrder = Double.parseDouble(String.valueOf(response.get("ordersCount")));
                    totalPagesOrder = Math.ceil(totalEntriesOrder / 10);
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
//                Log.i("onErrorResponse", "Error");
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
        map.put("Status", -1);
        map.put("OrderState", -1);
        map.put("DistributorId", DistributorId);
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumberOrder);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                //                    JSONArray jsonArray = new JSONArray(result);
                loader.hideLoader();
                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorOrdersModel>>() {
                }.getType();
                OrdersList = gson.fromJson(result.toString(), type);

                OrdersAdapter = new DistributorOrdersAdapter(getContext(), OrdersList);
                recyclerView.setAdapter(OrdersAdapter);
                if (result.length() != 0) {
                    tv_shipment_no_data.setVisibility(View.GONE);
                    spinner_container.setVisibility(View.VISIBLE);
                } else {
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

    private void fetchFilteredOrderData() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        String username = sharedPreferences.getString("username", "");
        String UserTypeId = sharedPreferences.getString("UserTypeId", "");
//        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId ", DistributorId);
        pageNumberOrder = 0;
        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumberOrder);
//        map.put("Status", null);
        map.put("username", username);
        map.put("usertypeid", UserTypeId);
        if (Filter_selected.equals("date")) {
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate + "T00:00:00.000Z");
            else if (!toDate.equals(""))
                map.put(Filter_selected1, toDate + "T00:00:00.000Z");
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate + "T23:59:59.000Z");
            else if (!fromDate.equals(""))
                map.put(Filter_selected2, fromDate + "T23:59:59.000Z");
        } else if (Filter_selected.equals("amount")) {
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else {
            map.put(Filter_selected, Filter_selected_value);
        }
//        Log.i("Map", String.valueOf(map));

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                //                    JSONArray jsonArray = new JSONArray(result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<DistributorOrdersModel>>() {
                }.getType();
                OrdersList = gson.fromJson(result.toString(), type);

                OrdersAdapter = new DistributorOrdersAdapter(getContext(), OrdersList);
                recyclerView.setAdapter(OrdersAdapter);
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
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchPaymentRequests() throws JSONException {
        Log.i("PaymentDebug", "In Main");
        loader.showLoader();
        tv_shipment_no_data1.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);

        Token = sharedPreferences.getString("Login_Token", "");
//        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "-1");
//        Log.i("DistributorId ", DistributorId);

//        JSONObject mapCount = new JSONObject();
//        mapCount.put("Status", -1);
//        mapCount.put("DistributorId", Integer.parseInt(DistributorId));
//
//        JsonObjectRequest countRequest = new JsonObjectRequest(Request.Method.POST, URL_DISTRIBUTOR_PAYMENTS_COUNT, mapCount, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("payment_all", String.valueOf(response));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(getContext(), error);
//                new ProcessingError().showError(getContext());
//
//                error.printStackTrace();
////                Log.i("onErrorResponse", "Error");
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        countRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(countRequest);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0.1);


        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_DISTRIBUTOR_PAYMENTS, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();

                try {
                    totalEntries = Double.parseDouble(String.valueOf(result.get("RecordCount")));
                    totalPages = Math.ceil(totalEntries / 10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("Payments all1", result.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result.getString("PrePaidRequestData"));
                    Log.i("jsonArray", String.valueOf(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Type type = new TypeToken<List<DistributorPaymentRequestModel>>() {
                }.getType();
                Gson gson = new Gson();
                PaymentsRequestList = gson.fromJson(jsonArray.toString(), type);

                mAdapter = new DistributorPaymentRequestAdaptor(getActivity(), getContext(), PaymentsRequestList);
                recyclerView.setAdapter(mAdapter);
                if (PaymentsRequestList.size() != 0) {
                    tv_shipment_no_data1.setVisibility(View.GONE);
                    spinner_container.setVisibility(View.VISIBLE);
                } else {
                    tv_shipment_no_data1.setVisibility(View.VISIBLE);
                    spinner_container.setVisibility(View.GONE);
                }
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
        Volley.newRequestQueue(getContext()).add(sr);
    }


    private boolean checkAndRequestPermissions() {
        int permissionRead = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    private void fetchFilteredPaymentRequests() throws JSONException {
        Log.i("PaymentDebug", "In Filter");
        loader.showLoader();
        tv_shipment_no_data1.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        String username = sharedPreferences.getString("username", "");
        String UserTypeId = sharedPreferences.getString("UserTypeId", "");
//        Log.i("Token", Token);
        pageNumber = 0;
        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
//        map.put("Status", null);
        map.put("username", username);
        map.put("usertypeid", UserTypeId);
        if (Filter_selected.equals("date")) {
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate + "T00:00:00.000Z");
            else if (!toDate.equals(""))
                map.put(Filter_selected1, toDate + "T00:00:00.000Z");
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate + "T23:59:59.000Z");
            else if (!fromDate.equals(""))
                map.put(Filter_selected2, fromDate + "T23:59:59.000Z");
        } else if (Filter_selected.equals("amount")) {
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else {
            if (Filter_selected.equals("PrePaidNumber"))
                map.put(Filter_selected, Double.parseDouble(Filter_selected_value));
            else
                map.put(Filter_selected, Filter_selected_value);
        }

        Log.i("Map123", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_DISTRIBUTOR_PAYMENTS, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                Log.i("Payments all1", result.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result.getString("PrePaidRequestData"));
                    Log.i("jsonArray", String.valueOf(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Type type = new TypeToken<List<DistributorPaymentRequestModel>>() {
                }.getType();
                Gson gson = new Gson();
                PaymentsRequestList = gson.fromJson(jsonArray.toString(), type);

                mAdapter = new DistributorPaymentRequestAdaptor(getActivity(), getContext(), PaymentsRequestList);
                recyclerView.setAdapter(mAdapter);
                if (PaymentsRequestList.size() != 0) {
                    tv_shipment_no_data1.setVisibility(View.GONE);
//                    spinner_container.setVisibility(View.VISIBLE);
                } else {
                    tv_shipment_no_data1.setVisibility(View.VISIBLE);
//                    spinner_container.setVisibility(View.GONE);
                }
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
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchDashboardData() {
//        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        StringRequest sr = new StringRequest(Request.Method.POST, URL_DISTRIBUTOR_DASHBOARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Log.i("over_view", result);
                loader.hideLoader();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    DecimalFormat formatter1 = new DecimalFormat("#,###,###,##0.00");
                    String yourFormattedString1 = formatter1.format(Double.parseDouble(jsonObject.get("TotalUnpaidAmount").toString()));
                    DecimalFormat formatter2 = new DecimalFormat("#,###,###,##0.00");
                    String yourFormattedString2 = formatter2.format(Double.parseDouble(jsonObject.get("TotalPrepaidAmount").toString()));
                    DecimalFormat formatter3 = new DecimalFormat("#,###,###,##0.00");
                    String yourFormattedString3 = formatter3.format(Double.parseDouble(jsonObject.get("TotalDistributorBalance").toString()));
                    value_unpaid_amount.setText("Rs. " + yourFormattedString1);
                    value_paid_amount.setText("Rs. " + yourFormattedString2);
                    current_balance.setText("Rs. " + yourFormattedString3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        Volley.newRequestQueue(getContext()).add(sr);
    }


    private void printErrMessage(VolleyError error) {
        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                JSONObject data = new JSONObject(responseBody);
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
//                if (data.get(key) instanceof JSONObject) {
//                    Log.i("message", String.valueOf(data.get(key)));
                    if (data.get(key).equals("TokenExpiredError")) {
                        SharedPreferences login_token = getContext().getSharedPreferences("LoginToken",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = login_token.edit();
                        editor.remove("Login_Token");
                        editor.commit();
                        Intent login = new Intent(getActivity(), Distribution_Login.class);
                        startActivity(login);
                        getActivity().finish();

                    }

                    message = message + data.get(key) + "\n";
//                }
                }
//                    if(data.has("message"))
//                        message = data.getString("message");
//                    else if(data. has("Error"))
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            fromDate = year1 + "-" + String.format("%02d", (month1 + 1)) + "-" + String.format("%02d", date1);
//            Log.i("fromDate", fromDate);

            first_date.setText(new StringBuilder()
                    .append(String.format("%02d", date1)).append("/").append(String.format("%02d", (month1 + 1))).append("/").append(year1));
        } else if (date_type.equals("second date")) {
            toDate = year2 + "-" + String.format("%02d", (month2 + 1)) + "-" + String.format("%02d", date2);
            second_date.setText(new StringBuilder()
                    .append(String.format("%02d", date2)).append("/").append(String.format("%02d", (month2 + 1))).append("/").append(year2));
        }
        if (tabName.equals("Payment")) {
            try {
                fetchFilteredPaymentRequests();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (tabName.equals("Order")) {
            try {
                fetchFilteredOrderData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void fetchCompanyData() {
//        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        URL_PAYMENT_LEDGER_COMPANY = URL_PAYMENT_LEDGER_COMPANY + DistributorId;
        Log.i("URL_PROOF_OF_PAYMENTS ", URL_PAYMENT_LEDGER_COMPANY);

        Log.i("Token", Token);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_LEDGER_COMPANY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                Log.i("aaaaaabb", String.valueOf(result));
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        company_names.add(jsonObject.getString("Name"));
                        //companyNameAndId.put(jsonObject.getString("Name"), jsonObject.getString("ID"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF COMPANY ID", result.toString());
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
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_criteria.setAdapter(arrayAdapterPayments);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("keyback_debug", String.valueOf(keyCode));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("back_key_debug", "back from fragment 1");

                    if (doubleBackToExitPressedOnce) {
//                    super.onBackPressed();
//                    finishAffinity();
                        logoutUser();
                    }
                    doubleBackToExitPressedOnce = true;
                    // Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 1500);
                    return true;
                }
                return false;
            }
        });
    }

    private void logoutUser() {

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        tv_discard.setText("Logout");
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to logout?");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText("Logout");
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();

                SharedPreferences login_token = getContext().getSharedPreferences("LoginToken",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = login_token.edit();
                editor.remove("Login_Token");
                editor.commit();

                Intent login = new Intent(getContext(), Distribution_Login.class);
                startActivity(login);
                ((FragmentActivity) getContext()).finish();
            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

}