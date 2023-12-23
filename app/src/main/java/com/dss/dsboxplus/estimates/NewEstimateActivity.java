package com.dss.dsboxplus.estimates;

import static com.dss.dsboxplus.estimates.Formulas.getInstance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityNewEstimateBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NewEstimateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityNewEstimateBinding newEstimateBinding;
    Spinner spinner, plySpinner;
    TextInputEditText tietnumberOfBox;
    TextInputLayout tilHeight, tilWidth, tilLength;
    Button btNext;
    String[] dimensions = {"mm", "cm", "inch"};
    String[] noOfPly = {"1Ply", "2Ply", "3Ply", "5Ply", "7Ply", "2Ply(KG)"};
    Double length = 0.0, width = 0.0, height = 0.0;
    TextView tvMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newEstimateBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_estimate);
        tietnumberOfBox = findViewById(R.id.tietnumberOfBox);
        tilHeight = findViewById(R.id.tilHeight);
        tilWidth = findViewById(R.id.tilWidth);
        tilLength = findViewById(R.id.tilLength);
        btNext = findViewById(R.id.btNextInNewFragment);
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
        tvMargin = findViewById(R.id.tvMargin);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enterBoxName=newEstimateBinding.tietEnterBoxName.getText().toString();
                String enterLength=newEstimateBinding.tietLength.getText().toString();
                String enterWidth=newEstimateBinding.tietWidth.getText().toString();
                String enterHeight=newEstimateBinding.tietHeight.getText().toString();
                boolean check=validateInfo(enterBoxName,enterLength,enterWidth,enterHeight);
                if (check==true){
                    String noOfPly=newEstimateBinding.spinnerNoOfPly.getSelectedItem().toString();
                    Intent intent = new Intent(getApplicationContext(), PaperSpecificationActivity.class);
                    intent.putExtra("cuttingLength", newEstimateBinding.tietCuttingLength.getText().toString());
                    intent.putExtra("decalSize", newEstimateBinding.tietDecalSize.getText().toString());
                    intent.putExtra("noOfPly",noOfPly);
                    startActivity(intent);
                }
            }
        });
        newEstimateBinding.tietLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("TAG", "beforeTextChanged: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e("TAG", "onTextChanged: " + s);
                if (!s.toString().isEmpty()) {
                    length = Double.parseDouble(s.toString());
                    String s1 = tvMargin.getText().toString();
                    String s2 = newEstimateBinding.tvDecalMarginSize.getText().toString();
                    Double d1 = Double.parseDouble(s1);
                    Double d2 = Double.parseDouble(s2);
                    if (length != 0.0 && width != 0.0 && height != 0.0) {
                        double v = getInstance().cuttingLength(length, width, height, d1);
                        double resForCuttingTiet=getInstance().cuttingLengthInTiet(v);
                        String resOfCuttingTiet=String.format("%.2f",resForCuttingTiet);
                        double decalSize = getInstance().decalSize(width, height, d2);
                        double resForDecalTiet=getInstance().decalSizeInTiet(decalSize);
                        String resOfDecalTiet=String.format("%.2f",resForDecalTiet);
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(v));
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(decalSize));
                        newEstimateBinding.tietCuttingLength.setText(resOfCuttingTiet);
                        newEstimateBinding.tietDecalSize.setText(resOfDecalTiet);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TAG", "afterTextChanged: " + s.toString());
            }
        });
        newEstimateBinding.tietWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    width = Double.parseDouble(s.toString());
                    String s1 = tvMargin.getText().toString();
                    String s2 = newEstimateBinding.tvDecalMarginSize.getText().toString();
                    Double d1 = Double.parseDouble(s1);
                    Double d2 = Double.parseDouble(s2);
                    if (length != 0.0 && width != 0.0 && height != 0.0) {
                        double v = getInstance().cuttingLength(length, width, height, d1);
                        double resForCuttingTiet=getInstance().cuttingLengthInTiet(v);
                        String resOfCuttingTiet=String.format("%.2f",resForCuttingTiet);
                        double decalSize = getInstance().decalSize(width, height, d2);
                        double resForDecalTiet=getInstance().decalSizeInTiet(decalSize);
                        String resOfDecalTiet=String.format("%.2f",resForDecalTiet);
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(v));
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(decalSize));
                        newEstimateBinding.tietCuttingLength.setText(resOfCuttingTiet);
                        newEstimateBinding.tietDecalSize.setText(resOfDecalTiet);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        newEstimateBinding.tietHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    height = Double.parseDouble(s.toString());
                    String s1 = tvMargin.getText().toString();
                    String s2 = newEstimateBinding.tvDecalMarginSize.getText().toString();
                    Double d1 = Double.parseDouble(s1);
                    Double d2 = Double.parseDouble(s2);
                    if (length != 0.0 && width != 0.0 && height != 0.0) {
                        double v = getInstance().cuttingLength(length, width, height, d1);
                        double resForCuttingTiet=getInstance().cuttingLengthInTiet(v);
                        String resOfCuttingTiet=String.format("%.2f",resForCuttingTiet);
                        double decalSize = getInstance().decalSize(width, height, d2);
                        double resForDecalTiet=getInstance().decalSizeInTiet(decalSize);
                        String resOfDecalTiet=String.format("%.2f",resForDecalTiet);
                        newEstimateBinding.tietCuttingLength.setText(resOfCuttingTiet);
                        newEstimateBinding.tietDecalSize.setText(resOfDecalTiet);
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(v));
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(decalSize));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    private boolean validateInfo(String enterBoxName, String enterLength, String enterWidth, String enterHeight) {
        if (enterBoxName.isEmpty()){
            newEstimateBinding.tietEnterBoxName.requestFocus();
            newEstimateBinding.tietEnterBoxName.setError("Please Enter Box Name");
            return false;
        }else if (enterLength.isEmpty()){
            newEstimateBinding.tietLength.requestFocus();
            newEstimateBinding.tietLength.setError("Enter Length");
            return false;
        } else if (enterWidth.isEmpty()) {
            newEstimateBinding.tietWidth.requestFocus();
            newEstimateBinding.tietWidth.setError("Enter Width");
            return false;
        } else if (enterHeight.isEmpty()) {
            newEstimateBinding.tietHeight.requestFocus();
            newEstimateBinding.tietHeight.setError("Enter Height");
            return false;
        }else {
            return true;
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.spinner) {
            String size = spinner.getItemAtPosition(i).toString();
            tilHeight.setHint(size);
            tilLength.setHint(size);
            tilWidth.setHint(size);

        } else if (adapterView.getId() == R.id.spinnerNoOfPly) {

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}