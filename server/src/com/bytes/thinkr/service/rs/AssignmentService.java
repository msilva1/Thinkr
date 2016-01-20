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

import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.entity.assignment.AssignmentList;
import com.bytes.thinkr.service.IAssignmentService;
import com.bytes.thinkr.service.impl.AssignmentServiceImpl;

@Singleton
@Path("/assignment")
public class AssignmentService implements IAssignmentService {

	/**
	 * Landing page for HTML request
	 * @return landing page
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHtml() {

		String title = "Thinkr: Assignment Service";
		String message = "Welcome to Assignment REST Service Landing Page";

		return String.format(
			"<html>" + "<title>%1$s</title>" + "<body><h1>%2$s</h1></body>" + "</html>",
			title, message);
	}

	
	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("create/{id}")
	@Override
	public Assignment create(@PathParam("id") String userId, Assignment assignment) {
		
		return AssignmentServiceImpl.getInstance().create(userId, assignment);
	}

	
	@DELETE
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("delete")
	@Override
	public Assignment delete(String assignmentId) {
		return AssignmentServiceImpl.getInstance().delete(assignmentId);
	}

	
	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("update")
	@Override
	public Assignment update(Assignment assignment) {
		return AssignmentServiceImpl.getInstance().update(assignment);
	}


	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("find/{id}")
	@Override
	public AssignmentList getAssignmentList(@PathParam("id") String userId) {
		return AssignmentServiceImpl.getInstance().getAssignmentList(userId);
	}


	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("assign/{uid}/{aid}")
	@Override
	public Assignment assign(@PathParam("uid") String userId, @PathParam("aid") String assignmentId) {
		return AssignmentServiceImpl.getInstance().assign(userId, assignmentId);
	}
	
}
