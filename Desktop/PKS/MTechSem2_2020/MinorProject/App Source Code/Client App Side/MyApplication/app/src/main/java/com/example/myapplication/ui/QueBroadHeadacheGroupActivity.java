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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.CmnMethods;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.DBBranchingLogicInfo;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBHeadacheCatInfo;
import com.example.myapplication.db.DBMultiBranchLogicInfo;
import com.example.myapplication.db.DBPatientResponseData;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.db.ReadOnlyDataStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class QueBroadHeadacheGroupActivity extends AppCompatActivity {

    public static final String TAG = "QueGrpActivity";
    String pid;
    int testId=0;
    float level =  3f;
    int queGroupId=1;

    float MAX_LVL=0;
    TextView textViewPidInGrpQueUI;
    TextView textViewLevelGrpQueUI;
    LinearLayout grpQueLinLayout;
    DBFuncAccess objDBFuncAccess;
    //initialize view's
    ListView simpleListViewGrpQue;
    ArrayList<DBBranchingLogicInfo> queSetList;
    AlertDialog alert;
    CmnMethods objCmnMethods;
    Button backBtnInQueBroadHdGp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_broad_headache_group);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textViewPidInGrpQueUI = findViewById(R.id.textViewPidInGrpQueUI);
        textViewLevelGrpQueUI = findViewById(R.id.textViewLevelGrpQueUI);
        grpQueLinLayout = findViewById(R.id.grpQueTopLinLayout);
        backBtnInQueBroadHdGp = findViewById(R.id.backBtnInQueBroadHdGp);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pid = bundle.getString("pid", "");
        testId=bundle.getInt("testId");
        level = bundle.getFloat("level");
        queGroupId=bundle.getInt("queGroupId");
        textViewPidInGrpQueUI.setText(pid);
        textViewLevelGrpQueUI.setText(" Level- " + level);


        objDBFuncAccess=DBFuncAccess.getInstance(this);
        MAX_LVL=getMaxTestLvl();
        showQue(level,queGroupId);

        objCmnMethods= new CmnMethods(this);

