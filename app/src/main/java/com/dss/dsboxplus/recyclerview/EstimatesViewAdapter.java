package com.dss.dsboxplus.recyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.model.EstimatesDataModel;

import java.util.ArrayList;

public class EstimatesViewAdapter extends RecyclerView.Adapter<EstimatesViewAdapter.EstimatesViewHolder> {
    boolean isSelectMode = false;
    private ArrayList<EstimatesDataModel> selectedItems = new ArrayList<>();
    private ArrayList<EstimatesDataModel> estimatesList;
    private ArrayList<EstimatesDataModel> filteredList;
    private OnEstimatesSelectedI onEstimatesSelectedListner;
    private boolean estimateSelection;


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
        holder.tvEstimateDate.setText(estimatesViewModel.getDateOfEstimate());
        //long click
        if (!estimateSelection) {
            holder.root.setBackgroundColor(Color.WHITE);
        }
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

    public void setEstimateSelectionClosed() {
        estimateSelection = false;
    }


    public interface OnEstimatesSelectedI {
        public void onEstimatesSelectedI(EstimatesDataModel estimatesDataModel);

        void onItemLongPressed(EstimatesDataModel estimatesDataModel, int adapterPosition);

        void onItemClicked(EstimatesDataModel estimatesDataModel, int adapterPosition);

    }

    class EstimatesViewHolder extends RecyclerView.ViewHolder {
        private CardView root;
        private TextView tvClientPhoto, tvBoxName, tvClientName, tvBoxDimension, tvCost, tvEstimateDate;

        public EstimatesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientPhoto = itemView.findViewById(R.id.ivClientPhoto);
            tvBoxName = itemView.findViewById(R.id.tvBoxName);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvBoxDimension = itemView.findViewById(R.id.tvBoxDimension);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvEstimateDate = itemView.findViewById(R.id.tvEstimateDate);
            root = itemView.findViewById(R.id.root);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    EstimatesDataModel estimatesDataModel = estimatesList.get(getAdapterPosition());
                    estimatesDataModel.setSelected(true);
                    estimateSelection = true;
                    root.setBackgroundColor(Color.GRAY);
                    onEstimatesSelectedListner.onItemLongPressed(estimatesList.get(getAdapterPosition()), getAdapterPosition());
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EstimatesDataModel estimatesDataModel = estimatesList.get(getAdapterPosition());
                    if (estimateSelection) {
                        if (!estimatesDataModel.isSelected()) {
                            root.setBackgroundColor(Color.GRAY);
                            estimatesDataModel.setSelected(true);
                        } else {
                            root.setBackgroundColor(Color.WHITE);
                            estimatesDataModel.setSelected(false);
                        }
                        onEstimatesSelectedListner.onItemClicked(estimatesList.get(getAdapterPosition()), getAdapterPosition());
                    } else {
                        onEstimatesSelectedListner.onEstimatesSelectedI(estimatesList.get(getAdapterPosition()));
                    }

                }
            });
        }
    }
}
