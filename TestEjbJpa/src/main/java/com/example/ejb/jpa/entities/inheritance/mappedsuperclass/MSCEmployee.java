package com.example.ejb.jpa.entities.inheritance.mappedsuperclass;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entitylisteners.MSCEmployeeListener;


/*When an event is raised, the listeners are executed in this order:

*@EntityListeners for a given entity or superclass in the array order
*Entity listeners for the superclasses (highest first)
*Entity Listeners for the entity
*Callbacks of the superclasses (highest first)
*Callbacks of the entity
*
**/


//Entity used for MappedSuerClass demo only(AuditLogging)
@Entity
@Table(name="msc_employee")
@Access(AccessType.FIELD)
@EntityListeners({MSCEmployeeListener.class})
public class MSCEmployee extends AuditLog {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MSCEmployee.class);
	}

	@Id
	@Basic
	@Column(name="employee_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer employeeId;

	@Basic
	@Column(name="first_name")
	private String firstName;

	@Basic
	@Column(name="last_name")
	private String lastName;

	@Transient
	private String fullName;

	@Basic
	@Column(name="salary")
	private Double salary;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_birth")
	private Date dob;

	@Transient
	private Integer age;


	@PrePersist
	@PreUpdate
	@PostLoad
	public void calculateAge() {

		LOGGER.info("Inside calculateAge entity callback ... with dob  :: " + this.dob);
		if(null != this.dob) {
			Calendar birth = new GregorianCalendar();
			birth.setTime(this.dob);
			Calendar now = new GregorianCalendar();
			now.setTime( new Date() );
			int adjust = 0;
			if ( now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
				adjust = -1;
			}
			this.age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;

			LOGGER.info("Calculated age is :: " + this.age);	
		}
	}


	//Getters and setters
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	@Override
	public String toString() {
		return "MSCEmployee [employeeId=" + employeeId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", fullName="
				+ fullName + ", salary=" + salary + ", dob=" + dob + ", age="
				+ age + "]";
	}
	
	
}
