package com.dss.dsboxplus.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.databinding.ActivityDefaultPaperSettingsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.dss.dsboxplus.viewmodels.profileviewmodels.DefaultPaperSettingsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class DefaultPaperSettings extends BaseActivity {
    ActivityDefaultPaperSettingsBinding defaultPaperSettingsBinding;
    DefaultPaperSettingsActivityViewModel viewModel;
    HomeViewModel homeViewModel  ;
    BusinessDetails businessDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultPaperSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_paper_settings);
        initView();
    }



    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(DefaultPaperSettingsActivityViewModel.class);
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
//
//                String configValue = appConfigDataItem.getConfigvalue();
//
//                if (configId == 10) {
//                    defaultPaperSettingsBinding.tietCuttingMargin.setText(configValue);
//                } else if (configId == 11) {
//                    defaultPaperSettingsBinding.tietDecalMargin.setText(configValue);
//                } else if (configId == 4) {
//                    defaultPaperSettingsBinding.tietFluteFactor.setText(configValue);
//                }
//
//            }
//
//        }

        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
            defaultPaperSettingsBinding.tietCuttingMargin.setText(String.valueOf(businessDetails.getMarginlength()));
            defaultPaperSettingsBinding.tietDecalMargin.setText(String.valueOf(businessDetails.getMarginwidth()));
            defaultPaperSettingsBinding.tietFluteFactor.setText(String.valueOf(businessDetails.getFlutefactor()));
        }


        defaultPaperSettingsBinding.btCloseInDefaultPaperSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        defaultPaperSettingsBinding.btSaveInDefaultPaperSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cuttingMargin = Integer.parseInt(defaultPaperSettingsBinding.tietCuttingMargin.getText().toString());
                int decalMargin = Integer.parseInt(defaultPaperSettingsBinding.tietDecalMargin.getText().toString());
                float fluteFactor = Float.parseFloat(defaultPaperSettingsBinding.tietFluteFactor.getText().toString());

                viewModel.updatebusinessDetails(
                        cuttingMargin, decalMargin,fluteFactor,businessDetails
                );
            }
        });
        viewModel.getUpdateBusinessdetailsLiveData().observe(this, updateBusinessDetailsResponse -> {
            Toast.makeText(this, "Updates Successfully", Toast.LENGTH_SHORT);
            finishAffinity();
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}