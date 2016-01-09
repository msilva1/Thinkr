package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.AccountList;
import com.bytes.thinkr.model.account.User;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.service.impl.AccountServiceImpl;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Kent on 12/6/2015.
 */

@Singleton
@Path("/account")
public class AccountService implements IAccountService {


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
	@Path("create")
	@Override
	public Account createAccount(User user) {

		return AccountServiceImpl.getInstance().createAccount(user);
	}
	

	@PUT
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("update")
	@Override
	public Account updateAccount(Account account) {
		return AccountServiceImpl.getInstance().updateAccount(account);
	}

	
	@DELETE
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("delete/{id}")
	@Override
	public Account deleteAccount(@PathParam("id") String accountId) {
		return AccountServiceImpl.getInstance().deleteAccount(accountId);
	}

	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("isExistingUser")
	@Override
	public boolean isExistingUserValid(User user) {
		return AccountServiceImpl.getInstance().isExistingUserValid(user);
	}


	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("find/{id}")
	@Override
	public Account find(@PathParam("id") String accountId) {
		return AccountServiceImpl.getInstance().find(accountId);
	}


    /**
     * TODO move to session service
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("find")
    @Override
    public AccountList findAll() {
        return AccountServiceImpl.getInstance().findAll();
    }
}
