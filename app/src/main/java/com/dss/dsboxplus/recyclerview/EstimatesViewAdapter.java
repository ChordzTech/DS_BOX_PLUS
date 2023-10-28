package com.dss.dsboxplus.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.model.EstimatesViewModel;

import java.util.ArrayList;

public class EstimatesViewAdapter extends RecyclerView.Adapter<EstimatesViewAdapter.EstimatesViewHolder> {
    private ArrayList<EstimatesViewModel> estimatesList;

    @NonNull
    @Override
    public EstimatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_estimates_item, parent, false);
        return new EstimatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstimatesViewHolder holder, int position) {
        EstimatesViewModel estimatesViewModel = estimatesList.get(position);
        holder.tvBoxName.setText(estimatesViewModel.getNameOfBox());
    }

    @Override
    public int getItemCount() {
        return estimatesList.size();
    }

    public ArrayList<EstimatesViewModel> getEstimatesList() {
        return estimatesList;
    }

    public void setEstimatesList(ArrayList<EstimatesViewModel> estimatesList) {
        this.estimatesList = estimatesList;
    }

    class EstimatesViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivClientPhoto;
        private TextView tvBoxName, tvClientName, tvBoxDimension, tvCost;

        public EstimatesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClientPhoto = itemView.findViewById(R.id.ivClientPhoto);
            tvBoxName = itemView.findViewById(R.id.tvBoxName);
            tvClientName = itemView.findViewById(R.id.tvClientsName);
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
