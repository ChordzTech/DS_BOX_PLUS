package com.dss.dsboxplus.data.repo.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateBusinessDetailsRequest(
    @field:SerializedName("gsm")
    var gsm: Int? = 0,

    @field:SerializedName("waste")
    var waste: Int? = 0,

    @field:SerializedName("pincode")
    var pincode: String? = null,

    @field:SerializedName("burstingfactor")
    var burstingfactor: Int? = 0,

    @field:SerializedName("activationdate")
    var activationdate: String? = null,

    @field:SerializedName("subscriptiondate")
    var subscriptiondate: String? = null,

    @field:SerializedName("address")
    var address: String? = null,

    @field:SerializedName("estimatenote")
    var estimatenote: String? = null,

    @field:SerializedName("businessid")
    var businessid: Int? = 0,

    @field:SerializedName("tax")
    var tax: Float? = 0F,

    @field:SerializedName("marginlength")
    var marginlength: Int? = 0,

    @field:SerializedName("marginwidth")
    var marginwidth: Int? = 0,

    @field:SerializedName("rate")
    var rate: Float? = 0F,

    @field:SerializedName("businessname")
    var businessname: String? = null,

    @field:SerializedName("multiuser")
    var multiuser: Int? = 0,

    @field:SerializedName("flutefactor")
    var flutefactor: Float? = 0F,

    @field:SerializedName("conversionrate")
    var conversionrate: Float? = 0F,

    @field:SerializedName("profit")
    var profit: Float? = 0F,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("contactno")
    var contactno: String? = null,

    @field:SerializedName("geolocation")
    var geolocation: String? = null,

    @field:SerializedName("status")
    var status: String? = null
) : Parcelable
