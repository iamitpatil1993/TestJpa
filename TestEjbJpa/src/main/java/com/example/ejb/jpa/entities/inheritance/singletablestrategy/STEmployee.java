package com.example.ejb.jpa.entities.inheritance.singletablestrategy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.ejb.jpa.entities.inheritance.Person;
import com.example.ejb.jpa.entities.inheritance.mappedsuperclass.AuditLog;


@NamedQueries({
	
	@NamedQuery(
			name = "STEmployee.findAll",
			query = "SELECT ste FROM STEmployee ste" 
	),
	@NamedQuery(
			name = "STEmployee.findAllEmployeeType",
			query = "SELECT ste FROM STEmployee ste WHERE empType = :empType" 
	),
	@NamedQuery(
			name = "STEmployee.findAllCountByGroup",
			query = "SELECT ste.empType, COUNT(ste.employeeId) FROM STEmployee ste GROUP BY ste.empType" 
	)
	
})
@Entity
@Table(name="st_employee")
@Access(AccessType.FIELD)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="emp_type", discriminatorType=DiscriminatorType.STRING)
public abstract class STEmployee extends AuditLog implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//This ID must be shared by all concrete subclass of Employee
	@Id
	@Basic
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employee_id")
	private Integer employeeId;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="join_date")
	private Date joinDate;
	
	@OneToOne
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	@Column(name="emp_type", updatable=false, insertable=false)
	private String empType;
	
	//Getters and Setters
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "STEmployee [employeeId=" + employeeId + ", joinDate="
				+ joinDate + ", person=" + person + ", empType=" + empType
				+ ", getCreatedOn()=" + getCreatedOn()
				+ ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}
	
	
}
