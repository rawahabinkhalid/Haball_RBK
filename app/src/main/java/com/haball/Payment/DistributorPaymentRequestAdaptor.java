package com.haball.Payment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.haball.Distribution_Login.Distribution_Login;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.ui.main.invoice.ViewInvoice;
import com.haball.Distributor.ui.payments.EditPayment;
import com.haball.Distributor.ui.payments.EditPaymentRequestFragment;
import com.haball.Distributor.ui.payments.PaymentScreen3Fragment;
import com.haball.Distributor.ui.payments.Payments_Fragment;
import com.haball.Distributor.ui.payments.ViewVoucherRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.SSL_HandShake;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class
DistributorPaymentRequestAdaptor extends RecyclerView.Adapter<DistributorPaymentRequestAdaptor.ViewHolder> {
    private Context context;
    private List<DistributorPaymentRequestModel> paymentsRequestList;
    private Button btn_update;
    public ImageButton btn_back;
    private String company_names;
    List<String> CompanyNames = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterPayments;
    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Activity activity;
    public String URL_DELETE_PAYMENT = "https://175.107.203.97:4013/api/prepaidrequests/save";

    Spinner spinner;
    private FragmentTransaction fragmentTransaction;

    public DistributorPaymentRequestAdaptor(Activity activity, Context context, List<DistributorPaymentRequestModel> paymentsRequestList) {
        this.activity = activity;
        this.context = context;
        this.paymentsRequestList = paymentsRequestList;
    }

    @NonNull
    @Override
    public DistributorPaymentRequestAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.payments_layout, parent, false);
        return new DistributorPaymentRequestAdaptor.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (paymentsRequestList.size() == 3 || paymentsRequestList.size() == 4) {
            if (position == (paymentsRequestList.size() - 1)) {
                //        if (position == 2) {
                Log.i("DebugSupportFilter_In", paymentsRequestList.get(position).getPrePaidNumber());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 50, 0, 360);
                holder.main_layout_payment_box.setLayoutParams(params);
            }
        }
        holder.tv_state.setVisibility(View.GONE);
        holder.tv_state_value.setVisibility(View.GONE);
        holder.tv_heading.setText(paymentsRequestList.get(position).getCompanyName());
        holder.payment_id_value.setText(paymentsRequestList.get(position).getPrePaidNumber());
        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");


        String yourFormattedString1 = formatter1.format(Double.parseDouble(paymentsRequestList.get(position).getPaidAmount()));
        holder.amount_value.setText("Rs. " + yourFormattedString1);
