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
import com.dss.dsboxpro.databinding.ActivityQuotationTermsBinding;
import com.dss.dsboxpro.home.HomeActivity;
import com.dss.dsboxpro.viewmodels.AppViewModelFactory;
import com.dss.dsboxpro.viewmodels.homeviewmodel.HomeViewModel;
import com.dss.dsboxpro.viewmodels.profileviewmodels.QuotationTermsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.button.MaterialButton;


public class QuotationTerms extends BaseActivity {
    MaterialButton btCloseInQuotationTerms;

    ActivityQuotationTermsBinding binding;
    QuotationTermsActivityViewModel viewModel;
    HomeViewModel homeViewModel;
    BusinessDetails businessDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quotation_terms);
        initView();


    }


    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(QuotationTermsActivityViewModel.class);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);

        homeViewModel.getBusinessDetails();
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

            //Get the estimation note from business details
            String estimateNote = businessDetails.getEstimatenote();

            // Replace any occurrence of '-' followed by a number with a newline character
            estimateNote = estimateNote.replaceAll("-([0-9])", "\n$1");
            // Set the text in the EditText
            binding.tietTermsAndConditions.setText(estimateNote);


        }
        btCloseInQuotationTerms = findViewById(R.id.btCloseInQuotationTerms);
        btCloseInQuotationTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btSaveInQuotationTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet()) {
                    viewModel.updateBusinessQuotation(
                            binding.tietTermsAndConditions.getText().toString(),
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