package com.haball.Retailor.ui.Place_Order.ui.main.Tabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.NonSwipeableViewPager;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Make_Payment.PaymentScreen3Fragment_Retailer;
import com.haball.Retailor.ui.Place_Order.ui.main.Adapters.Order_Summary_Adapter;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Retailer_Order_Summary extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView recyclerView1;
    private List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty, Token, DistributorId, CompanyId;
    private String URL_CONFIRM_ORDERS = "http://175.107.203.97:4014/api/Orders/saveOrder";
    //    private String URL_SAVE_TEMPLATE = "https://175.107.203.97:4013/api/ordertemplate/save";
    private String URL_SAVE_DRAFT = "http://175.107.203.97:4014/api/Orders/draft";
    //    private Button btn_confirm, btn_template, btn_draft, btn_add_product;
    private Button btn_confirm, btn_draft, btn_add_product;
    private TextView gross_amount, discount_amount, total_amount;
    //    private TextView gst_amount;
    private float totalAmount;
    private ViewPager viewpager;
    private List<OrderChildlist_Model> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_order__summary, container, false);
//        gross_amount = view.findViewById(R.id.gross_amount);
        discount_amount = view.findViewById(R.id.discount_amount);
//        gst_amount = view.findViewById(R.id.gst_amount);
        total_amount = view.findViewById(R.id.total_amount);
        btn_confirm = view.findViewById(R.id.btn_confirm);

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        SharedPreferences add_more_product = getContext().getSharedPreferences("add_more_product",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = add_more_product.edit();
        editor1.putString("add_more_product", "");
        editor1.apply();
//        SharedPreferences selectedDraft = getContext().getSharedPreferences("FromDraft",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editorDraft = selectedDraft.edit();
//        editorDraft.putString("fromDraft", "");
//        editorDraft.apply();

        btn_add_product = view.findViewById(R.id.btn_add_product);

        btn_draft = view.findViewById(R.id.place_item_button);

        InputMethodManager imm = (InputMethodManager) (getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        final Loader loader = new Loader(getContext());

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                loader.showLoader();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loader.hideLoader();
                                try {
                                    requestConfirmOrder();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = selectedProducts.edit();
                                editor.putString("selected_products", "");
                                editor.putString("selected_products_qty", "");
                                editor.apply();
                            }
                        }, 3000);
                    }
                });
            }
        });


        btn_add_product.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                loader.showLoader();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loader.hideLoader();
                                NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager_rpoid);
//                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
//                        Context.MODE_PRIVATE);
//                Gson gson = new Gson();
//                object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                object_string = selectedProducts.getString("selected_products", "");
//                Type type = new TypeToken<List<OrderChildlist_Model>>() {
//                }.getType();
//                Type typeString = new TypeToken<List<String>>() {
//                }.getType();
//                selectedProductsDataList = gson.fromJson(object_string, type);
//                selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
//                        if (selectedProductsDataList.size() > 0) {
//                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                                Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                                Log.i("qty", selectedProductsQuantityList.get(i));
//                                if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
//                            }
                                float grossAmount = 0;
                                if (selectedProductsDataList == null) {
                                    Log.i("debugOrder_ListIsNull", "selected product list is null");
                                    SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                                            Context.MODE_PRIVATE);
                                    Gson gson = new Gson();
                                    object_string = selectedProducts.getString("selected_products", "");
                                    object_stringqty = selectedProducts.getString("selected_products_qty", "");
                                    Log.i("object_string", object_string);
                                    Log.i("object_stringqty", object_stringqty);
                                    Type type = new TypeToken<List<OrderChildlist_Model>>() {
                                    }.getType();
                                    Type typeQty = new TypeToken<List<String>>() {
                                    }.getType();
                                    selectedProductsDataList = gson.fromJson(object_string, type);
                                    selectedProductsQuantityList = gson.fromJson(object_stringqty, typeQty);
                                }
                                if (selectedProductsDataList.size() > 0) {
                                    for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                        Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                        Log.i("qty", selectedProductsQuantityList.get(i));
                                        if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                            grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                                    }
                                    SharedPreferences add_more_product = getContext().getSharedPreferences("add_more_product",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = add_more_product.edit();
                                    editor1.putString("add_more_product", "fromAddMore");
                                    editor1.apply();

                                    SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = grossamount.edit();
                                    editor.putString("grossamount", String.valueOf(grossAmount));
                                    editor.apply();
//                    Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
                                    grossAmount = 0;
                                    viewPager.setCurrentItem(0);
                                    FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.main_container_ret, new Retailer_OrderPlace_retailer_dashboarad());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