//        if (paymentsRequestList.get(position).getStatus().equals("1"))
//            holder.status_value.setText("Paid");
//        else if (paymentsRequestList.get(position).getStatus().equals("0"))
//            holder.status_value.setText("Unpaid");
//        else if (paymentsRequestList.get(position).getStatus().equals("-1"))
//            holder.status_value.setText("Processing Payment");
        holder.status_value.setText(paymentsRequestList.get(position).getPrepaidStatusValue());


        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentsRequestList.get(position).getIsInvoice().equals("1")) {
                    if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Un-Paid")) {
//                        Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
//                        final PopupMenu popup = new PopupMenu(wrapper, view);
//                        MenuInflater inflater = popup.getMenuInflater();
//                        inflater.inflate(R.menu.distributor_payment_invoice_action_buttons, popup.getMenu());
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.view_retailer_payment:
//                                        SharedPreferences OrderId = ((FragmentActivity) context).getSharedPreferences("PaymentId",
//                                                Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = OrderId.edit();
//                                        editor.putString("PaymentId", paymentsRequestList.get(position).getID());
//                                        editor.putString("InvoiceStatus", String.valueOf(paymentsRequestList.get(position).getPrepaidStatusValue()));
//                                        Log.i("InvoiceStatus_Adapter", String.valueOf(paymentsRequestList.get(position).getPrepaidStatusValue()));
//                                        editor.commit();
//                                        fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
//                                        fragmentTransaction.add(R.id.main_container, new RetailerViewInvoice()).addToBackStack("tag");
//                                        fragmentTransaction.commit();
//
//                                        break;
//                                    case R.id.pay_by_retailer:
//
////                        setUnpaidPaymentMenu(position, view);
////                        //handle menu3 click
//                                        final AlertDialog alertDialog2 = new AlertDialog.Builder(context).create();
//                                        LayoutInflater inflater2 = LayoutInflater.from(context);
//                                        View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
//                                        alertDialog2.setView(view_popup2);
//                                        alertDialog2.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
//                                        WindowManager.LayoutParams layoutParams = alertDialog2.getWindow().getAttributes();
//                                        layoutParams.y = 200;
//                                        layoutParams.x = -70;// top margin
//                                        alertDialog2.getWindow().setAttributes(layoutParams);
//                                        alertDialog2.show();
//                                        ImageButton img_close = view_popup2.findViewById(R.id.image_button_close);
//                                        TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
//                                        payment_information_txt3.setText(paymentsRequestList.get(position).getPrePaidNumber());
//                                        Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
//                                        btn_view_voucher.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                if (checkAndRequestPermissions()) {
//                                                    try {
//                                                        viewInvoicePDF(context, paymentsRequestList.get(position).getID());
//                                                    } catch (JSONException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            }
//                                        });
//                                        img_close.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                alertDialog2.dismiss();
//                                            }
//                                        });
//
////                        Toast.makeText(context, "pay by", Toast.LENGTH_LONG).show();
////                        String paymentId = paymentsRequestList.get(position).getID();
////                        deletePayment(context, paymentsRequestList.get(position).getRetailerInvoiceId(), paymentsRequestList.get(position).getInvoiceNumber());
//
//
//                                        break;
//                                }
//                                return false;
//                            }
//                        });
//                        popup.show();

                    } else if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Pending") || paymentsRequestList.get(position).getPrepaidStatusValue().equals("Paid") || paymentsRequestList.get(position).getPrepaidStatusValue().equals("Payment Processing") || paymentsRequestList.get(position).getPrepaidStatusValue().equals("Cancelled")) {
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
                                        editor.putString("PaymentId", paymentsRequestList.get(position).getID());
                                        editor.putString("InvoiceStatus", String.valueOf(paymentsRequestList.get(position).getPrepaidStatusValue()));
                                        Log.i("InvoiceStatus_Adapter", String.valueOf(paymentsRequestList.get(position).getPrepaidStatusValue()));
                                        editor.commit();
                                        fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.main_container, new ViewInvoice()).addToBackStack("tag");
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
                } else if (paymentsRequestList.get(position).getIsInvoice().equals("0")) {
                    if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Paid")) {
                        // final PopupMenu popup = new PopupMenu(context, view);
                        Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                        final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
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
                                        editor.putString("paymentsRequestListID", paymentsRequestList.get(position).getID());
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

                    } else if (paymentsRequestList.get(position).getPrepaidStatusValue().equals("Unpaid")) {
                        // final PopupMenu popup = new PopupMenu(context, view);
                        Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                        final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.distributor_payment_action_buttons, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.view_retailer_payment:
//                                    View_Payment_Fragment view_Payment_Fragment = new View_Payment_Fragment();
//                                    SharedPreferences paymentsRequestListID = ((FragmentActivity) context).getSharedPreferences("paymentsRequestListID",
//                                            Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = paymentsRequestListID.edit();
//                                    editor.putString("paymentsRequestListID", paymentsRequestList.get(position).getID());
//                                    editor.commit();
//
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.main_container, view_Payment_Fragment);
//                                    fragmentTransaction.commit();
                                        SharedPreferences PrePaidNumber = context.getSharedPreferences("PrePaidNumber",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = PrePaidNumber.edit();
                                        editor.putString("PrePaidNumber", paymentsRequestList.get(position).getPrePaidNumber());
                                        editor.putString("PrePaidId", paymentsRequestList.get(position).getID());
                                        editor.putString("CompanyId", paymentsRequestList.get(position).getCompanyId());
                                        editor.putString("CompanyName", paymentsRequestList.get(position).getCompanyName());
                                        editor.putString("Amount", paymentsRequestList.get(position).getPaidAmount());
                                        editor.putString("MenuItem", "View");
                                        editor.apply();

                                        fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.main_container, new PaymentScreen3Fragment()).addToBackStack("tag");
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();

                                        break;
                                    case R.id.edit_retailer_payment:

                                        // Toast.makeText(context,"Edit Clicked",Toast.LENGTH_LONG).show();
