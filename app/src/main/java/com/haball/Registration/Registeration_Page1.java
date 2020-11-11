package com.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.haball.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class Registeration_Page1 extends AppCompatActivity implements View.OnFocusChangeListener {

    private Button btn_register;
    private ImageButton btn_back;
    private String username, password, confirmpassword;
    private EditText txt_firstname, txt_lastname, txt_email, txt_cnic, txt_mobile_number, txt_phone_number, txt_ntn, txt_conpany_name, txt_website;

    private int keyDel;
    private ProgressDialog progressDialog;
    private Boolean email_check = false, phone_check = false, mobile_check = false, cnic_check = false, ntn_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__2);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);


        progressDialog = new ProgressDialog(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            password = extras.getString("password");
            confirmpassword = extras.getString("confirmpassword");
        }
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        txt_firstname = findViewById(R.id.txt_firstname);
        txt_lastname = findViewById(R.id.txt_lastname);
        txt_email = findViewById(R.id.txt_email);
        txt_cnic = findViewById(R.id.txt_cnic);
        txt_mobile_number = findViewById(R.id.txt_mobile_number);
        txt_phone_number = findViewById(R.id.txt_phone_number);
        txt_ntn = findViewById(R.id.txt_ntn);
        txt_conpany_name = findViewById(R.id.txt_conpany_name);
        txt_website = findViewById(R.id.txt_website);

        (findViewById(R.id.txt_firstname)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_lastname)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_conpany_name)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_email)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_cnic)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_mobile_number)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_phone_number)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_ntn)).setOnFocusChangeListener(this);

        txt_phone_number.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txt_phone_number.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = txt_phone_number.getText().length();
                    if (len == 3) {
                        txt_phone_number.setText(txt_phone_number.getText() + "-");
                        txt_phone_number.setSelection(txt_phone_number.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
        txt_mobile_number.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txt_mobile_number.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = txt_mobile_number.getText().length();
                    if (len == 4) {
                        txt_mobile_number.setText(txt_mobile_number.getText() + "-");
                        txt_mobile_number.setSelection(txt_mobile_number.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
        txt_cnic.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txt_cnic.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = txt_cnic.getText().length();
                    if (len == 5 || len == 13) {
                        txt_cnic.setText(txt_cnic.getText() + "-");
                        txt_cnic.setSelection(txt_cnic.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setEnabled(false);
        btn_register.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
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
        txt_firstname.addTextChangedListener(textWatcher);
        txt_lastname.addTextChangedListener(textWatcher);
        txt_email.addTextChangedListener(textWatcher);
        txt_cnic.addTextChangedListener(textWatcher);
        txt_mobile_number.addTextChangedListener(textWatcher);
        txt_phone_number.addTextChangedListener(textWatcher);
        txt_ntn.addTextChangedListener(textWatcher);
        txt_conpany_name.addTextChangedListener(textWatcher);
        txt_website.addTextChangedListener(textWatcher);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!phone_check && !mobile_check && !email_check && !cnic_check && !ntn_check) {
                    if (checkAllFields()) {
                        Snackbar.make(view, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(Registeration_Page1.this, Registration_page2.class);
                        i.putExtra("username", username);
                        i.putExtra("password", password);
                        i.putExtra("confirmpassword", confirmpassword);
                        i.putExtra("firstname", txt_firstname.getText().toString());
                        i.putExtra("lastname", txt_lastname.getText().toString());
                        i.putExtra("email", txt_email.getText().toString());
                        i.putExtra("cnic", txt_cnic.getText().toString());
                        i.putExtra("mobile_number", txt_mobile_number.getText().toString());
                        i.putExtra("phone_number", txt_phone_number.getText().toString());
                        i.putExtra("ntn", txt_ntn.getText().toString());
                        i.putExtra("conpany_name", txt_conpany_name.getText().toString());
                        i.putExtra("website", txt_website.getText().toString());
                        startActivity(i);
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkFieldsForEmptyValues() {

        String first_name = txt_firstname.getText().toString();
        String last_name = txt_lastname.getText().toString();
        String email = txt_email.getText().toString();
        String cnic_ = txt_cnic.getText().toString();
        String mobile = txt_mobile_number.getText().toString();
        String phone = txt_phone_number.getText().toString();
        String ntn = txt_ntn.getText().toString();
        String company_name = txt_conpany_name.getText().toString();
//        String website = txt_website.getText().toString();
        if (first_name.equals("")
                || last_name.equals("")
                || email.equals("")
                || cnic_.equals("")
                || mobile.equals("")
                || phone.equals("")
                || ntn.equals("")
                || company_name.equals("")
//                || website.equals("")
        ) {
            btn_register.setEnabled(false);
            btn_register.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            btn_register.setEnabled(true);
            btn_register.setBackground(getResources().getDrawable(R.drawable.button_background));
        }

    }

    private boolean checkAllFields() {
        if (TextUtils.isEmpty(txt_firstname.getText().toString()) ||
                TextUtils.isEmpty(txt_lastname.getText().toString()) ||
                TextUtils.isEmpty(txt_email.getText().toString()) ||
                TextUtils.isEmpty(txt_cnic.getText().toString()) ||
                TextUtils.isEmpty(txt_mobile_number.getText().toString()) ||
                TextUtils.isEmpty(txt_phone_number.getText().toString()) ||
                TextUtils.isEmpty(txt_ntn.getText().toString()) ||
                TextUtils.isEmpty(txt_conpany_name.getText().toString())
//                ||
//                TextUtils.isEmpty(txt_website.getText().toString())
        )
            return true;
        else
            return false;
    }

    private void checkEmail(final View view) throws JSONException {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (TextUtils.isEmpty(txt_email.getText().toString())) {
            txt_email.setError("This field is required");
        } else if (!txt_email.getText().toString().matches(reg_ex)) {
            txt_email.setError("Email (format: johnsmith@Example.com)\n");
        } else {
            txt_email.setError(null);
            String URL = "https://175.107.203.97:4013/api/users/CheckField";

            JSONObject map = new JSONObject();
            map.put("CName", "EmailAddress");
            map.put("CValue", txt_email.getText().toString());
            map.put("TbName", "useraccounts");
            String requestBody = map.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    if (response.toString().equals("true")) {
                        txt_email.setError("Email Already exists.");

//                    Snackbar.make(view, "Email Already exists.", Snackbar.LENGTH_LONG).show();
//                    progressDialog.dismiss();
                    }
                    email_check = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });

            Volley.newRequestQueue(this).add(booleanRequest);
        }
    }

    private void checkMobile(final View view) throws JSONException {
        if (TextUtils.isEmpty(txt_mobile_number.getText().toString())) {
            txt_mobile_number.setError("This field is required");
        } else {
            txt_mobile_number.setError(null);

            String URL = "https://175.107.203.97:4013/api/users/CheckField";

            JSONObject map = new JSONObject();
            map.put("CName", "Mobile");
            map.put("CValue", txt_mobile_number.getText().toString());
            map.put("TbName", "distributors");
            String requestBody = map.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    if (response.toString().equals("true")) {
                        txt_mobile_number.setError("Mobile Number Already exists.");
//                    Snackbar.make(view, "Mobile Number Already exists.", Snackbar.LENGTH_LONG).show();
//                    progressDialog.dismiss();
                    }
                    mobile_check = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });

            Volley.newRequestQueue(this).add(booleanRequest);
        }
    }

    private void checkPhone(final View view) throws JSONException {
        if (TextUtils.isEmpty(txt_phone_number.getText().toString())) {
            txt_phone_number.setError("This field is required");
        } else {
            txt_phone_number.setError(null);

            String URL = "https://175.107.203.97:4013/api/users/CheckField";

            JSONObject map = new JSONObject();
            map.put("CName", "Phone");
            map.put("CValue", txt_phone_number.getText().toString());
            map.put("TbName", "distributors");
            String requestBody = map.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    if (response.toString().equals("true")) {
                        txt_phone_number.setError("Phone Number Already exists.");
//                    Snackbar.make(view, "Phone Number Already exists.", Snackbar.LENGTH_LONG).show();
//                    progressDialog.dismiss();
                    }
                    phone_check = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });

            Volley.newRequestQueue(this).add(booleanRequest);
        }
    }

    private void checkCNIC(final View view) throws JSONException {

        if (TextUtils.isEmpty(txt_cnic.getText().toString())) {
            txt_cnic.setError("This field is required");
        } else {
            txt_cnic.setError(null);
            String URL = "https://175.107.203.97:4013/api/users/CheckField";

            JSONObject map = new JSONObject();
            map.put("CName", "CNIC");
            map.put("CValue", txt_cnic.getText().toString());
            map.put("TbName", "distributors");
            String requestBody = map.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    if (response.toString().equals("true")) {
                        txt_cnic.setError("CNIC Number Already exists.");
//                    Snackbar.make(view, "CNIC Number Already exists.", Snackbar.LENGTH_LONG).show();
//                    progressDialog.dismiss();
                    }
                    cnic_check = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });

            Volley.newRequestQueue(this).add(booleanRequest);
        }
    }

    private void checkNTN(final View view) throws JSONException {

        if (TextUtils.isEmpty(txt_ntn.getText().toString())) {
            txt_ntn.setError("This field is required");
        } else {
            txt_ntn.setError(null);

            String URL = "https://175.107.203.97:4013/api/users/CheckField";

            JSONObject map = new JSONObject();
            map.put("CName", "CompanyNTN");
            map.put("CValue", txt_ntn.getText().toString());
            map.put("TbName", "distributors");
            String requestBody = map.toString();

            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    if (response.toString().equals("true")) {
                        txt_ntn.setError("NTN Already exists.");

//                    Snackbar.make(view, "NTN Number Already exists.", Snackbar.LENGTH_LONG).show();
//                    progressDialog.dismiss();
                    }
                    ntn_check = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
            Volley.newRequestQueue(this).add(booleanRequest);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            switch (view.getId()) {
                case R.id.txt_firstname:
                    checkEmpty(txt_firstname);
                    break;
                case R.id.txt_lastname:
                    checkEmpty(txt_lastname);
                    break;
                case R.id.txt_conpany_name:
                    checkEmpty(txt_conpany_name);

                    break;
                case R.id.txt_email:
                    try {
                        checkEmail(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_phone_number:
                    try {
                        checkPhone(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_mobile_number:
                    try {
                        checkMobile(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_cnic:
                    try {
                        checkCNIC(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_ntn:
                    try {
                        checkNTN(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void checkEmpty(EditText et_id) {
        if (TextUtils.isEmpty(et_id.getText().toString()))
            et_id.setError("This field is required");
        else
            et_id.setError(null);
    }
}
