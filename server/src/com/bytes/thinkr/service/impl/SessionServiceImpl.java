package com.bytes.thinkr.service.impl;

import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.model.entity.session.SessionList;
import com.bytes.thinkr.service.ISessionService;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/8/2016.
 */
public class SessionServiceImpl implements ISessionService {

    private static final Logger LOGGER = Logger.getLogger(SessionServiceImpl.class.getName());

    /**
     * In-memory storage for created accounts
     * TODO persist
     * key - user id
     * value - <tt>Session</tt>
     */
    private HashMap<String, Session> sessions;

    private static ISessionService instance;
    public static ISessionService getInstance() {

        if (instance == null) {
            instance = new SessionServiceImpl();
        }
        return instance;
    }

    private SessionServiceImpl() {
        sessions = new HashMap<>();
    }

    @Override
    public Session login(String userId, String password) {
        return logUser(userId, password, true);
    }

    @Override
    public Session logout(String userId, String password) {
        return logUser(userId, password, false);
    }

    /**
     * This implementation does not return sessions for account that
     * was never logged on
     * @param isLoggedIn
     * @return
     */
    @Override
    public SessionList getSessions(boolean isLoggedIn) {
        SessionList sessionList = new SessionList();

        for (String k : sessions.keySet()) {
            Session s = sessions.get(k);
            if (s.isLoggedIn() == isLoggedIn) {

                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE, "Client found: " + k);
                }
                sessionList.getSessions().put(k, s);
            }
        }

        return sessionList;
    }

    /**
     *
     * @param userId
     * @param password
     * @param state
     * @return
     */
    private Session logUser(String userId, String password, boolean state) {

        if (!AccountServiceImpl.getInstance().isExistingUserValid(new Client(userId, password)))
        {
            return Session.INVALID_ID_OR_PASSWORD;
        }

        Session session;
        if (sessions.containsKey(userId)) {
            session = sessions.get(userId);
        } else {
            session = new Session();
            sessions.put(userId, session);
        }

        // starts a new session
        session.setLoggedIn(state);
        session.setDuration(0);
        session.setLoggedInTime(new Date());

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "logged state: " + state);
        }

        return session;
    }
}
