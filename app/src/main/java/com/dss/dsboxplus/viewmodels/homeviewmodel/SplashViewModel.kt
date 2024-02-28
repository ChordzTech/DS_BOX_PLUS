package com.dss.dsboxplus.viewmodels.homeviewmodel

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.UpdateSubUserRequest
import com.dss.dsboxplus.data.repo.response.UpdateSubUserResponse
import com.dss.dsboxplus.data.repo.response.UserData
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class SplashViewModel(val repository: MainRepository) : BaseViewModel() {
    var userDetailsResponse = MutableLiveData<UserDetailsResponse>()
        get() = field

    var businessDetailsLiveData = MutableLiveData<BusinessDetailsResponse>()
        get() = field

    var updateSubUserLiveData = MutableLiveData<UpdateSubUserResponse>()
        get() = field

    var dishaOTPLiveData = MutableLiveData<Any>()


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

    fun updateSubUser(
        userid: Long,
        androidId: String,
        userData: UserData,
    ) {
        showLoader()
        val request = UpdateSubUserRequest()
        request.businessid = userData.businessid
        request.userid = userid.toInt()
        request.androidid = androidId
        request.deviceinfo = Build.BRAND + Build.MODEL
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

    fun getOTP(phoneNumber: Long,otp:String) {
        viewModelScope.launch {
            when (val response = repository.getOTP(phoneNumber,otp)) {
                is NetworkState.Success -> {
                    hideLoader()
                    dishaOTPLiveData.postValue(response!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        dishaOTPLiveData.postValue(response.response.body())
                    }
                }
            }
        }
    }
}