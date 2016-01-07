package com.bytes.hack.server.ws.msg;

public class Message {

	public enum Commands {
		None,
		Clear,
		Print,
	}	
	
	private String subject;
	private String content;
	private Commands command;
	
	public Message() {
		command = Commands.None;
		subject = "No Subject";
		content = "No Content";
	}

	public Commands getCommand() {
		return command;
	}

	public void setCommand(Commands command) {
		this.command = command;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString() {
		return "Message [command=" + command + ", subject=" + subject + ", content=" + content +"]";
	}

}
