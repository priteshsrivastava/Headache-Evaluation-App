package com.example.myapplication.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.server.DBWebServ;

import java.util.List;

public class AdminTestReportAdapter extends RecyclerView.Adapter<AdminTestReportAdapter.MyViewHolder> {

    private List<PatientTestData> patientTestDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pid, status, result, stTime;
        public ImageView imageViewAdminTestReport;

        public MyViewHolder(View view) {
            super(view);
            pid = (TextView) view.findViewById(R.id.pidAdminTestReportRow);
            status = (TextView) view.findViewById(R.id.adminTestReportStatus);
            result = (TextView) view.findViewById(R.id.adminTestReportResult);
            stTime = (TextView) view.findViewById(R.id.adminTestReportStTime);
            imageViewAdminTestReport = (ImageView) view.findViewById(R.id.imageViewAdminTestReport);
        }
    }


    public AdminTestReportAdapter(List<PatientTestData> patientTestDataList) {
        this.patientTestDataList = patientTestDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_test_report_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PatientTestData patientTestData = patientTestDataList.get(position);
        holder.pid.setText(patientTestData.getpId());
        if(patientTestData.getIsTestCompleted()==0){
            holder.status.setText("Status: Incomplete");
            holder.imageViewAdminTestReport.setImageResource(R.drawable.ic_launcher_redground);
        }else if(patientTestData.getIsTestCompleted()==1){
            holder.status.setText("Status: Completed");
            holder.imageViewAdminTestReport.setImageResource(R.drawable.ic_launcher_background);
        }
        //holder.result.setText("Result: Primary Headache, Migraine");
        holder.result.setText("Result: "+patientTestData.getResult());
        holder.stTime.setText(String.valueOf(patientTestData.getTestStDate()));
    }

    @Override
    public int getItemCount() {
        return patientTestDataList.size();
    }
}
