package com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.Distributor.ui.retailer.RetailerOrder.RetailerOrderDashboard;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderChildlist_Model;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderParentlist_Model;
import com.haball.NonSwipeableViewPager;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ParentListAdapter extends ExpandableRecyclerAdapter<OrderParentlist_Model, OrderChildlist_Model, OrderParentLIst_VH, OrderChildList_VH> {
    LayoutInflater inflater;
    private Context context;
    public List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private String object_string, object_stringqty;
    private int pre_expanded = -1;
    public List<OrderParentLIst_VH> OrderParentList = new ArrayList<>();
    private int parentPosition = -1;
    private List<OrderParentlist_Model> parentItemList;
    private RelativeLayout filter_layout;
    private OrderParentLIst_VH orderParentLIst_VH_main;
    private Button btn_checkout;
    private double Quantity = 0;
    private List<OrderChildlist_Model> productList = new ArrayList<>();

    public ParentListAdapter(Context context, List<OrderParentlist_Model> parentItemList, RelativeLayout filter_layout, Button btn_checkout, List<OrderChildlist_Model> productList) {
        super(parentItemList);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.parentItemList = parentItemList;
        this.filter_layout = filter_layout;
        this.btn_checkout = btn_checkout;
        this.productList = productList;


        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Model>>() {
        }.getType();
        Type typeString = new TypeToken<List<String>>() {
        }.getType();
        if (!object_string.equals("")) {
            selectedProductsDataList = gson.fromJson(object_string, type);
            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
            // Log.i("debugOrder_selProdQty", String.valueOf(object_stringqty));
            // Log.i("debugOrder_selProd", String.valueOf(object_string));
        }

        Quantity = 0;
        for (int i = 0; i < selectedProductsQuantityList.size(); i++) {
            Quantity = Quantity + Float.parseFloat(selectedProductsQuantityList.get(i));
        }
        if (Quantity > 0) {
            enableCheckoutButton();
        } else {
            disableCheckoutButton();
        }

    }

    private void enableCheckoutButton() {
        btn_checkout.setEnabled(true);
        btn_checkout.setBackgroundResource(R.drawable.button_round);
    }

    private void disableCheckoutButton() {
        btn_checkout.setEnabled(false);
        btn_checkout.setBackgroundResource(R.drawable.button_grey_round);
    }

    @Override
    public OrderParentLIst_VH onCreateParentViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.parentlist_retailer_order, viewGroup, false);
        OrderParentLIst_VH orderParentLIst_VH = new OrderParentLIst_VH(view, filter_layout);
        OrderParentList.add(orderParentLIst_VH);
        return new OrderParentLIst_VH(view, filter_layout);

    }


    @Override
    public OrderChildList_VH onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.orderchildlist_expand, viewGroup, false);

        return new OrderChildList_VH(view);

    }

    @Override
    public void onBindParentViewHolder(@NonNull final OrderParentLIst_VH orderParentLIst_vh, final int position, @NonNull OrderParentlist_Model o) {
//    public void onBindParentViewHolder(final OrderParentLIst_VH orderParentLIst_vh, final int position, OrderParentlist_Model o) {
        // Log.i("debugOrder_object", String.valueOf(position));
        // Log.i("debugOrder_object1", String.valueOf(orderParentLIst_vh.getPosition()));
        final OrderParentlist_Model orderParentlist_model = (OrderParentlist_Model) o;
        orderParentLIst_vh._textview.setText(orderParentlist_model.getTitle());
        orderParentLIst_VH_main = orderParentLIst_vh;
    }


    @Override
    public void onBindChildViewHolder(@NonNull final OrderChildList_VH orderChildList_vh, int pos, final int i, @NonNull OrderChildlist_Model o) {
        final NonSwipeableViewPager viewPager = ((FragmentActivity) context).findViewById(R.id.view_pager_rpoid);
        orderChildList_vh.product_code.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPager.setCurrentItem(0, false);
            }
        });
        viewPager.setCurrentItem(0);


        //    public void onBindChildViewHolder(OrderChildList_VH orderChildList_vh, int pos, int i, OrderChildlist_Model o) {

