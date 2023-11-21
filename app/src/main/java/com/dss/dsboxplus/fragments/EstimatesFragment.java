package com.dss.dsboxplus.fragments;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.dsboxplus.IHomeActivityCallBack;
import com.dss.dsboxplus.R;
import com.dss.dsboxplus.estimates.BoxEstimatesDetailsActivity;
import com.dss.dsboxplus.estimates.NewEstimateActivity;
import com.dss.dsboxplus.model.ClientsDataModel;
import com.dss.dsboxplus.model.EstimatesDataModel;
import com.dss.dsboxplus.recyclerview.EstimatesViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstimatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstimatesFragment extends Fragment implements EstimatesViewAdapter.OnEstimatesSelectedI {
    List<ClientsDataModel> dataModelList;
    private RecyclerView rvEstimatesRecyclerView;
    private EstimatesViewAdapter estimatesViewAdapter;
    private FloatingActionButton fabEstimates;
    private SearchView searchView;
    private IHomeActivityCallBack iHomeActivityCallBack;

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
        searchView = view.findViewById(R.id.svSearch);
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
        initView(view);
        prepareData();
        loadData();
    }


    private void initView(View view) {
        rvEstimatesRecyclerView = view.findViewById(R.id.rvEstimateRecyclerView);
        estimatesViewAdapter = new EstimatesViewAdapter();
        estimatesViewAdapter.setOnEstimatesSelectedListner(this);
        rvEstimatesRecyclerView.setAdapter(estimatesViewAdapter);
    }

    private void loadData() {
        estimatesViewAdapter.setEstimatesList(prepareData());
        estimatesViewAdapter.notifyDataSetChanged();
    }

    private ArrayList<EstimatesDataModel> prepareData() {
        ArrayList<EstimatesDataModel> estimatesList = new ArrayList<>();
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Box", "ABC", "300*60*300", "4", "06/10/2020"));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Pk", "Pankaj", "300*60*300", "4", "08/10/2021"));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Pankaj", "Pankaj", "300*60*300", "4", "10/10/2023"));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Shubham", "Pankaj", "300*60*300", "4", "01/05/2023"));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Abhishek", "Pankaj", "300*60*300", "4", "01/04/2023"));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Kalyan", "Pankaj", "300*60*300", "4", "01/04/2023"));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Shubham", "Pankaj", "300*60*300", "4", "03/05/2019"));
        estimatesList.add(new EstimatesDataModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMO2ILOr-K0b2G2KSf0c5fAKMRmcxou9hL6mODP2eJ&s",
                "Rushi", "Pankaj", "300*60*300", "4", "05/04/2023"));
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

}