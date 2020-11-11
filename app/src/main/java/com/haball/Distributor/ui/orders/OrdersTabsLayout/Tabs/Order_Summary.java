package com.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.orders.Adapter.OrderSummaryAdapter;
import com.haball.Distributor.ui.orders.Adapter.OrdersItemsAdapter;
import com.haball.Distributor.ui.orders.Models.OrderItemsModel;
import com.haball.Distributor.ui.orders.OrdersTabsLayout.ui.main.PageViewModel;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.ui.Logout.LogoutFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Order_Summary extends Fragment {

    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView recyclerView1;
    private List<OrderItemsModel> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty, Token, DistributorId, CompanyId;
    private String URL_CONFIRM_ORDERS = "https://175.107.203.97:4013/api/Orders/save";
    private Button btn_confirm, btn_more_items;
    private TextView gross_amount, discount_amount, gst_amount, total_amount;
    private float totalAmount;
    private ViewPager viewpager;
    private Loader loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_order__summary, container, false);
        gross_amount = view.findViewById(R.id.gross_amount);
        discount_amount = view.findViewById(R.id.discount_amount);
        gst_amount = view.findViewById(R.id.gst_amount);
        total_amount = view.findViewById(R.id.total_amount);
        btn_more_items = view.findViewById(R.id.btn_more_items);

        loader = new Loader(getContext());

        btn_more_items.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(getContext(), "Clicked", Toast.LENGTH_LONG).show();
                SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = grossamount.edit();
                editor.putString("grossamount", "0");
                editor.apply();

//                pageViewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
//                int index = 0;
//                pageViewModel.setIndex(index);
                SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("CompanyId",
                        Context.MODE_PRIVATE);
                viewpager = getActivity().findViewById(R.id.view_pager5);
                viewpager.setCurrentItem(0);


                FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new Orders_Items_Fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

//                getFragmentManager().popBackStack();
            }
        });

        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    requestConfirmOrder();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products", "");
                editor.putString("selected_products_qty", "");
                editor.apply();
            }
        });

        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_string = selectedProducts.getString("selected_products", "");
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        Log.i("object_string", object_string);
        Log.i("object_stringqty", object_stringqty);
        Type type = new TypeToken<List<OrderItemsModel>>() {
        }.getType();
        Type typeQty = new TypeToken<List<String>>() {
        }.getType();
        selectedProductsDataList = gson.fromJson(object_string, type);
        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeQty);

        SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                Context.MODE_PRIVATE);
        gross_amount.setText(grossamount.getString("grossamount", ""));
        discount_amount.setText(" - ");

        float gstAmount = (Float.parseFloat(grossamount.getString("grossamount", "")) * 17) / 100;
        totalAmount = Float.parseFloat(grossamount.getString("grossamount", "")) + gstAmount;

        gst_amount.setText(String.valueOf(gstAmount));
        total_amount.setText(String.valueOf(totalAmount));


        recyclerView1 = view.findViewById(R.id.rv_orders_summary);
        recyclerView1.setHasFixedSize(false);
        layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(layoutManager1);

        mAdapter1 = new OrderSummaryAdapter(getContext(), selectedProductsDataList, selectedProductsQuantityList);
        recyclerView1.setAdapter(mAdapter1);
        recyclerView1.setNestedScrollingEnabled(false);

        Log.i("aaaaaa", String.valueOf(mAdapter1));

        return view;

    }

    private void requestConfirmOrder() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyId",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        int temp;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < selectedProductsDataList.size(); i++) {
            JSONObject obj = new JSONObject();
            temp = Integer.parseInt(selectedProductsDataList.get(i).getUnitPrice()) * Integer.parseInt(selectedProductsQuantityList.get(i));
            obj.put("ProductId", selectedProductsDataList.get(i).getID());
            obj.put("ProductCode", selectedProductsDataList.get(i).getCode());
            obj.put("ProductName", selectedProductsDataList.get(i).getTitle());
            obj.put("ProductShortDescription", selectedProductsDataList.get(i).getShortDescription());
            obj.put("UnitPrice", selectedProductsDataList.get(i).getUnitPrice());
            obj.put("OrderQty", selectedProductsQuantityList.get(i));
            obj.put("DiscountAmount", 0);
            obj.put("UOM", selectedProductsDataList.get(i).getUOMId());
            obj.put("UOMTitle", selectedProductsDataList.get(i).getUOMTitle());
            obj.put("Discount", 0);
            obj.put("TotalPrice", temp);
            jsonArray.put(obj);
        }
        Log.i("Array", String.valueOf(jsonArray));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", 0);
        jsonObject.put("Status", 0);
        jsonObject.put("CompanyId", CompanyId);
        jsonObject.put("DistributorId", DistributorId);
        jsonObject.put("OrderDetails", jsonArray);
        jsonObject.put("NetPrice", totalAmount);
        jsonObject.put("Discount", 0);
        jsonObject.put("TotalPrice", totalAmount);
        jsonObject.put("ShippingAddressId", 569);
        jsonObject.put("PaymentTermId", 1);
        jsonObject.put("TransportTypeId", 1);
        jsonObject.put("BillingAddressId", 569);
        loader.showLoader();
        Log.i("jsonObject", String.valueOf(jsonObject));
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_CONFIRM_ORDERS, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject result) {
                loader.hideLoader();
                Log.i("RESPONSE ORDER .. ", result.toString());
                try {
                    Toast.makeText(getContext(), "Order Request ID " + result.get("OrderNumber") + " has been submitted successfully and sent for approval.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
                loader.hideLoader();
                error.printStackTrace();
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
