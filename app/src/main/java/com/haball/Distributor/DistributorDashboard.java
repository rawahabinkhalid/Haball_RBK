package com.haball.Distributor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distribution_Login.Distribution_Login;
import com.haball.Distributor.ui.Distributor_Terms_And_Conditions;
import com.haball.Distributor.ui.Fragment_Notification.FragmentNotification;
import com.haball.Distributor.ui.Fragment_Notification.NotificationAdapter;
import com.haball.Distributor.ui.Fragment_Notification.NotificationModel;
import com.haball.Distributor.ui.Network.Select_Tabs.My_Network_Fragment;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.Distributor.ui.payments.ConsolidatedPaymentsFragment;
import com.haball.Distributor.ui.payments.CreatePaymentRequestFragment;
import com.haball.Distributor.ui.payments.Payments_Fragment;
import com.haball.Distributor.ui.payments.ProofOfPaymentsDashboardFragment;
import com.haball.Distributor.ui.profile.Distributor_Profile;
import com.haball.Distributor.ui.retailer.Payment.RetailerPaymentDashboard;
import com.haball.Distributor.ui.retailer.RetailerFragment;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrderDashboard;
import com.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.haball.Distributor.ui.support.SupportFragment;
import com.haball.Distributor.ui.terms_and_conditions.TermsAndConditionsFragment;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.Retailer_Terms_And_Conditions;
import com.haball.Select_User.Register_Activity;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techatmosphere.expandablenavigation.model.ChildModel;
import com.techatmosphere.expandablenavigation.model.HeaderModel;
import com.techatmosphere.expandablenavigation.view.ExpandableNavigationListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DistributorDashboard extends AppCompatActivity {

    private TextView tv_username, tv_user_company, footer_item_1;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;
    private ExpandableNavigationListView navigationExpandableListView, navigationExpandableListView1;
    private String username, companyname, Token, ID, Name;
    private ImageButton notification_icon;
    private String URL_Notification = "https://175.107.203.97:4013/api/useralert/";
    private boolean doubleBackToExitPressedOnce = false;
    private JSONArray userRights;
    private List<String> NavList = new ArrayList<>();
    private List<String> NavList_Payment = new ArrayList<>();
    private List<String> NavList_Retailer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        Name = sharedPreferences.getString("Name", "");
        companyname = sharedPreferences.getString("CompanyName", "");
        Token = sharedPreferences.getString("Login_Token", "");
        ID = sharedPreferences.getString("ID", "");
        String RetailerCount = sharedPreferences.getString("RetailerCount", "0");

        tv_username = toolbar.findViewById(R.id.tv_username);
        tv_user_company = toolbar.findViewById(R.id.tv_user_company);
        notification_icon = toolbar.findViewById(R.id.notification_icon);

        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new FragmentNotification());
                fragmentTransaction.commit();
            }
        });

        getNotificationCount();

        tv_username.setText("Hi, " + Name);
        tv_user_company.setText(companyname);

        drawer = findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new HomeFragment());
        fragmentTransaction.commit();


        try {
            userRights = new JSONArray(sharedPreferences.getString("UserRights", ""));
            // Log.i("userRights", String.valueOf(userRights));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
//        drawer.setScrimColor(Color.parseColor("#33000000"));
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));

