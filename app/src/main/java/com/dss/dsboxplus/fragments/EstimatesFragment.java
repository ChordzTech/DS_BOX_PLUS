package com.dss.dsboxplus.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.alertdialog.DialogUtils;
import com.dss.dsboxplus.alertdialog.SubscriptionViewModel;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse;
import com.dss.dsboxplus.estimates.BoxEstimatesDetailsActivity;
import com.dss.dsboxplus.home.HomeActivity;
import com.dss.dsboxplus.loginandverification.IHomeActivityCallBack;
import com.dss.dsboxplus.model.EstimatesDataModel;
import com.dss.dsboxplus.preferences.AppPreferences;
import com.dss.dsboxplus.profile.SubscriptionActivity;
import com.dss.dsboxplus.recyclerview.EstimatesViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EstimatesFragment extends Fragment implements EstimatesViewAdapter.OnEstimatesSelectedI {
    ConstraintLayout estimatePopup, subPopup;
    MaterialButton btUpgradeSub, btContinue;
    private RecyclerView rvEstimatesRecyclerView;
    private EstimatesViewAdapter estimatesViewAdapter;
    private FloatingActionButton fabEstimates, fabCancel, fabCorrect;
    private SearchView searchView;
    private IHomeActivityCallBack iHomeActivityCallBack;
    private boolean estimateSelection;
    private EstimatesDataModel estimatesDataModel;
    private ArrayList<EstimatesDataModel> selectedEstimatesList;
    private EstimatesViewAdapter.OnEstimatesSelectedI onEstimatesSelectedListner;
    private ArrayList<DataItem> estimateList = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private ArrayList<AppConfigDataItems> appConfigList = new ArrayList<>();
    private HomeActivity homeActivity;
    private MutableLiveData<Boolean> onFloatingActionClickLiveData = new MutableLiveData<Boolean>();
    private SubscriptionViewModel viewModel;


    public EstimatesFragment(IHomeActivityCallBack iHomeActivityCallBack) {
        // Required empty public constructor
        this.iHomeActivityCallBack = iHomeActivityCallBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estimates_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabEstimates = view.findViewById(R.id.fabEstimates);
        fabCancel = view.findViewById(R.id.fabCancel);
        fabCorrect = view.findViewById(R.id.fabCorrect);
        searchView = view.findViewById(R.id.svSearch);
        estimatePopup = view.findViewById(R.id.estimatePopup);
        subPopup = view.findViewById(R.id.subPopup);
        btUpgradeSub = view.findViewById(R.id.btUpgradeSub);
        btContinue = view.findViewById(R.id.btContinue);
        bottomNavigationView = view.findViewById(R.id.bottomNavigation);

        //if remaining days==0 then hide fabEstimate Button
        viewModel = new ViewModelProvider(requireActivity()).get(SubscriptionViewModel.class);
        MutableLiveData<Integer> remainingDaysLiveData = viewModel.getRemainingDays();
        remainingDaysLiveData.observe(getViewLifecycleOwner(), remainingDays -> {
            if (remainingDays != null && remainingDays == 0) {
                fabEstimates.setVisibility(View.GONE);
            } else {
                fabEstimates.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty())
                    filterEstimatesList(newText);
                else{
                    estimatesViewAdapter.setFilterList(ConfigDataProvider.globalEstimateList);
                }
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                estimatesViewAdapter.setFilterList(ConfigDataProvider.globalEstimateList);
                return true;
            }
        });

        fabCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottommSheetDialogFragment bottommSheetDialogFragment = BottommSheetDialogFragment.newInstance();
                bottommSheetDialogFragment.show(getChildFragmentManager(), "add");
            }
        });
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabCorrect.setVisibility(View.INVISIBLE);
                fabCancel.setVisibility(View.INVISIBLE);
            }
        });


        //if SubDays==0
//        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        // Check if remaining days are zero
//        if (sharedViewModel.getRemainingDays() == 0) {
//            fabEstimates.setVisibility(View.GONE);
//        }

        // User Access
        if (ConfigDataProvider.INSTANCE.getUserDetails() != null && hasUserAccess(ConfigDataProvider.INSTANCE.getUserDetails(), 1)) {
            fabEstimates.setVisibility(View.GONE);
        }

        fabEstimates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFloatingActionClickLiveData.postValue(true);
                AppPreferences.INSTANCE.isCreatingEstimate(getActivity(), AppPreferences.IS_CREATING_ESTIMATE, true);
