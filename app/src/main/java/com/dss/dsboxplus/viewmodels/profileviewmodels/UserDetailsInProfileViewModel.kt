package com.dss.dsboxplus.viewmodels.profileviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.UpdateSubUserRequest
import com.dss.dsboxplus.data.repo.response.UpdateSubUserResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class UserDetailsInProfileViewModel(val repository: MainRepository) : BaseViewModel()  {
    var updateUserLiveData = MutableLiveData<UpdateSubUserResponse>()
        get() = field


    fun updateUserDetails(
        userid: Long,
        userName: String
    ){
        showLoader()
        val request = UpdateSubUserRequest()
        request.businessid =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID).toInt()
        request.userid=userid.toInt()
        request.username=userName

        viewModelScope.launch {
            when (val response = repository.updateSubUser(request)) {
                is NetworkState.Success -> {
                    hideLoader()
                    updateUserLiveData.postValue(response.data!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        updateUserLiveData.postValue(response.response.body())
                    } else {
//                        updateClientRequestLiveData.postValue(response.response.body()!!)
                    }
                }
            }
        }
    }
}