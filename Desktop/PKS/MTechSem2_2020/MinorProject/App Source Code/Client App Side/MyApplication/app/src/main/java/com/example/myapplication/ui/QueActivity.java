package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.*;
import com.example.myapplication.db.DBAnswerInfo;
import com.example.myapplication.db.DBBranchingLogicInfo;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBHeadacheCatInfo;
import com.example.myapplication.db.DBPatientResponseData;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.db.ReadOnlyDataStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QueActivity extends AppCompatActivity {
    public static final String TAG = "QueActivity";
    String pid;
    int testId = 0;
    float level = 1, queId = 1;
    String queStr = "";
    float MAX_LVL = 0;
    TextView textViewPidInQueUI;
    TextView textViewLevelQueUI;
    TextView textViewQueStrQueUI;
    RadioButton radioButtonYes, radioButtonNo;
    Button prevBtn;
    DBFuncAccess objDBFuncAccess;

    AlertDialog alert;
    CmnMethods objCmnMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que);

        textViewPidInQueUI = findViewById(R.id.textViewPidInQueUI);
        textViewLevelQueUI = findViewById(R.id.textViewLevelQueUI);
        textViewQueStrQueUI = findViewById(R.id.textViewQue);
        radioButtonYes = findViewById(R.id.radioButtonYes);
        radioButtonNo = findViewById(R.id.radioButtonNo);
        prevBtn = (Button) findViewById(R.id.button9);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pid = bundle.getString("pid", "");
        testId = bundle.getInt("testId");
        level = bundle.getFloat("level");
        queId = bundle.getFloat("queId");
        queStr = bundle.getString("queStr");
        textViewPidInQueUI.setText(pid);
        textViewLevelQueUI.setText("Level- " + level);
        textViewQueStrQueUI.setText(queStr);
        objDBFuncAccess = DBFuncAccess.getInstance(this);
        MAX_LVL = getMaxTestLvl();

        objCmnMethods = new CmnMethods(this);
//        setPrevButton();
    }

