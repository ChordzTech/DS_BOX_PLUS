package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.databinding.ActivityLoginScreenBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.SplashViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class LoginActivity extends BaseActivity {
    ActivityLoginScreenBinding loginScreenBinding;
    TextView dsBox;
    private SplashViewModel viewModel;
    EditText etPhoneNumber;
    Button btNext;
    ProgressBar pbSendingOtp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btNext = findViewById(R.id.btNext);
        dsBox = findViewById(R.id.tvName);
        pbSendingOtp = findViewById(R.id.pbSendingOtp);
        initView();
    }
    private void fetchData() {
//        String deviceInfo = (Build.BRAND + Build.MODEL).trim();
        String deviceInfo = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppPreferences.INSTANCE.saveStringToSharedPreferences(this,
                AppPreferences.DEVICE_INFO, deviceInfo);

        viewModel.getUserDetails(
                String.valueOf(AppPreferences.INSTANCE.getLongValueFromSharedPreferences(
                        AppPreferences.MOBILE_NUMBER)), deviceInfo);
//        splashViewModel.getUserDetails(
//                "9421013332", "Xiaomi Redmi Note 8 Pro");
    }

    private void initView() {
        if (isConnectedToInternet()) {
            RetrofitService retrofitService = RetrofitService.Companion.getInstance();
            MainRepository mainRepository = new MainRepository(retrofitService);
            viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(SplashViewModel.class);


            initObservers();
        } else {
            showNoInternetDialog();
        }
        loginScreenBinding.tvTermsAndPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, TermsAndPrivacyPolicyActivity.class);
//                intent.putExtra("termsandprivacylink", url);
                startActivity(intent);
            }
        });
        dsBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etPhoneNumber.getText().toString().trim().isEmpty()) {
                    if ((etPhoneNumber.getText().toString().trim()).length() == 10) {
                        AppPreferences.INSTANCE.saveLongToSharedPreferences(LoginActivity.this,
                                AppPreferences.MOBILE_NUMBER, Long.parseLong(etPhoneNumber.getText().toString()));
                        pbSendingOtp.setVisibility(View.VISIBLE);
                        btNext.setVisibility(View.INVISIBLE);
                        AppPreferences.INSTANCE.saveLongToSharedPreferences(LoginActivity.this,
                                AppPreferences.MOBILE_NUMBER, Long.parseLong(etPhoneNumber.getText().toString()));
                        validateNumber();

                    } else {
                        Toast.makeText(LoginActivity.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validateNumber() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + etPhoneNumber.getText().toString(),
                60, TimeUnit.SECONDS, LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        pbSendingOtp.setVisibility(View.GONE);
                        AppPreferences.INSTANCE.saveLongToSharedPreferences(LoginActivity.this,
                                AppPreferences.MOBILE_NUMBER, Long.parseLong(etPhoneNumber.getText().toString()));
                        btNext.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        pbSendingOtp.setVisibility(View.GONE);
                        btNext.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        fetchData();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        pbSendingOtp.setVisibility(View.GONE);
                        btNext.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(), VerifyOtpActivity.class);
                        intent.putExtra("mobile", etPhoneNumber.getText().toString());
                        intent.putExtra("backendotp", s);
                        startActivity(intent);

                    }
                }
        );
    }

    private void initObservers() {
        viewModel.getUserDetailsResponse().observe(this, userDetailsResponse -> {
            if (userDetailsResponse.getCode() == 404) {
                Intent intent = new Intent(getApplicationContext(), EnterBusinessDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                addUserDataToPreferences(userDetailsResponse);
                ConfigDataProvider.INSTANCE.setUserDetails(userDetailsResponse);
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.getLoaderLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                showLoader();
            } else {
                hideLoader();
            }
        });
    }


}