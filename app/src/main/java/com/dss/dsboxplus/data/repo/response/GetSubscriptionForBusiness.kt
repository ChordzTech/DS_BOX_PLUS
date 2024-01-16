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
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class SubscriptionForBusiness(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("remaining_days")
	val remainingDays: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
