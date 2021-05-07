package com.haball.Distributor.ui.retailer.RetailerOrder.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import androidx.fragment.app.FragmentTransaction;
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
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.CustomToast;
import com.haball.Distributor.StatusKVP;
import com.haball.Distributor.ui.payments.CreatePaymentRequestFragment;
import com.haball.Distributor.ui.payments.ViewPDFRequest;
import com.haball.Distributor.ui.payments.ViewVoucherRequest;
import com.haball.Distributor.ui.retailer.Payment.RetailerPaymentDashboard;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrderDashboard;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter.RetailerViewOrderProductAdapter;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerViewOrderProductModel;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Retailor.ui.Make_Payment.ViewInvoiceReceipt;
import com.haball.Retailor.ui.Make_Payment.ViewInvoiceVoucher;
import com.haball.SSL_HandShake;
import com.haball.TextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlaceholderFragment extends Fragment {

    //    private static final String ARG_SECTION_NUMBER = "section_number";
//    private String orderID;
    private String URL_Order_Data = "https://175.107.203.97:4013/api/retailerorder/";
    //    private PageViewModel pageViewModel;
//    private TextInputEditText txt_orderID, txt_company_order, txt_created_date_order, txt_status_order, txt_comments;
//    private TextInputEditText txt_companyName, txt_paymentID, txt_created_date, txt_confirm, txt_bank, txt_authorization_id, txt_settlement_id, txt_status, txt_amount, txt_transaction_charges, txt_total_amount;
//    private RecyclerView rv_fragment_retailer_order_details;
//    private TextView tv_shipment_no_data;
//    private RecyclerView.Adapter rv_productAdapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private List<RetailerViewOrderProductModel> invo_productList = new ArrayList<>();
    private String Token;
    private String DistributorId;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String orderID, InvoiceStatus, invoiceID;
    private PageViewModel pageViewModel;
    private TextInputLayout layout_txt_orderID, layout_txt_order_company, layout_txt_created_date_order, layout_txt_status_order, layout_txt_comments,
            layout_txt_companName, layout_txt_paymentID, layout_txt_created_date, layout_transaction_date,
            layout_txt_bank, layout_txt_authorization_id, layout_txt_settlement_id, layout_txt_status, layout_txt_order_reference, layout_txt_invoice_reference,
            layout_txt_amount, layout_txt_transaction_charges, layout_txt_total_amount;
    private TextInputEditText txt_orderID, txt_company_order, txt_created_date_order, txt_status_order, txt_comments, txt_confirm, txt_order_reference, txt_invoice_reference;
    private TextView discount, Rs_discount;
    private TextInputEditText txt_companyName, txt_paymentID, txt_created_date, txt_bank, txt_authorization_id, txt_settlement_id, txt_status, txt_amount, txt_transaction_charges, txt_total_amount;
    private RecyclerView rv_fragment_retailer_order_details;
    private TextView tv_shipment_no_data;
    private RecyclerView.Adapter rv_productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<RetailerViewOrderProductModel> invo_productList = new ArrayList<>();
    private HashMap<String, String> RetailerOrderStatusKVP = new HashMap<>();
    private StatusKVP StatusKVPClass;
    private TextView discount_amount;
    private TextView total_amount, disclaimer_tv;
    private Button button_back, button_view_receipt;
    private FragmentTransaction fragmentTransaction;

    private TextView tv_banking_channel, payment_id, btn_newpayment;
    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "https://175.107.203.97:4013/api/company/ReadActiveCompanyContract/";
    private String PrePaidNumber = "", PrePaidId = "", CompanyName = "", Amount = "", CompanyId = "", MenuItem = "";
    private Button btn_voucher, btn_update, btn_back;
    private Spinner spinner_companyName;
    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private List<String> CompanyNames = new ArrayList<>();
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String company_names;
    private Typeface myFont;
    private Loader loader;
    private RelativeLayout ln_login;
    private RelativeLayout rl_jazz_cash;
    private View view_root;


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
//
//    @Override
//    public View onCreateView(
//            @NonNull LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("OrderId",
//                Context.MODE_PRIVATE);
//
//
//        orderID = sharedPreferences3.getString("OrderId", "");
//        // Log.i("OrderId", orderID);
//        if (!URL_Order_Data.contains(orderID)) {
//            URL_Order_Data = URL_Order_Data + orderID;
//            // Log.i("URL_Order_Data", URL_Order_Data);
//        }
//        View rootView = null;
//        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
//            case 1: {
//                rootView = inflater.inflate(R.layout.fragment_retailer_orders_tab, container, false);
//                txt_orderID = rootView.findViewById(R.id.txt_orderID);
//                txt_company_order = rootView.findViewById(R.id.txt_company_order);
//                txt_created_date_order = rootView.findViewById(R.id.txt_created_date_order);
//                txt_status_order = rootView.findViewById(R.id.txt_status_order);
//                txt_comments = rootView.findViewById(R.id.txt_comments);
//
//                txt_orderID.setEnabled(false);
//                txt_company_order.setEnabled(false);
//                txt_created_date_order.setEnabled(false);
//                txt_status_order.setEnabled(false);
//                txt_comments.setEnabled(false);
//
//                getOrderData();
//                break;
//            }
//            case 2: {
//                rootView = inflater.inflate(R.layout.fragment_retailer_orders_details_tab, container, false);
//                rv_fragment_retailer_order_details = rootView.findViewById(R.id.rv_fragment_retailer_order_details);
//                rv_fragment_retailer_order_details.setHasFixedSize(true);
//                layoutManager = new LinearLayoutManager(rootView.getContext());
//                rv_fragment_retailer_order_details.setLayoutManager(layoutManager);
//
//
//                getOrderDetailsData(rootView);
//                break;
//            }
//            case 3: {
//                rootView = inflater.inflate(R.layout.fragment_retailer_payment_tab, container, false);
//                txt_companyName = rootView.findViewById(R.id.txt_companyName);
//                txt_paymentID = rootView.findViewById(R.id.txt_paymentID);
//                txt_created_date = rootView.findViewById(R.id.txt_created_date);
//                txt_confirm = rootView.findViewById(R.id.txt_confirm);
//                txt_bank = rootView.findViewById(R.id.txt_bank);
//                txt_authorization_id = rootView.findViewById(R.id.txt_authorization_id);
//                txt_settlement_id = rootView.findViewById(R.id.txt_settlement_id);
//                txt_status = rootView.findViewById(R.id.txt_status);
//                txt_amount = rootView.findViewById(R.id.txt_amount);
//                txt_transaction_charges = rootView.findViewById(R.id.txt_transaction_charges);
//                txt_total_amount = rootView.findViewById(R.id.txt_total_amount);
//
//                txt_companyName.setEnabled(false);
//                txt_paymentID.setEnabled(false);
//                txt_created_date.setEnabled(false);
//                txt_confirm.setEnabled(false);
//                txt_bank.setEnabled(false);
//                txt_authorization_id.setEnabled(false);
//                txt_settlement_id.setEnabled(false);
//                txt_status.setEnabled(false);
//                txt_amount.setEnabled(false);
//                txt_transaction_charges.setEnabled(false);
//                txt_total_amount.setEnabled(false);
//
//
//                getPaymentData();
//                break;
//            }
//        }
//        return rootView;
//    }


    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("OrderId",
                Context.MODE_PRIVATE);


        orderID = sharedPreferences3.getString("OrderId", "");
        // Log.i("OrderId", orderID);
        if (!URL_Order_Data.contains(orderID)) {
            URL_Order_Data = URL_Order_Data + orderID;
            // Log.i("URL_Order_Data", URL_Order_Data);
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");

        StatusKVPClass = new StatusKVP(getContext(), Token);
        RetailerOrderStatusKVP = StatusKVPClass.getRetailerOrderStatusKVP();
        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
        editorOrderTabsFromDraft.putString("TabNo", "1");
        editorOrderTabsFromDraft.apply();

        View rootView = null;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {
                loader = new Loader(getContext());
                rootView = inflater.inflate(R.layout.fragment_retailer_orders_tab, container, false);
                view_root = rootView;

                layout_txt_orderID = rootView.findViewById(R.id.layout_txt_orderID);
                layout_txt_order_company = rootView.findViewById(R.id.layout_txt_order_company);
                layout_txt_created_date_order = rootView.findViewById(R.id.layout_txt_created_date_order);
                layout_txt_status_order = rootView.findViewById(R.id.layout_txt_status_order);
                layout_txt_order_reference = rootView.findViewById(R.id.layout_txt_order_reference);
                layout_txt_invoice_reference = rootView.findViewById(R.id.layout_txt_invoice_reference);
                layout_txt_comments = rootView.findViewById(R.id.layout_txt_comments);
                txt_orderID = rootView.findViewById(R.id.txt_orderID);
                txt_company_order = rootView.findViewById(R.id.txt_company_order);
                txt_created_date_order = rootView.findViewById(R.id.txt_created_date_order);
                txt_status_order = rootView.findViewById(R.id.txt_status_order);
                txt_order_reference = rootView.findViewById(R.id.txt_order_reference);
                txt_invoice_reference = rootView.findViewById(R.id.txt_invoice_reference);
                txt_comments = rootView.findViewById(R.id.txt_comments);
                button_back = rootView.findViewById(R.id.button_back);


                // layout_txt_order_reference.setVisibility(View.GONE);
                // layout_txt_invoice_reference.setVisibility(View.GONE);

                new TextField().changeColor(this.getContext(), layout_txt_orderID, txt_orderID);
                new TextField().changeColor(this.getContext(), layout_txt_order_company, txt_company_order);
                new TextField().changeColor(this.getContext(), layout_txt_created_date_order, txt_company_order);
                new TextField().changeColor(this.getContext(), layout_txt_status_order, txt_status_order);
                new TextField().changeColor(this.getContext(), layout_txt_order_reference, txt_order_reference);
                new TextField().changeColor(this.getContext(), layout_txt_invoice_reference, txt_invoice_reference);
                new TextField().changeColor(this.getContext(), layout_txt_comments, txt_comments);

                txt_orderID.setEnabled(false);
                txt_company_order.setEnabled(false);
                txt_created_date_order.setEnabled(false);
                txt_status_order.setEnabled(false);
                txt_comments.setEnabled(false);

                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dashboard_Tab());
//                        fragmentTransaction.commit();

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard());
                        fragmentTransaction.commit();
                    }
                });

                getOrderData();
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.fragment_retailer_orders_details_tab, container, false);
                view_root = rootView;

                rv_fragment_retailer_order_details = rootView.findViewById(R.id.rv_fragment_retailer_order_details);
                total_amount = rootView.findViewById(R.id.total_amount);
                rv_fragment_retailer_order_details.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                rv_fragment_retailer_order_details.setLayoutManager(layoutManager);
                disclaimer_tv = rootView.findViewById(R.id.disclaimer_tv);
                button_back = rootView.findViewById(R.id.button_back);
                discount = rootView.findViewById(R.id.discount);
                Rs_discount = rootView.findViewById(R.id.Rs_discount);
                discount_amount = rootView.findViewById(R.id.discount_amount);


                SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("OrderId",
                        Context.MODE_PRIVATE);
                InvoiceStatus = sharedPreferences1.getString("InvoiceStatus", "");
                String Status = sharedPreferences1.getString("RetailerOrderStatusValue", "");
                // Log.i("InvoiceStatus", InvoiceStatus);

