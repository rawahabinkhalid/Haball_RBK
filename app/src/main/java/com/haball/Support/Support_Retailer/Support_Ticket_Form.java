package com.haball.Support.Support_Retailer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.LanguageClasses.ChangeLanguage;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.Forgot_Password_Retailer.Forgot_Pass_Retailer;
import com.haball.Retailor.ui.Support.SupportFragment;
import com.haball.SSL_HandShake;
import com.haball.Select_User.Register_Activity;
import com.haball.TextField;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pl.droidsonroids.gif.GifImageView;

public class Support_Ticket_Form extends AppCompatActivity {

    private TextInputEditText BName, Email, MobileNo, Comment;
    private TextInputLayout layout_BName, layout_Email, layout_MobileNo, layout_Comment;
    //    private ImageButton btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_DATA = "http://175.107.203.97:4014/api/support/PublicUsers";
    //    private String URL_SPINNER_ISSUETYPE = "https://175.107.203.97:4013/api/lookup/public/ISSUE_TYPE_PUBLIC";
//    private String URL_SPINNER_CRITICALITY = "https://175.107.203.97:4013/api/lookup/public/CRITICALITY_PUBLIC";
//    private String URL_SPINNER_PREFFEREDCONTACT = "https://175.107.203.97:4013/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4014/api/support/PublicSave";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();
    private HashMap<String, String> issue_type_map = new HashMap<>();
    private HashMap<String, String> criticality_map = new HashMap<>();
    private HashMap<String, String> preffered_contact_map = new HashMap<>();

    private String issueType, Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter<String> arrayAdapterIssueType, arrayAdapterCriticality, arrayAdapterPreferredContact;

    private Button login_submit, login_btn;
//    private RelativeLayout loader;

    private Typeface myFont;
    private Loader loader;

    private String DistributorId;
    //    private TextView tv_main_heading, tv_sub_heading;
    private int keyDel;
    private String language ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need__support);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        RelativeLayout rl_main_background = findViewById(R.id.rl_main_background);
        rl_main_background.setBackground(background_drawable);
        // selected Language Value
        SharedPreferences languageType = getSharedPreferences("changeLanguage",
                Context.MODE_PRIVATE);
        language = languageType.getString("language", "");
        loader = new Loader(Support_Ticket_Form.this);
        myFont = ResourcesCompat.getFont(this, R.font.open_sans);

        BName = findViewById(R.id.BName);
        Email = findViewById(R.id.Email);
        MobileNo = findViewById(R.id.MobileNo);
        Comment = findViewById(R.id.Comment);
        IssueType = findViewById(R.id.IssueType);
        critcicality = findViewById(R.id.critcicality);
        Preffered_Contact = findViewById(R.id.Preffered_Contact);
        login_submit = findViewById(R.id.login_submit);
        layout_BName = findViewById(R.id.layout_BName);
        layout_Email = findViewById(R.id.layout_Email);
        layout_MobileNo = findViewById(R.id.layout_MobileNo);
        layout_Comment = findViewById(R.id.layout_Comment);
//        BName.setSelected(false);
//        BName.setFocusable(false);
//        Email.setSelected(false);
//        Email.setFocusable(false);
//        MobileNo.setSelected(false);
//        MobileNo.setFocusable(false);
//        Comment.setSelected(false);
//        Comment.setFocusable(false);


//        tv_main_heading = findViewById(R.id.tv_main_heading);
//        tv_main_heading.setText(String.valueOf(tv_main_heading.getText()).replace("Distributor", "Retailer"));
//        tv_sub_heading = findViewById(R.id.tv_sub_heading);
//        tv_sub_heading.setText(String.valueOf(tv_sub_heading.getText()).replace("Distributor", "Retailer"));

        login_submit.setEnabled(false);
        login_submit.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        new TextField().changeColor(this, layout_BName, BName);
        new TextField().changeColor(this, layout_Email, Email);
        new TextField().changeColor(this, layout_MobileNo, MobileNo);
        new TextField().changeColor(this, layout_Comment, Comment);

        login_btn = findViewById(R.id.login_btn);
//        btn_back = (ImageButton) customView.findViewById(R.id.btn_back);

        issue_type.add(getResources().getString(R.string.issue_type));
        criticality.add(getResources().getString(R.string.criticality));
        preffered_contact.add(getResources().getString(R.string.preferred_method_of_contacting));

