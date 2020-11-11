package com.haball.Retailor.ui.Make_Payment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.CustomToast;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
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

import static android.os.Build.ID;

public class PaymentScreen3Fragment_Retailer extends Fragment {
    private String Token, DistributorId, ID;
    private TextView tv_banking_channel, payment_id, btn_newpayment;
    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "http://175.107.203.97:4014/api/prepaidrequests/GetByRetailerCode";
    private String PrePaidNumber = "", PrePaidId = "", CompanyName = "", Amount = "", CompanyId = "", MenuItem = "";
    private Button btn_voucher, btn_update, btn_back;
    private Spinner spinner_companyName;
    private TextInputEditText txt_amount;
    private TextInputLayout layout_txt_amount;
    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private List<String> CompanyNames = new ArrayList<>();
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String company_names;
    private Typeface myFont;
    private String URL_PAYMENT_REQUESTS_SAVE = "http://175.107.203.97:4014/api/prepaidrequests/save";
    private String prepaid_number;
    private String prepaid_id;
    private FragmentTransaction fragmentTransaction;
    private Loader loader;
    private RelativeLayout rl_jazz_cash;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (checkAndRequestPermissions()) {

        }

        View root = inflater.inflate(R.layout.activity_payment__screen3, container, false);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);

        payment_id = root.findViewById(R.id.payment_id);
        spinner_companyName = root.findViewById(R.id.spinner_companyName);
        txt_amount = root.findViewById(R.id.txt_amount);
        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        btn_newpayment = root.findViewById(R.id.btn_addpayment);
        btn_update = root.findViewById(R.id.btn_update);
        btn_voucher = root.findViewById(R.id.btn_voucher);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("PrePaidNumber",
                Context.MODE_PRIVATE);
        PrePaidNumber = sharedPreferences.getString("PrePaidNumber", "");
        PrePaidId = sharedPreferences.getString("PrePaidId", "");
        CompanyName = sharedPreferences.getString("CompanyName", "");
        CompanyId = sharedPreferences.getString("CompanyId", "");
        Amount = sharedPreferences.getString("Amount", "");
        MenuItem = sharedPreferences.getString("MenuItem", "");

        rl_jazz_cash = root.findViewById(R.id.rl_jazz_cash);
        rl_jazz_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscardDialogForJazzCash();
            }
        });

        new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
        loader = new Loader(getContext());


        //   spinner_companyName.setText(CompanyName);
        // CompanyNames.add(CompanyName);
        fetchCompanyData();
        CompanyNames.add("Select Company");
        company_names = "";

        if (MenuItem.equals("View")) {
            btn_update.setText("Back");
            txt_amount.setEnabled(false);
            spinner_companyName.setEnabled(false);
            spinner_companyName.setClickable(false);
        }

        payment_id.setText(PrePaidNumber);

        payment_id.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(payment_id.getText());
//                new CustomToast().showToast(getActivity(), "Payment ID: " + String.valueOf(payment_id.getText()) + " - Copied to clipboard");
                new CustomToast().showToast(getActivity(), "PSID has been copied to clipboard");
                return false;
            }
        });

//        PrePaidNumber = "358534338693873";
//        PrePaidId = "873";
//        CompanyName = "One call";
//        CompanyId = "20203847";
//        Amount = "500";
//        Log.i("payment3_PrePaidId", PrePaidId);
//        Log.i("payment3_CompanyName", CompanyName);
//        Log.i("payment3_CompanyId", CompanyId);
//        Log.i("payment3_Amount", Amount);
//
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
                text.setPadding(50, 0, 50, 0);
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
                text.setPadding(50, 0, 50, 0);
                return view;
            }
        };

        spinner_companyName.setAdapter(arrayAdapterPayments);
        spinner_companyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                    ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                company_names = CompanyNames.get(i);
                checkFieldsForEmptyValues();
