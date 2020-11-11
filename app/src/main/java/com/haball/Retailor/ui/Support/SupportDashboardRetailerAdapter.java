package com.haball.Retailor.ui.Support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.StatusKVP;
import com.haball.Distributor.ui.support.DeleteSupport;
import com.haball.R;
import com.haball.Retailor.ui.Support.SupportDashboardRetailerModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SupportDashboardRetailerAdapter extends RecyclerView.Adapter<SupportDashboardRetailerAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    Context mContxt;
    Context activity;
    String dashboard, id, pending, createdDate;
    List<SupportDashboardRetailerModel> supportList;
    private String URL_SUPPORT_VIEW = "http://175.107.203.97:4014/api/support/TicketById/";

    public SupportDashboardRetailerAdapter(Activity activity, Context applicationContext, String dashboard, String id, String pending, String createdDate) {
//        this.mContxt = applicationContext;
//        this.activity = activity;
//        this.dashboard = dashboard;
//        this.id = id;
//        this.pending = pending;
//        this.createdDate = createdDate;
    }

    public SupportDashboardRetailerAdapter(Context context, List<SupportDashboardRetailerModel> supportList) {
        this.mContxt = context;
        this.supportList = supportList;
        this.recyclerView = recyclerView;
    }

    @Override
    public SupportDashboardRetailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_support_rv, parent, false);
        return new SupportDashboardRetailerAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final SupportDashboardRetailerAdapter.ViewHolder holder, final int position) {
        Log.i("DebugSupportFilter", supportList.get(position).getIssueType());
        Log.i("DebugSupportFilter_1", String.valueOf(position));
        Log.i("DebugSupportFilter_2", String.valueOf((supportList.size() - 1)));
         if(supportList.size() == 3 || supportList.size() == 4) {
             if (position == (supportList.size() - 1)) {
 //        if (position == 2) {
                 Log.i("DebugSupportFilter_In", supportList.get(position).getIssueType());
                 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                         RelativeLayout.LayoutParams.WRAP_CONTENT,
                         RelativeLayout.LayoutParams.WRAP_CONTENT
                 );
                 params.setMargins(0, 50, 0, 360);
                 holder.main_layout_support_box_retailer.setLayoutParams(params);
             }
         }
        holder.heading.setText(supportList.get(position).getIssueType());
        holder.ticket_id_value.setText(supportList.get(position).getTicketNumber());
//        holder.ticket_id_value.setText(supportList.get(position).getTicketNumber() + "\n\n\n\n1");
        holder.status_value.setText(supportList.get(position).getStatus());
        holder.created_date_value.setText(supportList.get(position).getCreatedDate().split("T")[0]);

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(mContxt, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_items, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_view:
                                supportView(position);
                                break;
                            case R.id.menu_delete:
                                showDeleteTicketDialog(position);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    private void showDeleteTicketDialog(final int position) {
        Log.i("CreatePayment", "In Dialog");
//        final FragmentManager fm = getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(mContxt).create();
        LayoutInflater inflater = LayoutInflater.from(mContxt);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        tv_discard.setText("Delete Ticket");
        Button btn_discard = view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText("Delete");
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to delete this ticket?");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                try {
                    DeleteSupportTicket(supportList.get(position).getID());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    private void supportView(final int position) {
        SharedPreferences SupportId = ((FragmentActivity) mContxt).getSharedPreferences("SupportId",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SupportId.edit();
        editor.putString("SupportId", supportList.get(position).getID());
        editor.commit();

        FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container_ret, new Retailer_Support_Ticket_View());
        fragmentTransaction.commit();
    }

    private void DeleteSupportTicket(String ID) throws JSONException {
        DeleteSupportTicket deleteSupport = new DeleteSupportTicket();
        String response = deleteSupport.DeleteSupportTicket(mContxt, ID);

    }

    @Override
    public int getItemCount() {
        return supportList.size();
//        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView heading, ticket_id_value, status_value, created_date_value;
        public RelativeLayout main_layout_support_box_retailer;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_layout_support_box_retailer = itemView.findViewById(R.id.main_layout_support_box_retailer);
            heading = itemView.findViewById(R.id.heading);
            ticket_id_value = itemView.findViewById(R.id.ticket_id_value);
            status_value = itemView.findViewById(R.id.status_value);
            created_date_value = itemView.findViewById(R.id.created_date_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }


    // private void printErrorMessage(VolleyError error) {
    //     if (mContxt != null) {
    //         if (error instanceof NetworkError) {
    //             Toast.makeText(mContxt, "Network Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ServerError) {
    //             Toast.makeText(mContxt, "Server Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof AuthFailureError) {
    //             Toast.makeText(mContxt, "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ParseError) {
    //             Toast.makeText(mContxt, "Parse Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof NoConnectionError) {
    //             Toast.makeText(mContxt, "No Connection Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof TimeoutError) {
    //             Toast.makeText(mContxt, "Timeout Error !", Toast.LENGTH_LONG).show();
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
    //                 Toast.makeText(mContxt, message, Toast.LENGTH_LONG).show();
    //             } catch (UnsupportedEncodingException e) {
    //                 e.printStackTrace();
    //             } catch (JSONException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    // }
}
