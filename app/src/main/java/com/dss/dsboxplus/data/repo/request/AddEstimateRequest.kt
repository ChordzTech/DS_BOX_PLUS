package com.dss.dsboxplus.data.repo.request

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddEstimateRequest(

	@field:SerializedName("clientid")
	var clientid: Long? = 0,

	@field:SerializedName("totalweight")
	var totalweight: Int? = 0,

	@field:SerializedName("m1gsm")
	var m1gsm: Int? = 0,

	@field:SerializedName("height_cm_field")
	var heightCmField: Float? = 0.0F,

	@field:SerializedName("userid")
	var userid: Int? = 0,

	@field:SerializedName("boxname")
	var boxname: String? = "",

	@field:SerializedName("length_inch_field")
	var lengthInchField: Float? = 0.0F,

	@field:SerializedName("m1bf")
	var m1bf: Int? = 0,

	@field:SerializedName("width_mm_field")
	var widthMmField: Float? = 0.0F,

	@field:SerializedName("f1rate")
	var f1rate: Float? = 0F,


	@field:SerializedName("bottomgsm")
	var bottomgsm: Int? = 0,

	@field:SerializedName("f1bf")
	var f1bf: Int? = 0,

	@field:SerializedName("bottombf")
	var bottombf: Int? = 0,

	@field:SerializedName("waste")
	var waste: Float? = 0F,


	@field:SerializedName("f3ff")
	var f3ff: Float? = 0F,


	@field:SerializedName("width_cm_field")
	var widthCmField: Float? = 0.0F,

	@field:SerializedName("f1gsm")
	var f1gsm: Int? = 0,

	@field:SerializedName("length_cm_field")
	var lengthCmField: Float? = 0.0F,

	@field:SerializedName("f1ff")
	var f1ff: Float? = 0.0F,


	@field:SerializedName("businessid")
	var businessid: Int? = 0,

	@field:SerializedName("toprate")
	var toprate: Float? = 0F,

	@field:SerializedName("totalpapercost")
	var totalpapercost: Float? = 0F,

	@field:SerializedName("tax")
	var tax: Float? = 0F,

	@field:SerializedName("m1rate")
	var m1rate: Float? = 0F,

	@field:SerializedName("estimatedate")
	var estimatedate: String? = "",

	@field:SerializedName("decalsizemargin")
	var decalsizemargin: Int? = 0,

	@field:SerializedName("height_inch_field")
	var heightInchField: Float? = 0.0F,

	@field:SerializedName("m2rate")
	var m2rate: Float? = 0F,

	@field:SerializedName("netweight")
	var netweight: Int? = 0,

	@field:SerializedName("width_inch_field")
	var widthInchField: Float? = 0.0F,

	@field:SerializedName("ups")
	var ups: Int? = 0,

	@field:SerializedName("f3bf")
	var f3bf: Int? = 0,

	@field:SerializedName("cuttinglengthmargin")
	var cuttinglengthmargin: Int? = 0,

	@field:SerializedName("f2gsm")
	var f2gsm: Int? = 0,

	@field:SerializedName("bottomrate")
	var bottomrate: Float? = 0F,

	@field:SerializedName("topgsm")
	var topgsm: Int? = 0,

	@field:SerializedName("boxcost")
	var boxcost: Float? = 0F,

	@field:SerializedName("topbf")
	var topbf: Int? = 0,

	@field:SerializedName("boxpricewithtax")
	var boxpricewithtax: Float? = 0F,


	@field:SerializedName("cuttinglength")
	var cuttinglength: Float? = 0.0F,


	@field:SerializedName("length_mm_field")
	var lengthMmField: Float? = 0.0F,

	@field:SerializedName("totalbs")
	var totalbs: Float? = 0.0F,


	@field:SerializedName("m2bf")
	var m2bf: Int? = 0,

	@field:SerializedName("profit")
	var profit: Float? = 0F,

	@field:SerializedName("f3gsm")
	var f3gsm: Int? = 0,

	@field:SerializedName("f2ff")
	var f2ff: Float? = 0F,


	@field:SerializedName("m2gsm")
	var m2gsm: Int? = 0,

	@field:SerializedName("decalsize")
	var decalsize: Float? = 0.0F,


	@field:SerializedName("f2rate")
	var f2rate: Float? = 0F,


	@field:SerializedName("totalgsm")
	var totalgsm: Float? = 0.0F,


	@field:SerializedName("height_mm_field")
	var heightMmField: Float? = 0.0F,

	@field:SerializedName("boxprice")
	var boxprice: Float? = 0F,


	@field:SerializedName("overheadcharges")
	var overheadcharges: Int? = 0,

	@field:SerializedName("estimateid")
	var estimateid:  Long? = 0,

	@field:SerializedName("comment")
	var comment: String? = "",

	@field:SerializedName("ply")
	var ply: Int? = 0,


	@field:SerializedName("conversionrate")
	var conversionrate: Float? = 0F,

	@field:SerializedName("netpapercost")
	var netpapercost: Float? = 0F,

	@field:SerializedName("f2bf")
	var f2bf: Int? = 0,

	@field:SerializedName("f3rate")
	var f3rate: Float? = 0F
) : Parcelable
