package com.dss.dsboxplus.estimates;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.databinding.ActivityBoxSpecificationAndCostBinding;

import java.util.ArrayList;

public class BoxSpecificationAndCostActivity extends BaseActivity {
    ActivityBoxSpecificationAndCostBinding activityBoxSpecificationAndCostBinding;
    float mm = 25.4F;
    int divide = 1000;
    String formula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoxSpecificationAndCostBinding = DataBindingUtil.setContentView(this, R.layout.activity_box_specification_and_cost);
        initView();
    }

    private void initView() {
        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
        if (appConfigResponse.getData() != null) {
            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                // Access individual properties of AppConfigDataItems
                int configId = appConfigDataItem.getConfigid();
                String configValue = appConfigDataItem.getConfigvalue();
                if (configId == 29) {
                    activityBoxSpecificationAndCostBinding.tietWaste.setText(configValue);
                } else if (configId == 3) {
                    activityBoxSpecificationAndCostBinding.tietConversionCost.setText(configValue);
                } else if (configId == 19) {
                    activityBoxSpecificationAndCostBinding.tietProfit.setText(configValue);
                } else if (configId == 24) {
                    activityBoxSpecificationAndCostBinding.tietTax.setText(configValue);
                }
            }
        }

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
        String formula = getIntent().getStringExtra("noOfPly");
        String noOfBox = getIntent().getStringExtra("noOfBox");
        String waste = activityBoxSpecificationAndCostBinding.tietWaste.getText().toString();
        String convCostKg = activityBoxSpecificationAndCostBinding.tietConversionCost.getText().toString();
        String profit = activityBoxSpecificationAndCostBinding.tietProfit.getText().toString();
        String tax = activityBoxSpecificationAndCostBinding.tietTax.getText().toString();
        String overhead = activityBoxSpecificationAndCostBinding.tietOverHeadCharges.getText().toString();
        switch (formula) {
            case "1Ply": {
                formulaForOnePly(bfInTopPaper, gsmInTop, rateKgInTop, decalM, cuttingL, waste, convCostKg, profit, tax, overhead, noOfBox);
                break;
            }
            case "2Ply": {
                formulaForTwoPly(bfInTopPaper, gsmInTop, rateKgInTop, bfInFlutePaper, gsmInFlutePaper, rateKgInFlutePaper, ffInFluteOnePaper, decalM, cuttingL, waste, convCostKg, profit, tax, overhead, noOfBox);
                break;
            }

            case "3Ply": {
                formulaForThreePly(bfInTopPaper, gsmInTop, rateKgInTop, bfInFlutePaper, gsmInFlutePaper, rateKgInFlutePaper, ffInFluteOnePaper, bfInBottomPaper, gsmInBottomPaper, rateKgInBottomPaper, decalM, cuttingL, waste, convCostKg, profit, tax, overhead, noOfBox);
                break;
            }
            case "5Ply": {
                formulaForFivePly(bfInTopPaper, gsmInTop, rateKgInTop, bfInFlutePaper, gsmInFlutePaper, rateKgInFlutePaper, ffInFluteOnePaper, bfInMiddleOnePaper, gsmInMiddleOnePaper, rateKgInMiddleOnePaper, bfInFluteTwoPaper, gsmInFluteTwoPaper, rateKgInFluteTwoPaper, ffInFluteTwoPaper, bfInBottomPaper, gsmInBottomPaper, rateKgInBottomPaper, decalM, cuttingL, waste, convCostKg, profit, tax, overhead, noOfBox);
                break;
            }
            case "7Ply": {
                formulaForSevenPly(bfInTopPaper, gsmInTop, rateKgInTop, bfInFlutePaper, gsmInFlutePaper, rateKgInFlutePaper, ffInFluteOnePaper, bfInMiddleOnePaper, gsmInMiddleOnePaper, rateKgInMiddleOnePaper, bfInFluteTwoPaper, gsmInFluteTwoPaper, rateKgInFluteTwoPaper, ffInFluteTwoPaper, bfInMiddleTwoPaper, gsmInMiddleTwoPaper, rateKgInMiddleTwoPaper, bfInFluteThreePaper, gsmInFluteThreePaper, rateKgInFluteThreePaper, ffInFluteThreePaper, bfInBottomPaper, gsmInBottomPaper, rateKgInBottomPaper, decalM, cuttingL, waste, convCostKg, profit, tax, overhead, noOfBox);
                break;
            }
        }

        activityBoxSpecificationAndCostBinding.btBackInBoxSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void formulaForSevenPly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String bfInMiddleOnePaper, String gsmInMiddleOnePaper, String rateKgInMiddleOnePaper, String bfInFluteTwoPaper, String gsmInFluteTwoPaper, String rateKgInFluteTwoPaper, String ffInFluteTwoPaper, String bfInMiddleTwoPaper, String gsmInMiddleTwoPaper, String rateKgInMiddleTwoPaper, String bfInFluteThreePaper, String gsmInFluteThreePaper, String rateKgInFluteThreePaper, String ffInFluteThreePaper, String bfInBottomPaper, String gsmInBottomPaper, String rateKgInBottomPaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        // TotalBS Formula
        //Value for TopPaper
        float bfOfTop = Float.parseFloat(bfInTopPaper);
        float gsmOfTop = Float.parseFloat(gsmInTop);
        float valOne = (bfOfTop * gsmOfTop * 1) / divide;
        //Value for FluteOnePaper
        float bfOfFlute = Float.parseFloat(bfInFlutePaper);
        float gsmOfFlute = Float.parseFloat(gsmInFlutePaper);
        float valTwo = (float) ((bfOfFlute * gsmOfFlute * 0.5) / divide);
        //Value for Middle One paper
        float bfOfMiddle = Float.parseFloat(bfInMiddleOnePaper);
        float gsmOfMiddle = Float.parseFloat(gsmInMiddleOnePaper);
        float valThree = (bfOfMiddle * gsmOfMiddle * 1) / divide;
        //Value for FluteTwoPaper
        float bfOfFluteTwo = Float.parseFloat(bfInFluteTwoPaper);
        float gsmOfFluteTwo = Float.parseFloat(gsmInFluteTwoPaper);
        float valFour = (float) ((bfOfFluteTwo * gsmOfFluteTwo * 0.5) / divide);
        //value for Middle Two Paper
        float bfOfMiddleTwo = Float.parseFloat(bfInMiddleTwoPaper);
        float gsmOfMiddleTwo = Float.parseFloat(gsmInMiddleTwoPaper);
        float valFive = (float) ((bfOfMiddleTwo * gsmOfMiddleTwo * 1) / divide);
        //Value for Flute Three Paper
        float bfOfFluteThree = Float.parseFloat(bfInFluteThreePaper);
        float gsmOfFluteThree = Float.parseFloat(gsmInFluteThreePaper);
        float valSix = (float) ((bfOfFluteThree * gsmOfFluteThree * 0.5) / divide);
        //Value for Bottom
        float bfOFBottomPaper = Float.parseFloat(bfInBottomPaper);
        float gsmOfBottomPaper = Float.parseFloat(gsmInBottomPaper);
        float valSeven = (bfOFBottomPaper * gsmOfBottomPaper * 1) / divide;

        //Total BS
        float totalBs = (valOne + valTwo + valThree + valFour + valFive+valSix+valSeven);
        String resOfTotalBs = String.format("%.2f", totalBs);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);
        //TotalGsm
        //Value for TopPaper
        float valueOneOFGsm = (gsmOfTop * 1);
        //Value for fluteOnePaper
        float ffOfFluteOnePaper = Float.parseFloat(ffInFluteOnePaper);
        float valTwoOfGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Value for Middle Paper
        float vlaThreeOfGsm = (gsmOfMiddle * 1);
        //Value for fluteTwoPaper
        float ffOfFluteTwoPaper = Float.parseFloat(ffInFluteTwoPaper);
        float valueFourOfGsm = (gsmOfFluteTwo * ffOfFluteTwoPaper);
        //Value for Middle Two paper
        float valFiveOfGsm = (gsmOfMiddleTwo * 1);
        //Value for Flute Three
        float ffOfFluteThreePaper = Float.parseFloat(ffInFluteThreePaper);
        float valueSixOfGsm = (gsmOfFluteTwo * ffOfFluteThreePaper);
        //Value for Bottom
        float valSevenOfGsm = (gsmOfBottomPaper * 1);
        //totalGSm
        float totalGsm = (valueOneOFGsm + valTwoOfGsm + vlaThreeOfGsm + valueFourOfGsm + valFiveOfGsm + valueSixOfGsm+valSevenOfGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));

        //TotalWeight
        //Value for topPaper
        float decal = Float.parseFloat(decalM);
        float cutting = Float.parseFloat(cuttingL);
        float valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);

        //Value for flute one
        float valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);

        //Value for middle Paper
        float valueThirdOFTW = (decal * cutting * gsmOfMiddle * mm * mm / divide / divide / divide);

        //Value for FluteTwoPaper
        float valueFourthOfTW = (decal * cutting * gsmOfFluteTwo * ffOfFluteTwoPaper * mm * mm / divide / divide / divide);

        //Value for MiddleTwoPaper
        float valueFifthOfTW = (decal * cutting * gsmOfMiddleTwo * mm * mm / divide / divide / divide);

        //Value for FluteThreePaper
        float valueSixthOfTW = (decal * cutting * gsmOfFluteThree * ffOfFluteThreePaper * mm * mm / divide / divide / divide);

        //Value for BottomPaper
        float valueSeventhOfTW = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);
        //Total
        float total = (valueFirstOFTW + valueSecondOfTW + valueThirdOFTW + valueFourthOfTW + valueFifthOfTW + valueSixthOfTW + valueSeventhOfTW);
        //Waste value
        float wasteFromTiet = Float.parseFloat(waste);
        float wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        float totalWeight = ((total + wastePercentage)/noOFBoxUps);
        String resultOfTotalWeight = String.format("%.3f", totalWeight);
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(resultOfTotalWeight);
        //ConversionCostPerKG
        float convCostTiet = Float.parseFloat(convCostKg);
        float resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);

        //Costs
        //NetPaperCost
        //TopPaperCost
        float rateOfTop = Float.parseFloat(rateKgInTop);
        float valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //FluteOnePaperCost
        float rateOfFlute = Float.parseFloat(rateKgInFlutePaper);
        float valueSecondNPC = (valueSecondOfTW * rateOfFlute);

        //MiddleOne PaperCost
        float rateOfMiddle = Float.parseFloat(rateKgInMiddleOnePaper);
        float valueThirdNPC = (valueThirdOFTW * rateOfMiddle);

        //FluteTwoPaperCost
        float rateOfFluteTwo = Float.parseFloat(rateKgInFluteTwoPaper);
        float valueFourthNPC = (valueFourthOfTW * rateOfFluteTwo);

        //MiddleTwoPaperCost
        float rateOfMiddleTwo = Float.parseFloat(rateKgInMiddleTwoPaper);
        float valueFifthNPC = (valueFifthOfTW * rateOfMiddleTwo);
        //Flute Three paper Cost
        float rateOfFluteThree = Float.parseFloat(rateKgInFluteThreePaper);
        float valueSixthNPC = (valueSixthOfTW * rateOfFluteThree);
        // Bottom Paper
        float rateOfBottom = Float.parseFloat(rateKgInBottomPaper);
        float valueSeventhNPC = (valueSeventhOfTW * rateOfBottom);
        //NPC
        float resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC + valueFourthNPC + valueFifthNPC + valueSixthNPC + valueSeventhNPC))/noOFBoxUps;
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);
        //WasteCost
        float resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);
        //GrossPaperCost
        float grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfGrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfGrossPaperCost);
        //BoxMFGCost
