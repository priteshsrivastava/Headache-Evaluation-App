package com.example.myapplication.db;

public class DBPatientResponseData {
    int tId;
    String pId;
    float lvl;
    int queGroupId;
    float queId;
    int responseYesNo;
    String responseStr ;

    public DBPatientResponseData() {
        this.tId = 0;
        this.pId = "";
        this.lvl = 0;
        this.queGroupId = 0;
        this.queId = 0;
        this.responseYesNo = 0;
        this.responseStr = "";
    }

    public DBPatientResponseData(int tId, String pId, float lvl, int queGroupId, float queId, int responseYesNo, String responseStr) {
        this.tId = tId;
        this.pId = pId;
        this.lvl = lvl;
        this.queGroupId = queGroupId;
        this.queId = queId;
        this.responseYesNo = responseYesNo;
        this.responseStr = responseStr;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
    public float getLvl() {
        return lvl;
    }

    public void setLvl(float lvl) {
        this.lvl = lvl;
    }

    public int getQueGroupId() {
        return queGroupId;
    }

    public void setQueGroupId(int queGroupId) {
        this.queGroupId = queGroupId;
    }

    public float getQueId() {
        return queId;
    }



    public void setQueId(float queId) {
        this.queId = queId;
    }

    public int getResponseYesNo() {
        return responseYesNo;
    }

    public void setResponseYesNo(int responseYesNo) {
        this.responseYesNo = responseYesNo;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

}
