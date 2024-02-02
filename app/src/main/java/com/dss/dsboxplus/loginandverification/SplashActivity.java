package com.dss.dsboxplus.loginandverification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse;
import com.dss.dsboxplus.databinding.ActivitySplashBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.SplashViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;


public class SplashActivity extends BaseActivity {
    ActivitySplashBinding mainBinding;
    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }


    private void initView() {
        if (isConnectedToInternet()) {
            RetrofitService retrofitService = RetrofitService.Companion.getInstance();
            MainRepository mainRepository = new MainRepository(retrofitService);
            splashViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(SplashViewModel.class);


            initObservers();
            fetchData();
        } else {
            showNoInternetDialog();
        }

    }

    private void initObservers() {
        splashViewModel.getUserDetailsResponse().observe(this, userDetailsResponse -> {
            if (userDetailsResponse.getCode() == 404) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                addUserDataToPreferences(userDetailsResponse);
                ConfigDataProvider.INSTANCE.setUserDetails(userDetailsResponse);
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        splashViewModel.getLoaderLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                showLoader();
            } else {
                hideLoader();
            }
        });
    }

    private void fetchData() {
//        String deviceInfo = (Build.BRAND + Build.MODEL).trim();
        String deviceInfo = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppPreferences.INSTANCE.saveStringToSharedPreferences(this,
                AppPreferences.DEVICE_INFO, deviceInfo);

        splashViewModel.getUserDetails(
                String.valueOf(AppPreferences.INSTANCE.getLongValueFromSharedPreferences(
                        AppPreferences.MOBILE_NUMBER)), deviceInfo);
//        splashViewModel.getUserDetails(
//                "9421013332", "Xiaomi Redmi Note 8 Pro");
    }


}