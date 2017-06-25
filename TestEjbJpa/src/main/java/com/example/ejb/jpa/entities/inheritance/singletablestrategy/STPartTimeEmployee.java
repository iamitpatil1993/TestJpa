package com.example.ejb.jpa.entities.inheritance.singletablestrategy;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//This class will be a concrete class, which we will actually persist
//Since this class is persistent in SINGLE_TABLE inheritance strategy, we need to provide @DiscriminatorValue() annotation here
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue(value="STPartTimeEmployee")
public class STPartTimeEmployee extends STCompanyEmployee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic
	@Column(name="hourly_rate")
	private Integer hourlyRate;

	//Getters and Setters 
	public Integer getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(Integer hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	@Override
	public String toString() {
		return "STPartTimeEmployee [hourlyRate=" + hourlyRate
				+ ", getVacation()=" + getVacation() + ", getEmployeeId()="
				+ getEmployeeId() + ", getJoinDate()=" + getJoinDate()
				+ ", getPerson()=" + getPerson() + ", getCreatedOn()="
				+ getCreatedOn() + ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}
}
