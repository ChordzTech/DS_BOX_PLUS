//package com.dss.dsboxplus.data
//
//import com.dss.dsboxplus.data.remote.NetworkState
//import com.dss.dsboxplus.data.remote.RetrofitService
//
//
//class MainRepository constructor(private val retrofitService: RetrofitService) {
//
////    suspend fun getAllMovies(): NetworkState<ProductListResponse> {
////        val response = retrofitService.getAllMovies()
////        return if (response.isSuccessful) {
////            val responseBody = response.body()
////            if (responseBody != null) {
////                NetworkState.Success(responseBody)
////            } else {
////                NetworkState.Error(response)
////            }
////        } else {
////            NetworkState.Error(response)
////        }
////    }
//
//    suspend fun getBusinessList(): NetworkState<> {
//        val response = retrofitService.getBusinessList()
//        return if (response.isSuccessful) {
//            val responseBody = response.body()
//            if (responseBody != null) {
//                NetworkState.Success(responseBody)
//            } else {
//                NetworkState.Error(response)
//            }
//        } else {
//            NetworkState.Error(response)
//        }
//    }
//
//}