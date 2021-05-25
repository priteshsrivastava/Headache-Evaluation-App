package com.example.myapplication.db;

import java.io.Serializable;

public class DBTestDetailData implements Serializable {
    int tId;
    String pId;
    float lvl;
    String lvlStatus;
    float queAnsId;
    int lvlResultHeadacheCat;

    public DBTestDetailData() {
        this.tId = 0;
        this.pId = "";
        this.lvl = 0;
        this.lvlStatus = "";
        this.queAnsId = 0;
        this.lvlResultHeadacheCat=0;
    }

    public DBTestDetailData(int tId, String pId,float lvl, String lvlStatus, float queAnsId, int lvlResultHeadacheCat) {
        this.tId = tId;
        this.pId = pId;
        this.lvl = lvl;
        this.lvlStatus = lvlStatus;
        this.queAnsId = queAnsId;
        this.lvlResultHeadacheCat=lvlResultHeadacheCat;
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

    public String getLvlStatus() {
        return lvlStatus;
    }

    public void setLvlStatus(String lvlStatus) {
        this.lvlStatus = lvlStatus;
    }

    public float getQueAnsId() {
        return queAnsId;
    }

    public void setQueAnsId(float queAnsId) {
        this.queAnsId = queAnsId;
    }

    public int getLvlResultHeadacheCat() {
        return lvlResultHeadacheCat;
    }

    public void setLvlResultHeadacheCat(int lvlResultHeadacheCat) {
        this.lvlResultHeadacheCat = lvlResultHeadacheCat;
    }


}
