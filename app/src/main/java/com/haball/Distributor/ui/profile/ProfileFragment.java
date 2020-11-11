package com.haball.Distributor.ui.profile;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private Button change_pwd, update_password, distri_btn_save;
    private EditText edt_firstname, edt_lastname, edt_email, edt_dist_mobile, et_test;
    private TextInputEditText txt_password, txt_newpassword, txt_cfmpassword;
    public TextView edt_dist_code, tv_cnic, tv_NTN, tv_companyname, tv_created_date, tv_pr1;
    private String PROFILE_URL = "https://175.107.203.97:4013/api/distributor/";
    private String ChangePass_URL = "https://175.107.203.97:4013/api/Users/ChangePassword";
    private String PROFILE_EDIT_URL = "https://175.107.203.97:4013/api/distributor/save";
    private String Token;
    private String DistributorId, ID, Username, Phone;
    private Dialog change_password_dail;
    private Boolean password_check = false, confirm_password_check = false;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_distributor_profile, container, false);
        //init
        //change_pwd = root.findViewById(R.id.btn_changepwd);
        //profile
        edt_dist_code = root.findViewById(R.id.edt_dist_code);
        edt_firstname = root.findViewById(R.id.edt_firstname);
        edt_lastname = root.findViewById(R.id.edt_lastname);
        edt_email = root.findViewById(R.id.edt_email);
        edt_dist_mobile = root.findViewById(R.id.edt_dist_mobile);
        tv_cnic = root.findViewById(R.id.tv_cnic);
        tv_NTN = root.findViewById(R.id.tv_NTN);
        tv_companyname = root.findViewById(R.id.tv_companyname);
        tv_created_date = root.findViewById(R.id.tv_created_date);
        distri_btn_save = root.findViewById(R.id.distri_btn_save);

        edt_firstname.setInputType(InputType.TYPE_NULL);
        edt_lastname.setInputType(InputType.TYPE_NULL);
        edt_email.setInputType(InputType.TYPE_NULL);
        edt_dist_mobile.setInputType(InputType.TYPE_NULL);

        edt_firstname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_firstname.getRight() - edt_firstname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        edt_firstname.setInputType(InputType.TYPE_CLASS_TEXT);
                        edt_firstname.requestFocus();
                        edt_firstname.setFocusable(true);
                        edt_firstname.setFocusableInTouchMode(true);
                        edt_firstname.setSelection(edt_firstname.getText().length());
                        distri_btn_save.setEnabled(true);
                        distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                        return true;
                    }
                }
                return false;
            }
        });
        edt_lastname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_lastname.getRight() - edt_lastname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        edt_lastname.setInputType(InputType.TYPE_CLASS_TEXT);
                        edt_lastname.requestFocus();
                        edt_lastname.setFocusable(true);
                        edt_lastname.setFocusableInTouchMode(true);
                        edt_lastname.setSelection(edt_lastname.getText().length());
                        distri_btn_save.setEnabled(true);
                        distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                        return true;
                    }
                }
                return false;
            }
        });
        edt_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_email.getRight() - edt_email.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        edt_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        edt_email.requestFocus();
                        edt_email.setFocusable(true);
                        edt_email.setFocusableInTouchMode(true);
                        edt_email.setSelection(edt_email.getText().length());
                        distri_btn_save.setEnabled(true);
                        distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                        return true;
                    }
                }
                return false;
            }
        });
        edt_dist_mobile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edt_dist_mobile.getRight() - edt_dist_mobile.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        edt_dist_mobile.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edt_dist_mobile.requestFocus();
                        edt_dist_mobile.setFocusable(true);
                        edt_dist_mobile.setFocusableInTouchMode(true);
                        edt_dist_mobile.setSelection(edt_dist_mobile.getText().length());
                        distri_btn_save.setEnabled(true);
                        distri_btn_save.setBackground(getResources().getDrawable(R.drawable.button_background));
                        return true;
                    }
                }
                return false;
            }
        });
        distri_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveProfileData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_password_dail = new Dialog(getActivity());
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                change_password_dail.setContentView(R.layout.pasword_change);
                change_password_dail.setCancelable(true);
                change_password_dail.show();
                ImageButton close_button = change_password_dail.findViewById(R.id.image_button);
                txt_password = change_password_dail.findViewById(R.id.txt_password);
                txt_newpassword = change_password_dail.findViewById(R.id.txt_newpassword);
                txt_cfmpassword = change_password_dail.findViewById(R.id.txt_cfmpassword);
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change_password_dail.dismiss();
                    }
                });
                update_password = change_password_dail.findViewById(R.id.update_password);
                update_password.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (!String.valueOf(txt_password.getText()).equals("")) {
                            try {
                                updatePassword();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "Please fill Old Password", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }

        });
        profileData();
        return root;
    }

    private void saveProfileData() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("Distributor_Id ", DistributorId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ID", DistributorId);
        jsonObject.put("FirstName", edt_firstname.getText().toString());
        jsonObject.put("LastName", edt_lastname.getText().toString());
        jsonObject.put("CompanyName", tv_companyname.getText().toString());
        jsonObject.put("CompanyNTN", tv_NTN.getText().toString());
        jsonObject.put("CNIC", tv_cnic.getText().toString());
        jsonObject.put("Phone", Phone);
        jsonObject.put("Mobile", edt_dist_mobile.getText().toString());
        jsonObject.put("Email", edt_email.getText().toString());
        jsonObject.put("DealerCode", edt_dist_code.getText().toString());
        jsonObject.put("UserType", 0);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, PROFILE_EDIT_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Toast.makeText(getContext(), "Profile Information Successfully updated for " + result.getString("DealerCode"), Toast.LENGTH_LONG).show();
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
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);

    }

    private void profileData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId1 ", DistributorId);
        PROFILE_URL = PROFILE_URL + DistributorId;
        Log.i("Token1", Token);

        StringRequest sr = new StringRequest(Request.Method.GET, PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                Log.i("aaaaa", result);
                try {
                    if (result != null && !result.equals("")) {
                        Gson gson = new Gson();
                        Profile_Model profile_model = gson.fromJson(result, Profile_Model.class);
                        Phone = profile_model.getPhone();
                        edt_dist_code.setText(profile_model.getDealerCode());
                        edt_firstname.setText(profile_model.getFirstName());
                        edt_lastname.setText(profile_model.getLastName());
                        edt_email.setText(profile_model.getEmail());
                        edt_dist_mobile.setText(profile_model.getMobile());
                        tv_cnic.setText(profile_model.getCNIC());
                        tv_NTN.setText(profile_model.getCompanyNTN());
                        tv_companyname.setText(profile_model.getCompanyName());
                        String string = profile_model.getCreatedDate();
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        tv_created_date.setText(Date);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updatePassword() throws JSONException {
        checkPasswords();
        checkConfirmPassword();
        if (password_check && confirm_password_check) {

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");
            Log.i("Login_Token", Token);
            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    MODE_PRIVATE);
            ID = sharedPreferences1.getString("ID", "");
            Username = sharedPreferences1.getString("username", "");
            // Toast.makeText(getActivity(), "Update Password clicked", Toast.LENGTH_SHORT).show();

            change_password_dail.dismiss();

            JSONObject map = new JSONObject();
            map.put("Password", txt_password.getText().toString());
            map.put("NewPassword", txt_newpassword.getText().toString());
            map.put("NewPassword1", txt_cfmpassword.getText().toString());
            //        map.put("Password", "Force@123");
            //        map.put("NewPassword", "Force@123");
            //        map.put("NewPassword1", "Force@123");
            map.put("ID", ID);
            map.put("Username", Username);
            Log.i("Map", map.toString());
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
                    //                Log.e("RESPONSE", result.toString());
                    //                Toast.makeText(Distribution_Login.this,result.toString(),Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new HaballError().printErrorMessage(getContext(), error);
                    new ProcessingError().showError(getContext());

                    error.printStackTrace();
                    // Toast.makeText(getActivity(), String.valueOf(error),Toast.LENGTH_LONG).show();
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