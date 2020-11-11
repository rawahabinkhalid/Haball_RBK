package com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.RetailerPlaceOrder;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderChildlist_Model;
import com.haball.Distributor.ui.retailer.RetailerPlaceOrder.ui.main.Models.OrderParentlist_Model;
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
            Log.i("debugOrder_selProdQty", String.valueOf(object_stringqty));
            Log.i("debugOrder_selProd", String.valueOf(object_string));
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
        Log.i("debugOrder_object", String.valueOf(position));
        Log.i("debugOrder_object1", String.valueOf(orderParentLIst_vh.getPosition()));
        final OrderParentlist_Model orderParentlist_model = (OrderParentlist_Model) o;
        orderParentLIst_vh._textview.setText(orderParentlist_model.getTitle());
        orderParentLIst_VH_main = orderParentLIst_vh;
    }


    @Override
    public void onBindChildViewHolder(@NonNull final OrderChildList_VH orderChildList_vh, int pos, final int i, @NonNull OrderChildlist_Model o) {
//    public void onBindChildViewHolder(OrderChildList_VH orderChildList_vh, int pos, int i, OrderChildlist_Model o) {

//        Log.i("debugOrder_o", String.valueOf(o));
        OrderChildlist_Model orderChildlist_model = (OrderChildlist_Model) o;
        final OrderChildList_VH temp_orderChildList_vh = orderChildList_vh;
        final int temp_i = i;
        final OrderChildlist_Model temp_orderChildlist_model = orderChildlist_model;

        int totalChildInThisParent = 0;
        for (int iter = 0; iter < productList.size(); iter++) {
            if (productList.get(iter).getProductCategoryId().equals(orderChildlist_model.getProductCategoryId()))
                totalChildInThisParent++;
        }
        Log.i("totalChildInThisParent", String.valueOf(totalChildInThisParent));

        orderChildList_vh.list_txt_products.setText(orderChildlist_model.getTitle());
        orderChildList_vh.list_product_code_value.setText(orderChildlist_model.getProductCode());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        if (orderChildlist_model.getProductUnitPrice() != null) {
            String yourFormattedString1 = formatter1.format(Double.parseDouble(orderChildlist_model.getProductUnitPrice()));
            orderChildList_vh.list_price_value.setText("Rs. " + yourFormattedString1);
        }
        String yourFormattedString2;
        if (orderChildlist_model.getDiscountAmount() != null)
            yourFormattedString2 = formatter1.format(Double.parseDouble(orderChildlist_model.getDiscountAmount()));
        else
            yourFormattedString2 = formatter1.format(0);
        orderChildList_vh.list_discount_value.setText("Rs. " + yourFormattedString2);
//        if (orderChildlist_model.getPackSize() != null)
//            orderChildList_vh.list_pack_size_value.setText(orderChildlist_model.getPackSize());
        orderChildList_vh.list_UOM_value.setText(orderChildlist_model.getUnitOFMeasure());
        orderChildList_vh.list_numberOFitems.setText("");
        orderChildList_vh.list_numberOFitems.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                ((FragmentActivity) context). .getView().findFocus();
                Log.i("focusdebugging", String.valueOf(v.findFocus()));


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
                if (orderChildList_vh.list_numberOFitems.hasFocus()) {
                    String str_quantity = String.valueOf(s);
                    Log.i("textChanged12", "check");
                    Log.i("textChanged11", "'" + String.valueOf(s) + "'");
                    if (String.valueOf(s).equals(""))
                        str_quantity = "0";

                    if (temp_orderChildList_vh.list_txt_products.getText().equals(temp_orderChildlist_model.getTitle())) {
//                    if (Float.parseFloat(str_quantity) <= 0) {
//                        // Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_LONG).show();
//                    } else {
                        Log.i("debugOrder_textChang", String.valueOf(temp_orderChildlist_model.getTitle()));
                        Log.i("debugOrder_textChang1", String.valueOf(temp_orderChildList_vh.list_txt_products.getText()));
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
                Log.i("order_debugKey_Code", String.valueOf(keyCode));
                Log.i("order_debugKey_View", String.valueOf(v));
                Log.i("order_debugKey_Event", String.valueOf(event));
                Log.i("order_debugKey_Codeback", String.valueOf(KeyEvent.KEYCODE_BACK));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container, new Dist_OrderPlace()).addToBackStack("null");
                    fragmentTransaction.add(R.id.main_container, new RetailerPlaceOrder()).addToBackStack("null");
                    fragmentTransaction.commit();
                    return true;
                }
//                Log.i("order_place_debug", String.valueOf(KeyCode));

                return false;
            }
        });

        final int finalTotalChildInThisParent = totalChildInThisParent;

        orderChildList_vh.list_numberOFitems.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (finalTotalChildInThisParent == (i + 1)) {
                    Log.i("order_place_debug8", "done clicked on last child");
                    InputMethodManager imm = (InputMethodManager) ((FragmentActivity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

//        orderChildList_vh.list_numberOFitems.setText("");
        if (selectedProductsDataList != null && selectedProductsQuantityList != null) {
            if (selectedProductsDataList.size() > 0 && selectedProductsQuantityList.size() > 0) {
                Log.i("debugOrderQty1_found0", String.valueOf(i));
                Log.i("debugOrderQty1_found1", String.valueOf(orderChildlist_model.getProductCode()));
                Log.i("debugOrderQty1_found2", String.valueOf(orderChildList_vh.list_product_code_value.getText()));
                Log.i("debugOrderQty1_found3", String.valueOf(orderChildlist_model.getTitle()));
                Log.i("debugOrderQty1_found4", String.valueOf(orderChildList_vh.list_txt_products.getText()));
                setQuantity(orderChildList_vh, orderChildlist_model, i);
            }
        }

//        orderChildList_vh.list_numberOFitems.removeTextChangedListener(textWatcher);    }
    }

    private void setQuantity(OrderChildList_VH orderChildList_vh, OrderChildlist_Model orderChildlist_model, int pos) {
        if (selectedProductsQuantityList != null && selectedProductsDataList != null) {
            for (int j = 0; j < selectedProductsDataList.size(); j++) {
                if (orderChildList_vh.list_txt_products.getText().equals(String.valueOf(selectedProductsDataList.get(j).getTitle())) && orderChildList_vh.list_product_code_value.getText().equals(selectedProductsDataList.get(j).getProductCode())) {
                    Log.i("debugOrderQty_found0", String.valueOf(j));
                    Log.i("debugOrderQty_found1", String.valueOf(pos));
                    Log.i("debugOrderQty_found2", String.valueOf(orderChildList_vh.list_txt_products.getText()));
                    Log.i("debugOrderQty_found3", String.valueOf(selectedProductsDataList.get(j).getTitle()));
                    Log.i("debugOrderQty_found4", String.valueOf(orderChildlist_model.getTitle()));
                    Log.i("debugOrderQty_found5", String.valueOf(orderChildList_vh.list_product_code_value.getText()));
                    Log.i("debugOrderQty_found6", String.valueOf(selectedProductsDataList.get(j).getProductCode()));
                    Log.i("debugOrderQty_found7", String.valueOf(orderChildlist_model.getProductCode()));
                    Log.i("debugOrderQty_found8", String.valueOf(orderChildList_vh));
                    if (!selectedProductsQuantityList.get(j).equals("0") && !selectedProductsQuantityList.get(j).equals(""))
                        orderChildList_vh.list_numberOFitems.setText(selectedProductsQuantityList.get(j));
                    Log.i("debugOrderQty_found0", String.valueOf(j));
                    Log.i("debugOrderQty_found1", String.valueOf(pos));
                    Log.i("debugOrderQty_found2", String.valueOf(orderChildList_vh.list_txt_products.getText()));
                    Log.i("debugOrderQty_found3", String.valueOf(selectedProductsDataList.get(j).getTitle()));
                    Log.i("debugOrderQty_found4", String.valueOf(orderChildlist_model.getTitle()));
                    Log.i("debugOrderQty_found5", String.valueOf(orderChildList_vh.list_product_code_value.getText()));
                    Log.i("debugOrderQty_found6", String.valueOf(selectedProductsDataList.get(j).getProductCode()));
                    Log.i("debugOrderQty_found7", String.valueOf(orderChildlist_model.getProductCode()));
                    Log.i("debugOrderQty_found8", String.valueOf(orderChildList_vh));
                }
            }
        }
    }

    private void checkOutEnabler(OrderChildList_VH holder, int position, OrderChildlist_Model orderChildlist_model, String s) {
        Log.i("debugOrder_seldatalist", String.valueOf(selectedProductsDataList));
        if (selectedProductsDataList != null) {
            int foundIndex = -1;
            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                if (selectedProductsDataList.get(i).getTitle().equals(orderChildlist_model.getTitle()) && selectedProductsDataList.get(i).getProductCode().equals(orderChildlist_model.getProductCode())) {
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
        Log.i("debugOrder_jsonqty", jsonqty);
        Log.i("debugOrder_json", json);
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
