package com.haball.Retailor.ui.Make_Payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.Hex;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.CustomToast;
import com.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
import com.haball.SSL_HandShake;
import com.haball.TextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PaymentJazzCashApi extends Fragment {
    private String Token, ID;
    private Button btn_create;

    //    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "http://175.107.203.97:4014/api/kyc/KYCDistributorList";
    private String URL_PREPAID_REQUEST = "http://175.107.203.97:4014/api/prepaidrequests/";
    private String URL_INVOICE = "http://175.107.203.97:4014/api/invoices/";
    private String URL_PAYMENT_REQUESTS_GET_DATA = "";
    private String URL_LOGIN = "https://sandbox.jazzcash.com.pk/Sandbox/Login/Login";
    private String URL_PAYMENT_REQUESTS_GET_DATA_JAZZ_CASH = "http://175.107.203.97:4014/api/payaxis/PrePaidPay/";
    private String URL_Jazz_Cash_Transaction = "https://sandbox.jazzcash.com.pk/Sandbox/HomeV20/DoTransactionMWallet";
    private String URL_RegenerateTxnReference = "https://sandbox.jazzcash.com.pk/Sandbox/HomeV20/RegenerateGenerateTxnReference";
    private String URL_Calculate_Secure_Hash = "https://sandbox.jazzcash.com.pk/Sandbox/HomeV20/CalculateSecureHash";

    private String URL_PAYMENT_REQUESTS_SAVE = "http://175.107.203.97:4014/api/prepaidrequests/save";

    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private FragmentTransaction fragmentTransaction;
    private String prepaid_id;
    private TextInputEditText txt_company, txt_payment_id, txt_beneficiary, txt_amount, txt_transaction_charges, txt_total_amount, txt_account_no, txt_cnic, txt_otp;
    private TextInputLayout layout_company, layout_payment_id, layout_beneficiary, layout_txt_amount, layout_transaction_charges, layout_txt_total_amount, layout_txt_account_no, layout_txt_cnic, layout_txt_otp;
    private String prepaid_number;
    private Typeface myFont;
    private Loader loader;
    private int keyDel;
    private String pp_Amount = "";
    private String pp_BillReference = "";
    private String pp_Description = "";
    private String pp_Language = "";
    private String pp_MerchantID = "";
    private String pp_Password = "";
    private String pp_ProductID = "";
    private String pp_ReturnUrl = "";
    private String pp_SubMerchantID = "";
    private String pp_TxnCurrency = "";
    private String pp_TxnDateTime = "";
    private String pp_TxnExpiryDateTime = "";
    private String pp_TxnRefNo = "";
    private String pp_TxnType = "";
    private String pp_Version = "";
//    private String pp_BankID = "";
    private String pp_SecureHash = "";
    private String ppmpf_1 = "";
    private String ppmpf_2 = "";
    private String ppmpf_3 = "";
    private String ppmpf_4 = "";
    private String ppmpf_5 = "";
    private String pp_MobileNumber = "";
    private String pp_CNIC = "";
    private String aasecretkey = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pay_by_jazz_cash, container, false);

        btn_create = root.findViewById(R.id.btn_create);
        btn_create.setEnabled(false);
        btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        txt_company = root.findViewById(R.id.txt_company);
        layout_company = root.findViewById(R.id.layout_company);
        txt_payment_id = root.findViewById(R.id.txt_payment_id);
        layout_payment_id = root.findViewById(R.id.layout_payment_id);
        txt_beneficiary = root.findViewById(R.id.txt_beneficiary);
        layout_beneficiary = root.findViewById(R.id.layout_beneficiary);
        txt_amount = root.findViewById(R.id.txt_amount);
        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        txt_transaction_charges = root.findViewById(R.id.txt_transaction_charges);
        layout_transaction_charges = root.findViewById(R.id.layout_transaction_charges);
        txt_total_amount = root.findViewById(R.id.txt_total_amount);
        layout_txt_total_amount = root.findViewById(R.id.layout_txt_total_amount);
        txt_account_no = root.findViewById(R.id.txt_account_no);
        layout_txt_account_no = root.findViewById(R.id.layout_txt_account_no);
        txt_cnic = root.findViewById(R.id.txt_cnic);
        layout_txt_cnic = root.findViewById(R.id.layout_txt_cnic);
        txt_otp = root.findViewById(R.id.txt_otp);
        layout_txt_otp = root.findViewById(R.id.layout_txt_otp);
        loader = new Loader(getContext());

        new TextField().changeColor(getContext(), layout_company, txt_company);
        new TextField().changeColor(getContext(), layout_payment_id, txt_payment_id);
        new TextField().changeColor(getContext(), layout_beneficiary, txt_beneficiary);
        new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
        new TextField().changeColor(getContext(), layout_transaction_charges, txt_transaction_charges);
        new TextField().changeColor(getContext(), layout_txt_total_amount, txt_total_amount);
        new TextField().changeColor(getContext(), layout_txt_account_no, txt_account_no);
        new TextField().changeColor(getContext(), layout_txt_cnic, txt_cnic);
        new TextField().changeColor(getContext(), layout_txt_otp, txt_otp);
        SharedPreferences JazzCash = ((FragmentActivity) getContext()).getSharedPreferences("PaymentId",
                Context.MODE_PRIVATE);
//        String payment_number = JazzCash.getString("PrePaidNumber", "");
        String payment_id = JazzCash.getString("PrePaidId", "");
        String type = JazzCash.getString("Type", "");
        if (type.equals("Invoice")) {
            URL_PAYMENT_REQUESTS_GET_DATA = URL_INVOICE;
        } else {
            URL_PAYMENT_REQUESTS_GET_DATA = URL_PREPAID_REQUEST;
        }
        if (!URL_PAYMENT_REQUESTS_GET_DATA.contains("/" + payment_id))
            URL_PAYMENT_REQUESTS_GET_DATA = URL_PAYMENT_REQUESTS_GET_DATA + payment_id;

        if (type.equals("Invoice")) {
            getJazzCashDataFromInvoice();
        } else {
            getJazzCashDataFromPrepaidPayment();
        }

