package com.example.myapplication.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.MainActivity;
import com.example.myapplication.db.DBPatientDetailInfo;
import com.example.myapplication.db.DBPatientResponseData;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DBWebServ implements DBWebServAPI, AsyncResponse {
    Context context;
    public static ArrayList<DBPatientTestBasicInfo> objPatientTestBasicInfoList= new ArrayList<DBPatientTestBasicInfo>();
    public static ArrayList<DBTestDetailData> objTestDetailDataList= new ArrayList<DBTestDetailData>();
    public static ArrayList<DBPatientResponseData> objPatientResponseDataList= new ArrayList<DBPatientResponseData>();

    public static DBPatientDetailInfo objDBPatientDetailInfo=new DBPatientDetailInfo();

    public DBWebServ(Context context) {
        this.context = context;
//        objPatientTestBasicInfoList = new ArrayList<DBPatientTestBasicInfo>();
//        objTestDetailDataList= new ArrayList<DBTestDetailData>();
//        objPatientResponseDataList= new ArrayList<DBPatientResponseData>();
    }

    @Override
    public void processFinish(String url,String result, ProgressDialog p) {
        Log.i("DBWebServ", "inside processFinish()");
        JSONObject objectJSONResponse = null;
        try {
            objectJSONResponse = new JSONObject(result);
            Log.i("DBWebServ", "objectJSONResponse inside processFinish:" + objectJSONResponse.toString());
            if (!objectJSONResponse.getBoolean("error")) {
                Toast.makeText(context, objectJSONResponse.getString("message"), Toast.LENGTH_SHORT).show();
                Log.i("DBWebServ", objectJSONResponse.getString("message"));
                if (objectJSONResponse.length() > 2) {//Response is in third position
                    if(url.equalsIgnoreCase(DBWebServAPI.URL_READALL_PATIENT_TEST_BASIC_INFO)) {
                        JSONArray objPatientTestBasicInfoJSONArray = objectJSONResponse.getJSONArray("objPatientTestBasicInfoList");
                        if (objPatientTestBasicInfoJSONArray != null) {
                            //p.hide();
                            p.dismiss();
                            objPatientTestBasicInfoList.clear();
                            Log.i("DBWebServ", "Length of objPatientTestBasicInfoJSONArray(from Server):" + objPatientTestBasicInfoJSONArray.length());
                            for (int i = 0; i < objPatientTestBasicInfoJSONArray.length(); i++) {
                                //getting each TestBasicInfo object
                                JSONObject obj = objPatientTestBasicInfoJSONArray.getJSONObject(i);

                                //adding the DBPatientTestBasicInfo to the list
                                objPatientTestBasicInfoList.add(new DBPatientTestBasicInfo(
                                        obj.getInt("t_id"),
                                        obj.getString("p_id"),
                                        obj.getString("dr_id"),
                                        obj.getString("test_st_date"),
                                        obj.getString("test_end_date"),
                                        obj.getInt("is_test_completed")
                                ));
                            }
                            Log.i("DBWebServ", "Size of objPatientTestBasicInfoList(from Server, in processFinish):" + objPatientTestBasicInfoList.size());
                        } else {
                            p.show();
                        }
                    }else if(url.equalsIgnoreCase(DBWebServAPI.URL_READALL_TEST_DETAIL_DATA)) {
                        JSONArray objTestDetailDataJSONArray = objectJSONResponse.getJSONArray("objTestDetailDataList");
                        if (objTestDetailDataJSONArray != null) {
                            //p.hide();
                            p.dismiss();
                            objTestDetailDataList.clear();
                            Log.i("DBWebServ", "Length of objTestDetailDataJSONArray(from Server):" + objTestDetailDataJSONArray.length());
                            for (int i = 0; i < objTestDetailDataJSONArray.length(); i++) {
                                //getting each TestDetailData object
                                JSONObject obj = objTestDetailDataJSONArray.getJSONObject(i);

                                //adding the DBTestDetailData to the list
                                objTestDetailDataList.add(new DBTestDetailData(
                                        obj.getInt("t_id"),
                                        obj.getString("p_id"),
                                        (float)obj.getDouble("lvl"),
                                        obj.getString("lvl_status"),
                                        (float)obj.getDouble("que_ans_id"),
                                        obj.getInt("lvl_result_headache_cat")
                                ));
                            }
                            Log.i("DBWebServ", "Size of objTestDetailDataList(from Server, in processFinish):" + objTestDetailDataList.size());
                        } else {
                            p.show();
                        }
                    }else if(url.equalsIgnoreCase(DBWebServAPI.URL_READALL_PATIENT_RESPONSE_DATA)) {
                        JSONArray objPatientResponseDataJSONArray = objectJSONResponse.getJSONArray("objPatientResponseDataList");
                        if (objPatientResponseDataJSONArray != null) {
                            //p.hide();
                            p.dismiss();
                            objPatientResponseDataList.clear();
                            Log.i("DBWebServ", "Length of objPatientResponseDataJSONArray(from Server):" + objPatientResponseDataJSONArray.length());
                            for (int i = 0; i < objPatientResponseDataJSONArray.length(); i++) {
                                //getting each TestDetailData object
                                JSONObject obj = objPatientResponseDataJSONArray.getJSONObject(i);

                                //adding the DBPatientResponseData to the list
                                //t_id, p_id, lvl, que_group_id, que_id,response_yes_no,response_str
                               // int tId, String pId, float lvl, int queGroupId, float queId, int responseYesNo, String responseStr
                                objPatientResponseDataList.add(new DBPatientResponseData(
                                        obj.getInt("t_id"),
                                        obj.getString("p_id"),
                                        (float)obj.getDouble("lvl"),
                                        obj.getInt("que_group_id"),
                                        (float)obj.getDouble("que_id"),
                                        obj.getInt("response_yes_no"),
                                        obj.getString("response_str")
                                ));
                            }
                            Log.i("DBWebServ", "Size of objPatientResponseDataList(from Server, in processFinish):" + objPatientResponseDataList.size());
                        } else {
                            p.show();
                        }
                    }else if(url.equalsIgnoreCase(DBWebServAPI.URL_READ_PATIENT_DETAIL_INFO)){
                        JSONObject obj = objectJSONResponse.getJSONObject("objPatientDetailInfo");
                        if (obj != null) {
                            p.dismiss();
                            objDBPatientDetailInfo.setPid(obj.getString("p_id"));
                            objDBPatientDetailInfo.setPwd(obj.getString("pwd"));
                            objDBPatientDetailInfo.setName(obj.getString("name"));
                            objDBPatientDetailInfo.setDob(obj.getString("dob"));
                            objDBPatientDetailInfo.setGender(obj.getString("gender"));
                            objDBPatientDetailInfo.setAddress(obj.getString("address"));
                            objDBPatientDetailInfo.setMobileNo(obj.getLong("mobile_no"));
                            objDBPatientDetailInfo.setAdhaarNo(obj.getLong("adhaar_no"));
                            objDBPatientDetailInfo.setEmail(obj.getString("email"));
                            objDBPatientDetailInfo.setHealthHistory(obj.getString("health_history"));
                            objDBPatientDetailInfo.setImage(obj.getString("image"));
                            Log.i("DBWebServ", "ReadPatientDetailInfo successful(from Server, in processFinish) for pid:" + objDBPatientDetailInfo.getPid());
                        } else {
                            p.show();
                        }
                    }

                } else {
                   // p.hide();
                    p.dismiss();
                }
            }else {
                //p.hide();
                p.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(objectJSONResponse.getString("message"));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Server Error!!!");
                alert.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //p.hide();
            p.dismiss();
            Log.e("DBWebServ", "inside processFinish(),JSONException: " + e.getMessage());
            Toast.makeText(context, "No response from Server.", Toast.LENGTH_SHORT).show();

//            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setMessage("No response from Server..Try again.");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//                    dialog.cancel();
//                }
//            });
//            AlertDialog alert = builder.create();
//            alert.setTitle("Server Error!!!");
//            alert.show();
        }
    }


    @Override
    public ArrayList<DBPatientTestBasicInfo> dbReadAllPatientTestBasicInfo() {
        AsyncResponse listener = this;
        HashMap<String, String> params = null;
        PerformNetworkRequest request = new PerformNetworkRequest(URL_READALL_PATIENT_TEST_BASIC_INFO, params, CODE_GET_REQUEST, listener, context);
        request.execute();

        Log.i("DBWebServ", "DBWebServAPI dbReadAllPatientTestBasicInfo() called from Server.");
        return objPatientTestBasicInfoList;
    }

    @Override
    public boolean dbAddPatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo) {
        int tId = objDBPatientTestBasicInfo.gettId();//auto incremented
        String pId = objDBPatientTestBasicInfo.getpId();
        String drId = objDBPatientTestBasicInfo.getDrId();
        String testStDate = objDBPatientTestBasicInfo.getTestStDate();
        String testEndDate = objDBPatientTestBasicInfo.getTestEndDate();
        int isTestCompleted = objDBPatientTestBasicInfo.getIsTestCompleted();

        //validating the inputs
        if (objDBPatientTestBasicInfo.gettId() <= 0 || objDBPatientTestBasicInfo.getpId().isEmpty()) {
            Log.e("DBWebServ", "Invalid value for addPatientTestBasicInfo.");
            return false;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("t_id", String.valueOf(tId));
        params.put("p_id", pId);
        params.put("dr_id", drId);
        params.put("test_st_date", testStDate);
        params.put("test_end_date", testEndDate);
        params.put("is_test_completed", String.valueOf(isTestCompleted));

        //Calling the Add API
        PerformNetworkRequest request = new PerformNetworkRequest(URL_ADD_PATIENT_TEST_BASIC_INFO, params, CODE_POST_REQUEST);
        request.execute();
        Log.i("DBWebServ", "DBWebServAPI dbAddPatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo) called from Server.");
        return true;
    }

    @Override
    public boolean dbUpdatePatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo) {
        int tId = objDBPatientTestBasicInfo.gettId();
        String pId = objDBPatientTestBasicInfo.getpId();
        String drId = objDBPatientTestBasicInfo.getDrId();
        String testStDate = objDBPatientTestBasicInfo.getTestStDate();
        String testEndDate = objDBPatientTestBasicInfo.getTestEndDate();
        int isTestCompleted = objDBPatientTestBasicInfo.getIsTestCompleted();

        //validating the inputs
        if (objDBPatientTestBasicInfo.gettId() <= 0 || objDBPatientTestBasicInfo.getpId().isEmpty()) {
            Log.e("DBWebServ", "Invalid value for updatePatientTestBasicInfo.");
            return false;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("t_id", String.valueOf(tId));
        params.put("p_id", pId);
        params.put("dr_id", drId);
        params.put("test_st_date", testStDate);
        params.put("test_end_date", testEndDate);
        params.put("is_test_completed", String.valueOf(isTestCompleted));

        //Calling the Update API
        PerformNetworkRequest request = new PerformNetworkRequest(URL_UPDATE_PATIENT_TEST_BASIC_INFO, params, CODE_POST_REQUEST);
        request.execute();
        Log.i("DBWebServ", "DBWebServAPI dbUpdatePatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo) called from Server.");
        return true;
    }

    @Override
    public boolean dbDeletePatientTestBasicInfo(int tId, String pId) {
        //validating the inputs
        if (tId <= 0 || pId.isEmpty()) {
            Log.e("DBWebServ", "Invalid value for deletePatientTestBasicInfo.");
            return false;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("t_id", String.valueOf(tId));
        params.put("p_id", pId);

        //Calling the Delete API
        PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETE_PATIENT_TEST_BASIC_INFO, params, CODE_POST_REQUEST);
        request.execute();
        Log.i("DBWebServ", "DBWebServAPI dbDeletePatientTestBasicInfo(pId:"+pId+",tId:"+tId+") called from Server.");
        return true;
    }

    @Override
    public boolean dbDeleteAllPatientTestBasicInfo(String pId) {
        //validating the inputs
        if (pId.isEmpty()) {
            Log.e("DBWebServ", "Invalid value for deleteAllPatientTestBasicInfo.");
            return false;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("p_id", pId);

        //Calling the Delete API
        try {
            AsyncResponse listener = this;
            PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETEALL_PATIENT_TEST_BASIC_INFO, params, CODE_POST_REQUEST);
            request.execute();
            Log.i("DBWebServ", "DBWebServAPI dbDeleteAllPatientTestBasicInfo("+pId+") called from Server.");

            return true;
        } catch (Exception e) {
            Log.e("DBWebServ", "DELETEALL PATIENT_TEST_BASIC_INFO error for pid:" + pId + ", " + e.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<DBTestDetailData> dbReadAllTestDetailData() {
        AsyncResponse listener = this;
        HashMap<String, String> params = null;
        PerformNetworkRequest request = new PerformNetworkRequest(URL_READALL_TEST_DETAIL_DATA, params, CODE_GET_REQUEST, listener, context);
        request.execute();

        Log.i("DBWebServ", "DBWebServAPI dbReadAllTestDetailData() called from Server.");
        return objTestDetailDataList;
    }


    @Override
    public boolean dbAddAllTestDetailData(ArrayList<DBTestDetailData> objDBTestDetailDataList) {
        ArrayList<HashMap<String, String>> paramsList = new ArrayList<>();
        for (int i = 0; i < objDBTestDetailDataList.size(); i++) {
            DBTestDetailData objDBTestDetailData = objDBTestDetailDataList.get(i);
            int tId = objDBTestDetailData.gettId();
            String pId = objDBTestDetailData.getpId();
            Float lvl = objDBTestDetailData.getLvl();
            String lvlStatus = objDBTestDetailData.getLvlStatus();
            Float queAnsId = objDBTestDetailData.getQueAnsId();
            int lvlResultHeadacheCat = objDBTestDetailData.getLvlResultHeadacheCat();

            //validating the inputs
            if (objDBTestDetailData.gettId() <= 0 || objDBTestDetailData.getpId().isEmpty()) {
                Log.e("DBWebServ", "Invalid value for addTestDetailData.");
                return false;
            }

            //if validation passes
            HashMap<String, String> params = new HashMap<>();
            params.put("t_id", String.valueOf(tId));
            params.put("p_id", pId);
            params.put("lvl", String.valueOf(lvl));
            params.put("lvl_status", lvlStatus);
            params.put("que_ans_id", String.valueOf(queAnsId));
            params.put("lvl_result_headache_cat", String.valueOf(lvlResultHeadacheCat));
            paramsList.add(params);
        }
        //Calling the Add API
        try {
            AsyncResponse listener = this;
            // PerformNetworkRequest request = new PerformNetworkRequest(URL_READALL_TEST_DETAIL_DATA, null, CODE_GET_REQUEST,listener,context);
            PerformNetworkRequest request = new PerformNetworkRequest(URL_ADDALL_TEST_DETAIL_DATA, paramsList, CODE_POST_ARRAYLIST_REQUEST, listener, context);
            request.execute();
            Log.i("DBWebServ", "DBWebServAPI dbAddAllTestDetailData(ArrayList<DBTestDetailData> objDBTestDetailDataList) called from Server.");
            return true;
        } catch (Exception e) {
            Log.i("DBWebServ", "ADDALL TEST_DETAIL_DATA Error:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean dbDeleteAllTestDetailData(String pId) {
        //validating the inputs
        if (pId.isEmpty()) {
            Log.e("DBWebServ", "Invalid value for deleteAllTestDetailData.");
            return false;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("p_id", pId);

        //Calling the Delete API
        try {
            AsyncResponse listener = this;
            PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETEALL_TEST_DETAIL_DATA, params, CODE_POST_REQUEST);
            request.execute();
            Log.i("DBWebServ", "DBWebServAPI dbDeleteAllTestDetailData('"+pId+"') called from Server.");
            return true;
        } catch (Exception e) {
            Log.e("DBWebServ", "DELETEALL TEST_DETAIL_DATA error for pid:" + pId + ", " + e.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData() {
        AsyncResponse listener = this;
        HashMap<String, String> params = null;
        PerformNetworkRequest request = new PerformNetworkRequest(URL_READALL_PATIENT_RESPONSE_DATA, params, CODE_GET_REQUEST, listener, context);
        request.execute();

        Log.i("DBWebServ", "DBWebServAPI dbReadAllPatientResponseData() called from Server.");
        return objPatientResponseDataList;
    }

    @Override
    public boolean dbAddAllPatientResponseData(ArrayList<DBPatientResponseData> objDBPatientResponseDataList) {
        ArrayList<HashMap<String, String>> paramsList = new ArrayList<>();
        for (int i = 0; i < objDBPatientResponseDataList.size(); i++) {
            DBPatientResponseData objDBPatientResponseData = objDBPatientResponseDataList.get(i);
            int tId = objDBPatientResponseData.gettId();
            String pId = objDBPatientResponseData.getpId();
            Float lvl = objDBPatientResponseData.getLvl();
            int queGroupId = objDBPatientResponseData.getQueGroupId();
            Float queId = objDBPatientResponseData.getQueId();
            int responseYesNo = objDBPatientResponseData.getResponseYesNo();
            String responseStr = objDBPatientResponseData.getResponseStr();

            //validating the inputs
            if (objDBPatientResponseData.gettId() <= 0 || objDBPatientResponseData.getpId().isEmpty()) {
                Log.e("DBWebServ", "Invalid value for addPatientResponseData.");
                return false;
            }

            //if validation passes
            HashMap<String, String> params = new HashMap<>();
            params.put("t_id", String.valueOf(tId));
            params.put("p_id", pId);
            params.put("lvl", String.valueOf(lvl));
            params.put("que_group_id", String.valueOf(queGroupId));
            params.put("que_id", String.valueOf(queId));
            params.put("response_yes_no", String.valueOf(responseYesNo));
            params.put("response_str", responseStr);
            paramsList.add(params);
        }
        //Calling the Add API
        try {
            AsyncResponse listener = this;
            // PerformNetworkRequest request = new PerformNetworkRequest(URL_READALL_PATIENT_RESPONSE_DATA, null, CODE_GET_REQUEST,listener,context);
            PerformNetworkRequest request = new PerformNetworkRequest(URL_ADDALL_PATIENT_RESPONSE_DATA, paramsList, CODE_POST_ARRAYLIST_REQUEST, listener, context);
            request.execute();
            Log.i("DBWebServ", "DBWebServAPI dbAddAllPatientResponseData(ArrayList<DBPatientResponseData> objDBPatientResponseDataList) called from Server.");
            return true;
        } catch (Exception e) {
            Log.i("DBWebServ", "ADDALL PATIENT_RESPONSE_DATA Error:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean dbDeleteAllPatientResponseData(String pId) {
        //validating the inputs
        if (pId.isEmpty()) {
            Log.e("DBWebServ", "Invalid value for deleteAllPatientResponseData.");
            return false;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("p_id", pId);

        //Calling the Delete API
        try {
            AsyncResponse listener = this;
            PerformNetworkRequest request = new PerformNetworkRequest(URL_DELETEALL_PATIENT_RESPONSE_DATA, params, CODE_POST_REQUEST);
            request.execute();
            Log.i("DBWebServ", "DBWebServAPI dbDeleteAllPatientResponseData('"+pId+"') called from Server.");

            return true;
        } catch (Exception e) {
            Log.e("DBWebServ", "DELETEALL PATIENT_RESPONSE_DATA error for pid:" + pId + ", " + e.getMessage());
        }
        return false;
    }

    @Override
    public DBPatientDetailInfo dbReadPatientDetailInfo(String pId) {
        //validating the inputs
        if (pId.isEmpty()) {
            Log.e("DBWebServ", "Invalid value for dbReadPatientDetailInfo.");
            return null;
        }

        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("p_id", pId);
        AsyncResponse listener = this;
        PerformNetworkRequest request = new PerformNetworkRequest(URL_READ_PATIENT_DETAIL_INFO, params, CODE_POST_REQUEST, listener, context);
        request.execute();

        Log.i("DBWebServ", "DBWebServAPI dbReadPatientDetailInfo() called from Server.");
        return objDBPatientDetailInfo;
    }

    @Override
    public boolean dbAddPatientDetailInfo(DBPatientDetailInfo objDBPatientDetailInfo) {
            //validating the inputs
            if (objDBPatientDetailInfo.getName().isEmpty()|| objDBPatientDetailInfo.getPid().isEmpty()) {
                Log.e("DBWebServ", "Invalid value for dbAddPatientDetailInfo.");
                return false;
            }

            //if validation passes
            HashMap<String, String> params = new HashMap<>();
            params.put("p_id", objDBPatientDetailInfo.getPid());
            params.put("pwd", objDBPatientDetailInfo.getPwd());
            params.put("name", objDBPatientDetailInfo.getName());
            params.put("dob", objDBPatientDetailInfo.getDob());
            params.put("gender", objDBPatientDetailInfo.getGender());
            params.put("address", objDBPatientDetailInfo.getAddress());
            params.put("mobile_no", String.valueOf(objDBPatientDetailInfo.getMobileNo()));
            params.put("adhaar_no", String.valueOf(objDBPatientDetailInfo.getAdhaarNo()));
            params.put("email", objDBPatientDetailInfo.getEmail());
            params.put("health_history", objDBPatientDetailInfo.getHealthHistory());
            params.put("image", objDBPatientDetailInfo.getImage());

        //Calling the Add API
        try {
            AsyncResponse listener = this;
            PerformNetworkRequest request = new PerformNetworkRequest(URL_ADD_PATIENT_DETAIL_INFO, params, CODE_POST_REQUEST, listener, context);
            request.execute();
            Log.i("DBWebServ", "DBWebServAPI dbAddPatientDetailInfo(DBAddPatientDetailInfo dbAddPatientDetailInfo) called from Server.");
            return true;
        } catch (Exception e) {
            Log.i("DBWebServ", "ADD PATIENT_DETAIL_INFO Error:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean dbAddAllPatientTestBasicInfo(ArrayList<DBPatientTestBasicInfo> objDBPatientTestBasicInfoList) {
        ArrayList<HashMap<String, String>> paramsList = new ArrayList<>();
        for (int i = 0; i < objDBPatientTestBasicInfoList.size(); i++) {
            DBPatientTestBasicInfo objDBPatientTestBasicInfo = objDBPatientTestBasicInfoList.get(i);
            int tId = objDBPatientTestBasicInfo.gettId();//auto incremented
            String pId = objDBPatientTestBasicInfo.getpId();
            String drId = objDBPatientTestBasicInfo.getDrId();
            String testStDate = objDBPatientTestBasicInfo.getTestStDate();
            String testEndDate = objDBPatientTestBasicInfo.getTestEndDate();
            int isTestCompleted = objDBPatientTestBasicInfo.getIsTestCompleted();

            //validating the inputs
            if (objDBPatientTestBasicInfo.gettId() <= 0 || objDBPatientTestBasicInfo.getpId().isEmpty()) {
                Log.e("DBWebServ", "Invalid value for addPatientTestBasicInfo.");
                return false;
            }

            //if validation passes
            HashMap<String, String> params = new HashMap<>();
            params.put("t_id", String.valueOf(tId));
            params.put("p_id", pId);
            params.put("dr_id", drId);
            params.put("test_st_date", testStDate);
            params.put("test_end_date", testEndDate);
            params.put("is_test_completed", String.valueOf(isTestCompleted));
            paramsList.add(params);
        }
        //Calling the Add API
        try {
            AsyncResponse listener = this;
            // PerformNetworkRequest request = new PerformNetworkRequest(URL_READALL_PATIENT_TEST_BASIC_INFO, null, CODE_GET_REQUEST,listener,context);

            PerformNetworkRequest request = new PerformNetworkRequest(URL_ADDALL_PATIENT_TEST_BASIC_INFO, paramsList, CODE_POST_ARRAYLIST_REQUEST, listener, context);
            request.execute();
            Log.i("DBWebServ", "DBWebServAPI dbAddAllPatientTestBasicInfo(ArrayList<DBPatientTestBasicInfo> objDBPatientTestBasicInfoList) called from Server.");
            return true;
        } catch (Exception e) {
            Log.i("DBWebServ", "ADDALL PATIENT_TEST_BASIC_INFO Error:" + e.getMessage());
        }
        return false;
    }


}


