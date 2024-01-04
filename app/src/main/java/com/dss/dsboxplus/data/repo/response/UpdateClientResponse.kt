package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateClientResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: UpdateClient? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class UpdateClient(

	@field:SerializedName("clientid")
	val clientid: Long? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("clientname")
	val clientname: String? = null,

	@field:SerializedName("businessid")
	val businessid: Int? = null,

	@field:SerializedName("mobileno")
	val mobileno: String? = null
) : Parcelable
