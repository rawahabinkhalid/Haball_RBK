package com.haball.Support.Support_Ditributor.Adapter;

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
import android.widget.Adapter;
import android.widget.Button;
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
import com.haball.Distributor.ui.main.ViewOrder;
import com.haball.Distributor.ui.support.DeleteSupport;
import com.haball.Distributor.ui.support.MyJsonArrayRequest;
import com.haball.R;
import com.haball.Retailor.ui.Support.SupportFragment;
import com.haball.Support.Support_Ditributor.Model.SupportDashboardModel;
import com.haball.Support.Support_Ditributor.Support_Ticket_View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

public class SupportDashboardAdapter extends RecyclerView.Adapter<SupportDashboardAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    Context mContxt;
    Context activity;
    String dashboard, id, pending, createdDate;
    List<SupportDashboardModel> supportList;
    private FragmentTransaction fragmentTransaction;
//    private String URL_SUPPORT_VIEW = "https://175.107.203.97:4013/api/contact//";

    public SupportDashboardAdapter(Context context, List<SupportDashboardModel> supportList, RecyclerView recyclerView, RecyclerView.Adapter mAdapter) {
        this.mContxt = context;
        this.supportList = supportList;
        this.recyclerView = recyclerView;
        this.mAdapter = mAdapter;
    }

    @Override
    public SupportDashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.layout_support_rv, parent, false);
        return new SupportDashboardAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final SupportDashboardAdapter.ViewHolder holder, final int position) {
        if (supportList.size() == 3 || supportList.size() == 4) {
            if (position == (supportList.size() - 1)) {
                //        if (position == 2) {
                Log.i("DebugSupportFilter_In", supportList.get(position).getId());
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
        holder.status_value.setText(supportList.get(position).getStatus());
        holder.created_date_value.setText(supportList.get(position).getCreatedDate().split("T")[0]);

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PopupMenu popup = new PopupMenu(mContxt, view);
                Context wrapper = new ContextThemeWrapper(mContxt, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_items, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_view:
                                SharedPreferences SupportId = ((FragmentActivity) mContxt).getSharedPreferences("SupportId",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = SupportId.edit();
                                editor.putString("SupportId", supportList.get(position).getId());
                                editor.commit();

                                FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container, new Support_Ticket_View());
                                fragmentTransaction.commit();
//                                if (!URL_SUPPORT_VIEW.contains("//" + supportList.get(position).getId()))
//                                    URL_SUPPORT_VIEW = URL_SUPPORT_VIEW + supportList.get(position).getId();
//                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_SUPPORT_VIEW, null, new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        TextView tv_username, et_email, et_phone, et_issue_type, et_criticality, et_preffered_contact, et_status, et_comments;
////                                        Toast.makeText(mContxt, "View Clicked", Toast.LENGTH_LONG).show();
//                                        final AlertDialog alertDialog = new AlertDialog.Builder(mContxt).create();
//                                        LayoutInflater inflater = LayoutInflater.from(mContxt);
//                                        View view_popup = inflater.inflate(R.layout.view_popup, null);
//                                        alertDialog.setView(view_popup);
//
//                                        tv_username = view_popup.findViewById(R.id.tv_username);
//                                        et_email = view_popup.findViewById(R.id.et_email);
//                                        et_phone = view_popup.findViewById(R.id.et_phone);
//                                        et_issue_type = view_popup.findViewById(R.id.et_issue_type);
//                                        et_criticality = view_popup.findViewById(R.id.et_criticality);
//                                        et_preffered_contact = view_popup.findViewById(R.id.et_preffered_contact);
//                                        et_status = view_popup.findViewById(R.id.et_status);
//                                        et_comments = view_popup.findViewById(R.id.et_comments);
//
//                                        try {
//                                            tv_username.setText(String.valueOf(response.get("ContactName")));
//                                            et_email.setText("Email Address: " + String.valueOf(response.get("Email")));
//                                            et_phone.setText("Phone: " + String.valueOf(response.get("MobileNumber")));
//                                            et_issue_type.setText("Issue Type: " + String.valueOf(response.get("IssueType")));
//                                            et_criticality.setText("Criticality: " + String.valueOf(response.get("Criticality")));
//                                            et_preffered_contact.setText("Preferred Contact Method: " + String.valueOf(response.get("PreferredContactMethod")));
//                                            et_status.setText("Status: " + supportList.get(position).getStatus());
//                                            et_comments.setText("Message: " + String.valueOf(response.get("Description")));
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
//                                        img_email.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                alertDialog.dismiss();
//                                            }
//                                        });
//                                        alertDialog.show();
//
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        new HaballError().printErrorMessage(error);
//
//                                        error.printStackTrace();
//                                        Log.i("onErrorResponse", "Error");
//                                    }
//                                });
//                                Volley.newRequestQueue(mContxt).add(request);


                                break;
//                            case R.id.menu_edit:
//                                //handle menu2 click
//                                Toast.makeText(mContxt,"Edit Clicked",Toast.LENGTH_LONG).show();
//
//                                break;
                            case R.id.menu_delete:
                                showDeleteTicketDialog(position);
                                //handle menu3 click
//                                Toast.makeText(mContxt,"Delete Clicked",Toast.LENGTH_LONG).show();
//                                final AlertDialog deleteAlert = new AlertDialog.Builder(mContxt).create();
//                                LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
//                                View delete_alert = delete_inflater.inflate(R.layout.delete_alert, null);
//                                deleteAlert.setView(delete_alert);
//                                Button btn_delete = (Button) delete_alert.findViewById(R.id.btn_delete);
//                                btn_delete.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//
//                                        try {
//                                            deleteAlert.dismiss();
////                                            DeleteSupportTicket(supportList.get(position).getId());
//                                            DeleteSupport deleteSupport = new DeleteSupport();
//                                            String response = deleteSupport.DeleteSupportTicket(mContxt, supportList.get(position).getId());
////                                            notifyItemRemoved(position);
////                                            notifyItemRangeChanged(position, supportList.size());
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
////                                        final AlertDialog delete_successAlert = new AlertDialog.Builder(mContxt).create();
////                                        LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
////                                        View delete_success_alert = delete_inflater.inflate(R.layout.delete_success, null);
////                                        delete_successAlert.setView(delete_success_alert);
////
////                                        ImageButton img_delete = (ImageButton) delete_success_alert.findViewById(R.id.btn_close_success);
////                                        img_delete.setOnClickListener(new View.OnClickListener() {
////                                            @Override
////                                            public void onClick(View v) {
////                                                delete_successAlert.dismiss();
////                                            }
////                                        });
////                                        delete_successAlert.show();
//                                    }
//                                });
//                                ImageButton img_delete_alert = (ImageButton) delete_alert.findViewById(R.id.btn_close);
//                                img_delete_alert.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        deleteAlert.dismiss();
//                                    }
//                                });
//
//                                deleteAlert.show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }

    private void DeleteSupportTicket(String ID) throws JSONException {
        DeleteSupport deleteSupport = new DeleteSupport();
        String response = deleteSupport.DeleteSupportTicket(mContxt, ID);

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
                    DeleteSupportTicket(supportList.get(position).getId());
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

    @Override
    public int getItemCount() {
        return supportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView heading, ticket_id_value, status_value, created_date_value;
        public RelativeLayout main_layout_support_box_retailer;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            ticket_id_value = itemView.findViewById(R.id.ticket_id_value);
            status_value = itemView.findViewById(R.id.status_value);
            created_date_value = itemView.findViewById(R.id.created_date_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            main_layout_support_box_retailer = itemView.findViewById(R.id.main_layout_support_box_retailer);
        }
    }


    // private void printErrMessage(VolleyError error) {
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
