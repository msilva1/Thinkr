package com.bytes.hack.model.account;

/**
 * Created by Kent on 12/5/2015.
 */
public class Parent extends User {

	/** @InheritDoc */
	public Parent(String userId, String userEmail, String password) {
		super(userId, userEmail, password, User.Type.Parent);
		
	}
}
