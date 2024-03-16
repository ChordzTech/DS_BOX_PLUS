package com.dss.dsboxplus.viewmodels.clientsviewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.request.UpdateClientRequest
import com.dss.dsboxplus.data.repo.response.AddClientRequest
import com.dss.dsboxplus.data.repo.response.AddClientResponse
import com.dss.dsboxplus.data.repo.response.DeleteClientResponse
import com.dss.dsboxplus.data.repo.response.UpdateClientResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class ClientViewModel(val repository: MainRepository) : BaseViewModel() {
    var addClientRequestLiveData = MutableLiveData<AddClientResponse>()
        get() = field
    var updateClientRequestLiveData = MutableLiveData<UpdateClientResponse>()
        get() = field
    var requesrFailedLiveData = MutableLiveData<AddClientResponse>()
        get() = field
    var deleteClientRequestLiveData = MutableLiveData<DeleteClientResponse>()
        get() = field

    fun addClient(
        clientName: String,
        clientContactNo: String,
        clientAddress: String
    ) {
        showLoader()
        val request = AddClientRequest()

        request.clientname = clientName
        request.mobileno = clientContactNo
        request.address = clientAddress
        request.businessid =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID).toInt()

        viewModelScope.launch {
            when (val response = repository.addClient(request)) {
                is NetworkState.Success -> {
                    hideLoader()
                    addClientRequestLiveData.postValue(response.data!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        requesrFailedLiveData.postValue(response.response.body())
                    } else {
                        requesrFailedLiveData.postValue(response.response.body()!!)
                    }
                }
            }
        }
    }

    fun updateClient(
        clientId: Long,
        clientName: String,
        clientContactNo: String,
        clientAddress: String
    ) {
        showLoader()
        val request = UpdateClientRequest()

        request.clientid = clientId
        request.businessid =
            AppPreferences.getLongValueFromSharedPreferences(AppPreferences.BUSINESS_ID).toInt()
        request.clientname = clientName
        request.mobileno = clientContactNo
        request.address = clientAddress

        viewModelScope.launch {
            when (val response = repository.updateClient(request)) {
                is NetworkState.Success -> {
                    hideLoader()
                    updateClientRequestLiveData.postValue(response.data!!)
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        updateClientRequestLiveData.postValue(response.response.body())
                    } else {
//                        updateClientRequestLiveData.postValue(response.response.body()!!)
                    }
                }
            }
        }
    }

    fun deleteClient(clientid: Long, context: Context) {
        showLoader()

        viewModelScope.launch {
            when (val response = repository.deleteClient(clientid)) {
                is NetworkState.Success -> {

                    hideLoader()
                    if (response.data.code == 400) {
                        Toast.makeText(context, response.data.message, Toast.LENGTH_SHORT).show()
                    } else {
                        deleteClientRequestLiveData.postValue(response.data!!)
                    }
                }

                is NetworkState.Error -> {
                    hideLoader()
                    if (response.response.code() == 400) {
                        deleteClientRequestLiveData.postValue(response.response.body())
                    } else {
//                        updateClientRequestLiveData.postValue(response.response.body()!!)

                    }
                }
            }
        }
    }
}