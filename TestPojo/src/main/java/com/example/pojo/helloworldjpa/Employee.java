package com.example.pojo.helloworldjpa;

import java.util.Date;

public class Employee {

	private Integer employeeId;
	private String name;
	private Float salary;
	private Date dateOfJoining;
	private boolean isDeleted;
	private Date dob;
	private String fNamel;
	private String lNSame;
	
	public Employee(Integer employeeId, String name, Float salary,
			Date dateOfJoining, boolean isDeleted) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.salary = salary;
		this.dateOfJoining = dateOfJoining;
		this.isDeleted = isDeleted;
	}
	
	public Employee() {
	
		//Nothing to do here
	}
	
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
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	
	public String getlNSame() {
		return lNSame;
	}

	public void setlNSame(String lNSame) {
		this.lNSame = lNSame;
	}

	public String getfNamel() {
		return fNamel;
	}

	public void setfNamel(String fNamel) {
		this.fNamel = fNamel;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name
				+ ", salary=" + salary + ", dateOfJoining=" + dateOfJoining
				+ ", isDeleted=" + isDeleted + ", dob=" + dob + ", fNamel="
				+ fNamel + ", lNSame=" + lNSame + "]";
	}
	

	
}
