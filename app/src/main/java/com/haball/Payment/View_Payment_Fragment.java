package com.haball.Payment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.haball.Distributor.ui.payments.CreatePaymentRequestFragment;
import com.haball.Distributor.ui.payments.ViewPDFRequest;
import com.haball.Distributor.ui.payments.ViewVoucherRequest;
import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_Payment_Fragment extends Fragment {

    private String PaymentsRequestId;
    private String PAYMENT_REQUEST_URL = "https://175.107.203.97:4013/api/prepaidrequests/";
    private String Token, DistributorId;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private TextInputEditText txt_heading, txt_paymentid, txt_created_date, txt_transaction_date, txt_bname, txt_authorization, txt_settlement, txt_amount, txt_status, txt_transaction_charges, txt_total_amount;
    private Button btn_vreciept, btn_back;
    private TextInputLayout layout_txt_heading, layout_txt_paymentid, layout_created_date, layout_transaction_date,
            layout_txt_bname, layout_txt_authorization, layout_txt_settlement, layout_txt_amount,
            layout_txt_status, layout_txt_transaction_charges, layout_txt_total_amount;
    private FragmentTransaction fragmentTransaction;
    private TextView btn_make_payment;

    public View_Payment_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (checkAndRequestPermissions()) {
        }

        View root = null;
        root = inflater.inflate(R.layout.fragment_view__payment_, container, false);
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("paymentsRequestListID",
                Context.MODE_PRIVATE);
        PaymentsRequestId = sharedPreferences3.getString("paymentsRequestListID", "");
        if (!PAYMENT_REQUEST_URL.contains(PaymentsRequestId))
            PAYMENT_REQUEST_URL = PAYMENT_REQUEST_URL + PaymentsRequestId;

        Log.i("PaymentsRequestId12", PAYMENT_REQUEST_URL);

        layout_txt_heading = root.findViewById(R.id.layout_txt_heading);
        layout_txt_paymentid = root.findViewById(R.id.layout_txt_paymentid);
        layout_created_date = root.findViewById(R.id.layout_created_date);
        layout_transaction_date = root.findViewById(R.id.layout_transaction_date);
        layout_txt_bname = root.findViewById(R.id.layout_txt_bname);
        layout_txt_authorization = root.findViewById(R.id.layout_txt_authorization);
        layout_txt_settlement = root.findViewById(R.id.layout_txt_settlement);
        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        layout_txt_status = root.findViewById(R.id.layout_txt_status);
        layout_txt_transaction_charges = root.findViewById(R.id.layout_txt_transaction_charges);
        layout_txt_total_amount = root.findViewById(R.id.layout_txt_total_amount);

        txt_transaction_charges = root.findViewById(R.id.txt_transaction_charges);
        txt_heading = root.findViewById(R.id.txt_heading);
        txt_paymentid = root.findViewById(R.id.txt_paymentid);
        txt_created_date = root.findViewById(R.id.txt_created_date);
        txt_transaction_date = root.findViewById(R.id.txt_transaction_date);
        txt_bname = root.findViewById(R.id.txt_bname);
        txt_authorization = root.findViewById(R.id.txt_authorization);
        txt_settlement = root.findViewById(R.id.txt_settlement);
        txt_amount = root.findViewById(R.id.txt_amount);
        txt_status = root.findViewById(R.id.txt_status);
        txt_transaction_charges = root.findViewById(R.id.txt_transaction_charges);
        txt_total_amount = root.findViewById(R.id.txt_total_amount);

        btn_make_payment = root.findViewById(R.id.btn_addpayment);
        btn_vreciept = root.findViewById(R.id.btn_vreciept);
        btn_back = root.findViewById(R.id.btn_back);

        new TextField().changeColor(this.getContext(), layout_txt_heading, txt_heading);
        new TextField().changeColor(this.getContext(), layout_txt_transaction_charges, txt_transaction_charges);
        new TextField().changeColor(this.getContext(), layout_created_date, txt_created_date);
        new TextField().changeColor(this.getContext(), layout_transaction_date, txt_transaction_date);
        new TextField().changeColor(this.getContext(), layout_txt_bname, txt_bname);
        new TextField().changeColor(this.getContext(), layout_txt_authorization, txt_authorization);
        new TextField().changeColor(this.getContext(), layout_txt_settlement, txt_settlement);
        new TextField().changeColor(this.getContext(), layout_txt_amount, txt_amount);
        new TextField().changeColor(this.getContext(), layout_txt_status, txt_status);
        new TextField().changeColor(this.getContext(), layout_txt_settlement, txt_settlement);
        new TextField().changeColor(this.getContext(), layout_txt_transaction_charges, txt_transaction_charges);
        new TextField().changeColor(this.getContext(), layout_txt_paymentid, txt_paymentid);
        new TextField().changeColor(this.getContext(), layout_txt_total_amount, txt_total_amount);

        txt_heading.setEnabled(false);
        txt_paymentid.setEnabled(false);
        txt_created_date.setEnabled(false);
        txt_transaction_date.setEnabled(false);
        txt_bname.setEnabled(false);
        txt_authorization.setEnabled(false);
        txt_settlement.setEnabled(false);
        txt_amount.setEnabled(false);
        txt_status.setEnabled(false);
        txt_transaction_charges.setEnabled(false);
        txt_total_amount.setEnabled(false);

        btn_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new CreatePaymentRequestFragment()).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getActivity(), DistributorDashboard.class);
                startActivity(login_intent);
                getActivity().finish();
            }
        });

        btn_vreciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPDFRequest viewPDFRequest = new ViewPDFRequest();
                try {
                    viewPDFRequest.viewPDF(getContext(), PaymentsRequestId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            fetchPaymentData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        PaymentsRequestId = getArguments().getString("PaymentsRequestId");
        return root;

    }



    private void fetchPaymentData() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        Log.i("Map", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, PAYMENT_REQUEST_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("result", String.valueOf(result));
                try {
                    if (!String.valueOf(result.get("CompanyName")).equals("null")) {
                        txt_heading.setText(String.valueOf(result.get("CompanyName")));
                        txt_heading.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("PrePaidNumber")).equals("null")) {
                        txt_paymentid.setText(String.valueOf(result.get("PrePaidNumber")));
                        txt_paymentid.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("CreatedDate")).equals("null")) {
                        txt_created_date.setText(String.valueOf(result.get("CreatedDate")).split("T")[0]);
                        txt_created_date.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("PaidDate")).equals("null")) {
                        txt_transaction_date.setText(String.valueOf(result.get("PaidDate")).split("T")[0]);
                        txt_transaction_date.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("BankName")).equals("null")) {
                        txt_bname.setText(String.valueOf(result.get("BankName")));
                        txt_bname.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("AuthID")).equals("null")) {
                        txt_authorization.setText(String.valueOf(result.get("AuthID")));
                        txt_authorization.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("SettlementID")).equals("null")) {
                        txt_settlement.setText(String.valueOf(result.get("SettlementID")));
                        txt_settlement.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("PaidAmount")).equals("null")) {
                        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
                        String Formatted_TotalAmount = formatter1.format(Double.parseDouble(result.getString("PaidAmount")));
                        txt_amount.setText(Formatted_TotalAmount);
                        txt_amount.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("Status")).equals("null")) {
                        if (String.valueOf(result.get("Status")).equals("1"))
                            txt_status.setText("Paid");
                        else if (String.valueOf(result.get("Status")).equals("0"))
                            txt_status.setText("Unpaid");
                        else if (String.valueOf(result.get("Status")).equals("-1"))
                            txt_status.setText("Processing Payment");

                        txt_status.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("TransactionCharges")).equals("null")) {
                        txt_transaction_charges.setText(String.valueOf(result.get("TransactionCharges")));
                        txt_transaction_charges.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("TotalAmount")).equals("null")) {
                        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
                        String Formatted_TotalAmount = formatter1.format(Double.parseDouble(result.getString("TotalAmount")));
                        txt_total_amount.setText(Formatted_TotalAmount);
                        txt_total_amount.setTextColor(getContext().getResources().getColor(R.color.textcolor));
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
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean checkAndRequestPermissions() {
        int permissionRead = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
