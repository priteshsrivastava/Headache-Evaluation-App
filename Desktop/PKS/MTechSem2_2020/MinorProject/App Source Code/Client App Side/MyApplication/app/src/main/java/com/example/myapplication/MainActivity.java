package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.example.myapplication.admin.AdminHomeActivity;
import com.example.myapplication.db.DBBranchingLogicInfo;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.db.ReadOnlyDataStorage;
import com.example.myapplication.server.DBWebServ;
import com.example.myapplication.ui.AboutActivity;
import com.example.myapplication.ui.PatientHomeActivity;
import com.example.myapplication.ui.QueActivity;
import com.example.myapplication.ui.QueMigActivity;
import com.example.myapplication.ui.RegisterPatientActivity;
import com.example.myapplication.ui.ResultActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="MainActivity";
    EditText editTextPid;
    ViewFlipper v_flipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPid = (EditText) findViewById(R.id.editTextPid);
        v_flipper=findViewById(R.id.v_flipper);
        //int images[]={R.drawable.patient,R.drawable.iit_gallery1,R.drawable.iit_gallery2,R.drawable.time_saving};
        int images[]={R.drawable.patient1,R.drawable.iit_gallery1,R.drawable.iit_gallery2,R.drawable.decision_tree_save_time};

        Log.i(TAG,"\n\nCurrent LOCALE:"+getCurrentLocale(this));
        Log.i(TAG,"\n\n-----Test/Call DB routines-----");
        DBFuncAccess objDBFuncAccess=DBFuncAccess.getInstance(this);
        initReadOnlyStorage(objDBFuncAccess);

//        ArrayList<DBTestDetailData> testDetailDataArrayList=objDBFuncAccess.dbReadAllTestDetailData();
//        Log.i(TAG,"Size of testDetailDataArrayList:"+testDetailDataArrayList.size());
//        for(int i=0;i<testDetailDataArrayList.size();i++){
//            DBTestDetailData objDBTestDetailData=testDetailDataArrayList.get(i);
//            //if(objDBTestDetailData.getLvl()==3.2f){
//            Log.i(TAG,"t_id="+objDBTestDetailData.gettId()+" lvl:"+objDBTestDetailData.getLvl()+" lvlStatus:"+objDBTestDetailData.getLvlStatus());
//            //}
//        }
//        float lvl1= (float) 3.1;
//        float lvl2= (float) 3.2;
//        DBTestDetailData objDBTestDetailData1=objDBFuncAccess.dbReadTestDetailData(1, 2f);
//        DBTestDetailData objDBTestDetailData2=objDBFuncAccess.dbReadTestDetailData(1, lvl1);
//        DBTestDetailData objDBTestDetailData=objDBFuncAccess.dbReadTestDetailData(1, lvl2);
//        objDBFuncAccess.dbUpdateTestDetailData(objDBTestDetailData);
//        Log.i(TAG,"Read t_id="+objDBTestDetailData.gettId()+" lvl:"+objDBTestDetailData.getLvl());

