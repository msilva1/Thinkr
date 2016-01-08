package com.bytes.thinkr.model.account;

/**
 * Created by Kent on 12/5/2015.
 */
public class Student extends User  {
	
	/** @InheritDoc */
	public Student(String userId, String userEmail, String password) {
		super(userId, userEmail, password, User.Type.Student);
		
	}
}
