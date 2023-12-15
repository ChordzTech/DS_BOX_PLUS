package com.dss.dsboxplus.loginandverification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityHomeScreenBinding;
import com.dss.dsboxplus.fragments.ClientFragment;
import com.dss.dsboxplus.fragments.EstimatesFragment;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.dss.dsboxplus.model.EstimatesDataModel;
import com.dss.dsboxplus.recyclerview.EstimatesViewAdapter;


public class HomeActivity extends AppCompatActivity implements IHomeActivityCallBack {
    ActivityHomeScreenBinding homeScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen);
        replaceFragment(new EstimatesFragment(this));
        homeScreenBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.estimate) {
                replaceFragment(new EstimatesFragment(this));
            } else if (item.getItemId() == R.id.users) {
                replaceFragment(new ClientFragment());
            } else {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void loadClientFragmentOnEmptyEstimates() {
        replaceFragment(new ClientFragment());
    }


}