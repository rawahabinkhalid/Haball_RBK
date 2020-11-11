package com.haball.Retailor.Retailer_Support;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.R;
import com.haball.Support.Support_Ditributor.Support_Ticket_Form;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Support_Ticket_Form_Retailer extends AppCompatActivity {

    private EditText BName, Email, MobileNo, Comment;
    private ImageButton btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_ISSUETYPE = "http://175.107.203.97:4030/api/lookup/public/ISSUE_TYPE_PUBLIC";
    private String URL_SPINNER_CRITICALITY = "http://175.107.203.97:4030/api/lookup/public/CRITICALITY_PUBLIC";
    private String URL_SPINNER_PREFFEREDCONTACT = "http://175.107.203.97:4030/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4030/api/contact/save";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();

    private String issueType,Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter<String> arrayAdapterIssueType, arrayAdapterCriticality, arrayAdapterPreferredContact;

    private Button login_submit,login_btn;

    private String DistributorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support__ticket__form__retailer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.action_bar_main, null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);


        BName = findViewById(R.id.BName);
        Email = findViewById(R.id.Email);
        MobileNo = findViewById(R.id.MobileNo);
        Comment = findViewById(R.id.Comment);
        IssueType = findViewById(R.id.IssueType);
        critcicality = findViewById(R.id.critcicality);
        Preffered_Contact = findViewById(R.id.Preffered_Contact);
        login_submit= findViewById(R.id.login_submit);
        login_btn = findViewById(R.id.login_btn);
        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        issue_type.add("Issue Type *");
        criticality.add("Criticality *");
        preffered_contact.add("Preferred Method of Contacting *");

        arrayAdapterIssueType = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, issue_type);
        arrayAdapterCriticality = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, criticality);
        arrayAdapterPreferredContact = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, preffered_contact);

        fetchIssueType();
        fetchCriticality();
        fetchPrefferedContact();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    issueType = issue_type.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        critcicality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    Criticality = criticality.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Preffered_Contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    try {
                           ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                      ((TextView) adapterView.getChildAt(0)).setPadding(30,0 ,30 ,0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    PrefferedContacts = preffered_contact.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeTicketAddRequest();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void makeTicketAddRequest() throws JSONException {
        JSONObject map = new JSONObject();
        map.put("Name", BName.getText().toString());
        map.put("EmailAddress", Email.getText().toString());
        map.put("Mobile", MobileNo.getText().toString());
        map.put("DistributorId", DistributorId);
        map.put("IssueType", issueType);
        map.put("Criticality", Criticality);
        map.put("ContactingMethod", PrefferedContacts);
        map.put("Message", Comment.getText().toString());
        map.put("ID", 0);

        Log.i("TICKET OBJECT", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.e("RESPONSE", result.toString());
                finish();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }

        });
        Volley.newRequestQueue(this).add(sr);
    }

    private void fetchIssueType() {
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_ISSUETYPE,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                        issue_type.add(jsonObject.getString("value"));
                    }
                    Log.i("issue type values => ",issue_type.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF ISSUE TYPE", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterIssueType.notifyDataSetChanged();
        IssueType.setAdapter(arrayAdapterIssueType);
    }

    private void fetchCriticality() {
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_CRITICALITY,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                        criticality.add(jsonObject.getString("value"));

                    }
                    Log.i("criticality values => ",criticality.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF criticality", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCriticality.notifyDataSetChanged();
        critcicality.setAdapter(arrayAdapterCriticality);
    }

    private void fetchPrefferedContact() {
        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_PREFFEREDCONTACT,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for(int i=0;i<result.length();i++){
                        jsonObject  = result.getJSONObject(i);
                        preffered_contact.add(jsonObject.getString("value"));
                    }

                    Log.i("preffered_contact => ",preffered_contact.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE preferedcont", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("message");
                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer "+Token);
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPreferredContact.notifyDataSetChanged();
        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
    }

}




