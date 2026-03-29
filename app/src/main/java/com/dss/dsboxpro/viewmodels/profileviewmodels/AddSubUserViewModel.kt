package com.dss.dsboxpro.viewmodels.profileviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxpro.baseview.BaseViewModel
import com.dss.dsboxpro.data.repo.request.AddUserRequest
import com.dss.dsboxpro.data.repo.response.AddUserResponse
import com.dss.dsboxpro.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class AddSubUserViewModel(val repository: MainRepository) : BaseViewModel() {
    var addSubUserLiveData = MutableLiveData<AddUserResponse>()
        get() = field


    fun addSubUSer(
        username: String,
        contact: String
    ) {
        showLoader()
        val request=AddUserRequest()
        request.username=username
        request.mobileno=contact
        request.deviceinfo="NewUser"
        request.androidid="NewUser"
        request.businessid=AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID).toInt()

        viewModelScope.launch {
            when(val response=repository.addUser(request)){
                is NetworkState.Success -> {
                    hideLoader()
                    addSubUserLiveData.postValue(response.data!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        addSubUserLiveData.postValue(response.response.body())
                    } else {
                        addSubUserLiveData.postValue(response.response.body()!!)
                    }
                }
            }
        }
    }
}