package com.dss.dsboxplus.estimates;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityBoxEstimatesDetailsBinding;

public class BoxEstimatesDetailsActivity extends AppCompatActivity {
    ActivityBoxEstimatesDetailsBinding boxEstimatesDetailsBinding;
    private DataItem dataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boxEstimatesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_box_estimates_details);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        if (intent.hasExtra("ESTIMATES_BUNDLE")) {
            // Retrieve the bundle from the intent
            Bundle bundle = intent.getBundleExtra("ESTIMATES_BUNDLE");
            if (bundle != null) {
                // Retrieve the DataItem object from the bundle
                DataItem dataItem = bundle.getParcelable("ESTIMATES");
                if (dataItem != null) {
                    //Dimension For ImageOne
                    boxEstimatesDetailsBinding.tvLength.setText(String.valueOf(dataItem.getLengthMmField()+"mm"));
                    boxEstimatesDetailsBinding.tvWidth.setText(String.valueOf(dataItem.getWidthMmField()+"mm"));
                    boxEstimatesDetailsBinding.tvHeight.setText(String.valueOf(dataItem.getHeightMmField()+"mm"));
                    boxEstimatesDetailsBinding.tvTotalWeight.setText(String.valueOf(dataItem.getTotalweight()+"gm"));
                    boxEstimatesDetailsBinding.tvBS.setText(String.valueOf(dataItem.getTotalbs()));
                    //Dimension For ImageTwo
                    boxEstimatesDetailsBinding.tvDecalSizeForBox.setText(String.valueOf(dataItem.getDecalsize()+"inch"));


                    //Dimensions for ply
                    boxEstimatesDetailsBinding.tvTopBf.setText(String.valueOf(dataItem.getTopbf()));
                    boxEstimatesDetailsBinding.tvTopGsm.setText(String.valueOf(dataItem.getTopgsm()));
                    boxEstimatesDetailsBinding.tvTopRate.setText(String.valueOf(dataItem.getToprate()));
                    boxEstimatesDetailsBinding.tvFluteOnebf.setText(String.valueOf(dataItem.getF1bf()));
                    boxEstimatesDetailsBinding.tvGsmFluteOne.setText(String.valueOf(dataItem.getF1gsm()));
                    boxEstimatesDetailsBinding.tvRateFluteOne.setText(String.valueOf(dataItem.getF1rate()));
                    boxEstimatesDetailsBinding.tvFFFluteOne.setText(String.valueOf(dataItem.getF1ff()));
                    boxEstimatesDetailsBinding.tvMiddleOneBf.setText(String.valueOf(dataItem.getM1bf()));
                    boxEstimatesDetailsBinding.tvMiddleOneGsm.setText(String.valueOf(dataItem.getM1gsm()));
                    boxEstimatesDetailsBinding.tvMiddleOneRate.setText(String.valueOf(dataItem.getM1rate()));
                    boxEstimatesDetailsBinding.tvFluteTwoBf.setText(String.valueOf(dataItem.getF2bf()));
                    boxEstimatesDetailsBinding.tvFluteTwoGsm.setText(String.valueOf(dataItem.getF2gsm()));
                    boxEstimatesDetailsBinding.tvFluteTwoRate.setText(String.valueOf(dataItem.getF2rate()));
                    boxEstimatesDetailsBinding.tvFluteTwoFf.setText(String.valueOf(dataItem.getF2ff()));
                    boxEstimatesDetailsBinding.tvMiddleTwoBf.setText(String.valueOf(dataItem.getM2bf()));
                    boxEstimatesDetailsBinding.tvMiddleTwoGsm.setText(String.valueOf(dataItem.getM2gsm()));
                    boxEstimatesDetailsBinding.tvMiddleTwoRate.setText(String.valueOf(dataItem.getM2rate()));
                    boxEstimatesDetailsBinding.tvFluteThreeBf.setText(String.valueOf(dataItem.getF3bf()));
                    boxEstimatesDetailsBinding.tvFluteThreeGsm.setText(String.valueOf(dataItem.getF3gsm()));
                    boxEstimatesDetailsBinding.tvFluteThreeRate.setText(String.valueOf(dataItem.getF3rate()));
                    boxEstimatesDetailsBinding.tvFluteThreeFf.setText(String.valueOf(dataItem.getF3ff()));
                    boxEstimatesDetailsBinding.tvBottomBf.setText(String.valueOf(dataItem.getBottombf()));
                    boxEstimatesDetailsBinding.tvBottomGsm.setText(String.valueOf(dataItem.getBottomgsm()));
                    boxEstimatesDetailsBinding.tvBottomRate.setText(String.valueOf(dataItem.getBottomrate()));

                    boxEstimatesDetailsBinding.tvClientNameInBoxEstimateDetails.setText(String.valueOf(dataItem.getClientid()));
                    boxEstimatesDetailsBinding.tvBoxNameInBoxEstimateDetails.setText(dataItem.getBoxname());
                    boxEstimatesDetailsBinding.tvnoOfBox.setText(String.valueOf(dataItem.getUps()));
                    boxEstimatesDetailsBinding.tvtotalGsm.setText(String.valueOf(dataItem.getTotalgsm()));
                    boxEstimatesDetailsBinding.tvWaste.setText(String.valueOf(dataItem.getWaste()));
                    boxEstimatesDetailsBinding.tvConvKg.setText(String.valueOf(dataItem.getConversionrate()));
                    boxEstimatesDetailsBinding.tvConvCost.setText(String.valueOf(dataItem.getConversionrate()));
                    boxEstimatesDetailsBinding.tvPaperCost.setText(String.valueOf(dataItem.getTotalpapercost()));
                    boxEstimatesDetailsBinding.tvOverhead.setText(String.valueOf(dataItem.getOverheadcharges()));
                    boxEstimatesDetailsBinding.tvBoxMfg.setText(String.valueOf(dataItem.getBoxcost()));
                    boxEstimatesDetailsBinding.tvProfit.setText(String.valueOf(dataItem.getProfit()));
                    boxEstimatesDetailsBinding.tvBoxpriceInEstimateDetails.setText(String.valueOf(dataItem.getBoxprice()));
                    boxEstimatesDetailsBinding.tvTax.setText(String.valueOf(dataItem.getTax()));
                    boxEstimatesDetailsBinding.tvPriceWithtax.setText(String.valueOf(dataItem.getBoxpricewithtax()));
                }
            }
        }


        //Quotation
        boxEstimatesDetailsBinding.btQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuotationInBoxEstimateDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("EstimateDetails", dataItem);
                intent.putExtra("EstimateDetails_Bundle", bundle);
                startActivity(intent);
            }
        });
        //Production
        boxEstimatesDetailsBinding.btProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductionInBoxEstimatesDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("EstimateDetails", dataItem);
                // Put the bundle into the intent
                intent.putExtra("EstimateDetails_Bundle", bundle);
                startActivity(intent);

            }
        });
        //Delete
        boxEstimatesDetailsBinding.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(view);
            }
        });
        boxEstimatesDetailsBinding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void dialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are You Sure?");
        builder.setMessage("You won't be able to recover this file!");
        builder.setCancelable(false);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}