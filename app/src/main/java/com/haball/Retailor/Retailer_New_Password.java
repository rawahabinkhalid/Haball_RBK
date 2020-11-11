package com.haball.Retailor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.CustomToast;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.Forgot_Password_Retailer.Forgot_Pass_Retailer;
import com.haball.Retailor.ui.Dashboard.DashBoardFragment;
import com.haball.Retailor.ui.Dashboard.Dashboard_Tabs;
import com.haball.SSL_HandShake;
import com.haball.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Retailer_New_Password extends AppCompatActivity {

    private Button update_password, btn_back;
    private String URL = "http://175.107.203.97:4014/api/users/UpdatePasswordByLink";
    private String URL_TokenValidate = "http://175.107.203.97:4014/api/users/ValidateToken";
    private String Token;
    private String UserName, Name;
    private TextInputLayout layout_password3, layout_password1;
    private TextInputEditText txt_cfmpassword, txt_newpassword;
    private Boolean password_check = false, confirm_password_check = false;
    private int keyDel;
    private TextView txt_change1;
    private TextView tv_pr1, txt_header1;
    boolean doubleBackToExitPressedOnce = false;
    private Loader loader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_password);
        loader = new Loader(Retailer_New_Password.this);

        Uri uri = getIntent().getData();
        if (uri != null) {
            Token = String.valueOf(uri).split("\\?(?!\\?)")[1];
        }

        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
        rl_main_background.setBackground(background_drawable);
        update_password = findViewById(R.id.update_password);
        btn_back = findViewById(R.id.btn_back);
        layout_password3 = findViewById(R.id.layout_password3);
        txt_cfmpassword = findViewById(R.id.txt_cfmpassword);
        txt_change1 = findViewById(R.id.txt_change1);

        layout_password1 = findViewById(R.id.layout_password1);
        txt_newpassword = findViewById(R.id.txt_newpassword);

//        SharedPreferences sharedPreferences = getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//        UserName = sharedPreferences.getString("username", "");

//        SharedPreferences sharedPreferences1 = getSharedPreferences("SendData",
//                Context.MODE_PRIVATE);
//        Name = sharedPreferences1.getString("first_name", "");