//        new MyAsyncTask().execute();

        boolean Support = false;
        boolean Retailer_Management_Retailers = false;
        boolean Payments_Payment_Request = false;
        boolean PaymentsLedger = false;
        boolean Orders = false;
        boolean Retailer_Payments = false;
        boolean KYB_View = false;
        boolean Retailer_Order = false;
        boolean Shipment = false;
        boolean Invoices = false;
        boolean Profile = false;
        boolean Dashboard = false;
        boolean Company_Preference = false;
        boolean Order_Add_Update = false;
        boolean Delivery_Notes_View = false;
        boolean Invoice_View = false;
        boolean Distributor_Profile = false;
        boolean DashBoardView = false;
        boolean Print_Delivery_Note = false;
        boolean Print_Invoices_Detail = false;
        boolean Order_View = false;
        boolean PrepaidRequestAddUpdate = false;
        boolean PrepaidRequestView = false;
        boolean TransactionsView = false;
        boolean PaymentTermView = false;
        boolean RetailerOrder = false;
        boolean RetailerOrderView = false;
        boolean RetailerOrderAdd_Update = false;
        boolean Retailer = false;
        boolean Retailer_View = false;
        boolean Support_Resolve = false;


        for (int i = 0; i < userRights.length(); i++) {
            try {
                JSONObject userRightsData = new JSONObject(String.valueOf(userRights.get(i)));
                // Log.i("userRights", String.valueOf(userRightsData.get("RightId")));
                if (String.valueOf(userRightsData.get("RightId")).equals("2")) {
                    Company_Preference = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("6")) {
                    Order_Add_Update = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("7")) {
                    Shipment = true;
                    Delivery_Notes_View = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("9")) {
                    Orders = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("10")) {
                    Invoices = true;
                    Invoice_View = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("13")) {
                    Profile = true;
                    Distributor_Profile = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("14")) {
                    KYB_View = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("20")) {
                    Payments_Payment_Request = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("21")) {
                    PaymentsLedger = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("67")) {
                    Dashboard = true;
                    DashBoardView = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("75")) {
                    Print_Delivery_Note = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("80")) {
                    Print_Invoices_Detail = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("82")) {
                    Order_View = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("90")) {
                    PrepaidRequestAddUpdate = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("98")) {
                    PrepaidRequestView = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("99")) {
                    TransactionsView = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("101")) {
                    PaymentTermView = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("121")) {
                    Support = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("130")) {
                    Retailer_Order = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("131")) {
                    RetailerOrderView = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("132")) {
                    RetailerOrderAdd_Update = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("133")) {
                    Retailer_Management_Retailers = true;
                    Retailer_Payments = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("140")) {
                    Retailer = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("141")) {
                    Retailer_View = true;
                }
                if (String.valueOf(userRightsData.get("RightId")).equals("143")) {
                    Support_Resolve = true;
                }

//                // Log.i("userRightsData", String.valueOf(userRights.get(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        SharedPreferences retailerInfo = getSharedPreferences("Distributor_UserRights",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
        retailerInfo_editor.putString(getResources().getString(R.string.support), String.valueOf(Support));
        retailerInfo_editor.putString("Retailer_Management_Retailers", String.valueOf(Retailer_Management_Retailers));
        retailerInfo_editor.putString("Payments_Payment_Request", String.valueOf(Payments_Payment_Request));
        retailerInfo_editor.putString("PaymentsLedger", String.valueOf(PaymentsLedger));
        retailerInfo_editor.putString("Orders", String.valueOf(Orders));
        retailerInfo_editor.putString("Retailer_Payments", String.valueOf(Retailer_Payments));
        retailerInfo_editor.putString("KYB_View", String.valueOf(KYB_View));
        retailerInfo_editor.putString("Retailer_Order", String.valueOf(Retailer_Order));
        retailerInfo_editor.putString("Shipment", String.valueOf(Shipment));
        retailerInfo_editor.putString("Invoices", String.valueOf(Invoices));
        retailerInfo_editor.putString(getResources().getString(R.string.profile), String.valueOf(Profile));
        retailerInfo_editor.putString("Dashboard", String.valueOf(Dashboard));
        retailerInfo_editor.putString("Company_Preference", String.valueOf(Company_Preference));
        retailerInfo_editor.putString("Order_Add_Update", String.valueOf(Order_Add_Update));
        retailerInfo_editor.putString("Delivery_Notes_View", String.valueOf(Delivery_Notes_View));
        retailerInfo_editor.putString("Invoice_View", String.valueOf(Invoice_View));
        retailerInfo_editor.putString("Distributor_Profile", String.valueOf(Distributor_Profile));
        retailerInfo_editor.putString("DashBoardView", String.valueOf(DashBoardView));
        retailerInfo_editor.putString("Print_Delivery_Note", String.valueOf(Print_Delivery_Note));
        retailerInfo_editor.putString("Print_Invoices_Detail", String.valueOf(Print_Invoices_Detail));
        retailerInfo_editor.putString("Order_View", String.valueOf(Order_View));
        retailerInfo_editor.putString("PrepaidRequestAddUpdate", String.valueOf(PrepaidRequestAddUpdate));
        retailerInfo_editor.putString("PrepaidRequestView", String.valueOf(PrepaidRequestView));
        retailerInfo_editor.putString("TransactionsView", String.valueOf(TransactionsView));
        retailerInfo_editor.putString("PaymentTermView", String.valueOf(PaymentTermView));
        retailerInfo_editor.putString("RetailerOrder", String.valueOf(RetailerOrder));
        retailerInfo_editor.putString("RetailerOrderView", String.valueOf(RetailerOrderView));
        retailerInfo_editor.putString("RetailerOrderAdd_Update", String.valueOf(RetailerOrderAdd_Update));
        retailerInfo_editor.putString("Retailer", String.valueOf(Retailer));
        retailerInfo_editor.putString("Retailer_View", String.valueOf(Retailer_View));
        retailerInfo_editor.putString("Support_Resolve", String.valueOf(Support_Resolve));
        retailerInfo_editor.apply();


        toggle.syncState();

        if (Payments_Payment_Request || Orders || Dashboard)
            NavList.add(getResources().getString(R.string.dashboard));
        if (KYB_View)
            NavList.add(getResources().getString(R.string.my_network));
        if (Order_Add_Update)
            NavList.add(getResources().getString(R.string.place_order));
        if (Payments_Payment_Request || PaymentsLedger) {
            NavList.add(getResources().getString(R.string.payment));
            if (Payments_Payment_Request)
                NavList_Payment.add("Payments_Payment_Request");
            if (PaymentsLedger)
                NavList_Payment.add("PaymentsLedger");
        }
        if (Shipment)
            NavList.add(getResources().getString(R.string.shipment));
        if (Retailer_Management_Retailers || Retailer_Payments || Retailer_Order) {
            NavList.add(getResources().getString(R.string.retailer_management));
            if (Retailer_Management_Retailers)
                NavList_Retailer.add("Retailer_Management_Retailers");
            if (Retailer_Payments && !RetailerCount.equals("0"))
                NavList_Retailer.add("Retailer_Payments");
            if (Retailer_Order && !RetailerCount.equals("0"))
                NavList_Retailer.add("Retailer_Order");
        }
        if (Profile)
            NavList.add(getResources().getString(R.string.profile));
        if (Support)
            NavList.add(getResources().getString(R.string.support));
//        if (Retailer_Management_Retailers)
//            NavList.add("Retailer");
//        if (Payments_Payment_Request)
//            NavList.add("Make Payment");
//        if (PaymentsLedger)
//            NavList.add("Payment Ledger");
//        if (Retailer_Payments)
//            NavList.add("Retailer Payments");
//        if (Retailer_Order)
//            NavList.add("Order on Behalf");

        NavList.add(getResources().getString(R.string.logout));


        navigationExpandableListView = findViewById(R.id.expandable_navigation);
        footer_item_1 = findViewById(R.id.footer_item_1);
        footer_item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container, new TermsAndConditionsFragment());
//                fragmentTransaction.commit();
//                Intent login_intent = new Intent(RetailorDashboard.this, Retailer_Terms_And_Conditions.class);
//                startActivity(login_intent);
//                finish();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new Distributor_Terms_And_Conditions()).addToBackStack("tag");
                fragmentTransaction.commit();

                drawer.closeDrawer(GravityCompat.START);

            }
        });
        navigationExpandableListView.init(this);
        if (Payments_Payment_Request || Orders || Dashboard)
            navigationExpandableListView.addHeaderModel(new HeaderModel(getResources().getString(R.string.dashboard)));
        if (KYB_View)
            navigationExpandableListView.addHeaderModel(new HeaderModel(getResources().getString(R.string.my_network)));
        if (Order_Add_Update)
            navigationExpandableListView.addHeaderModel(new HeaderModel(getResources().getString(R.string.place_order)));
        if (Payments_Payment_Request || PaymentsLedger) {
            HeaderModel headerModel = new HeaderModel(getResources().getString(R.string.payment));
            if (Payments_Payment_Request)
                headerModel.addChildModel(new ChildModel(getResources().getString(R.string.make_payment_distri)));
            if (PaymentsLedger)
                headerModel.addChildModel(new ChildModel(getResources().getString(R.string.payment_ledger)));
            navigationExpandableListView.addHeaderModel(headerModel);
        }
        if (Shipment)
            navigationExpandableListView.addHeaderModel(new HeaderModel(getResources().getString(R.string.shipment)));
        if (Retailer_Management_Retailers || Retailer_Payments || Retailer_Order) {
            HeaderModel headerModel = new HeaderModel(getResources().getString(R.string.retailer_management));
            if (Retailer_Management_Retailers)
                headerModel.addChildModel(new ChildModel(getResources().getString(R.string.retailer_distri)));
            if (Retailer_Order && !RetailerCount.equals("0"))
                headerModel.addChildModel(new ChildModel(getResources().getString(R.string.order_behalf)));
            if (Retailer_Payments && !RetailerCount.equals("0"))
                headerModel.addChildModel(new ChildModel(getResources().getString(R.string.retailer_payment_distri)));
            navigationExpandableListView.addHeaderModel(headerModel);
        }
        if (Profile)
            navigationExpandableListView.addHeaderModel(new HeaderModel(getResources().getString(R.string.profile)));
        if (Support)
            navigationExpandableListView.addHeaderModel(new HeaderModel(getResources().getString(R.string.support)));
        navigationExpandableListView.addHeaderModel(new HeaderModel(getResources().getString(R.string.logout)));


        navigationExpandableListView.build()
                .addOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition);

                        if (NavList.contains(getResources().getString(R.string.dashboard)) && NavList.indexOf(getResources().getString(R.string.dashboard)) == id) {
                            // Log.i("Dashboard", "Dashboard Activity"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new HomeFragment());
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains(getResources().getString(R.string.my_network)) && NavList.indexOf(getResources().getString(R.string.my_network)) == id) {
                            // Log.i(getResources().getString(R.string.my_network), "My Network Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.add(R.id.main_container_ret, new My_NetworkDashboard());
                            fragmentTransaction.add(R.id.main_container, new My_Network_Fragment()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains(getResources().getString(R.string.place_order)) && NavList.indexOf(getResources().getString(R.string.place_order)) == id) {
                            // Log.i(getResources().getString(R.string.place_order), "Orders Activity");
//                            getSupportFragmentManager().popBackStack("tag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new Order_PlaceOrder()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains(getResources().getString(R.string.payment)) && NavList.indexOf(getResources().getString(R.string.payment)) == id) {
                            // Log.i("Payments", "Payments Activity");//DONE
                            navigationView.setItemTextColor(ColorStateList.valueOf(Color.RED));
                        } else if (NavList.contains(getResources().getString(R.string.shipment)) && NavList.indexOf(getResources().getString(R.string.shipment)) == id) {
                            // Log.i("Shipment", "Shipment Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new Shipments_Fragments()).addToBackStack("tag");
                            fragmentTransaction.commit();


                            //jsonObject.put("CompanyName", null);
//                            fragmentTransaction.commit();
                            //jsonObject.put("CompanyName", null);
//        jsonObject.put("CreateDateFrom", null);
//        jsonObject.put("CreateDateTo", null);
//        jsonObject.put("Status", null);
//        jsonObject.put("AmountMin", null);
//        jsonObject.put("AmountMax", null);
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains(getResources().getString(R.string.retailer_management)) && NavList.indexOf(getResources().getString(R.string.retailer_management)) == id) {
                            // Log.i("Retailer", "Retailer Activity");


                        } else if (NavList.contains(getResources().getString(R.string.profile)) && NavList.indexOf(getResources().getString(R.string.profile)) == id) {
                            // Log.i(getResources().getString(R.string.profile), "Profile Activity");
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new Distributor_Profile()).addToBackStack("tag");
                            ;
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains(getResources().getString(R.string.support)) && NavList.indexOf(getResources().getString(R.string.support)) == id) {
                            // Log.i("Suppport", "Support Activity"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new SupportFragment()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                        } else if (NavList.contains(getResources().getString(R.string.logout)) && NavList.indexOf(getResources().getString(R.string.logout)) == id) {
                            // Log.i(getResources().getString(R.string.logout), "Logout Activity");
                            if (Token != null) {
                                //                                Intent login = new Intent(DistributorDashboard.this, Distribution_Login.class);
//                                startActivity(login);
//                                finish();


                                final AlertDialog alertDialog = new AlertDialog.Builder(DistributorDashboard.this).create();
                                LayoutInflater inflater = LayoutInflater.from(DistributorDashboard.this);
                                View view_popup = inflater.inflate(R.layout.discard_changes, null);
                                TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
                                tv_discard.setText(getResources().getString(R.string.logout));
                                TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
                                tv_discard_txt.setText(getResources().getString(R.string.logout_msg));
                                alertDialog.setView(view_popup);
                                alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                                layoutParams.y = 200;
                                layoutParams.x = -70;// top margin
                                alertDialog.getWindow().setAttributes(layoutParams);
                                Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
                                btn_discard.setText(getResources().getString(R.string.logout));
                                btn_discard.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        alertDialog.dismiss();

                                        SharedPreferences login_token = getSharedPreferences("LoginToken",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = login_token.edit();
                                        editor.remove("Login_Token");
                                        editor.commit();

                                        Intent login = new Intent(DistributorDashboard.this, Distribution_Login.class);
                                        startActivity(login);
                                        finish();
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
                            drawer.closeDrawer(GravityCompat.START);
//                        } else if (id == 8) {
//                            // Log.i("terms and conditions", "terms and conditions");
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.add(R.id.main_container, new TermsAndConditionsFragment());
//                            fragmentTransaction.commit();
//
//                            drawer.closeDrawer(GravityCompat.START);
                        }

                        return false;
                    }
                })
                .addOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        navigationExpandableListView.setSelected(groupPosition, childPosition);

