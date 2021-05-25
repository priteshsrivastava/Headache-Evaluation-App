package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.*;
import com.example.myapplication.db.DBAnswerInfo;
import com.example.myapplication.db.DBBranchingLogicInfo;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBHeadacheCatInfo;
import com.example.myapplication.db.DBPatientResponseData;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.db.ReadOnlyDataStorage;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.security.AccessController.getContext;

public class ResultActivity extends AppCompatActivity {
    ArrayList<Float> uniqueLevelList;
    String pid,stDate,status;
    int tid;
    TextView textViewPid ;
    TextView textViewStDate;
    TextView textViewStatus;
    DBFuncAccess objDBFuncAccess;
    CmnMethods objCmnMethods;

    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

         textViewPid = findViewById(R.id.textView14);
         textViewStDate=findViewById(R.id.textView15);
         textViewStatus= findViewById(R.id.textView16);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pid = bundle.getString("pId", "");
        tid=bundle.getInt("tId");
        stDate=bundle.getString("test_start_date", "");
        status=bundle.getString("test_status", "");
        textViewPid.setText(pid);
        textViewStatus.setText(status);
        textViewStDate.setText(stDate);
        objDBFuncAccess=DBFuncAccess.getInstance(this);
        uniqueLevelList=getUniqueLevelList();
        refreshResultTable(tid);

        objCmnMethods= new CmnMethods(this);