//                if(true){
//                    iHomeActivityCallBack.loadClientFragmentOnEmptyEstimates();
//                    Toast.makeText(getContext(), "Select Client", Toast.LENGTH_SHORT).show();
//                }
            }

        });


//        createAddNewEstiPopUp();
//        createSubPopUp();
        initView(view);
//        prepareData();
//        loadData();
//        checkSubscriptionEndingNotification();
//        checkPremiumSubEndingNoti();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (AppPreferences.INSTANCE.getStringValueFromSharedPreferences(AppPreferences.APP_STATUS).equalsIgnoreCase("Expired")
                ||
                (ConfigDataProvider.INSTANCE.getUserDetails() != null &&
                        ConfigDataProvider.INSTANCE.getUserDetails().getData() != null &&
                        ConfigDataProvider.INSTANCE.getUserDetails().getData().get(0).getUseraccess() == 1)) {
            fabEstimates.setVisibility(View.GONE);
        } else {
            fabEstimates.setVisibility(View.VISIBLE);
        }
    }

    private boolean hasUserAccess(UserDetailsResponse userDetailsResponse, int i) {
        if (userDetailsResponse.getData() != null && !userDetailsResponse.getData().isEmpty()) {
            UserData userData = userDetailsResponse.getData().get(0); // Assuming there is only one UserData in the list
            return userData.getUseraccess() != null && userData.getUseraccess() == i;
        }
        return false;
    }

    private void checkPremiumSubEndingNoti() {
        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
        if (appConfigResponse.getData() != null) {
            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                int configId = appConfigDataItem.getConfigid();
                String configValue = appConfigDataItem.getConfigvalue();
                if (configId == 30) { // Assuming configid 30 is for Premium subscription ending notification
                    int daysBeforeNotification = Integer.parseInt(configValue);
                    if (daysBeforeNotification >= 0) {
                        createSubPopUp();
                        break;
                    }
                }
            }
        }
    }

    private void checkSubscriptionEndingNotification() {
        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
        if (appConfigResponse.getData() != null) {
            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                // Access individual properties of AppConfigDataItems
                int configId = appConfigDataItem.getConfigid();
                String configValue = appConfigDataItem.getConfigvalue();
                if (configId == 31) { // Assuming configid 31 is for subscription ending notification
                    int daysBeforeNotification = Integer.parseInt(configValue);
                    if (daysBeforeNotification >= 0) {
                        createSubPopUp();
                        break;
                    }
                }
            }
        }
    }


    private void prepareData() {
        Collections.sort(estimateList, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem item1, DataItem item2) {
                // Assuming your DataItem class has a method to get the date, adjust accordingly
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date1, date2;
                try {
                    date1 = sdf.parse(item1.getEstimatedate());
                    date2 = sdf.parse(item2.getEstimatedate());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0; // Handle the exception or return 0 if parsing fails
                }
                // Reverse the order to get the latest first
                return date2.compareTo(date1);
            }
        });
    }

    private void filterEstimatesList(String newText) {
        ArrayList<DataItem> filteredList = new ArrayList<>();

        for (DataItem dataItem : ConfigDataProvider.globalEstimateList) {
            // Assuming your DataItem class has a method to get the name, adjust accordingly
            if (dataItem.getBoxname().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(dataItem);
            }
        }

        estimatesViewAdapter.setFilterList(filteredList);
    }

    private void createSubPopUp() {
        DialogUtils.showCustomDialog(getContext(), new DialogUtils.DialogListener() {
            @Override
            public void onPositiveButtonClick() {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
            }

            @Override
            public void onNegativeButtonClick() {

            }
        });
    }

    private void createAddNewEstiPopUp() {
        if (estimateList.isEmpty()) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.add_new_estimate_popup, estimatePopup);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


    private void initView(View view) {
        rvEstimatesRecyclerView = view.findViewById(R.id.rvEstimateRecyclerView);
        estimatesViewAdapter = new EstimatesViewAdapter();
        rvEstimatesRecyclerView.setAdapter(estimatesViewAdapter);
        estimatesViewAdapter.setOnEstimatesSelectedListner(this);
        if (!estimateList.isEmpty()) {
//            loadData();
        }


//        rvEstimatesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 && bottomNavigationView.isShown()) {
//                    bottomNavigationView.setVisibility(View.GONE);
//                } else if (dy < 0 && !bottomNavigationView.isShown()) {
//                    bottomNavigationView.setVisibility(View.VISIBLE);
//                }
//            }
//        });

//        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
//        if (appConfigResponse.getData() != null) {
//            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
//            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
//                int configId = appConfigDataItem.getConfigid();
//                String configValue = appConfigDataItem.getConfigvalue();
//                if (configId==27){
//                    int estimateLimit = Integer.parseInt(configValue);
//                    if (estimateList.size() > estimateLimit) {
//                        // Hide the button
//                        fabEstimates.setVisibility(View.GONE);
//                    } else {
//                        // Show the button
//                        fabEstimates.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        }
    }


    private void loadData() {
        if (!estimateList.isEmpty()) {
            prepareData();
            estimatesViewAdapter.setEstimatesList(estimateList);
        }
    }

