package com.dss.dsboxplus.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.DataItem;

import java.util.ArrayList;

public class EstimateListViewAdapter extends RecyclerView.Adapter<EstimateListViewAdapter.EstimateListViewHolder> {

    @NonNull
    @Override
    public EstimateListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.estimate_item_list,parent,false);
        return new EstimateListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstimateListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class EstimateListViewHolder extends RecyclerView.ViewHolder {
        private CardView root;

        private TextView tvClientPhoto, tvBoxName, tvClientName, tvBoxDimension, tvCost, tvEstimateDate;

        public EstimateListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBoxName = itemView.findViewById(R.id.tvBoxNameA);
            tvClientName = itemView.findViewById(R.id.tvClientNameA);
            tvBoxDimension = itemView.findViewById(R.id.tvBoxDimensionA);
            tvCost = itemView.findViewById(R.id.tvCostA);
            tvEstimateDate = itemView.findViewById(R.id.tvEstimateDateA);
            root = itemView.findViewById(R.id.root);

        }
    }
}
