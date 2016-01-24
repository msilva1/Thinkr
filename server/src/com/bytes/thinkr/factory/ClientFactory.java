/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.factory.merge.MergeFactory;
import com.bytes.thinkr.model.util.HibernateUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/13/2016.
 */
public class ClientFactory extends EntityFactory<Client> {

    // This class can use FINE or lower logging levels
    private static final Logger LOGGER = Logger.getLogger(ClientFactory.class.getName());

    private static ClientFactory instance;
    public static ClientFactory getInstance() {
        if (instance == null) {
            instance = new ClientFactory();
        }
        return instance;
    }

    @Override
    protected boolean saveSubEntities(List<Client> entities) {
        return true; // no sub entities
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
    public Client findByEmail(String email) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Request to retrieveAllByName client with email: " + email);
        }

        // Construct this retrieveByQuery from the id list
        //  "from Client e where e.email = {email}
        String query = "from Client e where e.email = '" + email +"'";

        Client client = HibernateUtil.<Client>retrieveByQuery(query).get(0);

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Successfully retrieved client : " + client.toString());
        }

        return client;
    }
}
