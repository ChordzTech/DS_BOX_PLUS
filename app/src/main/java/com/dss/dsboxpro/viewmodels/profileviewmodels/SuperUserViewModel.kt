package com.dss.dsboxpro.viewmodels.profileviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxpro.baseview.BaseViewModel
import com.dss.dsboxpro.data.repo.response.SubUserDetailsResponse
import com.dss.dsboxpro.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class SuperUserViewModel(val repository: MainRepository) : BaseViewModel()  {
    var usersByBusinessLiveData = MutableLiveData<SubUserDetailsResponse>()
        get() = field

    fun getUserList(){
        val businessId =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID)

        viewModelScope.launch {
            when (val response = repository.getUsersByBusiness(businessId)) {
                is NetworkState.Success -> {
                    usersByBusinessLiveData.postValue(response.data!!)
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