//                try {
//                    requestSaveTemplate();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = selectedProducts.edit();
//                editor.putString("selected_products", "");
//                editor.putString("selected_products_qty", "");
//                editor.apply();
                                }
                            }
                        }, 3000);
                    }
                });

            }
        });
        btn_draft.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                loader.showLoader();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loader.hideLoader();

                                try {
                                    requestSaveDraft();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = selectedProducts.edit();
                                editor.putString("selected_products", "");
                                editor.putString("selected_products_qty", "");
                                editor.apply();
                            }
                        }, 3000);
                    }
                });
            }
        });


//        qtyChanged();
//        new MyAsyncTask().execute();
        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_string = selectedProducts.getString("selected_products", "");
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        Log.i("object_string", object_string);
        Log.i("object_stringqty", object_stringqty);
        Type type = new TypeToken<List<OrderChildlist_Model>>() {
        }.getType();
        Type typeQty = new TypeToken<List<String>>() {
        }.getType();
        selectedProductsDataList = gson.fromJson(object_string, type);
        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeQty);


        recyclerView1 = view.findViewById(R.id.rv_orders_summary);
        recyclerView1.setHasFixedSize(false);
        layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);

        mAdapter1 = new Order_Summary_Adapter(getActivity(), getContext(), selectedProductsDataList, selectedProductsQuantityList, btn_confirm, btn_draft, total_amount, discount_amount);
        recyclerView1.setAdapter(mAdapter1);
        recyclerView1.setNestedScrollingEnabled(false);
//
//        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = selectedProducts.edit();
//        editor.putString("selected_products", "");
//        editor.putString("selected_products_qty", "");
//        editor.apply();
//
//        SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
//                Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor_grossamount = grossamount.edit();
//        editor_grossamount.putString("grossamount", "0");
//        editor_grossamount.apply();

//        Log.i("aaaaaa", String.valueOf(mAdapter1));

        return view;

    }

    //
