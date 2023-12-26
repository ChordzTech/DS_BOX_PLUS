package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.databinding.ActivityDefaultPaperSettingsBinding;

import java.util.ArrayList;


public class DefaultPaperSettings extends AppCompatActivity {
    ActivityDefaultPaperSettingsBinding defaultPaperSettingsBinding;
    private ArrayList<AppConfigDataItems> appConfigList = new ArrayList<>();
    private int AppConfigDataItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultPaperSettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_default_paper_settings);
        initView();

    }

    private void initView() {

        if (!appConfigList.isEmpty()) {
            defaultPaperSettingsBinding.tietCuttingMargin.setText(String.valueOf(appConfigList.get(AppConfigDataItems).getConfigvalue()));
        }
        defaultPaperSettingsBinding.btCloseInDefaultPaperSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}