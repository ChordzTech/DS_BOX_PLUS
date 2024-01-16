package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateSubUserResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: UpdateSubUser? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class UpdateSubUser(

	@field:SerializedName("businessid")
	val businessid: Int? = null,

	@field:SerializedName("userrole")
	val userrole: String? = null,

	@field:SerializedName("mobileno")
	val mobileno: String? = null,

	@field:SerializedName("useraccess")
	val useraccess: Int? = null,

	@field:SerializedName("userid")
	val userid: Long? = null,

	@field:SerializedName("deviceinfo")
	val deviceinfo: String? = null,

	@field:SerializedName("androidid")
	val androidid: String? = null,

	@field:SerializedName("userpassword")
	val userpassword: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
