package com.dss.dsboxplus.recyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.model.EstimatesDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class EstimatesViewAdapter extends RecyclerView.Adapter<EstimatesViewAdapter.EstimatesViewHolder> {
    boolean isSelectMode = false;

    private ArrayList<DataItem> estimatesList = new ArrayList<>();

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
        int lengthMM = (int) Math.round(dataItem.getLengthMmField());
        int widthMM = (int) Math.round(dataItem.getWidthMmField());
        int heightMM = (int) Math.round(dataItem.getHeightMmField());
        int lengthCM = (int) Math.round(Double.parseDouble(dataItem.getLengthCmField().toString()));
        int widthCM = (int) Math.round(Double.parseDouble(dataItem.getWidthCmField().toString()));
        int heightCM = (int) Math.round(Double.parseDouble(dataItem.getHeightCmField().toString()));
        int lengthIN = (int) Math.round(Double.parseDouble(dataItem.getLengthInchField().toString()));
        int widthIN = (int) Math.round(Double.parseDouble(dataItem.getWidthInchField().toString()));
        int heightIN = (int) Math.round(Double.parseDouble(dataItem.getHeightInchField().toString()));
        if (lengthCM != 0 && widthCM != 0 && heightCM != 0) {
            holder.tvBoxDimension.setText(lengthCM + "x" + widthCM + "x" + heightCM + " cm");

        } else if (lengthIN != 0 && widthIN != 0 && heightIN != 0) {
            holder.tvBoxDimension.setText(lengthIN + "x" + widthIN + "x" + heightIN + " inch");

        } else {
            holder.tvBoxDimension.setText(lengthMM + "x" + widthMM + "x" + heightMM + " mm");
        }
        String boxCost = String.format("%.2f", dataItem.getBoxprice());
        String finalBox = "₹ " + boxCost;
        holder.tvCost.setText(finalBox);
        String dateString = dataItem.getEstimatedate();
        String formattedDate = formatDateFromApi(dateString);
        holder.tvEstimateDate.setText(formattedDate);
        holder.cvDummyView.setVisibility(View.GONE);
        holder.tvClientName.setText(getClientName(dataItem.getClientid()));

        int randomColor = getRandomColor();
        holder.tvEstimateDate.setBackgroundColor(randomColor);
        //long click
        if (!estimateSelection) {
            holder.root.setBackgroundColor(Color.WHITE);
        }
        if (position == getItemCount() - 1) {
            holder.cvDummyView.setVisibility(View.VISIBLE);
        }
    }

    private int getRandomColor() {
        Random rand = new Random();
        return Color.argb(155, rand.nextInt(156), rand.nextInt(156), rand.nextInt(156));
    }

    private String getClientName(Long clientid) {
        return ConfigDataProvider.INSTANCE.getClientIdMap().get(clientid) != null ?
                ConfigDataProvider.INSTANCE.getClientIdMap().get(clientid).getClientname() :
                "";
    }

    private String formatDateFromApi(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(inputDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate; // Return the original date string if parsing fails
        }
    }

    @Override
    public int getItemCount() {
        return estimatesList.size();
    }

    public void setEstimatesList(ArrayList<DataItem> updatedList) {
        this.estimatesList.addAll(updatedList);
        notifyItemRangeChanged(this.estimatesList.size() - updatedList.size(), updatedList.size());
    }

    public void setFilterList(ArrayList<DataItem> updatedList) {
        this.estimatesList.clear();
        this.estimatesList.addAll(updatedList);
        notifyDataSetChanged();
    }

    public void setEstimateSelectionClosed() {
        estimateSelection = false;
    }

    public void clearAdapter() {
        estimatesList.clear();
        notifyDataSetChanged();
    }

    public interface OnEstimatesSelectedI {
        public void onEstimatesSelectedI(DataItem dataItem);

        void onItemLongPressed(EstimatesDataModel estimatesDataModel, int adapterPosition);

        void onItemClicked(EstimatesDataModel estimatesDataModel, int adapterPosition);

    }

    class EstimatesViewHolder extends RecyclerView.ViewHolder {
        private CardView root;
        private ConstraintLayout cvDummyView;
        private TextView tvClientPhoto, tvBoxName, tvBoxDimension, tvCost, tvEstimateDate, tvClientName;

        public EstimatesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBoxName = itemView.findViewById(R.id.tvBoxName);
            tvBoxDimension = itemView.findViewById(R.id.tvBoxDimension);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvEstimateDate = itemView.findViewById(R.id.tvEstimateDate);
            root = itemView.findViewById(R.id.root);
            cvDummyView = itemView.findViewById(R.id.cvDummyView);
            tvClientName = itemView.findViewById(R.id.tvClientName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataItem dataItem = estimatesList.get(getAdapterPosition());
                    if (dataItem.getPly() == 3 &&
                            dataItem.getBottombf() == 0 &&
                            dataItem.getBottombf() == 0 &&
                            dataItem.getBottombf() == 0) {
                        dataItem.setBottombf(dataItem.getM1bf());
                        dataItem.setBottomgsm(dataItem.getM1gsm());
                        dataItem.setBottomrate((Double) dataItem.getM1rate());
                    }
                    if (dataItem.getPly() == 5 &&
                            dataItem.getBottombf() == 0 &&
                            dataItem.getBottombf() == 0 &&
                            dataItem.getBottombf() == 0) {
                        dataItem.setBottombf(dataItem.getM2bf());
                        dataItem.setBottomgsm(dataItem.getM2gsm());
                        dataItem.setBottomrate((Double) dataItem.getM2rate());
                    }

                    onEstimatesSelectedListner.onEstimatesSelectedI(dataItem);
                }
            });
        }
    }
}
