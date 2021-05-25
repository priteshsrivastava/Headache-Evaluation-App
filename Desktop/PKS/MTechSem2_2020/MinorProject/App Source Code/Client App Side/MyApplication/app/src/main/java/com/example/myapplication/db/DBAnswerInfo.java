package com.example.myapplication.db;

import java.io.Serializable;

public class DBAnswerInfo implements Serializable {
    float lvl;
    float ansId;
    int headacheCat;

    public DBAnswerInfo() {
        this.lvl = 0;
        this.ansId = 0;
        this.headacheCat = 0;
    }

    public float getLvl() {
        return lvl;
    }

    public void setLvl(float lvl) {
        this.lvl = lvl;
    }

    public float getAnsId() {
        return ansId;
    }

    public void setAnsId(float ansId) {
        this.ansId = ansId;
    }

    public int getHeadacheCat() {
        return headacheCat;
    }

    public void setHeadacheCat(int headacheCat) {
        this.headacheCat = headacheCat;
    }
}
