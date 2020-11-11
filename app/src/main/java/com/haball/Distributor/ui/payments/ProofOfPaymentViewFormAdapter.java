package com.haball.Distributor.ui.payments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haball.Payment.ConsolidatePaymentsModel;
import com.haball.Payment.Consolidate_Fragment_Adapter;
import com.haball.Payment.Consolidate_Fragment_View_Payment;
import com.haball.R;

import java.util.List;


public class ProofOfPaymentViewFormAdapter extends RecyclerView.Adapter<ProofOfPaymentViewFormAdapter.ViewHolder> {

    private Context context;
    private List<ProofOfPaymentViewFormModel> proofOfPaymentViewFormList;

    public ProofOfPaymentViewFormAdapter(Context context, List<ProofOfPaymentViewFormModel> proofOfPaymentViewFormList) {
        this.context = context;
        this.proofOfPaymentViewFormList = proofOfPaymentViewFormList;
    }

    @NonNull
    @Override
    public ProofOfPaymentViewFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.proof_of_payment_view_recycler,parent,false);
        return new ProofOfPaymentViewFormAdapter.ViewHolder(view_inflate);    }

    @Override
    public void onBindViewHolder(@NonNull ProofOfPaymentViewFormAdapter.ViewHolder holder, final int position) {
        holder.tv_pop_detail_title_view_value.setText(proofOfPaymentViewFormList.get(position).getTitle());
        String string = proofOfPaymentViewFormList.get(position).getCreatedDate();
        String[] parts = string.split("T");
        String Date = parts[0];
        holder.tv_pop_created_date_value.setText(Date);
        holder.tv_filetype.setText(proofOfPaymentViewFormList.get(position).getFileTypeValue());
        holder.pop_menu_btn.setOnClickListener(new View.OnClickListener() {
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
                                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                LayoutInflater inflater = LayoutInflater.from(context);
                                View view_popup = inflater.inflate(R.layout.popup_image_view, null);
                                alertDialog.setView(view_popup);
                                ImageView img_view = view_popup.findViewById(R.id.iv_image_view);
                                ImageButton close_btn = view_popup.findViewById(R.id.close_btn);

                                String string = proofOfPaymentViewFormList.get(position).getImageData();
                                String[] parts = string.split(",");
                                String img_bitmap = parts[1];

                                byte[] imageByteArray = Base64.decode(img_bitmap, Base64.DEFAULT);

                                Glide.with(context).asBitmap().load(imageByteArray).into(img_view);

                                close_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog.show();
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
        return proofOfPaymentViewFormList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_pop_detail_title_view_value, tv_pop_created_date_value, tv_filetype;
        public ImageButton pop_menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pop_detail_title_view_value = itemView.findViewById(R.id.tv_pop_detail_title_view_value);
            tv_pop_created_date_value = itemView.findViewById(R.id.tv_consolidated_date);
            tv_filetype = itemView.findViewById(R.id.consolidate_company_name);
            pop_menu_btn = itemView.findViewById(R.id.pop_menu_btn);

        }
    }
}
