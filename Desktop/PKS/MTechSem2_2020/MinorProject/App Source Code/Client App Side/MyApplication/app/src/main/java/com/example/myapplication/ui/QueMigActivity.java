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
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

public class QueMigActivity extends AppCompatActivity {
    public static final String TAG = "QueMigActivity";
    String pid;
    int testId=0;
    float level =  3.1f;
    int queGroupId=1;

    float MAX_LVL=0;
    TextView textViewPidInMigQueUI;
    TextView textViewLevelMigQueUI;
    LinearLayout migQueLinLayout;
    DBFuncAccess objDBFuncAccess;
    //initialize view's
    ListView simpleListViewMigQue;
    ArrayList<DBBranchingLogicInfo> queSetList;
    AlertDialog alert;
    CmnMethods objCmnMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_mig);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        textViewPidInMigQueUI = findViewById(R.id.textViewPidInMigQueUI);
        textViewLevelMigQueUI = findViewById(R.id.textViewLevelMigQueUI);
        migQueLinLayout = findViewById(R.id.migQueTopLinLayout);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pid = bundle.getString("pid", "");
        testId=bundle.getInt("testId");
        level = bundle.getFloat("level");
        queGroupId=bundle.getInt("queGroupId");
        textViewPidInMigQueUI.setText(pid);
        textViewLevelMigQueUI.setText(" Level- " + level);


        objDBFuncAccess=DBFuncAccess.getInstance(this);
        MAX_LVL=getMaxTestLvl();
        showQue(level,queGroupId);

        objCmnMethods= new CmnMethods(this);
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
        simpleListViewMigQue = (ListView) findViewById(R.id.simpleListViewMigQue);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < queSetList.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("que_str", queSetList.get(i).getQueStrEn() + "\nप्रश्न- " + queSetList.get(i).getQueStrHi());
            hashMap.put("yes_rad_btn", "Yes");
            hashMap.put("no_rad_btn", "No");
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        String[] from = {"que_str", "yes_rad_btn", "no_rad_btn"};//string array
        int[] to = {R.id.migQueTextView, R.id.migQueYesRadBtn, R.id.migQueNoRadBtn};//int array of views id's
        CustomAdapter simpleAdapter = new CustomAdapter(this, arrayList, R.layout.activity_show_mig_que, from, to);//Create object and set the parameters for simpleAdapter
        simpleListViewMigQue.setAdapter(simpleAdapter);//sets the adapter for listView


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
//        CustomAdapter listAdapter= (CustomAdapter)simpleListViewMigQue.getAdapter();
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
        for (int i = 0; i < CustomAdapter.selectedAnswers.size(); i++) {
            String ans= CustomAdapter.selectedAnswers.get(i); //selectedAnswers store 'Yes'/'No'/'Not Attempted' ans in QueMigActivity
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
                        Log.i(TAG,"continue with current level with Next QueGroupID:"+nextQueGroupId);

                        //update in Test_detail_data
                        DBTestDetailData objDBTestDetailData=objDBFuncAccess.dbReadTestDetailData(testId,pid,level);
                        objDBTestDetailData.setQueAnsId(nextQueGroupId);
                        objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailData);

                        //show next que in GUI
                        showQue(lvl, nextQueGroupId);
                        queGroupId=nextQueGroupId;//reset global var
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

                            nextQueGroupId=1;
                            lvl=getNextTestLvl(lvl);
                        if(lvl==MAX_LVL || headacheCat==5 || headacheCat==8 ) {                    //level=3.3 or Non-migrainous Headache(5) or Migraine without aura(8)
                            Log.i(TAG, "For TestID:" + testId + ", Testing reached to the max level(" + MAX_LVL + ") or Non-migrainous Headache(5) or Migraine without aura(8).");
                            ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList= objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
                            if(patientTestBasicInfoArrayList.size()>0) {
                                DBPatientTestBasicInfo recentDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(0);
                                recentDBPatientTestBasicInfo.setIsTestCompleted(1); //0:Incomplete, 1=Completed
                                recentDBPatientTestBasicInfo.setTestEndDate(getCurrentDate()); //currentDate
                                boolean updateStatus= objDBFuncAccess.dbUpdatePatientTestBasicInfo(recentDBPatientTestBasicInfo);
                                if(updateStatus==false){
                                    Log.e(TAG,"Error in dbUpdatePatientTestBasicInfo.");
                                }
                                if(lvl==MAX_LVL && headacheCat!=5 && headacheCat!=8) {//evaluate lvl3.3 also
                                    int migraineSubType = evaluateMigraineSubType();
                                    DBTestDetailData objDBTestDetailData = new DBTestDetailData();
                                    objDBTestDetailData.settId(testId);
                                    objDBTestDetailData.setpId(pid);
                                    objDBTestDetailData.setLvl(lvl);
                                    objDBTestDetailData.setLvlStatus("Completed");//Lvl Status can be: Incomplete/Completed/NotDone
                                    objDBTestDetailData.setLvlResultHeadacheCat(migraineSubType);
                                    objDBTestDetailData.setQueAnsId(1);
                                    boolean res = objDBFuncAccess.dbAddTestDetailData(objDBTestDetailData);
                                    if (res == true) {
                                        Log.i(TAG, "dbAddTestDetailData successfull for Lvl: " + lvl + " testId:" + testId);
                                    } else {
                                        Log.i(TAG, "dbAddTestDetailData fail for Lvl: " + lvl + " testId:" + testId);
                                    }
                                }
                                //show msg and Result GUI
                                String toast = "Testing completed.\nपरीक्षण पूरा हुआ ।";
                                Toast.makeText(this,toast , Toast.LENGTH_LONG).show();
                                Log.i(TAG,"Testing completed for TestID:"+testId+" \nResult: "+headacheCatStr);
                                String testStartDate =recentDBPatientTestBasicInfo.getTestStDate();
                                String testStatus = "Completed at "+recentDBPatientTestBasicInfo.getTestEndDate();// "Incomplete" / "Completed"
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
                        }else{
                            DBTestDetailData objDBTestDetailData=new DBTestDetailData();
                            objDBTestDetailData.settId(testId);
                            objDBTestDetailData.setpId(pid);
                            objDBTestDetailData.setLvl(lvl);
                            objDBTestDetailData.setLvlStatus("Incomplete");//Lvl Status can be: Incomplete/Completed/NotDone
                            objDBTestDetailData.setQueAnsId(nextQueGroupId);
                            boolean res=objDBFuncAccess.dbAddTestDetailData(objDBTestDetailData);
                            if(res==true){
                                Log.i(TAG,"dbAddTestDetailData successfull for Lvl: "+lvl+" testId:"+testId);
                            }else{
                                Log.i(TAG,"dbAddTestDetailData fail for Lvl: "+lvl+" testId:"+testId);
                            }

                            //Reset global var
                            level=lvl;
                            queGroupId=nextQueGroupId;
                            textViewLevelMigQueUI.setText(" Level- " + lvl+" ");

                            //show next que in UI
                            showQue(lvl, nextQueGroupId);
                        }
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
            case R.id.migQueYesRadBtn:
                if (checked){
                    //Log.i("TAG"," YesRadBtn: onCheckedChanged, isChecked: " + checked);
                }

                break;
            case R.id.migQueNoRadBtn:
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
        return nextTestLvl;
    }

    public String getHeadacheCatStr(int headacheCat){
        String headacheCatStr="";
        for(int l=0;l<ReadOnlyDataStorage.headacheCatInfoArrayList.size();l++) {
            DBHeadacheCatInfo objDBHeadacheCatInfo=ReadOnlyDataStorage.headacheCatInfoArrayList.get(l);
            if(objDBHeadacheCatInfo.getHeadacheCat()==headacheCat){
                headacheCatStr=objDBHeadacheCatInfo.getCatStrEn()+" / "+objDBHeadacheCatInfo.getCatStrHi();
                break;
            }
        }
        return headacheCatStr;
    }

    public void showMsgByDialog(String msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QueMigActivity.this);
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
        for (int i = 0; i < CustomAdapter.selectedAnswers.size(); i++) {
            objDBPatientResponseData=new DBPatientResponseData();
            objDBPatientResponseData.setLvl(level);
            objDBPatientResponseData.settId(testId);
            objDBPatientResponseData.setpId(pid);
            objDBPatientResponseData.setQueGroupId(queGroupId);
            objDBPatientResponseData.setQueId(queSetList.get(i).getQueId());
            String ans= CustomAdapter.selectedAnswers.get(i); //selectedAnswers store 'Yes'/'No'/'Not Attempted' ans in QueMigActivity
            if(ans.equalsIgnoreCase("No")){
                objDBPatientResponseData.setResponseYesNo(0);//0:No, 1:Yes
            }else if(ans.equalsIgnoreCase("Yes")){
                objDBPatientResponseData.setResponseYesNo(1);//0:No, 1:Yes
            }
            objDBFuncAccess.dbAddPatientResponseData(objDBPatientResponseData);
        }
    }

    int evaluateMigraineSubType(){
        int migraineSubType=-1;
        ArrayList<DBPatientResponseData> objDBPatientResponseDataArrayList=objDBFuncAccess.dbReadAllPatientResponseData(testId,pid);
        Log.i(TAG,"Size of objDBPatientResponseDataArrayList:"+objDBPatientResponseDataArrayList.size());
        //prepare flags to analyze response
        boolean flag21=false,flag23=false,flag26=false;
        boolean flag31=false,flag32=false,flag33=false,flag341=false,flag342=false,flag351=false,flag352=false,flag353=false,flag354=false,flag355=false,flag356=false,flag357=false,flag36=false;
        for(int i=0; i<objDBPatientResponseDataArrayList.size();i++){
            DBPatientResponseData objDBDbPatientResponseData=objDBPatientResponseDataArrayList.get(i);
            if(objDBDbPatientResponseData.getQueId()==2.1f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag21=true;
                Log.i(TAG,"flag21:"+flag21);
            }
            if(objDBDbPatientResponseData.getQueId()==2.3f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag23=true;
                Log.i(TAG,"flag23:"+flag23);
            }
            if(objDBDbPatientResponseData.getQueId()==2.6f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag26=true;
                Log.i(TAG,"flag26:"+flag26);
            }
            if(objDBDbPatientResponseData.getQueId()==3.1f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag31=true;
                Log.i(TAG,"flag31:"+flag31);
            }
            if(objDBDbPatientResponseData.getQueId()==3.2f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag32=true;
                Log.i(TAG,"flag32:"+flag32);
            }
            if(objDBDbPatientResponseData.getQueId()==3.3f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag33=true;
                Log.i(TAG,"flag33:"+flag33);
            }
            if(objDBDbPatientResponseData.getQueId()==3.41f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag341=true;
                Log.i(TAG,"flag341:"+flag341);
            }
            if(objDBDbPatientResponseData.getQueId()==3.42f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag342=true;
                Log.i(TAG,"flag342:"+flag342);
            }
            if(objDBDbPatientResponseData.getQueId()==3.51f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag351=true;
                Log.i(TAG,"flag351:"+flag351);
            }
            if(objDBDbPatientResponseData.getQueId()==3.52f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag352=true;
                Log.i(TAG,"flag352:"+flag352);
            }
            if(objDBDbPatientResponseData.getQueId()==3.53f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag353=true;
                Log.i(TAG,"flag353:"+flag353);
            }
            if(objDBDbPatientResponseData.getQueId()==3.54f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag354=true;
                Log.i(TAG,"flag354:"+flag354);
            }
            if(objDBDbPatientResponseData.getQueId()==3.55f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag355=true;
                Log.i(TAG,"flag355:"+flag355);
            }
            if(objDBDbPatientResponseData.getQueId()==3.56f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag356=true;
                Log.i(TAG,"flag356:"+flag356);
            }
            if(objDBDbPatientResponseData.getQueId()==3.57f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag357=true;
                Log.i(TAG,"flag357:"+flag357);
            }
            if(objDBDbPatientResponseData.getQueId()==3.6f && objDBDbPatientResponseData.getResponseYesNo()==1){
                flag36=true;
                Log.i(TAG,"flag36:"+flag36);
            }
        }

        int trueAnsInQ35=countTrueIn35(flag351,flag352,flag353,flag354,flag355,flag356,flag357);
        if(flag31==true && flag32==true && flag33==true &&
                (flag341==false && flag342==false) &&
                (flag351==false && flag352==false && flag353==false && flag354==false && flag355==false && flag356==false && flag357==false)&&
                flag36==false){
            migraineSubType= 11;//11: 'Migraine with typical aura'
        }
        if(flag341==false && flag342==false &&
                (trueAnsInQ35 >= 2)&&
                flag36==false){
            migraineSubType= 12;//12, 'Migraine with brainstem aura'
        }
        if(((flag21==true && flag23==true) || (flag23==true && flag26==true) ||(flag26==true && flag21==true ) )&&
                flag36==true){
            migraineSubType= 13;//13, 'Retinal Migraine'
        }
        if((flag31==true || flag32==true ||flag33==true)&&
                flag342==true){
            migraineSubType=14;//14, 'Hemiplegic Migraine'
        }

        return migraineSubType;
    }

    int countTrueIn35(boolean flag351,boolean flag352,boolean flag353, boolean flag354,boolean flag355,boolean flag356,boolean flag357){
        int trueInQ35=0;
        if(flag351==true){
            trueInQ35++;
        }
        if(flag352==true){
            trueInQ35++;
        }
        if(flag353==true){
            trueInQ35++;
        }
        if(flag354==true){
            trueInQ35++;
        }
        if(flag355==true){
            trueInQ35++;
        }
        if(flag356==true){
            trueInQ35++;
        }
        if(flag357==true){
            trueInQ35++;
        }

        return trueInQ35;
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

}
