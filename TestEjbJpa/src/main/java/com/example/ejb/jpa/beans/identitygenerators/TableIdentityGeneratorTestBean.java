package com.example.ejb.jpa.beans.identitygenerators;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.identitygenerators.table.TableGeneratorCustomEntity;
import com.example.ejb.jpa.entities.identitygenerators.table.TableGeneratorCustomEntity1;
import com.example.ejb.jpa.entities.identitygenerators.table.TableIdentityGeneratorEntity;
import com.example.ejb.jpa.entities.identitygenerators.table.TableIdentityGeneratorEntity1;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TableIdentityGeneratorTestBean {
	
	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(TableIdentityGeneratorTestBean.class);
	}
	
	
	@PersistenceContext(unitName="JPADB")
	private EntityManager em;
	
	public void create() {
	
		TableIdentityGeneratorEntity generatorEntity = new TableIdentityGeneratorEntity();
		em.persist(generatorEntity);
		
		TableIdentityGeneratorEntity1 identityGeneratorEntity1 = new TableIdentityGeneratorEntity1();
		em.persist(identityGeneratorEntity1);
		
		TableGeneratorCustomEntity customEntity = new TableGeneratorCustomEntity();
		em.persist(customEntity);
		
		TableGeneratorCustomEntity1 generatorCustomEntity1 = new TableGeneratorCustomEntity1();
		em.persist(generatorCustomEntity1);
		
		LOGGER.info("TableIdentityGeneratorEntity created id : " + generatorEntity.getId());
		LOGGER.info("TableIdentityGeneratorEntity1 created id : " + identityGeneratorEntity1.getId());
		LOGGER.info("TableGeneratorCustomEntity created id : " + customEntity.getId());
		LOGGER.info("TableGeneratorCustomEntity created id : " + generatorCustomEntity1.getId());
	}

}
