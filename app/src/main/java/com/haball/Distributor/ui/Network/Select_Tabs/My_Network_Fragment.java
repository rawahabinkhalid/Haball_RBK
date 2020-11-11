package com.haball.Distributor.ui.Network.Select_Tabs;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.Network.Adapters.Fragment_My_Network_Adapter;
import com.haball.Distributor.ui.Network.Models.Netwok_Model;
import com.haball.Distributor.ui.Network.Models.Network_Recieve_Model;
import com.haball.Distributor.ui.Network.Models.Network_Sent_Model;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.payments.MyJsonArrayRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.SSL_HandShake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Network_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView rv_network, rv_sent, rv_receive;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Netwok_Model paymentsViewModel;
    private RecyclerView.Adapter networkAdapter, sentadapter, recieveAdapter;
    private String Token, DistributorId;
    private String MYNETWORK_COUNT_URL = " https://175.107.203.97:4013/api/kyc/searchCount";
    private String MYNETWORK_URL = " https://175.107.203.97:4013/api/kyc/search";
    private int pageNumbernetwork = 0;
    private double totalPagesnetwork = 0;
    private double totalEntriesnetwork = 0;
    private List<Netwok_Model> MyNetworkList = new ArrayList<>();
    private List<Network_Sent_Model> MySentList = new ArrayList<>();
    private List<Network_Recieve_Model> MyReceiveList = new ArrayList<>();
    private Loader loader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_my__network_, container, false);
      /*  paymentsViewModel =
                ViewModelProviders.of(this).get(Fragment_My_Netwok_Model.class);
       */
        View root = inflater.inflate(R.layout.fragment_my__network_, container, false);

        //inflater = LayoutInflater.from(getContext());
//        recyclerView = (RecyclerView) root.findViewById(R.id.rv_my_network);
//        recyclerView.setHasFixedSize(true);

        loader = new Loader(getContext());

        rv_network = (RecyclerView) root.findViewById(R.id.rv_my_network);
//
        rv_network.setHasFixedSize(false);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        rv_network.setLayoutManager(layoutManager);

        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

        myNetworkData();
        // specify an adapter (see also next example)
        // mAdapter = new Fragment_My_Network_Adapter(this, "Connected", "123456789","Mz-2,Horizon Vista,Plot-10,Block-4,Clifton");
        //  recyclerView.setAdapter(mAdapter);
        return root;
    }

    private void myNetworkData() {
        loader.showLoader();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
        Log.i("netword_token", Token);
        Log.i("DistributorId ", DistributorId);
        pageNumbernetwork = 0;
        JSONObject map = new JSONObject();
        try {
            map.put("Status", -1);
            map.put("TotalRecords", 10);
            map.put("DistributorId", DistributorId);
            map.put("PageNumber", pageNumbernetwork);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, MYNETWORK_COUNT_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("results_network", String.valueOf(result));
                try {
                    totalEntriesnetwork = Double.parseDouble(String.valueOf(result.get("KYCCount")));
                    totalPagesnetwork = Math.ceil(totalEntriesnetwork / 10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
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


        //   networkAdapter = new Fragment_My_Network_Adapter(getContext(), "Connected", "123456789","Mz-2,Horizon Vista,Plot-10,Block-4,Clifton");
        // rv_network.setAdapter(networkAdapter);

        MyJsonArrayRequest sr1 = new MyJsonArrayRequest(Request.Method.POST, MYNETWORK_URL, map, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                //                    JSONArray jsonArray = new JSONArray(result);
                Log.i("results_network", String.valueOf(result));
                Gson gson = new Gson();
                Type type = new TypeToken<List<Netwok_Model>>() {
                }.getType();
                MyNetworkList = gson.fromJson(result.toString(), type);
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
                loader.hideLoader();
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
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
        Volley.newRequestQueue(getContext()).add(sr1);


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
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new HomeFragment());
                    fragmentTransaction.commit();
                }
                return false;
            }
        });

    }

}
