package com.dss.dsboxplus.estimates;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.CreateEstimateDataHolder;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.ClientDetails;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityPaperSpecificationBinding;

import java.util.ArrayList;

public class PaperSpecificationActivity extends BaseActivity {
    ActivityPaperSpecificationBinding paperSpecificationBinding;
    String noOfPly = "";
    private ClientDetails selectedClient;

    private DataItem dataItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paperSpecificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_paper_specification);
        dataItem = getIntent().getParcelableExtra("EDIT_ESTIMATE");
        selectedClient = getIntent().getParcelableExtra("selectedClient");
        if (dataItem != null) {
            updateUI();
        }
        initView();
    }

    private void updateUI() {


        paperSpecificationBinding.bfInTopPaper.setText(String.valueOf((int) dataItem.getTopbf().doubleValue()));
        paperSpecificationBinding.gsmInTopPaper.setText(String.valueOf((int) dataItem.getTopgsm().doubleValue()));
        paperSpecificationBinding.rateKgInTopPaper.setText(dataItem.getToprate().toString());

        paperSpecificationBinding.bfInFlutePaper.setText(String.valueOf((int) dataItem.getF1bf().doubleValue()));
        paperSpecificationBinding.gsmInFlutePaper.setText(String.valueOf((int) dataItem.getF1gsm().doubleValue()));
        paperSpecificationBinding.rateKgInFlutePaper.setText(dataItem.getF1rate().toString());
        paperSpecificationBinding.ffInFlutePaper.setText(dataItem.getF1ff().toString());

        paperSpecificationBinding.bfInMiddleOnePaper.setText(String.valueOf((int) dataItem.getM1bf().doubleValue()));
        paperSpecificationBinding.gsmInMiddleOnePaper.setText(String.valueOf((int) dataItem.getM1gsm().doubleValue()));
        paperSpecificationBinding.rateKgInMiddleOnePaper.setText(dataItem.getM1rate().toString());

        paperSpecificationBinding.bfInFluteTwoPaper.setText(String.valueOf((int) dataItem.getF2bf().doubleValue()));
        paperSpecificationBinding.gsmInFluteTwoPaper.setText(String.valueOf((int) dataItem.getF2gsm().doubleValue()));
        paperSpecificationBinding.rateKgInFluteTwoPaper.setText(dataItem.getF2rate().toString());
        paperSpecificationBinding.ffInFluteTwoPaper.setText(dataItem.getF2ff().toString());

        paperSpecificationBinding.bfInMiddleTwoPaper.setText(String.valueOf((int) dataItem.getM2bf().doubleValue()));
        paperSpecificationBinding.gsmInMiddleTwoPaper.setText(String.valueOf((int) dataItem.getM2gsm().doubleValue()));
        paperSpecificationBinding.rateKgInMiddleTwoPaper.setText(dataItem.getM2rate().toString());

        paperSpecificationBinding.bfInFluteThreePaper.setText(String.valueOf((int) dataItem.getF3bf().doubleValue()));
        paperSpecificationBinding.gsmInFluteThreePaper.setText(String.valueOf((int) dataItem.getF3gsm().doubleValue()));
        paperSpecificationBinding.rateKgInFluteThreePaper.setText(dataItem.getF3rate().toString());
        paperSpecificationBinding.ffInFluteThreePaper.setText(dataItem.getF3ff().toString());

        paperSpecificationBinding.bfInBottomPaper.setText(String.valueOf((int) dataItem.getBottombf().doubleValue()));
        paperSpecificationBinding.gsmInBottomPaper.setText(String.valueOf((int) dataItem.getBottomgsm().doubleValue()));
        paperSpecificationBinding.rateKgInBottomPaper.setText(dataItem.getBottomrate().toString());
    }

    private void initView() {

        String clientName = selectedClient.getClientname();
        paperSpecificationBinding.tvClientNameInEstimate.setText(clientName);

        paperSpecificationBinding.tvBoxNameInPaperSpecification.setText(CreateEstimateDataHolder.INSTANCE.getBoxName());
        paperSpecificationBinding.tvBoxDimensionInPaperSpecification.setText(CreateEstimateDataHolder.INSTANCE.getLengthMm() + "x" + CreateEstimateDataHolder.INSTANCE.getWidthMm() + "x" + CreateEstimateDataHolder.INSTANCE.getHeightMm());


        noOfPly = this.getIntent().getStringExtra("noOfPly");
        String noOfBox = getIntent().getStringExtra("noOfBox");
        String cuttingL = getIntent().getStringExtra("cuttingLength");
        String decalM = getIntent().getStringExtra("decalSize");
        switch (noOfPly) {
            case "1Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.GONE);
                break;
            case "2Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.GONE);
                break;
            case "3Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.VISIBLE);
                break;
            case "5Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.tvMiddleOnePaper.setText("Middle Paper");
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.tvFlutePaper.setText("Flute One Paper");
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.VISIBLE);
                break;
            case "7Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.tvFlutePaper.setText("Flute One Paper");
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.VISIBLE);
                break;
            case "2Ply(KG)":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.GONE);
                break;
        }