//        float overheadChargesTiet=Float.parseFloat(overhead);
        double resultOfBoxMFG = (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        float profitFromTiet = Float.parseFloat(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfBoxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfBoxPrice);

        //Box Price with Tax
        float taxFromTiet = Float.parseFloat(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfBoxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfBoxPriceWithTax);
    }

    private void formulaForFivePly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String bfInMiddleOnePaper, String gsmInMiddleOnePaper, String rateKgInMiddleOnePaper, String bfInFluteTwoPaper, String gsmInFluteTwoPaper, String rateKgInFluteTwoPaper, String ffInFluteTwoPaper, String bfInBottomPaper, String gsmInBottomPaper, String rateKgInBottomPaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        // TotalBS Formula
        //Value for TopPaper
        float bfOfTop = Float.parseFloat(bfInTopPaper);
        float gsmOfTop = Float.parseFloat(gsmInTop);
        float valOne = (bfOfTop * gsmOfTop * 1) / divide;
        //Value for FluteOnePaper
        float bfOfFlute = Float.parseFloat(bfInFlutePaper);
        float gsmOfFlute = Float.parseFloat(gsmInFlutePaper);
        float valTwo = (float) ((bfOfFlute * gsmOfFlute * 0.5) / divide);
        //Value for Middle paper
        float bfOfMiddle = Float.parseFloat(bfInMiddleOnePaper);
        float gsmOfMiddle = Float.parseFloat(gsmInMiddleOnePaper);
        float valThree = (bfOfMiddle * gsmOfMiddle * 1) / divide;
        //Value for FluteTwoPaper
        float bfOfFluteTwo = Float.parseFloat(bfInFluteTwoPaper);
        float gsmOfFluteTwo = Float.parseFloat(gsmInFluteTwoPaper);
        float valFour = (float) ((bfOfFluteTwo * gsmOfFluteTwo * 0.5) / divide);
        //Value for Bottom
        float bfOFBottomPaper = Float.parseFloat(bfInBottomPaper);
        float gsmOfBottomPaper = Float.parseFloat(gsmInBottomPaper);
        float valFive = (bfOFBottomPaper * gsmOfBottomPaper * 1) / divide;
        //Total BS
        float totalBs = (valOne + valTwo + valThree + valFour + valFive);
        String resOfTotalBs = String.format("%.2f", totalBs);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);

        //TotalGsm
        //Value for TopPaper
        float valueOneOFGsm = (gsmOfTop * 1);
        //Value for fluteOnePaper
        float ffOfFluteOnePaper = Float.parseFloat(ffInFluteOnePaper);
        float valTwoOfGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Value for Middle Paper
        float vlaThreeOfGsm = (gsmOfMiddle * 1);
        //Value for fluteTwoPaper
        float ffOfFluteTwoPaper = Float.parseFloat(ffInFluteTwoPaper);
        float valueFourOfGsm = (gsmOfFluteTwo * ffOfFluteTwoPaper);
        //Value for BottomPaper
        float valFiveOfGsm = (gsmOfBottomPaper * 1);
        //totalGSm
        float totalGsm = (valueOneOFGsm + valTwoOfGsm + vlaThreeOfGsm + valueFourOfGsm + valFiveOfGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));

        //TotalWeight
        //Value for topPaper
        float decal = Float.parseFloat(decalM);
        float cutting = Float.parseFloat(cuttingL);
        float valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);

        //Value for flute one
        float valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);

        //Value for middle Paper
        float valueThirdOFTW = (decal * cutting * gsmOfMiddle * mm * mm / divide / divide / divide);

        //Value for FluteTwoPaper
        float valueFourthOfTW = (decal * cutting * gsmOfFlute * ffOfFluteTwoPaper * mm * mm / divide / divide / divide);
        //Value for BottomPaper
        float valueFifthOfTW = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);

        //Total
        float total = (valueFirstOFTW + valueSecondOfTW + valueThirdOFTW + valueFourthOfTW + valueFifthOfTW);
        //Waste value
        float wasteFromTiet = Float.parseFloat(waste);
        float wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        float totalWeight = (total + wastePercentage)/noOFBoxUps;
        String resultOfTotalWeight = String.format("%.3f", totalWeight);
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(resultOfTotalWeight);
        //ConversionCostPerKG
        float convCostTiet = Float.parseFloat(convCostKg);
        float resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);

        //Costs
        //NetPaperCost
        //TopPaperCost
        float rateOfTop = Float.parseFloat(rateKgInTop);
        float valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //FluteOnePaperCost
        float rateOfFlute = Float.parseFloat(rateKgInFlutePaper);
        float valueSecondNPC = (valueSecondOfTW * rateOfFlute);

        //MiddlePaperCost
        float rateOfMiddle = Float.parseFloat(rateKgInMiddleOnePaper);
        float valueThirdNPC = (valueThirdOFTW * rateOfMiddle);

        //FluteTwoPaperCost
        float rateOfFluteTwo = Float.parseFloat(rateKgInFluteTwoPaper);
        float valueFourthNPC = (valueFourthOfTW * rateOfFluteTwo);
        //Bottom Paper Cost
        float rateOfBottom = Float.parseFloat(rateKgInBottomPaper);
        float valueFifthNPC = (valueFifthOfTW * rateOfBottom);
        //NetPaperCost
        float resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC + valueFourthNPC + valueFifthNPC)/noOFBoxUps);
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);
        //WasteCost
        float resultWasteCost = ((resultOfNPC * 3) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);
        //GrossPaperCost
        float grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfGrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfGrossPaperCost);
        //BoxMFGCost
        //        float overheadChargesTiet=Float.parseFloat(overhead);
        double resultOfBoxMFG = (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        float profitFromTiet = Float.parseFloat(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfBoxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfBoxPrice);

        //Box Price with Tax
        float taxFromTiet = Float.parseFloat(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfBoxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfBoxPriceWithTax);
    }

    private void formulaForTwoPly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        //TotalBS Formula
        //value for TopPaper
        float bsOfTop = Float.parseFloat(bfInTopPaper);
        float gsmOfTop = Float.parseFloat(gsmInTop);

        float val1 = (bsOfTop * gsmOfTop * 1) / divide;
        //Value for FlutePaper
        float bsOfFlute = Float.parseFloat(bfInFlutePaper);
        float gsmOfFlute = Float.parseFloat(gsmInFlutePaper);

        float val2 = (float) ((bsOfFlute * gsmOfFlute * 0.5) / divide);
        //Total BS
        float totalBS = (val1 + val2);
        String resOfTotalBs = String.format("%.2f", totalBS);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);
        //Total GSM
        //Value for Top
        float val1ofGsm = (gsmOfTop * 1);
        //Value for flute
        float ffOfFluteOnePaper = Float.parseFloat(ffInFluteOnePaper);
        float val2ofGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Total Gsm
        float totalGsm = (val1ofGsm + val2ofGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));
        //Total Weight
        //1St Value
        float decal = Float.parseFloat(decalM);
        float cutting = Float.parseFloat(cuttingL);
        float valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);
        //2nd Value
        float valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);
        //Total

        float total = (valueFirstOFTW + valueSecondOfTW);
        String totalString = String.format("%.3f", total);
        //Waste value
        float wasteFromTiet = Float.parseFloat(waste);
        float wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        float totalWeight = (total + wastePercentage)/noOFBoxUps;
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(String.valueOf(totalWeight));
        //ConversionCostPerKG
        float convCostTiet = Float.parseFloat(convCostKg);
        float resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);
        //Costs
        //NetPaperCost
        //1stValue
        float rateOfTop = Float.parseFloat(rateKgInTop);
        float valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //2ndValue
        float rateOfFlute = Float.parseFloat(rateKgInFlutePaper);
        float valueSecondNPC = (valueSecondOfTW * rateOfFlute);
        //NetPaperCost
        float resultOfNPC = ((valueFirstNPC + valueSecondNPC)/noOFBoxUps);
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);

        //WasteCost
        float resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);

        //GrossPaperCost
        float grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfgrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfgrossPaperCost);

        //BoxMFGCost
