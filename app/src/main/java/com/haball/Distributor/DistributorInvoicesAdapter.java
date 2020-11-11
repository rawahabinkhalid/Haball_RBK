package com.haball.Distributor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.haball.Distributor.ui.shipments.DistributorShipment_ViewDashboard;
import com.haball.Invoice.Distributor_Invoice_DashBoard;
import com.haball.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DistributorInvoicesAdapter extends RecyclerView.Adapter<DistributorInvoicesAdapter.ViewHolder> {
    private Context mContxt;
    List<DistributorInvoicesModel> invoicesList;

    public DistributorInvoicesAdapter(Context context, List<DistributorInvoicesModel> invoicesList) {
        this.mContxt = context;
        this.invoicesList = invoicesList;
        Log.i("Payments List => ", String.valueOf(invoicesList));
    }

    @NonNull
    @Override
    public DistributorInvoicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContxt).inflate(R.layout.payments_layout,parent,false);
        return new DistributorInvoicesAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorInvoicesAdapter.ViewHolder holder, int position) {
        holder.tv_heading.setText(invoicesList.get(position).getCompanyName());
        holder.tv_payment_id.setText(invoicesList.get(position).getInvoiceNumber());

        DecimalFormat formatter1 = new DecimalFormat("#,###,###.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(invoicesList.get(position).getTotalPrice()));
        holder.tv_amount.setText(yourFormattedString1);


        if(invoicesList.get(position).getState().equals("1")){
            holder.tv_status.setText("Consolidate");
        }
        else{
            holder.tv_status.setText("Normal");
        }
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
                                // Toast.makeText(mContxt,"View Invoice",Toast.LENGTH_LONG).show();
                                FragmentTransaction fragmentTransaction= ((FragmentActivity)mContxt).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container,new Distributor_Invoice_DashBoard());
                                fragmentTransaction.commit();

                                break;
                            case R.id.view_pdf:
                                // Toast.makeText(mContxt,"View PDF",Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_heading, tv_payment_id, tv_status, tv_amount;
        public ImageButton menu_btn;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_heading = itemView.findViewById(R.id.heading);
            tv_payment_id = itemView.findViewById(R.id.payment_id_value);
            tv_status = itemView.findViewById(R.id.status_value);
            tv_amount = itemView.findViewById(R.id.amount_value);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}
