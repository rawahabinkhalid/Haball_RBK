package com.haball.Retailor.ui.Support;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.RetailorDashboard;
import com.haball.SSL_HandShake;
import com.haball.Support.Support_Ditributor.Support_Ticket_Form;
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

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support_Ticket_Form_Fragment extends Fragment {

    private TextInputEditText BName, Email, MobileNo, Comment;
    private TextInputLayout layout_BName, layout_Email, layout_MobileNo, layout_Comment;
    private String DistributorId;
    private Button btn_back;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_DATA = " http://175.107.203.97:4014/api/lookup/null";
    //    private String URL_SPINNER_ISSUETYPE = "http://175.107.203.97:4014/api/lookup/public/ISSUE_TYPE_PRIVATE";
//    private String URL_SPINNER_CRITICALITY = "http://175.107.203.97:4014/api/lookup/public/CRITICALITY_PRIVATE";
//    private String URL_SPINNER_PREFFEREDCONTACT = "http://175.107.203.97:4014/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "http://175.107.203.97:4014/api/support/PrivateSave";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();
    private Map<String, String> issue_type_map = new HashMap<>();
    private Map<String, String> criticality_map = new HashMap<>();
    private Map<String, String> preffered_contact_map = new HashMap<>();

    private String issueType, Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter arrayAdapterIssueType;
    private ArrayAdapter arrayAdapterCriticality;
    private ArrayAdapter arrayAdapterPreferredContact;
    private Button ticket_btn;
    private Typeface myFont;
    private int keyDel;
    private String first_name = "", email = "", phone_number = "";
    private Boolean changed = false;
    private Loader loader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_support__ticket__form__retailerform, container, false);


        SharedPreferences data = getContext().getSharedPreferences("SendData",
                Context.MODE_PRIVATE);
        first_name = data.getString("first_name", "");
        email = data.getString("email", "");
        phone_number = data.getString("phone_number", "");

        loader = new Loader(getContext());

        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);

        Log.i("name", first_name);
        Log.i("email", email);
        Log.i("phone_number", phone_number);

        BName = root.findViewById(R.id.BName);
        Email = root.findViewById(R.id.Email);
        MobileNo = root.findViewById(R.id.MobileNo);
        Comment = root.findViewById(R.id.Comment);

        btn_back = root.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_BName = String.valueOf(BName.getText());
                final String txt_Email = String.valueOf(Email.getText());
                final String txt_MobileNo = String.valueOf(MobileNo.getText());
                final String txt_Comment = String.valueOf(Comment.getText());

                BName.clearFocus();
                Email.clearFocus();
                MobileNo.clearFocus();
                Comment.clearFocus();
                if (!txt_BName.equals(first_name) || !txt_Email.equals(email) || !txt_MobileNo.equals(phone_number) || !txt_Comment.equals("") || !issueType.equals("Issue Type") || !Criticality.equals("Criticality") || !PrefferedContacts.equals("Preferred Method of Contacting")) {
                    showDiscardDialog();
                } else {
//                        fm.popBackStack();
                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();

                }

            }
        });

        BName.setEnabled(false);

        Email.setFocusable(false);
        MobileNo.setFocusable(false);


        Email.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Email.getRight() - Email.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        Email.setFocusable(true);
                        Email.setFocusableInTouchMode(true);
                        Email.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        Email.setSelection(Email.getText().length());
