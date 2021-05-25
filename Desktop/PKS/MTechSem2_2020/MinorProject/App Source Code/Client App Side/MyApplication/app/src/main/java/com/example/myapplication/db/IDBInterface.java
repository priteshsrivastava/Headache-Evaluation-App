package com.example.myapplication.db;

import java.util.ArrayList;

public interface IDBInterface {

    public ArrayList<DBBranchingLogicInfo> dbReadAllBranchingLogicInfo();
    public DBBranchingLogicInfo dbReadBranchingLogicInfo(float lvl, float queId);

    public ArrayList<DBAnswerInfo> dbReadAllAnswerInfo();
    public DBAnswerInfo dbReadAnswerInfo(float lvl, float ansId);

    public ArrayList<DBHeadacheCatInfo> dbReadAllHeadacheCatInfo();
    public DBHeadacheCatInfo dbReadHeadacheCatInfo(int headacheCat);

    public ArrayList<DBPatientTestBasicInfo> dbReadAllPatientTestBasicInfo();
    public ArrayList<DBPatientTestBasicInfo> dbReadAllPatientTestBasicInfo(String pId);
    public DBPatientTestBasicInfo dbReadPatientTestBasicInfo(int tId,String pId);
    public boolean dbAddPatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo);
    public boolean dbUpdatePatientTestBasicInfo(DBPatientTestBasicInfo objDBPatientTestBasicInfo);
    public boolean dbDeletePatientTestBasicInfo(int tId, String pId);

    public ArrayList<DBTestDetailData> dbReadAllTestDetailData();
    public ArrayList<DBTestDetailData> dbReadAllTestDetailData(String pId);
    public ArrayList<DBTestDetailData> dbReadAllTestDetailData(int tId,String pId);
    public DBTestDetailData dbReadTestDetailData(int tId, String pId,float lvl);
    public boolean dbAddTestDetailData(DBTestDetailData objDBTestDetailData);
    public boolean dbUpdateTestDetailData(DBTestDetailData objDBTestDetailData);
    public boolean dbDeleteTestDetailData(int tId, String pId, float lvl);

    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData();
    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData(String pId);
    public ArrayList<DBPatientResponseData> dbReadAllPatientResponseData(int tId,String pId);
    public DBPatientResponseData dbReadPatientResponseData(int tId, String pId, float lvl, float queId);
    public boolean dbAddPatientResponseData(DBPatientResponseData objDBPatientResponseData);
    public boolean dbUpdatePatientResponseData(DBPatientResponseData objDBPatientResponseData);
    public boolean dbDeletePatientResponseData(int tId, String pId, float lvl, float queId);
    public boolean dbDeletePatientResponseData(int tId, String pId, float lvl);

    public ArrayList<DBMultiBranchLogicInfo> dbReadAllMultiBranchLogicInfo();
    public DBMultiBranchLogicInfo dbReadMultiBranchLogicInfo(float lvl, int queGroupId, int minYesResponse);

    public DBPatientDetailInfo dbReadPatientDetailInfo(String pId);
    public boolean dbAddPatientDetailInfo(DBPatientDetailInfo objDBPatientDetailInfo);

}
