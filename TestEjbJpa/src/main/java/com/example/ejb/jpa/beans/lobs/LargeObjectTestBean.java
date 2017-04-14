package com.example.ejb.jpa.beans.lobs;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.lobs.LargeObjectEntity;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LargeObjectTestBean {

	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(LargeObjectTestBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer uploadDocument(byte[] imageBytes) {

		LOGGER.info("Inside uploadDocument : " + imageBytes.length);
		
		LargeObjectEntity largeObjectEntity = new LargeObjectEntity();
		largeObjectEntity.setImage(imageBytes);
		em.persist(largeObjectEntity);
		
		LOGGER.info("image saved successfully : " + largeObjectEntity.getId());
		return largeObjectEntity.getId();
	}

}
