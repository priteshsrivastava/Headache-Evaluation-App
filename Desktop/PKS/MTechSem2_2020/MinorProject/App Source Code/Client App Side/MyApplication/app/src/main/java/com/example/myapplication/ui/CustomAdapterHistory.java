package com.example.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapterHistory extends SimpleAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<HashMap<String, String>> arrayList;

    public CustomAdapterHistory(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.arrayList = data;

        inflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
//        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, arrayList.get(position).get("name"), Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;
    }

}

