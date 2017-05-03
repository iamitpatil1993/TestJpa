package com.example.ejb.jpa.entities.blogpostapp;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.ejb.jpa.entities.embedded.AuditLogging;
import com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User;

@Entity
@Table(name="post")
@Access(AccessType.FIELD)
public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="post_id")
	@Basic
	private Integer postId;
	
	@Column(name="title")
	@Basic
	private String title;
	
	@Column(name="text")
	@Basic
	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date postedOn;

	@Embedded
	AuditLogging auditLogging;
	
	@ManyToOne
	@JoinColumn(name="postedby_user_id")
	private User user;

	@OneToMany(mappedBy="post", cascade=CascadeType.REMOVE)
	private Set<Tag> tags;
	
	public Post(String title, String text) {
		super();
		this.title = title;
		this.text = text;
		this.auditLogging = new AuditLogging();
	}
	
	public Post() {
		super();
		this.auditLogging = new AuditLogging();
	}

	
	//Audit Listeners
	@PrePersist
	private void prePersist() {
		
		this.postedOn = new Date();
		this.auditLogging.setCreatedOn(new Date());
		this.auditLogging.setUpdatedOn(new Date());
	}
	
	@PreUpdate
	private void preUpate() {
		
		this.auditLogging.setUpdatedOn(new Date());
	}
	
	//Getter and setters
	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		
		return null == this.postId ? super.hashCode() : this.postId;
	}
	
	@Override
	public boolean equals(Object post) {
		
		Post post2 = null;
		if(this == post)
			return true;
		
		if(null == post)
			return false;
		
		if(Post.class != post.getClass())
			return false;
		else
			post2 = (Post) post;
		
		if(this.postId == null || post2.getPostId() == null)
			return false;
		else if (this.postId.equals(post2.getPostId())) {
			return true;
		}
		else 
			return false;
	}
}