//                                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                                    LayoutInflater inflater = LayoutInflater.from(context);
//                                    View view_popup = inflater.inflate(R.layout.edit_payment_request, null);
//                                    alertDialog.setView(view_popup);
//                                    spinner = view_popup.findViewById(R.id.spinner_conso);
//                                    Button btn_update = (Button) view_popup.findViewById(R.id.btn_payment_request_update);
//                                    btn_update.setOnClickListener(new View.OnClickListener() {
//                                        public void onClick(View v) {
//                                            alertDialog.dismiss();
//                                            //    Toast.makeText(context,"Update",Toast.LENGTH_LONG).show();
//                                            final AlertDialog alertDialog1 = new AlertDialog.Builder(context).create();
//                                            LayoutInflater inflater = LayoutInflater.from(context);
//                                            View view_popup = inflater.inflate(R.layout.edit_request_payment_success, null);
//                                            alertDialog1.setView(view_popup);
//                                            ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_success);
//                                            img_email.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View v) {
//                                                    alertDialog1.dismiss();
//                                                }
//                                            });
//                                            alertDialog1.show();
//
//                                        }
//                                    });
//
//
//
//                                    CompanyNames.add("Company *");
//                                    CompanyNames.add("ABC");
//                                    company_names = "";
//
//                                    arrayAdapterPayments = new ArrayAdapter<>(view_popup.getContext(),
//                                            android.R.layout.simple_spinner_dropdown_item, CompanyNames);
//
//                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                            if (i == 0) {
//                                                ((TextView) adapterView.getChildAt(0)).setTextColor(context.getResources().getColor(android.R.color.darker_gray));
//                                            } else {
//                                                company_names = CompanyNames.get(i);
//                                                Log.i("company name and id ", companyNameAndId.get(company_names));
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                        }
//                                    });
//
//
//
//                                    ImageButton img_email = (ImageButton) view_popup.findViewById(R.id.image_payment_request);
//                                    img_email.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            alertDialog.dismiss();
//
//                                        }
//                                    });
//
//                                    alertDialog.show();
//                                    arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                    arrayAdapterPayments.notifyDataSetChanged();
//                                    spinner.setAdapter(arrayAdapterPayments);
                                        SharedPreferences PrePaidNumberEdit = context.getSharedPreferences("PrePaidNumber",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editorEdit = PrePaidNumberEdit.edit();
                                        editorEdit.putString("PrePaidNumber", paymentsRequestList.get(position).getPrePaidNumber());
                                        editorEdit.putString("PrePaidId", paymentsRequestList.get(position).getID());
                                        editorEdit.putString("CompanyName", paymentsRequestList.get(position).getCompanyName());
                                        editorEdit.putString("Amount", paymentsRequestList.get(position).getPaidAmount());
                                        Gson gson = new Gson();
                                        editorEdit.putString("paymentsRequestList", gson.toJson(paymentsRequestList.get(position)));

                                        editorEdit.putString("MenuItem", "Edit");
                                        editorEdit.apply();

                                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.main_container, new PaymentScreen3Fragment()).addToBackStack("Tag");
                                        fragmentTransaction.commit();

                                        break;
                                    case R.id.pay_by_retailer:
                                        //handle menu2 click
                                        //  Toast.makeText(context,"Bank Clicked",Toast.LENGTH_LONG).show();
                                        final AlertDialog alertDialog2 = new AlertDialog.Builder(context).create();
                                        LayoutInflater inflater2 = LayoutInflater.from(context);
                                        View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                                        alertDialog2.setView(view_popup2);
                                        alertDialog2.show();
                                        TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
                                        payment_information_txt3.setText(paymentsRequestList.get(position).getPrePaidNumber());
                                        Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
                                        btn_view_voucher.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (checkAndRequestPermissions()) {
                                                    try {
                                                        viewPDF(context, paymentsRequestList.get(position).getID());
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });
                                        ImageButton img_close = (ImageButton) view_popup2.findViewById(R.id.image_button_close);
                                        img_close.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog2.dismiss();
                                            }
                                        });
                                        break;
                                    case R.id.delete_retailer_payment:
                                        //handle menu3 click
                                        deletePayment(context, position);
                             /*   final AlertDialog deleteAlert = new AlertDialog.Builder(mContxt).create();
                                LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
                                View delete_alert = delete_inflater.inflate(R.layout.delete_alert, null);
                                deleteAlert.setView(delete_alert);
                                Button btn_delete = (Button) delete_alert.findViewById(R.id.btn_delete);
                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        deleteAlert.dismiss();
                                        AlertDialog.Builder delete_successAlert = new AlertDialog.Builder(mContxt);
                                        LayoutInflater delete_inflater = LayoutInflater.from(mContxt);
                                        View delete_success_alert = delete_inflater.inflate(R.layout.delete_success, null);
                                        delete_successAlert.setView(delete_success_alert);
                                        delete_successAlert.show();
                                    }
                                });
                                deleteAlert.show();*/
                                        break;


                                    case R.id.payment_request_ebay:
                                        Toast.makeText(context, "Epay Clicked", Toast.LENGTH_LONG).show();
                                        break;