//    private void setPrevButton() {
//        if (queId == 1) {
//            prevBtn.setEnabled(false);
//        } else {
//            prevBtn.setEnabled(true);
//        }
//    }

    public void callTestResultUI(View view) {
//        String testStartDate = "2020-05-02 13:09:11";
//        String testStatus = "Complete";// "Incomplete" / "Complete"
//        Bundle bundle = new Bundle();
//        bundle.putString("pid", pid);
//        bundle.putString("test_start_date", testStartDate);
//        bundle.putString("test_status", testStatus);
//        Intent intent = new Intent(this, ResultActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//        //finish();

        ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
        if (patientTestBasicInfoArrayList.size() > 0) {
            DBPatientTestBasicInfo recentDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(0);

            String testStartDate = recentDBPatientTestBasicInfo.getTestStDate();
            String testStatus = "Incomplete";// "Incomplete" / "Completed"
            if (recentDBPatientTestBasicInfo.getIsTestCompleted() == 1) {
                testStatus = "Completed at " + recentDBPatientTestBasicInfo.getTestEndDate();
            }

            Bundle bundle = new Bundle();
            bundle.putString("pId", pid);
            bundle.putInt("tId", testId);
            bundle.putString("test_start_date", testStartDate);
            bundle.putString("test_status", testStatus);
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void callNextQue(View view) {
        Log.i(TAG, "radioButtonYes.isChecked():" + radioButtonYes.isChecked() + ", radioButtonNo.isChecked():" + radioButtonNo.isChecked());
        if (radioButtonYes.isChecked() == false && radioButtonNo.isChecked() == false) {
            Toast.makeText(getApplicationContext(), "Kindly select 'Yes' or 'No' option.", Toast.LENGTH_SHORT).show();
        } else {
            float nextQueId = 0, nextLvl = level;
            String nextQueStr = "";
            boolean isOptionYesSelected = true;
            if (!radioButtonYes.isChecked()) {
                isOptionYesSelected = false;
            }
            for (int i = 0; i < ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++) {
                DBBranchingLogicInfo objDBBranchingLogicInfo = ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
                if (objDBBranchingLogicInfo.getQueId() == queId && objDBBranchingLogicInfo.getLvl() == level) {
                    if (isOptionYesSelected) {
                        nextQueId = objDBBranchingLogicInfo.getAnsYesId();
                    } else {
                        nextQueId = objDBBranchingLogicInfo.getAnsNoId();
                    }
                    break;
                }
            }
            Log.i(TAG, "isOptionYesSelected:" + isOptionYesSelected + ", nextQueId:" + nextQueId);
            boolean isAnsId = true;

            savePatientResponse();

            for (int i = 0; i < ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++) {
                DBBranchingLogicInfo objDBBranchingLogicInfo = ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
                if (objDBBranchingLogicInfo.getQueId() == nextQueId && objDBBranchingLogicInfo.getLvl() == level) {
                    //continue with current level with next que
                    Log.i(TAG, "continue with current level with next Que. Next Que ID:" + nextQueId);
                    if (objDBBranchingLogicInfo.getQueStrHi().isEmpty()) {
                        nextQueStr = "Que. " + objDBBranchingLogicInfo.getQueStrEn();
                    } else {
                        nextQueStr = "Que. " + objDBBranchingLogicInfo.getQueStrEn() + "\nप्रश्न- " + objDBBranchingLogicInfo.getQueStrHi();
                    }
                    isAnsId = false;

                    //update in Test_detail_data
                    DBTestDetailData objDBTestDetailData = objDBFuncAccess.dbReadTestDetailData(testId, pid, level);
                    objDBTestDetailData.setQueAnsId(nextQueId);
                    objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailData);

                    //show in GUI
                    textViewQueStrQueUI.setText(nextQueStr);
                    ((RadioGroup) findViewById(R.id.radioGroup2)).clearCheck();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        textViewQueStrQueUI.setTooltipText(objDBBranchingLogicInfo.getRemarks());
                    }
                    queId = nextQueId;
                    queStr = nextQueStr;
//                    setPrevButton();
                    break;
                }
            }

            if (isAnsId == true) {
                //level completed, update patient test data and
                // present que.1 of next level (or show result if this is last level) to the User.
                for (int i = 0; i < ReadOnlyDataStorage.answerInfoArrayList.size(); i++) {
                    DBAnswerInfo objDBAnswerInfo = ReadOnlyDataStorage.answerInfoArrayList.get(i);
                    if (objDBAnswerInfo.getAnsId() == nextQueId && objDBAnswerInfo.getLvl() == level) {
                        float completingLvl = objDBAnswerInfo.getLvl();
                        int headacheCat = objDBAnswerInfo.getHeadacheCat();
                        String headacheCatStr = getHeadacheCatStr(headacheCat);
                        //update in Test_detail_data
                        DBTestDetailData objDBTestDetailDataUpdated = objDBFuncAccess.dbReadTestDetailData(testId, pid, level);
                        objDBTestDetailDataUpdated.setQueAnsId(nextQueId);
                        objDBTestDetailDataUpdated.setLvlResultHeadacheCat(headacheCat);
                        objDBTestDetailDataUpdated.setLvlStatus("Completed");
                        objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailDataUpdated);
                        if (completingLvl == MAX_LVL || headacheCat == 0 || headacheCat == 4) {  // 4 = Secondary Headache
                            Log.i(TAG, "For TestID:" + testId + ", Testing reached to the max level(" + MAX_LVL + ") or No Headache or Sec Headache.");
                            ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
                            if (patientTestBasicInfoArrayList.size() > 0) {
                                DBPatientTestBasicInfo recentDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(0);
                                recentDBPatientTestBasicInfo.setIsTestCompleted(1); //0:Incomplete, 1=Completed
                                recentDBPatientTestBasicInfo.setTestEndDate(getCurrentDate()); //currentDate
                                boolean updateStatus = objDBFuncAccess.dbUpdatePatientTestBasicInfo(recentDBPatientTestBasicInfo);
//                            String msgStr = "Level-"+level+" Testing completed. \nResult: "+headacheCatStr;
//                            showMsgByDialog(msgStr,"Level Completion Info !!!");
                                //show msg and Result GUI
                                String toast = "Testing completed.\nपरीक्षण पूरा हुआ ।";
                                Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
                                Log.i(TAG, "Testing completed for TestID:" + testId + " \nResult: " + headacheCatStr);
                                String testStartDate = recentDBPatientTestBasicInfo.getTestStDate();
                                String testStatus = "Completed at " + recentDBPatientTestBasicInfo.getTestEndDate();// "Incomplete" / "Completed"
                                Bundle bundle = new Bundle();
                                bundle.putString("pId", pid);
                                bundle.putInt("tId", testId);
                                bundle.putString("test_start_date", testStartDate);
                                bundle.putString("test_status", testStatus);
                                Intent intent = new Intent(this, ResultActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            //add data for next lvl testing
                            String msgStr = "Result/परिणाम:\n " + headacheCatStr;
                            //Toast.makeText(this,msgStr , Toast.LENGTH_LONG).show();
                            showMsgByDialog(msgStr, "Level-" + level + " Testing completed.\n स्तर-" + level + " परीक्षण पूरा हुआ ।");
                            Log.i(TAG, "For TestID:" + testId + ", Testing level(" + level + ") completed. Result: " + headacheCatStr);

                            nextLvl = getNextTestLvl(level);
                            Log.i(TAG, "NextLvl: " + nextLvl);
                            if (nextLvl == (float) 2) {
                                nextQueId = 1;
                                DBBranchingLogicInfo objDBBranchingLogicInfo = objDBFuncAccess.dbReadBranchingLogicInfo(nextLvl, nextQueId);
                                if (objDBBranchingLogicInfo.getQueStrHi().isEmpty()) {
                                    nextQueStr = "Que. " + objDBBranchingLogicInfo.getQueStrEn();
                                } else {
                                    nextQueStr = "Que. " + objDBBranchingLogicInfo.getQueStrEn() + "\nप्रश्न- " + objDBBranchingLogicInfo.getQueStrHi();
                                }
                                DBTestDetailData objDBTestDetailData = new DBTestDetailData();
                                objDBTestDetailData.settId(testId);
                                objDBTestDetailData.setpId(pid);
                                objDBTestDetailData.setLvl(nextLvl);
                                objDBTestDetailData.setLvlStatus("Incomplete");//Lvl Status can be: Incomplete/Completed/NotDone
                                objDBTestDetailData.setQueAnsId(nextQueId);
                                boolean res = objDBFuncAccess.dbAddTestDetailData(objDBTestDetailData);
                                if (res == true) {
                                    Log.i(TAG, "dbAddTestDetailData successfull for Lvl: " + nextLvl + " testId:" + testId);
                                } else {
                                    Log.i(TAG, "dbAddTestDetailData fail for Lvl: " + nextLvl + " testId:" + testId);
                                }

                                //show next lvl & next que str in UI
                                textViewLevelQueUI.setText(" Level- " + nextLvl + " ");
                                textViewQueStrQueUI.setText(nextQueStr);
//                            radioButtonYes.setChecked(false);
//                            radioButtonNo.setChecked(false);
                                ((RadioGroup) findViewById(R.id.radioGroup2)).clearCheck();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    textViewQueStrQueUI.setTooltipText(objDBBranchingLogicInfo.getRemarks());
                                }
                                level = nextLvl;
                                queId = nextQueId;
                                queStr = nextQueStr;
//                                setPrevButton();
                            } else if (nextLvl == (float) 3.1 || nextLvl == (float) 3.2) {
                                //Episodic case: getLevel1Result()==1
                                //Chronic case: getLevel1Result()==2
                                if (getLevel1Result() == 1) {
                                    nextLvl = (float) 3.1;
                                } else if (getLevel1Result() == 2) {
                                    nextLvl = (float) 3.2;
                                }
                                Log.i(TAG, "Calling QueBroadHeadacheGroupActivity for NextLvl: " + nextLvl);
                                int queGroupId = 1;
                                DBTestDetailData objDBTestDetailData = new DBTestDetailData();
                                objDBTestDetailData.settId(testId);
                                objDBTestDetailData.setpId(pid);
                                objDBTestDetailData.setLvl(nextLvl);
                                objDBTestDetailData.setLvlStatus("Incomplete");//Lvl Status can be: Incomplete/Completed/NotDone
                                objDBTestDetailData.setQueAnsId(queGroupId);
                                boolean res = objDBFuncAccess.dbAddTestDetailData(objDBTestDetailData);
                                if (res == true) {
                                    Log.i(TAG, "dbAddTestDetailData successfull for Lvl: " + nextLvl + " testId:" + testId);
                                } else {
                                    Log.e(TAG, "dbAddTestDetailData fail for Lvl: " + nextLvl + " testId:" + testId);
                                }

                                Bundle bundle = new Bundle();
                                bundle.putString("pid", pid);
                                bundle.putInt("testId", testId);
                                bundle.putFloat("level", nextLvl);
                                bundle.putInt("queGroupId", queGroupId);
                                Intent intent = new Intent(this, QueBroadHeadacheGroupActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.i(TAG, "For TestID:" + testId + ", no next case");
                                ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
                                if (patientTestBasicInfoArrayList.size() > 0) {
                                    DBPatientTestBasicInfo recentDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(0);
                                    recentDBPatientTestBasicInfo.setIsTestCompleted(1); //0:Incomplete, 1=Completed
                                    recentDBPatientTestBasicInfo.setTestEndDate(getCurrentDate()); //currentDate
                                    boolean updateStatus = objDBFuncAccess.dbUpdatePatientTestBasicInfo(recentDBPatientTestBasicInfo);
                                    //show msg and Result GUI
                                    String toast = "Testing completed.\nपरीक्षण पूरा हुआ ।";
                                    Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
                                    Log.i(TAG, "Testing completed for TestID:" + testId + " \nResult: " + headacheCatStr);
                                    String testStartDate = recentDBPatientTestBasicInfo.getTestStDate();
                                    String testStatus = "Completed at " + recentDBPatientTestBasicInfo.getTestEndDate();// "Incomplete" / "Completed"
                                    Bundle bundle = new Bundle();
                                    bundle.putString("pId", pid);
                                    bundle.putInt("tId", testId);
                                    bundle.putString("test_start_date", testStartDate);
                                    bundle.putString("test_status", testStatus);
                                    Intent intent = new Intent(this, ResultActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                        break;
                    }
                }
            }

        }

    }

    public void callHomeUI(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //Home is name of the activity
        builder.setMessage("Do you want to exit from Questionnaire? \n क्या आप प्रश्नावली से बाहर निकलना चाहते हैं?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
//                Bundle bundle=new Bundle();
//                bundle.putString("pid",pid);
//                Intent intent = new Intent(getApplicationContext(), PatientHomeActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
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

    public void showMsgByDialog(String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QueActivity.this); //Home is name of the activity
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }

    public String getHeadacheCatStr(int headacheCat) {
        String headacheCatStr = "";
        for (int l = 0; l < ReadOnlyDataStorage.headacheCatInfoArrayList.size(); l++) {
            DBHeadacheCatInfo objDBHeadacheCatInfo = ReadOnlyDataStorage.headacheCatInfoArrayList.get(l);
            if (objDBHeadacheCatInfo.getHeadacheCat() == headacheCat) {
                //headacheCatStr = objDBHeadacheCatInfo.getCatStrEn() + " / " + objDBHeadacheCatInfo.getCatStrHi();
                if (objDBHeadacheCatInfo.getCatStrHi().isEmpty() || objDBHeadacheCatInfo.getCatStrHi() == " " || objDBHeadacheCatInfo.getCatStrHi().trim().equalsIgnoreCase("-")) {
                    headacheCatStr = objDBHeadacheCatInfo.getCatStrEn();
                } else {
                    headacheCatStr = objDBHeadacheCatInfo.getCatStrEn() + " / " + objDBHeadacheCatInfo.getCatStrHi();
                }
                break;
            }
        }
        return headacheCatStr;
    }

    private String getCurrentDate() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//YYYY-MM-DD HH24:MI:SS
        String todayString = formatter.format(todayDate);
        return todayString;
    }

    public float getMaxTestLvl() {
        float MAX_LVL = 1;
        for (int i = 0; i < ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++) {
            DBBranchingLogicInfo objDBBranchingLogicInfo = ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
            if (MAX_LVL < objDBBranchingLogicInfo.getLvl()) {
                MAX_LVL = objDBBranchingLogicInfo.getLvl();
            }
        }
        return MAX_LVL;
    }

    public float getNextTestLvl(float currentLvl) {
        float nextTestLvl = currentLvl;
        Log.e(TAG, "branchingLogicInfoArrayList size:" + ReadOnlyDataStorage.branchingLogicInfoArrayList.size());
        for (int i = 0; i < ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++) {
            DBBranchingLogicInfo objDBBranchingLogicInfo = ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
            if (currentLvl < objDBBranchingLogicInfo.getLvl()) {
                nextTestLvl = objDBBranchingLogicInfo.getLvl();
                break;
            }
        }
        return nextTestLvl;
    }

    public void savePatientResponse() {
        DBPatientResponseData objDBPatientResponseData = new DBPatientResponseData();
        boolean isOptionYesSelected = true;
        if (!radioButtonYes.isChecked()) {
            isOptionYesSelected = false;
        }
        objDBPatientResponseData.setLvl(level);
        objDBPatientResponseData.settId(testId);
        objDBPatientResponseData.setpId(pid);
        objDBPatientResponseData.setQueGroupId((int) queId);
        objDBPatientResponseData.setQueId(queId);
        if (isOptionYesSelected == false) {
            objDBPatientResponseData.setResponseYesNo(0);//0:No, 1:Yes
        } else if (isOptionYesSelected == true) {
            objDBPatientResponseData.setResponseYesNo(1);//0:No, 1:Yes
        }
        boolean addStatus = objDBFuncAccess.dbAddPatientResponseData(objDBPatientResponseData);
        if (addStatus == true) {
            Log.i(TAG, "dbAddPatientResponseData successful.");
        } else {
            Log.e(TAG, "dbAddPatientResponseData fail.");
        }
    }

    public boolean rollbackPatientResponse() {
        boolean delStatus = objDBFuncAccess.dbDeletePatientResponseData(testId, pid, level, queId);
        if (delStatus == true) {
            Log.i(TAG, "dbDeletePatientResponseData successful for queId:" + queId);
        } else {
            Log.e(TAG, "dbDeletePatientResponseData fail for queId:" + queId);
        }
        return delStatus;
    }

    public int getLevel1Result() { //1: 'Episodic Headache' , 2: 'Chronic Headache'
        int level1Res = -1;
        DBTestDetailData objDBTestDetailData = objDBFuncAccess.dbReadTestDetailData(testId, pid, (float) 1.0);
        level1Res = objDBTestDetailData.getLvlResultHeadacheCat();
        return level1Res;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_other, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logoutMenu:
                //add the function to perform here
                callLogout();
                return (true);
            case R.id.syncMenu:
                //add the function to perform here
                objCmnMethods.callSync(objDBFuncAccess, pid);
                return (true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //Home is name of the activity
        builder.setMessage("Do you want to exit?\nक्या आप बाहर निकलना चाहते हैं?");
        builder.setPositiveButtonIcon(getResources().getDrawable(R.drawable.ic_check_black_24dp));
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

        builder.setNegativeButtonIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp));
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alert = builder.create();
        alert.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (alert != null) {
            alert.dismiss();
            alert = null;
        }
    }

    public void callPrevQue(View view) {
        Log.e(TAG, "callPrevQue for queId:" + queId);
        if (queId != 1.0) {
            //Rollback in PatientResponse
            ArrayList<DBPatientResponseData> objPatientResponseDataList;

            objPatientResponseDataList = objDBFuncAccess.dbReadAllPatientResponseData(testId, pid);
            DBPatientResponseData prevDBPatientResponseData = objPatientResponseDataList.get(objPatientResponseDataList.size() - 1);
            queId = prevDBPatientResponseData.getQueId();

            //Rollback in Test_detail_data
            DBTestDetailData objDBTestDetailData = objDBFuncAccess.dbReadTestDetailData(testId, pid, level);
            objDBTestDetailData.setQueAnsId(queId);
            boolean resUpdate = objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailData);

//            setPrevButton();

            for (int i = 0; i < ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++) {
                DBBranchingLogicInfo objDBranchingLogicInfo = ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
                if (objDBranchingLogicInfo.getQueId() == queId && objDBranchingLogicInfo.getLvl() == level) {
                    //continue with current level with next que
                    Log.i(TAG, "continue with current level with prev Que. Prev Que ID:" + queId);
                    if (objDBranchingLogicInfo.getQueStrHi().isEmpty()) {
                        queStr = "Que. " + objDBranchingLogicInfo.getQueStrEn();
                    } else {
                        queStr = "Que. " + objDBranchingLogicInfo.getQueStrEn() + "\nप्रश्न- " + objDBranchingLogicInfo.getQueStrHi();
                    }
                    break;
                }
            }
            textViewQueStrQueUI.setText(queStr);
            boolean resDel = objDBFuncAccess.dbDeletePatientResponseData(testId, pid, level, queId);
            if (resDel == true) {
                Log.i(TAG, "dbDeletePatientResponseData successful for queId:" + queId);
            } else {
                Log.e(TAG, "dbDeletePatientResponseData fail for queId:" + queId);
            }

            Log.i(TAG, "Rolled back to prev question.");

        } else {
            Log.e(TAG, "Unable to roll back to prev question from first que in current lvl.");
            String toast = "You cannot go back to prev level.";
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
