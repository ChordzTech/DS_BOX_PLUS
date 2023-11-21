package com.dss.dsboxplus.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dss.dsboxplus.R;
import com.google.android.material.button.MaterialButton;


public class QuotationTerms extends AppCompatActivity {
    MaterialButton btCloseInQuotationTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_terms);
        btCloseInQuotationTerms=findViewById(R.id.btCloseInQuotationTerms);
        btCloseInQuotationTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}