package com.bytes.hack.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.User;
import com.bytes.hack.model.assignment.Answer;
import com.bytes.hack.model.assignment.Assignment;
import com.bytes.hack.model.assignment.AssignmentList;
import com.bytes.hack.model.assignment.Question;
import com.bytes.hack.model.sandbox.Todo;
import com.bytes.hack.service.impl.AccountServiceImpl;
import com.bytes.hack.service.impl.AssignmentServiceImpl;

/**
 * <p>This class is meant for testing different REST interfaces (i.e., XML, JSON, etc...) </p>
 * 
 * <ol>Usage:
 * <li>http://localhost:8080/WebServices/rest/todo - create a <tt>Todo</tt> object and return as XML or JSON
 * <li>http://localhost:8080/WebServices/rest/user - create a <tt>User</tt> object and return as XML or JSON
 * <li>http://localhost:8080/WebServices/rest/account - create an <tt>Account</tt> object and return as XML or JSON
 * </ol> 
 * Use header to set MIME type (i.e., <tt>Accept=application/json</tt> )
 */
@Path("/rest")
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
	public User getUserXml() {

		User user = new User("Kent", "Kentative@live.com", "ChangeMe", User.Type.Teacher);
		return user;
	}

	@GET
	@Path("todo")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Todo getTodoXml() {

		Todo todo = new Todo();
		todo.setSummary("This is my second todo");
		todo.setDescription("This is my first todo");
		todo.addTask("task1", "Return package");
		todo.addTask("task2", "Clean pool");
		todo.setType(Todo.Type.Admin);

		return todo;
	}
	
	@GET
	@Path("account")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Account getAccountXml() {

		User user = new User("Kent", "Kentative@live.com", "ChangeMe", User.Type.Admin);
		Account account = AccountServiceImpl.getInstance().createAccount(user);

		return account;
	}
	
	@GET
	@Path("assignment")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Assignment getAssignmentXml() {

		return new Assignment();
	}
	
	
	@GET
	@Path("question")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Question getQuestion() {

		return new Question();
	}
	
	@GET
	@Path("answer")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Answer getAswer() {

		return new Answer();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("assignmentlist/{id}")
	public AssignmentList getAssignmentList(@PathParam("id") String userId) {
		return AssignmentServiceImpl.getInstance().getAssignmentList(userId);
	}
	
}
