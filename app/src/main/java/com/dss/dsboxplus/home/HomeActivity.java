package com.dss.dsboxplus.home;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.data.repo.response.EstimateListResponse;
import com.dss.dsboxplus.databinding.ActivityHomeScreenBinding;
import com.dss.dsboxplus.fragments.ClientFragment;
import com.dss.dsboxplus.fragments.EstimatesFragment;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.dss.dsboxplus.loginandverification.IHomeActivityCallBack;
import com.dss.dsboxplus.model.EstimatesDataModel;
import com.dss.dsboxplus.recyclerview.EstimatesViewAdapter;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements IHomeActivityCallBack {
    private EstimatesFragment estimatesFragment;
    private ClientFragment clientFragment;
    private ProfileFragment profileFragment;
    private ActivityHomeScreenBinding homeScreenBinding;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen);
        initView();
        initObservables();
        fetchData();
    }

    private void fetchData() {
        homeViewModel.getEstimateList();
        homeViewModel.getClientList();
    }

    private void initObservables() {
        homeViewModel.getEstimateListLiveData().observe(this, estimateListResponse -> {
            estimatesFragment.setEstimateList((ArrayList<DataItem>) estimateListResponse.getData());
            Log.e("TAG", "estimateListResponse: " + estimateListResponse.getData().size());
        });
        homeViewModel.getClientListLiveData().observe(this, clientListResponse -> {
            clientFragment.setEstimateList((ArrayList<Client>) clientListResponse.getData());
            Log.e("TAG", "clientListResponse: " + clientListResponse.getData().size());
        });
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);

        estimatesFragment = new EstimatesFragment(this);
        clientFragment = new ClientFragment();
        profileFragment = new ProfileFragment();
        replaceFragment(estimatesFragment);
        homeScreenBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.estimate) {
                replaceFragment(estimatesFragment);
            } else if (item.getItemId() == R.id.users) {
                replaceFragment(clientFragment);
            } else {
                replaceFragment(profileFragment);
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