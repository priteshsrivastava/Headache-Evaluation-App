package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.myapplication.R;


public class ShowMigQueActivity extends AppCompatActivity {
    RadioButton rbYes;
    RadioButton rbNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mig_que);


        rbYes=findViewById(R.id.migQueYesRadBtn);
        rbNo=findViewById(R.id.migQueNoRadBtn);

//        rbYes.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.i("TAG","YesRadBtn: onCheckedChanged, isChecked: " + isChecked);
//            }
//        });
//
//        rbNo.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.i("TAG","NoRadBtn: onCheckedChanged, isChecked: " + isChecked);
//            }
//        });

    }

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch (view.getId()) {
//            case R.id.migQueYesRadBtn:
//                if (checked)
//                    Log.i("TAG","YesRadBtn: onCheckedChanged, isChecked: " + checked);
//                    break;
//            case R.id.migQueNoRadBtn:
//                if (checked)
//                    Log.i("TAG","NoRadBtn: onCheckedChanged, isChecked: " + checked);
//                    break;
//        }
//    }

}
