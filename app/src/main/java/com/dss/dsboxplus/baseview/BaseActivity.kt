package com.dss.dsboxplus.baseview

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.dss.dsboxplus.R
import com.dss.dsboxplus.viewmodels.AppViewModelFactory
import com.example.mvvmretrofit.data.repo.MainRepository
import com.example.mvvmretrofit.data.repo.remote.RetrofitService.Companion.getInstance
import java.io.File
import java.lang.reflect.Method

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
    open fun viewFile(pdfFile: File) {
        Log.e("!!pdfFile",pdfFile.absolutePath)

        val path: Uri
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val m: Method = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            path = FileProvider.getUriForFile(this, applicationContext.packageName+ ".provider", pdfFile)

            // path = Uri.parse(pdfFile.getAbsolutePath());

            // pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setDataAndType(path, "application/pdf")
            pdfIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            path = Uri.fromFile(pdfFile)
            pdfIntent.setDataAndType(path, "application/pdf")
        }


        /* Uri path = FileProvider.getUriForFile(getActivity(),
        BuildConfig.APPLICATION_ID + ".provider",
        pdfFile);*/
        try {
            startActivity(pdfIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.lblnoapplicationfound), Toast.LENGTH_SHORT).show()
        }
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