package com.example.ejb.jpa.entities.embedded;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//NOTE: Embeddable objects are not first class entities, they are simple java reusable classes that can be embedded into other entities.
//Embedded classes do not have their own existence, they are dependent on embedding entity.

//Embeddable classes takes the access types of embedding entity. So, if we want to maintain the consistency of Access Type taken by embedded class,
//we must explicitly specify the Access Type using @Access annotation.
//This may be a case that, we want to have property access in embedded class, because we want to do some additional operations in getters and setters, and if embed 
//this class in entity which has filed access type, then embedded class will also have field access and our code or logic in getters and setters that we wrote by
//assuming property access will not execute for that entity ever. Therefore it is better to define Access type for embedded objects explicitly

//Embedded objects are meant to be re-usable among entities so, define and design them with re-usability in mind.

@Embeddable
@Access(AccessType.FIELD)
public class AuditLogging {
	
	//Define attributes in exactly same ways as we do in entity.
	//CONSIDER like this all attribute mapping going to be placed in entity class

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="created_on")
	private Date createdOn;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	//We can have relationships as well here as like in entities

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
}
