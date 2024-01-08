package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.databinding.ActivityDefaultPaperSettingsBinding;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.clientsviewmodels.ClientViewModel;
import com.dss.dsboxplus.viewmodels.profileviewmodels.DefaultPaperSettingsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class DefaultPaperSettings extends BaseActivity {
    ActivityDefaultPaperSettingsBinding defaultPaperSettingsBinding;
    DefaultPaperSettingsActivityViewModel viewModel;
    private BusinessDetails business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultPaperSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_paper_settings);
        initView();
        initViewModel();
        addOververs();
    }

    private void addOververs() {
//        viewModel.getUpdateBusinessdetailsLiveData().observe(this,updateBusinessDetailsResponse -> {
//            Toast.makeText(this, updateBusinessDetailsResponse.getMessage(), Toast.LENGTH_SHORT);
//            finish();
//        });
    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(DefaultPaperSettingsActivityViewModel.class);
    }

    private void initView() {
        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
        if (appConfigResponse.getData() != null) {
            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                // Access individual properties of AppConfigDataItems
                int configId = appConfigDataItem.getConfigid();
                String configName = appConfigDataItem.getConfigname();
                String configValue = appConfigDataItem.getConfigvalue();

             if (configId==10){
                 defaultPaperSettingsBinding.tietCuttingMargin.setText(configValue);
             } else if (configId==11) {
                 defaultPaperSettingsBinding.tietDecalMargin.setText(configValue);
             } else if (configId==4) {
                 defaultPaperSettingsBinding.tietFluteFactor.setText(configValue);
             }

            }

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
                viewModel.updatebusinessDetails(
                        defaultPaperSettingsBinding.tietCuttingMargin.getText().toString(),
                        defaultPaperSettingsBinding.tietDecalMargin.getText().toString(),
                        defaultPaperSettingsBinding.tietFluteFactor.getText().toString()
                );
            }
        });
    }
}