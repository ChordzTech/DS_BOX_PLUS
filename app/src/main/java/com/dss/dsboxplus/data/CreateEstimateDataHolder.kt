package com.dss.dsboxplus.data

object CreateEstimateDataHolder {
    //Values From First Activity
    var clientName: String = ""
    var boxName: String = ""
    var boxDimension: String = ""
    var lengthMm: Int = 0
    var widthMm: Int = 0
    var heightMm: Int = 0
    var lengthCm: Int = 0
    var widthCm: Int = 0
    var heightCm: Int = 0
    var lengthInch: Float = 0.0F
    var widthInch: Float = 0.0F
    var heightInch: Float = 0.0F
    var noOfPly: String = "0"
    var noOfBox: Int = 0
    var cuttingLength: Double = 0.0
    var DecalSize: Double = 0.0
    var cuttingMarginMm: Int = 0
    var decalMarginMm: Int = 0
    var cuttingLengthMm: Int = 0
    var decalSizeMm: Int = 0

    //Values From Second Activity
    var topBf: Int = 0
    var topGsm: Int = 0
    var topRate: Float = 0.0F

    var f1Bf: Int = 0
    var f1Gsm: Int = 0
    var f1RateKg: Float = 0.0F
    var f1ff: Float = 0.0F

    var m1bf: Int = 0
    var m1Gsm: Int = 0
    var m1RateKg: Float = 0.0F

    var f2Bf: Int = 0
    var f2Gsm: Int = 0
    var f2RateKg: Float = 0.0F
    var f2ff: Float = 0.0F

    var m2bf: Int = 0
    var m2Gsm: Int = 0
    var m2RateKg: Float = 0.0F

    var f3Bf: Int = 0
    var f3Gsm: Int = 0
    var f3RateKg: Float = 0.0F
    var f3ff: Float = 0.0F

    var bottomBF: Int = 0
    var bottomGsm: Int = 0
    var bottomRateKg: Float = 0.0F

    //Values From Third activity
    var totalGsm: Double = 0.0
    var totalBs: Double = 0.0
    var totalWeight: Double = 0.0
    var netPaperCost: Double = 0.0
    var wasteCost: Double = 0.0
    var grossPaperCost: Double = 0.0
    var convCost: Double = 0.0
    var boxMfg: Double = 0.0
    var boxPrice: Double = 0.0
    var boxPriceTax: Double = 0.0
    var wasteInput: Float = 0.0F
    var convRate: Float = 0.0F
    var overHead: Float = 0.0F
    var tax: Float = 0.0F
    fun resetValues() {
        clientName = ""
        boxName = ""
        boxDimension = ""
        lengthMm = 0
        widthMm = 0
        heightMm = 0
        lengthCm = 0
        widthCm = 0
        heightCm = 0
        lengthInch = 0.0F
        widthInch = 0.0F
        heightInch = 0.0F
        noOfPly = "0"
        noOfBox = 0
        cuttingLength = 0.0
        DecalSize = 0.0
        cuttingMarginMm = 0
        decalMarginMm = 0
        cuttingLengthMm = 0
        decalSizeMm = 0

        //Values From Second Activity
        topBf = 0
        topGsm = 0
        topRate = 0.0F

        f1Bf = 0
        f1Gsm = 0
        f1RateKg = 0.0F
        f1ff = 0.0F

        m1bf = 0
        m1Gsm = 0
        m1RateKg = 0.0F

        f2Bf = 0
        f2Gsm = 0
        f2RateKg = 0.0F
        f2ff = 0.0F

        m2bf = 0
        m2Gsm = 0
        m2RateKg = 0.0F

        f3Bf = 0
        f3Gsm = 0
        f3RateKg = 0.0F
        f3ff = 0.0F

        bottomBF = 0
        bottomGsm = 0
        bottomRateKg = 0.0F

        //Values From Third activity
        totalGsm = 0.0
        totalBs = 0.0
        totalWeight = 0.0
        netPaperCost = 0.0
        wasteCost = 0.0
        grossPaperCost = 0.0
        convCost = 0.0
        boxMfg = 0.0
        boxPrice = 0.0
        boxPriceTax = 0.0
        wasteInput = 0.0F
        convRate = 0.0F
        overHead = 0.0F
        tax = 0.0F
    }
}