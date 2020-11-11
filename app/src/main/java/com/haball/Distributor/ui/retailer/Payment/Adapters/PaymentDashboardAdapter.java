package com.haball.Distributor.ui.retailer.Payment.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Distributor.ui.payments.PaymentScreen3Fragment;
import com.haball.Distributor.ui.retailer.Payment.Models.Dist_Retailer_Dashboard_Model;
import com.haball.Distributor.ui.retailer.Payment.PaymentDeleteOrder;
import com.haball.Distributor.ui.retailer.Payment.ViewPayment.RetailerPaymentView;
import com.haball.Distributor.ui.retailer.Payment.View_Payment_Fragment;
import com.haball.Distributor.ui.retailer.RetailerPayment.RetailerViewInvoice;
import com.haball.Distributor.ui.retailer.ViewInvoiceVoucher;
import com.haball.Distributor.ui.retailer.ViewVoucherRequest;
import com.haball.R;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentDashboardAdapter extends RecyclerView.Adapter<PaymentDashboardAdapter.PaymentDashboardVH> {
    private Context context;
    private List<Dist_Retailer_Dashboard_Model> paymentsList;
    private FragmentTransaction fragmentTransaction;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Activity activity;

    public PaymentDashboardAdapter(Activity activity, Context context, List<Dist_Retailer_Dashboard_Model> paymentsList) {
        this.context = context;
        this.paymentsList = paymentsList;
        this.activity = activity;
    }

    @Override
    public PaymentDashboardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.dist_retailer_payments, parent, false);
        return new PaymentDashboardAdapter.PaymentDashboardVH(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentDashboardVH holder, final int position) {
        if (paymentsList.size() == 3 || paymentsList.size() == 4) {
            if (position == (paymentsList.size() - 1)) {
                //        if (position == 2) {
                Log.i("DebugSupportFilter_In", paymentsList.get(position).getInvoiceNumber());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 50, 0, 360);
                holder.main_layout_payment_box_retailer.setLayoutParams(params);
            }
        }

        holder.tv_heading.setText(paymentsList.get(position).getCompanyName());
        holder.tv_payment_id.setText(paymentsList.get(position).getInvoiceNumber());

        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Double.parseDouble(paymentsList.get(position).getTotalPrice()));
        holder.tv_amount.setText("Rs. " + yourFormattedString1);

