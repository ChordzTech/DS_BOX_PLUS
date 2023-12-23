package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;
import com.google.android.material.button.MaterialButton;

public class SuperUserSetting extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    String[] access = {"Full Access","No Access","Read Only"};
    MaterialButton btCloseInUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_user_setting);
        spinner=findViewById(R.id.UserAccessSpinner);
        spinner.setOnItemSelectedListener(this);
        btCloseInUserDetails=findViewById(R.id.btCloseInUserDetails);
        btCloseInUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayAdapter accessAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, access);
        accessAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(accessAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}