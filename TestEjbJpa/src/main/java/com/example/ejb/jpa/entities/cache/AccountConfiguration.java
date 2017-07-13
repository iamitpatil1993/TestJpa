package com.example.ejb.jpa.entities.cache;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.example.ejb.jpa.entities.inheritance.mappedsuperclass.AuditLog;

@Entity	
@Table(name="account_configuration")
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class AccountConfiguration extends AuditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="account_configuration_id")
	private Integer accountConfigurationId;

	@Column(name="value")
	private String value;

	@ManyToOne	
	@JoinColumn(name="configuration_id")
	private Configuration configuration;

	
	//Getters and Setters
	public Integer getAccountConfigurationId() {
		return accountConfigurationId;
	}

	public void setAccountConfigurationId(Integer accountConfigurationId) {
		this.accountConfigurationId = accountConfigurationId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
