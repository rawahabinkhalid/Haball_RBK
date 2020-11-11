package com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters.Order_Summary_Adapter_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters.Order_Summary_Draft_Adapter;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Draft_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.HaballError;
import com.haball.NonSwipeableViewPager;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderSummaryDraft extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView recyclerView1;
    private List<OrderChildlist_Draft_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    //    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty, Token, DistributorId, CompanyId, DealerCode;
    private String URL_CONFIRM_ORDERS = "https://175.107.203.97:4013/api/Orders/save";
    private String URL_SAVE_TEMPLATE = "https://175.107.203.97:4013/api/ordertemplate/save";
    private String URL_SAVE_DRAFT = "https://175.107.203.97:4013/api/Orders/savedraft";
    private Button btn_confirm, btn_template, btn_draft;
    private TextView gross_amount, discount_amount, gst_amount, total_amount;
    private float grossAmount = 0;
    private float totalAmount;
    private ViewPager viewpager;
    private List<OrderChildlist_Draft_Model_DistOrder> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.distri_main_orderplace_draft_summary, container, false);
//        NonSwipeableViewPager viewPager = getActivity().findViewById(R.id.view_pager5);
//        viewPager.setCurrentItem(1);

        gross_amount = view.findViewById(R.id.gross_amount);
        discount_amount = view.findViewById(R.id.discount_amount);
        gst_amount = view.findViewById(R.id.gst_amount);
        total_amount = view.findViewById(R.id.total_amount);
        btn_template = view.findViewById(R.id.btn_template);
        btn_draft = view.findViewById(R.id.btn_draft);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                try {
                    requestConfirmOrder();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products", "");
                editor.putString("selected_products_qty", "");
                editor.apply();
            }
        });
        btn_template.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                try {
                    requestSaveTemplate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products", "");
                editor.putString("selected_products_qty", "");
                editor.apply();
            }
        });
        btn_draft.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                try {
                    requestSaveDraft();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products", "");
                editor.putString("selected_products_qty", "");
                editor.apply();
            }
        });

        qtyChanged();
        new MyAsyncTask().execute();

        recyclerView1 = view.findViewById(R.id.rv_orders_summary);
        recyclerView1.setHasFixedSize(false);
        layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);

        mAdapter1 = new Order_Summary_Draft_Adapter(getContext(), selectedProductsDataList);
        recyclerView1.setAdapter(mAdapter1);
        recyclerView1.setNestedScrollingEnabled(false);

        return view;

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
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                fm.popBackStack();
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

    private void requestConfirmOrder() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

        SharedPreferences sharedPreferences4 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DealerCode = sharedPreferences4.getString("DealerCode", "");

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedProductsDataList.size(); i++) {
            JSONObject obj = new JSONObject();

            if (!selectedProductsDataList.get(i).getOrderQty().equals("0") && !selectedProductsDataList.get(i).getOrderQty().equals("")) {
                float tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
                if (selectedProductsDataList.get(i).getDiscountAmount() != null)
                    tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getDiscountAmount());
                tempAmount *= Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());

                float totalProductAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
                totalProductAmount *= Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());

                obj.put("ID", 0);
                obj.put("ProductId", selectedProductsDataList.get(i).getID());
                obj.put("ProductCode", selectedProductsDataList.get(i).getCode());
                obj.put("ProductName", selectedProductsDataList.get(i).getTitle());
                obj.put("ProductShortDescription", selectedProductsDataList.get(i).getShortDescription());
                obj.put("UnitPrice", selectedProductsDataList.get(i).getUnitPrice());
                obj.put("IsSelected", true);
                obj.put("DiscountedAmount", tempAmount);
                obj.put("OrderQty", selectedProductsDataList.get(i).getOrderQty());
                obj.put("DiscountAmount", selectedProductsDataList.get(i).getDiscountAmount());
                obj.put("UOM", selectedProductsDataList.get(i).getUOMId());
                obj.put("UOMTitle", selectedProductsDataList.get(i).getUOMTitle());
                obj.put("Discount", selectedProductsDataList.get(i).getDiscountId());
                obj.put("TotalPrice", totalProductAmount);
                jsonArray.put(obj);
            }
        }
        Log.i("Array", String.valueOf(jsonArray));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DiscountAmount", 0);
        jsonObject.put("ID", 0);
        jsonObject.put("Status", 0);
        jsonObject.put("OrderDetails", jsonArray);
        jsonObject.put("CompanyId", CompanyId);
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("TransportTypeIdFreightCharges", 0);
        jsonObject.put("parentCategory", 0);
        jsonObject.put("ProductName", "");
        jsonObject.put("NetPrice", totalAmount);
        jsonObject.put("Discount", 0);
        jsonObject.put("TotalPrice", totalAmount);
        jsonObject.put("TotalGST", gst_amount);
        jsonObject.put("TotalDiscountAmount", 0);
        jsonObject.put("BillingAddressId", 638);
        jsonObject.put("BillingAddress1", "66565");
        jsonObject.put("BillingCountryId", 1);
        jsonObject.put("BillingProvinceId", 1);
        jsonObject.put("BillingCityId", 1);
        jsonObject.put("BillingPostCode", "00000");
        jsonObject.put("ShippingAddressId", 637);
        jsonObject.put("ShippingAddress1", "fsdfsf");
        jsonObject.put("ShippingCountryId", 1);
        jsonObject.put("ShippingProvinceId", 1);
        jsonObject.put("ShippingCityId", 1);
        jsonObject.put("ShippingPostCode", "00000");
        jsonObject.put("TransportTypeId", 1);
        jsonObject.put("PaymentTermId", 1);
        jsonObject.put("DistributorDealerCode", DealerCode);

        Log.i("jsonObject", String.valueOf(jsonObject));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_CONFIRM_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject result) {
//                loader.hideLoader();
                Log.i("RESPONSE ORDER .. ", result.toString());
                try {
                    SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = grossamount.edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences selectedProducts_distributor_draft = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor selectedProducts_distributor_editor = selectedProducts_distributor_draft.edit();
                    selectedProducts_distributor_editor.clear();
                    selectedProducts_distributor_editor.apply();

                    Toast.makeText(getContext(), "Order Request ID " + result.get("OrderNumber") + " has been submitted successfully and sent for approval.", Toast.LENGTH_LONG).show();
                    Intent login_intent = new Intent(getActivity(), DistributorDashboard.class);
                    startActivity(login_intent);
                    getActivity().finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshRetailerInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
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

    private void requestSaveTemplate() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

        SharedPreferences sharedPreferences4 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DealerCode = sharedPreferences4.getString("DealerCode", "");

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedProductsDataList.size(); i++) {
            JSONObject obj = new JSONObject();

            if (!selectedProductsDataList.get(i).getOrderQty().equals("0") && !selectedProductsDataList.get(i).getOrderQty().equals("")) {
                float tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
                if (selectedProductsDataList.get(i).getDiscountAmount() != null)
                    tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getDiscountAmount());
                tempAmount *= Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());

                float totalProductAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
                totalProductAmount *= Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());

                obj.put("ID", 0);
                obj.put("ProductId", selectedProductsDataList.get(i).getID());
                obj.put("ProductCode", selectedProductsDataList.get(i).getCode());
                obj.put("ProductName", selectedProductsDataList.get(i).getTitle());
                obj.put("ProductShortDescription", selectedProductsDataList.get(i).getShortDescription());
                obj.put("UnitPrice", selectedProductsDataList.get(i).getUnitPrice());
                obj.put("IsSelected", true);
                obj.put("DiscountedAmount", tempAmount);
                obj.put("OrderQty", selectedProductsDataList.get(i).getOrderQty());
                obj.put("DiscountAmount", selectedProductsDataList.get(i).getDiscountAmount());
                obj.put("UOM", selectedProductsDataList.get(i).getUOMId());
                obj.put("UOMTitle", selectedProductsDataList.get(i).getUOMTitle());
                obj.put("Discount", selectedProductsDataList.get(i).getDiscountId());
                obj.put("TotalPrice", totalProductAmount);
                jsonArray.put(obj);
            }
        }
        Log.i("Array", String.valueOf(jsonArray));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("CompanyId", CompanyId);
        jsonObject.put("Name", "name");
        jsonObject.put("Status", 1);
        jsonObject.put("OrderTemplateDetails", jsonArray);


        Log.i("jsonObject", String.valueOf(jsonObject));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_SAVE_TEMPLATE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject result) {
//                loader.hideLoader();
                Log.i("RESPONSE ORDER .. ", result.toString());
                SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = grossamount.edit();
                editor.clear();
                editor.apply();
                SharedPreferences selectedProducts_distributor_draft = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor selectedProducts_distributor_editor = selectedProducts_distributor_draft.edit();
                selectedProducts_distributor_editor.clear();
                selectedProducts_distributor_editor.apply();

                Toast.makeText(getContext(), "Order has been saved as template successfully", Toast.LENGTH_LONG).show();
                Intent login_intent = new Intent(getActivity(), DistributorDashboard.class);
                startActivity(login_intent);
                getActivity().finish();

                refreshRetailerInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
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

    private void requestSaveDraft() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

        SharedPreferences sharedPreferences4 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DealerCode = sharedPreferences4.getString("DealerCode", "");

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyInfo",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedProductsDataList.size(); i++) {
            JSONObject obj = new JSONObject();

            if (!selectedProductsDataList.get(i).getOrderQty().equals("0") && !selectedProductsDataList.get(i).getOrderQty().equals("")) {
                float tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
                if (selectedProductsDataList.get(i).getDiscountAmount() != null)
                    tempAmount = Float.parseFloat(selectedProductsDataList.get(i).getDiscountAmount());
                tempAmount *= Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());

                float totalProductAmount = Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice());
                totalProductAmount *= Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());

                obj.put("ID", 0);
                obj.put("ProductId", selectedProductsDataList.get(i).getID());
                obj.put("ProductCode", selectedProductsDataList.get(i).getCode());
                obj.put("ProductName", selectedProductsDataList.get(i).getTitle());
                obj.put("ProductShortDescription", selectedProductsDataList.get(i).getShortDescription());
                obj.put("UnitPrice", selectedProductsDataList.get(i).getUnitPrice());
                obj.put("IsSelected", true);
                obj.put("DiscountedAmount", tempAmount);
                obj.put("OrderQty", selectedProductsDataList.get(i).getOrderQty());
                obj.put("DiscountAmount", selectedProductsDataList.get(i).getDiscountAmount());
                obj.put("UOM", selectedProductsDataList.get(i).getUOMId());
                obj.put("UOMTitle", selectedProductsDataList.get(i).getUOMTitle());
                obj.put("Discount", selectedProductsDataList.get(i).getDiscountId());
                obj.put("TotalPrice", totalProductAmount);
                jsonArray.put(obj);
            }
        }
        Log.i("Array", String.valueOf(jsonArray));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DiscountAmount", 0);
        jsonObject.put("ID", 0);
        jsonObject.put("Status", 3);
        jsonObject.put("OrderDetails", jsonArray);
        jsonObject.put("CompanyId", CompanyId);
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("TransportTypeIdFreightCharges", 0);
        jsonObject.put("parentCategory", 0);
        jsonObject.put("ProductName", "");
        jsonObject.put("NetPrice", totalAmount);
        jsonObject.put("Discount", totalAmount);
        jsonObject.put("TotalPrice", totalAmount);
        jsonObject.put("TotalGST", gst_amount);
        jsonObject.put("TotalDiscountAmount", 0);
        jsonObject.put("ShippingAddressId", 637);
        jsonObject.put("ShippingAddress1", "fsdfsf");
        jsonObject.put("ShippingCountryId", 1);
        jsonObject.put("ShippingProvinceId", 1);
        jsonObject.put("ShippingCityId", 1);
        jsonObject.put("ShippingPostCode", "00000");
        jsonObject.put("BillingAddressId", 638);
        jsonObject.put("BillingAddress1", "66565");
        jsonObject.put("BillingCountryId", 1);
        jsonObject.put("BillingProvinceId", 1);
        jsonObject.put("BillingCityId", 1);
        jsonObject.put("BillingPostCode", "00000");

        Log.i("jsonObject", String.valueOf(jsonObject));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_SAVE_DRAFT, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject result) {
//                loader.hideLoader();
                Log.i("RESPONSE ORDER .. ", result.toString());
                try {
                    SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = grossamount.edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences selectedProducts_distributor_draft = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor selectedProducts_distributor_editor = selectedProducts_distributor_draft.edit();
                    selectedProducts_distributor_editor.clear();
                    selectedProducts_distributor_editor.apply();

                    Toast.makeText(getContext(), "Order Request ID " + result.get("OrderNumber") + " has been saved as draft successfully.", Toast.LENGTH_LONG).show();
                    Intent login_intent = new Intent(getActivity(), DistributorDashboard.class);
                    startActivity(login_intent);
                    getActivity().finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshRetailerInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
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

    private void refreshRetailerInfo() {
        SharedPreferences retailerInfo = getContext().getSharedPreferences("RetailerInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = retailerInfo.edit();
        editor.putString("RetailerID", "");
        editor.apply();

//        fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.main_container, new Distributor());
//        fragmentTransaction.commit();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (getContext() != null) {
//                Log.i("async", "in async");
                if (getContext().getSharedPreferences("selectedProducts_distributor_draft",
                        Context.MODE_PRIVATE) != null) {
                    SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                            Context.MODE_PRIVATE);
                    object_string = selectedProducts.getString("selected_products", "");
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<OrderChildlist_Draft_Model_DistOrder>>() {
                    }.getType();
                    temp_list = gson.fromJson(object_string, type);
                    if (!object_string.equals("")) {
                        if (selectedProductsDataList != null) {
                            if (temp_list != selectedProductsDataList) {
//                                Log.i("async", temp_list.size() + " + " + selectedProductsDataList.size());
//                                Log.i("async", String.valueOf(temp_list));
//                                Log.i("async", String.valueOf(selectedProductsDataList));
                                selectedProductsDataList = temp_list;
                                break;
                            }
                        }
//                    break;
                    }
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
        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts_distributor_draft",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Draft_Model_DistOrder>>() {
        }.getType();
        Type typeQty = new TypeToken<List<String>>() {
        }.getType();
        selectedProductsDataList = gson.fromJson(object_string, type);
        if (selectedProductsDataList != null) {
            if (selectedProductsDataList.size() > 0) {
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                    if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsDataList.get(i).getOrderQty().equals(""))
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());
                }

                SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = grossamount.edit();
                editor.putString("grossamount", String.valueOf(grossAmount));
                editor.apply();
            }
        }

//        gross_amount.setText(String.valueOf(grossAmount));
        gross_amount.setText(String.format("%.0f", grossAmount));
        discount_amount.setText(" - ");

//        float gstAmount = (Float.parseFloat(grossamount.getString("grossamount", "")) * 17) / 100;
        float gstAmount = 0;
        totalAmount = Float.parseFloat(String.valueOf(grossAmount)) + gstAmount;
        grossAmount = 0;

//        gst_amount.setText(String.valueOf(gstAmount));
//        total_amount.setText(String.valueOf(totalAmount));
        total_amount.setText(String.format("%.0f", totalAmount));


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
