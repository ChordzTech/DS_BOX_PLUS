package com.dss.dsboxplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.dsboxplusui.databinding.ActivityHomeScreenBinding;
import com.dss.dsboxplus.fragments.Estimates_Fragment;
import com.dss.dsboxplus.fragments.Profile_Fragment;
import com.dss.dsboxplus.fragments.Client_Fragment;

public class HomeScreen extends AppCompatActivity {
    ActivityHomeScreenBinding homeScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen);
       replaceFragment(new Estimates_Fragment());
       homeScreenBinding.bottomNavigation.setOnItemSelectedListener(item -> {
           if (item.getItemId()==R.id.estimate){
               replaceFragment(new Estimates_Fragment());
           } else if (item.getItemId()==R.id.users) {
               replaceFragment(new Client_Fragment());
           }else {
               replaceFragment(new Profile_Fragment());
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

}