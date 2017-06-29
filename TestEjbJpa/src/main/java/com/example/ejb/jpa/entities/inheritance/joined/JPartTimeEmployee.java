package com.example.ejb.jpa.entities.inheritance.joined;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="j_part_time_employee")
@Access(AccessType.FIELD)
@DiscriminatorValue("JPartTimeEmployee")
@PrimaryKeyJoinColumn(name="part_time_employee_id", referencedColumnName="employee_id")
public class JPartTimeEmployee extends JCompanyEmployee implements Serializable {

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
		return "JPartTimeEmployee [hourlyRate=" + hourlyRate
				+ ", getVacation()=" + getVacation() + ", getEmployeeId()="
				+ getEmployeeId() + ", getJoinDate()=" + getJoinDate()
				+ ", getPerson()=" + getPerson() + ", getEmpType()="
				+ getEmpType() + ", toString()=" + super.toString()
				+ ", getCreatedOn()=" + getCreatedOn()
				+ ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}
}
