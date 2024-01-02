package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddClientRequest(

	@field:SerializedName("clientid")
	val clientid: Int? = 0,

	@field:SerializedName("address")
	var address: String? = "",

	@field:SerializedName("clientname")
	var clientname: String? = "",

	@field:SerializedName("businessid")
	var businessid: Int? = 0,

	@field:SerializedName("mobileno")
	var mobileno: String? = ""
) : Parcelable
