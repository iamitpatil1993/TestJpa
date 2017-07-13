package com.example.ejb.jpa.beans.cache;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.cache.Configuration;

@Stateless
public class CacheTestBean {

	private static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(CacheTestBean.class);
	}

	@PersistenceContext(name="JPADB")
	private EntityManager em;


	public void findConfigById(Integer configId) {

		LOGGER.info("Getting first time");
		Configuration configuration = em.find(Configuration.class, configId);

		if(configuration != null) {
			LOGGER.info("Configuration1 :: " + configuration);
			LOGGER.info("Account Configuration size :: " + configuration.getAccountConfigurations().size());
		}

		LOGGER.info("Getting Second time");
		configuration = em.find(Configuration.class, configId);

		if(configuration != null) {
			LOGGER.info("Configuration2 :: " + configuration);
			LOGGER.info("Account Configuration size :: " + configuration.getAccountConfigurations().size());
		}
	}
}
