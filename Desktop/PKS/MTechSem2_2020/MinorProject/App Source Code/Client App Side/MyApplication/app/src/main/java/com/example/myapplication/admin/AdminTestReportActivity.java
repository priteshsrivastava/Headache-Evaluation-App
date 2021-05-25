package com.example.myapplication.admin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBHeadacheCatInfo;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.server.DBWebServ;
import com.example.myapplication.ui.PatientHomeActivity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;



import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.parser.Line;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminTestReportActivity extends AppCompatActivity {
    private List<PatientTestData> patientTestDataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdminTestReportAdapter mAdapter;

    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_test_report);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AdminTestReportAdapter(patientTestDataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // set the adapter
        recyclerView.setAdapter(mAdapter);

        //add the recycler view item click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PatientTestData movie = patientTestDataList.get(position);
                Toast.makeText(getApplicationContext(), movie.getpId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        preparePatientTestData();

        //to avoid exception: android.os.FileUriExposedException
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

       // fn_permission();
    }

    private void preparePatientTestData() {
        DBFuncAccess objDBFDBFuncAccess=DBFuncAccess.getInstance(this);
        ArrayList<DBHeadacheCatInfo> objDBHeadacheCatInfoArrayList = objDBFDBFuncAccess.dbReadAllHeadacheCatInfo();
        for(int i=0; i< DBWebServ.objPatientTestBasicInfoList.size(); i++){
            DBPatientTestBasicInfo movie = DBWebServ.objPatientTestBasicInfoList.get(i);
            String result="";
            for(int j=0; j< DBWebServ.objTestDetailDataList.size(); j++) {
                DBTestDetailData objTestDetailData = DBWebServ.objTestDetailDataList.get(j);
                if(objTestDetailData.getpId().equalsIgnoreCase(movie.getpId()) &&
                        objTestDetailData.gettId()==movie.gettId() &&
                        objTestDetailData.getLvlStatus().equalsIgnoreCase("Completed")){
                    for(int k=0;k<objDBHeadacheCatInfoArrayList.size();k++){
                        DBHeadacheCatInfo objDBHeadacheCatInfo=objDBHeadacheCatInfoArrayList.get(k);
                        if(objDBHeadacheCatInfo.getHeadacheCat()==objTestDetailData.getLvlResultHeadacheCat()){
                            result+=objDBHeadacheCatInfo.getCatStrEn()+", ";
                        }
                    }
                }
            }
            result = result.replaceAll(", $", "");//remove last comma
            PatientTestData objPatientTestData= new PatientTestData(movie.gettId(), movie.getpId(), movie.getDrId(), movie.getTestStDate(), movie.getTestEndDate(), movie.getIsTestCompleted(),result);
            patientTestDataList.add(objPatientTestData);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void callBack(View view) {
            finish();
    }

    public void callAdminHome(View view) {
        finish();
        Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
        startActivity(intent);
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void callPrintPatientTestReport(View view) {
        fn_permission();
        if (boolean_permission) {
//            progressDialog = new ProgressDialog(AdminTestReportActivity.this);
//            progressDialog.setMessage("Please wait");
            Log.e("TAG","boolean_permission is: "+boolean_permission+" calling createPdf()");
            createPdf();

        }else {
            Log.e("TAG","boolean_permission is: "+boolean_permission);
        }
        //createPdf();

    }


//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(){
        String fileName="PatientTestReport.pdf";
        String directoryName="HEMA";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+directoryName;
        File dir = new File(path);
        if(!dir.exists()) {
            Log.i("TAG","Folder does not exist. Creating new folder: "+directoryName);
            dir.mkdirs();
        }else{
            Log.i("TAG","Folder '"+directoryName+"' exist. ");
        }
        File file = new File(dir, fileName);

        try {
//            if (!file.exists()) {
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

//            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            float hight = displaymetrics.heightPixels ;
//            float width = displaymetrics.widthPixels ;
//
//            int convertHight = (int) hight, convertWidth = (int) width;
//            FileOutputStream fOut = new FileOutputStream(file);
//            PdfDocument document = new PdfDocument();
//            PdfDocument.PageInfo pageInfo = new
//                    PdfDocument.PageInfo.Builder(convertWidth, convertHight, 1).create();
//            PdfDocument.Page page = document.startPage(pageInfo);
//            Canvas canvas = page.getCanvas();
//            Paint paint = new Paint();
//            paint.setColor(Color.BLACK);
//            paint.setTextSize(40);
//            paint.setTextAlign(Paint.Align.CENTER);
//            int titleBaseLine = 72;
//            int leftMargin = 54; //convertWidth/2;
//            Log.i("TAG","titleBaseLine:"+titleBaseLine+" leftMargin:"+leftMargin+" convertWidth:"+convertWidth+" convertHight:"+convertHight);
//            canvas.drawText("Patient Test Report", leftMargin, titleBaseLine, paint);
//            document.finishPage(page);
//            document.writeTo(fOut);
//            document.close();

            //use itextpdf tool
            Document document = new Document(PageSize.A4, 10, 10, 10, 10);
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
            document.open();
            Paragraph title=new Paragraph("Patient Test Report");

            title.setAlignment(Element.ALIGN_CENTER);
            Font titleFont=new Font();
            titleFont.setSize(18);
            titleFont.setColor(BaseColor.BLUE);
            titleFont.setStyle(Font.BOLD);
            title.setFont(titleFont);
            document.add(title);
            Paragraph newLine=new Paragraph("\n");
            document.add(newLine);
            Paragraph genTime=new Paragraph("\tReport generated at: "+getCurrentDate());
            document.add(genTime);
            document.add(newLine);

            PdfPTable table = new PdfPTable(new float[] { 1, 2, 3, 2, 4 });
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sr. No.");
            table.addCell("Patient ID");
            table.addCell("Test Start Date");
            table.addCell("Test Status");
            table.addCell("Test Result");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }
            for (int i=0;i<patientTestDataList.size();i++){
                PatientTestData objPatientTestData=patientTestDataList.get(i);
                table.addCell(String.valueOf(i+1));
                table.addCell(objPatientTestData.getpId());
                table.addCell(objPatientTestData.getTestStDate());
                if(objPatientTestData.getIsTestCompleted()==0){
                    table.addCell("Incomplete");
                }else if(objPatientTestData.getIsTestCompleted()==1){
                    table.addCell("Completed");
                }
                table.addCell(objPatientTestData.getResult());
            }

            document.add(table);
            document.add(newLine);
            Paragraph endStmt=new Paragraph("--- End of Test Report ---");
            endStmt.setAlignment(Element.ALIGN_CENTER);
            document.add(endStmt);

            document.close();
            Log.i("TAG","Pdf file generated successfully.");
            Toast.makeText(getApplicationContext(), "PDF report generated.", Toast.LENGTH_SHORT).show();

            //open pdf file
            viewPdf(fileName, directoryName);

        }catch (Exception e){
            Log.e("error","IOException in creating dir or file:"+e.getLocalizedMessage());
        }
    }

    private void fn_permission() {
//        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
//                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
//            Log.i("TAG","permission asking from user.");
//            if ((ActivityCompat.shouldShowRequestPermissionRationale(AdminTestReportActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
//            } else {
//                ActivityCompat.requestPermissions(AdminTestReportActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_PERMISSIONS);
//
//            }
//
//            if ((ActivityCompat.shouldShowRequestPermissionRationale(AdminTestReportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
//            } else {
//                ActivityCompat.requestPermissions(AdminTestReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        REQUEST_PERMISSIONS);
//
//            }
//        } else {
//            boolean_permission = true;
//            Log.i("TAG","permission already granted from user.");
//        }
//

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            Log.i("TAG","permission asking from user.");

//            if ((ActivityCompat.shouldShowRequestPermissionRationale(AdminTestReportActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
//            } else {
                ActivityCompat.requestPermissions(AdminTestReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

//            }
        } else {
            boolean_permission = true;
            Log.i("TAG","permission already granted from user.");
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            Log.i("TAG","grantResults.length:"+grantResults.length+" permissions.length:"+permissions.length);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("TAG","grantResults[0]:"+grantResults[0] +" PackageManager.PERMISSION_GRANTED:"+PackageManager.PERMISSION_GRANTED);
                boolean_permission = true;
            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission and Try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {
       File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
    public String getCurrentDate() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//YYYY-MM-DD HH24:MI:SS
        String todayString = formatter.format(todayDate);
        return todayString;
    }

}

class PatientTestData extends DBPatientTestBasicInfo{
    String result;

    public PatientTestData(int tId, String pId, String drId, String testStDate, String testEndDate, int isTestCompleted, String result) {
        super(tId, pId, drId, testStDate, testEndDate, isTestCompleted);
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}