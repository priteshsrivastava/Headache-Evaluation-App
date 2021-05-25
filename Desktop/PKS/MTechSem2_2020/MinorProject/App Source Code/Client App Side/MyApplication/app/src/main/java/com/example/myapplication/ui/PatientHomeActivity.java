package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.myapplication.*;
import com.example.myapplication.db.DBBranchingLogicInfo;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.db.ReadOnlyDataStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.example.myapplication.server.*;

public class PatientHomeActivity extends AppCompatActivity {
    public static final String TAG = "PatientHomeActivity";
    String pid;
    TextView textViewPidHome;
    DBFuncAccess objDBFuncAccess;
    ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList;
    ViewFlipper v_flipper;
    AlertDialog alert;
    CmnMethods objCmnMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        textViewPidHome = findViewById(R.id.textViewPidHome);
        //int images[]={R.drawable.patient,R.drawable.iit_gallery1,R.drawable.decision_tree,R.drawable.time_saving};
        int images[]={R.drawable.patient1,R.drawable.iit_gallery1,R.drawable.iit_gallery2,R.drawable.decision_tree_save_time};
        v_flipper=findViewById(R.id.v_flipper);
        for(int image:images){
            flipperImage(image);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pid = bundle.getString("pid", "");
        textViewPidHome.setText(pid);
        objDBFuncAccess = DBFuncAccess.getInstance(this);
        patientTestBasicInfoArrayList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
        objCmnMethods=new CmnMethods(this);
    }

