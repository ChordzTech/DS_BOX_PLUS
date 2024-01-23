package com.dss.dsboxplus.estimates;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.CreateEstimateDataHolder;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityBoxSpecificationAndCostBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.estimatesviewmodels.BoxSpecificationAndCostActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;

public class BoxSpecificationAndCostActivity extends BaseActivity {
    private final double wasteFromTiet = 0.0;
    private final double wastePercentage = 0.0;
    ActivityBoxSpecificationAndCostBinding activityBoxSpecificationAndCostBinding;
    double mm = 25.4;
    double divide = 1000.0;
    String formula = "";
    private BoxSpecificationAndCostActivityViewModel viewModel;
    private String waste = "0";
    private String convCostKg = "0";
    private String profit = "0";
    private String tax = "0";
    private String overhead = "0";
    private DataItem dataItem;
    private Client selectedClient;
    private String resultOfProfitForED;
    private String resultOfTaxForED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoxSpecificationAndCostBinding = DataBindingUtil.setContentView(this, R.layout.activity_box_specification_and_cost);
        initView();
        initViewModel();
        addOververs();

    }


    private void addOververs() {
        viewModel.getCreateEstimateLiveData().observe(this, addEstimateResponse -> {
            Toast.makeText(this, "Estimate Added Successfully", Toast.LENGTH_SHORT).show();
            finishAffinity();
            startActivity(new Intent(BoxSpecificationAndCostActivity.this, HomeActivity.class));
        });
    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(BoxSpecificationAndCostActivityViewModel.class);

    }

    private void initView() {

        selectedClient = getIntent().getParcelableExtra("selectedClient");

        String boxName = getIntent().getStringExtra("boxName");
        String boxDimen = getIntent().getStringExtra("boxDimen");


        activityBoxSpecificationAndCostBinding.tvClientNameInboxSpecification.setText(selectedClient.getClientname());
        activityBoxSpecificationAndCostBinding.tvBoxNameInBoxSpecification.setText(boxName);
        activityBoxSpecificationAndCostBinding.tvBoxDimensionInBoxSpecification.setText(boxDimen);

//
//        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
//        if (appConfigResponse.getData() != null) {
//            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
//            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
//                // Access individual properties of AppConfigDataItems
//                int configId = appConfigDataItem.getConfigid();
//                String configValue = appConfigDataItem.getConfigvalue();
//                if (configId == 29) {
//                    double tietWaste = Double.parseDouble(configValue);
//                    activityBoxSpecificationAndCostBinding.tietWaste.setText(String.valueOf(tietWaste));
//                } else if (configId == 3) {
//                    double tietConversionCost = Double.parseDouble(configValue);
//                    activityBoxSpecificationAndCostBinding.tietConversionCost.setText(String.valueOf(tietConversionCost));
//                } else if (configId == 19) {
//                    double tietProfit = Double.parseDouble(configValue);
//                    activityBoxSpecificationAndCostBinding.tietProfit.setText(String.valueOf(tietProfit));
//                } else if (configId == 24) {
//                    double tietTax = Double.parseDouble(configValue);
//                    activityBoxSpecificationAndCostBinding.tietTax.setText(String.valueOf(tietTax));
//                }
//            }
//        }


        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
            activityBoxSpecificationAndCostBinding.tietWaste.setText(String.valueOf(businessDetails.getWaste()));
            activityBoxSpecificationAndCostBinding.tietConversionCost.setText(String.valueOf((businessDetails.getConversionrate())));
            activityBoxSpecificationAndCostBinding.tietProfit.setText(String.valueOf(businessDetails.getProfit()));
            activityBoxSpecificationAndCostBinding.tietTax.setText(String.valueOf(businessDetails.getTax()));
        }

        waste = activityBoxSpecificationAndCostBinding.tietWaste.getText().toString();
        activityBoxSpecificationAndCostBinding.tietWaste.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    waste = s.toString();
                    callFormula();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        convCostKg = activityBoxSpecificationAndCostBinding.tietConversionCost.getText().toString();
        activityBoxSpecificationAndCostBinding.tietConversionCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    convCostKg = s.toString();
                    callFormula();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        profit = activityBoxSpecificationAndCostBinding.tietProfit.getText().toString();
        activityBoxSpecificationAndCostBinding.tietProfit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    profit = s.toString();
                    callFormula();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tax = activityBoxSpecificationAndCostBinding.tietTax.getText().toString();
        activityBoxSpecificationAndCostBinding.tietTax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    tax = s.toString();
                    callFormula();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        overhead = activityBoxSpecificationAndCostBinding.tietOverHeadCharges.getText().toString();
        activityBoxSpecificationAndCostBinding.tietOverHeadCharges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    overhead = s.toString();
                    callFormula();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        callFormula();
        activityBoxSpecificationAndCostBinding.btBackInBoxSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activityBoxSpecificationAndCostBinding.btNextInBoxSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeValuesInEstimateDataHolder();
                callCreateEstimateAPI();
            }
        });
    }

    private void storeValuesInEstimateDataHolder() {
        CreateEstimateDataHolder.INSTANCE.setTotalGsm(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvTotalGsm.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setTotalBs(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvTotalBs.getText().toString()));
//        CreateEstimateDataHolder.INSTANCE.setTotalWeight(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvTotalWeight.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setNetPaperCost(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvNetPaperCost.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setGrossPaperCost(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvgrossPaperCost.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setConvCost(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvConversionCost.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setBoxMfg(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvBoxMFGCost.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setBoxPrice(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvBoxPrice.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setBoxPriceTax(Double.parseDouble(activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setWasteInput(Float.parseFloat(activityBoxSpecificationAndCostBinding.tietWaste.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setConvRate(Float.parseFloat(activityBoxSpecificationAndCostBinding.tietConversionCost.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setOverHead(Float.parseFloat(activityBoxSpecificationAndCostBinding.tietOverHeadCharges.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setTax(Float.parseFloat(resultOfTaxForED));
        CreateEstimateDataHolder.INSTANCE.setTax(Float.parseFloat(resultOfProfitForED));
    }

    private void callFormula() {
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
                formulaForTwoPlyKg(bfInTopPaper, gsmInTop, rateKgInTop, bfInFlutePaper, gsmInFlutePaper, rateKgInFlutePaper, ffInFluteOnePaper, decalM, cuttingL, waste, convCostKg, profit, tax, overhead, noOfBox);
                break;
            }
        }

    }

    private void callCreateEstimateAPI() {
        viewModel.createEstimate(
                selectedClient,
                CreateEstimateDataHolder.INSTANCE.getBoxName(),
                (int) CreateEstimateDataHolder.INSTANCE.getLengthMm(),
                (int) CreateEstimateDataHolder.INSTANCE.getWidthMm(),
                (int) CreateEstimateDataHolder.INSTANCE.getHeightMm(),
                CreateEstimateDataHolder.INSTANCE.getNoOfPly(),
                CreateEstimateDataHolder.INSTANCE.getNoOfBox(),
                CreateEstimateDataHolder.INSTANCE.getCuttingLength(),
                CreateEstimateDataHolder.INSTANCE.getDecalSize(),
                CreateEstimateDataHolder.INSTANCE.getCuttingMarginMm(),
                CreateEstimateDataHolder.INSTANCE.getDecalMarginMm(),

                CreateEstimateDataHolder.INSTANCE.getTopBf(),
                CreateEstimateDataHolder.INSTANCE.getTopGsm(),
                CreateEstimateDataHolder.INSTANCE.getTopRate(),

                CreateEstimateDataHolder.INSTANCE.getF1Bf(),
                CreateEstimateDataHolder.INSTANCE.getF1Gsm(),
                CreateEstimateDataHolder.INSTANCE.getF1RateKg(),
                CreateEstimateDataHolder.INSTANCE.getF1ff(),

                CreateEstimateDataHolder.INSTANCE.getM1bf(),
                CreateEstimateDataHolder.INSTANCE.getM1Gsm(),
                CreateEstimateDataHolder.INSTANCE.getM1RateKg(),

                CreateEstimateDataHolder.INSTANCE.getF2Bf(),
                CreateEstimateDataHolder.INSTANCE.getF2Gsm(),
                CreateEstimateDataHolder.INSTANCE.getF2RateKg(),
                CreateEstimateDataHolder.INSTANCE.getF2ff(),

                CreateEstimateDataHolder.INSTANCE.getM2bf(),
                CreateEstimateDataHolder.INSTANCE.getM2Gsm(),
                CreateEstimateDataHolder.INSTANCE.getM2RateKg(),

                CreateEstimateDataHolder.INSTANCE.getF3Bf(),
                CreateEstimateDataHolder.INSTANCE.getF3Gsm(),
                CreateEstimateDataHolder.INSTANCE.getF3RateKg(),
                CreateEstimateDataHolder.INSTANCE.getF3ff(),

                CreateEstimateDataHolder.INSTANCE.getBottomBF(),
                CreateEstimateDataHolder.INSTANCE.getBottomGsm(),
                CreateEstimateDataHolder.INSTANCE.getBottomRateKg(),

                CreateEstimateDataHolder.INSTANCE.getTotalGsm(),
                CreateEstimateDataHolder.INSTANCE.getTotalBs(),
                CreateEstimateDataHolder.INSTANCE.getTotalWeight(),
                (float) CreateEstimateDataHolder.INSTANCE.getNetPaperCost(),
                (float) CreateEstimateDataHolder.INSTANCE.getBoxMfg(),
                (float) CreateEstimateDataHolder.INSTANCE.getBoxPrice(),
                CreateEstimateDataHolder.INSTANCE.getBoxPriceTax(),

                CreateEstimateDataHolder.INSTANCE.getWasteInput(),
                CreateEstimateDataHolder.INSTANCE.getConvRate(),
                CreateEstimateDataHolder.INSTANCE.getOverHead(),
                CreateEstimateDataHolder.INSTANCE.getTax(),
                CreateEstimateDataHolder.INSTANCE.getProfit()
        );
    }

    private void formulaForTwoPlyKg(String bfInTopPaper, String gsmInTop, String rateKgInTop, String bfInFlutePaper, String gsmInFlutePaper, String rateKgInFlutePaper, String ffInFluteOnePaper, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
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

//        //Profit Price For Estimate Details Activity
//        double profitForED = resultOfBoxMFGTwoDigits * (profitFromTiet / 100);
//        resultOfProfitForED = String.format("%.2f", profitForED);
//
//        //Tax Price For Estimate Details Activity
//        double taxForED = boxPriceWithTax * (taxFromTiet / 100);
//        resultOfTaxForED = String.format("%.2f", taxForED);
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
        double valueFirstOFTotalWeight = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);
        double valueFirstOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFirstOFTotalWeight));

        //Value for flute one
        double valueSecondOFTotalWeight = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);
        double valueSecondOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueSecondOFTotalWeight));

        //Value for middle Paper
        double valueThirdOFTotalWeight = (decal * cutting * gsmOfMiddle * mm * mm / divide / divide / divide);
        double valueThirdOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueThirdOFTotalWeight));

        //Value for FluteTwoPaper
        double valueFourthOFTotalWeight = (decal * cutting * gsmOfFluteTwo * ffOfFluteTwoPaper * mm * mm / divide / divide / divide);
        double valueFourthOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFourthOFTotalWeight));

        //Value for MiddleTwoPaper
        double valueFifthOFTotalWeight = (decal * cutting * gsmOfMiddleTwo * mm * mm / divide / divide / divide);
        double valueFifthOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFifthOFTotalWeight));

        //Value for FluteThreePaper
        double valueSixthOFTotalWeight = (decal * cutting * gsmOfFluteThree * ffOfFluteThreePaper * mm * mm / divide / divide / divide);
        double valueSixthOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueSixthOFTotalWeight));

        //Value for BottomPaper
        double valueSeventhOFTotalWeight = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);
        double valueSeventhOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueSeventhOFTotalWeight));

        //Total
        double total = (valueFirstOFTotalWeightThreeDigits + valueSecondOFTotalWeightThreeDigits +
                valueThirdOFTotalWeightThreeDigits + valueFourthOFTotalWeightThreeDigits + valueFifthOFTotalWeightThreeDigits +
                valueSixthOFTotalWeightThreeDigits + valueSeventhOFTotalWeightThreeDigits);
        double totalThreeDigits = Double.valueOf(String.format("%.3f", total));
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((totalThreeDigits * wasteFromTiet) / 100);
        double wastePercentageThreeDigits = Double.valueOf(String.format("%.3f", wastePercentage));
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (totalThreeDigits + wastePercentageThreeDigits) / noOFBoxUps;
        double totalWeightThreeDigits = Double.valueOf(String.format("%.3f", totalWeight));
        int weightInInt = (int) (totalWeightThreeDigits * 1000);
        String gm = weightInInt + " gm";
        if(CreateEstimateDataHolder.INSTANCE.isEmptyBoxDim()){
            gm = weightInInt + " Kg";
        }
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(gm);
        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeightThreeDigits * convCostTiet);
        String resultOfConvCostString = "%.2f" + resultOfConvCost;
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);

        //Costs
        //NetPaperCost
        //TopPaperCost
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTotalWeightThreeDigits * rateOfTop);

        //FluteOnePaperCost
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOFTotalWeightThreeDigits * rateOfFlute);

        //MiddleOne PaperCost
        double rateOfMiddle = Double.parseDouble(rateKgInMiddleOnePaper);
        double valueThirdNPC = (valueThirdOFTotalWeightThreeDigits * rateOfMiddle);

        //FluteTwoPaperCost
        double rateOfFluteTwo = Double.parseDouble(rateKgInFluteTwoPaper);
        double valueFourthNPC = (valueFourthOFTotalWeightThreeDigits * rateOfFluteTwo);

        //MiddleTwoPaperCost
        double rateOfMiddleTwo = Double.parseDouble(rateKgInMiddleTwoPaper);
        double valueFifthNPC = (valueFifthOFTotalWeightThreeDigits * rateOfMiddleTwo);
        //Flute Three paper Cost
        double rateOfFluteThree = Double.parseDouble(rateKgInFluteThreePaper);
        double valueSixthNPC = (valueSixthOFTotalWeightThreeDigits * rateOfFluteThree);
        // Bottom Paper
        double rateOfBottom = Double.parseDouble(rateKgInBottomPaper);
        double valueSeventhNPC = (valueSeventhOFTotalWeightThreeDigits * rateOfBottom);
        //NPC
        double resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC + valueFourthNPC + valueFifthNPC + valueSixthNPC + valueSeventhNPC)) / noOFBoxUps;
        double resultOfNPCThreeDigits = Double.valueOf(String.format("%.3f", resultOfNPC));
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(resultOfNPCThreeDigits));
        //WasteCost
        double resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        double resultWasteCostTwoDigits = Double.valueOf(String.format("%.2f", resultWasteCost));
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCostTwoDigits));
        //GrossPaperCost
        double grossPaperCost = (resultOfNPC + resultWasteCost);
        double grossPaperCostTwoDigits = Double.valueOf(String.format("%.3f", grossPaperCost));
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCostTwoDigits));
        //BoxMFGCost
