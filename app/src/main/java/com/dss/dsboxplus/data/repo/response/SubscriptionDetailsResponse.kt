package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SubscriptionDetailsResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: ArrayList<SubscriptionDataItem?>? = arrayListOf(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class SubscriptionDataItem(

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("subscription")
	val subscription: String? = null,

	@field:SerializedName("subscriptionid")
	val subscriptionid: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