////
////                        if (groupPosition == 2 && childPosition == 0) {
////                            // Log.i("Consolidate Payments", "Child");//DONE
////                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                            fragmentTransaction.add(R.id.main_container, new ConsolidatedPaymentsFragment()).addToBackStack("tag");
////                            ;
////                            fragmentTransaction.commit();
////                            drawer.closeDrawer(GravityCompat.START);
////                        }
//                        if (groupPosition == 3 && childPosition == 0) {
                        if (NavList.contains(getResources().getString(R.string.payment)) && NavList.indexOf(getResources().getString(R.string.payment)) == groupPosition && NavList_Payment.contains("Payments_Payment_Request") && NavList_Payment.indexOf("Payments_Payment_Request") == childPosition) {
                            // Log.i("Make Payment", "Child");//DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new CreatePaymentRequestFragment()).addToBackStack(null);
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
//                        } else if (groupPosition == 3 && childPosition == 1) {
                        } else if (NavList.contains(getResources().getString(R.string.payment)) && NavList.indexOf(getResources().getString(R.string.payment)) == groupPosition && NavList_Payment.contains("PaymentsLedger") && NavList_Payment.indexOf("PaymentsLedger") == childPosition) {
                            // Log.i("Payment Ledger", "Child"); //DONE
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new Payments_Fragment()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
////                        }
//////                        else if (groupPosition == 2 && childPosition == 3) {
//////                            // Log.i("Proof of Payments", "Child"); //DONE
//////                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//////                            fragmentTransaction.add(R.id.main_container, new ProofOfPaymentsDashboardFragment()).addToBackStack("tag");
//////                            fragmentTransaction.commit();
//////                            drawer.closeDrawer(GravityCompat.START);
//////                        }
////                        else if (groupPosition == 2 && childPosition == 0) {
////                            // Log.i(getResources().getString(R.string.place_order), "Child"); //DONE
////                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                            fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder()).addToBackStack("tag");
////                            fragmentTransaction.commit();
////                            drawer.closeDrawer(GravityCompat.START);
//                        } else if (groupPosition == 5 && childPosition == 0) {
//                            Toast.makeText(DistributorDashboard.this, "retialer Managment", Toast.LENGTH_SHORT).show();
                        } else if (NavList.contains(getResources().getString(R.string.retailer_management)) && NavList.indexOf(getResources().getString(R.string.retailer_management)) == groupPosition && NavList_Retailer.contains("Retailer_Management_Retailers") && NavList_Retailer.indexOf("Retailer_Management_Retailers") == childPosition) {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new RetailerFragment()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
//                        } else if (groupPosition == 5 && childPosition == 1) {
                        } else if (NavList.contains(getResources().getString(R.string.retailer_management)) && NavList.indexOf(getResources().getString(R.string.retailer_management)) == groupPosition && NavList_Retailer.contains("Retailer_Payments") && NavList_Retailer.indexOf("Retailer_Payments") == childPosition) {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_container, new RetailerOrderDashboard()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
//                        } else if (groupPosition == 5 && childPosition == 2) {
                        } else if (NavList.contains(getResources().getString(R.string.retailer_management)) && NavList.indexOf(getResources().getString(R.string.retailer_management)) == groupPosition && NavList_Retailer.contains("Retailer_Order") && NavList_Retailer.indexOf("Retailer_Order") == childPosition) {
//                            Toast.makeText(DistributorDashboard.this, "Retailer Payment", Toast.LENGTH_SHORT).show();
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new RetailerPaymentDashboard()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            drawer.closeDrawer(GravityCompat.START);
                            drawer.closeDrawer(GravityCompat.START);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        return false;
                    }
                });
        navigationExpandableListView.expandGroup(1);

    }

    private void getNotificationCount() {
        if (!URL_Notification.contains("/" + ID))
            URL_Notification = URL_Notification + ID;
        // Log.i("URL_NOTIFICATION", URL_Notification);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_Notification, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {

                    int count = Integer.parseInt(String.valueOf(result.get("count")));
                    // Log.i("DistributorDashboard", String.valueOf(count));
                    if (count == 0) {
                        notification_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_notifications_black_24dp));
                    } else {
                        notification_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                printErrorMessage(error);
                error.printStackTrace();
                new HaballError().printErrorMessage(DistributorDashboard.this, error);
                new ProcessingError().showError(DistributorDashboard.this);
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
        Volley.newRequestQueue(DistributorDashboard.this).add(sr);
    }

