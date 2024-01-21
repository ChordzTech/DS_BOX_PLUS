package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.databinding.ActivityBusinessDetailsBinding;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.dss.dsboxplus.viewmodels.profileviewmodels.BusinessSettingsActivityViewModel;
import com.dss.dsboxplus.viewmodels.profileviewmodels.QuotationTermsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.button.MaterialButton;


public class BusinessDetailsActivity extends BaseActivity {
    MaterialButton btCloseInBusinessDetails;
    ActivityBusinessDetailsBinding binding;
    BusinessSettingsActivityViewModel viewModel;
    HomeViewModel homeViewModel  ;
    BusinessDetails businessDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_details);
        initView();
        initViewModel();
        addOververs();
    }

    private void addOververs() {
        viewModel.getUpdateBusinessdetailsLiveData().observe(this, updateBusinessDetailsResponse -> {
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT);
            finish();
        });
    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(BusinessSettingsActivityViewModel.class);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);

        homeViewModel.getBusinessDetails();
    }

    private void initView() {
        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();

            binding.tietBusinessName.setText(businessDetails.getBusinessname());
            binding.tietBusinessContact.setText(businessDetails.getContactno());
            binding.tietBusinessAddress.setText(businessDetails.getAddress());
            binding.tietBusinessPincode.setText(businessDetails.getPincode());
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
                viewModel.updateBusinessDetails(
                  binding.tietBusinessName.getText().toString(),
                  binding.tietBusinessContact.getText().toString(),
                  binding.tietBusinessAddress.getText().toString(),
                  binding.tietBusinessPincode.getText().toString(),
                  binding.tietBusinessMail.getText().toString(),
                        businessDetails
                );
            }
        });
    }
}
