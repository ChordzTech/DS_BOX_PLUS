package com.dss.dsboxplus.data.repo.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class AddEstimateResponse(

    @field:SerializedName("code")
    val code: Double? = 0.0,

    @field:SerializedName("data")
    val data: AddEstimateDetails? = null,

    @field:SerializedName("message")
    val message: String? = "",

    @field:SerializedName("status")
    val status: String? = ""
) : Parcelable

@Parcelize
data class AddEstimateDetails(

    @field:SerializedName("clientid")
    val clientid: Long? = 0,

    @field:SerializedName("totalweight")
    val totalweight: Double? = 0.0,

    @field:SerializedName("m1gsm")
    val m1gsm: Double? = 0.0,

    @field:SerializedName("height_cm_field")
    val heightCmField: @RawValue Any? = null,

    @field:SerializedName("userid")
    val userid: Double? = 0.0,

    @field:SerializedName("boxname")
    val boxname: String? = "",

    @field:SerializedName("length_inch_field")
    val lengthInchField: @RawValue Any? = null,

    @field:SerializedName("m1bf")
    val m1bf: Double? = 0.0,

    @field:SerializedName("width_mm_field")
    val widthMmField: Double? = 0.0,

    @field:SerializedName("f1rate")
    val f1rate: @RawValue Any? = null,

    @field:SerializedName("bottomgsm")
    val bottomgsm: Double? = 0.0,

    @field:SerializedName("f1bf")
    val f1bf: Double? = 0.0,

    @field:SerializedName("bottombf")
    val bottombf: Double? = 0.0,

    @field:SerializedName("waste")
    val waste: Double? = 0.0,

    @field:SerializedName("f3ff")
    val f3ff: Double? = 0.0,

    @field:SerializedName("f1gsm")
    val f1gsm: Double? = 0.0,

    @field:SerializedName("width_cm_field")
    val widthCmField: @RawValue Any? = null,

    @field:SerializedName("f1ff")
    val f1ff: @RawValue Any? = null,

    @field:SerializedName("length_cm_field")
    val lengthCmField: @RawValue Any? = null,

    @field:SerializedName("businessid")
    val businessid: Double? = 0.0,

    @field:SerializedName("toprate")
    val toprate: @RawValue Any? = null,

    @field:SerializedName("totalpapercost")
    val totalpapercost: @RawValue Any? = null,

    @field:SerializedName("tax")
    val tax: Double? = 0.0,

    @field:SerializedName("m1rate")
    val m1rate: @RawValue Any? = null,

    @field:SerializedName("estimatedate")
    val estimatedate: String? = "",

    @field:SerializedName("decalsizemargin")
    val decalsizemargin: Double? = 0.0,

    @field:SerializedName("height_inch_field")
    val heightInchField: @RawValue Any? = null,

    @field:SerializedName("m2rate")
    val m2rate: Double? = 0.0,

    @field:SerializedName("netweight")
    val netweight: Double? = 0.0,

    @field:SerializedName("width_inch_field")
    val widthInchField: @RawValue Any? = null,

    @field:SerializedName("ups")
    val ups: Double? = 0.0,

    @field:SerializedName("f3bf")
    val f3bf: Double? = 0.0,

    @field:SerializedName("cuttinglengthmargin")
    val cuttinglengthmargin: Double? = 0.0,

    @field:SerializedName("f2gsm")
    val f2gsm: Double? = 0.0,

    @field:SerializedName("bottomrate")
    val bottomrate: Double? = 0.0,

    @field:SerializedName("topgsm")
    val topgsm: Double? = 0.0,

    @field:SerializedName("boxcost")
    val boxcost: @RawValue Any? = null,

    @field:SerializedName("topbf")
    val topbf: Double? = 0.0,

    @field:SerializedName("boxpricewithtax")
    val boxpricewithtax: @RawValue Any? = null,

    @field:SerializedName("cuttinglength")
    val cuttinglength: @RawValue Any? = null,

    @field:SerializedName("length_mm_field")
    val lengthMmField: Double? = 0.0,

    @field:SerializedName("totalbs")
    val totalbs: @RawValue Any? = null,

    @field:SerializedName("m2bf")
    val m2bf: Double? = 0.0,

    @field:SerializedName("profit")
    val profit: Double? = 0.0,

    @field:SerializedName("f3gsm")
    val f3gsm: Double? = 0.0,

    @field:SerializedName("f2ff")
    val f2ff: Double? = 0.0,

    @field:SerializedName("m2gsm")
    val m2gsm: Double? = 0.0,

    @field:SerializedName("decalsize")
    val decalsize: @RawValue Any? = null,

    @field:SerializedName("f2rate")
    val f2rate: Double? = 0.0,

    @field:SerializedName("totalgsm")
    val totalgsm: Double? = 0.0,

    @field:SerializedName("height_mm_field")
    val heightMmField: Double? = 0.0,

    @field:SerializedName("boxprice")
    val boxprice: @RawValue Any? = null,

    @field:SerializedName("overheadcharges")
    val overheadcharges: @RawValue Any? = null,

    @field:SerializedName("estimateid")
    val estimateid: Long? = 0,

    @field:SerializedName("comment")
    val comment: String? = "",

    @field:SerializedName("ply")
    val ply: Double? = 0.0,

    @field:SerializedName("conversionrate")
    val conversionrate: Double? = 0.0,

    @field:SerializedName("netpapercost")
    val netpapercost: @RawValue Any? = null,

    @field:SerializedName("f2bf")
    val f2bf: Double? = 0.0,

    @field:SerializedName("f3rate")
    val f3rate: Double? = 0.0
) : Parcelable
