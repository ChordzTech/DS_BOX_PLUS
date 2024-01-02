package com.dss.dsboxplus.viewmodels.clientsviewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.data.repo.response.AddClientRequest
import com.dss.dsboxplus.data.repo.response.ClientListResponse
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.NetworkState
import kotlinx.coroutines.launch

class AddNewClientsActivityViewModel(val repository: MainRepository) : BaseViewModel() {
    var addClientRequestLiveData = MutableLiveData<ClientListResponse>()
        get() = field

    fun addClient(
        clientName: String,
        clientContactNo: String,
        clientAddress: String
    ) {
        showLoader()
        val request=AddClientRequest()
        request.clientname=clientName
        request.mobileno=clientContactNo
        request.address=clientAddress

        viewModelScope.launch {
            when (val response = repository.addClient(request)) {
                is NetworkState.Success -> {
                    hideLoader()
                    addClientRequestLiveData.postValue(response.data!!)
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