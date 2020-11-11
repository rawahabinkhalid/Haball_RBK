package com.haball.Payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.Invoice.Distributor_Invoice_DashBoard;
import com.haball.R;

import java.text.DecimalFormat;
import java.util.List;
public class Consolidate_Fragment_View_Adapter extends RecyclerView.Adapter<Consolidate_Fragment_View_Adapter.ViewHolder> {

    private Context context;
    private List<Consolidate_Fragment_Model> consolidatePaymentsDetailList;
    private FragmentTransaction fragmentTransaction;

    public Consolidate_Fragment_View_Adapter(Context context, List<Consolidate_Fragment_Model> consolidatePaymentsDetailsList) {
        this.context = context;
        this.consolidatePaymentsDetailList = consolidatePaymentsDetailsList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(context).inflate(R.layout.payments_consolidate_view_recycler,parent,false);
        return new Consolidate_Fragment_View_Adapter.ViewHolder(view_inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.consolidate_invoice_no_view.setText(consolidatePaymentsDetailList.get(position).getInvoiceNumber());
        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
        String yourFormattedString1 = formatter1.format(Integer.parseInt(consolidatePaymentsDetailList.get(position).getTotalPrice()));
        holder.consolidate_amount_value.setText(yourFormattedString1);
        if(consolidatePaymentsDetailList.get(position).getInvoiceType().equals("0"))
            holder.consolidate_status_view_current.setText("Pending");
        else if (consolidatePaymentsDetailList.get(position).getInvoiceType().equals("1"))
            holder.consolidate_status_view_current.setText("Perfoma Invoice");
        else if (consolidatePaymentsDetailList.get(position).getInvoiceType().equals("2"))
            holder.consolidate_status_view_current.setText("Partially Paid");
        else if (consolidatePaymentsDetailList.get(position).getInvoiceType().equals("3"))
            holder.consolidate_status_view_current.setText("Paid");
        else if (consolidatePaymentsDetailList.get(position).getInvoiceType().equals("-1"))
            holder.consolidate_status_view_current.setText("Payment Processing");

        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // final PopupMenu popup = new PopupMenu(context, view);
                Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                final PopupMenu popup = new androidx.appcompat.widget.PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.cosolidate_payment_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.consiladate_view:
                                // Toast.makeText(context,"View Invoice",Toast.LENGTH_LONG).show();

                                SharedPreferences shipmentid = ((FragmentActivity) context).getSharedPreferences("Invoice_ID",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = shipmentid.edit();
                                editor.putString("InvoiceID", consolidatePaymentsDetailList.get(position).getID());
                                editor.commit();

                                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container, new Distributor_Invoice_DashBoard());
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
//
//                                        FragmentTransaction fragmentTransaction= ((FragmentActivity)mContxt).getSupportFragmentManager().beginTransaction();
//                                        fragmentTransaction.add(R.id.main_container,new Distributor_Invoice_DashBoard());
//                                        fragmentTransaction.commit();

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
        return consolidatePaymentsDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView consolidate_invoice_no_view ,consolidate_amount_value,consolidate_status_view_current;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            consolidate_invoice_no_view = itemView.findViewById(R.id.consolidate_invoice_no_view);
            consolidate_amount_value = itemView.findViewById(R.id.consolidate_amount_value);
            consolidate_status_view_current = itemView.findViewById(R.id.consolidate_status_view_current);
            menu_btn = itemView.findViewById(R.id.consolidate_menu_btn);
        }
    }
}
