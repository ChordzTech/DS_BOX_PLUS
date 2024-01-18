package com.dss.dsboxplus.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.SubUser;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.databinding.ActivityUserDetailsInProfileBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.profile.subUser.SuperUserSetting;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.profileviewmodels.UserDetailsInProfileViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class UserDetailsInProfile extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Spinner userAccessSpinnerInUserDetails;
    String[] access = {"Full Access", "No Access", "Read Only"};

    ActivityUserDetailsInProfileBinding binding;
    UserDetailsInProfileViewModel viewModel;
    private SubUser userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details_in_profile);
        initView();
    }

    private void initView() {
        initViewModel();
        userAccessSpinnerInUserDetails = findViewById(R.id.userAccessSpinnerInUserDetails);
        userAccessSpinnerInUserDetails.setOnItemSelectedListener(this);
        ArrayAdapter accessAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, access);
        accessAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        userAccessSpinnerInUserDetails.setAdapter(accessAdapter);


        UserData userData = ((ArrayList<UserData>) ConfigDataProvider.INSTANCE.getUserDetails().getData()).get(0);
        binding.tietUserName.setText(userData.getUsername());
        binding.tvContactNo.setText(userData.getMobileno());
//        binding.tvUserName.setText(userData.getUsername()); //User Role
        binding.tvdeviceInfo.setText(userData.getDeviceinfo());

        binding.btUpdateInUSerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.updateUserDetails(userData.getUserid(),
                        binding.tietUserName.getText().toString()
                );
            }
        });
        viewModel.getUpdateUserLiveData().observe(this, updateSubUserResponse -> {
            Toast.makeText(this, "User Details Updated Successfully", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        });
        binding.btCloseInUSerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(UserDetailsInProfileViewModel.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}