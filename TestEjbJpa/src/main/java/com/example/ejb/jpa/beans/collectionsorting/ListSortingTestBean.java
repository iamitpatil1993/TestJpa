package com.example.ejb.jpa.beans.collectionsorting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

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

			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User userEntity = new com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User();
			userEntity.setName(user.getName());
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

}
