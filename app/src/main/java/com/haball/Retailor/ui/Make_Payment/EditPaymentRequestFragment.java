package com.haball.Retailor.ui.Make_Payment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EditPaymentRequestFragment extends Fragment {
    private String Token;
    private Button btn_create;

    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "http://175.107.203.97:4014/api/prepaidrequests/GetByRetailerCode";
    private String URL_PAYMENT_REQUESTS_SAVE = "http://175.107.203.97:4014/api/prepaidrequests/save";

    private List<String> CompanyNames = new ArrayList<>();
    private HashMap<String, String> companyNameAndId = new HashMap<>();

    private Spinner spinner_company;
    private ArrayAdapter<String> arrayAdapterPayments;
    private String company_names;
    private EditText txt_amount;
    private String prepaid_number, prepaid_id;
    private FragmentTransaction fragmentTransaction;
    private TextInputLayout layout_txt_amount;
    private String PrePaidNumber = "", PrePaidId = "", CompanyName = "", Amount = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_payment__screen1, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("PrePaidNumber",
                Context.MODE_PRIVATE);
        PrePaidNumber = sharedPreferences.getString("PrePaidNumber", "");
        PrePaidId = sharedPreferences.getString("PrePaidId", "");
        CompanyName = sharedPreferences.getString("CompanyName", "");
        Amount = sharedPreferences.getString("Amount", "");

        btn_create = root.findViewById(R.id.btn_create);
        btn_create.setEnabled(false);
        btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
        spinner_company = root.findViewById(R.id.spinner_company);
        txt_amount = root.findViewById(R.id.txt_amount);

        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        layout_txt_amount.setBoxStrokeColor(Color.parseColor("#e5e5e5"));
        CompanyNames.add("Company *");
        company_names = "";

        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, CompanyNames);

        spinner_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                        try {
                               ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                          ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                } else {
                        try {
                            ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                            ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                          ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    company_names = CompanyNames.get(i);
                    checkFieldsForEmptyValues();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fetchCompanyData();
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!company_names.equals("") && !String.valueOf(txt_amount.getText()).equals("")) {
                    try {
                        makeSaveRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();

            }
        };

        txt_amount.addTextChangedListener(textWatcher);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        txt_amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    txt_amount.clearFocus();
                    showDiscardDialog();
                }
                return false;
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    String txt_amounts = txt_amount.getText().toString();
                    String company = (String) spinner_company.getItemAtPosition(spinner_company.getSelectedItemPosition()).toString();
                    if (!txt_amounts.equals(Amount) || !company.equals(CompanyName)) {
                        showDiscardDialog();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

    }

    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
        alertDialog.setView(view_popup);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                fm.popBackStack();
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
    private void checkFieldsForEmptyValues() {

        String txt_amounts = txt_amount.getText().toString();
//        String company = "";
//        String company = (String) spinner_company.getItemAtPosition(spinner_company.getSelectedItemPosition()).toString();


        if (txt_amounts.equals("")) {
            btn_create.setEnabled(false);
            btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_create.setEnabled(true);
            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }


    private void makeSaveRequest() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        new EditPayment().EditPayment(getActivity(), getContext(), Token, PrePaidId, PrePaidNumber, companyNameAndId.get(company_names), txt_amount.getText().toString());
    }

    private void fetchCompanyData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_SELECT_COMPANY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        CompanyNames.add(jsonObject.getString("CompanyName"));
                        companyNameAndId.put(jsonObject.getString("CompanyName"), jsonObject.getString("DealerCode"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                arrayAdapterPayments.notifyDataSetChanged();
                spinner_company.setAdapter(arrayAdapterPayments);

                txt_amount.setText(Amount);
                Log.i("Debugging", String.valueOf(CompanyNames));
                Log.i("Debugging", String.valueOf(CompanyNames.indexOf(CompanyName)));
                Log.i("Debugging", String.valueOf(CompanyName));
//        int spinnerPosition = arrayAdapterPayments.getPosition(CompanyName);
                spinner_company.setSelection(CompanyNames.indexOf(CompanyName));

                Log.e("RESPONSE OF COMPANY ID", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
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
}


