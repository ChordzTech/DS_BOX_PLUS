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


public class HomeActivity extends AppCompatActivity implements IHomeActivityCallBack {
    ActivityHomeScreenBinding homeScreenBinding;
    private RetrofitService retrofitService;
    private MainRepository mainRepository;
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
    }

    private void initObservables() {
        homeViewModel.getEstimateListLiveData().observe(this, estimateListResponse -> {
            Log.e("TAG", "estimateListResponse: "+estimateListResponse.getData().size() );
        });
    }

    private void initView() {
        retrofitService = RetrofitService.Companion.getInstance();
        mainRepository = new MainRepository(retrofitService);
        homeViewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(HomeViewModel.class);

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