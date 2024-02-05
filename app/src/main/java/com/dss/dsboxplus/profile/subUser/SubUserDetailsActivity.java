package com.dss.dsboxplus.profile.subUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.repo.response.SubUser;
import com.dss.dsboxplus.databinding.ActivitySubUserDetailsBinding;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.profileviewmodels.SubUserDetailsViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.Objects;

public class SubUserDetailsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    ActivitySubUserDetailsBinding binding;
    String[] access = {"Full Access", "No Access", "Read Only"};
    SubUserDetailsViewModel viewModel;
    private SubUser userData;
    private String selectedAccessType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_user_details);
        initView();

    }

    private void initView() {
        initViewModel();
        binding.userAccessSpinnerInUserDetails.setOnItemSelectedListener(this);
        ArrayAdapter accessAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, access);
        accessAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        binding.userAccessSpinnerInUserDetails.setAdapter(accessAdapter);


        Intent intent = getIntent();
        // Check if the intent has the CLIENTS_BUNDLE extra
        if (intent.hasExtra("USERS_BUNDLE")) {
            // Retrieve the bundle from the intent
            Bundle bundle = intent.getBundleExtra("USERS_BUNDLE");

            // Check if the bundle is not null
            if (bundle != null) {
                userData = bundle.getParcelable("USERS");
            }
        }

        if (userData != null) {
            binding.tietSubUserName.setText(userData.getUsername());
            binding.tietSubUserContact.setText(userData.getMobileno());
            binding.tietSubUserDeviceInfo.setText(userData.getDeviceinfo());
            int spinnerValue = userData.getUseraccess();
            binding.userAccessSpinnerInUserDetails.setSelection(spinnerValue);
            if (Objects.equals(userData.getUserrole(), "Admin")) {
                binding.btDeleteInSubUSer.setVisibility(View.GONE);
            }else {
                binding.btDeleteInSubUSer.setVisibility(View.VISIBLE);

            }
        }


        binding.btDeleteInSubUSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteSubUser(userData.getUserid());
            }
        });
        viewModel.getDeleteSubUserLiveData().observe(this, deleteSubUserResponse -> {
            if (deleteSubUserResponse.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                finishAffinity();
                startActivity(new Intent(this, SuperUserSetting.class));
            } else {
                Toast.makeText(this, deleteSubUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btCloseInSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btUpdateInSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.updateSubUser(userData.getUserid(),
                        binding.tietSubUserName.getText().toString(),
                        selectedAccessType
                );
            }
        });
        viewModel.getUpdateSubUserLiveData().observe(this, updateSubUserResponse -> {
            if (updateSubUserResponse.getStatus().equalsIgnoreCase("success")) {
                Toast.makeText(this, "User Details Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, SuperUserSetting.class));
            } else {
                Toast.makeText(this, updateSubUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(SubUserDetailsViewModel.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedAccess = access[position];

        switch (selectedAccess) {
            case "Full Access":
                // Handle Full Access
                selectedAccessType = "2";
                break;
            case "No Access":
                // Handle No Access
                selectedAccessType = "3";

                break;
            case "Read Only":
                // Handle Read Only Access
                selectedAccessType = "1";

                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}