package com.haball.Retailor.ui.Profile;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.R;
import com.haball.SSL_HandShake;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button btn_changepwd, btn_save_password, update_password;
    private EditText Rfirstname, Remail, Rcode, Rcnic, Rmobile, R_created_date, R_Address, txt_password, txt_newpassword, txt_cfmpassword;
    private TextView tv_pr1;

    private String PROFILE_URL = "http://175.107.203.97:4014/api/retailer/";
    private String ChangePass_URL = "http://175.107.203.97:4014/api/Users/ChangePassword";
    private String PROFILE_EDIT_URL = "http://175.107.203.97:4014/api/retailer/Save";
    private String Token;
    private String RetailerId, ID, username, CompanyName;
    private Dialog change_password_dail;
    private Boolean password_check = false, confirm_password_check = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_retailor_profile, container, false);

        Rfirstname = root.findViewById(R.id.Rfirstname);
        Remail = root.findViewById(R.id.email_retailer);
        Rcode = root.findViewById(R.id.Rcode);
        Rcnic = root.findViewById(R.id.Rcnic);
        Rmobile = root.findViewById(R.id.Rmobile);
        R_created_date = root.findViewById(R.id.R_created_date);
        R_Address = root.findViewById(R.id.R_Address);
        //  btn_changepwd = root.findViewById(R.id.btn_changepwd);
        btn_save_password = root.findViewById(R.id.btn_save_password);

        Remail.setInputType(InputType.TYPE_NULL);
        Rmobile.setInputType(InputType.TYPE_NULL);
        R_Address.setInputType(InputType.TYPE_NULL);

        Remail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Remail.getRight() - Remail.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Remail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        Remail.requestFocus();
                        Remail.setFocusable(true);
                        Remail.setFocusableInTouchMode(true);
                        Remail.setSelection(Remail.getText().length());
                        btn_save_password.setEnabled(true);
                        btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                        return true;
                    }
                }
                return false;
            }
        });
        Rmobile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Rmobile.getRight() - Rmobile.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Rmobile.setInputType(InputType.TYPE_CLASS_NUMBER);
                        Rmobile.requestFocus();
                        Rmobile.setFocusable(true);
                        Rmobile.setFocusableInTouchMode(true);
                        Rmobile.setSelection(Rmobile.getText().length());
                        btn_save_password.setEnabled(true);
                        btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                        return true;
                    }
                }
                return false;
            }
        });
        R_Address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (R_Address.getRight() - R_Address.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        R_Address.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                        R_Address.requestFocus();
                        R_Address.setFocusable(true);
                        R_Address.setFocusableInTouchMode(true);
                        R_Address.setSelection(R_Address.getText().length());
                        btn_save_password.setEnabled(true);
                        btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                        return true;
                    }
                }
                return false;
            }
        });
        btn_save_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveProfileData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        btn_changepwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                change_password_dail = new Dialog(getActivity());