//        ArrayList<DBBranchingLogicInfo> branchingLogicInfoArrayList=objDBFuncAccess.dbReadAllBranchingLogicInfo();
//        Log.i(TAG,"Size of branchingLogicInfoArrayList:"+branchingLogicInfoArrayList.size());
//
//        DBPatientTestBasicInfo  objDBPatientTestBasicInfo=new DBPatientTestBasicInfo();
//        boolean addFlagPatientTestBasicInfo=objDBFuncAccess.dbAddPatientTestBasicInfo(objDBPatientTestBasicInfo);
//        if (addFlagPatientTestBasicInfo){
//            Log.i(TAG,"PatientTestBasicInfo data added successfully.");
//        }else{
//            Log.e(TAG,"Error in Add PatientTestBasicInfo.");
//        }
//        boolean updateFlagPatientTestBasicInfo=objDBFuncAccess.dbUpdatePatientTestBasicInfo(objDBPatientTestBasicInfo);
//        if (updateFlagPatientTestBasicInfo){
//            Log.i(TAG,"PatientTestBasicInfo data updated successfully.");
//        }else{
//            Log.e(TAG,"Error in Update PatientTestBasicInfo.");
//        }
//        boolean deleteFlagPatientTestBasicInfo=objDBFuncAccess.dbDeletePatientTestBasicInfo(objDBPatientTestBasicInfo.gettId());
//        if (deleteFlagPatientTestBasicInfo){
//            Log.i(TAG,"PatientTestBasicInfo data deleted successfully.");
//        }else{
//            Log.e(TAG,"Error in Delete PatientTestBasicInfo.");
//        }
        Log.i(TAG,"--------------------------\n\n");

        for(int image:images){
            flipperImage(image);
        }
    }



    public void login(View view) {
        String pid = editTextPid.getText().toString().trim();
        if(checkLogin(pid)==true) {
            if(pid.equalsIgnoreCase("admin")){
                Intent intent = new Intent(this, AdminHomeActivity.class);
                startActivity(intent);
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("pid", pid);
                Intent intent = new Intent(this, PatientHomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                DBWebServ objDBWebServ=new DBWebServ(this);
                objDBWebServ.dbReadPatientDetailInfo(pid);
            }
            editTextPid.setText("");//reset pid after login
            String toast = "Login Successful. \n सफलतापूर्ण प्रवेश।";
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
        //finish();
    }

    public void callAboutApp(View view){
        Intent intentAbout = new Intent(this, AboutActivity.class);
        startActivity(intentAbout);
    }


    boolean checkLogin(String pid){
        boolean status=true;
        if(pid.isEmpty()){
            editTextPid.requestFocus();
            String toast = "PID cannot be blank.\n रोगी आईडी रिक्त नहीं हो सकती।";
            Toast.makeText(this,toast , Toast.LENGTH_SHORT).show();
            status=false;
        }
        return status;
    }
    public void initReadOnlyStorage(DBFuncAccess objDBFuncAccess){
        ReadOnlyDataStorage.branchingLogicInfoArrayList =objDBFuncAccess.dbReadAllBranchingLogicInfo();
        ReadOnlyDataStorage.answerInfoArrayList =objDBFuncAccess.dbReadAllAnswerInfo();
        ReadOnlyDataStorage.headacheCatInfoArrayList =objDBFuncAccess.dbReadAllHeadacheCatInfo();
        ReadOnlyDataStorage.multiBranchLogicInfoArrayList=objDBFuncAccess.dbReadAllMultiBranchLogicInfo();
        Log.i(TAG,"Size of branchingLogicInfoArrayList:"+ReadOnlyDataStorage.branchingLogicInfoArrayList.size());
        Log.i(TAG,"Size of answerInfoArrayList:"+ReadOnlyDataStorage.answerInfoArrayList.size());
        Log.i(TAG,"Size of headacheCatInfoArrayList:"+ReadOnlyDataStorage.headacheCatInfoArrayList.size());
        Log.i(TAG,"Size of multiBranchLogicInfoArrayList:"+ReadOnlyDataStorage.multiBranchLogicInfoArrayList.size());
    }

    void flipperImage(int image){
        final ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3500);//3.5 sec
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean  onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
//           case R.id.settingMenu:
//                //add the function to perform here
//                Intent intentSettings = new Intent(this, SettingsActivity.class);
//                startActivity(intentSettings);
//                return(true);
            case R.id.registerMenu:
                //add the function to perform here
                String pid = editTextPid.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("pid", pid);
                Intent intentRegister = new Intent(this, RegisterPatientActivity.class);
                intentRegister.putExtras(bundle);
                startActivity(intentRegister);
                return(true);
            case R.id.aboutMenu:
                //add the function to perform here
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return(true);
            case R.id.exitMenu:
                //add the function to perform here
                //finishAffinity();
                //finish();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }else{
                    finish();
                }
                return(true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }
}
