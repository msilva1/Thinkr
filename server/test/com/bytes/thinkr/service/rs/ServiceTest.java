/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.IEntity;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by Kent on 1/22/2016.
 */
public class ServiceTest {

    protected String baseUrl;
    protected WebTarget target;

    @Before
    public void initialize() {

        baseUrl = "http://localhost:8080/WebServices";
        Client client = ClientBuilder.newClient(new ClientConfig());
        target = client.target(UriBuilder.fromUri(baseUrl).build());

    }

    /**
     *
     * @param path
     * @param entity
     * @param responseType
     * @param <T>
     * @return
     */
    protected <T extends IEntity> T post(String path, IEntity entity, Class<T> responseType) {

        T result = target.path(path)
                .request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON), responseType);

        System.out.println(" Response: " + result);

        return result;
    }

    /**
     *
     * @param path
     * @return
     */
    protected String delete(String path) {

        Response result = target.path(path).request(MediaType.APPLICATION_JSON)
                .delete();

        System.out.println(" Response: " + result);

        return result.toString();
    }
}
