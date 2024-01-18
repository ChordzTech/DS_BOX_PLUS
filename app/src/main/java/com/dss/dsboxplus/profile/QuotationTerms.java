package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.databinding.ActivityQuotationTermsBinding;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.profileviewmodels.QuotationTermsActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.button.MaterialButton;


public class QuotationTerms extends BaseActivity {
    MaterialButton btCloseInQuotationTerms;

    ActivityQuotationTermsBinding binding;
    QuotationTermsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quotation_terms);
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
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(QuotationTermsActivityViewModel.class);
    }

    private void initView() {
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
                viewModel.updateBusinessQuotation(
                        binding.tietTermsAndConditions.getText().toString()
                );
            }
        });
    }
}