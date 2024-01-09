package com.dss.dsboxplus.estimates;

import static com.dss.dsboxplus.estimates.Formulas.getInstance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.CreateEstimateDataHolder;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.databinding.ActivityNewEstimateBinding;
import com.dss.dsboxplus.viewmodels.estimatesviewmodels.NewEstimatesActivityViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class NewEstimateActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    ActivityNewEstimateBinding newEstimateBinding;
    Spinner spinner, plySpinner;
    TextInputEditText tietnumberOfBox;
    TextInputLayout tilHeight, tilWidth, tilLength;
    Button btNext;
    String[] dimensions = {"mm", "cm", "inch"};
    String[] noOfPly = {"1Ply", "2Ply", "3Ply", "5Ply", "7Ply", "2Ply(KG)"};
    Double length = 0.0, width = 0.0, height = 0.0;
    Double lengthMm = 0.0, widthMm = 0.0, heightMm = 0.0;
    TextView tvMargin;
    private NewEstimatesActivityViewModel viewModel;
    private String configMargin = "0";
    private String configDecal = "0";
    private boolean isSheetDetailsTouched = false;
    private String sizeMeasure = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    private void initView() {
        newEstimateBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_estimate);
        tietnumberOfBox = findViewById(R.id.tietNumberOfBox);
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
        int defaultPlyPosition = 2; // Index of "3Ply" in the noOfPly array
        plySpinner.setSelection(defaultPlyPosition);

        // Number of box
        tietnumberOfBox.setText("1");
        tvMargin = findViewById(R.id.tvMargin);
        setDefaultSheetSizes();
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterBoxName = newEstimateBinding.tietEnterBoxName.getText().toString();
                String enterLength = newEstimateBinding.tietLength.getText().toString();
                String enterWidth = newEstimateBinding.tietWidth.getText().toString();
                String enterHeight = newEstimateBinding.tietHeight.getText().toString();
                boolean check = validateInfo(enterBoxName, enterLength, enterWidth, enterHeight);
                if (check) {
                    storeValuesToEstimateDataHolder();
                    String noOfPly = newEstimateBinding.spinnerNoOfPly.getSelectedItem().toString();
                    String updatedCuttingLength = newEstimateBinding.tietCuttingLength.getText().toString();
                    String updatedDecalSize = newEstimateBinding.tietDecalSize.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), PaperSpecificationActivity.class);
                    intent.putExtra("cuttingLength", updatedCuttingLength);
                    intent.putExtra("decalSize", updatedDecalSize);
                    intent.putExtra("noOfBox", newEstimateBinding.tietNumberOfBox.getText().toString());
                    intent.putExtra("noOfPly", noOfPly);
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
                    setDefaultSheetSizes();
                    length = Double.parseDouble(s.toString());
                    convertValue();
                    String s1 = configMargin;
                    String s2 = configDecal;
                    Double d1 = Double.parseDouble(s1);
                    Double d2 = Double.parseDouble(s2);
                    if (length != 0.0 && width != 0.0 && height != 0.0) {
                        double v = getInstance().cuttingLength(lengthMm, widthMm, heightMm, d1);
                        double resForCuttingTiet = getInstance().cuttingLengthInTiet(v);
                        String resOfCuttingTiet = String.format("%.2f", resForCuttingTiet);
                        double decalSize = getInstance().decalSize(widthMm, heightMm, d2);
                        double resForDecalTiet = getInstance().decalSizeInTiet(decalSize);
                        String resOfDecalTiet = String.format("%.2f", resForDecalTiet);
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
                    setDefaultSheetSizes();
                    width = Double.parseDouble(s.toString());
                    convertValue();
                    String s1 = configMargin;
                    String s2 = configDecal;
                    Double d1 = Double.parseDouble(s1);
                    Double d2 = Double.parseDouble(s2);
                    if (length != 0.0 && width != 0.0 && height != 0.0) {
                        double v = getInstance().cuttingLength(lengthMm, widthMm, heightMm, d1);
                        double resForCuttingTiet = getInstance().cuttingLengthInTiet(v);
                        String resOfCuttingTiet = String.format("%.2f", resForCuttingTiet);
                        double decalSize = getInstance().decalSize(widthMm, heightMm, d2);
                        double resForDecalTiet = getInstance().decalSizeInTiet(decalSize);
                        String resOfDecalTiet = String.format("%.2f", resForDecalTiet);
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
                    setDefaultSheetSizes();
                    height = Double.parseDouble(s.toString());
                    convertValue();
                    String s1 = configMargin;
                    String s2 = configDecal;
                    Double d1 = Double.parseDouble(s1);
                    Double d2 = Double.parseDouble(s2);
                    if (length != 0.0 && width != 0.0 && height != 0.0) {
                        double v = getInstance().cuttingLength(lengthMm, widthMm, heightMm, d1);
                        double resForCuttingTiet = getInstance().cuttingLengthInTiet(v);
                        String resOfCuttingTiet = String.format("%.2f", resForCuttingTiet);
                        double decalSize = getInstance().decalSize(widthMm, heightMm, d2);
                        double resForDecalTiet = getInstance().decalSizeInTiet(decalSize);
                        String resOfDecalTiet = String.format("%.2f", resForDecalTiet);
                        newEstimateBinding.tietCuttingLength.setText(resOfCuttingTiet);
                        newEstimateBinding.tietDecalSize.setText(resOfDecalTiet);
                        int cuttingLengthMm = (int) v;
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(cuttingLengthMm));
                        int DecalSizeMm = (int) decalSize;
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(DecalSizeMm));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newEstimateBinding.tietNumberOfBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String noOfBox = s.toString();
                if (noOfBox.isEmpty()) {
                    noOfBox = "1";
                }
                try {
                    int numberOfBoxes = Integer.parseInt(noOfBox);
                    double newDecalSize = calculateNewDecalSize(numberOfBoxes);
                    double newDecalSizeInTiet = getInstance().decalSizeInTiet(newDecalSize);
                    String newResOfDecalTiet = String.format("%.2f", newDecalSizeInTiet);
                    newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(newDecalSize));
                    newEstimateBinding.tietDecalSize.setText(newResOfDecalTiet);

                } catch (NumberFormatException e) {

                }
            }
        });
        newEstimateBinding.tietCuttingLength.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSheetDetailsTouched = true;
                return false;
            }
        });
        newEstimateBinding.tietDecalSize.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSheetDetailsTouched = true;
                return false;
            }
        });
        newEstimateBinding.tietCuttingLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    String tietCuttingLength = s.toString();
                    float tietCuttingLengthF = Float.parseFloat(tietCuttingLength);
                    float multiplication = (float) (tietCuttingLengthF * 25.4);
                    int result = Math.round(multiplication);
                    if (isSheetDetailsTouched) {
                        isSheetDetailsTouched = false;
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(result));
                        newEstimateBinding.tvMargin.setText(String.valueOf(result));
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newEstimateBinding.tietDecalSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    String tietDecalSize = s.toString();
                    float tietDecalSizeF = Float.parseFloat(tietDecalSize);
                    float multiplication = (float) (tietDecalSizeF * 25.4);
                    int result = Math.round(multiplication);
                    if (isSheetDetailsTouched) {
                        isSheetDetailsTouched = false;
                        newEstimateBinding.tvDecalMarginSize.setText(String.valueOf(result));
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(result));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void storeValuesToEstimateDataHolder() {
        CreateEstimateDataHolder.INSTANCE.setBoxName(newEstimateBinding.tietEnterBoxName.getText().toString());
        CreateEstimateDataHolder.INSTANCE.setBoxDimension(newEstimateBinding.spinner.getSelectedItem().toString());
        CreateEstimateDataHolder.INSTANCE.setLengthMm(lengthMm);
        CreateEstimateDataHolder.INSTANCE.setWidthMm(widthMm);
        CreateEstimateDataHolder.INSTANCE.setHeightMm(heightMm);
        switch (sizeMeasure) {
            case "cm":
                CreateEstimateDataHolder.INSTANCE.setLengthCm(Integer.parseInt(newEstimateBinding.tietLength.getText().toString()));
                CreateEstimateDataHolder.INSTANCE.setWidthCm(Integer.parseInt(newEstimateBinding.tietWidth.getText().toString()));
                CreateEstimateDataHolder.INSTANCE.setHeightCm(Integer.parseInt(newEstimateBinding.tietHeight.getText().toString()));
                break;
            case "inch":
                CreateEstimateDataHolder.INSTANCE.setLengthInch(Integer.parseInt(newEstimateBinding.tietLength.getText().toString()));
                CreateEstimateDataHolder.INSTANCE.setWidthInch(Integer.parseInt(newEstimateBinding.tietWidth.getText().toString()));
                CreateEstimateDataHolder.INSTANCE.setHeightInch(Integer.parseInt(newEstimateBinding.tietHeight.getText().toString()));
                break;
        }
        CreateEstimateDataHolder.INSTANCE.setNoOfPly(String.valueOf(newEstimateBinding.spinnerNoOfPly.getSelectedItem().toString().charAt(0)));
        CreateEstimateDataHolder.INSTANCE.setNoOfBox(Integer.parseInt(newEstimateBinding.tietNumberOfBox.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setCuttingLength(Double.parseDouble(newEstimateBinding.tietCuttingLength.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setDecalSize(Double.parseDouble(newEstimateBinding.tietDecalSize.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setCuttingMarginMm(Integer.parseInt(newEstimateBinding.tvMargin.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setDecalMarginMm(Integer.parseInt(newEstimateBinding.tvDecalMarginSize.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setCuttingLengthMm(Integer.parseInt(newEstimateBinding.tvCuttingLengthSize.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setDecalSizeMm(Integer.parseInt(newEstimateBinding.tvDecalSizeValue.getText().toString()));
    }

    private void setDefaultSheetSizes() {
        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
        if (appConfigResponse.getData() != null) {
            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                int configId = appConfigDataItem.getConfigid();
                String configValue = appConfigDataItem.getConfigvalue();
                if (configId == 10) {
                    this.configMargin = configValue;
                    newEstimateBinding.tvMargin.setText(configValue);
                } else if (configId == 11) {
                    this.configDecal = configValue;
                    newEstimateBinding.tvDecalMarginSize.setText(configValue);
                }
            }
        }
    }

    private double calculateNewDecalSize(int numberOfBoxes) {
        String decalMarginSize = newEstimateBinding.tvDecalMarginSize.getText().toString();
        double decalMarginSizeDouble = Double.parseDouble(decalMarginSize);
        double newResult = (width + height) * numberOfBoxes + decalMarginSizeDouble;
        return newResult;
    }

    private void convertValue() {
        String selectedUnit = spinner.getSelectedItem().toString();

        switch (selectedUnit) {
            case "mm":
                lengthMm = length;
                widthMm = width;
                heightMm = height;
                break;
            case "cm":
                lengthMm = length * 10;
                widthMm = width * 10;
                heightMm = height * 10;
                break;
            case "inch":
                lengthMm = length * 2.54 * 10;
                widthMm = width * 2.54 * 10;
                heightMm = height * 2.54 * 10;
                break;
        }
    }

    private boolean validateInfo(String enterBoxName, String enterLength, String enterWidth, String enterHeight) {
        if (enterBoxName.isEmpty()) {
            newEstimateBinding.tietEnterBoxName.requestFocus();
            newEstimateBinding.tietEnterBoxName.setError("Please Enter Box Name");
            return false;
        } else if (enterLength.isEmpty()) {
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
        } else {
            return true;
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView.getId() == R.id.spinner) {
            String size = spinner.getItemAtPosition(i).toString();
            sizeMeasure = size;
            tilHeight.setHint(size);
            tilLength.setHint(size);
            tilWidth.setHint(size);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}