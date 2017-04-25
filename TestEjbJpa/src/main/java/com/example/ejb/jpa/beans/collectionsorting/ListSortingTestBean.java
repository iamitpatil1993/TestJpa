package com.example.ejb.jpa.beans.collectionsorting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.collectionsorting.indexedlist.Phone;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.Relative;
import com.example.pojo.User;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ListSortingTestBean {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(ListSortingTestBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public User createUser(User user) throws InsufficientDataException {

		if(null != user) {
			List<Phone> userPhones = new ArrayList<Phone>();
			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User userEntity = new com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User();
			userEntity.setName(user.getName());
			
			for(com.example.pojo.Phone phone : user.getPhones()) {
				userPhones.add(new Phone(null, phone.getPhoneNumber(), userEntity));
			}
			
			userEntity.setPhones(userPhones);
			em.persist(userEntity);

			user.setUserId(userEntity.getUserId());	
		}
		else {
			throw new InsufficientDataException("user object is null");
		}

		return user;
	}

	public Relative addRelative(Relative relative) throws InvalidDataException {

		if(relative != null && relative.getUser() != null) {

			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, relative.getUser().getUserId());
			if(null != user) {

				com.example.ejb.jpa.entities.collectionsorting.nonindexedlist.Relative relativeEntity = new com.example.ejb.jpa.entities.collectionsorting.nonindexedlist.Relative(relative.getFirstName(), relative.getLastName());
				relativeEntity.setUser(user);
				em.persist(relativeEntity);

				relative.setRelativeId(relativeEntity.getRelativeId());
			} else {
				throw new InvalidDataException("Invalid user id : " +  relative.getUser().getUserId());
			}
		}

		return relative;
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void displayUserRelatives(Integer userId) throws InsufficientDataException {

		if(null != userId) {

			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, userId);
			if(null != user) {

				//since collection mappings are by default get fetched lazily, this list is also expected to be fetched lazily
				//since we have used @OrderBy annotation, this will sort the list by names
				//See the generated sql for proof of sorting take place at database level efficiently
				LOGGER.info("Relatives are : ");
				for(com.example.ejb.jpa.entities.collectionsorting.nonindexedlist.Relative relative : user.getRelatives()) {
					LOGGER.info(relative);
				}
			}
		}
		else {
			throw new InsufficientDataException("UserId is null");
		}
	}
	
	public com.example.pojo.Phone addPhone(com.example.pojo.Phone phone) throws InvalidDataException {

		if(phone != null && phone.getUser() != null) {

			///Note : We are using em.getReference here opposed to find, because we are not interested in user entity's state, we only want reference to that entity
			//so that we can set user property of phone entity
			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user = em.getReference(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, phone.getUser().getUserId());
			if(null != user) {

				Phone phoneEntity = new Phone(null, phone.getPhoneNumber(), null);
				phoneEntity.setUser(user);
				em.persist(phoneEntity);
			
				phone.setPhoneId(phone.getPhoneId());
			} else {
				throw new InvalidDataException("Invalid user id : " +  phone.getUser().getUserId());
			}
		}

		return phone;
		//GENERATED SQL for for entire transaction is as below, note only one insert query executed on db, no select for user query.
		//08:07:31,595 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: insert into phone (phone_number, user_id) values (?, ?)
	}

}
