package com.example.mvvmretrofit.data.repo

import com.dss.dsboxplus.data.repo.response.AppConfigResponse
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.ClientListResponse
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import com.example.mvvmretrofit.data.repo.remote.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getEstimateList(): NetworkState<EstimateListResponse> {
        val response = retrofitService.getEstimateList()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    suspend fun getClientList(): NetworkState<ClientListResponse> {
        val response = retrofitService.getClientList()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    suspend fun getBusinessList(): NetworkState<BusinessDetailsResponse> {
        val response = retrofitService.getBusinessDetails()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    suspend fun getAppConfigList(): NetworkState<AppConfigResponse> {
        val response = retrofitService.getAppConfig()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}