//                Log.i("company name and id ", companyNameAndId.get(company_names));
//                if (company_names.equals("Select Company") || company_names.equals(CompanyName))
//                    btn_update.setText("Back");
//                else
//                    btn_update.setText("Update");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        txt_amount.setText(Amount);
        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
        txt_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txt_amount.hasFocus()) {
//                if (!String.valueOf(txt_amount.getText()).equals("") && !String.valueOf(txt_amount.getText()).equals(Amount))
//                    btn_update.setText("Update");
//                else
//                    btn_update.setText("Back");
                    Log.i("PaymentAmountDebug", String.valueOf(txt_amount.getText()));
                    checkFieldsForEmptyValues();
                }
            }
        });


        btn_newpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_update.getText().equals("Back")) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container_ret, new CreatePaymentRequestFragment());
                    fragmentTransaction.commit();
                } else {
                    showDiscardDialogForCreatePayment();
                }
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_update.getText().equals("Back")) {
//                    final FragmentManager fm = getActivity().getSupportFragmentManager();
//                    fm.popBackStack();
                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();

                } else if (btn_update.getText().equals("Update")) {
                    try {
                        makeUpdateRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container_ret, new EditPaymentRequestFragment());
//                fragmentTransaction.commit();

            }
        });

        btn_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndRequestPermissions()) {
                    try {
                        viewPDF(getContext(), PrePaidId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        tv_banking_channel = root.findViewById(R.id.tv_banking_channel);
        tv_banking_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog alertDialog2 = new AlertDialog.Builder(getContext()).create();
                LayoutInflater inflater2 = LayoutInflater.from(getContext());
                View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                alertDialog2.setView(view_popup2);
                alertDialog2.show();
                ImageButton img_close = view_popup2.findViewById(R.id.image_button_close);
                TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
                payment_information_txt3.setText(PrePaidNumber);
                Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
                btn_view_voucher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkAndRequestPermissions()) {
                            try {
                                viewPDF(getContext(), PrePaidId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                    }
                });
            }
        });

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
                    if (btn_update.getText().equals("Update")) {
                        showDiscardDialog();
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
                    String txt_amounts = txt_amount.getText().toString();
                    String company = (String) spinner_companyName.getItemAtPosition(spinner_companyName.getSelectedItemPosition()).toString();
//                    if ((!txt_amounts.equals("")
//                            && !txt_amounts.equals(Amount))
//                            || (!company.equals("Select Company") && !company.equals(CompanyName))
//
//                    ) {
                    if (btn_update.getText().equals("Update")) {
                        showDiscardDialog();
                        return true;
                    } else {
//                        return false;
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

    private void showDiscardDialogForCreatePayment() {
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
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container_ret, new CreatePaymentRequestFragment());
                fragmentTransaction.commit();
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

    private void showDiscardDialog() {
        final FragmentManager fm = getActivity().getSupportFragmentManager();
        if (!MenuItem.equals("View")) {
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
//                    fm.popBackStack();
                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();

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
        } else {
            SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
            editorOrderTabsFromDraft.putString("TabNo", "0");
            editorOrderTabsFromDraft.apply();

            Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
            ((FragmentActivity) getContext()).startActivity(login_intent);
            ((FragmentActivity) getContext()).finish();

//            fm.popBackStack();

        }
    }

    private void showDiscardDialogForJazzCash() {
        if (!String.valueOf(btn_update.getText()).equals("Back")) {
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
                    SharedPreferences JazzCash = ((FragmentActivity) getContext()).getSharedPreferences("PaymentId",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_JazzCash = JazzCash.edit();
                    editor_JazzCash.putString("PrePaidNumber", PrePaidNumber);
                    editor_JazzCash.putString("PrePaidId", PrePaidId);
                    editor_JazzCash.putString("CompanyName", CompanyName);
                    editor_JazzCash.putString("Amount", Amount);
                    editor_JazzCash.putString("Type", "PrePayment");
                    editor_JazzCash.apply();
                    fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container_ret, new PaymentJazzCashApi()).addToBackStack("null");
                    fragmentTransaction.commit();
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
        } else {
            SharedPreferences JazzCash = ((FragmentActivity) getContext()).getSharedPreferences("PaymentId",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_JazzCash = JazzCash.edit();
            editor_JazzCash.putString("PrePaidNumber", PrePaidNumber);
            editor_JazzCash.putString("PrePaidId", PrePaidId);
            editor_JazzCash.putString("CompanyName", CompanyName);
            editor_JazzCash.putString("Amount", Amount);
            editor_JazzCash.putString("Type", "PrePayment");
            editor_JazzCash.apply();
            fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_container_ret, new PaymentJazzCashApi()).addToBackStack("null");
            fragmentTransaction.commit();
        }
    }

    private void checkFieldsForEmptyValues() {
        String txt_amounts = txt_amount.getText().toString();
        String company = (String) spinner_companyName.getItemAtPosition(spinner_companyName.getSelectedItemPosition()).toString();
        Log.i("DebugEditPayment", txt_amounts);
        Log.i("DebugEditPayment", Amount);
        Log.i("DebugEditPayment", company);
        Log.i("DebugEditPayment", CompanyName);
        if ((!txt_amounts.equals(Amount)) || (!company.equals(CompanyName))

        ) {
            btn_update.setText("Update");
//        } else if ((txt_amounts.equals(Amount)) && (company.equals(CompanyName))) {
//            if(!String.valueOf(btn_update.getText()).equals("Back")) {
//                btn_update.setEnabled(false);
//                btn_update.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
//            }
        }


        if ((!txt_amounts.equals("")) && (!company.equals("Select Company"))) {
//            btn_update.setText("Update");
            btn_update.setEnabled(true);
            btn_update.setBackground(getResources().getDrawable(R.drawable.button_background));

        } else {
//            btn_update.setText("Back");
            if (!String.valueOf(btn_update.getText()).equals("Back")) {
                btn_update.setEnabled(false);
                btn_update.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
            }
        }
    }

    private void viewPDF(Context context, String ID) throws JSONException {
        ViewVoucherRequest viewPDFRequest = new ViewVoucherRequest();
        viewPDFRequest.viewPDF(context, ID);
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

    private void makeUpdateRequest() throws JSONException {
        String txt_amounts = String.valueOf(txt_amount.getText());
        if (!txt_amounts.equals("")) {
            if (Double.parseDouble(txt_amounts) >= 500) {
                loader.showLoader();
                btn_update.setEnabled(false);
                btn_update.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                        Context.MODE_PRIVATE);
                Token = sharedPreferences.getString("Login_Token", "");

                JSONObject map = new JSONObject();
                map.put("ID", PrePaidId);
                map.put("DealerCode", companyNameAndId.get(company_names));
//        map.put("DealerCode", "201911672");
                map.put("PaidAmount", txt_amount.getText().toString());

                Log.i("JSON ", String.valueOf(map));
                new SSL_HandShake().handleSSLHandshake();
//                final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

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

                        btn_update.setEnabled(true);
                        btn_update.setBackground(getResources().getDrawable(R.drawable.button_background));

                        SharedPreferences PrePaidNumber = getContext().getSharedPreferences("PrePaidNumber",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = PrePaidNumber.edit();
                        editor.putString("PrePaidNumber", prepaid_number);
                        editor.putString("PrePaidId", prepaid_id);
                        editor.putString("CompanyId", companyNameAndId.get(company_names));
                        editor.putString("CompanyName", company_names);
                        editor.putString("Amount", txt_amount.getText().toString());
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

                        btn_update.setEnabled(true);
                        btn_update.setBackground(getResources().getDrawable(R.drawable.button_background));
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
        } else {
            new CustomToast().showToast(getActivity(), "Amount cannot be less than Rs. 500.");
        }
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

                    arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterPayments.notifyDataSetChanged();
                    spinner_companyName.setAdapter(arrayAdapterPayments);

                    // txt_amount.setText(Amount);
                    Log.i("Debugging", String.valueOf(CompanyNames));
                    Log.i("Debugging", String.valueOf(CompanyNames.indexOf(CompanyName)));
                    Log.i("Debugging", String.valueOf(CompanyName));
//        int spinnerPosition = arrayAdapterPayments.getPosition(CompanyName);
                    spinner_companyName.setSelection(CompanyNames.indexOf(CompanyName));
                    company_names = CompanyName;

                    btn_update.setText("Back");
                    if (MenuItem.equals("View")) {
                        txt_amount.setEnabled(false);
                        spinner_companyName.setEnabled(false);
                        spinner_companyName.setClickable(false);
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
    }

    private void showSuccessDialog(String paymentID) {

        final Dialog fbDialogue = new Dialog(getActivity());
        //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        fbDialogue.setContentView(R.layout.password_updatepopup);
        TextView tv_pr1, txt_header1;
        txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
        tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
        tv_pr1.setText("");
        txt_header1.setText("Payment Updated");
        String steps1 = "Payment ID ";
        String steps2 = " has been updated successfully.";
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

