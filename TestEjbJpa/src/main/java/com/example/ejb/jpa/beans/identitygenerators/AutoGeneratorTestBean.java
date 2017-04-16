package com.example.ejb.jpa.beans.identitygenerators;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.identitygenerators.auto.AutoIdentityGeneratorEntity;

//no-interface view
@Stateless
public class AutoGeneratorTestBean {
	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(AutoGeneratorTestBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	private EntityManager em;
	
	//It created new table hibernate_sequence with single column next_value with one row value of 1
	//SO it created one table for entity. So it internally choose tablestrategy and not Identity, be cause it created one new table with one sequence in it
	//and no auto-increment on primary key of entity with auto generation strategy
	public void create() {
		
		AutoIdentityGeneratorEntity autoIdentityGeneratorEntity = new AutoIdentityGeneratorEntity();
		em.persist(autoIdentityGeneratorEntity);
		
		LOGGER.info("AutoIdentityGeneratorEntity created id : " + autoIdentityGeneratorEntity.getId());
		
	}

}
