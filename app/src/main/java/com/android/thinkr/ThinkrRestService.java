package com.android.thinkr;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by M. Silva on 1/2/16.
 */
public class ThinkrRestService {
    private static ThinkrService thinkrService;

    public static ThinkrService getService() {
        if (thinkrService != null) return thinkrService;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thinkr.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        thinkrService = retrofit.create(ThinkrService.class);

        return thinkrService;
    }
}
