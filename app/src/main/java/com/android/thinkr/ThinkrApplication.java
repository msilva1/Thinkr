package com.android.thinkr;

import android.app.Application;
import android.content.Context;

import com.bytes.hack.model.account.User;

/**
 * Created by M. Silva on 1/2/16.
 */
public class ThinkrApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
