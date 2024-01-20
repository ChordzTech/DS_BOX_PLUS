package com.dss.dsboxplus.viewmodels.estimatesviewmodels

import com.dss.dsboxplus.baseview.BaseViewModel
import com.example.mvvmretrofit.data.repo.MainRepository

class NewEstimatesActivityViewModel(val repository: MainRepository) : BaseViewModel() {
//    var addEstimateLiveData = MutableLiveData<AddEstimateResponse>()
//        get() = field
//
//    fun addEstimate(
//        buzz: BusinessDetailsResponse,
//        boxName: String,
//        lengthMm: Int,
//        widthMm: Int,
//        heightMm: Int,
//        lengthCm: Int,
//        widthCm: Int,
//        heightCm: Int,
//        lengthInch: Int,
//        widthInch: Int,
//        heightInch: Int,
//        ply: Int,
//        noOfBox: Int,
//        cuttingLengthTiet: Int,
//        decalSizeTiet: Int,
//        cuttingLengthMargin: Int,
//        decalSizeMargin: Int,
//    ) {
//        showLoader()
//
//        val request = AddEstimateRequest()
//        request.businessid = buzz.data!!.businessid
//        request.boxname = boxName
//        request.lengthMmField = lengthMm
//        request.widthMmField = widthMm
//        request.heightMmField = heightMm
//        request.ply = ply
//        request.ups = noOfBox
//        request.cuttinglength = cuttingLengthTiet
//        request.decalsize = decalSizeTiet
//        request.cuttinglengthmargin = cuttingLengthMargin
//        request.decalsizemargin = decalSizeMargin
//        viewModelScope.launch {
//            when (val response = repository.addEstimate(request)) {
//                is NetworkState.Success -> {
//                    hideLoader()
//                    addEstimateLiveData.postValue(response.data!!)
//                }
//
//                is NetworkState.Error -> {
//                    hideLoader()
//                    if (response.response.code() == 401) {
////                        estimateList.postValue(NetworkState.Error())
//                    } else {
////                        estimateList.postValue(NetworkState.Error)
//                    }
//                }
//            }
//        }
//    }
}