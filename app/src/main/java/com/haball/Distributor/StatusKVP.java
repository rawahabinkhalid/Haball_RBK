package com.haball.Distributor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.haball.HaballError;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatusKVP {
    private String URL_OrderStatus = "https://175.107.203.97:4013/api/lookup/ORDER_STATUS";
    private String URL_InvoiceStatus = "https://175.107.203.97:4013/api/lookup/INVOICE_STATUS";
    private String URL_PREPAIDStatus = "https://175.107.203.97:4013/api/lookup/PREPAID_STATUS";
    private String URL_InvoiceState = "https://175.107.203.97:4013/api/lookup/INVOICE_STATE";
    private String URL_Retailer_All_Status = "http://175.107.203.97:4014/api/lookup/null";
    private HashMap<String, String> OrderStatusKVP = new HashMap<>();
    private HashMap<String, String> PREPAIDStatusKVP = new HashMap<>();
    private HashMap<String, String> InvoiceStatusKVP = new HashMap<>();
    private HashMap<String, String> InvoiceStateKVP = new HashMap<>();
    private HashMap<String, String> RetailerOrderStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerOrderStateKVP = new HashMap<>();
    private HashMap<String, String> RetailerInvoiceStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerDeliveryStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerInvoiceTypeKVP = new HashMap<>();
    private HashMap<String, String> RetailerKYCStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerContractStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerPrepaidStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerAddressTypeKVP = new HashMap<>();
    private HashMap<String, String> RetailerStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerYesNoKVP = new HashMap<>();
    private HashMap<String, String> RetailerIssueTypePrivateKVP = new HashMap<>();
    private HashMap<String, String> RetailerCriticalityPrivateKVP = new HashMap<>();
    private HashMap<String, String> RetailerContactingMethodKVP = new HashMap<>();
    private HashMap<String, String> RetailerTransactionDocTypeKVP = new HashMap<>();
    private HashMap<String, String> RetailerIssueTypePublicKVP = new HashMap<>();
    private HashMap<String, String> RetailerCriticalityPublicKVP = new HashMap<>();
    private HashMap<String, String> RetailerInvoiceStateKVP = new HashMap<>();
    private HashMap<String, String> RetailerReturnReasonKVP = new HashMap<>();
    private HashMap<String, String> RetailerProofOfPaymentKVP = new HashMap<>();
    private HashMap<String, String> RetailerAccountWalletTypeKVP = new HashMap<>();
    private HashMap<String, String> RetailerAccountWalletStatusKVP = new HashMap<>();
    private HashMap<String, String> RetailerSupportStatusKVP = new HashMap<>();
    private Context context;
    private String Token, User_Type;

    public StatusKVP(Context mContext, String token) {
        context = mContext;
        Token = token;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        User_Type = sharedPreferences.getString("User_Type", "");
        if (User_Type.equals("Distributor")) {
            GetOrderStatusDefault();
            GetInvoiceStatusDefault();
            GetPrepaidStatusDefault();
        } else if (User_Type.equals("Retailer")) {
            GetRetailerStatusDefault();
        }

    }

    public void putInSharedPreferences() {
        Gson gson = new Gson();
        SharedPreferences prefs = context.getSharedPreferences("StatusKVP", context.MODE_PRIVATE);
        prefs.edit().putString("RetailerIssueTypePrivateKVP", gson.toJson(RetailerIssueTypePrivateKVP)).apply();
        prefs.edit().putString("RetailerContactingMethodKVP", gson.toJson(RetailerContactingMethodKVP)).apply();
        prefs.edit().putString("RetailerCriticalityPrivateKVP", gson.toJson(RetailerCriticalityPrivateKVP)).apply();

    }

    private void GetOrderStatusDefault() {
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_OrderStatus, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        OrderStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(context, error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(sr);
    }

    public HashMap<String, String> getOrderStatus() {
        wait_until_fetched(OrderStatusKVP);
//        Log.i("StatusKVP1", String.valueOf(OrderStatusKVP));
        return OrderStatusKVP;
    }

    private void GetInvoiceStatusDefault() {
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_InvoiceStatus, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        InvoiceStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(context, error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(sr);
    }

    public HashMap<String, String> getInvoiceStatus() {
        wait_until_fetched(InvoiceStatusKVP);
        return InvoiceStatusKVP;
    }

    private void GetPrepaidStatusDefault() {
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_InvoiceStatus, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        PREPAIDStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(context, error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(sr);
    }

    public HashMap<String, String> getPrepaidStatus() {
        wait_until_fetched(PREPAIDStatusKVP);
        return PREPAIDStatusKVP;
    }

    private void GetInvoiceStateDefault() {
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_InvoiceState, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        InvoiceStateKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(context, error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(sr);
    }

    public HashMap<String, String> getInvoiceState() {
        wait_until_fetched(InvoiceStateKVP);
        return InvoiceStateKVP;
    }

    private void GetRetailerStatusDefault() {
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_Retailer_All_Status, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        if (jsonObject.getString("type").equals("ORDER_STATUS"))
                            RetailerOrderStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("ORDER_STATE"))
                            RetailerOrderStateKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("INVOICE_STATUS"))
                            RetailerInvoiceStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("DELIVERY_STATUS"))
                            RetailerDeliveryStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("INVOICE_TYPE"))
                            RetailerInvoiceTypeKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("KYC_STATUS"))
                            RetailerKYCStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("CONTRACT_STATUS"))
                            RetailerContractStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("PREPAID_STATUS"))
                            RetailerPrepaidStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("ADDRESS_TYPE"))
                            RetailerAddressTypeKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("STATUS"))
                            RetailerStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("YES_NO"))
                            RetailerYesNoKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("ISSUE_TYPE_PRIVATE"))
                            RetailerIssueTypePrivateKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("CRITICALITY_PRIVATE"))
                            RetailerCriticalityPrivateKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("CONTACTING_METHOD")) {
                            RetailerContactingMethodKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
//                            Log.i("statuskvp", String.valueOf(RetailerContactingMethodKVP));
                        } else if (jsonObject.getString("type").equals("TRANSACTION_DOC_TYPE"))
                            RetailerTransactionDocTypeKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("ISSUE_TYPE_PUBLIC"))
                            RetailerIssueTypePublicKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("CRITICALITY_PUBLIC"))
                            RetailerCriticalityPublicKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("INVOICE_STATE"))
                            RetailerInvoiceStateKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("RETURN_REASON"))
                            RetailerReturnReasonKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("PROOF_OF_PAYMENT"))
                            RetailerProofOfPaymentKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("ACCOUNT_WALLET_TYPE"))
                            RetailerAccountWalletTypeKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("ACCOUNT_WALLET_STATUS"))
                            RetailerAccountWalletStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        else if (jsonObject.getString("type").equals("SUPPORT_STATUS"))
                            RetailerSupportStatusKVP.put(jsonObject.getString("key"), jsonObject.getString("value"));
                        putInSharedPreferences();
                    }
