package com.dss.dsboxplus.viewmodels.homeviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.AppConfigResponse
import com.dss.dsboxplus.data.repo.response.ClientListResponse
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.dss.dsboxplus.data.repo.response.QrCodeResponse
import com.dss.dsboxplus.data.repo.response.SubscriptionDetailsResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class HomeViewModel(val repository: MainRepository) : BaseViewModel() {

    //    var estimateListLiveData = MutableLiveData<EstimateListResponse>()
//        get() = field
    var clientListLiveData = MutableLiveData<ClientListResponse>()
        get() = field
    var appConfigLiveData = MutableLiveData<AppConfigResponse>()
        get() = field
    var subscriptionLiveData = MutableLiveData<SubscriptionDetailsResponse>()
        get() = field
    var qrCodeLiveData = MutableLiveData<QrCodeResponse>()
        get() = field
    var estimateListLiveData = MutableLiveData<EstimateListResponse>()
        get() = field
//    var clientListLiveData=MutableLiveData<ClientListResponse>()
//        get()=field

//    fun getEstimateList() {
//        viewModelScope.launch {
//            when (val response = repository.getEstimateList()) {
//                is NetworkState.Success -> {
//                    estimateListLiveData.postValue(response.data!!)
//                }
//
//                is NetworkState.Error -> {
//                    if (response.response.code() == 401) {
////                        estimateList.postValue(NetworkState.Error())
//                    } else {
////                        estimateList.postValue(NetworkState.Error)
//                    }
//                }
//            }
//        }
//    }

//    fun getClientList() {
//        viewModelScope.launch {
//            when (val response = repository.getClientList()) {
//                is NetworkState.Success -> {
//                    clientListLiveData.postValue(response.data!!)
//                }
//
//                is NetworkState.Error -> {
//                    if (response.response.code() == 401) {
////                        estimateList.postValue(NetworkState.Error())
//                    } else {
////                        estimateList.postValue(NetworkState.Error)
//                    }
//                }
//            }
//        }
//    }

    fun getAppConfig() {
        viewModelScope.launch {
            when (val response = repository.getAppConfigList()) {
                is NetworkState.Success -> {
                    appConfigLiveData.postValue(response.data!!)
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

    fun getSubscriptionList() {
        viewModelScope.launch {
            when (val response = repository.getSubscriptionList()) {
                is NetworkState.Success -> {
                    subscriptionLiveData.postValue(response.data!!)
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

    fun getQrCode() {
        viewModelScope.launch {
            when (val response = repository.getQrcode()) {
                is NetworkState.Success -> {
                    qrCodeLiveData.postValue(response.data!!)
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

    fun getEstimateByBusinessIdUserId() {
        showLoader()
        val businessId =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID)
        val userId = AppPreferences.getLongValueFromSharedPreferences(AppPreferences.USER_ID)
        viewModelScope.launch {
            when (val response = repository.getEstimateListByBusinessUserID(businessId, userId)) {
                is NetworkState.Success -> {
                    hideLoader()
                    estimateListLiveData.postValue(response.data!!)
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
//    fun getClientListByBusinessUserId() {
//        viewModelScope.launch {
//            when (val response = repository.getClientLisByBusinessUserId()) {
//                is NetworkState.Success -> {
//                    clientListLiveData.postValue(response.data!!)
//                }
//
//                is NetworkState.Error -> {
//                    if (response.response.code() == 401) {
////                        estimateList.postValue(NetworkState.Error())
//                    } else {
////                        estimateList.postValue(NetworkState.Error)
//                    }
//                }
//            }
//        }
//    }
}