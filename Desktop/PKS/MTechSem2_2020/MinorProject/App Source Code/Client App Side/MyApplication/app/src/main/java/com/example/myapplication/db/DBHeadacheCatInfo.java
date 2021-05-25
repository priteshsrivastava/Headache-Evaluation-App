package com.example.myapplication.db;

import java.io.Serializable;

public class DBHeadacheCatInfo implements Serializable {
    int headacheCat;
    String catStrEn;
    String catStrHi;

    public DBHeadacheCatInfo() {
        this.headacheCat = 0;
        this.catStrEn = "";
        this.catStrHi = "";
    }

    public int getHeadacheCat() {
        return headacheCat;
    }

    public void setHeadacheCat(int headacheCat) {
        this.headacheCat = headacheCat;
    }

    public String getCatStrEn() {
        return catStrEn;
    }

    public void setCatStrEn(String catStrEn) {
        this.catStrEn = catStrEn;
    }

    public String getCatStrHi() {
        return catStrHi;
    }

    public void setCatStrHi(String catStrHi) {
        this.catStrHi = catStrHi;
    }
}
