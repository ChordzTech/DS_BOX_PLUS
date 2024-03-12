package com.dss.dsboxplus.viewmodels.clientsviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class EstimateListViewModel(val repository: MainRepository) : BaseViewModel() {
    var estimateListByClientIdLivedata = MutableLiveData<EstimateListResponse>()
        get() = field

    fun getEstimateByClientId(clientId: Long) {
        showLoader()
        val userId =AppPreferences.getLongValueFromSharedPreferences(AppPreferences.USER_ID)
            viewModelScope.launch {
                when (val response = repository.getEstimateByClientID(clientId,userId)) {
                    is NetworkState.Success -> {
                        hideLoader()
                        estimateListByClientIdLivedata.postValue(response.data!!)
                    }

                    is NetworkState.Error -> {
                        hideLoader()
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