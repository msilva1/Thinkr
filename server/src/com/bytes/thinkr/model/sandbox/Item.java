package com.bytes.thinkr.model.sandbox;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="MyItem")
public class Item {
	
	public Item() {
		name = "Default Name Item";
	}

	public String name;

	public Item(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
