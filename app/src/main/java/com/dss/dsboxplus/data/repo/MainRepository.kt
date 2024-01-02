package com.example.mvvmretrofit.data.repo

import com.dss.dsboxplus.data.repo.request.AddUserRequest
import com.dss.dsboxplus.data.repo.request.BusinessDetailsRequest
import com.dss.dsboxplus.data.repo.response.AddClientRequest
import com.dss.dsboxplus.data.repo.response.AddClientResponse
import com.dss.dsboxplus.data.repo.response.AddEstimateRequest
import com.dss.dsboxplus.data.repo.response.AddUserResponse
import com.dss.dsboxplus.data.repo.response.AppConfigResponse
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.ClientListResponse
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.dss.dsboxplus.data.repo.response.QrCodeResponse
import com.dss.dsboxplus.data.repo.response.SubscriptionDetailsResponse
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import com.example.mvvmretrofit.data.repo.remote.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getUserDetails(
        mobileno: String,
        deviceinfo: String
    ): NetworkState<UserDetailsResponse> {
        val response = retrofitService.getUserDetails(mobileno, deviceinfo)
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

    suspend fun getClientList(businessId: Long): NetworkState<ClientListResponse> {
        val response = retrofitService.getClientList(businessId)
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

    suspend fun getSubscriptionList(): NetworkState<SubscriptionDetailsResponse> {
        val response = retrofitService.getSubscriptionDetails()
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

    suspend fun getQrcode(): NetworkState<QrCodeResponse> {
        val response = retrofitService.getQrCode()
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

    suspend fun getEstimateListByBusinessUserID(
        businessId: Long,
        userId: Long
    ): NetworkState<EstimateListResponse> {
        val response = retrofitService.getEstimateByBusinessIdUserId(businessId, userId)
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

    suspend fun addBusinessDetails(businessDetailsRequest: BusinessDetailsRequest): NetworkState<BusinessDetailsResponse> {
        val response = retrofitService.addBusinessDetails(businessDetailsRequest)
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

    suspend fun addUser(request: AddUserRequest): NetworkState<AddUserResponse> {
        val response = retrofitService.addUser(request)
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
    } suspend fun addEstimate(request: AddEstimateRequest): NetworkState<EstimateListResponse> {
        val response = retrofitService.addEstimate(request)
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
    suspend fun addClient(request: AddClientRequest):NetworkState<AddClientResponse>{
        val response = retrofitService.addClient(request)
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