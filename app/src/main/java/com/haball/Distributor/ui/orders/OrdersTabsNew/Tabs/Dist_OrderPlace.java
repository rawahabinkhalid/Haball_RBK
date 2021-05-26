package com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haball.CustomToast;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters.ParentList_Adapter_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderParentlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Tabs.Order_Summary;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.NonSwipeableViewPager;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Place_Order.ui.main.Tabs.Retailer_OrderPlace_retailer_dashboarad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dist_OrderPlace extends Fragment {

    RecyclerView recyclerView, subchlid_RV;
    private List<OrderParentlist_Model_DistOrder> titles = new ArrayList<>();
    private List<OrderChildlist_Model_DistOrder> productList = new ArrayList<>();
    //    private List<SimpleParent> parentObjects = new ArrayList<>();
    private String URL_DISTRIBUTOR_DASHBOARD = "https://175.107.203.97:4013/api/dashboard/ReadDistributorDashboard";
    private String URL_PRODUCT_CATEGORY = "https://175.107.203.97:4013/api/products/ReadCategories/0/";
    private String URL_PRODUCT = "https://175.107.203.97:4013/api/products/ReadProductsByCategories/0/";
    private String Token, DistributorId;
    private String object_string, object_stringqty;
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    private List<OrderChildlist_Model_DistOrder> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private float grossAmount = 0;
    private Button btn_checkout, close_order_button;
    private String CompanyId;
    private Spinner spinner_conso;
    private RelativeLayout spinner_container_main;
    //    private List<OrderParentlist_Model_DistOrder> totalCategory = new ArrayList<>();
    private List<String> totalCategoryTitle = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterSpinnerConso;
    private String Category_selected = "";
    private HashMap<String, String> Categories = new HashMap<>();
    private TextInputEditText et_test;
    List<OrderParentlist_Model_DistOrder> temp_titles = new ArrayList<>();
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private int lastExpandedPosition = -1;
    private Typeface myFont;
    private Loader loader;
    private View myview = null;
    //    private MyAsyncTask myAsyncTask;
    private String editTextValue = "";
    String current_balance;
    private int selected_category_index = 0;
    String byDefaultStatus = "true";
    private String fromDraft = "", fromAddMore = "";
    private boolean isKeyboardShowing = false;

    public Dist_OrderPlace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_dist_main_placeorder, container, false);
        myview = view;
        btn_checkout = view.findViewById(R.id.btn_checkout);
        close_order_button = view.findViewById(R.id.close_button);
        recyclerView = view.findViewById(R.id.rv_order_list);
        spinner_container_main = view.findViewById(R.id.spinner_container_main);
