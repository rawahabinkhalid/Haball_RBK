package com.haball.Retailor.ui.Profile.ui.main;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.haball.CustomToast;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.Retailer_New_Password;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Dashboard.Dashboard_Tabs;
import com.haball.Retailor.ui.Make_Payment.PaymentScreen3Fragment_Retailer;
import com.haball.Retailor.ui.Profile.Profile_Tabs;
import com.haball.SSL_HandShake;
import com.haball.Select_User.Register_Activity;
import com.haball.SplashScreen.SplashScreen;
import com.haball.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private String ChangePass_URL = " http://175.107.203.97:4014/api/users/ChangePassword";
    private String PROFILE_EDIT_URL = "http://175.107.203.97:4014/api/retailer/Save";
    private String Token;
    private String PROFILE_URL = "http://175.107.203.97:4014/api/retailer/";
    private String RetailerId, ID, username, CompanyName;
    private Button btn_changepwd, btn_save_password, update_password;
    private TextInputEditText Rfirstname, Remail, Rcode, Rcnic, Rmobile, R_created_date, R_Address, txt_password, txt_newpassword, txt_cfmpassword;
    private TextInputLayout layout_Remail, layout_Rmobile, layout_R_Address, layout_R_created_date, layout_Rfirstname, layout_Rcode, layout_Rcnic;
    private Dialog change_password_dail;
    private Boolean old_password_check = false, password_check = false, confirm_password_check = false;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView tv_pr1, txt_header1;
    private TextInputLayout layout_password1, layout_password3, layout_password;
    private FragmentTransaction fragmentTransaction;

    private String currentTab = "";
    private Boolean changed = false;
    private String Email = "", Address = "", Mobile = "";

    private PageViewModel pageViewModel;
    private int keyDel;
    private Loader loader;
    private Button btn_back;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = null;

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1: {

                root = inflater.inflate(R.layout.fragment_retailor_profile, container, false);
                loader = new Loader(getContext());
                currentTab = "Profile";
                Rfirstname = root.findViewById(R.id.Rfirstname);
                Rcode = root.findViewById(R.id.Rcode);
                Rcnic = root.findViewById(R.id.Rcnic);
                R_created_date = root.findViewById(R.id.R_created_date);

                layout_Rfirstname = root.findViewById(R.id.layout_Rfirstname);
                layout_Rcode = root.findViewById(R.id.layout_Rcode);
                layout_Rcnic = root.findViewById(R.id.layout_Rcnic);
                layout_R_created_date = root.findViewById(R.id.layout_R_created_date);

                layout_Remail = root.findViewById(R.id.layout_email_retailer);
                layout_Rmobile = root.findViewById(R.id.layout_Rmobile);
                layout_R_Address = root.findViewById(R.id.layout_R_Address);

                Remail = root.findViewById(R.id.email_retailer);
                Rmobile = root.findViewById(R.id.Rmobile);
                R_Address = root.findViewById(R.id.R_Address);
                btn_save_password = root.findViewById(R.id.btn_save_password);
                btn_back = root.findViewById(R.id.btn_back);

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (changed) {
                            showDiscardDialog();
                        } else {
                            FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs());
                            fragmentTransaction.commit();
                        }

                    }
                });


                Remail.setFocusable(false);
                Rmobile.setFocusable(false);
                R_Address.setFocusable(false);

                btn_save_password.setEnabled(false);
                btn_save_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

                new TextField().changeColor(getContext(), layout_Rcode, Rcode);
                new TextField().changeColor(getContext(), layout_Rfirstname, Rfirstname);
                new TextField().changeColor(getContext(), layout_Rcnic, Rcnic);
                new TextField().changeColor(getContext(), layout_R_created_date, R_created_date);

                new TextField().changeColor(getContext(), layout_Remail, Remail);
                new TextField().changeColor(getContext(), layout_Rmobile, Rmobile);
                new TextField().changeColor(getContext(), layout_R_Address, R_Address);

                Remail.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
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
                                Remail.setFocusable(true);
                                Remail.setFocusableInTouchMode(true);
                                Remail.requestFocus();

                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                Remail.setSelection(Remail.getText().length());
