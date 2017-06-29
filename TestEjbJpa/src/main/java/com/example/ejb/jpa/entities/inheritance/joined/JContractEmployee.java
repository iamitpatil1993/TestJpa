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
@Access(AccessType.FIELD)
@Table(name="j_contract_employee")
@DiscriminatorValue("JContractEmployee")
@PrimaryKeyJoinColumn(name="contract_employee_id", referencedColumnName="employee_id")
public class JContractEmployee extends JEmployee implements Serializable {

	private static final long serialVersionUID = 1322912253049522744L;
	
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
		return "JContractEmployee [dailyRate=" + dailyRate + ", term=" + term
				+ ", getEmployeeId()=" + getEmployeeId() + ", getJoinDate()="
				+ getJoinDate() + ", getPerson()=" + getPerson()
				+ ", getEmpType()=" + getEmpType() + ", getCreatedOn()="
				+ getCreatedOn() + ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}
}
