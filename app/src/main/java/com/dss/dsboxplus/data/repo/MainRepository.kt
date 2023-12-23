package com.example.mvvmretrofit.data.repo

import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import com.example.mvvmretrofit.data.repo.remote.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getEstimateList() : NetworkState<EstimateListResponse>{
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

}