//        // Log.i("debugOrder_o", String.valueOf(o));
        OrderChildlist_Model orderChildlist_model = (OrderChildlist_Model) o;
        final OrderChildList_VH temp_orderChildList_vh = orderChildList_vh;
        final int temp_i = i;
        final OrderChildlist_Model temp_orderChildlist_model = orderChildlist_model;

        int totalChildInThisParent = 0;
        for (int iter = 0; iter < productList.size(); iter++) {
            if (productList.get(iter).getProductCategoryId().equals(orderChildlist_model.getProductCategoryId()))
                totalChildInThisParent++;
        }
        // Log.i("totalChildInThisParent", String.valueOf(totalChildInThisParent));

        orderChildList_vh.list_txt_products.setText(orderChildlist_model.getTitle());
//        orderChildList_vh.list_product_code_value.setText(orderChildlist_model.getProductCode());
//        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//        if (orderChildlist_model.getProductUnitPrice() != null) {
//            String yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getProductUnitPrice()));
//            orderChildList_vh.list_price_value.setText("Rs. " + yourFormattedString1);
//        }
//        String yourFormattedString2;
//        if (orderChildlist_model.getDiscountAmount() != null)
//            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getDiscountAmount()));
//        else
//            yourFormattedString2 = formatter1.format(0);
//        orderChildList_vh.list_discount_value.setText("Rs. " + yourFormattedString2);
////        if (orderChildlist_model.getPackSize() != null)
////            orderChildList_vh.list_pack_size_value.setText(orderChildlist_model.getPackSize());
//        orderChildList_vh.list_UOM_value.setText(orderChildlist_model.getUnitOFMeasure());


        orderChildList_vh.product_code.setText(context.getResources().getString(R.string.product_code_for_adapter));
        SpannableString ss1 = new SpannableString(orderChildlist_model.getProductCode());
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        orderChildList_vh.product_code.append(ss1);

        orderChildList_vh.product_code.append("\n");

        orderChildList_vh.product_code.append(context.getResources().getString(R.string.price_adapter));

        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getProductUnitPrice()));

        ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        orderChildList_vh.product_code.append(ss1);


        if (orderChildlist_model.getUnitOFMeasure() != null && !orderChildlist_model.getUnitOFMeasure().equals("null")) {
            orderChildList_vh.product_code.append("\u00A0| ");

            orderChildList_vh.product_code.append(context.getResources().getString(R.string.UOM_adapter));
            String temp_uom = orderChildlist_model.getUnitOFMeasure().replaceAll(" ", "\u00A0");
            ss1 = new SpannableString(temp_uom);
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            orderChildList_vh.product_code.append(ss1);
        }

        if (orderChildlist_model.getDiscountAmount() != null && !orderChildlist_model.getDiscountAmount().equals("0") && !orderChildlist_model.getDiscountAmount().equals("") && !orderChildlist_model.getDiscountAmount().equals("null")) {
            orderChildList_vh.product_code.append("\u00A0| ");

            orderChildList_vh.product_code.append(context.getResources().getString(R.string.disc_adpter));

            formatter1 = new DecimalFormat("#,###,##0.00");
            yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getDiscountAmount()));

            ss1 = new SpannableString("Rs.\u00A0" + yourFormattedString1);
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            orderChildList_vh.product_code.append(ss1);
        }

        orderChildList_vh.list_numberOFitems.setText("");
        orderChildList_vh.list_numberOFitems.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                ((FragmentActivity) context). .getView().findFocus();
                // Log.i("focusdebugging", String.valueOf(v.findFocus()));


            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences orderCheckout1 = context.getSharedPreferences("FromDraft_Temp",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor orderCheckout_editor1 = orderCheckout1.edit();
                orderCheckout_editor1.putString("fromDraftChanged", "changed");
                orderCheckout_editor1.apply();

                if (orderChildList_vh.list_numberOFitems.hasFocus()) {
                    String str_quantity = String.valueOf(s);
                    // Log.i("textChanged12", "check");
                    // Log.i("textChanged11", "'" + String.valueOf(s) + "'");
                    if (String.valueOf(s).equals(""))
                        str_quantity = "0";

                    if (temp_orderChildList_vh.list_txt_products.getText().equals(temp_orderChildlist_model.getTitle())) {
//                    if (Float.parseFloat(str_quantity) <= 0) {
//                        // Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_LONG).show();
//                    } else {
                        // Log.i("debugOrder_textChang", String.valueOf(temp_orderChildlist_model.getTitle()));
                        // Log.i("debugOrder_textChang1", String.valueOf(temp_orderChildList_vh.list_txt_products.getText()));
                        checkOutEnabler(temp_orderChildList_vh, temp_i, temp_orderChildlist_model, str_quantity);
//                    }
                    }
                }
            }
        };
        orderChildList_vh.list_numberOFitems.addTextChangedListener(textWatcher);
        orderChildList_vh.list_numberOFitems.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i("order_debugKey_Code", String.valueOf(keyCode));
                // Log.i("order_debugKey_View", String.valueOf(v));
                // Log.i("order_debugKey_Event", String.valueOf(event));
                // Log.i("order_debugKey_Codeback", String.valueOf(KeyEvent.KEYCODE_BACK));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