//        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
//        if (appConfigResponse.getData() != null) {
//            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
//            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
//                int configId = appConfigDataItem.getConfigid();
//                String configValue = appConfigDataItem.getConfigvalue();
//                if (configId == 4) {
//                    paperSpecificationBinding.ffInFlutePaper.setText(configValue);
//                    paperSpecificationBinding.ffInFluteTwoPaper.setText(configValue);
//                    paperSpecificationBinding.ffInFluteThreePaper.setText(configValue);
//                }
//            }
//        }
        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
            paperSpecificationBinding.ffInFlutePaper.setText(String.valueOf(businessDetails.getFlutefactor()));
            paperSpecificationBinding.ffInFluteTwoPaper.setText(String.valueOf(businessDetails.getFlutefactor()));
            paperSpecificationBinding.ffInFluteThreePaper.setText(String.valueOf(businessDetails.getFlutefactor()));

        }

        paperSpecificationBinding.bfInTopPaper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                paperSpecificationBinding.bfInFlutePaper.setText(enteredText);
                paperSpecificationBinding.bfInMiddleOnePaper.setText(enteredText);
                paperSpecificationBinding.bfInFluteTwoPaper.setText(enteredText);
                paperSpecificationBinding.bfInMiddleTwoPaper.setText(enteredText);
                paperSpecificationBinding.bfInFluteThreePaper.setText(enteredText);
                paperSpecificationBinding.bfInBottomPaper.setText(enteredText);
            }
        });
        paperSpecificationBinding.gsmInTopPaper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                paperSpecificationBinding.gsmInFlutePaper.setText(enteredText);
                paperSpecificationBinding.gsmInMiddleOnePaper.setText(enteredText);
                paperSpecificationBinding.gsmInFluteTwoPaper.setText(enteredText);
                paperSpecificationBinding.gsmInMiddleTwoPaper.setText(enteredText);
                paperSpecificationBinding.gsmInFluteThreePaper.setText(enteredText);
                paperSpecificationBinding.gsmInBottomPaper.setText(enteredText);
            }
        });
        paperSpecificationBinding.rateKgInTopPaper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                paperSpecificationBinding.rateKgInFlutePaper.setText(enteredText);
                paperSpecificationBinding.rateKgInMiddleOnePaper.setText(enteredText);
                paperSpecificationBinding.rateKgInFluteTwoPaper.setText(enteredText);
                paperSpecificationBinding.rateKgInMiddleTwoPaper.setText(enteredText);
                paperSpecificationBinding.rateKgInFluteThreePaper.setText(enteredText);
                paperSpecificationBinding.rateKgInBottomPaper.setText(enteredText);
            }
        });

        paperSpecificationBinding.btNextInPaperSpecfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String noOfPly = getIntent().getStringExtra("noOfPly");
                String bfInTopPaper = paperSpecificationBinding.bfInTopPaper.getText().toString();
                String bfInFlutePaper = paperSpecificationBinding.bfInFlutePaper.getText().toString();
                String bfInMiddleOnePaper = paperSpecificationBinding.bfInMiddleOnePaper.getText().toString();
                String bfInFluteTwoPaper = paperSpecificationBinding.bfInFluteTwoPaper.getText().toString();
                String bfInMiddleTwoPaper = paperSpecificationBinding.bfInMiddleTwoPaper.getText().toString();
                String bfInFluteThreePaper = paperSpecificationBinding.bfInFluteThreePaper.getText().toString();
                String bfInBottomPaper = paperSpecificationBinding.bfInBottomPaper.getText().toString();
                String gsmInTop = paperSpecificationBinding.gsmInTopPaper.getText().toString();
                String gsmInFlutePaper = paperSpecificationBinding.gsmInFlutePaper.getText().toString();
                String gsmInMiddleOnePaper = paperSpecificationBinding.gsmInMiddleOnePaper.getText().toString();
                String gsmInFluteTwoPaper = paperSpecificationBinding.gsmInFluteTwoPaper.getText().toString();
                String gsmInMiddleTwoPaper = paperSpecificationBinding.gsmInMiddleTwoPaper.getText().toString();
                String gsmInFluteThreePaper = paperSpecificationBinding.gsmInFluteThreePaper.getText().toString();
                String gsmInBottomPaper = paperSpecificationBinding.gsmInBottomPaper.getText().toString();
                String rateKgInTop = paperSpecificationBinding.rateKgInTopPaper.getText().toString();
                String rateKgInFlutePaper = paperSpecificationBinding.rateKgInFlutePaper.getText().toString();
                String rateKgInMiddleOnePaper = paperSpecificationBinding.rateKgInMiddleOnePaper.getText().toString();
                String rateKgInFluteTwoPaper = paperSpecificationBinding.rateKgInFluteTwoPaper.getText().toString();
                String rateKgInMiddleTwoPaper = paperSpecificationBinding.rateKgInMiddleTwoPaper.getText().toString();
                String rateKgInFluteThreePaper = paperSpecificationBinding.rateKgInFluteThreePaper.getText().toString();
                String rateKgInBottomPaper = paperSpecificationBinding.rateKgInBottomPaper.getText().toString();
                String ffInFluteOnePaper = paperSpecificationBinding.ffInFlutePaper.getText().toString();
                String ffInFluteTwoPaper = paperSpecificationBinding.ffInFluteTwoPaper.getText().toString();
                String ffInFluteThreePaper = paperSpecificationBinding.ffInFluteThreePaper.getText().toString();


                String clientName = selectedClient.getClientname();
                String boxName = paperSpecificationBinding.tvBoxNameInPaperSpecification.getText().toString();
                String boxDimen = paperSpecificationBinding.tvBoxDimensionInPaperSpecification.getText().toString();

                boolean check = validateInfo(bfInTopPaper, bfInFlutePaper, bfInMiddleOnePaper, bfInFluteTwoPaper, bfInMiddleTwoPaper
                        , bfInFluteThreePaper, bfInBottomPaper, gsmInTop, gsmInFlutePaper, gsmInMiddleOnePaper, gsmInFluteTwoPaper, gsmInMiddleTwoPaper,
                        gsmInFluteThreePaper, gsmInBottomPaper, rateKgInTop, rateKgInFlutePaper, rateKgInMiddleOnePaper, rateKgInFluteTwoPaper
                        , rateKgInMiddleTwoPaper, rateKgInBottomPaper, ffInFluteOnePaper, ffInFluteTwoPaper, ffInFluteThreePaper, rateKgInFluteThreePaper);
                if (check) {
                    storeValuesToEstimateDataHolder();
                    Intent intent = new Intent(getApplicationContext(), BoxSpecificationAndCostActivity.class);
                    intent.putExtra("bfInTopPaper", bfInTopPaper);
                    intent.putExtra("bfInFlutePaper", bfInFlutePaper);
                    intent.putExtra("bfInMiddleOnePaper", bfInMiddleOnePaper);
                    intent.putExtra("bfInFluteTwoPaper", bfInFluteTwoPaper);
                    intent.putExtra("bfInMiddleTwoPaper", bfInMiddleTwoPaper);
                    intent.putExtra("bfInFluteThreePaper", bfInFluteThreePaper);
                    intent.putExtra("bfInBottomPaper", bfInBottomPaper);
                    intent.putExtra("gsmInTop", gsmInTop);
                    intent.putExtra("gsmInFlutePaper", gsmInFlutePaper);
                    intent.putExtra("gsmInMiddleOnePaper", gsmInMiddleOnePaper);
                    intent.putExtra("gsmInFluteTwoPaper", gsmInFluteTwoPaper);
                    intent.putExtra("gsmInMiddleTwoPaper", gsmInMiddleTwoPaper);
                    intent.putExtra("gsmInFluteThreePaper", gsmInFluteThreePaper);
                    intent.putExtra("gsmInBottomPaper", gsmInBottomPaper);
                    intent.putExtra("rateKgInTop", rateKgInTop);
                    intent.putExtra("rateKgInFlutePaper", rateKgInFlutePaper);
                    intent.putExtra("rateKgInMiddleOnePaper", rateKgInMiddleOnePaper);
                    intent.putExtra("rateKgInFluteTwoPaper", rateKgInFluteTwoPaper);
                    intent.putExtra("rateKgInMiddleTwoPaper", rateKgInMiddleTwoPaper);
                    intent.putExtra("rateKgInFluteThreePaper", rateKgInFluteThreePaper);
                    intent.putExtra("rateKgInBottomPaper", rateKgInBottomPaper);
                    intent.putExtra("ffInFluteOnePaper", ffInFluteOnePaper);
                    intent.putExtra("ffInFluteTwoPaper", ffInFluteTwoPaper);
                    intent.putExtra("ffInFluteThreePaper", ffInFluteThreePaper);
                    intent.putExtra("cuttingLengthResult", cuttingL);
                    intent.putExtra("decalSizeResult", decalM);
                    intent.putExtra("noOfPly", noOfPly);
                    intent.putExtra("noOfBox", noOfBox);
                    intent.putExtra("EDIT_ESTIMATE", dataItem);

                    intent.putExtra("selectedClient", selectedClient);
                    intent.putExtra("boxName", boxName);
                    intent.putExtra("boxDimen", boxDimen);
                    startActivity(intent);
                }

            }
        });
        paperSpecificationBinding.btBackInPaperSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void storeValuesToEstimateDataHolder() {
        CreateEstimateDataHolder.INSTANCE.setTopBf(Integer.parseInt(paperSpecificationBinding.bfInTopPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setTopGsm(Integer.parseInt(paperSpecificationBinding.gsmInTopPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setTopRate(Float.parseFloat(paperSpecificationBinding.rateKgInTopPaper.getText().toString()));

        CreateEstimateDataHolder.INSTANCE.setF1Bf(Integer.parseInt(paperSpecificationBinding.bfInFlutePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF1Gsm(Integer.parseInt(paperSpecificationBinding.gsmInFlutePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF1RateKg(Float.parseFloat(paperSpecificationBinding.rateKgInFlutePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF1ff(Float.parseFloat(paperSpecificationBinding.ffInFlutePaper.getText().toString()));

        CreateEstimateDataHolder.INSTANCE.setM1bf(Integer.parseInt(paperSpecificationBinding.bfInMiddleOnePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setM1Gsm(Integer.parseInt(paperSpecificationBinding.gsmInMiddleOnePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setM1RateKg(Float.parseFloat(paperSpecificationBinding.rateKgInMiddleOnePaper.getText().toString()));

        CreateEstimateDataHolder.INSTANCE.setF2Bf(Integer.parseInt(paperSpecificationBinding.bfInFluteTwoPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF2Gsm(Integer.parseInt(paperSpecificationBinding.gsmInFluteTwoPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF2RateKg(Float.parseFloat(paperSpecificationBinding.rateKgInFluteTwoPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF2ff(Float.parseFloat(paperSpecificationBinding.ffInFluteTwoPaper.getText().toString()));

        CreateEstimateDataHolder.INSTANCE.setM2bf(Integer.parseInt(paperSpecificationBinding.bfInMiddleTwoPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setM2Gsm(Integer.parseInt(paperSpecificationBinding.gsmInMiddleTwoPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setM2RateKg(Float.parseFloat(paperSpecificationBinding.rateKgInMiddleTwoPaper.getText().toString()));

        CreateEstimateDataHolder.INSTANCE.setF3Bf(Integer.parseInt(paperSpecificationBinding.bfInFluteThreePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF3Gsm(Integer.parseInt(paperSpecificationBinding.gsmInFluteThreePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF3RateKg(Float.parseFloat(paperSpecificationBinding.rateKgInFluteThreePaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setF3ff(Float.parseFloat(paperSpecificationBinding.ffInFluteThreePaper.getText().toString()));

        CreateEstimateDataHolder.INSTANCE.setBottomBF(Integer.parseInt(paperSpecificationBinding.bfInBottomPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setBottomGsm(Integer.parseInt(paperSpecificationBinding.gsmInBottomPaper.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setBottomRateKg(Float.parseFloat(paperSpecificationBinding.rateKgInBottomPaper.getText().toString()));

    }

    private boolean validateInfo(String bfInTopPaper, String bfInFlutePaper, String bfInMiddleOnePaper, String bfInFluteTwoPaper, String bfInMiddleTwoPaper, String bfInFluteThreePaper,
                                 String bfInBottomPaper, String gsmInTop, String gsmInFlutePaper, String gsmInMiddleOnePaper, String gsmInFluteTwoPaper, String gsmInMiddleTwoPaper, String gsmInFluteThreePaper,
                                 String gsmInBottomPaper, String rateKgInTop, String rateKgInFlutePaper, String rateKgInMiddleOnePaper, String rateKgInFluteTwoPaper, String rateKgInMiddleTwoPaper,
                                 String rateKgInBottomPaper, String ffInFluteOnePaper, String ffInFluteTwoPaper, String ffInFluteThreePaper, String rateKgInFluteThreePaper) {
        if (bfInTopPaper.isEmpty()) {
            paperSpecificationBinding.bfInTopPaper.requestFocus();
            paperSpecificationBinding.bfInTopPaper.setError("Enter Value");
            return false;
        } else if (bfInFlutePaper.isEmpty()) {
            paperSpecificationBinding.bfInFlutePaper.requestFocus();
            paperSpecificationBinding.bfInFlutePaper.setError("Enter Value");
            return false;
        } else if (bfInMiddleOnePaper.isEmpty()) {
            paperSpecificationBinding.bfInMiddleOnePaper.requestFocus();
            paperSpecificationBinding.bfInMiddleOnePaper.setError("Enter Value");
            return false;
        } else if (bfInFluteTwoPaper.isEmpty()) {
            paperSpecificationBinding.bfInFluteTwoPaper.requestFocus();
            paperSpecificationBinding.bfInFluteTwoPaper.setError("Enter Value");
            return false;
        } else if (bfInMiddleTwoPaper.isEmpty()) {
            paperSpecificationBinding.bfInMiddleTwoPaper.requestFocus();
            paperSpecificationBinding.bfInMiddleTwoPaper.setError("Enter Value");
            return false;
        } else if (bfInFluteThreePaper.isEmpty()) {
            paperSpecificationBinding.bfInFluteThreePaper.requestFocus();
            paperSpecificationBinding.bfInFluteThreePaper.setError("Enter Value");
            return false;
        } else if (bfInBottomPaper.isEmpty()) {
            paperSpecificationBinding.bfInBottomPaper.requestFocus();
            paperSpecificationBinding.bfInBottomPaper.setError("Enter Value");
            return false;
        } else if (gsmInTop.isEmpty()) {
            paperSpecificationBinding.gsmInTopPaper.requestFocus();
            paperSpecificationBinding.gsmInTopPaper.setError("Enter Value");
            return false;
        } else if (gsmInFlutePaper.isEmpty()) {
            paperSpecificationBinding.gsmInFlutePaper.requestFocus();
            paperSpecificationBinding.gsmInFlutePaper.setError("Enter Value");
            return false;
        } else if (gsmInMiddleOnePaper.isEmpty()) {
            paperSpecificationBinding.gsmInMiddleOnePaper.requestFocus();
            paperSpecificationBinding.gsmInMiddleOnePaper.setError("Enter Value");
            return false;
        } else if (gsmInFluteTwoPaper.isEmpty()) {
            paperSpecificationBinding.gsmInFluteTwoPaper.requestFocus();
            paperSpecificationBinding.gsmInFluteTwoPaper.setError("Enter Value");
            return false;
        } else if (gsmInMiddleTwoPaper.isEmpty()) {
            paperSpecificationBinding.gsmInMiddleTwoPaper.requestFocus();
            paperSpecificationBinding.gsmInMiddleTwoPaper.setError("Enter Value");
            return false;
        } else if (gsmInFluteThreePaper.isEmpty()) {
            paperSpecificationBinding.gsmInFluteThreePaper.requestFocus();
            paperSpecificationBinding.gsmInFluteThreePaper.setError("Enter Value");
            return false;
        } else if (gsmInBottomPaper.isEmpty()) {
            paperSpecificationBinding.gsmInBottomPaper.requestFocus();
            paperSpecificationBinding.gsmInBottomPaper.setError("Enter Value");
            return false;
        } else if (rateKgInTop.isEmpty()) {
            paperSpecificationBinding.rateKgInTopPaper.requestFocus();
            paperSpecificationBinding.rateKgInTopPaper.setError("Enter Value");
            return false;
        } else if (rateKgInFlutePaper.isEmpty()) {
            paperSpecificationBinding.rateKgInFlutePaper.requestFocus();
            paperSpecificationBinding.rateKgInFlutePaper.setError("Enter Value");
            return false;
        } else if (rateKgInMiddleOnePaper.isEmpty()) {
            paperSpecificationBinding.rateKgInMiddleOnePaper.requestFocus();
            paperSpecificationBinding.rateKgInMiddleOnePaper.setError("Enter Value");
            return false;
        } else if (rateKgInFluteTwoPaper.isEmpty()) {
            paperSpecificationBinding.rateKgInFluteTwoPaper.requestFocus();
            paperSpecificationBinding.rateKgInFluteTwoPaper.setError("Enter Value");
            return false;
        } else if (rateKgInMiddleTwoPaper.isEmpty()) {
            paperSpecificationBinding.rateKgInMiddleTwoPaper.requestFocus();
            paperSpecificationBinding.rateKgInMiddleTwoPaper.setError("Enter Value");
            return false;
        } else if (rateKgInBottomPaper.isEmpty()) {
            paperSpecificationBinding.rateKgInBottomPaper.requestFocus();
            paperSpecificationBinding.rateKgInBottomPaper.setError("Enter Value");
            return false;
        } else if (ffInFluteOnePaper.isEmpty()) {
            paperSpecificationBinding.ffInFlutePaper.requestFocus();
            paperSpecificationBinding.ffInFlutePaper.setError("Enter Value");
            return false;
        } else if (ffInFluteTwoPaper.isEmpty()) {
            paperSpecificationBinding.ffInFluteTwoPaper.requestFocus();
            paperSpecificationBinding.ffInFluteTwoPaper.setError("Enter Value");
            return false;
        } else if (ffInFluteThreePaper.isEmpty()) {
            paperSpecificationBinding.ffInFluteThreePaper.requestFocus();
            paperSpecificationBinding.ffInFluteThreePaper.setError("Enter Value");
            return false;
        } else if (rateKgInFluteThreePaper.isEmpty()) {
            paperSpecificationBinding.rateKgInFluteThreePaper.requestFocus();
            paperSpecificationBinding.rateKgInFluteThreePaper.setError("Enter Value");
            return false;
        } else {
            return true;
        }


    }
}




