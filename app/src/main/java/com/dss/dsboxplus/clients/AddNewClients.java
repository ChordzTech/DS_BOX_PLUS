package com.dss.dsboxplus.clients;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityAddNewClientsBinding;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.recyclerview.ClientsViewAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class AddNewClients extends AppCompatActivity {
    ClientsViewAdapter clientsViewAdapter;
    TextInputEditText tietClientName, tietContactNumber, tietAddress;
    MaterialButton btSubmitInNewClientsDetails;
    ActivityAddNewClientsBinding addNewClientsBinding;
    private ArrayList<ClientsDataModel> clientsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_clients);
        tietClientName = findViewById(R.id.tietClientName);
        tietContactNumber = findViewById(R.id.tietContactNumber);
        tietAddress = findViewById(R.id.tietAddress);
        btSubmitInNewClientsDetails = findViewById(R.id.btSubmitInNewClientsDetails);
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
}