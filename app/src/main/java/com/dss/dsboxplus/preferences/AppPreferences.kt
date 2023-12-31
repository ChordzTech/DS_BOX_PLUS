package com.dss.dsboxplus.preferences

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    const val MOBILE_NUMBER: String = "mobile_number"
    const val USER_ID: String = "user_id"
    const val BUSINESS_ID: String = "business_id"
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