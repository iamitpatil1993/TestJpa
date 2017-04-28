package com.example.ejb.jpa.entities.elementcollection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class PlacesLived {

	@Column(name="state")
	private String state;
	
	@Column(name="city")
	private String city;
	
	@Column(name="country")
	private String country;

	
	public PlacesLived(String state, String city, String country) {
		super();
		this.state = state;
		this.city = city;
		this.country = country;
	}
	
	//Embeddable objects also requires default constructor
	public PlacesLived() {
		//Nothing to do here
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

	@Override
	public String toString() {
		return "PlacesLived [state=" + state + ", city=" + city + ", country="
				+ country + "]";
	}
}
