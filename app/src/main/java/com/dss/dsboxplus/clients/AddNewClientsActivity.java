package com.dss.dsboxplus.clients;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.databinding.ActivityAddNewClientsBinding;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.recyclerview.ClientsViewAdapter;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.clientsviewmodels.ClientViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class AddNewClientsActivity extends BaseActivity {
    ClientsViewAdapter clientsViewAdapter;
    TextInputEditText tietClientName, tietContactNumber, tietAddress;
    MaterialButton btSubmitInNewClientsDetails;
    ActivityAddNewClientsBinding addNewClientsBinding;
    private ArrayList<ClientsDataModel> clientsList = new ArrayList<>();
    private ClientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNewClientsBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_clients);
        intiView();
        initViewModel();
        addOververs();
    }

    private void intiView() {
        tietClientName = findViewById(R.id.tietClientName);
        tietContactNumber = findViewById(R.id.tietContactNumber);
        tietAddress = findViewById(R.id.tietAddress);
        btSubmitInNewClientsDetails = findViewById(R.id.btSubmitInNewClientsDetails);

        btSubmitInNewClientsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientName = addNewClientsBinding.tietClientName.getText().toString();
                String clientContactNo = addNewClientsBinding.tietContactNumber.getText().toString();
                String clientAddress = addNewClientsBinding.tietAddress.getText().toString();
                boolean check = validateInfo(clientName, clientContactNo, clientAddress);
                if (check == true) {
                    viewModel.addClient(clientName, clientContactNo, clientAddress);
                }
            }
        });
    }

    private void addOververs() {
        viewModel.getAddClientRequestLiveData().observe(this, clientListResponse -> {
            Toast.makeText(this, "Client added Successfully", Toast.LENGTH_SHORT);
            finishAffinity();
            startActivity(new Intent(AddNewClientsActivity.this, HomeActivity.class));
        });
        viewModel.getRequesrFailedLiveData().observe(this, clientListResponse -> {
            Toast.makeText(this, clientListResponse.getMessage(), Toast.LENGTH_SHORT);
            finish();
        });
        viewModel.getUpdateClientRequestLiveData().observe(this,updateClientResponse -> {
            Toast.makeText(this, updateClientResponse.getMessage(), Toast.LENGTH_SHORT);
            finish();
        });
    }

    private void initViewModel() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(ClientViewModel.class);
    }

    private boolean validateInfo(String clientName, String clientContactNo, String clientAddress) {
        if (clientName.isEmpty()) {
            addNewClientsBinding.tietClientName.requestFocus();
            addNewClientsBinding.tietClientName.setError("Enter Client Name");
            return false;

        } else if (clientContactNo.isEmpty()) {
            addNewClientsBinding.tietContactNumber.requestFocus();
            addNewClientsBinding.tietContactNumber.setError("Enter Contact Number");
            return false;
        } else if (clientAddress.isEmpty()) {
            addNewClientsBinding.tietAddress.requestFocus();
            addNewClientsBinding.tietAddress.setError("Enter Address");
            return false;
        }
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Uri uri = data.getData();
//        addNewClientsBinding.ivNewClients.setImageURI(uri);
//    }
}