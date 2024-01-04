package com.dss.dsboxplus.data.repo.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateClientRequest(

	@field:SerializedName("clientid")
	var clientid: Long? = null,

	@field:SerializedName("address")
	var address: String? = null,

	@field:SerializedName("clientname")
	var clientname: String? = null,

	@field:SerializedName("businessid")
	var businessid: Int? = null,

	@field:SerializedName("mobileno")
	var mobileno: String? = null
) : Parcelable