//        float overheadChargesTiet=Double.parseDouble(overhead);
        double overheadValue = overhead.isEmpty() ? 0.0 : Double.parseDouble(overhead);
        double resultOfBoxMFG = grossPaperCost + resultOfConvCost + overheadValue;
        double resultOfBoxMFGTwoDigits = Double.valueOf(String.format("%.3f", resultOfBoxMFG));
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFGTwoDigits));
//
//        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFGTwoDigits * profitFromTiet / 100) + resultOfBoxMFGTwoDigits);
        String boxPriceThreeDigits = String.format("%.3f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(boxPriceThreeDigits);
//
//        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
        //Profit Price For Estimate Details Activity
        double profitForED = resultOfBoxMFGTwoDigits * (profitFromTiet / 100);
        resultOfProfitForED = String.format("%.2f", profitForED);

        //Tax Price For Estimate Details Activity
        double taxForED = boxPriceWithTax * (taxFromTiet / 100);
        resultOfTaxForED = String.format("%.2f", taxForED);
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
        double valueFirstOFTotalWeight = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);
        double valueFirstOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFirstOFTotalWeight));

        //Value for flute one
        double valueSecondOFTotalWeight = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);
        double valueSecondOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueSecondOFTotalWeight));
        //Value for middle Paper
        double valueThirdOFTotalWeight = (decal * cutting * gsmOfMiddle * mm * mm / divide / divide / divide);
        double valueThirdOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueThirdOFTotalWeight));

        //Value for FluteTwoPaper
        double valueFourthOFTotalWeight = (decal * cutting * gsmOfFlute * ffOfFluteTwoPaper * mm * mm / divide / divide / divide);
        double valueFourthOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFourthOFTotalWeight));

        //Value for BottomPaper
        double valueFifthOFTotalWeight = (decal * cutting * gsmOfBottomPaper * mm * mm / divide / divide / divide);
        double valueFifthOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFifthOFTotalWeight));


        //Total
        double total = (valueFirstOFTotalWeightThreeDigits + valueSecondOFTotalWeightThreeDigits
                + valueThirdOFTotalWeightThreeDigits + valueFourthOFTotalWeightThreeDigits + valueFifthOFTotalWeightThreeDigits);
        double totalThreeDigits = Double.valueOf(String.format("%.3f", total));
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((totalThreeDigits * wasteFromTiet) / 100);
        double wastePercentageThreeDigits = Double.valueOf(String.format("%.3f", wastePercentage));
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (totalThreeDigits + wastePercentageThreeDigits) / noOFBoxUps;
        double totalWeightThreeDigits = Double.valueOf(String.format("%.3f", totalWeight));
        int weightInInt = (int) (totalWeightThreeDigits * 1000);
        String gm = weightInInt + " gm";
        if(CreateEstimateDataHolder.INSTANCE.isEmptyBoxDim()){
            gm = weightInInt + " Kg";
        }
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(gm);
        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeightThreeDigits * convCostTiet);
        double resultOfConvCostTwoDigits = Double.valueOf(String.format("%.2f", resultOfConvCost));
        String resultOfConvCostString = String.format("%.2f", resultOfConvCostTwoDigits);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);

        //Costs
        //NetPaperCost
        //TopPaperCost
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTotalWeightThreeDigits * rateOfTop);


        //FluteOnePaperCost
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOFTotalWeightThreeDigits * rateOfFlute);

        //MiddlePaperCost
        double rateOfMiddle = Double.parseDouble(rateKgInMiddleOnePaper);
        double valueThirdNPC = (valueThirdOFTotalWeightThreeDigits * rateOfMiddle);

        //FluteTwoPaperCost
        double rateOfFluteTwo = Double.parseDouble(rateKgInFluteTwoPaper);
        double valueFourthNPC = (valueFourthOFTotalWeightThreeDigits * rateOfFluteTwo);
        //Bottom Paper Cost
        double rateOfBottom = Double.parseDouble(rateKgInBottomPaper);
        double valueFifthNPC = (valueFifthOFTotalWeightThreeDigits * rateOfBottom);
        //NetPaperCost
        double resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC + valueFourthNPC + valueFifthNPC) / noOFBoxUps);
        double resultOfNPCThreeDigits = Double.valueOf(String.format("%.3f", resultOfNPC));
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(resultOfNPCThreeDigits));

        //WasteCost
        double resultWasteCost = ((resultOfNPCThreeDigits * 3) / 100);
        double resultWasteCostTwoDigits = Double.valueOf(String.format("%.2f", resultWasteCost));
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCostTwoDigits));
        //GrossPaperCost
        double grossPaperCost = (resultOfNPCThreeDigits + resultWasteCost);
        double grossPaperCostTwoDigits = Double.valueOf(String.format("%.2f", grossPaperCost));
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCostTwoDigits));
        //BoxMFGCost
