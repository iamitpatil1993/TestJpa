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
import com.example.pojo.PlacesLived;
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

	
	public void addPlace(PlacesLived place) throws InvalidDataException {

		if(place != null && place.getUserId() != null) {

			//since element collection's can't be operated on directly since they are not entity. Any operation to be performed on them
			//must be done via it's parent
			//Therefore using find() method belong to get user and save places live for that user, otherwise we would gave used getReference
			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, place.getUserId());
			if(null != user) {

				//just create embedded object and add it to element collection in parent and save parent
				com.example.ejb.jpa.entities.elementcollection.PlacesLived placesLived = new com.example.ejb.jpa.entities.elementcollection.PlacesLived(place.getState(), place.getCity(), place.getCountry());
				user.addPlace(placesLived);
				em.merge(user);

			} else {
				throw new InvalidDataException("Invalid user id : " +  place.getUserId());
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void displayUserPlaces(Integer userId) throws InsufficientDataException {

		if(null != userId) {

			//since element collection's can't be operated on directly since they are not entity. Any operation to be performed on them
			//must be done via it's parent
			//Therefore using find() method belong to get user and save places live for that user, otherwise we would gave used getReference
			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, userId);
			if(null != user) {

				//since collection mappings are by default get fetched lazily, this list is also expected to be fetched lazily
				LOGGER.info("UserPlaces are : ");
				for(com.example.ejb.jpa.entities.elementcollection.PlacesLived place : user.getPlaces()) {
					LOGGER.info(place);
				}
			}
		}
		else {
			throw new InsufficientDataException("UserId is null");
		}
	}
	
	
	

//PLACES EAGER FETCH uses Hibernate FetchMode.JOIN fetch strategy, which gets the data of related entities using LEFT JOIN
//this strategy is not efficient in case of collections becuase it finds Cartesian product of parent and related entitys	
/*
08:35:01,382 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select user0_.user_id as user1_1_0_, user0_.desk_id as desk3_1_0_, user0_.name as name1_0_, places1_.user_id as user1_1_2_, places1_.city as city2_, places1_.country as country2_, places1_.state as state2_ from user user0_ left outer join user_places places1_ on user0_.user_id=places1_.user_id where user0_.user_id=?
08:35:01,415 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select parkinglot0_.parking_lot_id as parking1_16_0_, parkinglot0_.created_on as created2_16_0_, parkinglot0_.last_updated_on as last3_16_0_, parkinglot0_.parking_lot_user_id as parking4_16_0_ from parking_lot parkinglot0_ where parkinglot0_.parking_lot_user_id=?
08:35:01,418 INFO  [com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean] (http--0.0.0.0-8080-1) UserPlaces are : 
08:35:01,419 INFO  [com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean] (http--0.0.0.0-8080-1) PlacesLived [state=Maharastra, city=Pune, country=India]
08:35:01,419 INFO  [com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean] (http--0.0.0.0-8080-1) PlacesLived [state=Maharastra, city=Pune, country=India]
*/
	
	
//PLACES LAZY STRATEGY USS Hibernate FetchMode.SELECT fetch strategy, which gets the data of related entities using (Nn+1) select statements, hence causes, 
//(N+1) select problem	
/*	
08:38:05,741 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select user0_.user_id as user1_4_0_, user0_.desk_id as desk3_4_0_, user0_.name as name4_0_ from user user0_ where user0_.user_id=?
08:38:05,767 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select parkinglot0_.parking_lot_id as parking1_8_0_, parkinglot0_.created_on as created2_8_0_, parkinglot0_.last_updated_on as last3_8_0_, parkinglot0_.parking_lot_user_id as parking4_8_0_ from parking_lot parkinglot0_ where parkinglot0_.parking_lot_user_id=?
08:38:05,769 INFO  [com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean] (http--0.0.0.0-8080-1) UserPlaces are : 
08:38:05,770 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select places0_.user_id as user1_4_0_, places0_.city as city0_, places0_.country as country0_, places0_.state as state0_ from user_places places0_ where places0_.user_id=?
08:38:05,773 INFO  [com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean] (http--0.0.0.0-8080-1) PlacesLived [state=Maharastra, city=Pune, country=India]
08:38:05,774 INFO  [com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean] (http--0.0.0.0-8080-1) PlacesLived [state=Maharastra, city=Pune, country=India]

*/	
	


}