//        txt_change1.setText("Welcome " + Name + " to Haball's App. It is recommended to change the default password.");

        update_password.setEnabled(false);
        update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        txt_newpassword.addTextChangedListener(watcher);
        txt_cfmpassword.addTextChangedListener(watcher);

        try {
            validateToken();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new TextField().changeColor(Retailer_New_Password.this, layout_password3, txt_cfmpassword);
        new TextField().changeColor(Retailer_New_Password.this, layout_password1, txt_newpassword);

        txt_cfmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkConfirmPassword(hasFocus);

            }
        });
        txt_newpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkPasswords(hasFocus);

            }
        });

        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updatePassword();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(txt_newpassword.getText()).equals("") || !String.valueOf(txt_cfmpassword.getText()).equals(""))
                    showDiscardDialog();
                else {
                    Intent intent = new Intent(Retailer_New_Password.this, RetailerLogin.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void validateToken() throws JSONException {
        loader.showLoader();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", Token);
        Log.i("Password_Log", String.valueOf(jsonObject));

        String requestBody = jsonObject.toString();
        
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(Retailer_New_Password.this);

        BooleanRequest sr = new BooleanRequest(Request.Method.POST, URL_TokenValidate, requestBody, new Response.Listener<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Boolean result) {
                loader.hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(Retailer_New_Password.this, error);
                error.printStackTrace();
                final Dialog fbDialogue = new Dialog(Retailer_New_Password.this);
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);

                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
//                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
                txt_header1.setText("Session Expired");
                txt_header1.setTextColor(getResources().getColor(R.color.error_stroke_color));
                txt_header1.setBackground(ContextCompat.getDrawable(Retailer_New_Password.this, R.drawable.border_set_error));
                tv_pr1.setText("The requested session has expired. Please request again for password reset email. If the problem persists, contact support.");
                fbDialogue.setCancelable(true);
                fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
                layoutParams.y = 200;
                layoutParams.x = -70;// top margin
                fbDialogue.getWindow().setAttributes(layoutParams);
                fbDialogue.show();

                ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fbDialogue.dismiss();
                    }
                });

                fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent intent = new Intent(Retailer_New_Password.this, RetailerLogin.class);
                        startActivity(intent);
                        finish();
                    }
                });
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
        Volley.newRequestQueue(this).add(sr);
    }


    private void showDiscardDialog() {
        final FragmentManager fm = getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view_popup = inflater.inflate(R.layout.discard_changes, null);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        alertDialog.setCancelable(true);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard.setText("Alert");
        tv_discard_txt.setText("Are you sure, you want to exit this page?");
        btn_discard.setText("Yes");


        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        alertDialog.setView(view_popup);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                Intent intent = new Intent(Retailer_New_Password.this, RetailerLogin.class);
                startActivity(intent);
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
                img_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });

            }
        });


        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (!String.valueOf(txt_newpassword.getText()).equals("") || !String.valueOf(txt_cfmpassword.getText()).equals(""))
            showDiscardDialog();
        else {
            Intent intent = new Intent(Retailer_New_Password.this, RetailerLogin.class);
            startActivity(intent);
        }
    }

    private final TextWatcher watcher = new TextWatcher() {
        //        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        String reg_ex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkPasswords(true);
            checkConfirmPassword(true);
            if (TextUtils.isEmpty(txt_cfmpassword.getText())
                    || TextUtils.isEmpty(txt_newpassword.getText())
                    || !String.valueOf(txt_newpassword.getText()).matches(reg_ex)
                    || !String.valueOf(txt_cfmpassword.getText()).matches(reg_ex)
                    || (!password_check && !confirm_password_check)) {
                update_password.setEnabled(false);
                update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

            } else {
                update_password.setEnabled(true);
                update_password.setBackground(getResources().getDrawable(R.drawable.button_background));
            }
        }
    };

    private void updatePassword() throws JSONException {
        if (password_check && confirm_password_check) {
            loader.showLoader();
            Log.i("Token", Token);
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("Username", "1283798123981isaodio");
            jsonObject.put("NewPassword", txt_newpassword.getText());
            jsonObject.put("ConfirmPassword", txt_cfmpassword.getText());
            Log.i("Password_Log", String.valueOf(jsonObject));

            String requestBody = jsonObject.toString();
            
            new SSL_HandShake().handleSSLHandshake();
//            final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(Retailer_New_Password.this);

            BooleanRequest sr = new BooleanRequest(Request.Method.POST, URL, requestBody, new Response.Listener<Boolean>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Boolean result) {
                    loader.hideLoader();
                    if (result) {
                        final Dialog fbDialogue = new Dialog(Retailer_New_Password.this);
                        //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                        fbDialogue.setContentView(R.layout.password_updatepopup);

                        txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                        tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                        txt_header1.setText("Password Changed");
                        tv_pr1.setText("Your password has been changed. You can login with the new credentials.");
                        fbDialogue.setCancelable(true);
                        fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                        WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
                        layoutParams.y = 200;
                        layoutParams.x = -70;// top margin
                        fbDialogue.getWindow().setAttributes(layoutParams);
                        fbDialogue.show();

                        ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                        close_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                fbDialogue.dismiss();
                            }
                        });

                        fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                nullifySharedPreference();
                                Intent intent = new Intent(Retailer_New_Password.this, RetailerLogin.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loader.hideLoader();
                    new HaballError().printErrorMessage(Retailer_New_Password.this, error);
                    error.printStackTrace();
                    final Dialog fbDialogue = new Dialog(Retailer_New_Password.this);
                    //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                    fbDialogue.setContentView(R.layout.password_updatepopup);

                    tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                    txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
//                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
                    txt_header1.setText("Processing Error");
                    txt_header1.setTextColor(getResources().getColor(R.color.error_stroke_color));
                    txt_header1.setBackground(ContextCompat.getDrawable(Retailer_New_Password.this, R.drawable.border_set_error));
                    tv_pr1.setText("An error occurred while processing your request. Please try again later. If the problem persists, contact support.");
                    fbDialogue.setCancelable(true);
                    fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                    WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
                    layoutParams.y = 200;
                    layoutParams.x = -70;// top margin
                    fbDialogue.getWindow().setAttributes(layoutParams);
                    fbDialogue.show();

                    ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                    close_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fbDialogue.dismiss();
                        }
                    });

                    fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Intent intent = new Intent(Retailer_New_Password.this, RetailerLogin.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("authorization", "Bearer " + Token);
                    params.put("Content-Type", "application/json");

                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(this).add(sr);
        } else {
            layout_password1.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_newpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
//            Toast.makeText(Retailer_New_Password.this, "Password does not match", Toast.LENGTH_LONG).show();
            new CustomToast().showToast(Retailer_New_Password.this, "Password mismatch");
        }
    }


    private void checkPasswords(final boolean hasFocus) {
//        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        String reg_ex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";
        Log.i("Password_Log", "in password check");

        if (!hasFocus && txt_newpassword.getText().toString().trim().equals("")) {
            layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.edit_text_hint_color)));
        } else {
            layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));

            txt_newpassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (txt_newpassword.getText().toString().trim().equals("") && !hasFocus) {
                        layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.edit_text_hint_color)));
                    } else {
                        layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

        if (txt_newpassword.getText().toString().matches(reg_ex) || txt_newpassword.getText().toString().equals("")) {
            password_check = true;
//            layout_password1.setPasswordVisibilityToggleEnabled(true);

        } else {
//            txt_newpassword.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
            password_check = false;
//            layout_password1.setPasswordVisibilityToggleEnabled(false);
            txt_newpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
            layout_password1.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));


        }
        txt_newpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_password1.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_newpassword.setTextColor(getResources().getColor(R.color.textcolor));
