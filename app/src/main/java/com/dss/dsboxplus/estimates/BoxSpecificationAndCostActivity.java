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
            case "2Ply(KG)": {
//                formulaForTwoPlyKg()
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

    private void formulaForTwoPlyKg() {
    }

    private void formulaForSevenPly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String bfInMiddleOnePaper, String gsmInMiddleOnePaper, String rateKgInMiddleOnePaper, String bfInFluteTwoPaper, String gsmInFluteTwoPaper, String rateKgInFluteTwoPaper, String ffInFluteTwoPaper, String bfInMiddleTwoPaper, String gsmInMiddleTwoPaper, String rateKgInMiddleTwoPaper, String bfInFluteThreePaper, String gsmInFluteThreePaper, String rateKgInFluteThreePaper, String ffInFluteThreePaper, String bfInBottomPaper, String gsmInBottomPaper, String rateKgInBottomPaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        // TotalBS Formula
        //Value for TopPaper
        double bfOfTop = Double.parseDouble(bfInTopPaper);
        double gsmOfTop = Double.parseDouble(gsmInTop);
        double valOne = (bfOfTop * gsmOfTop * 1) / divide;
        //Value for FluteOnePaper
        double bfOfFlute = Double.parseDouble(bfInFlutePaper);
        double gsmOfFlute = Double.parseDouble(gsmInFlutePaper);
        double valTwo = (double) ((bfOfFlute * gsmOfFlute * 0.5) / divide);
        //Value for Middle One paper
        double bfOfMiddle = Double.parseDouble(bfInMiddleOnePaper);
        double gsmOfMiddle = Double.parseDouble(gsmInMiddleOnePaper);
        double valThree = (bfOfMiddle * gsmOfMiddle * 1) / divide;
        //Value for FluteTwoPaper
        double bfOfFluteTwo = Double.parseDouble(bfInFluteTwoPaper);
        double gsmOfFluteTwo = Double.parseDouble(gsmInFluteTwoPaper);
        double valFour = (double) ((bfOfFluteTwo * gsmOfFluteTwo * 0.5) / divide);
        //value for Middle Two Paper
        double bfOfMiddleTwo = Double.parseDouble(bfInMiddleTwoPaper);
        double gsmOfMiddleTwo = Double.parseDouble(gsmInMiddleTwoPaper);
        double valFive = (double) ((bfOfMiddleTwo * gsmOfMiddleTwo * 1) / divide);
        //Value for Flute Three Paper
        double bfOfFluteThree = Double.parseDouble(bfInFluteThreePaper);
        double gsmOfFluteThree = Double.parseDouble(gsmInFluteThreePaper);
        double valSix = (double) ((bfOfFluteThree * gsmOfFluteThree * 0.5) / divide);
        //Value for Bottom
        double bfOFBottomPaper = Double.parseDouble(bfInBottomPaper);
        double gsmOfBottomPaper = Double.parseDouble(gsmInBottomPaper);
        double valSeven = (bfOFBottomPaper * gsmOfBottomPaper * 1) / divide;

        //Total BS
        double totalBs = (valOne + valTwo + valThree + valFour + valFive + valSix + valSeven);
        String resOfTotalBs = String.format("%.2f", totalBs);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);
        //TotalGsm
        //Value for TopPaper
        double valueOneOFGsm = (gsmOfTop * 1);
        //Value for fluteOnePaper
        double ffOfFluteOnePaper = Double.parseDouble(ffInFluteOnePaper);
        double valTwoOfGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Value for Middle Paper
        double vlaThreeOfGsm = (gsmOfMiddle * 1);
        //Value for fluteTwoPaper
        double ffOfFluteTwoPaper = Double.parseDouble(ffInFluteTwoPaper);
        double valueFourOfGsm = (gsmOfFluteTwo * ffOfFluteTwoPaper);
        //Value for Middle Two paper
        double valFiveOfGsm = (gsmOfMiddleTwo * 1);
        //Value for Flute Three
        double ffOfFluteThreePaper = Double.parseDouble(ffInFluteThreePaper);
        double valueSixOfGsm = (gsmOfFluteTwo * ffOfFluteThreePaper);
        //Value for Bottom
        double valSevenOfGsm = (gsmOfBottomPaper * 1);
        //totalGSm
        double totalGsm = (valueOneOFGsm + valTwoOfGsm + vlaThreeOfGsm + valueFourOfGsm + valFiveOfGsm + valueSixOfGsm + valSevenOfGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));

        //TotalWeight
        //Value for topPaper
        double decal = Double.parseDouble(decalM);
        double cutting = Double.parseDouble(cuttingL);
        double valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);

        //Value for flute one
        double valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);

        //Value for middle Paper
        double valueThirdOFTW = (decal * cutting * gsmOfMiddle * mm * mm / divide / divide / divide);

        //Value for FluteTwoPaper
        double valueFourthOfTW = (decal * cutting * gsmOfFluteTwo * ffOfFluteTwoPaper * mm * mm / divide / divide / divide);

        //Value for MiddleTwoPaper
        double valueFifthOfTW = (decal * cutting * gsmOfMiddleTwo * mm * mm / divide / divide / divide);

        //Value for FluteThreePaper
        double valueSixthOfTW = (decal * cutting * gsmOfFluteThree * ffOfFluteThreePaper * mm * mm / divide / divide / divide);

        //Value for BottomPaper
        double valueSeventhOfTW = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);
        //Total
        double total = (valueFirstOFTW + valueSecondOfTW + valueThirdOFTW + valueFourthOfTW + valueFifthOfTW + valueSixthOfTW + valueSeventhOfTW);
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = ((total + wastePercentage) / noOFBoxUps);
        String resultOfTotalWeight = String.format("%.3f", totalWeight);
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(resultOfTotalWeight);
        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);

        //Costs
        //NetPaperCost
        //TopPaperCost
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //FluteOnePaperCost
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOfTW * rateOfFlute);

        //MiddleOne PaperCost
        double rateOfMiddle = Double.parseDouble(rateKgInMiddleOnePaper);
        double valueThirdNPC = (valueThirdOFTW * rateOfMiddle);

        //FluteTwoPaperCost
        double rateOfFluteTwo = Double.parseDouble(rateKgInFluteTwoPaper);
        double valueFourthNPC = (valueFourthOfTW * rateOfFluteTwo);

        //MiddleTwoPaperCost
        double rateOfMiddleTwo = Double.parseDouble(rateKgInMiddleTwoPaper);
        double valueFifthNPC = (valueFifthOfTW * rateOfMiddleTwo);
        //Flute Three paper Cost
        double rateOfFluteThree = Double.parseDouble(rateKgInFluteThreePaper);
        double valueSixthNPC = (valueSixthOfTW * rateOfFluteThree);
        // Bottom Paper
        double rateOfBottom = Double.parseDouble(rateKgInBottomPaper);
        double valueSeventhNPC = (valueSeventhOfTW * rateOfBottom);
        //NPC
        double resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC + valueFourthNPC + valueFifthNPC + valueSixthNPC + valueSeventhNPC)) / noOFBoxUps;
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);
        //WasteCost
        double resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);
        //GrossPaperCost
        double grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfGrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfGrossPaperCost);
        //BoxMFGCost
