package com.example.ejb.jpa.entities.enumerated;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.ejb.jpa.utils.Gender;


@Entity
@Table(name="enum_mappings")
public class EnumeratedMappingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//We don't need to add any annotation, default values will get apply.
	//Provider will see the attribute type is enum , so it will map it to database by considering default mapping
	//type ORDINAL
	//all other annotations Basic, Column are applicable here
	
	@Column(name="gender_default")
	private Gender genderDefault;
	
	//no annotation required here default value EnumType.ORDINAL will get applied and this ciolumn will get mapped to integer
	@Column(name="gender_ordinal")
	private Gender genderOrdinal;
	
	
	//Need Enumerated annotations here, this column will get mapped to String column in database.
	@Enumerated(EnumType.STRING)
	@Column(name="gender_string")
	private Gender genderString;
	
	//TO map enums whose ordinal values are set using constructor in enum, we must use one of the solution that mentioned in pdfs that is stored in 
	//JPA Online/ Enumerated folder.
	//Attribute converter is one of the best solution introduced on Jpa 2.1 and above. I Can't try this becuase this feature requires wildfy App. server


	public Gender getGenderDefault() {
		return genderDefault;
	}


	public void setGenderDefault(Gender genderDefault) {
		this.genderDefault = genderDefault;
	}


	public Gender getGenderOrdinal() {
		return genderOrdinal;
	}


	public void setGenderOrdinal(Gender genderOrdinal) {
		this.genderOrdinal = genderOrdinal;
	}


	public Gender getGenderString() {
		return genderString;
	}


	public void setGenderString(Gender genderString) {
		this.genderString = genderString;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "EnumeratedMappingEntity [id=" + id + ", genderDefault="
				+ genderDefault + ", genderOrdinal=" + genderOrdinal
				+ ", genderString=" + genderString + "]";
	}
	
	
}
