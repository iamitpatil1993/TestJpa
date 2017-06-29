package com.example.ejb.jpa.entities.inheritance.joined;

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

//Regardless of entity class is concrete or abstract table will get created for this base class
//Inheritance hierarchy will start from here, and not from AuditLog class

@NamedQueries({
	
	@NamedQuery(
			name = "JEmployee.findAll",
			query = "SELECT je FROM JEmployee je" 
	)
})
@Entity
@Access(AccessType.FIELD)
@Table(name="j_employee")
@Inheritance(strategy=InheritanceType.JOINED) //Table per every class, along with this base class as well
@DiscriminatorColumn(name="emp_type", discriminatorType=DiscriminatorType.STRING)
public abstract class JEmployee extends AuditLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
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

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	@Override
	public String toString() {
		return "JEmployee [employeeId=" + employeeId + ", joinDate=" + joinDate
				+ ", person=" + person + ", empType=" + empType
				+ ", getCreatedOn()=" + getCreatedOn()
				+ ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}
}
