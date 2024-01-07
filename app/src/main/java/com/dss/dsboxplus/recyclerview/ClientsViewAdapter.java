package com.dss.dsboxplus.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.clients.EstimateListActivity;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.estimates.NewEstimateActivity;
import com.dss.dsboxplus.preferences.AppPreferences;

import java.util.ArrayList;

public class ClientsViewAdapter extends RecyclerView.Adapter<ClientsViewAdapter.ClientsViewHolder> {
    private ArrayList<Client> clientsList;
    private OnClientSelectedI onClientSelectedListner;
//    public void setFilteredList(ArrayList<ClientsDataModel> filteredList) {
//        this.clientsList = filteredList;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_clients_item, parent, false);
        return new ClientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsViewHolder holder, int position) {
        Client client = clientsList.get(position);
        holder.tvClientsName.setText(client.getClientname());
        holder.tvPhoneNumber.setText(client.getMobileno());
//        holder.tvEstimates.setText(client.getMobileno());
//        Glide.with(holder.itemView.getContext())
//                .load(clientsViewModel.getImageURL())
//                .placeholder(R.drawable.ic_launcher_background)
//                .centerCrop()
//                .into(holder.ivClientPhoto);
    }

    @Override
    public int getItemCount() {
        return clientsList.size();
    }


    public void setClientsList(ArrayList<Client> clientsList) {
        this.clientsList = clientsList;
    }

    public OnClientSelectedI getOnClientSelectedListner() {
        return onClientSelectedListner;
    }

    public void setOnClientSelectedListner(OnClientSelectedI onClientSelectedListner) {
        this.onClientSelectedListner = onClientSelectedListner;
    }


    public interface OnClientSelectedI {
        public void onClientSelectedI(Client clientData);
    }

    class ClientsViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivClientPhoto, ivEstimates;
        private TextView tvClientsName, tvPhoneNumber;

        public ClientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClientPhoto = itemView.findViewById(R.id.ivClientPhoto);
            tvClientsName = itemView.findViewById(R.id.tvClientsName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            ivEstimates = itemView.findViewById(R.id.ivEstimates);

            ivEstimates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Open a new activity here
                    if (AppPreferences.INSTANCE.getIsCreatingEstimate(AppPreferences.IS_CREATING_ESTIMATE)) {
                        AppPreferences.INSTANCE.isCreatingEstimate(view.getContext(), AppPreferences.IS_CREATING_ESTIMATE, false);
                        Intent intent = new Intent(view.getContext(), NewEstimateActivity.class);
                        intent.putExtra("clientId", clientsList.get(getAdapterPosition()).getClientid());
                        view.getContext().startActivity(intent);
                    } else {
                        if (view.getContext() != null) {
                            Intent intent = new Intent(view.getContext(), EstimateListActivity.class);
                            intent.putExtra("clientId", clientsList.get(getAdapterPosition()).getClientid());
                            view.getContext().startActivity(intent);
                        }
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClientSelectedListner.onClientSelectedI(clientsList.get(getAdapterPosition()));
                }
            });
        }
    }

}
