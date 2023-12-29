package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.databinding.ActivityDefaultPaperSettingsBinding;

import java.util.ArrayList;


public class DefaultPaperSettings extends AppCompatActivity {
    ActivityDefaultPaperSettingsBinding defaultPaperSettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultPaperSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_paper_settings);
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

    }
}