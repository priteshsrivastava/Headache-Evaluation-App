package com.example.myapplication.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.CmnMethods;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.server.DBWebServ;
import com.example.myapplication.ui.PatientHomeActivity;
import com.example.myapplication.ui.RegisterPatientActivity;
import com.google.android.material.tabs.TabLayout;

public class AdminHomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Patient Report"));
        tabLayout.addTab(tabLayout.newTab().setText("Manage User"));
        tabLayout.addTab(tabLayout.newTab().setText("Other Report"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        int[] tabIcons = {
                R.drawable.questionnaire,
                R.drawable.person,
                R.drawable.result
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DBFuncAccess objDBFuncAccess=DBFuncAccess.getInstance(this);
        CmnMethods objCmnMethods=new CmnMethods(this);
        objCmnMethods.callReadAllAPI(objDBFuncAccess);

        Log.i("AdminHome","Size of objPatientTestBasicInfoList:"+DBWebServ.objPatientTestBasicInfoList.size());
        Log.i("AdminHome","Size of objTestDetailDataList:"+DBWebServ.objTestDetailDataList.size());
        Log.i("AdminHome","Size of objPatientResponseDataList:"+DBWebServ.objPatientResponseDataList.size());

    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean  onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adm, menu);
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

            case R.id.adm_logout:
                //add the function to perform here
                callLogout();
                return(true);
            case R.id.adm_sync:
                //add the function to perform here
                Log.i("TAG","Calling sync from server");
                //objCmnMethods.callSync(objDBFuncAccess,pid);
                Log.i("AdminHome","Size of objPatientTestBasicInfoList:"+DBWebServ.objPatientTestBasicInfoList.size());
                Log.i("AdminHome","Size of objTestDetailDataList:"+DBWebServ.objTestDetailDataList.size());
                Log.i("AdminHome","Size of objPatientResponseDataList:"+DBWebServ.objPatientResponseDataList.size());
                callSync();
                return(true);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void callSync(View view){
        callSync();
    }
    public void callSync(){
        DBFuncAccess objDBFuncAccess=DBFuncAccess.getInstance(this);
        CmnMethods objCmnMethods=new CmnMethods(this);
        objCmnMethods.callReadAllAPI(objDBFuncAccess);
    }
    public void logout(View view){
        callLogout();
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


    public void callAdminTestReport(View view){
        Intent intent = new Intent(this, AdminTestReportActivity.class);
        startActivity(intent);
    }
    public void callRegisterPatient(View view) {
        Intent intentRegister = new Intent(this, RegisterPatientActivity.class);
        startActivity(intentRegister);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        startActivity(new Intent(this,MainActivity.class));
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