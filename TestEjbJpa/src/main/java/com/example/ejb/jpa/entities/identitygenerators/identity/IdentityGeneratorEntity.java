package com.example.ejb.jpa.entities.identitygenerators.identity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "identity_identity_generator")
public class IdentityGeneratorEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Nothing else need to be specified. No need to specify generator
	//If schema auto generation is on then provider will make the primary key column auto-increment in underlying database if supported else will throw
	//deploy time exception.
	//If schema auto generation is off, then we must include auto increment clause in primary key column definition.
	//Since next identity value is created after inert statement at table level, identity value won't be available until trasaction commits.
	//ALso unlike table and sequence generators identity generator can not be shared among multiple entities and hence each entity must have it's own.
	//Also unlike table and sequence generators identity generator can not allocate identifiers in blocks
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
