package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

@Parcelize
data class GetSubForBusinessResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<SubForBusiness?>? = arrayListOf(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class SubForBusiness(

	@field:SerializedName("end_date")
	val endDate: String? = "",

	@field:SerializedName("remaining_days")
	val remainingDays: @RawValue Any? = ""
) : Parcelable
