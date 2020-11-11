package com.haball.Distributor.ui.orders.OrdersTabsNew.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

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
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.Distributor_Fragment_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.Dist_OrderPlace;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.Dist_Order_Summary;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private ViewGroup mycontainer;
    private LayoutInflater myinflater;
    private Button btn_checkout;
    private HashMap<String, String> companies = new HashMap<>();
    private TextInputLayout layout_name, layout_mobile_no, layout_email_address, layout_cnic_no, layout_txt_address;
    private TextInputEditText txt_name, txt_mobile_no, txt_email_address, txt_cnic_no, txt_address;
    private List<String> company_names = new ArrayList<>();
    private String Company_selected;
    private ArrayAdapter<String> arrayAdapterPayments, arrayAdapterPaymentsFilter;
    private Spinner spinner_conso;
    private RelativeLayout spinner_retailer_details;
    private TextView retailer_heading;
    private Button btn_next;
    private String Token, DistributorId;
    private String URL_Company = "https://175.107.203.97:4013/api/company/ReadActiveCompanyOrders/"; // To be done
    private List<Distributor_Fragment_Model_DistOrder> CompanyList;
    private String object_string;
    private Typeface myFont;
    private Loader loader;

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
        View rootView = null;
        mycontainer = container;
        myinflater = inflater;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
//                rootView = inflater.inflate(R.layout.fragment_dist_main_placeorder, container, false);
//                btn_checkout = rootView.findViewById(R.id.btn_checkout);
//                final ViewPager pager = getActivity().findViewById(R.id.view_pager5);
//
//                btn_checkout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getContext(), "Checkout", Toast.LENGTH_SHORT).show();
//                        FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    }
//                });
//                break;
                rootView = inflater.inflate(R.layout.fragment_place_order_company, container, false);
                loader = new Loader(getContext());
                company_names.add(" Select Company ");
                myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
                layout_name = rootView.findViewById(R.id.layout_name);
                layout_mobile_no = rootView.findViewById(R.id.layout_mobile_no);
                layout_email_address = rootView.findViewById(R.id.layout_email_address);
                layout_cnic_no = rootView.findViewById(R.id.layout_cnic_no);
                layout_txt_address = rootView.findViewById(R.id.layout_txt_address);
                spinner_conso = rootView.findViewById(R.id.spinner_conso);
                spinner_retailer_details = rootView.findViewById(R.id.spinner_retailer_details);
                retailer_heading = rootView.findViewById(R.id.retailer_heading);
                txt_name = rootView.findViewById(R.id.txt_name);
                txt_mobile_no = rootView.findViewById(R.id.txt_mobile_no);
                txt_email_address = rootView.findViewById(R.id.txt_email_address);
                txt_cnic_no = rootView.findViewById(R.id.txt_cnic_no);
                txt_address = rootView.findViewById(R.id.txt_address);
                spinner_retailer_details.setVisibility(View.GONE);

                txt_name.setEnabled(false);
                txt_mobile_no.setEnabled(false);
                txt_email_address.setEnabled(false);
                txt_cnic_no.setEnabled(false);
                txt_address.setEnabled(false);

                new TextField().changeColor(this.getContext(), layout_name, txt_name);
                new TextField().changeColor(this.getContext(), layout_mobile_no, txt_mobile_no);
                new TextField().changeColor(this.getContext(), layout_email_address, txt_email_address);
                new TextField().changeColor(this.getContext(), layout_cnic_no, txt_cnic_no);
                new TextField().changeColor(this.getContext(), layout_txt_address, txt_address);

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
                spinner_retailer_details.setVisibility(View.GONE);

                spinner_conso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        } else {
                            try {
                                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                                ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                                ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }

                            Company_selected = company_names.get(i);
//                            spinner_retailer_details.setVisibility(View.VISIBLE);
//                            try {
//                                Toast.makeText(getContext(), "Retailer Code: " + companies.get(Company_selected) + "\nCompany Name: " + Company_selected, Toast.LENGTH_LONG).show();
//                            Log.i("Retailer", "Retailer Code: " + companies.get(Company_selected) + "\nCompany Name: " + Company_selected);
//                                fetchPaymentLedgerData(companies.get(Company_selected));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                            retailer_heading.setText(Company_selected);
//                            retailer_heading.setVisibility(View.GONE);
//                            txt_name.setVisibility(View.GONE);
                            txt_name.setText(Company_selected);
                            setCompanyDetails(i - 1);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                final ViewPager pager = getActivity().findViewById(R.id.view_pager_rpoid);
                Holderorders(rootView, pager);
                break;
            }

            case 2: {
                rootView = inflater.inflate(R.layout.fragment_dist_order__summary, container, false);
                break;
            }

        }
        return rootView;


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
//
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                    fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
                    fragmentTransaction.commit();
                    return true;
//                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                    editorOrderTabsFromDraft.putString("TabNo", "0");
//                    editorOrderTabsFromDraft.apply();
//
//                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
//                    ((FragmentActivity) getContext()).startActivity(login_intent);
//                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

    }

    private void Holderorders(final View root, ViewPager pager) {
        try {
            fetchCompany(pager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btn_next = root.findViewById(R.id.btn_next);
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getContext(), "thek hai", Toast.LENGTH_SHORT).show();
//                FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
//            }
//        });

//        recyclerView = root.findViewById(R.id.rv_order_ledger);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        try {
//            fetchRetailers(pager);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        mAdapter = new RetailerFragmentAdapter(getContext(), RetailerList, pager);
//        recyclerView.setAdapter(mAdapter);


    }

    private void fetchCompany(final ViewPager pager) throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        if (!URL_Company.contains(DistributorId))
            URL_Company = URL_Company + DistributorId;
        Log.i("URL_Company ", URL_Company);

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_Company, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                Log.i("result", String.valueOf(result));
                object_string = String.valueOf(result);
                Gson gson = new Gson();
                Type type = new TypeToken<List<Distributor_Fragment_Model_DistOrder>>() {
                }.getType();
                CompanyList = gson.fromJson(object_string, type);
                Log.i("CompanyList", String.valueOf(CompanyList));
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
                params.put("Content-Type", "application/json; charset=UTF-8");
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
        spinner_conso.setAdapter(arrayAdapterPayments);
    }

    private void setCompanyDetails(int position) {
        Log.i("companyDetail", String.valueOf(CompanyList.get(position)));
//        txt_name.setVisibility(View.GONE);
        txt_name.setText(CompanyList.get(position).getName());
//        txt_email_address.setVisibility(View.GONE);
        txt_email_address.setText(CompanyList.get(position).getEmailAddress());
//        txt_cnic_no.setVisibility(View.GONE);
        txt_cnic_no.setText(CompanyList.get(position).getNTN());
//        txt_mobile_no.setVisibility(View.GONE);
        txt_mobile_no.setText(CompanyList.get(position).getPhone());
//        txt_address.setVisibility(View.GONE);
        txt_address.setText(CompanyList.get(position).getAddress1());
        Log.i("CompanyID", CompanyList.get(position).getID());
        SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = companyInfo.edit();
        editor.putString("CompanyId", CompanyList.get(position).getID());
        editor.putString("ID", "0");
        editor.putString("CategoryIndex", "0");
        editor.apply();

        FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("tag");
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
