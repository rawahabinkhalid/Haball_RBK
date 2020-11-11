package com.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distribution_Login.Distribution_Login;

import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

public class Registration_page2 extends AppCompatActivity {

    private Button btn_register;
    private ImageButton btn_back;
    private String username, password, confirmpassword, firstname, lastname, email, cnic, mobile_number, phone_number, ntn, conpany_name, website;
    private EditText Address, postal_shipping, Address02, postal_billing;
    private String URL_SPINNERS_COUNTRY = "https://175.107.203.97:4013/api/country";
    private String URL_SPINNERS_PROVINCE = "https://175.107.203.97:4013/api/state/ReadByCountry/1";
    private String URL_SPINNERS_CITY = "https://175.107.203.97:4013/api/city/ReadByState/1";
    private String URL = "https://175.107.203.97:4013/api/distributor/Register";

    private CheckBox check_box;
    private Boolean check_value = false;
    private RelativeLayout rl_billing_address;
    private String country1, country2, city1, city2, province1, province2;
    private Spinner spinner_country, spinner_city, spinner_province, spinner_country2, spinner_city2, spinner_province2;

    private ArrayList<String> countries, cities, provinces;

    ArrayAdapter<String> arrayAdapterCountry, arrayAdapterCity, arrayAdapterProvince;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__actvity2);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);

        countries = new ArrayList<>();
        cities = new ArrayList<>();
        provinces = new ArrayList<>();

        countries.add("Select Country *");
        cities.add("Select City *");
        provinces.add("Select Province *");

        arrayAdapterCountry = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, countries);
        arrayAdapterCity = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, cities);
        arrayAdapterProvince = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, provinces);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            password = extras.getString("password");
            confirmpassword = extras.getString("confirmpassword");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
            email = extras.getString("email");
            cnic = extras.getString("cnic");
            mobile_number = extras.getString("mobile_number");
            phone_number = extras.getString("phone_number");
            ntn = extras.getString("ntn");
            conpany_name = extras.getString("conpany_name");
            website = extras.getString("website");
        }

        spinner_country = findViewById(R.id.spinner_country);
        spinner_city = findViewById(R.id.spinner_city);
        spinner_province = findViewById(R.id.spinner_province);
        spinner_country2 = findViewById(R.id.spinner_country2);
        spinner_city2 = findViewById(R.id.spinner_city2);
        spinner_province2 = findViewById(R.id.spinner_province2);

        rl_billing_address = findViewById(R.id.rl_billing);
        check_box = findViewById(R.id.check_box);


        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_value = isChecked;
                if (isChecked) {
                    rl_billing_address.setVisibility(View.GONE);
                } else {
                    rl_billing_address.setVisibility(View.VISIBLE);
                }
                checkFieldsForEmptyValues();
            }
        });

        spinner_country.setPrompt("Choose Widget Mode");
        spinner_city.setPrompt("Choose Widget Mode");
        spinner_province.setPrompt("Choose Widget Mode");
        spinner_country2.setPrompt("Choose Widget Mode");
        spinner_city2.setPrompt("Choose Widget Mode");
        spinner_province2.setPrompt("Choose Widget Mode");

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading,\n please wait.");
//        progressDialog.show();

        fetch_countries();
        fetch_province();
        fetch_cities();

        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
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
                    country1 = countries.get(i);
                    checkFieldsForEmptyValues();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
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
                    province1 = provinces.get(i);
                    checkFieldsForEmptyValues();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
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
                    city1 = cities.get(i);
                    checkFieldsForEmptyValues();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_country2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
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
                    country2 = countries.get(i);
                    checkFieldsForEmptyValues();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_province2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
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
                    province2 = provinces.get(i);
                    checkFieldsForEmptyValues();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
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
                    checkFieldsForEmptyValues();
                    city2 = cities.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        Address = findViewById(R.id.Address);
        postal_shipping = findViewById(R.id.postal_shipping);
        Address02 = findViewById(R.id.Address02);
        postal_billing = findViewById(R.id.postal_billing);

        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                finish();
            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setEnabled(false);
        btn_register.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeRegisterRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();

            }
        };
        Address.addTextChangedListener(textWatcher);
        postal_shipping.addTextChangedListener(textWatcher);
        Address02.addTextChangedListener(textWatcher);
        postal_billing.addTextChangedListener(textWatcher);


    }

    private void checkFieldsForEmptyValues() {
        String address = Address.getText().toString();
        String postal_ship = postal_shipping.getText().toString();
        String address_2 = Address02.getText().toString();
        String postal_bill = postal_billing.getText().toString();
        String country = (String) spinner_country.getItemAtPosition(spinner_country.getSelectedItemPosition()).toString();
        String province = spinner_province.getItemAtPosition(spinner_province.getSelectedItemPosition()).toString();
        String city = spinner_city.getItemAtPosition(spinner_city.getSelectedItemPosition()).toString();
        String country2 = spinner_country2.getItemAtPosition(spinner_country2.getSelectedItemPosition()).toString();
        String province2 = spinner_province2.getItemAtPosition(spinner_province2.getSelectedItemPosition()).toString();
        String city2 = spinner_city2.getItemAtPosition(spinner_city2.getSelectedItemPosition()).toString();

        if (address.equals("")
                || postal_ship.equals("")
                || country.equals("Select Country *")
                || province.equals("Select Province *")
                || city.equals("Select City *")
        ) {
            if (check_value) {
                btn_register.setEnabled(true);
                btn_register.setBackground(getResources().getDrawable(R.drawable.button_background));
            } else {
                if (address_2.equals("")
                        || postal_bill.equals("")
                        || country2.equals("Select Country *")
                        || province2.equals("Select Province *")
                        || city2.equals("Select City *")

                ) {
                    btn_register.setEnabled(false);
                    btn_register.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

                } else {
                    btn_register.setEnabled(true);
                    btn_register.setBackground(getResources().getDrawable(R.drawable.button_background));
                }
            }

        } else {
            btn_register.setEnabled(true);
            btn_register.setBackground(getResources().getDrawable(R.drawable.button_background));
        }

    }

    private void makeRegisterRequest() throws JSONException {
        JSONObject map = new JSONObject();
        if (check_value) {
            map.put("status", 1);
            map.put("UserType", 0);
            map.put("Username", username);
            map.put("Password", password);
            map.put("ConfirmPassword", confirmpassword);
            map.put("FirstName", firstname);
            map.put("LastName", lastname);
            map.put("Email", email);
            map.put("CNIC", cnic);
            map.put("Mobile", mobile_number);
            map.put("Phone", phone_number);
            map.put("CompanyNTN", ntn);
            map.put("CompanyName", conpany_name);
            map.put("URL", website);
            map.put("ShippingCountryId", 1);
            map.put("BillingCountryId", 1);
            map.put("ShippingProvinceId", 1);
            map.put("BillingProvinceId", 1);
            map.put("ShippingCityId", 1);
            map.put("BillingCityId", 1);
            map.put("ShippingAddress1", Address.getText().toString());
            map.put("BillingAddress1", Address.getText().toString());
            map.put("ShippingPostCode", postal_shipping.getText().toString());
            map.put("BillingPostCode", postal_shipping.getText().toString());
            map.put("IsAgree", true);
        } else {
            map.put("status", 1);
            map.put("UserType", 0);
            map.put("Username", username);
            map.put("Password", password);
            map.put("ConfirmPassword", confirmpassword);
            map.put("FirstName", firstname);
            map.put("LastName", lastname);
            map.put("Email", email);
            map.put("CNIC", cnic);
            map.put("Mobile", mobile_number);
            map.put("Phone", phone_number);
            map.put("CompanyNTN", ntn);
            map.put("CompanyName", conpany_name);
            map.put("URL", website);
            map.put("ShippingCountryId", 1);
            map.put("BillingCountryId", 1);
            map.put("ShippingProvinceId", 1);
            map.put("BillingProvinceId", 1);
            map.put("ShippingCityId", 1);
            map.put("BillingCityId", 1);
            map.put("ShippingAddress1", Address.getText().toString());
            map.put("BillingAddress1", Address02.getText().toString());
            map.put("ShippingPostCode", postal_shipping.getText().toString());
            map.put("BillingPostCode", postal_billing.getText().toString());
            map.put("IsAgree", true);
        }

        Log.i("Register_Activity", String.valueOf(map));

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.i("Register_Activity", result.toString());
                try {
                    if (!result.get("DealerCode").toString().isEmpty()) {
                        Intent i = new Intent(Registration_page2.this, Distribution_Login.class);
                        Toast.makeText(Registration_page2.this, "You have been registered successfully, please use login credentials to access the Portal.", Toast.LENGTH_LONG).show();
                        startActivity(i);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration_page2.this, e.toString(), Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Register_Activity", String.valueOf(error));
                new HaballError().printErrorMessage(Registration_page2.this, error);
                new ProcessingError().showError(Registration_page2.this);
                error.printStackTrace();
                //  Toast.makeText(Registration_Actvity2.this,error.toString(),Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(this).add(sr);
    }

    private void fetch_cities() {
        final JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNERS_CITY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject obj = result.getJSONObject(0);
                    cities.add(obj.getString("Name"));
                    Log.i("cities", cities.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF CITIES", result.toString());
//                Toast.makeText(Registration_Actvity2.this,result.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(Registration_page2.this, error);
                new ProcessingError().showError(Registration_page2.this);
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCity.notifyDataSetChanged();
        spinner_city.setAdapter(arrayAdapterCity);
        spinner_city2.setAdapter(arrayAdapterCity);
    }

    private void fetch_province() {
        final JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNERS_PROVINCE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject obj = result.getJSONObject(i);
                        provinces.add(obj.getString("Name"));
                    }
                    Log.i("provinces", provinces.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF PROVINCES", result.toString());
//                Toast.makeText(Registration_Actvity2.this,result.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(Registration_page2.this, error);
                new ProcessingError().showError(Registration_page2.this);
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterProvince.notifyDataSetChanged();
        spinner_province.setAdapter(arrayAdapterProvince);
        spinner_province2.setAdapter(arrayAdapterProvince);

    }

    private void fetch_countries() {

        final JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNERS_COUNTRY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject obj = result.getJSONObject(0);
                    countries.add(obj.getString("Name"));
                    Log.i("countries", countries.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF COUNTRIES", result.toString());
//                Toast.makeText(Registration_Actvity2.this,result.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(Registration_page2.this, error);
                new ProcessingError().showError(Registration_page2.this);
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(sr);
        arrayAdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCountry.notifyDataSetChanged();
        spinner_country.setAdapter(arrayAdapterCountry);
        spinner_country2.setAdapter(arrayAdapterCountry);

    }

    private void printErrMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(Registration_page2.this, "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(Registration_page2.this, "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(Registration_page2.this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(Registration_page2.this, "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(Registration_page2.this, "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(Registration_page2.this, "Timeout Error !", Toast.LENGTH_LONG).show();
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                JSONObject data = new JSONObject(responseBody);
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
//                if (data.get(key) instanceof JSONObject) {
                    message = message + data.get(key) + "\n";
//                }
                }
//                    if(data.has("message"))
//                        message = data.getString("message");
//                    else if(data. has("Error"))
                Toast.makeText(Registration_page2.this, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
