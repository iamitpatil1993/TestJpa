package com.example.ejb.jpa.entities.identitygenerators.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


/*Name of table that provider created is hibernate_sequences
Name of the two columns were sequence_name, next_val.
It does not creats separate sequence for each entity.
Name of sequence generated was defalt
Same default sequence row is used for all enities that uses table generation strategy with default settions.
It creates single row for default sequence when actually inserting entity and not at schema/table generration time.

 */
@Entity
@Table(name="table_identity_generator_custom_1")
public class TableGeneratorCustomEntity1 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Here am creating talbeGenerator for this entity with name,and all other detail explicitly
	//So it will create new row for this generator whose name will be same as TableGeneratorCustomEntity1_GENERATOR
	//I am keeping table details same as defualt because I want to reuse same table for alll entities
	//One important thing to note here that, I have specified allocationSize=1 explicitly, because I check it's default value os 50 and it keeps incrementing
	//sequence value by 50 even though I am inserting single entity. So it was creating values as 1, 51, 101, 151, I don't know why this. Actually it should have used the
	//pool of 50 and once pool get completed then only it should have asked for new pool of 50. But in my case it was asking for pool of 50 everytime 
	//entity created. So I overridden then default of 50 and make it to 1.
	//Problem with this is that everytime entity created two queries will get executed on db, one for entity and other to updated sequence valiue for sequence generator for taht entity.

	@Id
	@TableGenerator(name="TableGeneratorCustomEntity1_GEN",
	table="hibernate_sequences",
	pkColumnName="sequence_name",
	valueColumnName="next_val",
	pkColumnValue="TableGeneratorCustomEntity1_GENERATOR",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TableGeneratorCustomEntity1_GEN")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}




}