//    private void logoutUser() {
//
//        final AlertDialog alertDialog = new AlertDialog.Builder(DistributorDashboard.this).create();
//        LayoutInflater inflater = LayoutInflater.from(DistributorDashboard.this);
//        View view_popup = inflater.inflate(R.layout.discard_changes, null);
//        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
//        tv_discard.setText(getResources().getString(R.string.logout));
//        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
//        tv_discard_txt.setText("Are you sure, you want to logout?");
//        alertDialog.setView(view_popup);
//        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
//        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
//        layoutParams.y = 200;
//        layoutParams.x = -70;// top margin
//        alertDialog.getWindow().setAttributes(layoutParams);
//        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
//        btn_discard.setText(getResources().getString(R.string.logout));
//        btn_discard.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                alertDialog.dismiss();
//
//                SharedPreferences login_token = getSharedPreferences("LoginToken",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = login_token.edit();
//                editor.remove("Login_Token");
//                editor.commit();
//
//                Intent login = new Intent(DistributorDashboard.this, Distribution_Login.class);
//                startActivity(login);
//                finish();
//            }
//        });
//
//        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
//        img_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog.show();
//    }

//
//    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                Thread.sleep(2500);
//                getNotificationCount();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            new MyAsyncTask().execute();
//        }
//    }

