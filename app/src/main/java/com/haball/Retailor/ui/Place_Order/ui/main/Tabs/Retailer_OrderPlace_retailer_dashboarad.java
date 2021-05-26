package com.haball.Retailor.ui.Place_Order.ui.main.Tabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.MyDividerItemDecoration;
import com.haball.NonSwipeableViewPager;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Place_Order.Retailer_Place_Order;
import com.haball.Retailor.ui.Place_Order.ui.main.Adapters.ParentListAdapter;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderParentlist_Model;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Retailer_OrderPlace_retailer_dashboarad extends Fragment {
    RecyclerView recyclerView, subchlid_RV;
    private List<OrderParentlist_Model> titles = new ArrayList<>();
    private List<OrderChildlist_Model> productList = new ArrayList<>();
    private List<SimpleParent> parentObjects = new ArrayList<>();
    private String URL_PRODUCT_CATEGORY = "http://175.107.203.97:4014/api/products/GetProductByDealerCode/";
    //    private String URL_PRODUCT = "http://175.107.203.97:4014/api/products/GetProductByDealerCode/";
    private String Token, Retailer_Id, CompanyId;
    private String object_string, object_stringqty;
    private List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
    private List<OrderChildlist_Model> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private float grossAmount = 0;
    private Button btn_checkout, btn_close;
    private Spinner spinner_conso;
    private List<String> totalCategoryTitle = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterSpinnerConso;
    private String Category_selected;
    private HashMap<String, String> Categories = new HashMap<>();
    private TextInputEditText et_test;
    List<OrderParentlist_Model> temp_titles = new ArrayList<>();
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private RelativeLayout spinner_container_main;
    private Typeface myFont;
    //    private MyAsyncTask myAsyncTask;
    private FragmentTransaction fragmentTransaction;
    private int lastExpandedPosition = -1;
    private String editTextValue = "";
    private View myview = null;
    private Loader loader;
    private boolean byDefault = true;
    boolean isKeyboardShowing = false;
    int width;
    int height;
    private String fromDraft = "", fromAddMore = "";

    public Retailer_OrderPlace_retailer_dashboarad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_order_place_retailer_dashboarad, container, false);
        myview = view;
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        final RelativeLayout main_container = view.findViewById(R.id.main_container);
        btn_checkout = view.findViewById(R.id.btn_checkout);
        btn_close = view.findViewById(R.id.close_button);
        loader = new Loader(getContext());

        final NonSwipeableViewPager viewPager = ((FragmentActivity) getContext()).findViewById(R.id.view_pager_rpoid);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPager.setCurrentItem(0, false);
            }
        });
        viewPager.setCurrentItem(0);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = view.getRootView().getHeight() - view.getHeight();

                if (heightDiff > 100) {
                    // Log.e("MyActivity", "keyboard opened");
                } else {
                    // Log.e("MyActivity", "keyboard closed");
                }
            }
        });


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
//                        Context.MODE_PRIVATE);
//                Gson gson = new Gson();
//                String orderCheckedOut = orderCheckout.getString("orderCheckout", "");
//
//                if (orderCheckedOut.equals("orderCheckout")) {
//                    showDiscardDialog();
//                } else {
//
//                    InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);
//
//                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("tag");
//                    fragmentTransaction.commit();
//                }

                SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
                        Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String orderCheckedOutStr = orderCheckout.getString("orderCheckout", "");

                List<OrderChildlist_Model> selectedProductsDataList_temp = new ArrayList<>();
                List<String> selectedProductsQuantityList_temp = new ArrayList<>();

                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                        Context.MODE_PRIVATE);
                object_stringqty = selectedProducts.getString("selected_products_qty", "");
                object_string = selectedProducts.getString("selected_products", "");
                Type type = new TypeToken<List<OrderChildlist_Model>>() {
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


            }
        });
        recyclerView = view.findViewById(R.id.rv_order_list);
        spinner_container_main = view.findViewById(R.id.spinner_container_main);
//        subchlid_RV = view.findViewById(R.id.subchlid_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        subchlid_RV.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ParentListAdapter adapter = new ParentListAdapter(getActivity(), (List<ParentObject>) initData());
//        adapter.setParentClickableViewAnimationDefaultDuration();
//        adapter.setParentAndIconExpandOnClick(true);
//        recyclerView.setAdapter(adapter);
        spinner_conso = view.findViewById(R.id.spinner_conso);
        et_test = view.findViewById(R.id.et_test);
