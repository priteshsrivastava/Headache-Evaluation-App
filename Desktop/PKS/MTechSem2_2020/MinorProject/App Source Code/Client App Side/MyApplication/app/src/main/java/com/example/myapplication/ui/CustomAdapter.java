package com.example.myapplication.ui;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends SimpleAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<HashMap<String, String>> arrayList;

    public static ArrayList<String> selectedAnswers; //store 'Yes'/'No'/'Not Attempted' ans in QueMigActivity

    SparseIntArray checked=new SparseIntArray();
    public CustomAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.arrayList = data;


        // initialize arraylist and add static string for all the questions
        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            selectedAnswers.add("Not Attempted");
        }

        inflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);
//        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, arrayList.get(position).get("name"), Toast.LENGTH_SHORT).show();
//            }
//        });

        // get the reference of TextView and Button's
        TextView question = (TextView) view.findViewById(R.id.migQueTextView);
        RadioButton yes = (RadioButton) view.findViewById(R.id.migQueYesRadBtn);
        RadioButton no = (RadioButton) view.findViewById(R.id.migQueNoRadBtn);

//
//// perform setOnCheckedChangeListener event on yes button
//        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//// set Yes values in ArrayList if RadioButton is checked
//                if (isChecked) {
//                    selectedAnswers.set(position, "Yes");
//                    Log.i("TAG", (position+1)+ " YesRadBtn checked");
//                }
//            }
//        });
//// perform setOnCheckedChangeListener event on no button
//        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//// set No values in ArrayList if RadioButton is checked
//                if (isChecked) {
//                    selectedAnswers.set(position, "No");
//                    Log.i("TAG", (position+1)+ " NoRadBtn checked");
//                }
//
//            }
//        });

        RadioGroup radioG=(RadioGroup) view.findViewById(R.id.radioGroupMigQue);
        radioG.setOnCheckedChangeListener(null);
        radioG.clearCheck();

        if(checked.indexOfKey(position)>-1){
            radioG.check(checked.get(position));
        }else{
            radioG.clearCheck();
        }
        radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)view.findViewById(checkedId);
                String text = checkedRadioButton.getText().toString();
                Log.i("TAG"," position:"+position+" checkedId:"+checkedId+" name:"+text);

                if(text.equalsIgnoreCase("Yes")){
                    checked.put(position, checkedId);
                    selectedAnswers.set(position, "Yes");
                }else if(text.equalsIgnoreCase("No")){
                    checked.put(position, checkedId);
                    selectedAnswers.set(position, "No");
                }

//                if(checkedId>-1){
//                    checked.put(position, checkedId);
//                    selectedAnswers.set(position, "Yes");
//                }else{
//                    if(checked.indexOfKey(position)>-1) {
//                        checked.removeAt(checked.indexOfKey(position));
//                        selectedAnswers.set(position, "No");
//                    }
//                }


//                if(checkedId==2131296436){ //Yes=2131296436, No=2131296433
//                    checked.put(position, checkedId);
//                    selectedAnswers.set(position, "Yes");
//                }else if(checkedId==2131296433){ //Yes=2131296436, No=2131296433
//                    checked.put(position, checkedId);
//                    selectedAnswers.set(position, "No");
//                } else{
//                    Log.i("TAG","checked.indexOfKey(position):"+checked.indexOfKey(position));
//                    if(checked.indexOfKey(position)>-1) {
//                        checked.removeAt(checked.indexOfKey(position));
//                        selectedAnswers.set(position, "No");
//                    }
//                }

            }
        });


// set the value in TextView
        question.setText("Que."+(position+1)+" "+arrayList.get(position).get("que_str"));


        return view;
    }

}
