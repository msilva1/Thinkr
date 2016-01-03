package com.android.thinkr.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class CookieJarReciever extends GcmReceiver {
    private GoogleCloudMessaging gcm;
    private String token;
    private Activity theActivity;
    private String PROJECT_NUMBER = "830091460192";
    private String gcmIdMsg;

    public CookieJarReciever(Activity activity) {
        theActivity = activity;
    }

    public void acquireRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                String authorizedEntity = PROJECT_NUMBER; // Project id from Google Developer Console
                String scope = "GCM"; // e.g. communicating using GCM, but you can use any
                // URL-safe characters up to a maximum of 1000, or
                // you can also leave it blank.

                String msg = "";
                try {
                    Log.i("GCM", "Getting token...");
                    token = InstanceID.getInstance(theActivity.getApplicationContext()).getToken(authorizedEntity,scope);

                    msg = "Device registered, registration ID=" + token;

                    Log.i("GCM", "Got token...");
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.i("GCM", "Couldn't get token...");
                }

                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                gcmIdMsg = msg;
            }
        }.execute(null, null, null);
    }

    public String getToken(){
        return token;
    }
    public String getGcmIdMsg(){
        return gcmIdMsg;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the GcmReveiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
