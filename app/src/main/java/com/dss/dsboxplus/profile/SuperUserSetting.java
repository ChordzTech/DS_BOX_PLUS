package com.dss.dsboxplus.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.databinding.ActivitySuperUserSettingBinding;
import com.dss.dsboxplus.model.AddSubNewUserDataModel;
import com.dss.dsboxplus.profile.subUser.AddSubUserActivity;
import com.dss.dsboxplus.recyclerview.SubUserViewAdapter;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.profileviewmodels.SuperUserViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;

public class SuperUserSetting extends BaseActivity {
    private static final int REQUEST_CODE_SECOND_ACTIVITY = 1001;
    ActivitySuperUserSettingBinding superUserSettingBinding;
    private ArrayList<AddSubNewUserDataModel> dataList = new ArrayList<>();
    private SubUserViewAdapter adapter;
    private SuperUserViewModel viewModel;
    private ArrayList<UserData> userList = new ArrayList<>();

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
                userList = (ArrayList<UserData>) userDetailsResponse.getData();
            }
        });
    }

    private void fetchData() {
        viewModel.getUserList();
    }

    private void initView() {

        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(SuperUserViewModel.class);


//        clientsList = new ArrayList<>();
//        adapter = new SubUserViewAdapter(clientsList);
//        superUserSettingBinding.rvRecyclerView.setAdapter(adapter);

        userList=new ArrayList<>();
        adapter=new SubUserViewAdapter(userList);
        superUserSettingBinding.rvRecyclerView.setAdapter(adapter);

        superUserSettingBinding.fabAddSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperUserSetting.this, AddSubUserActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY && resultCode == RESULT_OK && data != null) {
            String username = data.getStringExtra("username");
            String contact = data.getStringExtra("contact");


            AddSubNewUserDataModel newUserDataModel = new AddSubNewUserDataModel(username, contact);
            dataList.add(newUserDataModel);
            adapter.notifyDataSetChanged();
        }
    }
}