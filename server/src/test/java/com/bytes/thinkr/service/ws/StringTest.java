package com.bytes.thinkr.service.ws;

import org.junit.Before;

public class StringTest extends ConnectionTest {
	
	
	@Before public void initialize() {
        location = "wss://localhost:7001/WebSocketApplication/ws";
        http://127.0.0.1:7001/WebSocketApplication
//		location = "ws://localhost:8080/WebServices/ws";
		handshakeWait = 3000;
		sendInterval = 100;
		msgCount = 10;
		message = new String("This is a string test");
		
		
		/** Sends n messages to the server. */
		sendTask =  new Runnable() {
			
			@Override
			public void run() {
				while (clientCounter < msgCount) {
					client.send(message + " client: " + (clientCounter));
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
