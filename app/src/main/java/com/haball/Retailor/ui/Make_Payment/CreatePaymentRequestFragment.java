package com.haball.Retailor.ui.Make_Payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.CustomToast;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.Forgot_Password_Retailer.Forgot_Pass_Retailer;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Dashboard.Dashboard_Tabs;
import com.haball.Retailor.ui.Support.SupportFragment;
import com.haball.SSL_HandShake;
import com.haball.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CreatePaymentRequestFragment extends Fragment {
    private String Token, DistributorId, ID;
    private Button btn_create;

    //    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "http://175.107.203.97:4014/api/kyc/KYCDistributorList";
    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "http://175.107.203.97:4014/api/prepaidrequests/GetByRetailerCode";
    private String URL_PAYMENT_REQUESTS_SAVE = "http://175.107.203.97:4014/api/prepaidrequests/save";

    private List<String> CompanyNames = new ArrayList<>();
    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private FragmentTransaction fragmentTransaction;
    private String prepaid_id;

    private Spinner spinner_company;
    private ArrayAdapter<String> arrayAdapterPayments;
    private String company_names;
    private TextInputEditText txt_amount;
    private TextInputLayout layout_txt_amount;
    private String prepaid_number;
    private Typeface myFont;
    private Loader loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_payment__screen1, container, false);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);

        btn_create = root.findViewById(R.id.btn_create);
        btn_create.setEnabled(false);
        btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        spinner_company = root.findViewById(R.id.spinner_company);
        txt_amount = root.findViewById(R.id.txt_amount);
        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        CompanyNames.add("Select Company");
        company_names = "";
        loader = new Loader(getContext());

        new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);


//        arrayAdapterPayments = new ArrayAdapter<>(root.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, CompanyNames);

        arrayAdapterPayments = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, CompanyNames) {
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


        spinner_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    company_names = "";
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    company_names = CompanyNames.get(i);
                    Log.i("company name and id ", companyNameAndId.get(company_names));
                }
                checkFieldsForEmptyValues();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final View finalroot = root;
        fetchCompanyData();
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!company_names.equals("") && !String.valueOf(txt_amount.getText()).equals("")) {
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
        txt_amount.addTextChangedListener(textWatcher);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
//        final String txt_amounts = txt_amount.getText().toString();
//        final String company = String.valueOf(spinner_company.getItemAtPosition(spinner_company.getSelectedItemPosition()));
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        txt_amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    final String txt_amounts = txt_amount.getText().toString();
                    final String company = String.valueOf(spinner_company.getItemAtPosition(spinner_company.getSelectedItemPosition()));
                    Log.i("onResume_txt_amount", String.valueOf(txt_amounts));
                    Log.i("onResume_company_name", String.valueOf(company));

                    txt_amount.clearFocus();
                    if (!txt_amounts.equals("") || (!company.equals("Select Company") && company != null)) {
                        showDiscardDialog();

                        return true;
                    } else {
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
                        fragmentTransaction.commit();
                        return true;

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
                    final String txt_amounts = txt_amount.getText().toString();
                    final String company = String.valueOf(spinner_company.getItemAtPosition(spinner_company.getSelectedItemPosition()));
                    Log.i("onResume_txt_amount", String.valueOf(txt_amounts));
                    Log.i("onResume_company_name", String.valueOf(company));

//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    if (!txt_amounts.equals("") || (!company.equals("Select Company") && company != null)) {
                        showDiscardDialog();
                        return true;
                    } else {
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new HomeFragment());
                        fragmentTransaction.commit();
                        return true;
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

    private void checkFieldsForEmptyValues() {
        String txt_amounts = txt_amount.getText().toString();
        String company = (String) spinner_company.getItemAtPosition(spinner_company.getSelectedItemPosition()).toString();
        if (txt_amounts.equals("")
//                || Double.parseDouble(txt_amounts) < 500
                || company.equals("Select Company")

        ) {
            btn_create.setEnabled(false);
            btn_create.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_create.setEnabled(true);
            btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private void makeSaveRequest() throws JSONException {
        String txt_amounts = txt_amount.getText().toString();
        if(Double.parseDouble(txt_amounts) >= 500) {
            loader.showLoader();
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");

            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            ID = sharedPreferences1.getString("ID", "");
            Log.i("ID  ", ID);
            Log.i("Token", Token);

            JSONObject map = new JSONObject();
            map.put("ID", 0);
            map.put("DealerCode", companyNameAndId.get(company_names));
//        map.put("DealerCode", "201911672");
            map.put("PaidAmount", txt_amount.getText().toString());

            Log.i("JSON ", String.valueOf(map));
            new SSL_HandShake().handleSSLHandshake();
//            final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_PAYMENT_REQUESTS_SAVE, map, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    loader.hideLoader();
                    try {
                        Log.i("Response PR", result.toString());
                        prepaid_number = result.getString("PrePaidNumber");
                        prepaid_id = result.getString("ID");
                    } catch (JSONException e) {
                        Log.i("Response PR", e.toString());
                        e.printStackTrace();
                    }

                    btn_create.setEnabled(true);
                    btn_create.setBackground(getResources().getDrawable(R.drawable.button_background));

                    SharedPreferences PrePaidNumber = getContext().getSharedPreferences("PrePaidNumber",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = PrePaidNumber.edit();
                    editor.putString("PrePaidNumber", prepaid_number);
                    editor.putString("PrePaidId", prepaid_id);
                    editor.putString("CompanyId", companyNameAndId.get(company_names));
                    editor.putString("CompanyName", company_names);
                    editor.putString("Amount", txt_amount.getText().toString());
                    editor.putString("MenuItem", "Edit");
                    editor.apply();

                    showSuccessDialog(prepaid_number);

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
                    params.put("Authorization", "bearer " + Token);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(getContext()).add(sr);
        } else {
            new CustomToast().showToast(getActivity(), "Amount cannot be less than Rs. 500.");
        }
    }

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
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container_ret, new PaymentScreen3Fragment_Retailer());
                fragmentTransaction.commit();
            }
        });
    }

    private void fetchCompanyData() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        Log.i("Token", Token);
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_SELECT_COMPANY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
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
                Log.e("RESPONSE OF COMPANY ID", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
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
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPayments.notifyDataSetChanged();
        spinner_company.setAdapter(arrayAdapterPayments);
    }


