package com.example.ejb.jpa.entities.temporal;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="temporal_mapping")
public class TemporalMappingEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//java.sql tempral types Date, Time, Timestamp do not require any special mapping or metadata anntotations. 
	//The only way JDBC work with database temporal types is through java.sql temporal type classes. Hence we must use java.sql classes in JDBC code if write. java.util 
	//temporal classes won't work in case of JDBC.


	//In case of JPA mappig, if attribute type is java.sql temporal type out of Date, Time, Timestamp then we do not need any additional metadata 
	//aannotation, provider will check the java.sql temporal type and will map it to database type Date, Time or Timestamp
	//but mostly we use java.util temporal classes, so to map them we need to use @Temporal annotation and tell provider to which java.util temporal type
	//Date, Time or Timestamp we want to map java.util Date or Calender class.

	//NO Temporal annotation required here
	@Column(name="sql_date")
	private Date sqlDate; 

	//NO Temporal annotation required here
	@Column(name="sql_time")
	private Time sqlTime; 

	//NO Temporal annotation required here
	@Column(name="sql_timestamp")
	private Timestamp sqlTimestamp; 

	
	//Temporal annotation required here
	@Temporal(TemporalType.DATE)
	@Column(name="util_date")
	private java.util.Date utilDate; 


	//Temporal annotation required here
	@Temporal(TemporalType.TIME)
	@Column(name="util_time")
	private java.util.Date utilTime; 

	//Temporal annotation required here
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="util_timestamp")
	private java.util.Date utilTimestamp;


	//same we can do for java.util.Calender class, no need to test since erverything will be same as java.util.Date mapping as above
	

	public TemporalMappingEntity(Date sqlDate, Time sqlTime,
			Timestamp sqlTimestamp, java.util.Date utilDate,
			java.util.Date utilTime, java.util.Date utilTimestamp) {
		super();
		this.sqlDate = sqlDate;
		this.sqlTime = sqlTime;
		this.sqlTimestamp = sqlTimestamp;
		this.utilDate = utilDate;
		this.utilTime = utilTime;
		this.utilTimestamp = utilTimestamp;
	}
	
	

	public TemporalMappingEntity() {
		super();
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}

	public Time getSqlTime() {
		return sqlTime;
	}

	public void setSqlTime(Time sqlTime) {
		this.sqlTime = sqlTime;
	}

	public Timestamp getSqlTimestamp() {
		return sqlTimestamp;
	}

	public void setSqlTimestamp(Timestamp sqlTimestamp) {
		this.sqlTimestamp = sqlTimestamp;
	}

	public java.util.Date getUtilDate() {
		return utilDate;
	}

	public void setUtilDate(java.util.Date utilDate) {
		this.utilDate = utilDate;
	}

	public java.util.Date getUtilTime() {
		return utilTime;
	}

	public void setUtilTime(java.util.Date utilTime) {
		this.utilTime = utilTime;
	}

	public java.util.Date getUtilTimestamp() {
		return utilTimestamp;
	}

	public void setUtilTimestamp(java.util.Date utilTimestamp) {
		this.utilTimestamp = utilTimestamp;
	}

	@Override
	public String toString() {
		return "TemporalMappingEntity [id=" + id + ", sqlDate=" + sqlDate
				+ ", sqlTime=" + sqlTime + ", sqlTimestamp=" + sqlTimestamp
				+ ", utilDate=" + utilDate + ", utilTime=" + utilTime
				+ ", utilTimestamp=" + utilTimestamp + "]";
	}
	
		
}
