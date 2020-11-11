package com.haball.Retailor.ui.Network.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haball.Retailor.ui.Network.Models.Netwok_Model;
import com.haball.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment_My_Network_Adapter extends RecyclerView.Adapter<Fragment_My_Network_Adapter.ViewHolder> {

    private Context context;
    private List<Netwok_Model> myNetworkData;

    public Fragment_My_Network_Adapter(Context context, List<Netwok_Model> myNetworkData) {
        this.context = context;
        this.myNetworkData = myNetworkData;
    }

    public Fragment_My_Network_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view_inflate = LayoutInflater.from(context).inflate(R.layout.fragment_my_network_recycler,parent,false);
        return new Fragment_My_Network_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment_My_Network_Adapter.ViewHolder holder, int position) {

        holder.my_network_fragment_status_value.setText(myNetworkData.get(position).getStatus());
        holder.my_network_fragment_no_value.setText(myNetworkData.get(position).getMobile());
        holder.my_network_fragment_cnic_value.setText(myNetworkData.get(position).getAddress());
        holder.net_company_name.setText(myNetworkData.get(position).getCompanyName());
       // Toast.makeText("Adapter",);

    }

    @Override
    public int getItemCount() {
        return myNetworkData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView my_network_fragment_status_value,my_network_fragment_no_value,my_network_fragment_cnic_value ,net_company_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            my_network_fragment_status_value = itemView.findViewById(R.id.my_network_fragment_status_value);
            my_network_fragment_no_value = itemView.findViewById(R.id.my_network_fragment_no_value);
            my_network_fragment_cnic_value = itemView.findViewById(R.id.my_network_fragment_cnic_value);
            net_company_name =itemView.findViewById(R.id.net_company_name);

        }
    }
}
