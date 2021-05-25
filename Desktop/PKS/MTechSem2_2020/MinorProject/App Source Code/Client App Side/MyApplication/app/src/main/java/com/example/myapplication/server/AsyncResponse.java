package com.example.myapplication.server;

import android.app.ProgressDialog;

interface AsyncResponse {
    void processFinish(String url,String output, ProgressDialog p);
}
