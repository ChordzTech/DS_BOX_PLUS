package com.dss.dsboxplus.estimates;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityBoxSpecificationAndCostBinding;

public class BoxSpecificationAndCostActivity extends AppCompatActivity {
    ActivityBoxSpecificationAndCostBinding activityBoxSpecificationAndCostBinding;
    float mm = 25.4F;
    float divide = 1000F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoxSpecificationAndCostBinding = DataBindingUtil.setContentView(this, R.layout.activity_box_specification_and_cost);
        String bfInTopPaper = getIntent().getStringExtra("bfInTopPaper");
        String bfInFlutePaper = getIntent().getStringExtra("bfInFlutePaper");
        String bfInMiddleOnePaper = getIntent().getStringExtra("bfInMiddleOnePaper");
        String bfInFluteTwoPaper = getIntent().getStringExtra("bfInFluteTwoPaper");
        String bfInMiddleTwoPaper = getIntent().getStringExtra("bfInMiddleTwoPaper");
        String bfInFluteThreePaper = getIntent().getStringExtra("bfInFluteThreePaper");
        String bfInBottomPaper = getIntent().getStringExtra("bfInBottomPaper");
        String gsmInTop = getIntent().getStringExtra("gsmInTop");
        String gsmInFlutePaper = getIntent().getStringExtra("gsmInFlutePaper");
        String gsmInMiddleOnePaper = getIntent().getStringExtra("gsmInMiddleOnePaper");
        String gsmInFluteTwoPaper = getIntent().getStringExtra("gsmInFluteTwoPaper");
        String gsmInMiddleTwoPaper = getIntent().getStringExtra("gsmInMiddleTwoPaper");
        String gsmInFluteThreePaper = getIntent().getStringExtra("gsmInFluteThreePaper");
        String gsmInBottomPaper = getIntent().getStringExtra("gsmInBottomPaper");
        String rateKgInTop = getIntent().getStringExtra("rateKgInTop");
        String rateKgInFlutePaper = getIntent().getStringExtra("rateKgInFlutePaper");
        String rateKgInMiddleOnePaper = getIntent().getStringExtra("rateKgInMiddleOnePaper");
        String rateKgInFluteTwoPaper = getIntent().getStringExtra("rateKgInFluteTwoPaper");
        String rateKgInMiddleTwoPaper = getIntent().getStringExtra("rateKgInMiddleTwoPaper");
        String rateKgInFluteThreePaper = getIntent().getStringExtra("rateKgInFluteThreePaper");
        String rateKgInBottomPaper = getIntent().getStringExtra("rateKgInBottomPaper");
        String ffInFluteOnePaper = getIntent().getStringExtra("ffInFluteOnePaper");
        String ffInFluteTwoPaper = getIntent().getStringExtra("ffInFluteTwoPaper");
        String ffInFluteThreePaper = getIntent().getStringExtra("ffInFluteThreePaper");
        String decalM = getIntent().getStringExtra("decalSizeResult");
        String cuttingL = getIntent().getStringExtra("cuttingLengthResult");


        // TotalBS Formula
        //value for TopPaper
        float bsOfTop = Float.parseFloat(bfInTopPaper);
        float gsmOfTop = Float.parseFloat(gsmInTop);

        float val1 = (bsOfTop * gsmOfTop * 1) / 1000;
        //Value for FlutePaper
        float bsOfFlute = Float.parseFloat(bfInFlutePaper);
        float gsmOfFlute = Float.parseFloat(gsmInFlutePaper);

        float val2 = (float) ((bsOfFlute * gsmOfFlute * 0.5) / 1000);
        //Value fot BottomPaper
        float bfOFBottomPaper = Float.parseFloat(bfInBottomPaper);
        float gsmOfBottomPaper = Float.parseFloat(gsmInBottomPaper);

        float val3 = (bfOFBottomPaper * gsmOfBottomPaper * 1) / 1000;
        //Total BS
        float totalBS = (val1 + val2 + val3);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(String.valueOf(totalBS));

        //Total GSM
        //Value for Top
        //float topGsm=Float.parseFloat(gsmInTop);
        float val1ofGsm = (gsmOfTop * 1);
        //Value for flute
        float ffOfFluteOnePaper = Float.parseFloat(ffInFluteOnePaper);
        float val2ofGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Value for Bottom
        float val3ofGsm = (gsmOfBottomPaper * 1);
        //Total Gsm
        float totalGsm = (val1ofGsm + val2ofGsm + val3ofGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));

        //Total Weight
        //1St Value
        float decal = Float.parseFloat(decalM);
        float cutting = Float.parseFloat(cuttingL);
        float valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);

        //2nd Value
        float valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);

        //3rd Value
        float valueThirdOfTW = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);

        //Total
        float total = (valueFirstOFTW + valueSecondOfTW + valueThirdOfTW);
        //Waste value
        float wastePercentage = ((total * 3) / 100);
        //Total weight
        float totalWeight = total + wastePercentage;
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(String.valueOf(totalWeight));

        //ConversionCostPerKG
        float resultOfConvCost = (totalWeight * 9);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(String.valueOf(resultOfConvCost));


        //Costs
        //NetPaperCost
        //1stValue
        float rateOfTop = Float.parseFloat(rateKgInTop);
        float valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //2ndValue
        float rateOfFlute = Float.parseFloat(rateKgInFlutePaper);
        float valueSecondNPC = (valueSecondOfTW * rateOfFlute);

        //3rdValue
        float rateOfBottom = Float.parseFloat(rateKgInBottomPaper);
        float valueThirdNPC = (valueThirdOfTW * rateOfBottom);
        //NetPaperCost
        float resultOfNPC = (valueFirstNPC + valueSecondNPC + valueThirdNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(resultOfNPC));

        //WasteCost
        float resultWasteCost=((resultOfNPC*3)/100);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCost));

        //GrossPaperCost
        float grossPaperCost=(resultOfNPC+resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCost));

        //BoxMFGCost
        double resultOfBoxMFG=(grossPaperCost+resultOfConvCost+1.5);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFG));


        activityBoxSpecificationAndCostBinding.btBackInBoxSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}