//        setPrevButton();
    }

    public void showQue(float lvl, int queGrpId) {
        queSetList = new ArrayList<DBBranchingLogicInfo> ();
        for (int i = 0; i < ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++) {
            DBBranchingLogicInfo objDBBranchingLogicInfo = ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
            if (objDBBranchingLogicInfo.getQueId() >= queGrpId && objDBBranchingLogicInfo.getQueId() < (queGrpId + 1)
                    && objDBBranchingLogicInfo.getLvl() == lvl) {
                queSetList.add(objDBBranchingLogicInfo);
            }
        }

        Log.i(TAG, "Lvl:"+lvl+" queGrpId:"+queGrpId+ " Size of queSetList:" + queSetList.size());

        //dynamically create que str & option(Yes/No) and show
        simpleListViewGrpQue = (ListView) findViewById(R.id.simpleListViewGrpQue);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < queSetList.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
            if(queSetList.get(i).getQueStrHi().isEmpty()){
                hashMap.put("que_str", queSetList.get(i).getQueStrEn());
            }else {
                hashMap.put("que_str", queSetList.get(i).getQueStrEn() + "\nप्रश्न- " + queSetList.get(i).getQueStrHi());
            }
            hashMap.put("yes_rad_btn", "Yes");
            hashMap.put("no_rad_btn", "No");
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        String[] from = {"que_str", "yes_rad_btn", "no_rad_btn"};//string array
        int[] to = {R.id.grpQueTextView, R.id.grpQueYesRadBtn, R.id.grpQueNoRadBtn};//int array of views id's
        CustomAdapterGrpQue simpleAdapter = new CustomAdapterGrpQue(this, arrayList, R.layout.activity_show_broad_headache_group_que, from, to);//Create object and set the parameters for simpleAdapter
        simpleListViewGrpQue.setAdapter(simpleAdapter);//sets the adapter for listView

//        //perform listView item click event
//        simpleListViewMigQue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i(TAG,"ItemClicked row: " + i);//show the selected image in toast according to position
//            }
//
//        });

    }

    public void getNextQue(View view){
//        CustomAdapterGrpQue listAdapter= (CustomAdapterGrpQue)simpleListViewMigQue.getAdapter();
//        Log.i(TAG,"Size of list:"+listAdapter.arrayList.size());
//        for(int i=0; i<listAdapter.arrayList.size(); i++) {
//            HashMap<String, String> hashMap = listAdapter.arrayList.get(i);
//            Log.i(TAG,i+". que_str:"+hashMap.get("que_str"));
//            Log.i(TAG,i+". yes_rad_btn:"+hashMap.get("yes_rad_btn"));
//            Log.i(TAG,i+". no_rad_btn:"+hashMap.get("no_rad_btn"));
//        }

        String message = "";
        boolean isOptionSelectedForAllQue=true;
        int countYesResponse=0;
        // get the value of selected answers from custom adapter
        for (int i = 0; i < CustomAdapterGrpQue.selectedAnswers.size(); i++) {
            String ans= CustomAdapterGrpQue.selectedAnswers.get(i); //selectedAnswers store 'Yes'/'No'/'Not Attempted' ans in QueMigActivity
            message = message + "\n" + (i + 1) + " " +ans;
            Log.i(TAG," message:"+message);
            if(ans.equalsIgnoreCase("Not Attempted")){
                isOptionSelectedForAllQue=false;
            }else if(ans.equalsIgnoreCase("Yes")){
                countYesResponse++;
            }
        }
        Log.i(TAG," countYesResponse:"+countYesResponse);

        if(isOptionSelectedForAllQue==false){
            Toast.makeText(getApplicationContext(), "Kindly select 'Yes' or 'No' option for every question.", Toast.LENGTH_SHORT).show();
            // display the message on screen with the help of Toast.
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }else{
            //show next que of this level to user
            float lvl=level;
            int nextQueGroupId=0;
            int headacheCat=0;
            Log.i(TAG,"Read MultiBranchLogicInfo for>> level:"+level+" queGroupId:"+queGroupId+" countYesResponse:"+countYesResponse);

            savePatientResponse();

            for(int i=0;i< ReadOnlyDataStorage.multiBranchLogicInfoArrayList.size();i++){
                DBMultiBranchLogicInfo objDBMultiBranchLogicInfo=ReadOnlyDataStorage.multiBranchLogicInfoArrayList.get(i);
                if(objDBMultiBranchLogicInfo.getQueGroupId()==queGroupId && objDBMultiBranchLogicInfo.getLvl()==level
                        && objDBMultiBranchLogicInfo.getMinYesResponse()==countYesResponse){
                    if(objDBMultiBranchLogicInfo.getHeadacheCat()==0){
                        nextQueGroupId=objDBMultiBranchLogicInfo.getNextQueGroupId();
                        Log.e(TAG,"continue with current level from CurrentQueGroupID:"+queGroupId+" to NextQueGroupID:"+nextQueGroupId);

                        //update in Test_detail_data
                        DBTestDetailData objDBTestDetailData=objDBFuncAccess.dbReadTestDetailData(testId,pid,level);
                        objDBTestDetailData.setQueAnsId(nextQueGroupId);
                        objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailData);

                        //show next que in GUI
                        showQue(lvl, nextQueGroupId);
                        queGroupId=nextQueGroupId;//reset global var
//                        setPrevButton();
                    }else{
                        headacheCat=objDBMultiBranchLogicInfo.getHeadacheCat();
                        String headacheCatStr=getHeadacheCatStr(headacheCat);

                        //update in Test_detail_data
                        DBTestDetailData objDBTestDetailDataUpdated=objDBFuncAccess.dbReadTestDetailData(testId,pid,level);
                        objDBTestDetailDataUpdated.setQueAnsId(queGroupId);
                        objDBTestDetailDataUpdated.setLvlStatus("Completed");
                        objDBTestDetailDataUpdated.setLvlResultHeadacheCat(headacheCat);
                        objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailDataUpdated);


                        //add data for next lvl testing
//                            String msgStr = "Level-"+level+" Testing completed.\n स्तर-"+level+" परीक्षण पूरा हुआ। \nResult/परिणाम: \n  "+headacheCatStr;
//                            showMsgByDialog(msgStr,"Level Completion Info !!!");
                        String msgStr = "Result/परिणाम:\n "+headacheCatStr;
                        Toast.makeText(this,msgStr , Toast.LENGTH_LONG).show();
                        //showMsgByDialog(msgStr,"Level-"+level+" Testing completed.\n स्तर-"+level+" परीक्षण पूरा हुआ ।");

                        Log.i(TAG,"For TestID:"+testId+", Testing level("+level+") completed. Result: "+headacheCatStr);

                        ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList= objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
                        if(patientTestBasicInfoArrayList.size()>0) {
                            DBPatientTestBasicInfo recentDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(0);
                            recentDBPatientTestBasicInfo.setIsTestCompleted(1); //0:Incomplete, 1=Completed
                            recentDBPatientTestBasicInfo.setTestEndDate(getCurrentDate()); //currentDate
                            boolean updateStatus = objDBFuncAccess.dbUpdatePatientTestBasicInfo(recentDBPatientTestBasicInfo);
                            if (updateStatus == false) {
                                Log.e(TAG, "Error in dbUpdatePatientTestBasicInfo.");
                            }

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

//                        nextQueGroupId=1;
//                        lvl=getNextTestLvl(lvl);

//                            DBTestDetailData objDBTestDetailData=new DBTestDetailData();
//                            objDBTestDetailData.settId(testId);
//                            objDBTestDetailData.setpId(pid);
//                            objDBTestDetailData.setLvl(lvl);
//                            objDBTestDetailData.setLvlStatus("Incomplete");//Lvl Status can be: Incomplete/Completed/NotDone
//                            objDBTestDetailData.setQueAnsId(nextQueGroupId);
//                            boolean res=objDBFuncAccess.dbAddTestDetailData(objDBTestDetailData);
//                            if(res==true){
//                                Log.i(TAG,"dbAddTestDetailData successfull for Lvl: "+lvl+" testId:"+testId);
//                            }else{
//                                Log.i(TAG,"dbAddTestDetailData fail for Lvl: "+lvl+" testId:"+testId);
//                            }
//
//                            //Reset global var
//                            level=lvl;
//                            queGroupId=nextQueGroupId;
//                            textViewLevelGrpQueUI.setText(" Level- " + lvl+" ");
//
//                            //show next que in UI
//                            showQue(lvl, nextQueGroupId);

//                        if (lvl == (float) 4.1) {
//                            Log.i(TAG, "Calling QueMigActivity for NextLvl: " + lvl);
//                            int queGroupId = 1;
//                            DBTestDetailData objDBTestDetailData = new DBTestDetailData();
//                            objDBTestDetailData.settId(testId);
//                            objDBTestDetailData.setpId(pid);
//                            objDBTestDetailData.setLvl(lvl);
//                            objDBTestDetailData.setLvlStatus("Incomplete");//Lvl Status can be: Incomplete/Completed/NotDone
//                            objDBTestDetailData.setQueAnsId(queGroupId);
//                            boolean res = objDBFuncAccess.dbAddTestDetailData(objDBTestDetailData);
//                            if (res == true) {
//                                Log.i(TAG, "dbAddTestDetailData successfull for Lvl: " + lvl + " testId:" + testId);
//                            } else {
//                                Log.e(TAG, "dbAddTestDetailData fail for Lvl: " + lvl + " testId:" + testId);
//                            }
//
//                            Bundle bundle = new Bundle();
//                            bundle.putString("pid", pid);
//                            bundle.putInt("testId", testId);
//                            bundle.putFloat("level", lvl);
//                            bundle.putInt("queGroupId", queGroupId);
//                            Intent intent = new Intent(this, QueMigActivity.class);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                            finish();
//                        }

                    }
                    break;
                }
            }

        }
    }

    private String getCurrentDate(){
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//YYYY-MM-DD HH24:MI:SS
        String todayString = formatter.format(todayDate);
        return  todayString;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.grpQueYesRadBtn:
                if (checked){
                    //Log.i("TAG"," YesRadBtn: onCheckedChanged, isChecked: " + checked);
                }

                break;
            case R.id.grpQueNoRadBtn:
                if (checked){
                    //Log.i("TAG","NoRadBtn: onCheckedChanged, isChecked: " + checked);
                }
                break;
        }
    }

    public float getMaxTestLvl(){
        float MAX_LVL=1;
        for(int i = 0; i< ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++){
            DBBranchingLogicInfo objDBBranchingLogicInfo=ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
            if(MAX_LVL<objDBBranchingLogicInfo.getLvl()){
                MAX_LVL=objDBBranchingLogicInfo.getLvl();
            }
        }
        Log.i(TAG,"MAX_LVL: " +MAX_LVL);
        return MAX_LVL;
    }

    public float getNextTestLvl(float currentLvl){
        float nextTestLvl=currentLvl;
        for(int i=0; i< ReadOnlyDataStorage.branchingLogicInfoArrayList.size(); i++){
            DBBranchingLogicInfo objDBBranchingLogicInfo=ReadOnlyDataStorage.branchingLogicInfoArrayList.get(i);
            if(currentLvl<objDBBranchingLogicInfo.getLvl()){
                nextTestLvl=objDBBranchingLogicInfo.getLvl();
                break;
            }
        }
        Log.i(TAG,"nextTestLvl: " +nextTestLvl);
        return nextTestLvl;
    }

    public String getHeadacheCatStr(int headacheCat){
        String headacheCatStr="";
        for(int l=0;l<ReadOnlyDataStorage.headacheCatInfoArrayList.size();l++) {
            DBHeadacheCatInfo objDBHeadacheCatInfo=ReadOnlyDataStorage.headacheCatInfoArrayList.get(l);
            if(objDBHeadacheCatInfo.getHeadacheCat()==headacheCat){
                //headacheCatStr=objDBHeadacheCatInfo.getCatStrEn()+" / "+objDBHeadacheCatInfo.getCatStrHi();
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

    public void showMsgByDialog(String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QueBroadHeadacheGroupActivity.this);
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



    public void callTestResultUI(View view) {
        ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList= objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
        if(patientTestBasicInfoArrayList.size()>0) {
            DBPatientTestBasicInfo recentDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(0);

            String testStartDate = recentDBPatientTestBasicInfo.getTestStDate();
            String testStatus = "Incomplete";// "Incomplete" / "Completed"
            if(recentDBPatientTestBasicInfo.getIsTestCompleted()==1){
                testStatus = "Completed at "+recentDBPatientTestBasicInfo.getTestEndDate();
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

    public void callHomeUI(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //Home is name of the activity
        builder.setMessage("Do you want to exit from Questionnaire? \n क्या आप प्रश्नावली से बाहर निकलना चाहते हैं?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
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

    public void savePatientResponse(){
        DBPatientResponseData objDBPatientResponseData;
        for (int i = 0; i < CustomAdapterGrpQue.selectedAnswers.size(); i++) {
            objDBPatientResponseData=new DBPatientResponseData();
            objDBPatientResponseData.setLvl(level);
            objDBPatientResponseData.settId(testId);
            objDBPatientResponseData.setpId(pid);
            objDBPatientResponseData.setQueGroupId(queGroupId);
            objDBPatientResponseData.setQueId(queSetList.get(i).getQueId());
            String ans= CustomAdapterGrpQue.selectedAnswers.get(i); //selectedAnswers store 'Yes'/'No'/'Not Attempted' ans
            if(ans.equalsIgnoreCase("No")){
                objDBPatientResponseData.setResponseYesNo(0);//0:No, 1:Yes
            }else if(ans.equalsIgnoreCase("Yes")){
                objDBPatientResponseData.setResponseYesNo(1);//0:No, 1:Yes
            }
            objDBFuncAccess.dbAddPatientResponseData(objDBPatientResponseData);
        }
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
        builder.setMessage("Do you want to exit?\nक्या आप बाहर निकलना चाहते हैं?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(alert!=null){
            alert.dismiss();
            alert=null;
        }
    }

//    private void setPrevButton() {
//        if (queGroupId == 1.0f) {
//            backBtnInQueBroadHdGp.setEnabled(false);
//        } else {
//            backBtnInQueBroadHdGp.setEnabled(true);
//        }
//    }
    public void getPrevQue(View view) {
        Log.i(TAG, "callPrevQue for queGroupId:" + queGroupId);
        if (queGroupId != 1.0f) {
            //Rollback in PatientResponse
            ArrayList<DBPatientResponseData> objPatientResponseDataList;

            objPatientResponseDataList = objDBFuncAccess.dbReadAllPatientResponseData(testId, pid);
            DBPatientResponseData prevDBPatientResponseData = objPatientResponseDataList.get(objPatientResponseDataList.size() - 1);
            int prevQueGroupId = prevDBPatientResponseData.getQueGroupId();
            Log.i(TAG, "Last saved DBPatientResponseData queGroupId:" + prevDBPatientResponseData.getQueGroupId());

            //Rollback in Test_detail_data
            DBTestDetailData objDBTestDetailData = objDBFuncAccess.dbReadTestDetailData(testId, pid, level);
            Log.i(TAG, "dbUpdateTestDetailData: old que id: " + objDBTestDetailData.getQueAnsId()+"  new que id: "+prevQueGroupId);
            objDBTestDetailData.setQueAnsId(prevQueGroupId);
            boolean resUpdate = objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailData);


            Log.i(TAG, "continue with current level with Prev queGroupId:" + prevQueGroupId);
            showQue(level, prevQueGroupId);

            ArrayList<DBPatientResponseData> PatientResponseDataList = objDBFuncAccess.dbReadAllPatientResponseData(testId, pid);
            for(int i=0; i<PatientResponseDataList.size(); i++){
                DBPatientResponseData objDBPatientResponseData=PatientResponseDataList.get(i);
                float queId = objDBPatientResponseData.getQueId();
                if(objDBPatientResponseData.getLvl()==level && objDBPatientResponseData.getQueGroupId()==prevQueGroupId){
                    boolean resDel = objDBFuncAccess.dbDeletePatientResponseData(testId, pid, level,queId );
                    if (resDel == true) {
                        Log.i(TAG, "dbDeletePatientResponseData successful for queGroupId:" + queId);
                    } else {
                        Log.e(TAG, "dbDeletePatientResponseData fail for queGroupId:" + queId);
                    }
                }
            }


            Log.e(TAG, "Rolled back to currentQueGroupId:"+queGroupId+" to prevQueGroupId:"+prevQueGroupId);
            queGroupId = prevQueGroupId;
        } else {
            Log.e(TAG, "Unable to roll back to prev question from first que in current lvl.");
            String toast = "You cannot go back to prev level.";
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }

    }
}