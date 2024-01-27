package com.dss.dsboxplus.profile.subUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.databinding.ActivityAddSubUserBinding;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeandotpviewmodels.EnterBusinessDetailsActivityViewModel;
import com.dss.dsboxplus.viewmodels.profileviewmodels.AddSubUserViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

public class AddSubUserActivity extends BaseActivity {
    ActivityAddSubUserBinding addSubUserBinding;
    AddSubUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSubUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_sub_user);
        initView();

    }


    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(AddSubUserViewModel.class);
    }

    private void initView() {
        initViewModel();
        addSubUserBinding.btSubmitInAddSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = addSubUserBinding.tietUserNameInSubUser.getText().toString();
                String contact = addSubUserBinding.tietContactNoInSubUser.getText().toString();

                boolean check = validateInfo(username, contact);
                if (check) {
                    viewModel.addSubUSer(username, contact);

                }

            }

        });
        viewModel.getAddSubUserLiveData().observe(this, addUserResponse -> {
            if (addUserResponse != null && addUserResponse.getStatus().equalsIgnoreCase("success")) {
                finishAffinity();
                startActivity(new Intent(this, SuperUserSetting.class));
                Toast.makeText(this, addUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "User already exists or please try again later", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateInfo(String username, String contact) {
        if (username.isEmpty()) {
            addSubUserBinding.tietUserNameInSubUser.requestFocus();
            addSubUserBinding.tietUserNameInSubUser.setError("Enter User Name");
            return false;
        } else if (contact.length() <= 9) {
            addSubUserBinding.tietContactNoInSubUser.requestFocus();
            addSubUserBinding.tietContactNoInSubUser.setError("Enter Contact Number");
            return false;
        } else {
            return true;
        }
    }

}