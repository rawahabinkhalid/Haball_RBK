package com.haball.Distributor.ui.retailer.RetailerOrder;

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
import android.widget.TextView;

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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.StatusKVP;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.Adapter.CompanyFragmentAdapter;
import com.haball.Distributor.ui.orders.Models.Company_Fragment_Model;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter.RetailerOrdersAdapter;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Retailor.ui.Dashboard.RetailerOrderModel;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class RetailerOrderDashboard extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RelativeLayout btn_place_order;
    private String URL_ORDER = "https://175.107.203.97:4013/api/retailerorder/search";
    private String Token, DistributorId;
    private TextView tv_shipment_no_data;
    private String URL_FETCH_ORDERS = "https://175.107.203.97:4013/api/retailerorder/search";
    private List<RetailerOrdersModel> OrdersList;
    private Spinner spinner_order_ret;
    private RelativeLayout spinner_container, spinner_container1;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter;
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    //    private Button consolidate;
    private String Filter_selected = "", Filter_selected_value = "";
    private String Filter_selected1, Filter_selected2;
    private int pageNumber = 0;
    private double totalPages = 0;
    private double totalEntries = 0;
    private TextInputLayout search_bar;
    public String dateType = "";
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

    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout spinner_container_main;
    private HashMap<String, String> OrderStatusKVP;
    private HashMap<String, String> InvoiceStatusKVP;
    private Typeface myFont;
    private RelativeLayout search_rl;
    private StatusKVP statusKVP;
    private Loader loader;
    boolean byDefaultSelectCriteria = true;
    boolean byDefaultStatus = true;


    public RetailerOrderDashboard() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        loader = new Loader(getContext());

        statusKVP = new StatusKVP(getContext(), Token);
        OrderStatusKVP = statusKVP.getOrderStatus();
        InvoiceStatusKVP = statusKVP.getInvoiceStatus();

        Log.i("InvoiceStatusKVP", String.valueOf(InvoiceStatusKVP));

        final View root = inflater.inflate(R.layout.fragment_retailer_order_dashboard, container, false);
        recyclerView = root.findViewById(R.id.rv_retailer_order_dashboard);
        btn_place_order = root.findViewById(R.id.btn_place_order);
        search_bar = root.findViewById(R.id.search_bar);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
