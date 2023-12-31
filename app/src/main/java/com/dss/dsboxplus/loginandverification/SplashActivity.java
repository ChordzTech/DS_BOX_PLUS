package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsList;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse;
import com.dss.dsboxplus.databinding.ActivitySplashBinding;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.dss.dsboxplus.viewmodels.homeviewmodel.SplashViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class SplashActivity extends BaseActivity {
    ActivitySplashBinding mainBinding;
    private SplashViewModel splashViewModel;
    private ArrayList<BusinessDetailsList> businessDetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        splashViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(SplashViewModel.class);

        initObservers();
        fetchData();
    }

    private void initObservers() {
        splashViewModel.getUserDetailsResponse().observe(this, userDetailsResponse -> {
            addUserDataToPreferences(userDetailsResponse);
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        splashViewModel.getLoaderLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                showLoader();
            } else {
                hideLoader();
            }
        });
    }

    private void addUserDataToPreferences(UserDetailsResponse userDetailsResponse) {
        Integer userId = userDetailsResponse.getData().get(0).getUserid();
        Integer businessId = userDetailsResponse.getData().get(0).getBusinessid();
        AppPreferences.INSTANCE.saveLongToSharedPreferences(this,
                AppPreferences.USER_ID, userId);
        AppPreferences.INSTANCE.saveLongToSharedPreferences(this,
                AppPreferences.BUSINESS_ID, businessId);
    }

    private void fetchData() {
        String deviceInfo = Build.BRAND + Build.MODEL;
//        splashViewModel.getUserDetails(
//                AppPreferences.INSTANCE.getLongValueFromSharedPreferences(
//                        AppPreferences.INSTANCE.getMOBILE_NUMBER()), deviceInfo);
        splashViewModel.getUserDetails(
                "9421013332", "Xiaomi Redmi Note 8 Pro");
    }
}