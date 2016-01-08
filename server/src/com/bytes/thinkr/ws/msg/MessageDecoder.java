package com.bytes.thinkr.ws.msg;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public Message decode(String jsonMessage) throws DecodeException {

		System.out.println("MessageDecoder -decode method called");

		try {
			JsonObject jsonObject = Json.createReader(new StringReader(jsonMessage)).readObject();
			Message message = new Message();
			message.setSubject(jsonObject.getString("subject"));
			message.setContent(jsonObject.getString("content"));
			message.setCommand(Enum.valueOf(Message.Commands.class, jsonObject.getString("command")));
			return message;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new Message();
	}

	@Override
	public boolean willDecode(String jsonMessage) {

		System.out.println("MessageDecoder -willDecode method called");

		try {
			// Check if incoming message is valid JSON
			Json.createReader(new StringReader(jsonMessage)).readObject();
			return true;
		} catch (Exception e) {
			System.out.println("Unable to decode: " + e.getMessage());
		}
		
		return false;
	}

	@Override
	public void init(EndpointConfig ec) {
		System.out.println("MessageDecoder -init method called");
	}

	@Override
	public void destroy() {
		System.out.println("MessageDecoder - destroy method called");
	}

}