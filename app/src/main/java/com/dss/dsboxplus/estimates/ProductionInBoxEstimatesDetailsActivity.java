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
import com.dss.dsboxplus.model.EstimatesDataModel;
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
import java.util.HashMap;

public class ProductionInBoxEstimatesDetailsActivity extends BaseActivity {
    EstimatesDataModel estimatesDataModel;
    ActivityProductionInBoxEstimatesDetailsBinding productionInBoxEstimatesDetailsBinding;
    double mm = 25.4;
    double divide = 1000.0;
    private DataItem dataItem;

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
                String boxQuantity = productionInBoxEstimatesDetailsBinding.tietBoxQuantity.getText().toString().trim();
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

        HashMap<String, String> bfMap = new HashMap();
        bfMap.put(bfInTop, "bfInTop");
        bfMap.put(bfInF1, "bfInF1");
        bfMap.put(bfInM1, "bfInM1");
        bfMap.put(bfInF2, "bfInF2");
        bfMap.put(bfInM2, "bfInM2");
        bfMap.put(bfInF3, "bfInF3");
        bfMap.put(bfInBottom, "bfInBottom");


        String gsmInTop = intent.getStringExtra("gsmInTop");
        String gsmInF1 = intent.getStringExtra("gsmInF1");
        String gsmInM1 = intent.getStringExtra("gsmInM1");
        String gsmInF2 = intent.getStringExtra("gsmInF2");
        String gsmInM2 = intent.getStringExtra("gsmInM2");
        String gsmInF3 = intent.getStringExtra("gsmInF3");
        String gsmInBottom = intent.getStringExtra("gsmInBottom");

        HashMap<String, String> gsmMap = new HashMap();
        gsmMap.put(gsmInTop, "gsmInTop");
        gsmMap.put(gsmInF1, "gsmInF1");
        gsmMap.put(gsmInM1, "gsmInM1");
        gsmMap.put(gsmInF2, "gsmInF2");
        gsmMap.put(gsmInM2, "gsmInM2");
        gsmMap.put(gsmInF3, "gsmInF3");
        gsmMap.put(gsmInBottom, "gsmInBottom");


        String rate = intent.getStringExtra("rate");
        String ply = intent.getStringExtra("ply");
        String weight = intent.getStringExtra("weight");
        String ups = intent.getStringExtra("ups");
        String decalLength = intent.getStringExtra("decalLength");
        String cuttingLength = intent.getStringExtra("cuttingLength");
        String decalSizemm = intent.getStringExtra("decalSizemm");
        String cuttingLengthmm = intent.getStringExtra("cuttingLengthmm");

        Client clientDetails = ConfigDataProvider.INSTANCE.getClientIdMap().get(intent.getLongExtra("clientId", 0));


//        formulaForWeightPreBox(bfInTop, gsmInTop, cuttingLength, decalLength);

        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();
        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
        }


        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
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
        table1.addCell(new Cell().add(new Paragraph("Cutting Length:" + cuttingLength + "-" + cuttingLengthmm)).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Decal Size:" + decalLength + "-" + decalSizemm)).setBorder(Border.NO_BORDER));
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


        if ("1.0Ply".equals(ply)) {
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInTop) + "/" + gsmMap.get(gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        } else if ("2.0Ply".equals(ply)) {
            // If noofPly is 2, set the values for 2-ply scenario
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInTop) + "/" + gsmMap.get(gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInF1) + "/" + gsmMap.get(gsmInF1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        } else if ("3.0Ply".equals(ply)) {
            // If noofPly is 2, set the values for 2-ply scenario
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInTop) + "/" + gsmMap.get(gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInF1) + "/" + gsmMap.get(gsmInF1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInBottom) + "/" + gsmMap.get(gsmInBottom))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

        } else if ("5.0Ply".equals(ply)) {
            // If noofPly is 2, set the values for 2-ply scenario
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInTop) + "/" + gsmMap.get(gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInF1) + "/" + gsmMap.get(gsmInF1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("3")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInM1) + "/" + gsmMap.get(gsmInM1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("4")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInF2) + "/" + gsmMap.get(gsmInF2))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("5")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInBottom) + "/" + gsmMap.get(gsmInBottom))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        } else if ("7.0Ply".equals(ply)) {
            // If noofPly is 2, set the values for 2-ply scenario
            table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInTop) + "/" + gsmMap.get(gsmInTop))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInF1) + "/" + gsmMap.get(gsmInF1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("3")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInM1) + "/" + gsmMap.get(gsmInM1))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));


            table2.addCell(new Cell().add(new Paragraph("4")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInF2) + "/" + gsmMap.get(gsmInF2))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("3")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInM2) + "/" + gsmMap.get(gsmInM2))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));


            table2.addCell(new Cell().add(new Paragraph("4")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInF3) + "/" + gsmMap.get(gsmInF3))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));

            table2.addCell(new Cell().add(new Paragraph("5")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph(bfMap.get(bfInBottom) + "/" + gsmMap.get(gsmInBottom))).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        }

//
//        table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
//        table2.addCell(new Cell().add(new Paragraph("12/12")).setTextAlignment(TextAlignment.CENTER));
//        table2.addCell(new Cell().add(new Paragraph("56gm")).setTextAlignment(TextAlignment.CENTER));
//        table2.addCell(new Cell().add(new Paragraph("112gm")).setTextAlignment(TextAlignment.CENTER));
//
//        table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
//        table2.addCell(new Cell().add(new Paragraph("15/12")).setTextAlignment(TextAlignment.CENTER));
//        table2.addCell(new Cell().add(new Paragraph("12gm")).setTextAlignment(TextAlignment.CENTER));
//        table2.addCell(new Cell().add(new Paragraph("24gm")).setTextAlignment(TextAlignment.CENTER));

        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));

        table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
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
    }

    private void formulaForWeightPreBox(String bfInTop, String gsmInTop, String cuttingLength, String decalLength) {
        double decal = Double.parseDouble(decalLength);
        double cutting = Double.parseDouble(cuttingLength);
        double gsmTop = Double.parseDouble(gsmInTop);
        double valueFirstOFTotalWeight = (decal * cutting * gsmTop * mm * mm / divide / divide / divide);
    }
}