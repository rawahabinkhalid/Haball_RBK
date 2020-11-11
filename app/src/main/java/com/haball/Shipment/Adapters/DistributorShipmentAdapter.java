package com.haball.Shipment.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import com.haball.Distributor.ui.shipments.DistributorShipment_ViewDashboard;
import com.haball.Distributor.ui.shipments.ShipmentModel;
import com.haball.Distributor.ui.shipments.Shipments_Fragments;
import com.haball.R;

import java.text.DecimalFormat;
import java.util.List;

public class DistributorShipmentAdapter extends RecyclerView.Adapter<DistributorShipmentAdapter.ViewHolder> {
    //
//    private Shipments_Fragments mContext;
    private  String heading="",shipment_no_value="",tv_date="",tv_recv_date="",tv_quantity_value="",tv_option="";
    Context activity;
    private Context context;
    private List<ShipmentModel> shipmentList;


    public DistributorShipmentAdapter(Context context, List<ShipmentModel> shipmentList) {
        this.context = context;
        this.shipmentList = shipmentList;
    }

    public DistributorShipmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.distributorshipment_recycler,parent,false);
        return new DistributorShipmentAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorShipmentAdapter.ViewHolder holder, final int position) {
         if (shipmentList.size() == 3 || shipmentList.size() == 4) {
             if (position == (shipmentList.size() - 1)) {
 //        if (position == 2) {
                 Log.i("DebugSupportFilter_In", shipmentList.get(position).getDeliveryNumber());
                 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                         RelativeLayout.LayoutParams.WRAP_CONTENT,
                         RelativeLayout.LayoutParams.WRAP_CONTENT
                 );
                 params.setMargins(0, 50, 0, 400);
                 holder.main_layout_shipment_box_retailer.setLayoutParams(params);
             }
         }

        if(shipmentList.get(position).getCompanyName() != null)
            heading = shipmentList.get(position).getCompanyName();
        if(shipmentList.get(position).getDeliveryNumber() != null)
            shipment_no_value = shipmentList.get(position).getDeliveryNumber();
        if(shipmentList.get(position).getDeliveryDate() != null)
            tv_date = shipmentList.get(position).getDeliveryDate().split("T")[0];
        if(shipmentList.get(position).getReceivingDate() != null)
            tv_recv_date = shipmentList.get(position).getReceivingDate().split("T")[0];
        if(shipmentList.get(position).getShipmentStatusValue() != null)
            tv_option = shipmentList.get(position).getShipmentStatusValue();
        if(shipmentList.get(position).getQuantity() != null)
            tv_quantity_value = shipmentList.get(position).getQuantity();
        holder.tv_heading.setText(heading);
        holder.shipment_no_value.setText(shipment_no_value);
//        holder.tv_date.setText(tv_date);
//
//        holder.tv_recv_date.setText(tv_recv_date);
//        holder.tv_quantity_value.setText(tv_quantity_value);
        if(shipmentList.get(position).getStatus().equals("0"))
            holder.tv_option.setText("Pending");
        else if(shipmentList.get(position).getStatus().equals("1"))
            holder.tv_option.setText("In Transit");
        else if(shipmentList.get(position).getStatus().equals("2"))
            holder.tv_option.setText("Received");
        else if(shipmentList.get(position).getStatus().equals("3"))
            holder.tv_option.setText("Returned");
        else if(shipmentList.get(position).getStatus().equals("4"))
            holder.tv_option.setText("Revised");
//            holder.tv_option.setText(shipmentList.get(position).getShipmentStatusValue());

//        if(shipmentList.get(position).get().equals("0"))
//            holder.tv_option.setText("Pending");
//        else if(shipmentList.get(position).getShipmentStatusValue().equals("1"))
//            holder.tv_option.setText("Delivered");
//        else if(shipmentList.get(position).getShipmentStatusValue().equals("2"))
//            holder.tv_option.setText("Received");
//        else if(shipmentList.get(position).getShipmentStatusValue().equals("3"))
//            holder.tv_option.setText("Returned");
//        else if(shipmentList.get(position).getShipmentStatusValue().equals("4"))
//            holder.tv_option.setText("Revised");


        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PopupMenu popup = new PopupMenu(context, view);
                Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.cosolidate_payment_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.consiladate_view:
                                //handle menu1 click
//                                Toast.makeText(context,"View Clicked",Toast.LENGTH_LONG).show();
                                SharedPreferences shipmentid = ((FragmentActivity)context).getSharedPreferences("Shipment_ID",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = shipmentid.edit();
                                editor.putString("ShipmentID", shipmentList.get(position).getID());
                                editor.putString("DeliveryNumber", shipmentList.get(position).getDeliveryNumber());
                                editor.putString("ShipmentStatusValue", shipmentList.get(position).getShipmentStatusValue());
                                editor.commit();
                                FragmentTransaction fragmentTransaction= ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container,new DistributorShipment_ViewDashboard()).addToBackStack("tag");
                                fragmentTransaction.commit();
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
        return shipmentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading,shipment_no_value,tv_option;
        public RelativeLayout main_layout_shipment_box_retailer;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            shipment_no_value = itemView.findViewById(R.id.shipment_no_value);
//            tv_date = itemView.findViewById(R.id.tv_date);
//            tv_recv_date = itemView.findViewById(R.id.tv_recv_date);
//            tv_quantity_value = itemView.findViewById(R.id.tv_quantity_value);
            tv_option = itemView.findViewById(R.id.tv_option);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            main_layout_shipment_box_retailer = itemView.findViewById(R.id.main_layout_shipment_box_retailer);
        }
    }
}
