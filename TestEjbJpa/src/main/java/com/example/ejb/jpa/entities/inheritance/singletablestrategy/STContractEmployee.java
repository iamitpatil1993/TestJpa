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
@DiscriminatorValue(value="STContractEmployee")
public class STContractEmployee extends STEmployee implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic
	@Column(name="daily_rate")
	private Integer dailyRate;
	
	@Basic
	@Column(name="term")
	private Integer term;

	//Getters and Setters
	public Integer getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(Integer dailyRate) {
		this.dailyRate = dailyRate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	@Override
	public String toString() {
		return "STContractEmployee [dailyRate=" + dailyRate + ", term=" + term
				+ ", getEmployeeId()=" + getEmployeeId() + ", getJoinDate()="
				+ getJoinDate() + ", getPerson()=" + getPerson()
				+ ", getCreatedOn()=" + getCreatedOn()
				+ ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}
	
	
}
