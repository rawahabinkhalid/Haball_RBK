package com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersAdapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerViewOrderProductModel;
import com.haball.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RetailerViewOrderProductAdapter extends RecyclerView.Adapter<RetailerViewOrderProductAdapter.ViewHolder> {
    private Context context;
    private List<RetailerViewOrderProductModel> OrdersList;
    boolean scrollingLeft = false;

    public RetailerViewOrderProductAdapter(Context context, List<RetailerViewOrderProductModel> ordersList) {
        this.context = context;
        this.OrdersList = ordersList;
    }

    @NonNull
    @Override
    public RetailerViewOrderProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_items_new_recycler, parent, false);
        return new RetailerViewOrderProductAdapter.ViewHolder(view_inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RetailerViewOrderProductAdapter.ViewHolder holder, int position) {
        if (OrdersList.get(position).getProductTitle() != null)
            holder.txt_products.setText(OrdersList.get(position).getProductTitle());
        else
            holder.txt_products.setText(OrdersList.get(position).getProductName());
        holder.product_code.setText("Product Code:\u00A0");
        SpannableString ss1 = new SpannableString(OrdersList.get(position).getProductCode());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("\n");

        holder.product_code.append("Price:\u00A0");

        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1;
        if (OrdersList.get(position).getProductUnitPrice() != null)
            yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getProductUnitPrice()));
        else
            yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getUnitPrice()));

        ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);
        if (OrdersList.get(position).getUnitOFMeasure() != null && !OrdersList.get(position).getUnitOFMeasure().equals("null")) {
            holder.product_code.append("            ");
            holder.product_code.append("            ");

            holder.product_code.append("UOM:\u00A0");

            ss1 = new SpannableString(OrdersList.get(position).getUnitOFMeasure());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);

            holder.product_code.append(ss1);
        }

        if (!OrdersList.get(position).getDiscount().equals("0") && !OrdersList.get(position).getDiscount().equals("") && !OrdersList.get(position).getDiscount().equals("null")) {
            holder.product_code.append("            ");

            holder.product_code.append("Disc:\u00A0");

            formatter1 = new DecimalFormat("#,###,##0.00");
            yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getDiscount()));

            ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            holder.product_code.append(ss1);

        }
        holder.product_code.append("            ");
        holder.product_code.append("            ");

        holder.product_code.append("Qty:\u00A0");

        if (OrdersList.get(position).getOrderedQty() != null)
            ss1 = new SpannableString(OrdersList.get(position).getOrderedQty());
        else
            ss1 = new SpannableString(OrdersList.get(position).getOrderQty());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("            ");
        holder.product_code.append("            ");

        if (!OrdersList.get(position).getTaxValue().equals("0") && !OrdersList.get(position).getTaxValue().equals("") && !OrdersList.get(position).getTaxValue().equals("null")) {

            holder.product_code.append("Tax:\u00A0");

            formatter1 = new DecimalFormat("#,###,##0.00");
            yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTaxValue()));

            ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            holder.product_code.append(ss1);

            holder.product_code.append("            ");
            holder.product_code.append("            ");
        }

        holder.product_code.append("Amount:\u00A0");

        formatter1 = new DecimalFormat("#,###,##0.00");
        double totalAmount = 0;
        if (OrdersList.get(position).getTotalamount() != null)
            totalAmount = Double.parseDouble(OrdersList.get(position).getTotalamount());
        else
            totalAmount = Double.parseDouble(OrdersList.get(position).getTotalPrice());
//        if (!OrdersList.get(position).getDiscount().equals("0") && !OrdersList.get(position).getDiscount().equals("") && !OrdersList.get(position).getDiscount().equals("null")) {
//
//            double discount = Double.parseDouble(OrdersList.get(position).getDiscount());
//            totalAmount -= discount;
//
//        }

        yourFormattedString1 = formatter1.format(totalAmount);

        ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);
