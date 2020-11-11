package com.haball.Retailor.ui.Support;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.StatusKVP;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.SSL_HandShake;
import com.haball.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

public class Retailer_Support_Ticket_View extends Fragment {
    private String Token, DistributorId;
    private Button btn_delete, btn_back;
    private TextInputLayout layout_txt_business_name, layout_txt_email_address, layout_txt_mobile_number, layout_txt_comments;

    //    private String URL_SUPPORT_VIEW = "http://175.107.203.97:4014/api/contact//";
    private String URL_SUPPORT_VIEW = "http://175.107.203.97:4014/api/support/TicketById/";
    private TextView tv_ticket_id;
    private TextInputEditText txt_business_name;
    private TextInputEditText txt_email_address;
    private TextInputEditText txt_mobile_number;
    private EditText txt_issue_type;
    private EditText txt_criticality;
    private EditText txt_preferred_contact_method;
    private TextInputEditText txt_comments;
    private String ID;
    private FragmentTransaction fragmentTransaction;
    private HashMap<String, String> RetailerIssueTypePrivateKVP;
    private HashMap<String, String> RetailerCriticalityPrivateKVP;
    private HashMap<String, String> RetailerContactingMethodKVP;
    private StatusKVP statusKVP;
    private String RetailerIssueTypePrivateKVPString, RetailerCriticalityPrivateKVPString, RetailerContactingMethodKVPString;
    private Loader loader;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.layout_support_view, container, false);

        loader = new Loader(getContext());

        layout_txt_business_name = root.findViewById(R.id.layout_txt_business_name);
        layout_txt_email_address = root.findViewById(R.id.layout_txt_email_address);
        layout_txt_mobile_number = root.findViewById(R.id.layout_txt_mobile_number);
        layout_txt_comments = root.findViewById(R.id.layout_txt_comments);

        txt_business_name = root.findViewById(R.id.txt_business_name);
        txt_email_address = root.findViewById(R.id.txt_email_address);
        txt_mobile_number = root.findViewById(R.id.txt_mobile_number);
        txt_issue_type = root.findViewById(R.id.txt_issue_type);
        txt_criticality = root.findViewById(R.id.txt_criticality);
        txt_preferred_contact_method = root.findViewById(R.id.txt_preferred_contact_method);
        txt_comments = root.findViewById(R.id.txt_comments);
        tv_ticket_id = root.findViewById(R.id.tv_ticket_id);

        new TextField().changeColor(getContext(), layout_txt_business_name, txt_business_name);
        new TextField().changeColor(getContext(), layout_txt_email_address, txt_email_address);
        new TextField().changeColor(getContext(), layout_txt_mobile_number, txt_mobile_number);
        new TextField().changeColor(getContext(), layout_txt_comments, txt_comments);

        txt_business_name.setEnabled(false);
        txt_email_address.setEnabled(false);
        txt_mobile_number.setEnabled(false);
        txt_issue_type.setEnabled(false);
        txt_criticality.setEnabled(false);
        txt_preferred_contact_method.setEnabled(false);
        txt_comments.setEnabled(false);

        btn_delete = root.findViewById(R.id.btn_delete);
        btn_back = root.findViewById(R.id.btn_back);


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteTicketDialog();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container_ret, new SupportFragment()).addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token  ", Token);

//        statusKVP = new StatusKVP(getContext(), Token);