//                                btn_save_password.setEnabled(true);
//                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
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
                                Rmobile.setFocusable(true);
                                Rmobile.setFocusableInTouchMode(true);
                                Rmobile.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                                Rmobile.setSelection(Rmobile.getText().length());
//                                btn_save_password.setEnabled(true);
//                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
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
                                R_Address.setFocusable(true);
                                R_Address.setFocusableInTouchMode(true);
                                R_Address.requestFocus();
                                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                R_Address.setSelection(R_Address.getText().length());
//                                btn_save_password.setEnabled(true);
//                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                                changed = true;
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

                Remail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkEmail();
                        checkFieldsForEmptyValues();
                    }
                });

                Rmobile.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Rmobile.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {

                                if (keyCode == KeyEvent.KEYCODE_DEL)
                                    keyDel = 1;
                                return false;
                            }
                        });

                        if (keyDel == 0) {
                            int len = Rmobile.getText().length();
                            if (len == 4) {
                                Rmobile.setText(Rmobile.getText() + "-");
                                Rmobile.setSelection(Rmobile.getText().length());
                            }
                        } else {
                            keyDel = 0;
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkMobile();
                        checkFieldsForEmptyValues();
                    }
                });

                R_Address.addTextChangedListener(new TextWatcher() {
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
                });

                profileData();

                break;

            }
            case 2: {
                root = inflater.inflate(R.layout.pasword_change, container, false);
                currentTab = "Password";
                loader = new Loader(getContext());
                layout_password = root.findViewById(R.id.layout_password);
                txt_password = root.findViewById(R.id.txt_password);
                txt_newpassword = root.findViewById(R.id.txt_newpassword);
                txt_cfmpassword = root.findViewById(R.id.txt_cfmpassword);
                layout_password1 = root.findViewById(R.id.layout_password1);
                layout_password3 = root.findViewById(R.id.layout_password3);
                update_password = root.findViewById(R.id.update_password);
                LinearLayout ll_fields1 = root.findViewById(R.id.ll_fields1);


                btn_back = root.findViewById(R.id.btn_back);

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtpassword = txt_password.getText().toString();
                        String txtnewpassword = txt_newpassword.getText().toString();
                        String txtcfmpassword = txt_cfmpassword.getText().toString();
                        if (!txtpassword.equals("") || !txtnewpassword.equals("") || !txtcfmpassword.equals("")) {
                            showDiscardDialog();
                        } else {
                            Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                            ((FragmentActivity) getContext()).startActivity(login_intent);
                            ((FragmentActivity) getContext()).finish();
                        }
                    }
                });


                checkFieldsForEmptyValuesUpdatePass();

                new TextField().changeColor(getContext(), layout_password1, txt_newpassword);
                new TextField().changeColor(getContext(), layout_password3, txt_cfmpassword);
                new TextField().changeColor(getContext(), layout_password, txt_password);
                update_password.setEnabled(false);
                update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
                update_password.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
//                        if (!String.valueOf(txt_password.getText()).equals("")) {
                        try {
                            updatePassword();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        } else {
//                            Toast.makeText(getContext(), "Please fill Old Password", Toast.LENGTH_LONG).show();
//                        }

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
                        checkOldPasswords();
                        checkFieldsForEmptyValuesUpdatePass();

                    }
                };

                TextWatcher textWatcher_cfmPass = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkFieldsForEmptyValuesUpdatePass();
//                        if (txt_newpassword.getText().toString().equals(txt_cfmpassword.getText().toString())) {
//                            Log.i("Password_Log", "in password check2");
//                            confirm_password_check = true;
////            layout_password3.setPasswordVisibilityToggleEnabled(true);
//                            layout_password3.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
//                            layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
//                            layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
//                            txt_cfmpassword.setTextColor(getResources().getColor(R.color.textcolor));
//                        } else {
//                            confirm_password_check = false;
////            txt_cfmpassword.setError("Password does not match");
////            layout_password3.setPasswordVisibilityToggleEnabled(false);
//                            layout_password3.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
//                            layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
//                            layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
//                            txt_cfmpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
//
//
//                        }
                    }
                };

                TextWatcher textWatcher_newPass = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
