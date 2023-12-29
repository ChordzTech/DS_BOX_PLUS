package com.dss.dsboxplus.estimates;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityQuotationInBoxEstimateDetailsBinding;
import com.dss.dsboxplus.fragments.EstimatesFragment;
import com.dss.dsboxplus.model.EstimatesDataModel;
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
import java.util.ArrayList;

public class QuotationInBoxEstimateDetailsActivity extends AppCompatActivity {
    MaterialButton btCreatePDF;
    EstimatesDataModel estimatesDataModel;
    EstimatesFragment estimatesFragment;
    ActivityQuotationInBoxEstimateDetailsBinding activityQuotationInBoxEstimateDetailsBinding;
    private ArrayList<EstimatesDataModel> selectedEstimatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuotationInBoxEstimateDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_quotation_in_box_estimate_details);
//        if (getIntent().getExtras().getParcelable("EstimateDetails_Bundle") != null) {
//            estimatesDataModel = getIntent().getExtras().getBundle("EstimateDetails_Bundle").getParcelable("EstimateDetails");
//        }
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
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
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

        //table 1-01

        table.addCell(new Cell(4, 1).add(image1).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Pankaj Kashid")
                .setTextAlignment(TextAlignment.CENTER)).setFontSize(20f).setBold().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //table 1-02
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Uruli Kanchan,Pune Pincode-412202").setTextAlignment(TextAlignment.CENTER).setBold()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //table 1-03
//        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Contact-7972546880").setBold()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
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
        table1.addCell(new Cell().add(new Paragraph("3 Ply Box,DemoBox\n" +
                "Box outer Dimension-300mmX250mmX250mm\n" +
                "Paper Specification as below\n" +
                "1.Top Paper 14/200\n" +
                "2.Flute Paper 12/120\n" +
                "3.Bottom Paper 14/150")));
        table1.addCell(new Cell().add(new Paragraph("Rs 15.22\n" +
                "Tax@ 18.0%-2.74Rs\n" +
                "Total:17.96Rs")).setTextAlignment(TextAlignment.RIGHT));
        table1.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.RIGHT));

        Drawable d3 = getDrawable(R.drawable.thanks);
        Bitmap bitmap3 = ((BitmapDrawable) d3).getBitmap();
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
        byte[] bitmapData3 = stream3.toByteArray();
        ImageData imageData3 = ImageDataFactory.create(bitmapData3);
        Image image3 = new Image(imageData3);
        image3.setHeight(80);
        image3.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        document.add(table);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("To,\n" +
                "Manasi Snacks\n" +
                "Pune Maharashtra-412202\n" +
                "Ref.Contact-7972546880"));
        document.add(new Paragraph("\n"));
        document.add(table1);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("for " + "Pankaj Kashid").setFontSize(18f).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Terms & Conditions:"));
        document.add(new Paragraph("1.Valid : for 15 days only.\n" +
                "2.Taxes extra as applicable,if not mentioned in quoation.\n" +
                "3.Minimum order quantity 1000.If less than 1000,then transport charges extra."));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("Auto generated copy,no signature requried").setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM).setFontSize(10f));
        document.add(image3);
        document.close();
        Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show();
    }
}