//        txt_cnic.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                txt_cnic.setOnKeyListener(new View.OnKeyListener() {
//                    @Override
//                    public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                        if (keyCode == KeyEvent.KEYCODE_DEL)
//                            keyDel = 1;
//                        return false;
//                    }
//                });
//
//                if (keyDel == 0) {
//                    int len = txt_cnic.getText().length();
//                    if (len == 5 || len == 13) {
//                        txt_cnic.setText(txt_cnic.getText() + "-");
//                        txt_cnic.setSelection(txt_cnic.getText().length());
//                    }
//                } else {
//                    keyDel = 0;
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//            }
//        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!String.valueOf(txt_account_no.getText()).equals("") && !String.valueOf(txt_cnic.getText()).equals("")) {
                    btn_create.setEnabled(false);
                    btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

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
        txt_account_no.addTextChangedListener(textWatcher);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String txt_account = String.valueOf(txt_account_no.getText());
        final String txt_cnic_no = String.valueOf(txt_cnic.getText());
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        txt_amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    txt_amount.clearFocus();
                    if (!txt_account.equals("") || !txt_cnic_no.equals("")) {
                        showDiscardDialog();
                    } else {
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
                    }
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
                    if (!txt_account.equals("") || !txt_cnic_no.equals("")) {
                        showDiscardDialog();
                        return true;
                    } else {
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
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
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();

                Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();

//                fm.popBackStack();
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

    private void getJazzCashDataFromPrepaidPayment() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

//        JSONObject map = new JSONObject();
//        map.put("ID", 0);
//        map.put("DealerCode", companyNameAndId.get(company_names));
////        map.put("DealerCode", "201911672");
//        map.put("PaidAmount", txt_amount.getText().toString());

//        Log.i("JSON ", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_GET_DATA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
//                loader.hideLoader();
                Log.i("Response_PR", result.toString());
                try {
                    txt_payment_id.setText(String.valueOf(result.getString("PrePaidNumber")));
                    if (!String.valueOf(txt_payment_id.getText()).equals(""))
                        txt_payment_id.setTextColor(getResources().getColor(R.color.textcolor));
                    txt_company.setText(result.getString("CompanyName"));
                    if (!String.valueOf(txt_company.getText()).equals(""))
                        txt_company.setTextColor(getResources().getColor(R.color.textcolor));

//                    BigInteger d = new BigInteger(result.getString("PaidAmount"));
                    DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
                    String yourFormattedString1 = formatter1.format(Double.parseDouble(result.getString("PaidAmount")));
                    txt_amount.setText(yourFormattedString1);

//                    txt_amount.setText(String.valueOf(new BigDecimal(d, 2)));

                    if (!String.valueOf(txt_amount.getText()).equals(""))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
                    txt_beneficiary.setText(result.getString("CompanyName"));
                    if (!String.valueOf(txt_beneficiary.getText()).equals(""))
                        txt_beneficiary.setTextColor(getResources().getColor(R.color.textcolor));
                    yourFormattedString1 = formatter1.format(Double.parseDouble(result.getString("TransactionCharges")));
                    txt_transaction_charges.setText(yourFormattedString1);
                    if (!String.valueOf(txt_transaction_charges.getText()).equals(""))
                        txt_transaction_charges.setTextColor(getResources().getColor(R.color.textcolor));
                    yourFormattedString1 = formatter1.format(Double.parseDouble(result.getString("TotalAmount")));
                    txt_total_amount.setText(yourFormattedString1);
                    if (!String.valueOf(txt_total_amount.getText()).equals(""))
                        txt_total_amount.setTextColor(getResources().getColor(R.color.textcolor));

                    pp_Amount = result.getString("TotalAmount") + "00";
                    pp_BillReference = "T"+result.getString("PrePaidNumber");

                    txt_cnic.setText("345678");
                    txt_account_no.setText("03123456789");
                    getJazzCashDataFromPrePayAxisAPI(result.getString("PrePaidNumber"));
                } catch (JSONException e) {
                    Log.i("Response PR", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();

                btn_create.setEnabled(true);
                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
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
                1500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void getJazzCashDataFromPrePayAxisAPI(String PrePaidNumber) {
        if(!URL_PAYMENT_REQUESTS_GET_DATA_JAZZ_CASH.contains("/" + PrePaidNumber))
            URL_PAYMENT_REQUESTS_GET_DATA_JAZZ_CASH = URL_PAYMENT_REQUESTS_GET_DATA_JAZZ_CASH + PrePaidNumber;

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_GET_DATA_JAZZ_CASH, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                Log.i("Response_PR", result.toString());
                try {
                    aasecretkey = result.getString("aasecretkey");
                    pp_Description = result.getString("pp_Description");
                    pp_Language = result.getString("pp_Language");
                    pp_MerchantID = result.getString("pp_MerchantID");
                    pp_Password = result.getString("pp_Password");
                    pp_ReturnUrl = result.getString("pp_ReturnURL");
//                    pp_SubMerchantID = result.getString("pp_SubMerchantID");
                    pp_TxnCurrency = result.getString("pp_TxnCurrency");
                    pp_TxnDateTime = result.getString("pp_TxnDateTime");
                    pp_TxnExpiryDateTime = result.getString("pp_TxnExpiryDateTime");
//                    pp_TxnRefNo = result.getString("pp_TxnRefNo");
//                    pp_BillReference = result.getString("pp_TxnRefNo");
                    pp_TxnRefNo = pp_BillReference;
                    pp_BillReference = "billRef";
                    pp_Description = "Description of transaction";
                    pp_TxnType = result.getString("pp_TxnType");
                    pp_Version = result.getString("pp_Version");
//                    ppmpf_1 = result.getString("ppmpf_1");
//                    ppmpf_2 = result.getString("ppmpf_2");
//                    ppmpf_3 = result.getString("ppmpf_3");
//                    ppmpf_4 = result.getString("ppmpf_4");
//                    ppmpf_5 = result.getString("ppmpf_5");
                    generateSecureHash();

                } catch (JSONException | UnsupportedEncodingException e) {
                    Log.i("Response PR", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();

                btn_create.setEnabled(true);
                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
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
                1500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void getJazzCashDataFromInvoice() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

//        JSONObject map = new JSONObject();
//        map.put("ID", 0);
//        map.put("DealerCode", companyNameAndId.get(company_names));
////        map.put("DealerCode", "201911672");
//        map.put("PaidAmount", txt_amount.getText().toString());

//        Log.i("JSON ", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_GET_DATA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                JSONObject result = null;
                try {
                    result = response.getJSONObject("Invoice");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("Response_PR", result.toString());
                try {
                    txt_payment_id.setText(String.valueOf(result.getString("PrePaidNumber")));
                    if (!String.valueOf(txt_payment_id.getText()).equals(""))
                        txt_payment_id.setTextColor(getResources().getColor(R.color.textcolor));
                    txt_company.setText(result.getString("CompanyName"));
                    if (!String.valueOf(txt_company.getText()).equals(""))
                        txt_company.setTextColor(getResources().getColor(R.color.textcolor));

//                    BigInteger d = new BigInteger(result.getString("PaidAmount"));
                    DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
                    String yourFormattedString1 = formatter1.format(Double.parseDouble(result.getString("Amount")));
                    txt_amount.setText(yourFormattedString1);

//                    txt_amount.setText(String.valueOf(new BigDecimal(d, 2)));

                    if (!String.valueOf(txt_amount.getText()).equals(""))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
                    txt_beneficiary.setText(result.getString("CompanyName"));
                    if (!String.valueOf(txt_beneficiary.getText()).equals(""))
                        txt_beneficiary.setTextColor(getResources().getColor(R.color.textcolor));
                    yourFormattedString1 = formatter1.format(Double.parseDouble(result.getString("TransactionCharges")));
                    txt_transaction_charges.setText(yourFormattedString1);
                    if (!String.valueOf(txt_transaction_charges.getText()).equals(""))
                        txt_transaction_charges.setTextColor(getResources().getColor(R.color.textcolor));
                    yourFormattedString1 = formatter1.format(Double.parseDouble(result.getString("TotalAmount")));
                    txt_total_amount.setText(yourFormattedString1);
                    if (!String.valueOf(txt_total_amount.getText()).equals(""))
                        txt_total_amount.setTextColor(getResources().getColor(R.color.textcolor));

                    pp_Amount = result.getString("TotalAmount");
                    pp_BillReference = result.getString("PrePaidNumber");


                    txt_cnic.setText("345678");
                    txt_account_no.setText("03123456789");

                    getJazzCashDataFromPrePayAxisAPI(result.getString("PrePaidNumber"));
                } catch (JSONException e) {
                    Log.i("Response PR", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();

                btn_create.setEnabled(true);
                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
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
                1500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void checkFieldsForEmptyValues() {
        String txt_account = String.valueOf(txt_account_no.getText());
        String txt_cnic_no = String.valueOf(txt_cnic.getText());
        if (txt_cnic_no.equals("")
                || txt_account.equals("")

        ) {
            btn_create.setEnabled(false);
            btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_create.setEnabled(true);
            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }
//
//    private void loginToJazzCash() {
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("JazzCashResponse", response);
////                loader.hideLoader();
//                JSONObject resultRegen = null;
//                try {
//                    resultRegen = new JSONObject(response);
//                    if (resultRegen.getString("status").equals("success")) {
//                        new CustomToast().showToast(getActivity(), "Logged in successfully.");
//                        generateSecureHash();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loader.hideLoader();
//                new ProcessingError().showError(getContext());
//                new HaballError().printErrorMessage(getContext(), error);
//                error.printStackTrace();
//
//                btn_create.setEnabled(true);
//                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                return params;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<>();
//                map.put("username", "m.farrukhiftikhar@gmail.com");
//                map.put("password", "a1s2d3f4");
//
//                Log.i("JSON ", String.valueOf(map));
//                return map;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//    }
//
//    private void generateSecureHash() {
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_Calculate_Secure_Hash, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("JazzCashResponse123", response);
////                loader.hideLoader();
//                JSONObject resultHash;
//                try {
//                    resultHash = new JSONObject(response);
////                    if (resultHash.getString("status").equals("success")) {
//                    new CustomToast().showToast(getActivity(), resultHash.getString("hash"));
//                    payByJazzCashApi(resultHash.getString("hash"));
////                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////
////                    final JSONObject finalResultRegen = resultRegen;
////                    StringRequest sr = new StringRequest(Request.Method.POST, URL_Calculate_Secure_Hash, new Response.Listener<String>() {
////                        @Override
////                        public void onResponse(String response) {
////                            loader.hideLoader();
////                            Log.i("JSON_RESPONSE", response);
////                            JSONObject result = null;
////                            try {
////                                result = new JSONObject(response);
////
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                            Log.i("JSON_RESPONSE_JAZZ", String.valueOf(result));
//////                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
//////                Log.e("RESPONSE prepaid_number", result.toString());
////                        }
////                    }, new Response.ErrorListener() {
////                        @Override
////                        public void onErrorResponse(VolleyError error) {
////                            loader.hideLoader();
////                            new ProcessingError().showError(getContext());
////                            new HaballError().printErrorMessage(getContext(), error);
////                            error.printStackTrace();
////
////                            btn_create.setEnabled(true);
////                            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
////                        }
////                    }) {
////                        @Override
////                        public Map<String, String> getHeaders() throws AuthFailureError {
////                            Map<String, String> params = new HashMap<String, String>();
////                            params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
////                            return params;
////                        }
////
////                        @Override
////                        protected Map<String, String> getParams() throws AuthFailureError {
////
////                            Map<String, String> map = new HashMap<>();
//////        map.put("pp_Language", pp_Language);
////                            map.put("Request[pp_Language]", "EN");
//////        map.put("pp_MerchantID", pp_MerchantID);
////                            map.put("Request[pp_MerchantID]", "MC37757");
//////        map.put("pp_SubMerchantID", pp_SubMerchantID);
////                            map.put("Request[pp_SubMerchantID]", "");
//////        map.put("pp_Password", pp_Password);
////                            map.put("Request[pp_Password]", "s0xcy12dv2");
//////        map.put("pp_BankID", pp_BankID);
////                            map.put("Request[pp_BankID]", "");
//////        map.put("pp_ProductID", pp_ProductID);
////                            map.put("Request[pp_ProductID]", "");
//////        map.put("pp_TxnRefNo", pp_TxnRefNo);
////                            try {
////                                map.put("Request[pp_TxnRefNo]", finalResultRegen.getString("txnRefNumber"));
////                            } catch (JSONException e) {
////                                map.put("Request[pp_TxnRefNo]", "");
////                                e.printStackTrace();
////                            }
//////        map.put("pp_Amount", pp_Amount);
////                            map.put("Request[pp_Amount]", "32653200");
//////        map.put("pp_TxnCurrency", pp_TxnCurrency);
////                            map.put("Request[pp_TxnCurrency]", "Rs.");
//////        map.put("pp_TxnDateTime", pp_TxnDateTime);
////                            map.put("Request[pp_TxnDateTime]", "20200629130547");
//////        map.put("pp_BillReference", pp_BillReference);
////                            map.put("Request[pp_BillReference]", "billRef");
//////        map.put("pp_Description", pp_Description);
////                            map.put("Request[pp_Description]", "Description of transaction");
//////        map.put("pp_TxnExpiryDateTime", pp_TxnExpiryDateTime);
////                            map.put("Request[pp_TxnExpiryDateTime]", "20200630130536");
//////        map.put("pp_SecureHash", pp_SecureHash);
////                            map.put("Request[pp_SecureHash]", "F71FCBEB8E92B71A5509936A4EBB8DC246EE135E2B3083C949E5CE112370A631");
//////        map.put("ppmpf_1", ppmpf_1);
////                            map.put("Request[ppmpf_1]", "");
//////        map.put("ppmpf_2", ppmpf_2);
////                            map.put("Request[ppmpf_2]", "");
//////        map.put("ppmpf_3", ppmpf_3);
////                            map.put("Request[ppmpf_3]", "");
//////        map.put("ppmpf_4", ppmpf_4);
////                            map.put("Request[ppmpf_4]", "");
//////        map.put("ppmpf_5", ppmpf_5);
////                            map.put("Request[ppmpf_5]", "");
//////        map.put("pp_MobileNumber", pp_MobileNumber);
////                            map.put("Request[pp_MobileNumber]", "03123456789");
//////        map.put("pp_CNIC", pp_CNIC);
////                            map.put("Request[pp_CNIC]", "345678");
////
////                            Log.i("JSON ", String.valueOf(map));
////                            return map;
////                        }
////                    };
////                    sr.setRetryPolicy(new DefaultRetryPolicy(
////                            15000,
////                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////                    Volley.newRequestQueue(getContext()).add(sr);
////                } catch (JSONException ex) {
////                    ex.printStackTrace();
////                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loader.hideLoader();
//                new ProcessingError().showError(getContext());
//                new HaballError().printErrorMessage(getContext(), error);
//                error.printStackTrace();
//
//                btn_create.setEnabled(true);
//                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                params.put("Content-Length", "793");
////                try {
////                    params.put("Content-Length", String.valueOf(String.valueOf(map).getBytes("UTF-8").length));
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                }
//                params.put("Host", "sandbox.jazzcash.com.pk");
////                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                params.put("Cookie", "7D16C5AA7D431DCB8410E08F674D4DAC=5j5dbijewsiuchvkcxb5hixg; __RequestVerificationToken_L0N1c3RvbWVyUG9ydGFs0=hnGmBOxY-jZXXkJM6jzpdTYaIZrB9TmFKuN0408HWo2SSTtoBIuW66kBlmyEBezYBpSuOsXfQzhB0zGG_ET0xXseAuDTY9raQzA3XrRreaJcJxvETCpWzsF7MT3jgLMGpSnRYQONQmlDAxArRK9BnA2; DAD4D476F80E0148BCD134D7AA5C61D7=1j3e234cv0ji5dpe223uas5n");
//                return params;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<>();
//                map.put("IntegritySalt", "zy9y53331t");
//                JSONObject obj = new JSONObject();
//                try {
//                    obj.put("pp_Language", pp_Language);
////                obj.put("Request[pp_Language]", "EN");
//                    obj.put("pp_MerchantID", pp_MerchantID);
////                obj.put("Request[pp_MerchantID]", "MC37757");
//                    obj.put("pp_SubMerchantID", pp_SubMerchantID);
////                obj.put("Request[pp_SubMerchantID]", "");
//                    obj.put("pp_Password", pp_Password);
////                obj.put("Request[pp_Password]", "s0xcy12dv2");
//                    obj.put("pp_BankID", pp_BankID);
////                obj.put("Request[pp_BankID]", "");
//                    obj.put("pp_ProductID", pp_ProductID);
////                obj.put("Request[pp_ProductID]", "");
//                    obj.put("pp_TxnRefNo", "T" + pp_TxnRefNo);
////                try {
////                obj.put("Request[pp_TxnRefNo]", "T20200629140125");
////                } catch (JSONException e) {
////                    obj.put("Request[pp_TxnRefNo]", "");
////                    e.printStackTrace();
////                }
//                    obj.put("pp_Amount", pp_Amount);
////                obj.put("Request[pp_Amount]", "32653200");
//                    obj.put("pp_TxnCurrency", pp_TxnCurrency);
////                obj.put("Request[pp_TxnCurrency]", "Rs.");
//                    obj.put("pp_TxnDateTime", pp_TxnDateTime);
////                obj.put("Request[pp_TxnDateTime]", "20200629140134");
//                    obj.put("pp_BillReference", "billRef");
////                obj.put("Request[pp_BillReference]", "billRef");
//                    obj.put("pp_Description", pp_Description);
////                obj.put("Request[pp_Description]", "Description of transaction");
//                    obj.put("pp_TxnExpiryDateTime", pp_TxnExpiryDateTime);
////                obj.put("Request[pp_TxnExpiryDateTime]", "20200630140134");
//                    obj.put("pp_SecureHash", "");
////                obj.put("Request[pp_SecureHash]", "068AE9D269816DFF4729230750706019B598E56AE6F861B64DE5D2B70E1F1C78");
//                    obj.put("ppmpf_1", ppmpf_1);
////                obj.put("Request[ppmpf_1]", "");
//                    obj.put("ppmpf_2", ppmpf_2);
////                obj.put("Request[ppmpf_2]", "");
//                    obj.put("ppmpf_3", ppmpf_3);
////                obj.put("Request[ppmpf_3]", "");
//                    obj.put("ppmpf_4", ppmpf_4);
////                obj.put("Request[ppmpf_4]", "");
//                    obj.put("ppmpf_5", ppmpf_5);
////                obj.put("Request[ppmpf_5]", "");
//                    obj.put("pp_MobileNumber", String.valueOf(txt_account_no.getText()));
////                obj.put("Request[pp_MobileNumber]", "03123456789");
//                    obj.put("pp_CNIC", String.valueOf(txt_cnic.getText()));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                map.put("RequestBodyString", obj.toString());
//
//                Log.i("JSON ", String.valueOf(map));
//                return map;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//    }

//    private void payByJazzCashApi(String SecureHash) {
//
//        final Map<String, String> map = new HashMap<>();
//        map.put("Request%5Bpp_Language%5D", pp_Language);
////                map.put("Request[pp_Language]", "EN");
//        map.put("Request%5Bpp_MerchantID%5D", pp_MerchantID);
////                map.put("Request[pp_MerchantID]", "MC37757");
//        map.put("Request%5Bpp_SubMerchantID%5D", pp_SubMerchantID);
////                map.put("Request[pp_SubMerchantID]", "");
//        map.put("Request%5Bpp_Password%5D", pp_Password);
////                map.put("Request[pp_Password]", "s0xcy12dv2");
//        map.put("Request%5Bpp_BankID%5D", pp_BankID);
////                map.put("Request[pp_BankID]", "");
//        map.put("Request%5Bpp_ProductID%5D", pp_ProductID);
////                map.put("Request[pp_ProductID]", "");
//        map.put("Request%5Bpp_TxnRefNo%5D", pp_TxnRefNo);
////                try {
////                map.put("Request[pp_TxnRefNo]", "T20200629140125");
////                } catch (JSONException e) {
////                    map.put("Request[pp_TxnRefNo]", "");
////                    e.printStackTrace();
////                }
//        map.put("Request%5Bpp_Amount%5D", pp_Amount);
////                map.put("Request[pp_Amount]", "32653200");
//        map.put("Request%5Bpp_TxnCurrency%5D", pp_TxnCurrency);
////                map.put("Request[pp_TxnCurrency]", "Rs.");
//        map.put("Request%5Bpp_TxnDateTime%5D", pp_TxnDateTime);
////                map.put("Request[pp_TxnDateTime]", "20200629140134");
//        map.put("Request%5Bpp_BillReference%5D", "billRef");
////                map.put("Request[pp_BillReference]", "billRef");
//        map.put("Request%5Bpp_Description%5D", pp_Description);
////                map.put("Request[pp_Description]", "Description of transaction");
//        map.put("Request%5Bpp_TxnExpiryDateTime%5D", pp_TxnExpiryDateTime);
////                map.put("Request[pp_TxnExpiryDateTime]", "20200630140134");
//        map.put("Request%5Bpp_SecureHash%5D", SecureHash);
////                map.put("Request[pp_SecureHash]", "068AE9D269816DFF4729230750706019B598E56AE6F861B64DE5D2B70E1F1C78");
//        map.put("Request%5Bppmpf_1%5D", ppmpf_1);
////                map.put("Request[ppmpf_1]", "");
//        map.put("Request%5Bppmpf_2%5D", ppmpf_2);
////                map.put("Request[ppmpf_2]", "");
//        map.put("Request%5Bppmpf_3%5D", ppmpf_3);
////                map.put("Request[ppmpf_3]", "");
//        map.put("Request%5Bppmpf_4%5D", ppmpf_4);
////                map.put("Request[ppmpf_4]", "");
//        map.put("Request%5Bppmpf_5%5D", ppmpf_5);
////                map.put("Request[ppmpf_5]", "");
//        map.put("Request%5Bpp_MobileNumber%5D", String.valueOf(txt_account_no.getText()));
////                map.put("Request[pp_MobileNumber]", "03123456789");
//        map.put("Request%5Bpp_CNIC%5D", String.valueOf(txt_cnic.getText()));
//
//        new SSL_HandShake().handleSSLHandshake();
//
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_Jazz_Cash_Transaction, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                loader.hideLoader();
//                Log.i("JSON_RESPONSE", response);
//                JSONObject result = null;
//                try {
//                    result = new JSONObject(response);
//
////                showSuccessDialog(prepaid_number);
//
//                    try {
//                        new CustomToast().showToast(getActivity(), result.getString("message"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                } catch (JSONException e) {
//                    new CustomToast().showToast(getActivity(), "Jazz Cash - Log in to Jazz Cash Portal");
//                    e.printStackTrace();
//                }
//                Log.i("JSON_RESPONSE_JAZZ", String.valueOf(result));
////                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
////                Log.e("RESPONSE prepaid_number", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loader.hideLoader();
//                new ProcessingError().showError(getContext());
//                new HaballError().printErrorMessage(getContext(), error);
//                error.printStackTrace();
//
//                btn_create.setEnabled(true);
//                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Length", "839");
////                try {
////                    params.put("Content-Length", String.valueOf(String.valueOf(map).getBytes("UTF-8").length));
////                } catch (UnsupportedEncodingException e) {
////                    e.printStackTrace();
////                }
//                params.put("Host", "sandbox.jazzcash.com.pk");
////                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                params.put("Cookie", "7D16C5AA7D431DCB8410E08F674D4DAC=5j5dbijewsiuchvkcxb5hixg; __RequestVerificationToken_L0N1c3RvbWVyUG9ydGFs0=hnGmBOxY-jZXXkJM6jzpdTYaIZrB9TmFKuN0408HWo2SSTtoBIuW66kBlmyEBezYBpSuOsXfQzhB0zGG_ET0xXseAuDTY9raQzA3XrRreaJcJxvETCpWzsF7MT3jgLMGpSnRYQONQmlDAxArRK9BnA2; DAD4D476F80E0148BCD134D7AA5C61D7=1j3e234cv0ji5dpe223uas5n");
//                return params;
//            }
//
//            //            @Override
////            public String getBodyContentType() {
////                return "application/x-www-form-urlencoded; charset=UTF-8";
////            }
////
////            @Override
////            public byte[] getBody() throws AuthFailureError {
////                try {
////                    return map == null ? null : String.valueOf(map).getBytes("utf-8");
////                } catch (UnsupportedEncodingException uee) {
////                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", map, "utf-8");
////                    return null;
////                }
////            }
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Log.i("JSON ", String.valueOf(map));
//                return map;
//            }
//        };
//        sr.setRetryPolicy(new
//
//                DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(
//
//                getContext()).
//
//                add(sr);
//    }

    private void makeSaveRequest() throws JSONException {
        loader.showLoader();


                           final JSONObject map = new JSONObject();
        map.put("pp_Language", pp_Language);
//                            map.put("Request[pp_Language]", "EN");
        map.put("pp_MerchantID", pp_MerchantID);
//                            map.put("Request[pp_MerchantID]", "MC37757");
        map.put("pp_SubMerchantID", pp_SubMerchantID);
//                            map.put("Request[pp_SubMerchantID]", "");
        map.put("pp_Password", pp_Password);
//                            map.put("Request[pp_Password]", "s0xcy12dv2");
        map.put("pp_BankID", "");
//                            map.put("Request[pp_BankID]", "");
        map.put("pp_ProductID", "");
//                            map.put("Request[pp_ProductID]", "");
        map.put("pp_TxnRefNo", pp_TxnRefNo);
//                                map.put("Request[pp_TxnRefNo]", "");
        map.put("pp_Amount", pp_Amount );
//                            map.put("Request[pp_Amount]", "32653200");
        map.put("pp_TxnCurrency", pp_TxnCurrency);
//                            map.put("Request[pp_TxnCurrency]", "Rs.");
        map.put("pp_TxnDateTime", pp_TxnDateTime);
//                            map.put("Request[pp_TxnDateTime]", "20200629130547");
        map.put("pp_BillReference", pp_BillReference);
//                            map.put("Request[pp_BillReference]", "billRef");
        map.put("pp_Description", pp_Description);
//                            map.put("Request[pp_Description]", "Description of transaction");
        map.put("pp_TxnExpiryDateTime", pp_TxnExpiryDateTime);
//                            map.put("Request[pp_TxnExpiryDateTime]", "20200630130536");
        map.put("pp_SecureHash", pp_SecureHash);
//                            map.put("Request[pp_SecureHash]", "F71FCBEB8E92B71A5509936A4EBB8DC246EE135E2B3083C949E5CE112370A631");
        map.put("ppmpf_1", ppmpf_1);
//                            map.put("Request[ppmpf_1]", "");
        map.put("ppmpf_2", ppmpf_2);
//                            map.put("Request[ppmpf_2]", "");
        map.put("ppmpf_3", ppmpf_3);
//                            map.put("Request[ppmpf_3]", "");
        map.put("ppmpf_4", ppmpf_4);
//                            map.put("Request[ppmpf_4]", "");
        map.put("ppmpf_5", ppmpf_5);
//                            map.put("Request[ppmpf_5]", "");
        map.put("pp_MobileNumber", pp_MobileNumber);
//                            map.put("Request[pp_MobileNumber]", "03123456789");
        map.put("pp_CNIC", pp_CNIC);
//                            map.put("Request[pp_CNIC]", "345678");

//                map.put("Request[pp_CNIC]", "345678");
        // new SSL_HandShake().handleSSLHandshake();
//        generateSecureHash();

//        StringRequest sr = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("JazzCashResponse", response);
////                loader.hideLoader();
//                JSONObject resultRegen = null;
//                try {
//                    resultRegen = new JSONObject(response);
//                    if (resultRegen.getString("status").equals("success")) {
//                        new CustomToast().showToast(getActivity(), "Logged in successfully.");
//                    }
//                    StringRequest sr = new StringRequest(Request.Method.POST, URL_Calculate_Secure_Hash, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.i("JazzCashResponse123", response);
////                loader.hideLoader();
//                            JSONObject resultHash;
//                            try {
//                                resultHash = new JSONObject(response);
//                                if (resultHash.getString("status").equals("success")) {
//                                    new CustomToast().showToast(getActivity(), "Logged in successfully.");
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
////
////                    final JSONObject finalResultRegen = resultRegen;
////                    StringRequest sr = new StringRequest(Request.Method.POST, URL_Calculate_Secure_Hash, new Response.Listener<String>() {
////                        @Override
////                        public void onResponse(String response) {
////                            loader.hideLoader();
////                            Log.i("JSON_RESPONSE", response);
////                            JSONObject result = null;
////                            try {
////                                result = new JSONObject(response);
//                            new SSL_HandShake().handleSSLHandshake();
//
                            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_Jazz_Cash_Transaction, map, new Response.Listener<JSONObject>
                                    () {
                                @Override
                                public void onResponse(JSONObject result) {
                                    loader.hideLoader();
//                                    Log.i("JSON_RESPONSE", response);
//                                    JSONObject result = null;
//                                    try {
//                                        result = new JSONObject(response);

//                showSuccessDialog(prepaid_number);

                                        try {
                                            new CustomToast().showToast(getActivity(), result.getString("message"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

//                                    } catch (JSONException e) {
//                                        new CustomToast().showToast(getActivity(), "Jazz Cash - Log in to Jazz Cash Portal");
//                                        e.printStackTrace();
//                                    }
                                    Log.i("JSON_RESPONSE_JAZZ", String.valueOf(result));
//                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
//                Log.e("RESPONSE prepaid_number", result.toString());
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    loader.hideLoader();
                                    new ProcessingError().showError(getContext());
                                    new HaballError().printErrorMessage(getContext(), error);
                                    error.printStackTrace();

                                    btn_create.setEnabled(true);
                                    btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
                                }
                            }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("Content-Length", "536");
//                try {
//                    params.put("Content-Length", String.valueOf(String.valueOf(map).getBytes("UTF-8").length));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                                    params.put("Host", "sandbox.jazzcash.com.pk");
//                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                params.put("Content-Type", "application/json; charset=UTF-8");
//                                    params.put("Cookie", "7D16C5AA7D431DCB8410E08F674D4DAC=5j5dbijewsiuchvkcxb5hixg; __RequestVerificationToken_L0N1c3RvbWVyUG9ydGFs0=hnGmBOxY-jZXXkJM6jzpdTYaIZrB9TmFKuN0408HWo2SSTtoBIuW66kBlmyEBezYBpSuOsXfQzhB0zGG_ET0xXseAuDTY9raQzA3XrRreaJcJxvETCpWzsF7MT3jgLMGpSnRYQONQmlDAxArRK9BnA2; DAD4D476F80E0148BCD134D7AA5C61D7=1j3e234cv0ji5dpe223uas5n");
                                    return params;
                                }

                                //            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded; charset=UTF-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return map == null ? null : String.valueOf(map).getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", map, "utf-8");
//                    return null;
//                }
//            }
//                                @Override
//                                protected Map<String, String> getParams() throws AuthFailureError {
//                                    Log.i("JSON ", String.valueOf(map));
//                                    return map;
//                                }
                            };
                            sr.setRetryPolicy(new

                                    DefaultRetryPolicy(
                                    15000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            Volley.newRequestQueue(getContext()).add(sr);
////
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                            Log.i("JSON_RESPONSE_JAZZ", String.valueOf(result));
//////                Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
//////                Log.e("RESPONSE prepaid_number", result.toString());
////                        }
////                    }, new Response.ErrorListener() {
////                        @Override
////                        public void onErrorResponse(VolleyError error) {
////                            loader.hideLoader();
////                            new ProcessingError().showError(getContext());
////                            new HaballError().printErrorMessage(getContext(), error);
////                            error.printStackTrace();
////
////                            btn_create.setEnabled(true);
////                            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
////                        }
////                    }) {
////                        @Override
////                        public Map<String, String> getHeaders() throws AuthFailureError {
////                            Map<String, String> params = new HashMap<String, String>();
////                            params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
////                            return params;
////                        }
////
////                        @Override
////                        protected Map<String, String> getParams() throws AuthFailureError {
////
////                            Map<String, String> map = new HashMap<>();
//////        map.put("pp_Language", pp_Language);
////                            map.put("Request[pp_Language]", "EN");
//////        map.put("pp_MerchantID", pp_MerchantID);
////                            map.put("Request[pp_MerchantID]", "MC37757");
//////        map.put("pp_SubMerchantID", pp_SubMerchantID);
////                            map.put("Request[pp_SubMerchantID]", "");
//////        map.put("pp_Password", pp_Password);
////                            map.put("Request[pp_Password]", "s0xcy12dv2");
//////        map.put("pp_BankID", pp_BankID);
////                            map.put("Request[pp_BankID]", "");
//////        map.put("pp_ProductID", pp_ProductID);
////                            map.put("Request[pp_ProductID]", "");
//////        map.put("pp_TxnRefNo", pp_TxnRefNo);
////                            try {
////                                map.put("Request[pp_TxnRefNo]", finalResultRegen.getString("txnRefNumber"));
////                            } catch (JSONException e) {
////                                map.put("Request[pp_TxnRefNo]", "");
////                                e.printStackTrace();
////                            }
//////        map.put("pp_Amount", pp_Amount);
////                            map.put("Request[pp_Amount]", "32653200");
//////        map.put("pp_TxnCurrency", pp_TxnCurrency);
////                            map.put("Request[pp_TxnCurrency]", "Rs.");
//////        map.put("pp_TxnDateTime", pp_TxnDateTime);
////                            map.put("Request[pp_TxnDateTime]", "20200629130547");
//////        map.put("pp_BillReference", pp_BillReference);
////                            map.put("Request[pp_BillReference]", "billRef");
//////        map.put("pp_Description", pp_Description);
////                            map.put("Request[pp_Description]", "Description of transaction");
//////        map.put("pp_TxnExpiryDateTime", pp_TxnExpiryDateTime);
////                            map.put("Request[pp_TxnExpiryDateTime]", "20200630130536");
//////        map.put("pp_SecureHash", pp_SecureHash);
////                            map.put("Request[pp_SecureHash]", "F71FCBEB8E92B71A5509936A4EBB8DC246EE135E2B3083C949E5CE112370A631");
//////        map.put("ppmpf_1", ppmpf_1);
////                            map.put("Request[ppmpf_1]", "");
//////        map.put("ppmpf_2", ppmpf_2);
////                            map.put("Request[ppmpf_2]", "");
//////        map.put("ppmpf_3", ppmpf_3);
////                            map.put("Request[ppmpf_3]", "");
//////        map.put("ppmpf_4", ppmpf_4);
////                            map.put("Request[ppmpf_4]", "");
//////        map.put("ppmpf_5", ppmpf_5);
////                            map.put("Request[ppmpf_5]", "");
//////        map.put("pp_MobileNumber", pp_MobileNumber);
////                            map.put("Request[pp_MobileNumber]", "03123456789");
//////        map.put("pp_CNIC", pp_CNIC);
////                            map.put("Request[pp_CNIC]", "345678");
////
////                            Log.i("JSON ", String.valueOf(map));
////                            return map;
////                        }
////                    };
////                    sr.setRetryPolicy(new DefaultRetryPolicy(
////                            15000,
////                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////                    Volley.newRequestQueue(getContext()).add(sr);
////                } catch (JSONException ex) {
////                    ex.printStackTrace();
////                }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            loader.hideLoader();
//                            new ProcessingError().showError(getContext());
//                            new HaballError().printErrorMessage(getContext(), error);
//                            error.printStackTrace();
//
//                            btn_create.setEnabled(true);
//                            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//                        }
//                    }) {
//                        @Override
//                        public Map<String, String> getHeaders() throws AuthFailureError {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                            return params;
//                        }
//
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//
//                            Map<String, String> map = new HashMap<>();
//                            map.put("IntegritySalt", "zy9y53331t");
//                            JSONObject obj = new JSONObject();
//                            try {
//                                obj.put("pp_Language", pp_Language);
////                obj.put("Request[pp_Language]", "EN");
//                                obj.put("pp_MerchantID", pp_MerchantID);
////                obj.put("Request[pp_MerchantID]", "MC37757");
//                                obj.put("pp_SubMerchantID", pp_SubMerchantID);
////                obj.put("Request[pp_SubMerchantID]", "");
//                                obj.put("pp_Password", pp_Password);
////                obj.put("Request[pp_Password]", "s0xcy12dv2");
//                                obj.put("pp_BankID", pp_BankID);
////                obj.put("Request[pp_BankID]", "");
//                                obj.put("pp_ProductID", pp_ProductID);
////                obj.put("Request[pp_ProductID]", "");
//                                obj.put("pp_TxnRefNo", "T" + pp_TxnRefNo);
////                try {
////                obj.put("Request[pp_TxnRefNo]", "T20200629140125");
////                } catch (JSONException e) {
////                    obj.put("Request[pp_TxnRefNo]", "");
////                    e.printStackTrace();
////                }
//                                obj.put("pp_Amount", pp_Amount);
////                obj.put("Request[pp_Amount]", "32653200");
//                                obj.put("pp_TxnCurrency", pp_TxnCurrency);
////                obj.put("Request[pp_TxnCurrency]", "Rs.");
//                                obj.put("pp_TxnDateTime", pp_TxnDateTime);
////                obj.put("Request[pp_TxnDateTime]", "20200629140134");
//                                obj.put("pp_BillReference", "billRef");
////                obj.put("Request[pp_BillReference]", "billRef");
//                                obj.put("pp_Description", pp_Description);
////                obj.put("Request[pp_Description]", "Description of transaction");
//                                obj.put("pp_TxnExpiryDateTime", pp_TxnExpiryDateTime);
////                obj.put("Request[pp_TxnExpiryDateTime]", "20200630140134");
//                                obj.put("pp_SecureHash", "");
////                obj.put("Request[pp_SecureHash]", "068AE9D269816DFF4729230750706019B598E56AE6F861B64DE5D2B70E1F1C78");
//                                obj.put("ppmpf_1", ppmpf_1);
////                obj.put("Request[ppmpf_1]", "");
//                                obj.put("ppmpf_2", ppmpf_2);
////                obj.put("Request[ppmpf_2]", "");
//                                obj.put("ppmpf_3", ppmpf_3);
////                obj.put("Request[ppmpf_3]", "");
//                                obj.put("ppmpf_4", ppmpf_4);
////                obj.put("Request[ppmpf_4]", "");
//                                obj.put("ppmpf_5", ppmpf_5);
////                obj.put("Request[ppmpf_5]", "");
//                                obj.put("pp_MobileNumber", String.valueOf(txt_account_no.getText()));
////                obj.put("Request[pp_MobileNumber]", "03123456789");
//                                obj.put("pp_CNIC", String.valueOf(txt_cnic.getText()));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            map.put("RequestBodyString", obj.toString());
//
//                            Log.i("JSON ", String.valueOf(map));
//                            return map;
//                        }
//                    };
//                    sr.setRetryPolicy(new DefaultRetryPolicy(
//                            15000,
//                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                    Volley.newRequestQueue(getContext()).add(sr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loader.hideLoader();
//                new ProcessingError().showError(getContext());
//                new HaballError().printErrorMessage(getContext(), error);
//                error.printStackTrace();
//
//                btn_create.setEnabled(true);
//                btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//                return params;
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<>();
//                map.put("username", "m.farrukhiftikhar@gmail.com");
//                map.put("password", "a1s2d3f4");
//
//                Log.i("JSON ", String.valueOf(map));
//                return map;
//            }
//        };
//        sr.setRetryPolicy(new
//
//                DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(
//
//                getContext()).
//
//                add(sr);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void generateSecureHash() throws UnsupportedEncodingException {
        List<String> arrdata = new ArrayList<>();
        arrdata.add(pp_Language);
        arrdata.add(pp_MerchantID);
        arrdata.add(pp_SubMerchantID);
        arrdata.add(pp_Password);
        arrdata.add("");
        arrdata.add(pp_ProductID);
        arrdata.add(pp_TxnRefNo);
        arrdata.add(pp_Amount);
        arrdata.add(pp_TxnCurrency);
        arrdata.add(pp_TxnDateTime);
        arrdata.add(pp_BillReference);
        arrdata.add(pp_Description);
        arrdata.add(pp_TxnExpiryDateTime);
        arrdata.add("");
        arrdata.add(ppmpf_1);
        arrdata.add(ppmpf_2);
        arrdata.add(ppmpf_3);
        arrdata.add(ppmpf_4);
        arrdata.add(ppmpf_5);
        arrdata.add(pp_MobileNumber);
        arrdata.add(pp_CNIC);

        String SortedArray  = aasecretkey;
        for (String var : arrdata) {
            if (var != null && !var.equals("")) {
                SortedArray = SortedArray.concat('&' + var);
            }
        }
        Log.i("sortedarray", SortedArray);
        Log.i("sortedarray", URLEncoder.encode(SortedArray, StandardCharsets.UTF_8.displayName()));
        String Securehash = hmacSha(aasecretkey, URLEncoder.encode(SortedArray, StandardCharsets.UTF_8.displayName()),"HmacSHA256");
        pp_SecureHash = Securehash;
        Log.i("sortedarray123", Securehash);
    }
//
//     private static String encodeValue(String value) {
//        try {
//            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
//        } catch (UnsupportedEncodingException ex) {
//            throw new RuntimeException(ex.getCause());
//        }
//    }

    private void showSuccessDialog(String paymentID) {

        final Dialog fbDialogue = new Dialog(getActivity());
        //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        fbDialogue.setContentView(R.layout.password_updatepopup);
        TextView tv_pr1, txt_header1;
        txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
        tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
        tv_pr1.setText("");
        txt_header1.setText("Payment Created");
        String steps1 = "Payment ID ";
        String steps2 = " has been created successfully.";
        String title = paymentID;
        SpannableString ss1 = new SpannableString(title);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);

        tv_pr1.append(steps1);
        tv_pr1.append(ss1);
        tv_pr1.append(steps2);
        fbDialogue.setCancelable(true);
        fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        fbDialogue.getWindow().setAttributes(layoutParams);
        fbDialogue.show();

        ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbDialogue.dismiss();
            }
        });

        fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();
            }
        });
    }
//
//    public static String encode(String key, String data) throws Exception {
//        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
//        sha256_HMAC.init(secret_key);
//
//        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
//    }

    private String hmacSha(String KEY, String VALUE, String SHA_TYPE) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(KEY.getBytes("UTF-8"), SHA_TYPE);
            Mac mac = Mac.getInstance(SHA_TYPE);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(VALUE.getBytes("UTF-8"));
            byte[] hexArray = {(byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'};
            byte[] hexChars = new byte[rawHmac.length * 2];
            for (int j = 0; j < rawHmac.length; j++) {
                int v = rawHmac[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

