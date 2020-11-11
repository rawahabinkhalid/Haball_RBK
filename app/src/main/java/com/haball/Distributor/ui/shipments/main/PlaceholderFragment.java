package com.haball.Distributor.ui.shipments.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distribution_Login.Distribution_Login;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.main.OrdersFragment;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.Distributor.ui.payments.PaymentScreen3Fragment;
import com.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.haball.Distributor.ui.shipments.main.Adapters.SectionsPagerAdapter;
import com.haball.Distributor.ui.shipments.main.Models.PageViewModel;
import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Shipment.Adapters.ProductDetailsAdapter;
import com.haball.Shipment.Adapters.ProductOrderDetailsAdapter;
import com.haball.Shipment.ui.main.Models.Distributor_InvoiceModel;
import com.haball.Shipment.ui.main.Models.Distributor_OrderModel;
import com.haball.Shipment.ui.main.Models.Distributor_ProductModel;
import com.haball.Shipment.ui.main.Models.Distributor_ShipmentModel;
import com.haball.TextField;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    // invoice data
    private String INVOICE_URL = "https://175.107.203.97:4013/api/deliverynotes/";
    private String Token;
    private String DistributorId;
    private String shipmentID;
    private View view;
    // order data
    private Button btn_receive, btn_back;

    private TextInputLayout layout_company, layout_shipment_id, layout_shipment_created_date, layout_shipment_recieving_date, layout_shipment_tv_dc_number, layout_shipment_tv_shstatus;
    private TextInputEditText company, shipment_id, shipment_created_date, shipment_recieving_date, shipment_tv_dc_number, shipment_tv_shstatus;

    private TextInputLayout layout_order_company_name, layout_order_id, layout_order_tv_cdate, layout_order_tv_status, layout_order_tv_shaddress;
    private TextInputEditText order_company_name, order_id, order_tv_cdate, order_tv_status, order_tv_shaddress;

    private TextInputLayout layout_txt_companName, layout_txt_paymentID, layout_txt_created_date, layout_transaction_date,
            layout_txt_bank, layout_txt_authorization_id, layout_txt_settlement_id, layout_txt_status,
            layout_txt_amount, layout_txt_transaction_charges, layout_txt_total_amount;
    private TextInputEditText txt_companyName, txt_paymentID, txt_created_date, txt_confirm, txt_bank, txt_authorization_id, txt_settlement_id, txt_status, txt_amount, txt_transaction_charges, txt_total_amount;


    private TextView discount_amount, total_amount ,tv_shipment_no_data;
    private RecyclerView product_rv_shipment;

    private RecyclerView.Adapter productShipmentDetailsAdapter;
    private RecyclerView.Adapter productDetailsAdapter;
    private List<Distributor_ProductModel> productShipmentList = new ArrayList<>();
    private List<Distributor_ProductModel> productList = new ArrayList<>();

    private RecyclerView.LayoutManager layoutManager;
    FragmentManager fragmentManager;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private ViewPager viewPager;

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
        Log.i("abbccc", String.valueOf(index));
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case 1: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_fragment, container, false);
                SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                        Context.MODE_PRIVATE);
                String ShipmentStatusValue = sharedPreferences3.getString("ShipmentStatusValue", "");
