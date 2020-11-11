package com.haball.Forgot_Password;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.haball.CustomToast;
import com.haball.Distribution_Login.Distribution_Login;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.Forgot_Password_Retailer.Forgot_Pass_Retailer;
import com.haball.Select_User.Register_Activity;
import com.haball.Support.Support_Ditributor.Support_Ticket_Form;
import com.haball.TextField;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Forgot_Pass_Distributor extends AppCompatActivity {
    //private ImageButton btn_back;
    private Button btn_reset, btn_lgn;
    private String URL_FORGOT_PASSWORD = "https://175.107.203.97:4013/api/Users/forgot";
    // ProgressDialog progressDialog;
    private TextInputLayout layout_email_phone;
    private TextInputEditText txt_email;
    private Loader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__pass__distributor);
//        getWindow().setBackgroundDrawableResource(R.drawable.background_logo);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
        rl_main_background.setBackground(background_drawable);

        loader = new Loader(Forgot_Pass_Distributor.this);

//
//        ActionBar bar = getSupportActionBar();
//        assert bar != null;
//        bar.setBackgroundDrawable( new ColorDrawable( Color.parseColor( "#FFFFFF" ) ) );

        LayoutInflater inflater = LayoutInflater.from(this);
        txt_email = findViewById(R.id.txt_email);
        layout_email_phone = findViewById(R.id.layout_email);
        //  progressDialog = new ProgressDialog(this);

//        @SuppressLint("InflateParams") View customView = inflater.inflate( R.layout.action_bar_main, null );
//
//        bar.setCustomView( customView );
//        bar.setDisplayShowCustomEnabled( true );
//        bar.setBackgroundDrawable( new ColorDrawable( Color.parseColor( "#FFFFFF" ) ) );
//        bar.setTitle( "" );
//        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);
        new TextField().changeColor(this, layout_email_phone, txt_email);
        txt_email.clearFocus();

        btn_reset = findViewById(R.id.btn_reset);


        btn_reset.setEnabled(false);
        btn_reset.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt_email.getText().toString().equals("")) {

//                    Toast.makeText(getApplication(), "Clicked", Toast.LENGTH_LONG).show();
//                    final AlertDialog alertDialog1 = new AlertDialog.Builder(Forgot_Pass_Distributor.this).create();
//                    LayoutInflater inflater = LayoutInflater.from(Forgot_Pass_Distributor.this);
//                    @SuppressLint("InflateParams") View view_popup = inflater.inflate(R.layout.email_sent, null);
//                    alertDialog1.setView(view_popup);
//
//                    ImageButton img_email = view_popup.findViewById(R.id.image_email);
                    forgotPasswordRequest();

//
//                } else {
//                    Toast.makeText(getApplication(), "Please enter Email Address!", Toast.LENGTH_LONG).show();
                }

            }
        });
