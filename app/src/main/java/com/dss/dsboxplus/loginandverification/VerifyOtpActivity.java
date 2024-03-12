package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.SplashViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOtpActivity extends BaseActivity {
    EditText inputOtpOne, inputOtpTwo, inputOtpThree, inputOtpFour, inputOtpFive, inputOtpSix;
    Button btVerify;
    String backendopt;
    ProgressBar pbVerifyOtp;
    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setContentView(R.layout.activity_verify_otp);
        inputOtpOne = findViewById(R.id.inputOtpOne);
        inputOtpTwo = findViewById(R.id.inputOtpTwo);
        inputOtpThree = findViewById(R.id.inputOtpThree);
        inputOtpFour = findViewById(R.id.inputOtpFour);
        inputOtpFive = findViewById(R.id.inputOtpFive);
        inputOtpSix = findViewById(R.id.inputOtpSix);
        btVerify = findViewById(R.id.btVerify);
        pbVerifyOtp = findViewById(R.id.pbVerifyOtp);

        TextView textView = findViewById(R.id.tvVerify);
        textView.setText(String.format("Verify" + "+91-%s", getIntent().getStringExtra("mobile")));
        backendopt = getIntent().getStringExtra("backendotp");
        btVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputOtpOne.getText().toString().trim().isEmpty() && !inputOtpTwo.getText().toString().trim().isEmpty()) {
                    String enterCodeOtp = inputOtpOne.getText().toString() + inputOtpTwo.getText().toString() + inputOtpThree.getText().toString() + inputOtpFour.getText().toString() + inputOtpFive.getText().toString() + inputOtpSix.getText().toString();
                    if (backendopt != null) {
                        if (Integer.parseInt(backendopt) == Integer.parseInt(enterCodeOtp)) {
                            fetchData();
                            Toast.makeText(VerifyOtpActivity.this, "OTP verified", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        pbVerifyOtp.setVisibility(View.VISIBLE);
                        btVerify.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(backendopt, enterCodeOtp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pbVerifyOtp.setVisibility(View.GONE);
                                btVerify.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    fetchData();
                                } else {
                                    Toast.makeText(VerifyOtpActivity.this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(VerifyOtpActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(VerifyOtpActivity.this, "OTP Verify", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VerifyOtpActivity.this, "Please Enter All Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberOtpMove();
    }

    private void fetchData() {
//        String deviceInfo = (Build.BRAND + Build.MODEL).trim();
        String deviceInfo = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppPreferences.INSTANCE.saveStringToSharedPreferences(this, AppPreferences.DEVICE_INFO, deviceInfo);
        if (isConnectedToInternet()) {
            viewModel.getUserDetails(String.valueOf(AppPreferences.INSTANCE.getLongValueFromSharedPreferences(AppPreferences.MOBILE_NUMBER)), deviceInfo);
        } else {
            showNoInternetDialog();
        }
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
    }

    private void initObservers() {
        viewModel.getUpdateSubUserLiveData().observe(this, it -> {
            UserDetailsResponse userDetails = ConfigDataProvider.INSTANCE.getUserDetails();
            userDetails.getData().get(0).setAndroidid(it.getData().get(0).getAndroidid());
            ConfigDataProvider.INSTANCE.setUserDetails(userDetails);
            addUserDataToPreferences(ConfigDataProvider.INSTANCE.getUserDetails());
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
            Log.e("TAG", "initObservers: " + " device id updated start HomeActivity");

        });
        viewModel.getUserDetailsResponse().observe(this, userDetailsResponse -> {
            if (userDetailsResponse.getCode() == 404) {
                Intent intent = new Intent(getApplicationContext(), EnterBusinessDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                Log.e("TAG", "initObservers: " + "start EnterBusinessDetailsActivity");
            } else if (userDetailsResponse.getData() != null && !userDetailsResponse.getData().isEmpty() && userDetailsResponse.getData().get(0).getAndroidid().equalsIgnoreCase("NewUser")) {
                String deviceInfo = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                UserData userData = userDetailsResponse.getData().get(0);
                ConfigDataProvider.INSTANCE.setUserDetails(userDetailsResponse);
                viewModel.updateSubUser(userData.getUserid(), deviceInfo, userData);
            }
            if (userDetailsResponse.getCode() == 400) {
                showMessagePopUp("Your account is temporarily blocked. Please contact Admin.");
            } else {
//                ConfigDataProvider.INSTANCE.setUserDetails(userDetailsResponse);
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                Log.e("TAG", "initObservers: " + "start HomeActivity");
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


    private void numberOtpMove() {
        inputOtpOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputOtpTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtpTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputOtpThree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtpThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputOtpFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtpFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputOtpFive.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtpFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputOtpSix.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}