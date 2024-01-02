package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddEstimateRequest(

	@field:SerializedName("clientid")
	val clientid: Int? = null,

	@field:SerializedName("totalweight")
	val totalweight: Int? = null,

	@field:SerializedName("m1gsm")
	val m1gsm: Int? = null,

	@field:SerializedName("height_cm_field")
	val heightCmField: Int? = null,

	@field:SerializedName("userid")
	val userid: Int? = null,

	@field:SerializedName("boxname")
	val boxname: String? = null,

	@field:SerializedName("length_inch_field")
	val lengthInchField: Int? = null,

	@field:SerializedName("m1bf")
	val m1bf: Int? = null,

	@field:SerializedName("width_mm_field")
	val widthMmField: Int? = null,

	@field:SerializedName("f1rate")
	val f1rate: Int? = null,

	@field:SerializedName("bottomgsm")
	val bottomgsm: Int? = null,

	@field:SerializedName("f1bf")
	val f1bf: Int? = null,

	@field:SerializedName("bottombf")
	val bottombf: Int? = null,

	@field:SerializedName("waste")
	val waste: Int? = null,

	@field:SerializedName("f3ff")
	val f3ff: Int? = null,

	@field:SerializedName("width_cm_field")
	val widthCmField: Int? = null,

	@field:SerializedName("f1gsm")
	val f1gsm: Int? = null,

	@field:SerializedName("length_cm_field")
	val lengthCmField: Int? = null,

	@field:SerializedName("f1ff")
	val f1ff: Int? = null,

	@field:SerializedName("businessid")
	var businessid: Int? = null,

	@field:SerializedName("toprate")
	val toprate: Int? = null,

	@field:SerializedName("totalpapercost")
	val totalpapercost: Int? = null,

	@field:SerializedName("tax")
	val tax: Int? = null,

	@field:SerializedName("m1rate")
	val m1rate: Int? = null,

	@field:SerializedName("estimatedate")
	val estimatedate: String? = null,

	@field:SerializedName("decalsizemargin")
	val decalsizemargin: Int? = null,

	@field:SerializedName("height_inch_field")
	val heightInchField: Int? = null,

	@field:SerializedName("m2rate")
	val m2rate: Int? = null,

	@field:SerializedName("netweight")
	val netweight: Int? = null,

	@field:SerializedName("width_inch_field")
	val widthInchField: Int? = null,

	@field:SerializedName("ups")
	val ups: Int? = null,

	@field:SerializedName("f3bf")
	val f3bf: Int? = null,

	@field:SerializedName("cuttinglengthmargin")
	val cuttinglengthmargin: Int? = null,

	@field:SerializedName("f2gsm")
	val f2gsm: Int? = null,

	@field:SerializedName("bottomrate")
	val bottomrate: Int? = null,

	@field:SerializedName("topgsm")
	val topgsm: Int? = null,

	@field:SerializedName("boxcost")
	val boxcost: Int? = null,

	@field:SerializedName("topbf")
	val topbf: Int? = null,

	@field:SerializedName("boxpricewithtax")
	val boxpricewithtax: Int? = null,

	@field:SerializedName("cuttinglength")
	val cuttinglength: Int? = null,

	@field:SerializedName("length_mm_field")
	val lengthMmField: Int? = null,

	@field:SerializedName("totalbs")
	val totalbs: Int? = null,

	@field:SerializedName("m2bf")
	val m2bf: Int? = null,

	@field:SerializedName("profit")
	val profit: Int? = null,

	@field:SerializedName("f3gsm")
	val f3gsm: Int? = null,

	@field:SerializedName("f2ff")
	val f2ff: Int? = null,

	@field:SerializedName("m2gsm")
	val m2gsm: Int? = null,

	@field:SerializedName("decalsize")
	val decalsize: Int? = null,

	@field:SerializedName("f2rate")
	val f2rate: Int? = null,

	@field:SerializedName("totalgsm")
	val totalgsm: Int? = null,

	@field:SerializedName("height_mm_field")
	val heightMmField: Int? = null,

	@field:SerializedName("boxprice")
	val boxprice: Int? = null,

	@field:SerializedName("overheadcharges")
	val overheadcharges: Int? = null,

	@field:SerializedName("estimateid")
	val estimateid: Int? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("ply")
	val ply: Int? = null,

	@field:SerializedName("conversionrate")
	val conversionrate: Int? = null,

	@field:SerializedName("netpapercost")
	val netpapercost: Int? = null,

	@field:SerializedName("f2bf")
	val f2bf: Int? = null,

	@field:SerializedName("f3rate")
	val f3rate: Int? = null
) : Parcelable
