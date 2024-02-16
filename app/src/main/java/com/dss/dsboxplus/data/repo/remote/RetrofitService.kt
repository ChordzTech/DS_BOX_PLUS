package com.example.mvvmretrofit.data.repo.remote

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
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RetrofitService {

    //Estimate list APIs
    @GET("EstimateDetails/")
    suspend fun getEstimateList(): Response<EstimateListResponse>


    //client list APIs
    @PUT("ClientsDetails/{clientid}/")
    suspend fun updateClientDetails(
        @Path(value = "clientid") clientid: Long,
        @Body request: UpdateClientRequest
    ): Response<UpdateClientResponse>

    @PATCH("UserDetails/{userid}/")
    suspend fun updateSubUser(
        @Path(value = "userid") userId: Long,
        @Body request: UpdateSubUserRequest
    ): Response<UpdateSubUserResponse>

    @DELETE("ClientsDetails/{clientid}/")
    suspend fun deleteClient(
        @Path(value = "clientid") clientid: Long
    ): Response<DeleteClientResponse>

    @DELETE("UserDetails/{userid}/")
    suspend fun deleteSubUser(@Path(value = "userid") userId: Long): Response<DeleteSubUserResponse>

    //client list APIs
    @GET("GetClientByB/{businessid}/")
    suspend fun getClientList(@Path(value = "businessid") businessId: Long): Response<ClientListResponse>

    @POST("ClientsDetails/")
    suspend fun addClient(@Body request: AddClientRequest): Response<AddClientResponse>

    //Business Details APIs
    @GET("BusinessDetails/")
    suspend fun getBusinessDetails(): Response<BusinessDetailsResponse>

    @GET("BusinessDetails/{businessid}/")
    suspend fun getBusinessDetailsByBusinessID(
        @Path(value = "businessid") businessId: Long
    ): Response<BusinessDetailsResponse>

    @PATCH("BusinessDetails/{businessid}/")
    suspend fun updateBusinessDetails(
        @Path(value = "businessid") businessId: Long,
        @Body request: UpdateBusinessDetailsRequest
    ): Response<UpdateBusinessDetailsResponse>


    @POST("BusinessDetails/")
    suspend fun addBusinessDetails(@Body businessDetailsRequest: BusinessDetailsRequest): Response<BusinessDetailsResponse>

    //AppConfig
    @GET("AppConfig/")
    suspend fun getAppConfig(): Response<AppConfigResponse>

    @GET("SubscriptionsDetails/")
    suspend fun getSubscriptionDetails(): Response<SubscriptionDetailsResponse>

    @GET("UploadCode/")
    suspend fun getQrCode(): Response<QrCodeResponse>

    @GET("GetEstimatesByUB/{businessid}/{userid}/")
    suspend fun getEstimateByBusinessIdUserId(
        @Path(value = "businessid") businessId: Long, @Path(value = "userid") userId: Long
    ): Response<EstimateListResponse>

    @GET("ClientsDetails/{clientid}/")
    suspend fun getClientByClientId(@Path(value = "clientid") clientid: Long): Response<GetClientByClientIdResponse>


    @GET("GetEstimatesByClient/{clientid}/")
    suspend fun getEstimateByClientId(
        @Path(value = "clientid") clientId: Long,
    ): Response<EstimateListResponse>


    @GET("GetUserDetails/{mobileno}/{androidid}")
    suspend fun getUserDetails(
        @Path(value = "mobileno") mobileno: String, @Path(value = "androidid") deviceinfo: String
    ): Response<UserDetailsResponse>

    @GET("GetSubUserList/{businessid}/")
    suspend fun getUsersByBusinessId(
        @Path(value = "businessid") businessId: Long,
    ): Response<SubUserDetailsResponse>


    @POST("UserDetails/")
    suspend fun addUser(@Body request: AddUserRequest): Response<AddUserResponse>

    @POST("EstimateDetails/")
    suspend fun addEstimate(@Body request: AddEstimateRequest): Response<AddEstimateResponse>

    @PUT("EstimateDetails/{estimateid}/")
    suspend fun updateEstimate(
        @Body request: AddEstimateRequest,
        @Path(value = "estimateid") deleteEstimate: Long
    ): Response<AddEstimateResponse>

    @GET("SubscriptionforBusiness/{businessid}")
    suspend fun getSubForBusiness(
        @Path(value = "businessid") businessId: Long,
    ): Response<GetSubscriptionForBusiness>

    @DELETE("EstimateDetails/{estimateid}/")
    suspend fun deleteEstimate(
        @Path(value = "estimateid") deleteEstimate: Long,
    ): Response<EstimateDeleteResponse>


    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {

            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.addInterceptor(Interceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                chain.proceed(requestBuilder.build())
            })

            val gson = GsonBuilder().setLenient().create()
            if (retrofitService == null) {

                //This Below Url Has to be Replaced with Current Url
                // https://dsboxplus.dishaswaraj.in/api/

                val retrofit = Retrofit.Builder().baseUrl("https://dsboxplus.dishaswaraj.in/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getLogger())
                    .client(httpClientBuilder.build()).build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

        private fun getHeaders(): OkHttpClient {
            val httpClient = OkHttpClient()
            val interceptors = httpClient.networkInterceptors as ArrayList<Interceptor>
            interceptors.add(Interceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                chain.proceed(requestBuilder.build())
            })
            return httpClient;
        }

        private fun getLogger(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return OkHttpClient.Builder().addInterceptor(interceptor).build();
        }

    }
}