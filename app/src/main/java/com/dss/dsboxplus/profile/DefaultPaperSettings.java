package com.dss.dsboxplus.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dss.dsboxplus.R;
import com.google.android.material.button.MaterialButton;


public class DefaultPaperSettings extends AppCompatActivity {
MaterialButton btCloseInDefaultPaperSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_paper_settings);
        btCloseInDefaultPaperSettings=findViewById(R.id.btCloseInDefaultPaperSettings);
        btCloseInDefaultPaperSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}