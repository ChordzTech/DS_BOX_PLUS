package com.dss.dsboxplus.viewmodels.homeviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.configdata.ConfigDataProvider
import com.dss.dsboxplus.data.repo.response.AppConfigResponse
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.ClientListResponse
import com.dss.dsboxplus.data.repo.response.DataItem
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.dss.dsboxplus.data.repo.response.GetSubscriptionForBusiness
import com.dss.dsboxplus.data.repo.response.QrCodeResponse
import com.dss.dsboxplus.data.repo.response.SubscriptionDetailsResponse
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class HomeViewModel(val repository: MainRepository) : BaseViewModel() {
    var startIndex: Int = -10
    var limit: Int = 10

    var gloabalEstimateListLiveData = MutableLiveData<List<DataItem>>()
        get() = field

    var userDetailsResponse = MutableLiveData<UserDetailsResponse>()
        get() = field
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
    var subForBusinessLiveData = MutableLiveData<GetSubscriptionForBusiness>()
        get() = field
    var businessDetailsLiveData = MutableLiveData<BusinessDetailsResponse>()
        get() = field

    //    fun getEstimateList() {
//        viewModelScope.launch {
//            when (val response = repository.getEstimateList()) {
//                is NetworkState.Success -> {
//                    estimateListLiveData.postValue(response.data!!)
//                }
//
//                is NetworkState.Error -> {
//                    if (response.response.code() == 401) {
//                        estimateList.postValue(NetworkState.Error())
//                    } else {
////                        estimateList.postValue(NetworkState.Error)
//                    }
//                }
//            }
//        }
//    }


    fun getUserDetails(
        mobileno: String,
        deviceinfo: String
    ) {
        showLoader()

        viewModelScope.launch {
            when (val response =
                repository.getUserDetails(mobileno = mobileno, deviceinfo = deviceinfo)) {
                is NetworkState.Success -> {
                    userDetailsResponse.postValue(response.data!!)
                    hideLoader()
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 404) {

                        userDetailsResponse.postValue(
                            UserDetailsResponse(
                                response.response.code(), arrayListOf(),
                                response.response.message(), ""
                            )
                        )
                    } else {
                        userDetailsResponse.postValue(
                            UserDetailsResponse(
                                response.response.code(), arrayListOf(),
                                response.response.message(), ""
                            )
                        )
                    }
                }
            }
        }
    }

    fun getClientList() {
        val businessId =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID)
        viewModelScope.launch {
            when (val response = repository.getClientList(businessId)) {
                is NetworkState.Success -> {
                    clientListLiveData.postValue(response.data!!)
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

    fun getBusinessDetails() {
        val businessId =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID)
        viewModelScope.launch {
            when (val response = repository.getBusinessDetailsByBusinessID(businessId)) {
                is NetworkState.Success -> {
                    businessDetailsLiveData.postValue(response.data!!)
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
        //start_index and limit set globally
        //start_index increment on every iteration by limit


        showLoader()
        startIndex += limit
        val businessId =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID)
        val userId = AppPreferences.getLongValueFromSharedPreferences(AppPreferences.USER_ID)
        viewModelScope.launch {
            when (val response =
                repository.getEstimateListByBusinessUserID(businessId, userId, startIndex, limit)) {
                is NetworkState.Success -> {
                    hideLoader()
                    val estimateListResponse = response.data
                    if (startIndex == -10) {
                        ConfigDataProvider.globalEstimateList.clear()
                    }
                    if (estimateListResponse.data!!.isNotEmpty()) {
                        if (startIndex <= estimateListResponse.totalRecords!!) {
                            gloabalEstimateListLiveData.postValue(estimateListResponse.data!! as List<DataItem>?)
                            getEstimateByBusinessIdUserId()
                        }
                    }
                    if (estimateListResponse.data.isEmpty()) {
                        startIndex = -10
                    }
                    estimateListLiveData.postValue(estimateListResponse)
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
    fun getSubForBusiness() {
        val businessId =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID)
        viewModelScope.launch {
            when (val response = repository.getSubForBusiness(businessId)) {
                is NetworkState.Success -> {
                    subForBusinessLiveData.postValue(response.data!!)
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