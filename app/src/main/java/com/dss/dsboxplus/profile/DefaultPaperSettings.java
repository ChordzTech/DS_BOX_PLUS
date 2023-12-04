package com.dss.dsboxplus.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityDefaultPaperSettingsBinding;
import com.dss.dsboxplus.estimates.NewEstimateActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class DefaultPaperSettings extends AppCompatActivity {
    ActivityDefaultPaperSettingsBinding defaultPaperSettingsBinding;
    String cutting="30.0";
    String decal="30.0";
    String flute="30.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultPaperSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_paper_settings);
        initView();

    }

    private void initView() {
        defaultPaperSettingsBinding.btCloseInDefaultPaperSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        defaultPaperSettingsBinding.tietCuttingMargin.setText(cutting);
        defaultPaperSettingsBinding.tietDecalMargin.setText(decal);
        defaultPaperSettingsBinding.tietFluteFactor.setText(flute);
    }
}