    public void callNewTestUI(View view) {
        int level = 1, queId = 1, testId = 1;
        String queStr = "";
        DBBranchingLogicInfo objDBBranchingLogicInfo = objDBFuncAccess.dbReadBranchingLogicInfo(1, 1);
        queStr = "Que. "+objDBBranchingLogicInfo.getQueStrEn()+"\n प्रश्न- "+objDBBranchingLogicInfo.getQueStrHi();
        //Add entry in patient test data
        DBPatientTestBasicInfo objDBPatientTestBasicInfo = new DBPatientTestBasicInfo();
        objDBPatientTestBasicInfo.setpId(pid);
        objDBPatientTestBasicInfo.setDrId("");
        objDBPatientTestBasicInfo.setTestStDate(getCurrentDate()); //currentDate
        objDBPatientTestBasicInfo.setTestEndDate("");
        objDBPatientTestBasicInfo.setIsTestCompleted(0); //0:Incomplete, 1=Completed
        boolean insertStatus = objDBFuncAccess.dbAddPatientTestBasicInfo(objDBPatientTestBasicInfo);

       //Also add on server
//        DBWebServ objDBWebServ=new DBWebServ(this);
//        boolean delStatus=objDBWebServ.dbDeletePatientTestBasicInfo(38,pid);
//        Log.i(TAG,"Delete status:"+delStatus);
//        ArrayList<DBPatientTestBasicInfo> objDBPatientTestBasicInfoList=new ArrayList<>();
//        objDBPatientTestBasicInfoList.add(objDBPatientTestBasicInfo);
//        DBPatientTestBasicInfo objobjDBPatientTestBasicInfo2=new DBPatientTestBasicInfo();
//        objobjDBPatientTestBasicInfo2.settId(38);
//        objobjDBPatientTestBasicInfo2.setpId(objDBPatientTestBasicInfo.getpId());
//        objobjDBPatientTestBasicInfo2.setTestStDate(objDBPatientTestBasicInfo.getTestStDate());
//        objDBPatientTestBasicInfoList.add(objobjDBPatientTestBasicInfo2);
//        objDBWebServ.dbAddAllPatientTestBasicInfo(objDBPatientTestBasicInfoList);
//        //objDBWebServ.dbAddPatientTestBasicInfo(objDBPatientTestBasicInfo);
//        objDBWebServ.dbReadAllPatientTestBasicInfo();

        String toast = "";
        if (insertStatus == true) {
            toast = "New Test started for patient \n मरीज के लिए नया टेस्ट शुरू";
            DBTestDetailData objDBTestDetailData = new DBTestDetailData();
            ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
            if (patientTestBasicInfoList.size() > 0) {
                testId = patientTestBasicInfoList.get(0).gettId();
                objDBTestDetailData.settId(testId);
                objDBTestDetailData.setpId(pid);
                objDBTestDetailData.setLvl(level);
                objDBTestDetailData.setLvlStatus("Incomplete");//Lvl Status can be: Incomplete/Completed/NotDone
                objDBTestDetailData.setQueAnsId(queId);
                objDBFuncAccess.dbAddTestDetailData(objDBTestDetailData);
                Log.i(TAG, "New Test started for PID: " + pid + " with TestID:" + testId);
            }
            Bundle bundle = new Bundle();
            bundle.putString("pid", pid);
            bundle.putInt("testId", testId);
            bundle.putFloat("level", level);
            bundle.putFloat("queId", queId);
            bundle.putString("queStr", queStr);
            Intent intent = new Intent(this, QueActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            toast = "Unable to start new Test for PID: " + pid;
        }
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        Log.i(TAG,toast);
    }

    public void callRecentTestUI(View view) {
        float level = 1, queId = 1;
        int testId = 0;
        String queStr = "Que. Do you have headache?\nप्रश्न- क्या आपको सिरदर्द है?";
        patientTestBasicInfoArrayList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
        Log.i(TAG,"For PID:"+pid+", Size of patientTestBasicInfoArrayList:"+patientTestBasicInfoArrayList.size());
        if (patientTestBasicInfoArrayList.size() > 0) {
            DBPatientTestBasicInfo recentDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(0);
            testId = recentDBPatientTestBasicInfo.gettId();
            Log.i(TAG,"Calling ReadAllTestDetailData for testID:"+testId);

            if(recentDBPatientTestBasicInfo.getIsTestCompleted()==0) { //0:"Incomplete", 1:"Completed"
                ArrayList<DBTestDetailData> testDetailDataArrayList = objDBFuncAccess.dbReadAllTestDetailData(testId,pid);
                if (testDetailDataArrayList.size() > 0) {
                    DBTestDetailData recentDBTestDetailData = testDetailDataArrayList.get(0);
                    level = recentDBTestDetailData.getLvl();
                    queId = recentDBTestDetailData.getQueAnsId();
                    if(level==(float) 1 || level==(float) 2) {
                        for (int i = 0; i < ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++) {
                            DBBranchingLogicInfo objDBranchingLogicInfo = ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
                            if (objDBranchingLogicInfo.getQueId() == queId) {
                                queStr = "Que. " + objDBranchingLogicInfo.getQueStrEn() + "\n प्रश्न- " + objDBranchingLogicInfo.getQueStrHi();
                                break;
                            }
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", pid);
                        bundle.putInt("testId", testId);
                        bundle.putFloat("level", level);
                        bundle.putFloat("queId", queId);
                        bundle.putString("queStr", queStr);
                        Intent intent = new Intent(this, QueActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else if(level==(float) 3.1 || level==(float) 3.2 ) {
                        Bundle bundle = new Bundle();
                        bundle.putString("pid", pid);
                        bundle.putInt("testId", testId);
                        bundle.putFloat("level", (float) level);
                        bundle.putInt("queGroupId", (int)queId);

                        Intent intent = new Intent(this, QueBroadHeadacheGroupActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
//                    else if(level==(float) 4.1 || level==(float) 4.2 || level==(float) 4.3 ) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("pid", pid);
//                        bundle.putInt("testId", testId);
//                        bundle.putFloat("level", (float) level);
//                        bundle.putInt("queGroupId", (int)queId);
//
//                        Intent intent = new Intent(this, QueMigActivity.class);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
                } else {
                    Log.e(TAG, "No TestDetailData found for TestID:" + testId);
                }
            }else if(recentDBPatientTestBasicInfo.getIsTestCompleted()==1) {//0:"Incomplete", 1:"Completed"
                Bundle bundle = new Bundle();
                bundle.putString("pId", pid);
                bundle.putInt("tId", testId);
                bundle.putString("test_start_date", recentDBPatientTestBasicInfo.getTestStDate());
                bundle.putString("test_status", "Completed at "+recentDBPatientTestBasicInfo.getTestEndDate());
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            String toast = "No recent test found for patient \n रोगी के लिए हाल ही में कोई परीक्षण नहीं मिला";
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
            Log.i(TAG,"No recent test found for PID:" + pid);
        }

        //finish();
    }

    public void callHistoryUI(View view) {
        ArrayList<String> testStartDateList = new ArrayList<>();
        ArrayList<Integer> testStatusList = new ArrayList<>();
//        testStartDateList.add("2020-06-02 15:59:48");//default format is //YYYY-MM-DD HH24:MI:SS
//        testStartDateList.add("2020-05-09 10:55:08");
//        testStartDateList.add("2020-05-02 13:09:11");
        patientTestBasicInfoArrayList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
        if (patientTestBasicInfoArrayList.size() > 0) {
            for (int i = 0; i < patientTestBasicInfoArrayList.size(); i++) {
                DBPatientTestBasicInfo objDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(i);
                testStartDateList.add(objDBPatientTestBasicInfo.getTestStDate());
                testStatusList.add(objDBPatientTestBasicInfo.getIsTestCompleted());
            }
            Bundle bundle = new Bundle();
            bundle.putString("pid", pid);
            bundle.putStringArrayList("test_start_date_list", testStartDateList);
            bundle.putIntegerArrayList("test_status_list", testStatusList);
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            //finish();
        } else {
            String toast = "No Test History found for PID:" + pid+" \n रोगी के लिए कोई टेस्ट इतिहास नहीं मिला";
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
            Log.i(TAG,"No Test History found for PID:"+pid);
        }
    }

    public void callPatientDetailsUI(View view){
        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        Intent intent = new Intent(this, PatientDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void logout(View view) {
        callLogout();
    }

    public String getCurrentDate() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//YYYY-MM-DD HH24:MI:SS
        String todayString = formatter.format(todayDate);
        return todayString;
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
                Log.i("TAG","Context val(in switch):"+this);
                objCmnMethods.callSync(objDBFuncAccess,pid);
                return(true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   public void callLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //Home is name of the activity
        builder.setMessage("Do you want to exit?\nक्या आप बाहर निकलना चाहते हैं?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                finish();
//                Intent i = new Intent();
//                i.putExtra("finish", true);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
//                //startActivity(i);
//                finish();

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
        alert = builder.create();
        alert.show();
    }

    void flipperImage(int image){
        final ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);//4 sec
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        startActivity(new Intent(PatientHomeActivity.this,MainActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(alert!=null){
            alert.dismiss();
            alert=null;
        }
    }
}
