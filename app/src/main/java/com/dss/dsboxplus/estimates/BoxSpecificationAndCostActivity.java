package com.dss.dsboxplus.estimates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityBoxSpecificationAndCostBinding;

public class BoxSpecificationAndCostActivity extends AppCompatActivity {
ActivityBoxSpecificationAndCostBinding activityBoxSpecificationAndCostBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoxSpecificationAndCostBinding= DataBindingUtil.setContentView(this,R.layout.activity_box_specification_and_cost);
        activityBoxSpecificationAndCostBinding.btBackInBoxSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}