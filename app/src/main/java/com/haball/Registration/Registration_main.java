package com.haball.Registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.haball.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration_main extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText txt_username, txt_password, txt_confirm;
    private Boolean username_check = false, password_check = false, confirm_password_check = false;
    private TextInputLayout layout_txt_username, layout_txt_password, layout_txt_confirm;
    private  Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);
        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);


        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        final LayoutInflater inflater = LayoutInflater.from(this);

        @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.action_bar_main, null);

        bar.setCustomView(customView);
        bar.setDisplayShowCustomEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        bar.setTitle("");

        txt_username = findViewById(R.id.txt_username);
        layout_txt_username = findViewById(R.id.layout_txt_username);
        txt_password = findViewById(R.id.txt_password);
        layout_txt_password = findViewById(R.id.layout_txt_password);
        txt_confirm = findViewById(R.id.txt_confirm);
        layout_txt_confirm = findViewById(R.id.layout_txt_confirm);


        (findViewById(R.id.txt_username)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_password)).setOnFocusChangeListener(this);
        (findViewById(R.id.txt_confirm)).setOnFocusChangeListener(this);

        ImageButton btn_back = customView.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                finish();
            }
        });
        btn_next = findViewById(R.id.btn_next);
        btn_next.setEnabled(false);
        btn_next.setBackground( getResources().getDrawable( R.drawable.disabled_button_background ) );

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txt_username.getText().toString()) ||
                        TextUtils.isEmpty(txt_password.getText().toString()) ||
                        TextUtils.isEmpty(txt_confirm.getText().toString())) {
                    Snackbar.make(view, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (!username_check && password_check && confirm_password_check) {
                        Intent intent = new Intent(Registration_main.this, Registeration_Page1.class);
                        intent.putExtra("username", txt_username.getText().toString());
                        intent.putExtra("password", txt_password.getText().toString());
                        intent.putExtra("confirmpassword", txt_confirm.getText().toString());
                        startActivity(intent);
                    }
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

        txt_username.addTextChangedListener( textWatcher );
        txt_password.addTextChangedListener( textWatcher );
        txt_confirm.addTextChangedListener( textWatcher );
    }

    private void checkFieldsForEmptyValues() {
        String username_ = txt_username.getText().toString();
        String password = txt_password.getText().toString();
        String confrm_pass = txt_confirm.getText().toString();

        if (username_.equals( "" ) || password.equals( "" ) || confrm_pass.equals("")) {
            btn_next.setEnabled( false );
            btn_next.setBackground( getResources().getDrawable( R.drawable.disabled_button_background ) );

        } else {
            btn_next.setEnabled( true );
            btn_next.setBackground( getResources().getDrawable( R.drawable.button_background ) );
        }
    }

    private void checkPasswords() {
        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        if (txt_password.getText().toString().matches(reg_ex)) {
            password_check = true;
            layout_txt_password.setPasswordVisibilityToggleEnabled(true);

        } else {
//            layout_txt_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
//            layout_txt_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
//            layout_txt_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
//            txt_password.setErrorEnabled(true);
            txt_password.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
            txt_password.setTextColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_txt_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_txt_password.setPasswordVisibilityToggleEnabled(false);
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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        String txt_txt_username = txt_username.getText().toString();
        String txt_txt_password = txt_password.getText().toString();
        String txt_txt_confirmpass = txt_confirm.getText().toString();

        if (!txt_txt_username.equals("") || !txt_txt_password.equals("") || !txt_txt_confirmpass.equals("")) {
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
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
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

    private void checkUsername(final View view) throws JSONException {

        if (txt_username.getText().toString().equals("")) {
            txt_username.setError("This field is required");
            txt_username.setTextColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_username.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_username.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_txt_username.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));

        }
        else {
            txt_username.setError(null);

            String URL = "https://175.107.203.97:4013/api/users/CheckField";

            JSONObject map = new JSONObject();
            map.put("CName", "Username");
            map.put("CValue", txt_username.getText().toString());
            map.put("TbName", "useraccounts");
            String requestBody = map.toString();
            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    Log.i("response", String.valueOf(response));
                    if (response.toString().equals("true")) {
                        txt_username.setError("Username Already Exists.");
                    }
                    username_check = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(view, error.toString(), Snackbar.LENGTH_LONG).show();
                }
            });

            Volley.newRequestQueue(this).add(booleanRequest);

           }
        txt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_txt_username.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_txt_username.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_txt_username.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_username.setTextColor(getResources().getColor(R.color.textcolor));
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
                case R.id.txt_username:
                    try {
                        checkUsername(view);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.txt_password:
                    checkPasswords();
                    break;
                case R.id.txt_confirm:
                    checkConfirmPassword();
                    break;
            }
        }

    }

    private void checkConfirmPassword() {
        if (txt_password.getText().toString().equals(txt_confirm.getText().toString())) {
            confirm_password_check = true;

        } else {
            confirm_password_check = false;
            txt_confirm.setError("Password does not match");
            layout_txt_confirm.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_txt_confirm.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_txt_confirm.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_confirm.setTextColor(getResources().getColor(R.color.error_stroke_color));

        }
        txt_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_txt_confirm.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_txt_confirm.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_txt_confirm.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_confirm.setTextColor(getResources().getColor(R.color.textcolor));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkEmpty(EditText et_id) {
        if (TextUtils.isEmpty(et_id.getText().toString()))
            et_id.setError("This field is required");
        else
            et_id.setError(null);
    }
}
