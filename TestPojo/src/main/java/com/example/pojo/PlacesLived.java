package com.example.pojo;

public class PlacesLived {

	private String state;
	private String city;
	private String country;
	private Integer userId;

	
	public PlacesLived(String state, String city, String country, Integer userId) {
		super();
		this.state = state;
		this.city = city;
		this.country = country;
		this.userId = userId;
	}

	//Getters and Setters
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
