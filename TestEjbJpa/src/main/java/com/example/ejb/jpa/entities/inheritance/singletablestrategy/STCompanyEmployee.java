package com.example.ejb.jpa.entities.inheritance.singletablestrategy;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

//Making this class as Entity because I want to query this type, If I declare it as MappedSuperClass, then It won't be possible for me to query this.
//Making this class abstract, because I don't want to persist this class. Hence there won't be DescriminatoraValue() annotation here
@Entity
@Access(AccessType.FIELD)
public abstract class STCompanyEmployee extends STEmployee implements Serializable {

	/**
	 * 
	 */
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
