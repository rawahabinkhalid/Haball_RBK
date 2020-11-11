package com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Draft_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Order_Summary_Draft_Adapter extends RecyclerView.Adapter<Order_Summary_Draft_Adapter.ViewHolder> {

    private Context context;
    private List<OrderChildlist_Draft_Model_DistOrder> selectedProductsDataList;
    private float grossAmount = 0;

    public Order_Summary_Draft_Adapter(Context context, List<OrderChildlist_Draft_Model_DistOrder> selectedProductsDataList) {
        this.context = context;
        this.selectedProductsDataList = selectedProductsDataList;
    }

    public Order_Summary_Draft_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_summary_recycler_fragment, parent, false);
        return new Order_Summary_Draft_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final Order_Summary_Draft_Adapter.ViewHolder holder, int position) {
        final int finalPosition = position;
        Log.i("position", String.valueOf(finalPosition));
        if (selectedProductsDataList.get(position).getOrderQty() != null)
            holder.list_numberOFitems.setText(selectedProductsDataList.get(position).getOrderQty());
        if (selectedProductsDataList.get(position).getCode() != null)
            holder.list_product_code_value.setText(selectedProductsDataList.get(position).getCode());
        if (selectedProductsDataList.get(position).getTitle() != null)
            holder.list_txt_products_.setText(selectedProductsDataList.get(position).getTitle());
        if (selectedProductsDataList.get(position).getUnitPrice() != null)
            holder.list_price_value.setText(selectedProductsDataList.get(position).getUnitPrice());
        if (selectedProductsDataList.get(position).getDiscountAmount() != null)
            holder.list_discount_value.setText(selectedProductsDataList.get(position).getDiscountAmount());
        float totalamount = 0;
        if (!selectedProductsDataList.get(position).getUnitPrice().equals("") && !selectedProductsDataList.get(position).getOrderQty().equals(""))
            totalamount = Float.parseFloat(selectedProductsDataList.get(position).getOrderQty()) * Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice());
        holder.totalAmount_value.setText(String.valueOf(totalamount));
        holder.list_numberOFitems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (String.valueOf(s).equals("0")) {
//                    Log.i("position to be removed", String.valueOf(finalPosition));
//                    selectedProductsDataList.remove(finalPosition);
//                    selectedProductsDataListQty.remove(finalPosition);
//                    notifyItemRemoved(finalPosition);
//                    notifyItemRangeChanged(finalPosition, selectedProductsDataList.size());
//
//                    Gson gson = new Gson();
//                    String json = gson.toJson(selectedProductsDataList);
//                    String jsonqty = gson.toJson(selectedProductsDataListQty);
//                    Log.i("jsonqty", jsonqty);
//                    Log.i("json", json);
//
//                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor_draft",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = selectedProducts.edit();
//                    editor.putString("selected_products", json);
//                    editor.putString("selected_products_qty", jsonqty);
//                    editor.apply();
//                } else {
                checkOutEnabler(holder, finalPosition);
                String quantity = selectedProductsDataList.get(finalPosition).getOrderQty();
                if (quantity.equals(""))
                    quantity = "0";

                final float finaltotalamount = Float.parseFloat(quantity) * Float.parseFloat(selectedProductsDataList.get(finalPosition).getUnitPrice());
                holder.totalAmount_value.setText(String.valueOf(finaltotalamount));
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedProductsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView list_txt_products_, list_product_code_value, list_price_value, list_discount_value, list_UOM_value, list_pack_size_value, totalAmount_value;
        public EditText list_numberOFitems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_txt_products_ = itemView.findViewById(R.id.list_txt_products_);
            list_product_code_value = itemView.findViewById(R.id.list_product_code_value);
            list_price_value = itemView.findViewById(R.id.list_price_value);
            list_discount_value = itemView.findViewById(R.id.list_discount_value);
            list_UOM_value = itemView.findViewById(R.id.list_UOM_value);
//            list_pack_size_value = itemView.findViewById(R.id.list_pack_size_value);
            list_numberOFitems = itemView.findViewById(R.id.list_numberOFitems);
            totalAmount_value = itemView.findViewById(R.id.totalAmount_value);
        }
    }


    private void checkOutEnabler(Order_Summary_Draft_Adapter.ViewHolder holder, int position) {
        if (holder.list_numberOFitems.getText() != null && selectedProductsDataList.size() > position) {
            selectedProductsDataList.get(position).setOrderQty(String.valueOf(holder.list_numberOFitems.getText()));

            Gson gson = new Gson();
            String json = gson.toJson(selectedProductsDataList);
            Log.i("json", json);
            SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor_draft",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = selectedProducts.edit();
            editor.putString("selected_products", json);
            editor.apply();

            if (selectedProductsDataList.size() > 0) {
                for (int i = 0; i < selectedProductsDataList.size(); i++) {
                    if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsDataList.get(i).getOrderQty().equals(""))
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsDataList.get(i).getOrderQty());
                }
                SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_grossamount = grossamount.edit();
                editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                editor_grossamount.apply();
                grossAmount = 0;
            }
        }
    }
}
