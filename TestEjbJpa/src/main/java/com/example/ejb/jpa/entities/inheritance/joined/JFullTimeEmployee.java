package com.example.ejb.jpa.entities.inheritance.joined;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="j_full_time_employee")
@Access(AccessType.FIELD)
@DiscriminatorValue("JFullTimeEmployee")
//I am not using @PrimaryKeyJoinColumn annotation, so name of the primarykey for this table will be same as parent table, which will also act as foreign key
public class JFullTimeEmployee extends JCompanyEmployee implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic
	@Column(name="salary")
	private Integer salary;

	@Basic
	@Column(name="penion")
	private Integer pension;

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
		return "JFullTimeEmployee [salary=" + salary + ", pension=" + pension
				+ ", getVacation()=" + getVacation() + ", getEmployeeId()="
				+ getEmployeeId() + ", getJoinDate()=" + getJoinDate()
				+ ", getPerson()=" + getPerson() + ", getEmpType()="
				+ getEmpType() + ", toString()=" + super.toString()
				+ ", getCreatedOn()=" + getCreatedOn()
				+ ", getLastUpdatedOn()=" + getLastUpdatedOn() + "]";
	}
}