////                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
//                    fragmentTransaction.add(R.id.main_container, new RetailerPlaceOrder()).addToBackStack("null");
//                    fragmentTransaction.commit();
//                    return true;

                    SharedPreferences orderCheckout = context.getSharedPreferences("orderCheckout",
                            Context.MODE_PRIVATE);
                    String orderCheckedOutStr = orderCheckout.getString("orderCheckout", "");
                    // Log.i("back_debug", orderCheckedOutStr + "'''");
                    // Log.i("back_debug123", String.valueOf(selectedProductsDataList.size()));

                    List<OrderChildlist_Model> selectedProductsDataList = new ArrayList<>();
                    List<String> selectedProductsQuantityList = new ArrayList<>();

                    SharedPreferences selectedProductsSP = context.getSharedPreferences("FromDraft_Temp",
                            Context.MODE_PRIVATE);

                    SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer",
                            Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    object_stringqty = selectedProducts.getString("selected_products_qty", "");
                    object_string = selectedProducts.getString("selected_products", "");
                    Type type = new TypeToken<List<OrderChildlist_Model>>() {
                    }.getType();
                    Type typeString = new TypeToken<List<String>>() {
                    }.getType();
                    if (!object_string.equals("") && !object_stringqty.equals("")) {
                        selectedProductsDataList = gson.fromJson(object_string, type);
                        selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
                    }


                    int quantity = 0;
                    if (selectedProductsQuantityList != null && selectedProductsQuantityList.size() > 0)
                        for (int i = 0; i < selectedProductsQuantityList.size(); i++) {
                            quantity += Integer.parseInt(selectedProductsQuantityList.get(i));
                        }

                    return executeBackStackFlow(selectedProductsSP, orderCheckedOutStr, quantity, selectedProductsDataList, selectedProductsQuantityList);


                }
