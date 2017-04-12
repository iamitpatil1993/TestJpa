package com.example.ejb.jpa.beans.accesstypes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.accesstypes.PropertyAccessEntity;
import com.example.ejb.jpa.localinterfaces.accesstypes.AccessTypeBeanLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PropertyAccessTypeBean implements AccessTypeBeanLocal{

	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(PropertyAccessTypeBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	EntityManager em;

	@Override
	public void create() {

		PropertyAccessEntity accessEntity = new PropertyAccessEntity();
		accessEntity.setFirstName("Amit");
		accessEntity.setlName("Patil");

		//We must call merge or persist method on entity manager to make this entity class instance managed and persistent.
		//Currently it is just java class
		em.persist(accessEntity);

		LOGGER.info("prperty accessEntity id : " + accessEntity.getId());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void read(Integer id) {

		PropertyAccessEntity propertyAccessEntity = em.find(PropertyAccessEntity.class, id);

		if(null != propertyAccessEntity) {
			LOGGER.info("propertyAccessEntity : " + propertyAccessEntity);
		}
		else {
			LOGGER.info("propertyAccessEntity NOt Found ");
		}

	}

}
