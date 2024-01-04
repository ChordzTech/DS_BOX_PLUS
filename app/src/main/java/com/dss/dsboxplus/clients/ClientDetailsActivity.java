package com.dss.dsboxplus.clients;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.ClientListResponse;
import com.dss.dsboxplus.databinding.ActivityClientDetailsBinding;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.clientsviewmodels.ClientViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

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
        initViewModel();

    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(ClientViewModel.class);
    }

    private void initView() {

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

            }
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
    }
}