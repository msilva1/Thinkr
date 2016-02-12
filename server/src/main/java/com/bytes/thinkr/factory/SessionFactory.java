/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.FactoryResponseList;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.factory.merge.MergeFactory;
import com.bytes.thinkr.model.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public class SessionFactory extends EntityFactory<Session> {

    private static final Logger LOG = LoggerFactory.getLogger(SessionFactory.class);
    private static SessionFactory instance;

    public static SessionFactory getInstance() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance;
    }

    @Override
    protected MergeFactory<Session> getMergeFactory() {
        return null;
    }

    /**
     *
     * @param accountId
     * @return
     */
    public FactoryResponse<Session> findByAccountId(Long accountId) {

        LOG.debug("Request to retrieved session with account id: {}", accountId);

        // from Account a where a.client.email ='address1453271253491_0@email.com'
        String query = "from Session s where s.account.id ='" + accountId +"'";
        List<Session> entities = HibernateUtil.retrieveByQuery(query);
        FactoryResponse<Session> response = new FactoryResponse<>();
        if (entities != null && entities.size() >= 1) {

            Session session = entities.get(0);
            LOG.debug("Successfully retrieved session: {}", session.getId());
            response.setEntity(session);
        } else {
            LOG.debug("Unable to retrieved session with account id {} ", query);
            response.addValidation(ValidationInfo.Type.Session, ValidationInfo.Common.NotFound);
        }
        return response;
    }

    public FactoryResponseList<Session> findByLoginStatus(boolean isLoggedIn) {

        // from Session s where s.loggedIn = 'true'
        String query = "from Session s where s.loggedIn = '" + isLoggedIn +"'";
        List<Session> sessions = HibernateUtil.retrieveByQuery(query);

        FactoryResponseList<Session> response = new FactoryResponseList<>();
        if (sessions != null) {
            response.setEntities(sessions);
            LOG.debug("Successfully retrieved {} sessions with state {}", sessions.size(), isLoggedIn);
        } else {
            response.addValidation(ValidationInfo.Type.Session, ValidationInfo.Common.InvalidQuery);
        }

        return response;
    }
}
