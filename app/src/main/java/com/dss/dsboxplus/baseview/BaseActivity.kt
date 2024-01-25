package com.dss.dsboxplus.baseview

import android.app.Dialog
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.dss.dsboxplus.R
import com.dss.dsboxplus.data.repo.response.UserDetailsResponse
import com.dss.dsboxplus.preferences.AppPreferences
import com.dss.dsboxplus.preferences.AppPreferences.saveLongToSharedPreferences
import com.dss.dsboxplus.viewmodels.AppViewModelFactory
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.RetrofitService.Companion.getInstance
import java.io.File


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

    open fun showNoInternetDialog() {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton(
                "OK"
            ) { dialog, which -> finish() }
            .setCancelable(false)
            .show()
    }

    open fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    open fun openPDF(fileName: String) {
        Handler(Looper.myLooper()!!).postDelayed({
            try {
                val dsBox = this.baseContext.getExternalFilesDir("ds_box")

                val file = File(
                    dsBox!!.path,
                    fileName
                )
                Uri.fromFile(file)
                val pdfOpenintent = Intent(Intent.ACTION_VIEW)
//                pdfOpenintent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                val apkURI = FileProvider.getUriForFile(
                    this, getApplicationContext()
                        .getPackageName() + ".provider", file
                )
                pdfOpenintent.setDataAndType(apkURI, "application/pdf")
                pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(pdfOpenintent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, 1000)
    }

    open fun addUserDataToPreferences(userDetailsResponse: UserDetailsResponse) {
        val userId = userDetailsResponse.data!![0]!!.userid!!
        val businessId = userDetailsResponse.data!![0]!!.businessid!!
        val mobileno = userDetailsResponse.data!![0]!!.mobileno!!.toLong()
        saveLongToSharedPreferences(
            this,
            AppPreferences.USER_ID, userId.toLong()
        )
        saveLongToSharedPreferences(
            this,
            AppPreferences.BUSINESS_ID, businessId.toLong()
        )
        saveLongToSharedPreferences(
            this,
            AppPreferences.MOBILE_NUMBER, mobileno
        )
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