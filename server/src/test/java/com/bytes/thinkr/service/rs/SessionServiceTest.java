package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.factory.data.SessionDataFactory;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.model.entity.session.SessionList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kent on 1/8/2016.
 */
public class SessionServiceTest extends ServiceTest {

    Logger LOG = LoggerFactory.getLogger(SessionServiceTest.class);

    // The list of entities (detached)
    List<Session> entities;

    // key: SessionId, value: AccountId
    // Use to clean up entity on teardown
    HashMap<Long, Long> entityIdMap;

    HashMap<String, String> credentials;

    @Before
    public void startup(){

        credentials = new HashMap<>();
        entityIdMap = new HashMap<>();

        // Create entities
        entities = SessionDataFactory.getInstance().generate(10);
        LOG.debug("Startup generated {} sessions", entities.size());
        for (Session entity : entities) {

            Account account = entity.getAccount();
            Account response = post("account", account, Account.class);
            Assert.assertNotNull(response.getId());
            LOG.debug("Startup successfully committed account {}", account.getId());

            Client client = entity.getAccount().getClient();
            credentials.put(client.getEmail(), client.getPassword());
        }
    }

    @After
    public void teardown() {

        for (Long sessionId : entityIdMap.keySet()) {

            // Delete session
            Response response = target.path("session/" + sessionId).request().delete();
            if (response.getStatus() == 200) {
                LOG.debug("Teardown successfully removed session: {}", sessionId);
            } else {
                LOG.debug("Teardown unable to removed session: {}", sessionId);
            }

            // Delete account
            response = target.path("account/" + entityIdMap.get(sessionId)).request().delete();
            if (response.getStatus() == 200) {
                LOG.debug("Teardown successfully removed account: {}", entityIdMap.get(sessionId));
            }
        }
    }

    @Test
    public void testLogin() throws Exception {

        for(String email : credentials.keySet()) {
            LOG.debug("Login email: {} password: {} ...", email, credentials.get(email));
            String path = "/session/login/" + email + "/" + credentials.get(email);
            Session session = post(path, null, Session.class);
            Assert.assertTrue(session.isLoggedIn());
            entityIdMap.put(session.getId(), session.getAccount().getId());
            LOG.debug("Login state {}", session.isLoggedIn());
        }
    }

    @Test
    public void testLogout() throws Exception {

        for(String email : credentials.keySet()) {
            // Log in
            LOG.debug("Login email: {} password: {} ...", email, credentials.get(email));
            String path = "/session/login/" + email + "/" + credentials.get(email);
            Session session = post(path, null, Session.class);
            Assert.assertTrue(session.isLoggedIn());
            entityIdMap.put(session.getId(), session.getAccount().getId());
            LOG.debug("Login state {}", session.isLoggedIn());

            // Log out
            path = "/session/logout/" + email + "/" + credentials.get(email);
            session = post(path, null, Session.class);
            Assert.assertTrue(!session.isLoggedIn());
            entityIdMap.put(session.getId(), session.getAccount().getId());
            LOG.debug("Login state {}", session.isLoggedIn());
        }
    }

    @Test
    public void testGetSessions() throws Exception {

        int count = 0;
        for(String email : credentials.keySet()) {

            // Log in
            LOG.debug("Login email: {} password: {} ...", email, credentials.get(email));
            String path = "/session/login/" + email + "/" + credentials.get(email);
            Session session = post(path, null, Session.class);
            Assert.assertTrue(session.isLoggedIn());
            entityIdMap.put(session.getId(), session.getAccount().getId());
            LOG.debug("Login state {}", session.isLoggedIn());

            // List Logged in User
            SessionList sessionList = get("/session/list/true", SessionList.class);
            Assert.assertEquals(++count, sessionList.getSessions().size());
        }

        count = 0;
        for(String email : credentials.keySet()) {

            // Log out
            String path = "/session/logout/" + email + "/" + credentials.get(email);
            Session session = post(path, null, Session.class);
            Assert.assertTrue(!session.isLoggedIn());
            entityIdMap.put(session.getId(), session.getAccount().getId());
            LOG.debug("Login state {}", session.isLoggedIn());

            SessionList sessionList = get("/session/list/false", SessionList.class);
            Assert.assertEquals(++count, sessionList.getSessions().size());
        }
    }
}