//                // Log.i("order_place_debug", String.valueOf(KeyCode));

                return false;
            }
        });

        final int finalTotalChildInThisParent = totalChildInThisParent;

        orderChildList_vh.list_numberOFitems.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (finalTotalChildInThisParent == (i + 1)) {
                    // Log.i("order_place_debug8", "done clicked on last child");
                    InputMethodManager imm = (InputMethodManager) ((FragmentActivity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

//        orderChildList_vh.list_numberOFitems.setText("");
        if (selectedProductsDataList != null && selectedProductsQuantityList != null) {
            if (selectedProductsDataList.size() > 0 && selectedProductsQuantityList.size() > 0) {
//                // Log.i("debugOrderQty1_found0", String.valueOf(i));
//                // Log.i("debugOrderQty1_found1", String.valueOf(orderChildlist_model.getProductCode()));
//                // Log.i("debugOrderQty1_found2", String.valueOf(orderChildList_vh.list_product_code_value.getText()));
//                // Log.i("debugOrderQty1_found3", String.valueOf(orderChildlist_model.getTitle()));
//                // Log.i("debugOrderQty1_found4", String.valueOf(orderChildList_vh.list_txt_products.getText()));
                setQuantity(orderChildList_vh, orderChildlist_model, i);
            }
        }

//        orderChildList_vh.list_numberOFitems.removeTextChangedListener(textWatcher);    }
    }

    private boolean executeBackStackFlow(SharedPreferences selectedProductsSP, String orderCheckedOutStr, int quantity, List<OrderChildlist_Model> selectedProductsDataList, List<String> selectedProductsQuantityList) {
        FragmentTransaction fragmentTransaction;
        if (selectedProductsSP.getString("fromDraft", "").equals("draft")) {
            //draft flow
            if (selectedProductsSP.getString("fromDraftChanged", "").equals("changed")) {
                showDiscardDialog();
                return true;
            } else {
                fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard()).addToBackStack("null");
                fragmentTransaction.commit();
                return true;
            }
        } else {
            // place order flow
            if (((!orderCheckedOutStr.equals("")))) {
                showDiscardDialog();
                return true;
            } else {
                fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new RetailerPlaceOrder()).addToBackStack("null");
                fragmentTransaction.commit();
                return true;
            }
        }
    }

    private void showDiscardDialog() {
        // Log.i("CreatePayment", "In Dialog");
        final FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText(context.getResources().getString(R.string.discard_text));
        alertDialog.setView(view_popup);
        alertDialog.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.y = 200;
        layoutParams.x = -70;// top margin
        alertDialog.getWindow().setAttributes(layoutParams);
        Button btn_discard = (Button) view_popup.findViewById(R.id.btn_discard);
        btn_discard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Log.i("CreatePayment", "Button Clicked");
                alertDialog.dismiss();
                SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                editorOrderTabsFromDraft.putString("TabNo", "0");
                editorOrderTabsFromDraft.apply();

                SharedPreferences orderCheckout = context.getSharedPreferences("orderCheckout",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor orderCheckout_editor = orderCheckout.edit();
                orderCheckout_editor.putString("orderCheckout", "");
                orderCheckout_editor.apply();

                InputMethodManager imm = (InputMethodManager) ((FragmentActivity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view_popup.getWindowToken(), 0);

                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                fragmentTransaction.add(R.id.main_container, new RetailerOrderDashboard()).addToBackStack("null");
                fragmentTransaction.commit();


//                Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
//                ((FragmentActivity) getContext()).startActivity(login_intent);
//                ((FragmentActivity) getContext()).finish();

//                fm.popBackStack();
            }
        });

        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.btn_close);
        img_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        if(!alertDialog.isShowing())
        alertDialog.show();
    }

    private void setQuantity(OrderChildList_VH orderChildList_vh, OrderChildlist_Model orderChildlist_model, int pos) {
        if (selectedProductsQuantityList != null && selectedProductsDataList != null) {
            for (int j = 0; j < selectedProductsDataList.size(); j++) {
                if (orderChildList_vh.list_txt_products.getText().equals(String.valueOf(selectedProductsDataList.get(j).getTitle())) ) {
//                    // Log.i("debugOrderQty_found0", String.valueOf(j));
//                    // Log.i("debugOrderQty_found1", String.valueOf(pos));
//                    // Log.i("debugOrderQty_found2", String.valueOf(orderChildList_vh.list_txt_products.getText()));
//                    // Log.i("debugOrderQty_found3", String.valueOf(selectedProductsDataList.get(j).getTitle()));
//                    // Log.i("debugOrderQty_found4", String.valueOf(orderChildlist_model.getTitle()));
//                    // Log.i("debugOrderQty_found5", String.valueOf(orderChildList_vh.list_product_code_value.getText()));
//                    // Log.i("debugOrderQty_found6", String.valueOf(selectedProductsDataList.get(j).getProductCode()));
//                    // Log.i("debugOrderQty_found7", String.valueOf(orderChildlist_model.getProductCode()));
//                    // Log.i("debugOrderQty_found8", String.valueOf(orderChildList_vh));
                    if (!selectedProductsQuantityList.get(j).equals("0") && !selectedProductsQuantityList.get(j).equals(""))
                        orderChildList_vh.list_numberOFitems.setText(selectedProductsQuantityList.get(j));
//                    // Log.i("debugOrderQty_found0", String.valueOf(j));
//                    // Log.i("debugOrderQty_found1", String.valueOf(pos));
//                    // Log.i("debugOrderQty_found2", String.valueOf(orderChildList_vh.list_txt_products.getText()));
//                    // Log.i("debugOrderQty_found3", String.valueOf(selectedProductsDataList.get(j).getTitle()));
//                    // Log.i("debugOrderQty_found4", String.valueOf(orderChildlist_model.getTitle()));
//                    // Log.i("debugOrderQty_found5", String.valueOf(orderChildList_vh.list_product_code_value.getText()));
//                    // Log.i("debugOrderQty_found6", String.valueOf(selectedProductsDataList.get(j).getProductCode()));
//                    // Log.i("debugOrderQty_found7", String.valueOf(orderChildlist_model.getProductCode()));
//                    // Log.i("debugOrderQty_found8", String.valueOf(orderChildList_vh));
                }
            }
        }
    }

    private void checkOutEnabler(OrderChildList_VH holder, int position, OrderChildlist_Model orderChildlist_model, String s) {
        // Log.i("debugOrder_seldatalist", String.valueOf(selectedProductsDataList));
        if (selectedProductsDataList != null) {
            int foundIndex = -1;
            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                if (selectedProductsDataList.get(i).getTitle().equals(orderChildlist_model.getTitle())) {
                    foundIndex = i;
                    break;
                }
            }
            if (foundIndex != -1) {
                if (!String.valueOf(holder.list_numberOFitems.getText()).equals(""))
                    if (Integer.parseInt(String.valueOf(holder.list_numberOFitems.getText())) > 0) {
                        selectedProductsQuantityList.set(foundIndex, String.valueOf(holder.list_numberOFitems.getText()));
                    } else {
                        selectedProductsQuantityList.set(foundIndex, "0");
                    }
                else {
                    selectedProductsQuantityList.set(foundIndex, "0");
//                    selectedProductsQuantityList.remove(foundIndex);
                }
            } else {
                if (!String.valueOf(holder.list_numberOFitems.getText()).equals(""))
                    if (Integer.parseInt(String.valueOf(holder.list_numberOFitems.getText())) > 0) {
                        selectedProductsDataList.add(orderChildlist_model);
                        selectedProductsQuantityList.add(String.valueOf(holder.list_numberOFitems.getText()));
                    }
            }
        } else {
            if (!String.valueOf(holder.list_numberOFitems.getText()).equals("")) {
                if (Integer.parseInt(String.valueOf(holder.list_numberOFitems.getText())) > 0) {
                    selectedProductsDataList.add(orderChildlist_model);
                    selectedProductsQuantityList.add(String.valueOf(s));
                }
            }
        }

        // for (int i = 0; i < selectedProductsDataList.size(); i++)
        //     Toast.makeText(context, selectedProductsDataList.get(i).getTitle() + " - " + selectedProductsQuantityList.get(i), Toast.LENGTH_LONG).show();

        Gson gson = new Gson();
        String json = gson.toJson(selectedProductsDataList);
        String jsonqty = gson.toJson(selectedProductsQuantityList);
        // Log.i("debugOrder_jsonqty", jsonqty);
        // Log.i("debugOrder_json", json);
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_retailer",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", json);
        editor.putString("selected_products_qty", jsonqty);
        editor.apply();

        Quantity = 0;
        for (int i = 0; i < selectedProductsQuantityList.size(); i++) {
            Quantity = Quantity + Float.parseFloat(selectedProductsQuantityList.get(i));
        }
        if (Quantity > 0) {
            enableCheckoutButton();
        } else {
            disableCheckoutButton();
        }

    }


}
