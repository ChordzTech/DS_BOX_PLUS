package com.dss.dsboxplus.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.alertdialog.DialogUtils;
import com.dss.dsboxplus.estimates.BoxEstimatesDetailsActivity;
import com.dss.dsboxplus.estimates.NewEstimateActivity;
import com.dss.dsboxplus.loginandverification.IHomeActivityCallBack;
import com.dss.dsboxplus.model.EstimatesDataModel;
import com.dss.dsboxplus.profile.SubscriptionActivity;
import com.dss.dsboxplus.recyclerview.EstimatesViewAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
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
    private ArrayList<EstimatesDataModel> estimatesList = new ArrayList<>();
    private ArrayList<EstimatesDataModel> selectedEstimatesList;
    private EstimatesViewAdapter.OnEstimatesSelectedI onEstimatesSelectedListner;

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
        fabEstimates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if list is empty then load client fragment
//                if(){
//                    iHomeActivityCallBack.loadClientFragmentOnEmptyEstimates();
//                }
                Intent intent = new Intent(getActivity(), NewEstimateActivity.class);
                startActivity(intent);
            }
        });
        createAddNewEstiPopUp();
        createSubPopUp();
        initView(view);
        prepareData();
        loadData();
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
        if (prepareData().isEmpty()) {
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
        estimatesViewAdapter.setOnEstimatesSelectedListner(this);
    }

    private void loadData() {
        estimatesViewAdapter.setEstimatesList(prepareData());
        rvEstimatesRecyclerView.setAdapter(estimatesViewAdapter);
        estimatesViewAdapter.notifyDataSetChanged();
    }

    private ArrayList<EstimatesDataModel> prepareData() {
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Box", "ABC", "300X60X300", "Rs.40.00", "06/10/2020", false));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Pk", "Pankaj", "300X60X300", "Rs.700.00", "08/10/2021", false));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Pankaj", "Pankaj", "300X60X300", "Rs.60.00", "10/10/2023", false));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Shubham", "Pankaj", "300X60X300", "RS.500.00", "01/05/2023", false));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Abhishek", "Pankaj", "300X60X300", "Rs.50.00", "01/04/2023", false));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Kalyan", "Pankaj", "300X60X300", "Rs.80.00", "01/04/2023", false));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Shubham", "Pankaj", "300X60X300", "Rs.65.00", "03/05/2019", false));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Rushi", "Pankaj", "300X60X300", "Rs.89.00", "05/04/2023", false));
        Collections.sort(estimatesList, new Comparator<EstimatesDataModel>() {
            @Override
            public int compare(EstimatesDataModel estimatesDataModel, EstimatesDataModel t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date d1 = null, d2 = null;
                try {
                    d1 = sdf.parse(estimatesDataModel.getDateOfEstimate());
                    d2 = sdf.parse(t1.getDateOfEstimate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return d1.compareTo(d2);
            }
        });

        return estimatesList;
    }


    @Override
    public void onEstimatesSelectedI(EstimatesDataModel estimatesDataModel) {
        Intent intent = new Intent(getActivity(), BoxEstimatesDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("ESTIMATES", estimatesDataModel);
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
}
