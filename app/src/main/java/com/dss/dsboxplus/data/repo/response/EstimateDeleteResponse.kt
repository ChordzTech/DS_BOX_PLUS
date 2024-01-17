package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EstimateDeleteResponse(

	@field:SerializedName("code")
	val code: Int? = 0,

	@field:SerializedName("message")
	val message: String? = "",

	@field:SerializedName("status")
	val status: String? = ""
) : Parcelable
