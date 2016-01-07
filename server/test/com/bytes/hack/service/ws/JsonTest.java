package com.bytes.hack.service.ws;


import org.junit.Before;

import com.bytes.hack.server.ws.msg.Message;
import com.google.gson.Gson;

public class JsonTest extends StringTest {
	
	/**
	 * Initialize parameter to test json messages 
	 */
	@Before public void initialize() {
		
		location = "ws://localhost:8080/WebServices/json";
//		location = "ws://kentative.azurewebsites.net/WebServices/location";
		handshakeWait = 3000;
		sendInterval = 10;
		msgCount = 100;
		message = "json message content ";
		
		final Gson gson = new Gson();
		final Message msg = new Message();
		msg.setCommand(Message.Commands.None);
		msg.setSubject("Test");
		msg.setContent(message);
		
		
		
		/** Sends n JSON messages to the server. */
		sendTask =  new Runnable() {
			
			@Override
			public void run() {
				while (clientCounter < msgCount) {
					
					msg.setContent(message + (clientCounter+1));
					System.out.println("Sending to server: " + gson.toJson(msg));	
					client.send(message);
					
					clientCounter++;
					try {
						Thread.sleep(sendInterval);
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
				
			}
		};	
	}

}
