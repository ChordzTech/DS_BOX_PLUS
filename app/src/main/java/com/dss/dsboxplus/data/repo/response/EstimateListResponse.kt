package com.dss.dsboxplus.data.repo.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class EstimateListResponse(

    @field:SerializedName("code")
    var code: Double? = 0.0,

    @field:SerializedName("data")
    var data: List<DataItem?>? = arrayListOf(),

    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("status")
    var status: String? = null
) : Parcelable

@Parcelize
data class  DataItem(

    @field:SerializedName("clientid")
    var clientid: Long? = null,

    @field:SerializedName("totalweight")
    var totalweight: Double? = 0.0,

    @field:SerializedName("m1gsm")
    var m1gsm: Double? = 0.0,

    @field:SerializedName("height_cm_field")
    var heightCmField: @RawValue Any? = null,

    @field:SerializedName("userid")
    var userid: Double? = 0.0,

    @field:SerializedName("boxname")
    var boxname: String? = null,

    @field:SerializedName("length_inch_field")
    var lengthInchField: @RawValue Any? = null,

    @field:SerializedName("m1bf")
    var m1bf: Double? = 0.0,

    @field:SerializedName("width_mm_field")
    var widthMmField: Double? = 0.0,

    @field:SerializedName("f1rate")
    var f1rate: @RawValue Any? = null,

    @field:SerializedName("bottomgsm")
    var bottomgsm: Double? = 0.0,

    @field:SerializedName("f1bf")
    var f1bf: Double? = 0.0,

    @field:SerializedName("bottombf")
    var bottombf: Double? = 0.0,

    @field:SerializedName("waste")
    var waste: Double? = 0.0,

    @field:SerializedName("f3ff")
    var f3ff: Double? = 0.0,

    @field:SerializedName("f1gsm")
    var f1gsm: Double? = 0.0,

    @field:SerializedName("width_cm_field")
    var widthCmField: @RawValue Any? = null,

    @field:SerializedName("f1ff")
    var f1ff: @RawValue Any? = null,

    @field:SerializedName("length_cm_field")
    var lengthCmField: @RawValue Any? = null,

    @field:SerializedName("businessid")
    var businessid: Long? = null,

    @field:SerializedName("toprate")
    var toprate: @RawValue Any? = null,

    @field:SerializedName("totalpapercost")
    var totalpapercost: @RawValue Any? = null,

    @field:SerializedName("tax")
    var tax: Double? = 0.0,

    @field:SerializedName("m1rate")
    var m1rate: @RawValue Any? = null,

    @field:SerializedName("estimatedate")
    var estimatedate: String? = null,

    @field:SerializedName("decalsizemargin")
    var decalsizemargin: Double? = 0.0,

    @field:SerializedName("height_inch_field")
    var heightInchField: @RawValue Any? = null,

    @field:SerializedName("m2rate")
    var m2rate: Double? = 0.0,

    @field:SerializedName("netweight")
    var netweight: Double? = 0.0,

    @field:SerializedName("width_inch_field")
    var widthInchField: @RawValue Any? = null,

    @field:SerializedName("ups")
    var ups: Double? = 0.0,

    @field:SerializedName("f3bf")
    var f3bf: Double? = 0.0,

    @field:SerializedName("cuttinglengthmargin")
    var cuttinglengthmargin: Double? = 0.0,

    @field:SerializedName("f2gsm")
    var f2gsm: Double? = 0.0,

    @field:SerializedName("bottomrate")
    var bottomrate: Double? = 0.0,

    @field:SerializedName("topgsm")
    var topgsm: Double? = 0.0,

    @field:SerializedName("boxcost")
    var boxcost: @RawValue Any? = null,

    @field:SerializedName("topbf")
    var topbf: Double? = 0.0,

    @field:SerializedName("boxpricewithtax")
    var boxpricewithtax: @RawValue Any? = null,

    @field:SerializedName("cuttinglength")
    var cuttinglength: @RawValue Any? = null,

    @field:SerializedName("length_mm_field")
    var lengthMmField: Double? = 0.0,

    @field:SerializedName("totalbs")
    var totalbs: @RawValue Any? = null,

    @field:SerializedName("m2bf")
    var m2bf: Double? = 0.0,

    @field:SerializedName("profit")
    var profit: Double? = 0.0,

    @field:SerializedName("f3gsm")
    var f3gsm: Double? = 0.0,

    @field:SerializedName("f2ff")
    var f2ff: Double? = 0.0,

    @field:SerializedName("m2gsm")
    var m2gsm: Double? = 0.0,

    @field:SerializedName("decalsize")
    var decalsize: @RawValue Any? = null,

    @field:SerializedName("f2rate")
    var f2rate: Double? = 0.0,

    @field:SerializedName("totalgsm")
    var totalgsm: Double? = 0.0,

    @field:SerializedName("height_mm_field")
    var heightMmField: Double? = 0.0,

    @field:SerializedName("boxprice")
    var boxprice: @RawValue Any? = null,

    @field:SerializedName("overheadcharges")
    var overheadcharges: @RawValue Any? = null,

    @field:SerializedName("estimateid")
    var estimateid: Long? = null,

    @field:SerializedName("comment")
    var comment: String? = null,

    @field:SerializedName("ply")
    var ply: Int = 1,

    @field:SerializedName("conversionrate")
    var conversionrate: Double? = 0.0,

    @field:SerializedName("netpapercost")
    var netpapercost: @RawValue Any? = null,

    @field:SerializedName("f2bf")
    var f2bf: Double? = 0.0,

    @field:SerializedName("f3rate")
    var f3rate: Double? = 0.0
) : Parcelable
