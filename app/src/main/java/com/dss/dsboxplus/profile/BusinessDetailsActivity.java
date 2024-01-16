package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.databinding.ActivityBusinessDetailsBinding;
import com.google.android.material.button.MaterialButton;


public class BusinessDetailsActivity extends BaseActivity {
    MaterialButton btCloseInBusinessDetails;
    ActivityBusinessDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_details);
        initView();

    }

    private void initView() {

        btCloseInBusinessDetails = findViewById(R.id.btCloseInBusinessDetails);
        btCloseInBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
