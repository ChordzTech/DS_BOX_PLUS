package com.dss.dsboxplus.viewmodels.profileviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.request.UpdateBusinessDetailsRequest
import com.dss.dsboxplus.data.repo.response.BusinessDetails
import com.dss.dsboxplus.data.repo.response.UpdateBusinessDetailsResponse
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class QuotationTermsActivityViewModel(val repository: MainRepository) : BaseViewModel() {
    var updateBusinessdetailsLiveData = MutableLiveData<UpdateBusinessDetailsResponse>()
        get() = field


    fun updateBusinessQuotation(
        estimateNote: String,
        businessDetails: BusinessDetails
    ) {
        showLoader()
        val request = UpdateBusinessDetailsRequest()

        request.gsm = businessDetails.gsm

        request.waste = businessDetails.waste!!.toInt()

        request.pincode = businessDetails.pincode

        request.burstingfactor = businessDetails.burstingfactor

        request.activationdate = businessDetails.activationdate
        request.subscriptiondate = businessDetails.subscriptiondate

        request.address = businessDetails.address

        request.estimatenote = estimateNote

        request.businessid = businessDetails.businessid

        request.tax = businessDetails.tax!!.toFloat()

        request.marginlength = businessDetails.marginlength

        request.marginwidth = businessDetails.marginwidth

        request.rate = businessDetails.rate!!.toFloat()

        request.businessname = businessDetails.businessname

        request.multiuser = businessDetails.multiuser

        request.flutefactor = businessDetails.flutefactor

        request.conversionrate = businessDetails.conversionrate!!.toFloat()

        request.profit = businessDetails.profit!!.toFloat()

        request.email = businessDetails.email

        request.contactno = businessDetails.contactno

        request.geolocation = businessDetails.geolocation

        request.status = businessDetails.status
        viewModelScope.launch {
            when (val response = repository.updateBusinessDetails(request)) {
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