//        float overheadChargesTiet=Double.parseDouble(overhead);
        double resultOfBoxMFG = (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfBoxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfBoxPrice);

        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfBoxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfBoxPriceWithTax);
    }

    private void formulaForFivePly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String bfInMiddleOnePaper, String gsmInMiddleOnePaper, String rateKgInMiddleOnePaper, String bfInFluteTwoPaper, String gsmInFluteTwoPaper, String rateKgInFluteTwoPaper, String ffInFluteTwoPaper, String bfInBottomPaper, String gsmInBottomPaper, String rateKgInBottomPaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        // TotalBS Formula
        //Value for TopPaper
        double bfOfTop = Double.parseDouble(bfInTopPaper);
        double gsmOfTop = Double.parseDouble(gsmInTop);
        double valOne = (bfOfTop * gsmOfTop * 1) / divide;
        //Value for FluteOnePaper
        double bfOfFlute = Double.parseDouble(bfInFlutePaper);
        double gsmOfFlute = Double.parseDouble(gsmInFlutePaper);
        double valTwo = (double) ((bfOfFlute * gsmOfFlute * 0.5) / divide);
        //Value for Middle paper
        double bfOfMiddle = Double.parseDouble(bfInMiddleOnePaper);
        double gsmOfMiddle = Double.parseDouble(gsmInMiddleOnePaper);
        double valThree = (bfOfMiddle * gsmOfMiddle * 1) / divide;
        //Value for FluteTwoPaper
        double bfOfFluteTwo = Double.parseDouble(bfInFluteTwoPaper);
        double gsmOfFluteTwo = Double.parseDouble(gsmInFluteTwoPaper);
        double valFour = (double) ((bfOfFluteTwo * gsmOfFluteTwo * 0.5) / divide);
        //Value for Bottom
        double bfOFBottomPaper = Double.parseDouble(bfInBottomPaper);
        double gsmOfBottomPaper = Double.parseDouble(gsmInBottomPaper);
        double valFive = (bfOFBottomPaper * gsmOfBottomPaper * 1) / divide;
        //Total BS
        double totalBs = (valOne + valTwo + valThree + valFour + valFive);
        String resOfTotalBs = String.format("%.2f", totalBs);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);

        //TotalGsm
        //Value for TopPaper
        double valueOneOFGsm = (gsmOfTop * 1);
        //Value for fluteOnePaper
        double ffOfFluteOnePaper = Double.parseDouble(ffInFluteOnePaper);
        double valTwoOfGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Value for Middle Paper
        double vlaThreeOfGsm = (gsmOfMiddle * 1);
        //Value for fluteTwoPaper
        double ffOfFluteTwoPaper = Double.parseDouble(ffInFluteTwoPaper);
        double valueFourOfGsm = (gsmOfFluteTwo * ffOfFluteTwoPaper);
        //Value for BottomPaper
        double valFiveOfGsm = (gsmOfBottomPaper * 1);
        //totalGSm
        double totalGsm = (valueOneOFGsm + valTwoOfGsm + vlaThreeOfGsm + valueFourOfGsm + valFiveOfGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));

        //TotalWeight
        //Value for topPaper
        double decal = Double.parseDouble(decalM);
        double cutting = Double.parseDouble(cuttingL);
        double valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);

        //Value for flute one
        double valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);

        //Value for middle Paper
        double valueThirdOFTW = (decal * cutting * gsmOfMiddle * mm * mm / divide / divide / divide);

        //Value for FluteTwoPaper
        double valueFourthOfTW = (decal * cutting * gsmOfFlute * ffOfFluteTwoPaper * mm * mm / divide / divide / divide);
        //Value for BottomPaper
        double valueFifthOfTW = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);

        //Total
        double total = (valueFirstOFTW + valueSecondOfTW + valueThirdOFTW + valueFourthOfTW + valueFifthOfTW);
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (total + wastePercentage) / noOFBoxUps;
        String resultOfTotalWeight = String.format("%.3f", totalWeight);
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(resultOfTotalWeight);
        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);

        //Costs
        //NetPaperCost
        //TopPaperCost
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //FluteOnePaperCost
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOfTW * rateOfFlute);

        //MiddlePaperCost
        double rateOfMiddle = Double.parseDouble(rateKgInMiddleOnePaper);
        double valueThirdNPC = (valueThirdOFTW * rateOfMiddle);

        //FluteTwoPaperCost
        double rateOfFluteTwo = Double.parseDouble(rateKgInFluteTwoPaper);
        double valueFourthNPC = (valueFourthOfTW * rateOfFluteTwo);
        //Bottom Paper Cost
        double rateOfBottom = Double.parseDouble(rateKgInBottomPaper);
        double valueFifthNPC = (valueFifthOfTW * rateOfBottom);
        //NetPaperCost
        double resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC + valueFourthNPC + valueFifthNPC) / noOFBoxUps);
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);
        //WasteCost
        double resultWasteCost = ((resultOfNPC * 3) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);
        //GrossPaperCost
        double grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfGrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfGrossPaperCost);
        //BoxMFGCost
        //        float overheadChargesTiet=Double.parseDouble(overhead);
        double resultOfBoxMFG = (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfBoxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfBoxPrice);

        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfBoxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfBoxPriceWithTax);
    }

    private void formulaForTwoPly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        //TotalBS Formula
        //value for TopPaper
        double bsOfTop = Double.parseDouble(bfInTopPaper);
        double gsmOfTop = Double.parseDouble(gsmInTop);

        double val1 = (bsOfTop * gsmOfTop * 1) / divide;
        //Value for FlutePaper
        double bsOfFlute = Double.parseDouble(bfInFlutePaper);
        double gsmOfFlute = Double.parseDouble(gsmInFlutePaper);

        double val2 = ((bsOfFlute * gsmOfFlute * 0.5) / divide);
        //Total BS
        double totalBS = (val1 + val2);
        String resOfTotalBs = String.format("%.2f", totalBS);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);
        //Total GSM
        //Value for Top
        double val1ofGsm = (gsmOfTop * 1);
        //Value for flute
        double ffOfFluteOnePaper = Double.parseDouble(ffInFluteOnePaper);
        double val2ofGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Total Gsm
        double totalGsm = (val1ofGsm + val2ofGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));
        //Total Weight
        //1St Value
        double decal = Double.parseDouble(decalM);
        double cutting = Double.parseDouble(cuttingL);
        double valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);
        //2nd Value
        double valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);
        //Total

        double total = (valueFirstOFTW + valueSecondOfTW);
        String totalString = String.format("%.3f", total);
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (total + wastePercentage) / noOFBoxUps;
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(String.valueOf(totalWeight));
        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);
        //Costs
        //NetPaperCost
        //1stValue
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //2ndValue
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOfTW * rateOfFlute);
        //NetPaperCost
        double resultOfNPC = ((valueFirstNPC + valueSecondNPC) / noOFBoxUps);
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);

        //WasteCost
        double resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);

        //GrossPaperCost
        double grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfgrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfgrossPaperCost);

        //BoxMFGCost
