package com.dss.dsboxplus.viewmodels.profileviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.request.UpdateBusinessDetailsRequest
import com.dss.dsboxplus.data.repo.response.BusinessDetails
import com.dss.dsboxplus.data.repo.response.UpdateBusinessDetailsResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class BusinessSettingsActivityViewModel (val repository: MainRepository) : BaseViewModel(){

    var updateBusinessdetailsLiveData = MutableLiveData<UpdateBusinessDetailsResponse>()
        get() = field

    fun updateBusinessDetails(
        businessName: String,
        businessContact: String,
        businessAddress: String,
        businessPinCode: String,
        businessEmail: String,
        businessDetails: BusinessDetails
    ) {
        showLoader()
        val request = UpdateBusinessDetailsRequest()

        request.gsm = businessDetails.gsm

        request.waste = businessDetails.waste

        request.pincode = businessPinCode

        request.burstingfactor = businessDetails.burstingfactor

        request.activationdate = businessDetails.activationdate
        request.subscriptiondate = businessDetails.subscriptiondate

        request.address = businessAddress

        request.estimatenote = businessDetails.estimatenote

        request.businessid = businessDetails.businessid

        request.tax = businessDetails.tax!!.toFloat()

        request.marginlength = businessDetails.marginlength

        request.marginwidth = businessDetails.marginwidth

        request.rate = businessDetails.rate!!.toFloat()

        request.businessname = businessName

        request.multiuser = businessDetails.multiuser

        request.flutefactor = businessDetails.flutefactor

        request.conversionrate = businessDetails.conversionrate!!.toFloat()

        request.profit = businessDetails.profit!!.toFloat()

        request.email = businessEmail

        request.contactno =businessContact

        request.geolocation = businessDetails.geolocation

        request.status = businessDetails.status

        viewModelScope.launch {
            when (val response=repository.updateBusinessDetails(request)){
                is NetworkState.Success -> {
                    hideLoader()
                    updateBusinessdetailsLiveData.postValue(response.data!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        updateBusinessdetailsLiveData.postValue(response.response.body())
                    } else {
//                        updateClientRequestLiveData.postValue(response.response.body()!!)
                    }
                }
            }
        }
    }
}