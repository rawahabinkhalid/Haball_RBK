package com.haball.Retailor.ui.Dashboard;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.util.JsonReader;
        import android.util.Log;
        import android.widget.Toast;

        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.HurlStack;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.gson.reflect.TypeToken;
        import com.haball.NonSwipeableViewPager;
        import com.haball.R;
        import com.haball.Registration.BooleanRequest;
        import com.google.gson.Gson;
        import com.haball.Retailor.ui.Place_Order.Retailer_Place_Order;
        import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
        import com.haball.Retailor.ui.RetailerOrder.RetailerOrdersModel.RetailerViewOrderProductModel;
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
    public String URL_EDIT_ORDER_DRAFT = "http://175.107.203.97:4014/api/Orders/";
    public String Token;
    public Context mContext;
    private List<RetailerViewOrderProductModel> RetailerDraftProductsList = new ArrayList<>();
    private List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private float grossAmount = 0;
    private FragmentTransaction fragmentTransaction;


    public EditOrderDraft() {
    }

    public void editDraft(final Context context, final String orderId, final String orderNumber) {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

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
                // TODO handle the response
                // Log.i("responseDraft", String.valueOf(response));

                try {
                    JSONArray arr = response.getJSONArray("OrderDetails");
                    JSONObject obj = response.getJSONObject("OrderPaymentDetails");
                    String CompanyName = obj.getString("CompanyName");
                    String DealerCode = obj.getString("DealerCode");

                    SharedPreferences sharedPreferences1 = context.getSharedPreferences("DealerInfo",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor sharedPreferences1_editor = sharedPreferences1.edit();
                    sharedPreferences1_editor.putString("DealerCode", DealerCode);
                    sharedPreferences1_editor.putString("orderId", orderId);
                    sharedPreferences1_editor.apply();

                    // Log.i("jsonOrderDetail1", String.valueOf(arr));
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<RetailerViewOrderProductModel>>() {
                    }.getType();
                    RetailerDraftProductsList = gson.fromJson(arr.toString(), type);
                    for(int i = 0; i < RetailerDraftProductsList.size(); i++) {
                        // Log.i("jsonOrderDetail", String.valueOf(RetailerDraftProductsList.get(i).getProductName()));
                        selectedProductsDataList.add(new OrderChildlist_Model(RetailerDraftProductsList.get(i).getProductId(), RetailerDraftProductsList.get(i).getProductCode(), RetailerDraftProductsList.get(i).getProductName(), RetailerDraftProductsList.get(i).getUnitPrice(), "0", RetailerDraftProductsList.get(i).getUOMTitle(), RetailerDraftProductsList.get(i).getDiscount(), "1"));
                        selectedProductsQuantityList.add(RetailerDraftProductsList.get(i).getOrderQty());
                    }

                    grossAmount = 0;

                    if (selectedProductsDataList.size() > 0) {
                        for (int i = 0; i < selectedProductsDataList.size(); i++) {
                            // Log.i("unit price", selectedProductsDataList.get(i).getProductUnitPrice());
                            // Log.i("qty", selectedProductsQuantityList.get(i));
                            if (!selectedProductsDataList.get(i).getProductUnitPrice().equals("") && !selectedProductsQuantityList.get(i).equals(""))
                                grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getProductUnitPrice()) * Float.parseFloat(selectedProductsQuantityList.get(i));
                        }
                        SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_grossamount = grossamount.edit();
                        editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                        editor_grossamount.apply();
                        grossAmount = 0;
                    }

                    String json = gson.toJson(selectedProductsDataList);
                    String jsonqty = gson.toJson(selectedProductsQuantityList);
                    // Log.i("debugOrder_jsonqty", jsonqty);
                    // Log.i("debugOrder_json", json);
                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer_own",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = selectedProducts.edit();
                    editor.putString("CompanyName", CompanyName);
                    editor.putString("selected_products", json);
                    editor.putString("selected_products_qty", jsonqty);
                    editor.apply();

                    SharedPreferences selectedDraft = context.getSharedPreferences("FromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorDraft = selectedDraft.edit();
                    editorDraft.putString("fromDraft", "draft");
                    editorDraft.apply();

                    fragmentTransaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("null");
                    fragmentTransaction.commit();

//
//                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.main_container_ret, new Retailer_Place_Order()).addToBackStack("tag");
//                    fragmentTransaction.commit();

//                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor_draft",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = selectedProducts.edit();
//                    editor.putString("selected_products", String.valueOf(arr));
//                    editor.apply();
//
//                    FragmentTransaction fragmentTransaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.main_container_ret, new OrderSummaryDraft());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(request);


    }
}