//        float overheadChargesTiet=Double.parseDouble(overhead);
        double overheadValue = overhead.isEmpty() ? 0.0 : Double.parseDouble(overhead);
        double resultOfBoxMFG = grossPaperCost + resultOfConvCost + overheadValue;

        double resultOfBoxMFGTwoDigits = Double.valueOf(String.format("%.2f", resultOfBoxMFG));
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFGTwoDigits));
//
//        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFGTwoDigits * profitFromTiet / 100) + resultOfBoxMFGTwoDigits);
        String boxPriceThreeDigits = String.format("%.2f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(boxPriceThreeDigits);

//        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
        //Profit Price For Estimate Details Activity
        double profitForED = resultOfBoxMFGTwoDigits * (profitFromTiet / 100);
        resultOfProfitForED = String.format("%.2f", profitForED);

        //Tax Price For Estimate Details Activity
        double taxForED = boxPriceWithTax * (taxFromTiet / 100);
        resultOfTaxForED = String.format("%.2f", taxForED);
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
//
        //Total Weight
        //Value 1
        double decal = Double.parseDouble(decalM);
        double cutting = Double.parseDouble(cuttingL);
        double valueFirstOFTotalWeight = (decal * cutting * gsmOfTop * mm * mm / divide / divide / divide);
        double valueFirstOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFirstOFTotalWeight));

        //Value 2
        double valueSecondOfTotalWeight = (decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm / divide / divide / divide);
        double valueSecondOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueSecondOfTotalWeight));
        //Total
        double total = (valueFirstOFTotalWeightThreeDigits + valueSecondOFTotalWeightThreeDigits);
        double totalThreeDigits = Double.valueOf(String.format("%.3f", total));
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((totalThreeDigits * wasteFromTiet) / 100.0);
        double wastePercentageThreeDigits = Double.valueOf(String.format("%.3f", wastePercentage));
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (totalThreeDigits + wastePercentageThreeDigits) / noOFBoxUps;
        double totalWeightThreeDigits = Double.valueOf(String.format("%.3f", totalWeight));
        int weightInInt = (int) (totalWeightThreeDigits * 1000);
        String gm = weightInInt + " gm";
        if(CreateEstimateDataHolder.INSTANCE.isEmptyBoxDim()){
            gm = weightInInt + " Kg";
        }
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(gm);
        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeightThreeDigits * convCostTiet);
        String resultOfConvCostString = String.format("%.3f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);
        //Costs
        //NetPaperCost
        // 1stValue
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTotalWeightThreeDigits * rateOfTop);

        //2ndValue
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOFTotalWeightThreeDigits * rateOfFlute);
        //NPC
        double resultOfNPC = ((valueFirstNPC + valueSecondNPC) / noOFBoxUps);
        double resultOfNPCThreeDigits = Double.valueOf(String.format("%.3f", resultOfNPC));
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(resultOfNPCThreeDigits));

        //WasteCost
        double resultWasteCost = ((resultOfNPC * wasteFromTiet) / 100);
        double resultWasteCostTwoDigits = Double.valueOf(String.format("%.2f", resultWasteCost));
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCostTwoDigits));
        //GrossPaperCost
        double grossPaperCost = (resultOfNPCThreeDigits + resultWasteCostTwoDigits);
        double grossPaperCostTwoDigits = Double.valueOf(String.format("%.2f", grossPaperCost));
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCostTwoDigits));

        //BoxMFGCost
