package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;


public class BusinessDetails extends AppCompatActivity {
    MaterialButton btCloseInBusinessDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_details);
        btCloseInBusinessDetails = findViewById(R.id.btCloseInBusinessDetails);
        btCloseInBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}