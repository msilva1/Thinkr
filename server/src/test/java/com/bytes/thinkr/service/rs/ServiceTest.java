/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.IEntity;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by Kent on 1/22/2016.
 */
public class ServiceTest {

    protected String baseUrl;
    protected WebTarget target;

    public ServiceTest() {
        baseUrl = "http://localhost:8080/WebServices";
        Client client = ClientBuilder.newClient(new ClientConfig());
        target = client.target(UriBuilder.fromUri(baseUrl).build());
    }

    protected <T> T get(String path, Class<T> responseType) {
        return target.path(path)
                .request()
                .accept(
                        MediaType.APPLICATION_JSON,
                        MediaType.APPLICATION_XML,
                        MediaType.TEXT_HTML,
                        MediaType.TEXT_PLAIN)
                .get(responseType);
    }

    protected <T extends IEntity> T post(String path, IEntity entity, Class<T> responseType) {

        return target.path(path)
                .request(MediaType.APPLICATION_JSON)
                .accept(
                        MediaType.APPLICATION_JSON,
                        MediaType.APPLICATION_XML,
                        MediaType.TEXT_HTML,
                        MediaType.TEXT_PLAIN)
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON), responseType);
    }

    protected  <T extends IEntity> T put(String path, IEntity entity, Class<T> responseType) {

        return target.path(path)
                .request(MediaType.APPLICATION_JSON)
                .accept(
                        MediaType.APPLICATION_JSON,
                        MediaType.APPLICATION_XML,
                        MediaType.TEXT_HTML,
                        MediaType.TEXT_PLAIN)
                .put(Entity.entity(entity, MediaType.APPLICATION_JSON), responseType);
    }
}
