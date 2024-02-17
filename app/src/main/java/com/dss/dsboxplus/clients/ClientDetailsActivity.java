package com.dss.dsboxplus.clients;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.databinding.ActivityClientDetailsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.clientsviewmodels.ClientViewModel;
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
        )) {
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
                finish();
            }
        });
        clientDetailsBinding.btDeleteInClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteClient(client.getClientid());
            }
        });
        viewModel.getDeleteClientRequestLiveData().observe(this, deleteClientResponse -> {
            Toast.makeText(this, "Client Deleted Successfully", Toast.LENGTH_SHORT).show();
            finishAffinity();
            startActivity(new Intent(ClientDetailsActivity.this, HomeActivity.class));
        });
        clientDetailsBinding.btUpdateInClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.updateClient(client.getClientid(),
                        clientDetailsBinding.tietClientNameInClientdetails.getText().toString(),
                        clientDetailsBinding.tietClientContactNoInClientdetails.getText().toString(),
                        clientDetailsBinding.tietClientAddressInClientdetails.getText().toString()
                );
            }
        });
        viewModel.getUpdateClientRequestLiveData().observe(this, updateClientResponse -> {
            Toast.makeText(this, "Client Details Updated Successfully", Toast.LENGTH_SHORT).show();
            finishAffinity();
            startActivity(new Intent(ClientDetailsActivity.this, HomeActivity.class));
        });
    }
}