//        float overheadChargesTiet=Float.parseFloat(overhead);
        double resultOfBoxMFG = (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        float profitFromTiet = Float.parseFloat(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfboxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfboxPrice);

        //Box Price with Tax
        float taxFromTiet = Float.parseFloat(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
    }

    private void formulaForThreePly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String bfInBottomPaper, String gsmInBottomPaper, String rateKgInBottomPaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
//         TotalBS Formula
        //value for TopPaper
        float bsOfTop = Float.parseFloat(bfInTopPaper);
        float gsmOfTop = Float.parseFloat(gsmInTop);

        float val1 = (bsOfTop * gsmOfTop * 1) / divide;
        //Value for FlutePaper
        float bsOfFlute = Float.parseFloat(bfInFlutePaper);
        float gsmOfFlute = Float.parseFloat(gsmInFlutePaper);

        float val2 = (float) ((bsOfFlute * gsmOfFlute * 0.5) / divide);
        //Value for BottomPaper
        float bfOFBottomPaper = Float.parseFloat(bfInBottomPaper);
        float gsmOfBottomPaper = Float.parseFloat(gsmInBottomPaper);

        float val3 = (bfOFBottomPaper * gsmOfBottomPaper * 1) / divide;
        //Total BS
        float totalBS = (val1 + val2 + val3);
        String resOfTotalBs = String.format("%.2f", totalBS);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);

        //Total GSM
        //Value for Top
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
        float wasteFromTiet = Float.parseFloat(waste);
        float wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        float totalWeight = (total + wastePercentage) / noOFBoxUps;
        int weightInInt = (int) (totalWeight * 1000);
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(String.valueOf(totalWeight));

        //ConversionCostPerKG
        float convCostTiet = Float.parseFloat(convCostKg);
        float resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
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

        float resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC) / noOFBoxUps);
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);

        //WasteCost
        float resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);

        //GrossPaperCost
        float grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfgrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfgrossPaperCost);

        //BoxMFGCost
