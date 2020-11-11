package com.haball.Retailor.ui.Dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.R;
import com.haball.Retailor.ui.Make_Payment.PaymentScreen3Fragment_Retailer;
import com.haball.Retailor.ui.RetailerOrder.RetailerViewOrder;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.List;

public class RetailerOrderAdapter extends RecyclerView.Adapter<RetailerOrderAdapter.ViewHolder> {

    //    private PlaceholderFragment mContxt;
    private Context mContxt;
    private String heading, order_no_value, amount, status;
    private List<RetailerOrderModel> OrderList;

    public RetailerOrderAdapter(Context mContxt, List<RetailerOrderModel> orderList) {
        this.mContxt = mContxt;
        this.OrderList = orderList;
    }

//    public RetailerOrderAdapter(PlaceholderFragment placeholderFragment, String heading, String order_no_value, String amount, String status) {
//        this.mContxt = placeholderFragment;
//        this.heading = heading;
//        this.order_no_value = order_no_value;
//        this.amount = amount;
//        this.status = status;
//    }

    @NonNull
    @Override
    public RetailerOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.orders_layout, parent, false);
        return new RetailerOrderAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailerOrderAdapter.ViewHolder holder, final int position) {
         if (OrderList.size() == 3 || OrderList.size() == 4) {
             if (position == (OrderList.size() - 1)) {
 //        if (position == 2) {
                 Log.i("DebugSupportFilter_In", OrderList.get(position).getOrderNumber());
                 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                         RelativeLayout.LayoutParams.WRAP_CONTENT,
                         RelativeLayout.LayoutParams.WRAP_CONTENT
                 );
                 params.setMargins(0, 50, 0, 360);
                 holder.main_layout_order_box_retailer.setLayoutParams(params);
             }
         }

//        holder.tv_heading.setText(heading);
//        holder.order_no_value.setText(order_no_value);
//        holder.tv_status.setText(status);
//        holder.tv_amount.setText(amount);
        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrderList.get(position).getTotalPrice()));
        holder.tv_amount.setText("Rs. " + yourFormattedString1);

        holder.tv_heading.setText(OrderList.get(position).getCompanyName());
        holder.order_no_value.setText(OrderList.get(position).getOrderNumber());
        if (OrderList.get(position).getOrderStatusValue() != null)
            holder.tv_status.setText(OrderList.get(position).getOrderStatusValue());
        else if (OrderList.get(position).getStatus() != null)
            holder.tv_status.setText(OrderList.get(position).getStatus());

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(mContxt, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                if (OrderList.get(position).getOrderStatusValue() != null) {
                    if (OrderList.get(position).getOrderStatusValue().equals("Draft"))
                        setMenuDraft(popup, position);
                    else if (OrderList.get(position).getOrderStatusValue().equals("Cancelled"))
                        setMenuCancelled(popup, position);
                    else if (OrderList.get(position).getOrderStatusValue().equals("Rejected"))
                        setMenuCancelled(popup, position);
                    else if (OrderList.get(position).getOrderStatusValue().equals("Approved"))
                        setMenuAll(popup, position);
                    else
                        setMenuAll(popup, position);
                } else if (OrderList.get(position).getStatus() != null) {
                    if (OrderList.get(position).getStatus().equals("Draft"))
                        setMenuDraft(popup, position);
                    else if (OrderList.get(position).getStatus().equals("Cancelled"))
                        setMenuCancelled(popup, position);
                    else if (OrderList.get(position).getStatus().equals("Rejected"))
                        setMenuCancelled(popup, position);
                    else if (OrderList.get(position).getStatus().equals("Approved"))
                        setMenuAll(popup, position);
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
                            editOrderDraft(mContxt, OrderList.get(position).getID(), OrderList.get(position).getOrderNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(mContxt, "View Order ID - " + OrderList.get(position).getOrderNumber(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.orders_delete:
                        try {
                            deleteOrderDraft(mContxt, OrderList.get(position).getID(), OrderList.get(position).getOrderNumber());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                                Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void setMenuCancelled(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_view:
                        String ID = OrderList.get(position).getID();


                        FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new RetailerViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getStatus());
                        editor.putString("InvoiceUpload", String.valueOf(OrderList.get(position).getInvoiceUpload()));
                        editor.putString("InvoiceStatus", String.valueOf(OrderList.get(position).getInvoiceStatus()));
                        editor.commit();
                        // Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void showConfirmCancelOrderDialog(final int position) {

        Log.i("CreatePayment", "In Dialog");
//            final FragmentManager fm = mContxt.getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(mContxt).create();
        LayoutInflater inflater = LayoutInflater.from(mContxt);
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
                String orderID = OrderList.get(position).getID();
                try {
                    cancelOrder(mContxt, OrderList.get(position).getID(), OrderList.get(position).getOrderNumber());
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

    private void setMenuAll(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.dist_order_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_view:
                        String ID = OrderList.get(position).getID();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new RetailerViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getStatus());
                        editor.putString("InvoiceUpload", String.valueOf(OrderList.get(position).getInvoiceUpload()));
                        editor.putString("InvoiceStatus", String.valueOf(OrderList.get(position).getInvoiceStatus()));
                        Log.i("InvoiceStatus_Adapter", String.valueOf(OrderList.get(position).getInvoiceStatus()));
                        editor.commit();
                        Log.i("order_debug_getID", OrderList.get(position).getID());
                        Log.i("order_debug_getStatus", OrderList.get(position).getStatus());
                        Log.i("order_debug_InvUpload", String.valueOf(OrderList.get(position).getInvoiceUpload()));
                        Log.i("order_debug_InvStatus", "" + OrderList.get(position).getInvoiceStatus());
                        break;
                    case R.id.orders_cancel:
                        showConfirmCancelOrderDialog(position);
//                                Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void setMenuApproved(PopupMenu popup, final int position) {
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_view:
                        String ID = OrderList.get(position).getID();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new RetailerViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getStatus());
                        editor.putString("InvoiceUpload", String.valueOf(OrderList.get(position).getInvoiceUpload()));
                        editor.putString("InvoiceStatus", String.valueOf(OrderList.get(position).getInvoiceStatus()));
                        Log.i("InvoiceStatus_Adapter", String.valueOf(OrderList.get(position).getInvoiceStatus()));
                        editor.commit();
                        break;
                }
                return false;
            }
        });
        popup.show();

    }

    private void cancelOrder(Context context, String ID, String OrderNumber) throws JSONException {
        CancelOrder cancelOrder = new CancelOrder();
        cancelOrder.cancelOrder(context, ID, OrderNumber);
    }

    private void deleteOrderDraft(final Context context, final String ID, final String OrderNumber) throws JSONException {

        Log.i("CreatePayment", "In Dialog");
//            final FragmentManager fm = mContxt.getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(mContxt).create();
        LayoutInflater inflater = LayoutInflater.from(mContxt);
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

    private void editOrderDraft(Context context, String ID, String OrderNumber) throws JSONException {
        EditOrderDraft editDraft = new EditOrderDraft();
        editDraft.editDraft(context, ID, OrderNumber);
    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, order_no_value, tv_status, tv_amount;
        public RelativeLayout main_layout_order_box_retailer;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            order_no_value = itemView.findViewById(R.id.order_no_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn_orders);
            main_layout_order_box_retailer = itemView.findViewById(R.id.main_layout_order_box_retailer);
        }
    }

    public void addListItem(List<RetailerOrderModel> list) {
        for (RetailerOrderModel plm : list) {
            OrderList.add(plm);
        }
        notifyDataSetChanged();
    }
}
