package com.haball.Shipment.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.R;
import com.haball.Shipment.ui.main.Models.Distributor_ProductModel;

import java.text.DecimalFormat;
import java.util.List;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ShipmentDetailsVH> {

    private Context context;
    List<Distributor_ProductModel> product_list;

    public ProductDetailsAdapter(Context context, List<Distributor_ProductModel> product_list) {
        this.context = context;
        this.product_list = product_list;
    }

    @NonNull
    @Override
    public ShipmentDetailsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.product_details_shipment, parent, false);
        return new ProductDetailsAdapter.ShipmentDetailsVH(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentDetailsVH holder, int position) {
//        holder.list_product_code_value.setText(product_list.get(position).getProductCode());
//        holder.list_txt_products_.setText(product_list.get(position).getProductName());
//        holder.shipped_qty_value.setText(product_list.get(position).getDeliveredQty());
//        holder.list_batch_no_value.setText(product_list.get(position).getBatchNumber());
//        holder.list_prod_date_value.setText(product_list.get(position).getProductionDate());
//        holder.list_exp_date_value.setText(product_list.get(position).getExpiryDate());
        holder.list_txt_products_.setText(product_list.get(position).getProductName());

        holder.product_code.setText("Product Code:\u00A0");
        SpannableString ss1 = new SpannableString(product_list.get(position).getProductCode());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("\n");

        holder.product_code.append("Prod Date:\u00A0");

        ss1 = new SpannableString(product_list.get(position).getProductionDate());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);


        holder.product_code.append("\u00A0| ");

        holder.product_code.append("Batch No:\u00A0");
        ss1 = new SpannableString(product_list.get(position).getBatchNumber());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("\u00A0| ");

        holder.product_code.append("Exp Date:\u00A0");

        ss1 = new SpannableString(product_list.get(position).getExpiryDate());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);


        holder.product_code.append("\u00A0| ");

        holder.product_code.append("Shipped Qty:\u00A0");

        ss1 = new SpannableString(product_list.get(position).getDeliveredQty());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ShipmentDetailsVH extends RecyclerView.ViewHolder {
        //        private TextView list_txt_products_,list_product_code_value,list_prod_date_value ,list_exp_date_value ,list_batch_no_value ,shipped_qty_value;
        private TextView list_txt_products_, product_code;

        public ShipmentDetailsVH(@NonNull View itemView) {
            super(itemView);
            list_txt_products_ = itemView.findViewById(R.id.list_txt_products_);
            product_code = itemView.findViewById(R.id.product_code);
//            list_product_code_value = itemView.findViewById(R.id.list_product_code_value);
//            list_prod_date_value = itemView.findViewById(R.id.list_prod_date_value);
//            list_exp_date_value = itemView.findViewById(R.id.list_exp_date_value);
//            list_batch_no_value = itemView.findViewById(R.id.list_batch_no_value);
//            shipped_qty_value = itemView.findViewById(R.id.shipped_qty_value);


        }
    }
}
