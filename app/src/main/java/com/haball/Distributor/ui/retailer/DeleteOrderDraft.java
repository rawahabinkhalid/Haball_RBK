package com.haball.Distributor.ui.retailer;

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
        import androidx.fragment.app.FragmentTransaction;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.HurlStack;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.haball.Distributor.DistributorDashboard;
        import com.haball.Distributor.ui.home.HomeFragment;
        import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrderDashboard;
        import com.haball.HaballError;
        import com.haball.Loader;
        import com.haball.ProcessingError;
        import com.haball.R;
        import com.haball.Registration.BooleanRequest;
        import com.haball.Retailor.RetailorDashboard;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

public class DeleteOrderDraft {
    public String URL_DELETE_ORDER_DRAFT = "https://175.107.203.97:4013/api/retailerorder/delete/";
    public String DistributorId, Token;
    public Context mContext;
    private FragmentTransaction fragmentTransaction;
    private Loader loader;

    public DeleteOrderDraft() {
    }

    public void deleteDraft(final Context context, String orderId, final String orderNumber) throws JSONException {
        loader = new Loader(context);
        loader.showLoader();
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        if(!URL_DELETE_ORDER_DRAFT.contains(orderId))
            URL_DELETE_ORDER_DRAFT = URL_DELETE_ORDER_DRAFT + orderId;

//        final Context finalcontext = context;
        BooleanRequest request = new BooleanRequest(Request.Method.GET, URL_DELETE_ORDER_DRAFT, null, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                // TODO handle the response
                loader.hideLoader();
//                if(response)
//                    Toast.makeText(context, "Draft for Order # " + orderNumber + " is deleted", Toast.LENGTH_LONG).show();
//                SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                editorOrderTabsFromDraft.putString("TabNo", "1");
//                editorOrderTabsFromDraft.apply();
//
//                Intent login_intent = new Intent(((FragmentActivity) context), DistributorDashboard.class);
//                ((FragmentActivity) context).startActivity(login_intent);
//                ((FragmentActivity) context).finish();

                final Dialog fbDialogue = new Dialog(mContext);
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                tv_pr1.setText("Your Order ID " + orderNumber + " has been deleted successfully.");
                txt_header1.setText("Order Deleted");
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

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard());
                        fragmentTransaction.commit();
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                error.printStackTrace();
                 new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);

    }

}
