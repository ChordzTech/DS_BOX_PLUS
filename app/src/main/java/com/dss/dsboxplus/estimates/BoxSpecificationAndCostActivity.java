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
        String resOfTotalBs=String.format("%.2f",totalBS);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);

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
        String valueFirstOFTWString=String.format("%.3f",valueFirstOFTW);
        //2nd Value
        float valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);
        String valueSecondOfTWString=String.format("%.3f",valueSecondOfTW);

        //3rd Value
        float valueThirdOfTW = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);
        String valueThirdOfTWString=String.format("%.3f",valueThirdOfTW);
        //Total
        float total = (valueFirstOFTW + valueSecondOfTW + valueThirdOfTW);
        String totalString=String.format("%.3f",total);
        //Waste value
        float wastePercentage = ((total * 3) / 100);
        //Total weight
        float totalWeight = total + wastePercentage;
        String resultOftotalWeight=String.format("%.3f",totalWeight);
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(resultOftotalWeight);

        //ConversionCostPerKG
        float resultOfConvCost = (totalWeight * 9);
        String resultOfConvCostString=String.format("%.3f",resultOfConvCost);

        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);


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
        String resultOfNpcString=String.format("%.3f",resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);

        //WasteCost
        float resultWasteCost=((resultOfNPC*3)/100);
        String resultOfwasteString=String.format("%.3f",resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);

        //GrossPaperCost
        float grossPaperCost=(resultOfNPC+resultWasteCost);
        String resultOfgrossPaperCost=String.format("%.3f",grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfgrossPaperCost);

        //BoxMFGCost
        double resultOfBoxMFG=(grossPaperCost+resultOfConvCost+1.5);
        String resultOfBoxMFGString=String.format("%.3f",resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        double boxPrice=((resultOfBoxMFG*10/100)+resultOfBoxMFG);
        String resultOfboxPrice=String.format("%.2f",boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfboxPrice);

        //Box Price with Tax
        double boxPriceWithTax=((boxPrice*18/100)+boxPrice);
        String resultOfboxPriceWithTax=String.format("%.2f",boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);

        activityBoxSpecificationAndCostBinding.btBackInBoxSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}