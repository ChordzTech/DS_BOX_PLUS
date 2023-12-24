package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ClientListResponse(

	@field:SerializedName("code")
	val code: Long? = null,

	@field:SerializedName("data")
	val data: List<Client?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Client(

	@field:SerializedName("clientid")
	val clientid: Long? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("clientname")
	val clientname: String? = null,

	@field:SerializedName("businessid")
	val businessid: Long? = null,

	@field:SerializedName("mobileno")
	val mobileno: String? = null
) : Parcelable
