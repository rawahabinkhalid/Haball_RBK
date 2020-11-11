package com.haball.Retailor.Retailor_SignUp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.haball.Retailer_Login.RetailerLogin;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.SSL_HandShake;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class SignUp extends AppCompatActivity implements View.OnFocusChangeListener {

    private ImageButton btn_back;
    private String URL = "http://175.107.203.97:4014/api/retailer/Register";
    private EditText txt_username, txt_password, txt_confirmpass, txt_fullname, txt_email, txt_cnic, txt_mobile_number, txt_business_name, txt_address;
    private Button btn_register_signup, btn_register_close;
    private Boolean password_check = false, confirm_password_check = false;
    private int keyDel;
    private TextInputLayout layout_txt_password, layout_txt_confirmpass;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        loader = new Loader(SignUp.this);

        final LayoutInflater inflater = LayoutInflater.from(this);

        View customView = inflater.inflate(R.layout.action_bar_main, null);
        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirmpass = (EditText) findViewById(R.id.txt_confirmpass);
        txt_fullname = (EditText) findViewById(R.id.txt_fullname);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_cnic = (EditText) findViewById(R.id.txt_cnic);
        txt_mobile_number = (EditText) findViewById(R.id.txt_mobile_number);
        txt_business_name = (EditText) findViewById(R.id.txt_business_name);
        txt_address = (EditText) findViewById(R.id.txt_address);
        btn_register_signup = (Button) findViewById(R.id.btn_register_signup);
        layout_txt_password = findViewById(R.id.layout_txt_password);
        layout_txt_confirmpass = findViewById(R.id.layout_txt_confirmpass);

        txt_username.addTextChangedListener(watcher);
        txt_password.addTextChangedListener(watcher);
        txt_confirmpass.addTextChangedListener(watcher);
        txt_fullname.addTextChangedListener(watcher);
        txt_email.addTextChangedListener(watcher);
        txt_cnic.addTextChangedListener(watcher);
        txt_mobile_number.addTextChangedListener(watcher);
        txt_business_name.addTextChangedListener(watcher);
        txt_address.addTextChangedListener(watcher);
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

        btn_register_signup.setEnabled(false);
        btn_register_signup.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        (findViewById(R.id.txt_password)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_confirmpass)).setOnFocusChangeListener(this);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_register_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    makeRegisterRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_register_close = findViewById(R.id.btn_register_close);
        btn_register_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(txt_username.getText())
                    || TextUtils.isEmpty(txt_password.getText())
                    || TextUtils.isEmpty(txt_confirmpass.getText())
                    || TextUtils.isEmpty(txt_fullname.getText())
                    || TextUtils.isEmpty(txt_email.getText())
                    || TextUtils.isEmpty(txt_cnic.getText())
                    || TextUtils.isEmpty(txt_mobile_number.getText())
                    || TextUtils.isEmpty(txt_business_name.getText())
                    || TextUtils.isEmpty(txt_address.getText())
                    || (!password_check && !confirm_password_check)) {
                btn_register_signup.setEnabled(false);
                btn_register_signup.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

            } else {
                btn_register_signup.setEnabled(true);
                btn_register_signup.setBackground(getResources().getDrawable(R.drawable.button_background));
            }
        }
    };

    private void makeRegisterRequest() throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        if (password_check && confirm_password_check) {
            map.put("ID", 0);
            map.put("Email", txt_email.getText().toString());
            map.put("Username", txt_username.getText().toString());
            map.put("Password", txt_password.getText().toString());
            map.put("ConfirmPassword", txt_confirmpass.getText().toString());
            map.put("Name", txt_fullname.getText().toString());
            map.put("CNIC", txt_cnic.getText().toString());
            map.put("Mobile", txt_mobile_number.getText().toString());
            map.put("CompanyName", txt_business_name.getText().toString());
            map.put("Address", txt_address.getText().toString());
            map.put("CountryId", 1);
            map.put("ProvinceId", 1);
            map.put("CityId", 1);
            map.put("PostCode", "12345");

            Log.i("MAP OBJECT", String.valueOf(map));
            new SSL_HandShake().handleSSLHandshake();
//            final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(this);

            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL, map, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    loader.hideLoader();
                    Log.e("RESPONSE", result.toString());
                    try {
                        if (!result.get("RetailerCode").toString().isEmpty()) {
                            Intent i = new Intent(SignUp.this, RetailerLogin.class);
                            Toast.makeText(SignUp.this, "You have been registered successfully, please use login credentials to access the Portal.", Toast.LENGTH_LONG).show();
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SignUp.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loader.hideLoader();
                    new ProcessingError().showError(SignUp.this);
                    new HaballError().printErrorMessage(SignUp.this, error);
                    error.printStackTrace();
                    //  Toast.makeText(SignUp.this,error.toString(),Toast.LENGTH_LONG).show();
                }

            });
            Volley.newRequestQueue(this).add(sr);
        } else {

            layout_txt_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_txt_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_password.setTextColor(getResources().getColor(R.color.error_stroke_color));
            Toast.makeText(SignUp.this, "Password does not match", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        String txt_txt_username = txt_username.getText().toString();
        String txt_txt_password = txt_password.getText().toString();
        String txt_txt_confirmpass = txt_confirmpass.getText().toString();
        String txt_txt_fullname = txt_fullname.getText().toString();
        String txt_txt_email = txt_email.getText().toString();
        String txt_txt_cnic = txt_cnic.getText().toString();
        String txt_txt_mobile_number = txt_mobile_number.getText().toString();
        String txt_txt_business_name = txt_business_name.getText().toString();
        String txt_txt_address = txt_address.getText().toString();

        if (!txt_txt_username.equals("") || !txt_txt_password.equals("") || !txt_txt_confirmpass.equals("") || !txt_txt_fullname.equals("") || !txt_txt_email.equals("") || !txt_txt_cnic.equals("") || !txt_txt_mobile_number.equals("") || !txt_txt_business_name.equals("") || !txt_txt_address.equals("")) {
            showDiscardDialog();
        } else {
            finish();
        }
    }


    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
//        final FragmentManager fm = getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
        alertDialog.setView(view_popup);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                finish();
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

    private void checkPasswords() {
        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        if (txt_password.getText().toString().matches(reg_ex)) {
            password_check = true;
            layout_txt_password.setPasswordVisibilityToggleEnabled(true);

        } else {
            txt_password.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
            password_check = false;
            layout_txt_password.setPasswordVisibilityToggleEnabled(false);
            txt_password.setTextColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_txt_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));


        }
        txt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_txt_password.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_txt_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_txt_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_password.setTextColor(getResources().getColor(R.color.textcolor));
                layout_txt_password.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void checkConfirmPassword() {
        if (txt_password.getText().toString().equals(txt_confirmpass.getText().toString())) {
            confirm_password_check = true;
            layout_txt_confirmpass.setPasswordVisibilityToggleEnabled(true);


        } else {
            confirm_password_check = false;
            txt_confirmpass.setError("Password does not match");
            layout_txt_confirmpass.setPasswordVisibilityToggleEnabled(false);
            layout_txt_confirmpass.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_confirmpass.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_txt_confirmpass.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_confirmpass.setTextColor(getResources().getColor(R.color.error_stroke_color));


        }
        txt_confirmpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_txt_confirmpass.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_txt_confirmpass.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_txt_confirmpass.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_confirmpass.setTextColor(getResources().getColor(R.color.textcolor));
                layout_txt_confirmpass.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            switch (view.getId()) {
                case R.id.txt_password:
                    checkPasswords();
                    break;
                case R.id.txt_confirmpass:
                    checkConfirmPassword();
                    break;
            }
        }

    }

