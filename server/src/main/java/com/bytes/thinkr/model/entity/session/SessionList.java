/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.session;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SessionList {
	
	private Map<String, Session> sessions;

	public SessionList() {
		sessions = new HashMap<String, Session>();
	}
	
	/**
	 * @return the sessions
	 */
	public Map<String, Session> getSessions() {
		return sessions;
	}

	/**
	 * @param sessions the sessions to set
	 */
	public void setSessions(Map<String, Session> sessions) {
		this.sessions = sessions;
	}
	
}
