package com.dss.dsboxplus.estimates;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.ClientDetails;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityBoxEstimatesDetailsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.estimatesviewmodels.BoxEstimatesDetailsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;

public class BoxEstimatesDetailsActivity extends BaseActivity {
    ActivityBoxEstimatesDetailsBinding boxEstimatesDetailsBinding;
    BoxEstimatesDetailsActivityViewModel viewModel;
    private DataItem dataItem;
    private ClientDetails clientDetails;
    private ArrayList<Client> clientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boxEstimatesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_box_estimates_details);
        initView();
        fetchData();
        initObservables();
    }

    private void initObservables() {
        viewModel.getGetBusinessLiveData().observe(this, businessDetailsResponse -> {
            if (businessDetailsResponse != null) {

            }
        });

        viewModel.getGetClientByClientIdLiveData().observe(this, getClientByClientIdResponse -> {
            if (getClientByClientIdResponse != null) {
                handleClientDetailsResponse(getClientByClientIdResponse.getData());
            }
        });

        viewModel.getDeleteEstimateLiveData().observe(this, estimateDeleteResponse -> {
            Toast.makeText(this, estimateDeleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
            finishAffinity();
            startActivity(new Intent(this, HomeActivity.class));
        });
    }


    private void handleClientDetailsResponse(ClientDetails clientDetails) {
        if (clientDetails != null) {
            // Access the data from the ClientDetails object and update your UI accordingly
            String clientName = clientDetails.getClientname();
            boxEstimatesDetailsBinding.tvClientNameInBoxEstimateDetails.setText(clientName);
        }
    }

    private void fetchData() {
        viewModel.getBusinessDetailsByBusinessId();
        viewModel.getClientByClientId();
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(BoxEstimatesDetailsActivityViewModel.class);




        Intent intent = getIntent();
        if (intent.hasExtra("ESTIMATES_BUNDLE")) {
            // Retrieve the bundle from the intent
            Bundle bundle = intent.getBundleExtra("ESTIMATES_BUNDLE");
            if (bundle != null) {
                // Retrieve the DataItem object from the bundle
                dataItem = bundle.getParcelable("ESTIMATES");
                if (dataItem != null) {
                    //Dimension For ImageOne

                    boxEstimatesDetailsBinding.tvLength.setText(String.valueOf(dataItem.getLengthMmField() + "mm"));
                    boxEstimatesDetailsBinding.tvWidth.setText(String.valueOf(dataItem.getWidthMmField() + "mm"));
                    boxEstimatesDetailsBinding.tvHeight.setText(String.valueOf(dataItem.getHeightMmField() + "mm"));
                    boxEstimatesDetailsBinding.tvTotalWeight.setText(String.valueOf(dataItem.getTotalweight() + "gm"));
                    boxEstimatesDetailsBinding.tvBS.setText(String.valueOf(dataItem.getTotalbs()));
                    //Dimension For ImageTwo
                    boxEstimatesDetailsBinding.tvDecalSizeForBox.setText(String.valueOf(dataItem.getDecalsize() + "inch"));
                    boxEstimatesDetailsBinding.tvCuttingForBox.setText(String.valueOf(dataItem.getCuttinglength() + "inch"));

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

//                    boxEstimatesDetailsBinding.tvClientNameInBoxEstimateDetails.setText(String.valueOf(dataItem.getClientid()));
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

                    boxEstimatesDetailsBinding.tvPly.setText(dataItem.getPly() + "Ply");

                    double noOfPly = dataItem.getPly();
                    if (noOfPly == 1.0) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.GONE);
                    } else if (noOfPly == 2.0) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.GONE);
                    } else if (noOfPly == 3.0) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.VISIBLE);
                    } else if (noOfPly == 5.0) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.VISIBLE);
                    } else if (noOfPly == 7.0) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.VISIBLE);
                    }
                }
            }
        }


        //Quotation
        boxEstimatesDetailsBinding.btQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), QuotationInBoxEstimateDetailsActivity.class);
                intent.putExtra("length", boxEstimatesDetailsBinding.tvLength.getText().toString());
                intent.putExtra("width", boxEstimatesDetailsBinding.tvWidth.getText().toString());
                intent.putExtra("height", boxEstimatesDetailsBinding.tvHeight.getText().toString());
                intent.putExtra("boxName", boxEstimatesDetailsBinding.tvBoxNameInBoxEstimateDetails.getText().toString());
                intent.putExtra("bfInTop", boxEstimatesDetailsBinding.tvTopBf.getText().toString());
                intent.putExtra("bfInF1", boxEstimatesDetailsBinding.tvFluteOnebf.getText().toString());
                intent.putExtra("bfInM1", boxEstimatesDetailsBinding.tvMiddleOneBf.getText().toString());
                intent.putExtra("bfInF2", boxEstimatesDetailsBinding.tvFluteTwoBf.getText().toString());
                intent.putExtra("bfInM2", boxEstimatesDetailsBinding.tvMiddleTwoBf.getText().toString());
                intent.putExtra("bfInF3", boxEstimatesDetailsBinding.tvFluteThreeBf.getText().toString());
                intent.putExtra("bfInBottom", boxEstimatesDetailsBinding.tvBottomBf.getText().toString());

                intent.putExtra("gsmInTop", boxEstimatesDetailsBinding.tvTopGsm.getText().toString());
                intent.putExtra("gsmInF1", boxEstimatesDetailsBinding.tvGsmFluteOne.getText().toString());
                intent.putExtra("gsmInM1", boxEstimatesDetailsBinding.tvMiddleOneGsm.getText().toString());
                intent.putExtra("gsmInF2", boxEstimatesDetailsBinding.tvFluteTwoGsm.getText().toString());
                intent.putExtra("gsmInM2", boxEstimatesDetailsBinding.tvMiddleTwoGsm.getText().toString());
                intent.putExtra("gsmInF3", boxEstimatesDetailsBinding.tvFluteThreeGsm.getText().toString());
                intent.putExtra("gsmInBottom", boxEstimatesDetailsBinding.tvBottomGsm.getText().toString());
                intent.putExtra("rate", boxEstimatesDetailsBinding.tvPriceWithtax.getText().toString());
                intent.putExtra("ply", boxEstimatesDetailsBinding.tvPly.getText().toString());
                intent.putExtra("clientId",dataItem.getClientid());
                intent.putExtra("profit",dataItem.getProfit());
                intent.putExtra("tax",dataItem.getTax());
                startActivity(intent);
            }
        });
        //Production
        boxEstimatesDetailsBinding.btProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductionInBoxEstimatesDetailsActivity.class);
                intent.putExtra("length", boxEstimatesDetailsBinding.tvLength.getText().toString());
                intent.putExtra("width", boxEstimatesDetailsBinding.tvWidth.getText().toString());
                intent.putExtra("height", boxEstimatesDetailsBinding.tvHeight.getText().toString());
                intent.putExtra("boxName", boxEstimatesDetailsBinding.tvBoxNameInBoxEstimateDetails.getText().toString());
                intent.putExtra("bfInTop", boxEstimatesDetailsBinding.tvTopBf.getText().toString());
                intent.putExtra("bfInF1", boxEstimatesDetailsBinding.tvFluteOnebf.getText().toString());
                intent.putExtra("bfInM1", boxEstimatesDetailsBinding.tvMiddleOneBf.getText().toString());
                intent.putExtra("bfInF2", boxEstimatesDetailsBinding.tvFluteTwoBf.getText().toString());
                intent.putExtra("bfInM2", boxEstimatesDetailsBinding.tvMiddleTwoBf.getText().toString());
                intent.putExtra("bfInF3", boxEstimatesDetailsBinding.tvFluteThreeBf.getText().toString());
                intent.putExtra("bfInBottom", boxEstimatesDetailsBinding.tvBottomBf.getText().toString());

                intent.putExtra("gsmInTop", boxEstimatesDetailsBinding.tvTopGsm.getText().toString());
                intent.putExtra("gsmInF1", boxEstimatesDetailsBinding.tvGsmFluteOne.getText().toString());
                intent.putExtra("gsmInM1", boxEstimatesDetailsBinding.tvMiddleOneGsm.getText().toString());
                intent.putExtra("gsmInF2", boxEstimatesDetailsBinding.tvFluteTwoGsm.getText().toString());
                intent.putExtra("gsmInM2", boxEstimatesDetailsBinding.tvMiddleTwoGsm.getText().toString());
                intent.putExtra("gsmInF3", boxEstimatesDetailsBinding.tvFluteThreeGsm.getText().toString());
                intent.putExtra("gsmInBottom", boxEstimatesDetailsBinding.tvBottomGsm.getText().toString());
                intent.putExtra("rate", boxEstimatesDetailsBinding.tvPriceWithtax.getText().toString());
                intent.putExtra("ply", boxEstimatesDetailsBinding.tvPly.getText().toString());
//                intent.putExtra("cuttingLength", dataItem.getCuttinglengthmargin());
//                intent.putExtra("cuttingLength", dataItem.getDecalsizemargin());
//                intent.putExtra("weight", dataItem.getTotalweight());
//                intent.putExtra("ups", dataItem.getUps());
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
        boxEstimatesDetailsBinding.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoxEstimatesDetailsActivity.this, NewEstimateActivity.class);
                intent.putExtra("EDIT_ESTIMATE", dataItem);
                startActivity(intent);
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
                viewModel.deleteEstimate(dataItem.getEstimateid());
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