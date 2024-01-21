package com.dss.dsboxplus.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.clients.ClientDetailsActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.databinding.ActivityDefaultRateSettingsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeandotpviewmodels.HomeActivityViewModel;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.dss.dsboxplus.viewmodels.profileviewmodels.DefaultRateSettingsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;


public class DefaultRateSettings extends BaseActivity {

    ActivityDefaultRateSettingsBinding defaultRateSettingsBinding;

    DefaultRateSettingsActivityViewModel viewModel;
    HomeViewModel homeViewModel  ;
    BusinessDetails businessDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultRateSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_rate_settings);
        initView();

    }


    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(DefaultRateSettingsActivityViewModel.class);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);

        homeViewModel.getBusinessDetails();


        homeViewModel.getBusinessDetailsLiveData().observe(this, businessDetailsResponse -> {
            if (businessDetailsResponse.getData() != null) {
                 businessDetails = businessDetailsResponse.getData();
            }
        });
    }

    private void initView() {
        initViewModel();
//        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
//        if (appConfigResponse.getData() != null) {
//            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
//            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
//                // Access individual properties of AppConfigDataItems
//                int configId = appConfigDataItem.getConfigid();
//                String configValue = appConfigDataItem.getConfigvalue();
//
//                if (configId == 29) {
//                    defaultRateSettingsBinding.tietWaste.setText(String.valueOf(configValue));
//                } else if (configId == 3) {
//                    defaultRateSettingsBinding.tietConversionCost.setText(String.valueOf(configValue));
//                } else if (configId == 19) {
//
//                    defaultRateSettingsBinding.tietProfit.setText(String.valueOf(configValue));
//                } else if (configId == 24) {
//                    defaultRateSettingsBinding.tietTax.setText(String.valueOf(configValue));
//
//                }
//
//            }
//        }
        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();

            // Now you can use the businessDetails object to set values in your UI
            defaultRateSettingsBinding.tietWaste.setText(String.valueOf(businessDetails.getWaste()));
            defaultRateSettingsBinding.tietConversionCost.setText(String.valueOf(businessDetails.getConversionrate()));
            defaultRateSettingsBinding.tietProfit.setText(String.valueOf(businessDetails.getProfit()));
            defaultRateSettingsBinding.tietTax.setText(String.valueOf(businessDetails.getTax()));
        }
        defaultRateSettingsBinding.btCloseInDefaultRateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        defaultRateSettingsBinding.btSaveInDefaultRateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.updateBusinessDetails(
                        defaultRateSettingsBinding.tietWaste.getText().toString(),
                        defaultRateSettingsBinding.tietConversionCost.getText().toString(),
                        defaultRateSettingsBinding.tietProfit.getText().toString(),
                        defaultRateSettingsBinding.tietTax.getText().toString(),
                        businessDetails
                );
            }
        });
        viewModel.getUpdateBusinessdetailsLiveData().observe(this, updateBusinessDetailsResponse -> {
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT);
            finishAffinity();
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}
