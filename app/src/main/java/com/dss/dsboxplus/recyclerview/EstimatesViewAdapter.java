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
import com.dss.dsboxplus.model.EstimatesDataModel;

import java.util.ArrayList;

public class EstimatesViewAdapter extends RecyclerView.Adapter<EstimatesViewAdapter.EstimatesViewHolder> {
    private ArrayList<EstimatesDataModel> estimatesList;

    @NonNull
    @Override
    public EstimatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_estimates_item, parent, false);
        return new EstimatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstimatesViewHolder holder, int position) {
        EstimatesDataModel estimatesViewModel=estimatesList.get(position);
        holder.tvBoxName.setText(estimatesViewModel.getNameOfBox());
        holder.tvClientName.setText(estimatesViewModel.getNameofClients());
        holder.tvBoxDimension.setText(estimatesViewModel.getDimensionOfBox());
        holder.tvCost.setText(estimatesViewModel.getCostOfBox());
        Glide.with(holder.itemView.getContext())
                .load(estimatesViewModel.getImageURL())
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.ivClientPhoto);
    }

    @Override
    public int getItemCount() {
        return estimatesList.size();
    }

    public ArrayList<EstimatesDataModel> getEstimatesList() {
        return estimatesList;
    }

    public void setEstimatesList(ArrayList<EstimatesDataModel> estimatesList) {
        this.estimatesList = estimatesList;
    }

    class EstimatesViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivClientPhoto;
        private TextView tvBoxName, tvClientName, tvBoxDimension, tvCost;

        public EstimatesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClientPhoto = itemView.findViewById(R.id.ivClientPhoto);
            tvBoxName = itemView.findViewById(R.id.tvBoxName);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvBoxDimension = itemView.findViewById(R.id.tvBoxDimension);
            tvCost = itemView.findViewById(R.id.tvCost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
