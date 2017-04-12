package com.example.ejb.jpa.entities.accesstypes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 *There is no concept of default access type for entity in JPA specifications. Jpa specifications says, if all fiels are annotated with metadata annotaions then field access will be assumed by persistence provider, if all properties are annotated with metadata annotations then propery access is assumed by persistance provider. If we annotate both fields and properties at same time in entity without proper mixed access steps, behaiour will be un-defined.
 *But, in case of hibernate, the access type for primary key, i.e location where @Id annotation used will determine default access for entity. 
 * 
*/

@Entity
//Not using @table annotation, so table table name will be assumed is "FIELDACCESSENTITY"
public class FieldAccessEntity {
	
	//Default identity generation strategy is 'AUTO'
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String company;
	
	//No getter and setters are required by persistence provider to access enitty state.
	//Persistence provider will access entity state using Reflections
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "FieldAccessEntity [id=" + id + ", name=" + name + ", company="
				+ company + "]";
	}
	
	
}
