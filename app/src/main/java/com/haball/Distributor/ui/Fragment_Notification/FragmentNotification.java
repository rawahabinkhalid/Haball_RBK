package com.haball.Distributor.ui.Fragment_Notification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.Payment.ConsolidatePaymentsModel;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Retailor.RetailorDashboard;

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
import java.util.Timer;
import java.util.TimerTask;

public class FragmentNotification extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter NotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotificationModel> notificationLists = new ArrayList<>();
    private String Token, DistributorId, ID;
    SharedPreferences sharedPreferences;
    private Context mcontext;
    static int counter;
    //private String URL_NOTIFICATION = "https://175.107.203.97:4013/api/useralert/ShowAll/";
    private Loader loader;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_notification, container, false);
        mcontext = getContext();

        loader = new Loader(mcontext);
        recyclerView = root.findViewById(R.id.rv_notification);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);

        fetchNotificationForItemCount();
        setNotificationStatus();


//
        return root;
    }

    private void fetchNotification(final int resultLenght) {
        sharedPreferences = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                loader.showLoader();
//            }
//        });


        SharedPreferences sharedPreferences1 = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        ID = sharedPreferences1.getString("ID", "");
        String URL_NOTIFICATION = "https://175.107.203.97:4013/api/useralert/";
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        if (!URL_NOTIFICATION.contains("/" + ID))
            URL_NOTIFICATION = URL_NOTIFICATION + ID;
        Log.i("URL_NOTIFICATION", URL_NOTIFICATION);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_NOTIFICATION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("URL_NOTIFICATION", String.valueOf(result));
                loader.hideLoader();

                try {
                    if (resultLenght == result.getInt("count")) {

                    }

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<NotificationModel>>() {
                    }.getType();
                    notificationLists = gson.fromJson(result.getString("data"), type);
                    NotificationAdapter = new NotificationAdapter(mcontext, notificationLists, Token);
                    recyclerView.setAdapter(NotificationAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                setNotificationStatus(DistributorId, ID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
//                printErrorMessage(error);
                Log.i("fetch_notification", "error in fetch notification");
                error.printStackTrace();
                new HaballError().printErrorMessage(mcontext, error);
                new ProcessingError().showError(mcontext);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(mcontext).add(sr);
    }

    private void setNotificationStatus() {
        loader.showLoader();

        sharedPreferences = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loader.showLoader();
            }
        });


        SharedPreferences sharedPreferences1 = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        ID = sharedPreferences1.getString("ID", "");

        String URL_NOTIFICATION_SEEN = "https://175.107.203.97:4013/api/useralert/MarkSeen/";
        if (!URL_NOTIFICATION_SEEN.contains("/" + ID))
            URL_NOTIFICATION_SEEN = URL_NOTIFICATION_SEEN + ID;
        Log.i("URL_NOTIFICATION", URL_NOTIFICATION_SEEN);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_NOTIFICATION_SEEN, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                Log.i("fetch_notification", "error in setNotificationStatus");
                new HaballError().printErrorMessage(mcontext, error);
                new ProcessingError().showError(mcontext);
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
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(mcontext).add(sr);
    }

    private void fetchNotificationForItemCount() {
        loader.showLoader();
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        ID = sharedPreferences1.getString("ID", "");
        String URL_NOTIFICATION = "https://175.107.203.97:4013/api/useralert/";
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        URL_NOTIFICATION = URL_NOTIFICATION + ID;
        Log.i("URL_NOTIFICATION", URL_NOTIFICATION);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_NOTIFICATION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject result) {
                loader.hideLoader();

                try {
                    FragmentNotification.counter = result.getInt("count");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    fetchNotification(result.getInt("count"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Timer timer = new Timer();
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        try {
//                            fetchNotification(result.getInt("count"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, 0, 5000);
                Log.i("ResultLength", "" + result.length());
                Log.i("RESULT NOTIFICATION", result.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(mcontext, error);
                Log.i("fetch_notification", "error in fetchNotificationForItemCount");
                new ProcessingError().showError(mcontext);
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
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(mcontext).add(sr);
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

//                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                    editorOrderTabsFromDraft.putString("TabNo", "0");
//                    editorOrderTabsFromDraft.apply();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                    fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
                    fragmentTransaction.commit();
                    return true;

//                    Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                    ((FragmentActivity) getContext()).startActivity(login_intent);
//                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

    }
}