//        float overheadChargesTiet=Double.parseDouble(overhead);
        double overheadValue = overhead.isEmpty() ? 0.0 : Double.parseDouble(overhead);
        double resultOfBoxMFG = grossPaperCost + resultOfConvCost + overheadValue;

        double resultOfBoxMFGTwoDigits = Double.valueOf(String.format("%.3f", resultOfBoxMFG));
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFGTwoDigits));
//
//        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFGTwoDigits * profitFromTiet / 100) + resultOfBoxMFGTwoDigits);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(String.valueOf(boxPrice));
//
//        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
        //Profit Price For Estimate Details Activity
        double profitForED = resultOfBoxMFGTwoDigits * (profitFromTiet / 100);
        resultOfProfitForED = String.format("%.2f", profitForED);

        //Tax Price For Estimate Details Activity
        double taxForED = boxPriceWithTax * (taxFromTiet / 100);
        resultOfTaxForED = String.format("%.2f", taxForED);
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
        int resTotalGsmInt = (int) totalGsm;
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(resTotalGsmInt));

        //Total Weight
        //1St Value
        double decal = Double.parseDouble(decalM);
        double cutting = Double.parseDouble(cuttingL);
        double valueFirstOFTotalWeight = ((((decal * cutting * gsmOfTop * mm * mm) / divide )/ divide) / divide);
        double valueFirstOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueFirstOFTotalWeight));

        //2nd Value
        double valueSecondOfTotalWeight = ((((decal * cutting * gsmOfFlute * ffOfFluteOnePaper * mm * mm) / divide) / divide )/ divide);
        double valueSecondOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueSecondOfTotalWeight));

        //3rd Value
        double valueThirdOfThirdWeight = ((((decal * cutting * gsmOfBottomPaper * mm * mm )/ divide) / divide )/ divide);
        double valueThirdOFTotalWeightThreeDigits = Double.valueOf(String.format("%.3f", valueThirdOfThirdWeight));
        //Total
        double totalThreeDigits = (valueFirstOFTotalWeightThreeDigits + valueSecondOFTotalWeightThreeDigits + valueThirdOFTotalWeightThreeDigits);
