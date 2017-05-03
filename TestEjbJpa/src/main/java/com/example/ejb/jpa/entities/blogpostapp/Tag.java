package com.example.ejb.jpa.entities.blogpostapp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.ejb.jpa.entities.embedded.AuditLogging;
import com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User;

@Entity
@Table(name="tag")
@Access(AccessType.FIELD)
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tag_id")
	private Integer tagId;

	//Don't want to cascade any operation from child entity to two parent entities
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	//Don't want to cascade any operation from child entity to two parent entities
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;

	//Additional attribute for Many-To-Many relation. If this was not here, I would have used normal @manyToMany annotation	
	@Temporal(TemporalType.TIMESTAMP)
	private Date tagedOn;

	@Embedded
	private AuditLogging auditLogging = new AuditLogging();
	

	//Audit Listeners
	@PrePersist
	private void prePersist() {
		
		this.tagedOn = new Date();
		this.auditLogging.setCreatedOn(new Date());
		this.auditLogging.setUpdatedOn(new Date());
	}
	
	@PreUpdate
	private void preUpate() {
		
		this.auditLogging.setUpdatedOn(new Date());
	}
	

	//Getter and Setters
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Date getTagedOn() {
		return tagedOn;
	}

	public void setTagedOn(Date tagedOn) {
		this.tagedOn = tagedOn;
	}

	@Override
	public String toString() {
		return "Tag [tagId=" + tagId + ", user=" + user + ", post=" + post
				+ ", tagedOn=" + tagedOn + ", auditLogging=" + auditLogging
				+ "]";
	}

	@Override
	public int hashCode() {
		
		int userHash = user != null ? user.hashCode() : 0;
		int postHash = post != null ? post.hashCode() : 0;
		
		return 31 * (userHash + postHash);
	}

	@Override
	public boolean equals(Object other) {

		Tag tag;
		User user;
		Post post = null;
		
		if(this == other)
			 return true;
		
		if(null == other)
			return false;

		if(Tag.class != other.getClass())
			return false;
		else
			tag = (Tag) other;

		user = tag.getUser();
		post = tag.getPost();
		
		if(this.user != null ? !this.user.equals(user) : user != null)
			return false;
			 
		if(this.post != null ? !this.post.equals(post) : post != null)
			return false;
		
		return true;
	}
}
