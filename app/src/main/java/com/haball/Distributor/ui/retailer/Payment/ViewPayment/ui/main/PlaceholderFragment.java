package com.haball.Distributor.ui.retailer.Payment.ViewPayment.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.retailer.Payment.Adapters.Payment_View_Adapter;
import com.haball.Distributor.ui.retailer.Payment.Models.Payment_View_Model;
import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private String PaymentId, isEditable;
    private String URL_Payment_Data = "https://175.107.203.97:4013/api/retailerinvoice/";
    private TextInputEditText txt_orderID, txt_company_order, txt_created_date_order, txt_status_order, txt_comments;
    private TextInputEditText txt_companyName, txt_paymentID, txt_created_date, txt_confirm, txt_bank, txt_authorization_id, txt_settlement_id, txt_status, txt_amount, txt_transaction_charges, txt_total_amount;
    private RecyclerView rv_dist_retailer_order_details;
    private RecyclerView.Adapter rv_productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Payment_View_Model> invo_productList = new ArrayList<>();
    private String Token;
    private String DistributorId;

    private PageViewModel pageViewModel;

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
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("PaymentId",
                Context.MODE_PRIVATE);
        PaymentId = sharedPreferences3.getString("PaymentId", "");
        isEditable = sharedPreferences3.getString("IsEditable", "");
        if (isEditable.equals("0")) {
            URL_Payment_Data = "https://175.107.203.97:4013/api/retailerinvoice/";
        } else {
            URL_Payment_Data = "https://175.107.203.97:4013/api/retailerprepaidrequest/";
        }
        Log.i("PaymentId", PaymentId);
        if (!URL_Payment_Data.contains(PaymentId)) {
            URL_Payment_Data = URL_Payment_Data + PaymentId;
            Log.i("URL_Order_Data", URL_Payment_Data);
        }
        View rootView = null;


        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case 1: {

                rootView = inflater.inflate(R.layout.dist_retailer_payment, container, false);
                txt_companyName = rootView.findViewById(R.id.txt_companyName);
                txt_companyName.setEnabled(false);
                txt_paymentID = rootView.findViewById(R.id.txt_paymentID);
                txt_paymentID.setEnabled(false);
                txt_created_date = rootView.findViewById(R.id.txt_created_date);
                txt_created_date.setEnabled(false);
                txt_confirm = rootView.findViewById(R.id.txt_confirm);
                txt_confirm.setEnabled(false);
                txt_bank = rootView.findViewById(R.id.txt_bank);
                txt_bank.setEnabled(false);
                txt_authorization_id = rootView.findViewById(R.id.txt_authorization_id);
                txt_authorization_id.setEnabled(false);
                txt_settlement_id = rootView.findViewById(R.id.txt_settlement_id);
                txt_settlement_id.setEnabled(false);
                txt_status = rootView.findViewById(R.id.txt_status);
                txt_status.setEnabled(false);
                txt_amount = rootView.findViewById(R.id.txt_amount);
                txt_amount.setEnabled(false);
                txt_transaction_charges = rootView.findViewById(R.id.txt_transaction_charges);
                txt_transaction_charges.setEnabled(false);
                txt_total_amount = rootView.findViewById(R.id.txt_total_amount);
                txt_total_amount.setEnabled(false);

                getPaymentData();

                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.dist_retailer_order, container, false);
                txt_orderID = rootView.findViewById(R.id.txt_orderID);
                txt_company_order = rootView.findViewById(R.id.txt_company_order);
                txt_created_date_order = rootView.findViewById(R.id.txt_created_date_order);
                txt_status_order = rootView.findViewById(R.id.txt_status_order);
                txt_comments = rootView.findViewById(R.id.txt_comments);

                txt_orderID.setEnabled(false);
                txt_company_order.setEnabled(false);
                txt_created_date_order.setEnabled(false);
                txt_status_order.setEnabled(false);
                txt_comments.setEnabled(false);
                getOrderData();
                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.dist_retailer_order_details, container, false);
                rv_dist_retailer_order_details = rootView.findViewById(R.id.rv_dist_retailer_order_details);
                rv_dist_retailer_order_details.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                rv_dist_retailer_order_details.setLayoutManager(layoutManager);


                getOrderDetailsData();
                break;
            }
        }
        return rootView;

    }


    private void getOrderData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Payment_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Order Data response", String.valueOf(response));
                try {
                    txt_orderID.setText(String.valueOf(response.get("InvoiceOrderNumber")));
                    txt_company_order.setText(String.valueOf(response.get("RetailerCompanyName")));
                    txt_created_date_order.setText(String.valueOf(response.get("RetailerOrderCreatedDate")).split("T")[0]);
                    txt_status_order.setText(String.valueOf(response.get("OrderStatus")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new HaballError().printErrorMessage(getContext(), error);
                        new ProcessingError().showError(getContext());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void getOrderDetailsData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Payment_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Payment_View_Model>>() {
                }.getType();
                try {
                    invo_productList = gson.fromJson(response.get("OrderDetails").toString(), type);
                    Log.i("OrderDetails", String.valueOf(response.get("OrderDetails")));
                    Payment_View_Adapter productAdapter = new Payment_View_Adapter(getContext(), invo_productList);
                    rv_dist_retailer_order_details.setAdapter(productAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new HaballError().printErrorMessage(getContext(), error);
                        new ProcessingError().showError(getContext());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void getPaymentData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Payment_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Order Data response2", String.valueOf(response));
                try {
                    txt_companyName.setText(String.valueOf(response.get("RetailerCompanyName")));
                    txt_paymentID.setText(String.valueOf(response.get("PaymentID")));
                    txt_created_date.setText(String.valueOf(response.get("PaymentCreatedDate")).split("T")[0]);
//                    txt_confirm.setText(String.valueOf(response.get("")));
//                    txt_bank.setText(String.valueOf(response.get("RetailerOrderNumber")));
//                    txt_authorization_id.setText(String.valueOf(response.get("RetailerOrderNumber")));
//                    txt_settlement_id.setText(String.valueOf(response.get("RetailerOrderNumber")));
                    txt_status.setText(String.valueOf(response.get("Status")));
                    txt_amount.setText(String.valueOf(response.get("Amount")));
//                    txt_transaction_charges.setText(String.valueOf(response.get("RetailerOrderNumber")));
                    txt_total_amount.setText(String.valueOf(response.get("Amount")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new HaballError().printErrorMessage(getContext(), error);
                        new ProcessingError().showError(getContext());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(stringRequest);

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