package com.dss.dsboxplus.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;


public class UserDetailsInProfile extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Spinner userAccessSpinnerInUserDetails;
    String[] access = {"Full Access","No Access","Read Only"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_in_profile);
        userAccessSpinnerInUserDetails=findViewById(R.id.userAccessSpinnerInUserDetails);
        userAccessSpinnerInUserDetails.setOnItemSelectedListener(this);
        ArrayAdapter accessAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, access);
        accessAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        userAccessSpinnerInUserDetails.setAdapter(accessAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}