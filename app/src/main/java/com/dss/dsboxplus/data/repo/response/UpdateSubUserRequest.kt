package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateSubUserRequest(

	@field:SerializedName("businessid")
	var businessid: Int? = null,

	@field:SerializedName("userrole")
	val userrole: String? = null,

	@field:SerializedName("mobileno")
	val mobileno: String? = null,

	@field:SerializedName("useraccess")
    var useraccess: Int? = null,

	@field:SerializedName("userid")
	var userid: Int? = null,

	@field:SerializedName("deviceinfo")
    var deviceinfo: String? = null,

	@field:SerializedName("androidid")
	var androidid: String? = null,

	@field:SerializedName("userpassword")
	val userpassword: String? = null,

	@field:SerializedName("username")
	var username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
