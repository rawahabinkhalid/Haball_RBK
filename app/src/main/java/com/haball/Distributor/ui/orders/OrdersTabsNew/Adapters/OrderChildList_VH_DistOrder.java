package com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haball.Distributor.ui.orders.OrdersTabsNew.ChildViewHolder;
import com.haball.R;
//import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class OrderChildList_VH_DistOrder extends ChildViewHolder {
    TextView discount, UOM, list_txt_products, list_product_code_value, list_price_value, list_discount_value, list_UOM_value, list_Quantity_value, list_amount_value;
    EditText list_numberOFitems;

    public OrderChildList_VH_DistOrder(View itemView) {
        super(itemView);
        discount = itemView.findViewById(R.id.discount);
        UOM = itemView.findViewById(R.id.UOM);
        list_txt_products = itemView.findViewById(R.id.list_txt_products_);
        list_product_code_value = itemView.findViewById(R.id.list_product_code_value);
        list_price_value = itemView.findViewById(R.id.list_price_value);
        list_discount_value = itemView.findViewById(R.id.list_discount_value);
        list_UOM_value = itemView.findViewById(R.id.list_UOM_value);
//        list_pack_size_value = itemView.findViewById(R.id.list_pack_size_value);
        list_numberOFitems = itemView.findViewById(R.id.list_numberOFitems);
    }
}
