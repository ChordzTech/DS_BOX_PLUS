package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityDefaultRateSettingsBinding;


public class DefaultRateSettings extends AppCompatActivity {

    ActivityDefaultRateSettingsBinding defaultRateSettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultRateSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_rate_settings);
        initView();
    }

    private void initView() {
        defaultRateSettingsBinding.btCloseInDefaultRateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