//                        checkPasswords();
                        checkFieldsForEmptyValuesUpdatePass();
                    }
                };
                txt_password.addTextChangedListener(textWatcher);
                txt_newpassword.addTextChangedListener(textWatcher_newPass);
                txt_cfmpassword.addTextChangedListener(textWatcher_cfmPass);
            }
            break;
        }


        return root;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//    Remail.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    Remail.clearFocus();
//                    showDiscardDialog();
//                }
//                return false;
//            }
//        });
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    // handle back button's click listener
////                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
//                    String remail = Remail.getText().toString();
//                    if (!remail.equals("")) {
//                        showDiscardDialog();
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//                return false;
//            }
//        });
//
//    }
//
//    private void showDiscardDialog() {
//        Log.i("CreatePayment", "In Dialog");
//        final FragmentManager fm = getActivity().getSupportFragmentManager();
//
//        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View view_popup = inflater.inflate(R.layout.discard_changes, null);
//        alertDialog.setView(view_popup);
//        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
//        btn_discard.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.i("CreatePayment", "Button Clicked");
//                alertDialog.dismiss();
//                fm.popBackStack();
//            }
//        });
//
//        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
//        img_email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//
//            }
//        });
//
//        alertDialog.show();
//    }
//


    private void checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (!Remail.getText().toString().matches(reg_ex)) {
            layout_Remail.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_Remail.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_Remail.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            Remail.setTextColor(getResources().getColor(R.color.error_stroke_color));
            btn_save_password.setEnabled(false);
            btn_save_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        } else {
            layout_Remail.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
            layout_Remail.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
            layout_Remail.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
            Remail.setTextColor(getResources().getColor(R.color.textcolor));
            checkFieldsForEmptyValues();
        }
    }

    private void checkFieldsForEmptyValues() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        String remail = Remail.getText().toString();
        String rmobile = Rmobile.getText().toString();
        String r_Address = R_Address.getText().toString();
        if (changed) {
            if (!remail.equals("") && !rmobile.equals("") && !r_Address.equals("")) {
                if ((remail.equals(Email)
                        && rmobile.equals(Mobile)
                        && r_Address.equals(Address))
                        || !remail.matches(reg_ex)
                        || rmobile.length() != 12
//                || comment.equals("")
                ) {
                    Log.i("debugProfileVali", "true");
                    Log.i("debugProfileVali", "'" + remail + "'");
                    Log.i("debugProfileVali", "'" + rmobile + "'");
                    Log.i("debugProfileVali", "'" + r_Address + "'");
                    btn_save_password.setEnabled(false);
                    btn_save_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

                } else {
                    Log.i("debugProfileVali", "false");
                    Log.i("debugProfileVali", "'" + remail + "'");
                    Log.i("debugProfileVali", "'" + rmobile + "'");
                    Log.i("debugProfileVali", "'" + r_Address + "'");

                    btn_save_password.setEnabled(true);
                    btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                }
            } else {
                btn_save_password.setEnabled(false);
                btn_save_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

            }
        }
    }

    private void checkMobile() {
        if (String.valueOf(Rmobile.getText()).length() != 12) {
            layout_Rmobile.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_Rmobile.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_Rmobile.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            Rmobile.setTextColor(getResources().getColor(R.color.error_stroke_color));
            btn_save_password.setEnabled(false);
            btn_save_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            layout_Rmobile.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
            layout_Rmobile.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
            layout_Rmobile.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
            Rmobile.setTextColor(getResources().getColor(R.color.textcolor));
            checkFieldsForEmptyValues();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentTab.equals("Profile"))
            onResumeProfile();
        else if (currentTab.equals("Password"))
            onResumePassword();
    }

    private void onResumeProfile() {
        View.OnKeyListener listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Remail.clearFocus();
                    Rmobile.clearFocus();
                    R_Address.clearFocus();
                    showDiscardDialog();
                }
                return false;
            }
        };
        Rmobile.setOnKeyListener(listener);
        Remail.setOnKeyListener(listener);
        R_Address.setOnKeyListener(listener);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    if (changed) {
                        showDiscardDialog();
                        return true;
                    } else {
                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
                    }
                }
                return false;
            }
        });

    }

    private void onResumePassword() {
        View.OnKeyListener listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    txt_password.clearFocus();
                    txt_newpassword.clearFocus();
                    txt_cfmpassword.clearFocus();
                    showDiscardDialog();
                }
                return false;
            }
        };
        txt_password.setOnKeyListener(listener);
        txt_newpassword.setOnKeyListener(listener);
        txt_cfmpassword.setOnKeyListener(listener);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    String txtpassword = txt_password.getText().toString();
                    String txtnewpassword = txt_newpassword.getText().toString();
                    String txtcfmpassword = txt_cfmpassword.getText().toString();
                    if (!txtpassword.equals("") || !txtnewpassword.equals("") || !txtcfmpassword.equals("")) {
                        showDiscardDialog();
                        return true;
                    } else {
                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();
                    }
                }
                return false;
            }
        });

    }

    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        alertDialog.setView(view_popup);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
