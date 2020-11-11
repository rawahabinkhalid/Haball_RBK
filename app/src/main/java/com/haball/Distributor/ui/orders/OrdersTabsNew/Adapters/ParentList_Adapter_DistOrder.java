package com.haball.Distributor.ui.orders.OrdersTabsNew.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderChildlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Models.OrderParentlist_Model_DistOrder;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Order_PlaceOrder;
import com.haball.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ParentList_Adapter_DistOrder extends ExpandableRecyclerAdapter<OrderParentlist_Model_DistOrder, OrderChildlist_Model_DistOrder, OrderParentList_VH_DistOrder, OrderChildList_VH_DistOrder> {
    LayoutInflater inflater;
    private Context context;
    public List<OrderChildlist_Model_DistOrder> selectedProductsDataList = new ArrayList<>();
    private List<String> selectedProductsQuantityList = new ArrayList<>();
    private List<String> selectedProductsCategoryList = new ArrayList<>();
    private String object_string, object_stringqty, object_stringcategory;
    private int pre_expanded = -1;
    public List<OrderParentList_VH_DistOrder> OrderParentList = new ArrayList<>();
    private int parentPosition = -1;
    private List<OrderParentlist_Model_DistOrder> parentItemList;
    private RelativeLayout filter_layout;
    private OrderParentList_VH_DistOrder orderParentLIst_VH_main;
    private Button btn_checkout;
    private double Quantity = 0;
    private List<OrderChildlist_Model_DistOrder> productList = new ArrayList<>();
    private Spinner spinner_conso;
    private String Category_selected;

    public ParentList_Adapter_DistOrder(Context context, List<OrderParentlist_Model_DistOrder> parentItemList, RelativeLayout filter_layout, Button btn_checkout, List<OrderChildlist_Model_DistOrder> productList, String Category_selected) {
        super(parentItemList);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.parentItemList = parentItemList;
        this.filter_layout = filter_layout;
        this.btn_checkout = btn_checkout;
        this.productList = productList;
        this.Category_selected = Category_selected;


        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        object_stringqty = selectedProducts.getString("selected_products_qty", "");
        object_stringcategory = selectedProducts.getString("selected_products_category", "");
        object_string = selectedProducts.getString("selected_products", "");
        Type type = new TypeToken<List<OrderChildlist_Model_DistOrder>>() {
        }.getType();
        Type typeString = new TypeToken<List<String>>() {
        }.getType();
        if (!object_string.equals("")) {
            selectedProductsDataList = gson.fromJson(object_string, type);
            selectedProductsQuantityList = gson.fromJson(object_stringqty, typeString);
            selectedProductsCategoryList = gson.fromJson(object_stringcategory, typeString);
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
    public OrderParentList_VH_DistOrder onCreateParentViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.parentlist_retailer_order, viewGroup, false);
        OrderParentList_VH_DistOrder OrderParentList_VH_DistOrder = new OrderParentList_VH_DistOrder(view, filter_layout);
        OrderParentList.add(OrderParentList_VH_DistOrder);
        return new OrderParentList_VH_DistOrder(view, filter_layout);

    }


    @Override
    public OrderChildList_VH_DistOrder onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.orderchildlist_expand, viewGroup, false);

        return new OrderChildList_VH_DistOrder(view);

    }

    @Override
    public void onBindParentViewHolder(@NonNull final OrderParentList_VH_DistOrder OrderParentList_VH_DistOrder, final int position, @NonNull OrderParentlist_Model_DistOrder o) {
        Log.i("debugOrder_object", String.valueOf(position));
        Log.i("debugOrder_object1", String.valueOf(OrderParentList_VH_DistOrder.getPosition()));
        final OrderParentlist_Model_DistOrder OrderParentlist_Model_DistOrder = (OrderParentlist_Model_DistOrder) o;
        OrderParentList_VH_DistOrder._textview.setText(OrderParentlist_Model_DistOrder.getTitle());

        orderParentLIst_VH_main = OrderParentList_VH_DistOrder;
    }


    @Override
    public void onBindChildViewHolder(@NonNull final OrderChildList_VH_DistOrder OrderChildList_VH_DistOrder, final int pos, final int i, @NonNull OrderChildlist_Model_DistOrder o) {
        OrderChildlist_Model_DistOrder OrderChildlist_Model_DistOrder = (OrderChildlist_Model_DistOrder) o;
        final OrderChildList_VH_DistOrder temp_orderChildList_vh = OrderChildList_VH_DistOrder;
        final int temp_i = i;
        final OrderChildlist_Model_DistOrder temp_orderChildlist_model = OrderChildlist_Model_DistOrder;

        int totalChildInThisParent = 0;
        for (int iter = 0; iter < productList.size(); iter++) {
            if (productList.get(iter).getCategoryId() != null)
                if (productList.get(iter).getCategoryId().equals(OrderChildlist_Model_DistOrder.getCategoryId()))
                    totalChildInThisParent++;
        }


        OrderChildList_VH_DistOrder.discount.setText("Pack Size: ");
        OrderChildList_VH_DistOrder.UOM.setText("Disc: ");

        OrderChildList_VH_DistOrder.list_txt_products.setText(OrderChildlist_Model_DistOrder.getTitle());
        OrderChildList_VH_DistOrder.list_product_code_value.setText(OrderChildlist_Model_DistOrder.getCode());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        if (OrderChildlist_Model_DistOrder.getUnitPrice() != null) {
            String yourFormattedString1 = formatter1.format(Double.parseDouble(OrderChildlist_Model_DistOrder.getUnitPrice()));
            OrderChildList_VH_DistOrder.list_price_value.setText("Rs. " + yourFormattedString1);
        }
        String yourFormattedString2;
        if (OrderChildlist_Model_DistOrder.getDiscountValue() != null)
            yourFormattedString2 = formatter1.format(Double.parseDouble(OrderChildlist_Model_DistOrder.getDiscountValue()));
        else
            yourFormattedString2 = formatter1.format(0);
        OrderChildList_VH_DistOrder.list_UOM_value.setText("Rs. " + yourFormattedString2);
        OrderChildList_VH_DistOrder.list_discount_value.setText(OrderChildlist_Model_DistOrder.getPackSize());
        OrderChildList_VH_DistOrder.list_numberOFitems.setText("");
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (OrderChildList_VH_DistOrder.list_numberOFitems.hasFocus()) {
                    String str_quantity = String.valueOf(s);
                    if (String.valueOf(s).equals(""))
                        str_quantity = "0";

                    if (temp_orderChildList_vh.list_txt_products.getText().equals(temp_orderChildlist_model.getTitle())) {
                        checkOutEnabler(temp_orderChildList_vh, temp_i, temp_orderChildlist_model, str_quantity);
                    }
                }
            }
        };
        OrderChildList_VH_DistOrder.list_numberOFitems.addTextChangedListener(textWatcher);
        OrderChildList_VH_DistOrder.list_numberOFitems.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("order_place_debug", String.valueOf(keyCode));
                Log.i("order_place_debug123123", String.valueOf(KeyEvent.KEYCODE_BACK));
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new Order_PlaceOrder()).addToBackStack("null");
                    fragmentTransaction.commit();
                    return true;
                }

                return false;
            }
        });
        final int finalTotalChildInThisParent = totalChildInThisParent;

        OrderChildList_VH_DistOrder.list_numberOFitems.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (finalTotalChildInThisParent == (i + 1)) {
                    Toast.makeText(context, pos + " - " + i, Toast.LENGTH_SHORT).show();

                    Log.i("order_place_debug8", "done clicked on last child");
                    InputMethodManager imm = (InputMethodManager) ((FragmentActivity) context).getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        if (selectedProductsDataList != null && selectedProductsQuantityList != null) {
            if (selectedProductsDataList.size() > 0 && selectedProductsQuantityList.size() > 0) {
                setQuantity(OrderChildList_VH_DistOrder, OrderChildlist_Model_DistOrder, i);
            }
        }

    }

    private void setQuantity(OrderChildList_VH_DistOrder OrderChildList_VH_DistOrder, OrderChildlist_Model_DistOrder OrderChildlist_Model_DistOrder, int pos) {
        if (selectedProductsQuantityList != null && selectedProductsDataList != null) {
            for (int j = 0; j < selectedProductsDataList.size(); j++) {
                if (OrderChildList_VH_DistOrder.list_txt_products.getText().equals(String.valueOf(selectedProductsDataList.get(j).getTitle())) && OrderChildList_VH_DistOrder.list_product_code_value.getText().equals(selectedProductsDataList.get(j).getCode())) {
                    if (!selectedProductsQuantityList.get(j).equals("0") && !selectedProductsQuantityList.get(j).equals(""))
                        OrderChildList_VH_DistOrder.list_numberOFitems.setText(selectedProductsQuantityList.get(j));
                }
            }
        }
    }

    private void checkOutEnabler(OrderChildList_VH_DistOrder holder, int position, OrderChildlist_Model_DistOrder OrderChildlist_Model_DistOrder, String s) {
        if (selectedProductsDataList != null) {
            int foundIndex = -1;
            for (int i = 0; i < selectedProductsDataList.size(); i++) {
                if (selectedProductsDataList.get(i).getTitle().equals(OrderChildlist_Model_DistOrder.getTitle()) && selectedProductsDataList.get(i).getCode().equals(OrderChildlist_Model_DistOrder.getCode())) {
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
                }
            } else {
                if (!String.valueOf(holder.list_numberOFitems.getText()).equals(""))
                    if (Integer.parseInt(String.valueOf(holder.list_numberOFitems.getText())) > 0) {
                        selectedProductsDataList.add(OrderChildlist_Model_DistOrder);
                        selectedProductsQuantityList.add(String.valueOf(holder.list_numberOFitems.getText()));
                    }
            }
        } else {
            if (!String.valueOf(holder.list_numberOFitems.getText()).equals("")) {
                if (Integer.parseInt(String.valueOf(holder.list_numberOFitems.getText())) > 0) {
                    selectedProductsDataList.add(OrderChildlist_Model_DistOrder);
                    selectedProductsQuantityList.add(String.valueOf(s));
                }
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(selectedProductsDataList);
        String jsonqty = gson.toJson(selectedProductsQuantityList);
        String jsoncategory = gson.toJson(selectedProductsCategoryList);
        SharedPreferences selectedProducts = context.getSharedPreferences("selectedProducts_distributor",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = selectedProducts.edit();
        editor.putString("selected_products", json);
        editor.putString("selected_products_qty", jsonqty);
        editor.putString("selected_products_category", jsoncategory);
        editor.apply();

        Quantity = 0;
        for (
                int i = 0; i < selectedProductsQuantityList.size(); i++) {
            Quantity = Quantity + Float.parseFloat(selectedProductsQuantityList.get(i));
        }
        if (Quantity > 0) {
            enableCheckoutButton();
        } else {
            disableCheckoutButton();
        }

    }
}