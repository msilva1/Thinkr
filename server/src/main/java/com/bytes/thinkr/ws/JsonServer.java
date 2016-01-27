package com.bytes.thinkr.ws;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.bytes.thinkr.ws.msg.Message;
import com.bytes.thinkr.ws.msg.MessageDecoder;
import com.bytes.thinkr.ws.msg.MessageEncoder;

@ServerEndpoint(
	value = "/json", 
	encoders = { MessageEncoder.class }, 
	decoders = { MessageDecoder.class }
)
public class JsonServer {
	
	
	private static MessageEncoder encoder = new MessageEncoder();
	/**
	 * 
	 * @param message
	 * @param session
	 * @throws IOException
	 * @throws EncodeException
	 */
	@OnMessage
	public void onMessage(Message message, Session session) throws IOException, EncodeException {

		System.out.println("onMessage");
		
		// Echo the received message back to the client
		Message response = new Message();
		response.setCommand(message.getCommand());
		response.setSubject("Response to " + message.getSubject());
		response.setContent("echo " + message.getContent());
		
		System.out.println("Response message created");
		
		
//		switch (message.getCommand()) {
//		case Clear:
//			WebSocketServer.getSessions().clear();
//			
//			response.setContent("Session Cleared");
//			break;
//			
//		case Print:
//			StringBuilder sb = new StringBuilder();
//			for(Session s : WebSocketServer.getSessions()) {
//				sb.append("Session " + s.getId() + "\n");
//			}			
//			response.setContent(sb.toString());
//			break;
//			
//		default:
//			response.setContent("No action");
//			break;
//			
//		}
		
		String text = encoder.encode(response);
		System.out.println(text);
		System.out.println("Sending response: " + text);
		session.getBasicRemote().sendObject(response);
	}

	@OnOpen
	public void onOpen() {
		System.out.println("Client connected");
	}

	@OnClose
	public void onClose() {
		System.out.println("Connection closed");
	}

}