//        SectionsPagerAdapter sectionsPagerAdapter = null;
                if ((!InvoiceStatus.equals("null") && !InvoiceStatus.equals("0")) || Status.equals("Cancelled")) {
                    disclaimer_tv.setVisibility(View.GONE);
                }


                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dashboard_Tab());
//                        fragmentTransaction.commit();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard());
                        fragmentTransaction.commit();
                    }
                });

                getOrderDetailsData(rootView);
                break;
            }
            case 3: {

                SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("OrderId",
                        Context.MODE_PRIVATE);
                InvoiceStatus = sharedPreferences1.getString("InvoiceStatus", "");
                // Log.i("InvoiceStatus", InvoiceStatus);
//                if (InvoiceStatus.equals("0") || InvoiceStatus.equals("3")) {

                rootView = inflater.inflate(R.layout.fragment_retailer_payment_tab, container, false);

                view_root = rootView;

                layout_txt_companName = rootView.findViewById(R.id.layout_txt_companName);
                layout_txt_paymentID = rootView.findViewById(R.id.layout_txt_paymentID);
                layout_txt_created_date = rootView.findViewById(R.id.layout_txt_created_date);
                layout_transaction_date = rootView.findViewById(R.id.layout_transaction_date);
                layout_txt_bank = rootView.findViewById(R.id.layout_txt_bank);
                layout_txt_status = rootView.findViewById(R.id.layout_txt_status);
                layout_txt_authorization_id = rootView.findViewById(R.id.layout_txt_authorization_id);
                layout_txt_settlement_id = rootView.findViewById(R.id.layout_txt_settlement_id);
                layout_txt_amount = rootView.findViewById(R.id.layout_txt_amount);
                layout_txt_transaction_charges = rootView.findViewById(R.id.layout_txt_transaction_charges);
                layout_txt_total_amount = rootView.findViewById(R.id.layout_txt_total_amount);
                button_back = rootView.findViewById(R.id.button_back);
                button_view_receipt = rootView.findViewById(R.id.button_view_receipt);

                txt_companyName = rootView.findViewById(R.id.txt_companyName);
                txt_paymentID = rootView.findViewById(R.id.txt_paymentID);
                txt_created_date = rootView.findViewById(R.id.txt_created_date);
                txt_confirm = rootView.findViewById(R.id.txt_confirm);
                txt_bank = rootView.findViewById(R.id.txt_bank);
                txt_authorization_id = rootView.findViewById(R.id.txt_authorization_id);
                txt_settlement_id = rootView.findViewById(R.id.txt_settlement_id);
                txt_status = rootView.findViewById(R.id.txt_status);
                txt_amount = rootView.findViewById(R.id.txt_amount);
                txt_transaction_charges = rootView.findViewById(R.id.txt_transaction_charges);
                txt_total_amount = rootView.findViewById(R.id.txt_total_amount);


                new TextField().changeColor(getContext(), layout_txt_companName, txt_companyName);
                new TextField().changeColor(getContext(), layout_txt_paymentID, txt_paymentID);
                new TextField().changeColor(getContext(), layout_txt_created_date, txt_created_date);
                new TextField().changeColor(getContext(), layout_transaction_date, txt_confirm);
                new TextField().changeColor(getContext(), layout_txt_bank, txt_bank);
                new TextField().changeColor(getContext(), layout_txt_authorization_id, txt_authorization_id);
                new TextField().changeColor(getContext(), layout_txt_settlement_id, txt_settlement_id);
                new TextField().changeColor(getContext(), layout_txt_status, txt_status);
                new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
                new TextField().changeColor(getContext(), layout_txt_transaction_charges, txt_transaction_charges);
                new TextField().changeColor(getContext(), layout_txt_total_amount, txt_total_amount);

                // fields hidden here
                // layout_txt_created_date.setVisibility(View.GONE);
                // layout_transaction_date.setVisibility(View.GONE);
                // layout_txt_bank.setVisibility(View.GONE);
                // layout_txt_authorization_id.setVisibility(View.GONE);
                // layout_txt_settlement_id.setVisibility(View.GONE);
                // layout_txt_status.setVisibility(View.GONE);
                // layout_txt_amount.setVisibility(View.GONE);
                // layout_txt_transaction_charges.setVisibility(View.GONE);
                // layout_txt_total_amount.setVisibility(View.GONE);

                txt_companyName.setEnabled(false);
                txt_paymentID.setEnabled(false);
                txt_created_date.setEnabled(false);
                txt_confirm.setEnabled(false);
                txt_bank.setEnabled(false);
                txt_authorization_id.setEnabled(false);
                txt_settlement_id.setEnabled(false);
                txt_status.setEnabled(false);
                txt_amount.setEnabled(false);
                txt_transaction_charges.setEnabled(false);
                txt_total_amount.setEnabled(false);

                button_view_receipt.setVisibility(View.GONE);

                button_view_receipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkAndRequestPermissions()) {
                            try {
                                viewReceiptPDF(getContext(), invoiceID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.add(R.id.main_container, new Dashboard_Tab());
//                            fragmentTransaction.commit();

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard());
                        fragmentTransaction.commit();
                    }
                });

                getPaidInvoiceData();
