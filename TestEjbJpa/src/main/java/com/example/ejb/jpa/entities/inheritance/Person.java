package com.example.ejb.jpa.entities.inheritance;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.inheritance.mappedsuperclass.AuditLog;

@Entity
@Table(name="person")
@Access(AccessType.FIELD)
public class Person extends AuditLog implements Serializable {

	private static final long serialVersionUID;
	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(Person.class);
		serialVersionUID = 1L;
	}

	@Id
	@Basic
	@Column(name="person_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer personId;

	@Column(name="first_name")
	private String fName;

	@Basic
	@Column(name="last_name")
	private String lName;

	@Transient
	private String fullName;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_birth")
	private Date dob;

	@Transient
	private Integer age;


	//Entity Life cycle callback
	@PostLoad
	private void calcuateAge() {

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
	
	
	//Getter and Setters
	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getFullName() {
		return this.fName + " " + this.lName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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


	@Override
	public String toString() {
		return "Person [personId=" + personId + ", fName=" + fName + ", lName="
				+ lName + ", fullName=" + fullName + ", dob=" + dob + ", age="
				+ age + ", getCreatedOn()=" + getCreatedOn()
				+ ", getLastUpdatedOn()=" + getLastUpdatedOn()
				+ ", isDeleted()=" + isDeleted() + "]";
	}

	
	
}
