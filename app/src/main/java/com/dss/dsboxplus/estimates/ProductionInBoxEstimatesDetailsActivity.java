package com.dss.dsboxplus.estimates;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.BoxFormula;
import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.databinding.ActivityProductionInBoxEstimatesDetailsBinding;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductionInBoxEstimatesDetailsActivity extends BaseActivity {

    ActivityProductionInBoxEstimatesDetailsBinding productionInBoxEstimatesDetailsBinding;

    private DataItem dataItem;
    private int boxQuantity = 0;
    private String grossWeightUnit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        productionInBoxEstimatesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_production_in_box_estimates_details);
        super.onCreate(savedInstanceState);
        initView();


    }

    private void initView() {


        productionInBoxEstimatesDetailsBinding.btCloseInProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        productionInBoxEstimatesDetailsBinding.btCreatePDFInProduction.setOnClickListener(new View.OnClickListener() {
            // Get the text from the TextInputEditText

            @Override
            public void onClick(View v) {
                boxQuantity = Integer.parseInt(productionInBoxEstimatesDetailsBinding.tietBoxQuantity.getText().toString().trim());
                if (boxQuantity != 0) {
                    try {
                        createProductionDsPdf();
                    } catch (FileNotFoundException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    Toast.makeText(ProductionInBoxEstimatesDetailsActivity.this, "Enter Box Quantity", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createProductionDsPdf() throws FileNotFoundException {

        Intent intent = getIntent();
        String length = intent.getStringExtra("length");
        String width = intent.getStringExtra("width");
        String height = intent.getStringExtra("height");
        String boxName = intent.getStringExtra("boxName");
        String bfInTop = intent.getStringExtra("bfInTop");
        String bfInF1 = intent.getStringExtra("bfInF1");
        String bfInM1 = intent.getStringExtra("bfInM1");
        String bfInF2 = intent.getStringExtra("bfInF2");
        String bfInM2 = intent.getStringExtra("bfInM2");
        String bfInF3 = intent.getStringExtra("bfInF3");
        String bfInBottom = intent.getStringExtra("bfInBottom");
        String ffinf1 = intent.getStringExtra("ffinf1");
        String ffinf2 = intent.getStringExtra("ffinf2");
        String ffinf3 = intent.getStringExtra("ffinf3");


//        HashMap<String, String> bfMap = new HashMap();
//        bfMap.put(bfInTop, "bfInTop");
//        bfMap.put(bfInF1, "bfInF1");
//        bfMap.put(bfInM1, "bfInM1");
//        bfMap.put(bfInF2, "bfInF2");
//        bfMap.put(bfInM2, "bfInM2");
//        bfMap.put(bfInF3, "bfInF3");
//        bfMap.put(bfInBottom, "bfInBottom");


        String gsmInTop = intent.getStringExtra("gsmInTop");
        String gsmInF1 = intent.getStringExtra("gsmInF1");
        String gsmInM1 = intent.getStringExtra("gsmInM1");
        String gsmInF2 = intent.getStringExtra("gsmInF2");
        String gsmInM2 = intent.getStringExtra("gsmInM2");
        String gsmInF3 = intent.getStringExtra("gsmInF3");
        String gsmInBottom = intent.getStringExtra("gsmInBottom");

//        HashMap<String, String> gsmMap = new HashMap();
//        gsmMap.put(gsmInTop, "gsmInTop");
//        gsmMap.put(gsmInF1, "gsmInF1");
//        gsmMap.put(gsmInM1, "gsmInM1");
//        gsmMap.put(gsmInF2, "gsmInF2");
//        gsmMap.put(gsmInM2, "gsmInM2");
//        gsmMap.put(gsmInF3, "gsmInF3");
//        gsmMap.put(gsmInBottom, "gsmInBottom");


        String rate = intent.getStringExtra("rate");
        String ply = intent.getStringExtra("ply");
        String weight = intent.getStringExtra("weight");
        String ups = intent.getStringExtra("ups");


        // Retrieve data from the intent
        double decalLength = intent.getDoubleExtra("decalLength", 0.0);
        double cuttingLength = getIntent().getDoubleExtra("cuttingLength", 0.0);
        int decalSizemm = getIntent().getIntExtra("decalSizemm", 0);
        int cuttingLengthmm = getIntent().getIntExtra("cuttingLengthmm", 0);

        Client clientDetails = ConfigDataProvider.INSTANCE.getClientIdMap().get(intent.getLongExtra("clientId", 0));

        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();
        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
        }


        File dsBox = this.getBaseContext().getExternalFilesDir("ds_box");
        if (dsBox.exists()) {
            dsBox.delete();
        }
        dsBox.mkdir();

        String pdfPath = dsBox.getPath();
        File file = new File(pdfPath, "Production.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        DeviceRgb gray = new DeviceRgb(128, 128, 128);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        float columnWidth[] = {62, 140, 140, 140};
        Table table = new Table(columnWidth);

        Drawable d1 = getDrawable(R.drawable.companylogo);
        Bitmap bitmap1 = ((BitmapDrawable) d1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitmapData1 = stream1.toByteArray();
        ImageData imageData1 = ImageDataFactory.create(bitmapData1);
        Image image1 = new Image(imageData1);
        image1.setWidth(80f);

        table.addCell(new Cell(4, 1).add(image1).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(businessDetailsResponse.getData().getBusinessname()).setTextAlignment(TextAlignment.CENTER)).setFontSize(20f).setBold().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //table 1-02
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(businessDetailsResponse.getData().getAddress() + ", " + businessDetailsResponse.getData().getPincode()).setTextAlignment(TextAlignment.CENTER).setBold()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //table 1-03
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(businessDetailsResponse.getData().getContactno()).setBold()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //table 1-04
//        table.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));

//        //table 1-05
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
//        table.addCell(new Cell(1,1).add(new Paragraph("Approx.Paper required for Production")).setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        float columnWidth1[] = {62, 324, 174};
        Table table1 = new Table(columnWidth1);

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Date:" + LocalDate.now().format(dateTimeFormatter).toString()).setBorder(Border.NO_BORDER)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Client Name:" + clientDetails.getClientname())).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Box Name:" + boxName)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Box Outer Dimension:" + length + "X" + width + "X" + height)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Cutting Length:" + cuttingLength + "inch" + "-" + cuttingLengthmm + "mm")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Decal Size:" + decalLength + "inch" + "-" + decalSizemm + "mm")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Ups:" + ups)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Production Quantity:" + productionInBoxEstimatesDetailsBinding.tietBoxQuantity.getText().toString())).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));


        float columnWidth2[] = {62, 140, 140, 140};
        Table table2 = new Table(columnWidth2);

        table2.addCell(new Cell().add(new Paragraph("Paper")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));
        table2.addCell(new Cell().add(new Paragraph("Description")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));
        table2.addCell(new Cell().add(new Paragraph("Weight Per Box")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));
        table2.addCell(new Cell().add(new Paragraph("Total Weight")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));


//      double result= weightPerBoxM1(gsmInM1,cuttingLength,decalLength);
//      double result = weightPerBoxF2(gsmInF2  ,cuttingLength ,decalLength  ,ffinf2);
//      double result=weightPerBoxM2(gsmInM2,cuttingLength,decalLength);
//      double result=weightPerBoxF3(gsmInF3,cuttingLength,decalLength,ffinf3);
        double totalWeight = 0.0;
        double grossWeight = 0.0;
        BoxFormula boxFormula = new BoxFormula();
        boxFormula.setNumberOfBox(boxQuantity);
        switch (ply) {
            case "1.0Ply" -> {
                table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
//            double result = weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength);
//            String gm = String.valueOf(result * 1000);
//            table2.addCell(new Cell().add(new Paragraph(gm + "gm")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            }
            case "2.0Ply" -> {
                // If noofPly is 2, set the values for 2-ply scenario
                if (bfInTop.equalsIgnoreCase(bfInF1) && gsmInTop.equalsIgnoreCase(gsmInF1)) {
                    table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
                    table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));

                    double weightPerBoxTopPaper = boxFormula.weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength);
                    double weightPerBoxFlute1 = boxFormula.weightPerBoxFlute1(gsmInF1, decalLength, cuttingLength, ffinf1);
                    table2.addCell(new Cell().add(new Paragraph(weightPerBoxTopPaper + weightPerBoxFlute1 + "gm")).setTextAlignment(TextAlignment.CENTER));

                    totalWeight = boxFormula.getTotalWeightPerBoxTopGm() + boxFormula.getTotalWeightPerBoxFlute1Gm();
                    table2.addCell(new Cell().add(new Paragraph(totalWeight * boxQuantity + "gm")).setTextAlignment(TextAlignment.CENTER));
                    grossWeight = totalWeight;

                } else {
                    table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
                    table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
                    table2.addCell(new Cell().add(new Paragraph(boxFormula.weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength) + "gm")).setTextAlignment(TextAlignment.CENTER));
                    double totalWeightTopgm = boxFormula.getTotalWeightPerBoxTopGm();
                    double totalWeightTopKG = 0.0;
                    String totalWeightTopUnit = "";
                    if (totalWeightTopgm % 1000 == 0) {
                        totalWeightTopKG = totalWeightTopgm / 1000;
                        totalWeightTopUnit = "Kg";
                    } else {
                        totalWeightTopKG = totalWeightTopgm;

                        totalWeightTopUnit = "gm";
                    }
                    table2.addCell(new Cell().add(new Paragraph(totalWeightTopKG + totalWeightTopUnit)).setTextAlignment(TextAlignment.CENTER));

                    table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
                    table2.addCell(new Cell().add(new Paragraph((bfInF1) + "/" + (gsmInF1))).setTextAlignment(TextAlignment.CENTER));
                    double weightPerBoxFlute1 = boxFormula.weightPerBoxFlute1(gsmInF1, decalLength, cuttingLength, ffinf1);
                    table2.addCell(new Cell().add(new Paragraph(weightPerBoxFlute1 + "gm")).setTextAlignment(TextAlignment.CENTER));
                    double totalWeightF1gm = boxFormula.getTotalWeightPerBoxFlute1Gm();
                    double totalWeightF1Kg = 0.0;
                    String totalWeightF1Unit = "";
                    if (totalWeightF1gm % 1000 == 0) {
                        totalWeightF1Kg = totalWeightF1gm / 1000;
                        totalWeightF1Unit = "Kg";
                    } else {
                        totalWeightF1Kg = totalWeightF1gm;
                        totalWeightF1Unit = "gm";
                    }
                    table2.addCell(new Cell().add(new Paragraph(totalWeightF1Kg + totalWeightF1Unit)).setTextAlignment(TextAlignment.CENTER));

                    totalWeight = boxFormula.getWeightPerBoxTopGm() + boxFormula.getWeightPerBoxFlute1Gm();

                    grossWeight = totalWeightTopgm + totalWeightF1gm;

                }

            }
            case "3.0Ply" -> {

            }
            case "5.0Ply" -> {
                String topRatio, flute1Ratio, middle1Ratio, flute2Ratio, bottomRatio;
                topRatio = bfInTop + "/" + gsmInTop;
                flute1Ratio = bfInF1 + "/" + gsmInF1;
                middle1Ratio = bfInM1 + "/" + gsmInM1;
                flute2Ratio = bfInF2 + "/" + gsmInF2;
                bottomRatio = bfInBottom + "/" + gsmInBottom;

                ArrayList<Double> weightPerBoxTopList = new ArrayList<>();
                ArrayList<Double> weightPerBoxFlute1List = new ArrayList<>();
                ArrayList<Double> weightPerBoxMiddle1List = new ArrayList<>();
                ArrayList<Double> weightPerBoxFlute2List = new ArrayList<>();
                ArrayList<Double> weightPerBoxBottomList = new ArrayList<>();

                HashMap<String, ArrayList<Double>> weightMap = new HashMap<>();
                //top
                weightPerBoxTopList.add(boxFormula.weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength));
                weightMap.put(topRatio, weightPerBoxTopList);
                //flute1
                if (weightMap.containsKey(flute1Ratio)) {
                    weightMap.get(flute1Ratio).add(boxFormula.weightPerBoxFlute1(gsmInF1, decalLength, cuttingLength, ffinf1));
                } else {
                    weightPerBoxFlute1List.add(boxFormula.weightPerBoxFlute1(gsmInF1, decalLength, cuttingLength, ffinf1));
                    weightMap.put(flute1Ratio, weightPerBoxFlute1List);
                }
                //middle1
                if (weightMap.containsKey(middle1Ratio)) {
                    weightMap.get(middle1Ratio).add(boxFormula.weightPerBoxMiddle1(gsmInM1, cuttingLength, decalLength));
                } else {
                    weightPerBoxMiddle1List.add(boxFormula.weightPerBoxMiddle1(gsmInM1, cuttingLength, decalLength));
                    weightMap.put(middle1Ratio, weightPerBoxMiddle1List);
                }
                //flute2
                if (weightMap.containsKey(flute2Ratio)) {
                    weightMap.get(flute2Ratio).add(boxFormula.weightPerBoxFlute2(gsmInF2, cuttingLength, decalLength, ffinf2));
                } else {
                    weightPerBoxFlute2List.add(boxFormula.weightPerBoxFlute2(gsmInF2, cuttingLength, decalLength, ffinf2));
                    weightMap.put(flute2Ratio, weightPerBoxFlute2List);
                }
                //bottom
                if (weightMap.containsKey(bottomRatio)) {
                    weightMap.get(bottomRatio).add(boxFormula.weightPerBoxBottomPaper(gsmInBottom, cuttingLength, decalLength));
                } else {
                    weightPerBoxBottomList.add(boxFormula.weightPerBoxBottomPaper(gsmInBottom, cuttingLength, decalLength));
                    weightMap.put(bottomRatio, weightPerBoxBottomList);
                }

                //hash map iterate
                //set key as ratio
                //set value as wight per box
            }
            case "7.0Ply" -> {

            }
            case "2.0Ply(KG)" -> {

            }
        }


        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));

        table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER));
        String totalWeightUnit = "";
        if (totalWeight % 1000 == 0) {
            totalWeight = totalWeight / 1000;
            totalWeightUnit = "Kg";
        } else {
            totalWeightUnit = "gm";
        }
        table2.addCell(new Cell().add(new Paragraph(totalWeight + totalWeightUnit)).setTextAlignment(TextAlignment.CENTER));
        if (grossWeight % 1000 == 0) {
            grossWeight = grossWeight / 1000;
            grossWeightUnit = "Kg";
        } else {
            grossWeightUnit = "gm";
        }
        table2.addCell(new Cell().add(new Paragraph(grossWeight + grossWeightUnit)).setTextAlignment(TextAlignment.CENTER));


        float columnWidth3[] = {482};
        Table table3 = new Table(columnWidth3);

        table3.addCell(new Cell().add(new Paragraph("Auto generated copy.no signature required.")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

        document.add(table);
        document.add(table1);
        document.add(table2);
        document.add(new Paragraph("For " + businessDetailsResponse.getData().getBusinessname()).setTextAlignment(TextAlignment.RIGHT));
        document.add(table3);
        document.close();
        Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show();
//        viewFile(file);
        openPDF("Production.pdf");
    }
}