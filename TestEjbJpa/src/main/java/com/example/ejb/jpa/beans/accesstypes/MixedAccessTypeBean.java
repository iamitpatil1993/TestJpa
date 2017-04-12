package com.example.ejb.jpa.beans.accesstypes;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.accesstypes.MixedAccessEntity;
import com.example.ejb.jpa.localinterfaces.accesstypes.AccessTypeBeanLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MixedAccessTypeBean implements AccessTypeBeanLocal{

	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MixedAccessTypeBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	EntityManager em;

	@Override
	public void create() {

		MixedAccessEntity mixedAccessEntity = new MixedAccessEntity();	
		mixedAccessEntity.setfName("Amit");
		mixedAccessEntity.setlName("patil");

		//We must call merge or persist method on entity manager to make this entity class instance managed and persistent.
		//Currently it is just java class
		em.persist(mixedAccessEntity);

		LOGGER.info("mixedAccessEntity id : " + mixedAccessEntity.getId());
	}

	@Override
	public void read(Integer id) {

		MixedAccessEntity mixedAccessEntity = em.find(MixedAccessEntity.class, id);

		if(null != mixedAccessEntity) {
			LOGGER.info("mixedAccessEntity : " + mixedAccessEntity);
		}
		else {
			LOGGER.info("mixedAccessEntity NOt Found ");
		}


	}

}
