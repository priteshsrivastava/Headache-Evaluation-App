package com.example.myapplication.server;

import com.example.myapplication.db.DBPatientDetailInfo;
import com.example.myapplication.db.DBPatientResponseData;
import com.example.myapplication.db.DBPatientTestBasicInfo;
import com.example.myapplication.db.DBTestDetailData;

import java.util.ArrayList;

/**
* @author: PKS
* This interface (DBWebServAPI) file have signatures of all Database API for the Android client application.
* It also  contain web-server configuration.
 */
public interface DBWebServAPI {
    //http://localhost/DBAPI.php?apicall=dbReadAllPatientTestBasicInfo
    static final String WEB_SERVER_IP="192.168.43.152:8080";//ip when airtel mobile net on
    //static final String WEB_SERVER_IP="172.20.10.5";//ip when vodafone mobile net on
    //static final String WEB_SERVER_IP="192.168.1.101";//ip when mtnl net on

    //static final String WEB_SERVER_IP="hema.cloudns.asia:8080";

    static final String ROOT_URL = "http://"+WEB_SERVER_IP+"/DBAPI.php?apicall=";

    public static final String URL_READALL_PATIENT_TEST_BASIC_INFO = ROOT_URL + "dbReadAllPatientTestBasicInfo";
    public static final String URL_ADD_PATIENT_TEST_BASIC_INFO = ROOT_URL + "dbAddPatientTestBasicInfo";
    public static final String URL_ADDALL_PATIENT_TEST_BASIC_INFO = ROOT_URL + "dbAddAllPatientTestBasicInfo";
    public static final String URL_UPDATE_PATIENT_TEST_BASIC_INFO = ROOT_URL + "dbUpdatePatientTestBasicInfo";
    public static final String URL_DELETE_PATIENT_TEST_BASIC_INFO = ROOT_URL + "dbDeletePatientTestBasicInfo";
    public static final String URL_DELETEALL_PATIENT_TEST_BASIC_INFO = ROOT_URL + "dbDeleteAllPatientTestBasicInfo";

    public static final String URL_READALL_TEST_DETAIL_DATA = ROOT_URL + "dbReadAllTestDetailData";
    public static final String URL_ADDALL_TEST_DETAIL_DATA = ROOT_URL + "dbAddAllTestDetailData";
    public static final String URL_DELETEALL_TEST_DETAIL_DATA = ROOT_URL + "dbDeleteAllTestDetailData";

    public static final String URL_READALL_PATIENT_RESPONSE_DATA = ROOT_URL + "dbReadAllPatientResponseData";
    public static final String URL_ADDALL_PATIENT_RESPONSE_DATA = ROOT_URL + "dbAddAllPatientResponseData";
    public static final String URL_DELETEALL_PATIENT_RESPONSE_DATA = ROOT_URL + "dbDeleteAllPatientResponseData";

    public static final String URL_READ_PATIENT_DETAIL_INFO = ROOT_URL + "dbReadPatientDetailInfo";
    public static final String URL_ADD_PATIENT_DETAIL_INFO = ROOT_URL + "dbAddPatientDetailInfo";

     static final int CODE_GET_REQUEST = 1024;
     static final int CODE_POST_REQUEST = 1025;
    static final int CODE_POST_ARRAYLIST_REQUEST = 1026;//to handle ADDALL

    public ArrayList<DBPatientTestBasicInfo> dbReadAllPatientTestBasicInfo();
    //public ArrayList<DBPatientTestBasicInfo> dbReadAllPatientTestBasicInfo(String pId);
    //public DBPatientTestBasicInfo dbReadPatientTestBasicInfo(int tId,String pId);
    public boolean dbAddPatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo);
    public boolean dbAddAllPatientTestBasicInfo(ArrayList<DBPatientTestBasicInfo> objDBPatientTestBasicInfoList);
    public boolean dbUpdatePatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo);
    public boolean dbDeletePatientTestBasicInfo(int tId, String pId);
    public boolean dbDeleteAllPatientTestBasicInfo(String pId);

    public ArrayList<DBTestDetailData> dbReadAllTestDetailData();
    public boolean dbAddAllTestDetailData(ArrayList<DBTestDetailData> objDBTestDetailDataList);
    public boolean dbDeleteAllTestDetailData(String pId);

    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData();
    public boolean dbAddAllPatientResponseData(ArrayList<DBPatientResponseData> objDBPatientResponseDataList);
    public boolean dbDeleteAllPatientResponseData(String pId);

    public DBPatientDetailInfo dbReadPatientDetailInfo(String pId);
    public boolean dbAddPatientDetailInfo(DBPatientDetailInfo objDBPatientDetailInfo);

}
