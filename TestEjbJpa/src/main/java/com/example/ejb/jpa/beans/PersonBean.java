package com.example.ejb.jpa.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.pojo.Person;

@Stateless
public class PersonBean {
	
	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(PersonBean.class);
	}
	
	public PersonBean() {
		//Nothing to do here
	}
	
	@PersistenceContext(unitName="JPADB")
	private EntityManager em;
	
	
	public Person addPerson(Person person) throws InsufficientDataException {
		
		if(null != person) {
			
			com.example.ejb.jpa.entities.inheritance.Person personEntity = new com.example.ejb.jpa.entities.inheritance.Person();
			personEntity.setDob(person.getDob());
			personEntity.setfName(person.getfName());
			personEntity.setlName(person.getlName());
			
			em.persist(personEntity);
			person.setPersonId(personEntity.getPersonId());
		}
		else {
			throw new InsufficientDataException();
		}
		return person;
	}
	

}
