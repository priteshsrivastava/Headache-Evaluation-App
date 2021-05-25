package com.example.myapplication.db;

import java.io.Serializable;

public class DBMultiBranchLogicInfo implements Serializable {
    float lvl;
    int queGroupId;
     int minYesResponse;
     int nextQueGroupId;
     int headacheCat;

    public DBMultiBranchLogicInfo() {
        this.lvl = 0;
        this.queGroupId = 0;
        this.minYesResponse = 0;
        this.nextQueGroupId = 0;
        this.headacheCat = 0;
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

    public int getMinYesResponse() {
        return minYesResponse;
    }

    public void setMinYesResponse(int minYesResponse) {
        this.minYesResponse = minYesResponse;
    }

    public int getNextQueGroupId() {
        return nextQueGroupId;
    }

    public void setNextQueGroupId(int nextQueGroupId) {
        this.nextQueGroupId = nextQueGroupId;
    }

    public int getHeadacheCat() {
        return headacheCat;
    }

    public void setHeadacheCat(int headacheCat) {
        this.headacheCat = headacheCat;
    }
}
