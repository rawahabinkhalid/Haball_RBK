package com.haball.Retailor.ui.Notification;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.Fragment_Notification.NotificationAdapter;
import com.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.Retailer_New_Password;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Notification.Adapters.Notification_Adapter;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notification_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter NotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Socket iSocket;
    private static final String URL = "http://175.107.203.97:4014/";
    private String URL_Mark_Seen = "http://175.107.203.97:4014/api/useralert/MarkSeen";
    private String UserId, Token;
    private List<Retailer_Notification_Model> NotificationList = new ArrayList<>();
    private TextView tv_notification_no_data;
    private Loader loader;
    private FragmentTransaction fragmentTransaction;
    private static int y;
    private List<String> scrollEvent = new ArrayList<>();
    private int pageNumber = 0;
    private double totalPages = 0;
    private double totalEntries = 0;
    private String URL_Notification = "http://175.107.203.97:4014/api/useralert/ShowAll";

    public Notification_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //     return inflater.inflate(R.layout.fragment_blank, container, false);
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        IO.Options opts = new IO.Options();
//            opts.query = "userId=" + UserId;
        try {
            iSocket = IO.socket(URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        loader = new Loader(getContext());
        View root = inflater.inflate(R.layout.notification_fragment, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        UserId = sharedPreferences.getString("UserId", "");
        Token = sharedPreferences.getString("Login_Token", "");
        tv_notification_no_data = root.findViewById(R.id.tv_notification_no_data);
        tv_notification_no_data.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.rv_notification_retailor);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        tv_notification_no_data.setVisibility(View.GONE);
//        NotificationAdapter = new Notification_Adapter(getContext(), "Payment", "Payment ID 345697977970 has been approved successfully");
//        recyclerView.setAdapter(NotificationAdapter);
        getNotifications();
//        try {
//            fetchNotification();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                if (isLastItemDisplaying(recyclerView)) {

                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (totalPages != 0 && pageNumber < totalPages) {
//                            Toast.makeText(getContext(), pageNumber + " - " + totalPages, Toast.LENGTH_LONG).show();
//                        btn_load_more.setVisibility(View.VISIBLE);
                            pageNumber++;
                            try {
                                performPaginationNotification();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
//            }
        });

        try {
            markSeenApi();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }


    private void performPaginationNotification() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        UserId = sharedPreferences.getString("UserId", "");
        Log.i("Token  ", Token);

        JSONObject map = new JSONObject();
//        map.put("userId", Integer.parseInt(UserId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);

        Log.i("map_SSSS", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_Notification, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                Log.i("response_support ", String.valueOf(response));
                Gson gson = new Gson();
                Type type = new TypeToken<List<Retailer_Notification_Model>>() {
                }.getType();
                try {
//                    SupportList = gson.fromJson(String.valueOf(response.get(0)), type);
                    List<Retailer_Notification_Model> retailer_Notification_Model = new ArrayList<>();
                    retailer_Notification_Model = gson.fromJson(String.valueOf(response.getJSONArray("data")), type);
                    NotificationList.addAll(retailer_Notification_Model);

                    if (NotificationList.size() != 0) {
                        tv_notification_no_data.setVisibility(View.GONE);

                    } else {

                        tv_notification_no_data.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NotificationAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Content-Length", "1612");
//                params.put("Host", "175.107.203.97");

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
    }


    private void fetchNotification() throws JSONException {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        UserId = sharedPreferences.getString("UserId", "");
        Log.i("Token  ", Token);
        pageNumber = 0;
        JSONObject map = new JSONObject();
//        map.put("userId", Integer.parseInt(UserId));
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumber);

        Log.i("map_SSSS", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_Notification, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                Log.i("response_support ", String.valueOf(response));
                Gson gson = new Gson();
                Type type = new TypeToken<List<Retailer_Notification_Model>>() {
                }.getType();
                try {
                    Log.i("response_support_1", String.valueOf(response.getJSONArray("data")));
//                    SupportList = gson.fromJson(String.valueOf(response.get(0)), type);
                    NotificationList = gson.fromJson(String.valueOf(response.getJSONArray("data")), type);


                    if (NotificationList.size() != 0) {
                        tv_notification_no_data.setVisibility(View.GONE);
                    } else {
                        tv_notification_no_data.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NotificationAdapter = new Notification_Adapter(getContext(), NotificationList);
                recyclerView.setAdapter(NotificationAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Content-Length", "1612");
                params.put("Host", "175.107.203.97");

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(request);
    }


    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() > 8) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    private String getScrollEvent() {
        String scroll = "";
        if (scrollEvent.size() > 0) {
            if (scrollEvent.size() > 15)
                scrollEvent = new ArrayList<>();
            if (Collections.frequency(scrollEvent, "ScrollUp") > Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollDown") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollUp") > 3)
                        scroll = "ScrollUp";
                } else {
                    scroll = "ScrollUp";
                }
            } else if (Collections.frequency(scrollEvent, "ScrollUp") < Collections.frequency(scrollEvent, "ScrollDown")) {
                if (Collections.frequency(scrollEvent, "ScrollUp") > 0) {
                    if (Collections.frequency(scrollEvent, "ScrollDown") > 3)
                        scroll = "ScrollDown";
                } else {
                    scroll = "ScrollDown";
                }
            }
        }
        Log.i("scrolling123", scroll);
        return scroll;
    }

    private void markSeenApi() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        BooleanRequest sr = new BooleanRequest(Request.Method.PUT, URL_Mark_Seen, null, new Response.Listener<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Boolean result) {
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
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void getNotifications() {
//        loader.showLoader();
//        List<Retailer_Notification_Model> temp_NotificationList = new ArrayList<>();

//        if (iSocket.connected()) {
        iSocket.emit("userId", UserId);
        iSocket.on("userId" + UserId, new Emitter.Listener() {
            @Override
            public void call(final Object... args) {


                if (getActivity() != null) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject data = (JSONObject) args[0];
//                                                Toast.makeText(getContext(), data.toString(), Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Retailer_Notification_Model>>() {
                            }.getType();
                            try {
                                loader.hideLoader();
                                NotificationList = gson.fromJson(String.valueOf(data.getJSONArray("data")), type);
                                totalEntries = Double.parseDouble(String.valueOf(data.getString("TotalCount")));
                                totalPages = Math.ceil(totalEntries / 10);

                                if (NotificationList.size() != 0) {
                                    tv_notification_no_data.setVisibility(View.GONE);
                                } else {
                                    tv_notification_no_data.setVisibility(View.VISIBLE);
                                }

                                Log.i("notificationTest12", String.valueOf(NotificationList));
                                NotificationAdapter = new Notification_Adapter(getContext(), NotificationList);
                                recyclerView.setAdapter(NotificationAdapter);

                                iSocket.disconnect();
                                iSocket.close();
                            } catch (
                                    JSONException e) {
                                e.printStackTrace();
                                loader.hideLoader();
                            }


                        }
                    });
                }
            }
        });
//        }
        iSocket.connect();
//        try {
//            fetchNotification();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    //
    private void setNotificationStatus(final int count) {
//        if (getActivity() != null) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
        if (count != 0) {
            tv_notification_no_data.setVisibility(View.GONE);
        } else {
            tv_notification_no_data.setVisibility(View.VISIBLE);
        }
//                }
//            });
//        }
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

                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                }
                return false;
            }
        });

    }


}
