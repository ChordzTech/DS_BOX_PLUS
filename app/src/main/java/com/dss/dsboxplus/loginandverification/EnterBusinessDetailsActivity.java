package com.dss.dsboxplus.loginandverification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dss.dsboxplus.R;
import com.google.android.material.button.MaterialButton;

public class EnterBusinessDetailsActivity extends AppCompatActivity {
MaterialButton btSubmitInEnterBusinessDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_business_details);
        btSubmitInEnterBusinessDetails=findViewById(R.id.btSubmitInEnterBusinessDetails);
        btSubmitInEnterBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}