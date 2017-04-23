package com.example.ejb.jpa.entities.collectionsorting.nonindexedlist;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User;

@Entity
@Table(name="relative")
@Access(AccessType.FIELD)
public class Relative implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="relative_id")
	@Basic
	private Integer relativeId;
	
	@Column(name="first_name")
	@Basic
	private String firstName;

	@Column(name="last_name")
	@Basic
	private String lastName;
	
	//This is many side, so default value for fetchType is Eager, so set this to LAZY explicitly
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	
	public Relative() {
	
	}
	
	public Relative(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	//Getters and setters
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
				+ firstName + ", lastName=" + lastName + "]";
	}
	
	
}
