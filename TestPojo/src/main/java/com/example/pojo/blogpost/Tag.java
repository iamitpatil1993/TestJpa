package com.example.pojo.blogpost;

import java.util.Date;

import com.example.pojo.User;

public class Tag {

	private Integer tagId;
	private User user;
	private Post post;
	private Date tagedOn;
	
	
	
	public Tag(User user, Post post) {
		super();
		this.user = user;
		this.post = post;
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
}
