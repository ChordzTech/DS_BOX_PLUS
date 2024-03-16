package com.dss.dsboxplus.home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.alertdialog.DialogUtils;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.data.repo.response.SubscriptionDataItem;
import com.dss.dsboxplus.data.repo.response.SubscriptionForBusiness;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse;
import com.dss.dsboxplus.databinding.ActivityHomeScreenBinding;
import com.dss.dsboxplus.fragments.ClientFragment;
import com.dss.dsboxplus.fragments.EstimatesFragment;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.dss.dsboxplus.loginandverification.IHomeActivityCallBack;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.profile.SubscriptionActivity;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity implements IHomeActivityCallBack {
    MutableLiveData<Boolean> onFloatingActionClickLiveData;
    private EstimatesFragment estimatesFragment;
    private ArrayList<Client> clientsList = new ArrayList<>();
    private ArrayList<SubscriptionDataItem> subscriptionList = new ArrayList<>();
    private ArrayList<SubscriptionForBusiness> subscription = new ArrayList<>();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (isConnectedToInternet()) {
            homeViewModel.getClientList();

        } else {
            showNoInternetDialog();
        }
    }

    private void fetchData() {
        String deviceInfo = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppPreferences.INSTANCE.saveStringToSharedPreferences(this,
                AppPreferences.DEVICE_INFO, deviceInfo);
        if (isConnectedToInternet()) {
            homeViewModel.getUserDetails(
                    String.valueOf(AppPreferences.INSTANCE.getLongValueFromSharedPreferences(
                            AppPreferences.MOBILE_NUMBER)), deviceInfo);
        } else {
            showNoInternetDialog();
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isConnectedToInternet()) {
                    homeViewModel.getAppConfig();
                    homeViewModel.getSubscriptionList();
                    homeViewModel.getQrCode();
                    homeViewModel.getSubForBusiness();
                    homeViewModel.getBusinessDetails();
                    homeViewModel.getClientList();
                    homeViewModel.getEstimateByBusinessIdUserId();
                } else {
                    showNoInternetDialog();
                }
            }
        }, 1000);

    }

    private void initObservables() {

        homeViewModel.getGloabalEstimateListLiveData().observe(this,it->{
            ConfigDataProvider.INSTANCE.globalEstimateList.addAll(it);
        });

        homeViewModel.getEstimateListLiveData().observe(this, estimateListResponse -> {
            if (!estimateListResponse.getData().isEmpty()) {
                estimateList = (ArrayList<DataItem>) estimateListResponse.getData();
                estimatesFragment.setEstimateList(estimateList);
//                ConfigDataProvider.INSTANCE.setEstimateListResponse(estimateListResponse);
            }
        });
        homeViewModel.getUserDetailsResponse().observe(this, userDetailsResponse -> {
            if (userDetailsResponse.getStatus().equalsIgnoreCase("success")) {
                ConfigDataProvider.INSTANCE.setUserDetails(userDetailsResponse);
                addUserDataToPreferences(userDetailsResponse);
                if (ConfigDataProvider.INSTANCE.getUserDetails() != null && hasUserAccess(ConfigDataProvider.INSTANCE.getUserDetails(), 3)) {
                    showNoAccessPopup();
                }
            }
        });
        homeViewModel.getClientListLiveData().observe(this, clientListResponse -> {
            if (!clientListResponse.getData().isEmpty()) {
                clientsList = (ArrayList<Client>) clientListResponse.getData();
                ConfigDataProvider.INSTANCE.setClientListResponse(clientListResponse);
                ConfigDataProvider.INSTANCE.createClientIdMap(clientListResponse.getData());
            }
        });
        homeViewModel.getAppConfigLiveData().observe(this, appConfigResponse -> {
            if (!appConfigResponse.getData().isEmpty()) {
                appConfigList = (ArrayList<AppConfigDataItems>) appConfigResponse.getData();
                profileFragment.setAppConfigList(appConfigList);
//                estimatesFragment.setAppConfigList(appConfigList);
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
        homeViewModel.getSubForBusinessLiveData().observe(this, getSubForBusinessResponse -> {
            if (!getSubForBusinessResponse.getData().isEmpty()) {

                if (getSubForBusinessResponse.getData().get(0).getRemainingDays() == 0) {
                    AppPreferences.INSTANCE.saveStringToSharedPreferences(this, AppPreferences.APP_STATUS,
                            getSubForBusinessResponse.getData().get(0).getStatus());
                    DialogUtils.showCustomDialog(this, new DialogUtils.DialogListener() {
                        @Override
                        public void onPositiveButtonClick() {
                            Intent intent = new Intent(HomeActivity.this, SubscriptionActivity.class);
                            startActivity(intent);
                            BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();
                            if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
                                BusinessDetails businessDetails = businessDetailsResponse.getData();
                            }
                        }

                        @Override
                        public void onNegativeButtonClick() {

                        }
                    });
                }

                subscription = (ArrayList<SubscriptionForBusiness>) getSubForBusinessResponse.getData();
                profileFragment.setSubscription(subscription);
            }
        });

        homeViewModel.getBusinessDetailsLiveData().observe(this, businessDetailsResponse -> {
            if (businessDetailsResponse.getData() != null) {
                BusinessDetails businessDetails = businessDetailsResponse.getData();
                profileFragment.setBusinessdetails(businessDetails);
                ConfigDataProvider.INSTANCE.setBusinessDetailsResponse(businessDetailsResponse);
            }
        });
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);

        //UserAccess

        if (ConfigDataProvider.INSTANCE.getUserDetails() != null && hasUserAccess(ConfigDataProvider.INSTANCE.getUserDetails(), 3)) {
            showNoAccessPopup();
        }

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
                fetchData();
                if (isConnectedToInternet()) {

                } else {
                    showNoInternetDialog();
                }
                if (!estimateList.isEmpty()) {
//                    estimatesFragment.setEstimateList(estimateList);
                }
                if (!appConfigList.isEmpty()) {
                    estimatesFragment.setAppConfigList(appConfigList);
                }
            } else if (item.getItemId() == R.id.users) {

                homeScreenBinding.tvPageTitle.setText(R.string.title_clients);
                replaceFragment(clientFragment);
                if (isConnectedToInternet()) {
                    homeViewModel.getClientList();
                } else {
                    showNoInternetDialog();
                }
                if (!clientsList.isEmpty()) {
                    clientFragment.setClientList(clientsList);
                }

            } else {
                if (isConnectedToInternet()) {
                    homeViewModel.getBusinessDetails();
                } else {
                    showNoInternetDialog();
                }
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
                if (!subscription.isEmpty()) {
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

    private void showNoAccessPopup() {
        // Use AlertDialog or any other popup method to display a message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Access");
        builder.setMessage("You do not have access to this application.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Close the app or handle as needed
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private boolean hasUserAccess(UserDetailsResponse userDetailsResponse, int i) {
        if (userDetailsResponse.getData() != null && !userDetailsResponse.getData().isEmpty()) {
            UserData userData = userDetailsResponse.getData().get(0); // Assuming there is only one UserData in the list
            return userData.getUseraccess() != null && userData.getUseraccess() == i;
        }
        return false;
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