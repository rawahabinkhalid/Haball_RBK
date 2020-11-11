package com.haball.Distributor.ui.Network.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haball.Distributor.ui.Network.Models.Netwok_Model;
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

        View view_inflate = LayoutInflater.from(context).inflate(R.layout.fragment_my_network_distributor,parent,false);
        return new Fragment_My_Network_Adapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment_My_Network_Adapter.ViewHolder holder, int position) {

        holder.network_status.setText(myNetworkData.get(position).getKycStatusValue());
        holder.net_company_name.setText(myNetworkData.get(position).getCompanyName());

//        holder.my_network_fragment_address.setText("Created Date: ");
//        holder.my_network_fragment_cnic_value.setText(myNetworkData.get(position).getCreatedDate().split("T")[0]);

       // holder.my_network_fragment.setText("Request No: ");
        holder.company_mobile_no.setText(myNetworkData.get(position).getDistributorMobile());
        holder.company_address.setText(myNetworkData.get(position).getAddress());

       // Toast.makeText("Adapter",);

    }

    @Override
    public int getItemCount() {
        return myNetworkData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView company_mobile_no,network_status ,company_address,net_company_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            network_status = itemView.findViewById(R.id.network_status);
            company_mobile_no = itemView.findViewById(R.id.company_mobile_no);
            company_address = itemView.findViewById(R.id.company_address);
            net_company_name =itemView.findViewById(R.id.net_company_name);

        }
    }
}
