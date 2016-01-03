package com.android.thinkr.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class CookieJarReciever extends GcmReceiver {
    private GoogleCloudMessaging gcm;
    private Activity theActivity;
    private String regid;
    private String PROJECT_NUMBER = "";
    private String gcmIdMsg;

    public CookieJarReciever(Activity activity) {
        theActivity = activity;
    }

    public void acquireRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(theActivity.getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                gcmIdMsg = msg;
            }
        }.execute(null, null, null);
    }

    public String getId(){
        return regid;
    }
    public String getGcmIdMsg(){
        return gcmIdMsg;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
