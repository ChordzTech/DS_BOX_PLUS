package com.dss.dsboxplus.clients;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.ClientListResponse;
import com.dss.dsboxplus.databinding.ActivityClientDetailsBinding;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ClientDetailsActivity extends BaseActivity {
    ActivityClientDetailsBinding clientDetailsBinding;
    MaterialButton btCloseInClientDetails;
    //    private ClientsDataModel clientsDataModel;
    private ArrayList<ClientsDataModel> clientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_client_details);
        initView();
//        if (getIntent().getExtras().getParcelable("CLIENTS_BUNDLE") != null) {
//            clientsDataModel = getIntent().getExtras().getBundle("CLIENTS_BUNDLE").getParcelable("CLIENTS");
//        }

    }

    private void initView() {

        Intent intent = getIntent();
        // Check if the intent has the CLIENTS_BUNDLE extra
        if (intent.hasExtra("CLIENTS_BUNDLE")) {
            // Retrieve the bundle from the intent
            Bundle bundle = intent.getBundleExtra("CLIENTS_BUNDLE");

            // Check if the bundle is not null
            if (bundle != null) {
                Client client = bundle.getParcelable("CLIENTS");

                if (client != null) {
                    clientDetailsBinding.tietClientNameInClientdetails.setText(client.getClientname());
                    clientDetailsBinding.tietClientContactNoInClientdetails.setText(client.getMobileno());
                    clientDetailsBinding.tietClientAddressInClientdetails.setText(client.getAddress());

                }
            }
        }

        btCloseInClientDetails = findViewById(R.id.btCloseInClientDetails);
        btCloseInClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}