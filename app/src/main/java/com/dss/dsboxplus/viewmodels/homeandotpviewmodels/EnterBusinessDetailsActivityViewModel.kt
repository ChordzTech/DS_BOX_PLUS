package com.dss.dsboxplus.viewmodels.homeandotpviewmodels

import android.content.Context
import android.os.Build
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.DateUtils
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.request.AddUserRequest
import com.dss.dsboxplus.data.repo.request.BusinessDetailsRequest
import com.dss.dsboxplus.data.repo.response.AddUserResponse
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.loginandverification.EnterBusinessDetailsActivity
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class EnterBusinessDetailsActivityViewModel(val repository: MainRepository) : BaseViewModel() {

    var businessDetailsLiveData = MutableLiveData<BusinessDetailsResponse>()
        get() = field
    var addUserResponseLiveData = MutableLiveData<AddUserResponse>()
        get() = field

    fun addBusinessDetails(
        businessName: String,
        contact: String,
        address: String,
        pinCode: String
    ) {
        showLoader()

        val request = BusinessDetailsRequest()
        request.businessname = businessName
        request.contactno = contact
        request.address = address
        request.pincode = pinCode
        request.activationdate = DateUtils.getDateInYYYYMMDDFormat()
        request.subscriptiondate =  DateUtils.getDateInYYYYMMDDFormat()

        viewModelScope.launch {
            when (val response = repository.addBusinessDetails(request)) {
                is NetworkState.Success -> {
                    hideLoader()
                    businessDetailsLiveData.postValue(response.data!!)
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

    fun addUser(
        buzz: BusinessDetailsResponse,
        context: Context
    ) {
        val request = AddUserRequest()
        request.androidid =Secure.getString(context.contentResolver, Secure.ANDROID_ID)
        request.businessid = buzz.data!!.businessid
        request.userid = -1
        request.userpassword ="123"
        request.mobileno = buzz.data.contactno
        request.userrole = "na"
        request.useraccess = -1
        request.deviceinfo = Build.BRAND + Build.MODEL
        request.status = "-1"


        viewModelScope.launch {
            when (val response = repository.addUser(request)) {
                is NetworkState.Success -> {
                    hideLoader()
                    addUserResponseLiveData.postValue(response.data!!)
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