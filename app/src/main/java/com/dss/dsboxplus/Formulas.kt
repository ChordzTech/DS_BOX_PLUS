package com.dss.dsboxplus

import java.lang.Double
import kotlin.String

class BoxFormula {
    private var totalWeightPerBoxTopInKg: kotlin.Double = 0.0
    private var weightPerBoxTopInKg: kotlin.Double = 0.0
    private var weightPerBoxFlute1InKg: kotlin.Double = 0.0
    private var totalWeightPerBoxFlute1Kg: kotlin.Double = 0.0
    var plyType: String = ""
    var mm = 25.4
    var divid = 1000.0
    var numberOfBox = 1
    var weightPerBoxTopGm: kotlin.Double = 0.0
    var totalWeightPerBoxTopGm: kotlin.Double = 0.0
    var weightPerBoxFlute1Gm: kotlin.Double = 0.0
    var totalWeightPerBoxFlute1Gm: kotlin.Double = 0.0
    var weightPerBoxFlute2Gm: kotlin.Double = 0.0
    var totalWeightPerBoxFlute2Gm: kotlin.Double = 0.0
    var weightPerBoxFlute3Gm: kotlin.Double = 0.0
    var totalWeightPerBoxFlute3Gm: kotlin.Double = 0.0
    var weightPerBoxMiddle1Gm: kotlin.Double = 0.0
    var totalWeightPerBoxMiddle1Gm: kotlin.Double = 0.0
    var weightPerBoxMiddle2Gm: kotlin.Double = 0.0
    var totalWeightPerBoxMiddle2Gm: kotlin.Double = 0.0
    var weightPerBoxBottomGm: kotlin.Double = 0.0
    var totalWeightPerBoxBottomGm: kotlin.Double = 0.0

    fun weightPerBoxTopPaper(
        bfInTop: String, gsmInTop: String, decalLength: kotlin.Double, cuttingLength: kotlin.Double
    ): kotlin.Double {
        val gsmOfTop = gsmInTop.toDouble()
        val resultForTop: kotlin.Double =
            decalLength * cuttingLength * gsmOfTop * mm * mm / divid / divid / divid
        weightPerBoxTopGm = resultForTop * 1000
        weightPerBoxTopGm = Double.valueOf(String.format("%.3f", weightPerBoxTopGm))
        totalWeightPerBoxTopGm = weightPerBoxTopGm * numberOfBox
        return weightPerBoxTopGm
    }

    fun weightPerBoxTopPaperKG(
        bfInTop: String, gsmInTop: String, decalLength: kotlin.Double, cuttingLength: kotlin.Double
    ): kotlin.Double {
        val gsmOfTop = gsmInTop.toDouble()
        var cuttingLengthX = cuttingLength
        do {
            val resultForTop: kotlin.Double =
                decalLength * cuttingLengthX * gsmOfTop * mm * mm / divid / divid / divid
            weightPerBoxTopGm = resultForTop * 1000
            weightPerBoxTopGm = Double.valueOf(String.format("%.3f", weightPerBoxTopGm))
            totalWeightPerBoxTopGm = weightPerBoxTopGm * numberOfBox
            cuttingLengthX += 50.0
        } while (weightPerBoxTopGm < 1000);

        return weightPerBoxTopGm
    }

    fun weightPerBoxFlute1(
        gsmInF1: String, decalLength: kotlin.Double, cuttingLength: kotlin.Double, ffinf1: String
    ): kotlin.Double {
        val gsmOfF1 = gsmInF1.toDouble()
        val fluteInf1 = ffinf1.toDouble()
        val resultForF1: kotlin.Double =
            decalLength * cuttingLength * gsmOfF1 * fluteInf1 * mm * mm / divid / divid / divid
        weightPerBoxFlute1Gm = resultForF1 * 1000
        weightPerBoxFlute1Gm = Double.valueOf(String.format("%.3f", weightPerBoxFlute1Gm))
        totalWeightPerBoxFlute1Gm = weightPerBoxFlute1Gm * numberOfBox
        return weightPerBoxFlute1Gm
    }

