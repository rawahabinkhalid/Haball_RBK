package com.haball.Distributor.ui.retailer.Retailor_Management;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.material.textfield.TextInputLayout;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.retailer.RetailerFragment;
import com.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.haball.TextField;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRetailer extends Fragment {
    private String RetailerId;
    private TextInputEditText mg_rt_code, mg_rt_firstname, mg_rt_email, mg_cnic_no, mg_mobile_no, mg_rt_company, mg_tr_address, mg_rt_sapcode, mg_rt_status;
    private TextInputLayout layout_mg_rt_code, layout_mg_rt_firstname, layout_mg_rt_email, layout_mg_cnic_no, layout_mg_mobile_no, layout_mg_rt_company, layout_mg_tr_address, layout_mg_rt_sapcode, layout_mg_rt_status;
    private String URL_RETAILER_DETAILS = "https://175.107.203.97:4013/api/retailer/";
    private String URL_UPDATE_RETAILER = "https://175.107.203.97:4013/api/retailer/RetailerUpdate";
    private String Token, DistributorId;
    private CheckBox check_box;
    private Button btn_close, btn_save;
    private FragmentTransaction fragmentTransaction;


    public ViewRetailer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_retailer, container, false);
        RetailerId = getArguments().getString("RetailerId");

        mg_rt_code = root.findViewById(R.id.mg_rt_code);
        mg_rt_firstname = root.findViewById(R.id.mg_rt_firstname);
        mg_rt_email = root.findViewById(R.id.mg_rt_email);
        mg_cnic_no = root.findViewById(R.id.mg_cnic_no);
        mg_mobile_no = root.findViewById(R.id.mg_mobile_no);
        check_box = root.findViewById(R.id.check_box);
        mg_rt_company = root.findViewById(R.id.mg_rt_company);
        mg_rt_status = root.findViewById(R.id.mg_rt_status);
        mg_tr_address = root.findViewById(R.id.mg_tr_address);
        mg_rt_sapcode = root.findViewById(R.id.mg_rt_sapcode);

        layout_mg_rt_code = root.findViewById(R.id.layout_mg_rt_code);
        layout_mg_rt_firstname = root.findViewById(R.id.layout_mg_rt_firstname);
        layout_mg_rt_email = root.findViewById(R.id.layout_mg_rt_email);
        layout_mg_cnic_no = root.findViewById(R.id.layout_mg_cnic_no);
        layout_mg_mobile_no = root.findViewById(R.id.layout_mg_mobile_no);
        layout_mg_rt_company = root.findViewById(R.id.layout_mg_rt_company);
        layout_mg_rt_status = root.findViewById(R.id.layout_mg_rt_status);
        layout_mg_tr_address = root.findViewById(R.id.layout_mg_tr_address);
        layout_mg_rt_sapcode = root.findViewById(R.id.layout_mg_rt_sapcode);

        btn_close = root.findViewById(R.id.btn_close);
//        btn_save = root.findViewById(R.id.btn_save);


        mg_rt_code.setEnabled(false);
        mg_rt_firstname.setEnabled(false);
        mg_rt_email.setEnabled(false);
        mg_cnic_no.setEnabled(false);
        mg_mobile_no.setEnabled(false);
        mg_rt_company.setEnabled(false);
        mg_rt_status.setEnabled(false);
        mg_tr_address.setEnabled(false);

        new TextField().changeColor(getContext(), layout_mg_rt_code, mg_rt_code);
        new TextField().changeColor(getContext(), layout_mg_rt_firstname, mg_rt_firstname);
        new TextField().changeColor(getContext(), layout_mg_rt_email, mg_rt_email);
        new TextField().changeColor(getContext(), layout_mg_cnic_no, mg_cnic_no);
        new TextField().changeColor(getContext(), layout_mg_mobile_no, mg_mobile_no);
        new TextField().changeColor(getContext(), layout_mg_rt_company, mg_rt_company);
        new TextField().changeColor(getContext(), layout_mg_rt_status, mg_rt_status);
        new TextField().changeColor(getContext(), layout_mg_tr_address, mg_tr_address);
        new TextField().changeColor(getContext(), layout_mg_rt_sapcode, mg_rt_sapcode);

//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getActivity(), "Save data", Toast.LENGTH_SHORT).show();
//                try {
//                    saveData();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new RetailerFragment());
                fragmentTransaction.commit();
            }
        });
        try {
            fetchRetailerData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void saveData() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("RetailerCode", mg_rt_code.getText());
        map.put("Status", String.valueOf((check_box.isChecked()) ? 1 : 2));
        map.put("SapCode", mg_rt_sapcode.getText());
        Log.i("Status", String.valueOf((check_box.isChecked()) ? 1 : 2));


        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_UPDATE_RETAILER, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                if (check_box.isChecked()) {
                    try {
                        Toast.makeText(getContext(), "Retailer Code " + result.getString("RetailerCode") + " has been activated successfully", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Toast.makeText(getContext(), "Retailer Code " + result.getString("RetailerCode") + " has been inactivated successfully", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new RetailerFragment());
                fragmentTransaction.commit();
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

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new RetailerFragment());
                    fragmentTransaction.commit();
                }
                return false;
            }
        });
    }

    private void fetchRetailerData() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        URL_RETAILER_DETAILS = URL_RETAILER_DETAILS + RetailerId;
        Log.i("URL_RETAILER_DETAILS ", URL_RETAILER_DETAILS);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        Log.i("Map", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_RETAILER_DETAILS, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
//                    Log.i("result", String.valueOf(result));
                    mg_rt_code.setText(result.getString("RetailerCode"));
                    mg_rt_code.setTextColor(getResources().getColor(R.color.textcolor));
                    mg_rt_firstname.setText(result.getString("Name"));
                    mg_rt_firstname.setTextColor(getResources().getColor(R.color.textcolor));
                    mg_rt_email.setText(result.getString("Email"));
                    mg_rt_email.setTextColor(getResources().getColor(R.color.textcolor));
                    mg_cnic_no.setText(result.getString("CNIC"));
                    mg_cnic_no.setTextColor(getResources().getColor(R.color.textcolor));
                    mg_mobile_no.setText(result.getString("Mobile"));
                    mg_mobile_no.setTextColor(getResources().getColor(R.color.textcolor));
                    mg_rt_company.setText(result.getString("CompanyName"));
                    mg_rt_company.setTextColor(getResources().getColor(R.color.textcolor));
                    mg_tr_address.setText(result.getString("Address"));
                    mg_tr_address.setTextColor(getResources().getColor(R.color.textcolor));
                    if (result.getString("Status").equals("1"))
                        mg_rt_status.setText("Connected");
                    else if (result.getString("Status").equals("2"))
                        mg_rt_status.setText("Disconnected");
                    else if (result.getString("Status").equals("0"))
                        mg_rt_status.setText("Pending");
                    mg_rt_status.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (result.getString("Status").equals("1"))
//                        check_box.setChecked(true);
//                    else
//                        check_box.setChecked(false);
                    Log.i("result_getString", result.getString("Address"));
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
