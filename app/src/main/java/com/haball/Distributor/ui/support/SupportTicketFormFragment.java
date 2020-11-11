package com.haball.Distributor.ui.support;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
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

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SupportTicketFormFragment extends Fragment {

    private TextInputEditText BName, Email, MobileNo, Comment;
   private Button btn_back;
    private TextInputLayout layout_BName, layout_Email, layout_MobileNo, layout_Comment;
    private Spinner IssueType, critcicality, Preffered_Contact;
    private String URL_SPINNER_ISSUETYPE = "https://175.107.203.97:4013/api/lookup/ISSUE_TYPE_PRIVATE";
    private String URL_SPINNER_CRITICALITY = "https://175.107.203.97:4013/api/lookup/CRITICALITY_PRIVATE";
    private String URL_SPINNER_PREFFEREDCONTACT = "https://175.107.203.97:4013/api/lookup/public/CONTRACTING_METHOD";
    private String URL_TICkET = "https://175.107.203.97:4013/api/contact/save";

    private List<String> issue_type = new ArrayList<>();
    private List<String> criticality = new ArrayList<>();
    private List<String> preffered_contact = new ArrayList<>();

    private String issueType, Criticality, PrefferedContacts;
    private String Token;
    private ArrayAdapter<String> arrayAdapterIssueType, arrayAdapterCriticality, arrayAdapterPreferredContact;

    private Button ticket_btn;
    private int keyDel;
    private String first_name = "", email = "", phone_number = "";
    private Loader loader;

    private String DistributorId;
    private Typeface myFont;
    private Boolean changed = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_support__ticket__form, container, false);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        loader = new Loader(getContext());

        SharedPreferences data = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String first_name = data.getString("CompanyName", "");
        final String email = data.getString("EmailAddress", "");
        final String phone_number = data.getString("Mobile", "");

        Log.i("name", first_name);
        Log.i("email", email);
        Log.i("phone_number", phone_number);
        BName = root.findViewById(R.id.BName);
        Email = root.findViewById(R.id.Email);
        MobileNo = root.findViewById(R.id.MobileNo);
        Comment = root.findViewById(R.id.Comment);

        layout_BName = root.findViewById(R.id.layout_BName);
        layout_Email = root.findViewById(R.id.layout_Email);
        layout_MobileNo = root.findViewById(R.id.layout_MobileNo);
        layout_Comment = root.findViewById(R.id.layout_Comment);


        new TextField().changeColor(this.getContext(), layout_BName, BName);
        new TextField().changeColor(this.getContext(), layout_Email, Email);
        new TextField().changeColor(this.getContext(), layout_MobileNo, MobileNo);
        new TextField().changeColor(this.getContext(), layout_Comment, Comment);

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

        btn_back =  root.findViewById(R.id.btn_back_support);

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

                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new SupportFragment());
                    fragmentTransaction.commit();

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


        fetchIssueType();

        IssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                issueType = issue_type.get(i);
                checkFieldsForEmptyValues();
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50, 0, 50, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50, 0, 50, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        critcicality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Criticality = criticality.get(i);
                checkFieldsForEmptyValues();
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50, 0, 50, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50, 0, 50, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Preffered_Contact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PrefferedContacts = preffered_contact.get(i);
                checkFieldsForEmptyValues();
                if (i == 0) {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50, 0, 50, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                        ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                        ((TextView) adapterView.getChildAt(0)).setPadding(50, 0, 50, 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }

                }
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
                checkFieldsForEmptyValues();

            }
        };

        BName.addTextChangedListener(textWatcher);
        Email.addTextChangedListener(textWatcher);
        MobileNo.addTextChangedListener(textWatcher);
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

    private void makeTicketAddRequest() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);

        JSONObject map = new JSONObject();
        map.put("Name", BName.getText().toString());
        map.put("EmailAddress", Email.getText().toString());
        map.put("Mobile", MobileNo.getText().toString());
        map.put("DistributorId", DistributorId);
        map.put("IssueType", issueType);
        map.put("Criticality", Criticality);
        map.put("ContactingMethod", PrefferedContacts);
        map.put("Message", Comment.getText().toString());
        map.put("ID", 0);

        Log.i("TICKET OBJECT", String.valueOf(map));
        loader.showLoader();
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_TICkET, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                Log.e("RESPONSE", result.toString());
//                Toast.makeText(getContext(), "Ticket generated successfully.", Toast.LENGTH_LONG).show();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(((ViewGroup) getView().getParent()).getId(), new SupportFragment());
//                fragmentTransaction.commit();
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
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());

                error.printStackTrace();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("rightid", "-1");
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    private void fetchIssueType() {
        loader.showLoader();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);

        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_ISSUETYPE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        issue_type.add(jsonObject.getString("value"));
                    }
                    Log.i("issue type values => ", issue_type.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF ISSUE TYPE", result.toString());
                fetchCriticality();
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
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterIssueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterIssueType.notifyDataSetChanged();
        IssueType.setAdapter(arrayAdapterIssueType);
    }

    private void fetchCriticality() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);


        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_CRITICALITY, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        criticality.add(jsonObject.getString("value"));

                    }
                    Log.i("criticality values => ", criticality.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("RESPONSE OF criticality", result.toString());
                fetchPrefferedContact();
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
                params.put("rightid", "-1");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterCriticality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterCriticality.notifyDataSetChanged();
        critcicality.setAdapter(arrayAdapterCriticality);
    }

    private void fetchPrefferedContact() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        Log.i("Token", Token);


        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_SPINNER_PREFFEREDCONTACT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray result) {
                loader.hideLoader();
                try {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < result.length(); i++) {
                        jsonObject = result.getJSONObject(i);
                        preffered_contact.add(jsonObject.getString("value"));
                    }

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
                new ProcessingError().showError(getContext());

                error.printStackTrace();
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
        Volley.newRequestQueue(getContext()).add(sr);
        arrayAdapterPreferredContact.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPreferredContact.notifyDataSetChanged();
        Preffered_Contact.setAdapter(arrayAdapterPreferredContact);
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

                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new SupportFragment());
                fragmentTransaction.commit();
//                Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                ((FragmentActivity) getContext()).startActivity(login_intent);
//                ((FragmentActivity) getContext()).finish();

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
    @Override
    public void onResume() {
        super.onResume();
        View.OnKeyListener listener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
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

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new SupportFragment());
                        fragmentTransaction.commit();

                    }
                    return true;
                }
                return false;
            }
        };
        BName.setOnKeyListener(listener);
        Email.setOnKeyListener(listener);
        MobileNo.setOnKeyListener(listener);
        Comment.setOnKeyListener(listener);

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
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

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new SupportFragment());
                        fragmentTransaction.commit();

                    }

                    return  true;
                }
                return false;
            }
        });

    }
}

