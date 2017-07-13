package com.example.ejb.jpa.entities.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.example.ejb.jpa.entities.inheritance.mappedsuperclass.AuditLog;

@Entity
@Access(AccessType.FIELD)
@Table(name="configuration")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Configuration extends AuditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="configuration_id")
	private Integer configurationId;
	
	@Column(name="code")
	@Enumerated(EnumType.STRING)
	private String code;
	
	@Column(name="value")
	private String value;
	
	@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="configuration")
	private List<AccountConfiguration> accountConfigurations = new ArrayList<AccountConfiguration>();
	
	public Configuration() {
		super();
		//Nothing to do here
	}
	
	public Configuration(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}

	//Getters and Setters
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(Integer configurationId) {
		this.configurationId = configurationId;
	}

	public List<AccountConfiguration> getAccountConfigurations() {
		return accountConfigurations;
	}

	public void setAccountConfigurations(
			List<AccountConfiguration> accountConfigurations) {
		this.accountConfigurations = accountConfigurations;
	}

	public void addAccountConfiguration(AccountConfiguration accountConfiguration) {
	
		this.accountConfigurations.add(accountConfiguration);
		accountConfiguration.setConfiguration(this);
	}
	
	@Override
	public String toString() {
		return "Configuration [configurationId=" + configurationId + ", code="
				+ code + ", value=" + value + "]";
	}
}