//     private void printErrorMessage(VolleyError error) {
//         if (error instanceof NetworkError) {
//             Toast.makeText(SignUp.this, "Network Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof ServerError) {
//             Toast.makeText(SignUp.this, "Server Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof AuthFailureError) {
//             Toast.makeText(SignUp.this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof ParseError) {
//             Toast.makeText(SignUp.this, "Parse Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof NoConnectionError) {
//             Toast.makeText(SignUp.this, "No Connection Error !", Toast.LENGTH_LONG).show();
//         } else if (error instanceof TimeoutError) {
//             Toast.makeText(SignUp.this, "Timeout Error !", Toast.LENGTH_LONG).show();
//         }

//         if (error.networkResponse != null && error.networkResponse.data != null) {
//             try {
//                 String message = "";
//                 String responseBody = new String(error.networkResponse.data, "utf-8");
//                 JSONObject data = new JSONObject(responseBody);
//                 Iterator<String> keys = data.keys();
//                 while (keys.hasNext()) {
//                     String key = keys.next();
// //                if (data.get(key) instanceof JSONObject) {
//                     message = message + data.get(key) + "\n";
// //                }
//                 }
// //                    if(data.has("message"))
// //                        message = data.getString("message");
// //                    else if(data. has("Error"))
//                 Toast.makeText(SignUp.this, message, Toast.LENGTH_LONG).show();
//             } catch (UnsupportedEncodingException e) {
//                 e.printStackTrace();
//             } catch (JSONException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
}
