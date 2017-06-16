package com.example.ejb.jpa.beans.collectionsorting;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.blogpostapp.Post;
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
			userEntity.setGender(user.getGender());

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


	public void addNickName(String name, Integer userId) throws InvalidDataException {

		if(name != null && !name.isEmpty() && userId != null) {

			//since element collection's can't be operated on directly since they are not entity. Any operation to be performed on them
			//must be done via it's parent
			//Therefore using find() method belong to get user and save places live for that user, otherwise we would gave used getReference
			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, userId);
			if(null != user) {

				//just add name to element collection in parent and save parent
				user.addNickName(name);
				em.merge(user);

			} else {
				throw new InvalidDataException("Invalid user id : " +  userId);
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void displayUserNickNames(Integer userId) throws InsufficientDataException {

		if(null != userId) {

			//since element collection's can't be operated on directly since they are not entity. Any operation to be performed on them
			//must be done via it's parent
			//Therefore using find() method belong to get user and save places live for that user, otherwise we would gave used getReference
			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, userId);
			if(null != user) {

				//since collection mappings are by default get fetched lazily, this list is also expected to be fetched lazily
				LOGGER.info("UserNickNames are : ");
				for(String name : user.getNickNames()) {
					LOGGER.info(name);
				}
			}
		}
		else {
			throw new InsufficientDataException("UserId is null");
		}
	}


	public User updateUser(User user) throws InsufficientDataException, InvalidDataException {

		if(null != user) {

			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User userEntity = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, user.getUserId());
			if(null != userEntity) {

				userEntity.setName(user.getName());
				userEntity.setGender(user.getGender());
				em.merge(userEntity);
			}
			else {

				throw new InvalidDataException("Invalid User id");
			}
		}
		else {
			throw new InsufficientDataException("User is null");
		}
		return user;
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void getUsers() {

		List<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> users = em.createNamedQuery("User.findAllUsers", com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class)
				.getResultList();

		LOGGER.info("users found : " + users.size());
		LOGGER.info("user are : ");
		for(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User user : users)
			LOGGER.info(user);
	}

	public void deleteUser(Integer userId) throws InvalidDataException, InsufficientDataException {

		if(null != userId) {

			//to delete/remove entity we must fetch that etity before removing it. Entity to be deleted must be managed entity
			//in current persistence context.
			com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User userEntity = em.find(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class, userId);

			//Need to check null otherwise provider will throw below exception at runtime and which we dont want to happen
			//javax.ejb.EJBException: java.lang.IllegalArgumentException: attempt to create delete event with null entity
			if(null != userEntity)
				em.remove(userEntity);
			else 
				throw new InvalidDataException("Invalid user id : " + userId);

			//Observe the SQL created for cascade remove of post tags 
		}
		else {
			throw new InsufficientDataException("Use Id to delete : " + userId);
		}
	}

	//SQL generated to delete single user
	/*			08:35:31,197 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select user0_.user_id as user1_15_0_, user0_.desk_id as desk4_15_0_, user0_.gender as gender15_0_, user0_.name as name15_0_ from user user0_ where user0_.user_id=?
			08:35:31,226 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select parkinglot0_.parking_lot_id as parking1_10_0_, parkinglot0_.created_on as created2_10_0_, parkinglot0_.last_updated_on as last3_10_0_, parkinglot0_.parking_lot_user_id as parking4_10_0_ from parking_lot parkinglot0_ where parkinglot0_.parking_lot_user_id=?
			08:35:31,231 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select phones0_.user_id as user3_15_1_, phones0_.phone_id as phone1_1_, phones0_.phone_id as phone1_9_0_, phones0_.phone_number as phone2_9_0_, phones0_.user_id as user3_9_0_ from phone phones0_ where phones0_.user_id=?
			08:35:31,235 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select posts0_.postedby_user_id as postedby7_15_1_, posts0_.post_id as post1_1_, posts0_.post_id as post1_20_0_, posts0_.created_on as created2_20_0_, posts0_.updated_on as updated3_20_0_, posts0_.postedOn as postedOn20_0_, posts0_.text as text20_0_, posts0_.title as title20_0_, posts0_.postedby_user_id as postedby7_20_0_ from post posts0_ where posts0_.postedby_user_id=?
			08:35:31,241 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select tags0_.post_id as post5_20_2_, tags0_.tag_id as tag1_2_, tags0_.tag_id as tag1_18_1_, tags0_.created_on as created2_18_1_, tags0_.updated_on as updated3_18_1_, tags0_.post_id as post5_18_1_, tags0_.tagedOn as tagedOn18_1_, tags0_.user_id as user6_18_1_, user1_.user_id as user1_15_0_, user1_.desk_id as desk4_15_0_, user1_.gender as gender15_0_, user1_.name as name15_0_ from tag tags0_ left outer join user user1_ on tags0_.user_id=user1_.user_id where tags0_.post_id=?
			08:35:31,243 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select parkinglot0_.parking_lot_id as parking1_10_0_, parkinglot0_.created_on as created2_10_0_, parkinglot0_.last_updated_on as last3_10_0_, parkinglot0_.parking_lot_user_id as parking4_10_0_ from parking_lot parkinglot0_ where parkinglot0_.parking_lot_user_id=?
			08:35:31,245 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select projects0_.user_id as user1_15_1_, projects0_.project_id as project2_1_, project1_.project_id as project1_13_0_, project1_.created_on as created2_13_0_, project1_.updated_on as updated3_13_0_, project1_.name as name13_0_ from user_project projects0_ inner join project project1_ on projects0_.project_id=project1_.project_id where projects0_.user_id=?
			08:35:31,247 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select relatives0_.user_id as user4_15_1_, relatives0_.relative_id as relative1_1_, relatives0_.relative_id as relative1_22_0_, relatives0_.first_name as first2_22_0_, relatives0_.last_name as last3_22_0_, relatives0_.user_id as user4_22_0_ from relative relatives0_ where relatives0_.user_id=? order by relatives0_.first_name asc, relatives0_.last_name asc
			08:35:31,248 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select usertags0_.user_id as user6_15_3_, usertags0_.tag_id as tag1_3_, usertags0_.tag_id as tag1_18_2_, usertags0_.created_on as created2_18_2_, usertags0_.updated_on as updated3_18_2_, usertags0_.post_id as post5_18_2_, usertags0_.tagedOn as tagedOn18_2_, usertags0_.user_id as user6_18_2_, post1_.post_id as post1_20_0_, post1_.created_on as created2_20_0_, post1_.updated_on as updated3_20_0_, post1_.postedOn as postedOn20_0_, post1_.text as text20_0_, post1_.title as title20_0_, post1_.postedby_user_id as postedby7_20_0_, user2_.user_id as user1_15_1_, user2_.desk_id as desk4_15_1_, user2_.gender as gender15_1_, user2_.name as name15_1_ from tag usertags0_ left outer join post post1_ on usertags0_.post_id=post1_.post_id left outer join user user2_ on post1_.postedby_user_id=user2_.user_id where usertags0_.user_id=?
			08:35:31,255 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from user_nickname where user_id=?
			08:35:31,256 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from user_places where user_id=?
			08:35:31,259 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from phone where phone_id=?
			08:35:31,260 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from tag where tag_id=?
			08:35:31,261 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from tag where tag_id=?
			08:35:31,262 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from post where post_id=?
			08:35:31,264 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from user where user_id=?
	 */


	void foo() {


		List<Integer> ids = em.createQuery("SELECT f.id from foo f where f.dob > : dob order by f.dob")
				.getResultList();

		List<Object> foos = em.createQuery("select f.fnma, f.lname, f.dob from foo f where f.id IN (:ids)")
				.setParameter("id", ids.subList(0, 100))
				.getResultList();
	}

	public void getUsersByCriteriaAPI(String name) {

		//1. get criteria builder
		CriteriaBuilder cb = em.getCriteriaBuilder();

		//2. Create Criteria
		CriteriaQuery<String> query = cb.createQuery(String.class);

		//3. Create query root
		Root<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> users = query.from(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class);

		//4.Specify select clause
		query.select(users.<String>get("name"));

		if(null != name) {
			query.where(cb.like(users.<String>get("name"), name + "%"));
		}

		TypedQuery<String> typedQuery = em.createQuery(query);
		List<String> userList = typedQuery.getResultList();

		LOGGER.info("users found using Criteria Query are  :: " + userList.size());
		LOGGER.info("user are using Criteria Query are  :: ");
		for(String user : userList)
			LOGGER.info(user);		
	}


	public void displayPostMultiSelectUsingObjectArray(Integer userId) {

		LOGGER.info("Inside displayPostMultiSelectUsingObjectArray with userId :: " + userId);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

		Root<Post> root = query.from(Post.class);
		Join<Post, com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> postedBy = root.join("user", JoinType.LEFT);

		query.select(cb.array(root.get("postId"), root.get("title"), root.get("text"), postedBy.get("name").alias("postedByUser"), root.get("postedOn")));

		//we can achieve same using below code as well
		//query.multiselect(root.get("postId"), root.get("title"), root.get("text"), postedBy.get("name").alias("postedByUser"), root.get("postedOn"));


		if(null != userId) {
			query.where(cb.equal(root.get("user").get("userId"), cb.parameter(Integer.class, "userId")));
		}

		TypedQuery<Object[]> typedQuery = em.createQuery(query);

		if(null != userId) {
			typedQuery.setParameter("userId", userId);
		}

		List<Object[]> posts = typedQuery.getResultList();
		for(Object[] obj : posts) {

			LOGGER.info("postId :: " + obj[0]);
			LOGGER.info("title :: " + obj[1]);
			LOGGER.info("text :: " + obj[2]);
			LOGGER.info("postedByUser :: " + obj[3]);
			LOGGER.info("postedOn :: " + obj[4]);
		}
	}


	public void displayPostMultiselectUsingTuple(Integer userId) {


		LOGGER.info("Inside displayPostMultiselectUsingTuple with userId :: " + userId);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = cb.createTupleQuery();

		Root<Post> post = criteria.from(Post.class);
		Join<Post, com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> user = post.join("user", JoinType.LEFT);

		criteria.select(cb.tuple(post.get("postId").alias("postId"), post.get("title").alias("title"), post.get("text").alias("text"), user.get("name").alias("postedByUser"), post.get("postedOn").alias("postedOn")));

		//we can achieve same result using below code as well
		//criteria.multiselect(post.get("postId").alias("postId"), post.get("title").alias("title"), post.get("text").alias("text"), user.get("name").alias("postedByUser"), post.get("postedOn").alias("postedOn"));

		criteria.orderBy(cb.asc(post.<String>get("title")),  cb.desc(post.<Date>get("postedOn")));

		if(user != null) {
			criteria.where(cb.equal(user.get("userId"), cb.parameter(Integer.class, "userId")));
		}

		TypedQuery<Tuple> typedQuery = em.createQuery(criteria);
		if(userId != null) {
			typedQuery.setParameter("userId", userId);
		}

		List<Tuple> tuples = typedQuery.getResultList();
		for(Tuple tuple : tuples) {

			LOGGER.info("---------------- ++++++++++ ------------------");
			LOGGER.info("postId :: " + tuple.get("postId"));
			LOGGER.info("title :: " + tuple.get("title", String.class));//To avoid manual type casting we can use overloaded method which takes destination type as argument
			LOGGER.info("text :: " + tuple.get("text"));
			LOGGER.info("postedByUser :: " + tuple.get("postedByUser"));
			LOGGER.info("postedOn :: " + tuple.get("postedOn"));
		}
	}


	public void displayPostUsingConstructorExpression(Integer userId) {

		//(Integer userId, String name, Gender gender)
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<com.example.pojo.blogpost.Post> criteria = cb.createQuery(com.example.pojo.blogpost.Post.class);

		Root<Post> post = criteria.from(Post.class);
		Join<Post, com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> user = post.join("user", JoinType.LEFT);

		//(Integer postId, String title, String text, Date postedOn, User user) 
		criteria.select(cb.construct(com.example.pojo.blogpost.Post.class, post.<Integer>get("postId"), post.<String>get("title"), post.<String>get("text"), post.<Date>get("postedOn")));


		criteria.orderBy(cb.asc(post.<String>get("title")),  cb.desc(post.<Date>get("postedOn")));

		if(user != null) {
			criteria.where(cb.equal(user.get("userId"), cb.parameter(Integer.class, "userId")));
		}

		TypedQuery<com.example.pojo.blogpost.Post> typedQuery = em.createQuery(criteria);
		if(userId != null) {
			typedQuery.setParameter("userId", userId);
		}

		List<com.example.pojo.blogpost.Post> posts = typedQuery.getResultList();
		for(com.example.pojo.blogpost.Post tempPost : posts) {

			LOGGER.info("---------------- ++++++++++ ------------------");
			LOGGER.info(tempPost);
		}
	}


	//This is subquery example : Non Co related Subquery
	public void searchUserByPostTitleUsingNonCorelatedSubQuery(String title) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> criteria = cb.createQuery(String.class);

		Root<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> user = criteria.from(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class);
		criteria.select(user.<String>get("name"));

		if(title != null) {

			Subquery<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> subCriteria = criteria.subquery(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class);
			Root<Post> postRoot = subCriteria.from(Post.class);
			Join<Post, com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> postedBy = postRoot.join("user", JoinType.INNER);

			subCriteria.select(postedBy);
			subCriteria.where(cb.like(postRoot.<String>get("title"), cb.parameter(String.class, "title")));

			criteria.where(cb.in(user).value(subCriteria));
		}

		TypedQuery<String> typedQuery = em.createQuery(criteria);

		if(null != title) {

			typedQuery.setParameter("title", "%" + title + "%");
		}

		List<String> userNames = typedQuery.getResultList();

		LOGGER.info("users who has post with title :: " + title + " are as below :: ");
		for(String userName : userNames)
			LOGGER.info("userName :: " + userName);
	}

	//This is subquery example : Non related Subquery, with co-relation in where clause
	public void searchUserByPostTitleUsingCorelatedSubquery(String title) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> criteria =  cb.createQuery(String.class);

		Root<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> user = criteria.from(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class);
		criteria.select(user.<String>get("name"));

		if(title != null) {

			Subquery<Integer> subCriteria = criteria.subquery(Integer.class);
			Root<Post> subRoot = subCriteria.from(Post.class);
			Join<Post, com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> postedByUser = subRoot.join("user", JoinType.INNER);
			subCriteria.select(postedByUser.<Integer>get("userId"));

			subCriteria.where(cb.like(subRoot.<String>get("title"), cb.parameter(String.class, "title")), cb.equal(user, postedByUser));

			criteria.where(cb.exists(subCriteria));
		}

		TypedQuery<String> typedQuery = em.createQuery(criteria);
		if(title != null) {
			typedQuery.setParameter("title", "%" + title + "%");
		}

		for (String userName : typedQuery.getResultList()) {
			LOGGER.info("user ::  " + userName);
		}

	}


	//This is subquery example : Non related Subquery, with co-relation in where clause
	public void searchUserByPostTitleUsingCorelatedSubqueryWithPaentRootInSubQueryJoinClause(String title) {

		LOGGER.info("Inside searchUserByPostTitleUsingCorelatedSubqueryWithPaentRootInSubQueryJoinClause with title :: " + title);

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> criteria =  cb.createQuery(String.class);

		Root<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> user = criteria.from(com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User.class);
		criteria.select(user.<String>get("name"));

		if(title != null) {

			Subquery<Integer> subCriteria = criteria.subquery(Integer.class);

			//correlate() is the method that we use to use parent query roots or join in subquery from clause.
			//we can not use from clause because from clause only takes entity class type as argument and we want to pass Expression instance 
			Root<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User> subRoot = subCriteria.correlate(user);
			Join<com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User, Post> post = subRoot.join("posts", JoinType.INNER);
			subCriteria.select(post.<Integer>get("postId"));

			subCriteria.where(cb.like(post.<String>get("title"), cb.parameter(String.class, "title")));

			criteria.where(cb.exists(subCriteria));
		}

		TypedQuery<String> typedQuery = em.createQuery(criteria);
		if(title != null) {
			typedQuery.setParameter("title", "%" + title + "%");
		}

		for (String userName : typedQuery.getResultList()) {
			LOGGER.info("user ::  " + userName);
		}
	}

}
