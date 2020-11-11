package com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter;

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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.haball.Distributor.StatusKVP;
import com.haball.Distributor.ui.retailer.DeleteOrderDraft;
import com.haball.Distributor.ui.retailer.EditOrderDraft;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerViewOrder;
import com.haball.R;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class RetailerOrdersAdapter extends RecyclerView.Adapter<RetailerOrdersAdapter.ViewHolder> {
    private Context context;
    private List<RetailerOrdersModel> OrdersList;
    private HashMap<String, String> OrderStatusKVP;
    private HashMap<String, String> InvoiceStatusKVP;

    public RetailerOrdersAdapter(Context context, List<RetailerOrdersModel> ordersList, HashMap<String, String> orderStatusKVP, HashMap<String, String> invoiceStatusKVP) {
        this.context = context;
        this.OrdersList = ordersList;
        this.OrderStatusKVP = orderStatusKVP;
        this.InvoiceStatusKVP = invoiceStatusKVP;
        Log.i("InvoiceStatusKVP123", String.valueOf(InvoiceStatusKVP));

    }

    @NonNull
    @Override
    public RetailerOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.layout_retailer_orders_dashboard, parent, false);
        return new RetailerOrdersAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrdersAdapter.ViewHolder holder, final int position) {
        if (OrdersList.size() == 3 || OrdersList.size() == 4) {
            if (position == (OrdersList.size() - 1)) {
                //        if (position == 2) {
                Log.i("DebugSupportFilter_In", OrdersList.get(position).getOrderNumber());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 50, 0, 360);
                holder.main_layout_order_box_retailer.setLayoutParams(params);
            }
        }
        holder.tv_heading.setText(OrdersList.get(position).getRetailer());
        holder.tv_order_no_value.setText(OrdersList.get(position).getOrderNumber());

        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTotalAmount()));
        holder.tv_amount.setText("Rs. " + yourFormattedString1);