//        totalCategoryTitle.add("Select All");
//        arrayAdapterSpinnerConso = new ArrayAdapter<>(view.getContext(),
//                android.R.layout.simple_spinner_dropdown_item, totalCategoryTitle);

//        totalCategoryTitle.add("All Category");
//        arrayAdapterSpinnerConso = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, totalCategoryTitle) {
//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                // TODO Auto-generated method stub
//                View view = super.getView(position, convertView, parent);
//                TextView text = (TextView) view.findViewById(android.R.id.text1);
//                text.setTextColor(getResources().getColor(R.color.text_color_selection));
//                text.setTextSize((float) 13.6);
//                text.setPadding(30, 0, 30, 0);
//                text.setTypeface(myFont);
//                return view;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                // TODO Auto-generated method stub
//                View view = super.getView(position, convertView, parent);
//                TextView text = (TextView) view.findViewById(android.R.id.text1);
//                text.setTextColor(getResources().getColor(R.color.text_color_selection));
//                text.setTextSize((float) 13.6);
//                text.setPadding(30, 0, 30, 0);
//                return view;
//            }
//        };
        SharedPreferences add_more_product = getContext().getSharedPreferences("add_more_product",
                Context.MODE_PRIVATE);
        if (!add_more_product.getString("add_more_product", "").equals("fromAddMore")) {
            // Log.i("debugOrder_AddMore", "not from add more product");
            SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", "");
            editor.putString("selected_products_qty", "");
            editor.apply();
        }

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
                                NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager_rpoid);
                                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                                        Context.MODE_PRIVATE);
                                Gson gson = new Gson();
                                object_stringqty = selectedProducts.getString("selected_products_qty", "");
                                object_string = selectedProducts.getString("selected_products", "");
                                Type type = new TypeToken<List<OrderChildlist_Model>>() {
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
                                        if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                            grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                                    }
                                    SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = grossamount.edit();
                                    editor.putString("grossamount", String.valueOf(grossAmount));
                                    editor.apply();
//                            Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
                                    grossAmount = 0;
                                    viewPager.setCurrentItem(1);

                                    InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);


                                    FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container_ret, new Retailer_Order_Summary());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            }
                        }, 3000);
                    }
                });
            }
        });
//
//        SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor_grossamount = grossamount.edit();
//        editor_grossamount.putString("grossamount", "0");
//        editor_grossamount.apply();

//        arrayAdapterSpinnerConso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_conso.setAdapter(arrayAdapterSpinnerConso);
        spinner_conso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.i("DebugFilter", "in spinner: " + position);
                // Log.i("DebugFilter", "in spinner: E, " + editTextValue);
                Category_selected = totalCategoryTitle.get(position);

//                ((TextView) parent.getChildAt(position)).setTextColor(getResources().getColor(R.color.textcolor));
//                ((TextView) parent.getChildAt(position)).setTextSize((float) 13.6);
//                ((TextView) parent.getChildAt(position)).setPadding(50, 0, 50, 0);
                if (!byDefault) {
                    if (position != 0) {
                        et_test.setText("");
                        et_test.clearFocus();
                        try {
                            // Log.i("Categoriesselected", Categories.get(Category_selected) + " - " + Category_selected);
                            getFilteredProductCategory(Categories.get(Category_selected));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Log.i("titles123", "in else");
                        if (editTextValue.equals("")) {
                            try {
                                getProductCategory();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    byDefault = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                            getFilteredProduct(String.valueOf(s));
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
//                if (!byDefault) {
//
//                    if (!String.valueOf(s).equals("")) {
//                        spinner_conso.setSelection(0);
//                        // Log.i("titles123", "in if");
//                        try {
//                            getFilteredProduct(String.valueOf(s));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        // Log.i("titles123", "in else");
//                        if (Category_selected != null && Category_selected.equals("All Category")) {
//                            try {
//                                getProductCategory();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                } else {
//                    byDefault = false;
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
                    // Log.i("scrolling", "Scroll Down");
                } else if (dy > 5) {
                    scrollEvent.add("ScrollUp");
                    // Log.i("scrolling", "Scroll Up");
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

//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
//                    if (totalPages != 0 && pageNumber < totalPages) {
////                                Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
//                    }
            }

        });
        try {
            getProductCategory();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ViewTreeObserver vto = main_container.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    main_container.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    main_container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                width = main_container.getMeasuredWidth();
                height = main_container.getMeasuredHeight();

                // Log.i("heightOfLayout", String.valueOf(height));
//                if (height < 1500) {
//                    recyclerView.setPadding(0, 0, 0, 500);
//                }

                // Log.i("debug_dim_height_debug", String.valueOf(height));
                // Log.i("debug_dim_width_debug", String.valueOf(width));

            }
        });

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
        tv_discard_txt.setText(getResources().getString(R.string.discard_text));
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

