package com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Tabs.Dist_OrderPlace;
import com.haball.Loader;
import com.haball.NonSwipeableViewPager;
import com.haball.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Order_Summary_Adapter_DistOrder extends RecyclerView.Adapter<Order_Summary_Adapter_DistOrder.ViewHolder> {

    private Context context;
    private FragmentActivity activity;
    private List<OrderChildlist_Model_DistOrder> selectedProductsDataList, selectedProductsDataList_temp;
    private List<String> selectedProductsDataListQty, selectedProductsDataListQty_temp;
    private float grossAmount = 0;
    private float discAmount = 0;
    private Button btn_draft;
    private Button btn_confirm;
    private double Quantity = 0, Quantity_temp = 0;
    private TextView total_amount;
    private TextView discount_amount;

    public Order_Summary_Adapter_DistOrder(FragmentActivity activity, Context context, List<OrderChildlist_Model_DistOrder> selectedProductsDataList, List<String> selectedProductsDataListQty, Button btn_confirm, Button btn_draft, TextView total_amount, TextView discount_amount) {
        Log.i("debug_back_pressed", "String.valueOf(selectedProductsDataList)");
        this.context = context;
        this.activity = activity;
        this.selectedProductsDataList = selectedProductsDataList;
        this.selectedProductsDataListQty = selectedProductsDataListQty;
        this.btn_draft = btn_draft;
        this.btn_confirm = btn_confirm;
        this.total_amount = total_amount;
        this.discount_amount = discount_amount;

        for (int iter = 0; iter < this.selectedProductsDataList.size(); iter++) {
            if (this.selectedProductsDataListQty.get(iter).equals("0") || this.selectedProductsDataListQty.get(iter).equals("")) {
                this.selectedProductsDataListQty.set(iter, "0");

                grossAmount = 0;
                discAmount = 0;


                for (int i = 0; i < this.selectedProductsDataList.size(); i++) {
                    if (!this.selectedProductsDataList.get(i).getUnitPrice().equals("") && !this.selectedProductsDataListQty.get(i).equals(""))
                        grossAmount += Float.parseFloat(this.selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(this.selectedProductsDataListQty.get(i));
                }
                SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_grossamount = grossamount.edit();
                editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                editor_grossamount.apply();
                grossAmount = 0;
                discAmount = 0;

                this.selectedProductsDataList.remove(iter);
                this.selectedProductsDataListQty.remove(iter);
                notifyItemRemoved(iter);
                notifyItemRangeChanged(iter, this.selectedProductsDataList.size());

                Gson gson = new Gson();
                String json = gson.toJson(this.selectedProductsDataList);
                String jsonqty = gson.toJson(this.selectedProductsDataListQty);
                Log.i("jsonqty", jsonqty);
                Log.i("json", json);

                SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = selectedProducts.edit();
                editor.putString("selected_products", json);
                editor.putString("selected_products_qty", jsonqty);
                editor.apply();
            }
        }

        if (selectedProductsDataList.size() > 0) {
            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                Log.i("qty", selectedProductsDataListQty.get(i));
                if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals("")) {
                    if (selectedProductsDataList.get(i).getDiscountValue() != null) {
                        grossAmount += (Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) - Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue())) * Float.parseFloat(selectedProductsDataListQty.get(i));
                        discAmount += Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                    } else
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                }
            }

            float gstAmount = 0;
            float totalAmount = grossAmount + gstAmount;

            DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
            String yourFormattedString3 = formatter1.format(totalAmount);
            total_amount.setText(String.format(yourFormattedString3));

            yourFormattedString3 = formatter1.format(discAmount);
            discount_amount.setText(String.format(yourFormattedString3));


            SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_grossamount = grossamount.edit();
            editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
            editor_grossamount.apply();
            grossAmount = 0;
            discAmount = 0;
        }


        Quantity = 0;
        for (int i = 0; i < this.selectedProductsDataListQty.size(); i++) {
            Quantity = Quantity + Float.parseFloat(this.selectedProductsDataListQty.get(i));
        }
        Quantity_temp = Quantity;
        if (Quantity > 0) {
            enableCheckoutButton();
        } else {
            disableCheckoutButton();
        }


        this.selectedProductsDataList_temp = this.selectedProductsDataList;
        this.selectedProductsDataListQty_temp = this.selectedProductsDataListQty;


        Log.i("selectedProducts", String.valueOf(this.selectedProductsDataList));
        Log.i("debug_back_pressed", String.valueOf(this.selectedProductsDataList_temp));
        Log.i("debug_back_pressed", String.valueOf(this.selectedProductsDataListQty_temp));
    }

    private void enableCheckoutButton() {
        btn_confirm.setEnabled(true);
        btn_confirm.setBackgroundResource(R.drawable.button_round);
        btn_draft.setEnabled(true);
        btn_draft.setBackgroundResource(R.drawable.button_round);
    }

    private void disableCheckoutButton() {
        btn_confirm.setEnabled(false);
        btn_confirm.setBackgroundResource(R.drawable.button_grey_round);
        btn_draft.setEnabled(false);
        btn_draft.setBackgroundResource(R.drawable.button_grey_round);
    }

    public Order_Summary_Adapter_DistOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.order_summary_recycler_fragment, parent, false);
        return new Order_Summary_Adapter_DistOrder.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final Order_Summary_Adapter_DistOrder.ViewHolder holder, final int position) {
        final int finalPosition = position;
        Log.i("position", String.valueOf(finalPosition));
        holder.list_numberOFitems.setText(selectedProductsDataListQty.get(position));
        holder.list_product_code_value.setText(selectedProductsDataList.get(position).getCode());
        holder.list_txt_products_.setText(selectedProductsDataList.get(position).getTitle());
        final DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");

        if (selectedProductsDataList.get(position).getUnitPrice() != null) {
            String yourFormattedString1 = formatter1.format(Double.parseDouble(selectedProductsDataList.get(position).getUnitPrice()));
            holder.list_price_value.setText("Rs. " + yourFormattedString1);
        }
        String yourFormattedString2;
        Log.i("getDiscountValue()", "'" + String.valueOf(selectedProductsDataList.get(position).getDiscountValue()) + "'");
        if (selectedProductsDataList.get(position).getDiscountValue() != null)
            yourFormattedString2 = formatter1.format(Double.parseDouble(selectedProductsDataList.get(position).getDiscountValue()));
        else
            yourFormattedString2 = formatter1.format(0);
        holder.list_discount_value.setText("Rs. " + yourFormattedString2);
        holder.list_UOM_value.setText(selectedProductsDataList.get(position).getUOMTitle());


        float totalamount = 0;
        String yourFormattedString3;
        if (!selectedProductsDataList.get(position).getUnitPrice().equals("") && !selectedProductsDataListQty.get(position).equals("")) {
            if (selectedProductsDataList.get(position).getDiscountValue() != null)
                totalamount = Float.parseFloat(selectedProductsDataListQty.get(position)) * (Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice()) - Float.parseFloat(selectedProductsDataList.get(position).getDiscountValue()));
            else
                totalamount = Float.parseFloat(selectedProductsDataListQty.get(position)) * Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice());
        }

        yourFormattedString3 = formatter1.format(totalamount);
        holder.totalAmount_value.setText(yourFormattedString3);

        holder.list_numberOFitems.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    final Loader loader = new Loader(context);
                    loader.showLoader();
                    ((FragmentActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loader.hideLoader();
                                    Log.i("back_key_debug", "back from fragment 1");
                                    SharedPreferences selectedProductsSP = context.getSharedPreferences("fromDraft",
                                            Context.MODE_PRIVATE);
//                                    if (!selectedProductsSP.getString("fromDraft", "").equals("draft")) {
                                    Log.i("debug_back_pressed", String.valueOf(selectedProductsDataList));
                                    Log.i("debug_back_pressed", String.valueOf(selectedProductsDataList_temp));
                                    Log.i("debug_back_pressed", String.valueOf(Quantity_temp));
                                    Log.i("debug_back_pressed", String.valueOf(Quantity));
                                    if (selectedProductsDataList != selectedProductsDataList_temp || Quantity != Quantity_temp) {
                                        showDiscardDialog();
                                    } else {
                                        SharedPreferences orderCheckout1 = context.getSharedPreferences("FromDraft_Temp",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor orderCheckout_editor1 = orderCheckout1.edit();
                                        orderCheckout_editor1.putString("fromDraft", "");
                                        orderCheckout_editor1.apply();

                                        SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                                        editorOrderTabsFromDraft.putString("TabNo", "1");
                                        editorOrderTabsFromDraft.apply();
                                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("tag");
                                        fragmentTransaction.commit();
                                    }
//                        showDiscardDialog();
//                        return true;
//                                    } else {
//                                        if (selectedProductsDataList != selectedProductsDataList_temp && selectedProductsDataListQty != selectedProductsDataListQty_temp) {
//                                            showDiscardDialog();
//                                        } else {
//                                            SharedPreferences orderCheckout1 = context.getSharedPreferences("FromDraft_Temp",
//                                                    Context.MODE_PRIVATE);
//                                            SharedPreferences.Editor orderCheckout_editor1 = orderCheckout1.edit();
//                                            orderCheckout_editor1.putString("fromDraft", "");
//                                            orderCheckout_editor1.apply();
//
//                                            SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
//                                                    Context.MODE_PRIVATE);
//                                            SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
//                                            editorOrderTabsFromDraft.putString("TabNo", "0");
//                                            editorOrderTabsFromDraft.apply();
//                                            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
//                                            fragmentTransaction.add(R.id.main_container, new HomeFragment()).addToBackStack("tag");
//                                            fragmentTransaction.commit();
//                                        }
//                                    }
                                }
                            }, 3000);
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        holder.list_numberOFitems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.list_numberOFitems.hasFocus()) {
                    String str_quantity = String.valueOf(s);
                    if (!String.valueOf(s).equals("") && Integer.parseInt(String.valueOf(s)) == 0) {
                        str_quantity = "0";
                    }

                    if (str_quantity.equals("0")) {
                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View view_popup = inflater.inflate(R.layout.discard_changes, null);
                        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
                        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
                        tv_discard_txt.setText("Are you sure, you want to delete this product?");
                        tv_discard.setText("Delete Product");

                        alertDialog.setView(view_popup);
                        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                        layoutParams.y = 200;
                        layoutParams.x = -70;// top margin
                        alertDialog.getWindow().setAttributes(layoutParams);
                        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
                        btn_discard.setText("Delete");
                        btn_discard.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Log.i("CreatePayment", "Button Clicked");
                                holder.list_numberOFitems.clearFocus();
                                alertDialog.dismiss();
                                deleteProduct(holder, finalPosition);
                            }
                        });

                        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
                        img_email.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }

                    if (!str_quantity.equals("")) {
                        float totalamount = 0;
                        String yourFormattedString3;
                        if (!selectedProductsDataList.get(position).getUnitPrice().equals("")) {
                            if (selectedProductsDataList.get(position).getDiscountValue() != null)
                                totalamount = Float.parseFloat(str_quantity) * (Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice()) - Float.parseFloat(selectedProductsDataList.get(position).getDiscountValue()));
                            else
                                totalamount = Float.parseFloat(str_quantity) * Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice());
                        }

                        yourFormattedString3 = formatter1.format(totalamount);
                        holder.totalAmount_value.setText(yourFormattedString3);
                    } else {
                        float totalamount = 0;
                        String yourFormattedString3;
                        if (!selectedProductsDataList.get(position).getUnitPrice().equals("")) {
                            if (selectedProductsDataList.get(position).getDiscountValue() != null)
                                totalamount = 0 * (Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice()) - Float.parseFloat(selectedProductsDataList.get(position).getDiscountValue()));
                            else
                                totalamount = 0 * Float.parseFloat(selectedProductsDataList.get(position).getUnitPrice());
                        }

                        yourFormattedString3 = formatter1.format(totalamount);
                        holder.totalAmount_value.setText(yourFormattedString3);
                    }

                    if (String.valueOf(s).equals("")) {
                        str_quantity = "0";
                    }


                    if (holder.list_txt_products_.getText().equals(selectedProductsDataList.get(position).getTitle())) {
                        Log.i("debugOrder_textChang", String.valueOf(selectedProductsDataList.get(position).getTitle()));
                        Log.i("debugOrder_textChang1", String.valueOf(holder.list_txt_products_.getText()));
                        checkOutEnabler(holder, position, str_quantity);
                    }
                }
            }
        });
        holder.btn_delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                LayoutInflater inflater = LayoutInflater.from(context);
                View view_popup = inflater.inflate(R.layout.discard_changes, null);
                TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
                TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
                tv_discard_txt.setText("Are you sure, you want to delete this product?");
                tv_discard.setText("Delete Product");

                alertDialog.setView(view_popup);
                alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
                layoutParams.y = 200;
                layoutParams.x = -70;// top margin
                alertDialog.getWindow().setAttributes(layoutParams);
                Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
                btn_discard.setText("Delete");
                btn_discard.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("CreatePayment", "Button Clicked");
                        alertDialog.dismiss();
                        deleteProduct(holder, finalPosition);
                    }
                });

                ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
                img_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });

                alertDialog.show();
            }
        });
    }

    private void deleteProduct(@NonNull final Order_Summary_Adapter_DistOrder.ViewHolder holder, final int finalPosition) {
        if (selectedProductsDataList.size() > 1) {

            selectedProductsDataListQty.set(finalPosition, "0");
            holder.list_numberOFitems.setText("0");
            checkOutEnabler(holder, finalPosition, "0");

            grossAmount = 0;
            discAmount = 0;


            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                Log.i("qty", selectedProductsDataListQty.get(i));
                if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals("")) {
                    if (selectedProductsDataList.get(i).getDiscountValue() != null) {
                        grossAmount += (Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) - Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue())) * Float.parseFloat(selectedProductsDataListQty.get(i));
                        discAmount += Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                    } else
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                }
            }

            float gstAmount = 0;
            float totalAmount = grossAmount + gstAmount;


            DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
            String yourFormattedString3 = formatter1.format(totalAmount);

            total_amount.setText(String.format(yourFormattedString3));

            yourFormattedString3 = formatter1.format(discAmount);

            discount_amount.setText(String.format(yourFormattedString3));


            SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_grossamount = grossamount.edit();
            editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
            editor_grossamount.apply();
            grossAmount = 0;
            discAmount = 0;
        }

        selectedProductsDataList.remove(finalPosition);
        selectedProductsDataListQty.remove(finalPosition);
        notifyItemRemoved(finalPosition);
        notifyItemRangeChanged(finalPosition, selectedProductsDataList.size());

        Gson gson = new Gson();
        String json = gson.toJson(selectedProductsDataList);
        String jsonqty = gson.toJson(selectedProductsDataListQty);
        Log.i("jsonqty", jsonqty);
        Log.i("json", json);

        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", json);
        editor.putString("selected_products_qty", jsonqty);
        editor.apply();

        final Dialog fbDialogue = new Dialog(context);

        fbDialogue.setContentView(R.layout.password_updatepopup);
        TextView tv_pr1, txt_header1;
        txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
        tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
        tv_pr1.setText("Your product has been deleted successfully.");
        txt_header1.setText("Product Deleted");
        fbDialogue.setCancelable(true);
        fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        fbDialogue.getWindow().setAttributes(layoutParams);
        fbDialogue.show();

        ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbDialogue.dismiss();
            }
        });
        fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (selectedProductsDataList.size() <= 0) {
                    SharedPreferences add_more_product = context.getSharedPreferences("add_more_product",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = add_more_product.edit();
                    editor1.putString("add_more_product", "fromAddMore");
                    editor1.apply();

                    if (selectedProductsDataList.size() > 0) {
                        for (int i = 0; i < selectedProductsDataList.size(); i++) {
                            Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                            Log.i("qty", selectedProductsDataListQty.get(i));
                            if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals("")) {
                                if (selectedProductsDataList.get(i).getDiscountValue() != null) {
                                    grossAmount += (Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) - Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue())) * Float.parseFloat(selectedProductsDataListQty.get(i));
                                    discAmount += Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                                } else
                                    grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                            }
                        }

                        float gstAmount = 0;
                        float totalAmount = grossAmount + gstAmount;

                        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
                        String yourFormattedString3 = formatter1.format(totalAmount);
                        total_amount.setText(String.format(yourFormattedString3));

                        yourFormattedString3 = formatter1.format(discAmount);
                        discount_amount.setText(String.format(yourFormattedString3));


                        SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_grossamount = grossamount.edit();
                        editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
                        editor_grossamount.apply();
                        grossAmount = 0;
                        discAmount = 0;
                    }


                    grossAmount = 0;
                    discAmount = 0;
                    NonSwipeableViewPager viewPager = activity.findViewById(R.id.view_pager5);
                    viewPager.setCurrentItem(0);

                    FragmentTransaction fragmentTransaction = (activity).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void showDiscardDialog() {
        Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = activity.getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to leave this page? Your changes will be discarded.");
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();


                FragmentTransaction fragmentTransaction = (activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new HomeFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return selectedProductsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView list_txt_products_, list_product_code_value, list_price_value, list_discount_value, list_UOM_value, totalAmount_value;
        public EditText list_numberOFitems;
        public TextView btn_delete_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_txt_products_ = itemView.findViewById(R.id.list_txt_products_);
            list_product_code_value = itemView.findViewById(R.id.list_product_code_value);
            list_price_value = itemView.findViewById(R.id.list_price_value);
            list_discount_value = itemView.findViewById(R.id.list_discount_value);
            list_UOM_value = itemView.findViewById(R.id.list_UOM_value);
            list_numberOFitems = itemView.findViewById(R.id.list_numberOFitems);
            totalAmount_value = itemView.findViewById(R.id.totalAmount_value);
            btn_delete_item = itemView.findViewById(R.id.btn_delete_item);

        }

    }

    private void checkOutEnabler(Order_Summary_Adapter_DistOrder.ViewHolder holder, int position, String s) {
        Log.i("debugOrder_seldatalist", String.valueOf(selectedProductsDataList));
        if (selectedProductsDataList != null) {
            Log.i("debugOrder_seldata_nnul", String.valueOf(selectedProductsDataList));
            int foundIndex = -1;
            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                if (selectedProductsDataList.get(i).getTitle().equals(String.valueOf(holder.list_txt_products_.getText())) && selectedProductsDataList.get(i).getCode().equals(String.valueOf(holder.list_product_code_value.getText()))) {
                    foundIndex = i;
                    break;
                }
            }
            Log.i("debugOrder_seldata_ind", String.valueOf(foundIndex));

            if (foundIndex != -1) {
                selectedProductsDataListQty.set(foundIndex, String.valueOf(s));
            }
            Log.i("debugOrder_seldata_qty", String.valueOf(selectedProductsDataListQty));
        }

        Gson gson = new Gson();
        String json = gson.toJson(selectedProductsDataList);
        String jsonqty = gson.toJson(selectedProductsDataListQty);
        Log.i("debugOrder_jsonqty", jsonqty);
        Log.i("debugOrder_json", json);
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", json);
        editor.putString("selected_products_qty", jsonqty);
        editor.apply();

        if (selectedProductsDataList.size() > 0) {
            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                Log.i("unit price", selectedProductsDataList.get(i).getUnitPrice());
                Log.i("qty", selectedProductsDataListQty.get(i));
                if (!selectedProductsDataList.get(i).getUnitPrice().equals("") && !selectedProductsDataListQty.get(i).equals("")) {
                    if (selectedProductsDataList.get(i).getDiscountValue() != null) {
                        grossAmount += (Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) - Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue())) * Float.parseFloat(selectedProductsDataListQty.get(i));
                        discAmount += Float.parseFloat(selectedProductsDataList.get(i).getDiscountValue()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                    } else
                        grossAmount += Float.parseFloat(selectedProductsDataList.get(i).getUnitPrice()) * Float.parseFloat(selectedProductsDataListQty.get(i));
                }
            }

            float gstAmount = 0;
            float totalAmount = grossAmount + gstAmount;

            DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
            String yourFormattedString3 = formatter1.format(totalAmount);

            total_amount.setText(String.format(yourFormattedString3));

            yourFormattedString3 = formatter1.format(discAmount);

            discount_amount.setText(String.format(yourFormattedString3));


            SharedPreferences grossamount = context.getSharedPreferences("grossamount",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_grossamount = grossamount.edit();
            editor_grossamount.putString("grossamount", String.valueOf(grossAmount));
            editor_grossamount.apply();
            grossAmount = 0;
            discAmount = 0;
        }


        Quantity = 0;
        for (int i = 0; i < this.selectedProductsDataListQty.size(); i++) {
            Quantity = Quantity + Float.parseFloat(this.selectedProductsDataListQty.get(i));
        }
        if (Quantity > 0) {
            enableCheckoutButton();
        } else {
            disableCheckoutButton();
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

