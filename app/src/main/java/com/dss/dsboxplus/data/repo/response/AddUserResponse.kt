package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

@Parcelize
data class AddUserResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String = "",

	@field:SerializedName("status")
	val status: String = ""
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("businessid")
	val businessid: Int = 0,

	@field:SerializedName("userrole")
	val userrole: String = "",

	@field:SerializedName("mobileno")
	val mobileno: String = "0",

	@field:SerializedName("useraccess")
	val useraccess: Int = 0,

	@field:SerializedName("userid")
	val userid: Int = 0,

	@field:SerializedName("deviceinfo")
	val deviceinfo: @RawValue String = "",

	@field:SerializedName("androidid")
	val androidid:  @RawValue String  = "",

	@field:SerializedName("userpassword")
	val userpassword: String = "",

	@field:SerializedName("username")
	val username: String = "",

	@field:SerializedName("status")
	val status: String = ""
) : Parcelable
