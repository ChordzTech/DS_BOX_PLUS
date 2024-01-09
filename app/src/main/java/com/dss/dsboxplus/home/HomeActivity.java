package com.dss.dsboxplus.home;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.data.repo.response.GetSubForBusinessResponse;
import com.dss.dsboxplus.data.repo.response.SubForBusiness;
import com.dss.dsboxplus.data.repo.response.SubscriptionDataItem;
import com.dss.dsboxplus.databinding.ActivityHomeScreenBinding;
import com.dss.dsboxplus.fragments.ClientFragment;
import com.dss.dsboxplus.fragments.EstimatesFragment;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.dss.dsboxplus.loginandverification.IHomeActivityCallBack;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity implements IHomeActivityCallBack {
    MutableLiveData<Boolean> onFloatingActionClickLiveData;
    private EstimatesFragment estimatesFragment;
    private ArrayList<Client> clientsList = new ArrayList<>();
    private ArrayList<SubscriptionDataItem> subscriptionList = new ArrayList<>();
    private ArrayList<SubForBusiness> subscription=new ArrayList<>();
    private ArrayList<DataItem> estimateList = new ArrayList<>();
    private ArrayList<AppConfigDataItems> appConfigList = new ArrayList<>();
    private ClientFragment clientFragment;
    private String base64Code = new String();
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
        homeViewModel.getClientList();
        homeViewModel.getAppConfig();
        homeViewModel.getSubscriptionList();
        homeViewModel.getQrCode();
        homeViewModel.getEstimateByBusinessIdUserId();
        homeViewModel.getSubForBusiness();
    }

    private void initObservables() {

        homeViewModel.getEstimateListLiveData().observe(this, estimateListResponse -> {
            if (!estimateListResponse.getData().isEmpty()) {
                estimateList = (ArrayList<DataItem>) estimateListResponse.getData();
                estimatesFragment.setEstimateList(estimateList);
                ConfigDataProvider.INSTANCE.setEstimateListResponse(estimateListResponse);
            }
        });
        homeViewModel.getClientListLiveData().observe(this, clientListResponse -> {
            if (!clientListResponse.getData().isEmpty()) {
                clientsList = (ArrayList<Client>) clientListResponse.getData();
                ConfigDataProvider.INSTANCE.setClientListResponse(clientListResponse);
            }
        });
        homeViewModel.getAppConfigLiveData().observe(this, appConfigResponse -> {
            if (!appConfigResponse.getData().isEmpty()) {
                appConfigList = (ArrayList<AppConfigDataItems>) appConfigResponse.getData();
                profileFragment.setAppConfigList(appConfigList);
                ConfigDataProvider.INSTANCE.setAppConfigResponse(appConfigResponse);
            }
        });
        homeViewModel.getSubscriptionLiveData().observe(this, subscriptionDetailsResponse -> {
            if (!subscriptionDetailsResponse.getData().isEmpty()) {
                subscriptionList = (ArrayList<SubscriptionDataItem>) subscriptionDetailsResponse.getData();
                profileFragment.setSubscriptionList(subscriptionList);
                ConfigDataProvider.INSTANCE.setSubResponse(subscriptionDetailsResponse);
            }
        });
        homeViewModel.getQrCodeLiveData().observe(this, qrCodeResponse -> {
            if (!qrCodeResponse.getBase64Code().isEmpty()) {
                base64Code = qrCodeResponse.getBase64Code();
                ConfigDataProvider.INSTANCE.setQrCodeBase64(base64Code);
            }
        });
        homeViewModel.getSubForBusinessLiveData().observe(this,getSubForBusinessResponse -> {
            if(!getSubForBusinessResponse.getData().isEmpty()){
                subscription=(ArrayList<SubForBusiness>) getSubForBusinessResponse.getData();
                profileFragment.setSubscription(subscription);
            }
        });
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);

        estimatesFragment = new EstimatesFragment(this);
        onFloatingActionClickLiveData = estimatesFragment.getOnFloatingActionClickLiveData();
        onFloatingActionClickLiveData.observe(this, onClicked -> {
            if (onClicked) {
                homeScreenBinding.bottomNavigation.setSelectedItemId(R.id.users);
                Toast.makeText(this, "Select Client", Toast.LENGTH_SHORT).show();
            }
        });
        clientFragment = new ClientFragment();
        profileFragment = new ProfileFragment();
        replaceFragment(estimatesFragment);
        homeScreenBinding.tvPageTitle.setText(R.string.title_estimates);
        homeScreenBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.estimate) {
                homeScreenBinding.tvPageTitle.setText(R.string.title_estimates);
                replaceFragment(estimatesFragment);
                homeViewModel.getEstimateByBusinessIdUserId();
                if (!estimateList.isEmpty()) {
                    estimatesFragment.setEstimateList(estimateList);
                }
            } else if (item.getItemId() == R.id.users) {
                homeScreenBinding.tvPageTitle.setText(R.string.title_clients);
                replaceFragment(clientFragment);
                homeViewModel.getClientList();
                if (!clientsList.isEmpty()) {
                    clientFragment.setClientList(clientsList);
                }

            } else {
                homeScreenBinding.tvPageTitle.setText(R.string.title_profile);
                replaceFragment(profileFragment);
                if (!appConfigList.isEmpty()) {
                    profileFragment.setAppConfigList(appConfigList);
                }
                if (!subscriptionList.isEmpty()) {
                    profileFragment.setSubscriptionList(subscriptionList);
                }
                if (!base64Code.isEmpty()) {
                    profileFragment.setQrCode(base64Code);
                }
                if (!subscription.isEmpty()){
                    profileFragment.setSubscription(subscription);
                }
            }
            return true;
        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showLogoutPopUp();
            }
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