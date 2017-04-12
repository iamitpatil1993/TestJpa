package com.example.ejb.jpa.entities.accesstypes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PropertyAccessEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String fName;
	private String lName;
	private String fullName;
	
	
	
	//We MUST use metadata annotations on GETTERS for Property access
	
	//In case of Identity strategy, we do not need to provide genenrator, because this job is don be underlying database's 
	//AUto increment feature
	//Column annotation is not used so default vlaue ID will apply for 'name' element
 	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	//Here we want to map fName to first_name column in database. and access it via FirstName not fname
	@Column(name="first_name")
	public String getFirstName() {
		return fName;
	}
	
	public void setFirstName(String firstName) {
		this.fName = firstName;
	}
	
	//Here we do not use column annotation so defalt value LNAME will get apply
	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	@Column(name = "full_name")
	public String getFullName() {
		return (fName != null ? fName : "" )+ " " + (lName != null ? lName : "");
	}

	//note : even though we do not need this fullNAme argument in setter method we must have at for convention purpose
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "PropertyAccessEntity [id=" + id + ", fName=" + fName
				+ ", lName=" + lName + ", fullName=" + fullName + "]";
	}
	
}
