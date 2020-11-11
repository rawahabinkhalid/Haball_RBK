package com.haball.Distributor.ui.support;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.ProcessingError;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResolveTicket {
    private String ID;
    private String URL_RESOLVE_TICKET = "https://175.107.203.97:4013/api/useralert/DismissAlert/";

    public ResolveTicket() {}

    protected void RequestResolveTicket(String id, final Context context, final String Token) {

        URL_RESOLVE_TICKET = URL_RESOLVE_TICKET+id;
        Log.i("NOTIFICATION_DISMISS", URL_RESOLVE_TICKET);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_RESOLVE_TICKET, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("DISMISSED ", result.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                 new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);
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
}
