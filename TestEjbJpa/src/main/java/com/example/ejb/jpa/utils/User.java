package com.example.ejb.jpa.utils;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	@Override
	public String toString() {
		return "User [name=" + name + ", dob=" + dob + "]";
	}
	private String name;
	private Date dob;
	public User(String name, Date dob) {
		super();
		this.name = name;
		this.dob = dob;
	}
	
	
	
}
