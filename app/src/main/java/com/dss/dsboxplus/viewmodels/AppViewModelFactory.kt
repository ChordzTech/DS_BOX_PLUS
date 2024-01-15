package com.dss.dsboxplus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dss.dsboxplus.baseview.BaseViewModel
import com.dss.dsboxplus.viewmodels.clientsviewmodels.ClientViewModel
import com.dss.dsboxplus.viewmodels.clientsviewmodels.EstimateListViewModel
import com.dss.dsboxplus.viewmodels.estimatesviewmodels.BoxEstimatesDetailsActivityViewModel
import com.dss.dsboxplus.viewmodels.estimatesviewmodels.BoxSpecificationAndCostActivityViewModel
import com.dss.dsboxplus.viewmodels.homeandotpviewmodels.EnterBusinessDetailsActivityViewModel
import com.dss.dsboxplus.viewmodels.homeviewmodel.HomeViewModel
import com.dss.dsboxplus.viewmodels.homeviewmodel.SplashViewModel
import com.dss.dsboxplus.viewmodels.profileviewmodels.AddSubUserViewModel
import com.dss.dsboxplus.viewmodels.profileviewmodels.DefaultPaperSettingsActivityViewModel
import com.dss.dsboxplus.viewmodels.profileviewmodels.DefaultRateSettingsActivityViewModel
import com.dss.dsboxplus.viewmodels.profileviewmodels.QuotationTermsActivityViewModel
import com.dss.dsboxplus.viewmodels.profileviewmodels.SuperUserViewModel
import com.example.mvvmretrofit.data.repo.MainRepository

class AppViewModelFactory constructor(private val repository: MainRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            BaseViewModel() as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            SplashViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EnterBusinessDetailsActivityViewModel::class.java)) {
            EnterBusinessDetailsActivityViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ClientViewModel::class.java)) {
            ClientViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(BoxSpecificationAndCostActivityViewModel::class.java)) {
            BoxSpecificationAndCostActivityViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(EstimateListViewModel::class.java)) {
            EstimateListViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(AddSubUserViewModel::class.java)) {
            AddSubUserViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DefaultPaperSettingsActivityViewModel::class.java)) {
            DefaultPaperSettingsActivityViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DefaultRateSettingsActivityViewModel::class.java)) {
            DefaultRateSettingsActivityViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(QuotationTermsActivityViewModel::class.java)) {
            QuotationTermsActivityViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SuperUserViewModel::class.java)) {
            SuperUserViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(BoxEstimatesDetailsActivityViewModel::class.java)) {
            BoxEstimatesDetailsActivityViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}