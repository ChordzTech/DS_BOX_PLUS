package com.dss.dsboxplus.viewmodels.homeviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class SplashViewModel(val repository: MainRepository) : BaseViewModel() {
    var userDetailsResponse = MutableLiveData<UserDetailsResponse>()
        get() = field

    var businessDetailsLiveData = MutableLiveData<BusinessDetailsResponse>()
        get() = field


    fun getBusinessDetails() {
        viewModelScope.launch {
            when (val response = repository.getBusinessList()) {
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