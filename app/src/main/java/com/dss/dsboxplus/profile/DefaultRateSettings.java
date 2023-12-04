package com.dss.dsboxplus.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityDefaultRateSettingsBinding;
import com.google.android.material.button.MaterialButton;


public class DefaultRateSettings extends AppCompatActivity {

    ActivityDefaultRateSettingsBinding defaultRateSettingsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultRateSettingsBinding=DataBindingUtil.setContentView(this,R.layout.activity_default_rate_settings);
        initView();
    }

    private void initView() {
        defaultRateSettingsBinding.btCloseInDefaultRateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        defaultRateSettingsBinding.tietWaste.setText("3.0");
        defaultRateSettingsBinding.tietConversionCost.setText("15.0");
        defaultRateSettingsBinding.tietProfit.setText("15.0");
        defaultRateSettingsBinding.tietTax.setText("18.0");
    }
}