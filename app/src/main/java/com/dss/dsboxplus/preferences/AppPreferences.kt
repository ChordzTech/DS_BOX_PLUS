package com.dss.dsboxplus.preferences

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    const val IS_CREATING_ESTIMATE: String = "is_creating_estimate"
    const val MOBILE_NUMBER: String = "mobile_number"
    const val DEVICE_INFO: String = "device_info"
    const val USER_ID: String = "user_id"
    const val BUSINESS_ID: String = "business_id"
    const val CLIENT_ID:String="client_id"
    private var sharedPreferences: SharedPreferences? = null

    private const val SharedPreferencesName = "DSBOX_PREFERENCES"
    fun getStringValueFromSharedPreferences(key: String): String? {
        if (sharedPreferences != null) {
            return sharedPreferences!!.getString(key, "")
        }
        return ""
    }

    fun saveStringToSharedPreferences(context: Context, key: String, value: String) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE)
        }
        val edit = sharedPreferences!!.edit()
        edit.putString(key, value)
        edit.apply()
        edit.commit()
    }
    fun isCreatingEstimate(context: Context, key: String, value: Boolean) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE)
        }
        val edit = sharedPreferences!!.edit()
        edit.putBoolean(key, value)
        edit.apply()
        edit.commit()
    }

    fun getIsCreatingEstimate(key: String): Boolean {
        if (sharedPreferences != null) {
            return sharedPreferences!!.getBoolean(key, false)
        }
        return false;
    }
    fun getLongValueFromSharedPreferences(key: String): Long {
        if (sharedPreferences != null) {
            return sharedPreferences!!.getLong(key, 0)
        }
        return 0;
    }

    fun saveLongToSharedPreferences(context: Context, key: String, value: Long) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE)
        }
        val edit = sharedPreferences!!.edit()
        edit.putLong(key, value)
        edit.apply()
        edit.commit()
    }
}