//        subchlid_RV = view.findViewById(R.id.subchlid_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        spinner_conso = view.findViewById(R.id.spinner_conso);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        et_test = view.findViewById(R.id.et_test);


        final NonSwipeableViewPager viewPager = ((FragmentActivity) getContext()).findViewById(R.id.view_pager5);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPager.setCurrentItem(0, false);
            }
        });
        viewPager.setCurrentItem(0);

        arrayAdapterSpinnerConso = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, totalCategoryTitle) {
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
//
//        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = selectedProducts.edit();
//        editor.putString("selected_products", "");
//        editor.putString("selected_products_qty", "");
//        editor.apply();
        SharedPreferences add_more_product = getContext().getSharedPreferences("add_more_product",
                Context.MODE_PRIVATE);
        fromAddMore = add_more_product.getString("add_more_product", "");
        if (!fromAddMore.equals("fromAddMore")) {
            // Log.i("debugOrder_AddMore", "not from add more product");
            SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", "");
            editor.putString("selected_products_qty", "");
            editor.apply();
        }

//        fetchDashboardData();
        loader = new Loader(getContext());
//
//        close_order_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container, new HomeFragment());
//                fragmentTransaction.commit();
//
//            }
//        });
        ((FragmentActivity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        close_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                        Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String orderCheckedOutStr = orderCheckout.getString("orderCheckout", "");

                List<OrderChildlist_Model_DistOrder> selectedProductsDataList_temp = new ArrayList<>();
                List<String> selectedProductsQuantityList_temp = new ArrayList<>();

                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                        Context.MODE_PRIVATE);
                object_stringqty = selectedProducts.getString("selected_products_qty", "");
                object_string = selectedProducts.getString("selected_products", "");
                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                }.getType();
                Type typeString = new TypeToken<List<String>>() {
                }.getType();
                if (!object_string.equals("") && !object_stringqty.equals("")) {
                    selectedProductsDataList_temp = gson.fromJson(object_string, type);
                    selectedProductsQuantityList_temp = gson.fromJson(object_stringqty, typeString);
                }

                // Log.i("debug_order_back_pres", String.valueOf(selectedProductsDataList_temp));
                // Log.i("debug_order_back_pres", String.valueOf(selectedProductsDataList_temp.size()));
                // Log.i("debug_order_back_pres", String.valueOf(orderCheckedOutStr));

                final SharedPreferences orderCheckout_SP = getContext().getSharedPreferences("orderCheckout_discard",
                        Context.MODE_PRIVATE);
                orderCheckedOutStr = orderCheckout_SP.getString("orderCheckout", "");

                SharedPreferences selectedProductsSP = getContext().getSharedPreferences("FromDraft_Temp",
                        Context.MODE_PRIVATE);
                int quantity = 0;
                if (selectedProductsQuantityList_temp != null && selectedProductsQuantityList_temp.size() > 0)
                    for (int i = 0; i < selectedProductsQuantityList_temp.size(); i++) {
                        quantity += Integer.parseInt(selectedProductsQuantityList_temp.get(i));
                    }
                executeBackStackFlow(selectedProductsSP, orderCheckedOutStr, quantity, selectedProductsDataList_temp, selectedProductsQuantityList_temp);

//
////                if (selectedProductsDataList_temp != null && selectedProductsDataList_temp.size() > 0 && (!orderCheckedOutStr.equals(""))) {
////                    showDiscardDialog();
//                if (selectedProductsDataList_temp != null && selectedProductsDataList_temp.size() > 0 && quantity > 0 && (!orderCheckedOutStr.equals("") || (selectedProductsSP.getString("fromDraft", "").equals("draft")) && selectedProductsSP.getString("fromDraftChanged", "").equals("changed"))) {
////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("orderCheckout") || orderCheckedOut.equals("orderCheckout123"))) {
//                    showDiscardDialog();
//                } else if ((selectedProductsDataList_temp == null || selectedProductsDataList_temp.size() == 0 || quantity == 0) && (selectedProductsSP.getString("fromDraft", "").equals("draft"))) {
////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("orderCheckout") || orderCheckedOut.equals("orderCheckout123"))) {
//                    showDiscardDialog();
//                } else {
//
//                    InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);
//
//                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
////                    fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
//                    if (!selectedProductsSP.getString("fromDraft", "").equals("draft"))
//                        fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder()).addToBackStack("null");
//                    else
//                        fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
//                    fragmentTransaction.commit();
//                }
            }
        });

        SharedPreferences selectedProductsSP = getContext().getSharedPreferences("FromDraft",
                Context.MODE_PRIVATE);
        fromDraft = selectedProductsSP.getString("fromDraft", "");
        if (fromDraft.equals("draft")) {
            SharedPreferences orderCheckout1 = getContext().getSharedPreferences("FromDraft",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor orderCheckout_editor1 = orderCheckout1.edit();
            orderCheckout_editor1.putString("fromDraft", "");
            orderCheckout_editor1.apply();
        }

        SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        String CategoryIndex = companyInfo.getString("CategoryIndex", "0");
        selected_category_index = Integer.parseInt(CategoryIndex);


        arrayAdapterSpinnerConso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_conso.setAdapter(arrayAdapterSpinnerConso);
        spinner_conso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                Category_selected = totalCategoryTitle.get(position);
//
//                try {
//                    ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
//                    ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
//                    ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
//                    // Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    getFilteredProductCategory(Categories.get(Category_selected));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


                final SharedPreferences orderCheckout_SP = getContext().getSharedPreferences("orderCheckout",
                        Context.MODE_PRIVATE);
                final String orderCheckedOut = orderCheckout_SP.getString("orderCheckout", "");

                // Log.i("orderCheck_debug_Check", orderCheckedOut + "''");
                // Log.i("orderCheck_debug_Status", byDefaultStatus + "''");

                SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
                        Context.MODE_PRIVATE);
                String CategoryIndex = companyInfo.getString("CategoryIndex", "0");
                selected_category_index = Integer.parseInt(CategoryIndex);

                // Log.i("orderCheck_debug_Cat", CategoryIndex + "''");

                // Log.i("orderCheck_debug", Categories.get(Category_selected) + " - " + Category_selected);
                // Log.i("orderCheck_debug_Draft", fromDraft + "'''");

                if (fromDraft.equals("draft")) {
                    spinner_conso.setSelection(Integer.parseInt(CategoryIndex));
                    fromDraft = "";
                    return;
                }

                if (fromAddMore.equals("fromAddMore")) {
                    fromAddMore = "";
                    byDefaultStatus = "false";

                }

                if (orderCheckedOut.equals("orderCheckout")) {
                    // Log.i("orderCheck_debug", "in orderCheckout");

                    spinner_conso.setSelection(Integer.parseInt(CategoryIndex));

                    SharedPreferences.Editor orderCheckout_editor = orderCheckout_SP.edit();
                    orderCheckout_editor.putString("orderCheckout", "orderCheckout123");
                    orderCheckout_editor.apply();

                    try {
                        ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
                        ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    try {
                        getFilteredProductCategory(Categories.get(Category_selected));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    byDefaultStatus = "true";
                } else {
                    if (byDefaultStatus.equals("false")) {
                        // Log.i("orderCheck_debug", "in false of bydefault");


                        loader.showLoader();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loader.hideLoader();
                                        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                                                Context.MODE_PRIVATE);
                                        Gson gson = new Gson();
                                        object_stringqty = selectedProducts.getString("selected_products_qty", "");
                                        object_string = selectedProducts.getString("selected_products", "");
                                        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                                        }.getType();
                                        Type typeString = new TypeToken<List<String>>() {
                                        }.getType();
                                        int quantity = 0;
                                        if (!object_string.equals("") && !object_stringqty.equals("")) {
                                            selectedProductsDataList = gson.fromJson(object_string, type);
                                            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
                                            for (int i = 0; i < selectedProductsQuantityList.size(); i++) {
                                                quantity += Integer.parseInt(selectedProductsQuantityList.get(i));
                                            }
                                        }

                                        // Log.i("orderCheck_debug_size()", selectedProductsDataList.size() + "''");
                                        // Log.i("orderCheck_debug_qty", quantity + "''");

                                        if (quantity > 0 && (orderCheckedOut.equals("") || (orderCheckedOut.equals("orderCheckout123")))) {
                                            // Log.i("orderCheck_debug", "Cross_Category");
                                            spinner_conso.setSelection(selected_category_index);
//                                            byDefaultStatus = "";
                                            new CustomToast().showToast(((FragmentActivity) getContext()), "Cross-Category Product selection is not allowed.");
                                        } else {
                                            // Log.i("orderCheck_debug", "NOT IN Cross_Category");
                                            fromDraft = "";
                                            selected_category_index = position;
                                            SharedPreferences companyInfo = getContext().getSharedPreferences("CompanyInfo",
                                                    Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = companyInfo.edit();
                                            editor.putString("CategoryIndex", String.valueOf(selected_category_index));
                                            editor.apply();
                                            try {
                                                ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
                                                ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
                                                ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
                                                // Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                getFilteredProductCategory(Categories.get(Category_selected));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

//                                            if (orderCheckedOut.equals("orderCheckout")) {
                                            SharedPreferences.Editor orderCheckout_editor = orderCheckout_SP.edit();
                                            orderCheckout_editor.putString("orderCheckout", "orderCheckout123");
                                            orderCheckout_editor.apply();
//                                            }
                                        }
                                    }
                                }, 3000);
                            }
                        });


                    } else if (byDefaultStatus.equals("true")) {
                        // Log.i("orderCheck_debug", "in true of bydefault");
                        byDefaultStatus = "false";
                        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                                Context.MODE_PRIVATE);
                        Gson gson = new Gson();
                        object_stringqty = selectedProducts.getString("selected_products_qty", "");
                        object_string = selectedProducts.getString("selected_products", "");
                        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                        }.getType();
                        Type typeString = new TypeToken<List<String>>() {
                        }.getType();
                        int quantity = 0;
                        if (!object_string.equals("") && !object_stringqty.equals("")) {
                            selectedProductsDataList = gson.fromJson(object_string, type);
                            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
                            for (int i = 0; i < selectedProductsQuantityList.size(); i++) {
                                quantity += Integer.parseInt(selectedProductsQuantityList.get(i));
                            }
                        }

                        // Log.i("orderCheck_debug_size()", selectedProductsDataList.size() + "''");
                        // Log.i("orderCheck_debug_qty", quantity + "''");

                        if (quantity > 0 && (orderCheckedOut.equals("") || (orderCheckedOut.equals("orderCheckout123")))) {
//                        if (quantity > 0 && (orderCheckedOut.equals("") || (orderCheckedOut.equals("orderCheckout123"))) && !fromDraft.equals("draft")) {
                            // Log.i("orderCheck_debug", "Cross_Category");
                            spinner_conso.setSelection(selected_category_index);
                            byDefaultStatus = "";
                            new CustomToast().showToast(((FragmentActivity) getContext()), "Cross-Category Product selection is not allowed.");
                        } else {
                            // Log.i("orderCheck_debug", "NOT IN Cross_Category");
                            fromDraft = "";
                            selected_category_index = position;

                            SharedPreferences.Editor editor = companyInfo.edit();
                            editor.putString("CategoryIndex", String.valueOf(selected_category_index));
                            editor.apply();
                            try {
                                ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
                                ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
                                ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
                                // Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            try {
                                getFilteredProductCategory(Categories.get(Category_selected));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            SharedPreferences.Editor orderCheckout_editor = orderCheckout_SP.edit();
                            orderCheckout_editor.putString("orderCheckout", "orderCheckout123");
                            orderCheckout_editor.apply();
                        }
                    } else {
                        // Log.i("orderCheck_debug", "in else of bydefault");
                        byDefaultStatus = "false";
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        et_test.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                titles = new ArrayList<>();
//                if (!String.valueOf(s).equals("")) {
//                    // Log.i("titles123", "in if");
//                    try {
//                        getFilteredProductsFromCategory(String.valueOf(s));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    // Log.i("titles123", "in else");
//                    try {
//                        getProductCategory();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

//        et_test.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Log.i("DebugFilter", "in edit text: " + s);
//                // Log.i("DebugFilter", "in edit text: C, " + Category_selected);
//                editTextValue = String.valueOf(s);
//
////                titles = new ArrayList<>();
//                if (!String.valueOf(s).equals("")) {
//                    spinner_conso.setSelection(0);
//                    // Log.i("titles123", "in if");
//                    try {
//                        getFilteredProductsFromCategory(String.valueOf(s));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    // Log.i("titles123", "in else");
//                    if (Category_selected != null && Category_selected.equals("All Category")) {
//                        try {
//                            getProductCategory();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });


        et_test.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((FragmentActivity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (!hasFocus) {
                    if (spinner_container_main.getVisibility() == View.GONE) {

                        spinner_container_main.setVisibility(View.VISIBLE);
                        TranslateAnimation animate1 = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                -spinner_container_main.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate1.setDuration(250);
                        animate1.setFillAfter(true);
                        spinner_container_main.clearAnimation();
                        spinner_container_main.startAnimation(animate1);
                    }


                    String s = String.valueOf(et_test.getText());
                    // Log.i("DebugFilter", "in edit text: " + s);
                    // Log.i("DebugFilter", "in edit text: C, " + Category_selected);
                    editTextValue = String.valueOf(s);

//                titles = new ArrayList<>();
                    if (!String.valueOf(s).equals("")) {
                        spinner_conso.setSelection(0);
                        // Log.i("titles123", "in if");
                        try {
                            getFilteredProductsFromCategory(String.valueOf(s));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Log.i("titles123", "in else");
                        try {
                            getFilteredProductCategory(Categories.get(Category_selected));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
//        et_test.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                String Filter_selected_value = String.valueOf(et_test.getText());
//                if (!Filter_selected_value.equals("")) {
//                    try {
//                        getFilteredProductsFromCategory(String.valueOf(Filter_selected_value));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        getProductCategory();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollEvent = new ArrayList<>();

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                y = dy;
                if (dy <= -5) {
                    scrollEvent.add("ScrollDown");
//                            // Log.i("scrolling", "Scroll Down");
                } else if (dy > 5) {
                    scrollEvent.add("ScrollUp");
//                            // Log.i("scrolling", "Scroll Up");
                }
                String scroll = getScrollEvent();

                if (scroll.equals("ScrollDown")) {
                    if (spinner_container_main.getVisibility() == View.GONE) {

                        spinner_container_main.setVisibility(View.VISIBLE);
                        TranslateAnimation animate1 = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                -spinner_container_main.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate1.setDuration(250);
                        animate1.setFillAfter(true);
                        spinner_container_main.clearAnimation();
                        spinner_container_main.startAnimation(animate1);
                    }
                } else if (scroll.equals("ScrollUp")) {
                    y = 0;
                    if (spinner_container_main.getVisibility() == View.VISIBLE) {
//                                line_bottom.setVisibility(View.INVISIBLE);
                        //spinner_container_main.setVisibility(View.VISIBLE);
                        TranslateAnimation animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                0,  // fromYDelta
                                -spinner_container_main.getHeight()); // toYDelta
                        animate.setDuration(100);
                        animate.setFillAfter(true);
                        spinner_container_main.clearAnimation();
                        spinner_container_main.startAnimation(animate);
                        spinner_container_main.setVisibility(View.GONE);
                    }
                }

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
//                    if (totalPages != 0 && pageNumber < totalPages) {
////                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
//                    }
            }

        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.showLoader();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loader.hideLoader();
                                //Do something after 1 second

                                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
                                orderCheckout_editor.putString("orderCheckout", "orderCheckout");
                                orderCheckout_editor.apply();
                                final NonSwipeableViewPager viewPager = ((FragmentActivity) getContext()).findViewById(R.id.view_pager5);
                                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                                        Context.MODE_PRIVATE);
                                Gson gson = new Gson();
                                object_stringqty = selectedProducts.getString("selected_products_qty", "");
                                object_string = selectedProducts.getString("selected_products", "");
                                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                                }.getType();
                                Type typeString = new TypeToken<List<String>>() {
                                }.getType();
                                selectedProductsDataList = gson.fromJson(object_string, type);
                                selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//                        if (selectedProductsDataList.size() > 0) {
//                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                                // Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                                // Log.i("qty", selectedProductsQuantityList.get(i));
//                                if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
//                            }
                                if (selectedProductsDataList.size() > 0) {
                                    for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                                // Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                                // Log.i("qty", selectedProductsQuantityList.get(i));
                                        if (selectedProductsQuantityList.get(i).equals("") || Integer.parseInt(selectedProductsQuantityList.get(i)) == 0) {
                                            selectedProductsDataList.remove(i);
                                            selectedProductsQuantityList.remove(i);

                                            String json = gson.toJson(selectedProductsDataList);
                                            String jsonqty = gson.toJson(selectedProductsQuantityList);
                                            // Log.i("jsonqty", jsonqty);
                                            // Log.i("json", json);

                                            SharedPreferences selectedProducts_zero = getContext().getSharedPreferences("selectedProducts_distributor",
                                                    Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = selectedProducts_zero.edit();
                                            editor.putString("selected_products", json);
                                            editor.putString("selected_products_qty", jsonqty);
                                            editor.apply();
                                        }

                                        if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                            grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                                    }
                                    SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = grossamount.edit();
                                    editor.putString("grossamount", String.valueOf(grossAmount));
                                    editor.apply();
//                            Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
                                    grossAmount = 0;
                                    viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                        @Override
                                        public void onGlobalLayout() {
                                            viewPager.setCurrentItem(1, false);
                                        }
                                    });
                                    InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);


                                    FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container, new Dist_Order_Summary());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            }
                        }, 3000);
                    }
                });
            }
        });

        try {
            getProductCategory();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // ContentView is the root view of the layout of this activity/fragment
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        view.getWindowVisibleDisplayFrame(r);
                        int screenHeight = view.getRootView().getHeight();

                        // r.bottom is the position above soft keypad or device button.
                        // if keypad is shown, the r.bottom is smaller than that before.
                        int keypadHeight = screenHeight - r.bottom;

                        // Log.d("order_debugKey_KeyHeig", "keypadHeight = " + keypadHeight);

                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            // keyboard is opened
                            if (!isKeyboardShowing) {
                                isKeyboardShowing = true;
                                onKeyboardVisibilityChanged(true);
                            }
                        } else {
                            // keyboard is closed
                            if (isKeyboardShowing) {
                                isKeyboardShowing = false;
                                onKeyboardVisibilityChanged(false);
                            }
                        }
                    }
                });

        return view;

    }

    private void onKeyboardVisibilityChanged(boolean opened) {
        // Log.i("order_debugKey_OpenClos", "keyboard " + opened);
        if (!opened) {
            spinner_container_main.setVisibility(View.VISIBLE);
            TranslateAnimation animate1 = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    -spinner_container_main.getHeight(),  // fromYDelta
                    0);                // toYDelta
            animate1.setDuration(250);
            animate1.setFillAfter(true);
            spinner_container_main.clearAnimation();
            spinner_container_main.startAnimation(animate1);
        }
    }

    private void showDiscardDialog() {
        // Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText(R.string.discard_text);
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();

                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
                orderCheckout_editor.putString("orderCheckout", "");
                orderCheckout_editor.apply();

                InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
                fragmentTransaction.commit();


//                Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                ((FragmentActivity) getContext()).startActivity(login_intent);
//                ((FragmentActivity) getContext()).finish();

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
        if(!alertDialog.isShowing())
        alertDialog.show();
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
//
//                    if (selectedProductsDataList == null || selectedProductsDataList.size() == 0) {
                    SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout_discard",
                            Context.MODE_PRIVATE);
                    String orderCheckedOutStr = orderCheckout.getString("orderCheckout", "");

                    List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
                    List<String> selectedProductsQuantityList = new ArrayList<>();

                    SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
                            Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    object_stringqty = selectedProducts.getString("selected_products_qty", "");
                    object_string = selectedProducts.getString("selected_products", "");
                    Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                    }.getType();
                    Type typeString = new TypeToken<List<String>>() {
                    }.getType();
                    if (!object_string.equals("") && !object_stringqty.equals("")) {
                        selectedProductsDataList = gson.fromJson(object_string, type);
                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
                    }
                    SharedPreferences selectedProductsSP = getContext().getSharedPreferences("FromDraft_Temp",
                            Context.MODE_PRIVATE);
                    int quantity = 0;
                    if (selectedProductsQuantityList != null && selectedProductsQuantityList.size() > 0)
                        for (int i = 0; i < selectedProductsQuantityList.size(); i++) {
                            quantity += Integer.parseInt(selectedProductsQuantityList.get(i));
                        }


