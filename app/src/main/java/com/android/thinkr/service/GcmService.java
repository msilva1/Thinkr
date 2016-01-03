package com.android.thinkr.service;

import com.android.thinkr.gcm.GcmMessage;

import retrofit.http.Headers;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by ryanclinton on 1/3/16.
 */
public interface GcmService {

    @Headers({
        "Content-Type: application/json",
            "Accept: text/plain",
            "Authorization: key=AIzaSyAgq5KeAYbBTrAGRO3zIWnGooIbVSKVdVk"
    })
    @POST("/gcm/send")
    Call<Void> notifyHomeworkDone(@Body GcmMessage message);

}
