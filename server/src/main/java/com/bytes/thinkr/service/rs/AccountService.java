package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.AccountList;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.service.impl.AccountServiceImpl;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kent on 12/6/2015.
 */

@Singleton
@Path("account")
public class AccountService implements IAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);

    @Path("/version")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String version() {
        return "<p>Version: " + Application.apiVersion + "</p>";
    }

    /**
	 * Landing page for HTML request
	 * @return landing page
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHtml() {

		String title = "Thinkr: Account Service";
		String message = "Welcome to Account REST Service Landing Page";
		return String.format(
			"<html>" + "<title>%1$s</title>" + "<body><h1>%2$s</h1></body>" + "</html>",
			title, message);
	}

	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Account create(Account account) {

        LOG.info("Request to create an account: {}", account);
		return AccountServiceImpl.getInstance().create(account);
	}
	

	@POST
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
	@Override
	public Account update(@PathParam("id") String id, Account account) {

        LOG.info("Request to update account: {}", id);
		return AccountServiceImpl.getInstance().update(id, account);
	}

	
	@DELETE
	@Path("{id}")
	@Override
	public boolean delete(@PathParam("id") String accountId) {

        LOG.info("Request to delete account by id: {}", accountId);
		return AccountServiceImpl.getInstance().delete(accountId);
	}

    @DELETE
    @Path("email/{email}")
    @Override
    public boolean deleteByEmail(@PathParam("email") String email) {

        LOG.info("Request to delete account by email: {}");
        return AccountServiceImpl.getInstance().deleteByEmail(email);
    }


    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    @Override
    public Account find(@PathParam("id") String accountId) {

        LOG.info("Request to find account: {}", accountId);
        return AccountServiceImpl.getInstance().find(accountId);
    }

    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("email/{email}")
    @Override
    public Account findByEmail(@PathParam("email") String email) {
        return AccountServiceImpl.getInstance().findByEmail(email);
    }

	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("login/{email}/{password}")
	@Override
	public boolean login(
            @PathParam("email") String email,
            @PathParam("password") String password) {

        LOG.info("Request to log in client: {}", email);
		return AccountServiceImpl.getInstance().login(email, password);
	}


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("list")
    @Override
    public AccountList findAll() {

        LOG.info("Request to retrieve all accounts.");
        return AccountServiceImpl.getInstance().findAll();
    }


    @GET
    @Path("authenticate/{email}/{password}")
    @Override
    public boolean authenticate(
            @PathParam("email") String email,
            @PathParam("password") String password) {
        return AccountServiceImpl.getInstance().authenticate(email, password);
    }
}