//        consolidate = root.findViewById(R.id.consolidate);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);
        search_bar = root.findViewById(R.id.search_bar);

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

        spinner_container = root.findViewById(R.id.spinner_container);
        spinner_container.setVisibility(View.GONE);
        spinner_container1 = root.findViewById(R.id.spinner_container1);
        spinner_container1.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);

        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        search_rl = root.findViewById(R.id.search_rl);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);

        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);
        consolidate_felter = new ArrayList<>();
        consolidate_felter = new ArrayList<>();
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Order ID");
        consolidate_felter.add("Company");
        consolidate_felter.add("Date");
        consolidate_felter.add("Status");
        consolidate_felter.add("Amount");

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
                        fetchRetailerOrdersData();
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

                    Filter_selected = consolidate_felter.get(i);

                    spinner2.setSelection(0);
                    conso_edittext.setText("");

                    recyclerView = root.findViewById(R.id.rv_retailer_order_dashboard);
                    recyclerView.setHasFixedSize(true);


                    if (Filter_selected.equals("Order ID")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "OrderNumber";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "Retailer";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Date")) {
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
                    } else if (Filter_selected.equals("Amount")) {
                        amount_filter_rl.setVisibility(View.VISIBLE);
                        Filter_selected = "amount";
                        Filter_selected1 = "AmountMin";
                        Filter_selected2 = "AmountMax";
                        checkAmountChanged();
                    } else if (Filter_selected.equals("Submitter")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "Submitter";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);

                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "OrderStatus";
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
//        filters.addAll(OrderStatusKVP.values());
//        filters.add("Pending");
//        filters.add("Approved");
//        filters.add("Rejected");
//        filters.add("Draft");
//        filters.add("Cancelled");
        arrayAdapterFeltter = new ArrayAdapter<String>(root.getContext(),
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
        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                if (!byDefaultStatus) {
//                    try {
//                        fetchRetailerOrdersData();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);

                        try {
                            fetchRetailerOrdersData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    byDefaultStatus = false;
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

//                    Filter_selected_value = String.valueOf(i - 1);
//                    Log.i("Filter_selected_value", Filter_selected_value);

                    for (Map.Entry<String, String> entry : OrderStatusKVP.entrySet()) {
                        if (entry.getValue().equals(filters.get(i)))
                            Filter_selected_value = entry.getKey();
                    }

//                    Filter_selected_value = OrderStatusKVP.get(filters.get(i));
//                    Log.i("Filter_selected_value", filters.get(i));
//                    Log.i("Filter_selected_value", String.valueOf(OrderStatusKVP.containsValue(filters.get(i))));
//                    Log.i("Filter_selected_value", OrderStatusKVP.get(filters.get(i)));
//                    Log.i("Filter_selected_value", Filter_selected_value);
                    if (!Filter_selected_value.equals("")) {
                        try {
                            fetchFilteredOrderData();
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
//
//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                Log.i("text2", String.valueOf(Filter_selected));
//                Log.i("text1", "check");
//                Log.i("text", String.valueOf(s));
//                Filter_selected_value = String.valueOf(s);
//                if (!Filter_selected_value.equals("")) {
//                    try {
//                        fetchFilteredOrderData();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        fetchRetailerOrdersData();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
////                else {
////                    if(!Filter_selected.equals(""))
////                        Filter_selected = "";
////                }
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
                            fetchFilteredOrderData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            fetchRetailerOrdersData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new RetailerPlaceOrder());
                fragmentTransaction.commit();

            }
        });
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
            fetchRetailerOrdersData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;

    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() > 9) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    private void openCalenderPopup(String date_type) {
        dateType = date_type;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.DialogTheme, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    private void fetchRetailerOrdersData() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        pageNumberOrder = 0;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", pageNumberOrder);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_FETCH_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                try {

                    if (filters.size() == 1) {
                        filters.addAll(OrderStatusKVP.values());
                        arrayAdapterFeltter.notifyDataSetChanged();
                    }

                    Log.i("ORDERS DATA - ", result.toString());
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerOrdersModel>>() {
                    }.getType();
                    OrdersList = gson.fromJson(result.get("Data").toString(), type);
                    tv_shipment_no_data.setVisibility(View.GONE);

//                    pageNumberOrder = Integer.parseInt(result.getString("Count"));
                    totalEntriesOrder = Double.parseDouble(String.valueOf(result.get("Count")));
                    totalPagesOrder = Math.ceil(totalEntriesOrder / 10);


                    Log.i("OrdersList", String.valueOf(OrdersList));
                    InvoiceStatusKVP = statusKVP.getInvoiceStatus();
                    mAdapter = new RetailerOrdersAdapter(getContext(), OrdersList, OrderStatusKVP, InvoiceStatusKVP);
                    recyclerView.setAdapter(mAdapter);
                    if (OrdersList.size() != 0) {
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void performPaginationOrder() throws JSONException {

        loader.showLoader();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", pageNumberOrder);
        if (Filter_selected.equals("date")) {
            if (!fromDate.equals(""))
                jsonObject.put(Filter_selected1, fromDate + "T00:00:00.000Z");
            else if (!toDate.equals(""))
                jsonObject.put(Filter_selected1, toDate + "T00:00:00.000Z");
            if (!toDate.equals(""))
                jsonObject.put(Filter_selected2, toDate + "T23:59:59.000Z");
            else if (!fromDate.equals(""))
                jsonObject.put(Filter_selected2, fromDate + "T23:59:59.000Z");
        } else if (Filter_selected.equals("amount")) {
            if (!fromAmount.equals(""))
                jsonObject.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                jsonObject.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            jsonObject.put(Filter_selected, Filter_selected_value);
        }


        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_FETCH_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
//                if(loader == null)
//                    loader = new Loader(getContext());
                loader.hideLoader();

//                btn_load_more.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerOrdersModel>>() {
                    }.getType();
//                OrdersList = gson.fromJson(result.toString(), type);
//                ((DistributorOrdersAdapter) recyclerView.getAdapter()).addListItem(OrdersList);
                    List<RetailerOrdersModel> OrdersList_temp = new ArrayList<>();
                    OrdersList_temp = gson.fromJson(result.get("Data").toString(), type);
                    OrdersList.addAll(OrdersList_temp);
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    // loader.hideLoader();
                    e.printStackTrace();
                }

//                if (OrdersList.size() < 4) {
//                    if (rv_filter.getVisibility() == View.GONE) {
//
//                        rv_filter.setVisibility(View.VISIBLE);
//                        TranslateAnimation animate1 = new TranslateAnimation(
//                                0,                 // fromXDelta
//                                0,                 // toXDelta
//                                -rv_filter.getHeight(),  // fromYDelta
//                                0);                // toYDelta
//                        animate1.setDuration(250);
//                        animate1.setFillAfter(true);
//                        rv_filter.clearAnimation();
//                        rv_filter.startAnimation(animate1);
//                    }
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);

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
                13000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);

    }

    private void fetchFilteredOrderData() throws JSONException {
        loader.showLoader();
        Log.i("map", "in function");
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);
        pageNumberOrder = 0;
        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", 0);
        if (Filter_selected.equals("date")) {
            if (!fromDate.equals(""))
                jsonObject.put(Filter_selected1, fromDate + "T00:00:00.000Z");
            else if (!toDate.equals(""))
                jsonObject.put(Filter_selected1, toDate + "T00:00:00.000Z");
            if (!toDate.equals(""))
                jsonObject.put(Filter_selected2, toDate + "T23:59:59.000Z");
            else if (!fromDate.equals(""))
                jsonObject.put(Filter_selected2, fromDate + "T23:59:59.000Z");
        } else if (Filter_selected.equals("amount")) {
            if (!fromAmount.equals(""))
                jsonObject.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                jsonObject.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            if (Filter_selected.equals("OrderStatus"))
                jsonObject.put(Filter_selected, Integer.parseInt(Filter_selected_value));
            else
                jsonObject.put(Filter_selected, Filter_selected_value);
        }

        Log.i("map_filter_debug", String.valueOf(jsonObject));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_FETCH_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                try {
                    Log.i("ORDERS DATA - ", result.toString());
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerOrdersModel>>() {
                    }.getType();
                    OrdersList = gson.fromJson(result.get("Data").toString(), type);
                    if (OrdersList.size() != 0) {
                        tv_shipment_no_data.setVisibility(View.GONE);


                    } else {
                        tv_shipment_no_data.setVisibility(View.VISIBLE);
                    }
                    Log.i("OrdersList", String.valueOf(OrdersList));
                    InvoiceStatusKVP = statusKVP.getInvoiceStatus();
                    mAdapter = new RetailerOrdersAdapter(getContext(), OrdersList, OrderStatusKVP, InvoiceStatusKVP);
                    recyclerView.setAdapter(mAdapter);
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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
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
            Log.i("fromDate", fromDate);

            first_date.setText(new StringBuilder()
                    .append(String.format("%02d", date1)).append("/").append(String.format("%02d", (month1 + 1))).append("/").append(year1));
        } else if (date_type.equals("second date")) {
            toDate = year2 + "-" + String.format("%02d", (month2 + 1)) + "-" + String.format("%02d", date2);
            second_date.setText(new StringBuilder()
                    .append(String.format("%02d", date2)).append("/").append(String.format("%02d", (month2 + 1))).append("/").append(year2));
        }
        try {
            fetchFilteredOrderData();
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
                        fetchFilteredOrderData();
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
                        fetchFilteredOrderData();
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
                    return true;
                }
                return false;
            }
        });
    }

}
