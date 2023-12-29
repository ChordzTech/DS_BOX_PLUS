package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QrCodeResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("base64_code")
	val base64Code: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
