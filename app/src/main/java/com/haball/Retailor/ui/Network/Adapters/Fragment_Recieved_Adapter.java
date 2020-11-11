package com.haball.Retailor.ui.Network.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haball.R;
import com.haball.Retailor.ui.Network.Models.Network_Recieve_Model;
import com.haball.Retailor.ui.Network.Models.Network_Sent_Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment_Recieved_Adapter extends RecyclerView.Adapter<Fragment_Recieved_Adapter.ViewHolder> {

    private Context context;
    private List<Network_Recieve_Model> recieveDataList;

    public Fragment_Recieved_Adapter(Context context, List<Network_Recieve_Model> recieveDataList) {
        this.context = context;
        this.recieveDataList = recieveDataList;
    }

    public Fragment_Recieved_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.fragment_recieved_recycler,parent,false);
        return new Fragment_Recieved_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment_Recieved_Adapter.ViewHolder holder, int position) {

        holder.recieved_no_value.setText(recieveDataList.get(position).getMobile());
        holder.recieved_address_value.setText(recieveDataList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return recieveDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recieved_no_value,recieved_address_value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recieved_no_value = itemView.findViewById(R.id.recieved_no_value);
            recieved_address_value = itemView.findViewById(R.id.recieved_address_value);

        }
    }
}
