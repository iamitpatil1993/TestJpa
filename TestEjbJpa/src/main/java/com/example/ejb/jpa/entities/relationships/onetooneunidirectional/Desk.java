package com.example.ejb.jpa.entities.relationships.onetooneunidirectional;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.ejb.jpa.entities.embedded.AuditLogging;

@Entity
@Table(name="desk")
@Access(AccessType.FIELD)
public class Desk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="desk_id")
	@Basic
	private Integer deskId;

	//Overriding multiple attributes form embedded class therefore using @AttributeOverrides annotation
	@Embedded
	@AttributeOverrides(
			value={
					@AttributeOverride(name="createdOn", column=@Column(name="record_created_on")),
					@AttributeOverride(name="updatedOn", column=@Column(name="last_updated_on")),
			})
	private AuditLogging auditLogging;
	
	public Integer getDeskId() {
		return deskId;
	}

	public void setDeskId(Integer deskId) {
		this.deskId = deskId;
	}

	public AuditLogging getAuditLogging() {
		return auditLogging;
	}

	public void setAuditLogging(AuditLogging auditLogging) {
		this.auditLogging = auditLogging;
	}

	
}
