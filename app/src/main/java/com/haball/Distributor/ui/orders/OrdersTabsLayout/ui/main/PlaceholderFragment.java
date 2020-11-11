package com.haball.Distributor.ui.orders.OrdersTabsLayout.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.orders.Adapter.CompanyFragmentAdapter;
import com.haball.Distributor.ui.orders.Adapter.DistributorOrderAdapter;
import com.haball.Distributor.ui.orders.Adapter.OrderSummaryAdapter;
import com.haball.Distributor.ui.orders.Adapter.OrdersItemsAdapter;
import com.haball.Distributor.ui.orders.Models.Company_Fragment_Model;
import com.haball.Distributor.ui.orders.Models.OrderFragmentModel;
import com.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs.Orders_Items_Fragment;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private RecyclerView recyclerView, recyclerView1;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager, layoutManager1;
    private Button create_payment;
    private Spinner spinner_consolidate;


    private List<Company_Fragment_Model> CompanyList;
    private String URL_Company = "https://175.107.203.97:4013/api/company/ReadActiveCompanyOrders/";
    private String Token, DistributorId;
    private String Filter_selected, Filter_selected_value;
    private Spinner spinner2;
    private EditText conso_edittext;
    private List<String> consolidate_felter = new ArrayList<>();
    private List<String> filters = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private ArrayAdapter<String> arrayAdapterFeltter;
    private ViewGroup mycontainer;
    private LayoutInflater myinflater;
    private ViewPager mPager;
    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private Button place_item_button;

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
                rootView = inflater.inflate(R.layout.fragment_place_order, container, false);
                final ViewPager pager = getActivity().findViewById(R.id.view_pager5);
                Holderorders(rootView, pager);
                break;
            }

            case 2: {
                rootView = inflater.inflate(R.layout.fragment_order__summary, container, false);
                break;
            }

        }
        return rootView;


    }


    private void Holderorders(final View root, ViewPager pager) {

        recyclerView = root.findViewById(R.id.rv_order_ledger);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        try {
            fetchCompany(pager);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void fetchCompany(final ViewPager pager) throws JSONException {
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

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_Company, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
//                loader.hideLoader();
                Log.i("Payments Requests", result.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<List<Company_Fragment_Model>>() {
                }.getType();
                CompanyList = gson.fromJson(result.toString(), type);
                Log.i("CompanyList", String.valueOf(CompanyList));
                mAdapter = new CompanyFragmentAdapter(getContext(), CompanyList, pager);
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
//
//
//    private void fetchFilteredOrders() throws JSONException {
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token","");
//        Log.i("Token", Token);
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id","");
//        Log.i("DistributorId ", DistributorId);
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        map.put("TotalRecords", 10);
//        map.put("PageNumber", 0);
////        map.put("Status", -1);
//        map.put("OrderState", -1);
//        map.put(Filter_selected, Filter_selected_value);
//        Log.i("Map", String.valueOf(map));
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, URL_ORDER, map,new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                Log.i("Payments Requests", result.toString());
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderFragmentModel>>(){}.getType();
//                OrderList = gson.fromJson(result.toString(),type);
//
//                mAdapter = new DistributorOrderAdapter(getContext(),OrderList);
//                recyclerView.setAdapter(mAdapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " +Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(sr);
//    }

    private void printErrMessage(VolleyError error) {
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

