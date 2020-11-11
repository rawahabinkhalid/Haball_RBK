package com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;


import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.haball.Distributor.ui.orders.OrdersTabsNew.ParentViewHolder;
import com.haball.R;

public class OrderParentLIst_VH extends ParentViewHolder {
    public TextView _textview;
    public ImageView imageView;
    public RelativeLayout layout_expandable, rl_orderName_retailer;
    public ImageView minus_icon;
    public RecyclerView subchlid_RV;
    public RelativeLayout rl_parentList;
    private RelativeLayout filter_layout;

    public OrderParentLIst_VH(View itemView, RelativeLayout filter_layout) {
        super(itemView);
        _textview = (TextView) itemView.findViewById(R.id.orderName_retailer);
        imageView = itemView.findViewById(R.id.plus_icon);
        minus_icon = itemView.findViewById(R.id.minus_icon);
        subchlid_RV = itemView.findViewById(R.id.subchlid_RV);
        layout_expandable = itemView.findViewById(R.id.layout_expandable);
        rl_orderName_retailer = itemView.findViewById(R.id.rl_orderName_retailer);
        rl_parentList = itemView.findViewById(R.id.rl_parentList);
        minus_icon.setVisibility(View.GONE);
        this.filter_layout = filter_layout;
        View.OnClickListener plusMinusOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                togglePlusMinusIcon();
            }
        };
//        _textview.setOnClickListener(plusMinusOnClick);
//        imageView.setOnClickListener(plusMinusOnClick);
//        minus_icon.setOnClickListener(plusMinusOnClick);
//        imageView.setOnClickListener(plusMinusOnClick);
        rl_orderName_retailer.setOnClickListener(plusMinusOnClick);
    }

    public void mycollapseView() {
//        if (isExpanded()) {
        collapseView();
        minus_icon.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

        if (filter_layout.getVisibility() == View.GONE) {

            filter_layout.setVisibility(View.VISIBLE);
            TranslateAnimation animate1 = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    -filter_layout.getHeight(),  // fromYDelta
                    0);                // toYDelta
            animate1.setDuration(250);
            animate1.setFillAfter(true);
            filter_layout.clearAnimation();
            filter_layout.startAnimation(animate1);
        }
//            togglePlusMinusIcon();
//        }
    }

    public void myexpandView() {
//        if (!isExpanded()) {
        expandView();
        imageView.setVisibility(View.GONE);
        minus_icon.setVisibility(View.VISIBLE);
//            togglePlusMinusIcon();
//        }
    }

    public void togglePlusMinusIcon() {
        if (filter_layout.getVisibility() == View.GONE) {

            filter_layout.setVisibility(View.VISIBLE);
            TranslateAnimation animate1 = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    -filter_layout.getHeight(),  // fromYDelta
                    0);                // toYDelta
            animate1.setDuration(250);
            animate1.setFillAfter(true);
            filter_layout.clearAnimation();
            filter_layout.startAnimation(animate1);
        }
        if (isExpanded()) {
            collapseView();
            minus_icon.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            expandView();
            imageView.setVisibility(View.GONE);
            minus_icon.setVisibility(View.VISIBLE);
        }

    }

}
