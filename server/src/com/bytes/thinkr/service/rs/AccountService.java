package com.bytes.thinkr.service.rs;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.SessionList;
import com.bytes.thinkr.model.account.User;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.service.impl.AccountServiceImpl;

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

		String title = "Nanobytes: Account Service";
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
	
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("login/{id}/{password}")
	@Override
	public Account login(@PathParam("id") String userId, @PathParam("password") String password) {
		return AccountServiceImpl.getInstance().login(userId, password); 
	}
	
	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("logout/{id}/{password}")
	@Override
	public Account logout(@PathParam("id") String userId, @PathParam("password") String password) {
		return AccountServiceImpl.getInstance().logout(userId, password);
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("getSessions/{loggedInState}")
	@Override
	public SessionList getSessions(@PathParam("loggedInState") boolean isLoggedIn) {
		return AccountServiceImpl.getInstance().getSessions(isLoggedIn);
	}
	
	
	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("isExistingUser")
	@Override
	public boolean isExistingUserValid(User user) {
		return AccountServiceImpl.getInstance().isExistingUserValid(user);
	}

}
