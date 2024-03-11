package com.example.mvvmretrofit.data.repo

import com.dss.dsboxplus.data.repo.request.AddUserRequest
import com.dss.dsboxplus.data.repo.request.BusinessDetailsRequest
import com.dss.dsboxplus.data.repo.request.UpdateBusinessDetailsRequest
import com.dss.dsboxplus.data.repo.request.UpdateClientRequest
import com.dss.dsboxplus.data.repo.response.AddClientRequest
import com.dss.dsboxplus.data.repo.response.AddClientResponse
import com.dss.dsboxplus.data.repo.request.AddEstimateRequest
import com.dss.dsboxplus.data.repo.response.AddEstimateResponse
import com.dss.dsboxplus.data.repo.response.AddUserResponse
import com.dss.dsboxplus.data.repo.response.AppConfigResponse
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.ClientListResponse
import com.dss.dsboxplus.data.repo.response.DeleteClientResponse
import com.dss.dsboxplus.data.repo.response.DeleteSubUserResponse
import com.dss.dsboxplus.data.repo.response.EstimateDeleteResponse
import com.dss.dsboxplus.data.repo.response.EstimateListResponse
import com.dss.dsboxplus.data.repo.response.GetClientByClientIdResponse
import com.dss.dsboxplus.data.repo.response.GetSubscriptionForBusiness
import com.dss.dsboxplus.data.repo.response.QrCodeResponse
import com.dss.dsboxplus.data.repo.response.SubUserDetailsResponse
import com.dss.dsboxplus.data.repo.response.SubscriptionDetailsResponse
import com.dss.dsboxplus.data.repo.response.UpdateBusinessDetailsResponse
import com.dss.dsboxplus.data.repo.response.UpdateClientResponse
import com.dss.dsboxplus.data.repo.response.UpdateSubUserRequest
import com.dss.dsboxplus.data.repo.response.UpdateSubUserResponse
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import com.example.mvvmretrofit.data.repo.remote.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getOTP(phoneNumber: Long, otp: String): NetworkState<Any> {

        val response = retrofitService.getOTP(
            "dishaswaraj",
            "Disha3332",
            "trans1",
            "SDISHA",
            phoneNumber.toString(),
            "OTP for DS Box Plus App is "+otp+"%0A%0AFrom : Box Costing%0AContact Support :%0ARahul%0A9421013332",
            "1207161675123262002"
        )
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

    suspend fun getUsersByBusiness(businessId: Long): NetworkState<SubUserDetailsResponse> {
        val response = retrofitService.getUsersByBusinessId(businessId)
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

    suspend fun getBusinessDetailsByBusinessID(businessId: Long): NetworkState<BusinessDetailsResponse> {
        val response = retrofitService.getBusinessDetailsByBusinessID(businessId)
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
        userId: Long,
        startingIndex:Int,
        limit:Int
    ): NetworkState<EstimateListResponse> {
        val response = retrofitService.getEstimateByBusinessIdUserId(businessId, userId,startingIndex,limit)
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

    suspend fun getClientByClintId(
        clientId: Long
    ): NetworkState<GetClientByClientIdResponse> {
        val response = retrofitService.getClientByClientId(clientId)
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

    suspend fun getEstimateByClientID(
        clientId: Long,
        userid:Long
    ): NetworkState<EstimateListResponse> {
        val response = retrofitService.getEstimateByClientId(clientId,userid)
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
    }

    suspend fun addEstimate(request: AddEstimateRequest): NetworkState<AddEstimateResponse> {
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

    suspend fun updateEstimate(request: AddEstimateRequest): NetworkState<AddEstimateResponse> {
        val response = retrofitService.updateEstimate(request, request.estimateid!!)
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

    suspend fun addClient(request: AddClientRequest): NetworkState<AddClientResponse> {
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

    suspend fun updateClient(request: UpdateClientRequest): NetworkState<UpdateClientResponse> {
        val response = retrofitService.updateClientDetails(request.clientid!!, request)
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

    suspend fun updateSubUser(request: UpdateSubUserRequest): NetworkState<UpdateSubUserResponse> {
        val response = retrofitService.updateUser(request.userid!!.toLong(), request)
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


    suspend fun deleteClient(clientId: Long): NetworkState<DeleteClientResponse> {
        val response = retrofitService.deleteClient(clientId)
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

    suspend fun deleteSubUser(userId: Long): NetworkState<DeleteSubUserResponse> {
        val response = retrofitService.deleteSubUser(userId)
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

    suspend fun updateBusinessDetails(request: UpdateBusinessDetailsRequest): NetworkState<UpdateBusinessDetailsResponse> {
        val response = retrofitService.updateBusinessDetails(request.businessid!!.toLong(), request)
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

    suspend fun getSubForBusiness(
        businessId: Long
    ): NetworkState<GetSubscriptionForBusiness> {
        val response = retrofitService.getSubForBusiness(businessId)
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

    suspend fun deleteEstimate(estimateid: Long): NetworkState<EstimateDeleteResponse> {
        val response = retrofitService.deleteEstimate(estimateid)
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