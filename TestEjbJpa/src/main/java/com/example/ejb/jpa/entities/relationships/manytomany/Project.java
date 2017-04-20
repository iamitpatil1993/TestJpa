package com.example.ejb.jpa.entities.relationships.manytomany;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.ejb.jpa.entities.embedded.AuditLogging;
import com.example.ejb.jpa.entities.relationships.onetooneunidirectional.User;

@Entity
@Table(name="project")
@Access(AccessType.FIELD) 
public class Project implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="project_id")
	@Basic
	private Integer projectId;
	
	private String name;
	
	//Mark user entity as owner of relationship
	//We can choose any of the entity as owner no matter
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="projects")
	private List<User> users;
	
	//Actually this embedded annotation is completely optional, we can sepcify this only when we want to override the attributes in embedded objects.
	@Embedded
	private AuditLogging auditLogging;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuditLogging getAuditLogging() {
		return auditLogging;
	}

	public void setAuditLogging(AuditLogging auditLogging) {
		this.auditLogging = auditLogging;
	}
}
