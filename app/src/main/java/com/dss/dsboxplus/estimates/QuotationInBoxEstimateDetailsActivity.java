package com.dss.dsboxplus.estimates;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
import com.dss.dsboxplus.data.repo.response.Client;
import com.dss.dsboxplus.databinding.ActivityQuotationInBoxEstimateDetailsBinding;
import com.dss.dsboxplus.fragments.ProfileFragment;
import com.google.android.material.button.MaterialButton;
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
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class QuotationInBoxEstimateDetailsActivity extends BaseActivity {
    MaterialButton btCreatePDF;
    ActivityQuotationInBoxEstimateDetailsBinding activityQuotationInBoxEstimateDetailsBinding;
    private boolean isPaperSpecification = false;
    private boolean isTaxEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuotationInBoxEstimateDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_quotation_in_box_estimate_details);
        initView();


    }

    private void initView() {
//
        activityQuotationInBoxEstimateDetailsBinding.swEnablePaperSpecification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isPaperSpecification = true;
                } else {
                    isPaperSpecification = false;
                }
            }
        });
        activityQuotationInBoxEstimateDetailsBinding.swEnableTaxAndPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isTaxEnable = true;
                } else {
                    isTaxEnable = false;
                }
            }
        });

        activityQuotationInBoxEstimateDetailsBinding.btCloseInQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btCreatePDF = findViewById(R.id.btCreateQuotationPDF);
        btCreatePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createDsPdf();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void createDsPdf() throws FileNotFoundException {

        Intent intent = getIntent();

        // Retrieve the value using the key "length"
        String lengthValue = intent.getStringExtra("length");
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

        String gsmInTop = intent.getStringExtra("gsmInTop");
        String gsmInF1 = intent.getStringExtra("gsmInF1");
        String gsmInM1 = intent.getStringExtra("gsmInM1");
        String gsmInF2 = intent.getStringExtra("gsmInF2");
        String gsmInM2 = intent.getStringExtra("gsmInM2");
        String gsmInF3 = intent.getStringExtra("gsmInF3");
        String gsmInBottom = intent.getStringExtra("gsmInBottom");

        String rateA = intent.getStringExtra("rate");
        float rateAF = Float.parseFloat(rateA);
        String ply = intent.getStringExtra("ply");
        Double tax = intent.getDoubleExtra("tax", 0);
        String resultForTaxThreeDigits = intent.getStringExtra("resultForTaxThreeDigits");
        float resTaxForAddition = Float.parseFloat(resultForTaxThreeDigits);

        float addition = rateAF + resTaxForAddition;
        String formattedResult = String.format("%.2f", addition);

        Client clientDetails = ConfigDataProvider.INSTANCE.getClientIdMap().get(intent.getLongExtra("clientId", 0));


        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
        }

        File dsBox = this.getBaseContext().getExternalFilesDir("ds_box");
        if (!dsBox.exists())
            dsBox.mkdir();
        String pdfPath = dsBox.getPath();
        File file = new File(pdfPath, "Quotation.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(file);

        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        DeviceRgb gray = new DeviceRgb(128, 128, 128);

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

//        ProfileFragment fragment = new ProfileFragment();
//        String profilePictureFilePath = fragment.getProfilePictureFilePath();
//        Bitmap bitmap = BitmapFactory.decodeFile(profilePictureFilePath);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] bitmapData = stream.toByteArray();
//        ImageData imageData = ImageDataFactory.create(bitmapData);
//        Image image = new Image(imageData);
//        image.setWidth(80f);


        //table 1-01
        table.addCell(new Cell(4, 1).add(image1).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(businessDetailsResponse.getData().getBusinessname())
                .setTextAlignment(TextAlignment.CENTER)).setFontSize(20f).setBold().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //table 1-02
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(businessDetailsResponse.getData().getAddress() + "," + businessDetailsResponse.getData().getPincode()).setTextAlignment(TextAlignment.CENTER).setBold()).setBorder(Border.NO_BORDER));
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

        //table 1-05
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Quotation")).setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //table 1-06
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(LocalDate.now().format(dateTimeFormatter).toString())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));


        float columnWidth1[] = {62, 324, 174};
        Table table1 = new Table(columnWidth1);

        //table2-01
        table1.addCell(new Cell().add(new Paragraph("Sr.No")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));
        table1.addCell(new Cell().add(new Paragraph("Description")).setBackgroundColor(gray));
        table1.addCell(new Cell().add(new Paragraph("Rate")).setTextAlignment(TextAlignment.RIGHT).setBackgroundColor(gray));

        //table2-02
        table1.addCell(new Cell().add(new Paragraph("1")));
        String paperSpecification = "";
        if (isPaperSpecification) {
            paperSpecification = ply + "Box," + boxName + "\n" +
                    "Box Outer Dimensions- " + lengthValue + "X" + width + "X" + height + "\n" +
                    "Paper Specification as below\n";

            switch (ply != null ? ply : "") {
                case "1Ply" ->
                        paperSpecification += "1.Top Paper" + bfInTop + "/" + gsmInTop + "\n";
                case "2Ply", "2Ply(KG)" ->
                        paperSpecification += "1.Top Paper" + bfInTop + "/" + gsmInTop + "\n" +
                                "2.Flute Paper" + bfInF1 + "/" + gsmInF1 + "\n";
                case "3Ply" ->
                        paperSpecification += "1.Top Paper" + bfInTop + "/" + gsmInTop + "\n" +
                                "2.Flute Paper" + bfInF1 + "/" + gsmInF1 + "\n" +
                                "3.Bottom Paper" + bfInBottom + "/" + gsmInBottom + "\n";
                case "5Ply" ->
                        paperSpecification += "1.Top Paper" + bfInTop + "/" + gsmInTop + "\n" +
                                "2.Flute Paper" + bfInF1 + "/" + gsmInF1 + "\n" +
                                "3.Middle One Paper" + bfInM1 + "/" + gsmInM1 + "\n" +
                                "4.Flute Two Paper" + bfInF2 + "/" + gsmInF2 + "\n" +
                                "5.Bottom Paper" + bfInBottom + "/" + gsmInBottom + "\n";
                case "7Ply" ->
                        paperSpecification += "1.Top Paper" + bfInTop + "/" + gsmInTop + "\n" +
                                "2.Flute Paper" + bfInF1 + "/" + gsmInF1 + "\n" +
                                "3.Middle One Paper" + bfInM1 + "/" + gsmInM1 + "\n" +
                                "4.Flute Two Paper" + bfInF2 + "/" + gsmInF2 + "\n" +
                                "5.Middle Two Paper" + bfInM2 + "/" + gsmInM2 + "\n" +
                                "6.Flute Three Paper" + bfInF3 + "/" + gsmInF3 + "\n" +
                                "7.Bottom Paper" + bfInBottom + "/" + gsmInBottom + "\n";
            }
        } else {
            paperSpecification = ply + " Box," + boxName;
        }
        table1.addCell(new Cell().add(new Paragraph(paperSpecification)));

        String rate = "";
        if (isTaxEnable) {

            rate = "Rs " + rateA + "\n" +
                    "Tax @ " + tax + "-" + resultForTaxThreeDigits + " Rs\n" +
                    "Total: " + formattedResult + " Rs";

        } else {
            rate = "Rs " + rateA + "\n";

        }
        table1.addCell(new Cell().add(new Paragraph(rate)).setTextAlignment(TextAlignment.RIGHT));
