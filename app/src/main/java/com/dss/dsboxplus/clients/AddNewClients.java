package com.dss.dsboxplus.clients;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.databinding.ActivityAddNewClientsBinding;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.recyclerview.ClientsViewAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class AddNewClients extends BaseActivity {
    ClientsViewAdapter clientsViewAdapter;
    TextInputEditText tietClientName, tietContactNumber, tietAddress;
    MaterialButton btSubmitInNewClientsDetails;
    ActivityAddNewClientsBinding addNewClientsBinding;
    private ArrayList<ClientsDataModel> clientsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNewClientsBinding=DataBindingUtil.setContentView(this,R.layout.activity_add_new_clients);
        tietClientName = findViewById(R.id.tietClientName);
        tietContactNumber = findViewById(R.id.tietContactNumber);
        tietAddress = findViewById(R.id.tietAddress);
        btSubmitInNewClientsDetails = findViewById(R.id.btSubmitInNewClientsDetails);
        addNewClientsBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddNewClients.this)
                        .cropSquare()
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .start();
            }
        });
        btSubmitInNewClientsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String clientName = addNewClientsBinding.tietClientName.getText().toString();
//                String clientContactNo = addNewClientsBinding.tietContactNumber.getText().toString();
//                String clientAddress = addNewClientsBinding.tietAddress.getText().toString();
//                addClient();
//                boolean check=validateInfo(clientName,clientContactNo,clientAddress);
//                if (check==true){
//                    finish();
//                }
            }
        });

    }

    private boolean validateInfo(String clientName, String clientContactNo, String clientAddress) {
        if (clientName.isEmpty()){
            addNewClientsBinding.tietClientName.requestFocus();
            addNewClientsBinding.tietClientName.setError("Enter Client Name");
            return false;

        } else if (clientContactNo.isEmpty()) {
            addNewClientsBinding.tietContactNumber.requestFocus();
            addNewClientsBinding.tietContactNumber.setError("Enter Contact Number");
            return false;
        }else if (clientAddress.isEmpty()){
            addNewClientsBinding.tietAddress.requestFocus();
            addNewClientsBinding.tietAddress.setError("Enter Address");
            return false;
        }
        return true;
    }

    private void addClient() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        addNewClientsBinding.ivNewClients.setImageURI(uri);
    }
}