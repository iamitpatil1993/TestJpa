package com.example.ejb.jpa.entities.inheritance.joined;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

//If we declare this class as Entity then, it will create table for this class, which I don't want
//If we try to declare this class as entity so that we can query this class, then it will unnecessarily create table for this.

//So it is like mutually exclusiove condition, if i declare it as entity so that I can query this, it will create table which I don't want,
//and if I declare it as mappedsuperclass to avoid table creation, then I won't be able to query this table
@MappedSuperclass
public abstract class JCompanyEmployee extends JEmployee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name="vacation")
	private Integer vacation;

	//Getters and Setters
	public Integer getVacation() {
		return vacation;
	}

	public void setVacation(Integer vacation) {
		this.vacation = vacation;
	}
}
