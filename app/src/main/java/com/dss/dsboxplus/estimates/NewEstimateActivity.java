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
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.CreateEstimateDataHolder;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.ClientDetails;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityNewEstimateBinding;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.estimatesviewmodels.NewEstimatesActivityViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

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
    private DataItem dataItem;
    private ClientDetails selectedClient;
    private Client client;
    private Long businessId = 0L;
    private Long clientId = 0L;

    private boolean isNewEstimate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newEstimateBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_estimate);

        dataItem = getIntent().getParcelableExtra("EDIT_ESTIMATE");
        selectedClient = getIntent().getParcelableExtra("CLIENT_DETAILS");
        initView();
        if (dataItem != null && selectedClient != null) {
            isNewEstimate = false;
            updateUI();
            businessId = dataItem.getBusinessid();
            clientId = dataItem.getClientid();
        } else {
            isNewEstimate = true;
            client = getIntent().getParcelableExtra("selectedClient");
            businessId = Long.valueOf(client.getBusinessid());
            clientId = client.getClientid();
        }
        fetchData();

    }

    private void updateUI() {
        CreateEstimateDataHolder.INSTANCE.setClientName(selectedClient.getClientname());
        CreateEstimateDataHolder.INSTANCE.setBoxName(dataItem.getBoxname());
        CreateEstimateDataHolder.INSTANCE.setLengthMm((int) dataItem.getLengthMmField().doubleValue());
        CreateEstimateDataHolder.INSTANCE.setWidthMm((int) dataItem.getWidthMmField().doubleValue());
        CreateEstimateDataHolder.INSTANCE.setHeightMm((int) dataItem.getHeightMmField().doubleValue());
//        CreateEstimateDataHolder.INSTANCE.setBoxDimension(newEstimateBinding.spinner.getSelectedItem().toString());


        CreateEstimateDataHolder.INSTANCE.setNoOfBox((int) dataItem.getUps().doubleValue());
        CreateEstimateDataHolder.INSTANCE.setCuttingLength((Double) dataItem.getCuttinglength());
        CreateEstimateDataHolder.INSTANCE.setDecalSize((Double) dataItem.getDecalsize());

        newEstimateBinding.tvClientNameInEstimate.setText(selectedClient.getClientname());
        newEstimateBinding.tietEnterBoxName.setText(dataItem.getBoxname());
        newEstimateBinding.tietLength.setText(String.valueOf((int) dataItem.getLengthMmField().doubleValue()));
        newEstimateBinding.tietWidth.setText(String.valueOf((int) dataItem.getWidthMmField().doubleValue()));
        newEstimateBinding.tietHeight.setText(String.valueOf((int) dataItem.getHeightMmField().doubleValue()));
        List<String> asList = Arrays.asList(noOfPly);
        for (int i = 0; i < asList.size(); i++) {
            String s = asList.get(i);
            s = s.replace("Ply", "");
            s = s.replace("(KG)", "");
            if ((dataItem.getLengthMmField() == 0 && dataItem.getWidthMmField() == 0 && dataItem.getHeightMmField() == 0)
                    && Integer.parseInt(s) == dataItem.getPly()) {
                newEstimateBinding.spinnerNoOfPly.setSelection(5);
                break;
            }
            if (Integer.parseInt(s) == dataItem.getPly()) {
                newEstimateBinding.spinnerNoOfPly.setSelection(i);
                break;
            }
        }

        switch (sizeMeasure) {
            case "cm":
                newEstimateBinding.tietLength.setText(String.valueOf((int) dataItem.getLengthCmField()));
                newEstimateBinding.tietWidth.setText(String.valueOf((int) dataItem.getWidthCmField()));
                newEstimateBinding.tietHeight.setText(String.valueOf((int) dataItem.getHeightCmField()));

                CreateEstimateDataHolder.INSTANCE.setLengthCm(Integer.parseInt(dataItem.getLengthCmField().toString()));
                CreateEstimateDataHolder.INSTANCE.setWidthCm(Integer.parseInt(dataItem.getWidthCmField().toString()));
                CreateEstimateDataHolder.INSTANCE.setHeightCm(Integer.parseInt(dataItem.getHeightCmField().toString()));
                break;
            case "inch":
                newEstimateBinding.tietLength.setText(String.valueOf((int) dataItem.getLengthInchField()));
                newEstimateBinding.tietWidth.setText(String.valueOf((int) dataItem.getWidthInchField()));
                newEstimateBinding.tietHeight.setText(String.valueOf((int) dataItem.getHeightInchField()));

                CreateEstimateDataHolder.INSTANCE.setLengthCm(Float.parseFloat(dataItem.getLengthInchField().toString()));
                CreateEstimateDataHolder.INSTANCE.setLengthCm(Float.parseFloat(dataItem.getWidthInchField().toString()));
                CreateEstimateDataHolder.INSTANCE.setLengthCm(Float.parseFloat(dataItem.getHeightInchField().toString()));


                break;
        }
//        CreateEstimateDataHolder.INSTANCE.setNoOfPly(String.valueOf(newEstimateBinding.spinnerNoOfPly.getSelectedItem().toString().charAt(0)));
        CreateEstimateDataHolder.INSTANCE.setCuttingLength(Double.parseDouble(dataItem.getCuttinglength().toString()));
        CreateEstimateDataHolder.INSTANCE.setDecalSize(Double.parseDouble(dataItem.getDecalsize().toString()));
//        CreateEstimateDataHolder.INSTANCE.setCuttingMarginMm((String.valueOf((int)dataItem.getCuttinglengthmargin())));
//        CreateEstimateDataHolder.INSTANCE.setDecalMarginMm((String.valueOf(dataItem.getDecalsizemargin())));
        newEstimateBinding.tietNumberOfBox.setText(String.valueOf((int) dataItem.getUps().doubleValue()));
        newEstimateBinding.tietCuttingLength.setText(dataItem.getCuttinglength().toString());
        newEstimateBinding.tietDecalSize.setText(dataItem.getDecalsize().toString());
        newEstimateBinding.tvMargin.setText(String.valueOf((int) dataItem.getCuttinglengthmargin().doubleValue()));
        newEstimateBinding.tvDecalMarginSize.setText(String.valueOf((int) dataItem.getDecalsizemargin().doubleValue()));


    }

    private void fetchData() {
        if (isConnectedToInternet()) {
            viewModel.getBusinessDetailsByBusinessId(businessId);
        } else {
            showNoInternetDialog();
        }
        if (isConnectedToInternet()) {
            viewModel.getClientByClientId(clientId);
        } else {
            showNoInternetDialog();
        }

        viewModel.getGetClientByClientIdLiveData().observe(this, getClientByClientIdResponse -> {
            if (getClientByClientIdResponse != null) {
                handleClientDetailsResponse(getClientByClientIdResponse.getData());
            }
        });
    }

    private void handleClientDetailsResponse(ClientDetails clientDetails) {
        if (clientDetails != null) {
            this.selectedClient = clientDetails;
            newEstimateBinding.tvClientNameInEstimate.setText(clientDetails.getClientname());
        }
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(NewEstimatesActivityViewModel.class);


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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Number of Ply
        plySpinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, noOfPly);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                String clientName = newEstimateBinding.tvClientNameInEstimate.getText().toString();

                boolean check = validateInfo(enterBoxName, enterLength, enterWidth, enterHeight);
                if (check) {
                    storeValuesToEstimateDataHolder();
//                    updateUI();
                    String noOfPly = newEstimateBinding.spinnerNoOfPly.getSelectedItem().toString();
                    String updatedCuttingLength = newEstimateBinding.tietCuttingLength.getText().toString();
                    String updatedDecalSize = newEstimateBinding.tietDecalSize.getText().toString();


                    Intent intent = new Intent(NewEstimateActivity.this, PaperSpecificationActivity.class);
                    intent.putExtra("cuttingLength", updatedCuttingLength);
                    intent.putExtra("decalSize", updatedDecalSize);
                    intent.putExtra("noOfBox", newEstimateBinding.tietNumberOfBox.getText().toString());
                    intent.putExtra("noOfPly", noOfPly);
                    intent.putExtra("EDIT_ESTIMATE", dataItem);
                    intent.putExtra("selectedClient", selectedClient);
                    if (length == 0.0 && width == 0.0 && height == 0.0) {
                        CreateEstimateDataHolder.INSTANCE.setEmptyBoxDim(true);
                    }
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
                        newEstimateBinding.tietCuttingLength.setText(resOfCuttingTiet);
                        newEstimateBinding.tietDecalSize.setText(resOfDecalTiet);

                        int cuttingLengthMm = (int) v;
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(cuttingLengthMm));
                        int DecalSizeMm = (int) decalSize;
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(DecalSizeMm));
                    } else {
                        int cuttingMargin = Integer.parseInt(newEstimateBinding.tvMargin.getText().toString());
                        int decalMargin = Integer.parseInt(newEstimateBinding.tvDecalMarginSize.getText().toString());
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(cuttingMargin));
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(decalMargin));

                        float cuttingLength = (float) (cuttingMargin / 25.4);
                        float decalSize = (float) (decalMargin / 25.4);

                        float twoDigitsCuttingLength = Float.valueOf(String.format("%.2f", cuttingLength));
                        float twoDigitsDecalSize = Float.valueOf(String.format("%.2f", decalSize));
                        newEstimateBinding.tietCuttingLength.setText(String.valueOf(twoDigitsCuttingLength));
                        newEstimateBinding.tietDecalSize.setText(String.valueOf(twoDigitsDecalSize));

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
                        newEstimateBinding.tietCuttingLength.setText(resOfCuttingTiet);
                        newEstimateBinding.tietDecalSize.setText(resOfDecalTiet);

                        int cuttingLengthMm = (int) v;
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(cuttingLengthMm));
                        int DecalSizeMm = (int) decalSize;
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(DecalSizeMm));
                    } else {
                        int cuttingMargin = Integer.parseInt(newEstimateBinding.tvMargin.getText().toString());
                        int decalMargin = Integer.parseInt(newEstimateBinding.tvDecalMarginSize.getText().toString());
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(cuttingMargin));
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(decalMargin));

                        float cuttingLength = (float) (cuttingMargin / 25.4);
                        float decalSize = (float) (decalMargin / 25.4);
                        float twoDigitsCuttingLength = Float.valueOf(String.format("%.2f", cuttingLength));
                        float twoDigitsDecalSize = Float.valueOf(String.format("%.2f", decalSize));
                        newEstimateBinding.tietCuttingLength.setText(String.valueOf(twoDigitsCuttingLength));
                        newEstimateBinding.tietDecalSize.setText(String.valueOf(twoDigitsDecalSize));

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
                    } else {
                        int cuttingMargin = Integer.parseInt(newEstimateBinding.tvMargin.getText().toString());
                        int decalMargin = Integer.parseInt(newEstimateBinding.tvDecalMarginSize.getText().toString());
                        newEstimateBinding.tvCuttingLengthSize.setText(String.valueOf(cuttingMargin));
                        newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(decalMargin));

                        float cuttingLength = (float) (cuttingMargin / 25.4);
                        float decalSize = (float) (decalMargin / 25.4);
                        float twoDigitsCuttingLength = Float.valueOf(String.format("%.2f", cuttingLength));
                        float twoDigitsDecalSize = Float.valueOf(String.format("%.2f", decalSize));
                        newEstimateBinding.tietCuttingLength.setText(String.valueOf(twoDigitsCuttingLength));
                        newEstimateBinding.tietDecalSize.setText(String.valueOf(twoDigitsDecalSize));

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
                String noOfBox = s.toString();
                if (noOfBox.isEmpty()) {
//                    noOfBox = "1";
                }
                try {
                    int numberOfBoxes = Integer.parseInt(noOfBox);
                    int newDecalSize = (int) calculateNewDecalSize(numberOfBoxes);

                    newEstimateBinding.tvDecalSizeValue.setText(String.valueOf(newDecalSize));

                    double tietDecalSize = newDecalSize / 25.4;
                    String tiet = String.format("%.2f", tietDecalSize);
                    newEstimateBinding.tietDecalSize.setText(tiet);
                } catch (NumberFormatException e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                        isSheetDetailsTouched = true;
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
                        isSheetDetailsTouched = true;
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
                CreateEstimateDataHolder.INSTANCE.setLengthInch(Float.parseFloat(newEstimateBinding.tietLength.getText().toString()));
                CreateEstimateDataHolder.INSTANCE.setWidthInch(Float.parseFloat(newEstimateBinding.tietWidth.getText().toString()));
                CreateEstimateDataHolder.INSTANCE.setHeightInch(Float.parseFloat(newEstimateBinding.tietHeight.getText().toString()));
                break;
        }
        CreateEstimateDataHolder.INSTANCE.setNoOfPly(String.valueOf(newEstimateBinding.spinnerNoOfPly.getSelectedItem().toString().charAt(0)));
        CreateEstimateDataHolder.INSTANCE.setNoOfBox(Integer.parseInt(newEstimateBinding.tietNumberOfBox.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setCuttingLength(Double.parseDouble(newEstimateBinding.tietCuttingLength.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setDecalSize(Double.parseDouble(newEstimateBinding.tietDecalSize.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setCuttingMarginMm(Integer.parseInt(newEstimateBinding.tvMargin.getText().toString()));
        CreateEstimateDataHolder.INSTANCE.setDecalMarginMm(Integer.parseInt(newEstimateBinding.tvDecalMarginSize.getText().toString()));
    }

    private void setDefaultSheetSizes() {
//        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
//        if (appConfigResponse.getData() != null) {
//            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
//            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
//                int configId = appConfigDataItem.getConfigid();
//                String configValue = appConfigDataItem.getConfigvalue();
//                if (configId == 10) {
//                    this.configMargin = configValue;
//                    newEstimateBinding.tvMargin.setText(configValue);
//                } else if (configId == 11) {
//                    this.configDecal = configValue;
//                    newEstimateBinding.tvDecalMarginSize.setText(configValue);
//                }
//            }
//        }
        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
            // Set this.configMargin to the margin length
            this.configMargin = String.valueOf(businessDetails.getMarginlength());

            newEstimateBinding.tvMargin.setText(configMargin);

            this.configDecal = String.valueOf(businessDetails.getMarginwidth());
            newEstimateBinding.tvDecalMarginSize.setText(configDecal);
        }
    }

    private double calculateNewDecalSize(int numberOfBoxes) {
        String decalMarginSize = newEstimateBinding.tvDecalMarginSize.getText().toString();
        double decalMarginSizeDouble = Double.parseDouble(decalMarginSize);
        double newResult = ((widthMm + heightMm) * numberOfBoxes) + decalMarginSizeDouble;
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
                lengthMm = (double) Math.round(length * 2.54 * 10);
                widthMm = (double) Math.round(width * 2.54 * 10);
                heightMm = (double) Math.round(height * 2.54 * 10);
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