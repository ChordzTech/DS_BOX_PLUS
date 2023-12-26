package com.dss.dsboxplus.data.repo.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppConfigResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<AppConfigDataItems?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@Parcelize
data class AppConfigDataItems(

    @field:SerializedName("configid")
    val configid: Int? = null,

    @field:SerializedName("configname")
    val configname: String? = null,

    @field:SerializedName("configvalue")
    val configvalue: String? = null
) : Parcelable
