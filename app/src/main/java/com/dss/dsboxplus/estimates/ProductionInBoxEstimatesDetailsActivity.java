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

public class ProductionInBoxEstimatesDetailsActivity extends BaseActivity {

    ActivityProductionInBoxEstimatesDetailsBinding productionInBoxEstimatesDetailsBinding;
    double mm = 25.4;
    double divide = 1000.0;
    private DataItem dataItem;
    private String boxQuantity;

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
                boxQuantity = productionInBoxEstimatesDetailsBinding.tietBoxQuantity.getText().toString().trim();
                if (!boxQuantity.isEmpty()) {
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
        if (dsBox != null && !dsBox.exists())
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
        table.addCell(new Cell().add(new Paragraph(businessDetailsResponse.getData().getBusinessname())
                .setTextAlignment(TextAlignment.CENTER)).setFontSize(20f).setBold().setBorder(Border.NO_BORDER));
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
        if ("1.0Ply".equals(ply)) {
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            double result = weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength);
            String gm = String.valueOf(result * 1000);
            table2.addCell(new Cell().add(new Paragraph(gm + "gm")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));


        } else if ("2.0Ply".equals(ply)) {
            // If noofPly is 2, set the values for 2-ply scenario


            if (bfInTop.equalsIgnoreCase(bfInF1) && gsmInTop.equalsIgnoreCase(gsmInF1)) {
                table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
                double result = weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength);
                String gm = String.valueOf(result * 1000);
                table2.addCell(new Cell().add(new Paragraph(gm + "gm")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

                double weightTop = weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength);
                double weightF1 = weightPerBoxF1(gsmInF1, decalLength, cuttingLength, ffinf1);
                totalWeight = weightTop + weightF1;

                String gmF = String.valueOf(totalWeight * 1000);
                table2.addCell(new Cell().add(new Paragraph(gmF + "gm")).setTextAlignment(TextAlignment.CENTER));
                double grossWeight = Double.parseDouble(boxQuantity) * totalWeight;
                String grossWeightUnit = "";
                if (grossWeight / 1000 == 0) {
                    grossWeightUnit = "Kg";
                } else {
                    grossWeightUnit = "gm";
                }
                table2.addCell(new Cell().add(new Paragraph(String.valueOf(grossWeight))).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph(grossWeightUnit).setTextAlignment(TextAlignment.CENTER)));
            } else {
                table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
                double weightTop = weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength);
                String gm = String.valueOf(weightTop * 1000);
                table2.addCell(new Cell().add(new Paragraph(gm + "gm")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

                table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph((bfInF1) + "/" + (gsmInF1))).setTextAlignment(TextAlignment.CENTER));
                double weightF1 = weightPerBoxF1(gsmInF1, decalLength, cuttingLength, ffinf1);
                String gmF = String.valueOf(weightF1 * 1000);
                table2.addCell(new Cell().add(new Paragraph(gmF + "gm")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));


                totalWeight = weightTop + weightF1;