//    private ArrayList<EstimatesDataModel> prepareData() {
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Box", "ABC", "300X60X300", "Rs.40.00", "06/10/2020", false));
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Pk", "Pankaj", "300X60X300", "Rs.700.00", "08/10/2021", false));
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Pankaj", "Pankaj", "300X60X300", "Rs.60.00", "10/10/2023", false));
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Shubham", "Pankaj", "300X60X300", "RS.500.00", "01/05/2023", false));
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Abhishek", "Pankaj", "300X60X300", "Rs.50.00", "01/04/2023", false));
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Kalyan", "Pankaj", "300X60X300", "Rs.80.00", "01/04/2023", false));
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Shubham", "Pankaj", "300X60X300", "Rs.65.00", "03/05/2019", false));
//        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
//                "Rushi", "Pankaj", "300X60X300", "Rs.89.00", "05/04/2023", false));
//        Collections.sort(estimatesList, new Comparator<EstimatesDataModel>() {
//            @Override
//            public int compare(EstimatesDataModel estimatesDataModel, EstimatesDataModel t1) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                Date d1 = null, d2 = null;
//                try {
//                    d1 = sdf.parse(estimatesDataModel.getDateOfEstimate());
//                    d2 = sdf.parse(t1.getDateOfEstimate());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                return d1.compareTo(d2);
//            }
//        });
//
//        return estimatesList;
//    }


//    @Override
//    public void onEstimatesSelectedI(EstimatesDataModel estimatesDataModel) {
//        Intent intent = new Intent(getActivity(), BoxEstimatesDetailsActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("ESTIMATES", estimatesDataModel);
//        intent.putExtra("ESTIMATES_BUNDLE", bundle);
//        startActivity(intent);
//    }

    @Override
    public void onEstimatesSelectedI(DataItem dataItem) {
        Intent intent = new Intent(getActivity(), BoxEstimatesDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("ESTIMATES", dataItem);
        intent.putExtra("ESTIMATES_BUNDLE", bundle);
        startActivity(intent);
    }

    @Override
    public void onItemLongPressed(EstimatesDataModel estimatesDataModel, int adapterPosition) {
        if (!estimateSelection) {
            selectedEstimatesList = new ArrayList<EstimatesDataModel>();
        }
        fabCorrect.setVisibility(View.VISIBLE);
        fabCancel.setVisibility(View.VISIBLE);
        selectedEstimatesList.add(estimatesDataModel);
        this.estimateSelection = true;
    }


    @Override
    public void onItemClicked(EstimatesDataModel estimatesDataModel, int adapterPosition) {
        if (estimateSelection) {
            selectedEstimatesList.add(estimatesDataModel);
        } else {
            //open details
        }
    }

    public ArrayList<DataItem> getEstimateList() {
        return estimateList;
    }

    public void setEstimateList(ArrayList<DataItem> estimateList) {
        this.estimateList = estimateList;
        loadData();
    }

    public MutableLiveData<Boolean> getOnFloatingActionClickLiveData() {
        return onFloatingActionClickLiveData;
    }

    public void setOnFloatingActionClickLiveData(MutableLiveData<Boolean> onFloatingActionClickLiveData) {
        this.onFloatingActionClickLiveData = onFloatingActionClickLiveData;
    }

    public void setAppConfigList(ArrayList<AppConfigDataItems> appConfigList) {
        this.appConfigList = appConfigList;
    }
}