        //to avoid exception: android.os.FileUriExposedException
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }


    void refreshResultTable( int tid){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.e("ResultActivity","Screen width:"+width+" height:"+height);

        TableLayout lvlTable = (TableLayout)findViewById(R.id.lvlTable);
        lvlTable.removeViewsInLayout(1,lvlTable.getChildCount()-1);

        lvlTable.setColumnShrinkable(2,true);

        //lvlTable.setStretchAllColumns(true);
        //lvlTable.bringToFront();

        ArrayList<DBTestDetailData> objDBTestDetailDataList=objDBFuncAccess.dbReadAllTestDetailData(tid,pid);
        for(int i = 0; i<uniqueLevelList.size(); i++){
            String levelStr=" L-"+uniqueLevelList.get(i);
            String levelStatus="NotDone";
            String result="   -   ";
            for(int j = 0; j< objDBTestDetailDataList.size(); j++){
                DBTestDetailData objDBTestDetailData=objDBTestDetailDataList.get(j);
                if(uniqueLevelList.get(i)==objDBTestDetailData.getLvl()){
                    levelStatus=objDBTestDetailData.getLvlStatus();
                    float ansId=objDBTestDetailData.getQueAnsId();
                    if(levelStatus.equalsIgnoreCase("Completed")) {
                        //result=getTestLevelResult(uniqueLevelList.get(i),ansId);
                        result = getTestResultStr(objDBTestDetailData.getLvlResultHeadacheCat());
                    }
                }
            }//end j
            Log.e("ResultActivity","Table width:"+lvlTable.getLayoutParams().width+" height:"+lvlTable.getLayoutParams().height);
            //set values in UI table
            TableRow tr =  new TableRow(this);
            tr.setBackgroundColor(Color.rgb(218,232,252)); //android:background="#0079D6"
            tr.setPadding(8,0,0,0);//android:padding="5dp"
            tr.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
            Log.e("ResultActivity","Table Row width:"+tr.getLayoutParams().width+" height:"+tr.getLayoutParams().height);
            TextView c1 = new TextView(this);
            c1.setGravity(Gravity.CENTER_HORIZONTAL);//170
            c1.setLayoutParams(new TableRow.LayoutParams(
                    (int) (width * 0.15),
                    TableRow.LayoutParams.WRAP_CONTENT));
            c1.setText(levelStr);
            TextView c2 = new TextView(this);
            c2.setGravity(Gravity.CENTER_HORIZONTAL);//310
            c2.setLayoutParams(new TableRow.LayoutParams(
                    (int) (width * 0.25),
                    TableRow.LayoutParams.WRAP_CONTENT));
            c2.setText(levelStatus);
            TextView c3 = new TextView(this);
            c3.setGravity(Gravity.CENTER_HORIZONTAL);//400
            c3.setLayoutParams(new TableRow.LayoutParams(
                    (int) (width * 0.5),
                    TableRow.LayoutParams.WRAP_CONTENT));
            c3.setText(result);
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            lvlTable.addView(tr);

        }//end i

//        TableLayout prices = (TableLayout)findViewById(R.id.lvlTable);
//        prices.setStretchAllColumns(true);
//        prices.bringToFront();
//        for(int i = 0; i < drug.length; i++){
//            TableRow tr =  new TableRow(this);
//            TextView c1 = new TextView(this);
//            c1.setText(drug[i].getName());
//            TextView c2 = new TextView(this);
//            c2.setText(String.valueOf(drug[i].getPrice()));
//            TextView c3 = new TextView(this);
//            c3.setText(String.valueOf(drug[i].getAmount()));
//            tr.addView(c1);
//            tr.addView(c2);
//            tr.addView(c3);
//            prices.addView(tr);
//        }
    }

    public void callHomeUI(View view){
        finish();
        Bundle bundle=new Bundle();
        bundle.putString("pid",pid);
        Intent intent = new Intent(this, PatientHomeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goBack(View view) {
        finish();
    }


    public String getTestLevelResult(float level, float ansId){
        String result="   -   ";
        for(int k=0;k<ReadOnlyDataStorage.answerInfoArrayList.size();k++){
            DBAnswerInfo objDBAnswerInfo=ReadOnlyDataStorage.answerInfoArrayList.get(k);
            if(objDBAnswerInfo.getAnsId()==ansId && objDBAnswerInfo.getLvl()==level){
                int headacheCat=objDBAnswerInfo.getHeadacheCat();
                for(int l=0;l<ReadOnlyDataStorage.headacheCatInfoArrayList.size();l++) {
                    DBHeadacheCatInfo objDBHeadacheCatInfo=ReadOnlyDataStorage.headacheCatInfoArrayList.get(l);
                    if(objDBHeadacheCatInfo.getHeadacheCat()==headacheCat){
                        result=objDBHeadacheCatInfo.getCatStrEn()+" / "+objDBHeadacheCatInfo.getCatStrHi();
                        break;
                    }
                }
                break;
            }
        }
        return result;
    }

    public String getTestResultStr(int headacheCat){
        String result="   -   ";
        for(int l=0;l<ReadOnlyDataStorage.headacheCatInfoArrayList.size();l++) {
            DBHeadacheCatInfo objDBHeadacheCatInfo=ReadOnlyDataStorage.headacheCatInfoArrayList.get(l);
            if(objDBHeadacheCatInfo.getHeadacheCat()==headacheCat){
                if(objDBHeadacheCatInfo.getCatStrHi().isEmpty() || objDBHeadacheCatInfo.getCatStrHi()==" " || objDBHeadacheCatInfo.getCatStrHi().trim().equalsIgnoreCase("-")){
                    result=objDBHeadacheCatInfo.getCatStrEn();
                }else{
                    Log.e("TAG","CatInHindi:"+objDBHeadacheCatInfo.getCatStrHi());
                    result=objDBHeadacheCatInfo.getCatStrEn()+" / "+objDBHeadacheCatInfo.getCatStrHi();
                }
                break;
            }
        }
        return result;
    }

    public ArrayList<Float> getUniqueLevelList(){
        ArrayList<Float> uniqueLevelList=new ArrayList<>();
        float tempLvl=0;
        for(int i=0; i< ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++){
            DBBranchingLogicInfo objDBBranchingLogicInfo=ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
            if(tempLvl!=objDBBranchingLogicInfo.getLvl()){
                uniqueLevelList.add(objDBBranchingLogicInfo.getLvl());
                tempLvl=objDBBranchingLogicInfo.getLvl();
            }
        }
        return uniqueLevelList;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean  onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_other, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.logoutMenu:
                //add the function to perform here
                callLogout();
                return(true);
            case R.id.syncMenu:
                //add the function to perform here
                objCmnMethods.callSync(objDBFuncAccess,pid);
                return(true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void callLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //Home is name of the activity
        builder.setMessage("Do you want to exit? \n क्या आप बाहर निकलना चाहते हैं?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                finish();
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                i.putExtra("finish", true);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
//                startActivity(i);
//                //finish();

                finishAffinity();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void callPatientResponseUI(View view) {
        fn_permission();
        if (boolean_permission) {
            Log.e("TAG","boolean_permission is: "+boolean_permission+" calling createPdf()");
            createResponsePdf();
        }else {
            Log.e("TAG","boolean_permission is: "+boolean_permission);
        }
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            Log.i("TAG","permission asking from user.");
            ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSIONS);
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

    private void createResponsePdf(){
        ArrayList<DBPatientResponseData> patientResponseList = objDBFuncAccess.dbReadAllPatientResponseData(tid,pid);

        String fileName="PatientResponse_"+pid+"_"+tid+".pdf";
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
            //use itextpdf tool
            Font fontUnderline=new Font();
            fontUnderline.setStyle(Font.UNDERLINE);

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
            Paragraph genPID=new Paragraph("Patient ID: "+pid);
            document.add(genPID);
            Paragraph genTID=new Paragraph("Test ID:"+tid);
            document.add(genTID);
            Paragraph genTime=new Paragraph("Report generated at: "+getCurrentDate());
            document.add(genTime);
            document.add(newLine);
            Paragraph genPatientRes=new Paragraph("Patient Response:");
            genPatientRes.setFont(fontUnderline);
            document.add(genPatientRes);
            document.add(newLine);
            PdfPTable table = new PdfPTable(new float[] { 1, 1, 6, 2 });
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Level");
            table.addCell("Que ID");
            table.addCell("Question");
            table.addCell("Response");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }
            for (int i=0;i<patientResponseList.size();i++){
                DBPatientResponseData objDBPatientResponseData=patientResponseList.get(i);
                float lvl=(float)objDBPatientResponseData.getLvl();
                float qid=(float)objDBPatientResponseData.getQueId();
                table.addCell(String.valueOf(lvl));
                table.addCell(String.valueOf(qid));
                DBBranchingLogicInfo objDBBranchingLogicInfo = objDBFuncAccess.dbReadBranchingLogicInfo(lvl, qid);
                table.addCell(objDBBranchingLogicInfo.getQueStrEn());
                if(objDBPatientResponseData.getResponseYesNo()==0){
                    table.addCell("No");
                }else if(objDBPatientResponseData.getResponseYesNo()==1){
                    table.addCell("Yes");
                }
            }
            document.add(table);
            document.add(newLine);
            Paragraph genTestRes=new Paragraph("Test Result:");
            genTestRes.setFont(fontUnderline);
            document.add(genTestRes);
            document.add(newLine);

            PdfPTable tableRes = new PdfPTable(new float[] { 1, 2, 7, 2 });
            tableRes.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableRes.addCell("Level");
            tableRes.addCell("Status");
            tableRes.addCell("Result");
            tableRes.addCell("ICHD3 Code");
            tableRes.setHeaderRows(1);
            PdfPCell[] cellsRes = tableRes.getRow(0).getCells();
            for (int j=0;j<cellsRes.length;j++){
                cellsRes[j].setBackgroundColor(BaseColor.GRAY);
            }

            ArrayList<DBTestDetailData> objDBTestDetailDataList=objDBFuncAccess.dbReadAllTestDetailData(tid,pid);
            for(int i = 0; i<uniqueLevelList.size(); i++){
                String levelStr = " L-"+uniqueLevelList.get(i);
                String levelStatus = "NotDone";
                String result = "   -   ";
                int headache_cat = -1;
                for(int j = 0; j< objDBTestDetailDataList.size(); j++){
                    DBTestDetailData objDBTestDetailData=objDBTestDetailDataList.get(j);
                    if(uniqueLevelList.get(i)==objDBTestDetailData.getLvl()){
                        levelStatus=objDBTestDetailData.getLvlStatus();
                        float ansId=objDBTestDetailData.getQueAnsId();
                        if(levelStatus.equalsIgnoreCase("Completed")) {
                            //result=getTestLevelResult(uniqueLevelList.get(i),ansId);
                            headache_cat = objDBTestDetailData.getLvlResultHeadacheCat();
                            result = getTestResultStr(headache_cat);
                        }
                    }
                }//end j
                tableRes.addCell(levelStr);
                tableRes.addCell(levelStatus);
                tableRes.addCell(result);
                tableRes.addCell(getICHD3Code(headache_cat));
            }//end i


            document.add(tableRes);
            document.add(newLine);
            Paragraph endStmt=new Paragraph("--- End of Report ---");
            endStmt.setAlignment(Element.ALIGN_CENTER);
            document.add(endStmt);

            document.close();
            Log.i("TAG","Pdf file generated successfully.");
            Toast.makeText(getApplicationContext(), "PDF report generated inside HEMA folder.", Toast.LENGTH_SHORT).show();

            //open pdf file
            viewPdf(fileName, directoryName);

        }catch (Exception e){
            Log.e("error","IOException in creating dir or file:"+e.getLocalizedMessage());
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

    public String getICHD3Code(int headache_cat){
        switch(headache_cat) {
            case -1:
                return "";
            case 0:
                return "";
            case 1:
                return "";
            case 2:
                return "";
            case 3:
                return "";
            case 4:
                return "";
            case 5:
                return "";
            case 6:
                return "";
            case 7:
                return "";
            case 8:
                return "";
            case 9:
                return "";
            case 10:
                return "";

            case 11:
                return "";
            case 12:
                return "";
            case 13:
                return "";
            case 14:
                return "";
            case 15:
                return "2.1/2.2";
            case 16:
                return "3.1.1";
            case 17:
                return "3.2.1";
            case 18:
                return "3.3";
            case 19:
                return "1.4.1";
            case 20:
                return "4.10";

            case 21:
                return "1.3";
            case 22:
                return "2.3";
            case 23:
                return "3.4";
            case 24:
                return "3.1.2";
            case 25:
                return "3.2.2";
            case 26:
                return "3.3";
            case 27:
                return "";
            case 28:
                return "4.7";
            case 29:
                return "4.1";
            case 30:
                return "4.2";

            case 31:
                return "4.3";
            case 32:
                return "4.5";
            case 33:
                return "4.5";
            case 34:
                return "4.6";
            case 35:
                return "4.6";
            case 36:
                return "4.9";
            case 37:
                return "4.8";
            case 38:
                return "8.2";
            case 39:
                return "8.2";
            case 40:
                return "8.2";
            case 41:
                return "8.2";
            case 42:
                return "8.2";
            case 43:
                return "8.2";
            case 44:
                return "4.4";
            default: //optional
                return "";
        }
    }

}
