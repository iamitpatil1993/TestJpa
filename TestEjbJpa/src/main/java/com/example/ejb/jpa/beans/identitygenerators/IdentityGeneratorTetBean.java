package com.example.ejb.jpa.beans.identitygenerators;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.identitygenerators.identity.IdentityGeneratorEntity;

@Stateless
public class IdentityGeneratorTetBean {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(IdentityGeneratorTetBean.class);
	}


	@PersistenceContext(unitName="JPADB")
	private EntityManager em;
	
	@Resource
	private SessionContext sessionContext;

	//I want to check here that what will happen if i try to access the primary key of newly created entity before transaction commits
	//So I am logging the ID field for entity before transaction commits
	//ANS --> It executes the inert statement right at the time we invike persist method on entity manager and then it gets the id from databsea and assigns immediately
	//to entity before waiting for transaction to commit. 
	//So I guess it executes tatements on database while executin the bean method and commits/rollback at the end.
	public void create() {

		IdentityGeneratorEntity identityGeneratorEntity = new IdentityGeneratorEntity();
		em.persist(identityGeneratorEntity);

		try {
			LOGGER.info("Sleeping for 10 secs ....");
			Thread.sleep(10000);

			LOGGER.info("IdentityGeneratorEntity created id : " + identityGeneratorEntity.getId());

			LOGGER.info("Sleeping for again 10 secs ....");
			Thread.sleep(10000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	
	//Here in this case I am trying to insert and then setting rollback true so that transaction will get rollback.
	//Observation -> It executes the insert statement on database and gets the primary key for entity and assigns it to entity as well.
	//But when we set transaction rollback true and when transaction get rollback, entity get removed from database.
	public void createFail() {

		IdentityGeneratorEntity identityGeneratorEntity = new IdentityGeneratorEntity();
		em.persist(identityGeneratorEntity);

		try {
			LOGGER.info("Sleeping for 10 secs ....");
			Thread.sleep(10000);

			LOGGER.info("IdentityGeneratorEntity created id : " + identityGeneratorEntity.getId());

			LOGGER.info("Sleeping for again 10 secs ....");
			
			Thread.sleep(10000);
			
			LOGGER.info("Setting tollback true ... so that trnasaction get rollbacked");
			sessionContext.setRollbackOnly();
		
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	
}
