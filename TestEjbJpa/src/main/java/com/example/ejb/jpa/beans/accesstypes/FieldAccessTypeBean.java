package com.example.ejb.jpa.beans.accesstypes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.accesstypes.FieldAccessEntity;
import com.example.ejb.jpa.localinterfaces.accesstypes.AccessTypeBeanLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FieldAccessTypeBean implements AccessTypeBeanLocal{

	private final static Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(FieldAccessTypeBean.class);
	}
	
	@PersistenceContext(unitName="JPADB")
	EntityManager em;
	
	
	@Override
	public void create() {
	
		//As soon as this transaction starts, persistence context will get created associated for this transaction
		
		FieldAccessEntity  fieldAccessEntity = new FieldAccessEntity();
		fieldAccessEntity.setCompany("Praxify Technologies Pvt Ltd");
		fieldAccessEntity.setName("Amit Patil");
		
		//We must call merge or persist method on entity manager to make this entity class instance managed and persistent.
		//Currently it is just java class
		
		em.persist(fieldAccessEntity);
		
		LOGGER.info("fieldAccessEntity id : " + fieldAccessEntity.getId());
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void read(Integer id) {
		
		FieldAccessEntity fieldAccessEntity = em.find(FieldAccessEntity.class, id);
		
		if(null != fieldAccessEntity) {
			LOGGER.info("fieldAccessEntity : " + fieldAccessEntity);
		}
		else {
			LOGGER.info("fieldAccessEntity NOt Found ");
		}
	}
}
