package com.bytes.hack.model.sandbox;

import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
public class Todo {

	private Item item;

//	/**
//	 * @return the item
//	 */
//	@XmlElement(name = "MyItem", type=Item.class)
//	public Item getItem() {
//		return item;
//	}
//	/**
//	 * @param item the item to set
//	 */
//	public void setItem(Item item) {
//		this.item = item;
//	}
	
	public enum Type {
		Teacher,
		Student,
		Parent,
		Admin
	}
	

	private String summary;
	private String description;
	
	
	private HashMap<String, String> tasks;
	private Type type;
	private Date date;
	

	
	
//	 @XmlElement(name = "user")
//	protected User user;

	public Todo() {
		tasks = new HashMap<>();
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addTask(String taskId, String task) {

		tasks.put(taskId, task);
	}

	/**
	 * @return the tasks
	 */
	public HashMap<String, String> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 *            the tasks to set
	 */
	public void setTasks(HashMap<String, String> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}