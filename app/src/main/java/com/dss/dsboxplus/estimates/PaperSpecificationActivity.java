package com.dss.dsboxplus.estimates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dss.dsboxplus.R;

public class PaperSpecificationActivity extends AppCompatActivity {
    private Button btNextInPaperSpecification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_specification);
        btNextInPaperSpecification=findViewById(R.id.btNextInPaperSpecfication);
        btNextInPaperSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), BoxSpecificationAndCostActivity.class);
                startActivity(intent);
            }
        });

    }
}