//    private void requestSaveTemplate() throws JSONException {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//
//        SharedPreferences sharedPreferences4 = this.getActivity().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DealerCode = sharedPreferences4.getString("DealerCode", "");
//
//        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
//                Context.MODE_PRIVATE);
//        CompanyId = sharedPreferences2.getString("CompanyId", "");
//
//        JSONArray jsonArray = new JSONArray();
//        for (int i = 0; i < selectedProductsDataList.size(); i++) {
//            JSONObject obj = new JSONObject();
//
//            if (!selectedProductsQuantityList.get(i).equals("0") && !selectedProductsQuantityList.get(i).equals("")) {
//                float tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
//                if (selectedProductsDataList.get(i).getDiscountAmount() != null)
//                    tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getDiscountAmount());
//                tempAmount *= Float.parseFloat(selectedProductsQuantityList.get(i));
//
//                float totalProductAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
//                totalProductAmount *= Float.parseFloat(selectedProductsQuantityList.get(i));
//
//                obj.put("ID", 0);
//                obj.put("ProductId", selectedProductsDataList.get(i).getID());
//                obj.put("ProductCode", selectedProductsDataList.get(i).getCode());
//                obj.put("ProductName", selectedProductsDataList.get(i).getTitle());
//                obj.put("ProductShortDescription", selectedProductsDataList.get(i).getShortDescription());
//                obj.put("UnitPrice", selectedProductsDataList.get(i).getUnitPrice());
//                obj.put("IsSelected", true);
//                obj.put("DiscountedAmount", tempAmount);
//                obj.put("OrderQty", selectedProductsQuantityList.get(i));
//                obj.put("DiscountAmount", selectedProductsDataList.get(i).getDiscountAmount());
//                obj.put("UOM", selectedProductsDataList.get(i).getUOMId());
//                obj.put("UOMTitle", selectedProductsDataList.get(i).getUOMTitle());
//                obj.put("Discount", selectedProductsDataList.get(i).getDiscountId());
//                obj.put("TotalPrice", totalProductAmount);
//                jsonArray.put(obj);
//            }
//        }
//        Log.i("Array", String.valueOf(jsonArray));
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("DistributorId", DistributorId);
//        jsonObject.put("CompanyId", CompanyId);
//        jsonObject.put("Name", "name");
//        jsonObject.put("Status", 1);
//        jsonObject.put("OrderTemplateDetails", jsonArray);
//
//
//        Log.i("jsonObject", String.valueOf(jsonObject));
//        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_SAVE_TEMPLATE, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(final JSONObject result) {
//                Log.i("RESPONSE ORDER .. ", result.toString());
//                SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = grossamount.edit();
//                editor.clear();
//                editor.apply();
//                SharedPreferences selectedProducts_distributor = getContext().getSharedPreferences("selectedProducts_distributor",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor selectedProducts_distributor_editor = selectedProducts_distributor.edit();
//                selectedProducts_distributor_editor.clear();
//                selectedProducts_distributor_editor.apply();
//
//                Toast.makeText(getContext(), "Order has been saved as template successfully", Toast.LENGTH_LONG).show();
//                Intent login_intent = new Intent(getActivity(), RetailorDashboard.class);
//                startActivity(login_intent);
//                getActivity().finish();
//
//                refreshRetailerInfo();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(error);
//                error.printStackTrace();
//                refreshRetailerInfo();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("Content-Type", "application/json");
//                return params;
//            }
//        };
//
//        Volley.newRequestQueue(getContext()).add(sr);
//    }
    private void disableAllButtons() {
        btn_draft.setEnabled(false);
        btn_draft.setBackgroundResource(R.drawable.button_grey_round);
        btn_confirm.setEnabled(false);
        btn_confirm.setBackgroundResource(R.drawable.button_grey_round);
        btn_add_product.setEnabled(false);
        btn_add_product.setBackgroundResource(R.drawable.button_grey_round);
    }

    private void enableAllButtons() {
        btn_draft.setEnabled(true);
        btn_draft.setBackgroundResource(R.drawable.button_round);
        btn_confirm.setEnabled(true);
        btn_confirm.setBackgroundResource(R.drawable.button_round);
        btn_add_product.setEnabled(true);
        btn_add_product.setBackgroundResource(R.drawable.button_round);
    }

    private void requestSaveDraft() throws JSONException {
        disableAllButtons();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("DealerInfo",
                Context.MODE_PRIVATE);
        String DealerCode = sharedPreferences1.getString("DealerCode", "");
        String OrderId = sharedPreferences1.getString("orderId", "0");

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedProductsDataList.size(); i++) {
            JSONObject obj = new JSONObject();

            if (!selectedProductsQuantityList.get(i).equals("0") && !selectedProductsQuantityList.get(i).equals("")) {
                obj.put("ProductId", selectedProductsDataList.get(i).getProductId());
                obj.put("OrderQty", selectedProductsQuantityList.get(i));
                jsonArray.put(obj);
            }
        }
//        Log.i("Array", String.valueOf(jsonArray));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", Integer.parseInt(OrderId));
//        jsonObject.put("RetailerID", RetailerID);
        jsonObject.put("DealerCode", DealerCode);
        jsonObject.put("OrderDetails", jsonArray);
