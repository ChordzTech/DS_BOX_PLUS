package com.dss.dsboxplus.estimates;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;
import com.google.android.material.button.MaterialButton;

public class ProductionInBoxEstimatesDetailsActivity extends AppCompatActivity {
    MaterialButton btCloseInProduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_in_box_estimates_details);
        btCloseInProduction = findViewById(R.id.btCloseInProduction);
        btCloseInProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}