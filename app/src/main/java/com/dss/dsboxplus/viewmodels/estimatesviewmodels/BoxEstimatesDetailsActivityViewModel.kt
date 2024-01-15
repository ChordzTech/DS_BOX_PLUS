package com.dss.dsboxplus.viewmodels.estimatesviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.GetClientByClientIdResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class BoxEstimatesDetailsActivityViewModel(val repository: MainRepository) : BaseViewModel() {
    var getBusinessLiveData = MutableLiveData<BusinessDetailsResponse>()
        get() = field

    var getClientByClientIdLiveData = MutableLiveData<GetClientByClientIdResponse>()
        get() = field

    fun getBusinessDetailsByBusinessId() {
        val businessId =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID)
        viewModelScope.launch {
            when (val response = repository.getBusinessDetailsByBusinessID(businessId)) {
                is NetworkState.Success -> {
                    getBusinessLiveData.postValue(response.data!!)
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
    fun getClientByClientId(){
        val clientId=AppPreferences.getLongValueFromSharedPreferences(AppPreferences.CLIENT_ID)
        viewModelScope.launch {
            when (val response = repository.getClientByClintId(clientId)) {
                is NetworkState.Success -> {
                    getClientByClientIdLiveData.postValue(response.data!!)
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