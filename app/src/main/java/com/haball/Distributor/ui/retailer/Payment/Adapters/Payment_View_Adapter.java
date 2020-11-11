package com.haball.Distributor.ui.retailer.Payment.Adapters;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.text.SpannableString;
        import android.text.style.StyleSpan;
        import android.view.LayoutInflater;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.recyclerview.widget.RecyclerView;

        import com.haball.Distributor.ui.retailer.Payment.Models.Payment_View_Model;
        import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerOrdersModel;
        import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrdersModel.RetailerViewOrderProductModel;
        import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerViewOrder;
        import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
        import com.haball.R;

        import java.text.DecimalFormat;
        import java.util.List;

public class Payment_View_Adapter extends RecyclerView.Adapter<Payment_View_Adapter.ViewHolder> {
    private Context context;
    private List<Payment_View_Model> OrdersList;

    public Payment_View_Adapter(Context context, List<Payment_View_Model> ordersList) {
        this.context = context;
        this.OrdersList = ordersList;
    }

    @NonNull
    @Override
    public Payment_View_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_items_new_recycler, parent, false);
        return new Payment_View_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Payment_View_Adapter.ViewHolder holder, int position) {
        holder.txt_products.setText(OrdersList.get(position).getProductName());
        holder.product_code.setText("Product Code:\u00A0");
        SpannableString ss1 = new SpannableString(OrdersList.get(position).getProductCode());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("\n");

        holder.product_code.append("Price:\u00A0");

        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getUnitPrice()));

        ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);
        if (OrdersList.get(position).getUOM() != null && !OrdersList.get(position).getUOM().equals("null")) {
            holder.product_code.append("        ");

            holder.product_code.append("UOM:\u00A0");

            ss1 = new SpannableString(OrdersList.get(position).getUOM());
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
        holder.product_code.append("        ");

        holder.product_code.append("Qty:\u00A0");

        ss1 = new SpannableString(OrdersList.get(position).getOrderQty());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);

        holder.product_code.append("        ");

        holder.product_code.append("Amount:\u00A0");

        formatter1 = new DecimalFormat("#,###,##0.00");
        yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTotalPrice()));

        ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        holder.product_code.append(ss1);
//        holder.txt_products.setText(OrdersList.get(position).getProductName());
//        holder.product_code_value.setText(OrdersList.get(position).getProductCode());
//        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//        String yourFormattedString1 = formatter1.format(Double.parseDouble(OrdersList.get(position).getUnitPrice()));
//        holder.price_value.setText("Rs. " + yourFormattedString1);
//
//        String yourFormattedString2 = formatter1.format(Double.parseDouble(OrdersList.get(position).getDiscount()));
//        holder.discount_value.setText("Rs. " + yourFormattedString2);
//
//        holder.UOM_value.setText(OrdersList.get(position).getUOM());
//
////        holder.Quantity_value.setText(OrdersList.get(position).getOrderQty());
//        holder.Quantity.setText("Quantity: "+OrdersList.get(position).getOrderQty());
//
//        String yourFormattedString3 = formatter1.format(Double.parseDouble(OrdersList.get(position).getTotalPrice()));
////        holder.amount_value.setText("Rs. " + yourFormattedString3);
//        holder.amount.setText("Amount: Rs. " + yourFormattedString3);
    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView txt_products, product_code_value, price_value, discount_value, UOM_value, pack_size_value, Quantity, amount;
public TextView txt_products, product_code;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_products = itemView.findViewById(R.id.txt_products);
            product_code = itemView.findViewById(R.id.product_code);

//            product_code_value = itemView.findViewById(R.id.product_code_value);
//            price_value = itemView.findViewById(R.id.price_value);
//            discount_value = itemView.findViewById(R.id.discount_value);
//            UOM_value = itemView.findViewById(R.id.UOM_value);
//            pack_size_value = itemView.findViewById(R.id.tax_value);
//            Quantity = itemView.findViewById(R.id.Quantity);
//            amount = itemView.findViewById(R.id.amount);
//            Quantity_value = itemView.findViewById(R.id.Quantity_value);
//            amount_value = itemView.findViewById(R.id.amount_value);
        }
    }
}
