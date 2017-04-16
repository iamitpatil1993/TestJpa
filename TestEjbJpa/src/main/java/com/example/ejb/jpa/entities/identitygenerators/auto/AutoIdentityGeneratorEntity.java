package com.example.ejb.jpa.entities.identitygenerators.auto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auto_identity_generator")
public class AutoIdentityGeneratorEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Auto generator strategy will choose one of identity, sequence, table based on hibernate dialect set in persistence.xml. Thisi is because it check the dialect and
	//selects the strategy that will be best suited for that dialect. For example for mysql it may choose Identity, for Oracle it will choose sequence.
	//Nothing else need to be specified in case of auto generation strategy.
	//IF schema generation is on then provider will create actual database resource specific to strategy with default setting and names. For example it will create hibername_sequences
	//table if it choose table generator strategy. Or database sequence with defaul name and setting if it choose sequence as generation strategy.
	//If schema generation is on , then it's our responsibility to determine which strategy provider will choose and will need to create all database resources with default settings and names
	//that provider will assume specific to strategy it chooses.
	
	
	
	//It created new table hibernate_sequence with single column next_value with one row value of 1
	//SO it created one table for entity. So it internally choose tablestrategy and not Identity, be cause it created one new table with one sequence in it
	//and no auto-increment on primary key of entity with auto generation strategy
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}  


}
