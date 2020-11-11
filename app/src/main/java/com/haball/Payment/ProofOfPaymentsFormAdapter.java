package com.haball.Payment;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haball.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProofOfPaymentsFormAdapter extends RecyclerView.Adapter<ProofOfPaymentsFormAdapter.ViewHolder> {
    private Context mContext;
    private List<String> documentNames;
    private List<String> selectedImageFileTypes;
    private List<String> imageBitmapBase64;

//    public ProofOfPaymentsFormAdapter(Proof_Of_Payment_Form proof_of_payment_form, String deposit, String slip) {
//        this.mContext = proof_of_payment_form;
//        this.deposit = deposit;
//        this.slip = slip;
//    }

    public ProofOfPaymentsFormAdapter(Context context, ArrayList<String> documentNames, ArrayList<String> selectedImageFileTypes, ArrayList<String> imageBitmapBase64) {
        this.mContext = context;
        this.documentNames = documentNames;
        this.selectedImageFileTypes = selectedImageFileTypes;
        this.imageBitmapBase64 = imageBitmapBase64;

    }

    @NonNull
    @Override
    public ProofOfPaymentsFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(mContext).inflate(R.layout.proof_of_payment_form_recycler,parent,false);
        return new ProofOfPaymentsFormAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProofOfPaymentsFormAdapter.ViewHolder holder, final int position) {
        holder.txt_doc1.setText(documentNames.get(position));
        holder.txt_slip.setText(selectedImageFileTypes.get(position));
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // REMOVE IMAGE HERE .........
                documentNames.remove(position);
                selectedImageFileTypes.remove(position);
                imageBitmapBase64.remove(position);

                notifyItemRemoved(position);

                notifyItemRangeChanged(position, documentNames.size());
                notifyItemRangeChanged(position, selectedImageFileTypes.size());
                notifyItemRangeChanged(position, imageBitmapBase64.size());

            }
        });
        holder.btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view_popup = inflater.inflate(R.layout.popup_image_view, null);
                alertDialog.setView(view_popup);
                ImageView img_view = view_popup.findViewById(R.id.iv_image_view);
                ImageButton close_btn = view_popup.findViewById(R.id.close_btn);

                byte[] imageByteArray = Base64.decode(imageBitmapBase64.get(position), Base64.DEFAULT);

                Glide.with(mContext).asBitmap().load(imageByteArray).into(img_view);

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_doc1,txt_slip;
        public ImageButton menu_btn;
        Button btn_view, btn_remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_doc1 = itemView.findViewById(R.id.txt_doc1);
            txt_slip = itemView.findViewById(R.id.txt_slip);
            menu_btn = itemView.findViewById(R.id.menu_btn);
            btn_view = itemView.findViewById(R.id.btn_view);
            btn_remove = itemView.findViewById(R.id.btn_remove);

        }
    }
}