//        if(paymentsList.get(position).getStatus().equals("0")){
//            holder.tv_status.setText("Paid");
//        }
//        else{
//            holder.tv_status.setText("Unpaid");
//        }
        holder.tv_status.setText(OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()));
        final int finalPosition = position;
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // final PopupMenu popup = new PopupMenu(context, view);
//                Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
//                final PopupMenu popup = new PopupMenu(wrapper, view);
//
//                MenuInflater inflater = popup.getMenuInflater();
//                if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Approved"))
//                    inflater.inflate(R.menu.dist_order_menu, popup.getMenu());
//                else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Cancelled"))
//                    inflater.inflate(R.menu.cosolidate_payment_menu, popup.getMenu());
//                else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Draft"))
//                    inflater.inflate(R.menu.dist_order_draft_menu, popup.getMenu());
//                else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Pending"))
//                    inflater.inflate(R.menu.pending_order_menu, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.consiladate_view:
//                            case R.id.view_payment:
//                            case R.id.orders_view:
//                                viewOrder(position);
//                                break;
//                            case R.id.view_payment_cancel:
//                            case R.id.orders_cancel:
//                                String orderID = OrdersList.get(position).getOrderId();
//                                showConfirmCancelOrderDialog(position);
//                        }
//                        return false;
//                    }
//                });
//                popup.show();
                Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                if (OrdersList.get(position).getOrderStatusValue() != null) {
                    if (OrdersList.get(position).getOrderStatusValue().equals("Draft"))
                        setMenuDraft(popup, position);
                    else if (OrdersList.get(position).getOrderStatusValue().equals("Cancelled"))
                        setMenuCancelled(popup, position);
                    else if (OrdersList.get(position).getOrderStatusValue().equals("Rejected"))
                        setMenuCancelled(popup, position);
                    else if (OrdersList.get(position).getOrderStatusValue().equals("Approved"))
                        setMenuApproved(popup, position);
                    else
                        setMenuAll(popup, position);
                } else if (OrdersList.get(position).getOrderStatus() != null) {
                    if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Draft"))
                        setMenuDraft(popup, position);
                    else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Cancelled"))
                        setMenuCancelled(popup, position);
                    else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Rejected"))
                        setMenuCancelled(popup, position);
                    else if (OrderStatusKVP.get(OrdersList.get(position).getOrderStatus()).equals("Approved"))
                        setMenuApproved(popup, position);
                    else
                        setMenuAll(popup, position);
                }
            }


        });

    }


    private void setMenuDraft(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.dist_order_draft_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_edit:
                        try {
                            editOrderDraft(context, OrdersList.get(position).getOrderId(), OrdersList.get(position).getOrderNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(context, "View Order ID - " + OrdersList.get(position).getOrderNumber(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.orders_delete:
                        try {
                            deleteOrderDraft(context, OrdersList.get(position).getOrderId(), OrdersList.get(position).getOrderNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                                Toast.makeText(context, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void editOrderDraft(Context context, String ID, String OrderNumber) throws JSONException {
        EditOrderDraft editDraft = new EditOrderDraft();
        editDraft.editDraft(context, ID, OrderNumber);
    }

    private void deleteOrderDraft(final Context context, final String ID, final String OrderNumber) throws JSONException {

        Log.i("CreatePayment", "In Dialog");
//            final FragmentManager fm = mContxt.getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        tv_discard.setText("Delete Order");
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to delete this order?");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText("Delete");
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                DeleteOrderDraft deleteDraft = new DeleteOrderDraft();
                try {
                    deleteDraft.deleteDraft(context, ID, OrderNumber);
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

    private void setMenuCancelled(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_view:
                        String ID = OrdersList.get(position).getOrderId();


                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new RetailerViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) context).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrdersList.get(position).getOrderId());
                        editor.putString("Status", OrdersList.get(position).getOrderStatusValue());
                        editor.putString("InvoiceUpload", String.valueOf(OrdersList.get(position).getInvoiceReferenceNumber()));
                        editor.putString("InvoiceStatus", String.valueOf(OrdersList.get(position).getInvoiceStatus()));
                        editor.commit();
                        // Toast.makeText(context, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }


    private void setMenuAll(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.dist_order_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_view:
                        String ID = OrdersList.get(position).getOrderId();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new RetailerViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) context).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrdersList.get(position).getOrderId());
                        editor.putString("Status", OrdersList.get(position).getOrderStatusValue());
                        editor.putString("InvoiceUpload", String.valueOf(OrdersList.get(position).getInvoiceReferenceNumber()));
                        editor.putString("InvoiceStatus", String.valueOf(OrdersList.get(position).getInvoiceStatus()));
                        Log.i("InvoiceStatus_Adapter", String.valueOf(OrdersList.get(position).getInvoiceStatus()));
                        editor.commit();
                        Log.i("order_debug_getID", OrdersList.get(position).getOrderId());
                        Log.i("order_debug_getStatus", OrdersList.get(position).getOrderStatusValue());
                        Log.i("order_debug_InvUpload", String.valueOf(OrdersList.get(position).getInvoiceReferenceNumber()));
                        Log.i("order_debug_InvStatus", "" + OrdersList.get(position).getInvoiceStatus());
                        break;
                    case R.id.orders_cancel:
                        showConfirmCancelOrderDialog(position);
//                                Toast.makeText(context, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void setMenuApproved(PopupMenu popup, final int position) {
        if (OrdersList.get(position).getInvoiceStatus().equals("0") || OrdersList.get(position).getInvoiceStatus().equals("1")) {
            setMenuAll(popup, position);
        } else {
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.orders_view:
                            String ID = OrdersList.get(position).getOrderId();
                            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.main_container, new RetailerViewOrder()).addToBackStack("tag");
                            fragmentTransaction.commit();
                            SharedPreferences OrderId = ((FragmentActivity) context).getSharedPreferences("OrderId",
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = OrderId.edit();
                            editor.putString("OrderId", OrdersList.get(position).getOrderId());
                            editor.putString("Status", OrdersList.get(position).getOrderStatusValue());
                            editor.putString("InvoiceUpload", String.valueOf(OrdersList.get(position).getInvoiceReferenceNumber()));
                            editor.putString("InvoiceStatus", String.valueOf(OrdersList.get(position).getInvoiceStatus()));
                            Log.i("InvoiceStatus_Adapter", String.valueOf(OrdersList.get(position).getInvoiceStatus()));
                            editor.commit();
                            break;
                    }
                    return false;
                }
            });
            popup.show();
        }
    }

    private void showConfirmCancelOrderDialog(final int position) {

        Log.i("CreatePayment", "In Dialog");
//            final FragmentManager fm = context.getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        tv_discard.setText("Cancel Order");
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to cancel this order?");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setText("Cancel Order");
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                cancelOrder(context, OrdersList.get(position).getOrderId(), OrdersList.get(position).getOrderNumber());
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

    private void viewOrder(int finalPosition) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        String Token = sharedPreferences.getString("Login_Token", "");

        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new RetailerViewOrder());
        fragmentTransaction.commit();
        SharedPreferences OrderId = ((FragmentActivity) context).getSharedPreferences("OrderId",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = OrderId.edit();
        editor.putString("OrderId", OrdersList.get(finalPosition).getOrderId());
        editor.putString("Status", OrdersList.get(finalPosition).getOrderStatus());
        editor.putString("InvoiceUpload", String.valueOf(OrdersList.get(finalPosition).getInvoiceReferenceNumber()));
//        Log.i("InvoiceStatusKVP65432", OrdersList.get(finalPosition).getInvoiceStatus());
//        Log.i("InvoiceStatusKVP65432", String.valueOf(InvoiceStatusKVP));
//        Log.i("InvoiceStatusKVP65432", "" + InvoiceStatusKVP.get(OrdersList.get(finalPosition).getInvoiceStatus()));
        editor.putString("InvoiceStatus", String.valueOf(InvoiceStatusKVP.get(OrdersList.get(finalPosition).getInvoiceStatus())));
        editor.commit();

    }

    private void cancelOrder(Context context, String ID, String OrderNumber) {

        RetailerCancelOrder cancelOrder = new RetailerCancelOrder();
        try {
            cancelOrder.cancelOrder(context, ID, OrderNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_heading, tv_order_no_value, tv_status, tv_amount;
        public RelativeLayout main_layout_order_box_retailer;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_order_no_value = itemView.findViewById(R.id.order_no_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn_orders);
            main_layout_order_box_retailer = itemView.findViewById(R.id.main_layout_order_box_retailer);
        }
    }
}
