package com.dss.dsboxplus.data.repo.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddUserRequest(

	@field:SerializedName("businessid")
	var businessid: Int? = 0,

	@field:SerializedName("userrole")
	var userrole: String? = "user",

	@field:SerializedName("mobileno")
	var mobileno: String? = "",

	@field:SerializedName("useraccess")
	var useraccess: Int? = 0,

	@field:SerializedName("userid")
	var userid: Int? = 0,

	@field:SerializedName("deviceinfo")
	var deviceinfo: String? = "",

	@field:SerializedName("androidid")
	var androidid: String? = "",

	@field:SerializedName("userpassword")
	var userpassword: String? = "user",

	@field:SerializedName("username")
	var username: String? = "",

	@field:SerializedName("status")
	var status: String? = ""
) : Parcelable
