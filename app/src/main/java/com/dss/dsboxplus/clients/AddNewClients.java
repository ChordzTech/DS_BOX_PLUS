package com.dss.dsboxplus.clients;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.recyclerview.ClientsViewAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class AddNewClients extends AppCompatActivity {
    ClientsViewAdapter clientsViewAdapter;
    TextInputEditText tietClientName, tietContactNumber, tietAddress;
    MaterialButton btSubmitInNewClientsDetails;

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
//                addClient();
            }
        });
    }

    private void addClient() {

    }
}