package com.example.ejb.jpa.entitylisteners;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.inheritance.mappedsuperclass.AuditLog;

//Entity Listener Must be a Concrete class, it must not be Abstract class, coz, Entity Manager itself requires instace if this class.
public class AuditListener {

	private static final Logger LOGGER; 
	
	static {
		LOGGER = Logger.getLogger(AuditListener.class);
	}

	@PrePersist
	private void prePersist(AuditLog entity) {

		LOGGER.info("Inside AuditListener.prePersist callback ...");
		Date now = Calendar.getInstance().getTime();

		entity.setCreatedOn(now);
		entity.setLastUpdatedOn(now);
	}

	
	@PreUpdate
	private void preUpdate(AuditLog entity) {

		LOGGER.info("Inside AuditListener.preUpdate callback ...");
		Date now = Calendar.getInstance().getTime();
		entity.setLastUpdatedOn(now);
	}
	
	@PostLoad
	private void postLoad(AuditLog auditLog) {
		
		//Nothinf to do here as such
		//added to check invocation sequence
		LOGGER.info("Inside AuditListener.postLoad...");
	}
}
