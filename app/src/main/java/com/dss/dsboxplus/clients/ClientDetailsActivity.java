package com.dss.dsboxplus.clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityClientDetailsBinding;
import com.dss.dsboxplus.model.ClientsDataModel;

public class ClientDetailsActivity extends AppCompatActivity {
    ActivityClientDetailsBinding clientDetailsBinding;
    private ClientsDataModel clientsDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_client_details);
        if (getIntent().getExtras().getParcelable("CLIENTS_BUNDLE")!=null){
            clientsDataModel=getIntent().getExtras().getBundle("CLIENTS_BUNDLE").getParcelable("CLIENTS");
        }
    }
}