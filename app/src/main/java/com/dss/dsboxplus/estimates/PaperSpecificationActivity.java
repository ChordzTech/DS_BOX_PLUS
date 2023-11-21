package com.dss.dsboxplus.estimates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dss.dsboxplus.R;

public class PaperSpecificationActivity extends AppCompatActivity {
    private Button btNextInPaperSpecification,btBackInPaperSpecification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_specification);
        btNextInPaperSpecification=findViewById(R.id.btNextInPaperSpecfication);
        btBackInPaperSpecification=findViewById(R.id.btBackInPaperSpecification);
        btNextInPaperSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), BoxSpecificationAndCostActivity.class);
                startActivity(intent);
            }
        });
        btBackInPaperSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}