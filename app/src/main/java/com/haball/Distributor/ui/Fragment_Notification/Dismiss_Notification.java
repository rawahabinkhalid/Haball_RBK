package com.haball.Distributor.ui.Fragment_Notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dismiss_Notification {
    private String ID;
    private String URL_NOTIFICATION = "https://175.107.203.97:4013/api/useralert/DismissAlert/";
    private Loader loader;

    public Dismiss_Notification() {
    }

    protected void requestDismissNotification(String id, final Context context, final String Token) {
        loader = new Loader(context);
//        loader.showLoader();
        URL_NOTIFICATION = URL_NOTIFICATION + id;
        Log.i("NOTIFICATION_DISMISS", URL_NOTIFICATION);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_NOTIFICATION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
//                loader.hideLoader();
                Log.i("DISMISSED ", result.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
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
        Volley.newRequestQueue(context).add(sr);
    }

}
