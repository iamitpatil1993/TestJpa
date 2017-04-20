package com.example.ejb.jpa.entities.relationships.onetoonebidirectional;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.ejb.jpa.entities.embedded.AuditLogging;
import com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User;

@Entity
@Table(name="parking_lot")
@Access(AccessType.FIELD)
public class ParkingLot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="parking_lot_id")
	@Basic
	private Integer parkingLotId;
	
	//Owning entity
	//joincolumn annotation is completely optional, we can omit it and provider will assume default values. We can omit it of we are happy with default values
	//otherwise must override it u
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parking_lot_user_id")
	private User parkingLotUser;

	//Overriding single attribute so using @AttributeOverride annotation, if want to override multiple attributes in embedded class then use plural  @AttributeOverrides
	//name - name of attribute in embedded class type, and column is just column definition that we want to override, we can't override other things like type 
	@Embedded
	@AttributeOverride(name="updatedOn", column=@Column(name="last_updated_on"))
	private AuditLogging auditLogging;
	
	public User getParkingLotUser() {
		return parkingLotUser;
	}

	public void setParkingLotUser(User parkingLotUser) {
		this.parkingLotUser = parkingLotUser;
	}

	public Integer getParkingLotId() {
		return parkingLotId;
	}

	public void setParkingLotId(Integer parkingLotId) {
		this.parkingLotId = parkingLotId;
	}

	public AuditLogging getAuditLogging() {
		return auditLogging;
	}

	public void setAuditLogging(AuditLogging auditLogging) {
		this.auditLogging = auditLogging;
	} 
}