//
//                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("tag");
//                fragmentTransaction.commit();

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
//                    // handle back button's click listener
////                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
////
//                    SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
//                            Context.MODE_PRIVATE);
//                    Gson gson = new Gson();
//                    object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                    object_string = selectedProducts.getString("selected_products", "");
//                    Type type = new TypeToken<List<OrderChildlist_Model>>() {
//                    }.getType();
//                    Type typeString = new TypeToken<List<String>>() {
//                    }.getType();
//                    selectedProductsDataList = gson.fromJson(object_string, type);
//                    selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//
//                    if (selectedProductsDataList == null || selectedProductsDataList.size() == 0) {
//                        InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);
//
//
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("null");
//                        fragmentTransaction.commit();
//                        return true;
//                    } else {
//                        showDiscardDialog();
//                        return true;
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
//                }
                    SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout_discard",
                            Context.MODE_PRIVATE);
                    String orderCheckedOutStr = orderCheckout.getString("orderCheckout", "");

                    List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
                    List<String> selectedProductsQuantityList = new ArrayList<>();

                    SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                            Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    object_stringqty = selectedProducts.getString("selected_products_qty", "");
                    object_string = selectedProducts.getString("selected_products", "");
                    Type type = new TypeToken<List<OrderChildlist_Model>>() {
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
                }
                return false;
            }
        });

    }



    private boolean executeBackStackFlow(SharedPreferences selectedProductsSP, String orderCheckedOutStr, int quantity, List<OrderChildlist_Model> selectedProductsDataList, List<String> selectedProductsQuantityList) {
//        // Log.i("back_debug", orderCheckedOutStr + "'''1");
//        // Log.i("back_debug123", String.valueOf(selectedProductsDataList.size()) + "'''2");
//        // Log.i("back_debug123", String.valueOf(quantity) + "'''3");
//        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraft", "") + "'''4"));
//        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraftChanged", "") + "'''5"));
//
//        // Log.i("back_debug123", String.valueOf(!orderCheckedOutStr.equals("")) + "'''11");
//        // Log.i("back_debug123", String.valueOf(selectedProductsDataList.size() > 0) + "'''12");
//        // Log.i("back_debug123", String.valueOf(quantity > 0) + "'''13");
//        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraft", "").equals("draft") + "'''14"));
//        // Log.i("back_debug123", String.valueOf(selectedProductsSP.getString("fromDraftChanged", "").equals("changed") + "'''15"));


        if (selectedProductsSP.getString("fromDraft", "").equals("draft")) {
            // Log.i("back_debug", "in draft flow" + "'''1");
            //draft flow
            if (selectedProductsSP.getString("fromDraftChanged", "").equals("changed")) {
                showDiscardDialog();
                return true;
            } else {
                Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();
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
                fragmentTransaction.replace(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("tag");
                fragmentTransaction.commit();
                return true;
            }
        }
    }

//
//    private boolean enableCheckout() {
////        // Log.i("checkout", "in checkout");
//        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
//                Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        object_stringqty = selectedProducts.getString("selected_products_qty", "");
//        object_string = selectedProducts.getString("selected_products", "");
//        Type type = new TypeToken<List<OrderChildlist_Model>>() {
//        }.getType();
//        Type typeString = new TypeToken<List<String>>() {
//        }.getType();
//        if (!object_string.equals("") && !object_stringqty.equals("")) {
//            selectedProductsDataList = gson.fromJson(object_string, type);
//            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//        }
////        float totalQty = 0;
////        if (selectedProductsDataList != null) {
////            if (selectedProductsDataList.size() > 0) {
////                for (int i = 0; i < selectedProductsDataList.size(); i++) {
//////                    // Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//////                    // Log.i("qty", selectedProductsQuantityList.get(i));
////                    if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
////                        if (Float.parseFloat(selectedProductsQuantityList.get(i)) > 0) {
////                            totalQty = totalQty + Float.parseFloat(selectedProductsQuantityList.get(i));
////                        }
////                }
////            }
////        }
//////        // Log.i("totalQty", "here");
//////        // Log.i("totalQty", String.valueOf(totalQty));
////        if (totalQty > 0) {
////            btn_checkout.setEnabled(true);
////            btn_checkout.setBackgroundResource(R.drawable.button_round);
////        } else {
////            btn_checkout.setEnabled(false);
////            btn_checkout.setBackgroundResource(R.drawable.button_grey_round);
////        }
////        myAsyncTask = new MyAsyncTask();
////        myAsyncTask.execute();
//        final Loader loader = new Loader(getContext());
//
////            selectedProductsDataList = gson.fromJson(object_string, type);
//        if (selectedProductsDataList != null) {
//            if (selectedProductsDataList.size() > 0) {
////                btn_checkout.setBackgroundResource(R.drawable.button_round);
////                btn_checkout.setEnabled(true);
//                btn_checkout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loader.showLoader();
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        loader.hideLoader();
//                                        //Do something after 1 second
//
//                                        SharedPreferences orderCheckout = getContext().getSharedPreferences("orderCheckout",
//                                                Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
//                                        orderCheckout_editor.putString("orderCheckout", "orderCheckout");
//                                        orderCheckout_editor.apply();
//                                        NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager_rpoid);
//                                        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
//                                                Context.MODE_PRIVATE);
//                                        Gson gson = new Gson();
//                                        object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                                        object_string = selectedProducts.getString("selected_products", "");
//                                        Type type = new TypeToken<List<OrderChildlist_Model>>() {
//                                        }.getType();
//                                        Type typeString = new TypeToken<List<String>>() {
//                                        }.getType();
//                                        selectedProductsDataList = gson.fromJson(object_string, type);
//                                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
////                        if (selectedProductsDataList.size() > 0) {
////                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
////                                // Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
////                                // Log.i("qty", selectedProductsQuantityList.get(i));
////                                if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
////                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
////                            }
//                                        if (selectedProductsDataList.size() > 0) {
//                                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
////                                // Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
////                                // Log.i("qty", selectedProductsQuantityList.get(i));
//                                                if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
//                                            }
//                                            SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
//                                                    Context.MODE_PRIVATE);
//                                            SharedPreferences.Editor editor = grossamount.edit();
//                                            editor.putString("grossamount", String.valueOf(grossAmount));
//                                            editor.apply();
////                            Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
//                                            grossAmount = 0;
//                                            viewPager.setCurrentItem(1);
//
//                                            InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
//                                            imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);
//
//
//                                            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
//                                            fragmentTransaction.add(R.id.main_container_ret, new Retailer_Order_Summary());
//                                            fragmentTransaction.addToBackStack(null);
//                                            fragmentTransaction.commit();
//
//                                        }
//                                    }
//                                }, 3000);
//                            }
//                        });
//                    }
//                });
//                return true;
//            }
//        }
//        return false;
//    }


//    private void getFilteredProductsFromCategory(final String Product) throws JSONException {
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
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        // Log.i("Map", String.valueOf(map));
//        if (!URL_PRODUCT.contains("/" + CompanyId))
//            URL_PRODUCT = URL_PRODUCT + CompanyId;
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                productList = new ArrayList<>();
//                List<OrderParentlist_Model> temp12_titles = titles;
////                temp_titles = titles;
//                titles = new ArrayList<>();
//                // Log.i("temp_titles", String.valueOf(temp_titles));
//                // Log.i("resultLength", String.valueOf(result.length()));
//                // Log.i("result", String.valueOf(result));
//                for (int i = 0; i < result.length(); i++) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<OrderChildlist_Model>>() {
//                    }.getType();
//
//                    OrderChildlist_Model tempModel = null;
//
//                    try {
//                        tempModel = gson.fromJson(((JSONArray) result).get(i).toString(), OrderChildlist_Model.class);
//                        if (tempModel.getTitle().toLowerCase().contains(Product.toLowerCase())) {
//                            productList.add(tempModel);
////                        boolean found = false;
//                            for (int j = 0; j < temp_titles.size(); j++) {
//                                if (temp_titles.get(j).getCategoryId().equals(tempModel.getProductCategoryId())) {
////                                Categories.put(tempModel.getTitle(), tempModel.getID());
////                                totalCategoryTitle.add(tempModel.getTitle());
//                                    if (!titles.contains(temp_titles.get(j)))
//                                        titles.add(temp_titles.get(j));
//                                    // Log.i("titles", String.valueOf(titles));
////                                found = true;
//                                }
//                            }
//
////                        if (!found) {
////                            try {
////                                getFilteredProductCategoryForProducts(tempModel.getCategoryId());
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                // Log.i("productList", String.valueOf(productList));
////                // Log.i("titles123", String.valueOf(titles));
//                ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
////                adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
//                adapter.setParentClickableViewAnimationDefaultDuration();
//                adapter.setParentAndIconExpandOnClick(true);
//                recyclerView.setAdapter(adapter);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(error);
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
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//        new MyAsyncTask().execute();
//    }

    private void getFilteredProductCategory(final String ParentId) throws JSONException {

       // // Log.i("ParentId", ParentId);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Retailer_Id = sharedPreferences1.getString("Retailer_Id", "");
        // Log.i("Retailer_Id ", Retailer_Id);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");
        // Log.i("CompanyId", CompanyId);


//        JSONObject map = new JSONObject();
//        map.put("RetailerId", Integer.parseInt(Retailer_Id));
//        // Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject resultMain) {
                JSONArray result = null;
                try {
                    result = resultMain.getJSONArray("SubCategory");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                titles = new ArrayList<>();
                // Log.i("result", String.valueOf(result));
                for (int i = 0; i < result.length(); i++) {
//                    totalCategory = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderParentlist_Model>>() {
                    }.getType();
                    try {
                        Object item = result.get(i);
                        // Log.i("itemFilter", String.valueOf(item));

                        // `instanceof` tells us whether the object can be cast to a specific type
                        if (item instanceof JSONObject) {
//                            titles = gson.fromJson(result.get(i).toString(), type);
//                            // Log.i("productCategory", String.valueOf(titles));
//                            for (int j = 0; j < ((JSONArray) item).length(); j++) {
                            OrderParentlist_Model tempModel = gson.fromJson(((JSONObject) item).toString(), OrderParentlist_Model.class);
                            // Log.i("itemFilter_123", String.valueOf(tempModel));
                            if (tempModel.getParentId().equals(ParentId)) {
                                titles.add(tempModel);
                                // Log.i("itemFilter_123456", String.valueOf(titles));
                            }
//                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                temp_titles = titles;

                // Log.i("titles", String.valueOf(titles));

                try {
                    Gson gsonChild = new Gson();
                    Type typeChild = new TypeToken<List<OrderChildlist_Model>>() {
                    }.getType();
                    productList = gsonChild.fromJson(String.valueOf(resultMain.getJSONArray("Products")), typeChild);
                    // Log.i("productList", String.valueOf(productList));

                    if (productList.size() < 3) {
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
                    }

                    final ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData(), spinner_container_main, btn_checkout, productList, loader);
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
                            if (height < 1500) {
                                recyclerView.setPadding(0, 0, 0, 500);
                            }

                        }

                        @UiThread
                        @Override
                        public void onParentCollapsed(int parentPosition) {
                            if (height < 1500) {
                                recyclerView.setPadding(0, 0, 0, 0);
                            }
//                            adapter.OrderParentList.get(parentPosition).togglePlusMinusIcon();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    boolean bool = true;

    private void getProductCategory() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Retailer_Id = sharedPreferences1.getString("Retailer_Id", "");
//        // Log.i("Retailer_Id ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("DealerInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("DealerCode", "");
        // Log.i("DealerCode", CompanyId);


//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        // Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;

//        // Log.i("Map", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject resultMain) {
                loader.showLoader();
                JSONArray resultFilter = null;
                JSONArray result = null;
                JSONArray resultProduct = null;
                try {
                    resultFilter = resultMain.getJSONArray("MainCategory");
                    result = resultMain.getJSONArray("SubCategory");
                    resultProduct = resultMain.getJSONArray("Products");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                titles = new ArrayList<>();
                Categories = new HashMap<>();
                totalCategoryTitle = new ArrayList<>();
                totalCategoryTitle.add(getResources().getString(R.string.get_all_category));
                // Log.i("result", String.valueOf(result));
                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderParentlist_Model>>() {
                }.getType();
                try {
                    for (int j = 0; j < ((JSONArray) result).length(); j++) {
                        loader.showLoader();
                        OrderParentlist_Model tempModel = gson.fromJson(((JSONArray) result).get(j).toString(), OrderParentlist_Model.class);
                        int countOfProduct = 0;
                        if (resultProduct.length() < 180)
                            for (int k = 0; k < ((JSONArray) resultProduct).length(); k++) {
                                loader.showLoader();
                                OrderChildlist_Model tempModelProduct = gson.fromJson(((JSONArray) resultProduct).get(k).toString(), OrderChildlist_Model.class);
                                if (tempModel.getCategoryId().equals(tempModelProduct.getProductCategoryId()))
                                    countOfProduct++;
                            }

                        if (resultProduct.length() > 180 || countOfProduct > 0)
                            titles.add(tempModel);
                    }

                    for (int j = 0; j < ((JSONArray) resultFilter).length(); j++) {
                        loader.showLoader();
                        OrderParentlist_Model tempModel = gson.fromJson(((JSONArray) resultFilter).get(j).toString(), OrderParentlist_Model.class);
                        int countOfProduct = 0;
                        if (resultProduct.length() < 180)
                            for (int k = 0; k < titles.size(); k++) {
                                loader.showLoader();
                                OrderParentlist_Model tempModelProduct = titles.get(k);
//                            // Log.i("tempModelProduct", tempModel.getCategoryId() + " - " + tempModelProduct.getParentId());
                                if (tempModel.getCategoryId().equals(tempModelProduct.getParentId())) {
//                                // Log.i("tempModelProduct", "found: " + tempModel.getCategoryId() + " - " + tempModelProduct.getParentId());
                                    countOfProduct++;
                                }
                            }

                        if (resultProduct.length() > 180 || countOfProduct > 0) {
                            Categories.put(tempModel.getTitle(), tempModel.getCategoryId());
                            totalCategoryTitle.add(tempModel.getTitle());
                        }
                    }
                    // Log.i("totalCategoryTitle", String.valueOf(totalCategoryTitle));
                    if (arrayAdapterSpinnerConso == null) {
                        arrayAdapterSpinnerConso = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, totalCategoryTitle) {
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

                        spinner_conso.setAdapter(arrayAdapterSpinnerConso);
                    }
//                    arrayAdapterSpinnerConso.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                temp_titles = titles;

                // Log.i("titles", String.valueOf(titles));
                loader.showLoader();

                try {
                    Gson gsonChild = new Gson();
                    Type typeChild = new TypeToken<List<OrderChildlist_Model>>() {
                    }.getType();
                    productList = gsonChild.fromJson(String.valueOf(resultMain.get("Products")), typeChild);
                    // Log.i("productList", String.valueOf(productList));

                    if (productList.size() <= 5) {
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
                    }


                    final ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData(), spinner_container_main, btn_checkout, productList, loader);
                    adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                        @UiThread
                        @Override
                        public void onParentExpanded(int parentPosition) {
                            if (lastExpandedPosition != -1
                                    && parentPosition != lastExpandedPosition) {
                                adapter.collapseParent(lastExpandedPosition);
                            }
                            lastExpandedPosition = parentPosition;
                            if (height < 1500) {
                                recyclerView.setPadding(0, 0, 0, 500);
                            }
                        }

                        @UiThread
                        @Override
                        public void onParentCollapsed(int parentPosition) {
                            if (height < 1500) {
                                recyclerView.setPadding(0, 0, 0, 0);
                            }
                        }
                    });

                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


    private void getFilteredProduct(final String name) throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Retailer_Id = sharedPreferences1.getString("Retailer_Id", "");
//        // Log.i("Retailer_Id ", DistributorId);

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("DealerInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("DealerCode", "");
        // Log.i("DealerCode", CompanyId);


//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        // Log.i("Map", String.valueOf(map));
        if (!URL_PRODUCT_CATEGORY.contains("/" + CompanyId))
            URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORY + CompanyId;

        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PRODUCT_CATEGORY, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject resultMain) {
//                loader.hideLoader();


                JSONArray resultFilter = null;
                JSONArray result = null;
                JSONArray resultProduct = null;
                try {
                    resultFilter = resultMain.getJSONArray("MainCategory");
                    result = resultMain.getJSONArray("SubCategory");
                    resultProduct = resultMain.getJSONArray("Products");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                titles = new ArrayList<>();
                productList = new ArrayList<>();

                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderParentlist_Model>>() {
                }.getType();
                try {
                    for (int j = 0; j < ((JSONArray) result).length(); j++) {
                        OrderParentlist_Model tempModel = gson.fromJson(((JSONArray) result).get(j).toString(), OrderParentlist_Model.class);
                        int countOfProduct = 0;
                        for (int k = 0; k < ((JSONArray) resultProduct).length(); k++) {
                            OrderChildlist_Model tempModelProduct = gson.fromJson(((JSONArray) resultProduct).get(k).toString(), OrderChildlist_Model.class);
                            if (tempModel.getCategoryId().equals(tempModelProduct.getProductCategoryId()))
                                if (tempModelProduct.getTitle().toLowerCase().contains(name.toLowerCase()))
                                    countOfProduct++;
                        }

                        if (countOfProduct > 0)
                            titles.add(tempModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                temp_titles = titles;

                // Log.i("titles", String.valueOf(titles));

                try {
                    Gson gsonChild = new Gson();
                    Type typeChild = new TypeToken<List<OrderChildlist_Model>>() {
                    }.getType();
                    List<OrderChildlist_Model> tempproductList = gsonChild.fromJson(String.valueOf(resultMain.get("Products")), typeChild);
                    // Log.i("productlistfilter", String.valueOf(tempproductList));
                    for (int i = 0; i < tempproductList.size(); i++) {
                        // Log.i("productlistfilter_123", String.valueOf(tempproductList.get(i)));

                        if (tempproductList.get(i).getTitle().toLowerCase().contains(name.toLowerCase())) {
                            productList.add(tempproductList.get(i));
                            // Log.i("productlistfilter_3456", String.valueOf(productList));
                        }
                    }

                    final ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData(), spinner_container_main, btn_checkout, productList, loader);
                    adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                        @UiThread
                        @Override
                        public void onParentExpanded(int parentPosition) {

                            if (lastExpandedPosition != -1
                                    && parentPosition != lastExpandedPosition) {
                                adapter.collapseParent(lastExpandedPosition);
//                            adapter.OrderParentList.get(lastExpandedPosition).togglePlusMinusIcon();
                            }
                            lastExpandedPosition = parentPosition;
                            if (height < 1500) {
                                recyclerView.setPadding(0, 0, 0, 500);
                            }

                        }

                        @UiThread
                        @Override
                        public void onParentCollapsed(int parentPosition) {
                            if (height < 1500) {
                                recyclerView.setPadding(0, 0, 0, 0);
                            }
                        }
                    });
                    //adapter.setParentClickableViewAnimationDefaultDuration();
                    //adapter.setParentAndIconExpandOnClick(false);
//                    recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
//    private void getProductsFromCategory() throws JSONException {
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
//
//        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        // Log.i("Map", String.valueOf(map));
//
//
//        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, URL_PRODUCT, null, new Response.Listener<JSONArray>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(JSONArray result) {
//                // Log.i("result", String.valueOf(result));
//
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderChildlist_Model>>() {
//                }.getType();
//                productList = gson.fromJson(String.valueOf(result), type);
//                // Log.i("productList", String.valueOf(productList));
//
//                ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
//                //adapter.setParentClickableViewAnimationDefaultDuration();
//                //adapter.setParentAndIconExpandOnClick(false);
//                recyclerView.setAdapter(adapter);
////
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(error);
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
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(sr);
//        new MyAsyncTask().execute();
//    }

    private List<OrderParentlist_Model> initData() {
        List<OrderParentlist_Model> parentObjects = new ArrayList<>();
        for (OrderParentlist_Model title : titles) {
            // Log.i("title", String.valueOf(title.getCategoryId()));
            List<Object> childlist = new ArrayList<>();
//            childlist.add(new OrderChildlist_Model());
            for (OrderChildlist_Model product : productList) {
                // Log.i("product", String.valueOf(product.getProductCategoryId()));
                if (title.getCategoryId().equals(product.getProductCategoryId()))
                    childlist.add(product);
            }
            title.setChildList(childlist);
            parentObjects.add(title);
        }
        return parentObjects;
    }
//
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
//
//    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            while (getContext() != null && !isCancelled()) {
////                // Log.i("productsAsync", "in loop");
////                // Log.i("productsAsync", String.valueOf(selectedProductsDataList));
//                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
//                        Context.MODE_PRIVATE);
//                object_string = selectedProducts.getString("selected_products", "");
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<OrderChildlist_Model>>() {
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
}