//                Log.i("shipmentID shared pref", shipmentID);

                btn_receive = rootView.findViewById(R.id.btn_receive);
                if (ShipmentStatusValue.equals("Delivered"))
                    btn_receive.setVisibility(View.VISIBLE);

                btn_back = rootView.findViewById(R.id.btn_back);

                layout_company = rootView.findViewById(R.id.layout_company);
                layout_shipment_id = rootView.findViewById(R.id.layout_shipment_id);
                layout_shipment_created_date = rootView.findViewById(R.id.layout_shipment_created_date);
                layout_shipment_recieving_date = rootView.findViewById(R.id.layout_shipment_recieving_date);
                layout_shipment_tv_dc_number = rootView.findViewById(R.id.layout_shipment_tv_dc_number);
                layout_shipment_tv_shstatus = rootView.findViewById(R.id.layout_shipment_tv_shstatus);

                company = rootView.findViewById(R.id.company);
                shipment_id = rootView.findViewById(R.id.shipment_id);
                shipment_created_date = rootView.findViewById(R.id.shipment_created_date);
                shipment_recieving_date = rootView.findViewById(R.id.shipment_recieving_date);
                shipment_tv_dc_number = rootView.findViewById(R.id.shipment_tv_dc_number);
                shipment_tv_shstatus = rootView.findViewById(R.id.shipment_tv_shstatus);

                new TextField().changeColor(this.getContext(), layout_company, company);
                new TextField().changeColor(this.getContext(), layout_shipment_id, shipment_id);
                new TextField().changeColor(this.getContext(), layout_shipment_created_date, shipment_created_date);
                new TextField().changeColor(this.getContext(), layout_shipment_recieving_date, shipment_recieving_date);
                new TextField().changeColor(this.getContext(), layout_shipment_tv_dc_number, shipment_tv_dc_number);
                new TextField().changeColor(this.getContext(), layout_shipment_tv_shstatus, shipment_tv_shstatus);
                shipmentData();

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                        ((FragmentActivity) getContext()).startActivity(login_intent);
//                        ((FragmentActivity) getContext()).finish();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new Shipments_Fragments()).addToBackStack("tag");
                        fragmentTransaction.commit();
                    }
                });

                btn_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        receiveShipment();
                    }
                });
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.distributor_shipment_view_shipment_details_fragment, container, false);

                product_rv_shipment = rootView.findViewById(R.id.product_rv_shipment);
                btn_back = rootView.findViewById(R.id.btn_back);
                tv_shipment_no_data = rootView.findViewById(R.id.tv_shipment_no_data);
                product_rv_shipment.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                product_rv_shipment.setLayoutManager(layoutManager);
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                        ((FragmentActivity) getContext()).startActivity(login_intent);
//                        ((FragmentActivity) getContext()).finish();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new Shipments_Fragments()).addToBackStack("tag");
                        fragmentTransaction.commit();

                    }
                });

                shipmentListData();
                break;
            }
            case 3: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_2_fragment, container, false);

                btn_back = rootView.findViewById(R.id.btn_back);

                layout_order_id = rootView.findViewById(R.id.layout_order_id);
                layout_order_company_name = rootView.findViewById(R.id.layout_order_company_name);
                layout_order_tv_cdate = rootView.findViewById(R.id.layout_order_tv_cdate);
                layout_order_tv_status = rootView.findViewById(R.id.layout_order_tv_status);
                layout_order_tv_shaddress = rootView.findViewById(R.id.layout_order_tv_shaddress);

                order_id = rootView.findViewById(R.id.order_id);
                order_company_name = rootView.findViewById(R.id.order_company_name);
                order_tv_cdate = rootView.findViewById(R.id.order_tv_cdate);
                order_tv_status = rootView.findViewById(R.id.order_tv_status);
                order_tv_shaddress = rootView.findViewById(R.id.order_tv_shaddress);

                new TextField().changeColor(this.getContext(), layout_order_id, order_id);
                new TextField().changeColor(this.getContext(), layout_order_company_name, order_company_name);
                new TextField().changeColor(this.getContext(), layout_order_tv_cdate, order_tv_cdate);
                new TextField().changeColor(this.getContext(), layout_order_tv_status, order_tv_status);
                new TextField().changeColor(this.getContext(), layout_order_tv_shaddress, order_tv_shaddress);

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                        ((FragmentActivity) getContext()).startActivity(login_intent);
//                        ((FragmentActivity) getContext()).finish();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new Shipments_Fragments()).addToBackStack("tag");
                        fragmentTransaction.commit();

                    }
                });

                orderData();
                break;
            }
            case 4: {
                rootView = inflater.inflate(R.layout.distributor_shipment__view_shipment_3_fragment, container, false);

                btn_back = rootView.findViewById(R.id.btn_back);

                product_rv_shipment = (RecyclerView) rootView.findViewById(R.id.product_rv_shipment);
                discount_amount = rootView.findViewById(R.id.discount_amount);
                total_amount = rootView.findViewById(R.id.total_amount);
                ProductData();
                product_rv_shipment.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                product_rv_shipment.setLayoutManager(layoutManager);

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                        ((FragmentActivity) getContext()).startActivity(login_intent);
//                        ((FragmentActivity) getContext()).finish();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new Shipments_Fragments()).addToBackStack("tag");
                        fragmentTransaction.commit();
                    }
                });

                break;
            }

            case 5: {
                rootView = inflater.inflate(R.layout.fragment_retailer_payment_tab, container, false);
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
                btn_back = rootView.findViewById(R.id.button_back);
                Button button_view_receipt = rootView.findViewById(R.id.button_view_receipt);
                button_view_receipt.setVisibility(View.GONE);

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

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                        ((FragmentActivity) getContext()).startActivity(login_intent);
//                        ((FragmentActivity) getContext()).finish();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new Shipments_Fragments()).addToBackStack("tag");
                        fragmentTransaction.commit();
                    }
                });

                InvoiceData();
                break;
            }
        }

        return rootView;

    }

    private void receiveShipment() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        final String DeliveryNumber = sharedPreferences3.getString("DeliveryNumber", "");
        Log.i("shipmentID shared pref", shipmentID);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        String RECEIVE_SHIPMENT_URL = "https://175.107.203.97:4013/api/deliverynotes/MarkReceived/" + shipmentID;

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, RECEIVE_SHIPMENT_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {

                final Dialog fbDialogue = new Dialog(getActivity());
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                tv_pr1.setText("");
                txt_header1.setText("Shipment Received");
                String steps1 = "Shipment ID ";
                String steps2 = " has been received successfully.";
                String title = DeliveryNumber;
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
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new Shipments_Fragments());
                        fragmentTransaction.commit();
                    }
                });
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
        Volley.newRequestQueue(getContext()).add(obj);

    }


    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void ProductData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if (!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL12", INVOICE_URL);

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, INVOICE_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
                    String yourFormattedString1 = formatter1.format(Double.parseDouble(response.getString("TotalPrice")));

                    total_amount.setText(yourFormattedString1);

                    yourFormattedString1 = formatter1.format(Double.parseDouble(response.getString("Discount")));
                    discount_amount.setText(yourFormattedString1);

                    Log.i("responesProductmy", response.getString("DeliveryNoteDetails").toString());
                    JSONArray jsonArray = new JSONArray(response.getString("DeliveryNoteDetails"));
                    Log.i("responesProduct", jsonArray.toString());

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Distributor_ProductModel>>() {
                    }.getType();
                    productList = gson.fromJson(String.valueOf(jsonArray), type);
                    Log.i("productList", String.valueOf(productList));

                    productDetailsAdapter = new ProductOrderDetailsAdapter(getContext(), productList);
                    product_rv_shipment.setAdapter(productDetailsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Error Product", e.toString());
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(obj);

    }

    private void shipmentData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if (!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL1", INVOICE_URL);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, INVOICE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", String.valueOf(response));
                try {
                    Gson gson = new Gson();
                    Distributor_ShipmentModel shipmentModel = gson.fromJson(String.valueOf(response), Distributor_ShipmentModel.class);
                    company.setText(response.getJSONObject("deliveryCorp").getString("CompanyName"));
                    shipment_id.setText(shipmentModel.getShippmentNo());
                    shipment_created_date.setText(shipmentModel.getCreatedDate().split("T")[0]);
                    shipment_tv_dc_number.setText(shipmentModel.getDeliveryNumber());

                    if (shipmentModel.getDeliveryNoteStatus().equals("0")) {
                        shipment_tv_shstatus.setText("Pending");
                    } else if (shipmentModel.getDeliveryNoteStatus().equals("1")) {
                        shipment_tv_shstatus.setText("In Transit");
                    } else if (shipmentModel.getDeliveryNoteStatus().equals("2")) {
                        shipment_tv_shstatus.setText("Received");
                    } else if (shipmentModel.getDeliveryNoteStatus().equals("3")) {
                        shipment_tv_shstatus.setText("Returned");
                    } else if (shipmentModel.getDeliveryNoteStatus().equals("4")) {
                        shipment_tv_shstatus.setText("Revised");
                    }

                    layout_shipment_tv_dc_number.setVisibility(View.VISIBLE);
                    if (!String.valueOf(shipment_tv_dc_number.getText()).equals("") && !String.valueOf(shipment_tv_dc_number.getText()).equals("null"))
                        shipment_tv_dc_number.setTextColor(getResources().getColor(R.color.textcolor));

                    if (String.valueOf(shipment_tv_shstatus.getText()).equals("Received")) {
                        layout_shipment_recieving_date.setVisibility(View.VISIBLE);
                        shipment_recieving_date.setText(shipmentModel.getGoodsreceivenotesReceivingDate().split("T")[0]);

                        if (!String.valueOf(shipment_recieving_date.getText()).equals("") && !String.valueOf(shipment_recieving_date.getText()).equals("null"))
                            shipment_recieving_date.setTextColor(getResources().getColor(R.color.textcolor));
                    }

                    if (!String.valueOf(company.getText()).equals("") && !String.valueOf(company.getText()).equals("null"))
                        company.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(shipment_id.getText()).equals("") && !String.valueOf(shipment_id.getText()).equals("null"))
                        shipment_id.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(shipment_created_date.getText()).equals("") && !String.valueOf(shipment_created_date.getText()).equals("null"))
                        shipment_created_date.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(shipment_tv_shstatus.getText()).equals("") && !String.valueOf(shipment_tv_shstatus.getText()).equals("null"))
                        shipment_tv_shstatus.setTextColor(getResources().getColor(R.color.textcolor));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

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
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }


    private void shipmentListData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if (!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL1", INVOICE_URL);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, INVOICE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", String.valueOf(response));
                try {

                    Gson gson = new Gson();
                    JSONObject deliveryCorp = response.getJSONObject("deliveryCorp");
                    JSONArray jsonArray = deliveryCorp.getJSONArray("DeliveryNoteDetails");
                    Log.i("responesProduct", jsonArray.toString());

                    Type type = new TypeToken<List<Distributor_ProductModel>>() {
                    }.getType();
                    productShipmentList = gson.fromJson(String.valueOf(jsonArray), type);
                    Log.i("productList", String.valueOf(productShipmentList));

                    productShipmentDetailsAdapter = new ProductDetailsAdapter(getContext(), productShipmentList);
                    product_rv_shipment.setAdapter(productShipmentDetailsAdapter);
                    if (productShipmentList.size() != 0){
                        tv_shipment_no_data.setVisibility(View.GONE);
                    }
                    else
                        tv_shipment_no_data.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

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
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    @Override
    public void onResume() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("tag");
                    fragmentTransaction.add(R.id.main_container, new Shipments_Fragments()).addToBackStack("tag");
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });
        super.onResume();
    }

    private void orderData() {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        Log.i("emthod", "kmkn");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if (!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL", INVOICE_URL);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, INVOICE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", String.valueOf(response));
                try {
                    Gson gson = new Gson();
                    Distributor_ShipmentModel orderModel = gson.fromJson(String.valueOf(response), Distributor_ShipmentModel.class);
                    order_id.setText(orderModel.getOrderNumber());
                    order_company_name.setText(response.getJSONObject("deliveryCorp").getString("CompanyName"));

                    String string = orderModel.getCreatedDate();
                    String[] parts = string.split("T");
                    String Date = parts[0];
                    order_tv_cdate.setText(Date);
                    order_tv_status.setText(orderModel.getOrderStatus());

                    if (orderModel.getOrderStatus().equals("0")) {
                        order_tv_status.setText("Pending");
                    } else if (orderModel.getOrderStatus().equals("1")) {
                        order_tv_status.setText("Approved");
                    } else if (orderModel.getOrderStatus().equals("2")) {
                        order_tv_status.setText("Rejected");
                    } else if (orderModel.getOrderStatus().equals("3")) {
                        order_tv_status.setText("Draft");
                    } else if (orderModel.getOrderStatus().equals("4")) {
                        order_tv_status.setText("Cancelled");
                    }
                    order_tv_shaddress.setText(orderModel.getComments());

                    if (!String.valueOf(order_id.getText()).equals("") && !String.valueOf(order_id.getText()).equals("null"))
                        order_id.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(order_company_name.getText()).equals("") && !String.valueOf(order_company_name.getText()).equals("null"))
                        order_company_name.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(order_tv_cdate.getText()).equals("") && !String.valueOf(order_tv_cdate.getText()).equals("null"))
                        order_tv_cdate.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(order_tv_status.getText()).equals("") && !String.valueOf(order_tv_status.getText()).equals("null"))
                        order_tv_status.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(order_tv_shaddress.getText()).equals("") && !String.valueOf(order_tv_shaddress.getText()).equals("null"))
                        order_tv_shaddress.setTextColor(getResources().getColor(R.color.textcolor));


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

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
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }


    private void InvoiceData() {

        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("Shipment_ID",
                Context.MODE_PRIVATE);
        shipmentID = sharedPreferences3.getString("ShipmentID", "");
        Log.i("shipmentID shared pref", shipmentID);

        Log.i("emthod", "kmkn");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId invoice", DistributorId);
        Log.i("Token invoice", Token);
        if (!INVOICE_URL.contains(shipmentID))
            INVOICE_URL = INVOICE_URL + shipmentID;
        Log.i("INVOICE_URL", INVOICE_URL);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, INVOICE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("Invoice"))
                    try {
                        Log.i("responseInvoice", String.valueOf(response.getJSONObject("Invoice")));
                        Gson gson = new Gson();
                        Distributor_InvoiceModel invoiceModel = gson.fromJson(String.valueOf(response.getJSONObject("Invoice")), Distributor_InvoiceModel.class);
                        txt_paymentID.setText(invoiceModel.getInvoiceNumber());
                        txt_companyName.setText(response.getJSONObject("deliveryCorp").getString("CompanyName"));
                        txt_created_date.setText(invoiceModel.getCreatedDate().split("T")[0]);
                        txt_amount.setText("Rs. "+invoiceModel.getPaidAmount());
                        txt_total_amount.setText("Rs. "+invoiceModel.getTotalPrice());
//                        tv_status.setText(invoiceModel.getStatus());
                        if (invoiceModel.getStatus().equals("0")) {
                            txt_status.setText("Pending");
                        } else if (invoiceModel.getStatus().equals("1")) {
                            txt_status.setText("Unpaid");
                        } else if (invoiceModel.getStatus().equals("2")) {
                            txt_status.setText("Partially Paid");
                        } else if (invoiceModel.getStatus().equals("3")) {
                            txt_status.setText("Paid");
                        } else if (invoiceModel.getStatus().equals("4")) {
                            txt_status.setText("Payment Processing");
                        }


                        if (!String.valueOf(txt_paymentID.getText()).equals("") && !String.valueOf(txt_paymentID.getText()).equals("null"))
                            txt_paymentID.setTextColor(getResources().getColor(R.color.textcolor));
                        if (!String.valueOf(txt_companyName.getText()).equals("") && !String.valueOf(txt_companyName.getText()).equals("null"))
                            txt_companyName.setTextColor(getResources().getColor(R.color.textcolor));
                        if (!String.valueOf(txt_created_date.getText()).equals("") && !String.valueOf(txt_created_date.getText()).equals("null"))
                            txt_created_date.setTextColor(getResources().getColor(R.color.textcolor));
                        if (!String.valueOf(txt_amount.getText()).equals("") && !String.valueOf(txt_amount.getText()).equals("null"))
                            txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
                        if (!String.valueOf(txt_total_amount.getText()).equals("") && !String.valueOf(txt_total_amount.getText()).equals("null"))
                            txt_total_amount.setTextColor(getResources().getColor(R.color.textcolor));
                        if (!String.valueOf(txt_status.getText()).equals("") && !String.valueOf(txt_status.getText()).equals("null"))
                            txt_status.setTextColor(getResources().getColor(R.color.textcolor));

                        layout_txt_created_date.setVisibility(View.VISIBLE);
                        // layout_transaction_date.setVisibility(View.GONE);
                        // layout_txt_bank.setVisibility(View.GONE);
                        // layout_txt_authorization_id.setVisibility(View.GONE);
                        // layout_txt_settlement_id.setVisibility(View.GONE);
                        layout_txt_status.setVisibility(View.VISIBLE);
                        layout_txt_amount.setVisibility(View.VISIBLE);
                        // layout_txt_transaction_charges.setVisibility(View.GONE);
                        layout_txt_total_amount.setVisibility(View.VISIBLE);

//                        if (invoiceModel.getStatus().equals("1")) {
//                            tv_status.setText("Delivered");
//                        } else if (invoiceModel.getStatus().equals("2")) {
//                            tv_status.setText("Received");
//                        } else if (invoiceModel.getStatus().equals("3")) {
//                            tv_status.setText("Returned");
//                        } else if (invoiceModel.getStatus().equals("4")) {
//                            tv_status.setText("Revised");
//                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

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