package com.haball.Distributor.ui;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.R;
import com.haball.Registration.BooleanRequest;
import com.haball.Retailer_Login.RetailerLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Distributor_Terms_And_Conditions extends Fragment {

    private Button back_button;
    private String Token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.retailer_logged_in_terms_and_conditions, container, false);

        back_button = root.findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent login_intent = new Intent(getContext(), RetailorDashboard.class);
//                ((FragmentActivity) getContext()).startActivity(login_intent);
//                ((FragmentActivity) getContext()).finish();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
                fragmentTransaction.commit();
            }
        });


        WebView webView = (WebView) root.findViewById(R.id.txt_change3);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        String htmlText = " %s ";
        String myData = "<html><head><link href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,400,700\" rel=\"stylesheet\"><style>.open-sans-font{ font-family: 'Open Sans', sans-serif; }</style></head><body class=\"open-sans-font\" style=\"text-align:justify; font-family: open-sans; font-size: 13px;padding-bottom: 10px\"><p style=\"text-align: center\"><b>HABALL DIGITAL INTERFACE END USER LICENSE AGREEMENT</b></p><p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><strong><span style='font-size:13px;' class=\"open-sans-font\">IMPORTANT NOTE: -</span></strong><span style='font-size:13px;' class=\"open-sans-font\">&nbsp;</span><span class=\"open-sans-font\" style='font-size:11px;'>THIS HABALL DIGITAL INTERFACE END USER LICENSE AGREEMENT IS A LEGAL AND BINDING AGREEMENT BETWEEN &ldquo;<strong>YOU</strong>&rdquo; AND HABALL (PRIVATE) LIMITED HAVING REGISTERED PLACE OF BUSINESS AT</span> <span class=\"open-sans-font\" style='font-size:11px;'>MZ2, HORIZON VISTA, BLOCK 4, CLIFTON, KARACHI, PAKISTAN (&ldquo;<strong>HABALL</strong>&rdquo;) AND SHALL GOVERN LICENSE TO USE THE HABALL DIGITAL INTERFACE. BY CLICKING &ldquo;ACCEPT&rdquo; YOU HEREBY AGREE TO BE BOUND BY THE TERMS AND CONDITIONS OF THIS END USER LICENSE AGREEMENT (&ldquo;<strong>EULA</strong>&rdquo;). IF A COMPANY IS ACCEPTING THIS AGREEMENT ON BEHALF OF ANOTHER PERSON OR OTHER LEGAL ENTITY, COMPANY REPRESENTS AND WARRANTS THAT COMPANY HAS FULL AUTHORITY TO BIND THAT PERSON OR LEGAL ENTITY TO THIS AGREEMENT. THE COMPANY MUST ENSURE THAT END USERS (AS DEFINED BELOW) COMPLY WITH THIS AGREEMENT AND IS RESPONSIBLE FOR END USERS' COMPLIANCE WITH OR BREACH OF THIS AGREEMENT. &nbsp;</span></p>\n" +
                "<ol class=\"decimal_type\" style=\"list-style-type: decimal;margin-left:-0.25in;\">\n" +
                "    <li><strong><span style=\"font-size:8.0pt;;\">LICENSE GRANT; TERMS OF USE&nbsp;</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: decimal;\">\n" +
                "            <li><span class=\"open-sans-font\">Right to use the Haball Digital Interface: is subject to the terms and conditions of this EULA. Haball grants you a non-exclusive, non-transferable, non-assignable, non-perpetual license/right to use the Haball Digital Interface solely for your own use or internal business operations.&nbsp;</span></li>\n" +
                "            <li><span class=\"open-sans-font\">You agree that Haball can grant a similar License of the Haball Digital Interface to any third party or to enter into similar agreements with other companies/entities/ corporations.</span></li>\n" +
                "            <li><span class=\"open-sans-font\">Upon payment of the associated License Fee by the Onboarding Party, you shall be entitled to the License for the limited Use of the Haball Digital Interface as provided herein.&nbsp;</span></li>\n" +
                "            <li><span class=\"open-sans-font\">You hereby agree that the License for the limited Use of the Haball Digital Interface shall only be used in accordance with the terms and conditions of this EULA.</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span style=\"font-size:8.0pt;;\">RESTRICTIONS&nbsp;</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\">You may not, and may not cause or allow any third party to: (a) decompile, disassemble or reverse-engineer the Haball Digital Interface, or create or recreate the source code for the Haball Digital Interface; (b) remove, erase, obscure or tamper with any copyright or any other product identification or proprietary rights notices, seal or instructional label printed or stamped on, affixed to, or encoded or recorded in or on the Haball Digital Interface or documentation; or fail to preserve all copyright and other proprietary notices in all copies; (c) lease, lend or sell, market, license, sublicense, distribute or otherwise grant to any person or entity any right to use the Haball Digital Interface EULA except to the extent expressly permitted in this EULA; or use the Haball Digital Interface to provide, alone or in combination with any other product or service, any product or service to any person or entity, whether on a fee basis or otherwise; (d) modify, adapt, tamper with, translate or create derivative works of the Haball Digital Interface or the associated documentation; combine or merge any part of the Haball Digital Interface or any associated documentation with or into any other software or documentation; or refer to or otherwise use the Haball Digital Interface as part of any effort to develop software (including any routine, script, code, or program) having any functional attributes, visual expressions or other features similar to those of the Haball Digital Interface to compete with Haball (e) except with Haball's prior written permission, publish any performance or benchmark tests or analysis relating to the Haball Digital Interface;&nbsp;</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "</ol>\n" +
                "<p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>(f) attempt to do any of activities in Subsections (a) to (e).&nbsp;</span></p>\n" +
                "<ol class=\"decimal_type\" style=\"list-style-type: undefined;margin-left:-0.25in;\">\n" +
                "    <li><strong><span style=\"font-size:8.0pt;;\">GENERAL TERMS &amp; CONDITIONS</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>The Haball Digital Interface will be available only for End Users of Haball.&nbsp;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>In order to use the Haball Digital Interface, you must get yourself Registered with Haball, through an Onboarding Party.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Subsequent to such registration, initially a system generated login password will be sent to you through SMS and/or Email, as the case maybe, to have access to Haball Digital Interface which then can be changed by you from time to time. The Login Password will be used to identify you whenever you will have access to Haball Digital Interface.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You irrevocably and unconditionally agree and accept as binding any Transaction and/or Instruction made or given through the Haball Digital Interface by you at your own risk and responsibility.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You irrevocably and unconditionally agree and accept that you shall be solely responsible and liable for the accuracy, completeness and authenticity of the Data/Information provided by you.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball's records of any Transaction processed/availed/executed through Haball Digital Interface shall be deemed to be provided by you or the Financial Institution and shall constitute binding and conclusive evidence of such Transaction conducted by you.&nbsp;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>The Instructions given on Haball Digital Interface to process any Transaction cannot be reversed.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Your Telephone conversation(s) with any authorized representative(s) of Haball may be recorded at the discretion of Haball which may <em>inter-alia</em> subsequently be used for presentation in competent courts of law for evidence and other purposes.</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>DEVICES&nbsp;</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><u><span class=\"open-sans-font\" style='font-size:8.0pt;'>Compatibility</span></u><span class=\"open-sans-font\">: Haball Digital Interface is available on following platforms:</span>\n" +
                "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Android Phones &ndash; Android Version 5.0 and above</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Web Browser: IE7 and above,&nbsp;</span><span class=\"open-sans-font\">Google Chrome&nbsp;25</span><span class=\"open-sans-font\" style='font-size:8.0pt;'>&nbsp;and above,&nbsp;</span><span class=\"open-sans-font\">Mozilla Firefox&nbsp;18.0</span><span class=\"open-sans-font\" style='font-size:8.0pt;'>&nbsp;and above, Safari 5.0 and above, Opera Mini 8.0 and above</span></li>\n" +
                "                </ol>\n" +
                "            </li>\n" +
                "            <li><u><span class=\"open-sans-font\" style='font-size:8.0pt;'>In Case Device is Lost</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>:&nbsp;</span>\n" +
                "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You assume full responsibility to inform the mobile phone service provider or law enforcement agency to block the SIM card or terminate the mobile phone number in case of loss or theft of the mobile phone/device and Haball shall not be liable in any way or form by any loss caused thereof.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You undertake to notify Haball in writing or by calling our support immediately in case of loss/theft of your phone/device in order to protect the interest of all parties.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You are solely responsible for protecting the Device and ensuring that the Haball Digital Interface is not accessed / used on a Jail broken and/or rooted Device.</span></li>\n" +
                "                </ol>\n" +
                "            </li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>RESPONSIBILITY AND OBLIGATIONS OF THE CUSTOMER</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You hereby agree that the use of the &ldquo;Login Password&rdquo; generated by you and changes made from time to time by you is adequate for identification of you as an End User on the Haball Digital Interface to execute Transactions. Haball is responsible to act on your Instructions received through Haball Digital Interface without obtaining any further written or other confirmation from you, even if those Instructions are not actually given or authorized by you.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You are solely responsible to follow instructions to avail Haball Digital Interface and to adopt the security protocols and such other guidelines as may be provided by Haball from time to time through notification on Haball Digital Interface, website and/or through other channels such as email/SMS alerts and/or other ways as Haball may feel appropriate in this respect.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You must not allow anyone else to operate the Digital Interface on your behalf. In case of breach of this condition Haball shall not be liable for any loss and/or damage which may consequently occur to you.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You must not leave the Device unattended while you are using the Haball Digital Interface.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You should change your &ldquo;Login Password&rdquo; frequently and shall do so whenever the Haball Digital Interface requires you to do so. You should not choose a login Password you have used before;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Whenever you choose a Login Password, you must take care not to choose password that is likely to be guessed by anyone. You should avoid your own or a relative's name, birth date, home address or any part of your mobile/telephone number;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You must take all reasonable steps to ensure that you safeguard your Login Password at all times. You must not disclose Login Password to anyone else, including a member of Haball's staff, or to someone giving assistance on a technical helpdesk in connection with the Haball Digital Interface;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You must not record/save your Login Password in a way that could make them recognizable by someone else as a password;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>If you discover or suspect that your Login Password or any part of them are known to someone else, you must immediately change the Login Password yourself through the Haball Digital Interface. If this is not possible, you must notify Haball immediately by contacting our support at&nbsp;</span> <span class=\"open-sans-font\" style='font-size:8.0pt;color:windowtext;'>support@haball.pk</span><span class=\"open-sans-font\" style='font-size:8.0pt;'>. The Haball Digital Interface will be suspended until new password has been set up. Please note that Haball will not under any circumstances be held responsible for any unauthorized use of the Haball Digital Interface prior to notification made as provided above;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Never share your passwords or any personal details/information with anyone. In case of breach of this condition, Haball shall not be liable for any dispute, loss, damage which may occur and/or be sustained by you.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>If you become aware of execution of any Transaction on any of your Account(s) that has not been validly done or authorized by you, please notify Haball immediately by calling on +92 21 353 74194 It is hereby advised that you must check your statement/e-statements of Account(s) regularly to reconcile balance amount and to identify and report dubious/un-authorized Transactions (if any) for early action on part of the Parties.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball will not be liable for misuse of your Login Password by any un-authorized person. In this respect, you are liable to take care of the following:</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Comply with all the security procedures/protocols as described in herein and instructions passed on by Haball from time to time in this respect;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Notify Financial Institution that your Login Password (or any one of them) are or might be known to someone else prior to the execution of unauthorized Transactions.&nbsp;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You will be held liable and Haball disclaims liability for all losses and expenses due to unauthorized use/access of the Haball Digital Interface if you have acted negligently, fraudulently or in collusion with others attempted to breach the security protocols of Haball.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You will be held liable and Haball disclaims liability for all losses and expenses arising out of the creation of a Sub-User granting access to Haball's Digital Interface and to your Account. You are solely liable to ensure that each Sub-User granted such access to the Digital Interface or your Account shall at all times strictly comply with the terms and conditions of this EULA and shall be bound by it.</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>HABALL'S AUTHORITY &nbsp;</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You must not use the Haball Digital Interface to create an unauthorized overdraft on any of your Accounts and Haball is entitled to refuse to accept any such Instructions that you will pass on in this respect. If an unauthorized overdraft is created, Haball may take appropriate action as it may deem fit and in case such Transaction is passed on due to any reason whatsoever, Haball shall reserve the right to cancel or charge any mark- up, damages and charges to the account in question.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball has the absolute right to change/revise/amend/modify the terms and conditions contained herein at any time by giving you notice either in writing or by placing prominent notices on the Haball Digital Interface or Haball's website for a period of fifteen (15) days or by sending you a message via SMS and/or Email, as the case may be.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball will give you fifteen (15) days' notice of any change before it takes effect, except when notice is to be shorter in order to protect the security of the service or in other circumstances beyond our control.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>If any part of terms and conditions stipulated herein are proved to be legally incompetent or unenforceable in any way, the other terms and conditions shall remain valid. Haball in its sole discretion may relax and/or waive one or more conditions contained herein on case to case basis however, such relaxation/waiver shall not apply as precedent for similar or other circumstances thereafter.</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>HABALL'S LIMITED RESPONSIBILITIES&nbsp;</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball shall not be responsible for any of the following:</span>\n" +
                "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>To reverse any Instruction given through the Haball Digital Interface.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>To accept any Instruction which is conditional, or which require Financial Institution to make payment to a third party earlier (or later) than the time stipulated by Bank / Financial Institutions / PSO/PSPs required according to normal banking practice.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball, in its sole discretion, will be entitled to refuse to carry out an Instruction submitted through the Haball Digital Interface.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Transfers of funds will not be permitted against un-cleared funds.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You agree to hold harmless and indemnify Haball, its officers, executives, employees, management and Board of Directors against any loss, cost, damage, expense, liability or proceedings which you may incur or suffer as a result of Haball acting upon or delaying to act upon or refraining from acting upon your Instructions.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>When you give an Instruction through the Haball Digital Interface, Haball will act on that Instruction in accordance with the cut-off times notified to Haball by Banks / Financial Institutions/ PSO/PSPs. From time to time Haball may notify you of any changes to these cut-off times. Instructions given at any other time may not be acted on until the next Business Bay. Any delay may happen due to unforeseen events or Force Majeure conditions and circumstances which are beyond the reasonable control of Haball and Haball disclaims liability of any nature whatsoever arising due to the result of Force Majeure.&nbsp;</span></li>\n" +
                "                </ol>\n" +
                "            </li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>REFUSAL TO CARRY OUT INSTRUCTIONS&nbsp;</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball may, when it believes is justified in doing so:</span>\n" +
                "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Refuse to carry out an Instruction given via the Haball Digital Interface; or</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Require written confirmation from you of a particular Instruction.</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>If Haball believes that an Instruction to execute certain Transaction has not been authorized by you then, it will be entitled to take all reasonable measures to check the validity of such Transactions and upon suspicion, Bank / Financial Institution / PSO/PSP may reverse any action taken on the basis of that Instruction. It is possible that due to precaution of Haball, Bank / Financial Institution / PSO/PSP, your authorized Transactions may suffer for which you will also indemnify and hold harmless Haball against all legal and regulatory actions. This is one of the conditions precedents to avail the Haball Digital Interface. Moreover, Haball will not be responsible for any direct/indirect loss to you that may result from such a reversal or action. You agree to be responsible for any costs which Haball may incur as a result.</span></li>\n" +
                "                </ol>\n" +
                "            </li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span style=\"font-size:8.0pt;;\">OPERATING TIMES, CHANGES AND DISRUPTIONS</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball shall take reasonable steps to provide you the Haball Digital Interface appropriately. You accept, that routine maintenance requirements, system glitches, network failures, excessive traffic on system, Force Majeure conditions and circumstances beyond our control may mean it is not always possible for the Haball Digital Interface to be available during its normal operating hours.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>In connection with the Haball Digital Interface, Haball is entitled at any time to:</span>\n" +
                "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Add to, remove or otherwise change, and or suspend any of the facilities available;&nbsp;</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Suspend the Haball Digital Interface;</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>End the Haball Digital Interface;</span></li>\n" +
                "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball will not be responsible if you are unable to gain access and/or use Haball Digital Interface due to reasons beyond Haball's control, including with limitation, any Mobile phone telecommunication, electrical, technical or network failure or malfunction and routine maintenance/update requirements etc.</span></li>\n" +
                "                </ol>\n" +
                "            </li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>PROTECTING AGAINST VIRUSES</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You must take all reasonable measures to ensure that any Device from which you have access to the Haball Digital Interface is free of any viruses etc. and is adequately maintained in every way.&nbsp;</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You must ensure that any Device you use to access the Haball Digital Interface is adequately protected against acquiring Viruses.</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>ACCESSING HABALL DIGITAL INTERFACE THROUGH THIRD PARTY SERVICES</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball cannot be held responsible for any services through which you access the Haball Digital Interface that are not controlled by Haball, or for any loss you may suffer as a result of using such third party service. You must comply with all the terms and conditions and responsible to pay all the charges, damages etc. connected with it.</span></li>\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>If you access the Haball Digital Interface from outside Pakistan, you are responsible for complying with the local laws of that jurisdiction.</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n" +
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>COMPENSATION BY CUSTOMER FOR BREACH OF THE TERMS AND CONDITIONS</span></strong>\n" +
                "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>It is your responsibility to compensate Haball for any loss and/or damages and/or claim it may suffer as a result of you breaching any term and conditions contained hereunder.</span></li>\n" +
                "        </ol>\n" +
                "    </li>\n";
        String myData1 =
                "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>ENDING YOUR USE OF HABALL DIGITAL INTERFACE</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Cancelling the Services</span>\n" +
                        "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You may cancel the use of the Haball Digital Interface at any time by giving written notice to Haball or contacting our support at&nbsp;</span><span class=\"open-sans-font\">support@haball.pk</span><span class=\"open-sans-font\">.</span> <span class=\"open-sans-font\" style='font-size:8.0pt;'>You can also be deactivated and /or suspended from using Haball Digital Interface, or any specific feature of it thereof, on the request of the Onboarding Party. Such cancellation will not affect any pervious services availed by you and financial consideration involved therein.</span></li>\n" +
                        "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball reserves the absolute right at any time to cancel the License to use of the Haball Digital Interface with or without assigning any reason, with or without giving any prior notice to you, at Haball's discretion.</span></li>\n" +
                        "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>If your use of the Haball Digital Interface comes to an end for any reason, this will not affect any Instructions you have already given via the Haball Digital Interface.</span></li>\n" +
                        "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Please note that your request to cancel the License to the use of the Haball Digital Interface will take some procedural time and all Transactions to be executed before complete closure/cancellation of the Haball Digital Interface shall be settled by you without any exception or making any excuse that you had notified to cancel the same.</span></li>\n" +
                        "                </ol>\n" +
                        "            </li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>TRANSACTIONAL CHARGES</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You agree that:</span>\n" +
                        "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You will be charged fees for the Transaction which shall be as per Haball's schedule of charges available on the Haball Digital Interface and agreed with the Onboarding Party; and</span></li>\n" +
                        "                    <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Haball can change those fees and charges from time to time by giving a fifteen (15) Business Days' notice to that effect or by effecting such change through Haball's schedule of charges.</span></li>\n" +
                        "                </ol>\n" +
                        "            </li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\" style='font-size:8.0pt;'>COMMUNICATIONS</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Communication between You &amp; Haball</span></li>\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Except for situations where these EULA Terms and Conditions refer to you for giving us notice by telephone, you should give us any other formal notice in connection with the Transaction in writing to Haball's support staff (or any other address Haball may notify to you from time to time for this purpose).</span></li>\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You further authorize Haball to act on the verbal instructions communicated to a representative of Haball over the telephone. Haball reserves the absolute right to verify your identity over the telephone. You will be liable for any and all Instructions made after the standard verification by Haball's representative and will not hold Haball responsible for acting upon such Instructions.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>Any complaints in connection with the Haball Digital Interface should be directed to any of Haball's support staff (or any other address as Haball may notify to you from time to time for this purpose).</span></li>\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>If Haball needs to send you a notice, the address provided in the profile during registration connection with any of your Account will be used.</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\">CONSENTS</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\">You hereby acknowledge, agree and consent to the fact that Haball, in connection with the Haball Digital Interface, will access, collect, use, process, adapt, store, transfer and delete Information (including without limitation Account Information/Data) in accordance with Haball's Privacy Policy.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">You hereby acknowledge, agree and consent that Haball has the right to make anonymized data based on or derived from your Information and combine the anonymized data with that of other users of the Haball Digital Interface in a way that does not identify you or any individual person, and to use such anonymized aggregate data, both during the term of this EULA and after its termination, for any purpose, including but not limited to:</span>\n" +
                        "                <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "                    <li><span class=\"open-sans-font\">providing, maintaining, supporting and improving the Haball Digital Interface;</span></li>\n" +
                        "                    <li><span class=\"open-sans-font\">conducting analytical research, compiling statistical reports and performance tracking;</span></li>\n" +
                        "                    <li><span class=\"open-sans-font\">developing and/or improving other Haball's services and products; and</span></li>\n" +
                        "                    <li><span class=\"open-sans-font\">sharing such anonymized aggregate data with Haball's affiliates, agents or other third parties with whom Haball has a business relationship.</span></li>\n" +
                        "                </ol>\n" +
                        "            </li>\n" +
                        "            <li><span class=\"open-sans-font\">You acknowledge and agree that Haball will collect, use, process and store your Information for the purpose of this EULA.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">You expressly authorize Haball, in connection with the EULA, to use the Haball Digital Interface and certain Information in order to: (i) collect your Account Data; (ii) reformat, organize, structure, alter and adapt certain Information; (iii) create and provide hypertext links to Financial Institution(s); (iv) update and maintain the information in &nbsp;Account created in connection with this EULA, including performing offline updates (without Licensee's interaction); (v) address errors or service interruptions; (vi) enhance the type of data and services Haball can provide in the future; and (vii) take such other actions as are reasonably necessary to perform the actions described.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">BY GIVING YOUR CONSENT AND USING THE HABALL DIGITAL INTERFACE YOU REPRESENT AND WARRANT THAT YOU ARE THE LEGAL OWNER OF THE ACCOUNT DATA AND INFORMATION AND THAT YOU HAVE THE AUTHORITY TO APPOINT AND DO HEREBY EXPRESSLY APPOINT HABALL AS YOUR AGENT WITH LIMITED POWER OF ATTORNEY TO ACCESS YOUR ACCOUNT DATA AND INFORMATION IN READ-ONLY MODE IN ORDER TO RETRIEVE THE ACCOUNT DATA AND INFROMATION ON YOUR BEHALF AND/OR ENABLE HABALL TO INITIATE TRANSACTIONS ON YOUR BEHALF THROUGH THE HABALL DIGITAL INTERFACE.&nbsp;</span></li>\n" +
                        "            <li><span class=\"open-sans-font\" style='font-size:8.0pt;'>You acknowledge that Haball does not review or analyze the Your Account Data and/or Information and you agree that Haball is not responsible for its completeness or accuracy. If any Transactions or activities performed in any Financial Institution are not made through the Digital Interface, Haball assumes no responsibility for such Transactions or activities. You acknowledge that Haball may use the Haball Digital Interface to initiate payment Transactions on your behalf via the Haball Digital Interface, in which case Haball will transmit the Transaction related information and data corresponding to the initiated payment Transaction from the Haball Digital Interface to your respective Financial Institution. You agree that it is solely responsible for any changes to your Account and/or Data, and that any such changes must be reflected in the respective Financial Institution.&nbsp;</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\">CONFIDENTIALITY</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\">Each party acknowledges that it may have access to Confidential Information of the other party in connection with this EULA, and that each party's Confidential Information is of substantial value to the Disclosing Party, which could be impaired if it were improperly disclosed to third parties or used in violation of this EULA.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">Each Recipient of Confidential Information under this EULA must: (a) keep the Disclosing Party's Confidential Information confidential and protect it at least to the same extent it protects its own Confidential Information and to the same extent that a reasonable person would protect such Confidential Information; (b) not use the Disclosing Party's Confidential Information in any way for its own account or the account of any third party except to perform its duties, exercise its rights or is otherwise authorized under this EULA; and (c) not disclose the Disclosing Party's Confidential Information except to perform its duties or exercise its rights under this EULA or as otherwise authorized under this EULA, provided that: (i) any disclosure made to the Recipient's employees, contractors or agents is on a need-to-know basis; and (ii) the Recipient's employees, contractors or agents in receipt of the Confidential Information are under an obligation of confidentiality no less stringent than that set forth in this section.&nbsp;</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">Notwithstanding the restrictions in Section 18.2, if the Recipient is required to disclose any of the Disclosing Party's Confidential Information by law, such as in response to a subpoena or requirement of any regulator, court, arbitral, administrative, or legislative body, the Recipient must: (a) where reasonably possible and permitted, immediately provide written notice to the Disclosing Party of the required disclosure to give the Disclosing Party an opportunity to move for a protective order or otherwise prevent the disclosure; (b) disclose only the minimum amount of Confidential Information required to satisfy the legal obligation; and (c) assert and take proper steps with the body requiring disclosure to maintain the confidentiality of the Confidential Information to be disclosed.&nbsp;</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">You will immediately, and at least within seventy-two (72) hours, notify Haball if Confidential Information of Haball is used or disclosed in breach of this EULA. As monetary damages may not be sufficient relief if anyone violates or threaten to violate the terms of this section, Haball is immediately entitled to enforce its rights by specific performance or injunction proceedings, in addition to any other rights or remedies it may have.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">Upon the Disclosing Party's request and upon termination of this EULA (unless agreed otherwise by the parties at the time), each party will return, destroy or delete permanently (at the Disclosing Party's election) the other party's Confidential Information.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">On termination of this EULA, the Recipient must continue to keep the Disclosing Party's Confidential Information confidential for five (5) years in accordance with this section.</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\">INTELLECTUAL PROPERTY RIGHTS</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\">The Haball Digital Interface, including its object code and source code, whether or not provided to you, is Confidential Information of Haball. Haball (or its licensors) owns exclusively and reserves all rights, title and interest in and to the Haball Digital Interface and any other related or ancillary products and Documentation, including all Intellectual Property Rights as well as any Derivative Works. You may not exercise any right, title and interest in and to the Haball Digital Interface and associated documentation or any related Intellectual Property Rights, except for the limited usage rights granted to you in this EULA. You hereby agree that you will take no action inconsistent with Haball's Intellectual Property Rights.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">This EULA is not an agreement of sale, and does not transfer any title, Intellectual Property Rights or ownership rights to Haball Digital Interface or any associated documentation to You. You hereby acknowledge and agree that the Haball Digital Interface, and associated documentation and all ideas, methods, algorithms, formulae, processes and concepts used in developing or incorporated into the Haball Digital Interface or associated documentation, all future updates and upgrades, and all other improvements, revisions, corrections, bug-fixes, hot- fixes, patches, modifications, enhancements, releases, DATs, signature sets, upgrades, and policy and database updates and other updates in, of, or to Haball Digital Interface or associated documentation, as applicable, all Derivative Works based on any of the foregoing, and all copies of the foregoing are trade secrets and proprietary property of Haball, having great commercial value to Haball.</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\">DISCLAIMER OF WARRANTY.</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\">HABALL DIGITAL INTERFACE IS PROVIDED ON AN &ldquo;AS IS&rdquo; BASIS. TO THE EXTENT PERMITTED BY LAW, HABALL MAKES NO OTHER REPRESENTATIONS OR WARRANTIES OF ANY KIND REGARDING THE HABALL DIGITAL INTERFACE AND DISCLAIMS ALL OTHER OBLIGATIONS AND LIABILITIES, OR EXPRESS OR IMPLIED WARRANTIES REGARDING THE HABALL DIGITAL INTERFACE, INCLUDING IMPLIED WARRANTIES OF MERCHANTABILITY, QUALITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE, NON-INFRINGEMENT, OR SYSTEMS INTEGRATION. HABALL MAKES NO WARRANTY, REPRESENTATION OR GUARANTEE AS TO THE HABALL DIGITAL INTERFACE'S USE OR PERFORMANCE, OR THAT THE OPERATION OF HABALL DIGITAL INTERFACE WILL BE FAIL- SAFE, UNINTERRUPTED FREE FROM ERRORS OR DEFECTS; OR THAT THE HABALL DIGITAL INTERFACE WILL PROTECT AGAINST ALL POSSIBLE THREATS.&nbsp;</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\">LIMITATION OF LIABILITY.&nbsp;</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\">HABALL WILL NOT BE LIABLE FOR ANY CONSEQUENTIAL DAMAGES IN CONNECTION WITH THIS EULA, EVEN IF THE DAMAGES WERE FORESEEABLE OR A PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF THOSE DAMAGES. THIS LIMITATION OF LIABILITY APPLIES WHETHER SUCH CLAIMS ARISE UNDER CONTRACT, TORT (INCLUDING NEGLIGENCE), EQUITY, STATUTE OR OTHERWISE. NOTHING IN THIS EULA LIMITS OR EXCLUDES ANY LIABILITY THAT CANNOT BE LIMITED OR EXCLUDED UNDER APPLICABLE LAW.</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">WITHOUT PREJUDICE TO THE FOREGOING, IN ANY EVENT THE LIABILITY OF HABALL, FOR ANY CLAIM WITH RESPECT TO A PARTICULAR TRANSACTION SHALL BE LIMITED TO THE LOWEST OF (AND NOT EXCEED) 1% OF THE HABALL'S TRANSACTIONAL INCOME FOR MONTH IN WHICH SUCH CLAIM OCCURRED.&nbsp;</span></li>\n" +
                        "            <li><span class=\"open-sans-font\">HABALL'S MAXIMUM AGGREGATE LIABILITY FOR CLAIMS IN EACH COMPLETED YEAR OF THIS AGREEMENT SHALL NOT EXCEED 1% OF THE HABALL'S TRANSACTIONAL INCOMCE FOR THE TWELVE (12) MONTHS PRECEDING THE CLAIM.</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "</ol>\n" +
                        "<p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "<ol class=\"decimal_type\" style=\"list-style-type: undefined;margin-left:-0.25in;\">\n" +
                        "    <li><strong><span class=\"open-sans-font\">INDEMNIFICATION</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><span class=\"open-sans-font\">You will unconditionally indemnify, defend and hold harmless Haball its affiliates, and their officers, directors, employees, contractors and agents (each a Haball Indemnified Party) against any claims, liabilities and expenses (including court costs and reasonable attorney fees) that a Haball Indemnified Party incurs as a result of or in connection with: (a) any third-party claims arising from: (i) Your failure to obtain any consent, authorization or license required for Haball Digital Interface's use of data, software, materials, systems, networks or other technology provided by You under this EULA; (ii) Your use of Haball Digital Interface in a manner not expressly permitted by this EULA; (iii) Haball's compliance with any technology, designs, instructions or requirements provided by You or a third party on your behalf; (iv) any claims, costs, damages and liabilities whatsoever asserted by any of your Representative; or (v) any violation by You of applicable laws; and (b) any reasonable costs and attorneys' fees required for Haball to respond to a subpoena, court order or other official government inquiry regarding Your use of Haball Digital Interface.</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "    <li><strong><span class=\"open-sans-font\">GENERAL PROVISIONS</span></strong>\n" +
                        "        <ol class=\"decimal_type\" style=\"list-style-type: undefined;\">\n" +
                        "            <li><u><span class=\"open-sans-font\">Relationship</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: The parties are independent contractors under this EULA and expressly disclaim any partnership, franchise, joint venture, agency, employer/employee, fiduciary or other special relationship. Neither party intends this EULA to benefit or create any right or cause of action in or on behalf of, any person or entity other than the parties and listed Affiliates. This EULA is not intended to create a third-party beneficiary of any kind. You must not represent to any third party that it has any right to bind Haball in any manner and You will not to make any representations or warranties on behalf of Haball.</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Severability</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: If a court holds that any provision of this EULA is invalid or unenforceable under applicable law, the court will modify the provision to the minimum extent necessary to make it valid and enforceable or, if it cannot be made valid and enforceable, the court will sever and delete the provision from this EULA. The change will affect neither the validity of the amended provision nor the validity of any other provision of this EULA, which will continue in full force and effect.</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">No waiver</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: A party's failure or delay in enforcing any provision of this EULA will not operate as a waiver of the right to enforce that provision or any other provision of this EULA at any time. A waiver of any provision of this EULA must be in writing, specify the provision to be waived and signed by the party agreeing to the waiver.</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Force Majeure</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: (a) Neither Party will be liable for any losses or delays or interruption of its performance of obligations under the Agreement not a result of its default but due to any acts of God, acts of civil or military authorities, civil disturbances, wars, strikes or other labor disputes, fires, transportation contingencies, interruptions in telecommunications, utility, Internet services or network provider services, acts or omissions of a third party, infiltration or disruption of the Haball's Digital Interface or the Transaction by a third party by any means, including without limitation, distributed denial-of-service attacks, software viruses, Trojan horses, worms, time bombs or any other software program or technology designed to disrupt or delay the Transaction, or other catastrophes or any other occurrences which are beyond such Parties' reasonable control (each a &ldquo;Force Majeure Event&rdquo;), provided that the Party delayed will promptly provide the other Party notice of any such delay or interruption and will use commercially reasonable efforts to minimize any delays or interruptions resulting from the Force Majeure Event and in no event will any failure to pay any monetary sum due under this Agreement be excused for any Force Majeure Event. If an Force Majeure Event uninterruptedly continues for a period of thirty (30) days, the Party not affected by the Force Majeure but by the non-performance of the Party affected by the Force Majeure may terminate this Agreement with a written notice of thirty (30) days to the other Parties.; (b) Haball's failures or delays in its performance are excused to the extent they result from: (i) Your acts or omissions, or those of your employees, agents, users, affiliates or contractors; (ii) notwithstanding the generality of Section 9.4(b)(i), Your failure or delay in the performance of a specific task, obligation or responsibility under this EULA or a Schedule, which task, obligation, or responsibility is a condition or requirement for a Haball's task, obligation, or responsibility; (iii) reliance on instructions, authorizations, approvals or other information from You; or (iv) acts or omissions of third parties (unless directed by Haball).</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Governing law</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: All disputes arising out of or relating to this EULA or its subject matter will be governed by the laws of the Islamic Republic of Pakistan.&nbsp;</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Jurisdiction</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: The courts of Karachi Pakistan shall exclusively have exclusive jurisdiction over all disputes arising out of or relating to this EULA or its subject- matter:</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Entire Agreement, order of precedence and amendments:&nbsp;</span></u><span class=\"open-sans-font\">(a) This EULA constitutes the entire understanding between Haball, and You relating to its subject-matter and supersede all oral or written proposals, and all communications between the parties relating to its subject-matter. This EULA, including all document incorporated by reference, notwithstanding any variance with any other written instrument submitted by You, whether or not expressly rejected by Haball; (b) Haball reserves the right to amend any terms of this EULA at any time. Any amendment will be effective on the posting of an updated version at Haball's Digital Interface..</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Notices</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: Any notice given under or in relation to this EULA must be in writing, signed by or on behalf of the party giving it, and addressed to the relevant entity.&nbsp;</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Assignment</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: You may not sublicense, assign or transfer its rights under this EULA without Haball's prior written consent. Any attempt by You to sublicense, assign or transfer any of its rights, duties or obligations under this EULA, whether directly, or indirectly by merger, acquisition or change of control, will be null and void.</span></li>\n" +
                        "            <li><u><span class=\"open-sans-font\">Survival</span></u><span class=\"open-sans-font\" style='font-size:8.0pt;'>: The following sections, together with any other terms necessary for the interpretation or enforcement of this EULA, will survive termination of this EULA: 17 (Consent) 18 (Confidentiality), 19 (Intellectual Property Rights), 20 (Disclaimer of Warranty), 21 (Limitation of liability), 22 (Indemnification), 23.5 (Governing law), 23.6 (Jurisdiction), and this Section 23.10 (Survival).</span></li>\n" +
                        "        </ol>\n" +
                        "    </li>\n" +
                        "</ol>\n" +
                        "<p class=\"open-sans-font\" style='margin-right:0in;margin-left:.25in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "<p class=\"open-sans-font\" style='margin-right:0in;margin-left:.25in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:center;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>INTERPRETATION</span></strong></p>\n" +
                        "<p class=\"open-sans-font\" style='margin-right:0in;margin-left:.25in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "<table style=\"width:2.7in;margin-left:.25in;border-collapse:collapse;border:none;\">\n" +
                        "    <tbody>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>Account&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: 1pt solid windowtext;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-image: initial;border-left: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>means the interchangeable terminology with respect to the following:&nbsp;</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "                <div style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\">\n" +
                        "                    <ol start=\"1\" style=\"margin-bottom:0in;list-style-type: lower-alpha;margin-left:-0.25in;\">\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" style='font-size:8.0pt;'>your account opened by Haball at its end at the time of your registration on to the Haball Digital Interface;&nbsp;</span></li>\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" style='font-size:8.0pt;'>your bank account a current deposit, saving deposit, or any other account maintained by you in a Financial Institution in which credits and debits may be affected;&nbsp;</span></li>\n" +
                        "                    </ol>\n" +
                        "                </div>\n" +
                        "                <p class=\"open-sans-font\" style='margin-right:0in;margin-left:.25in;font-size:11px;margin-top:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span style='font-size:11px;'>&ldquo;Account Data&rdquo;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>means data relating to your financial account in Financial Institution including:</span></p>\n" +
                        "                <div style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\">\n" +
                        "                    <ol start=\"1\" style=\"margin-bottom:0in;list-style-type: lower-alpha;margin-left:0.75px;\">\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\">financial account details (including by way of example and without limitation account number, type, currency, balance);</span></li>\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\">transactions details (including by way of example and without limitation transaction amount, date, description, currency); and</span></li>\n" +
                        "                    </ol>\n" +
                        "                </div>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>financial account holder details (including by way of example and without limitation name, address, email, phone number) to the extent that such information is made available, in whole or in part, by the respective Financial Institution.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Consent&rdquo;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>means any freely given, specific, informed and unambiguous indication of Licensee's wishes by which he or she, by a statement or by a clear affirmative action, signifies agreement to the access to Licensee's financial account by Haball on its own behalf for the purpose of retrieving Account Data / Information initiating a payment Transaction via Haball Digital Interface.&nbsp;</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>Data/Information&rdquo;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>means and includes:</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "                <div style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\">\n" +
                        "                    <ol start=\"1\" style=\"margin-bottom:0in;list-style-type: lower-alpha;margin-left:-0.25in;\">\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" style='font-size:8.0pt;'>means any representation of fact, information or concept for processing in an information system including source code or a program suitable to cause an information system to perform a function;</span></li>\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:8.0pt;'>traffic data;</span></li>\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:8.0pt;'>includes text, message, data, voice, sound, database, video, signals, software, computer programmes, any forms of intelligence as defined under the Pakistan Telecommunication (Reorganization) Act, 1996 (xv of 1996) and codes including object code and source code;&nbsp;</span></li>\n" +
                        "                    </ol>\n" +
                        "                </div>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Device&rdquo;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>includes:</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "                <div style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\">\n" +
                        "                    <ol start=\"1\" style=\"margin-bottom:0in;list-style-type: lower-alpha;\">\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:8.0pt;'>physical device or article;&nbsp;</span></li>\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:8.0pt;'>an electronic or visual tool that is not in physical form;&nbsp;</span></li>\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" style='font-size:8.0pt;'>a password, access code or similar data in electronic or other form, by which the whole or any part of an information system is capable of being accessed; or \\</span></li>\n" +
                        "                        <li style='margin:0in;margin-bottom:.0001pt;font-size:11px; ' class=\"open-sans-font\"><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:8.0pt;'>automated, self-executing, adaptive or autonomous devices, programs or information systems;&nbsp;</span></li>\n" +
                        "                    </ol>\n" +
                        "                </div>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>which may be linked to the system of Haball to access the services and/or any other information system and/or&nbsp;</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Digital Interface&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>means an online / digital platform developed by Haball accessible through a Device, through which information is processed by You, Onboarding Party and Haball, Financial Institutions and PSO/PSP for onwards processing of any and all information related to Orders and Transactions.</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n";
        String myData2 =

                "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;End User&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>means &ldquo;You&rdquo; which terminology can be used interchangeably.&nbsp;</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Financial Institution&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>means a legal entity engaged in the business of dealing with financial transactions, including without limitation banks, building societies, credit institutions, payment system providers, loan companies, mortgage companies, investment companies, utilities/bills providers and other financial service providers located within the territorial jurisdiction of Pakistan.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Haball&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>means as defined in the Important Notice.&nbsp;</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Instructions&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>means any instruction given by you through the Institutional Aggregator's Digital Solution.&nbsp;</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Login Password&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>The password which is used for login to access Haball's Web Portal and Digital Interface collectively called &ldquo;Digital Interface&rdquo; to confirm customer's identity. First-time login password is system generated and thereafter customer may change login<strong>&nbsp;</strong>password from time to time.</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;One Time Password&rdquo;&nbsp;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span style=\"font-size:11px;\">A secret code which is used for identification of a genuine End User in financial (sent via email) &amp; Non-financial transactions (sent via SMS &amp; Email).</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Onboarding Party&rdquo;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>The Party who has created, or instructed Haball to create, Your Account.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Password&rdquo;&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>Login Password and/or One-Time Password used for financial and non-financial transactions.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;PSO/PSP&rdquo;<br>&nbsp;&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>means such Authorized Party that is a company registered under Companies Ordinance 1984 and is engaged in operating and/or providing Payment Systems related services like electronic payment gateway, payment scheme, clearing house, ATM Switch, POS Gateway, E-Commerce Gateway etc. acting as an intermediary for multilateral routing, switching and processing of payment transactions.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Payment Instrument&rdquo;</span></strong></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>means any instrument or instruction, whether tangible or intangible, acceptable by the Financial Institution that enables you to make payment; but excludes Payment Instruments prescribed in Negotiable Instrument Act, 1881(XXVI of 1881);</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Services&rdquo;&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>All the facilities including but not limited to financial and non-financial activities provided through Haball's Digital Interface.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>Sub-User</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>Any representative of the customer authorized by You to access the Account on Your behalf</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Transaction&rdquo;&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>means any payment transaction effected or to be effected by you through Haball's Digital Solution, by the use of a valid Payment Instrument, which will result in the Financial Institution charging the your Payment Instrument, after authorization by the Financial Institution, for the payment to be made, as contemplated by the Instructions.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;Transaction Report&rdquo;&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;text-align:justify;'><span class=\"open-sans-font\" style='font-size:11px;'>References and details of any Transactions held being reflected on Haball's Digital Interface.</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&ldquo;You&rdquo;/ &ldquo;you&rdquo;</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>the user of the digital interface with a valid Account</span></p>\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "        <tr>\n" +
                        "            <td style=\"width: 93.35pt;border-right: 1pt solid windowtext;border-bottom: 1pt solid windowtext;border-left: 1pt solid windowtext;border-image: initial;border-top: none;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>Haball's Transactional Income</span></strong></p>\n" +
                        "            </td>\n" +
                        "            <td style=\"width: 101.05pt;border-top: none;border-left: none;border-bottom: 1pt solid windowtext;border-right: 1pt solid windowtext;padding: 0in 5.4pt;vertical-align: top;\">\n" +
                        "                <p class=\"open-sans-font\"  style='margin-right:0in;margin-left:0in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><span class=\"open-sans-font\" style='font-size:11px;'>Haball's income per transaction after deduction of revenue share between Financial Institutions, PSO/PSP and Onboarding Party, where applicable, along with deduction of any and all applicable taxes</span></p>\n" +
                        "            </td>\n" +
                        "        </tr>\n" +
                        "    </tbody>\n" +
                        "</table>\n" +
                        "<p class=\"open-sans-font\" style='margin-right:0in;margin-left:.25in;font-size:11px;margin:0in;margin-bottom:.0001pt;'><strong><span class=\"open-sans-font\" style='font-size:11px;'>&nbsp;</span></strong></p></body></html>";

        webView.loadData(String.format(htmlText, (myData + myData1 + myData2)), "text/html", "utf-8");


        return root;
    }


    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener

                    SharedPreferences tabsFromDraft = getContext().getSharedPreferences("OrderTabsFromDraft",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                    editorOrderTabsFromDraft.putString("TabNo", "0");
                    editorOrderTabsFromDraft.apply();

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                    fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("null");
                    fragmentTransaction.commit();
                }
                return false;
            }
        });

    }

}
