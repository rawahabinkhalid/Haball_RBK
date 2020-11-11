package com.haball.Distributor.ui.retailer.Payment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.RetailorDashboard;
import com.haball.SSL_HandShake;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentDeleteOrder {

    public String URL_DELETE_PAYMENT = "http://175.107.203.97:4014/api/prepaidrequests/Delete/";
    public Context context;
    public String invoiceNumber;
    public String RetailerId, Token;
    private Loader loader;


    public PaymentDeleteOrder() {
    }


    public void deleteOrder(final Context context, String invoiceId, final String invoiceNumber) {
        loader = new Loader(context);
        loader.showLoader();
        Log.i("paymentLog", "in delete order");
        Log.i("paymentLog_Error", String.valueOf(invoiceId));


        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        RetailerId = sharedPreferences1.getString("Retailer_Id", "");
        if (!URL_DELETE_PAYMENT.contains("/" + invoiceId))
            URL_DELETE_PAYMENT = URL_DELETE_PAYMENT + invoiceId;
        Log.i("RetailerId ", RetailerId);
        Log.i("Token Retailer ", Token);


        final Context finalcontext = context;
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, URL_DELETE_PAYMENT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle the response
                loader.hideLoader();
                Log.i("paymentLog_Response", String.valueOf(response));
                final Dialog fbDialogue = new Dialog(context);
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                tv_pr1.setText("Your Payment ID " + invoiceNumber + " has been deleted successfully.");
                txt_header1.setText("Payment Deleted");
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
                        SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) context), RetailorDashboard.class);
                        ((FragmentActivity) context).startActivity(login_intent);
                        ((FragmentActivity) context).finish();
                    }
                });
//                fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container, new HomeFragment());
//                fragmentTransaction.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("paymentLog_Error", String.valueOf(error));
                loader.hideLoader();
                new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);
                error.printStackTrace();
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(request);
    }
}
