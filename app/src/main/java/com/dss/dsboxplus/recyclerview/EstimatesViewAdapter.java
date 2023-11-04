package com.dss.dsboxplus.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.model.EstimatesDataModel;

import java.util.ArrayList;

public class EstimatesViewAdapter extends RecyclerView.Adapter<EstimatesViewAdapter.EstimatesViewHolder> {
    private ArrayList<EstimatesDataModel> estimatesList;
    private OnEstimatesSelectedI onEstimatesSelectedListner;

    public void setOnEstimatesSelectedListner(OnEstimatesSelectedI onEstimatesSelectedListner) {
        this.onEstimatesSelectedListner = onEstimatesSelectedListner;
    }

    @NonNull
    @Override
    public EstimatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_estimates_item, parent, false);
        return new EstimatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstimatesViewHolder holder, int position) {
        EstimatesDataModel estimatesViewModel = estimatesList.get(position);
        holder.tvBoxName.setText(estimatesViewModel.getNameOfBox());
        holder.tvClientName.setText(estimatesViewModel.getNameofClients());
        holder.tvBoxDimension.setText(estimatesViewModel.getDimensionOfBox());
        holder.tvCost.setText(estimatesViewModel.getCostOfBox());
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

    public interface OnEstimatesSelectedI {
        public void onEstimatesSelectedI(EstimatesDataModel estimatesDataModel);
    }

    class EstimatesViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClientPhoto, tvBoxName, tvClientName, tvBoxDimension, tvCost;

        public EstimatesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientPhoto = itemView.findViewById(R.id.ivClientPhoto);
            tvBoxName = itemView.findViewById(R.id.tvBoxName);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvBoxDimension = itemView.findViewById(R.id.tvBoxDimension);
            tvCost = itemView.findViewById(R.id.tvCost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEstimatesSelectedListner.onEstimatesSelectedI(estimatesList.get(getAbsoluteAdapterPosition()));
                }
            });
        }


    }
}
