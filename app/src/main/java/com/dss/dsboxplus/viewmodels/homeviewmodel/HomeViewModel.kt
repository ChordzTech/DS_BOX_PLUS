package com.dss.dsboxplus.viewmodels.homeviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class HomeViewModel(val repository: MainRepository) : ViewModel() {

     var estimateListLiveData = MutableLiveData<EstimateListResponse>()
        get() = field

    fun getEstimateList(){
        viewModelScope.launch {
            when(val response = repository.getEstimateList() ){
                is NetworkState.Success -> {
                    estimateListLiveData.postValue(response.data!!)
                }
                is NetworkState.Error -> {
                    if (response.response.code() == 401) {
//                        estimateList.postValue(NetworkState.Error())
                    } else {
//                        estimateList.postValue(NetworkState.Error)
                    }
                }
            }
        }
    }
}