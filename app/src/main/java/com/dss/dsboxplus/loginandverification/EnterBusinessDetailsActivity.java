package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.databinding.ActivityEnterBusinessDetailsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeandotpviewmodels.EnterBusinessDetailsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.button.MaterialButton;

public class EnterBusinessDetailsActivity extends BaseActivity {
    ActivityEnterBusinessDetailsBinding businessDetailsBinding;
    MaterialButton btSubmitInEnterBusinessDetails;
    private EnterBusinessDetailsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_business_details);

        initViewModel();
        addOververs();

//        businessDetailsBinding.tietBusinessContact.setText((int) AppPreferences.INSTANCE.getLongValueFromSharedPreferences(AppPreferences.MOBILE_NUMBER));
        btSubmitInEnterBusinessDetails = findViewById(R.id.btSubmitInEnterBusinessDetails);
        btSubmitInEnterBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String businessName = businessDetailsBinding.tietBusinessName.getText().toString();
                String businessContact = businessDetailsBinding.tietBusinessContact.getText().toString();
                String businessAddress = businessDetailsBinding.tietBusinessAddress.getText().toString();
                String businessPinCode = businessDetailsBinding.tietBusinessPinCode.getText().toString();
                boolean check = validateInfo(businessName, businessContact, businessAddress, businessPinCode);
                if (check) {
                    if (isConnectedToInternet()) {
                        viewModel.addBusinessDetails(businessName, businessContact, businessAddress, businessPinCode);
                    } else {
                        showNoInternetDialog();
                    }
                }
            }
        });
    }

    private void addOververs() {
        viewModel.getBusinessDetailsLiveData().observe(this, businessDetailsResponse -> {
            this.businessDetailsResponse = businessDetailsResponse;
            AppPreferences.INSTANCE.saveLongToSharedPreferences(this, AppPreferences.BUSINESS_ID,
                    businessDetailsResponse.getData().getBusinessid());
            addUserForBusiness(businessDetailsResponse);

        });
        viewModel.getUserDetailsResponse().observe(this, userDetailsResponse -> {
            addUserDataToPreferences(userDetailsResponse);
            ConfigDataProvider.INSTANCE.setUserDetails(userDetailsResponse);
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        });

        viewModel.getAddUserResponseLiveData().observe(this, addUserResponse -> {
            AppPreferences.INSTANCE.saveLongToSharedPreferences(this,
                    AppPreferences.USER_ID, addUserResponse.getData().getUserid());
            AppPreferences.INSTANCE.saveLongToSharedPreferences(this,
                    AppPreferences.BUSINESS_ID, addUserResponse.getData().getBusinessid());
            AppPreferences.INSTANCE.saveLongToSharedPreferences(this,
                    AppPreferences.MOBILE_NUMBER, Long.parseLong(addUserResponse.getData().getMobileno()));
            addClientForBusiness(businessDetailsResponse);
        });
        viewModel.getAddClientRequestLiveData().observe(this, clientListResponse -> {
            if (isConnectedToInternet()) {
                String deviceInfo = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

                viewModel.getUserDetails(
                        String.valueOf(AppPreferences.INSTANCE.getLongValueFromSharedPreferences(
                                AppPreferences.MOBILE_NUMBER)), deviceInfo);
            } else {
                showNoInternetDialog();
            }

        });
    }

    private void addClientForBusiness(BusinessDetailsResponse businessDetailsResponse) {
        if (isConnectedToInternet()) {
            viewModel.addClient(businessDetailsResponse);
        } else {
            showNoInternetDialog();
        }
    }

    private void addUserForBusiness(BusinessDetailsResponse businessDetailsResponse) {
        if (isConnectedToInternet()) {
            viewModel.addUser(businessDetailsResponse, this);
        } else {
            showNoInternetDialog();
        }

    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(EnterBusinessDetailsActivityViewModel.class);

    }

    private boolean validateInfo(String businessName, String businessContact, String businessAddress, String businessPinCode) {
        if (businessName.length() == 0) {
            businessDetailsBinding.tietBusinessName.requestFocus();
            businessDetailsBinding.tietBusinessName.setError("Please Enter Business Name");
            return false;
//        } else if (businessContact.length() <= 9) {
//            businessDetailsBinding.tietBusinessContact.requestFocus();
//            businessDetailsBinding.tietBusinessContact.setError("Please Enter Business Contact");
//            return false;
        } else if (businessAddress.length() == 0) {
            businessDetailsBinding.tietBusinessAddress.requestFocus();
            businessDetailsBinding.tietBusinessAddress.setError("Please Enter Business Address");
            return false;
        } else if (businessPinCode.length() <= 5) {
            businessDetailsBinding.tietBusinessPinCode.requestFocus();
            businessDetailsBinding.tietBusinessPinCode.setError("Enter Six Digits Code");
            return false;
        } /*else if (!String.valueOf(AppPreferences.INSTANCE.getLongValueFromSharedPreferences(AppPreferences.MOBILE_NUMBER)).equals(businessContact)) {
            Toast.makeText(this, "Please enter verified mobile number", Toast.LENGTH_SHORT).show();
            return false;
        }*/ else {
            return true;
        }
    }
}