    fun weightPerBoxFlute1KG(
        gsmInF1: String, decalLength: kotlin.Double, cuttingLength: kotlin.Double, ffinf1: String
    ): kotlin.Double {
        val gsmOfF1 = gsmInF1.toDouble()
        val fluteInf1 = ffinf1.toDouble()
        var cuttingLengthX = cuttingLength
        do {
            val resultForF1: kotlin.Double =
                decalLength * cuttingLengthX * gsmOfF1 * fluteInf1 * mm * mm / divid / divid / divid
            weightPerBoxFlute1Gm = resultForF1 * 1000
            weightPerBoxFlute1Gm = Double.valueOf(String.format("%.3f", weightPerBoxFlute1Gm))
            totalWeightPerBoxFlute1Gm = weightPerBoxFlute1Gm * numberOfBox
            cuttingLengthX += 50;
        } while (weightPerBoxFlute1Gm < 1000)
        return weightPerBoxFlute1Gm
    }

    fun weightPerBoxFlute2(
        gsmInF2: String, cuttingLength: kotlin.Double, decalLength: kotlin.Double, ffinf2: String
    ): kotlin.Double {
        val gsmOff2 = gsmInF2.toDouble()
        val fluteinF2 = ffinf2.toDouble()
        val resultForBoxF2: kotlin.Double =
            cuttingLength * decalLength * gsmOff2 * fluteinF2 * mm * mm / divid / divid / divid
        weightPerBoxFlute2Gm = resultForBoxF2 * 1000
        weightPerBoxFlute2Gm = Double.valueOf(String.format("%.3f", weightPerBoxFlute2Gm))
        totalWeightPerBoxFlute2Gm = weightPerBoxFlute2Gm * numberOfBox
        return weightPerBoxFlute2Gm
    }

    fun weightPerBoxFlute3(
        gsmInF3: String, cuttingLength: kotlin.Double, decalLength: kotlin.Double, ffinf3: String
    ): kotlin.Double {
        val gsmOff3 = gsmInF3.toDouble()
        val ffOFf3 = ffinf3.toDouble()
        weightPerBoxFlute3Gm =
            ((((cuttingLength * decalLength * gsmOff3 * ffOFf3 * mm * mm) / divid) / divid) / divid) * 1000
        totalWeightPerBoxFlute3Gm = weightPerBoxFlute3Gm * numberOfBox
        return weightPerBoxFlute3Gm
    }

    fun weightPerBoxMiddle1(
        gsmInM1: String, cuttingLength: kotlin.Double, decalLength: kotlin.Double
    ): kotlin.Double {
        val gsmOfM1 = gsmInM1.toDouble()
        val resultForMBoxM1: kotlin.Double =
            ((((cuttingLength * decalLength * gsmOfM1 * mm * mm) / divid) / divid) / divid) * 1000
        weightPerBoxMiddle1Gm = Double.valueOf(String.format("%.3f", resultForMBoxM1))
        totalWeightPerBoxMiddle1Gm = weightPerBoxMiddle1Gm * numberOfBox
        return weightPerBoxMiddle1Gm
    }

    fun weightPerBoxMiddle2(
        gsmInM2: String, cuttingLength: kotlin.Double, decalLength: kotlin.Double
    ): kotlin.Double {
        val gsmOfM2 = gsmInM2.toDouble()
        val resultForBoxM2: kotlin.Double =
            ((((cuttingLength * decalLength * gsmOfM2 * mm * mm) / divid) / divid) / divid) * 1000
        weightPerBoxMiddle2Gm = Double.valueOf(String.format("%.3f", resultForBoxM2))
        totalWeightPerBoxMiddle2Gm = weightPerBoxMiddle2Gm * numberOfBox
        return weightPerBoxMiddle2Gm
    }

    fun weightPerBoxBottomPaper(
        gsmInBottom: String, cuttingLength: kotlin.Double, decalLength: kotlin.Double
    ): kotlin.Double {
        val gsmOfBottom = gsmInBottom.toDouble()
        val resultForBottom: kotlin.Double =
            ((((decalLength * cuttingLength * gsmOfBottom * mm * mm) / divid) / divid) / divid) * 1000
        weightPerBoxBottomGm = Double.valueOf(String.format("%.3f", resultForBottom))

        totalWeightPerBoxBottomGm = weightPerBoxBottomGm * numberOfBox
        return weightPerBoxBottomGm
    }
}