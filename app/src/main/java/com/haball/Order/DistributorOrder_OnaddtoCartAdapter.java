package com.haball.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Order.DistributorOrder_OnaddtoCart;
import com.haball.R;

public class DistributorOrder_OnaddtoCartAdapter extends RecyclerView.Adapter<DistributorOrder_OnaddtoCartAdapter.ViewHolder> {
    @NonNull

    private Context mContext;
    private  String txt_product_value,txt_price_value;

    public DistributorOrder_OnaddtoCartAdapter(DistributorOrder_OnaddtoCart addcart, String txt_product_value, String txt_price_value){
        this.mContext = addcart;
        this.txt_product_value = txt_product_value;
        this.txt_price_value = txt_price_value;
    }

    public DistributorOrder_OnaddtoCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContext).inflate(R.layout.addtocartrecycler,parent,false);
        return new DistributorOrder_OnaddtoCartAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorOrder_OnaddtoCartAdapter.ViewHolder holder, int position) {

        holder.txt_product_value.setText(txt_product_value);
        holder.txt_price_value.setText(txt_price_value);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_product_value,txt_price_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_product_value = itemView.findViewById(R.id.txt_product );
            txt_price_value = itemView.findViewById(R.id.txt_price);
        }
    }
}
