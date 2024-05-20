package com.dss.dsboxpro.baseview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val loaderLiveData = MutableLiveData<Boolean>()

    fun showLoader() {
        loaderLiveData.postValue(true)
    }

    fun hideLoader() {
        loaderLiveData.postValue(false)
    }

}