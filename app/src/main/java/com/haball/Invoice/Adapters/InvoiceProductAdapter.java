package com.haball.Invoice.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InvoiceProductAdapter extends RecyclerView.Adapter<InvoiceProductAdapter.InvoiceProductVH> {

    private Context mContx;



    @NonNull
    @Override
    public InvoiceProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceProductVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class  InvoiceProductVH extends RecyclerView.ViewHolder {
        public InvoiceProductVH(@NonNull View itemView) {
            super(itemView);
        }
    }

}