//
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        btn_lgn = findViewById(R.id.btn_lgn);
        btn_lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(txt_email.getText()).equals(""))
                    showDiscardDialog();
                else {
                    Intent intent = new Intent(Forgot_Pass_Distributor.this, Distribution_Login.class);
                    startActivity(intent);
                    finish();
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
                checkEmail();
            }
        };

        txt_email.addTextChangedListener(textWatcher);


    }

    private void checkFieldsForEmptyValues() {


        String email_ = txt_email.getText().toString();


        if (email_.equals("")) {
            btn_reset.setEnabled(false);
            btn_reset.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {

            btn_reset.setEnabled(true);
            btn_reset.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    @Override
    public void onBackPressed() {
        String txt_Email = txt_email.getText().toString();

        if (!txt_Email.equals("")) {
            showDiscardDialog();
        } else {
            Intent intent = new Intent(Forgot_Pass_Distributor.this, Distribution_Login.class);
            startActivity(intent);
            finish();
        }
    }


    private void forgotPasswordRequest() {
        loader.showLoader();

//        progressDialog.setTitle("Resetting Password");
//        progressDialog.setMessage("Loading, Please Wait..");
//        progressDialog.show();
        StringRequest sr = new StringRequest(Request.Method.POST, URL_FORGOT_PASSWORD, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String result) {
                loader.hideLoader();
                if (result.equals("\"success\"")) {
//                Log.e("RESPONSE", result);
//                img_email.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog1.dismiss();
//                    }
//                });
//                alertDialog1.show();

                    final Dialog fbDialogue = new Dialog(Forgot_Pass_Distributor.this);
                    //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                    fbDialogue.setContentView(R.layout.password_updatepopup);
                    TextView tv_pr1, txt_header1;
                    txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                    tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                    txt_header1.setText("Email Sent");
                    tv_pr1.setText("Password reset instructions have been sent to the email address provided.");
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
                            Intent intent = new Intent(Forgot_Pass_Distributor.this, Distribution_Login.class);
                            startActivity(intent);

                            finish();
                        }
                    });
                } else {
                    new CustomToast().showToast(Forgot_Pass_Distributor.this, "User does not exist");
                    layout_email_phone.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                    layout_email_phone.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                    layout_email_phone.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                    txt_email.setTextColor(getResources().getColor(R.color.error_stroke_color));
                    btn_reset.setEnabled(false);
                    btn_reset.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));


                }
            }

        }, new Response.ErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                if (error.networkResponse.statusCode == 405) {
                    new CustomToast().showToast(Forgot_Pass_Distributor.this, "User does not exist");
                    layout_email_phone.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                    layout_email_phone.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                    layout_email_phone.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                    txt_email.setTextColor(getResources().getColor(R.color.error_stroke_color));
                    btn_reset.setEnabled(false);
                    btn_reset.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
                } else {
                    new ProcessingError().showError(Forgot_Pass_Distributor.this);
                    new HaballError().printErrorMessage(Forgot_Pass_Distributor.this, error);
                    error.printStackTrace();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params;
                params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public byte[] getBody() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("EmailAddress", txt_email.getText().toString());
                    return jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                } catch (Exception e) {
                    return null;
                }
            }
        };
        sr.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 1000;
            }

            @Override
            public void retry(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(sr);


    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void printErrMessage(VolleyError error) {
//
//        if (error instanceof NetworkError) {
//            Toast.makeText(Forgot_Pass_Distributor.this, "Network Error !", Toast.LENGTH_LONG).show();
//        } else if (error instanceof ServerError) {
//            Toast.makeText(Forgot_Pass_Distributor.this, "Server Error !", Toast.LENGTH_LONG).show();
//        } else if (error instanceof AuthFailureError) {
//            Toast.makeText(Forgot_Pass_Distributor.this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
//        } else if (error instanceof ParseError) {
//            Toast.makeText(Forgot_Pass_Distributor.this, "Parse Error !", Toast.LENGTH_LONG).show();
//        } else if (error instanceof TimeoutError) {
//            Toast.makeText(Forgot_Pass_Distributor.this, "Timeout Error !", Toast.LENGTH_LONG).show();
//        }
//
//        if (error.networkResponse != null && error.networkResponse.data != null) {
//            try {
//                StringBuilder message = new StringBuilder();
//                String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
//                JSONObject data = new JSONObject(responseBody);
//                Iterator<String> keys = data.keys();
//                while (keys.hasNext()) {
//                    String key = keys.next();
//                    //                if (data.get(key) instanceof JSONObject) {
//                    message.append(data.get(key)).append("\n");
//                    //                }
//                }
//                //                    if(data.has("message"))
//                //                        message = data.getString("message");
//                //                    else if(data. has("Error"))
//                Toast.makeText(Forgot_Pass_Distributor.this, message.toString(), Toast.LENGTH_LONG).show();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard.setText("Alert");
        tv_discard_txt.setText("Are you sure, you want to exit this page?");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText("Exit");
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();

                Intent intent = new Intent(Forgot_Pass_Distributor.this, Distribution_Login.class);
                startActivity(intent);
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

    private void checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (!txt_email.getText().toString().matches(reg_ex)) {
            layout_email_phone.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_email_phone.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_email_phone.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            txt_email.setTextColor(getResources().getColor(R.color.error_stroke_color));
            btn_reset.setEnabled(false);
            btn_reset.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        } else {
            layout_email_phone.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
            layout_email_phone.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
            layout_email_phone.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
            txt_email.setTextColor(getResources().getColor(R.color.textcolor));
            checkFieldsForEmptyValues();
        }
    }


}



