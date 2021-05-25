package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.util.ArrayList;

public class DBFuncAccess implements IDBInterface {
    private static final String TAG = "DBFuncAccess";
    private static SQLiteDatabase dbConn;

    //static member holds only one instance of the DBFuncAccess class.
    private static DBFuncAccess objDBFuncAccess;

    //DBFuncAccess prevents the instantiation from any other class.
    private DBFuncAccess() {  }

    //Now we are providing global point of access.
    public static DBFuncAccess getInstance(Context context) {
        if (objDBFuncAccess==null){
            objDBFuncAccess=new  DBFuncAccess(context);
        }
        return objDBFuncAccess;
    }

    private DBFuncAccess(Context context) {
        DatabaseHelper databasehelper = DatabaseHelper.getInstance(context);
        try{
            dbConn = databasehelper.getWritableDatabase();
            Log.i(TAG,"Database connected successfully.");
        }catch(SQLiteException sqliteex){
            Log.e(TAG,"Unable to connect with Database. Exception:"+sqliteex.getCause());
            sqliteex.printStackTrace();
            return;
        }
    }


    @Override
    public ArrayList<DBBranchingLogicInfo> dbReadAllBranchingLogicInfo() {
        ArrayList<DBBranchingLogicInfo> itemList = new ArrayList();
        String[] columnArray = { "lvl,que_id, ans_yes_id, ans_no_id, prev_que_id, que_str_en, que_str_hi, remarks" };
        Cursor cursor = dbConn.query("branching_logic_info",
                columnArray, null, null, null, null, "lvl,que_id ASC", null);

//        String selectQuery = "SELECT  * FROM " + TABLE_TODO;
//        Log.e(LOG, selectQuery);
//        Cursor cursor = dbConn.rawQuery(selectQuery, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBBranchingLogicInfo objDBBranchingLogicInfo = new DBBranchingLogicInfo();
                objDBBranchingLogicInfo.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBBranchingLogicInfo.setQueId(cursor.getFloat(cursor.getColumnIndex("que_id")));
                objDBBranchingLogicInfo.setAnsYesId(cursor.getFloat(cursor.getColumnIndex("ans_yes_id")));
                objDBBranchingLogicInfo.setAnsNoId(cursor.getFloat(cursor.getColumnIndex("ans_no_id")));
                objDBBranchingLogicInfo.setPrevQueId(cursor.getFloat(cursor.getColumnIndex("prev_que_id")));
                objDBBranchingLogicInfo.setQueStrEn(cursor.getString(cursor.getColumnIndex("que_str_en")));
                objDBBranchingLogicInfo.setQueStrHi(cursor.getString(cursor.getColumnIndex("que_str_hi")));
                objDBBranchingLogicInfo.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                itemList.add(objDBBranchingLogicInfo);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllBranchingLogicInfo - No value found");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public DBBranchingLogicInfo dbReadBranchingLogicInfo(float level1, float queId1) {
        float lvl=level1;
        float queId=queId1;
        DBBranchingLogicInfo objDBBranchingLogicInfo = new DBBranchingLogicInfo();
        String[] columnArray = { "lvl,que_id, ans_yes_id, ans_no_id, prev_que_id, que_str_en, que_str_hi, remarks" };
        Cursor cursor = dbConn.query("branching_logic_info",
                columnArray, "lvl="+lvl+" and que_id="+queId, null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBBranchingLogicInfo.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
            objDBBranchingLogicInfo.setQueId(cursor.getFloat(cursor.getColumnIndex("que_id")));
            objDBBranchingLogicInfo.setAnsYesId(cursor.getFloat(cursor.getColumnIndex("ans_yes_id")));
            objDBBranchingLogicInfo.setAnsNoId(cursor.getFloat(cursor.getColumnIndex("ans_no_id")));
            objDBBranchingLogicInfo.setPrevQueId(cursor.getFloat(cursor.getColumnIndex("prev_que_id")));
            objDBBranchingLogicInfo.setQueStrEn(cursor.getString(cursor.getColumnIndex("que_str_en")));
            objDBBranchingLogicInfo.setQueStrHi(cursor.getString(cursor.getColumnIndex("que_str_hi")));
            objDBBranchingLogicInfo.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
        } else {
            Log.e(TAG,"dbReadBranchingLogicInfo - No value found for lvl="+lvl+" and que_id="+queId+".");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBBranchingLogicInfo;
    }

    @Override
    public ArrayList<DBAnswerInfo> dbReadAllAnswerInfo() {
        ArrayList<DBAnswerInfo> itemList=new ArrayList<>();
        String[] columnArray = { "lvl, ans_id, headache_cat" };
        Cursor cursor = dbConn.query("answer_info",
                columnArray, null, null, null, null, "lvl, ans_id ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do{
                DBAnswerInfo objDBAnswerInfo = new DBAnswerInfo();
                objDBAnswerInfo.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBAnswerInfo.setAnsId(cursor.getFloat(cursor.getColumnIndex("ans_id")));
                objDBAnswerInfo.setHeadacheCat(cursor.getInt(cursor.getColumnIndex("headache_cat")));
                itemList.add(objDBAnswerInfo);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllAnswerInfo - No value found.");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public DBAnswerInfo dbReadAnswerInfo(float level1, float ansId1) {
        double lvl=level1;
        double ansId=ansId1;
        DBAnswerInfo objDBAnswerInfo = new DBAnswerInfo();
        String[] columnArray = { "lvl, ans_id, headache_cat" };
        Cursor cursor = dbConn.query("answer_info",
                columnArray, "lvl="+lvl+" and ans_id="+ansId, null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBAnswerInfo.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
            objDBAnswerInfo.setAnsId(cursor.getFloat(cursor.getColumnIndex("ans_id")));
            objDBAnswerInfo.setHeadacheCat(cursor.getInt(cursor.getColumnIndex("headache_cat")));

        } else {
            Log.e(TAG,"dbReadAnswerInfo - No value found for lvl="+lvl+" and ans_id="+ansId+".");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBAnswerInfo;
    }

    @Override
    public ArrayList<DBHeadacheCatInfo> dbReadAllHeadacheCatInfo() {
         ArrayList<DBHeadacheCatInfo> itemList=new ArrayList<>();
       String[] columnArray = { "headache_cat, cat_str_en, cat_str_hi" };
        Cursor cursor = dbConn.query("headache_cat_info",
                columnArray, null, null, null, null, "headache_cat ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do{
                DBHeadacheCatInfo objDBHeadacheCatInfo = new DBHeadacheCatInfo();
                objDBHeadacheCatInfo.setHeadacheCat(cursor.getInt(cursor.getColumnIndex("headache_cat")));
                objDBHeadacheCatInfo.setCatStrEn(cursor.getString(cursor.getColumnIndex("cat_str_en")));
                objDBHeadacheCatInfo.setCatStrHi(cursor.getString(cursor.getColumnIndex("cat_str_hi")));
                itemList.add(objDBHeadacheCatInfo);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllHeadacheCatInfo - No value found.");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public DBHeadacheCatInfo dbReadHeadacheCatInfo(int headacheCat) {
        DBHeadacheCatInfo objDBHeadacheCatInfo = new DBHeadacheCatInfo();
        String[] columnArray = { "headache_cat, cat_str_en, cat_str_hi" };
        Cursor cursor = dbConn.query("headache_cat_info",
                columnArray, "headache_cat="+headacheCat, null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBHeadacheCatInfo.setHeadacheCat(cursor.getInt(cursor.getColumnIndex("headache_cat")));
            objDBHeadacheCatInfo.setCatStrEn(cursor.getString(cursor.getColumnIndex("cat_str_en")));
            objDBHeadacheCatInfo.setCatStrHi(cursor.getString(cursor.getColumnIndex("cat_str_hi")));
        } else {
            Log.e(TAG,"dbReadHeadacheCatInfo - No value found for headache_cat="+headacheCat+".");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBHeadacheCatInfo;
    }

    @Override
    public ArrayList<DBPatientTestBasicInfo> dbReadAllPatientTestBasicInfo() {
        ArrayList<DBPatientTestBasicInfo> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, dr_id, test_st_date, test_end_date, is_test_completed" };
        Cursor cursor = dbConn.query("patient_test_basic_info",
                columnArray, null, null, null, null, "p_id, t_id ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do{
            DBPatientTestBasicInfo objDBPatientTestBasicInfo = new DBPatientTestBasicInfo();
            objDBPatientTestBasicInfo.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
            objDBPatientTestBasicInfo.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
            objDBPatientTestBasicInfo.setDrId(cursor.getString(cursor.getColumnIndex("dr_id")));
            objDBPatientTestBasicInfo.setTestStDate(cursor.getString(cursor.getColumnIndex("test_st_date")));
            objDBPatientTestBasicInfo.setTestEndDate(cursor.getString(cursor.getColumnIndex("test_end_date")));
            objDBPatientTestBasicInfo.setIsTestCompleted(cursor.getInt(cursor.getColumnIndex("is_test_completed")));
            itemList.add(objDBPatientTestBasicInfo);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllPatientTestBasicInfo - No value found.");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public ArrayList<DBPatientTestBasicInfo> dbReadAllPatientTestBasicInfo(String pId){
        ArrayList<DBPatientTestBasicInfo> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, dr_id, test_st_date, test_end_date, is_test_completed" };
        Cursor cursor = dbConn.query("patient_test_basic_info",
                columnArray, "p_id='"+pId+"' ", null, null, null, "test_st_date DESC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do{
                DBPatientTestBasicInfo objDBPatientTestBasicInfo = new DBPatientTestBasicInfo();
                objDBPatientTestBasicInfo.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
                objDBPatientTestBasicInfo.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
                objDBPatientTestBasicInfo.setDrId(cursor.getString(cursor.getColumnIndex("dr_id")));
                objDBPatientTestBasicInfo.setTestStDate(cursor.getString(cursor.getColumnIndex("test_st_date")));
                objDBPatientTestBasicInfo.setTestEndDate(cursor.getString(cursor.getColumnIndex("test_end_date")));
                objDBPatientTestBasicInfo.setIsTestCompleted(cursor.getInt(cursor.getColumnIndex("is_test_completed")));
                itemList.add(objDBPatientTestBasicInfo);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllPatientTestBasicInfo - No value found.");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public DBPatientTestBasicInfo dbReadPatientTestBasicInfo(int tId,String pId) {
        DBPatientTestBasicInfo objDBPatientTestBasicInfo = new DBPatientTestBasicInfo();
        String[] columnArray = { "t_id, p_id, dr_id, test_st_date, test_end_date, is_test_completed" };
        Cursor cursor = dbConn.query("patient_test_basic_info",
                columnArray, "t_id="+tId+" and p_id='"+pId+"'", null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBPatientTestBasicInfo.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
            objDBPatientTestBasicInfo.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
            objDBPatientTestBasicInfo.setDrId(cursor.getString(cursor.getColumnIndex("dr_id")));
            objDBPatientTestBasicInfo.setTestStDate(cursor.getString(cursor.getColumnIndex("test_st_date")));
            objDBPatientTestBasicInfo.setTestEndDate(cursor.getString(cursor.getColumnIndex("test_end_date")));
            objDBPatientTestBasicInfo.setIsTestCompleted(cursor.getInt(cursor.getColumnIndex("is_test_completed")));
        } else {
            Log.e(TAG,"dbReadPatientTestBasicInfo - No value found for t_id="+tId+" and p_id="+pId);
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBPatientTestBasicInfo;
    }

    @Override
    public boolean dbAddPatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo) {
        boolean insertFlag = false;
        int tId = (int) getNextAutoIncrement("patient_test_basic_info");
        objDBPatientTestBasicInfo.settId(tId);
        long result = dbConn.insert("patient_test_basic_info", null,
                this.getContentValues(objDBPatientTestBasicInfo));
        if(result != -1){// result returns row_id of newly inserted row
            insertFlag= true;
        }else{
            Log.e(TAG,"dbAddPatientTestBasicInfo - Insert failed for t_id="+objDBPatientTestBasicInfo.gettId()+" and p_id="+objDBPatientTestBasicInfo.getpId() );
        }
        return insertFlag;
    }

    @Override
    public boolean dbUpdatePatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo) {
        boolean updateFlag = false;
        String whereClause = "t_id = ? and p_id = ? ";
        String[] whereArgs = { String.valueOf(objDBPatientTestBasicInfo.gettId()),String.valueOf(objDBPatientTestBasicInfo.getpId()) };
        int result = dbConn.update("patient_test_basic_info",
                this.getContentValues(objDBPatientTestBasicInfo), whereClause, whereArgs);
        if(result == 1){//result returns the number of rows affected
            updateFlag= true;
        }else{
            Log.e(TAG,"dbUpdatePatientTestBasicInfo - Update failed for t_id="+objDBPatientTestBasicInfo.gettId()+" and p_id="+objDBPatientTestBasicInfo.getpId()  );
        }
        return updateFlag;
    }

    @Override
    public boolean dbDeletePatientTestBasicInfo(int tId, String pId) {
        boolean delFlag = false;
        String whereClause = "t_id = ? and p_id = ? ";
        String[] whereArgs = { String.valueOf(tId), pId};
        int result = dbConn.delete("patient_test_basic_info", whereClause, whereArgs);
        if(result == 1){//result returns the number of rows affected
            delFlag= true;
        }else{
            Log.e(TAG,"dbDeletePatientTestBasicInfo - Delete failed for t_id="+tId+" and p_id="+pId );
        }
        return delFlag;
    }


    @Override
    public ArrayList<DBTestDetailData> dbReadAllTestDetailData() {
        ArrayList<DBTestDetailData> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, lvl, lvl_status, que_ans_id, lvl_result_headache_cat" };
        Cursor cursor = dbConn.query("test_detail_data",
                columnArray, null, null, null, null, "p_id, t_id, lvl ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBTestDetailData objDBTestDetailData = new DBTestDetailData();
                objDBTestDetailData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
                objDBTestDetailData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
                objDBTestDetailData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBTestDetailData.setLvlStatus(cursor.getString(cursor.getColumnIndex("lvl_status")));
                objDBTestDetailData.setQueAnsId(cursor.getFloat(cursor.getColumnIndex("que_ans_id")));
                objDBTestDetailData.setLvlResultHeadacheCat(cursor.getInt(cursor.getColumnIndex("lvl_result_headache_cat")));
                itemList.add(objDBTestDetailData);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllTestDetailData - No value found .");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public ArrayList<DBTestDetailData> dbReadAllTestDetailData(String pId) {
        ArrayList<DBTestDetailData> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, lvl, lvl_status, que_ans_id, lvl_result_headache_cat" };
        Cursor cursor = dbConn.query("test_detail_data",
                columnArray, "p_id='"+pId+"'", null, null, null, "t_id,lvl DESC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBTestDetailData objDBTestDetailData = new DBTestDetailData();
                objDBTestDetailData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
                objDBTestDetailData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
                objDBTestDetailData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBTestDetailData.setLvlStatus(cursor.getString(cursor.getColumnIndex("lvl_status")));
                objDBTestDetailData.setQueAnsId(cursor.getFloat(cursor.getColumnIndex("que_ans_id")));
                objDBTestDetailData.setLvlResultHeadacheCat(cursor.getInt(cursor.getColumnIndex("lvl_result_headache_cat")));
                itemList.add(objDBTestDetailData);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllTestDetailData - No value found .");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public ArrayList<DBTestDetailData> dbReadAllTestDetailData(int tId,String pId){
        ArrayList<DBTestDetailData> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, lvl, lvl_status, que_ans_id, lvl_result_headache_cat" };
        Cursor cursor = dbConn.query("test_detail_data",
                columnArray, "t_id="+tId+" and p_id='"+pId+"'", null, null, null, "lvl DESC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBTestDetailData objDBTestDetailData = new DBTestDetailData();
                objDBTestDetailData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
                objDBTestDetailData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
                objDBTestDetailData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBTestDetailData.setLvlStatus(cursor.getString(cursor.getColumnIndex("lvl_status")));
                objDBTestDetailData.setQueAnsId(cursor.getFloat(cursor.getColumnIndex("que_ans_id")));
                objDBTestDetailData.setLvlResultHeadacheCat(cursor.getInt(cursor.getColumnIndex("lvl_result_headache_cat")));
                itemList.add(objDBTestDetailData);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllTestDetailData - No value found .");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }


    @Override
    public DBTestDetailData dbReadTestDetailData(int tId, String pId, float level1) {
        double level=level1;
        DBTestDetailData objDBTestDetailData = new DBTestDetailData();
        String[] columnArray = { "t_id, p_id, lvl, lvl_status, que_ans_id, lvl_result_headache_cat" };
        Cursor cursor = dbConn.query("test_detail_data",
                columnArray, "t_id="+tId+" and p_id='"+pId+"' and lvl="+level, null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBTestDetailData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
            objDBTestDetailData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
            objDBTestDetailData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
            objDBTestDetailData.setLvlStatus(cursor.getString(cursor.getColumnIndex("lvl_status")));
            objDBTestDetailData.setQueAnsId(cursor.getFloat(cursor.getColumnIndex("que_ans_id")));
            objDBTestDetailData.setLvlResultHeadacheCat(cursor.getInt(cursor.getColumnIndex("lvl_result_headache_cat")));
        } else {
            Log.e(TAG,"dbReadTestDetailData - No value found for t_id="+tId+" and p_id="+pId+" and lvl="+level);
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBTestDetailData;
    }

    @Override
    public boolean dbAddTestDetailData(DBTestDetailData objDBTestDetailData) {
        boolean insertFlag = false;
        long result = dbConn.insert("test_detail_data", null,
                this.getContentValues(objDBTestDetailData));
        if(result != -1){// result returns row_id of newly inserted row
            insertFlag= true;
        }else{
            Log.e(TAG,"dbAddTestDetailData - Insert failed for t_id="+objDBTestDetailData.gettId()+", p_id="+objDBTestDetailData.getpId()+", lvl="+objDBTestDetailData.getLvl() );
        }
        return insertFlag;
    }

    @Override
    public boolean dbUpdateTestDetailData(DBTestDetailData objDBTestDetailData) {
        boolean updateFlag = false;
        String whereClause = "t_id = ? and p_id = ? and lvl = ? ";
        String[] whereArgs = { String.valueOf(objDBTestDetailData.gettId()),objDBTestDetailData.getpId(),String.valueOf((double)objDBTestDetailData.getLvl()) };
        int result = dbConn.update("test_detail_data",
                this.getContentValues(objDBTestDetailData), whereClause, whereArgs);
        if(result == 1){//result returns the number of rows affected
            updateFlag= true;
        }else{
            Log.e(TAG,"dbUpdateDBTestDetailData - Update failed for t_id="+objDBTestDetailData.gettId()+", p_id="+objDBTestDetailData.getpId()+", lvl="+objDBTestDetailData.getLvl() );
        }
        return updateFlag;
    }

    @Override
    public boolean dbDeleteTestDetailData(int tId, String pId, float lvl) {
        boolean delFlag = false;
        String whereClause = "t_id = ? and p_id = ? and lvl = ?";
        String[] whereArgs = { String.valueOf(tId), pId, String.valueOf((double)lvl) };
        int result = dbConn.delete("test_detail_data", whereClause, whereArgs);
        if(result == 1){//result returns the number of rows affected
            delFlag= true;
        }else{
            Log.e(TAG,"dbDeleteTestDetailData - Delete failed for t_id="+tId+" and p_id="+pId+" and lvl="+lvl );
        }
        return delFlag;
    }


    @Override
    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData() {
        ArrayList<DBPatientResponseData> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, lvl, que_group_id, que_id,response_yes_no,response_str " };
        Cursor cursor = dbConn.query("patient_response_data",
                columnArray, null, null, null, null, "p_id, t_id, lvl, que_group_id, que_id ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBPatientResponseData objDBPatientResponseData = new DBPatientResponseData();
                objDBPatientResponseData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
                objDBPatientResponseData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
                objDBPatientResponseData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBPatientResponseData.setQueGroupId(cursor.getInt(cursor.getColumnIndex("que_group_id")));
                objDBPatientResponseData.setQueId(cursor.getFloat(cursor.getColumnIndex("que_id")));
                objDBPatientResponseData.setResponseYesNo(cursor.getInt(cursor.getColumnIndex("response_yes_no")));
                objDBPatientResponseData.setResponseStr(cursor.getString(cursor.getColumnIndex("response_str")));

                itemList.add(objDBPatientResponseData);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllPatientResponseData - No value found .");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData(String pId) {
        ArrayList<DBPatientResponseData> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, lvl, que_group_id, que_id,response_yes_no,response_str " };
        Cursor cursor = dbConn.query("patient_response_data",
                columnArray, "p_id='"+pId+"' ", null, null, null, "t_id,lvl,que_group_id,que_id ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBPatientResponseData objDBPatientResponseData = new DBPatientResponseData();
                objDBPatientResponseData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
                objDBPatientResponseData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
                objDBPatientResponseData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBPatientResponseData.setQueGroupId(cursor.getInt(cursor.getColumnIndex("que_group_id")));
                objDBPatientResponseData.setQueId(cursor.getFloat(cursor.getColumnIndex("que_id")));
                objDBPatientResponseData.setResponseYesNo(cursor.getInt(cursor.getColumnIndex("response_yes_no")));
                objDBPatientResponseData.setResponseStr(cursor.getString(cursor.getColumnIndex("response_str")));

                itemList.add(objDBPatientResponseData);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"For  PID:"+pId+", dbReadAllPatientResponseData - No value found .");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData(int tId, String pId) {
        ArrayList<DBPatientResponseData> itemList = new ArrayList();
        String[] columnArray = { "t_id, p_id, lvl, que_group_id, que_id,response_yes_no,response_str " };
        Cursor cursor = dbConn.query("patient_response_data",
                columnArray, "t_id="+tId+" and p_id='"+pId+"' ", null, null, null, "lvl,que_group_id,que_id ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBPatientResponseData objDBPatientResponseData = new DBPatientResponseData();
                objDBPatientResponseData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
                objDBPatientResponseData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
                objDBPatientResponseData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBPatientResponseData.setQueGroupId(cursor.getInt(cursor.getColumnIndex("que_group_id")));
                objDBPatientResponseData.setQueId(cursor.getFloat(cursor.getColumnIndex("que_id")));
                objDBPatientResponseData.setResponseYesNo(cursor.getInt(cursor.getColumnIndex("response_yes_no")));
                objDBPatientResponseData.setResponseStr(cursor.getString(cursor.getColumnIndex("response_str")));

                itemList.add(objDBPatientResponseData);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"For TID:"+tId+" and PID:"+pId+", dbReadAllPatientResponseData - No value found .");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public DBPatientResponseData dbReadPatientResponseData(int tId, String pId, float level1, float queId1) {
        double lvl=level1;
        double queId=queId1;
        DBPatientResponseData objDBPatientResponseData=new DBPatientResponseData();
        String[] columnArray = { "t_id, p_id, lvl, que_group_id, que_id,response_yes_no,response_str " };
        Cursor cursor = dbConn.query("patient_response_data",
                columnArray, "t_id="+tId+" and p_id='"+pId+"' and lvl="+lvl+" and que_id="+queId, null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBPatientResponseData.settId(cursor.getInt(cursor.getColumnIndex("t_id")));
            objDBPatientResponseData.setpId(cursor.getString(cursor.getColumnIndex("p_id")));
            objDBPatientResponseData.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
            objDBPatientResponseData.setQueGroupId(cursor.getInt(cursor.getColumnIndex("que_group_id")));
            objDBPatientResponseData.setQueId(cursor.getFloat(cursor.getColumnIndex("que_id")));
            objDBPatientResponseData.setResponseYesNo(cursor.getInt(cursor.getColumnIndex("response_yes_no")));
            objDBPatientResponseData.setResponseStr(cursor.getString(cursor.getColumnIndex("response_str")));
        } else {
            Log.e(TAG,"dbReadPatientResponseData - No value found for p_id="+pId+" and t_id="+tId+" and lvl="+lvl+" and que_id="+queId);
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBPatientResponseData;
    }

    @Override
    public boolean dbAddPatientResponseData(DBPatientResponseData objDBPatientResponseData) {
        boolean insertFlag = false;
        long result = dbConn.insert("patient_response_data", null,
                this.getContentValues(objDBPatientResponseData));
        if(result != -1){// result returns row_id of newly inserted row
            insertFlag= true;
            Log.i(TAG,"dbAddPatientResponseData - Insert success for p_id="+objDBPatientResponseData.getpId()+", t_id="+objDBPatientResponseData.gettId()+", lvl="+objDBPatientResponseData.getLvl()+", que_id:"+objDBPatientResponseData.getQueId() );

        }else{
            Log.e(TAG,"dbAddPatientResponseData - Insert failed for p_id="+objDBPatientResponseData.getpId()+", t_id="+objDBPatientResponseData.gettId()+", lvl="+objDBPatientResponseData.getLvl()+", que_id:"+objDBPatientResponseData.getQueId() );
        }
        return insertFlag;
    }

    @Override
    public boolean dbUpdatePatientResponseData(DBPatientResponseData objDBPatientResponseData) {
        boolean updateFlag = false;
        String whereClause = "t_id = ? and p_id = ? and lvl = ? and que_id = ?";
        String[] whereArgs = { String.valueOf(objDBPatientResponseData.gettId()),objDBPatientResponseData.getpId(),String.valueOf((double)objDBPatientResponseData.getLvl()),String.valueOf((double)objDBPatientResponseData.getQueId()) };
        int result = dbConn.update("patient_response_data",
                this.getContentValues(objDBPatientResponseData), whereClause, whereArgs);
        if(result == 1){//result returns the number of rows affected
            updateFlag= true;
        }else{
            Log.e(TAG,"dbUpdatePatientResponseData - Update failed for p_id="+objDBPatientResponseData.getpId()+", t_id="+objDBPatientResponseData.gettId()+", lvl="+objDBPatientResponseData.getLvl()+", que_id:"+ objDBPatientResponseData.getQueId());
        }
        return updateFlag;
    }

    @Override
    public boolean dbDeletePatientResponseData(int tId, String pId, float lvl, float queId) {
        boolean delFlag = false;
        String whereClause = "t_id = ? and p_id = ? and lvl = ? and que_id = ?";
        String[] whereArgs = { String.valueOf(tId), pId,String.valueOf((double)lvl), String.valueOf((double)queId) };
        int result = dbConn.delete("patient_response_data", whereClause, whereArgs);
        if(result == 1){//result returns the number of rows affected
            delFlag= true;
        }else{
            Log.e(TAG,"dbDeletePatientResponseData - Delete failed for t_id="+tId+" and lvl="+lvl+ " and que_id" +queId+" and p_id ="+pId);
        }
        return delFlag;
    }

    @Override
    public boolean dbDeletePatientResponseData(int tId, String pId, float lvl) {
        boolean delFlag = false;
        String whereClause = "t_id = ? and p_id = ? and lvl = ? ";
        String[] whereArgs = { String.valueOf(tId), pId, String.valueOf((double)lvl) };
        int result = dbConn.delete("patient_response_data", whereClause, whereArgs);
        if (result >= 1) {//result returns the number of rows affected
               delFlag = true;
           } else {
               Log.e(TAG, "result:"+result+" dbDeletePatientResponseData - Delete failed for t_id=" + tId + " and lvl=" + lvl+" and p_id ="+pId);
           }

        return delFlag;
    }



    @Override
    public ArrayList<DBMultiBranchLogicInfo> dbReadAllMultiBranchLogicInfo() {
        ArrayList<DBMultiBranchLogicInfo> itemList = new ArrayList();
        String[] columnArray = { "lvl,que_group_id, min_yes_response, next_que_group_id,headache_cat" };
        Cursor cursor = dbConn.query("multi_branch_logic_info",
                columnArray, null, null, null, null, "lvl,que_group_id ASC", null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                DBMultiBranchLogicInfo objDBMultiBranchLogicInfo = new DBMultiBranchLogicInfo();
                objDBMultiBranchLogicInfo.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
                objDBMultiBranchLogicInfo.setQueGroupId(cursor.getInt(cursor.getColumnIndex("que_group_id")));
                objDBMultiBranchLogicInfo.setMinYesResponse(cursor.getInt(cursor.getColumnIndex("min_yes_response")));
                objDBMultiBranchLogicInfo.setNextQueGroupId(cursor.getInt(cursor.getColumnIndex("next_que_group_id")));
                objDBMultiBranchLogicInfo.setHeadacheCat(cursor.getInt(cursor.getColumnIndex("headache_cat")));
                itemList.add(objDBMultiBranchLogicInfo);
            } while (cursor.moveToNext());
        } else {
            Log.e(TAG,"dbReadAllMultiBranchLogicInfo - No value found");
        }
        if(cursor!=null) {
            cursor.close();
        }
        return itemList;
    }

    @Override
    public DBMultiBranchLogicInfo dbReadMultiBranchLogicInfo(float level1, int queGroupId, int minYesResponse) {
        double lvl=level1;
        DBMultiBranchLogicInfo objDBMultiBranchLogicInfo = new DBMultiBranchLogicInfo();
        String[] columnArray = { "lvl,que_group_id, min_yes_response, next_que_group_id,headache_cat" };
        Cursor cursor = dbConn.query("multi_branch_logic_info",
                columnArray, "lvl="+lvl+" and que_id="+queGroupId+" and min_yes_response="+minYesResponse, null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBMultiBranchLogicInfo.setLvl(cursor.getFloat(cursor.getColumnIndex("lvl")));
            objDBMultiBranchLogicInfo.setQueGroupId(cursor.getInt(cursor.getColumnIndex("que_group_id")));
            objDBMultiBranchLogicInfo.setMinYesResponse(cursor.getInt(cursor.getColumnIndex("min_yes_response")));
            objDBMultiBranchLogicInfo.setNextQueGroupId(cursor.getInt(cursor.getColumnIndex("next_que_group_id")));
            objDBMultiBranchLogicInfo.setHeadacheCat(cursor.getInt(cursor.getColumnIndex("headache_cat")));
        } else {
            Log.e(TAG,"dbReadMultiBranchLogicInfo - No value found for lvl="+lvl+" and que_id="+queGroupId+" and min_yes_response="+minYesResponse);
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBMultiBranchLogicInfo;
    }

    @Override
    public DBPatientDetailInfo dbReadPatientDetailInfo(String pId) {
        DBPatientDetailInfo objDBPatientDetailInfo = new DBPatientDetailInfo();
        String[] columnArray = { "p_id, pwd, name, dob, gender, address, mobile_no, adhaar_no, email, health_history, image" };
        Cursor cursor = dbConn.query("patient_detail_info",
                columnArray, "p_id='"+pId+"'", null, null, null, null, null);

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            objDBPatientDetailInfo.setPid(cursor.getString(cursor.getColumnIndex("p_id")));
            objDBPatientDetailInfo.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
            objDBPatientDetailInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            objDBPatientDetailInfo.setDob(cursor.getString(cursor.getColumnIndex("dob")));
            objDBPatientDetailInfo.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            objDBPatientDetailInfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            objDBPatientDetailInfo.setMobileNo(cursor.getLong(cursor.getColumnIndex("mobile_no")));
            objDBPatientDetailInfo.setAdhaarNo(cursor.getLong(cursor.getColumnIndex("adhaar_no")));
            objDBPatientDetailInfo.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            objDBPatientDetailInfo.setHealthHistory(cursor.getString(cursor.getColumnIndex("health_history")));
            objDBPatientDetailInfo.setImage(cursor.getString(cursor.getColumnIndex("image")));


        } else {
            Log.e(TAG,"dbReadPatientDetailInfo - No value found for p_id="+pId);
        }
        if(cursor!=null) {
            cursor.close();
        }
        return objDBPatientDetailInfo;
    }

    @Override
    public boolean dbAddPatientDetailInfo(DBPatientDetailInfo objDBPatientDetailInfo) {
        boolean insertFlag = false;
        long result = dbConn.insert("patient_detail_info", null,
                this.getContentValues(objDBPatientDetailInfo));
        if(result != -1){// result returns row_id of newly inserted row
            insertFlag= true;
        }else{
            Log.e(TAG,"dbAddPatientDetailInfo - Insert failed for p_id="+objDBPatientDetailInfo.getPid());
        }
        return insertFlag;
    }



    private ContentValues getContentValues(Object object) {
        ContentValues contentValues = new ContentValues();
        if(object instanceof DBPatientTestBasicInfo) {
            DBPatientTestBasicInfo table = (DBPatientTestBasicInfo) object;
            contentValues.put("t_id", table.gettId());
            contentValues.put("p_id", table.getpId());
            contentValues.put("dr_id", table.getDrId());
            contentValues.put("test_st_date", table.getTestStDate());
            contentValues.put("test_end_date", table.getTestEndDate());
            contentValues.put("is_test_completed", table.getIsTestCompleted());
        }else  if(object instanceof DBTestDetailData) {
            DBTestDetailData table = (DBTestDetailData) object;
            contentValues.put("t_id", table.gettId());
            contentValues.put("p_id", table.getpId());
            contentValues.put("lvl", table.getLvl());
            contentValues.put("lvl_status", table.getLvlStatus());
            contentValues.put("que_ans_id", table.getQueAnsId());
            contentValues.put("lvl_result_headache_cat", table.getLvlResultHeadacheCat());
        }else  if(object instanceof DBPatientResponseData) {
            DBPatientResponseData table = (DBPatientResponseData) object;
            contentValues.put("t_id", table.gettId());
            contentValues.put("p_id", table.getpId());
            contentValues.put("lvl", table.getLvl());
            contentValues.put("que_group_id", table.getQueGroupId());
            contentValues.put("que_id", table.getQueId());
            contentValues.put("response_yes_no", table.getResponseYesNo());
            contentValues.put("response_str", table.getResponseStr());
        }else if(object instanceof DBPatientDetailInfo) {
            DBPatientDetailInfo table = (DBPatientDetailInfo) object;
            contentValues.put("p_id", table.getPid());
            contentValues.put("pwd", table.getPwd());
            contentValues.put("name", table.getName());
            contentValues.put("dob", table.getDob());
            contentValues.put("gender", table.getGender());
            contentValues.put("address", table.getAddress());
            contentValues.put("mobile_no", table.getMobileNo());
            contentValues.put("adhaar_no", table.getAdhaarNo());
            contentValues.put("email", table.getEmail());
            contentValues.put("health_history", table.getHealthHistory());
            contentValues.put("image", table.getImage());
        }
        return contentValues;
    }

    /**
     * Query sqlite_sequence table and search for the AUTOINCREMENT value for <code>tableName</code>
     * @param tableName The table name with which the AUTOINCREMENT value is associated.
     * @return The next AUTOINCREMENT value for <code>tableName</code>
     * If an INSERT call was not previously executed on <code>tableName</code>, the value 1 will
     * be returned. Otherwise, the returned value will be the next AUTOINCREMENT.
     */
    private long getNextAutoIncrement(String tableName) {
        /*
         * From the docs: SQLite keeps track of the largest ROWID using an internal table named "sqlite_sequence".
         * The sqlite_sequence table is created and initialized automatically
         * whenever a normal table that contains an INTEGER PRIMARY KEY AUTOINCREMENT column is created.
         *
         * Seq can be reset by any 'value' : UPDATE SQLITE_SEQUENCE SET SEQ= 'value' WHERE NAME='table_name';
         */
        String[] columns = {"seq"};
        String selection = "name=?";
        String[] selectionArgs = { tableName };

        Cursor cursor = dbConn.query("sqlite_sequence",
                columns, selection, selectionArgs, null, null, null);
        long autoIncrement = 0;
        if (cursor.moveToFirst()) {
            int indexSeq = cursor.getColumnIndex(columns[0]);
            autoIncrement = cursor.getLong(indexSeq);
        }
        if(cursor!=null) {
            cursor.close();
        }
        return autoIncrement + 1;
    }

}
