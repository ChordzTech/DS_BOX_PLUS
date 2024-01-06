package com.dss.dsboxplus.data.repo.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddEstimateRequest(

	@field:SerializedName("clientid")
	var clientid: Int? = 0,

	@field:SerializedName("totalweight")
	var totalweight: Int? = 0,

	@field:SerializedName("m1gsm")
	var m1gsm: Int? = 0,

	@field:SerializedName("height_cm_field")
	val heightCmField: Int? = 0,

	@field:SerializedName("userid")
	var userid: Int? = 0,

	@field:SerializedName("boxname")
	var boxname: String? = "",

	@field:SerializedName("length_inch_field")
	val lengthInchField: Int? = 0,

	@field:SerializedName("m1bf")
	var m1bf: Int? = 0,

	@field:SerializedName("width_mm_field")
	var widthMmField: Int? = 0,

	@field:SerializedName("f1rate")
	var f1rate: Int? = 0,


	@field:SerializedName("bottomgsm")
	var bottomgsm: Int? = 0,

	@field:SerializedName("f1bf")
	var f1bf: Int? = 0,

	@field:SerializedName("bottombf")
	var bottombf: Int? = 0,

	@field:SerializedName("waste")
	var waste: Int? = 0,


	@field:SerializedName("f3ff")
	var f3ff: Int? = 0,


	@field:SerializedName("width_cm_field")
	val widthCmField: Int? = 0,

	@field:SerializedName("f1gsm")
	var f1gsm: Int? = 0,

	@field:SerializedName("length_cm_field")
	val lengthCmField: Int? = 0,

	@field:SerializedName("f1ff")
	var f1ff: Int? = 0,


	@field:SerializedName("businessid")
	var businessid: Int? = 0,

	@field:SerializedName("toprate")
	var toprate: Int? = 0,


	@field:SerializedName("totalpapercost")
	val totalpapercost: Int? = 0,

	@field:SerializedName("tax")
	var tax: Int? = 0,


	@field:SerializedName("m1rate")
	var m1rate: Int? = 0,

	@field:SerializedName("estimatedate")
	var estimatedate: String? = "",

	@field:SerializedName("decalsizemargin")
	var decalsizemargin: Int? = 0,

	@field:SerializedName("height_inch_field")
	val heightInchField: Int? = 0,

	@field:SerializedName("m2rate")
	var m2rate: Int? = 0,

	@field:SerializedName("netweight")
	val netweight: Int? = 0,

	@field:SerializedName("width_inch_field")
	val widthInchField: Int? = 0,

	@field:SerializedName("ups")
	var ups: Int? = 0,

	@field:SerializedName("f3bf")
	var f3bf: Int? = 0,

	@field:SerializedName("cuttinglengthmargin")
	var cuttinglengthmargin: Int? = 0,

	@field:SerializedName("f2gsm")
	var f2gsm: Int? = 0,

	@field:SerializedName("bottomrate")
	var bottomrate: Int? = 0,

	@field:SerializedName("topgsm")
	var topgsm: Int? = 0,

	@field:SerializedName("boxcost")
	var boxcost: Int? = 0,

	@field:SerializedName("topbf")
	var topbf: Int? = 0,

	@field:SerializedName("boxpricewithtax")
	var boxpricewithtax: Int? = 0,


	@field:SerializedName("cuttinglength")
	var cuttinglength: Int? = 0,


	@field:SerializedName("length_mm_field")
	var lengthMmField: Int? = 0,

	@field:SerializedName("totalbs")
	var totalbs: Int? = 0,


	@field:SerializedName("m2bf")
	var m2bf: Int? = 0,

	@field:SerializedName("profit")
	val profit: Int? = 0,

	@field:SerializedName("f3gsm")
	var f3gsm: Int? = 0,

	@field:SerializedName("f2ff")
	var f2ff: Int? = 0,


	@field:SerializedName("m2gsm")
	var m2gsm: Int? = 0,

	@field:SerializedName("decalsize")
	var decalsize: Int? = 0,


	@field:SerializedName("f2rate")
	var f2rate: Int? = 0,


	@field:SerializedName("totalgsm")
	var totalgsm: Int? = 0,


	@field:SerializedName("height_mm_field")
	var heightMmField: Int? = 0,

	@field:SerializedName("boxprice")
	var boxprice: Int? = 0,


	@field:SerializedName("overheadcharges")
	var overheadcharges: Int? = 0,

	@field:SerializedName("estimateid")
	val estimateid: Int? = 0,

	@field:SerializedName("comment")
	val comment: String? = "",

	@field:SerializedName("ply")
	var ply: Int? = 0,


	@field:SerializedName("conversionrate")
	var conversionrate: Int? = 0,

	@field:SerializedName("netpapercost")
	var netpapercost: Int? = 0,

	@field:SerializedName("f2bf")
	var f2bf: Int? = 0,

	@field:SerializedName("f3rate")
	var f3rate: Int? = 0
) : Parcelable