//        double totalThreeDigits = Double.valueOf(String.format("%.3f", total));
        //Waste value
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentageThreeDigits = ((totalThreeDigits * wasteFromTiet) / 100);
//        double wastePercentageThreeDigits = Double.valueOf(String.format("%.3f", wastePercentage));
        //Total weight
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeightThreeDigits = (totalThreeDigits + wastePercentageThreeDigits) / noOFBoxUps;
//        double totalWeightThreeDigits = Double.valueOf(String.format("%.3f", totalWeight));
        int weightInInt = (int) (totalWeightThreeDigits * 1000);
        String gm = weightInInt + " gm";
        if(CreateEstimateDataHolder.INSTANCE.isEmptyBoxDim()){
            gm = weightInInt + " Kg";
        }
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(gm);

        //ConversionCostPerKG

        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeightThreeDigits * convCostTiet);
        String resultOfConvCostString = String.format("%.2f", resultOfConvCost);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(resultOfConvCostString);


        //Costs
        //NetPaperCost
        //1stValue
        double rateOfTop = Double.parseDouble(rateKgInTop);
        double valueFirstNPC = (valueFirstOFTotalWeightThreeDigits * rateOfTop);

        //2ndValue
        double rateOfFlute = Double.parseDouble(rateKgInFlutePaper);
        double valueSecondNPC = (valueSecondOFTotalWeightThreeDigits * rateOfFlute);

        //3rdValue
        double rateOfBottom = Double.parseDouble(rateKgInBottomPaper);
        double valueThirdNPC = (valueThirdOFTotalWeightThreeDigits * rateOfBottom);
        //NetPaperCost

        double resultOfNPC = ((valueFirstNPC + valueSecondNPC + valueThirdNPC) / noOFBoxUps);
        double resultOfNPCThreeDigits = Double.valueOf(String.format("%.3f", resultOfNPC));

        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(resultOfNPCThreeDigits));

        //WasteCost
        double resultWasteCost = ((resultOfNPCThreeDigits * wasteFromTiet) / 100);
        double resultWasteCostTwoDigits = Double.valueOf(String.format("%.2f", resultWasteCost));
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCostTwoDigits));

        //GrossPaperCost
        double grossPaperCost = (resultOfNPCThreeDigits + resultWasteCostTwoDigits);
        double grossPaperCostTwoDigits = Double.valueOf(String.format("%.3f", grossPaperCost));
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCostTwoDigits));

        //BoxMFGCost
