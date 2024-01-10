package com.dss.dsboxplus.viewmodels.profileviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.request.UpdateBusinessDetailsRequest
import com.dss.dsboxplus.data.repo.response.UpdateBusinessDetailsResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class DefaultPaperSettingsActivityViewModel(val repository: MainRepository) : BaseViewModel(){


    var updateBusinessdetailsLiveData = MutableLiveData<UpdateBusinessDetailsResponse>()
        get() = field


    fun updatebusinessDetails(

        cuttingMargin: String,
        decalMargin: String,
        fluteFactor: String
    ) {
        showLoader()
        val request=UpdateBusinessDetailsRequest()
        request.businessid =AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID).toInt()

        request.marginlength=cuttingMargin.toInt()
        request.marginwidth=decalMargin.toInt()
        request.flutefactor=fluteFactor.toInt()

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