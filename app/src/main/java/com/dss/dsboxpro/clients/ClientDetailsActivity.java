package com.dss.dsboxpro.clients;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxpro.R;
import com.dss.dsboxpro.baseview.BaseActivity;
import com.dss.dsboxpro.data.configdata.ConfigDataProvider;
import com.dss.dsboxpro.data.repo.response.Client;
import com.dss.dsboxpro.databinding.ActivityClientDetailsBinding;
import com.dss.dsboxpro.home.HomeActivity;
import com.dss.dsboxpro.model.ClientsDataModel;
import com.dss.dsboxpro.preferences.AppPreferences;
import com.dss.dsboxpro.viewmodels.AppViewModelFactory;
import com.dss.dsboxpro.viewmodels.clientsviewmodels.ClientViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ClientDetailsActivity extends BaseActivity {
    ActivityClientDetailsBinding clientDetailsBinding;
    MaterialButton btCloseInClientDetails;
    private ArrayList<ClientsDataModel> clientsList;
    private ClientViewModel viewModel;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_client_details);
        initView();


    }

    @Override
    public void onResume() {
        super.onResume();

        if (AppPreferences.INSTANCE.getStringValueFromSharedPreferences(AppPreferences.APP_STATUS).equalsIgnoreCase(
                "Expired"
        ) ||
                (ConfigDataProvider.INSTANCE.getUserDetails() != null &&
                        ConfigDataProvider.INSTANCE.getUserDetails().getData() != null &&
                        ConfigDataProvider.INSTANCE.getUserDetails().getData().get(0).getUseraccess() == 1)) {
            clientDetailsBinding.btUpdateInClientDetails.setVisibility(View.GONE);
            clientDetailsBinding.btDeleteInClientDetails.setVisibility(View.GONE);
        } else {
            clientDetailsBinding.btUpdateInClientDetails.setVisibility(View.VISIBLE);
            clientDetailsBinding.btDeleteInClientDetails.setVisibility(View.VISIBLE);
        }
    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(ClientViewModel.class);
    }

    private void initView() {
        initViewModel();
        Intent intent = getIntent();
        // Check if the intent has the CLIENTS_BUNDLE extra
        if (intent.hasExtra("CLIENTS_BUNDLE")) {
            // Retrieve the bundle from the intent
            Bundle bundle = intent.getBundleExtra("CLIENTS_BUNDLE");

            // Check if the bundle is not null
            if (bundle != null) {
                client = bundle.getParcelable("CLIENTS");

                if (client != null) {
                    clientDetailsBinding.tietClientNameInClientdetails.setText(client.getClientname());
                    clientDetailsBinding.tietClientContactNoInClientdetails.setText(client.getMobileno());
                    clientDetailsBinding.tietClientAddressInClientdetails.setText(client.getAddress());

                }
            }
        }

        clientDetailsBinding.btCloseInClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        clientDetailsBinding.btDeleteInClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet()) {
                    viewModel.deleteClient(client.getClientid(), ClientDetailsActivity.this);
                } else {
                    showNoInternetDialog();
                }
            }
        });
        viewModel.getDeleteClientRequestLiveData().observe(this, deleteClientResponse -> {
            Toast.makeText(this, deleteClientResponse.getMessage(), Toast.LENGTH_SHORT).show();
            finishAffinity();
            startActivity(new Intent(ClientDetailsActivity.this, HomeActivity.class));

        });
        clientDetailsBinding.btUpdateInClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet()) {
                    viewModel.updateClient(client.getClientid(),
                            clientDetailsBinding.tietClientNameInClientdetails.getText().toString(),
                            clientDetailsBinding.tietClientContactNoInClientdetails.getText().toString(),
                            clientDetailsBinding.tietClientAddressInClientdetails.getText().toString()
                    );
                } else {
                    showNoInternetDialog();
                }

            }
        });
        viewModel.getUpdateClientRequestLiveData().observe(this, updateClientResponse -> {
            Toast.makeText(this, "Client Details Updated Successfully", Toast.LENGTH_SHORT).show();
            finishAffinity();
            startActivity(new Intent(ClientDetailsActivity.this, HomeActivity.class));

        });
    }
}