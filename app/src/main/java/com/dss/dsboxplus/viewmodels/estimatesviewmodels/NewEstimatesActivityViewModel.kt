package com.dss.dsboxplus.viewmodels.estimatesviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.AddEstimateRequest
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.example.mvvmretrofit.data.repo.MainRepository

class NewEstimatesActivityViewModel(val repository: MainRepository) : BaseViewModel(){
    var addEstimateLiveData=MutableLiveData<EstimateListResponse>()
        get()=field
    fun addEstimate(
        buzz: BusinessDetailsResponse,
        boxName:String,
        lengthMm:Int,
        widthMm:Int,
        height:Int,
        ply:Int,
        cuttingLengthTiet:Int,
        decalSizeTiet:Int,
        cuttingLengthMargin:Int,
        decalSizeMargin:Int,
    ){
      showLoader()

      val request=AddEstimateRequest()
      request.businessid=buzz.data!!.businessid

    }
}