package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.AccountList;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.assignment.Answer;
import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.entity.assignment.AssignmentList;
import com.bytes.thinkr.model.entity.assignment.Question;
import com.bytes.thinkr.factory.data.AccountDataFactory;
import com.bytes.thinkr.service.impl.AccountServiceImpl;
import com.bytes.thinkr.service.impl.AssignmentServiceImpl;
import com.bytes.thinkr.service.impl.SanboxServiceImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <p>This class is meant for testing different REST interfaces (i.e., XML, JSON, etc...) </p>
 * 
 * <ol>Usage:
 * <li>http://localhost:8080/WebServices/rest/todo - create a <tt>Todo</tt> object and return as XML or JSON
 * <li>http://localhost:8080/WebServices/rest/user - create a <tt>Client</tt> object and return as XML or JSON
 * <li>http://localhost:8080/WebServices/rest/account - create an <tt>Account</tt> object and return as XML or JSON
 * </ol> 
 * Use header to set MIME type (i.e., <tt>Accept=application/json</tt> )
 */
@Path("/rs")
public class SandboxService {

	/**
	 * Landing page for HTML request
	 * @return landing page
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHtml() {

		String title = "Nanobytes: Sandbox Service";
		String message = "Welcome to Sandbox REST Service Landing Page";
		return String.format("<html>" + "<title>%1$s</title>" + "<body><h1>%2$s</h1></body>" + "</html>", 
				title,
				message);
	}

	@GET
	@Path("user")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Client getUserXml() {

		Client client = new Client("Kent", "Kentative@live.com", "ChangeMe", Client.Type.Teacher);
		return client;
	}

	
	@GET
	@Path("account")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Account getAccount() {

        return AccountDataFactory.getInstance().generate(1).get(0);
	}


    @GET
    @Path("accountlist")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public AccountList getAccountList() {
        return AccountServiceImpl.getInstance().findAll();
    }


	@GET
	@Path("assignment")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Assignment getAssignmentXml() {

		return SanboxServiceImpl.createAssignment();
	}
	
	@GET
	@Path("question")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Question getQuestion() {
		return SanboxServiceImpl.createQuestion();
	}


	@GET
	@Path("answer")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Answer getAswer() {
		return SanboxServiceImpl.createAnswer();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("assignmentlist/{id}")
	public AssignmentList getAssignmentList(@PathParam("id") String userId) {
		return AssignmentServiceImpl.getInstance().getAssignmentList(userId);
	}
}
