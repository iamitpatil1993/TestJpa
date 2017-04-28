package com.example.ejb.jpa.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**To defne new entity class must has following three things
 * 1.@Entity annotation at class level
 * 2.@Id annotation on at least on field or getter
 * 3. Default constructor
*/



/*JPA provider will always look for table in databse with same as 'EntityName'
By fefault entity name is equal to name of class. We can change name of entity
using name element of @Entity annotation.
If don't want to use entity name as underlying database table name, we can use @Table annotation 
with name element specifying name of table to map this entity with*/

@Entity
@Table(name = "employee")
//All Named Queries in JPA are considered to be 'Static Queries' which are get compiled and pre-processed only once during deployment time before actually getting called
@NamedQueries({
		
	@NamedQuery(
				
			//'Static Query' - Less overhead at execution time
			name = "Employee.findAll",
			query = "SELECT NEW com.example.pojo.helloworldjpa.Employee(emp.employeeId, emp.name, emp.salary, emp.dateOfJoining, emp.isDeleted) FROM Employee emp"
	)
		
})
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Integer employeeId;
	
	private String name;
	
	private Float salary;
	
	@Column(name = "date_of_joining")
	private Date dateOfJoining;
	
	private boolean isDeleted;

	//No need of default constructor, as long parameterized constructor is provided. 
	
	//Getters and Setters
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
	
}
