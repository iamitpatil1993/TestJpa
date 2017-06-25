package com.example.pojo;
import java.util.Date;


public class Person {

	private Integer personId;

	private String fName;

	private String lName;

	private String fullName;

	private Date dob;

	private Integer age;



	public Person(String fName, String lName, Date dob) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.dob = dob;
	}

	public Person(Integer personId) {
		super();
		this.personId = personId;
	}

	public Person() {
		super();

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
				+ age + "]";
	}



}
