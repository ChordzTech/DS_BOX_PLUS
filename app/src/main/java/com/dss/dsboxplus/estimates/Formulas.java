package com.dss.dsboxplus.estimates;

public class Formulas {
    private static Formulas formulas;
    float mm = 25.4F;

    private Formulas() {

    }

    public static Formulas getInstance() {
        if (formulas == null) {
            formulas = new Formulas();
        }
        return formulas;
    }

    public double cuttingLength(Double length, Double width, Double height, Double tvMargin) {
        double v = (((length + width) * 2) + tvMargin) ;
        return v;
    }
    public double cuttingLengthInTiet(Double v){
        return v/mm;
    }

    public double decalSize(Double width, Double height, Double d2) {

        double result = ((width + height) + d2) ;
        return result;
    }
    public double decalSizeInTiet(Double decalSize){
        return decalSize/mm;
    }

}