//                                btn_save_password.setEnabled(true);
//                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                        changed = true;
                        return true;
                    }
                }
                return false;
            }
        });


        MobileNo.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (MobileNo.getRight() - MobileNo.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        MobileNo.setInputType(InputType.TYPE_CLASS_NUMBER);
                        MobileNo.setFocusable(true);
                        MobileNo.setFocusableInTouchMode(true);
                        MobileNo.requestFocus();

                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        MobileNo.setSelection(MobileNo.getText().length());
//                                btn_save_password.setEnabled(true);
//                                btn_save_password.setBackground(getResources().getDrawable(R.drawable.button_background));
                        changed = true;
                        return true;
                    }
                }
                return false;
            }
        });

        layout_BName = root.findViewById(R.id.layout_BName);
        layout_Email = root.findViewById(R.id.layout_Email);
        layout_MobileNo = root.findViewById(R.id.layout_MobileNo);
        layout_Comment = root.findViewById(R.id.layout_Comment);

        new TextField().changeColor(getContext(), layout_BName, BName);
        new TextField().changeColor(getContext(), layout_Email, Email);
        new TextField().changeColor(getContext(), layout_MobileNo, MobileNo);
        new TextField().changeColor(getContext(), layout_Comment, Comment);

        IssueType = root.findViewById(R.id.IssueType);
        critcicality = root.findViewById(R.id.critcicality);
        Preffered_Contact = root.findViewById(R.id.Preffered_Contact);
        ticket_btn = root.findViewById(R.id.ticket_btn);
        ticket_btn.setEnabled(false);
        ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));


        Email.setText(email);
        MobileNo.setText(phone_number);
        BName.setText(first_name);
        BName.setTextColor(getResources().getColor(R.color.textcolor));
        MobileNo.setTextColor(getResources().getColor(R.color.textcolor));
        Email.setTextColor(getResources().getColor(R.color.textcolor));

        issue_type.add("Issue Type");
        issueType = "Issue Type";
        criticality.add("Criticality");
        Criticality = "Criticality";
        preffered_contact.add("Preferred Method of Contacting");
        PrefferedContacts = "Preferred Method of Contacting";


//        arrayAdapterIssueType = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, issue_type);
        arrayAdapterIssueType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, issue_type) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
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
                text.setPadding(50, 0, 50, 0);
                return view;
            }
        };
//        arrayAdapterCriticality = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, criticality);
        arrayAdapterCriticality = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, criticality) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
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
                text.setPadding(50, 0, 50, 0);
                return view;
            }
        };
//        arrayAdapterPreferredContact = new ArrayAdapter<>(this,
//                android.R.layout.simple_dropdown_item_1line, preffered_contact);
        arrayAdapterPreferredContact = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, preffered_contact) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(getResources().getColor(R.color.text_color_selection));
                text.setTextSize((float) 13.6);
                text.setPadding(50, 0, 50, 0);
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
                text.setPadding(50, 0, 50, 0);
                return view;
            }
        };

        fetchSpinnerData();
//        fetchCriticality();
//        fetchPrefferedContact();


        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    issueType = "Issue Type";
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
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
//                    issueType = issue_type.get(i);
                    issueType = issue_type_map.get(issue_type.get(i));
                }
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        critcicality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Criticality = "Criticality";
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
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
//                    Criticality = criticality.get(i);
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
                if (i == 0) {
                    PrefferedContacts = "Preferred Method of Contacting";
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                        ((TextView) adapterView.getChildAt(0)).setTypeface(myFont);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
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
                    PrefferedContacts = preffered_contact_map.get(preffered_contact.get(i));
                }
                checkFieldsForEmptyValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ticket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(BName.getText().toString()) ||
                        TextUtils.isEmpty(Email.getText().toString()) ||
