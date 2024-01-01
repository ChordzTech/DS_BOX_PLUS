package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.databinding.ActivityEnterBusinessDetailsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.google.android.material.button.MaterialButton;

public class EnterBusinessDetailsActivity extends BaseActivity {
    ActivityEnterBusinessDetailsBinding businessDetailsBinding;
    MaterialButton btSubmitInEnterBusinessDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_business_details);

        btSubmitInEnterBusinessDetails = findViewById(R.id.btSubmitInEnterBusinessDetails);
        btSubmitInEnterBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String businessName = businessDetailsBinding.tietBusinessName.getText().toString();
                String businessContact = businessDetailsBinding.tietBusinessContact.getText().toString();
                String businessAddress = businessDetailsBinding.tietBusinessAddress.getText().toString();
                String businessPinCode = businessDetailsBinding.tietBusinessPinCode.getText().toString();
                boolean check = validateInfo(businessName, businessContact, businessAddress, businessPinCode);
                if (check == true) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInfo(String businessName, String businessContact, String businessAddress, String businessPinCode) {
        if (businessName.length() == 0) {
            businessDetailsBinding.tietBusinessName.requestFocus();
            businessDetailsBinding.tietBusinessName.setError("Please Enter Business Name");
            return false;
        } else if (businessContact.length() <= 9) {
            businessDetailsBinding.tietBusinessContact.requestFocus();
            businessDetailsBinding.tietBusinessContact.setError("Please Enter Business Contact");
            return false;
        } else if (businessAddress.length() == 0) {
            businessDetailsBinding.tietBusinessAddress.requestFocus();
            businessDetailsBinding.tietBusinessAddress.setError("Please Enter Business Address");
            return false;
        } else if (businessPinCode.length() <= 5) {
            businessDetailsBinding.tietBusinessPinCode.requestFocus();
            businessDetailsBinding.tietBusinessPinCode.setError("Enter Six Digits Code");
            return false;
        } else {
            return true;
        }
    }
}