//        Log.i("statuskvp", "String.valueOf(RetailerContactingMethodKVP)");
//        Log.i("statuskvp", String.valueOf(RetailerContactingMethodKVP));
        SharedPreferences prefs = getContext().getSharedPreferences("StatusKVP", getContext().MODE_PRIVATE);
        RetailerIssueTypePrivateKVPString = prefs.getString("RetailerIssueTypePrivateKVP", "");
        RetailerContactingMethodKVPString = prefs.getString("RetailerContactingMethodKVP", "");
        RetailerCriticalityPrivateKVPString = prefs.getString("RetailerCriticalityPrivateKVP", "");
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        Gson gson = new Gson();
//
//        prefs.edit().putString("RetailerIssueTypePrivateKVP", gson.toJson(RetailerIssueTypePrivateKVP)).apply();
//        prefs.edit().putString("RetailerContactingMethodKVP", gson.toJson(RetailerContactingMethodKVP)).apply();
//        prefs.edit().putString("RetailerCriticalityPrivateKVP", gson.toJson(RetailerCriticalityPrivateKVP)).apply();


        Log.i("statuskvp", "String.valueOf(RetailerIssueTypePrivateKVP)");
        Log.i("statuskvp", String.valueOf(RetailerIssueTypePrivateKVP));


        fetchSupportData();

        return root;
    }

    private void deleteSupportTicket() throws JSONException {
//        DeleteSupport deleteSupport = new DeleteSupport();
//        String response = deleteSupport.DeleteSupportTicket(getContext(), ID);
        DeleteSupportTicket deleteSupport = new DeleteSupportTicket();
        String response = deleteSupport.DeleteSupportTicket(getContext(), ID);


    }

    private void showDeleteTicketDialog() {
        Log.i("CreatePayment", "In Dialog");
//        final FragmentManager fm = getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        tv_discard.setText("Delete Ticket");
        Button btn_discard = view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText("Delete");
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to delete this ticket?");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                try {
                    deleteSupportTicket();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    private void fetchSupportData() {
        loader.showLoader();
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("SupportId",
                Context.MODE_PRIVATE);
        ID = sharedPreferences3.getString("SupportId", "");

        Log.i("IDDDD", ID);
        if (!URL_SUPPORT_VIEW.contains("/" + ID))
            URL_SUPPORT_VIEW = URL_SUPPORT_VIEW + ID;
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_SUPPORT_VIEW, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Gson gson = new Gson();
                RetailerIssueTypePrivateKVP = gson.fromJson(RetailerIssueTypePrivateKVPString, type);
                RetailerContactingMethodKVP = gson.fromJson(RetailerContactingMethodKVPString, type);
                RetailerCriticalityPrivateKVP = gson.fromJson(RetailerCriticalityPrivateKVPString, type);
                Log.i("statuskvp1", String.valueOf(RetailerIssueTypePrivateKVPString));
                Log.i("statuskvp1", String.valueOf(RetailerIssueTypePrivateKVP));
//                RetailerIssueTypePrivateKVP = statusKVP.getRetailerIssueTypePrivateKVP();
//                RetailerCriticalityPrivateKVP = statusKVP.getRetailerCriticalityPrivateKVP();
//                RetailerContactingMethodKVP = statusKVP.getRetailerContactingMethodKVP();
//                Log.i("statuskvp", "String.valueOf(RetailerContactingMethodKVP)");
//                Log.i("statuskvp", String.valueOf(RetailerContactingMethodKVP));

                Log.i("ticket_viewdata", String.valueOf(response));

                String issue_type = "", criticality = "", preffered_contact = "";
                try {
                    tv_ticket_id.setText(response.getString("TicketNumber"));

                    if(!String.valueOf(response.get("ContactName")).equals("") && !String.valueOf(response.get("ContactName")).equals("null")) {
                        txt_business_name.setText(String.valueOf(response.get("ContactName")));
                        txt_business_name.setTextColor(getResources().getColor(R.color.textcolor));
                    }
                    if(!String.valueOf(response.get("Email")).equals("") && !String.valueOf(response.get("Email")).equals("null")) {
                        txt_email_address.setText(String.valueOf(response.get("Email")));
                        txt_email_address.setTextColor(getResources().getColor(R.color.textcolor));
                    }
                    if(!String.valueOf(response.get("MobileNumber")).equals("") && !String.valueOf(response.get("MobileNumber")).equals("null")) {
                        txt_mobile_number.setText(String.valueOf(response.get("MobileNumber")));
                        txt_mobile_number.setTextColor(getResources().getColor(R.color.textcolor));
                    }
                    if(!String.valueOf(response.get("Description")).equals("") && !String.valueOf(response.get("Description")).equals("null")) {
                        txt_comments.setText(String.valueOf(response.get("Description")));
                        txt_comments.setTextColor(getResources().getColor(R.color.textcolor));
                    }

                    ID = String.valueOf(response.get("ID"));
                    Log.i("statuskvp2", String.valueOf(RetailerIssueTypePrivateKVP));

                    for (Map.Entry<String, String> entry : RetailerIssueTypePrivateKVP.entrySet()) {
                        Log.i("statuskvp3", String.valueOf(entry));
                        Log.i("statuskvp3", String.valueOf(response.get("IssueType")));
                        if (entry.getKey().equals(String.valueOf(response.get("IssueType"))))
                            issue_type = entry.getValue();
                    }
                    for (Map.Entry<String, String> entry : RetailerCriticalityPrivateKVP.entrySet()) {
                        if (entry.getKey().equals(String.valueOf(response.get("Criticality"))))
                            criticality = entry.getValue();
                    }
                    for (Map.Entry<String, String> entry : RetailerContactingMethodKVP.entrySet()) {
                        if (entry.getKey().equals(String.valueOf(response.get("PreferredContactMethod"))))
                            preffered_contact = entry.getValue();
                    }
                    txt_issue_type.setText(issue_type);
                    txt_criticality.setText(criticality);
                    txt_preferred_contact_method.setText(preffered_contact);
//                    et_status.setText("Status: " + supportList.get(position).getStatus());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(getContext());
                new HaballError().printErrorMessage(getContext(), error);

                error.printStackTrace();
                Log.i("onErrorResponse", "Error");
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
        Volley.newRequestQueue(getContext()).add(request);


//
//
//        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("SupportId",
//                Context.MODE_PRIVATE);
//
//
//        ID = sharedPreferences3.getString("SupportId", "");
//        Log.i("SupportId", ID);
//        if (!URL_SUPPORT_VIEW.contains("//" + ID)) {
//            URL_SUPPORT_VIEW = URL_SUPPORT_VIEW + ID;
//            Log.i("URL_SUPPORT_VIEW", URL_SUPPORT_VIEW);
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_SUPPORT_VIEW, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    txt_business_name.setText(String.valueOf(response.get("ContactName")));
//                    txt_email_address.setText(String.valueOf(response.get("Email")));
//                    txt_mobile_number.setText(String.valueOf(response.get("MobileNumber")));
//                    txt_issue_type.setText(String.valueOf(response.get("IssueType")));
//                    txt_criticality.setText(String.valueOf(response.get("Criticality")));
//                    txt_preferred_contact_method.setText(String.valueOf(response.get("PreferredContactMethod")));
//                    txt_comments.setText(String.valueOf(response.get("Description")));
//                    ID = String.valueOf(response.get("Id"));
//                    tv_ticket_id.setText(ID);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(error);
//
//                error.printStackTrace();
//                Log.i("onErrorResponse", "Error");
//            }
//        });
//        Volley.newRequestQueue(getContext()).add(request);

    }


    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container_ret, new SupportFragment()).addToBackStack("null");
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });

    }

    // private void printErrorMessage(VolleyError error) {
    //     if (getContext() != null) {
    //         if (error instanceof NetworkError) {
    //             Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ServerError) {
    //             Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof AuthFailureError) {
    //             Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ParseError) {
    //             Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof NoConnectionError) {
    //             Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof TimeoutError) {
    //             Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
    //         }

    //         if (error.networkResponse != null && error.networkResponse.data != null) {
    //             try {
    //                 String message = "";
    //                 String responseBody = new String(error.networkResponse.data, "utf-8");
    //                 Log.i("responseBody", responseBody);
    //                 JSONObject data = new JSONObject(responseBody);
    //                 Log.i("data", String.valueOf(data));
    //                 Iterator<String> keys = data.keys();
    //                 while (keys.hasNext()) {
    //                     String key = keys.next();
    //                     message = message + data.get(key) + "\n";
    //                 }
    //                 Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    //             } catch (UnsupportedEncodingException e) {
    //                 e.printStackTrace();
    //             } catch (JSONException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    // }
}