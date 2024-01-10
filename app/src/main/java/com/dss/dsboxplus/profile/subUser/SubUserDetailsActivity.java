package com.dss.dsboxplus.profile.subUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.repo.response.SubUser;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.databinding.ActivitySubUserDetailsBinding;

public class SubUserDetailsActivity extends BaseActivity {

    ActivitySubUserDetailsBinding binding;
    private SubUser userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sub_user_details);
        initView();

    }

    private void initView() {
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

        }
    }
}