package com.example.myapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.server.DBWebServ;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PatientDetailActivity extends AppCompatActivity {
    String pid;
    TextView textViewPid;
    TextView textViewName;
    TextView textViewDob;
    TextView textViewGender;
    TextView textViewAddress;
    TextView textViewMob;
    TextView textViewEmail;
    ImageView imageViewReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        textViewPid = findViewById(R.id.textViewPid);
        textViewName= findViewById(R.id.textViewName);
         textViewDob= findViewById(R.id.textViewDob);
         textViewGender= findViewById(R.id.textViewGender);
         textViewAddress= findViewById(R.id.textViewAddress);
         textViewMob= findViewById(R.id.textViewMob);
         textViewEmail= findViewById(R.id.textViewEmail);
         imageViewReg= findViewById(R.id.imageViewReg);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pid = bundle.getString("pid", "");
        textViewPid.setText(pid);
        if(!DBWebServ.objDBPatientDetailInfo.getPid().isEmpty()){
            Log.i("TAG","dbReadPatientDetailInfo successful.");
            textViewName.setText(DBWebServ.objDBPatientDetailInfo.getName());
            textViewDob.setText(DBWebServ.objDBPatientDetailInfo.getDob());
            textViewGender.setText(DBWebServ.objDBPatientDetailInfo.getGender());
            textViewAddress.setText(DBWebServ.objDBPatientDetailInfo.getAddress());
            textViewMob.setText(String.valueOf(DBWebServ.objDBPatientDetailInfo.getMobileNo()));
            textViewEmail.setText(DBWebServ.objDBPatientDetailInfo.getEmail());
            //InputStream targetStream = new ByteArrayInputStream(DBWebServ.objDBPatientDetailInfo.getImage().getBytes());
            Log.i("TAG","DBWebServ.objDBPatientDetailInfo.getImage():"+DBWebServ.objDBPatientDetailInfo.getImage());
            //imageViewReg.setImageBitmap(BitmapFactory.decodeStream(targetStream));
            byte[] decodedString = Base64.decode(DBWebServ.objDBPatientDetailInfo.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageViewReg.setImageBitmap(decodedByte);
        }else{
            textViewName.setText("");
            textViewDob.setText("");
            textViewGender.setText("");
            textViewAddress.setText("");
            textViewMob.setText("");
            textViewEmail.setText("");
            Log.e("TAG","error in dbReadPatientDetailInfo.");
        }
    }

    public void callBack(View view){
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void callLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()); //Home is name of the activity
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
                Intent i = new Intent();
                i.putExtra("finish", true);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                //startActivity(i);
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
}
