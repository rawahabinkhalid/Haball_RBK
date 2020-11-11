package com.haball.Distributor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.haball.Distributor.ui.main.PlaceholderFragment;
import com.haball.Distributor.ui.payments.CreatePaymentRequestFragment;
import com.haball.Distributor.ui.payments.PaymentRequestDashboard;
import com.haball.Distributor.ui.payments.PaymentsSummaryFragment;
import com.haball.Distributor.ui.payments.ViewPDFRequest;
import com.haball.Invoice.Distributor_Invoice_DashBoard;
import com.haball.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DistributorPaymentsAdapter extends RecyclerView.Adapter<DistributorPaymentsAdapter.ViewHolder> {
    private Context mContxt;
    List<DistributorPaymentsModel> paymentsList;
    List<DistributorInvoicesModel> invoiceList;
    int size;

    public DistributorPaymentsAdapter(Context context, List<DistributorPaymentsModel> paymentsList, List<DistributorInvoicesModel> invoiceList) {
        this.mContxt = context;
        this.paymentsList = paymentsList;
        this.invoiceList = invoiceList;
        if (invoiceList != null && paymentsList != null) {
            size = paymentsList.size() + invoiceList.size();
            Log.i("(size) if", String.valueOf(size));
            Log.i("Payments List (size) if", String.valueOf(paymentsList.size()));
            Log.i("Invoice List (size) if", String.valueOf(paymentsList.size()));
        } else if (paymentsList != null) {
            size = paymentsList.size();
            Log.i("(size) elseif", String.valueOf(size));

            Log.i("Payments List(size)elif", String.valueOf(paymentsList.size()));

        } else if (invoiceList != null) {
            size = invoiceList.size();
            Log.i("(size) elseif", String.valueOf(size));

            Log.i("Invoice List(size)elif", String.valueOf(paymentsList.size()));

        }

    }

    @NonNull
    @Override
    public DistributorPaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.payments_layout, parent, false);
        return new DistributorPaymentsAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorPaymentsAdapter.ViewHolder holder, int position) {
        Log.i("POSITION .. ", String.valueOf(position));

        if (position < paymentsList.size()) {
            holder.tv_state.setVisibility(View.GONE);
            holder.tv_state_value.setVisibility(View.GONE);
            holder.menu_btn.setVisibility(View.GONE);
            holder.tv_heading.setText(paymentsList.get(position).getName());
            holder.tv_payment_id.setText(paymentsList.get(position).getPrePaidNumber());

            DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
            String yourFormattedString1 = formatter1.format(Integer.parseInt(paymentsList.get(position).getPaidAmount()));
            holder.tv_amount.setText(yourFormattedString1);

            if (paymentsList.get(position).getStatus().equals("1")) {
                holder.tv_status.setText("Paid");
            } else {
                holder.tv_status.setText("Unpaid");
            }
        } else {
            position = position - paymentsList.size();
            if (invoiceList != null) {
                holder.tv_state.setVisibility(View.VISIBLE);
                holder.tv_state_value.setVisibility(View.VISIBLE);
                holder.menu_btn.setVisibility(View.VISIBLE);

                holder.tv_heading.setText(invoiceList.get(position).getCompanyName());
                holder.tv_payment_id.setText(invoiceList.get(position).getInvoiceNumber());

                DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
                String yourFormattedString1 = formatter1.format(Integer.parseInt(invoiceList.get(position).getTotalPrice()));
                holder.tv_amount.setText(yourFormattedString1);
                holder.tv_status.setText(invoiceList.get(position).getInvoiceStatusValue());

                if (invoiceList.get(position).getState().equals("1")) {
                    holder.tv_state_value.setText("Consolidate");
                } else {
                    holder.tv_state_value.setText("Normal");
                }
                final int finalPosition = position;
                holder.menu_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // final PopupMenu popup = new PopupMenu(mContxt, view);
                        Context wrapper = new ContextThemeWrapper(mContxt, R.style.AppBaseTheme);
                        final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.payment_invoice_menu, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.view_invoice:
                                        // Toast.makeText(mContxt, "View Invoice", Toast.LENGTH_LONG).show();

                                        FragmentTransaction fragmentTransaction = ((FragmentActivity) mContxt).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.main_container, new Distributor_Invoice_DashBoard());
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();

                                        SharedPreferences shipmentid = ((FragmentActivity) mContxt).getSharedPreferences("Invoice_ID",
                                                Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = shipmentid.edit();
                                        editor.putString("InvoiceID", invoiceList.get(finalPosition).getID());
                                        editor.commit();

                                        break;
                                    case R.id.view_pdf:
//                                        Toast.makeText(mContxt, "View PDF", Toast.LENGTH_LONG).show();
                                        try {
                                            viewPDF(invoiceList.get(finalPosition).getID());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });
            }
        }
    }

    private void viewPDF(String ID) throws JSONException {
        ViewPDFRequest viewPDFRequest = new ViewPDFRequest();
        viewPDFRequest.viewPDF(mContxt, ID);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_heading, tv_payment_id, tv_status, tv_amount, tv_state, tv_state_value;
        public ImageButton menu_btn;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_payment_id = itemView.findViewById(R.id.payment_id_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_state_value = itemView.findViewById(R.id.tv_state_value);

        }
    }
}
