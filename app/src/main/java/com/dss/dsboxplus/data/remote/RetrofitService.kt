//package com.dss.dsboxplus.data.remote
//
//
//import com.google.gson.GsonBuilder
//import okhttp3.Interceptor
//import okhttp3.Interceptor.*
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.GET
//
//
//interface RetrofitService {
//
//    @GET("BusinessDetails")
//    suspend fun getBusinessList() : Response<>
//
//    companion object {
//        var retrofitService: RetrofitService? = null
//        fun getInstance() : RetrofitService {
//
//            val httpClientBuilder = OkHttpClient.Builder()
//            httpClientBuilder.addInterceptor(Interceptor { chain ->
//                val requestBuilder: Request.Builder = chain.request().newBuilder()
//                requestBuilder.header("Content-Type", "application/json")
////                requestBuilder.header("Accept", "application/json")
//                chain.proceed(requestBuilder.build())
//            })
//
//            val gson = GsonBuilder()
//                .setLenient()
//                .create()
//            if (retrofitService == null) {
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://dsbox.beatsacademy.in/api/")
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .client(getLogger())
//                    .client(httpClientBuilder.build())
//                    .build()
//                retrofitService = retrofit.create(RetrofitService::class.java)
//            }
//            return retrofitService!!
//        }
//
//        private fun getHeaders(): OkHttpClient{
//            val httpClient = OkHttpClient()
//            val interceptors =httpClient.networkInterceptors as ArrayList<Interceptor>
//            interceptors.add(Interceptor { chain ->
//                val requestBuilder: Request.Builder = chain.request().newBuilder()
//                requestBuilder.header("Content-Type", "application/json")
//                chain.proceed(requestBuilder.build())
//            })
//            return httpClient;
//        }
//
//        private fun getLogger(): OkHttpClient {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            return OkHttpClient.Builder().addInterceptor(interceptor).build();
//        }
//
//    }
//}