//        table1.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.RIGHT));

//        Drawable d3 = getDrawable(R.drawable.thanks);
//        Bitmap bitmap3 = ((BitmapDrawable) d3).getBitmap();
//        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
//        bitmap3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
//        byte[] bitmapData3 = stream3.toByteArray();
//        ImageData imageData3 = ImageDataFactory.create(bitmapData3);
//        Image image3 = new Image(imageData3);
//        image3.setHeight(80);
//        image3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        document.add(table);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("To,\n" +
                clientDetails.getClientname() + "\n" +
                clientDetails.getAddress() + "\n" +
                clientDetails.getMobileno()));
        document.add(new Paragraph("\n"));
        document.add(table1);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("for " + businessDetailsResponse.getData().getBusinessname()).setFontSize(18f).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Terms & Conditions:"));

        //Get the estimation note from business details
        String estimateNote = businessDetailsResponse.getData().getEstimatenote();

        // Replace any occurrence of '-' followed by a number with a newline character
        estimateNote = estimateNote.replaceAll("-", "\n");
//        // Set the text in the EditText
//        String[] lines = estimateNote.split("\n");


//        for (String line : lines) {
//            document.add(new Paragraph(line));
//        }
        document.add(new Paragraph(estimateNote));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("Auto generated copy,no signature required").setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM).setFontSize(10f));
//        document.add(image3);
        document.close();
        Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show();
//        viewFile(file);
        openPDF("Quotation.pdf");
    }
}