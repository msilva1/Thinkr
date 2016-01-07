package com.bytes.hack.model.account;

/**
 * Created by Kent on 12/5/2015.
 */
public class Teacher extends User {

	/** @InheritDoc */
	public Teacher(String userId, String userEmail, String password) {
		super(userId, userEmail, password, User.Type.Teacher);
		
	}
}
