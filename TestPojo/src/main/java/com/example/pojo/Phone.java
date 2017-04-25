package com.example.pojo;


public class Phone {

	private Integer phoneId;
	private String phoneNumber;
	private User user;


	public Phone() {
		super();
	}

	public Phone(Integer phoneId, String phoneNumber, User user) {
		super();
		this.phoneId = phoneId;
		this.phoneNumber = phoneNumber;
		this.user = user;
	}

	//Getters and Setters
	public Integer getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Integer phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
}
