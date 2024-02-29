package com.dss.dsboxplus.loginandverification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatDelegate;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
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

    private void showNoAccessPopup() {
        // Use AlertDialog or any other popup method to display a message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Access");
        builder.setMessage("You do not have access to this application.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Close the app or handle as needed
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private boolean hasUserAccess(UserDetailsResponse userDetailsResponse, int i) {
        if (userDetailsResponse.getData() != null && !userDetailsResponse.getData().isEmpty()) {
            UserData userData = userDetailsResponse.getData().get(0); // Assuming there is only one UserData in the list
            return userData.getUseraccess() != null && userData.getUseraccess() == i;
        }
        return false;
    }

    private void initObservers() {
        splashViewModel.getUserDetailsResponse().observe(this, userDetailsResponse -> {
            if (userDetailsResponse.getCode() == 404) {
                finishAffinity();
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            } else if (userDetailsResponse.getCode() == 400) {
                showMessagePopUp(userDetailsResponse.getMessage());
            } else {

                if (userDetailsResponse.getData() != null && !userDetailsResponse.getData().isEmpty()) {
                    addUserDataToPreferences(userDetailsResponse);
                    ConfigDataProvider.INSTANCE.setUserDetails(userDetailsResponse);
                    AppPreferences.INSTANCE.saveStringToSharedPreferences(this, AppPreferences.APP_STATUS,
                            userDetailsResponse.getData().get(0).getStatus());

                    if (ConfigDataProvider.INSTANCE.getUserDetails() != null && hasUserAccess(ConfigDataProvider.INSTANCE.getUserDetails(), 3)) {
                        showNoAccessPopup();
                    }
                    String phoneNumber = userDetailsResponse.getData().get(0).getMobileno();
                    mainBinding.tvDeviceNumber.setText("+91 " + phoneNumber);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishAffinity();
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }, 1000);
                }

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
        if (isConnectedToInternet()) {
            splashViewModel.getUserDetails(
                    String.valueOf(AppPreferences.INSTANCE.getLongValueFromSharedPreferences(
                            AppPreferences.MOBILE_NUMBER)), deviceInfo);
        } else {
            showNoInternetDialog();
        }
//        splashViewModel.getUserDetails(
//                "9421013332", "Xiaomi Redmi Note 8 Pro");
    }


}