//                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
//                change_password_dail.setContentView(R.layout.pasword_change);
//                change_password_dail.setCancelable(true);
//                change_password_dail.show();
//                ImageButton close_button = change_password_dail.findViewById(R.id.image_button);
//                txt_password = change_password_dail.findViewById(R.id.txt_password);
//                txt_newpassword = change_password_dail.findViewById(R.id.txt_newpassword);
//                txt_cfmpassword = change_password_dail.findViewById(R.id.txt_cfmpassword);
//                close_button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        change_password_dail.dismiss();
//                    }
//                });
////                update_password = change_password_dail.findViewById(R.id.update_password);
////                update_password.setOnClickListener(new View.OnClickListener() {
////                    @RequiresApi(api = Build.VERSION_CODES.O)
////                    @Override
////                    public void onClick(View v) {
////                        if (!String.valueOf(txt_password.getText()).equals("")) {
////                            try {
////                                updatePassword();
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        } else {
////                            Toast.makeText(getContext(), "Please fill Old Password", Toast.LENGTH_LONG).show();
////                        }
////
////                    }
////                });
//            }
//
//        });
        profileData();

        return root;
    }

    private void saveProfileData() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        RetailerId = sharedPreferences1.getString("Retailer_Id", "");
        Log.i("RetailerId ", RetailerId);
        PROFILE_URL = PROFILE_URL + RetailerId;
        Log.i("Token Retailer ", Token);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", 1);
        jsonObject.put("Name", Rfirstname.getText().toString());
        jsonObject.put("CNIC", Rcnic.getText().toString());
        jsonObject.put("Mobile", Rmobile.getText().toString());
        jsonObject.put("CompanyName", CompanyName);
        jsonObject.put("Address", R_Address.getText().toString());
        jsonObject.put("Email", Remail.getText().toString());
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, PROFILE_EDIT_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Toast.makeText(getContext(), "Profile Information Successfully updated for " + result.getString("RetailerCode"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
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

    }

    private void updatePassword() throws JSONException {
        checkPasswords();
        checkConfirmPassword();
        if (password_check && confirm_password_check) {

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");
            Log.i("Login_Token", Token);
            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            ID = sharedPreferences1.getString("ID", "");
            username = sharedPreferences1.getString("username", "");
            Toast.makeText(getActivity(), "Update Password clicked", Toast.LENGTH_SHORT).show();

            change_password_dail.dismiss();

            JSONObject map = new JSONObject();
            map.put("Password", txt_password.getText().toString());
            map.put("NewPassword", txt_newpassword.getText().toString());
            map.put("NewPassword1", txt_cfmpassword.getText().toString());
            map.put("ID", ID);
            map.put("Username", username);
            Log.i("Map", map.toString());
            new SSL_HandShake().handleSSLHandshake();
//            final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, ChangePass_URL, map, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(JSONObject result) {
                    Log.i("response", String.valueOf(result));
                    try {
                        if (result.has("message")) {
                            Toast.makeText(getActivity(), result.get("message").toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            final Dialog fbDialogue = new Dialog(getActivity());
                            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                            fbDialogue.setContentView(R.layout.password_updatepopup);
                            tv_pr1 = fbDialogue.findViewById(R.id.tv_pr1);
                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
                            fbDialogue.setCancelable(true);
                            fbDialogue.show();
                            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                            close_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fbDialogue.dismiss();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new HaballError().printErrorMessage(getContext(), error);
                    error.printStackTrace();
//                    Toast.makeText(getActivity(), String.valueOf(error),Toast.LENGTH_LONG).show();
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
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            Volley.newRequestQueue(getActivity()).add(sr);
        } else {
            Toast.makeText(getActivity(), "Password do not Match", Toast.LENGTH_LONG).show();
        }

    }

    private void checkPasswords() {
        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
        if (txt_newpassword.getText().toString().matches(reg_ex)) {
            password_check = true;
            txt_newpassword.setError(null);
        } else {
            txt_newpassword.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
            password_check = false;
        }
    }

    private void checkConfirmPassword() {
        if (txt_newpassword.getText().toString().equals(txt_cfmpassword.getText().toString())) {
            confirm_password_check = true;
            txt_cfmpassword.setError(null);
        } else {
            confirm_password_check = false;
            txt_cfmpassword.setError("Password does not match");
        }
    }

    private void profileData() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        RetailerId = sharedPreferences1.getString("Retailer_Id", "");
        Log.i("RetailerId ", RetailerId);
        PROFILE_URL = PROFILE_URL + RetailerId;
        Log.i("Token Retailer ", Token);
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, PROFILE_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Log.i("aaaaa", String.valueOf(result));
                    CompanyName = result.getString("CompanyName");
                    Rfirstname.setText(result.getString("Name"));
                    Remail.setText(result.getString("Email"));
                    Rcode.setText(result.getString("RetailerCode"));
                    Rcnic.setText(result.getString("CNIC"));
                    Rmobile.setText(result.getString("Mobile"));
                    R_Address.setText(result.getString("Address"));
                    String string = result.getString("CreatedDate");
                    String[] parts = string.split("T");
                    String Date = parts[0];
                    R_created_date.setText(Date);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
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
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        Volley.newRequestQueue(getContext()).add(sr);


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