//                        TextUtils.isEmpty(Comment.getText().toString()) ||
                        TextUtils.isEmpty(MobileNo.getText().toString())) {

                    Snackbar.make(v, "Please Enter All Required Fields", Snackbar.LENGTH_SHORT).show();
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
                keyDel = 0;
                checkFieldsForEmptyValues();

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
                keyDel = 0;
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

        return root;
    }

    private void checkEmail() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        if (!Email.getText().toString().matches(reg_ex)) {
            layout_Email.setBoxStrokeColor(getResources().getColor(R.color.error_stroke_color));
            layout_Email.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            layout_Email.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.error_stroke_color)));
            Email.setTextColor(getResources().getColor(R.color.error_stroke_color));
            ticket_btn.setEnabled(false);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
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
            ticket_btn.setEnabled(false);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            layout_MobileNo.setBoxStrokeColor(getResources().getColor(R.color.box_stroke));
            layout_MobileNo.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.green_color)));
            layout_MobileNo.setPasswordVisibilityToggleTintList(ColorStateList.valueOf(getResources().getColor(R.color.textcolorhint)));
            MobileNo.setTextColor(getResources().getColor(R.color.textcolor));
            checkFieldsForEmptyValues();
        }
    }

    private void checkFieldsForEmptyValues() {
        String reg_ex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        keyDel = 0;
        String bname = BName.getText().toString();
        String email = Email.getText().toString();
        String mobile = MobileNo.getText().toString();
        String comment = Comment.getText().toString();
        String contact = "Preferred Method of Contacting";
        if (Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()) != null)
            contact = Preffered_Contact.getItemAtPosition(Preffered_Contact.getSelectedItemPosition()).toString();
        String issue_type = "Issue Type";
        if (IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()) != null)
            issue_type = IssueType.getItemAtPosition(IssueType.getSelectedItemPosition()).toString();
        String critical = "Criticality";
        if (critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()) != null)
            critical = critcicality.getItemAtPosition(critcicality.getSelectedItemPosition()).toString();

        if (bname.equals("")
                || email.equals("")
                || !Email.getText().toString().matches(reg_ex)
                || mobile.equals("")
                || mobile.length() != 12
//                || comment.equals("")
                || contact.equals("Preferred Method of Contacting")
                || issue_type.equals("Issue Type")
                || critical.equals("Criticality")
        ) {
            ticket_btn.setEnabled(false);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));

        } else {
            ticket_btn.setEnabled(true);
            ticket_btn.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final String txt_BName = String.valueOf(BName.getText());
        final String txt_Email = String.valueOf(Email.getText());
        final String txt_MobileNo = String.valueOf(MobileNo.getText());
        final String txt_Comment = String.valueOf(Comment.getText());
        final FragmentManager fm = getActivity().getSupportFragmentManager();


        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("supportDebug1", "'" + txt_BName + "', '" + first_name + "'");
                    Log.i("supportDebug1", "'" + txt_Email + "', '" + email + "'");
                    Log.i("supportDebug1", "'" + txt_MobileNo + "', '" + phone_number + "'");
                    Log.i("supportDebug1", "'" + txt_Comment + "'");
                    Log.i("supportDebug1", "'" + issueType + "', '" + IssueType.getSelectedItemPosition() + "'");
                    Log.i("supportDebug1", "'" + PrefferedContacts + "', '" + Preffered_Contact.getSelectedItemPosition() + "'");
                    Log.i("supportDebug1", "'" + Criticality + "', '" + critcicality.getSelectedItemPosition() + "'");

                    BName.clearFocus();
                    Email.clearFocus();
                    MobileNo.clearFocus();
                    Comment.clearFocus();
                    if (!txt_BName.equals(first_name) || !txt_Email.equals(email) || !txt_MobileNo.equals(phone_number) || !txt_Comment.equals("") || !issueType.equals("Issue Type") || !Criticality.equals("Criticality") || !PrefferedContacts.equals("Preferred Method of Contacting")) {
                        showDiscardDialog();
                    } else {
//                        fm.popBackStack();
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();

                    }
                }
                return false;
            }
        };

        BName.setOnKeyListener(keyListener);
        Email.setOnKeyListener(keyListener);
        MobileNo.setOnKeyListener(keyListener);
        Comment.setOnKeyListener(keyListener);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("supportDebug", "'" + txt_BName + "', '" + first_name + "'");
                    Log.i("supportDebug", "'" + txt_Email + "', '" + email + "'");
                    Log.i("supportDebug", "'" + txt_MobileNo + "', '" + phone_number + "'");
                    Log.i("supportDebug", "'" + txt_Comment + "'");
                    Log.i("supportDebug", "'" + issueType + "', '" + IssueType.getSelectedItemPosition() + "'");
                    Log.i("supportDebug", "'" + PrefferedContacts + "', '" + Preffered_Contact.getSelectedItemPosition() + "'");
                    Log.i("supportDebug", "'" + Criticality + "', '" + critcicality.getSelectedItemPosition() + "'");
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
//                    if (!txt_BName.equals(first_name) || !txt_Email.equals(email) || !txt_MobileNo.equals(phone_number) || !txt_Comment.equals("") || !issueType.equals("Issue Type") || !Criticality.equals("Criticality") || !PrefferedContacts.equals("Preferred Method of Contacting")) {
                    if (!txt_BName.equals(first_name) || !txt_Email.equals(email) || !txt_MobileNo.equals(phone_number) || !txt_Comment.equals("") || !issueType.equals("Issue Type") || !Criticality.equals("Criticality") || !PrefferedContacts.equals("Preferred Method of Contacting")) {
//                    if (!txt_BName.equals("") || !txt_Email.equals("") || !txt_MobileNo.equals("") || !txt_Comment.equals("") || !issueType.equals("Issue Type") || !critcicality_val.equals("Criticality") || !preffered_Contact.equals("Preferred Method of Contacting")) {
                        showDiscardDialog();
                        return true;
                    } else {
//                        fm.popBackStack();
                        SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) getContext()), RetailorDashboard.class);
                        ((FragmentActivity) getContext()).startActivity(login_intent);
                        ((FragmentActivity) getContext()).finish();

                        return false;
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
//                    Log.i("issue type values => ", issue_type.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("RESPONSE OF ISSUE TYPE", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(error);
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
//        Volley.newRequestQueue(this.getContext()).add(sr);
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
//                    Log.i("criticality values => ", criticality.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("RESPONSE OF criticality", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(error);
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
//        Volley.newRequestQueue(this.getContext()).add(sr);
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
//                    Log.i("preffered_contact => ", preffered_contact.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.e("RESPONSE preferedcont", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(error);
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
//        Volley.newRequestQueue(this.getContext()).add(sr);
//        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        arrayAdapterPreferredContact.notifyDataSetChanged();
//        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
//    }


    private void fetchSpinnerData() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_DATA, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        if (jsonObject.get("type").equals("ISSUE_TYPE_PRIVATE")) {
                            issue_type.add(jsonObject.getString("value"));
                            issue_type_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                        } else if (jsonObject.get("type").equals("CONTACTING_METHOD")) {
                            preffered_contact.add(jsonObject.getString("value"));
                            preffered_contact_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                        } else if (jsonObject.get("type").equals("CRITICALITY_PRIVATE")) {
                            criticality.add(jsonObject.getString("value"));
                            criticality_map.put(jsonObject.getString("value"), jsonObject.getString("key"));
                        }

                    }
                    arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterCriticality.notifyDataSetChanged();
                    critcicality.setAdapter(arrayAdapterCriticality);

                    arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterPreferredContact.notifyDataSetChanged();
                    Preffered_Contact.setAdapter(arrayAdapterPreferredContact);

                    arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapterIssueType.notifyDataSetChanged();
                    IssueType.setAdapter(arrayAdapterIssueType);
                    Log.i("preffered_contact => ", preffered_contact.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE preferedcont", result.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
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
        Volley.newRequestQueue(this.getContext()).add(sr);
        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPreferredContact.notifyDataSetChanged();
        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
    }

    private void makeTicketAddRequest() throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("ContactName", BName.getText().toString());
        map.put("Email", Email.getText().toString());
        map.put("MobileNumber", MobileNo.getText().toString());
//        map.put("DistributorId", DistributorId);
        map.put("IssueType", issueType);
        map.put("Criticality", Criticality);
        map.put("PreferredContactMethod", PrefferedContacts);
        map.put("Description", Comment.getText().toString());
        map.put("ID", 0);

        Log.i("TICKET_OBJECT", String.valueOf(map));
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                loader.hideLoader();
                Log.e("RESPONSE", result.toString());
//                Toast.makeText(getContext(), "Ticket generated successfully.", Toast.LENGTH_LONG).show();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(((ViewGroup) getView().getParent()).getId(), new SupportFragment());
//                fragmentTransaction.commit();


                final Dialog fbDialogue = new Dialog(getActivity());
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                txt_header1.setText("Ticket Created");
                try {
                    tv_pr1.setText("Your Ticket ID " + result.get("TicketNumber") + " has been created successfully.");
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
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(((ViewGroup) getView().getParent()).getId(), new SupportFragment());
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
        Volley.newRequestQueue(this.getContext()).add(sr);
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
