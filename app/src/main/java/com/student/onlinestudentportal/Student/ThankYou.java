package com.student.onlinestudentportal.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import com.student.onlinestudentportal.GlobalData;
import com.student.onlinestudentportal.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ThankYou extends AppCompatActivity {

    Button btnResult;
    TextView result_percents,result_correct_text,result_wrong_text,result_fullMarks_text;

    String course,department,roll,subject_name,regno,exam_heading,semester;
    int correct,wrong,total_marks,full_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        ActivityCompat.requestPermissions(ThankYou.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
        btnResult=findViewById(R.id.btnResult);
        result_correct_text=findViewById(R.id.results_correct_text);
        result_percents=findViewById(R.id.results_percent);
        result_wrong_text=findViewById(R.id.results_wrong_text);
        result_fullMarks_text=findViewById(R.id.results_fullMarks_text);
        course=getIntent().getStringExtra("Course");
        department=getIntent().getStringExtra("Department");
        subject_name=getIntent().getStringExtra("Subject_Name");
        semester=getIntent().getStringExtra("Semester");
        exam_heading=getIntent().getStringExtra("Exam_Heading");
        roll=getIntent().getStringExtra("Roll");
        regno=getIntent().getStringExtra("Reg_No");
        correct=getIntent().getIntExtra("Correct",0);
        wrong=getIntent().getIntExtra("Wrong",0);
        full_marks=getIntent().getIntExtra("Full_Marks",0);

        double c=(double)correct;
        double w=(double)full_marks;
        double percentage=(c/w)*100;
        result_fullMarks_text.setText(String.valueOf(full_marks));
        result_correct_text.setText(String.valueOf(correct));
        result_wrong_text.setText(String.valueOf(wrong));
        result_percents.setText(String.valueOf(percentage)+"%");
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getResult();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e("Data",e.getMessage());

                }
            }
        });

    }

    private void getResult() throws FileNotFoundException {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(filePath,"eleveResult.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(file);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0,0,0,0);
        Drawable d = getDrawable(R.drawable.student2);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Paragraph title = new Paragraph("Eleve Portail Exam Desk").setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
        float[] width = {100f , 100f};
        Table table = new Table(width);

        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(new Cell().add(new Paragraph("Candidate Name")));
        table.addCell(new Cell().add(new Paragraph(GlobalData.StudentName)));

        table.addCell(new Cell().add(new Paragraph("Total Question")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(full_marks))));

        table.addCell(new Cell().add(new Paragraph("Correct")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(correct))));

        table.addCell(new Cell().add(new Paragraph("Wrong")));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(wrong))));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        table.addCell(new Cell().add(new Paragraph("Date")));
        table.addCell(new Cell().add(new Paragraph(LocalDate.now().format(dateTimeFormatter).toString())));

        DateTimeFormatter imeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
        table.addCell(new Cell().add(new Paragraph("Time")));
        table.addCell(new Cell().add(new Paragraph(LocalTime.now().format(imeFormatter).toString())));

        BarcodeQRCode qrCode = new BarcodeQRCode(GlobalData.StudentName+"\n"+LocalDate.now().format(dateTimeFormatter)+"\n"+LocalTime.now().format(imeFormatter));
        PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK,pdfDocument);
        Image qr = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);

        document.add(image);
        document.add(title);
        document.add(table);
        document.add(qr);
        document.close();
        Toast.makeText(getApplicationContext(),"Downloding...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Exam Already ", Toast.LENGTH_SHORT).show();

    }
}