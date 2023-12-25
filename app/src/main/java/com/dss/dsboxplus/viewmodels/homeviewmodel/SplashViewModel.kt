package com.dss.dsboxplus.viewmodels.homeviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class SplashViewModel (val repository: MainRepository): ViewModel(){
    var businessDetailsLiveData= MutableLiveData<BusinessDetailsResponse>()
        get()=field
    fun getBusinessDetails(){
        viewModelScope.launch {
            when(val response=repository.getBusinessList()){
                is NetworkState.Success->{
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
}