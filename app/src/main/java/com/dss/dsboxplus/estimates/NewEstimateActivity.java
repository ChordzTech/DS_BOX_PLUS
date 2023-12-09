package com.dss.dsboxplus.estimates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityNewEstimateBinding;
import com.dss.dsboxplus.profile.DefaultPaperSettings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NewEstimateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityNewEstimateBinding newEstimateBinding;
    DefaultPaperSettings defaultPaperSettings;
    Spinner spinner, plySpinner;
    TextInputEditText tietnumberOfBox;
    TextInputLayout tilHeight,tilWidth,tilLength;
    Button btNext;
    String[] dimensions = {"mm", "cm", "inch"};
    String[] noOfPly = {"1Ply", "2Ply", "3Ply", "5Ply", "7Ply","2Ply(KG)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newEstimateBinding= DataBindingUtil.setContentView(this,R.layout.activity_new_estimate);
        tietnumberOfBox=findViewById(R.id.tietnumberOfBox);
        tilHeight=findViewById(R.id.tilHeight);
        tilWidth=findViewById(R.id.tilWidth);
        tilLength=findViewById(R.id.tilLength);
        btNext=findViewById(R.id.btNextInNewFragment);
        spinner = findViewById(R.id.spinner);
        plySpinner = findViewById(R.id.spinnerNoOfPly);
        //Dimensions
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dimensions);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Number of Ply
        plySpinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noOfPly);
        adapter1.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        plySpinner.setAdapter(adapter1);

        // Number of box
        tietnumberOfBox.setText("1");
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),PaperSpecificationActivity.class);
                startActivity(intent);
            }
        });
        newEstimateBinding.tietCuttingLength.setText("30");
        newEstimateBinding.tietDecalSize.setText("20");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId()==R.id.spinner){
            String  size=spinner.getItemAtPosition(i).toString();
            tilHeight.setHint(size);
            tilLength.setHint(size);
            tilWidth.setHint(size);

        } else if (adapterView.getId()==R.id.spinnerNoOfPly) {

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}