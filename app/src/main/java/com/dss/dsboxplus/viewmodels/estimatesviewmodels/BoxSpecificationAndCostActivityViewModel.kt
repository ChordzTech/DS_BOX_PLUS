package com.dss.dsboxplus.viewmodels.estimatesviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.DateUtils
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.CreateEstimateDataHolder
import com.dss.dsboxplus.data.repo.request.AddEstimateRequest
import com.dss.dsboxplus.data.repo.response.AddEstimateResponse
import com.dss.dsboxplus.data.repo.response.ClientDetails
import com.dss.dsboxplus.data.repo.response.DataItem
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class BoxSpecificationAndCostActivityViewModel(val repository: MainRepository) : BaseViewModel() {

    var createEstimateLiveData = MutableLiveData<AddEstimateResponse>()
        get() = field


    fun createOrUpdateEstimate(
        isUpdate: Boolean,
        dataItem: DataItem?,
        selectedClient: ClientDetails,
        boxName: String,
        lengthMm: Int,
        widthMm: Int,
        heightMm: Int,
        noOfPly: String,
        noOfBox: Int,
        cuttingLength: Double,
        decalSize: Double,
        cuttingMarginMm: Int,
        decalMarginMm: Int,
        topBf: Int,
        topGsm: Int,
        topRate: Float,
        f1Bf: Int,
        f1Gsm: Int,
        f1RateKg: Float,
        f1ff: Float,
        m1bf: Int,
        m1Gsm: Int,
        m1RateKg: Float,
        f2Bf: Int,
        f2Gsm: Int,
        f2RateKg: Float,
        f2ff: Float,
        m2bf: Int,
        m2Gsm: Int,
        m2RateKg: Float,
        f3Bf: Int,
        f3Gsm: Int,
        f3RateKg: Float,
        f3ff: Float,
        bottomBF: Int,
        bottomGsm: Int,
        bottomRateKg: Float,
        totalGsm: Double,
        totalBs: Double,
        totalWeight: Double,
//        wasteCost: Double,
//        grossPaperCost: Double,
//        convCost: Double,
        netPaperCost: Double,
        totalPaperCost: Float,
        boxMfg: Float,
        boxPrice: Float,
        boxPriceTax: Double,
        wasteInput: Float,
        convRate: Float,
        overHead: Float,
        tax: Float,
        profit: Float
    ) {
        showLoader()
        var createEstimateRequest = AddEstimateRequest()
        if (isUpdate)
            createEstimateRequest.estimateid = dataItem!!.estimateid
        createEstimateRequest.businessid = selectedClient.businessid
        createEstimateRequest.clientid = selectedClient.clientid!!
        createEstimateRequest.userid =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.USER_ID).toInt()
        createEstimateRequest.boxname = boxName
        createEstimateRequest.lengthMmField = lengthMm
        createEstimateRequest.widthMmField = widthMm
        createEstimateRequest.heightMmField = heightMm
        createEstimateRequest.lengthCmField = CreateEstimateDataHolder.lengthCm.toInt()
        createEstimateRequest.widthCmField = CreateEstimateDataHolder.widthCm.toInt()
        createEstimateRequest.heightCmField = CreateEstimateDataHolder.heightCm.toInt()
        createEstimateRequest.lengthInchField = CreateEstimateDataHolder.lengthInch.toInt()
        createEstimateRequest.widthInchField = CreateEstimateDataHolder.widthInch.toInt()
        createEstimateRequest.heightInchField = CreateEstimateDataHolder.heightInch.toInt()
        createEstimateRequest.ply = noOfPly.toInt()
        createEstimateRequest.ups = noOfBox
        createEstimateRequest.cuttinglength = cuttingLength.toInt()
        createEstimateRequest.decalsize = decalSize.toInt()
        createEstimateRequest.cuttinglengthmargin = cuttingMarginMm
        createEstimateRequest.decalsizemargin = decalMarginMm

        createEstimateRequest.topbf = topBf
        createEstimateRequest.topgsm = topGsm
        createEstimateRequest.toprate = topRate

        createEstimateRequest.f1bf = f1Bf
        createEstimateRequest.f1gsm = f1Gsm
        createEstimateRequest.f1rate = f1RateKg
        createEstimateRequest.f1ff = f1ff

        createEstimateRequest.m1bf = m1bf
        createEstimateRequest.m1gsm = m1Gsm
        createEstimateRequest.m1rate = m1RateKg

        createEstimateRequest.f2bf = f2Bf
        createEstimateRequest.f2gsm = f2Gsm
        createEstimateRequest.f2rate = f2RateKg
        createEstimateRequest.f2ff = f2ff

        createEstimateRequest.m2bf = m2bf
        createEstimateRequest.m2gsm = m2Gsm
        createEstimateRequest.m2rate = m2RateKg

        createEstimateRequest.f3bf = f3Bf
        createEstimateRequest.f3gsm = f3Gsm
        createEstimateRequest.f3rate = f3RateKg
        createEstimateRequest.f3ff = f3ff

        createEstimateRequest.bottombf = bottomBF
        createEstimateRequest.bottomgsm = bottomGsm
        createEstimateRequest.bottomrate = bottomRateKg

        createEstimateRequest.totalgsm = totalGsm.toInt()
        createEstimateRequest.totalbs = totalBs.toInt()
        createEstimateRequest.totalweight = totalWeight.toInt()
        createEstimateRequest.netpapercost = netPaperCost.toFloat()
        createEstimateRequest.totalpapercost = totalPaperCost
        createEstimateRequest.boxcost = boxMfg
        createEstimateRequest.boxprice = boxPrice
        createEstimateRequest.boxpricewithtax = boxPriceTax.toFloat()
        createEstimateRequest.waste = wasteInput
        createEstimateRequest.conversionrate = convRate
        createEstimateRequest.overheadcharges = overHead.toInt()

        createEstimateRequest.tax = tax
        createEstimateRequest.profit = profit
        createEstimateRequest.estimatedate = DateUtils.getDateInYYYYMMDDFormat()
        if (isUpdate) {
            viewModelScope.launch {
                when (val response = repository.updateEstimate(createEstimateRequest)) {
                    is NetworkState.Success -> {
                        hideLoader()
                        createEstimateLiveData.postValue(response.data!!)
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
        } else {
            viewModelScope.launch {
                when (val response = repository.addEstimate(createEstimateRequest)) {
                    is NetworkState.Success -> {
                        hideLoader()
                        createEstimateLiveData.postValue(response.data!!)
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
}