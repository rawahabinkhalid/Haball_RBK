package com.haball.Distributor.ui.orders.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haball.Distributor.ui.orders.Models.OrderItemsModel;
import com.haball.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private Context context;
    private String txt_count;
    private String txt_products;
    private String price_summary_value;
    private String discount_summary_price;
    private String Rs_summary_value;
    private List<OrderItemsModel> selectedProductsDataList;
    private List<String> selectedProductsDataListQty;
    private float grossAmount = 0;
    public OrderSummaryAdapter(Context context, List<OrderItemsModel> selectedProductsDataList, List<String> selectedProductsDataListQty) {
        this.context = context;
        this.selectedProductsDataList = selectedProductsDataList;
        this.selectedProductsDataListQty = selectedProductsDataListQty;
    }

    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.orders_summary_recycler,parent,false);
        return new OrderSummaryAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.ViewHolder holder, final int position) {

        holder.show_quantity.setText(selectedProductsDataListQty.get(position));
        holder.txt_count.setText(selectedProductsDataList.get(position).getCode());
        holder.txt_products.setText("| "+selectedProductsDataList.get(position).getTitle());
        holder.price_summary_value.setText(selectedProductsDataList.get(position).getUnitPrice());
        holder.discount_summary_price.setText(selectedProductsDataList.get(position).getDiscountAmount());
        float totalamount = 0;
        if (!selectedProductsDataList.get(position).getUnitPrice().equals("") && !selectedProductsDataListQty.get(position).equals(""))
            totalamount = Float.parseFloat(selectedProductsDataListQty.get(position)) * Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice());
        holder.Rs_summary_value.setText(String.valueOf(totalamount));
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("position to be removed", String.valueOf(position));
                selectedProductsDataList.remove(position);
                selectedProductsDataListQty.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, selectedProductsDataList.size());

//                SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts",
//                        Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = selectedProducts.edit();
//                editor.putString("selected_products", String.valueOf(selectedProductsDataList));
//                editor.putString("selected_products_qty", String.valueOf(selectedProductsDataListQty));
//                editor.apply();
//
//

                Gson gson = new Gson();
                String json = gson.toJson(selectedProductsDataList);
                String jsonqty = gson.toJson(selectedProductsDataListQty);
                Log.i("jsonqty",jsonqty);
                Log.i("json",json);

                SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products",json);
                editor.putString("selected_products_qty",jsonqty);
                editor.apply();


            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedProductsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView btn_remove, txt_count,txt_products,price_summary_value,discount_summary_price,Rs_summary_value,show_quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_count = itemView.findViewById(R.id.txt_count);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            txt_products = itemView.findViewById(R.id.txt_products);
            price_summary_value = itemView.findViewById(R.id.price_summary_value);
            discount_summary_price = itemView.findViewById(R.id. discount_summary_price);
            Rs_summary_value =  itemView.findViewById(R.id.Rs_summary_value);
            show_quantity =  itemView.findViewById(R.id.show_quantity);
        }
    }
}
