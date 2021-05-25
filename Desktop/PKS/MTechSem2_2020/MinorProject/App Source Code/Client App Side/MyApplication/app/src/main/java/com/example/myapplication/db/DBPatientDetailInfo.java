package com.example.myapplication.db;

import java.io.Serializable;

public class DBPatientDetailInfo implements Serializable {
    String pid;
    String pwd;
    String name;
    String dob;
    String gender;
    String address;
    long mobileNo;
    long adhaarNo;
    String email;
    String healthHistory;
    String image;

    public DBPatientDetailInfo() {
        this.pid = "";
        this.pwd = "";
        this.name = "";
        this.dob = "";
        this.gender = "";
        this.address = "";
        this.mobileNo = 0;
        this.adhaarNo = 0;
        this.email = "";
        this.healthHistory = "";
        this.image = "";
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public long getAdhaarNo() {
        return adhaarNo;
    }

    public void setAdhaarNo(long adhaarNo) {
        this.adhaarNo = adhaarNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHealthHistory() {
        return healthHistory;
    }

    public void setHealthHistory(String healthHistory) {
        this.healthHistory = healthHistory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}//end of class DBPatientDetailInfo
