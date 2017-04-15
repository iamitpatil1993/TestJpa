package com.example.ejb.jpa.beans.temporal;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.temporal.TemporalMappingEntity;

@Stateless
public class TemporalMappingTestBean {

	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(TemporalMappingTestBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	EntityManager em;

	public void create() {
		
		TemporalMappingEntity temporalMappingEntity = new TemporalMappingEntity(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()),
				new Timestamp(System.currentTimeMillis()), new java.util.Date(), new java.util.Date(), new java.util.Date());
		
		em.persist(temporalMappingEntity);
		LOGGER.info("TemporalMappingEntity saved id : " + temporalMappingEntity.getId());
	}
	
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void display(Integer id) {
		
		TemporalMappingEntity entity = em.find(TemporalMappingEntity.class, id);
		LOGGER.info("TemporalMappingEntity with id : " + id + " is : " + entity);
	}
}
