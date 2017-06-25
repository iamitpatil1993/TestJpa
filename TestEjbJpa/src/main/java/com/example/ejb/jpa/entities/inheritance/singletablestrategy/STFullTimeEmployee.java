package com.example.ejb.jpa.entities.inheritance.singletablestrategy;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue(value="STFullTimeEmployee")
public class STFullTimeEmployee extends STCompanyEmployee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name="salary")
	private Integer salary;

	@Basic
	@Column(name="penion")
	private Integer pension;

	//Getters and Setters
	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Integer getPension() {
		return pension;
	}

	public void setPension(Integer pension) {
		this.pension = pension;
	}

	@Override
	public String toString() {
		return "STFullTimeEmployee [salary=" + salary + ", pension=" + pension
				+ ", getVacation()=" + getVacation() + ", getEmployeeId()="
				+ getEmployeeId() + ", getJoinDate()=" + getJoinDate()
				+ ", getPerson()=" + getPerson() + ", getCreatedOn()="
				+ getCreatedOn() + ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}
	
	
}

