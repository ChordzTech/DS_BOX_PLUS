package com.dss.dsboxplus.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dss.dsboxplus.R;
import com.dss.dsboxplus.model.ClientsDataModel;

import java.util.ArrayList;
import java.util.List;

public class ClientsViewAdapter extends RecyclerView.Adapter<ClientsViewAdapter.ClientsViewHolder> {
    private ArrayList<ClientsDataModel> clientsList;
    private OnClientSelectedI onClientSelectedListner;
    public void setFilteredList(ArrayList<ClientsDataModel>filteredList){
        this.clientsList=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_clients_item, parent, false);
        return new ClientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsViewHolder holder, int position) {
        ClientsDataModel clientsViewModel = clientsList.get(position);
        holder.tvClientsName.setText(clientsViewModel.getNameOfClient());
        holder.tvPhoneNumber.setText(clientsViewModel.getPhoneNumberOfClient());
        holder.tvEstimates.setText(clientsViewModel.getNumberOfEstimatesOfClients());
        Glide.with(holder.itemView.getContext())
                .load(clientsViewModel.getImageURL())
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.ivClientPhoto);
    }

    @Override
    public int getItemCount() {
        return clientsList.size();
    }

    public ArrayList<ClientsDataModel> getClientsList() {
        return clientsList;
    }

    public void setClientsList(ArrayList<ClientsDataModel> clientsList) {
        this.clientsList = clientsList;
    }

    public OnClientSelectedI getOnClientSelectedListner() {
        return onClientSelectedListner;
    }

    public void setOnClientSelectedListner(OnClientSelectedI onClientSelectedListner) {
        this.onClientSelectedListner = onClientSelectedListner;
    }




    public interface OnClientSelectedI {
        public void onClientSelectedI(ClientsDataModel clientsDataModel);
    }

    class ClientsViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivClientPhoto;
        private TextView tvClientsName, tvPhoneNumber, tvEstimates;

        public ClientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClientPhoto = itemView.findViewById(R.id.ivClientPhoto);
            tvClientsName = itemView.findViewById(R.id.tvClientsName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvEstimates = itemView.findViewById(R.id.tvEstimates);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClientSelectedListner.onClientSelectedI(clientsList.get(getAdapterPosition()));
                }
            });
        }
    }

}
