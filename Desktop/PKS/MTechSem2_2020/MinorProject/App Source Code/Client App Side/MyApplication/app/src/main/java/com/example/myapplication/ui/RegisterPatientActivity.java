package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.db.DBPatientDetailInfo;
import com.example.myapplication.server.DBWebServ;
import com.example.myapplication.server.DBWebServAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class RegisterPatientActivity extends AppCompatActivity {
    EditText editTextRegPid;
    EditText editTextRegName;
    EditText editTextRegPwd;
    EditText editTextRegDob;
    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;
    EditText editTextRegAddress;
    EditText editTextRegMob;
    EditText editTextRegAdhaar;
    EditText editTextRegEmail;
    EditText editTextRegHistory;
    private ImageView imageViewReg;

    private Bitmap bitmap;
    private int REQUEST_CAMERA = 0;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

         editTextRegPid=(EditText)findViewById(R.id.editTextRegPid);
         editTextRegName=(EditText)findViewById(R.id.editTextRegName);
         editTextRegPwd=(EditText)findViewById(R.id.editTextTextPassword);
         editTextRegDob=(EditText)findViewById(R.id.editTextRegDob);
         radioButtonMale=(RadioButton)findViewById(R.id.radioButtonMale);
         radioButtonFemale=(RadioButton)findViewById(R.id.radioButtonFemale);
         editTextRegAddress=(EditText)findViewById(R.id.editTextRegAddress);
         editTextRegMob=(EditText)findViewById(R.id.editTextRegMob);
        editTextRegAdhaar =(EditText)findViewById(R.id.editTextRegAdhaar) ;
         editTextRegEmail=(EditText)findViewById(R.id.editTextRegEmail);
         editTextRegHistory=(EditText)findViewById(R.id.editTextRegHistory);
        imageViewReg=(ImageView)findViewById(R.id.imageViewReg);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
       // showDate(year, month+1, day); //to show current date
    }

    public void callCancle(View view) {
        finish();
    }

    public void callReset(View view) {
        recreate();
    }

    public void callRegister(View view) {
        DBPatientDetailInfo objDBPatientDetailInfo=new DBPatientDetailInfo();
        objDBPatientDetailInfo.setPid(editTextRegPid.getText().toString().trim());
        objDBPatientDetailInfo.setName( editTextRegName.getText().toString().trim());
        objDBPatientDetailInfo.setPwd( editTextRegPwd.getText().toString().trim());
        objDBPatientDetailInfo.setDob( editTextRegDob.getText().toString().trim());
         if(radioButtonMale.isChecked()){
             objDBPatientDetailInfo.setGender("Male");
         }else if(radioButtonFemale.isChecked()){
             objDBPatientDetailInfo.setGender("Female");
         }

        objDBPatientDetailInfo.setAddress(editTextRegAddress.getText().toString().trim());
         if(!editTextRegMob.getText().toString().isEmpty()){
             objDBPatientDetailInfo.setMobileNo( Long.parseLong(editTextRegMob.getText().toString().trim()));
         }
        if(!editTextRegAdhaar.getText().toString().isEmpty()){
            objDBPatientDetailInfo.setAdhaarNo( Long.parseLong(editTextRegAdhaar.getText().toString().trim()));
        }
        objDBPatientDetailInfo.setEmail( editTextRegEmail.getText().toString().trim());
        objDBPatientDetailInfo.setHealthHistory( editTextRegHistory.getText().toString().trim());

//        imageViewReg.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        imageViewReg.layout(0, 0, imageViewReg.getMeasuredWidth(), imageViewReg.getMeasuredHeight());
//        imageViewReg.setDrawingCacheEnabled(true);
//        imageViewReg.buildDrawingCache();
//        bitmap = imageViewReg.getDrawingCache();
        String uploadImage = getStringImage(bitmap);

        objDBPatientDetailInfo.setImage(uploadImage);
        Log.i("TAG","Value set for image:"+objDBPatientDetailInfo.getImage());

        DBWebServ objDBWebServ=new DBWebServ(this);
        boolean addRes=objDBWebServ.dbAddPatientDetailInfo(objDBPatientDetailInfo);
//        if(!DBWebServ.objDBPatientDetailInfo.getPid().isEmpty()){
//            Log.i("TAG","dbAddPatientDetailInfo successful.");
//        }else{
//            Log.e("TAG","error in dbAddPatientDetailInfo.");
//        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void showFileChooser() {
        //from gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        //from Camera
//        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,REQUEST_CAMERA);
    }

    private void showCameraChooser() {
        //from Camera
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG","inside onActivityResult"+ " resultCode:"+resultCode+" data:"+data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                filePath = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewReg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(requestCode == REQUEST_CAMERA && data != null ) {
//            try {
                Log.e("TAG","inside req camera");
//                filePath = data.getData();
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
//                imageViewReg.setImageURI(filePath);

                bitmap=(Bitmap)data.getExtras().get("data");
                imageViewReg.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }

    public void callImageChooser(View view) {
        showFileChooser();
    }

    public void callCameraImage(View view) {
        showCameraChooser();
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "select date",
//                Toast.LENGTH_SHORT)
//                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        editTextRegDob.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
}