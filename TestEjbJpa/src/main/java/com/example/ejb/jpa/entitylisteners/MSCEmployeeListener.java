package com.example.ejb.jpa.entitylisteners;

import javax.persistence.PostLoad;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.inheritance.mappedsuperclass.MSCEmployee;

//No class level annotations for Entity Listeners

//SInce this listener is specific to MSCEmplyee we can directly use MSCEmployee reference variable in callback method signature as argument
public class MSCEmployeeListener {
	
	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(MSCEmployeeListener.class);
	}
	

	@PostLoad
	private void postLoad(MSCEmployee employee) {
	
		LOGGER.info("inside MSCEmployeeListener.postLoad ...");
		employee.setFullName(employee.getFirstName() + " " + employee.getLastName());
	}
	

}
