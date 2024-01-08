package com.dss.dsboxplus.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.databinding.ActivityUserDetailsInProfileBinding;

import java.util.ArrayList;


public class UserDetailsInProfile extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Spinner userAccessSpinnerInUserDetails;
    String[] access = {"Full Access","No Access","Read Only"};

    ActivityUserDetailsInProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this,R.layout.activity_user_details_in_profile);
        initView();
    }

    private void initView() {
        userAccessSpinnerInUserDetails=findViewById(R.id.userAccessSpinnerInUserDetails);
        userAccessSpinnerInUserDetails.setOnItemSelectedListener(this);
        ArrayAdapter accessAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, access);
        accessAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        userAccessSpinnerInUserDetails.setAdapter(accessAdapter);


        UserData userData = ((ArrayList<UserData>) ConfigDataProvider.INSTANCE.getUserDetails().getData()).get(0);
        binding.tvUserName.setText(userData.getUsername());
        binding.tvContactNo.setText(userData.getMobileno());
//        binding.tvUserName.setText(userData.getUsername()); //User Role
        binding.tvdeviceInfo.setText(userData.getDeviceinfo());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}