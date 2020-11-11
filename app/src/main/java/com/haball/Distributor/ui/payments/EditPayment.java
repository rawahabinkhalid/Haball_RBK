package com.haball.Distributor.ui.payments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.HaballError;
import com.haball.ProcessingError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EditPayment {
    private String URL_PAYMENT_REQUESTS_SAVE = "https://175.107.203.97:4013/api/prepaidrequests/save";
    private Context mContext;

    public EditPayment() {
    }

    public void EditPayment(final FragmentActivity activity, final Context context, final String Token, String DistributorId, String PrePaidId, final String PrePaidNumber, String CompanyId, String PaidAmount) throws JSONException {
        mContext = context;
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("Status", 0);
        map.put("ID", PrePaidId);
        map.put("DistributorId", Integer.parseInt(DistributorId));
        map.put("CompanyId", CompanyId);
        map.put("PaidAmount", PaidAmount);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_PAYMENT_REQUESTS_SAVE, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
//                loader.hideLoader();
                Toast.makeText(context, "Payment Request " + PrePaidNumber + " has been updated successfully.", Toast.LENGTH_SHORT).show();
                Intent dashboard_intent = new Intent(context, DistributorDashboard.class);
                context.startActivity(dashboard_intent);
                activity.finish();

                Log.e("RESPONSE prepaid_number", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);

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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(sr);

    }


    private void printErrMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(mContext, "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(mContext, "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(mContext, "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(mContext, "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(mContext, "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(mContext, "Timeout Error !", Toast.LENGTH_LONG).show();
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
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
