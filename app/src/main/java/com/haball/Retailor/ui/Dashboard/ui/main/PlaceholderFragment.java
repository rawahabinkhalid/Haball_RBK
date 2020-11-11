package com.haball.Retailor.ui.Dashboard.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Dashboard.RetailerOrderAdapter;
import com.haball.Retailor.ui.Dashboard.RetailerOrderModel;
import com.haball.Retailor.ui.Dashboard.RetailerPaymentAdapter;
import com.haball.Retailor.ui.Dashboard.RetailerPaymentModel;
import com.haball.SSL_HandShake;
import com.haball.Support.Support_Retailer.Support_Ticket_Form;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.R;
import com.haball.Retailor.ui.Dashboard.RetailerOrderAdapter;
import com.haball.Retailor.ui.Dashboard.RetailerOrderModel;
import com.haball.Retailor.ui.Dashboard.RetailerPaymentAdapter;
import com.haball.Retailor.ui.Dashboard.RetailerPaymentModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RetailerPaymentModel paymentsViewModel;
    private RecyclerView.Adapter OrdersAdapter;
    private RecyclerView recyclerView, recyclerViewPayment;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String Token, DistributorId;
    private RelativeLayout search_rl;
    private String URL = "http://175.107.203.97:4014/api/prepaidrequests/search";
    private String URL_DISTRIBUTOR_ORDERS = "http://175.107.203.97:4014/api/Orders/Search";
    //    private String URL_DISTRIBUTOR_PAYMENTS_COUNT = "https://175.107.203.97:4013/api/prepaidrequests/searchCount";
//    private String URL_DISTRIBUTOR_ORDERS_COUNT = "https://175.107.203.97:4013/api/orders/searchCount";
    private TextView tv_shipment_no_data, tv_shipment_no_data1;
    private List<RetailerPaymentModel> PaymentsList = new ArrayList<>();
    //spiner1

    private String Filter_selected = "", Filter_selected_value = "";
    //spinner2
    private Spinner payment_retailer_spiner2;
    private List<String> payment_filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter_PaymentFeltter;
    private EditText edt_payment_ret;

    private TextView value_unpaid_amount, value_paid_amount;
    private List<RetailerPaymentModel> PaymentsRequestList = new ArrayList<>();
    private List<RetailerOrderModel> OrdersList = new ArrayList<>();
    // private String Token, DistributorId;

    private PageViewModel pageViewModel;
    private RelativeLayout spinner_container1;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter;
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private Button consolidate;
    private String Filter_selected1, Filter_selected2;

    private TextInputLayout search_bar;
    private Button btn_load_more;
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
    private RelativeLayout rv_filter, spinner_container_main, spinner_container;
    //    private ScrollView scroll_view_main;
