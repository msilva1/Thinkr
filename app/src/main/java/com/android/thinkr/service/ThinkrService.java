package com.android.thinkr.service;

import com.bytes.hack.model.Assignment;
import com.bytes.hack.model.AssignmentList;
import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.SessionList;
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

    String CONTENT_TYPE_JSON = "Content-Type: application/json";
    String ACCEPT_JSON = "Accept: application/json";

    //region Account Services
    String ACCOUNT_PATH = "/WebServices/account";
    /**
     * Logs in the specified account if the following criteria are met:
     * <ul>
     * 	<li>user exists
     * 	<li>credential is valid
     *  <li>user is currently not logged in
     * </ul>
     *
     * TODO encrypt password in transit
     *
     * @param userId the id of the user to be logged in
     * @param password the password of the user to be logged in
     * @return
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @POST(ACCOUNT_PATH + "/login/{id}/{password}")
    Call<Account> login(@Path("id") String userId, @Path("password") String password);


    /**
     * Logs out the specified account if the following criteria are met:
     * <ul>
     * 	<li>user exists
     * 	<li>credential is valid
     *  <li>user is currently logged in
     * </ul>
     *
     * TODO encrypt password in transit
     *
     * @param userId the id of the user to be logged out
     * @param password the password of the user to be logged out
     * @return the account
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @POST(ACCOUNT_PATH + "/logout/{id}/{password}")
    Call<Account> logout(@Path("id") String userId, @Path("password") String password);

    /**
     *
     * @param user the user account with administrator privilege and valid credentials
     * @return the created account
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @POST(ACCOUNT_PATH + "/create")
    Call<Account> create(@Body User user);

    /**
     * Verify if the specified user information matches with the server account
     * Validates all specified information. If not specified,
     * the validation result ignores the missing value.
     * @param user
     * @return true if valid
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @GET(ACCOUNT_PATH + "/isExistingUser")
    Call<Boolean> isExistingUser(@Body User user);


    /**
     * Returns the list of user matching the logged in state. This can be use to obtain
     * the list of logged in users.
     * @param isLoggedIn
     * @return the list of sessions matching the specified logged in state
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @GET(ACCOUNT_PATH + "/getSessions/{loggedInState}")
    Call<SessionList> getSessions(@Path("loggedInState") boolean isLoggedIn);
    //endregion

    //region Assignment Services
    String ASSIGNMENT_PATH = "/WebServices/assignment";
    /**
     * Create the specified assignment
     * @param userId the administrator or teacher
     * @param assignment the assignment to be created
     * @return the created assignment
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @POST(ASSIGNMENT_PATH + "/create/{uid}")
    Call<Assignment> createAssignment(@Path("uid") String userId, @Body Assignment assignment);

    /**
     * Get all assignments associated with the specified user
     * @param userId the user
     * @return the list of assignments
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @GET(ASSIGNMENT_PATH + "/find/{uid}")
    Call<AssignmentList> getAssignmentList(@Path("uid") String userId);

    /**
     * Assign an assignment to a user
     * @param userId the user with the privilege to assign
     * @param assignmentId the assignment
     * @return the assigned assignment, status indicated by the validation info
     */
    @Headers({ CONTENT_TYPE_JSON, ACCEPT_JSON })
    @POST(ASSIGNMENT_PATH + "/assign/{uid}/{aid}")
    Call<Assignment> assign(@Path("uid") String userId, @Path("aid") String assignmentId);
    //endregion

}
