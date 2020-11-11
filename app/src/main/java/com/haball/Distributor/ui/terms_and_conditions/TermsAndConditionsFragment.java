package com.haball.Distributor.ui.terms_and_conditions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.haball.Retailer_Login.RetailerLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.SSL_HandShake;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermsAndConditionsFragment extends AppCompatActivity {

    private TermsAndConditionsViewModel mViewModel;
    private Button agree_button, disagree_button;
    private String URL = "http://175.107.203.97:4014/api/users/termsandcondition";
    private String Token;

    public static TermsAndConditionsFragment newInstance() {
        return new TermsAndConditionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions_fragment);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
        rl_main_background.setBackground(background_drawable);
        agree_button = findViewById(R.id.agree_button);
        disagree_button = findViewById(R.id.disagree_button);

        agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    termsAndConditionAccepted();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        disagree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsAndConditionsFragment.this, RetailerLogin.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void termsAndConditionAccepted() throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        String RetailerID = sharedPreferences.getString("Retailer_Id", "");
        Log.i("Token", Token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("RetailerID", Integer.parseInt(RetailerID));

        String requestBody = jsonObject.toString();
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(TermsAndConditionsFragment.this);

        BooleanRequest sr = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Boolean result) {
                if(result) {

                } else {
                    Toast.makeText(TermsAndConditionsFragment.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TermsAndConditionsFragment.this, RetailerLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // printErrorMessage(error);
                error.printStackTrace();
                 new HaballError().printErrorMessage(TermsAndConditionsFragment.this, error);
                new ProcessingError().showError(TermsAndConditionsFragment.this);
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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(sr);
    }

}
