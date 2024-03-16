package com.dss.dsboxplus.clients;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityEstimateListBinding;
import com.dss.dsboxplus.estimates.BoxEstimatesDetailsActivity;
import com.dss.dsboxplus.estimates.NewEstimateActivity;
import com.dss.dsboxplus.model.EstimatesDataModel;
import com.dss.dsboxplus.recyclerview.EstimatesViewAdapter;
import com.dss.dsboxplus.viewmodels.AppViewModelFactory;
import com.dss.dsboxplus.viewmodels.clientsviewmodels.EstimateListViewModel;
import com.example.mvvmretrofit.data.repo.MainRepository;
import com.example.mvvmretrofit.data.repo.remote.RetrofitService;

import java.util.ArrayList;

public class EstimateListActivity extends BaseActivity implements EstimatesViewAdapter.OnEstimatesSelectedI {

    ActivityEstimateListBinding binding;
    EstimateListViewModel viewModel;
    private ArrayList<DataItem> estimateList = new ArrayList<>();
    private long clientId = 0;
    private EstimatesViewAdapter estimatesViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_estimate_list);
        initView();
        initObservables();
        fetchData();
    }

    private void fetchData() {
        if (isConnectedToInternet()) {
            viewModel.getEstimateByClientId(clientId);
        } else {
            showNoInternetDialog();
        }
    }

    private void initObservables() {
        viewModel.getEstimateListByClientIdLivedata().observe(this, estimateListResponse ->
        {
            estimateList = (ArrayList<DataItem>) estimateListResponse.getData();
            loadData();
        });
    }

    private void initView() {
        RetrofitService retrofitService = RetrofitService.Companion.getInstance();
        MainRepository mainRepository = new MainRepository(retrofitService);
        viewModel = new ViewModelProvider(this, new AppViewModelFactory(mainRepository)).get(EstimateListViewModel.class);

        clientId = this.getIntent().getLongExtra("clientId", 0);
        estimatesViewAdapter = new EstimatesViewAdapter();
        estimatesViewAdapter.setOnEstimatesSelectedListner(this);

        binding.svSearchInEstimate.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()){
                    filterEstimatesList(newText);
                } else{
                    estimatesViewAdapter.setFilterList(ConfigDataProvider.globalEstimateList);
                }
                return true;
            }
        });
        binding.svSearchInEstimate.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                estimatesViewAdapter.setFilterList(ConfigDataProvider.globalEstimateList);
                return true;
            }
        });

        binding.fabAddEstimateInEstimateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstimateListActivity.this, NewEstimateActivity.class);
                startActivity(intent);
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

    @Override
    public void onEstimatesSelectedI(DataItem dataItem) {
        Intent intent = new Intent(this, BoxEstimatesDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("ESTIMATES", dataItem);
        intent.putExtra("ESTIMATES_BUNDLE", bundle);
        startActivity(intent);
    }

    @Override
    public void onItemLongPressed(EstimatesDataModel estimatesDataModel, int adapterPosition) {
//        if (!estimateSelection) {
//            selectedEstimatesList = new ArrayList<EstimatesDataModel>();
//        }
//        fabCorrect.setVisibility(View.VISIBLE);
//        fabCancel.setVisibility(View.VISIBLE);
//        selectedEstimatesList.add(estimatesDataModel);
//        this.estimateSelection = true;
    }


    @Override
    public void onItemClicked(EstimatesDataModel estimatesDataModel, int adapterPosition) {
//        if (estimateSelection) {
//            selectedEstimatesList.add(estimatesDataModel);
//        } else {
//            //open details
//        }
    }

    private void loadData() {
        if (!estimateList.isEmpty()) {
            binding.rvEstimateRecyclerViewInEstimateList.setAdapter(estimatesViewAdapter);
            estimatesViewAdapter.setEstimatesList(estimateList);
        }
    }
}