//                fm.popBackStack();
                SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();

                Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                ((FragmentActivity) getContext()).startActivity(login_intent);
                ((FragmentActivity) getContext()).finish();

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

    private void checkFieldsForEmptyValuesUpdatePass() {
        String reg_ex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";
//        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";


        String password = txt_password.getText().toString();
        String newPass = txt_newpassword.getText().toString();
        String confrm_pass = txt_cfmpassword.getText().toString();
        if (password.equals("")
                || newPass.equals("")
                || confrm_pass.equals("")
                || !password.matches(reg_ex)
                || !password.matches(reg_ex)
                || !confrm_pass.matches(reg_ex)
        ) {
            update_password.setEnabled(false);
            update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            update_password.setEnabled(true);
            update_password.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private void updatePassword() throws JSONException {
        checkOldPasswords();
        checkPasswords();
        checkConfirmPassword();
        if (old_password_check && password_check && confirm_password_check) {
            if (!String.valueOf(txt_password.getText()).equals(String.valueOf(txt_newpassword.getText()))) {

                loader.showLoader();

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                        Context.MODE_PRIVATE);
                Token = sharedPreferences.getString("Login_Token", "");
                Log.i("Login_Token", Token);
                SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                        Context.MODE_PRIVATE);
                ID = sharedPreferences1.getString("ID", "");
                username = sharedPreferences1.getString("username", "");
//            Toast.makeText(getActivity(), "Update Password clicked", Toast.LENGTH_SHORT).show();

//            change_password_dail.dismiss();

                JSONObject map = new JSONObject();
                map.put("Password", txt_password.getText().toString());
                map.put("NewPassword", txt_newpassword.getText().toString());
                map.put("ConfirmPassword", txt_cfmpassword.getText().toString());
//            map.put("ID", ID);
                map.put("Username", username);
                Log.i("MapChangePass", map.toString());
                new SSL_HandShake().handleSSLHandshake();
//            final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());
                BooleanRequest sr = new BooleanRequest(Request.Method.POST, ChangePass_URL, String.valueOf(map), new Response.Listener<Boolean>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Boolean result) {
                        loader.hideLoader();
                        Log.i("response", String.valueOf(result));
                        if (result) {
//                            Toast.makeText(getActivity(), result.get("message").toString(), Toast.LENGTH_SHORT).show();
//                        } else {
                            final Dialog fbDialogue = new Dialog(getActivity());
                            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                            fbDialogue.setContentView(R.layout.password_updatepopup);

                            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
//                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
                            tv_pr1.setText("Your password has been updated. You can login with the new credentials.");
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
                                    SharedPreferences login_token = getContext().getSharedPreferences("LoginToken",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = login_token.edit();
                                    editor.putString("Login_Token", "");
                                    editor.putString("User_Type", "");
                                    editor.putString("Retailer_Id", "");
                                    editor.putString("username", "");
                                    editor.putString("CompanyName", "");
                                    editor.putString("UserId", "");

                                    editor.commit();

                                    Intent intent = new Intent(getContext(), RetailerLogin.class);
                                    startActivity(intent);
                                    ((FragmentActivity) getContext()).finish();
                                }
                            });
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loader.hideLoader();
//                    new HaballError().printErrorMessage(error);
                        error.printStackTrace();
                        new CustomToast().showToast(getActivity(), "Password mismatch");

                        layout_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                        layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                        layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                        txt_password.setTextColor(getResources().getColor(R.color.error_stroke_color));
//            layout_password1.setPasswordVisibilityToggleEnabled(false);
                        update_password.setEnabled(false);
                        update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

//                    final Dialog fbDialogue = new Dialog(getActivity());
//                    //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
//                    fbDialogue.setContentView(R.layout.password_updatepopup);
//                    txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
//                    tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
//                    txt_header1.setText("Error");
//                    txt_header1.setTextColor(getResources().getColor(R.color.error_stroke_color));
//                    txt_header1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_set_error));
//                    tv_pr1.setText("Password mismatch.");
//                    fbDialogue.setCancelable(true);
//// Setting dialogview
////            Window window = fbDialogue.getWindow();
////            window.setGravity(Gravity.TOP);
//
//                    fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
//                    WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
//                    layoutParams.y = 200;
//                    layoutParams.x = -70;// top margin
//                    fbDialogue.getWindow().setAttributes(layoutParams);
//
//
//                    fbDialogue.show();
//                    ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
//                    close_button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            fbDialogue.dismiss();
//                        }
//                    });
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
                new CustomToast().showToast(getActivity(), "New password cannot be old password.");

            }
        } else{
//            Toast.makeText(getActivity(), "Password do not Match", Toast.LENGTH_LONG).show();
                new CustomToast().showToast(getActivity(), "Password mismatch");
                layout_password1.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                txt_newpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
//            layout_password1.setPasswordVisibilityToggleEnabled(false);
                update_password.setEnabled(false);
                update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

                layout_password3.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
//            layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                txt_cfmpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));

//            final Dialog fbDialogue = new Dialog(getActivity());
//            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
//            fbDialogue.setContentView(R.layout.password_updatepopup);
//            txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
//            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
//            txt_header1.setText("Error");
//            txt_header1.setTextColor(getResources().getColor(R.color.error_stroke_color));
//            txt_header1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_set_error));
//            tv_pr1.setText("Password do not Match");
//            fbDialogue.setCancelable(true);
// Setting dialogview
//            Window window = fbDialogue.getWindow();
//            window.setGravity(Gravity.TOP);

//            fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
//            WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
//            layoutParams.y = 200;
//            layoutParams.x = -70;// top margin
//            fbDialogue.getWindow().setAttributes(layoutParams);
//
//
//            fbDialogue.show();
//            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
//            close_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    fbDialogue.dismiss();
//                }
//            });
            }

        }

        private void checkOldPasswords () {
            String reg_ex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";
//        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
            if (txt_password.getText().toString().matches(reg_ex)) {
                old_password_check = true;
                layout_password.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_password.setTextColor(getResources().getColor(R.color.textcolor));
//            layout_password1.setPasswordVisibilityToggleEnabled(true);
                checkFieldsForEmptyValuesUpdatePass();
            } else {
//            txt_newpassword.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
                old_password_check = false;
                layout_password.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                txt_password.setTextColor(getResources().getColor(R.color.error_stroke_color));
//            layout_password1.setPasswordVisibilityToggleEnabled(false);
                update_password.setEnabled(false);
                update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
            }
//        txt_password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                layout_password.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
//                layout_password.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
//                layout_password.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
//                txt_password.setTextColor(getResources().getColor(R.color.textcolor));
////                layout_password1.setPasswordVisibilityToggleEnabled(true);
//                checkFieldsForEmptyValuesUpdatePass();
//                checkOldPasswords();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        }

        private void checkPasswords () {
            String reg_ex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$";
//        String reg_ex = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*[\\.,#';\\\\\\(\\)\\{\\}'`/$^+=!*()@%&])).{6,}$";
            if (txt_newpassword.getText().toString().matches(reg_ex)) {
                password_check = true;
                layout_password1.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_newpassword.setTextColor(getResources().getColor(R.color.textcolor));
//            layout_password1.setPasswordVisibilityToggleEnabled(true);
                checkFieldsForEmptyValuesUpdatePass();
            } else {
//            txt_newpassword.setError("Please enter password with minimum 6 characters & 1 Numeric or special character");
                password_check = false;
                layout_password1.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                layout_password1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                layout_password1.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                txt_newpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
//            layout_password1.setPasswordVisibilityToggleEnabled(false);
                update_password.setEnabled(false);
                update_password.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
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
                    checkFieldsForEmptyValuesUpdatePass();
                    checkConfirmPassword();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        private void checkConfirmPassword () {
            if (txt_newpassword.getText().toString().equals(txt_cfmpassword.getText().toString())) {
                confirm_password_check = true;
                layout_password3.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
                layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
                layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
                txt_cfmpassword.setTextColor(getResources().getColor(R.color.textcolor));
//            layout_password3.setPasswordVisibilityToggleEnabled(true);
                checkFieldsForEmptyValuesUpdatePass();
            } else {
                confirm_password_check = false;
//            txt_cfmpassword.setError("Password does not match");
                layout_password3.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
                layout_password3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
//            layout_password3.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
                txt_cfmpassword.setTextColor(getResources().getColor(R.color.error_stroke_color));
//            layout_password3.setPasswordVisibilityToggleEnabled(false);
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
                    checkFieldsForEmptyValuesUpdatePass();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        private void saveProfileData () throws JSONException {
            loader.showLoader();

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            Token = sharedPreferences.getString("Login_Token", "");

            SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                    Context.MODE_PRIVATE);
            RetailerId = sharedPreferences1.getString("Retailer_Id", "");
            Log.i("RetailerId ", RetailerId);
//        PROFILE_URL = PROFILE_URL + RetailerId;
            Log.i("Token Retailer ", Token);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ID", RetailerId);
            jsonObject.put("Name", Rfirstname.getText().toString());
            jsonObject.put("CNIC", Rcnic.getText().toString());
            jsonObject.put("Mobile", Rmobile.getText().toString());
            jsonObject.put("CompanyName", CompanyName);
            jsonObject.put("Address", R_Address.getText().toString());
            jsonObject.put("Email", Remail.getText().toString());
            new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

            JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, PROFILE_EDIT_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject result) {
                    loader.hideLoader();
                    final Dialog fbDialogue = new Dialog(getActivity());
                    //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                    fbDialogue.setContentView(R.layout.password_updatepopup);
                    TextView tv_pr1, txt_header1;
                    txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                    tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                    tv_pr1.setText("Your profile has been updated successfully.");
                    txt_header1.setText("Profile Updated");
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
                            //                    Toast.makeText(getContext(), "Profile Information Successfully updated for " + result.getString("RetailerCode"), Toast.LENGTH_LONG).show();
                            fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container_ret, new Profile_Tabs()).addToBackStack("tag");
                            fragmentTransaction.commit();
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loader.hideLoader();
                    new ProcessingError().showError(getContext());
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

        // private void printErrorMessage(VolleyError error) {

        //     if (error instanceof NetworkError) {
        //         Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
        //     } else if (error instanceof ServerError) {
        //         Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
        //     } else if (error instanceof AuthFailureError) {
        //         Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
        //     } else if (error instanceof ParseError) {
        //         Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
        //     } else if (error instanceof NoConnectionError) {
        //         Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
        //     } else if (error instanceof TimeoutError) {
        //         Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
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
        //             Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        //         } catch (UnsupportedEncodingException e) {
        //             e.printStackTrace();
        //         } catch (JSONException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }

        private void profileData () {
            loader.showLoader();

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
                    loader.hideLoader();
                    try {
                        Log.i("aaaaa", String.valueOf(result));
                        CompanyName = result.getString("CompanyName");
                        Rfirstname.setText(result.getString("Name"));
                        Remail.setText(result.getString("Email"));
                        Email = result.getString("Email");
                        Rcode.setText(result.getString("RetailerCode"));
                        Rcnic.setText(result.getString("CNIC"));
                        Rmobile.setText(result.getString("Mobile"));
                        Mobile = result.getString("Mobile");
                        R_Address.setText(result.getString("Address"));
                        Address = result.getString("Address");
                        String string = result.getString("CreatedDate");
                        String[] parts = string.split("T");
                        String Date = parts[0];
                        R_created_date.setText(Date);
                        checkFieldsForEmptyValues();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loader.hideLoader();
                    new ProcessingError().showError(getContext());
                    //new HaballError().printErrorMessage(error);
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
    }