//                                case R.id.payment_request_download:
////                                        Toast.makeText(mContxt, "View PDF", Toast.LENGTH_LONG).show();
//                                    try {
//                                        viewPDF(context, paymentsRequestList.get(position).getID());
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                }
            }
        });
    }

//
//    private void viewInvoicePDF(Context context, String ID) throws JSONException {
//        ViewInvoiceVoucher viewPDFRequest = new ViewInvoiceVoucher();
//        viewPDFRequest.viewPDF(context, ID);
//    }

    private void deletePayment(final Context context, final int position) {

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

                try {
                    deletePayment_API(context, position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


    private void deletePayment_API(final Context context, final int position) throws JSONException {
        final Loader loader = new Loader(context);
        loader.showLoader();


        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String Token = sharedPreferences.getString("Login_Token", "");

        Log.i("Token Retailer ", Token);
        JSONObject map = new JSONObject();
        map.put("ActionValue", "6");
        map.put("BankIMD", paymentsRequestList.get(position).getBankIMD());
        map.put("CompanyCNIC", paymentsRequestList.get(position).getCompanyCNIC());
        map.put("CompanyId", paymentsRequestList.get(position).getCompanyId());
        map.put("CompanyName", paymentsRequestList.get(position).getCompanyName());
        map.put("CreatedBy", paymentsRequestList.get(position).getCreatedBy());
        map.put("CreatedDate", paymentsRequestList.get(position).getCreatedDate());
        map.put("DistributorCNIC", paymentsRequestList.get(position).getDistributorCNIC());
        map.put("DistributorId", paymentsRequestList.get(position).getDistributorId());
        map.put("DistributorName", paymentsRequestList.get(position).getDistributorName());
        map.put("ID", paymentsRequestList.get(position).getID());
        map.put("IsTransmitted", paymentsRequestList.get(position).getIsTransmitted());
        map.put("LastChangedBy", paymentsRequestList.get(position).getLastChangedBy());
        map.put("LastChangedDate", paymentsRequestList.get(position).getLastChangedDate());
        map.put("PaidAmount", paymentsRequestList.get(position).getPaidAmount());
        map.put("PaidDate", paymentsRequestList.get(position).getPaidDate());
        map.put("PrePaidNumber", paymentsRequestList.get(position).getPrePaidNumber());
        map.put("PrepaidStatusValue", paymentsRequestList.get(position).getPrepaidStatusValue());
        map.put("ReferenceID", paymentsRequestList.get(position).getReferenceID());
        map.put("State", paymentsRequestList.get(position).getState());
        map.put("Status", -1);
        map.put("employeesName", paymentsRequestList.get(position).getEmployeesName());


        final Context finalcontext = context;
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_DELETE_PAYMENT, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle the response
                loader.hideLoader();
                Log.i("paymentLog_Response", String.valueOf(response));
                final Dialog fbDialogue = new Dialog(context);
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
                tv_pr1.setText("Your Payment ID " + paymentsRequestList.get(position).getPrePaidNumber() + " has been deleted successfully.");
                txt_header1.setText("Payment Deleted");
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
                        SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "0");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) context), DistributorDashboard.class);
                        ((FragmentActivity) context).startActivity(login_intent);
                        ((FragmentActivity) context).finish();
                    }
                });
//                fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.main_container, new HomeFragment());
//                fragmentTransaction.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("paymentLog_Error", String.valueOf(error));
                loader.hideLoader();
                new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(request);
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

    private void viewPDF(Context context, String ID) throws JSONException {
        ViewVoucherRequest viewPDFRequest = new ViewVoucherRequest();
        viewPDFRequest.viewPDF(context, ID);
    }

    @Override
    public int getItemCount() {
        return paymentsRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading, payment_id_value, amount_value, status_value, tv_state, tv_state_value;
        public RelativeLayout main_layout_payment_box;
        public ImageButton menu_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            payment_id_value = itemView.findViewById(R.id.payment_id_value);
            amount_value = itemView.findViewById(R.id.amount_value);
            status_value = itemView.findViewById(R.id.status_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_state_value = itemView.findViewById(R.id.tv_state_value);
            main_layout_payment_box = itemView.findViewById(R.id.main_layout_payment_box_retailer);
        }
    }

    public void addListItem(List<DistributorPaymentRequestModel> list) {
        for (DistributorPaymentRequestModel plm : list) {
            paymentsRequestList.add(plm);
        }
        notifyDataSetChanged();
    }
}
