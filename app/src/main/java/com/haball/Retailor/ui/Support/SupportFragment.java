package com.haball.Retailor.ui.Support;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import android.widget.ScrollView;
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
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Retailor.ui.Dashboard.RetailerPaymentModel;
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

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView tv_shipment_no_ticket, tv_shipment_no_data;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> array = new ArrayList<>();
    private TextView btn_add_ticket_retailer;
    private String Token, DistributorId;
    private String URL_SUPPORT = "http://175.107.203.97:4014/api/support/Search";
    private SupportDashboardRetailerModel supportViewModel;
    private List<SupportDashboardRetailerModel> SupportList = new ArrayList<>();
    //spinner1
    private ArrayAdapter<String> arrayAdapterPayments, arrayAdapterPaymentsFilter;
    private String Filter_selected1, Filter_selected2;
    private TextInputLayout search_bar;
    private RelativeLayout search_rl;
    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private RelativeLayout spinner_container1;
    private RelativeLayout spinner_container;

    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterFeltter;
    private String Filter_selected = "", Filter_selected_value = "";
    private ImageButton first_date_btn, second_date_btn;
    private LinearLayout date_filter_rl, amount_filter_rl;
    private TextView first_date, second_date;
    private String fromDate = "", toDate = "";
    private RelativeLayout spinner_container_main;
    private static int y;
    private String dateType = "";
    private int year1, year2, month1, month2, date1, date2;
    private List<String> scrollEvent = new ArrayList<>();
    private Typeface myFont;
    //    private GifImageView loader;
    private Loader loader;
    private boolean byDefaultSelectCriteria = true;
    private boolean byDefaultStatus = true;
    //    private ScrollView scrollview_support;
    private int pageNumber = 0;
    private double totalPages = 0;
    private double totalEntries = 0;


    public SupportFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_support, container, false);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);

        btn_add_ticket_retailer = root.findViewById(R.id.btn_add_ticket);
        btn_add_ticket_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(((ViewGroup) getView().getParent()).getId(), new Support_Ticket_Form_Fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        //init
        recyclerView = root.findViewById(R.id.rv_support_complaints);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        tv_shipment_no_ticket = root.findViewById(R.id.tv_shipment_no_ticket);
        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);


//        scrollview_support = root.findViewById(R.id.scrollview_support);
//        scrollview_support.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.i("scrolling_1234", scrollY + " ---- " + oldScrollY);
//            }
//        });

//        tv_shipment_no_ticket.setVisibility(View.VISIBLE);
//        loader =  root.findViewById(R.id.loader);
        tv_shipment_no_ticket.setVisibility(View.GONE);
        tv_shipment_no_data.setVisibility(View.GONE);
