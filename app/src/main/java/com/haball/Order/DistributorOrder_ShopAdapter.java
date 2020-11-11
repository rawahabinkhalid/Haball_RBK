package com.haball.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Order.DistributorOrder_ShopSelection;
import com.haball.R;

public class DistributorOrder_ShopAdapter extends RecyclerView.Adapter<DistributorOrder_ShopAdapter.ViewHolder> {

    private Context mContext;
    private  String txt_select_value;


    public DistributorOrder_ShopAdapter(DistributorOrder_ShopSelection order_shop, String txt_select_value){
        this.mContext = order_shop;
        this.txt_select_value = txt_select_value;
    }
    @NonNull
    @Override
    public DistributorOrder_ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContext).inflate(R.layout.distributerorder_shoprecycler,parent,false);
        return new DistributorOrder_ShopAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrder_ShopAdapter.ViewHolder holder, int position) {

        holder.txt_value.setText(txt_select_value);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_value = itemView.findViewById(R.id.txt_value );
        }
    }
}
