/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.factory.merge.MergeFactory;
import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public class ClientFactory extends EntityFactory<Client> {

    // This class can use FINE or lower logging levels
    private static final Logger LOG = LoggerFactory.getLogger(ClientFactory.class.getName());

    private static ClientFactory instance;
    public static ClientFactory getInstance() {
        if (instance == null) {
            instance = new ClientFactory();
        }
        return instance;
    }

    @Override
    protected MergeFactory<Client> getMergeFactory() {
        return null;
    }

    /**
     *
     * @param email
     * @return
     */
    public FactoryResponse<Client> findByEmail(String email) {

        LOG.debug("Request to retrieveAllByName client with email: {}", email);

        // Construct this retrieveByQuery from the id list
        //  "from Client e where e.email = {email}
        String query = "from Client e where e.email = '" + email +"'";
        List<Client> clients = HibernateUtil.retrieveByQuery(query);

        FactoryResponse<Client> response = new FactoryResponse<>();
        if (clients != null) {

            response.setEntity(clients.get(0));
            LOG.debug("Successfully retrieved client: {}", response.getEntity());

        } else {
            response.getValidationInfo().add(
                ValidationInfo.Type.Client, ValidationInfo.Common.NotFound);
        }

        return response;
    }
}