//                } else if (InvoiceStatus.equals("1") || InvoiceStatus.equals("2") || InvoiceStatus.equals("-1")) {
////                    rootView = inflater.inflate(R.layout.activity_payment__screen3, container, false);
//
//
//                    rootView = inflater.inflate(R.layout.activity_payment__screen3, container, false);
//                    view_root = rootView;
//
//
//                    myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
//
//                    payment_id = rootView.findViewById(R.id.payment_id);
//                    spinner_companyName = rootView.findViewById(R.id.spinner_companyName);
//                    txt_amount = rootView.findViewById(R.id.txt_amount);
//                    layout_txt_amount = rootView.findViewById(R.id.layout_txt_amount);
//                    btn_newpayment = rootView.findViewById(R.id.btn_addpayment);
//                    btn_update = rootView.findViewById(R.id.btn_update);
//                    btn_voucher = rootView.findViewById(R.id.btn_voucher);
//                    ln_login = rootView.findViewById(R.id.ln_login);
//
//                    ln_login.setVisibility(View.GONE);
//
//
//                    rl_jazz_cash = rootView.findViewById(R.id.rl_jazz_cash);
//
//                    rl_jazz_cash.setVisibility(View.GONE);
//
//                    new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
//
//                    getUnPaidInvoiceData();
//
//                    payment_id.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View v) {
//                            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                            cm.setText(payment_id.getText());
////                            new CustomToast().showToast(getActivity(), "Payment ID: " + String.valueOf(payment_id.getText()) + " - Copied to clipboard");
//                            new CustomToast().showToast(getActivity(), "PSID has been copied to clipboard");
//                            return false;
//                        }
//                    });
//
//                    company_names = "";
//
//                    txt_amount.setEnabled(false);
//                    spinner_companyName.setEnabled(false);
//                    spinner_companyName.setClickable(false);
//
//                    arrayAdapterPayments = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, CompanyNames) {
//                        @Override
//                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                            // TODO Auto-generated method stub
//                            View view = super.getView(position, convertView, parent);
//                            TextView text = (TextView) view.findViewById(android.R.id.text1);
//                            text.setTextColor(getResources().getColor(R.color.text_color_selection));
//                            text.setTextSize((float) 13.6);
//                            text.setPadding(50, 0, 50, 0);
//                            text.setTypeface(myFont);
//                            return view;
//                        }
//
//                        @Override
//                        public View getView(int position, View convertView, ViewGroup parent) {
//                            // TODO Auto-generated method stub
//                            View view = super.getView(position, convertView, parent);
//                            TextView text = (TextView) view.findViewById(android.R.id.text1);
//                            text.setTextColor(getResources().getColor(R.color.text_color_selection));
//                            text.setTextSize((float) 13.6);
//                            text.setPadding(50, 0, 50, 0);
//                            return view;
//                        }
//                    };
//
//                    spinner_companyName.setAdapter(arrayAdapterPayments);
//
//
//                    btn_newpayment.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.add(R.id.main_container, new CreatePaymentRequestFragment());
//                            fragmentTransaction.commit();
//                        }
//                    });
//
//                    btn_update.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                            final FragmentManager fm = getActivity().getSupportFragmentManager();
////                            fm.popBackStack();
//                            SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
//                                    Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                            editorOrderTabsFromDraft.putString("TabNo", "0");
//                            editorOrderTabsFromDraft.apply();
//
//
//                            FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard());
//                            fragmentTransaction.commit();
////                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
////                fragmentTransaction.add(R.id.main_container, new EditPaymentRequestFragment());
////                fragmentTransaction.commit();
//
//                        }
//                    });
//
//                    btn_voucher.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (checkAndRequestPermissions()) {
//                                try {
//                                    viewPDF(getContext(), invoiceID);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });
//
//                    tv_banking_channel = rootView.findViewById(R.id.tv_banking_channel);
//                    tv_banking_channel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            final AlertDialog alertDialog2 = new AlertDialog.Builder(getContext()).create();
//                            LayoutInflater inflater2 = LayoutInflater.from(getContext());
//                            View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
//                            alertDialog2.setView(view_popup2);
//                            alertDialog2.show();
//                            ImageButton img_close = view_popup2.findViewById(R.id.image_button_close);
//                            TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
//                            payment_information_txt3.setText(PrePaidNumber);
//                            Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
//                            btn_view_voucher.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (checkAndRequestPermissions()) {
//                                        try {
//                                            viewPDF(getContext(), invoiceID);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//                            });
//
//                            img_close.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    alertDialog2.dismiss();
//                                }
//                            });
//                        }
//                    });
//
//                }

                break;
            }
        }
        return rootView;
    }

    private void viewReceiptPDF(Context context, String ID) throws JSONException {
        ViewPDFRequest viewPDFRequest = new ViewPDFRequest();
        viewPDFRequest.viewPDF_Invoice(context, ID);
    }

    private void viewPDF(Context context, String ID) throws JSONException {
        ViewVoucherRequest viewPDFRequest = new ViewVoucherRequest();
        viewPDFRequest.viewPDF_Invoice(context, ID);
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

//
//    private void getOrderData() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        // Log.i("DistributorId invoice", DistributorId);
//        // Log.i("Token invoice", Token);
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // Log.i("Order Data response", String.valueOf(response));
//                try {
//                    txt_orderID.setText(String.valueOf(response.get("RetailerOrderNumber")));
//                    txt_company_order.setText(String.valueOf(response.get("RetailerCompanyName")));
//                    txt_created_date_order.setText(String.valueOf(response.get("RetailerRetailerOrderCreatedDate")).split("T")[0]);
//                    txt_status_order.setText(String.valueOf(response.get("RetailerOrderStatusValue")));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        printErrorMessage(error);
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }
//
//    private void getOrderDetailsData(View rootView) {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        tv_shipment_no_data = rootView.findViewById(R.id.tv_shipment_no_data);
//        tv_shipment_no_data.setVisibility(View.GONE);
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        // Log.i("DistributorId invoice", DistributorId);
//        // Log.i("Token invoice", Token);
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<RetailerViewOrderProductModel>>() {
//                }.getType();
//                try {
//                    invo_productList = gson.fromJson(response.get("OrderDetails").toString(), type);
//                    // Log.i("OrderDetails", String.valueOf(response.get("OrderDetails")));
//                    RetailerViewOrderProductAdapter productAdapter = new RetailerViewOrderProductAdapter(getContext(), invo_productList);
//                    rv_fragment_retailer_order_details.setAdapter(productAdapter);
//                    if (invo_productList.size() != 0) {
//                        tv_shipment_no_data.setVisibility(View.GONE);
//                    } else {
//                        tv_shipment_no_data.setVisibility(View.VISIBLE);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        printErrorMessage(error);
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }
//
//    private void getPaymentData() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        // Log.i("DistributorId invoice", DistributorId);
//        // Log.i("Token invoice", Token);
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // Log.i("Order Data response2", String.valueOf(response));
//                try {
//                    txt_companyName.setText(String.valueOf(response.get("RetailerCompanyName")));
//                    txt_paymentID.setText(String.valueOf(response.get("RetailerInvoiceNumber")));
//                    txt_created_date.setText(String.valueOf(response.get("RetailerInvoiceCreateDate")));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        printErrorMessage(error);
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }

    @Override
    public void onResume() {
        super.onResume();

        if (view_root != null) {
            view_root.setFocusableInTouchMode(true);
            view_root.requestFocus();
            view_root.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard());
                        fragmentTransaction.commit();
                    }
                    return false;
                }
            });
        }

    }

    private void getOrderData() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        // Log.i("DistributorId invoice", DistributorId);
        // Log.i("Token invoice", Token);
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                // Log.i("Order Data response", String.valueOf(response));
                try {
                    if (!String.valueOf(response.get("RetailerOrderNumber")).equals("") && !String.valueOf(response.get("RetailerOrderNumber")).equals("null"))
                        txt_orderID.setText(String.valueOf(response.get("RetailerOrderNumber")));
                    if (!String.valueOf(response.get("RetailerCompanyName")).equals("") && !String.valueOf(response.get("RetailerCompanyName")).equals("null"))
                        txt_company_order.setText(String.valueOf(response.get("RetailerCompanyName")));
                    if (!String.valueOf(response.get("RetailerOrderCreatedDate")).equals("") && !String.valueOf(response.get("RetailerOrderCreatedDate")).equals("null"))
                        txt_created_date_order.setText(String.valueOf(response.get("RetailerOrderCreatedDate")).split("T")[0]);
                    if (!String.valueOf(response.get("RetailerOrderStatusValue")).equals("") && !String.valueOf(response.get("RetailerOrderStatusValue")).equals("null"))
                        txt_status_order.setText(String.valueOf(response.get("RetailerOrderStatusValue")));
                    if (!String.valueOf(response.get("OrderReference")).equals("") && !String.valueOf(response.get("OrderReference")).equals("null"))
                        txt_order_reference.setText(String.valueOf(response.get("OrderReference")));
                    if (!String.valueOf(response.get("InvoiceReference")).equals("") && !String.valueOf(response.get("InvoiceReference")).equals("null"))
                        txt_invoice_reference.setText(String.valueOf(response.get("InvoiceReference")));

                    if (!String.valueOf(response.get("RetailerOrderNumber")).equals("") && !String.valueOf(response.get("RetailerOrderNumber")).equals("null"))
                        txt_orderID.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerCompanyName")).equals("") && !String.valueOf(response.get("RetailerCompanyName")).equals("null"))
                        txt_company_order.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerOrderCreatedDate")).equals("") && !String.valueOf(response.get("RetailerOrderCreatedDate")).equals("null"))
                        txt_created_date_order.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerOrderStatusValue")).equals("") && !String.valueOf(response.get("RetailerOrderStatusValue")).equals("null"))
                        txt_status_order.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("OrderReference")).equals("") && !String.valueOf(response.get("OrderReference")).equals("null")) {
                        layout_txt_order_reference.setVisibility(View.VISIBLE);
                        txt_order_reference.setTextColor(getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(response.get("InvoiceReference")).equals("") && !String.valueOf(response.get("InvoiceReference")).equals("null")) {
                        layout_txt_invoice_reference.setVisibility(View.VISIBLE);
                        txt_invoice_reference.setTextColor(getResources().getColor(R.color.textcolor));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loader.hideLoader();
                        new ProcessingError().showError(getContext());
                        new HaballError().printErrorMessage(getContext(), error);

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

    private void getOrderDetailsData(View rootView) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        tv_shipment_no_data = rootView.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<RetailerViewOrderProductModel>>() {
                }.getType();
                try {
                    double totalPrice = 0;
                    double totalDiscount = 0;
                    invo_productList = gson.fromJson(response.get("OrderDetails").toString(), type);
                    for (int i = 0; i < invo_productList.size(); i++) {
                        if (!String.valueOf(invo_productList.get(i).getTotalamount()).equals("null"))
                            totalPrice += Double.parseDouble(invo_productList.get(i).getTotalamount());
                    }
                    for (int i = 0; i < invo_productList.size(); i++) {
                        if (!String.valueOf(invo_productList.get(i).getDiscount()).equals("null"))
                            totalDiscount += Double.parseDouble(invo_productList.get(i).getDiscount());
                    }
                    // Log.i("OrderDetails", String.valueOf(response.get("OrderDetails")));
                    RetailerViewOrderProductAdapter productAdapter = new RetailerViewOrderProductAdapter(getContext(), invo_productList);
                    rv_fragment_retailer_order_details.setAdapter(productAdapter);
                    DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//
//                    String TotalAmount = "";
//                    if (!response.getString("TotoalOrderDiscount").equals("null") && !response.getString("TotoalOrderDiscount").equals("0")) {
//                        String OrderTotalDiscount = formatter1.format(Double.parseDouble(response.getString("TotoalOrderDiscount")));
//                        discount_amount.setText(OrderTotalDiscount);
//
//                        if (totalPrice != 0)
//                            TotalAmount = formatter1.format(totalPrice - Double.parseDouble(response.getString("TotoalOrderDiscount")));
//                        total_amount.setText(TotalAmount);
//                    } else if (totalDiscount == 0) {
//                        discount.setVisibility(View.GONE);
//                        Rs_discount.setVisibility(View.GONE);
//                        discount_amount.setVisibility(View.GONE);
//
//                        if (totalPrice != 0)
//                            TotalAmount = formatter1.format(totalPrice);
//                        total_amount.setText(TotalAmount);
//                    } else {
//                        String OrderTotalDiscount = formatter1.format(totalDiscount);
//                        discount_amount.setText(OrderTotalDiscount);
//
//                        if (totalPrice != 0)
//                            TotalAmount = formatter1.format(totalPrice - totalDiscount);
//                        total_amount.setText(TotalAmount);
//                    }
//
                    String TotalAmount = "";
                    if (totalPrice != 0)
                        TotalAmount = formatter1.format(Double.parseDouble(response.getString("RetailerOrderTotal")));
                    total_amount.setText(TotalAmount);
                    if (!response.getString("TotoalOrderDiscount").equals("null") && !response.getString("TotoalOrderDiscount").equals("0")) {
                        String OrderTotalDiscount = formatter1.format(Double.parseDouble(response.getString("TotoalOrderDiscount")));
                        discount_amount.setText(OrderTotalDiscount);
                    } else if (totalDiscount == 0) {
                        discount.setVisibility(View.GONE);
                        Rs_discount.setVisibility(View.GONE);
                        discount_amount.setVisibility(View.GONE);
                    } else {
                        String OrderTotalDiscount = formatter1.format(totalDiscount);
                        discount_amount.setText(OrderTotalDiscount);
                    }

                    if (invo_productList.size() != 0) {
                        tv_shipment_no_data.setVisibility(View.GONE);
                    } else {
                        tv_shipment_no_data.setVisibility(View.VISIBLE);
                    }
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

    private void getUnPaidInvoiceData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        if (!URL_Order_Data.contains("/" + orderID)) {
            URL_Order_Data = URL_Order_Data + orderID;
            // Log.i("URL_Payment_Data", URL_Order_Data);
        }
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        // Log.i("DistributorId invoice", DistributorId);
        // Log.i("Token invoice12", Token);
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Log.i("Order Data_UnPaid", String.valueOf(response));
                try {
                    invoiceID = response.getString("RetailerInvoiceId");
                    CompanyName = String.valueOf(response.get("RetailerCompanyName"));
//                    spinner_companyName.setSelection(CompanyNames.indexOf(CompanyName));

                    CompanyNames.add(CompanyName);
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


                    PrePaidNumber = String.valueOf(response.get("PaymentID"));
                    Amount = String.valueOf(response.get("RetailerPaymentAmount"));
                    txt_amount.setText(Amount);
                    payment_id.setText(PrePaidNumber);
//                    PrePaidId = String.valueOf(response.get("InvoiceTotalAmount"));
                    if (!String.valueOf(response.get("RetailerPaymentAmount")).equals("") && !String.valueOf(response.get("RetailerPaymentAmount")).equals("null"))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));

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

    private void getPaidInvoiceData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
//        // Log.i("DistributorId invoice", DistributorId);
        // Log.i("Token invoice12", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Log.i("Order Data response2", String.valueOf(response));
                try {
//                    JSONObject response = result.getJSONObject("OrderPaymentDetails");
                    invoiceID = response.getString("RetailerInvoiceId");
                    txt_companyName.setText(String.valueOf(response.get("RetailerCompanyName")));
                    txt_paymentID.setText(String.valueOf(response.get("PaymentID")));
                    setTextAndShowDate(layout_txt_created_date, txt_created_date, String.valueOf(response.get("RetailerPaymentCreatedDate")).split("T")[0]);
//                    setTextAndShow(layout_txt_amount, txt_amount, String.valueOf(response.get("InvoiceTotalAmount")));
                    DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
                    String Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("RetailerPaymentAmount")));
                    setTextAndShow(layout_txt_amount, txt_amount, Formatted_TotalAmount);

                    setTextAndShow(layout_txt_status, txt_status, String.valueOf(response.getString("RetailerPaymentStatus")));
                    setTextAndShow(layout_transaction_date, txt_confirm, String.valueOf(response.getString("RetailerInvoicePaidDate")).split("T")[0]);
//                    setTextAndShow(layout_txt_bank, txt_bank, String.valueOf(response.getString("BankName")));
//                    setTextAndShow(layout_txt_authorization_id, txt_authorization_id, String.valueOf(response.getString("AuthID")));
//                    setTextAndShow(layout_txt_settlement_id, txt_settlement_id, String.valueOf(response.getString("SettlementID")));
//                    setTextAndShow(layout_txt_total_amount, txt_total_amount, String.valueOf(response.getString("TotalAmount")));
                    Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("RetailerOrderTotal")));
                    setTextAndShow(layout_txt_total_amount, txt_total_amount, Formatted_TotalAmount);

