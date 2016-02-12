package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
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

    @Path("/version")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String version() {
        return "<p>Version: " + com.bytes.thinkr.service.rs.Application.apiVersion + "</p>";
    }

    /**
     * Landing page for HTML request
     * @return landing page
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {

        String title = "Thinkr: Session Service";
        String message = "Welcome to Session REST Service Landing Page";
        return String.format(
                "<html>" + "<title>%1$s</title>" + "<body><h1>%2$s</h1></body>" + "</html>",
                title, message);
    }

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
    public Session logout(@PathParam("id") String email, @PathParam("password") String password) {
        return SessionServiceImpl.getInstance().logout(email, password);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("list/{loggedInState}")
    @Override
    public SessionList getSessions(@PathParam("loggedInState") boolean isLoggedIn) {
        return SessionServiceImpl.getInstance().getSessions(isLoggedIn);
    }

    @Override
    public Session create(Session resource) {
        Account account = resource.getAccount();
        if (account != null) {
            Client client = account.getClient();
            if (client != null) {
                SessionServiceImpl.getInstance().login(client.getEmail(), client.getPassword());
            }
        }
        return null;
    }

    @Override
    public Session update(String id, Session resource) {
        return null;
    }

    @DELETE
    @Path("{id}")
    @Override
    public boolean delete(@PathParam("id") String id) {
        return SessionServiceImpl.getInstance().delete(id);
    }

    @Override
    public Session find(String id) {
        return null;
    }
}
