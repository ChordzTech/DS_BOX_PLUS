package com.dss.dsboxplus.estimates;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
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

public class ProductionInBoxEstimatesDetailsActivity extends AppCompatActivity {
    EstimatesDataModel estimatesDataModel;
    ActivityProductionInBoxEstimatesDetailsBinding productionInBoxEstimatesDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        productionInBoxEstimatesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_production_in_box_estimates_details);
        super.onCreate(savedInstanceState);
        productionInBoxEstimatesDetailsBinding.btCloseInProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        productionInBoxEstimatesDetailsBinding.btCreatePDFInProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createProductionDsPdf();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        });

    }

    private void createProductionDsPdf() throws FileNotFoundException {
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
        table.addCell(new Cell().add(new Paragraph("Client Name")
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
        table1.addCell(new Cell().add(new Paragraph("Client Name:Pankaj")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Box Name:7Ply Box")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Box Outer Dimension:400mm X 200mm X 300mm")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Cutting Length:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Decal Size:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Ups:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("Production Quantity:")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));


        float columnWidth2[] = {62, 140, 140, 140};
        Table table2 = new Table(columnWidth2);

        table2.addCell(new Cell().add(new Paragraph("Paper")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));
        table2.addCell(new Cell().add(new Paragraph("Description")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));
        table2.addCell(new Cell().add(new Paragraph("Weight Per Box")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));
        table2.addCell(new Cell().add(new Paragraph("Total Weight")).setTextAlignment(TextAlignment.CENTER).setBackgroundColor(gray));

        table2.addCell(new Cell().add(new Paragraph("1")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("12/12")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("56gm")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("112gm")).setTextAlignment(TextAlignment.CENTER));

        table2.addCell(new Cell().add(new Paragraph("2")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("15/12")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("12gm")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("24gm")).setTextAlignment(TextAlignment.CENTER));

        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("\n")).setTextAlignment(TextAlignment.CENTER));

        table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("66gm")).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell().add(new Paragraph("136gm")).setTextAlignment(TextAlignment.CENTER));


        float columnWidth3[] = {482};
        Table table3 = new Table(columnWidth3);

        table3.addCell(new Cell().add(new Paragraph("Auto generated copy.no signature required.")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

        document.add(table);
        document.add(table1);
        document.add(table2);
        document.add(new Paragraph("For Pankaj").setTextAlignment(TextAlignment.RIGHT));
        document.add(table3);
        document.close();
        Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show();
    }
}