package com.haball.Retailor.ui.Network.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haball.R;
import com.haball.Retailor.ui.Network.Models.Network_Sent_Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment_Sent_Adapter extends RecyclerView.Adapter<Fragment_Sent_Adapter.ViewHolder> {

    private Context context;
    private List<Network_Sent_Model> sentDataList;

    public Fragment_Sent_Adapter(Context context, List<Network_Sent_Model> sentDataList) {
        this.context = context;
        this.sentDataList = sentDataList;
    }

    public Fragment_Sent_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.fragment_sent_recycler,parent,false);
        return new Fragment_Sent_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment_Sent_Adapter.ViewHolder holder, int position) {

        holder.send_no_value.setText(sentDataList.get(position).getMobile());
        holder.send_address_value.setText(sentDataList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return sentDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView send_no_value,send_address_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            send_no_value = itemView.findViewById(R.id.sent_no_value);
            send_address_value = itemView.findViewById(R.id.sent_address_value);

        }
    }
}
