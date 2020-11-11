package com.haball.Retailor.ui.Network.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorOrdersAdapter;
import com.haball.Distributor.DistributorOrdersModel;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.R;
import com.haball.Retailor.ui.Network.Adapters.Fragment_My_Network_Adapter;
import com.haball.Retailor.ui.Network.Adapters.Fragment_Recieved_Adapter;
import com.haball.Retailor.ui.Network.Adapters.Fragment_Sent_Adapter;
import com.haball.Retailor.ui.Network.Models.Netwok_Model;
import com.haball.Retailor.ui.Network.Models.Network_Recieve_Model;
import com.haball.Retailor.ui.Network.Models.Network_Sent_Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.SSL_HandShake;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private RecyclerView rv_network,rv_sent,rv_receive;
    private RecyclerView.Adapter networkAdapter,sentadapter,recieveAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String Token, DistributorId;
    //network Api
    private String MYNETWORK_URL = " http://175.107.203.97:4014/api/kyc/Search";
    private int pageNumbernetwork = 0;
    private double totalPagesnetwork = 0;
    private double totalEntriesnetwork = 0;
    private List<Netwok_Model> MyNetworkList = new ArrayList<>();
    private List<Network_Sent_Model> MySentList = new ArrayList<>();
    private List<Network_Recieve_Model> MyReceiveList = new ArrayList<>();


    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = null;


        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {

            case 1: {

                rootView = inflater.inflate(R.layout.fragment_my__network_, container, false);

                rv_network = (RecyclerView) rootView.findViewById(R.id.rv_my_network);
//
                rv_network.setHasFixedSize(false);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(getContext());
                rv_network.setLayoutManager(layoutManager);
                try {
                    myNetworkData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                break;
            }
            case 2: {
                rootView = inflater.inflate(R.layout.fragment_sent_, container, false);
                rv_sent = (RecyclerView) rootView.findViewById(R.id.rv_sent);
                rv_sent.setHasFixedSize(false);
                layoutManager = new LinearLayoutManager(getContext());
                rv_sent.setLayoutManager(layoutManager);
                try {
                    mySentData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
////
                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.fragment_recieved_, container, false);
                 rv_receive = (RecyclerView) rootView.findViewById(R.id.rv_my_network_recieved);
                rv_receive.setHasFixedSize(false);
                layoutManager = new LinearLayoutManager(getContext());
                rv_receive.setLayoutManager(layoutManager);
                try {
                    myReceiveData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return rootView;

    }

    private void myReceiveData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumbernetwork);
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, MYNETWORK_URL, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                //                    JSONArray jsonArray = new JSONArray(result);

                Gson gson = new Gson();
                Type type = new TypeToken<List<Network_Recieve_Model>>() {
                }.getType();
                try {
                    totalEntriesnetwork = Double.parseDouble(String.valueOf(result.getJSONObject(1).get("kycRequestCount")));
                    totalPagesnetwork = Math.ceil(totalEntriesnetwork / 10);
                    MyReceiveList = gson.fromJson(result.get(0).toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("sentDatalist", String.valueOf(MyReceiveList));
                recieveAdapter = new Fragment_Recieved_Adapter(getContext(), MyReceiveList);
                rv_receive.setAdapter(recieveAdapter);
//                if (MySentList.size() != 0) {
//                    tv_shipment_no_data.setVisibility(View.GONE);
//                } else {
//                    tv_shipment_no_data.setVisibility(View.VISIBLE);
//                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   new HaballError().printErrorMessage(error);

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
        Volley.newRequestQueue(getContext()).add(sr);


    }

    private void mySentData() throws JSONException{
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumbernetwork);
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, MYNETWORK_URL, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                //                    JSONArray jsonArray = new JSONArray(result);

                Gson gson = new Gson();
                Type type = new TypeToken<List<Network_Sent_Model>>() {
                }.getType();
                try {
                    totalEntriesnetwork = Double.parseDouble(String.valueOf(result.getJSONObject(1).get("kycRequestCount")));
                    totalPagesnetwork = Math.ceil(totalEntriesnetwork / 10);
                    MySentList = gson.fromJson(result.get(0).toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("sentDatalist", String.valueOf(MySentList));
                sentadapter = new Fragment_Sent_Adapter(getContext(), MySentList);
                rv_sent.setAdapter(sentadapter);
//                if (MySentList.size() != 0) {
//                    tv_shipment_no_data.setVisibility(View.GONE);
//                } else {
//                    tv_shipment_no_data.setVisibility(View.VISIBLE);
//                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   new HaballError().printErrorMessage(error);

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
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void myNetworkData() throws JSONException {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
    Log.i("netword_token" ,Token);
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("TotalRecords", 10);
        map.put("PageNumber", pageNumbernetwork);

        //   networkAdapter = new Fragment_My_Network_Adapter(getContext(), "Connected", "123456789","Mz-2,Horizon Vista,Plot-10,Block-4,Clifton");
               // rv_network.setAdapter(networkAdapter);
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        MyJsonArrayRequest sr = new MyJsonArrayRequest(Request.Method.POST, MYNETWORK_URL, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                //                    JSONArray jsonArray = new JSONArray(result);
              Log.i("results_network" , String.valueOf(result));
                Gson gson = new Gson();
                Type type = new TypeToken<List<Netwok_Model>>() {
                }.getType();
                try {
                    totalEntriesnetwork = Double.parseDouble(String.valueOf(result.getJSONObject(1).get("kycRequestCount")));
                    totalPagesnetwork = Math.ceil(totalEntriesnetwork / 10);
                    MyNetworkList = gson.fromJson(result.get(0).toString(), type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("OrdersList", String.valueOf(MyNetworkList));
                networkAdapter = new Fragment_My_Network_Adapter(getContext(), MyNetworkList);
                rv_network.setAdapter(networkAdapter);
//                if (MyNetworkList.size() != 0) {
//                    tv_shipment_no_data.setVisibility(View.GONE);
//                } else {
//                    tv_shipment_no_data.setVisibility(View.VISIBLE);
//                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //new HaballError().printErrorMessage(error);

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
        Volley.newRequestQueue(getContext()).add(sr);

        //Log.i("aaaaaa", String.valueOf(mAdapter));
    }
}