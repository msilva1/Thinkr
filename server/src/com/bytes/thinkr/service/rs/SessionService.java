package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.model.entity.session.SessionList;
import com.bytes.thinkr.service.ISessionService;
import com.bytes.thinkr.service.impl.SessionServiceImpl;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Kent on 1/8/2016.
 *
 * TODO (currently using the one in account service)
 */
@Singleton
@Path("/session")
public class SessionService implements ISessionService{

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("login/{id}/{password}")
    @Override
    public Session login(@PathParam("id") String userId, @PathParam("password") String password) {
        return SessionServiceImpl.getInstance().login(userId, password);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("logout/{id}/{password}")
    @Override
    public Session logout(@PathParam("id") String userId, @PathParam("password") String password) {
        return SessionServiceImpl.getInstance().logout(userId, password);
    }

    /**
     * TODO move to session service
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getSessions/{loggedInState}")
    @Override
    public SessionList getSessions(@PathParam("loggedInState") boolean isLoggedIn) {
        return SessionServiceImpl.getInstance().getSessions(isLoggedIn);
    }
}