//        jsonObject.put("NetPrice", totalAmount);
//        jsonObject.put("Discount", totalAmount);
//        jsonObject.put("TotalPrice", totalAmount);
//        jsonObject.put("TotalGST", gst_amount);
//        jsonObject.put("TotalDiscountAmount", 0);

//        Log.i("jsonObject", String.valueOf(jsonObject));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_SAVE_DRAFT, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject result) {
                enableAllButtons();
                Log.i("RESPONSE ORDER .. ", result.toString());
                try {
                    SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = grossamount.edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences selectedProducts_distributor = getContext().getSharedPreferences("selectedProducts_distributor",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor selectedProducts_distributor_editor = selectedProducts_distributor.edit();
                    selectedProducts_distributor_editor.clear();
                    selectedProducts_distributor_editor.apply();

//                    Toast.makeText(getContext(), "Order Request ID " + result.get("OrderNumber") + " has been saved as draft successfully.", Toast.LENGTH_LONG).show();
//                    Intent login_intent = new Intent(getActivity(), RetailorDashboard.class);
//                    startActivity(login_intent);
//                    getActivity().finish();

                    final Dialog fbDialogue = new Dialog(getActivity());
                    //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                    fbDialogue.setContentView(R.layout.password_updatepopup);
                    TextView tv_pr1, txt_header1;
                    txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                    tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                    txt_header1.setText("Order Saved");
                    tv_pr1.setText("Your Order ID " + result.getString("OrderNumber") + " has been saved successfully.");
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
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container_ret, new PaymentScreen3Fragment_Retailer());
//                        fragmentTransaction.commit();

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshRetailerInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                enableAllButtons();
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();
                refreshRetailerInfo();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void requestConfirmOrder() throws JSONException {
        disableAllButtons();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("DealerInfo",
                Context.MODE_PRIVATE);
        String DealerCode = sharedPreferences1.getString("DealerCode", "");
        String OrderId = sharedPreferences1.getString("orderId", "0");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedProductsDataList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("ProductId", selectedProductsDataList.get(i).getProductId());
            obj.put("OrderQty", selectedProductsQuantityList.get(i));
            if (!selectedProductsQuantityList.get(i).equals("0") && !selectedProductsQuantityList.get(i).equals(""))
                jsonArray.put(obj);
        }
//        Log.i("Array", String.valueOf(jsonArray));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", Integer.parseInt(OrderId));
//        jsonObject.put("ProductName", "");
        jsonObject.put("DealerCode", DealerCode);
//        jsonObject.put("RetailerID", RetailerID);
        jsonObject.put("OrderDetails", jsonArray);
//        jsonObject.put("NetPrice", totalAmount);
//        jsonObject.put("Discount", 0);
//        jsonObject.put("TotalPrice", totalAmount);

//        Log.i("jsonObject", String.valueOf(jsonObject));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_CONFIRM_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject result) {
                enableAllButtons();

                final Dialog fbDialogue = new Dialog(getActivity());
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                txt_header1.setText("Order Created");
                try {
                    tv_pr1.setText("Your Order ID " + result.getString("OrderNumber") + " has been created successfully.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container_ret, new PaymentScreen3Fragment_Retailer());
//                        fragmentTransaction.commit();

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
//                Log.i("RESPONSE ORDER .. ", result.toString());
//                try {
//                    Toast.makeText(getContext(), "Order Request ID " + result.get("OrderNumber") + " has been submitted successfully and sent for approval.", Toast.LENGTH_LONG).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                refreshRetailerInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                enableAllButtons();
                new HaballError().printErrorMessage(getContext(), error);
                error.printStackTrace();
                refreshRetailerInfo();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(sr);
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
                    showDiscardDialog();
                    return true;
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

    private void refreshRetailerInfo() {
        SharedPreferences retailerInfo = getContext().getSharedPreferences("RetailerInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = retailerInfo.edit();
        editor.putString("RetailerCode", "");
        editor.putString("RetailerID", "");
        editor.apply();
//
//        Intent login_intent = new Intent(getActivity(), RetailorDashboard.class);
//        startActivity(login_intent);
//        getActivity().finish();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (getContext() != null) {
//                Log.i("async", "in async");
                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                        Context.MODE_PRIVATE);
                object_string = selectedProducts.getString("selected_products", "");
                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderChildlist_Model>>() {
                }.getType();
                temp_list = gson.fromJson(object_string, type);
                object_stringqty = selectedProducts.getString("selected_products_qty", "");
//                Log.i("qty_async", object_stringqty);
                Type typestr = new TypeToken<List<String>>() {
                }.getType();
                temp_listqty = gson.fromJson(object_stringqty, typestr);
                if (!object_stringqty.equals("")) {
                    if (selectedProductsQuantityList != null) {
                        if (temp_listqty != selectedProductsQuantityList) {
                            selectedProductsQuantityList = temp_listqty;
                            break;
                        }
                    }
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (getContext() != null) {
//                Log.i("async", "in async else");
                qtyChanged();
                new MyAsyncTask().execute();
            }
//            mAdapter1 = new OrdersItemsAdapter(getContext(), ProductsDataList);
//            itemsSelect_Rv.setAdapter(mAdapter1);

//            ParentListAdapter adapter = new ParentListAdapter(getActivity(), initData());
//            adapter.setParentClickableViewAnimationDefaultDuration();
//            adapter.setParentAndIconExpandOnClick(true);
//            recyclerView.setAdapter(adapter);

        }
    }

    private void qtyChanged() {
        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_retailer_own",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_string = selectedProducts.getString("selected_products", "");
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        Log.i("object_string", object_string);
        Log.i("object_stringqty", object_stringqty);
        Type type = new TypeToken<List<OrderChildlist_Model>>() {
        }.getType();
        Type typeQty = new TypeToken<List<String>>() {
        }.getType();
        selectedProductsDataList = gson.fromJson(object_string, type);
        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeQty);

        SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                Context.MODE_PRIVATE);
//        gross_amount.setText(grossamount.getString("grossamount", "0"));
//        float temp_grossAmount = Float.parseFloat(grossamount.getString("grossamount", "0"));
//        gross_amount.setText(String.format("%.0f", temp_grossAmount));
        discount_amount.setText("0.00");

//        float gstAmount = (Float.parseFloat(grossamount.getString("grossamount", "")) * 17) / 100;
        float gstAmount = 0;
        totalAmount = Float.parseFloat(grossamount.getString("grossamount", "0")) + gstAmount;
//        if (totalAmount <= 0) {
//            btn_draft.setEnabled(false);
//            btn_draft.setBackgroundResource(R.drawable.button_grey_round);
//            btn_confirm.setEnabled(false);
//            btn_confirm.setBackgroundResource(R.drawable.button_grey_round);
//
//        } else {
//            btn_draft.setEnabled(true);
//            btn_draft.setBackgroundResource(R.drawable.button_round);
//            btn_confirm.setEnabled(true);
//            btn_confirm.setBackgroundResource(R.drawable.button_round);
//
//        }
//        float grossAmount = 0;
//        if(selectedProductsDataList != null) {
//            if (selectedProductsDataList.size() > 0) {
//                for (int i = 0; i < selectedProductsDataList.size(); i++) {
//                    Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
//                    Log.i("qty", selectedProductsQuantityList.get(i));
//                    if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
//                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
//                }
//            }
//
//        }

//        gst_amount.setText(String.valueOf(gstAmount));
//        total_amount.setText(String.valueOf(totalAmount));
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString3 = formatter1.format(totalAmount);
        total_amount.setText(yourFormattedString3);


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
