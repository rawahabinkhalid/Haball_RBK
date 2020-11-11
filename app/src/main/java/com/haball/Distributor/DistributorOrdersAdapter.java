package com.haball.Distributor;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Distributor.ui.main.PlaceholderFragment;
import com.haball.Distributor.ui.main.ViewOrder;
import com.haball.Distributor.ui.orders.CancelOrder;
import com.haball.Distributor.ui.orders.DeleteOrderDraft;
import com.haball.Distributor.ui.orders.EditOrderDraft;
import com.haball.Payment.DistributorPaymentRequestModel;
import com.haball.R;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DistributorOrdersAdapter extends RecyclerView.Adapter<DistributorOrdersAdapter.ViewHolder> {

    //    private PlaceholderFragment mContxt;
    private Context mContxt;
    private String heading, order_no_value, amount, status;
    private List<DistributorOrdersModel> OrderList;

    public DistributorOrdersAdapter(Context mContxt, List<DistributorOrdersModel> orderList) {
        this.mContxt = mContxt;
        this.OrderList = orderList;
    }

//    public DistributorOrdersAdapter(PlaceholderFragment placeholderFragment, String heading, String order_no_value, String amount, String status) {
//        this.mContxt = placeholderFragment;
//        this.heading = heading;
//        this.order_no_value = order_no_value;
//        this.amount = amount;
//        this.status = status;
//    }

    @NonNull
    @Override
    public DistributorOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.orders_layout, parent, false);
        return new DistributorOrdersAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrdersAdapter.ViewHolder holder, final int position) {
        if (OrderList.size() == 3 || OrderList.size() == 4) {
             if (position == (OrderList.size() - 1)) {
 //        if (position == 2) {
                 Log.i("DebugSupportFilter_In", OrderList.get(position).getOrderNumber());
                 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                         RelativeLayout.LayoutParams.WRAP_CONTENT,
                         RelativeLayout.LayoutParams.WRAP_CONTENT
                 );
                 params.setMargins(0, 50, 0, 360);
                 holder.main_layout_order_box.setLayoutParams(params);
             }
         }
//        holder.tv_heading.setText(heading);
//        holder.order_no_value.setText(order_no_value);
//        holder.tv_status.setText(status);
//        holder.tv_amount.setText(amount);

        holder.tv_heading.setText(OrderList.get(position).getCompanyName());
        holder.order_no_value.setText(OrderList.get(position).getOrderNumber());
        if (OrderList.get(position).getOrderStatusValue() != null)
            holder.tv_status.setText(OrderList.get(position).getOrderStatusValue());
        else if (OrderList.get(position).getStatus() != null)
            holder.tv_status.setText(OrderList.get(position).getStatus());
        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrderList.get(position).getTotalPrice()));
        holder.tv_amount.setText("Rs. " + yourFormattedString1);
        //holder.tv_amount.setText(OrderList.get(position).getTotalPrice());

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // final PopupMenu popup = new PopupMenu(mContxt, view);
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
                        setMenuApproved(popup, position);
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
                        fragmentTransaction.add(R.id.main_container, new ViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getOrderStatusValue());
                        editor.putString("InvoiceUpload", String.valueOf(OrderList.get(position).getOrderState()));
                        editor.putString("InvoiceStatus", String.valueOf(OrderList.get(position).getOrderStateValue()));
                        editor.commit();

                        // Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
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
                        fragmentTransaction.add(R.id.main_container, new ViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getOrderStatusValue());
                        editor.putString("InvoiceUpload", String.valueOf(OrderList.get(position).getOrderState()));
                        editor.putString("InvoiceStatus", String.valueOf(OrderList.get(position).getOrderStateValue()));
                        editor.commit();

                        // Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
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
                        String ID = OrderList.get(position).getID();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new ViewOrder()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        SharedPreferences OrderId = ((FragmentActivity) mContxt).getSharedPreferences("OrderId",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = OrderId.edit();
                        editor.putString("OrderId", OrderList.get(position).getID());
                        editor.putString("Status", OrderList.get(position).getOrderStatusValue());
                        editor.putString("InvoiceUpload", String.valueOf(OrderList.get(position).getOrderState()));
                        editor.putString("InvoiceStatus", String.valueOf(OrderList.get(position).getOrderStateValue()));
                        editor.commit();
                        break;
                    case R.id.orders_cancel:
                        String orderID = OrderList.get(position).getID();
                        showConfirmCancelOrderDialog(position);
//                                Toast.makeText(mContxt, "View Order ID - " + ID, Toast.LENGTH_LONG).show();
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

    private void cancelOrder(Context context, String ID, String OrderNumber) throws JSONException {
        CancelOrder cancelOrder = new CancelOrder();
        cancelOrder.cancelOrder(context, ID, OrderNumber);
    }


    private void deleteOrderDraft(final Context context, final String ID, final String OrderNumber) throws JSONException {
//        DeleteOrderDraft deleteDraft = new DeleteOrderDraft();
//        deleteDraft.deleteDraft(context, ID, OrderNumber);


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
        public RelativeLayout main_layout_order_box;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            order_no_value = itemView.findViewById(R.id.order_no_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn_orders);
            main_layout_order_box = itemView.findViewById(R.id.main_layout_order_box_retailer);
        }
    }

    public void addListItem(List<DistributorOrdersModel> list) {
        for (DistributorOrdersModel plm : list) {
            OrderList.add(plm);
        }
        notifyDataSetChanged();
    }
}