//                    setTextAndShow(layout_txt_transaction_charges, txt_transaction_charges, String.valueOf(response.getString("TransactionCharges")));

                    if (!String.valueOf(response.get("RetailerCompanyName")).equals("") && !String.valueOf(response.get("RetailerCompanyName")).equals("null"))
                        txt_companyName.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("PaymentID")).equals("") && !String.valueOf(response.get("PaymentID")).equals("null"))
                        txt_paymentID.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerPaymentCreatedDate")).split("T")[0].equals("") && !String.valueOf(response.get("RetailerPaymentCreatedDate")).split("T")[0].equals("null"))
                        txt_created_date.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerInvoicePaidDate")).equals("") && !String.valueOf(response.get("RetailerInvoicePaidDate")).equals("null"))
                        txt_confirm.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("BankName")).equals("") && !String.valueOf(response.get("BankName")).equals("null"))
//                        txt_bank.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("AuthID")).equals("") && !String.valueOf(response.get("AuthID")).equals("null"))
//                        txt_authorization_id.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("SettlementID")).equals("") && !String.valueOf(response.get("SettlementID")).equals("null"))
//                        txt_settlement_id.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerPaymentStatus")).equals("") && !String.valueOf(response.get("RetailerPaymentStatus")).equals("null"))
                        txt_status.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerPaymentAmount")).equals("") && !String.valueOf(response.get("RetailerPaymentAmount")).equals("null"))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("TransactionCharges")).equals("") && !String.valueOf(response.get("TransactionCharges")).equals("null"))
