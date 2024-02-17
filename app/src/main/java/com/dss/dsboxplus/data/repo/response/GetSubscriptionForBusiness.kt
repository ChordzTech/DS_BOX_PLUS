package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GetSubscriptionForBusiness(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<SubscriptionForBusiness?>? = arrayListOf(),

    @field:SerializedName("message")
    val message: String? = "",

    @field:SerializedName("status")
    val status: String? = ""
) : Parcelable

@Parcelize
data class SubscriptionForBusiness(
//	"subscription_date": "2023-12-31",

    @field:SerializedName("subscription_date")
    val subscriptionDate: String? = "",

    @field:SerializedName("end_date")
    val endDate: String? = "",

    @field:SerializedName("remaining_days")
    val remainingDays: Int? = 0,

    @field:SerializedName("status")
    val status: String? = ""
) : Parcelable