//        loader.setVisibility(View.VISIBLE);
        loader = new Loader(getContext());

        try {
            fetchSupport();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        search_bar = root.findViewById(R.id.search_bar);
        search_rl = root.findViewById(R.id.search_rl);

        // DATE FILTERS ......
        date_filter_rl = root.findViewById(R.id.date_filter_rl);
        first_date = root.findViewById(R.id.first_date);
        first_date_btn = root.findViewById(R.id.first_date_btn);
        second_date = root.findViewById(R.id.second_date);
        second_date_btn = root.findViewById(R.id.second_date_btn);

        spinner_consolidate = (Spinner) root.findViewById(R.id.spinner_conso);
        spinner2 = (Spinner) root.findViewById(R.id.conso_spinner2);
        conso_edittext = (EditText) root.findViewById(R.id.conso_edittext);
        spinner_container1 = root.findViewById(R.id.spinner_container1);
        spinner_container = root.findViewById(R.id.spinner_container);
        spinner_container.setVisibility(View.GONE);

        spinner_container1.setVisibility(View.GONE);
        date_filter_rl.setVisibility(View.GONE);
        conso_edittext.setVisibility(View.GONE);
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Ticket ID");
        consolidate_felter.add("Status");
        consolidate_felter.add("Created Date");
        consolidate_felter.add("Issue Type");
//
//        arrayAdapterPaymentsFilter = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_dropdown_item_1line, consolidate_felter);

        arrayAdapterPaymentsFilter = new ArrayAdapter<String>(getContext(),
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
                filters = new ArrayList<>();
                spinner_container1.setVisibility(View.GONE);
                date_filter_rl.setVisibility(View.GONE);
                conso_edittext.setVisibility(View.GONE);
                search_rl.setVisibility(View.GONE);
                conso_edittext.setText("");
                first_date.setText("DD/MM/YYYY");
                second_date.setText("DD/MM/YYYY");

                if (!byDefaultSelectCriteria) {
                    try {
                        fetchSupport();
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
                    if (Filter_selected.equals("Ticket ID")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "TicketNumber";
                        conso_edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Issue Type")) {
                        Filter_selected = "IssueType";
                        spinner_container1.setVisibility(View.VISIBLE);

                        filters = new ArrayList<>();

                        filters.add("Issue Type");
                        filters.add("Make Payment");
                        filters.add("Profile");
                        filters.add("Account & Wallet");
                        filters.add("Change Password");

//                        arrayAdapterFeltter = new ArrayAdapter<>(getContext(),
//                                android.R.layout.simple_dropdown_item_1line, filters);
//
//                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        arrayAdapterFeltter = new ArrayAdapter<String>(getContext(),
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
                        arrayAdapterFeltter.notifyDataSetChanged();
                        spinner2.setAdapter(arrayAdapterFeltter);

                    } else if (Filter_selected.equals("Created Date")) {
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
                    } else if (Filter_selected.equals("Status")) {

                        Filter_selected = "Status";
                        tv_shipment_no_ticket.setVisibility(View.VISIBLE);
                        spinner_container1.setVisibility(View.VISIBLE);

                        filters = new ArrayList<>();
                        filters.add("Status");
                        filters.add("Pending");
                        filters.add("Resolved");
                        filters.add("Inactive");
//
//                        arrayAdapterFeltter = new ArrayAdapter<>(getContext(),
//                                android.R.layout.simple_dropdown_item_1line, filters);

                        arrayAdapterFeltter = new ArrayAdapter<String>(getContext(),
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
                        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        arrayAdapterFeltter.notifyDataSetChanged();
                        spinner2.setAdapter(arrayAdapterFeltter);


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

        Log.i("aaaa1111", String.valueOf(consolidate_felter));
        Log.i("ffffffff", String.valueOf(Filter_selected));
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
                            fetchSupport();
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
                    if (Filter_selected.equals("Status"))
                        Filter_selected_value = String.valueOf(i - 1);
                    else if (Filter_selected.equals("IssueType"))
                        Filter_selected_value = String.valueOf(i);

                    if (!Filter_selected_value.equals("")) {
                        try {
                            fetchFilteredSupport();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            fetchSupport();
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
//
//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(final Editable s) {
//                Log.i("text1", "check");
//                Log.i("text", String.valueOf(s));
////                Filter_selected_value = String.valueOf(s);
////                if (!Filter_selected_value.equals("")) {
////
////                    try {
////                        fetchFilteredSupport();
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                } else {
////                    try {
////                        fetchSupport();
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
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
//                                                    fetchFilteredSupport();
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            } else {
//                                                try {
//                                                    loader.showLoader();
//                                                    fetchSupport();
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
                            fetchFilteredSupport();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            loader.showLoader();
                            fetchSupport();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
//
//        scrollview_support.setNestedScrollingEnabled(false);
//
//        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollEvent = new ArrayList<>();

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (isLastItemDisplaying(recyclerView)) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                y = dy;
                if (dy <= -5) {
                    scrollEvent.add("ScrollDown");
                    Log.i("scrolling", "Scroll Down");
                } else if (dy > 5) {
                    scrollEvent.add("ScrollUp");
                    Log.i("scrolling", "Scroll Up");
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
                        if (totalPages != 0 && pageNumber < totalPages) {
//                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
                            pageNumber++;
                            try {
                                performPaginationSupport();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
//            }
        });
        return root;
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() > 8) {
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

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();

                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

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


    private void fetchSupport() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token  ", Token);
        pageNumber = 0;
        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loader.hideLoader();
                byDefaultStatus = false;
                try {
                    Log.i("onResponse => SUPPORT ", "" + response.get(0).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                Gson gson = new Gson();
                Type type = new TypeToken<List<SupportDashboardRetailerModel>>() {
                }.getType();
                try {
                    SupportList = gson.fromJson(String.valueOf(response.get(0)), type);
//                    loader.setVisibility(View.GONE);
                    JSONObject recordCountObj = response.getJSONObject(1);
                    Log.i("recordCountObj", String.valueOf(recordCountObj));
                    totalEntries = Double.parseDouble(String.valueOf(recordCountObj.getString("RecordCount")));
                    totalPages = Math.ceil(totalEntries / 10);
                    Log.i("recordCountObj_123", String.valueOf(response));
                    Log.i("SupportSize_1", String.valueOf(SupportList.size()));
                    if (SupportList.size() > 0) {
                        tv_shipment_no_ticket.setVisibility(View.GONE);
                        tv_shipment_no_data.setVisibility(View.GONE);
                        spinner_container.setVisibility(View.VISIBLE);
                    } else {
                        tv_shipment_no_ticket.setVisibility(View.VISIBLE);
                        tv_shipment_no_data.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new SupportDashboardRetailerAdapter(getContext(), SupportList);
                recyclerView.setAdapter(mAdapter);
//
//                if (SupportList.size() < 5) {
//                    if (spinner_container_main.getVisibility() == View.GONE) {
//
//                        spinner_container_main.setVisibility(View.VISIBLE);
//                        TranslateAnimation animate1 = new TranslateAnimation(
//                                0,                 // fromXDelta
//                                0,                 // toXDelta
//                                -spinner_container_main.getHeight(),  // fromYDelta
//                                0);                // toYDelta
//                        animate1.setDuration(250);
//                        animate1.setFillAfter(true);
//                        spinner_container_main.clearAnimation();
//                        spinner_container_main.startAnimation(animate1);
//
//                    }
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
//                loader.setVisibility(View.GONE);
                loader.hideLoader();
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
    }


    private void performPaginationSupport() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token  ", Token);

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
        if (Filter_selected.equals("date")) {
            loader.showLoader();
            map.put(Filter_selected1, fromDate);
            map.put(Filter_selected2, toDate);
        } else {
            loader.showLoader();
            map.put(Filter_selected, Filter_selected_value);
        }
        Log.i("map_SSSS", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loader.hideLoader();
                Log.i("response_support ", String.valueOf(response));
                Gson gson = new Gson();
                Type type = new TypeToken<List<SupportDashboardRetailerModel>>() {
                }.getType();
                try {
//                    SupportList = gson.fromJson(String.valueOf(response.get(0)), type);
                    List<SupportDashboardRetailerModel> supportDashboardRetailerModel = new ArrayList<>();
                    supportDashboardRetailerModel = gson.fromJson(String.valueOf(response.get(0)), type);
                    SupportList.addAll(supportDashboardRetailerModel);
                    Log.i("SupportSize_2", String.valueOf(SupportList.size()));

                    if (SupportList.size() > 0) {
                        tv_shipment_no_ticket.setVisibility(View.GONE);
                        tv_shipment_no_data.setVisibility(View.GONE);
                    } else {
                        tv_shipment_no_ticket.setVisibility(View.VISIBLE);
                        tv_shipment_no_data.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
//                mAdapter = new SupportDashboardRetailerAdapter(getContext(), SupportList);
//                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
//
//                if (SupportList.size() < 4) {
//                    if (spinner_container_main.getVisibility() == View.GONE) {
//
//                        spinner_container_main.setVisibility(View.VISIBLE);
//                        TranslateAnimation animate1 = new TranslateAnimation(
//                                0,                 // fromXDelta
//                                0,                 // toXDelta
//                                -spinner_container_main.getHeight(),  // fromYDelta
//                                0);                // toYDelta
//                        animate1.setDuration(250);
//                        animate1.setFillAfter(true);
//                        spinner_container_main.clearAnimation();
//                        spinner_container_main.startAnimation(animate1);
//                    }
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
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
        request.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
    }

    private void fetchFilteredSupport() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token  ", Token);
        pageNumber = 0;
        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
        if (Filter_selected.equals("date")) {
            loader.showLoader();
            map.put(Filter_selected1, fromDate);
            map.put(Filter_selected2, toDate);
        } else {
            loader.showLoader();
            map.put(Filter_selected, Filter_selected_value);
        }
        Log.i("map_SSSS", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, URL_SUPPORT, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loader.hideLoader();
                Log.i("response_support ", String.valueOf(response));
                Gson gson = new Gson();
                Type type = new TypeToken<List<SupportDashboardRetailerModel>>() {
                }.getType();
                try {
                    SupportList = gson.fromJson(String.valueOf(response.get(0)), type);
                    Log.i("SupportSize_3", String.valueOf(SupportList.size()));

                    if (SupportList.size() != 0) {
                        tv_shipment_no_data.setVisibility(View.GONE);
                        tv_shipment_no_ticket.setVisibility(View.GONE);
                    } else {
                        tv_shipment_no_data.setVisibility(View.VISIBLE);
                        tv_shipment_no_ticket.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter = new SupportDashboardRetailerAdapter(getContext(), SupportList);
                recyclerView.setAdapter(mAdapter);
//
//                if (SupportList.size() < 4) {
//                    if (spinner_container_main.getVisibility() == View.GONE) {
//
//                        spinner_container_main.setVisibility(View.VISIBLE);
//                        TranslateAnimation animate1 = new TranslateAnimation(
//                                0,                 // fromXDelta
//                                0,                 // toXDelta
//                                -spinner_container_main.getHeight(),  // fromYDelta
//                                0);                // toYDelta
//                        animate1.setDuration(250);
//                        animate1.setFillAfter(true);
//                        spinner_container_main.clearAnimation();
//                        spinner_container_main.startAnimation(animate1);
//                    }
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
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
        request.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
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
            fetchFilteredSupport();
        } catch (JSONException e) {
            e.printStackTrace();
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
        Log.i("scrolling123", scroll);
        return scroll;
    }
}
