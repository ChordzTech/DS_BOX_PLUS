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
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("businessid")
	val businessid: Int? = null,

	@field:SerializedName("userrole")
	val userrole: String? = null,

	@field:SerializedName("mobileno")
	val mobileno: String? = null,

	@field:SerializedName("useraccess")
	val useraccess: Int? = null,

	@field:SerializedName("userid")
	val userid: Int? = null,

	@field:SerializedName("deviceinfo")
	val deviceinfo: @RawValue Any? = null,

	@field:SerializedName("androidid")
	val androidid:  @RawValue Any?  = null,

	@field:SerializedName("userpassword")
	val userpassword: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