//        float overheadChargesTiet=Float.parseFloat(overhead);
        float resultOfBoxMFG = (float) (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        float profitFromTiet = Float.parseFloat(profit);
        float boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfboxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfboxPrice);

        //Box Price with Tax
        float taxFromTiet = Float.parseFloat(tax);
        float boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
    }

    private void formulaForOnePly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        //GSM
        float gsmForOnePly = Float.parseFloat(gsmInTop);
        float resTotalGsm = (gsmForOnePly * 1);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(resTotalGsm));

        //BS
        float bsOfTop = Float.parseFloat(bfInTopPaper);
        float gsmOfTop = Float.parseFloat(gsmInTop);
        float totalBs = (bsOfTop * gsmOfTop * 1) / 1000;
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(String.valueOf(totalBs));

        //Total Weight
        //wtInTopPaper
        float decalValue = Float.parseFloat(decalM);
        float cuttingValue = Float.parseFloat(cuttingL);
        float totalWeightInTopPaper = (decalValue * cuttingValue * gsmForOnePly * mm * mm / divide / divide / divide);

        //Waste
        float wasteFromTiet = Float.parseFloat(waste);
        float wastePercentage = ((totalWeightInTopPaper * wasteFromTiet) / 100);

        //totalWeigth
        int noOFBoxUps = Integer.parseInt(noOfBox);
        float totalWeight = (totalWeightInTopPaper + wastePercentage) / noOFBoxUps;
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(String.valueOf(totalWeight));

        //NetPaperCost
        //CostValue
        float rateKgInTopForOnePly = Float.parseFloat(rateKgInTop);
        float costValue = (totalWeightInTopPaper * rateKgInTopForOnePly);

        //NetpaperCostForOnePly
        double netPaperCost = (costValue / noOFBoxUps);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(netPaperCost));

        //WasteCost
        float resultWasteCost = ((costValue * wasteFromTiet) / 100);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCost));
        //GrossPaperCost
        float grossPaperCost = (costValue + resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCost));

        //ConversionCostPerKG
        float convCostTiet = Float.parseFloat(convCostKg);
        float resultOfConvCost = (totalWeightInTopPaper * convCostTiet);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(String.valueOf(resultOfConvCost));

        //BoxMFGCost
//        float overheadChargesTiet=Float.parseFloat(overhead);
        float resultOfBoxMFG = (float) (grossPaperCost + resultOfConvCost + 1.5);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFG));

        //Box Price
        float profitFromTiet = Float.parseFloat(profit);
        float boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(String.valueOf(boxPrice));
        //Box Price with Tax
        float taxFromTiet = Float.parseFloat(tax);
        float boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(String.valueOf(boxPriceWithTax));
    }


}