//
//        SharedPreferences sharedPreferences1 = context.getSharedPreferences("OrderId",
//                Context.MODE_PRIVATE);
//        String InvoiceStatus = sharedPreferences1.getString("InvoiceStatus", "null");
//        Log.i("InvoiceStatus", InvoiceStatus);
//
//
//        holder.product_code_value.setText(OrdersList.get(position).getProductCode());
//        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getProductUnitPrice()));
//        holder.price_value.setText("Rs. " + yourFormattedString1);
//        holder.UOM_value.setText(OrdersList.get(position).getUOMTitle());
//        String yourFormattedString2;
//        if (OrdersList.get(position).getDiscount().equals("0") || OrdersList.get(position).getDiscount().equals("null")) {
////            holder.discount.setText("Tax: ");
////            String yourFormattedString4 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTaxValue()));
////            holder.discount_value.setText("Rs. " + yourFormattedString4);
////            holder.tv_taxValue.setText("Quantity: ");
////            holder.tax_value.setText(OrdersList.get(position).getOrderQty());
////            holder.Quantity_value.setVisibility(View.GONE);
////            holder.separator_3.setVisibility(View.GONE);
////            holder.Quantity.setVisibility(View.GONE);
//
//            if (!OrdersList.get(position).getTaxValue().equals("0") && !OrdersList.get(position).getTaxValue().equals("") && !OrdersList.get(position).getTaxValue().equals("null")) {
//                holder.discount.setText("Tax: ");
//                String yourFormattedString4 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTaxValue()));
//                holder.discount_value.setText("Rs.\u00A0" + yourFormattedString4);
//                holder.tv_taxValue.setText("Quantity:\u00A0");
//                holder.tax_value.setText(OrdersList.get(position).getOrderQty());
//                holder.Quantity_value.setVisibility(View.GONE);
//                holder.separator_3.setVisibility(View.GONE);
//                holder.Quantity.setVisibility(View.GONE);
//            } else {
//                holder.discount.setVisibility(View.GONE);
//                holder.discount_value.setVisibility(View.GONE);
//                holder.separator_2.setVisibility(View.GONE);
//                holder.tax_value.setVisibility(View.GONE);
//                holder.tv_taxValue.setVisibility(View.GONE);
//                holder.Quantity_value.setText(OrdersList.get(position).getOrderQty());
//            }
//
//
//        } else {
//            yourFormattedString2 = formatter1.format(Double.parseDouble(OrdersList.get(position).getDiscount()));
//            holder.discount_value.setText("Rs.\u00A0" + yourFormattedString2);
//            holder.tax_value.setText(OrdersList.get(position).getTaxValue());
//            if (!OrdersList.get(position).getTaxValue().equals("0") && !OrdersList.get(position).getTaxValue().equals("") && !OrdersList.get(position).getTaxValue().equals("null")) {
//                String yourFormattedString4 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTaxValue()));
//                holder.tax_value.setText("Rs.\u00A0" + yourFormattedString4);
////            holder.tax_value.setText(OrdersList.get(position).getTaxValue());
//                holder.Quantity_value.setText(OrdersList.get(position).getOrderQty());
//            } else {
//                holder.tv_taxValue.setText("Quantity:\u00A0");
//                holder.tax_value.setText(OrdersList.get(position).getOrderQty());
//                holder.Quantity_value.setVisibility(View.GONE);
//                holder.separator_3.setVisibility(View.GONE);
//                holder.Quantity.setVisibility(View.GONE);
//            }
//        }
////        holder.tax_value.setVisibility(View.GONE);
////        holder.tv_taxValue.setVisibility(View.GONE);
////        holder.separator_2.setVisibility(View.GONE);
//
//        String yourFormattedString3 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTotalPrice()));
//        holder.amount_value.setText("Rs.\u00A0" + yourFormattedString3);
//        holder.amount_new_line_value.setText("Rs.\u00A0" + yourFormattedString3);

    }

    public static boolean isTextViewEllipsized(final TextView textView) {
        // Check if the supplied TextView is not null
        if (textView == null) {
            return false;
        }

        // Check if ellipsizing the text is enabled
        final TextUtils.TruncateAt truncateAt = textView.getEllipsize();
        if (truncateAt == null || TextUtils.TruncateAt.END.equals(truncateAt)) {
            return false;
        }

        // Retrieve the layout in which the text is rendered
        final Layout layout = textView.getLayout();
        if (layout == null) {
            return false;
        }

        // Iterate all lines to search for ellipsized text
        for (int line = 0; line < layout.getLineCount(); ++line) {

            // Check if characters have been ellipsized away within this line of text
            if (layout.getEllipsisCount(line) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        TextView txt_products, product_code, product_code_value, separator_1, discount, separator_2, tv_taxValue, price_value, discount_value, UOM_value, tax_value, Quantity_value, amount_value, Quantity, separator_3;
//        public TextView amount_new_line, amount_new_line_value, amount;
        public TextView txt_products, product_code;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
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
