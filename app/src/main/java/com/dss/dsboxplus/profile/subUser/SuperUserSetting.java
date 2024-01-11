package com.dss.dsboxplus.profile.subUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.repo.response.SubUser;
import com.dss.dsboxplus.databinding.ActivitySuperUserSettingBinding;
import com.dss.dsboxplus.recyclerview.SubUserViewAdapter;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.profileviewmodels.SuperUserViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;

public class SuperUserSetting extends BaseActivity implements SubUserViewAdapter.OnItemClickListener {
    ActivitySuperUserSettingBinding superUserSettingBinding;
    private SubUserViewAdapter adapter;
    private SuperUserViewModel viewModel;
    private ArrayList<SubUser> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        superUserSettingBinding = DataBindingUtil.setContentView(this, R.layout.activity_super_user_setting);
        initView();
        initObservable();
        fetchData();
    }

    private void initObservable() {
        viewModel.getUsersByBusinessLiveData().observe(this, userDetailsResponse -> {
            if (!userDetailsResponse.getData().isEmpty()) {
                userList = (ArrayList<SubUser>) userDetailsResponse.getData();
                adapter.updateUserList(userList);
            }
        });
        userList = new ArrayList<>();
        adapter = new SubUserViewAdapter(userList);
        adapter.setOnItemClickListener(this);
        superUserSettingBinding.rvRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void fetchData() {
        viewModel.getUserList();
    }

    private void initView() {

        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(SuperUserViewModel.class);


        superUserSettingBinding.fabAddSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperUserSetting.this, AddSubUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(SubUser subUser) {
        // Handle item click here
        Intent intent = new Intent(SuperUserSetting.this, SubUserDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("USERS", subUser);
        intent.putExtra("USERS_BUNDLE", bundle);
        startActivity(intent);
    }

}