//        float overheadChargesTiet=Double.parseDouble(overhead);
        double resultOfBoxMFG = (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfboxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfboxPrice);

        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
    }

    private void formulaForThreePly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String bfInBottomPaper, String gsmInBottomPaper, String rateKgInBottomPaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
//         TotalBS Formula
        //value for TopPaper
        double bsOfTop = Double.parseDouble(bfInTopPaper);
        double gsmOfTop = Double.parseDouble(gsmInTop);

        double val1 = (bsOfTop * gsmOfTop * 1) / divide;
        //Value for FlutePaper
        double bsOfFlute = Double.parseDouble(bfInFlutePaper);
        double gsmOfFlute = Double.parseDouble(gsmInFlutePaper);

        double val2 = (float) ((bsOfFlute * gsmOfFlute * 0.5) / divide);
        //Value for BottomPaper
        double bfOFBottomPaper = Double.parseDouble(bfInBottomPaper);
        double gsmOfBottomPaper = Double.parseDouble(gsmInBottomPaper);

        double val3 = (bfOFBottomPaper * gsmOfBottomPaper * 1) / divide;
        //Total BS
        double totalBS = (val1 + val2 + val3);
        String resOfTotalBs = String.format("%.2f", totalBS);
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(resOfTotalBs);

        //Total GSM
        //Value for Top
        double val1ofGsm = (gsmOfTop * 1);
        //Value for flute
        double ffOfFluteOnePaper = Double.parseDouble(ffInFluteOnePaper);
        double val2ofGsm = (gsmOfFlute * ffOfFluteOnePaper);
        //Value for Bottom
        double val3ofGsm = (gsmOfBottomPaper * 1);
        //Total Gsm
        double totalGsm = (val1ofGsm + val2ofGsm + val3ofGsm);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(totalGsm));

        //Total Weight
        //1St Value
        double decal = Double.parseDouble(decalM);
        double cutting = Double.parseDouble(cuttingL);
        double valueFirstOFTW = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);

        //2nd Value
        double valueSecondOfTW = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);


        //3rd Value
        double valueThirdOfTW = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);

        //Total
        double total = (valueFirstOFTW + valueSecondOfTW + valueThirdOfTW);
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((total * wasteFromTiet) / 100);
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (total + wastePercentage) / noOFBoxUps;
        int weightInInt = (int) (totalWeight * 1000);
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(String.valueOf(totalWeight));

        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeight * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);


        //Costs
        //NetPaperCost
        //1stValue
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTW * rateOfTop);

        //2ndValue
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOfTW * rateOfFlute);

        //3rdValue
        double rateOfBottom = Double.parseDouble(rateKgInBottomPaper);
        double valueThirdNPC = (valueThirdOfTW * rateOfBottom);
        //NetPaperCost

        double resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC) / noOFBoxUps);
        String resultOfNpcString = String.format("%.3f", resultOfNPC);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(resultOfNpcString);

        //WasteCost
        double resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        String resultOfwasteString = String.format("%.3f", resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(resultOfwasteString);

        //GrossPaperCost
        double grossPaperCost = (resultOfNPC + resultWasteCost);
        String resultOfgrossPaperCost = String.format("%.3f", grossPaperCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(resultOfgrossPaperCost);

        //BoxMFGCost
//        float overheadChargesTiet=Double.parseDouble(overhead);
        double resultOfBoxMFG = (double) (grossPaperCost + resultOfConvCost + 1.5);
        String resultOfBoxMFGString = String.format("%.3f", resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(resultOfBoxMFGString);

        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100) + resultOfBoxMFG);
        String resultOfboxPrice = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(resultOfboxPrice);

        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
    }

    private void formulaForOnePly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        //GSM
        double gsmForOnePly = Double.parseDouble(gsmInTop);
        double resTotalGsm = (gsmForOnePly * 1);
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(resTotalGsm));

        //BS
        double bsOfTop = Double.parseDouble(bfInTopPaper);
        double gsmOfTop = Double.parseDouble(gsmInTop);
        double totalBs = (bsOfTop * gsmOfTop * 1) / 1000;
        activityBoxSpecificationAndCostBinding.tvTotalBs.setText(String.valueOf(totalBs));

        //Total Weight
        //wtInTopPaper
        double decalValue = Double.parseDouble(decalM);
        double cuttingValue = Double.parseDouble(cuttingL);
        double totalWeightInTopPaper = (decalValue * cuttingValue * gsmForOnePly * mm * mm / divide / divide / divide);

        //Waste
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((totalWeightInTopPaper * wasteFromTiet) / 100.0);

        //totalWeigth
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (totalWeightInTopPaper + wastePercentage) / noOFBoxUps;
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(String.valueOf(totalWeight));

        //NetPaperCost
        //CostValue
        double rateKgInTopForOnePly = Double.parseDouble(rateKgInTop);
        double costValue = (totalWeightInTopPaper * rateKgInTopForOnePly);

        //NetpaperCostForOnePly
        double netPaperCost = (costValue / noOFBoxUps);
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(netPaperCost));

        //WasteCost
        double resultWasteCost = ((costValue * wasteFromTiet) / 100.0);
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCost));
        //GrossPaperCost
        double grossPaperCost = (costValue + resultWasteCost);
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCost));

        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeightInTopPaper * convCostTiet);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(String.valueOf(resultOfConvCost));

        //BoxMFGCost
//        float overheadChargesTiet=Double.parseDouble(overhead);
        double resultOfBoxMFG = (grossPaperCost + resultOfConvCost + 1.5);
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFG));

        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFG * profitFromTiet / 100.0) + resultOfBoxMFG);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(String.valueOf(boxPrice));
        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100.0) + boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(String.valueOf(boxPriceWithTax));
    }


}