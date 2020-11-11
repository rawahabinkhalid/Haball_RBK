package com.haball.Distributor.ui.payments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorInvoicesModel;
import com.haball.Distributor.DistributorPaymentsAdapter;
import com.haball.HaballError;
import com.haball.Payment.ProofOfPaymentModel;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class ProofOfPaymentFormViewFragment extends Fragment {

    private TextView tv_pop_id, tv_distributor_name, tv_created_date, tv_payment_mode, tv_bank, tv_branch_code, tv_transaction_no, tv_payment_id, tv_proof_of_payment_view_status;
    private RecyclerView rv_proof_of_payments_view;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ProofOfPaymentViewFormModel> proofOfPaymentViewFormList = new ArrayList<>();
    private String DistributorId, Token, ProofOfPaymentID;
    private String URL_PROOF_OF_PAYMENTS = "https://175.107.203.97:4013/api/proofofpayment/";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_proof_of_payment_view, container, false);
        ProofOfPaymentID = getArguments().getString("ProofOfPaymentID");
        Log.i("ProofOfPaymentID: ", ProofOfPaymentID);

        tv_pop_id = root.findViewById(R.id.tv_pop_id);
        tv_distributor_name = root.findViewById(R.id.tv_distributor_name);
        tv_created_date = root.findViewById(R.id.tv_created_date);
        tv_payment_mode = root.findViewById(R.id.tv_payment_mode);
        tv_bank = root.findViewById(R.id.tv_bank);
        tv_branch_code = root.findViewById(R.id.tv_branch_code);
        tv_payment_id = root.findViewById(R.id.tv_payment_id);
        tv_proof_of_payment_view_status = root.findViewById(R.id.tv_proof_of_payment_view_status);
        tv_transaction_no = root.findViewById(R.id.tv_transaction_no);
        rv_proof_of_payments_view = root.findViewById(R.id.rv_proof_of_payments_view);

        rv_proof_of_payments_view.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rv_proof_of_payments_view.setLayoutManager(layoutManager);

        makePOPViewRequest();

        return root;
    }

    private void makePOPViewRequest() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
        URL_PROOF_OF_PAYMENTS = URL_PROOF_OF_PAYMENTS + ProofOfPaymentID;

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_PROOF_OF_PAYMENTS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("RESPONSE", result.toString());

                try {
                    tv_pop_id.setText(result.getString("POPNumber"));
                    tv_distributor_name.setText(result.getString("DistributorName"));
                    tv_created_date.setText(result.getString("CreatedDate"));
                    tv_payment_mode.setText(result.getString("PaymentModeName"));
                    tv_branch_code.setText(result.getString("BranchCode"));
                    tv_payment_id.setText(result.getString("PaymnetNumber"));
                    tv_proof_of_payment_view_status.setText(result.getString("POPStatus"));
                    tv_transaction_no.setText(result.getString("ChecqueNo"));
                    tv_bank.setText(result.getString("Bank"));

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ProofOfPaymentViewFormModel>>() {
                    }.getType();
                    proofOfPaymentViewFormList = gson.fromJson(result.get("ProofOfPaymentDetails").toString(), type);
                    mAdapter = new ProofOfPaymentViewFormAdapter(getContext(), proofOfPaymentViewFormList);
                    rv_proof_of_payments_view.setAdapter(mAdapter);

                    Log.i("View Details", String.valueOf(proofOfPaymentViewFormList));

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
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }


    private void printErrMessage(VolleyError error) {
        if (getContext() != null) {
            if (error instanceof NetworkError) {
                Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