//                        txt_transaction_charges.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("RetailerOrderTotal")).equals("") && !String.valueOf(response.get("RetailerOrderTotal")).equals("null"))
                        txt_total_amount.setTextColor(getResources().getColor(R.color.textcolor));

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

    private void setTextAndShow(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value);
        }


        if (InvoiceStatus.equals("0") || InvoiceStatus.equals("Pending") || InvoiceStatus.equals("Cancelled")) {
            layout_transaction_date.setVisibility(View.GONE);
            layout_txt_bank.setVisibility(View.GONE);
            layout_txt_authorization_id.setVisibility(View.GONE);
            layout_txt_settlement_id.setVisibility(View.GONE);
            layout_txt_transaction_charges.setVisibility(View.GONE);
            layout_txt_total_amount.setVisibility(View.GONE);
        }
    }

    private void setTextAndShowDate(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value.split("T")[0]);
        }


        if (InvoiceStatus.equals("0") || InvoiceStatus.equals("Pending") || equals("Cancelled")) {
            layout_transaction_date.setVisibility(View.GONE);
            layout_txt_bank.setVisibility(View.GONE);
            layout_txt_authorization_id.setVisibility(View.GONE);
            layout_txt_settlement_id.setVisibility(View.GONE);
            layout_txt_transaction_charges.setVisibility(View.GONE);
            layout_txt_total_amount.setVisibility(View.GONE);
        }
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
                    // Log.i("responseBody", responseBody);
                    JSONObject data = new JSONObject(responseBody);
                    // Log.i("data", String.valueOf(data));
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