//        float overheadChargesTiet= (float) Double.parseDouble(overhead);
        double overheadValue = overhead.isEmpty() ? 0.0 : Double.parseDouble(overhead);
        double resultOfBoxMFG = grossPaperCost + resultOfConvCost + overheadValue;
        double resultOfBoxMFGTwoDigits = Double.valueOf(String.format("%.3f", resultOfBoxMFG));
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFGTwoDigits));
//
//        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFGTwoDigits * profitFromTiet / 100) + resultOfBoxMFGTwoDigits);
        String boxPriceThreeDigits = String.format("%.3f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(boxPriceThreeDigits);
//
//        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);
        //Profit Price For Estimate Details Activity
        double profitForED = resultOfBoxMFGTwoDigits * (profitFromTiet / 100);
        resultOfProfitForED = String.format("%.2f", profitForED);

        //Tax Price For Estimate Details Activity
        double taxForED = boxPriceWithTax * (taxFromTiet / 100);
        resultOfTaxForED = String.format("%.2f", taxForED);
    }

    private void formulaForOnePly(String bfInTopPaper, String gsmInTop, String rateKgInTop, String decalM, String cuttingL, String waste, String convCostKg, String profit, String tax, String overhead, String noOfBox) {
        //GSM
        double gsmForOnePly = Double.parseDouble(gsmInTop);
        double resTotalGsm = (gsmForOnePly * 1);
        int resTotalGsmInt = (int) resTotalGsm;
        activityBoxSpecificationAndCostBinding.tvTotalGsm.setText(String.valueOf(resTotalGsmInt));

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
        double totalWeightInTopPaperThreeDigits = Double.valueOf(String.format("%.3f", totalWeightInTopPaper));


        // Waste
        double wasteFromTiet = Double.parseDouble(waste);
        double wastePercentage = ((totalWeightInTopPaperThreeDigits * wasteFromTiet) / 100.0);

        //totalWeigth
        int noOFBoxUps = Integer.parseInt(noOfBox);
        double totalWeight = (totalWeightInTopPaperThreeDigits + wastePercentage) / noOFBoxUps;
        double totalWeightThreeDigits = Double.valueOf(String.format("%.3f", totalWeight));
        int weightInInt = (int) (totalWeightThreeDigits * 1000);
        String gm = weightInInt + " gm";
        if(CreateEstimateDataHolder.INSTANCE.isEmptyBoxDim()){
            gm = weightInInt + " Kg";
        }
        activityBoxSpecificationAndCostBinding.tvTotalWeight.setText(gm);

        //NetPaperCost
        //CostValue
        double rateKgInTopForOnePly = Double.parseDouble(rateKgInTop);
        double costValue = (totalWeightInTopPaperThreeDigits * rateKgInTopForOnePly);

        //NetpaperCostForOnePly
        double netPaperCost = (costValue / noOFBoxUps);
        double resultOfNPCThreeDigits = Double.valueOf(String.format("%.3f", netPaperCost));
        activityBoxSpecificationAndCostBinding.tvNetPaperCost.setText(String.valueOf(resultOfNPCThreeDigits));

        //WasteCost
        double resultWasteCost = ((costValue * wasteFromTiet) / 100.0);
        double resultWasteCostTwoDigits = Double.valueOf(String.format("%.2f", resultWasteCost));
        activityBoxSpecificationAndCostBinding.tvWasteCost.setText(String.valueOf(resultWasteCostTwoDigits));
        //GrossPaperCost
        double grossPaperCost = (costValue + resultWasteCostTwoDigits);
        double grossPaperCostTwoDigits = Double.valueOf(String.format("%.2f", grossPaperCost));
        activityBoxSpecificationAndCostBinding.tvgrossPaperCost.setText(String.valueOf(grossPaperCostTwoDigits));

        //ConversionCostPerKG
        double convCostTiet = Double.parseDouble(convCostKg);
        double resultOfConvCost = (totalWeightThreeDigits * convCostTiet);
        activityBoxSpecificationAndCostBinding.tvConversionCost.setText(String.valueOf(resultOfConvCost));

        //BoxMFGCost
        double overheadValue = overhead.isEmpty() ? 0.0 : Double.parseDouble(overhead);
        double resultOfBoxMFG = grossPaperCost + resultOfConvCost + overheadValue;
        double resultOfBoxMFGTwoDigits = Double.valueOf(String.format("%.3f", resultOfBoxMFG));
        activityBoxSpecificationAndCostBinding.tvBoxMFGCost.setText(String.valueOf(resultOfBoxMFGTwoDigits));

        //Box Price
        double profitFromTiet = Double.parseDouble(profit);
        double boxPrice = ((resultOfBoxMFGTwoDigits * profitFromTiet / 100.0) + resultOfBoxMFGTwoDigits);
        String boxPriceThreeDigits = String.format("%.3f", boxPrice);
        activityBoxSpecificationAndCostBinding.tvBoxPrice.setText(boxPriceThreeDigits);


        //Box Price with Tax
        double taxFromTiet = Double.parseDouble(tax);
        double boxPriceWithTax = ((boxPrice * taxFromTiet / 100.0) + boxPrice);
        String resultOfboxPriceWithTax = String.format("%.2f", boxPriceWithTax);
        activityBoxSpecificationAndCostBinding.tvBoxPriceWithTax.setText(resultOfboxPriceWithTax);

        //Profit Price For Estimate Details Activity
        double profitForED = resultOfBoxMFGTwoDigits * (profitFromTiet / 100);
        resultOfProfitForED = String.format("%.2f", profitForED);

        //Tax Price For Estimate Details Activity
        double taxForED = boxPriceWithTax * (taxFromTiet / 100);
        resultOfTaxForED = String.format("%.2f", taxForED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CreateEstimateDataHolder.INSTANCE.resetValues();
    }
}