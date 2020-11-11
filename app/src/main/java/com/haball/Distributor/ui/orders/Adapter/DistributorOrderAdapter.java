package com.haball.Distributor.ui.orders.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Distributor.ui.orders.Models.OrderFragmentModel;
import com.haball.R;

import java.text.DecimalFormat;
import java.util.List;

public class DistributorOrderAdapter extends RecyclerView.Adapter<DistributorOrderAdapter.ViewHolder> {

  //  private Orders_Fragment mContext;
    private  String heading="",orderno="",amount="",status="";

    private Context context;
    private List<OrderFragmentModel> orderList;
    public DistributorOrderAdapter(Context requestOrder, String heading, String orderno, String amount, String status){

        this.heading = heading;
        this.context = requestOrder;
        this.orderno = orderno;
        this.amount = amount;
        this.status = status;
    }
//
//    public DistributorOrderAdapter(Context context, List<OrderFragmentModel> orderList) {
//        this.context = context;
//        this.orderList = orderList;
//    }

    @NonNull
    @Override
    public DistributorOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.orders_layout,parent,false);
        return new DistributorOrderAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrderAdapter.ViewHolder holder, int position) {
//
//        if(orderList.get(position).getCompanyName() != null)
//            heading = orderList.get(position).getCompanyName();
//
//        if(orderList.get(position).getCompanyName()!= null)
//            orderno = orderList.get(position).getOrderNumber();
//        if(orderList.get(position).getTotalPrice()!=null)
//            amount = orderList.get(position).getTotalPrice();
//        if(orderList.get(position).getOrderStatusValue()!= null)
//            status = orderList.get(position).getOrderStatusValue();
//
//        holder.tv_heading.setText(heading);
//        holder.order_no_value.setText(orderno);
//        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//        String yourFormattedString1 = formatter1.format(Integer.parseInt(amount));
//        holder.amount_value.setText(yourFormattedString1);
//
////        holder.amount_value.setText(amount);
//        holder.status_value.setText(status);
//
//        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popup = new PopupMenu(context, view);
//                MenuInflater inflater = popup.getMenuInflater();
//                inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.orders_view:
//                                Log.i("text1", "View Item");
//                                Toast.makeText(context,"View Clicked",Toast.LENGTH_LONG).show();
//                                break;
//
//
//                        }
//                        return false;
//                    }
//                });
//                popup.show();
//            }
//        });

//        if(orderList.get(position).getCompanyName() != null)
//            heading = orderList.get(position).getCompanyName();
//
//        if(orderList.get(position).getCompanyName()!= null)
//            orderno = orderList.get(position).getOrderNumber();
//        if(orderList.get(position).getTotalPrice()!=null)
//            amount = orderList.get(position).getTotalPrice();
//        if(orderList.get(position).getOrderStatusValue()!= null)
//            status = orderList.get(position).getOrderStatusValue();

        holder.tv_heading.setText(heading);
        holder.order_no_value.setText(orderno);
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(amount));
        holder.amount_value.setText(yourFormattedString1);

//        holder.amount_value.setText(amount);
        holder.status_value.setText(status);

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PopupMenu popup = new PopupMenu(context, view);
                Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.orders_view:
                                Log.i("text1", "View Item");
                                // Toast.makeText(context,"View Clicked",Toast.LENGTH_LONG).show();
                                break;


                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

    }
    @Override
    public int getItemCount() {
//        return orderList.size();
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading,order_no_value,amount_value,status_value,tv_price;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            order_no_value = itemView.findViewById(R.id.order_no_value);
            amount_value = itemView.findViewById(R.id.amount_value);
            status_value = itemView.findViewById(R.id.status_value);
            menu_btn = itemView.findViewById(R.id.menu_btn_orders);
        }
    }
}
