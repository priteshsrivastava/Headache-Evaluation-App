package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.*;
import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {
    String pid;
    ArrayList<String> testStartDateList;
    ArrayList<Integer> testStatusList;
    TextView textViewPid;
    DBFuncAccess objDBFuncAccess;
    ArrayList<DBPatientTestBasicInfo> patientTestBasicInfoArrayList;
    //initialize view's
    ListView simpleListView;
    //String[] fruitsNames = {"Apple", "Banana", "Litchi", "Mango", "PineApple"};//fruit names array
   // int[] fruitsImagesGreen = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};//, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};//fruits images array

    CmnMethods objCmnMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        testStartDateList = new ArrayList<>();
        testStatusList = new ArrayList<>();
        textViewPid = findViewById(R.id.textView13);
        objDBFuncAccess=DBFuncAccess.getInstance(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pid = bundle.getString("pid", "");
        testStartDateList = bundle.getStringArrayList("test_start_date_list");
        testStatusList = bundle.getIntegerArrayList("test_status_list");
        textViewPid.setText(pid);
        patientTestBasicInfoArrayList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);

        simpleListView = (ListView) findViewById(R.id.simpleListView);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < testStartDateList.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
            if(testStatusList.get(i)==1){
                hashMap.put("name", testStartDateList.get(i)+"\n\t"+"Completed");
                hashMap.put("image", R.drawable.ic_launcher_redground + "");
            }else{
                hashMap.put("name", testStartDateList.get(i)+"\n\t"+"Incomplete");
                hashMap.put("image", R.drawable.ic_launcher_background + "");
            }

            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        String[] from = {"name", "image"};//string array
        int[] to = {R.id.textView, R.id.imageView};//int array of views id's
        CustomAdapterHistory simpleAdapter = new CustomAdapterHistory(this, arrayList, R.layout.activity_view_history, from, to);//Create object and set the parameters for simpleAdapter
        simpleListView.setAdapter(simpleAdapter);//sets the adapter for listView

        //perform listView item click event
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(getApplicationContext(), "Selected Date: " + testStartDateList.get(i), Toast.LENGTH_SHORT).show();//show the selected image in toast according to position

                String testStatus = "Incomplete";// "Incomplete" / "Completed"
                int testId=0;
                String testStartDate ="";
                for (int j=0; j<patientTestBasicInfoArrayList.size(); j++) {
                    DBPatientTestBasicInfo objDBPatientTestBasicInfo = patientTestBasicInfoArrayList.get(j);
                    testId=objDBPatientTestBasicInfo.gettId();
                    testStartDate = objDBPatientTestBasicInfo.getTestStDate();
                    if(testStartDateList.get(i).equalsIgnoreCase(testStartDate)) {
                        if (objDBPatientTestBasicInfo.getIsTestCompleted() == 1) {
                            testStatus = "Completed at "+objDBPatientTestBasicInfo.getTestEndDate();
                        }else  if (objDBPatientTestBasicInfo.getIsTestCompleted() == 0) {
                            testStatus = "Incomplete";
                        }
                        break;
                    }
                }//end for j

                final String testStatusFin = testStatus;
                final int testIdFin=testId;
                final String testStartDateFin =testStartDate;
                final int itemIndex=i;
                String[] actionArray={"View/विवरण देखें ","Delete/हटाएं"};
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("Pick an Action / एक क्रिया चुनें")
                        .setItems(actionArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                if(which==0){//View
                                    Toast.makeText(getApplicationContext(), " Viewing Test Result/Summary", Toast.LENGTH_SHORT).show();//show the selected image in toast according to position

                                    Bundle bundle = new Bundle();
                                    bundle.putString("pId", pid);
                                    bundle.putInt("tId", testIdFin);
                                    bundle.putString("test_start_date", testStartDateFin);
                                    bundle.putString("test_status", testStatusFin);
                                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }else if(which==1){//Delete
                                    testStartDateList.remove(itemIndex);
                                    testStatusList.remove(itemIndex);
                                    objDBFuncAccess.dbDeletePatientTestBasicInfo(testIdFin,pid);
                                    ArrayList<DBTestDetailData> testDetailList=objDBFuncAccess.dbReadAllTestDetailData(testIdFin,pid);
                                    for(int i=0;i<testDetailList.size();i++){
                                        objDBFuncAccess.dbDeleteTestDetailData(testIdFin,pid,testDetailList.get(i).getLvl());
                                        objDBFuncAccess.dbDeletePatientResponseData(testIdFin,pid,testDetailList.get(i).getLvl());
                                    }

                                    /////////////////Refresh UI////////////
                                    Intent intent = getIntent();
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    finish();

                                    Bundle bundle = new Bundle();
                                    bundle.putString("pid", pid);
                                    bundle.putStringArrayList("test_start_date_list", testStartDateList);
                                    bundle.putIntegerArrayList("test_status_list", testStatusList);
                                    intent.putExtras(bundle);

                                    startActivity(intent);
                                    /////////////////////////////////////////

                                    Toast.makeText(getApplicationContext(), "Deleted Test Data", Toast.LENGTH_SHORT).show();//show the selected image in toast according to position
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
         objCmnMethods = new CmnMethods(this);
    }
    public void callHomeUI(View view){
        finish();
        Bundle bundle=new Bundle();
        bundle.putString("pid",pid);
        Intent intent = new Intent(getApplicationContext(), PatientHomeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goBack(View view) {
        finish();
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
}
