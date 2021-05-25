package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.db.DBFuncAccess;
import com.example.myapplication.db.DBPatientResponseData;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;
import com.example.myapplication.server.DBWebServ;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CmnMethods {
    String TAG="CmnMethods";
    private Context context;

    public CmnMethods(Context context) {
        this.context=context;
        Log.i("TAG","Context val(in constructor):"+context);
    }

    public void callSync( DBFuncAccess objDBFuncAccess, String pid) {
        Log.i("TAG","callSync() for pid:"+pid);
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            Log.i(TAG, "Network service available.");
            if(isOnline()){
                Log.i(TAG, "Internet is connected.");
//                ProgressDialog p = new ProgressDialog(context);
//                p.setMessage("Please wait...Connecting with Server");
//                p.setIndeterminate(false);
//                p.setCancelable(false);
//                p.show();

                boolean flag1=uploadPatientTestBasicInfo(objDBFuncAccess, pid);
                boolean flag2=uploadTestDetailData(objDBFuncAccess, pid);
                boolean flag3=uploadPatientResponseData(objDBFuncAccess, pid);
                if(flag1 && flag2 && flag3){
//                   p.dismiss();
                    Log.i(TAG, "All Data Uploaded on Server Successfully");
                }

            }else{
                Log.e(TAG, "No Internet.");
                new AlertDialog.Builder(context)
                        .setTitle("Internet Service Unavailable")
                        .setMessage("No Internet Connection. Try again.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else {
            Log.e(TAG, "Network service is not available.");
            new AlertDialog.Builder(context)
                    .setTitle("Connection Failure")
                    .setMessage("Network service is not available.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void callReadAllAPI( DBFuncAccess objDBFuncAccess){
        Log.i("TAG","Calling callReadAllAPI() ");
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            Log.i(TAG, "Network service available.");
            if(isOnline()){
                Log.i(TAG, "Internet is connected.");
                DBWebServ objDBWebServ = new DBWebServ(context);
                objDBWebServ.dbReadAllPatientTestBasicInfo();
                objDBWebServ.dbReadAllTestDetailData();
                objDBWebServ.dbReadAllPatientResponseData();
                Log.i(TAG, "Called callReadAllAPI() from Server.");
            }else{
                Log.e(TAG, "No Internet.");
                new AlertDialog.Builder(context)
                        .setTitle("Internet Service Unavailable")
                        .setMessage("No Internet Connection. Try again.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else {
            Log.e(TAG, "Network service is not available.");
            new AlertDialog.Builder(context)
                    .setTitle("Connection Failure")
                    .setMessage("Network service is not available.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    //not in use
    public boolean isConnectedToServer(String url, int timeout) {
        try{
//            URL myUrl = new URL("http://"+url);
//            URLConnection connection = myUrl.openConnection();
//            connection.setConnectTimeout(timeout);
//            connection.connect();


            //Initializing Url
            URL myUrl = new URL("http://"+url);

            //Creating an httmlurl connection
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setConnectTimeout(timeout);

            conn.connect();
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                return true;
            }else{
                Log.e(TAG, " Server url:"+url+" unavailable or timeout("+timeout+").");
                return false;
            }

        } catch (Exception e) {
            // Handle your exceptions
            e.printStackTrace();
            Log.e(TAG, " Server url:"+url+" unavailable or timeout("+timeout+"). Exception:"+e.getMessage());
            return false;
        }
    }

    //to check availability of Internet
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            //pinging the Google DNS servers
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean uploadPatientTestBasicInfo(DBFuncAccess objDBFuncAccess,String pid){
        boolean flag=false;
        ArrayList<DBPatientTestBasicInfo> objDBPatientTestBasicInfoList = objDBFuncAccess.dbReadAllPatientTestBasicInfo(pid);
        Log.i(TAG, "Size of objDBPatientTestBasicInfoList:" + objDBPatientTestBasicInfoList.size());
        String msgStr="";
        if (objDBPatientTestBasicInfoList.size() > 0) {
            DBWebServ objDBWebServ = new DBWebServ(context);
            boolean delStatus = objDBWebServ.dbDeleteAllPatientTestBasicInfo(pid);
            if (delStatus == true) {
                boolean addAllStatus = objDBWebServ.dbAddAllPatientTestBasicInfo(objDBPatientTestBasicInfoList);
                if (addAllStatus == true) {
                    msgStr = "Data uploaded on Server.";
                    flag=true;
                } else {
                    msgStr = "Unable to AddAllPatientTestBasicInfo on Server. ";
                }
            } else {
                msgStr = "Unable to DeleteAllPatientTestBasicInfo on Server. ";
            }
        } else {
            msgStr = "No PatientTestBasicInfo data found for upload on Server.";
        }
//      Toast.makeText(context, msgStr, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msgStr);
        return flag;
    }

    public boolean uploadTestDetailData(DBFuncAccess objDBFuncAccess,String pid) {
        boolean flag = false;
        ArrayList<DBTestDetailData> objDBTestDetailDataList = objDBFuncAccess.dbReadAllTestDetailData(pid);
        Log.i(TAG, "Size of objDBTestDetailDataList:" + objDBTestDetailDataList.size());
        String msgStr = "";
        if (objDBTestDetailDataList.size() > 0) {
            DBWebServ objDBWebServ = new DBWebServ(context);
            boolean delStatus = objDBWebServ.dbDeleteAllTestDetailData(pid);
            if (delStatus == true) {
                boolean addAllStatus = objDBWebServ.dbAddAllTestDetailData(objDBTestDetailDataList);
                if (addAllStatus == true) {
                    msgStr = "Data uploaded on Server.";
                    flag = true;
                } else {
                    msgStr = "Unable to AddAllTestDetailData on Server. ";
                }
            } else {
                msgStr = "Unable to DeleteAllTestDetailData on Server. ";
            }
        } else {
            msgStr = "No TestDetailData data found for upload on Server.";
        }
//      Toast.makeText(context, msgStr, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msgStr);
        return flag;
    }

    public boolean uploadPatientResponseData(DBFuncAccess objDBFuncAccess,String pid){
        boolean flag=false;
        ArrayList<DBPatientResponseData> objDBPatientResponseDataList = objDBFuncAccess.dbReadAllPatientResponseData(pid);
        Log.i(TAG, "Size of objDBPatientResponseDataList:" + objDBPatientResponseDataList.size());
        String msgStr="";
        if (objDBPatientResponseDataList.size() > 0) {
            DBWebServ objDBWebServ = new DBWebServ(context);
            boolean delStatus = objDBWebServ.dbDeleteAllPatientResponseData(pid);
            if (delStatus == true) {
                boolean addAllStatus = objDBWebServ.dbAddAllPatientResponseData(objDBPatientResponseDataList);
                if (addAllStatus == true) {
                    msgStr = "Data uploaded on Server.";
                    flag=true;
                } else {
                    msgStr = "Unable to AddAllPatientResponseData on Server. ";
                }
            } else {
                msgStr = "Unable to DeleteAllPatientResponseData on Server. ";
            }
        } else {
            msgStr = "No PatientResponseData data found for upload on Server.";
        }
//      Toast.makeText(context, msgStr, Toast.LENGTH_SHORT).show();
        Log.e(TAG, msgStr);
        return flag;
    }

}
