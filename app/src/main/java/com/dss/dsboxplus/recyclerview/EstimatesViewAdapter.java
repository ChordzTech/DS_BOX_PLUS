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
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.model.EstimatesDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class EstimatesViewAdapter extends RecyclerView.Adapter<EstimatesViewAdapter.EstimatesViewHolder> {
    boolean isSelectMode = false;
    private ArrayList<EstimatesDataModel> selectedItems;
    private ArrayList<DataItem> estimatesList=new ArrayList<>();
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
        DataItem dataItem = estimatesList.get(position);
        holder.tvBoxName.setText(dataItem.getBoxname());
        holder.tvClientName.setText(String.valueOf(dataItem.getClientid()));
        holder.tvBoxDimension.setText(dataItem.getLengthMmField() + "x" + dataItem.getWidthMmField() + "x" + dataItem.getHeightMmField());
        String boxCost=String.format("%.2f",dataItem.getBoxprice());
        String finalBox="₹ "+boxCost;
        holder.tvCost.setText(finalBox);
        String dateString=dataItem.getEstimatedate();
        String formattedDate=formatDateFromApi(dateString);
        holder.tvEstimateDate.setText(formattedDate);

        //long click
        if (!estimateSelection) {
            holder.root.setBackgroundColor(Color.WHITE);
        }
    }

    private String formatDateFromApi(String inputDate) {
        try{
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date=inputFormat.parse(inputDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            return outputFormat.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
            return inputDate; // Return the original date string if parsing fails
        }
    }

    @Override
    public int getItemCount() {
        return estimatesList.size();
    }

    public void setEstimatesList(ArrayList<DataItem> estimatesList) {
        this.estimatesList = estimatesList;
    }

    public void setEstimateSelectionClosed() {
        estimateSelection = false;
    }


    public interface OnEstimatesSelectedI {
        public void onEstimatesSelectedI(DataItem dataItem);

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
//                    EstimatesDataModel estimatesDataModel = estimatesList.get(getAdapterPosition());
//                    estimatesDataModel.setSelected(true);
//                    estimateSelection = true;
//                    root.setBackgroundColor(Color.GRAY);
//                    onEstimatesSelectedListner.onItemLongPressed(estimatesList.get(getAdapterPosition()), getAdapterPosition());
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    EstimatesDataModel estimatesDataModel = estimatesList.get(getAdapterPosition());
//                    if (estimateSelection) {
//                        if (!estimatesDataModel.isSelected()) {
//                            root.setBackgroundColor(Color.GRAY);
//                            estimatesDataModel.setSelected(true);
//                        } else {
//                            root.setBackgroundColor(Color.WHITE);
//                            estimatesDataModel.setSelected(false);
//                        }
//                        onEstimatesSelectedListner.onItemClicked(estimatesList.get(getAdapterPosition()), getAdapterPosition());
//                    } else {
                        onEstimatesSelectedListner.onEstimatesSelectedI(estimatesList.get(getAdapterPosition()));
//                    }

                }
            });
        }
    }
}
