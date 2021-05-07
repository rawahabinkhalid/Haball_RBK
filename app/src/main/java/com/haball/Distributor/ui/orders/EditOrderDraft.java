package com.haball.Distributor.ui.orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderParentlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.Dist_Order_Summary;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.OrderSummaryDraft;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.NonSwipeableViewPager;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.google.gson.Gson;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderParentlist_Model;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditOrderDraft {
    public String URL_EDIT_ORDER_DRAFT = "https://175.107.203.97:4013/api/Orders/";
    public String DistributorId, Token;
    public Context mContext;

    private List<ViewOrderProductModel> RetailerDraftProductsList = new ArrayList<>();
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private float grossAmount = 0;
    private FragmentTransaction fragmentTransaction;
    private String URL_PRODUCT_CATEGORY = "https://175.107.203.97:4013/api/products/ReadCategories/0/";
    private HashMap<String, String> Categories = new HashMap<>();
    private List<OrderParentlist_Model_DistOrder> totalCategoryTitle = new ArrayList<>();
    private HashMap<String, String> Sub_Categories = new HashMap<>();
    private List<OrderParentlist_Model_DistOrder> totalSubCategoryTitle = new ArrayList<>();


    public EditOrderDraft() {
    }

    public void editDraft(final Context context, final String orderId, final String orderNumber) {
        final Loader loader = new Loader(context);
//        mContext = context;
//        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
//        // Log.i("DistributorId ", DistributorId);
//        // Log.i("Token", Token);
//
//        if (!URL_EDIT_ORDER_DRAFT.contains(orderId))
//            URL_EDIT_ORDER_DRAFT = URL_EDIT_ORDER_DRAFT + orderId;
//
////        final Context finalcontext = context;
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_EDIT_ORDER_DRAFT, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // TODO handle the response
//                // Log.i("responseDraft", String.valueOf(response));
//
//                try {
//                    JSONArray arr = response.getJSONArray("OrderDetails");
//
////                    // Log.i("jsonOrderDetail1", String.valueOf(arr));
////                    Gson gson = new Gson();
////                    String json = null;
////                    json = gson.toJson(arr);
////                    // Log.i("jsonOrderDetail", json);
//                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor_draft",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = selectedProducts.edit();
//                    editor.putString("selected_products", String.valueOf(arr));
//                    editor.apply();
//
//                    FragmentTransaction fragmentTransaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.main_container, new OrderSummaryDraft());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

        mContext = context;
        loader.showLoader();
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        // Log.i("Token", Token);

        if (!URL_EDIT_ORDER_DRAFT.contains(orderId))
            URL_EDIT_ORDER_DRAFT = URL_EDIT_ORDER_DRAFT + orderId;

//        final Context finalcontext = context;
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_EDIT_ORDER_DRAFT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                loader.hideLoader();
                // TODO handle the response
                // Log.i("responseDraft", String.valueOf(response));

                getParentCategory(loader, orderId, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);
                error.printStackTrace();
                loader.hideLoader();
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(request);


    }

    private void getParentCategory(final Loader loader, final String orderId, final JSONObject response) {
        try {

            final String CompanyName = response.getString("CompanyName");
            final String CompanyId = response.getString("CompanyId");


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
                    loader.hideLoader();
                    // Log.i("result", String.valueOf(result));
                    for (int i = 0; i < result.length(); i++) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<OrderParentlist_Model_DistOrder>>() {
                        }.getType();
                        Object item = null;
                        try {
                            item = result.get(i);
                            // `instanceof` tells us whether the object can be cast to a specific type
                            if (item instanceof JSONArray) {
                                JSONArray jsonarray = (JSONArray) item;
                                for (int j = 0; j < ((JSONArray) item).length(); j++) {
                                    OrderParentlist_Model_DistOrder tempModel = gson.fromJson(jsonarray.get(j).toString(), OrderParentlist_Model_DistOrder.class);
                                    Sub_Categories.put(tempModel.getID(), tempModel.getID());
                                    totalSubCategoryTitle.add(tempModel);
                                }
                            } else if (item instanceof JSONObject) {
                                OrderParentlist_Model_DistOrder tempModel = gson.fromJson(item.toString(), OrderParentlist_Model_DistOrder.class);
                                Categories.put(tempModel.getID(), tempModel.getID());
                                totalCategoryTitle.add(tempModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    JSONArray arr = null;
                    try {
                        arr = response.getJSONArray("OrderDetails");

                        // Log.i("jsonOrderDetail1", String.valueOf(arr));
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<ViewOrderProductModel>>() {
                        }.getType();
                        RetailerDraftProductsList = gson.fromJson(arr.toString(), type);
                        for (int i = 0; i < RetailerDraftProductsList.size(); i++) {
                            // Log.i("jsonOrderDetail", String.valueOf(RetailerDraftProductsList.get(i).getProductName()));
                            //                                                                                                                                                                                                                                                                                                  public OrderChildlist_Model_DistOrder(String ID, String companyId, String categoryId, String code, String title, String shortDescription, String longDescription, String unitPrice, String categoryTitle, String packSize, String UOMId, String UOMTitle, String imageData, String imageType, String discountId, String effectiveDate, String expiryDate, String isPercentage, String discountValue, String discountAmount) {
                            selectedProductsDataList.add(new OrderChildlist_Model_DistOrder(RetailerDraftProductsList.get(i).getID(), RetailerDraftProductsList.get(i).getCompanyId(), RetailerDraftProductsList.get(i).getCategoryId(), RetailerDraftProductsList.get(i).getProductCode(), RetailerDraftProductsList.get(i).getTitle(), RetailerDraftProductsList.get(i).getShortDescription(), RetailerDraftProductsList.get(i).getLongDescription(), RetailerDraftProductsList.get(i).getUnitPrice(), RetailerDraftProductsList.get(i).getCategoryTitle(), RetailerDraftProductsList.get(i).getPackSize(), RetailerDraftProductsList.get(i).getUOMId(), RetailerDraftProductsList.get(i).getUOMTitle(), RetailerDraftProductsList.get(i).getImageData(), RetailerDraftProductsList.get(i).getImageType(), RetailerDraftProductsList.get(i).getDiscountId(), RetailerDraftProductsList.get(i).getEffectiveDate(), RetailerDraftProductsList.get(i).getExpiryDate(), RetailerDraftProductsList.get(i).getIsPercentage(), RetailerDraftProductsList.get(i).getDiscountValue(), RetailerDraftProductsList.get(i).getDiscountAmount()));
                            selectedProductsQuantityList.add(RetailerDraftProductsList.get(i).getOrderQty());
                        }

                        grossAmount = 0;

                        if (selectedProductsDataList.size() > 0) {
                            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                                // Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                                // Log.i("qty", selectedProductsQuantityList.get(i));
                                if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                            }
                            SharedPreferences grossamount = mContext.getSharedPreferences("grossamount",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_grossamount = grossamount.edit();
                            editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                            editor_grossamount.apply();
                            grossAmount = 0;
                        }
                        String selectedCategoryId = selectedProductsDataList.get(0).getCategoryId();
                        String parentId = "";
                        int parentId_index = -1;
                        for (int i = 0; i < totalSubCategoryTitle.size(); i++) {
                            if (totalSubCategoryTitle.get(i).getID().equals(selectedCategoryId))
                                parentId = totalSubCategoryTitle.get(i).getParentId();
                        }

                        for (int i = 0; i < totalCategoryTitle.size(); i++) {
                            if (totalCategoryTitle.get(i).getID().equals(parentId)) {
                                parentId_index = i;
                                break;
                            }
                        }


                        SharedPreferences companyInfo = mContext.getSharedPreferences("CompanyInfo",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor companyInfo_editor = companyInfo.edit();
                        companyInfo_editor.putString("CategoryIndex", String.valueOf(parentId_index));
                        companyInfo_editor.apply();

                        final SharedPreferences orderCheckout_SP = mContext.getSharedPreferences("orderCheckout",
                                Context.MODE_PRIVATE);

                        SharedPreferences.Editor orderCheckout_editor = orderCheckout_SP.edit();
                        orderCheckout_editor.putString("orderCheckout", "orderCheckout");
                        orderCheckout_editor.apply();

                        String json = gson.toJson(selectedProductsDataList);
                        String jsonqty = gson.toJson(selectedProductsQuantityList);
                        // Log.i("debugOrder_jsonqty", jsonqty);
                        // Log.i("debugOrder_json", json);
                        SharedPreferences selectedProducts = mContext.getSharedPreferences("selectedProducts_distributor",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = selectedProducts.edit();
                        editor.putString("CompanyName", CompanyName);
                        editor.putString("ID", orderId);
                        editor.putString("selected_products", json);
                        editor.putString("selected_products_qty", jsonqty);
                        editor.apply();

                        SharedPreferences.Editor editor_company = companyInfo.edit();
                        editor_company.putString("CompanyId", CompanyId);
                        editor_company.putString("ID", orderId);
                        editor_company.apply();

                        SharedPreferences selectedDraft = mContext.getSharedPreferences("FromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorDraft = selectedDraft.edit();
                        editorDraft.putString("fromDraft", "draft");
                        editorDraft.apply();

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Order_PlaceOrder()).addToBackStack("null");
                        fragmentTransaction.commit();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new HaballError().printErrorMessage(mContext, error);
                    new ProcessingError().showError(mContext);

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

                    mContext).

                    add(sr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
