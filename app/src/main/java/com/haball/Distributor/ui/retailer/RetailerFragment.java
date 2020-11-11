package com.haball.Distributor.ui.retailer;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
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
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.Distributor.ui.retailer.Retailor_Management.Adapter.Retailer_Management_Dashboard_Adapter;
import com.haball.Distributor.ui.retailer.Retailor_Management.Model.Retailer_Management_Dashboard_Model;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Retailor.RetailorDashboard;

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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RetailerFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RetailerViewModel shareViewModel;
    private String URL_Retailers = "https://175.107.203.97:4013/api/retailer/search";
    private RecyclerView recyclerView;
    private Button btn_load_more;
    private int pageNumber = 0;
    private RecyclerView.Adapter mAdapter;
    private TextView tv_shipment_no_data;
    private RecyclerView.LayoutManager layoutManager;
    private List<Retailer_Management_Dashboard_Model> RetailerList = new ArrayList<>();
    private String Token, DistributorId;
    private String Filter_selected = "", Filter_selected_value = "";
    private RelativeLayout rv_filter;
    private double totalPages = 0;
    private double totalEntries = 0;

    private Spinner spinner_consolidate;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;


    private RelativeLayout spinner_container, spinner_container1;
    private String Filter_selected1, Filter_selected2;
    private TextInputLayout search_bar;

    private String dateType = "";
    private int year1, year2, month1, month2, date1, date2;

    private ImageButton first_date_btn, second_date_btn;
    private LinearLayout date_filter_rl, amount_filter_rl;
    private TextView first_date, second_date;
    private EditText et_amount1, et_amount2;

    private String fromDate = "", toDate = "", fromAmount = "", toAmount = "";
    private RelativeLayout spinner_container_main;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout search_rl;
    private Typeface myFont;
    private Loader loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(RetailerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_retailer__dashboard, container, false);

        loader = new Loader(getContext());

        tv_shipment_no_data = root.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);
        spinner_container_main = root.findViewById(R.id.spinner_container_main);
        search_bar = root.findViewById(R.id.search_bar);
        // btn_load_more = root.findViewById(R.id.btn_load);
        rv_filter = root.findViewById(R.id.spinner_container_main);

//        SpannableString content = new SpannableString("Load More");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//
//       btn_load_more.setText(content);
////        btn_load_more.setVisibility(View.GONE);
//
//        btn_load_more.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText( getContext(),"Clicked",Toast.LENGTH_SHORT ).show();
//                pageNumber++;
//                try {
//                    performPagination();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//        } );

        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_retailer_management);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
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
        consolidate_felter.add("Select Criteria");
        consolidate_felter.add("Retailer Code");
        consolidate_felter.add("Company");
        consolidate_felter.add("Date");
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
                    fetchRetailer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);

                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                    Filter_selected = consolidate_felter.get(i);

                    spinner2.setSelection(0);
                    conso_edittext.setText("");

                    if (Filter_selected.equals("Retailer Code")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "RetailerCode";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Company")) {
                        search_bar.setHint("Search by " + Filter_selected);
                        Filter_selected = "CompanyName";
                        conso_edittext.setVisibility(View.VISIBLE);
                        search_rl.setVisibility(View.VISIBLE);
                    } else if (Filter_selected.equals("Date")) {
//                        Toast.makeText(getContext(), "Delivery Date selected", Toast.LENGTH_LONG).show();
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
                    } else if (Filter_selected.equals("Status")) {
                        Filter_selected = "Status";
                        spinner_container1.setVisibility(View.VISIBLE);
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

        filters = new ArrayList<>();
        filters.add("Status");
        filters.add("Disconnected");
        filters.add("Connected");
//        filters.add("Pending");


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


        Log.i("aaaa1111", String.valueOf(consolidate_felter));
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
                        fetchRetailer();
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

//                    Filter_selected_value = String.valueOf(i - 1);
                    if (filters.get(i).equals("Disconnected"))
                        Filter_selected_value = "2";
                    else if (filters.get(i).equals("Connected"))
                        Filter_selected_value = "1";
                    else {
                        Filter_selected_value = "";
                        Filter_selected = "";

                    }
                    Log.i("Filter_selected_value", Filter_selected_value);
                    try {
                        fetchFilteredRetailer();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        arrayAdapterFeltter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterFeltter.notifyDataSetChanged();
        spinner2.setAdapter(arrayAdapterFeltter);


//        conso_edittext.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                Log.i("text1", "check");
//                Log.i("text", String.valueOf(s));
//                Filter_selected_value = String.valueOf(s);
//                if (!Filter_selected_value.equals("")) {
//
//                    try {
//                        fetchFilteredRetailer();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        fetchRetailer();
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
                            fetchFilteredRetailer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            fetchRetailer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
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

//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
//                    if (totalPages != 0 && pageNumber < totalPages) {
////                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
//                    }
//                }

                if (isLastItemDisplaying(recyclerView)) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (totalPages != 0 && pageNumber < totalPages) {
                            Log.i("map_Retailer_debug", "in scroll listener: " + totalPages);
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
        try {
            fetchRetailer();
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

    private void performPagination() throws JSONException {
        loader.showLoader();
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
        map.put("PageNumber", pageNumber);
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
            map.put(Filter_selected1, fromAmount);
            map.put(Filter_selected2, toAmount);
        } else if (!Filter_selected.equals("")) {
            map.put(Filter_selected, Filter_selected_value);
        }

        Log.i("map_Retailer_debug", String.valueOf(map));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_Retailers, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                Log.i("Payments Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<Retailer_Management_Dashboard_Model>>() {
                }.getType();
                try {
                    List<Retailer_Management_Dashboard_Model> tempList = gson.fromJson(result.get("Data").toString(), type);
                    RetailerList.addAll(tempList);

                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
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


    private void fetchRetailer() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        pageNumber = 0;
        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);


        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_Retailers, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                Log.i("Payments_Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<Retailer_Management_Dashboard_Model>>() {
                }.getType();
                try {
                    totalEntries = Double.parseDouble(String.valueOf(result.get("Count")));
                    totalPages = Math.ceil(totalEntries / 10);


                    RetailerList = gson.fromJson(result.get("Data").toString(), type);
                    Log.i("Payments_Requests", String.valueOf(RetailerList.size()));
                    mAdapter = new Retailer_Management_Dashboard_Adapter(getContext(), RetailerList);
                    recyclerView.setAdapter(mAdapter);
                    if (RetailerList.size() != 0) {
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


    private void fetchFilteredRetailer() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        pageNumber = 0;

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);
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
            map.put(Filter_selected1, fromAmount);
            map.put(Filter_selected2, toAmount);
        } else {
            map.put(Filter_selected, Filter_selected_value);
        }

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_Retailers, map, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                Log.i("Payments Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<Retailer_Management_Dashboard_Model>>() {
                }.getType();
                try {
                    RetailerList = gson.fromJson(result.get("Data").toString(), type);

                    mAdapter = new Retailer_Management_Dashboard_Adapter(getContext(), RetailerList);
                    recyclerView.setAdapter(mAdapter);
                    if (RetailerList.size() != 0) {
                        tv_shipment_no_data.setVisibility(View.GONE);

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
            fetchFilteredRetailer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new HomeFragment());
                    fragmentTransaction.commit();
                }
                return false;
            }
        });

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
                        fetchFilteredRetailer();
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
                        fetchFilteredRetailer();
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