//        if(paymentsList.get(position).getStatus().equals("0")){
//            holder.tv_status.setText("Paid");
//        }
//        else{
//            holder.tv_status.setText("Unpaid");
//        }
        holder.tv_status.setText(paymentsList.get(position).getInvoiceStatusValue());
        final int finalPosition = position;
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (paymentsList.get(position).getInvoiceStatusValue().equals("Pending") || paymentsList.get(position).getInvoiceStatusValue().equals("Unpaid") || paymentsList.get(position).getInvoiceStatusValue().equals("Cancelled") || paymentsList.get(position).getInvoiceStatusValue().equals("Cancelled")) {
//                    // final PopupMenu popup = new PopupMenu(context, view);
//                    Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
//                    final PopupMenu popup = new PopupMenu(wrapper, view);
//                    MenuInflater inflater = popup.getMenuInflater();
//                    inflater.inflate(R.menu.payment_dashboard_menu, popup.getMenu());
//                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.view_payment:
//                                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.main_container, new RetailerPaymentView()).addToBackStack("Tag");
//                                    fragmentTransaction.commit();
//                                    SharedPreferences PaymentId = ((FragmentActivity) context).getSharedPreferences("PaymentId",
//                                            Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = PaymentId.edit();
//                                    editor.putString("PaymentId", paymentsList.get(finalPosition).getRetailerInvoiceId());
//                                    editor.putString("Status", paymentsList.get(finalPosition).getInvoiceStatusValue());
//                                    editor.putString("IsEditable", paymentsList.get(finalPosition).getIsEditable());
//                                    editor.commit();
//
//                                    break;
//                                case R.id.view_payment_cancel:
//                                    Toast.makeText(context, "Cancel button pressed", Toast.LENGTH_SHORT).show();
//                            }
//                            return false;
//                        }
//                    });
//                    popup.show();
//
//                } else {
//                    // final PopupMenu popup = new PopupMenu(context, view);
//                    Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
//                    final PopupMenu popup = new PopupMenu(wrapper, view);
//
//                    MenuInflater inflater = popup.getMenuInflater();
//                    inflater.inflate(R.menu.retailer_dashboard_view_menu, popup.getMenu());
//                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()) {
//                                case R.id.view_view:
//                                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.main_container, new RetailerPaymentView()).addToBackStack("Tag");
//                                    fragmentTransaction.commit();
//                                    SharedPreferences PaymentId = ((FragmentActivity) context).getSharedPreferences("PaymentId",
//                                            Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = PaymentId.edit();
//                                    editor.putString("PaymentId", paymentsList.get(finalPosition).getRetailerInvoiceId());
//                                    editor.putString("Status", paymentsList.get(finalPosition).getInvoiceStatusValue());
//                                    editor.putString("IsEditable", paymentsList.get(finalPosition).getIsEditable());
//                                    editor.commit();
//
//                                    break;
//                            }
//                            return false;
//                        }
//                    });
//                    popup.show();
//
//
//                }
                if (paymentsList.get(position).getIsEditable().equals("0")) {
                    if (paymentsList.get(position).getInvoiceStatusValue().equals("Unpaid")) {
                        Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                        final PopupMenu popup = new PopupMenu(wrapper, view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.retailer_on_behalf_payment_invoice_action_buttons, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.view_retailer_payment:
                                        SharedPreferences PaymentId = ((FragmentActivity) context).getSharedPreferences("PaymentId",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = PaymentId.edit();
                                        editor.putString("PaymentId", paymentsList.get(finalPosition).getRetailerInvoiceId());
                                        editor.putString("Status", paymentsList.get(finalPosition).getInvoiceStatusValue());
                                        editor.putString("ReferenceNumber", paymentsList.get(finalPosition).getInvoiceReferenceNumber());
                                        editor.putString("InvoiceStatus", paymentsList.get(finalPosition).getInvoiceStatusValue());
                                        editor.putString("IsEditable", paymentsList.get(finalPosition).getIsEditable());
                                        editor.commit();
                                        fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.main_container, new RetailerViewInvoice()).addToBackStack("tag");
                                        fragmentTransaction.commit();

                                        break;

                                    case R.id.pay_by_retailer:

//                        setUnpaidPaymentMenu(position, view);
//                        //handle menu3 click
                                        final AlertDialog alertDialog2 = new AlertDialog.Builder(context).create();
                                        LayoutInflater inflater2 = LayoutInflater.from(context);
                                        View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                                        alertDialog2.setView(view_popup2);
                                        alertDialog2.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                                        WindowManager.LayoutParams layoutParams = alertDialog2.getWindow().getAttributes();
                                        layoutParams.y = 200;
                                        layoutParams.x = -70;// top margin
                                        alertDialog2.getWindow().setAttributes(layoutParams);
                                        alertDialog2.show();
                                        ImageButton img_close = view_popup2.findViewById(R.id.image_button_close);
                                        TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
                                        payment_information_txt3.setText(paymentsList.get(position).getInvoiceNumber());
                                        Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
                                        btn_view_voucher.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (checkAndRequestPermissions()) {
                                                    try {
                                                        viewInvoicePDF(context, paymentsList.get(position).getRetailerInvoiceId());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });
                                        img_close.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog2.dismiss();
                                            }
                                        });

//                        Toast.makeText(context, "pay by", Toast.LENGTH_LONG).show();
//                        String paymentId = paymentsList.get(position).getID();
//                        deletePayment(context, paymentsList.get(position).getRetailerInvoiceId(), paymentsList.get(position).getInvoiceNumber());


                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();

//                    } else if (paymentsList.get(position).getInvoiceStatusValue().equals("Pending") || paymentsList.get(position).getInvoiceStatusValue().equals("Paid") || paymentsList.get(position).getInvoiceStatusValue().equals("Payment Processing") || paymentsList.get(position).getInvoiceStatusValue().equals("Cancelled") || paymentsList.get(position).getInvoiceStatusValue().equals("Invoiced")) {
                    } else {
                        Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                        final PopupMenu popup = new PopupMenu(wrapper, view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.orders_fragment_menu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.orders_view:
                                        SharedPreferences OrderId = ((FragmentActivity) context).getSharedPreferences("PaymentId",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = OrderId.edit();
                                        editor.putString("PaymentId", paymentsList.get(position).getRetailerInvoiceId());
                                        editor.putString("InvoiceStatus", String.valueOf(paymentsList.get(position).getInvoiceStatusValue()));
                                        Log.i("InvoiceStatus_Adapter", String.valueOf(paymentsList.get(position).getStatus()));
                                        editor.commit();
                                        fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.main_container, new RetailerViewInvoice()).addToBackStack("tag");
                                        fragmentTransaction.commit();

                                        break;
//                                case R.id.view_pdf:
//                                    // Toast.makeText(context, "View PDF", Toast.LENGTH_LONG).show();
//                                    break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                } else if (paymentsList.get(position).getIsEditable().equals("1")) {
                    if (paymentsList.get(position).getInvoiceStatusValue().equals("Un-Paid")) {
                        setUnpaidPaymentMenu(position, view);
                    } else if (paymentsList.get(position).getInvoiceStatusValue().equals("Invoiced") || paymentsList.get(position).getInvoiceStatusValue().equals("Pending") || paymentsList.get(position).getInvoiceStatusValue().equals("Paid") || paymentsList.get(position).getInvoiceStatusValue().equals("Payment Processing") || paymentsList.get(position).getInvoiceStatusValue().equals("Cancelled")) {
//                    } else if (paymentsList.get(position).getStatus().equals("Paid")) {
                        setPaidPaymentMenu(position, view);
                    }
                }

            }


        });
    }

    private void deletePayment(final Context context, final String retailerInvoiceId, final String invoiceNumber) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view_popup = inflater.inflate(R.layout.discard_changes, null);
        TextView tv_discard = view_popup.findViewById(R.id.tv_discard);
        tv_discard.setText("Delete Payment");
        TextView tv_discard_txt = view_popup.findViewById(R.id.tv_discard_txt);
        tv_discard_txt.setText("Are you sure, you want to delete this payment?");
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
                alertDialog.dismiss();

                PaymentDeleteOrder delete = new PaymentDeleteOrder();
                delete.deleteOrder(context, retailerInvoiceId, invoiceNumber);
