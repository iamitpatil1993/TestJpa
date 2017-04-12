package com.example.ejb.jpa.entities.accesstypes;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
//STEP 1. Set Default access type for entity to 'FIELD' as we want
@Access(AccessType.FIELD)
public class MixedAccessEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//FILED Access
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//FILED Access
	private String fName;
	private String lName;

	//PROPERTY ACCESS
	//STEP 3.SET this to transient so that persistence provider will avoid this field while applying field access transformations
	//we are making this transient because, we do't want persistence provider to consider this attribute for field access transformations and from overriding values 
	@Transient
	private String fullName;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//We want to use 'PROPERTY' filed access type for this field. SInce we want to populate full name from first name and last name
	//STEP 2. SET 'PROPERTY' access type for attribute explicitly to OPPOSITE to Default as we want
	@Access(AccessType.PROPERTY)
	@Column(name = "full_name")
	public String getFullName() {
		return (fName != null ? fName : "" )+ " " + (lName != null ? lName : "");
	}

	//note : even though we do not need this fullNAme argument in setter method we must have at for convention purpose
	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	@Override
	public String toString() {
		return "MixedAccessEntity [id=" + id + ", fName=" + fName + ", lName="
				+ lName + ", fullName=" + fullName + "]";
	}


}
