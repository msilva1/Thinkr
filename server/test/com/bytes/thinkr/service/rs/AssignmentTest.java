package com.bytes.thinkr.service.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bytes.thinkr.model.account.User;
import com.bytes.thinkr.model.assignment.Answer;
import com.bytes.thinkr.model.assignment.Assignment;
import com.bytes.thinkr.model.assignment.AssignmentList;
import com.bytes.thinkr.model.assignment.AssignmentValidation;
import com.bytes.thinkr.model.assignment.Question;
import com.bytes.thinkr.service.impl.AssignmentServiceImpl;

public class AssignmentTest extends RestClientTest {

	private String uid = "Kent";
	private String pass = "1a2b3c4d5e";
	private String assignmentName = "Sample_Assignment";
	private Assignment.Category category = Assignment.Category.Homework;
	private Assignment assignment;
	
	@Before 
	public void initialize() {
	
		baseUrl = "http://localhost:8080/WebServices/assignment";
		
		Client client = ClientBuilder.newClient(new ClientConfig());
		target = client.target(UriBuilder.fromUri(baseUrl).build());
		assignment = createAssignment(assignmentName, category, (int) (Math.random()*90));
		
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
			assignment.setName(assignmentName + i);
			Assignment response1 = target.path("create").path(uid).request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.entity(assignment, MediaType.APPLICATION_JSON), Assignment.class);
				
			AssignmentValidation.Assignment status = response1.getValidation().getAssignmentStatus();
			Assert.assertTrue((status == AssignmentValidation.Assignment.Valid) || 
				(status == AssignmentValidation.Assignment.Existing) );
		}
	
		// Assign odd-numbered assignments to user: kent
		for (int i = 1; i < 11; i++) {
			if (i%2==1) {
				String aid = getAssignmentId(i);
				User user = new User(uid, pass);
				Assignment response2  = target.path(String.format("assign/%1$s/%2$s", uid, aid))
					.request().accept(MediaType.APPLICATION_JSON)
					.post(Entity.entity(user, MediaType.APPLICATION_JSON), Assignment.class);
				
				AssignmentValidation.Assignment status = response2.getValidation().getAssignmentStatus();
				Assert.assertTrue((status == AssignmentValidation.Assignment.Assigned) || 
					(status == AssignmentValidation.Assignment.AlreadyAssigned) );
			}
		}
		
	}
	
	/**
	 * This test assignment creation and assignment, 
	 * bypassing the rs service
	 * NOTE: Local interface does not respect the singleton annotation 
	 * (i.e., different instances of data-store)
	 */
	//	@Test
	public void createAndAssignLocalInterface() {
	
		String aid = getAssignmentId(1);
		Assignment temp = createAssignment(assignmentName+1, category, (int) (Math.random()*90));
		
		Assignment a = AssignmentServiceImpl.getInstance().create(uid, temp);
		System.out.println(a.getValidation().getAssignmentStatus());
		
		Assignment b = AssignmentServiceImpl.getInstance().assign(uid, aid);
		System.out.println(b.getValidation().getAssignmentStatus());
		
		AssignmentList list = AssignmentServiceImpl.getInstance().getAssignmentList(uid);
		System.out.println("Local assigned list size: " + list.getAssignments().size());
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
	
	private Assignment createAssignment(String name, Assignment.Category category, int duration) {
		
		List<Question> questions = new ArrayList<Question>();
		for (int i = 0; i < 10; i++) {
			Question q = new Question();
			q.setQuestion("This is question " + i+1);
			for (int j = 0; j < 4; j++) {
				Answer a = new Answer();
				a.addAnswer("This is answer " + (j+1) + "for question " + i+1);
			}
		}
		
		questions.add(new Question());		
		return new Assignment(name, category , questions, duration);
	}
	
	private String getAssignmentId(int i) {
		// id = {teacher user id} + {assignment name} + {assignment category}
		return (uid + assignmentName + i + category.toString()).toLowerCase();
	}
		
}
