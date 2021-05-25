package com.example.myapplication.server;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.myapplication.server.DBWebServAPI;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
    Context context;
    ProgressDialog p;

    //the url where we need to send the request
    String url;

    //the parameters
    HashMap<String, String> params;
    ArrayList< HashMap<String, String> > paramsList;

    //the request code to define whether it is a GET or POST
    int requestCode;

    //require to handle response from background async processes
    private AsyncResponse listenerAsyncResponse;

    //constructor to initialize values(applicable where response not to be handled)
    PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    //Used in Add/Del/Update/ReadAll
    PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode,AsyncResponse listener,Context context) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
        this.listenerAsyncResponse=listener;
        this.context=context;

        p = new ProgressDialog(context);
        p.setMessage("Please wait...connecting with Server!!");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
    }
    //used in AddAll
    PerformNetworkRequest(String url, ArrayList< HashMap<String, String> > paramsList, int requestCode,AsyncResponse listener,Context context) {
        this.url = url;
        this.paramsList = paramsList;
        this.requestCode = requestCode;
        this.listenerAsyncResponse=listener;
        this.context=context;

        p = new ProgressDialog(context);
        p.setMessage("Please wait...Sync with Server!!!");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
    }

    //when the task started displaying a progressbar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressBar.setVisibility(View.VISIBLE);

//        p = new ProgressDialog(context);
//        p.setMessage("Please wait...Reading Data from Server");
//        p.setIndeterminate(false);
//        p.setCancelable(false);
//        p.show();
    }


    //this method will give the response from the request
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (!isCancelled()) {
            if (listenerAsyncResponse != null) {
                listenerAsyncResponse.processFinish(url,result,p);
            }else{
                Log.e("PerformNetworkRequest","listener is null.");

            }
        }
    }

    //the network operation will be performed in background
    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == DBWebServAPI.CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == DBWebServAPI.CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == DBWebServAPI.CODE_POST_ARRAYLIST_REQUEST)
            return requestHandler.sendPostArrayListRequest(url, paramsList);

        return null;
    }

}
