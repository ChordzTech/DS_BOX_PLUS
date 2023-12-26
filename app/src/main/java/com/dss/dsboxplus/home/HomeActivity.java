package com.dss.dsboxplus.home;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityHomeScreenBinding;
import com.dss.dsboxplus.fragments.ClientFragment;
import com.dss.dsboxplus.fragments.EstimatesFragment;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.dss.dsboxplus.loginandverification.IHomeActivityCallBack;
import com.dss.dsboxplus.loginandverification.SplashActivity;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.dss.dsboxplus.viewmodels.homeviewmodel.SplashViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements IHomeActivityCallBack {
    private EstimatesFragment estimatesFragment;
    private ArrayList<Client> clientsList = new ArrayList<>();
    private ArrayList<DataItem> estimateList = new ArrayList<>();
    private ArrayList<AppConfigDataItems> appConfigList=new ArrayList<>();
    private ClientFragment clientFragment;
    private ProfileFragment profileFragment;
    private ActivityHomeScreenBinding homeScreenBinding;
    private HomeViewModel homeViewModel;
    //    private EstimatesFragment estimatesFragment;
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
        homeViewModel.getAppConfig();
    }

    private void initObservables() {
        homeViewModel.getEstimateListLiveData().observe(this, estimateListResponse -> {
            if (!estimateListResponse.getData().isEmpty()) {
                estimateList = (ArrayList<DataItem>) estimateListResponse.getData();
                estimatesFragment.setEstimateList(estimateList);
            }
            Log.e("TAG", "estimateListResponse: " + estimateListResponse.getData().size());
        });
        homeViewModel.getClientListLiveData().observe(this, clientListResponse -> {
            if (!clientListResponse.getData().isEmpty()) {
                clientsList = (ArrayList<Client>) clientListResponse.getData();
                clientFragment.setClientList(clientsList);
            }
            Log.e("TAG", "clientListResponse: " + clientListResponse.getData().size());
        });
        homeViewModel.getAppConfigLiveData().observe(this,appConfigResponse -> {
            if (!appConfigResponse.getData().isEmpty()) {
                appConfigList = (ArrayList<AppConfigDataItems>) appConfigResponse.getData();
                profileFragment.setAppConfigList(appConfigList);
            }
            Log.e("TAG", "estimateListResponse: " + appConfigResponse.getData().size());
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
                if (!estimateList.isEmpty()) {
                    estimatesFragment.setEstimateList(estimateList);
                }
            } else if (item.getItemId() == R.id.users) {
                replaceFragment(clientFragment);
                if (!clientsList.isEmpty()) {
                    clientFragment.setClientList(clientsList);
                }

            } else {
                replaceFragment(profileFragment);
                if (!appConfigList.isEmpty()) {
                    profileFragment.setAppConfigList(appConfigList);
                }
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