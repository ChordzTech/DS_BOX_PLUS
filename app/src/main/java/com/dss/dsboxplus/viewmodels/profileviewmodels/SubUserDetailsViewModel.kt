package com.dss.dsboxplus.viewmodels.profileviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.DeleteSubUserResponse
import com.dss.dsboxplus.data.repo.response.UpdateSubUserRequest
import com.dss.dsboxplus.data.repo.response.UpdateSubUserResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class SubUserDetailsViewModel(val repository: MainRepository) : BaseViewModel() {
    var deleteSubUserLiveData = MutableLiveData<DeleteSubUserResponse>()
        get() = field
    var updateSubUserLiveData = MutableLiveData<UpdateSubUserResponse>()
        get() = field

    fun deleteSubUser(userid: Long) {
        showLoader()

        viewModelScope.launch {
            when (val response = repository.deleteSubUser(userid)) {
                is NetworkState.Success -> {
                    hideLoader()
                    deleteSubUserLiveData.postValue(response.data!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        deleteSubUserLiveData.postValue(response.response.body())
                    } else {
//                        deleteSubUserLiveData.postValue(response.response.body()!!)
                    }
                }
            }
        }
    }

    fun updateSubUser(
        userid: Long,
        userName: String,
        accessLevel: String
    ) {
        showLoader()
        val request = UpdateSubUserRequest()
        request.businessid =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID).toInt()
        request.userid=userid.toInt()
        request.username=userName
        request.useraccess=accessLevel.toInt()
        viewModelScope.launch {
            when (val response = repository.updateSubUser(request)) {
                is NetworkState.Success -> {
                    hideLoader()
                    updateSubUserLiveData.postValue(response.data!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        updateSubUserLiveData.postValue(response.response.body())
                    } else {
//                        updateClientRequestLiveData.postValue(response.response.body()!!)
                    }
                }
            }
        }
    }
}