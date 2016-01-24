package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.AccountList;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.service.impl.AccountServiceImpl;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 12/6/2015.
 */

@Singleton
@Path("account")
public class AccountService implements IAccountService {

    private static final Logger LOGGER = Logger.getLogger(AccountService.class.getName());

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
	public Account createAccount(Client client) {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to create an account: " + client);
        }

		return AccountServiceImpl.getInstance().createAccount(client);
	}
	

	@PUT
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Account updateAccount(Account account) {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to update account: " + account);
        }

		return AccountServiceImpl.getInstance().updateAccount(account);
	}

	
	@DELETE
	@Path("{id}")
	@Override
	public boolean deleteAccount(@PathParam("id") String accountId) {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to delete account: " + accountId);
        }

		return AccountServiceImpl.getInstance().deleteAccount(accountId);
	}

    @DELETE
    @Path("email/{id}")
    @Override
    public boolean deleteByEmail(@PathParam("id") String accountId) {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to delete account: " + accountId);
        }

        return AccountServiceImpl.getInstance().deleteAccount(accountId);
    }


    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    @Override
    public Account find(@PathParam("id") String accountId) {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to find account: " + accountId);
        }

        return AccountServiceImpl.getInstance().find(accountId);
    }

	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("login")
	@Override
	public boolean authenticate(Client client) {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to authenticate client: " + client);
        }

		return AccountServiceImpl.getInstance().authenticate(client);
	}

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("list")
    @Override
    public AccountList findAll() {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to retrieve all accounts.");
        }

        return AccountServiceImpl.getInstance().findAll();
    }

    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("email/{email}")
    @Override
    public Account findByEmail(@PathParam("email") String email) {
        return AccountServiceImpl.getInstance().findByEmail(email);
    }
}