                double grossWeight = Double.parseDouble(boxQuantity) * totalWeight;
                String grossWeightUnit = "";
                if (grossWeight / 1000 == 0) {
                    grossWeightUnit = "Kg";
                } else {
                    grossWeightUnit = "gm";
                }
                table2.addCell(new Cell().add(new Paragraph(String.valueOf(grossWeight))).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph(grossWeightUnit).setTextAlignment(TextAlignment.CENTER)));
            }

        } else if ("3.0Ply".equals(ply)) {
            // If noofPly is 3, set the values for 3-ply scenario
            assert bfInTop != null;
            if (bfInTop.equalsIgnoreCase(bfInF1)) {
                bfInF1 = "";
            }
            if (bfInTop.equalsIgnoreCase(bfInBottom)) {
                bfInBottom = "";
            }
            assert bfInF1 != null;
            if (!bfInF1.isEmpty() && bfInF1.equalsIgnoreCase(bfInBottom)) {
                bfInBottom = "";
            }

            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            double result = weightPerBoxTopPaper(bfInTop, gsmInTop, decalLength, cuttingLength);
            String gm = String.valueOf(result * 1000);
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            assert bfInF1 != null;
            if (!bfInF1.isEmpty()) {
                table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph((bfInF1) + "/" + (gsmInF1))).setTextAlignment(TextAlignment.CENTER));
                double resultF = weightPerBoxF1(gsmInF1, decalLength, cuttingLength, ffinf1);
                String gmF = String.valueOf(resultF * 1000);
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            }
            assert bfInBottom != null;
            if (!bfInBottom.isEmpty()) {
                table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph((bfInBottom) + "/" + (gsmInBottom))).setTextAlignment(TextAlignment.CENTER));
                double resultB = weightPerBoxBottomPaper(gsmInBottom, cuttingLength, decalLength);
                String gmB = String.valueOf(resultB * 1000);
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
                table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            }
        } else if ("5.0Ply".equals(ply)) {
            // If noofPly is 2, set the values for 2-ply scenario
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInF1) + "/" + (gsmInF1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("3")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInM1) + "/" + (gsmInM1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("4")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInF2) + "/" + (gsmInF2))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("5")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInBottom) + "/" + (gsmInBottom))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        } else if ("7.0Ply".equals(ply)) {
            // If noofPly is 2, set the values for 2-ply scenario
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInTop) + "/" + (gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInF1) + "/" + (gsmInF1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("3")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInM1) + "/" + (gsmInM1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));


            table2.addCell(new Cell().add(new Paragraph("4")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInF2) + "/" + (gsmInF2))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("3")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInM2) + "/" + (gsmInM2))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));


            table2.addCell(new Cell().add(new Paragraph("4")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInF3) + "/" + (gsmInF3))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("5")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph((bfInBottom) + "/" + (gsmInBottom))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        }


        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));

        table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph(String.valueOf(totalWeight))).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));


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

    private double weightPerBoxF3(String gsmInF3, double cuttingLength, double decalLength, String ffinf3) {
        double gsmOff3 = Double.parseDouble(gsmInF3);
        double ffOFf3 = Double.parseDouble(ffinf3);
        double resForBoxF3 = ((cuttingLength * decalLength * gsmOff3 * ffOFf3 * mm * mm) / divide / divide / divide);
        return resForBoxF3;
    }

    private double weightPerBoxM2(String gsmInM2, double cuttingLength, double decalLength) {
        double gsmOfM2 = Double.parseDouble(gsmInM2);
        double resultForBoxM2 = ((cuttingLength * decalLength * gsmOfM2 * mm * mm) / divide / divide / divide);
        double resultThreeDigits = Double.valueOf(String.format("%.3f", resultForBoxM2));
        return resultThreeDigits;
    }

    private double weightPerBoxF2(String gsmInF2, double cuttingLength, double decalLength, String ffinf2) {
        double gsmOff2 = Double.parseDouble(gsmInF2);
        double fluteinF2 = Double.parseDouble(ffinf2);
        double resultForBoxF2 = ((cuttingLength * decalLength * gsmOff2 * fluteinF2 * mm * mm) / divide / divide / divide);
        double resultThreeDigits = Double.valueOf(String.format("%.3f", resultForBoxF2));
        return resultThreeDigits;
    }

    private double weightPerBoxM1(String gsmInM1, double cuttingLength, double decalLength) {
        double gsmOfM1 = Double.parseDouble(gsmInM1);
        double resultForMBoxM1 = ((cuttingLength * decalLength * gsmOfM1 * mm * mm) / divide / divide / divide);
        double resultThreeDigits = Double.valueOf(String.format("%.3f", resultForMBoxM1));
        return resultThreeDigits;
    }

    private double weightPerBoxBottomPaper(String gsmInBottom, double cuttingLength, double decalLength) {
        double gsmOfBottom = Double.parseDouble(gsmInBottom);
        double resultForBottom = ((decalLength * cuttingLength * gsmOfBottom * mm * mm) / divide / divide / divide);
        double resultThreeDigits = Double.valueOf(String.format("%.3f", resultForBottom));
        return resultThreeDigits;
    }

    private double weightPerBoxF1(String gsmInF1, double decalLength, double cuttingLength, String ffinf1) {
        double gsmOfF1 = Double.parseDouble(gsmInF1);
        double fluteInf1 = Double.parseDouble(ffinf1);
        double resultForF1 = ((decalLength * cuttingLength * gsmOfF1 * fluteInf1 * mm * mm) / divide / divide / divide);
        double resultThreeDigits = Double.valueOf(String.format("%.3f", resultForF1));
        return resultThreeDigits;
    }

    private double weightPerBoxTopPaper(String bfInTop, String gsmInTop, double decalLength, double cuttingLength) {
        double gsmOfTop = Double.parseDouble(gsmInTop);
        double resultForTop = ((decalLength * cuttingLength * gsmOfTop * mm * mm) / divide / divide / divide);
        double resultThreeDigits = Double.valueOf(String.format("%.3f", resultForTop));
        return resultThreeDigits;
    }

}