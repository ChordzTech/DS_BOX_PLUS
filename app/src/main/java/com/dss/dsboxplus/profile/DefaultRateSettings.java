package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.databinding.ActivityDefaultRateSettingsBinding;

import java.util.ArrayList;


public class DefaultRateSettings extends AppCompatActivity {

    ActivityDefaultRateSettingsBinding defaultRateSettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultRateSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_rate_settings);
        initView();
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

                if (configId==29){
                    defaultRateSettingsBinding.tietWaste.setText(configValue);
                } else if (configId==3) {
                    defaultRateSettingsBinding.tietConversionCost.setText(configValue);
                } else if (configId==19) {
                    defaultRateSettingsBinding.tietProfit.setText(configValue);
                }
                else if (configId==24){
                    defaultRateSettingsBinding.tietTax.setText(configValue);

                }

            }

        }
        defaultRateSettingsBinding.btCloseInDefaultRateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
