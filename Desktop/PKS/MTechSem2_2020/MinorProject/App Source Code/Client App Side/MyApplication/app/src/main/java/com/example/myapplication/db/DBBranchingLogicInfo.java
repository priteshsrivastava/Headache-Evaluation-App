package com.example.myapplication.db;

import java.io.Serializable;

public class DBBranchingLogicInfo implements Serializable {
    float lvl;
    float queId;
    float ansYesId;
    float ansNoId;
    float prevQueId;
    String queStrEn;
    String queStrHi;
    String remarks;

    public DBBranchingLogicInfo() {
        this.lvl = 0;
        this.queId = 0;
        this.ansYesId = 0;
        this.ansNoId = 0;
        this.prevQueId = 0;
        this.queStrEn = "";
        this.queStrHi = "";
        this.remarks = "";
    }

    public float getLvl() {
        return lvl;
    }

    public void setLvl(float lvl) {
        this.lvl = lvl;
    }

    public float getQueId() {
        return queId;
    }

    public void setQueId(float queId) {
        this.queId = queId;
    }

    public float getAnsYesId() {
        return ansYesId;
    }

    public void setAnsYesId(float ansYesId) {
        this.ansYesId = ansYesId;
    }

    public float getAnsNoId() {
        return ansNoId;
    }

    public void setAnsNoId(float ansNoId) {
        this.ansNoId = ansNoId;
    }

    public float getPrevQueId() {
        return prevQueId;
    }

    public void setPrevQueId(float prevQueId) {
        this.prevQueId = prevQueId;
    }

    public String getQueStrEn() {
        return queStrEn;
    }

    public void setQueStrEn(String queStrEn) {
        this.queStrEn = queStrEn;
    }

    public String getQueStrHi() {
        return queStrHi;
    }

    public void setQueStrHi(String queStrHi) {
        this.queStrHi = queStrHi;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
