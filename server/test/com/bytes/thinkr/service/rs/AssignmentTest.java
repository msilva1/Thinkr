package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Client;
import com.bytes.thinkr.model.assignment.Assignment;
import com.bytes.thinkr.model.assignment.AssignmentList;
import com.bytes.thinkr.model.assignment.Task;
import com.bytes.thinkr.model.entity.AssignmentEntityFactory;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class AssignmentTest extends RestClientTest {

	private String uid = "Kent";
	private String pass = "1a2b3c4d5e";
	private String assignmentName = "Sample_Assignment";
	private Task.Category category = Task.Category.Homework;
	private Assignment assignment;
	
	@Before 
	public void initialize() {
	
		baseUrl = "http://localhost:8080/WebServices/assignment";
		
		javax.ws.rs.client.Client client = ClientBuilder.newClient(new ClientConfig());
		target = client.target(UriBuilder.fromUri(baseUrl).build());
        assignment = AssignmentEntityFactory.getInstance().generate(1).get(0);
		
	}
	
	/**
	 * Test the connection to the landing page.
	 * This is the simplest functionality, 
	 * if this doesn't work, it's likely that nothing else would.
	 */
	@Test 
	public void landingPage() {
		
		String html = target.request().accept(MediaType.TEXT_HTML).get(String.class);
		Assert.assertNotNull(html);
		
	}
	
	/**
	 * Test assignment creation and assignment 
	 * using REST interfaces
	 */
	@Test
	public void createAndAssign() {
	
		// Create an 10 assignments
		for (int i = 1; i < 11; i++) {
			assignment.getTask().setName(assignmentName + i);
			Assignment response1 = target.path("create").path(uid).request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.entity(assignment, MediaType.APPLICATION_JSON), Assignment.class);
				
			String status = response1.getValidation().get(ValidationInfo.Type.Assignment);
			Assert.assertTrue((status == ValidationInfo.Common.Valid.toString()) ||
				(status == ValidationInfo.Assignment.Existing.toString()) );
		}
	
		// Assign odd-numbered assignments to user: kent
		for (int i = 1; i < 11; i++) {
			if (i%2==1) {
				String aid = getAssignmentId(i);
				Client client = new Client(uid, pass);
				Assignment response2  = target.path(String.format("assign/%1$s/%2$s", uid, aid))
					.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.entity(client, MediaType.APPLICATION_JSON), Assignment.class);

				String status = response2.getValidation().get(ValidationInfo.Type.Assignment);
				Assert.assertTrue((status == ValidationInfo.Assignment.Assigned.toString()) ||
					(status == ValidationInfo.Assignment.AlreadyAssigned.toString()) );
			}
		}
		
	}

	@Test
	public void getAssignedList() {
	
		AssignmentList assignmentList = target.path(String.format("find/%1$s", uid)).request()
			.accept(MediaType.APPLICATION_JSON)
			.get(AssignmentList.class);

		System.out.println("Remote assigned list size: " + assignmentList.getAssignments().size());
		Assert.assertTrue(assignmentList.getAssignments().size() >= 1);
	}

	@Test
	public void update() {
		// TODO
	}
	
	
	@Test
	public void delete() {
		// TODO
	}
	

	private String getAssignmentId(int i) {
		// id = {teacher user id} + {assignment name} + {assignment category}
		return (uid + assignmentName + i + category.toString()).toLowerCase();
	}
		
}
