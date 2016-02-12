package com.bytes.thinkr.service.impl;

import com.bytes.thinkr.factory.AccountFactory;
import com.bytes.thinkr.factory.SessionFactory;
import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.FactoryResponseList;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.model.entity.session.SessionList;
import com.bytes.thinkr.service.ISessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kent on 1/8/2016.
 */
public class SessionServiceImpl implements ISessionService {

    private static final Logger LOG = LoggerFactory.getLogger(SessionServiceImpl.class);

    private static ISessionService instance;
    public static ISessionService getInstance() {

        if (instance == null) {
            instance = new SessionServiceImpl();
        }
        return instance;
    }

    private SessionServiceImpl() {}

    @Override
    public Session login(String email, String password) {
        return logUser(email, password, true);
    }

    @Override
    public Session logout(String email, String password) {
        return logUser(email, password, false);
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
        FactoryResponseList<Session> responseList = SessionFactory.getInstance().findByLoginStatus(isLoggedIn);
        List<Session> entities = responseList.getEntities();
        if (entities != null && entities.size() > 0) {
            LOG.debug("Found {} sessions. Login status: {}", entities.size(), isLoggedIn);
            sessionList.setSessions(entities);
        }
        return sessionList;
    }

    @Override
    public Session create(Session resource) {
        return null;
    }

    @Override
    public Session update(String id, Session resource) {
        return null;
    }

    @Override
    public boolean delete(String sessionId) {

        Long id = Long.parseLong(sessionId);
        FactoryResponse<Session> response = SessionFactory.getInstance().findById(id);
        Session entity = response.getEntity();
        if (entity != null) {
            return SessionFactory.getInstance().delete(entity);
        }
        return false;
    }

    @Override
    public Session find(String id) {
        return null;
    }

    /**
     *
     * @param email
     * @param password
     * @param state
     * @return
     */
    private Session logUser(String email, String password, boolean state) {

        if (!AccountServiceImpl.getInstance().authenticate(email, password))
        {
            return Session.INVALID_ID_OR_PASSWORD;
        }

        FactoryResponse<Account> accountR = AccountFactory.getInstance().findByEmail(email);
        Account account = accountR.getEntity();

        FactoryResponse<Session> sessionR = SessionFactory.getInstance().findByAccountId(account.getId());
        Session session = sessionR.getEntity();

        // No existing session for the specified account
        if (session == null) {
            session = new Session(account);
        }

        if (session.isLoggedIn() != state) {

            // Update logged in timestamp
            session.setLoggedIn(state);
            if (state) {
                session.setLoggedInTime(new Date());
            } else {
                session.setLoggedOutTime(new Date());
            }

        } else {
            // Log user/account in
            session.setLoggedIn(true);
            session.setLoggedInTime(new Date());
        }

        // starts a new session
        session.setLoggedIn(state);
        session.setLoggedInTime(new Date());

        if (SessionFactory.getInstance().save(session)) {
            return session;
        }

        return Session.INVALID_ID_OR_PASSWORD;
    }
}