//                layout_password1.setPasswordVisibilityToggleEnabled(true);
                layout_password3.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_cfmpassword.setTextColor(getResources().getColor(R.color.textcolor));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void checkConfirmPassword(final boolean hasFocus) {
        Log.i("Password_Log", "in password check1");
        if (!hasFocus && txt_cfmpassword.getText().toString().trim().equals("")) {
            layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.edit_text_hint_color)));
        } else {
            layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));

            txt_cfmpassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (txt_cfmpassword.getText().toString().trim().equals("") && !hasFocus) {
                        layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.edit_text_hint_color)));
                    } else {
                        layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }
        if (txt_newpassword.getText().toString().equals(txt_cfmpassword.getText().toString())) {
            Log.i("Password_Log", "in password check2");
            confirm_password_check = true;
//            layout_password3.setPasswordVisibilityToggleEnabled(true);
        } else {
            confirm_password_check = false;
//            txt_cfmpassword.setError("Password does not match");
//            layout_password3.setPasswordVisibilityToggleEnabled(false);
            layout_password3.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_cfmpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));


        }
        txt_cfmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_password3.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_cfmpassword.setTextColor(getResources().getColor(R.color.textcolor));
//                layout_password3.setPasswordVisibilityToggleEnabled(true);

                layout_password1.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_newpassword.setTextColor(getResources().getColor(R.color.textcolor));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    // private void printErrorMessage(VolleyError error) {

    //     if (error instanceof NetworkError) {
    //         Toast.makeText(this, "Network Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof ServerError) {
    //         Toast.makeText(this, "Server Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof AuthFailureError) {
    //         Toast.makeText(this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof ParseError) {
    //         Toast.makeText(this, "Parse Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof NoConnectionError) {
    //         Toast.makeText(this, "No Connection Error !", Toast.LENGTH_LONG).show();
    //     } else if (error instanceof TimeoutError) {
    //         Toast.makeText(this, "Timeout Error !", Toast.LENGTH_LONG).show();
    //     }

    //     if (error.networkResponse != null && error.networkResponse.data != null) {
    //         try {
    //             String message = "";
    //             String responseBody = new String(error.networkResponse.data, "utf-8");
    //             Log.i("responseBody", responseBody);
    //             JSONObject data = new JSONObject(responseBody);
    //             Log.i("data", String.valueOf(data));
    //             Iterator<String> keys = data.keys();
    //             while (keys.hasNext()) {
    //                 String key = keys.next();
    //                 message = message + data.get(key) + "\n";
    //             }
    //             Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    //         } catch (UnsupportedEncodingException e) {
    //             e.printStackTrace();
    //         } catch (JSONException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    private void nullifySharedPreference() {
        SharedPreferences login_token = getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login_token.edit();
        editor.putString("Login_Token", "");
        editor.putString("User_Type", "");
        editor.putString("Retailer_Id", "");
        editor.putString("username", "");
        editor.putString("CompanyName", "");
        editor.putString("IsTermAndConditionAccepted", "");
        editor.putString("UserId", "");

        editor.commit();

        SharedPreferences retailerInfo = getSharedPreferences("RetailerInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor retailerInfo_editor = retailerInfo.edit();
        retailerInfo_editor.putString("RetailerCode", "");
        retailerInfo_editor.putString("RetailerID", "");
        retailerInfo_editor.apply();

        SharedPreferences companyId = getSharedPreferences("SendData",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editorCompany = companyId.edit();
        editorCompany.putString("first_name", "");
        editorCompany.putString("email", "");
        editorCompany.putString("phone_number", "");
        editorCompany.apply();
    }

}
