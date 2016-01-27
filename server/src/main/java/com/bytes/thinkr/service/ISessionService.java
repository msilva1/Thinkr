package com.bytes.thinkr.service;

import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.model.entity.session.SessionList;

/**
 * Created by Kent on 1/8/2016.
 */
public interface ISessionService {

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
    Session login(String userId, String password);


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
     * @return
     */
    Session logout(String userId, String password);


    /**
     * Returns the list of user matching the logged in state. This can be use to obtain
     * the list of logged in users.
     * @param isLoggedIn
     * @return the list of sessions matching the specified logged in state
     */
    SessionList getSessions(boolean isLoggedIn);
}