//                Intent login_intent = new Intent(context, RetailorDashboard.class);
//                context.startActivity(login_intent);
//                ((FragmentActivity) context).finish();

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

    private void setUnpaidPaymentMenu(final int position, final View view) {
        Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
        final PopupMenu popup = new PopupMenu(wrapper, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.retailer_on_behalf_payment_action_buttons, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_retailer_payment:
                        SharedPreferences PrePaidNumberEdit = context.getSharedPreferences("PrePaidNumber",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorEdit = PrePaidNumberEdit.edit();
                        editorEdit.putString("PrePaidNumber", paymentsList.get(position).getInvoiceNumber());
                        editorEdit.putString("PrePaidId", paymentsList.get(position).getRetailerInvoiceId());
                        editorEdit.putString("CompanyName", paymentsList.get(position).getCompanyName());
                        editorEdit.putString("Amount", paymentsList.get(position).getTotalPrice());
                        editorEdit.putString("MenuItem", "Edit");
                        editorEdit.apply();
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new PaymentScreen3Fragment()).addToBackStack("Tag");
                        fragmentTransaction.commit();
                        break;
                    case R.id.delete_retailer_payment:
                        deletePayment(context, paymentsList.get(position).getRetailerInvoiceId(), paymentsList.get(position).getInvoiceNumber());
                        break;
                    case R.id.view_retailer_payment:
//                        Toast.makeText(context, "Edit Clicked", Toast.LENGTH_LONG).show();
//                        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                        LayoutInflater inflater = LayoutInflater.from(context);
//                        View view_popup = inflater.inflate(R.layout.edit_payment_request, null);
//                        alertDialog.setView(view_popup);
//                        Button btn_update = (Button) view_popup.findViewById(R.id.btn_payment_request_update);
//                        btn_update.setOnClickListener(new View.OnClickListener() {
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                                //    Toast.makeText(context,"Update",Toast.LENGTH_LONG).show();
//                                final AlertDialog alertDialog1 = new AlertDialog.Builder(context).create();
//                                LayoutInflater inflater = LayoutInflater.from(context);
//                                View view_popup = inflater.inflate(R.layout.edit_request_payment_success, null);
//                                alertDialog1.setView(view_popup);
//                                ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_success);
//                                img_email.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        alertDialog1.dismiss();
//                                    }
//                                });
//                                alertDialog1.show();
//
//                            }
//                        });
//                        ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_payment_request);
//                        img_email.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//
//                            }
//                        });
//                        alertDialog.show();
                        SharedPreferences PrePaidNumberView = context.getSharedPreferences("PrePaidNumber",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorView = PrePaidNumberView.edit();
                        editorView.putString("PrePaidNumber", paymentsList.get(position).getInvoiceNumber());
                        editorView.putString("PrePaidId", paymentsList.get(position).getRetailerInvoiceId());
                        editorView.putString("CompanyName", paymentsList.get(position).getCompanyName());
                        editorView.putString("Amount", paymentsList.get(position).getTotalPrice());
                        editorView.putString("MenuItem", "View");
                        editorView.apply();

                        FragmentTransaction fragmentTransactionView = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransactionView.add(R.id.main_container, new PaymentScreen3Fragment()).addToBackStack("Tag");
                        fragmentTransactionView.commit();

                        break;
                    case R.id.pay_by_retailer:
//                        setUnpaidPaymentMenu(position, view);
//                        //handle menu3 click
                        final AlertDialog alertDialog2 = new AlertDialog.Builder(context).create();
                        LayoutInflater inflater2 = LayoutInflater.from(context);
                        View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                        alertDialog2.setView(view_popup2);
                        alertDialog2.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                        WindowManager.LayoutParams layoutParams = alertDialog2.getWindow().getAttributes();
                        layoutParams.y = 200;
                        layoutParams.x = -70;// top margin
                        alertDialog2.getWindow().setAttributes(layoutParams);
                        alertDialog2.show();
                        ImageButton img_close = view_popup2.findViewById(R.id.image_button_close);
                        TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
                        payment_information_txt3.setText(paymentsList.get(position).getInvoiceNumber());
                        Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
                        btn_view_voucher.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkAndRequestPermissions()) {
                                    try {
                                        viewPDF(context, paymentsList.get(position).getRetailerInvoiceId());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        img_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog2.dismiss();
                            }
                        });

//                        Toast.makeText(context, "pay by", Toast.LENGTH_LONG).show();
//                        String paymentId = paymentsList.get(position).getID();
//                        deletePayment(context, paymentsList.get(position).getRetailerInvoiceId(), paymentsList.get(position).getInvoiceNumber());


                        break;
                }
                return false;
            }
        });
        popup.show();
    }


    private void setPaidPaymentMenu(final int position, View view) {
        Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
        final PopupMenu popup = new PopupMenu(wrapper, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.retailer_dashboard_view_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.view_view:
                        SharedPreferences paymentsRequestListID = ((FragmentActivity) context).getSharedPreferences("paymentsRequestListID",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = paymentsRequestListID.edit();
                        editor.putString("paymentsRequestListID", paymentsList.get(position).getRetailerInvoiceId());
                        editor.commit();

                        fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container, new View_Payment_Fragment()).addToBackStack("tag");
                        fragmentTransaction.commit();
                        break;
                }
                return false;
            }
        });
        popup.show();
    }


    private void viewPDF(Context context, String ID) throws JSONException {
        ViewVoucherRequest viewPDFRequest = new ViewVoucherRequest();
        viewPDFRequest.viewPDF(context, ID);
    }

    private void viewInvoicePDF(Context context, String ID) throws JSONException {
        ViewInvoiceVoucher viewPDFRequest = new ViewInvoiceVoucher();
        viewPDFRequest.viewPDF(context, ID);
    }

    private boolean checkAndRequestPermissions() {
        int permissionRead = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return paymentsList.size();
    }

    public static class PaymentDashboardVH extends RecyclerView.ViewHolder {
        TextView tv_heading, tv_payment_id, tv_status, tv_amount;
        public RelativeLayout main_layout_payment_box_retailer;
        public ImageButton menu_btn;

        public PaymentDashboardVH(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_payment_id = itemView.findViewById(R.id.dist_ret_payment_id_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.dist_ret_amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            main_layout_payment_box_retailer = itemView.findViewById(R.id.main_layout_payment_box_retailer);

        }


    }
}
