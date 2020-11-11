package com.haball.Distributor.ui.support;

        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.SharedPreferences;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;

        import com.android.volley.AuthFailureError;
        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.haball.HaballError;
        import com.haball.ProcessingError;
        import com.haball.R;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.UnsupportedEncodingException;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.Map;

public class DeleteSupport {
//    public String URL_SUPPORT_STATUS_CHANGE = "https://175.107.203.97:4013/api/contact/StatusChange";
    public String URL_SUPPORT_STATUS_CHANGE = "https://175.107.203.97:4013/api/contact/StatusDelete";
    public String DistributorId, Token;
    public Context mContext;
    private String response = "";
    private FragmentTransaction fragmentTransaction;

    public DeleteSupport(){}

    public String DeleteSupportTicket(final Context context, String supportId) throws JSONException {
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        Log.i("DistributorId ", DistributorId);
        Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("Id", supportId);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, URL_SUPPORT_STATUS_CHANGE, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    Log.i("support delete",String.valueOf(result));
                    Log.i("support delete id",String.valueOf(result.get("Id")));
                    final AlertDialog delete_successAlert = new AlertDialog.Builder(context).create();
                    delete_successAlert.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                    WindowManager.LayoutParams layoutParams = delete_successAlert.getWindow().getAttributes();
                    layoutParams.y = 200;
                    layoutParams.x = -70;// top margin
                    delete_successAlert.getWindow().setAttributes(layoutParams);

                    LayoutInflater delete_inflater = LayoutInflater.from(context);
                    View delete_success_alert = delete_inflater.inflate(R.layout.password_updatepopup, null);
                    delete_successAlert.setView(delete_success_alert);
                    TextView tv_pr1, txt_header1;
                    txt_header1 = delete_success_alert.findViewById(R.id.txt_header1);
                    tv_pr1 = delete_success_alert.findViewById(R.id.txt_details);
                    txt_header1.setText("Ticket Deleted");
                    tv_pr1.setText("Your Support Ticket has been deleted successfully.");

                    ImageButton img_delete = (ImageButton) delete_success_alert.findViewById(R.id.image_button);
                    img_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete_successAlert.dismiss();
                            fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new SupportFragment()).addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    delete_successAlert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            delete_successAlert.dismiss();
                            fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new SupportFragment()).addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    delete_successAlert.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(context).add(sr);
        return response;
    }


    private void printErrMessage(VolleyError error) {
        if(error.networkResponse != null && error.networkResponse.data != null) {
            try {
                String message = "";
                String responseBody = new String(error.networkResponse.data, "utf-8");
                JSONObject data = new JSONObject(responseBody);
                Iterator<String> keys = data.keys();
                while(keys.hasNext()) {
                    String key = keys.next();
                    //                if (data.get(key) instanceof JSONObject) {
                    message = message + data.get(key) + "\n";
                    //                }
                }
                //                    if(data.has("message"))
                //                        message = data.getString("message");
                //                    else if(data. has("Error"))
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
