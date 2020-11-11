package com.haball.Shipment.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.R;
import com.haball.Shipment.ui.main.Models.Distributor_ProductModel;

import java.text.DecimalFormat;
import java.util.List;

public class ProductOrderDetailsAdapter extends RecyclerView.Adapter<ProductOrderDetailsAdapter.ShipmentDetailsVH> {

    private Context context;
    List<Distributor_ProductModel> product_list;

    public ProductOrderDetailsAdapter(Context context, List<Distributor_ProductModel> product_list) {
        this.context = context;
        this.product_list = product_list;
    }

    @NonNull
    @Override
    public ShipmentDetailsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_items_new_recycler, parent, false);
        return new ProductOrderDetailsAdapter.ShipmentDetailsVH(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentDetailsVH holder, int position) {
        holder.txt_products.setText(product_list.get(position).getProductName());
        holder.product_code.setText("Product Code:\u00A0");
        SpannableString ss1 = new SpannableString(product_list.get(position).getProductCode());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("\n");

        holder.product_code.append("Price:\u00A0");

        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(product_list.get(position).getUnitPrice()));

        ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);
        if (product_list.get(position).getUOMTitle() != null && !product_list.get(position).getUOMTitle().equals("null")) {
            holder.product_code.append("        ");

            holder.product_code.append("UOM:\u00A0");

            ss1 = new SpannableString(product_list.get(position).getUOMTitle());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);

            holder.product_code.append(ss1);
        }

        if (!product_list.get(position).getDiscount().equals("0") && !product_list.get(position).getDiscount().equals("") && !product_list.get(position).getDiscount().equals("null")) {
            holder.product_code.append("            ");

            holder.product_code.append("Disc:\u00A0");

            formatter1 = new DecimalFormat("#,###,##0.00");
            yourFormattedString1 = formatter1.format(Double.parseDouble(product_list.get(position).getDiscount()));

            ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            holder.product_code.append(ss1);

        }
        holder.product_code.append("        ");

        holder.product_code.append("Qty:\u00A0");

        ss1 = new SpannableString(product_list.get(position).getDeliveredQty());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("        ");

        holder.product_code.append("Amount:\u00A0");

        formatter1 = new DecimalFormat("#,###,##0.00");
        yourFormattedString1 = formatter1.format(Double.parseDouble(product_list.get(position).getTotalPrice()));

        ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);
//
//        holder.txt_products.setText(product_list.get(position).getProductName());
//        holder.product_code_value.setText(product_list.get(position).getProductCode());
//        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//        String yourFormattedString1 = formatter1.format(Double.parseDouble(product_list.get(position).getUnitPrice()));
//        holder.price_value.setText("Rs. " + yourFormattedString1);
//        holder.UOM_value.setText(product_list.get(position).getUOMTitle());
//        holder.discount_value.setText(product_list.get(position).getDiscount());
//        holder.Quantity_value.setText(product_list.get(position).getDeliveredQty());
//        holder.tax_value.setVisibility(View.GONE);
//        holder.tv_taxValue.setVisibility(View.GONE);
//        holder.separator_3.setVisibility(View.GONE);
//        yourFormattedString1 = formatter1.format(Double.parseDouble(product_list.get(position).getTotalPrice()));
//        holder.amount_value.setText(yourFormattedString1);

    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ShipmentDetailsVH extends RecyclerView.ViewHolder {
        //        TextView txt_products, product_code_value, separator_1, discount, separator_2, tv_taxValue, price_value, discount_value, UOM_value, tax_value, Quantity_value, amount_value, Quantity, separator_3;
//        public TextView amount_new_line, amount_new_line_value, amount;
        public TextView txt_products, product_code;

        public ImageButton menu_btn;

        public ShipmentDetailsVH(@NonNull View itemView) {
            super(itemView);
            txt_products = itemView.findViewById(R.id.txt_products);
            product_code = itemView.findViewById(R.id.product_code);
//            product_code_value = itemView.findViewById(R.id.product_code_value);
//            price_value = itemView.findViewById(R.id.price_value);
//            discount = itemView.findViewById(R.id.discount);
//            discount_value = itemView.findViewById(R.id.discount_value);
//            UOM_value = itemView.findViewById(R.id.UOM_value);
//            tax_value = itemView.findViewById(R.id.tax_value);
//            tv_taxValue = itemView.findViewById(R.id.tv_taxValue);
//            separator_1 = itemView.findViewById(R.id.separator_1);
//            separator_2 = itemView.findViewById(R.id.separator_2);
//            Quantity_value = itemView.findViewById(R.id.Quantity_value);
//            amount = itemView.findViewById(R.id.amount);
//            amount_value = itemView.findViewById(R.id.amount_value);
//            separator_2 = itemView.findViewById(R.id.separator_2);
//            Quantity = itemView.findViewById(R.id.Quantity);
//            separator_3 = itemView.findViewById(R.id.separator_3);
//            amount_new_line = itemView.findViewById(R.id.amount_new_line);
//            amount_new_line_value = itemView.findViewById(R.id.amount_new_line_value);
        }
    }
}