//     private void printErrorMessage(VolleyError error) {
//         if (getContext() != null) {
//             if (error instanceof NetworkError) {
//                 Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof ServerError) {
//                 Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof AuthFailureError) {
//                 Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof ParseError) {
//                 Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof NoConnectionError) {
//                 Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof TimeoutError) {
//                 Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
//             }

//             if (error.networkResponse != null && error.networkResponse.data != null) {
//                 try {
//                     String message = "";
//                     String responseBody = new String(error.networkResponse.data, "utf-8");
//                     JSONObject data = new JSONObject(responseBody);
//                     Iterator<String> keys = data.keys();
//                     while (keys.hasNext()) {
//                         String key = keys.next();
// //                if (data.get(key) instanceof JSONObject) {
//                         message = message + data.get(key) + "\n";
// //                }
//                     }
//                     if (message.equals(""))
//                         message = responseBody;
// //                    if(data.has("message"))
// //                        message = data.getString("message");
// //                    else if(data. has("Error"))
//                     Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
//                 } catch (UnsupportedEncodingException e) {
//                     e.printStackTrace();
//                 } catch (JSONException e) {
//                     e.printStackTrace();
//                 }
//             }
// //        NetworkResponse response = error.networkResponse;
// //        if (error instanceof ServerError && response != null) {
// //            try {
// //                String message = "";
// //
// //                String res = new String(response.data,
// //                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
// //                // Now you can use any deserializer to make sense of data
// //                JSONObject obj = new JSONObject(res);
// //                Log.i("obj", String.valueOf(obj));
// //                Iterator<String> keys = obj.keys();
// //                int i = 0;
// //                while(keys.hasNext()) {
// //                    String key = keys.next();
// ////                    if (obj.get(key) instanceof JSONObject) {
// //                        message = message + obj.get(key) + "\n";
// ////                    }
// //                    i++;
// //                }
// //                Log.i("message", message);
// //                Toast.makeText(getContext(), String.valueOf(message), Toast.LENGTH_LONG).show();
// //            } catch (UnsupportedEncodingException e1) {
// //                // Couldn't properly decode data to string
// //                e1.printStackTrace();
// //            } catch (JSONException e2) {
// //                // returned data is not JSONObject?
// //                e2.printStackTrace();
// //            }
// //        }
//         }
//     }
}
