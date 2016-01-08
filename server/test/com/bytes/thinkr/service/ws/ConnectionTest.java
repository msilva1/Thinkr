package com.bytes.thinkr.service.ws;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Assert;
import org.junit.Test;


/**
 * This is the base class for all connection-type tests.
 * Test pattern is as follow:
 * 	1. define connection parameters
 *  2. define message parameters
 *  3. define the sendTask (Runnable) to send test-specific data to the server
 * @author Kent
 *
 */
public abstract class ConnectionTest {

	// Connection Parameters
	protected WebSocketClient client;   // The Web Socket client, no need to override this
	protected String location;          // The Web Socket server address, fully qualified 
	protected int handshakeWait;        // The handshake wait time before sending the first message (millis)
	
	// Message Parameters
	protected String message;     		// The default message, override as needed
	protected int sendInterval;   		// The timeout between each message (millis)
	protected int msgCount;       		// The number of messages to send
	
	// Send Task Parameters 
	protected Runnable sendTask;        // Define the test-specific send task
	protected int clientCounter = 0;	// Use to track message count [optional]

	/**
	 * Sends n messages to the server and check to see if the server receives all of them.
	 * 	 * 
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 */
	@Test public void ConnectClient() throws URISyntaxException, InterruptedException {
		
		URI address = new URI(location);		
		client = new WebSocketClient(address, new Draft_17()) {		
			
			protected volatile int serverCounter = 0;
			
			@Override
			public void onOpen(ServerHandshake handshake) {				
				Assert.assertNotNull(handshake);
				serverCounter = 0;
			}
			
			@Override
			public void onMessage(String msg) {
				
				System.out.println("Message from server: " + msg);
				if (msg.contains(message)) {
					System.out.println("Received from server: " + msg + System.lineSeparator());
					serverCounter++;
				}

				if (serverCounter == msgCount) {
					client.close();
				}
			}		

			@Override
			public void onError(Exception ex) {
				
				System.out.println(ex.getMessage());				
			}
			
			@Override
			public void onClose(int code, String reason, boolean remote) {
				
				System.out.println("Reason: " + reason +" Remote: " + remote);	
				System.out.println("msgCount: " + msgCount +" serverCounter: " + serverCounter);	
				Assert.assertEquals(msgCount, serverCounter);
			}
		};		
		
		client.connect();
		
		
		// Allow time to connect
		Thread.sleep(handshakeWait);
		if (client.getConnection().isOpen()) {
			System.out.println("Connection is open. Executing sendTask.");
			if (sendTask != null) {
				sendTask.run();
			}		
		} else {
			System.out.println("Connection is not open.");
		}
	}
}
