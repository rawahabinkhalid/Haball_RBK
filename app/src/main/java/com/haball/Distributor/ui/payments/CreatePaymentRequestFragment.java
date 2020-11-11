package com.haball.Distributor.ui.payments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.CustomToast;
import com.haball.Distributor.DistributorDashboard;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
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

public class CreatePaymentRequestFragment extends Fragment {
    private String Token, DistributorId;
    private Button btn_create;

    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "https://175.107.203.97:4013/api/company/ReadActiveCompanyContract/";
    private String URL_PAYMENT_REQUESTS_SAVE = "https://175.107.203.97:4013/api/prepaidrequests/save";

    private List<String> CompanyNames = new ArrayList<>();
    private HashMap<String, String> companyNameAndId = new HashMap<>();

    private Spinner spinner_company;
    private ArrayAdapter<String> arrayAdapterPayments;
    private String company_names;
    private TextInputEditText txt_amount;
    private String prepaid_number, prepaid_id;
    private FragmentTransaction fragmentTransaction;
    private TextInputLayout layout_txt_amount;
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
                    Log.i("Company_Nameeee ", company_names);

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

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
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

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
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

                Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
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
        if (Double.parseDouble(txt_amounts) >= 500) {
            loader.showLoader();
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");

            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            DistributorId = sharedPreferences1.getString("Distributor_Id", "");
            Log.i("DistributorId ", DistributorId);
            Log.i("Token", Token);

            JSONObject map = new JSONObject();
            map.put("Status", 0);
            map.put("ID", 0);
            map.put("DistributorId", Integer.parseInt(DistributorId));
            map.put("CompanyId", companyNameAndId.get(company_names));
            map.put("PaidAmount", txt_amount.getText().toString());

            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_PAYMENT_REQUESTS_SAVE, map, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    loader.hideLoader();
                    try {
                        prepaid_number = result.getString("PrePaidNumber");
                        prepaid_id = result.getString("ID");
                    } catch (JSONException e) {
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

                    //                    Toast.makeText(getContext(), "Payment Request " + prepaid_number + " has been created successfully.", Toast.LENGTH_SHORT).show();
//                    Log.e("RESPONSE prepaid_number", result.toString());
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
        } else {
            new CustomToast().showToast(getActivity(), "Amount cannot be less than Rs. 500.");
        }
    }

    private void fetchCompanyData() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        URL_PAYMENT_REQUESTS_SELECT_COMPANY = URL_PAYMENT_REQUESTS_SELECT_COMPANY + DistributorId;
        Log.i("URL_PROOF_OF_PAYMENTS ", URL_PAYMENT_REQUESTS_SELECT_COMPANY);

        Log.i("Token", Token);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_SELECT_COMPANY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                Log.i("aaaaaabb", String.valueOf(result));
                try {
                    JSONObject jsonObject = null;
                    CompanyNames = new ArrayList<>();
                    CompanyNames.add("Select Company");
                    companyNameAndId = new HashMap<>();
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        CompanyNames.add(jsonObject.getString("Name"));
                        companyNameAndId.put(jsonObject.getString("Name"), jsonObject.getString("ID"));

                    }
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
                    spinner_company.setAdapter(arrayAdapterPayments);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF COMPANY ID", result.toString());
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
                fragmentTransaction.add(R.id.main_container, new PaymentScreen3Fragment());
                fragmentTransaction.commit();
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
}