//                    // Log.i("back_debug", orderCheckedOutStr + "'''1");
//                    // Log.i("back_debug123", String.valueOf(selectedProductsDataList.size()) + "'''2");
//                    // Log.i("back_debug123", String.valueOf(quantity) + "'''3");
//                    // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraft", "") + "'''4"));
//                    // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraftChanged", "") + "'''5"));
//
//                    // Log.i("back_debug123", String.valueOf(selectedProductsDataList.size() > 0) + "'''12");
//                    // Log.i("back_debug123", String.valueOf(quantity > 0) + "'''13");
//                    // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraft", "").equals("draft") + "'''14"));
//                    // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraftChanged", "").equals("changed") + "'''15"));

                    return executeBackStackFlow(selectedProductsSP, orderCheckedOutStr, quantity, selectedProductsDataList, selectedProductsQuantityList);
////                if (selectedProductsDataList_temp != null && selectedProductsDataList_temp.size() > 0 && (!orderCheckedOutStr.equals(""))) {
////                    showDiscardDialog();
//                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && quantity > 0 && (!orderCheckedOutStr.equals("") || (selectedProductsSP.getString("fromDraft", "").equals("draft")) && selectedProductsSP.getString("fromDraftChanged", "").equals("changed"))) {
////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && quantity > 0 && (!orderCheckedOutStr.equals("")) || (selectedProductsSP.getString("fromDraft", "").equals("draft"))) {
////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && quantity > 0 && (!orderCheckedOutStr.equals("")) && (!selectedProductsSP.getString("fromDraft", "").equals("draft"))) {
////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("orderCheckout") || orderCheckedOut.equals("orderCheckout123"))) {
//                        showDiscardDialog();
//                        return true;
//                    } else if ((selectedProductsDataList == null || selectedProductsDataList.size() == 0 || quantity == 0) && (selectedProductsSP.getString("fromDraft", "").equals("draft"))) {
//
////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (!orderCheckedOutStr.equals("")) && (!selectedProductsSP.getString("fromDraft", "").equals("draft"))) {
//////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("orderCheckout") || orderCheckedOut.equals("orderCheckout123"))) {
////                        showDiscardDialog();
////                        return true;
////                    } else if ((selectedProductsDataList == null || selectedProductsDataList.size() == 0) && (selectedProductsSP.getString("fromDraft", "").equals("draft"))) {
//////                    if (selectedProductsDataList != null && selectedProductsDataList.size() > 0 && (orderCheckedOut.equals("orderCheckout") || orderCheckedOut.equals("orderCheckout123"))) {
//                        showDiscardDialog();
//                        return true;
//                    } else {
//                        InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);
//
////
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        if (!selectedProductsSP.getString("fromDraft", "").equals("draft"))
//                            fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder()).addToBackStack("null");
//                        else
//                            fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
//                        fragmentTransaction.commit();
//                        return true;
////                    } else {
////                        showDiscardDialog();
////                        return true;
//                    }
////                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
////                            Context.MODE_PRIVATE);
////                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
////                    editorOrderTabsFromDraft.putString("TabNo", "0");
////                    editorOrderTabsFromDraft.apply();
////
////                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
////                    ((FragmentActivity) getContext()).startActivity(login_intent);
////                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

    }

    private boolean executeBackStackFlow(SharedPreferences selectedProductsSP, String orderCheckedOutStr, int quantity, List<OrderChildlist_Model_DistOrder> selectedProductsDataList, List<String> selectedProductsQuantityList) {
        // Log.i("back_debug", orderCheckedOutStr + "'''1");
        // Log.i("back_debug123", String.valueOf(selectedProductsDataList.size()) + "'''2");
        // Log.i("back_debug123", String.valueOf(quantity) + "'''3");
        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraft", "") + "'''4"));
        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraftChanged", "") + "'''5"));

        // Log.i("back_debug123", String.valueOf(!orderCheckedOutStr.equals("")) + "'''11");
        // Log.i("back_debug123", String.valueOf(selectedProductsDataList.size() > 0) + "'''12");
        // Log.i("back_debug123", String.valueOf(quantity > 0) + "'''13");
        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraft", "").equals("draft") + "'''14"));
        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraftChanged", "").equals("changed") + "'''15"));


        if (selectedProductsSP.getString("fromDraft", "").equals("draft")) {
            // Log.i("back_debug", "in draft flow" + "'''1");
            //draft flow
            if (selectedProductsSP.getString("fromDraftChanged", "").equals("changed")) {
                showDiscardDialog();
                return true;
            } else {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
                fragmentTransaction.commit();
                return true;
            }
        } else {
            // Log.i("back_debug", "in place order flow" + "'''1");
            // place order flow
            if (((!orderCheckedOutStr.equals("")))) {
//            if (quantity > 0 && (!orderCheckedOutStr.equals(""))) {
                showDiscardDialog();
                return true;
            } else {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder()).addToBackStack("null");
                fragmentTransaction.commit();
                return true;
            }
        }
    }

    private void getProductCategory() throws JSONException {
//        totalCategoryTitle = new ArrayList<>();
//        Categories = new HashMap<>();
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        // Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");
        // Log.i("CompanyId", CompanyId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        // Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;
//        titles = new ArrayList<>();

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.showLoader();
                // Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
                    loader.showLoader();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
                    }.getType();
                    try {
                        Object item = result.get(i);

                        // `instanceof` tells us whether the object can be cast to a specific type
                        if (item instanceof JSONArray) {
//                            titles = gson.fromJson(result.get(i).toString(), type);
//                            // Log.i("productCategory", String.valueOf(titles));
                        } else if (item instanceof JSONObject) {
                            OrderParentlist_Model_DistOrder tempModel = gson.fromJson(item.toString(), OrderParentlist_Model_DistOrder.class);
                            Categories.put(tempModel.getTitle(), tempModel.getID());
                            totalCategoryTitle.add(tempModel.getTitle());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    loader.hideLoader();

                }
//                arrayAdapterSpinnerConso.notifyDataSetChanged();
                spinner_conso.setAdapter(arrayAdapterSpinnerConso);
                spinner_conso.setSelection(selected_category_index);
//                // Log.i("totalCategory", String.valueOf(totalCategory));
                try {
                    getProductsFromCategory();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                loader.hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new

                DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(

                getContext()).

                add(sr);
    }

    private void getFilteredProductCategory(final String ParentId) throws JSONException {
        byDefaultStatus = "false";
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        // Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");
        // Log.i("CompanyId", CompanyId);

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        // Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                titles = new ArrayList<>();
                // Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
//                    totalCategory = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
                    }.getType();
                    try {
                        Object item = result.get(i);

                        // `instanceof` tells us whether the object can be cast to a specific type
                        if (item instanceof JSONArray) {
//                            titles = gson.fromJson(result.get(i).toString(), type);
//                            // Log.i("productCategory", String.valueOf(titles));
                            for (int j = 0; j < ((JSONArray) item).length(); j++) {
                                OrderParentlist_Model_DistOrder tempModel = gson.fromJson(((JSONArray) item).get(j).toString(), OrderParentlist_Model_DistOrder.class);
                                if (tempModel.getParentId().equals(ParentId)) {
                                    titles.add(tempModel);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
//                titles.add(new OrderParentlist_Model_DistOrder("", "", "", "", "", "", ""));
                temp_titles = titles;

                // Log.i("titles", String.valueOf(titles));
                try {
                    getProductsFromCategory();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new

                DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(

                getContext()).

                add(sr);
    }

    private void getProductsFromCategory() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        // Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        // Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT.contains("/" + CompanyId))
            URL_PRODUCT = URL_PRODUCT + CompanyId;

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.showLoader();
                // Log.i("resultLength", String.valueOf(result.length()));
                // Log.i("result", String.valueOf(result));

                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                }.getType();
                productList = gson.fromJson(String.valueOf(result), type);
                // Log.i("productList", String.valueOf(productList));

                final ParentList_Adapter_DistOrder adapter = new ParentList_Adapter_DistOrder(getActivity(), initData(), spinner_container_main, btn_checkout, productList, Category_selected);
//                adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
//                adapter.setParentClickableViewAnimationDefaultDuration();
                adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @UiThread
                    @Override
                    public void onParentExpanded(int parentPosition) {

                        if (lastExpandedPosition != -1
                                && parentPosition != lastExpandedPosition) {
                            adapter.collapseParent(lastExpandedPosition);
//                                adapter.OrderParentList.get(lastExpandedPosition).togglePlusMinusIcon();
//                                adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                        }
                        lastExpandedPosition = parentPosition;
                    }

                    @UiThread
                    @Override
                    public void onParentCollapsed(int parentPosition) {
//                            adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                    }
                });
                //adapter.setParentClickableViewAnimationDefaultDuration();
//                    adapter.setParentAndIconExpandOnClick(false);
//                    recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 30));
                recyclerView.setAdapter(adapter);
//                loader.hideLoader();

                if (spinner_container_main.getVisibility() == View.GONE) {

                    spinner_container_main.setVisibility(View.VISIBLE);
                    TranslateAnimation animate1 = new TranslateAnimation(
                            0,                 // fromXDelta
                            0,                 // toXDelta
                            -spinner_container_main.getHeight(),  // fromYDelta
                            0);                // toYDelta
                    animate1.setDuration(250);
                    animate1.setFillAfter(true);
                    spinner_container_main.clearAnimation();
                    spinner_container_main.startAnimation(animate1);
                }
                loader.hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
//        new MyAsyncTask().execute();
    }

    private void getFilteredProductsFromCategory(final String Product) throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        // Log.i("DistributorId ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONObject map = new JSONObject();
        map.put("DistributorId", Integer.parseInt(DistributorId));
        // Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT.contains("/" + CompanyId))
            URL_PRODUCT = URL_PRODUCT + CompanyId;

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray result) {
                loader.showLoader();
                productList = new ArrayList<>();
                List<OrderParentlist_Model_DistOrder> temp12_titles = titles;
//                temp_titles = titles;
                titles = new ArrayList<>();
                // Log.i("resultLength", String.valueOf(result.length()));
                // Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
                    loader.showLoader();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
                    }.getType();

                    OrderChildlist_Model_DistOrder tempModel = null;

                    try {
                        tempModel = gson.fromJson(((JSONArray) result).get(i).toString(), OrderChildlist_Model_DistOrder.class);
                        if (tempModel.getTitle().toLowerCase().contains(Product.toLowerCase())) {
                            productList.add(tempModel);
//                        boolean found = false;
                            for (int j = 0; j < temp_titles.size(); j++) {
//                                loader.showLoader();
                                if (temp_titles.get(j).getID().equals(tempModel.getCategoryId())) {
//                                Categories.put(tempModel.getTitle(), tempModel.getID());
//                                totalCategoryTitle.add(tempModel.getTitle());
                                    if (!titles.contains(temp_titles.get(j)))
                                        titles.add(temp_titles.get(j));
//                                found = true;
                                }
//                                loader.hideLoader();
                            }

//                        if (!found) {
//                            try {
//                                getFilteredProductCategoryForProducts(tempModel.getCategoryId());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Log.i("productList", String.valueOf(productList));
//                // Log.i("titles123", String.valueOf(titles));
                final ParentList_Adapter_DistOrder adapter = new ParentList_Adapter_DistOrder(getActivity(), initData(), spinner_container_main, btn_checkout, productList, Category_selected);
//                adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
//                adapter.set .setParentClickableViewAnimationDefaultDuration();
//                adapter.setParentAndIconExpandOnClick(false);
//                adapter.onParentItemClickListener(1);
                adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @UiThread
                    @Override
                    public void onParentExpanded(int parentPosition) {

                        if (lastExpandedPosition != -1
                                && parentPosition != lastExpandedPosition) {
                            adapter.collapseParent(lastExpandedPosition);
//                                adapter.OrderParentList.get(lastExpandedPosition).togglePlusMinusIcon();
//                                adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                        }
                        lastExpandedPosition = parentPosition;
                    }

                    @UiThread
                    @Override
                    public void onParentCollapsed(int parentPosition) {
//                            adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                    }
                });
                //adapter.setParentClickableViewAnimationDefaultDuration();
//                    adapter.setParentAndIconExpandOnClick(false);
//                    recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 30));
                recyclerView.setAdapter(adapter);

                if (spinner_container_main.getVisibility() == View.GONE) {

                    spinner_container_main.setVisibility(View.VISIBLE);
                    TranslateAnimation animate1 = new TranslateAnimation(
                            0,                 // fromXDelta
                            0,                 // toXDelta
                            -spinner_container_main.getHeight(),  // fromYDelta
                            0);                // toYDelta
                    animate1.setDuration(250);
                    animate1.setFillAfter(true);
                    spinner_container_main.clearAnimation();
                    spinner_container_main.startAnimation(animate1);
                }
                loader.hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
//        new MyAsyncTask().execute();
    }
//
//    private void getFilteredProductCategoryForProducts(final String CategoryId) throws JSONException {
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//        // Log.i("Token", Token);
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        // Log.i("DistributorId ", DistributorId);
//
//        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
//                Context.MODE_PRIVATE);
//        CompanyId = sharedPreferences2.getString("CompanyId", "");
//        // Log.i("CompanyId", CompanyId);
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        // Log.i("Map", String.valueOf(map));
//        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
//            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                // Log.i("result", String.valueOf(result));
//                for (int i = 0; i < result.length(); i++) {
//                    totalCategory = new ArrayList<>();
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
//                    }.getType();
//                    try {
//                        Object item = result.get(i);
//
//                        // `instanceof` tells us whether the object can be cast to a specific type
//                        if (item instanceof JSONArray) {
////                            titles = gson.fromJson(result.get(i).toString(), type);
////                            // Log.i("productCategory", String.valueOf(titles));
//                            for (int j = 0; j < ((JSONArray) item).length(); j++) {
//                                OrderParentlist_Model_DistOrder tempModel = gson.fromJson(((JSONArray) item).get(j).toString(), OrderParentlist_Model_DistOrder.class);
//                                if (tempModel.getID().equals(CategoryId)) {
//                                    titles.add(tempModel);
//                                }
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//
//                // Log.i("titles", String.valueOf(titles));
////                try {
////                    getProductsFromCategory();
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                printErrorMessage(error);
//
//                error.printStackTrace();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
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

//    private List<OrderParentlist_Model_DistOrder> initData() {
//        List<OrderParentlist_Model_DistOrder> parentObjects = new ArrayList<>();
//        for (OrderParentlist_Model_DistOrder title : titles) {
//            // Log.i("title", String.valueOf(title.getTitle()));
//            List<Object> childlist = new ArrayList<>();
////            childlist.add(new OrderChildlist_Model());
//            for (OrderChildlist_Model_DistOrder product : productList) {
//                // Log.i("product", String.valueOf(product.getCategoryTitle()));
//                if (title.getTitle().equals(product.getCategoryTitle())) {
//                    // Log.i("productAdded", product.getTitle());
//                    childlist.add(product);
//                }
//            }
//            title.setChildList(childlist);
//            parentObjects.add(title);
//        }
//        return parentObjects;
//    }


//    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            while (getContext() != null) {
////                // Log.i("productsAsync", "in loop");
////                // Log.i("productsAsync", String.valueOf(selectedProductsDataList));
//                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                        Context.MODE_PRIVATE);
//                object_string = selectedProducts.getString("selected_products", "");
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//                }.getType();
//                temp_list = gson.fromJson(object_string, type);
//                object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                Type typestr = new TypeToken<List<String>>() {
//                }.getType();
//                temp_listqty = gson.fromJson(object_stringqty, typestr);
//                if (!object_string.equals("")) {
//                    if (selectedProductsDataList != null) {
//                        if (temp_list != selectedProductsDataList) {
//                            selectedProductsDataList = temp_list;
//                            selectedProductsQuantityList = temp_listqty;
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (getContext() != null)
//                enableCheckout();
//        }
//    }

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


    private List<OrderParentlist_Model_DistOrder> initData() {
        List<OrderParentlist_Model_DistOrder> parentObjects = new ArrayList<>();
        for (OrderParentlist_Model_DistOrder title : titles) {
            // Log.i("title", String.valueOf(title.getTitle()));
            List<Object> childlist = new ArrayList<>();
//            childlist.add(new OrderChildlist_Model());
            for (OrderChildlist_Model_DistOrder product : productList) {
                // Log.i("product", String.valueOf(product.getCategoryId()));
                if (title.getTitle().equals(product.getCategoryTitle())) {
                    childlist.add(product);
                }
            }
            title.setChildList(childlist);
            parentObjects.add(title);
        }
        return parentObjects;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (!isVisibleToUser) {
//            if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
//                myAsyncTask.cancel(true);
//
//            if (myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
//                myAsyncTask.cancel(true);
//        }
//    }

//    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            while (getContext() != null && !isCancelled()) {
////                // Log.i("productsAsync", "in loop");
////                // Log.i("productsAsync", String.valueOf(selectedProductsDataList));
//                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor",
//                        Context.MODE_PRIVATE);
//                object_string = selectedProducts.getString("selected_products", "");
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
//                }.getType();
//                temp_list = gson.fromJson(object_string, type);
//                object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                Type typestr = new TypeToken<List<String>>() {
//                }.getType();
//                temp_listqty = gson.fromJson(object_stringqty, typestr);
//                if (!object_string.equals("")) {
//                    if (selectedProductsDataList != null) {
//                        if (temp_list != selectedProductsDataList) {
//                            selectedProductsDataList = temp_list;
//                            selectedProductsQuantityList = temp_listqty;
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (getContext() != null)
//                enableCheckout();
//        }
//    }

    // private void printErrMessage(VolleyError error) {
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
    //                 // Log.i("responseBody", responseBody);
    //                 JSONObject data = new JSONObject(responseBody);
    //                 // Log.i("data", String.valueOf(data));
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

    private String getScrollEvent() {
        String scroll = "";
        if (scrollEvent.size() > 0) {
            if (scrollEvent.size() > 15)
                scrollEvent = new ArrayList<>();
            if (Collections.frequency(scrollEvent, "ScrollUp") > Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollDown") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollUp") > 3)
                        scroll = "ScrollUp";
                } else {
                    scroll = "ScrollUp";
                }
            } else if (Collections.frequency(scrollEvent, "ScrollUp") < Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollUp") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollDown") > 3)
                        scroll = "ScrollDown";
                } else {
                    scroll = "ScrollDown";
                }
            }
        }
//        // Log.i("distinct", scroll);
        return scroll;
    }
//
//    private void fetchDashboardData() {
////        loader.showLoader();
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_DISTRIBUTOR_DASHBOARD, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String result) {
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    DecimalFormat formatter3 = new DecimalFormat("#,###,###,##0.00");
//                     current_balance = formatter3.format(Double.parseDouble(jsonObject.get("TotalDistributorBalance").toString()));
//                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("currentBalance",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                    editorOrderTabsFromDraft.putString("current_balance" , String.valueOf(current_balance));
//                    editorOrderTabsFromDraft.apply();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(getContext(), error);
//                new ProcessingError().showError(getContext());
//                loader.hideLoader();
//
//                error.printStackTrace();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//    }
}
