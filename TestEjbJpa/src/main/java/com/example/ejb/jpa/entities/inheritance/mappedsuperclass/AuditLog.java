package com.example.ejb.jpa.entities.inheritance.mappedsuperclass;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.ejb.jpa.entitylisteners.AuditListener;

//Mapped SuperClass used for auditLogginf
//since we don't want to persist this, we are making it abstract as well
//This class won't be persistent
@MappedSuperclass
@EntityListeners({AuditListener.class})
public abstract class AuditLog {

	private boolean isDeleted;

	@Basic(fetch=FetchType.EAGER, optional=false)
	@Column(name="created_on", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Basic(fetch=FetchType.EAGER, optional=false)
	@Column(name="last_updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedOn;
	
	
	//Getters and Setters
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