//                    ReturnRetailerContactingMethodKVP();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(context, error);

                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(sr);
    }

    public HashMap<String, String> ReturnRetailerContactingMethodKVP() {
        return RetailerContactingMethodKVP;
    }

    public HashMap<String, String> getRetailerOrderStatusKVP() {
        wait_until_fetched(RetailerOrderStatusKVP);
        return RetailerOrderStatusKVP;
    }

    public HashMap<String, String> getRetailerOrderStateKVP() {
        wait_until_fetched(RetailerOrderStateKVP);
        return RetailerOrderStateKVP;
    }

    public HashMap<String, String> getRetailerInvoiceStatusKVP() {
        wait_until_fetched(RetailerInvoiceStatusKVP);
        return RetailerInvoiceStatusKVP;
    }

    public HashMap<String, String> getRetailerDeliveryStatusKVP() {
        wait_until_fetched(RetailerDeliveryStatusKVP);
        return RetailerDeliveryStatusKVP;
    }

    public HashMap<String, String> getRetailerInvoiceTypeKVP() {
        wait_until_fetched(RetailerInvoiceTypeKVP);
        return RetailerInvoiceTypeKVP;
    }

    public HashMap<String, String> getRetailerKYCStatusKVP() {
        wait_until_fetched(RetailerKYCStatusKVP);
        return RetailerKYCStatusKVP;
    }

    public HashMap<String, String> getRetailerContractStatusKVP() {
        wait_until_fetched(RetailerContractStatusKVP);
        return RetailerContractStatusKVP;
    }

    public HashMap<String, String> getRetailerPrepaidStatusKVP() {
        wait_until_fetched(RetailerPrepaidStatusKVP);
        return RetailerPrepaidStatusKVP;
    }

    public HashMap<String, String> getRetailerAddressTypeKVP() {
        wait_until_fetched(RetailerAddressTypeKVP);
        return RetailerAddressTypeKVP;
    }

    public HashMap<String, String> getRetailerYesNoKVP() {
        wait_until_fetched(RetailerYesNoKVP);
        return RetailerYesNoKVP;
    }

    public HashMap<String, String> getRetailerIssueTypePrivateKVP() {
        wait_until_fetched(RetailerIssueTypePrivateKVP);
        return RetailerIssueTypePrivateKVP;
    }

    public HashMap<String, String> getRetailerCriticalityPrivateKVP() {
        wait_until_fetched(RetailerCriticalityPrivateKVP);
        return RetailerCriticalityPrivateKVP;
    }

    public HashMap<String, String> getRetailerContactingMethodKVP() {
        wait_until_fetched(RetailerContactingMethodKVP);
//        Log.i("statuskvp", String.valueOf(RetailerContactingMethodKVP));
        return RetailerContactingMethodKVP;
    }

    public HashMap<String, String> getRetailerTransactionDocTypeKVP() {
        wait_until_fetched(RetailerTransactionDocTypeKVP);
        return RetailerTransactionDocTypeKVP;
    }

    public HashMap<String, String> getRetailerIssueTypePublicKVP() {
        wait_until_fetched(RetailerIssueTypePublicKVP);
        return RetailerIssueTypePublicKVP;
    }

    public HashMap<String, String> getRetailerCriticalityPublicKVP() {
        wait_until_fetched(RetailerCriticalityPublicKVP);
        return RetailerCriticalityPublicKVP;
    }

    public HashMap<String, String> getRetailerInvoiceStateKVP() {
        wait_until_fetched(RetailerInvoiceStateKVP);
        return RetailerInvoiceStateKVP;
    }

    public HashMap<String, String> getRetailerReturnReasonKVP() {
        wait_until_fetched(RetailerReturnReasonKVP);
        return RetailerReturnReasonKVP;
    }

    public HashMap<String, String> getRetailerProofOfPaymentKVP() {
        wait_until_fetched(RetailerProofOfPaymentKVP);
        return RetailerProofOfPaymentKVP;
    }

    public HashMap<String, String> getRetailerAccountWalletTypeKVP() {
        wait_until_fetched(RetailerAccountWalletTypeKVP);
        return RetailerAccountWalletTypeKVP;
    }

    public HashMap<String, String> getRetailerAccountWalletStatusKVP() {
        wait_until_fetched(RetailerAccountWalletStatusKVP);
        return RetailerAccountWalletStatusKVP;
    }

    public HashMap<String, String> getRetailerSupportStatusKVP() {
        wait_until_fetched(RetailerSupportStatusKVP);
        return RetailerSupportStatusKVP;
    }

    public HashMap<String, String> getRetailerStatus() {
        wait_until_fetched(RetailerStatusKVP);
        return RetailerStatusKVP;
    }

    private boolean wait_until_fetched(HashMap StatusKVP) {
        for (int iWait = 0; iWait < 10; iWait++) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StatusKVP.size() != 0)
                return true;
        }
        return false;
    }

    // private void printErrMessage(VolleyError error) {
    //     if (error instanceof NetworkError) {
    //         Toast.makeText(context, "Network Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof ServerError) {
    //         Toast.makeText(context, "Server Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof AuthFailureError) {
    //         Toast.makeText(context, "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof ParseError) {
    //         Toast.makeText(context, "Parse Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof NoConnectionError) {
    //         Toast.makeText(context, "No Connection Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof TimeoutError) {
    //         Toast.makeText(context, "Timeout Error !", Toast.LENGTH_LONG).show();
    //     }

    //     if (error.networkResponse != null && error.networkResponse.data != null) {
    //         try {
    //             String message = "";
    //             String responseBody = new String(error.networkResponse.data, "utf-8");
    //             Log.i("responseBody", responseBody);
    //             JSONObject data = new JSONObject(responseBody);
    //             Log.i("data", String.valueOf(data));
    //             Iterator<String> keys = data.keys();
    //             while (keys.hasNext()) {
    //                 String key = keys.next();
    //                 message = message + data.get(key) + "\n";
    //             }
    //             Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    //         } catch (UnsupportedEncodingException e) {
    //             e.printStackTrace();
    //         } catch (JSONException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
}
