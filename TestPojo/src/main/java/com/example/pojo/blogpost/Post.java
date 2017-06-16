package com.example.pojo.blogpost;

import java.util.Date;

import com.example.pojo.User;

public class Post {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer postId;
	
	private String title;
	
	private String text;
	
	private Date postedOn;

	private User user;

	public Post(String title, String text) {
		super();
		this.title = title;
		this.text = text;
		this.postedOn = new Date();
	}
	
	
	
	public Post(Integer postId, String title, String text, Date postedOn) {
		super();
		this.postId = postId;
		this.title = title;
		this.text = text;
		this.postedOn = postedOn;
	}



	public Post() {
		super();
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

	@Override
	public int hashCode() {
		
		return null == this.postId ? super.hashCode() : this.postId;
	}
	
	@Override
	public boolean equals(Object post) {
		
		Post post2 = null;
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

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", title=" + title + ", text=" + text
				+ ", postedOn=" + postedOn + ", user=" + user + "]";
	}
}
