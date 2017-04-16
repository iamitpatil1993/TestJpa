package com.example.ejb.jpa.entities.identitygenerators.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/*Name of table that provider created is hibernate_sequences
Name of the two columns were sequence_name, next_val.
It does not creats separate sequence for each entity.
Name of sequence generated was defalt
Same default sequence row is used for all enities that uses table generation strategy with default settions.
It creates single row for default sequence when actually inserting entity and not at schema/table generration time.

*/
@Entity
@Table(name="table_identity_generator_1")
public class TableIdentityGeneratorEntity1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
