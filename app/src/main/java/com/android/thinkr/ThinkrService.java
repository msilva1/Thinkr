package com.android.thinkr;

import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by M. Silva on 1/2/16.
 */
public interface ThinkrService {

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("/WebServices/account/login/{id}/{password}")
    Call<Account> logIn(@Path("id") String id, @Path("password") String password);

    @POST("/WebServices/account/create")
    Call<User> signUp(@Body User user);
}
