package com.example.ejb.jpa.entities.relationships.onetooneunidirectional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.example.ejb.jpa.entities.collectionsorting.indexedlist.Phone;
import com.example.ejb.jpa.entities.collectionsorting.nonindexedlist.Relative;
import com.example.ejb.jpa.entities.elementcollection.PlacesLived;
import com.example.ejb.jpa.entities.relationships.manytomany.Project;
import com.example.ejb.jpa.entities.relationships.onetoonebidirectional.ParkingLot;

@Entity
@Table(name="user")
@Access(AccessType.FIELD)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Integer is best data type to be used as primary key column in entity. Don't use long. Range of Int is quite large so no need of large.
	//Using large as primary key will un-necessarily increase memory usage.
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	@Basic
	private Integer userId;

	@Column(name="name")
	private String name;

	//this is one-to-one uni-directional relationship from user --> desk
	//so user- source entity and desk --> target entity
	//Since it is one directional relationshipe, and desk entity won't be having reference back to this entity. We will choose to keep foreign key (JoinColumn)
	//in this entity only.
	//User - Owning Entity, Desk- Non-owning entity or inverse entity


	//Check what is difference between CascadeType.REMOVE and orphalRemove
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="desk_id")
	private Desk desk;

	@OneToOne(mappedBy="parkingLotUser", fetch=FetchType.LAZY)
	private ParkingLot parkingLot;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="user_project",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="project_id")
			)
	private List<Project> projects;

	//1.Rule one must use collection interfaces opposed to concrete implementation classes as collection attributes.
	//2.Instantiate collection attribute in entity loading, initialization or construction time.
	//3.Set orderBy clause which will be used while loading this list first time into persistence context. This ordering takes place at database level
	//i.e it will add "order by firstName ASC, lastName ASC" clause in select query. So very much efficient for large data as well
	//Default ordering will be by primary key of association in ascending order
	@OneToMany(mappedBy="user")
	@OrderBy("firstName ASC, lastName ASC")
	private List<Relative> relatives = new ArrayList<Relative>();


	//Define order column to persist order in database, this column will be used internally by provider
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
	private List<Phone> phones = new ArrayList<Phone>();

	//Element Collection
	//1.We need to tell provider that this one is element collection using ElementCollection annotation
	//2.We should use @CollectionTable annotation to override default provided by provider. To customize table name, schema, join column
	//3.Join column in Collection table will be foreign key to entity which embedding the list i.e parent
	//4.Optionally we can specify fetch type in @ElementCollection annotation which is FetchType.LAZY by default
	//5.Optionally we can use @AttributeOverride and @AttributeOverrides annotations to override the one or more attribute in embeddable class 
	@ElementCollection(fetch=FetchType.LAZY)
	@CollectionTable(name="user_places",
					joinColumns=@JoinColumn(name="user_id")
					)
	@OrderColumn
	private List<PlacesLived> places = new ArrayList<PlacesLived>();
	
	
	//Element collection without embedded object, element collection of built in java types which does not require separate object/class to be defined
	@ElementCollection
	@CollectionTable(name="user_nickname", joinColumns=@JoinColumn(name="user_id"))
	@OrderColumn(name="order_index")
	private List<String> nickNames = new ArrayList<String>();
	
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Desk getDesk() {
		return desk;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}


	public List<Relative> getRelatives() {
		return relatives;
	}

	public void setRelatives(List<Relative> relatives) {
		this.relatives = relatives;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	//method to add relative to user's relative list 
	public void addRelative(Relative relative) {
		this.relatives.add(relative);
		relative.setUser(this);
	}

	//method to add phone to user's phone list
	public void addPhone(Phone phone) {
		this.phones.add(phone);
		phone.setUser(this);
	}

	public List<PlacesLived> getPlaces() {
		return places;
	}

	public void setPlaces(List<PlacesLived> places) {
		this.places = places;
	}
	
	public void addPlace(PlacesLived place) {
		
		if(null != place)
			this.places.add(place);
	}

	public List<String> getNickNames() {
		return nickNames;
	}

	public void setNickNames(List<String> nickNames) {
		this.nickNames = nickNames;
	}
	
	public void addNickName(String name) {
		this.nickNames.add(name);
	}
}
