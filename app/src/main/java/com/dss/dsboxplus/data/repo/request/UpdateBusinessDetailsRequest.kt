package com.dss.dsboxplus.data.repo.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateBusinessDetailsRequest(
    @field:SerializedName("gsm")
    var gsm: Int? = 0,

    @field:SerializedName("waste")
    var waste: Int? = 0,

    @field:SerializedName("pincode")
    var pincode: String? = "",

    @field:SerializedName("burstingfactor")
    var burstingfactor: Int? = 0,

    @field:SerializedName("activationdate")
    var activationdate: String? = "",

    @field:SerializedName("subscriptiondate")
    var subscriptiondate: String? = "",

    @field:SerializedName("address")
    var address: String? = "",

    @field:SerializedName("estimatenote")
    var estimatenote: String? = "",

    @field:SerializedName("businessid")
    var businessid: Int? = 0,

    @field:SerializedName("tax")
    var tax: Int? = 0,

    @field:SerializedName("marginlength")
    var marginlength: Int? = 0,

    @field:SerializedName("marginwidth")
    var marginwidth: Int? = 0,

    @field:SerializedName("rate")
    var rate: Int? = 0,

    @field:SerializedName("businessname")
    var businessname: String? = "",

    @field:SerializedName("multiuser")
    var multiuser: Int? = 0,

    @field:SerializedName("flutefactor")
    var flutefactor: Int? = 0,

    @field:SerializedName("conversionrate")
    var conversionrate: Int? = 0,

    @field:SerializedName("profit")
    var profit: Int? = 0,

    @field:SerializedName("email")
    var email: String? = "",

    @field:SerializedName("contactno")
    var contactno: String? = "",

    @field:SerializedName("geolocation")
    var geolocation: String? = "",

    @field:SerializedName("status")
    var status: String? = ""
): Parcelable
