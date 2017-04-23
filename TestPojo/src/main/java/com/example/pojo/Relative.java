package com.example.pojo;


public class Relative {

	private Integer relativeId;
	private String firstName;
	private String lastName;
	private User user;
	
	
	public Relative(String firstName, String lastName, User user) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.user = user;
	}
	public Integer getRelativeId() {
		return relativeId;
	}
	public void setRelativeId(Integer relativeId) {
		this.relativeId = relativeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Override
	public String toString() {
		return "Relative [relativeId=" + relativeId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", user=" + user + "]";
	}
	
	
}