//    private ObservableScrollView scroll_view_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout line_bottom;


    private Button create_payment;
    private Typeface myFont;
    private Loader loader;
    boolean byDefaultSelectCriteria = true;
    boolean byDefaultStatus = true;

    private static final String ARG_SECTION_NUMBER = "section_number";


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
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // View root = inflater.inflate( R.layout.activity_dashboard__tabs, container, false );
        View root = null;

        SharedPreferences add_more_product = getContext().getSharedPreferences("add_more_product",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = add_more_product.edit();
        editor1.putString("add_more_product", "");
        editor1.apply();

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                loader = new Loader(getContext());
                tabName = "Payment";
                root = inflater.inflate(R.layout.fragment_dashboard_retailor, container, false);
                try {
                    paymentFragmentTask(root);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            }
            case 2: {
//                loader = new Loader(getContext());
                tabName = "Order";
                root = inflater.inflate(R.layout.fragment_orders, container, false);
                try {
                    orderFragmentTask(root);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return root;
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
        return scroll;
    }

    private void paymentFragmentTask(View root) throws JSONException {
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        tv_shipment_no_data1 = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data1.setVisibility(View.GONE);
        search_bar = root.findViewById(R.id.search_bar);
        recyclerViewPayment = root.findViewById(R.id.rv_fragment_payments);
        recyclerViewPayment.setHasFixedSize(true);
        search_rl = root.findViewById(R.id.search_rl);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewPayment.setLayoutManager(layoutManager);

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
        spinner_container = root.findViewById(R.id.spinner_container);
        spinner_container.setVisibility(View.GONE);
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);

        consolidate_felter = new ArrayList<>();
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Payment ID");
        consolidate_felter.add("Company");
        consolidate_felter.add("Status");
        consolidate_felter.add("Paid Date");
        consolidate_felter.add("Amount");

//        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, consolidate_felter);


//        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, filters);
        arrayAdapterPayments = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, consolidate_felter) {
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
//        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
//        spinner_consolidate.setDropDownWidth(width - 20 - 15);
        spinner_consolidate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                Toast.makeText(getContext(), consolidate_felter.get(i), Toast.LENGTH_LONG).show();
                spinner_container1.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                date_filter_rl.setVisibility(View.GONE);
                amount_filter_rl.setVisibility(View.GONE);
                search_rl.setVisibility(View.GONE);
                conso_edittext.setText("");
                et_amount1.setText("");
                et_amount2.setText("");
                first_date.setText("DD/MM/YYYY");
                second_date.setText("DD/MM/YYYY");

                if (!byDefaultSelectCriteria) {
                    try {
                        fetchPaymentsData();
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
//                            ((TextView) adapterView.getChildAt(0)).setCompoundDrawablesWithIntrinsicBounds(0 ,0,R.drawable.ic_arrow_drop_down_black_24dp ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    Filter_selected = consolidate_felter.get(i);

                    spinner2.setSelection(0);
                    conso_edittext.setText("");

                    if (Filter_selected.equals("Payment ID")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "InvoiceNumber";
                        conso_edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "CompanyName";
                        conso_edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Paid Date")) {
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
                        Filter_selected1 = "PaymentAmountMin";
                        Filter_selected2 = "PaymentAmountMax";
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
        Log.i("asssss", String.valueOf(consolidate_felter));
        Log.i("asdsdad", String.valueOf(Filter_selected));
//        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_consolidate.setAdapter(arrayAdapterPayments);

        filters = new ArrayList<>();
        filters.add("Status");
        filters.add("Un-Paid");
        filters.add("Cancelled");
        filters.add("Paid");
//        filters.add("Delete");
//        filters.add("Pending");
//        filters.add("Partially Paid");
//        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, filters);

        arrayAdapterFeltter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, filters) {
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
                            fetchPaymentsData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

                    Filter_selected_value = String.valueOf(filters.get(i));
                    if (Filter_selected_value.equals("Cancelled"))
                        Filter_selected_value = "Payment Processing";
//                    Log.i("Filter_selected_value", String.valueOf(i));

                    if (!Filter_selected_value.equals("")) {
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
//        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        arrayAdapterFeltter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item) {
//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                // TODO Auto-generated method stub
//                View view = super.getView(position, convertView, parent);
//                TextView text = (TextView) view.findViewById(android.R.id.text1);
//                text.setTextColor(getResources().getColor(R.color.text_color_selection));
//                text.setTextSize((float) 13.6);
//                text.setPadding(30, 0, 30, 0);
//                text.setTypeface(myFont);
//                return view;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                // TODO Auto-generated method stub
//                View view = super.getView(position, convertView, parent);
//                TextView text = (TextView) view.findViewById(android.R.id.text1);
//                text.setTextColor(getResources().getColor(R.color.text_color_selection));
//                text.setTextSize((float) 13.6);
//                text.setPadding(30, 0, 30, 0);
//                return view;
//            }
//        };

        arrayAdapterFeltter.notifyDataSetChanged();
        spinner2.setAdapter(arrayAdapterFeltter);
//
//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(final Editable s) {
////                Log.i("text1", "check");
////                Log.i("text", String.valueOf(s));
//                final String Filter_selected_value_main = String.valueOf(s);
//
//                new java.util.Timer().schedule(
//                        new java.util.TimerTask() {
//                            @Override
//                            public void run() {
//                                // your code here
//                                getActivity().runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        //your code
//
//                                        Filter_selected_value = String.valueOf(s);
//                                        if (Filter_selected_value_main.equals(Filter_selected_value)) {
//                                            if (!Filter_selected_value.equals("")) {
//                                                try {
//                                                    fetchFilteredRetailerPayments();
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            } else {
//                                                try {
//                                                    fetchPaymentsData();
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }
//                                    }
//                                });
//                            }
//                        },
//                        2500
//                );
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

//        btn_load_more = root.findViewById(R.id.btn_load_more);
        rv_filter = root.findViewById(R.id.spinner_container_main);
        line_bottom = root.findViewById(R.id.line_bottom);

        SpannableString content = new SpannableString("Load More");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        btn_load_more.setText(content);
//        btn_load_more.setVisibility(View.GONE);

//
//        btn_load_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pageNumber++;
//                try {
//                    performPaginationPayment();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        recyclerViewPayment.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if (isLastItemDisplaying(recyclerViewPayment)) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (totalPages != 0 && pageNumber < totalPages) {
//                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
                            pageNumber++;
                            try {
                                performPaginationPayment();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        fetchPaymentsData();


    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() > 9) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    private void checkAmountChanged() {
        et_amount1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    fromAmount = String.valueOf(et_amount1.getText());
                    if (tabName.equals("Payment")) {
                        try {
                            fetchFilteredRetailerPayments();
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
                            fetchFilteredRetailerPayments();
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

    private void fetchPaymentsData() throws JSONException {
        Log.i("CheckPaymentLoader", "simple");
//        if (!loader.isShowing())
        loader.showLoader();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);
        pageNumber = 0;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TotalRecords", 10);
        jsonObject.put("PageNumber", pageNumber);

        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                try {
                    totalEntries = Double.parseDouble(String.valueOf(result.get("RecordCount")));
                    totalPages = Math.ceil(totalEntries / 10);

//                    System.out.println("RESPONSE PAYMENTS" + result.getJSONArray("PrePaidRequestData"));
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerPaymentModel>>() {
                    }.getType();
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                    Log.i("PaymentsList", String.valueOf(PaymentsList));
//                    if (PaymentsList.size() < 4) {
//                        if (rv_filter.getVisibility() == View.GONE) {
//                            rv_filter.setVisibility(View.VISIBLE);
//                            TranslateAnimation animate1 = new TranslateAnimation(
//                                    0,                 // fromXDelta
//                                    0,                 // toXDelta
//                                    -rv_filter.getHeight(),  // fromYDelta
//                                    0);                // toYDelta
//                            animate1.setDuration(250);
//                            animate1.setFillAfter(true);
//                            rv_filter.clearAnimation();
//                            rv_filter.startAnimation(animate1);
//                        }
//                    }

                    mAdapter = new RetailerPaymentAdapter(getActivity(), getContext(), PaymentsList);
                    Log.i("mAdapter", String.valueOf(mAdapter));
                    recyclerViewPayment.setAdapter(mAdapter);

                } catch (JSONException e) {
                    // loader.hideLoader();
                    e.printStackTrace();
                }
                if (PaymentsList.size() != 0) {
                    tv_shipment_no_data1.setVisibility(View.GONE);
                    spinner_container.setVisibility(View.VISIBLE);
                } else {
                    tv_shipment_no_data1.setVisibility(View.VISIBLE);
                }

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
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                13000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchFilteredRetailerPayments() throws JSONException {
        Log.i("CheckPaymentLoader", "filter");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        pageNumber = 0;

        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
        if (Filter_selected.equals("date")) {
            loader.showLoader();
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate);
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate);
        } else if (Filter_selected.equals("amount")) {
//            loader = null;
            loader.showLoader();
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            loader.showLoader();
            map.put(Filter_selected, Filter_selected_value);
        }

        Log.i("Mapsssss", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
//                if(loader != null)
                loader.hideLoader();
                Log.i("retailerPayment", result.toString());

                Gson gson = new Gson();
                Type type = new TypeToken<List<RetailerPaymentModel>>() {
                }.getType();
                try {
                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                } catch (JSONException e) {
                    // loader.hideLoader();
                    e.printStackTrace();
                }
//                if (PaymentsList.size() < 4) {
//                    if (rv_filter.getVisibility() == View.GONE) {
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


                if (PaymentsList.size() != 0)
                    tv_shipment_no_data1.setVisibility(View.GONE);
                else
                    tv_shipment_no_data1.setVisibility(View.VISIBLE);

                mAdapter = new RetailerPaymentAdapter(getActivity(), getContext(), PaymentsList);
                recyclerViewPayment.setAdapter(mAdapter);
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
                params.put("Content-Type", "application/json; charset=UTF-8 ");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                13000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);

    }

    private void performPaginationPayment() throws JSONException {
        loader.showLoader();
        Log.i("CheckPaymentLoader", "pagination");

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
//        Log.i("Token", Token);
//        pageNumber = 0;
        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);

        if (Filter_selected.equals("date")) {
            loader.showLoader();
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate);
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate);
        } else if (Filter_selected.equals("amount")) {
            loader.showLoader();
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            loader.showLoader();
            map.put(Filter_selected, Filter_selected_value);
        }

        Log.i("mapRetailerPayment", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerPaymentModel>>() {
                    }.getType();
//                    PaymentsList = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
//                    Log.i("PaymentsList", String.valueOf(PaymentsList));
//
//                    mAdapter = new RetailerPaymentAdapter(getContext(), PaymentsList);
//                    Log.i("mAdapter", String.valueOf(mAdapter));
//                    recyclerViewPayment.setAdapter(mAdapter);
                    List<RetailerPaymentModel> PaymentsList_temp = new ArrayList<>();
                    PaymentsList_temp = gson.fromJson(result.getJSONArray("PrePaidRequestData").toString(), type);
                    PaymentsList.addAll(PaymentsList_temp);
//                    if (PaymentsList.size() < 4) {
//                        if (rv_filter.getVisibility() == View.GONE) {
//                            rv_filter.setVisibility(View.VISIBLE);
//                            TranslateAnimation animate1 = new TranslateAnimation(
//                                    0,                 // fromXDelta
//                                    0,                 // toXDelta
//                                    -rv_filter.getHeight(),  // fromYDelta
//                                    0);                // toYDelta
//                            animate1.setDuration(250);
//                            animate1.setFillAfter(true);
//                            rv_filter.clearAnimation();
//                            rv_filter.startAnimation(animate1);
//                        }
//                    }

                    mAdapter.notifyDataSetChanged();
                    if (PaymentsList.size() != 0)
                        tv_shipment_no_data1.setVisibility(View.GONE);
                    else
                        tv_shipment_no_data1.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    // loader.hideLoader();
                    e.printStackTrace();
                }

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

    // private void printErrorMessage(VolleyError error) {
    //     // loader.hideLoader();
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
    //                 // loader.hideLoader();
    //                 e.printStackTrace();
    //             } catch (JSONException e) {
    //                 // loader.hideLoader();
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    // }

    private void performPaginationOrder() throws JSONException {
        loader.showLoader();


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
//        Log.i("Token", Token);
        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumberOrder);
        if (Filter_selected.equals("date")) {
            loader.showLoader();
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate);
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate);
        } else if (Filter_selected.equals("amount")) {
            loader.showLoader();
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            loader.showLoader();
            map.put(Filter_selected, Filter_selected_value);
        }
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
//                if(loader == null)
//                    loader = new Loader(getContext());
                loader.hideLoader();

//                btn_load_more.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerOrderModel>>() {
                    }.getType();
//                OrdersList = gson.fromJson(result.toString(), type);
//                ((DistributorOrdersAdapter) recyclerView.getAdapter()).addListItem(OrdersList);
                    List<RetailerOrderModel> OrdersList_temp = new ArrayList<>();
                    OrdersList_temp = gson.fromJson(result.get(0).toString(), type);
                    OrdersList.addAll(OrdersList_temp);
                    OrdersAdapter.notifyDataSetChanged();
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

    private void orderFragmentTask(View root) throws JSONException {

        loader = new Loader(getContext());
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        search_rl = root.findViewById(R.id.search_rl);
        search_bar = root.findViewById(R.id.search_bar);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_fragment_orders);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

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
        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner_container1.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        amount_filter_rl.setVisibility(View.GONE);

        consolidate_felter = new ArrayList<>();
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Order ID");
        consolidate_felter.add("Company");
        consolidate_felter.add("Status");
        consolidate_felter.add("Created Date");
        consolidate_felter.add("Amount");

//        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, consolidate_felter);
        arrayAdapterPayments = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, consolidate_felter) {
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

                    if (Filter_selected.equals("Order ID")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "OrderNumber";
                        conso_edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "CompanyName";
                        conso_edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Transaction Date")) {
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
//        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_consolidate.setAdapter(arrayAdapterPayments);

        filters = new ArrayList<>();
        filters.add("Status");
        filters.add("Pending");
        filters.add("Approved");
        filters.add("Rejected");
        filters.add("Draft");
        filters.add("Cancelled");

//        arrayAdapterFeltter = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, filters);
        arrayAdapterFeltter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, filters) {
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
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    Filter_selected_value = String.valueOf(i - 1);
//                    Log.i("Filter_selected_value", String.valueOf(i));

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
//        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterFeltter.notifyDataSetChanged();
        spinner2.setAdapter(arrayAdapterFeltter);

//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(final Editable s) {
////                Log.i("text1", "check");
////                Log.i("text", String.valueOf(s));
//
//                final String Filter_selected_value_main = String.valueOf(s);
//
//                new java.util.Timer().schedule(
//                        new java.util.TimerTask() {
//                            @Override
//                            public void run() {
//                                // your code here
//                                getActivity().runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        //your code
//
//                                        Filter_selected_value = String.valueOf(s);
//                                        if (Filter_selected_value_main.equals(Filter_selected_value)) {
//                                            if (!Filter_selected_value.equals("")) {
//                                                try {
//                                                    fetchFilteredOrderData();
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            } else {
//                                                try {
//                                                    loader.showLoader();
//                                                    fetchOrderData();
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }
//                                    }
//                                });
//                            }
//                        },
//                        2500
//                );
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
                            loader.showLoader();
                            fetchOrderData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
//        btn_load_more = root.findViewById(R.id.btn_load_more);
        rv_filter = root.findViewById(R.id.spinner_container_main);
        line_bottom = root.findViewById(R.id.line_bottom);

        SpannableString content = new SpannableString("Load More");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        btn_load_more.setText(content);
//        btn_load_more.setVisibility(View.GONE);


//        btn_load_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pageNumber++;
//                try {
//                    performPaginationOrder();
//                } catch (JSONException e) {
//                    e.printStackTrace();
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

        fetchOrderData();
    }

    private void fetchOrderData() throws JSONException {

//        loader.showLoader();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", 0);
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                //                    JSONArray jsonArray = new JSONArray(result);

                Gson gson = new Gson();
                Type type = new TypeToken<List<RetailerOrderModel>>() {
                }.getType();
                try {
                    totalEntriesOrder = Double.parseDouble(String.valueOf(result.getJSONObject(1).get("RecordCount")));
                    totalPagesOrder = Math.ceil(totalEntriesOrder / 10);
                    OrdersList = gson.fromJson(result.get(0).toString(), type);
                } catch (JSONException e) {
                    // loader.hideLoader();
                    e.printStackTrace();
                }
                Log.i("OrdersList", String.valueOf(OrdersList));
                OrdersAdapter = new RetailerOrderAdapter(getContext(), OrdersList);
                recyclerView.setAdapter(OrdersAdapter);
                if (OrdersList.size() != 0) {
                    tv_shipment_no_data.setVisibility(View.GONE);
                    spinner_container.setVisibility(View.VISIBLE);
                } else {
                    tv_shipment_no_data.setVisibility(View.VISIBLE);
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
//        if (loader.isShowing())
//            loader.hideLoader();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
        pageNumberOrder = 0;
        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumberOrder);
        if (Filter_selected.equals("date")) {
            loader.showLoader();
            if (!fromDate.equals(""))
                map.put(Filter_selected1, fromDate);
            if (!toDate.equals(""))
                map.put(Filter_selected2, toDate);
        } else if (Filter_selected.equals("amount")) {
            loader.showLoader();
            if (!fromAmount.equals(""))
                map.put(Filter_selected1, fromAmount);
            if (!toAmount.equals(""))
                map.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            loader.showLoader();
            map.put(Filter_selected, Filter_selected_value);
        }

        Log.i("OrderFilter", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_DISTRIBUTOR_ORDERS, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                //                    JSONArray jsonArray = new JSONArray(result);

                Gson gson = new Gson();
                Type type = new TypeToken<List<RetailerOrderModel>>() {
                }.getType();
                try {
                    totalEntriesOrder = Double.parseDouble(String.valueOf(result.getJSONObject(1).get("RecordCount")));
                    totalPagesOrder = Math.ceil(totalEntriesOrder / 10);
                    OrdersList = gson.fromJson(result.get(0).toString(), type);
                } catch (JSONException e) {
                    // loader.hideLoader();
                    e.printStackTrace();
                }
                Log.i("OrdersList", String.valueOf(OrdersList));
                OrdersAdapter = new RetailerOrderAdapter(getContext(), OrdersList);
                recyclerView.setAdapter(OrdersAdapter);
                if (OrdersList.size() != 0) {
                    tv_shipment_no_data.setVisibility(View.GONE);
                } else {
                    tv_shipment_no_data.setVisibility(View.VISIBLE);
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
//            Log.i("fromDate", fromDate);

            first_date.setText(new StringBuilder()
                    .append(String.format("%02d", date1)).append("/").append(String.format("%02d", (month1 + 1))).append("/").append(year1));
        } else if (date_type.equals("second date")) {
            toDate = year2 + "-" + String.format("%02d", (month2 + 1)) + "-" + String.format("%02d", date2) + "T23:59:59.000Z";
            second_date.setText(new StringBuilder()
                    .append(String.format("%02d", date2)).append("/").append(String.format("%02d", (month2 + 1))).append("/").append(year2));
        }
        if (tabName.equals("Payment")) {
            try {
                fetchFilteredRetailerPayments();
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