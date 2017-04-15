package com.example.ejb.jpa.beans.enums;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.enumerated.EnumeratedMappingEntity;
import com.example.ejb.jpa.utils.Gender;

@Stateless
public class EnumMappingTestBean {
	
	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(EnumMappingTestBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	EntityManager em;

	
	public void create() {
		
		EnumeratedMappingEntity enumeratedMappingEntity = new EnumeratedMappingEntity();
		enumeratedMappingEntity.setGenderDefault(Gender.MALE);
		enumeratedMappingEntity.setGenderOrdinal(Gender.FEMALE);
		enumeratedMappingEntity.setGenderString(Gender.OTHER);

		em.persist(enumeratedMappingEntity);
		LOGGER.info("EnumeratedMappingEntity saved : " + enumeratedMappingEntity.getId());
	}
	
	public void display(Integer id) {
		
		EnumeratedMappingEntity enumeratedMappingEntity  = em.find(EnumeratedMappingEntity.class, id);
		LOGGER.info("EnumeratedMappingEntity with ID + " + id + "IS : " + enumeratedMappingEntity);
	}
}
