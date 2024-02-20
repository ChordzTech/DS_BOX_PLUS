package com.dss.dsboxplus.estimates;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.alertdialog.SubscriptionViewModel;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.ClientDetails;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse;
import com.dss.dsboxplus.databinding.ActivityBoxEstimatesDetailsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.estimatesviewmodels.BoxEstimatesDetailsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;

public class BoxEstimatesDetailsActivity extends BaseActivity {
    ActivityBoxEstimatesDetailsBinding boxEstimatesDetailsBinding;
    BoxEstimatesDetailsActivityViewModel viewModel;
    String resultForProfitThreeDigits = "";
    String resultForTaxThreeDigits = "";
    String[] noOfPly = {"1Ply", "2Ply", "3Ply", "5Ply", "7Ply", "2Ply(KG)"};
    private DataItem dataItem;
    private ClientDetails clientDetails;
    private ArrayList<Client> clientsList;

    private SubscriptionViewModel subscriptionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boxEstimatesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_box_estimates_details);

        initView();
        fetchData();
        initObservables();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (AppPreferences.INSTANCE.getStringValueFromSharedPreferences(AppPreferences.APP_STATUS).equalsIgnoreCase(
                "Expired"
        ) ||
                ConfigDataProvider.INSTANCE.getUserDetails().getData().get(0).getUseraccess() == 1) {
            boxEstimatesDetailsBinding.btEdit.setVisibility(View.GONE);
            boxEstimatesDetailsBinding.btDelete.setVisibility(View.GONE);
        } else {
            boxEstimatesDetailsBinding.btEdit.setVisibility(View.VISIBLE);
            boxEstimatesDetailsBinding.btDelete.setVisibility(View.VISIBLE);
        }
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
            this.clientDetails = clientDetails;
            // Access the data from the ClientDetails object and update your UI accordingly
            String clientName = clientDetails.getClientname();
            boxEstimatesDetailsBinding.tvClientNameInBoxEstimateDetails.setText(clientName);
        }
    }

    private void fetchData() {
        if (isConnectedToInternet()) {
            viewModel.getBusinessDetailsByBusinessId(dataItem.getBusinessid());
            viewModel.getClientByClientId(dataItem.getClientid());
        } else {
            showNoInternetDialog();
        }
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(BoxEstimatesDetailsActivityViewModel.class);
        subscriptionViewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);

        if (ConfigDataProvider.INSTANCE.getUserDetails() != null && hasUserAccess(ConfigDataProvider.INSTANCE.getUserDetails(), 1)) {
            boxEstimatesDetailsBinding.llEditAndDelete.setVisibility(View.GONE);
        }


        // Observe the remainingDays LiveData
        subscriptionViewModel.getRemainingDays().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer remainingDays) {
                if (remainingDays != null && remainingDays == 0) {
                    // Hide your views when remainingDays is 0
                    boxEstimatesDetailsBinding.llEditAndDelete.setVisibility(View.GONE);
                } else {
                    // Show your views for other cases
                    boxEstimatesDetailsBinding.llEditAndDelete.setVisibility(View.VISIBLE);
                }
            }
        });


        Intent intent = getIntent();
        if (intent.hasExtra("ESTIMATES_BUNDLE")) {
            // Retrieve the bundle from the intent
            Bundle bundle = intent.getBundleExtra("ESTIMATES_BUNDLE");
            if (bundle != null) {
                // Retrieve the DataItem object from the bundle
                dataItem = bundle.getParcelable("ESTIMATES");
                if (dataItem != null) {

                    //ConvCost
                    double totalwt = dataItem.getTotalweight();
                    double convrate = dataItem.getConversionrate();
                    double result = ((totalwt / 1000) * convrate);
                    String resultThreeDigits = String.format("%.2f", result);
                    boxEstimatesDetailsBinding.tvConvCost.setText((resultThreeDigits));
                    //Profit & Tax
                    double boxMfg = (double) dataItem.getBoxcost();
                    double profit = dataItem.getProfit();
                    double tax = dataItem.getTax();

                    boxEstimatesDetailsBinding.tvProfitPercentage.setText(String.valueOf("Profit  " + profit + " %"));
                    boxEstimatesDetailsBinding.tvTaxPercentage.setText(String.valueOf("Tax  " + tax + " %"));

                    double boxPrice = (double) dataItem.getBoxprice();
                    double resultForProfit = boxMfg * (profit / 100);
                    resultForProfitThreeDigits = String.format("%.2f", resultForProfit);
                    double resultForTax = boxPrice * (tax / 100);
                    resultForTaxThreeDigits = String.format("%.2f", resultForTax);

                    int lengthMM = (int) Math.round(dataItem.getLengthMmField());
                    int widthMM = (int) Math.round(dataItem.getWidthMmField());
                    int heightMM = (int) Math.round(dataItem.getHeightMmField());
//                    int lengthCM = (int) Math.round(Double.parseDouble(dataItem.getLengthCmField().toString()));
//                    int widthCM = (int) Math.round(Double.parseDouble(dataItem.getWidthCmField().toString()));
//                    int heightCM = (int) Math.round(Double.parseDouble(dataItem.getHeightCmField().toString()));
                    int lengthIN = (int) Math.round(Double.parseDouble(dataItem.getLengthInchField().toString()));
                    int widthIN = (int) Math.round(Double.parseDouble(dataItem.getWidthInchField().toString()));
                    int heightIN = (int) Math.round(Double.parseDouble(dataItem.getHeightInchField().toString()));
                    int decalSize = (int) Double.parseDouble(dataItem.getDecalsize().toString());
                    int cuttingLength = (int) Double.parseDouble(dataItem.getCuttinglength().toString());
//                    if (lengthCM != 0 && widthCM != 0 && heightCM != 0) {
//                        boxEstimatesDetailsBinding.tvLength.setText(String.valueOf(lengthCM + "cm"));
//                        boxEstimatesDetailsBinding.tvWidth.setText(String.valueOf(widthCM + "cm"));
//                        boxEstimatesDetailsBinding.tvHeight.setText(String.valueOf(heightCM + "cm"));

//                        int decal = (int) (decalSize * 2.54);
//                        int cutting = (int) (cuttingLength * 2.54);
//                        boxEstimatesDetailsBinding.tvDecalSizeForBox.setText(String.valueOf(decal + "cm"));
//                        boxEstimatesDetailsBinding.tvCuttingSizeForBox.setText(String.valueOf(cutting + "cm"));
                     if (cuttingLength != 0 && decalSize != 0) {
                         boxEstimatesDetailsBinding.tvLength.setText(String.valueOf(lengthMM + "mm"));
                         boxEstimatesDetailsBinding.tvWidth.setText(String.valueOf(widthMM + "mm"));
                         boxEstimatesDetailsBinding.tvHeight.setText(String.valueOf(heightMM + "mm"));
//                        boxEstimatesDetailsBinding.tvLength.setText(String.valueOf(lengthIN + "inch"));
//                        boxEstimatesDetailsBinding.tvWidth.setText(String.valueOf(widthIN + "inch"));
//                        boxEstimatesDetailsBinding.tvHeight.setText(String.valueOf(heightIN + "inch"));


                        boxEstimatesDetailsBinding.tvDecalSizeForBox.setText(String.valueOf(decalSize + "inch"));
                        boxEstimatesDetailsBinding.tvCuttingSizeForBox.setText(String.valueOf(cuttingLength + "inch"));
                    } else {
                        boxEstimatesDetailsBinding.tvLength.setText(String.valueOf(lengthMM + "mm"));
                        boxEstimatesDetailsBinding.tvWidth.setText(String.valueOf(widthMM + "mm"));
                        boxEstimatesDetailsBinding.tvHeight.setText(String.valueOf(heightMM + "mm"));

                        int decal = (int) (decalSize * 25.4);
                        int cutting = (int) (cuttingLength * 25.4);
                        boxEstimatesDetailsBinding.tvDecalSizeForBox.setText(String.valueOf(decal + "mm"));
                        boxEstimatesDetailsBinding.tvCuttingSizeForBox.setText(String.valueOf(cutting + "mm"));
                    }

                    boxEstimatesDetailsBinding.tvTotalWeight.setText(String.valueOf((int) Math.round(dataItem.getTotalweight()) + "gm"));
                    boxEstimatesDetailsBinding.tvBS.setText(String.valueOf(dataItem.getTotalbs()));
                    //Dimension For ImageTwo


                    double decalSizeInInches = (double) dataItem.getDecalsize();
                    int decalSizeInMillimeters = (int) Math.round(decalSizeInInches * 25.4);

                    boxEstimatesDetailsBinding.tvDecalSizeinInch.setText(String.valueOf(decalSizeInMillimeters + "mm"));


                    double cuttingSizeInInch = (double) dataItem.getCuttinglength();
                    int cuttingSizeinMm = (int) Math.round(cuttingSizeInInch * 25.4);
                    boxEstimatesDetailsBinding.tvCuttingLengthinch.setText(String.valueOf(cuttingSizeinMm + "mm"));
                    //Dimensions for ply
                    boxEstimatesDetailsBinding.tvTopBf.setText(String.valueOf((int) Math.round(dataItem.getTopbf())));
                    boxEstimatesDetailsBinding.tvTopGsm.setText(String.valueOf((int) Math.round(dataItem.getTopgsm())));
                    boxEstimatesDetailsBinding.tvTopRate.setText(String.valueOf(dataItem.getToprate()));
                    boxEstimatesDetailsBinding.tvFluteOnebf.setText(String.valueOf((int) Math.round(dataItem.getF1bf())));
                    boxEstimatesDetailsBinding.tvGsmFluteOne.setText(String.valueOf((int) Math.round(dataItem.getF1gsm())));
                    boxEstimatesDetailsBinding.tvRateFluteOne.setText(String.valueOf(dataItem.getF1rate()));
                    boxEstimatesDetailsBinding.tvFFFluteOne.setText(String.valueOf(dataItem.getF1ff()));
                    boxEstimatesDetailsBinding.tvMiddleOneBf.setText(String.valueOf((int) Math.round(dataItem.getM1bf())));
                    boxEstimatesDetailsBinding.tvMiddleOneGsm.setText(String.valueOf((int) Math.round(dataItem.getM1gsm())));
                    boxEstimatesDetailsBinding.tvMiddleOneRate.setText(String.valueOf(dataItem.getM1rate()));
                    boxEstimatesDetailsBinding.tvFluteTwoBf.setText(String.valueOf((int) Math.round(dataItem.getF2bf())));
                    boxEstimatesDetailsBinding.tvFluteTwoGsm.setText(String.valueOf((int) Math.round(dataItem.getF2gsm())));
                    boxEstimatesDetailsBinding.tvFluteTwoRate.setText(String.valueOf(dataItem.getF2rate()));
                    boxEstimatesDetailsBinding.tvFluteTwoFf.setText(String.valueOf(dataItem.getF2ff()));
                    boxEstimatesDetailsBinding.tvMiddleTwoBf.setText(String.valueOf((int) Math.round(dataItem.getM2bf())));
                    boxEstimatesDetailsBinding.tvMiddleTwoGsm.setText(String.valueOf((int) Math.round(dataItem.getM2gsm())));
                    boxEstimatesDetailsBinding.tvMiddleTwoRate.setText(String.valueOf(dataItem.getM2rate()));
                    boxEstimatesDetailsBinding.tvFluteThreeBf.setText(String.valueOf((int) Math.round(dataItem.getF3bf())));
                    boxEstimatesDetailsBinding.tvFluteThreeGsm.setText(String.valueOf((int) Math.round(dataItem.getF3gsm())));
                    boxEstimatesDetailsBinding.tvFluteThreeRate.setText(String.valueOf(dataItem.getF3rate()));
                    boxEstimatesDetailsBinding.tvFluteThreeFf.setText(String.valueOf(dataItem.getF3ff()));
                    boxEstimatesDetailsBinding.tvBottomBf.setText(String.valueOf((int) Math.round(dataItem.getBottombf())));
                    boxEstimatesDetailsBinding.tvBottomGsm.setText(String.valueOf((int) Math.round(dataItem.getBottomgsm())));
                    boxEstimatesDetailsBinding.tvBottomRate.setText(String.valueOf(dataItem.getBottomrate()));


                    boxEstimatesDetailsBinding.tvClientNameInBoxEstimateDetails.setText(String.valueOf(dataItem.getClientid()));


                    boxEstimatesDetailsBinding.tvBoxNameInBoxEstimateDetails.setText(dataItem.getBoxname());
                    boxEstimatesDetailsBinding.tvnoOfBox.setText(String.valueOf(dataItem.getUps()));
                    boxEstimatesDetailsBinding.tvtotalGsm.setText(Math.round(dataItem.getTotalgsm()) + "");
                    boxEstimatesDetailsBinding.tvWaste.setText(String.format("%.2f", dataItem.getWaste()));
                    boxEstimatesDetailsBinding.tvConvKg.setText(String.format("%.2f", dataItem.getConversionrate()));
//                    boxEstimatesDetailsBinding.tvConvCost.setText(String.valueOf(dataItem.getConversionrate()));
                    boxEstimatesDetailsBinding.tvPaperCost.setText(String.format("%.2f", dataItem.getTotalpapercost()));
                    boxEstimatesDetailsBinding.tvOverhead.setText(String.format("%.2f", dataItem.getOverheadcharges()));
                    boxEstimatesDetailsBinding.tvBoxMfg.setText(String.valueOf(dataItem.getBoxcost()));
                    boxEstimatesDetailsBinding.tvProfit.setText(String.valueOf(resultForProfitThreeDigits));
                    boxEstimatesDetailsBinding.tvBoxpriceInEstimateDetails.setText(String.format("%.2f", dataItem.getBoxprice()));
                    boxEstimatesDetailsBinding.tvTax.setText(String.valueOf(resultForTaxThreeDigits));
                    boxEstimatesDetailsBinding.tvPriceWithtax.setText(String.format("%.2f", dataItem.getBoxpricewithtax()));
                    boxEstimatesDetailsBinding.tvPly.setText(dataItem.getPly() + " Ply");

                    int noOfPly = dataItem.getPly();
                    if (noOfPly == 1) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.GONE);
                    } else if (noOfPly == 2) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.GONE);
                    } else if (noOfPly == 3) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.VISIBLE);
                    } else if (noOfPly == 5) {
                        boxEstimatesDetailsBinding.llTop.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleOne.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llFluteTwo.setVisibility(View.VISIBLE);
                        boxEstimatesDetailsBinding.llMiddleTwo.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llFluteThree.setVisibility(View.GONE);
                        boxEstimatesDetailsBinding.llBottom.setVisibility(View.VISIBLE);
                    } else if (noOfPly == 7) {
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
                intent.putExtra("rate", boxEstimatesDetailsBinding.tvBoxpriceInEstimateDetails.getText().toString());
                intent.putExtra("ply", boxEstimatesDetailsBinding.tvPly.getText().toString());
                intent.putExtra("clientId", dataItem.getClientid());
                intent.putExtra("profit", dataItem.getProfit());
                intent.putExtra("resultForProfitThreeDigits", resultForProfitThreeDigits);
                intent.putExtra("resultForTaxThreeDigits", resultForTaxThreeDigits);
                intent.putExtra("tax", dataItem.getTax());
                startActivity(intent);
            }
        });
        //Production
        boxEstimatesDetailsBinding.btProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductionInBoxEstimatesDetailsActivity.class);
                double decalSize = (double) dataItem.getDecalsize();
                double cuttingLength = (double) dataItem.getCuttinglength();

                double decalSizeInInches = (double) dataItem.getDecalsize();
                int decalSizeInMillimeters = (int) Math.round(decalSizeInInches * 25.4);

                double cuttingSizeInInch = (double) dataItem.getCuttinglength();
                int cuttingSizeinMm = (int) Math.round(cuttingSizeInInch * 25.4);

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
                intent.putExtra("ffinf1", boxEstimatesDetailsBinding.tvFFFluteOne.getText().toString());
                intent.putExtra("ffinf2", boxEstimatesDetailsBinding.tvFluteTwoFf.getText().toString());
                intent.putExtra("ffinf3", boxEstimatesDetailsBinding.tvFluteThreeFf.getText().toString());


                intent.putExtra("gsmInTop", boxEstimatesDetailsBinding.tvTopGsm.getText().toString());
                intent.putExtra("gsmInF1", boxEstimatesDetailsBinding.tvGsmFluteOne.getText().toString());
                intent.putExtra("gsmInM1", boxEstimatesDetailsBinding.tvMiddleOneGsm.getText().toString());
                intent.putExtra("gsmInF2", boxEstimatesDetailsBinding.tvFluteTwoGsm.getText().toString());
                intent.putExtra("gsmInM2", boxEstimatesDetailsBinding.tvMiddleTwoGsm.getText().toString());
                intent.putExtra("gsmInF3", boxEstimatesDetailsBinding.tvFluteThreeGsm.getText().toString());
                intent.putExtra("gsmInBottom", boxEstimatesDetailsBinding.tvBottomGsm.getText().toString());
                intent.putExtra("rate", boxEstimatesDetailsBinding.tvBoxpriceInEstimateDetails.getText().toString());
                intent.putExtra("ply", boxEstimatesDetailsBinding.tvPly.getText().toString());
                intent.putExtra("decalLength", decalSize);
                intent.putExtra("cuttingLength", cuttingLength);
                intent.putExtra("decalSizemm", decalSizeInMillimeters);
                intent.putExtra("cuttingLengthmm", cuttingSizeinMm);
                intent.putExtra("ups", boxEstimatesDetailsBinding.tvnoOfBox.getText().toString());
                intent.putExtra("clientId", dataItem.getClientid());

                startActivity(intent);

            }
        });
        //Delete
        boxEstimatesDetailsBinding.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectedToInternet()) {
                    dialog(view);
                } else {
                    Toast.makeText(BoxEstimatesDetailsActivity.this, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        boxEstimatesDetailsBinding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        boxEstimatesDetailsBinding.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet()) {
                    Intent intent = new Intent(BoxEstimatesDetailsActivity.this, NewEstimateActivity.class);
                    intent.putExtra("EDIT_ESTIMATE", dataItem);
                    if (clientDetails != null)
                        intent.putExtra("CLIENT_DETAILS", clientDetails);
                    startActivity(intent);
                } else {
                    Toast.makeText(BoxEstimatesDetailsActivity.this, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean hasUserAccess(UserDetailsResponse userDetailsResponse, int i) {
        if (userDetailsResponse.getData() != null && !userDetailsResponse.getData().isEmpty()) {
            UserData userData = userDetailsResponse.getData().get(0); // Assuming there is only one UserData in the list
            return userData.getUseraccess() != null && userData.getUseraccess() == i;
        }
        return false;
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