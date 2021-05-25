package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.myapplication.R;

public class ShowBroadHeadacheGroupQueActivity extends AppCompatActivity {
    RadioButton rbYes;
    RadioButton rbNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_broad_headache_group_que);

        rbYes=findViewById(R.id.grpQueYesRadBtn);
        rbNo=findViewById(R.id.grpQueNoRadBtn);
    }


}