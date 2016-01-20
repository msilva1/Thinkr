package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.entity.account.AccountList;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.session.Session;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class AccountTest extends RestClientTest {

	String[] userIds = {"Kent", "Nadia", "Kaelyn", "Kydan"};
	String[] userPass = {"iamawesome", "password", "ibrushmyteeth", "icryalot"};
	String[] userEmails = {"kentative@gmail.com", "nadia@gmail.com", "kaelyn@live.com", "kydan@live.com"};
	
	@Before 
	public void initialize() {
	
		baseUrl = "http://localhost:8080/WebServices";
		
		javax.ws.rs.client.Client client = ClientBuilder.newClient(new ClientConfig());
		target = client.target(UriBuilder.fromUri(baseUrl).build());
		
	}
	
	/**
	 * Test the connection to the landing page.
	 * This is the simplest functionality, 
	 * if this doesn't work, it's likely that nothing else would.
	 */
	@Test 
	public void landingPage() {
		
		String html = target.path("account").request().accept(MediaType.TEXT_HTML).get(String.class);
		Assert.assertNotNull(html);
		
	}
	
	/**
	 * Create 10 accounts of each type
	 */
	@Test
	public void createAccounts() {
	
		int count = 10;
		for (int i = 0; i < count; i ++) {
			createAccount(userIds[0] +i, userEmails[0], userPass[0] +i, Client.Type.Admin);
			createAccount(userIds[1] +i, userEmails[1], userPass[1] +i, Client.Type.Teacher);
			createAccount(userIds[2] +i, userEmails[2], userPass[2] +i, Client.Type.Student);
			createAccount(userIds[3] +i, userEmails[3], userPass[3] +i, Client.Type.Student);
		}
		
		AccountList accounts = target.path("account/find").request()
			.accept(MediaType.APPLICATION_JSON).get(AccountList.class);

		System.out.println("Client created: " + accounts.getAccounts().size());
		Assert.assertTrue(accounts.getAccounts().size() == (count*4)+1);
	}
	
	/**
	 * Log users in and out
	 * http://localhost:8080/WebServices/account/login/Kydan6/ChangeMe
	 */
	@Test
	public void logUsers() {

		int count = 10;
		for (int i = 0; i < count; i ++) {
			
			login(userIds[0]+i, userPass[0]+i);
			login(userIds[1]+i, userPass[1]+i);
			login(userIds[2]+i, userPass[2]+i);
			login(userIds[3]+i, userPass[3]+i);
			
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < count; i ++) {
			
			logout(userIds[0]+i, userPass[0]+i);
			logout(userIds[1]+i, userPass[1]+i);
			logout(userIds[2]+i, userPass[2]+i);
			logout(userIds[3]+i, userPass[3]+i);
			
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param pass
	 */
	private void  login(String id, String pass) {
		
		Session session = target.path(String.format("session/login/%1$s/%2$s", id, pass)).request()
			.accept(MediaType.APPLICATION_XML).post(Entity.text(null), Session.class);
		
		System.out.println(id +"/" + pass + " logged in status: " + session.isLoggedIn());
		Assert.assertTrue(session.isLoggedIn());

	}
	
	private void  logout(String id, String pass) {

		Session session = target.path(String.format("session/logout/%1$s/%2$s", id, pass)).request()
			.accept(MediaType.APPLICATION_XML).post(Entity.text(null), Session.class);
			
		System.out.println(id +"/" + pass + " logged out status: " + session.isLoggedIn());
		Assert.assertTrue(!session.isLoggedIn());
	}

	
	/**
	 * 
	 * @param userId
	 * @param email
	 * @param password
	 * @param type
	 * @return
	 */
	private void createAccount(String userId, String email, String password, Client.Type type) {
		
		System.out.print("Creating account: " + userId);

		Client client = new Client(userId, email, password, Client.Type.Teacher);

		String account = target.path("account").path("create")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.post(Entity.entity(client, MediaType.APPLICATION_JSON), String.class);

		System.out.println(" Response: " + account);
//		System.out.println(" Response: " + account.getValidation().get(ValidationInfo.Type.Account));
	}

}
