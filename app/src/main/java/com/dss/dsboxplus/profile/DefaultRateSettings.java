package com.dss.dsboxplus.profile;

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
import com.dss.dsboxplus.databinding.ActivityDefaultRateSettingsBinding;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.profileviewmodels.DefaultPaperSettingsActivityViewModel;
import com.dss.dsboxplus.viewmodels.profileviewmodels.DefaultRateSettingsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class DefaultRateSettings extends BaseActivity {

    ActivityDefaultRateSettingsBinding defaultRateSettingsBinding;

    DefaultRateSettingsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultRateSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_rate_settings);
        initView();
        initViewModel();
        addOververs();
    }

    private void addOververs() {
        viewModel.getUpdateBusinessdetailsLiveData().observe(this,updateBusinessDetailsResponse -> {
            Toast.makeText(this, updateBusinessDetailsResponse.getMessage(), Toast.LENGTH_SHORT);
            finish();
        });
    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(DefaultRateSettingsActivityViewModel.class);
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
                    defaultRateSettingsBinding.tietWaste.setText(String.valueOf(configValue));
                } else if (configId == 3) {
                    defaultRateSettingsBinding.tietConversionCost.setText(String.valueOf(configValue));
                } else if (configId == 19) {

                    defaultRateSettingsBinding.tietProfit.setText(String.valueOf(configValue));
                } else if (configId == 24) {
                    defaultRateSettingsBinding.tietTax.setText(String.valueOf(configValue));

                }

            }

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
                      defaultRateSettingsBinding.tietTax.getText().toString()

                );
            }
        });
    }
}
