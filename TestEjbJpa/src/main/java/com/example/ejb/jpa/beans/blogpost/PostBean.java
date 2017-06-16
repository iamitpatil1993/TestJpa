package com.example.ejb.jpa.beans.blogpost;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.blogpost.Post;
import com.example.pojo.blogpost.Tag;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PostBean {

	private static final Logger LOGGER ;

	static {
		LOGGER = Logger.getLogger(PostBean.class);
	}

	@PersistenceContext(unitName="JPADB")
	private EntityManager em;

	public Post addPost(Post post) throws InsufficientDataException {

		if(null != post && post.getUser() != null) {

			//No need to fetch user object, we are not using cascade from user to post, becaus that won't be case in real life
			//No one will create user and user's posy at the same time
			User user = em.getReference(User.class, post.getUser().getUserId());

			com.example.ejb.jpa.entities.blogpostapp.Post postEntity = new com.example.ejb.jpa.entities.blogpostapp.Post(post.getText(), post.getText());
			postEntity.setUser(user);
			em.persist(postEntity);

			LOGGER.info("Post added with postId : " + postEntity.getPostId());
			post.setPostId(postEntity.getPostId());
		}
		else {
			throw new InsufficientDataException("post : " + post);
		}
		return post;
	}

	public Post updatePost(Post post) throws InsufficientDataException {

		if(null != post && post.getPostId() != null) {

			//No need to fetch user object, we are not using cascade from user to post, because that won't be case in real life
			//No one will create user and user's posy at the same time
			com.example.ejb.jpa.entities.blogpostapp.Post postEntity = em.getReference(com.example.ejb.jpa.entities.blogpostapp.Post.class, post.getPostId());
			postEntity.setTitle(post.getTitle());
			postEntity.setText(post.getText());
			
			//eve though we call getReference To get proxy of entity, to avoid fetching data, provider does that and creates proxy with postId
			//but, as sson as we perform merge operation on entity, get makes that entity managed entity in current persistence context. 
			//so unfortunately  all eager fetching will take place here
			//so better way will be to use dynamic query. And most better way will be to use Static/Named queries which are compiled only once
			postEntity = em.merge(postEntity);

			//LOGGER.info("Post updated with postId : " + postEntity.getPostId());
			
		}
		else {
			throw new InsufficientDataException("post : " + post);
		}
		return post;
	}
	
	public void displayPosts(Integer userId) {

		if(null != userId) {

			List<Post> posts = em.createQuery("SELECT new com.example.pojo.blogpost.Post(p.title, p.text) FROM Post p LEFT JOIN p.user u WHERE u.userId = :userId", Post.class)
					.setParameter("userId", userId)
					.getResultList();
			
			LOGGER.info("post foud for user : " + userId + " are : " + posts.size());
			
			for(Post post : posts) 
				LOGGER.info(post);
		}
	}
	

	public Tag tagUser(Tag tag) throws InsufficientDataException {

		if(null != tag && tag.getUser() != null && tag.getPost() != null) {

			//I don't want to fetch any data here, just want proxies so using getReference
			User user = em.getReference(User.class, tag.getUser().getUserId());
			com.example.ejb.jpa.entities.blogpostapp.Post post = em.getReference(com.example.ejb.jpa.entities.blogpostapp.Post.class, tag.getPost().getPostId());

			//Need to add logic to check given user is already taggedfor given post. This check will be manual 
			com.example.ejb.jpa.entities.blogpostapp.Tag tagEntity = new com.example.ejb.jpa.entities.blogpostapp.Tag();
			tagEntity.setUser(user);
			tagEntity.setPost(post);
			em.persist(tagEntity);

			LOGGER.info("tag saved Successfuly with id :  "  + tagEntity.getTagId());
			tag.setTagId(tagEntity.getTagId());
		}
		else {
			throw new InsufficientDataException("Tag : " + tag);
		}
		return tag;
	}
	
	
	//DELETE - Post : This method will hard dellete the post.
	//I am intentionally doing hard delete to test cascade.REmobe of tags entries for post
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean deletePost(Integer postId) throws InsufficientDataException, InvalidDataException {
		
		if(null != postId) {
			
			//to delete/remove entity we must fetch that etity before removing it. Entity to be deleted must be managed entity
			//in current persistence context.
			com.example.ejb.jpa.entities.blogpostapp.Post postEntity = em.getReference(com.example.ejb.jpa.entities.blogpostapp.Post.class, postId);
			
			//Need to check null otherwise provider will throw below exception at runtime and which we dont want to happen
			//javax.ejb.EJBException: java.lang.IllegalArgumentException: attempt to create delete event with null entity
			if(null != postEntity)
				em.remove(postEntity);
			else 
				throw new InvalidDataException("Invalid post id : " + postId);
			
			//Observe the SQL created for cascade remove of post tags 
		}
		else {
			throw new InsufficientDataException("Post Id to delete : " + postId);
		}
		return false;
	}
	
	public void searchPost(Integer userId, String title, String text, String postedBy) {
		
		LOGGER.info("Inside searchPost with userId :: " + userId + " title :: " + title + " text :: " + text + " postedBy :: " + postedBy);
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<com.example.ejb.jpa.entities.blogpostapp.Post> criteria = cb.createQuery(com.example.ejb.jpa.entities.blogpostapp.Post.class);
		Root<com.example.ejb.jpa.entities.blogpostapp.Post> post = criteria.from(com.example.ejb.jpa.entities.blogpostapp.Post.class);
		criteria.select(post);
		Join<com.example.ejb.jpa.entities.blogpostapp.Post, User> postedByUser = post.join("user", JoinType.LEFT);
		
		
		Predicate disjunction = cb.disjunction();
		Predicate conjunction = cb.conjunction();
		if(null != title) {
			disjunction = cb.or(disjunction, cb.like(post.<String>get("title"), cb.parameter(String.class, "title")));	
		}
		
		if(null != text) {
			disjunction = cb.or(disjunction, cb.like(post.<String>get("text"), cb.parameter(String.class, "text")));
		}
		
		if(null != userId) {
			conjunction = cb.and(conjunction, cb.equal(post.get("user").<Integer>get("userId"), cb.parameter(Integer.class, "userId")));
		}
		
		if(null != postedBy) {
			disjunction = cb.or(disjunction, cb.like(postedByUser.<String>get("name"), cb.parameter(String.class, "postedBy")));	
		}
		
		LOGGER.info("disjunction.getExpressions().size() == " + disjunction.getExpressions().size());
		criteria.where(cb.and(conjunction, disjunction.getExpressions().size() == 0 ? disjunction.not() : disjunction));
		
		TypedQuery<com.example.ejb.jpa.entities.blogpostapp.Post> posts = em.createQuery(criteria);
		
		if(null != title)
			posts.setParameter("title", "%" + title + "%");
		
		if(null != text)
			posts.setParameter("text",  "%" + text + "%");
		
		if(null != userId)
			posts.setParameter("userId", userId);
		
		if(null != postedBy)
			posts.setParameter("postedBy", "%" + postedBy + "%");
		
		List<com.example.ejb.jpa.entities.blogpostapp.Post> postList = posts.getResultList();
		LOGGER.info("Post COunt is :: " + postList.size());
		LOGGER.info("Post are :: ");
		
		for(com.example.ejb.jpa.entities.blogpostapp.Post post2 : postList)
			LOGGER.info(post2);
		
	}
	
	
	//This method fetchs users along with their all posts
	//This method uses criteria fetch join to eagerly fetch lazy relation "posts" in single query.
	//Best part of fetch join is that if we use it with Criteria API ALONG WITH DISTINCT and we fetch collection association then, unlike jpql it does not 
	//returns duplicate record of parent for each value child in collection.
	
	//If we do not use distinct in below query it will return duplicate records of user instance for each post.
	//Here we get posts array assigned automatically to each user object.
	//I tried with same in JPQL but could not found way.
	
	//NOTE:Criteia api first select Cartesian product and then creates object graph in memory. So this can be inefficient in case of larger value collections.
	//if collections are small like user addresses, phone numbers then we can go ahead with this approach. But not with large value colletions like 
	//back transactions against saving account 
	public void displayPostUsingCriteriaFetch() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);

		Root<User> user = criteria.from(User.class);
		user.fetch("posts");
		
		criteria.select(user);
		
		//This is most important line. This distinct works as, it select distinct user like it clubs the user object and assign list of it's post to 
		//it
		criteria.distinct(true);
		
		TypedQuery<User> typedQuery = em.createQuery(criteria);
		List<User> users = typedQuery.getResultList();
		
		LOGGER.info("users.size() :: " + users.size());
		for(User tempUser : users) {
		
			LOGGER.info("username :: " + tempUser.getName());
			LOGGER.info("posts :: " + tempUser.getPosts());
		}
	}
	
}