//    @Override
//    public void onBackPressed() {
////        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//////            super.onBackPressed();
////        if (drawer.isDrawerOpen(Gravity.LEFT)) {
////            drawer.closeDrawer(Gravity.LEFT);
////        } else {
////            FragmentManager fm = getSupportFragmentManager();
////            if (fm.getBackStackEntryCount() == 0) {
////                if (doubleBackToExitPressedOnce) {
////                    super.onBackPressed();
////                    finishAffinity();
////                    return;
////                }
////                this.doubleBackToExitPressedOnce = true;
////                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        doubleBackToExitPressedOnce = false;
////                    }
////                }, 1500);
////            } else {
//////            super.onBackPressed();
////                fm.popBackStack();
////            }
////
////        }
//
//        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////            super.onBackPressed();
//        if (drawer.isDrawerOpen(Gravity.LEFT)) {
//            drawer.closeDrawer(Gravity.LEFT);
//        } else {
////            FragmentManager fm = getSupportFragmentManager();
////            if (fm.getBackStackEntryCount() == 0) {
//            if (doubleBackToExitPressedOnce) {
////                    super.onBackPressed();
////                    finishAffinity();
//                logoutUser();
//
//                return;
//            }
//            this.doubleBackToExitPressedOnce = true;
//           // Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce = false;
//                }
//            }, 1500);
////            } else {
//////            super.onBackPressed();
////                fm.popBackStack();
////            }
//        }
//    }

    private void printErrMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(DistributorDashboard.this, "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(DistributorDashboard.this, "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(DistributorDashboard.this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(DistributorDashboard.this, "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(DistributorDashboard.this, "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(DistributorDashboard.this, "Timeout Error !", Toast.LENGTH_LONG).show();
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
                Toast.makeText(DistributorDashboard.this, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}