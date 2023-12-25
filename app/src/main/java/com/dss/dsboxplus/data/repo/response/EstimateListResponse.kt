package com.dss.dsboxplus.data.repo.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class EstimateListResponse(

    @field:SerializedName("code")
    val code: Double? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@Parcelize
data class DataItem(

    @field:SerializedName("clientid")
    val clientid: Long? = null,

    @field:SerializedName("totalweight")
    val totalweight: Double? = null,

    @field:SerializedName("m1gsm")
    val m1gsm: Double? = null,

    @field:SerializedName("height_cm_field")
    val heightCmField: @RawValue Any? = null,

    @field:SerializedName("userid")
    val userid: Double? = null,

    @field:SerializedName("boxname")
    val boxname: String? = null,

    @field:SerializedName("length_inch_field")
    val lengthInchField: @RawValue Any? = null,

    @field:SerializedName("m1bf")
    val m1bf: Double? = null,

    @field:SerializedName("width_mm_field")
    val widthMmField: Double? = null,

    @field:SerializedName("f1rate")
    val f1rate: @RawValue Any? = null,

    @field:SerializedName("bottomgsm")
    val bottomgsm: Double? = null,

    @field:SerializedName("f1bf")
    val f1bf: Double? = null,

    @field:SerializedName("bottombf")
    val bottombf: Double? = null,

    @field:SerializedName("waste")
    val waste: Double? = null,

    @field:SerializedName("f3ff")
    val f3ff: Double? = null,

    @field:SerializedName("f1gsm")
    val f1gsm: Double? = null,

    @field:SerializedName("width_cm_field")
    val widthCmField: @RawValue Any? = null,

    @field:SerializedName("f1ff")
    val f1ff: @RawValue Any? = null,

    @field:SerializedName("length_cm_field")
    val lengthCmField: @RawValue Any? = null,

    @field:SerializedName("businessid")
    val businessid: Double? = null,

    @field:SerializedName("toprate")
    val toprate: @RawValue Any? = null,

    @field:SerializedName("totalpapercost")
    val totalpapercost: @RawValue Any? = null,

    @field:SerializedName("tax")
    val tax: Double? = null,

    @field:SerializedName("m1rate")
    val m1rate: @RawValue Any? = null,

    @field:SerializedName("estimatedate")
    val estimatedate: String? = null,

    @field:SerializedName("decalsizemargin")
    val decalsizemargin: Double? = null,

    @field:SerializedName("height_inch_field")
    val heightInchField: @RawValue Any? = null,

    @field:SerializedName("m2rate")
    val m2rate: Double? = null,

    @field:SerializedName("netweight")
    val netweight: Double? = null,

    @field:SerializedName("width_inch_field")
    val widthInchField: @RawValue Any? = null,

    @field:SerializedName("ups")
    val ups: Double? = null,

    @field:SerializedName("f3bf")
    val f3bf: Double? = null,

    @field:SerializedName("cuttinglengthmargin")
    val cuttinglengthmargin: Double? = null,

    @field:SerializedName("f2gsm")
    val f2gsm: Double? = null,

    @field:SerializedName("bottomrate")
    val bottomrate: Double? = null,

    @field:SerializedName("topgsm")
    val topgsm: Double? = null,

    @field:SerializedName("boxcost")
    val boxcost: @RawValue Any? = null,

    @field:SerializedName("topbf")
    val topbf: Double? = null,

    @field:SerializedName("boxpricewithtax")
    val boxpricewithtax: @RawValue Any? = null,

    @field:SerializedName("cuttinglength")
    val cuttinglength: @RawValue Any? = null,

    @field:SerializedName("length_mm_field")
    val lengthMmField: Double? = null,

    @field:SerializedName("totalbs")
    val totalbs: @RawValue Any? = null,

    @field:SerializedName("m2bf")
    val m2bf: Double? = null,

    @field:SerializedName("profit")
    val profit: Double? = null,

    @field:SerializedName("f3gsm")
    val f3gsm: Double? = null,

    @field:SerializedName("f2ff")
    val f2ff: Double? = null,

    @field:SerializedName("m2gsm")
    val m2gsm: Double? = null,

    @field:SerializedName("decalsize")
    val decalsize: @RawValue Any? = null,

    @field:SerializedName("f2rate")
    val f2rate: Double? = null,

    @field:SerializedName("totalgsm")
    val totalgsm: Double? = null,

    @field:SerializedName("height_mm_field")
    val heightMmField: Double? = null,

    @field:SerializedName("boxprice")
    val boxprice: @RawValue Any? = null,

    @field:SerializedName("overheadcharges")
    val overheadcharges: @RawValue Any? = null,

    @field:SerializedName("estimateid")
    val estimateid: Long? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("ply")
    val ply: Double? = null,

    @field:SerializedName("conversionrate")
    val conversionrate: Double? = null,

    @field:SerializedName("netpapercost")
    val netpapercost: @RawValue Any? = null,

    @field:SerializedName("f2bf")
    val f2bf: Double? = null,

    @field:SerializedName("f3rate")
    val f3rate: Double? = null
) : Parcelable
