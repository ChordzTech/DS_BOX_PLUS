package com.dss.dsboxpro.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxpro.R;
import com.dss.dsboxpro.baseview.BaseActivity;
import com.dss.dsboxpro.data.configdata.ConfigDataProvider;
import com.dss.dsboxpro.data.repo.response.BusinessDetails;
import com.dss.dsboxpro.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxpro.databinding.ActivityBusinessDetailsBinding;
import com.dss.dsboxpro.home.HomeActivity;
import com.dss.dsboxpro.viewmodels.AppViewModelFactory;
import com.dss.dsboxpro.viewmodels.homeviewmodel.HomeViewModel;
import com.dss.dsboxpro.viewmodels.profileviewmodels.BusinessSettingsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.button.MaterialButton;


public class BusinessDetailsActivity extends BaseActivity {
    MaterialButton btCloseInBusinessDetails;
    ActivityBusinessDetailsBinding binding;
    BusinessSettingsActivityViewModel viewModel;
    HomeViewModel homeViewModel;
    BusinessDetails businessDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_details);
        initView();


    }


    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(BusinessSettingsActivityViewModel.class);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);
        if (isConnectedToInternet()) {
            homeViewModel.getBusinessDetails();
        } else {
            showNoInternetDialog();
        }
        homeViewModel.getBusinessDetailsLiveData().observe(this, businessDetailsResponse -> {
            if (businessDetailsResponse.getData() != null) {
                businessDetails = businessDetailsResponse.getData();
            }
        });
    }

    private void initView() {
        initViewModel();
        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();

            binding.tietBusinessName.setText(businessDetails.getBusinessname());
            binding.tietBusinessContact.setText(businessDetails.getContactno());
            binding.tietBusinessAddress.setText(businessDetails.getAddress());
            binding.tietBusinessPincode.setText(businessDetails.getPincode());
            binding.tietBusinessMail.setText(businessDetails.getEmail());
        }


        btCloseInBusinessDetails = findViewById(R.id.btCloseInBusinessDetails);
        btCloseInBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btSaveinBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet()) {
                    viewModel.updateBusinessDetails(
                            binding.tietBusinessName.getText().toString(),
                            binding.tietBusinessContact.getText().toString(),
                            binding.tietBusinessAddress.getText().toString(),
                            binding.tietBusinessPincode.getText().toString(),
                            binding.tietBusinessMail.getText().toString(),
                            businessDetails
                    );
                } else {
                    showNoInternetDialog();
                }
            }
        });
        viewModel.getUpdateBusinessdetailsLiveData().observe(this, updateBusinessDetailsResponse -> {
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            finishAffinity();
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}