//        arrayAdapterIssueType = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, issue_type);
        arrayAdapterIssueType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, issue_type) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                return view;
            }
        };
//        arrayAdapterCriticality = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, criticality);
        arrayAdapterCriticality = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, criticality) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                return view;
            }
        };
//        arrayAdapterPreferredContact = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, preffered_contact);
        arrayAdapterPreferredContact = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, preffered_contact) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                text.setTypeface(myFont);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(30, 0, 30, 0);
                return view;
            }
        };

//        fetchIssueType();
//        fetchCriticality();
//        fetchPrefferedContact();
        fetchSpinnerData();

//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                    ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                }
//                issueType = issue_type.get(i);
                issueType = issue_type_map.get(issue_type.get(i));
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        critcicality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                    ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    Criticality = criticality_map.get(criticality.get(i));
                }
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Preffered_Contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                    ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                    ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                }
//                PrefferedContacts = preffered_contact.get(i);
                PrefferedContacts = preffered_contact_map.get(preffered_contact.get(i));
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_BName = BName.getText().toString();
                String txt_Email = Email.getText().toString();
                String txt_MobileNo = MobileNo.getText().toString();
                String txt_IssueType = (String) IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
                String txt_critcicality = (String) critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();
                String txt_Preffered_Contact = (String) Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
                String txt_Comment = Comment.getText().toString();

                if (!txt_BName.equals("") || !txt_Email.equals("") || !txt_MobileNo.equals("") || !txt_IssueType.equals("Issue Type") || !txt_critcicality.equals("Criticality") || !txt_Preffered_Contact.equals("Preferred Method of Contacting") || !txt_Comment.equals("")) {
                    showDiscardDialog();
                } else {
                    Intent intent = new Intent(Support_Ticket_Form.this, RetailerLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(BName.getText().toString()) ||
                        TextUtils.isEmpty(Email.getText().toString()) ||
//                        TextUtils.isEmpty(Comment.getText().toString()) ||
                        TextUtils.isEmpty(MobileNo.getText().toString())) {

                    Snackbar.make(view, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
                } else {

                    try {
                        makeTicketAddRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                checkBusinessName();

            }
        };
        TextWatcher textWatcher1 = new TextWatcher() {
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
        BName.addTextChangedListener(textWatcher);
        Email.addTextChangedListener(textWatcher1);
        MobileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MobileNo.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = MobileNo.getText().length();
                    if (len == 4) {
                        MobileNo.setText(MobileNo.getText() + "-");
                        MobileNo.setSelection(MobileNo.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                checkFieldsForEmptyValues();
                checkMobile();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
        changeLanguage();

    }


    private void checkBusinessName() {
        if (String.valueOf(BName.getText()).replaceAll(" ", "").equals("")) {
            layout_BName.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_BName.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_BName.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            BName.setTextColor(getResources().getColor(R.color.error_stroke_color));
            login_submit.setEnabled(false);
            login_submit.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
        } else {
            layout_BName.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
            layout_BName.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
            layout_BName.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
            BName.setTextColor(getResources().getColor(R.color.textcolor));
            checkFieldsForEmptyValues();
        }
    }

    private void checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (!Email.getText().toString().matches(reg_ex)) {
            layout_Email.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_Email.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_Email.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            Email.setTextColor(getResources().getColor(R.color.error_stroke_color));
            login_submit.setEnabled(false);
            login_submit.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            layout_Email.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
            layout_Email.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
            layout_Email.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
            Email.setTextColor(getResources().getColor(R.color.textcolor));
            checkFieldsForEmptyValues();
        }
    }

    private void checkMobile() {
        if (String.valueOf(MobileNo.getText()).length() != 12) {
            layout_MobileNo.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_MobileNo.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_MobileNo.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            MobileNo.setTextColor(getResources().getColor(R.color.error_stroke_color));
        } else {
            layout_MobileNo.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
            layout_MobileNo.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
            layout_MobileNo.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
            MobileNo.setTextColor(getResources().getColor(R.color.textcolor));
            checkFieldsForEmptyValues();
        }
    }

    @Override
    public void onBackPressed() {

        String txt_BName = BName.getText().toString();
        String txt_Email = Email.getText().toString();
        String txt_MobileNo = MobileNo.getText().toString();
        String txt_Preffered_Contact = getResources().getString(R.string.preferred_method_of_contacting);
        if (Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()) != null)
            txt_Preffered_Contact = Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
        String txt_IssueType =getResources().getString(R.string.issue_type);
        if (IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()) != null)
            txt_IssueType = IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
        String txt_critcicality = getResources().getString(R.string.criticality);
        if (critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()) != null)
            txt_critcicality = critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();
        String txt_Comment = Comment.getText().toString();

        if (!txt_BName.equals("") || !txt_Email.equals("") || !txt_MobileNo.equals("") || !txt_IssueType.equals("Issue Type") || !txt_critcicality.equals("Criticality") || !txt_Preffered_Contact.equals("Preferred Method of Contacting") || !txt_Comment.equals("")) {
            showDiscardDialog();
        } else {
            Intent intent = new Intent(Support_Ticket_Form.this, RetailerLogin.class);
            startActivity(intent);
            finish();
        }
    }


    private void showDiscardDialog() {
        // Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText(R.string.discard_text);
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText(R.string.yes);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                Intent intent = new Intent(Support_Ticket_Form.this, RetailerLogin.class);
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
        if(!alertDialog.isShowing())
        alertDialog.show();
    }


    private void checkFieldsForEmptyValues() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        String bname = BName.getText().toString();
        String email = Email.getText().toString();
        String mobile = MobileNo.getText().toString();
        String contact = getResources().getString(R.string.preferred_method_of_contacting);
        if (Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()) != null)
            contact = Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
        String issue_type =getResources().getString(R.string.issue_type);
        if (IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()) != null)
            issue_type = IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
        String critical = getResources().getString(R.string.criticality);
        if (critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()) != null)
            critical = critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();

        if (bname.equals("")
                || bname.replaceAll(" ", "").equals("")
                || email.equals("")
                || !Email.getText().toString().matches(reg_ex)
                || mobile.equals("")
                || mobile.length() != 12
//                || comment.equals("")
                || contact.equals(getResources().getString(R.string.preferred_method_of_contacting))
                || issue_type.equals(getResources().getString(R.string.issue_type))
                || critical.equals(getResources().getString(R.string.criticality))
        ) {
            login_submit.setEnabled(false);
            login_submit.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            login_submit.setEnabled(true);
            login_submit.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private void makeTicketAddRequest() throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("ContactName", BName.getText().toString());
        map.put("Email", Email.getText().toString());
        map.put("MobileNumber", MobileNo.getText().toString());
        map.put("IssueType", issueType);
        map.put("Criticality", Criticality);
        map.put("PreferredContactMethod", PrefferedContacts);
        map.put("Description", Comment.getText().toString());

        // Log.i("TICKET OBJECT", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(Support_Ticket_Form.this);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                // Log.e("RESPONSE", result.toString());
//                Toast.makeText(getApplicationContext(), "Ticket Created Successfully", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Support_Ticket_Form.this, RetailerLogin.class);
//                startActivity(intent);
//                finish();


                final Dialog fbDialogue = new Dialog(Support_Ticket_Form.this);
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                txt_header1.setText(R.string.tickted_created);
                try {
                    tv_pr1.setText((getResources().getString(R.string.your_ticket_id)) + result.get("TicketNumber") + (getResources().getString(R.string.ticket_created_msg_logout)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                        Intent intent = new Intent(Support_Ticket_Form.this, RetailerLogin.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new HaballError().printErrorMessage(Support_Ticket_Form.this, error);
                new ProcessingError().showError(Support_Ticket_Form.this);
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(sr);
    }
//
//    private void fetchIssueType() {
//        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_ISSUETYPE, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray result) {
//                try {
//                    JSONObject jsonObject = null;
//                    for (int i = 0; i < result.length(); i++) {
//                        jsonObject = result.getJSONObject(i);
//                        issue_type.add(jsonObject.getString("value"));
//                    }
//                    // Log.i("issue type values => ", issue_type.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                // Log.e("RESPONSE OF ISSUE TYPE", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    String responseBody = new String(error.networkResponse.data, "utf-8");
//                    JSONObject data = new JSONObject(responseBody);
//                    String message = data.getString("message");
//                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("rightid", "-1");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(sr);
//        arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterIssueType.notifyDataSetChanged();
//        IssueType.setAdapter(arrayAdapterIssueType);
//    }
//
//    private void fetchCriticality() {
//        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_CRITICALITY, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray result) {
//                try {
//                    JSONObject jsonObject = null;
//                    for (int i = 0; i < result.length(); i++) {
//                        jsonObject = result.getJSONObject(i);
//                        criticality.add(jsonObject.getString("value"));
//
//                    }
//                    // Log.i("criticality values => ", criticality.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                // Log.e("RESPONSE OF criticality", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    String responseBody = new String(error.networkResponse.data, "utf-8");
//                    JSONObject data = new JSONObject(responseBody);
//                    String message = data.getString("message");
//                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("rightid", "-1");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(sr);
//        arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterCriticality.notifyDataSetChanged();
//        critcicality.setAdapter(arrayAdapterCriticality);
//    }
//
//    private void fetchPrefferedContact() {
//        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_PREFFEREDCONTACT, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray result) {
//                try {
//                    JSONObject jsonObject = null;
//                    for (int i = 0; i < result.length(); i++) {
//                        jsonObject = result.getJSONObject(i);
//                        preffered_contact.add(jsonObject.getString("value"));
//                    }
//
//                    // Log.i("preffered_contact => ", preffered_contact.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                // Log.e("RESPONSE preferedcont", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    String responseBody = new String(error.networkResponse.data, "utf-8");
//                    JSONObject data = new JSONObject(responseBody);
//                    String message = data.getString("message");
//                    Toast.makeText(new Support_Ticket_Form(), message, Toast.LENGTH_LONG).show();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("rightid", "-1");
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(sr);
//        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterPreferredContact.notifyDataSetChanged();
//        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
//    }

    private void fetchSpinnerData() {
        loader.showLoader();
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(Support_Ticket_Form.this);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, URL_SPINNER_DATA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                JSONObject jsonObject = null;
//                loader.setVisibility(View.GONE);
//                loader.hideLoader();
                try {
                    JSONArray temp_preffered_contact = result.getJSONArray("CONTACTING_METHOD");
                    jsonObject = null;
                    for (int i = 0; i < temp_preffered_contact.length(); i++) {
                        jsonObject = temp_preffered_contact.getJSONObject(i);
                        preffered_contact.add(jsonObject.getString("value"));

                        preffered_contact_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                    }

                    JSONArray temp_criticality = result.getJSONArray("CRITICALITY_PUBLIC");
                    jsonObject = null;
                    for (int i = 0; i < temp_criticality.length(); i++) {
                        jsonObject = temp_criticality.getJSONObject(i);
                        criticality.add(jsonObject.getString("value"));
                        criticality_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                    }

                    JSONArray temp_issue_type = result.getJSONArray("ISSUE_TYPE_PUBLIC");
                    jsonObject = null;
                    for (int i = 0; i < temp_issue_type.length(); i++) {
                        jsonObject = temp_issue_type.getJSONObject(i);
                        issue_type.add(jsonObject.getString("value"));
                        issue_type_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                    }
                    // Log.i("preffered_contact => ", preffered_contact.toString());
                    arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterPreferredContact.notifyDataSetChanged();
                    Preffered_Contact.setAdapter(arrayAdapterPreferredContact);

                    arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterCriticality.notifyDataSetChanged();
                    critcicality.setAdapter(arrayAdapterCriticality);

                    arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterIssueType.notifyDataSetChanged();
                    IssueType.setAdapter(arrayAdapterIssueType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Log.e("RESPONSE preferedcont", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                new ProcessingError().showError(Support_Ticket_Form.this);
                new HaballError().printErrorMessage(Support_Ticket_Form.this, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
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


    private void printErrorMessage(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(this, "Network Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(this, "Server Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(this, "Auth Failure Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(this, "Parse Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(this, "No Connection Error !", Toast.LENGTH_LONG).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(this, "Timeout Error !", Toast.LENGTH_LONG).show();
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                // Log.i("responseBody", responseBody);
                JSONObject data = new JSONObject(responseBody);
                // Log.i("data", String.valueOf(data));
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    message = message + data.get(key) + "\n";
                }
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    void changeLanguage() {
        ChangeLanguage changeLanguage = new ChangeLanguage();
        changeLanguage.changeLanguage(this, language);
        if (language.equals("ur")) {
           // login_submit.setText(R.string.reset);
            //login_btn.setText(R.string.login);
        }
    }
}
