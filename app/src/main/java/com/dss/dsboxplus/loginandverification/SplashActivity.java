package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsList;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.databinding.ActivitySplashBinding;
import com.dss.dsboxplus.viewmodels.homeviewmodel.SplashViewModel;

import java.util.ArrayList;


public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding mainBinding;
    private SplashViewModel splashViewModel;
    private ArrayList<BusinessDetailsList> businessDetailsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
//        initObservables();
//        fetchData();


    }

    private void initView() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    private void initObservables() {
        splashViewModel.getBusinessDetailsLiveData().observe(this, businessDetailsResponse -> {

        });

    }

    private void fetchData() {
        splashViewModel.getBusinessDetails();
    }
}