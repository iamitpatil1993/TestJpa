package com.example.pojo;

import java.util.ArrayList;
import java.util.List;


public class User {

	
	private Integer userId;
	private String name;
	private List<Relative> relatives = new ArrayList<Relative>();
	
	public User() {
		
	}
	
	public User(Integer userId) {
		super();
		this.userId = userId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Relative> getRelatives() {
		return relatives;
	}
	public void setRelatives(List<Relative> relatives) {
		this.relatives = relatives;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", relatives="
				+ relatives + "]";
	}
	
		
}
