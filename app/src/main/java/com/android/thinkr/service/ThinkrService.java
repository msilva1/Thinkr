package com.android.thinkr.service;

import com.bytes.hack.model.Assignment;
import com.bytes.hack.model.AssignmentList;
import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
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


    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("/WebServices/account/logout/{id}/{password}")
    Call<Account> logOut(@Path("id") String id, @Path("password") String password);


    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("/WebServices/account/create")
    Call<Account> signUp(@Body User user);


    // ======================================================================================
    // Assignment Services
    // ======================================================================================
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("/WebServices/assignment/create/{uid}")
    Call<Assignment> createAssignment(@Path("uid") String userId, @Body Assignment assignment);


    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("/WebServices/assignment/find/{uid}")
    Call<AssignmentList> getAssignmentList(@Path("uid") String userId);


    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("/WebServices/assignment/assgin/{uid}/{aid}")
    Call<Assignment> assign(@Path("uid") String userId, @Path("aid") String assignmentId);

}
