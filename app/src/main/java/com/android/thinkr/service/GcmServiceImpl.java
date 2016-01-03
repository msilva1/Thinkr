package com.android.thinkr.service;

import com.android.thinkr.gcm.GcmMessage;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ryanclinton on 1/3/16.
 */
public class GcmServiceImpl {
    private static  GcmService gcmService;

    public static GcmService getGcmService(){
        if( gcmService !=null ) return gcmService;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gcmService = retrofit.create(GcmService.class);

        return gcmService;
    }

}
