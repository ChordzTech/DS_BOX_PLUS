package com.dss.dsboxplus.estimates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dss.dsboxplus.R;

public class NewEstimateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] typeOfPlys={"1 Ply","2 Ply","3 Ply","5 Ply","7Ply"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_estimate);
        Spinner spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,typeOfPlys);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}