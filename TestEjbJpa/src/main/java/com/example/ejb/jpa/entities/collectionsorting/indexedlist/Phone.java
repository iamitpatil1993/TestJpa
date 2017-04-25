package com.example.ejb.jpa.entities.collectionsorting.indexedlist;

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
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User;

@Entity
@Table(name="phone")
@Access(AccessType.FIELD)
public class Phone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6886936576178318190L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="phone_id")
	@Basic
	private Integer phoneId;
	
	
	@Column(name="phone_number")
	@Basic
	private String phoneNumber;
	
	//This column will be included in insert statement created by provider for this entity, but will not be in update statement
	//therefore setting (insertable=true, updatable=false)
	//since this is many side of association, entity will be by default fetched eagerly, to disable this set fetch=FetchType.LAZY 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", insertable=true, updatable=true)
	@OrderColumn(name="order_index")
	private User user;

	
	//Entity must have default constructor, so don;'t forgot to add this as soon as u add parameterized constructor
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
