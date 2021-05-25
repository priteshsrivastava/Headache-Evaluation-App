package com.example.myapplication.db;

import java.io.Serializable;

public class DBPatientTestBasicInfo implements Serializable {
    int tId;//auto incremented
    String pId;
    String drId;
    String testStDate;
    String testEndDate;
    int isTestCompleted;

    public DBPatientTestBasicInfo() {
        this.tId = 0;
        this.pId = "";
        this.drId = "";
        this.testStDate = "";
        this.testEndDate = "";
        this.isTestCompleted = 0;
    }

    public DBPatientTestBasicInfo(int tId, String pId, String drId, String testStDate, String testEndDate, int isTestCompleted) {
        this.tId = tId;
        this.pId = pId;
        this.drId = drId;
        this.testStDate = testStDate;
        this.testEndDate = testEndDate;
        this.isTestCompleted = isTestCompleted;
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

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getTestStDate() {
        return testStDate;
    }

    public void setTestStDate(String testStDate) {
        this.testStDate = testStDate;
    }

    public String getTestEndDate() {
        return testEndDate;
    }

    public void setTestEndDate(String testEndDate) {
        this.testEndDate = testEndDate;
    }

    public int getIsTestCompleted() {
        return isTestCompleted;
    }

    public void setIsTestCompleted(int isTestCompleted) {
        this.isTestCompleted = isTestCompleted;
    }
}
