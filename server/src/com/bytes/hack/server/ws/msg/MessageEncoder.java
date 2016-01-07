package com.bytes.hack.server.ws.msg;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

	@Override
	public String encode(Message message) throws EncodeException {

		System.out.println("MessageEncoder - encode method called");

		JsonObject jsonObject = Json.createObjectBuilder()
			.add("subject", message.getSubject())
			.add("content", message.getContent())
			.add("command", message.getCommand().toString())
			.build();
		
		return jsonObject.toString();

	}

	@Override
	public void init(EndpointConfig ec) {
		System.out.println("MessageEncoder - init method called");
	}

	@Override
	public void destroy() {
		System.out.println("MessageEncoder - destroy method called");
	}

}