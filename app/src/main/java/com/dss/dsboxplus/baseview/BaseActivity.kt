package com.dss.dsboxplus.baseview

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dss.dsboxplus.R
import com.dss.dsboxplus.viewmodels.AppViewModelFactory
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.RetrofitService.Companion.getInstance

open class BaseActivity : AppCompatActivity() {
    private lateinit var logoutDialog: AlertDialog
    private lateinit var loader: Dialog
    private lateinit var baseViewModel: BaseViewModel
    private lateinit var logoutBuilder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBaseViewModel()
        createAlertLoaderDialog();
    }

    private fun registerBaseViewModel() {
        val retrofitService = getInstance()
        val mainRepository = MainRepository(retrofitService)
        baseViewModel =
            ViewModelProvider(this, AppViewModelFactory(mainRepository)).get<BaseViewModel>(
                BaseViewModel::class.java
            )

    }

    private fun createAlertLoaderDialog() {
        loader = Dialog(this)
        loader.setCancelable(false)
        loader.setContentView(R.layout.loader)
        loader.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent);
    }

    fun showLoader() {
        if (!loader.isShowing) {
            loader.show()
        }
    }

    fun hideLoader() {
        if (loader.isShowing) {
            loader.dismiss()
        }
    }

    fun showLogoutPopUp() {
        logoutBuilder = AlertDialog.Builder(this)
        logoutBuilder.setTitle("DS BOX+")
        logoutBuilder.setMessage("Do you want to close the App?")
        logoutBuilder.setPositiveButton(
            "Ok"
        ) { _, _ -> finish() }
        logoutBuilder.setNegativeButton(
            "Cancel"
        ) { _, _ -> logoutDialog.dismiss() }
        logoutDialog = logoutBuilder.create();
        logoutDialog.setCancelable(false)
        logoutDialog.show()
    }
}