package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.entity.assignment.AssignmentList;
import com.bytes.thinkr.service.IAssignmentService;
import com.bytes.thinkr.service.impl.AssignmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/assignment")
public class AssignmentService implements IAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AssignmentService.class);

    @Path("/version")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String version() {

        String title = "Thinkr: Assignment Service";
        String message = "Welcome to Assignment Resource";

        return String.format(
                "<html>" + "<title>%1$s</title>" +
                        "<body><h1>%2$s</h1>" +
                        "<p>Version: " + Application.apiVersion + "</p>" +
                        "</body>" + "</html>",
                title, message);
    }


    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    @Override
    public Assignment find(@PathParam("id") String accountId) {

        LOG.info("Request to find assignment: {}", accountId);
        return AssignmentServiceImpl.getInstance().find(accountId);
    }


    @DELETE
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("delete")
    @Override
    public boolean delete(String assignmentId) {
        return AssignmentServiceImpl.getInstance().delete(assignmentId);
    }

	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("create")
	@Override
	public Assignment create(Assignment assignment) {
		
		return AssignmentServiceImpl.getInstance().create(assignment);
	}

    @PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("update/{id}")
	@Override
	public Assignment update(@PathParam("id") String id, Assignment assignment) {
        return AssignmentServiceImpl.getInstance().update(id, assignment);
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
