package com.haball.Distributor.ui.orders.OrdersTabsLayout.Tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.orders.Adapter.OrdersItemsAdapter;
import com.haball.Distributor.ui.orders.Models.OrderItemsModel;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Payment.ConsolidatePaymentsModel;
import com.haball.Payment.Consolidate_Fragment_Adapter;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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

public class Orders_Items_Fragment extends Fragment {

    private RecyclerView itemsSelect_Rv;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.LayoutManager layoutManager1;
    private Button place_item_button;
    String CompanyId = "2";
    private String PRODUCTS_URL = "https://175.107.203.97:4013/api/products/ReadProductsByCategories/";
    private String PRODUCTS_CATEGORY_URL = "https://175.107.203.97:4013/api/products/ReadCategories/0/";

    private String Token, DistributorId, object_string, object_stringqty;
    private List<OrderItemsModel> selectedProductsDataList = new ArrayList<>();
    private List<OrderItemsModel> temp_list = new ArrayList<>();
    private List<String> temp_listqty = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private List<OrderItemsModel> ProductsDataList = new ArrayList<>();
    private int i = 0;
    String string_id;
    private float grossAmount = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.orders_items_fragments, container, false);

        place_item_button = view.findViewById(R.id.place_item_button);

        holderitems(view);

        return view;
    }

    private boolean enableCheckout() {
        Log.i("checkout", "in checkout");
        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_string = selectedProducts.getString("selected_products", "");
        Log.i("object_string", object_string);
        Type type = new TypeToken<List<OrderItemsModel>>() {
        }.getType();
        if (!object_string.equals(""))
            selectedProductsDataList = gson.fromJson(object_string, type);
        if (selectedProductsDataList != null) {
            if (selectedProductsDataList.size() > 0) {
                place_item_button.setBackgroundResource(R.drawable.button_round);
                place_item_button.setEnabled(true);
                place_item_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewPager viewPager = getActivity().findViewById(R.id.view_pager5);
                        SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts",
                                Context.MODE_PRIVATE);
                        Gson gson = new Gson();
                        object_stringqty = selectedProducts.getString("selected_products_qty", "");
                        object_string = selectedProducts.getString("selected_products", "");
                        Type type = new TypeToken<List<OrderItemsModel>>() {
                        }.getType();
                        Type typeString = new TypeToken<List<String>>() {
                        }.getType();
                        selectedProductsDataList = gson.fromJson(object_string, type);
                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
                        if (selectedProductsDataList.size() > 0) {
                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                                Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                                Log.i("qty", selectedProductsQuantityList.get(i));
                                if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                            }

                            SharedPreferences grossamount = getContext().getSharedPreferences("grossamount",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = grossamount.edit();
                            editor.putString("grossamount", String.valueOf(grossAmount));
                            editor.apply();
                            // Toast.makeText(getContext(), "Total Amount: " + grossAmount, Toast.LENGTH_SHORT).show();
                            grossAmount = 0;
                            viewPager.setCurrentItem(1);
                            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new Order_Summary());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    private void fetchProductsData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        SharedPreferences sharedPreferences2 = this.getActivity().getSharedPreferences("CompanyId",
                Context.MODE_PRIVATE);
        CompanyId = sharedPreferences2.getString("CompanyId", "");

        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);
        PRODUCTS_CATEGORY_URL = PRODUCTS_CATEGORY_URL + CompanyId;
        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.GET, PRODUCTS_CATEGORY_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray result) {
//                loader.hideLoader();
                Log.i("CATEGORY DATA .. ", result.toString());
                for (i = 0; i < result.length(); i++) {
                    try {
                        JSONObject obj = result.getJSONObject(i);
                        string_id = String.valueOf(obj.get("ID"));
                        PRODUCTS_URL = PRODUCTS_URL + obj.get("ID") + "/" + CompanyId;
                        Log.i("PRODUCTS_URL", PRODUCTS_URL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MyJsonArrayRequest sr1 = new MyJsonArrayRequest(Request.Method.GET, PRODUCTS_URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray resultProduct) {
                            Log.i("PRODUCTS DATA .. ", resultProduct.toString());
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<OrderItemsModel>>() {
                            }.getType();
                            ProductsDataList.addAll((Collection<? extends OrderItemsModel>) gson.fromJson(resultProduct.toString(), type));
                            mAdapter1 = new OrdersItemsAdapter(getContext(), ProductsDataList);
                            itemsSelect_Rv.setAdapter(mAdapter1);
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
                    Volley.newRequestQueue(getContext()).add(sr1);

                    PRODUCTS_URL = "https://175.107.203.97:4013/api/products/ReadProductsByCategories/";
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

        Volley.newRequestQueue(getContext()).add(sr);
        new MyAsyncTask().execute();
    }

    private void holderitems(final View root) {
        itemsSelect_Rv = (RecyclerView) root.findViewById(R.id.rv_items_orders);

        itemsSelect_Rv.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getContext());
        itemsSelect_Rv.setLayoutManager(layoutManager1);

        /* ****************************** */
        /* Smooth Scroll in Recycler View */
        itemsSelect_Rv.setNestedScrollingEnabled(false);
        /* ****************************** */
        fetchProductsData();
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

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (getContext() != null) {
                SharedPreferences selectedProducts = getContext().getSharedPreferences("selectedProducts",
                        Context.MODE_PRIVATE);
                object_string = selectedProducts.getString("selected_products", "");
                Gson gson = new Gson();
                Type type = new TypeToken<List<OrderItemsModel>>() {
                }.getType();
                temp_list = gson.fromJson(object_string, type);
                object_stringqty = selectedProducts.getString("selected_products_qty", "");
                Type typestr = new TypeToken<List<String>>() {
                }.getType();
                temp_listqty = gson.fromJson(object_stringqty, typestr);
                if (!object_string.equals("")) {
                    if (selectedProductsDataList != null) {
                        if (temp_list != selectedProductsDataList) {
                            selectedProductsDataList = temp_list;
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
            if (getContext() != null)
                enableCheckout();
            mAdapter1 = new OrdersItemsAdapter(getContext(), ProductsDataList);
            itemsSelect_Rv.setAdapter(mAdapter1);

        }
    }
}