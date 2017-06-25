package com.example.pojo.helloworldjpa;

import java.util.Date;

import com.example.pojo.Person;
import com.example.pojo.generic.StaticConstant.STEmployeeType;

//We should implement Inheritance at DTO level as well in the same way as we did for entities. But for time being, I am keeping it 
//as a flat structure.
public class Employee {

	private Integer employeeId;
	private String name;
	private Float salary;
	private Date dateOfJoining;
	private boolean isDeleted;
	private Date dob;
	private String fNamel;
	private String lNSame;

	//This is used to distinguish the employee type (as 'DiscriminatorValue')
	private STEmployeeType empType;

	//STContractEmployee Fields
	private Integer dailyRate;
	private Integer term;

	//STPartTimeEmployee fields
	private Integer hourlyRate;

	//STFullTimeEmployee fields
	private Integer stSalary;
	private Integer pension;

	//CompanyEmployee Fields
	private Integer vacation;

	//Employee person
	private Person person;

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

	public Integer getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(Integer dailyRate) {
		this.dailyRate = dailyRate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(Integer hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Integer getStSalary() {
		return stSalary;
	}

	public void setStSalary(Integer stSalary) {
		this.stSalary = stSalary;
	}

	public Integer getPension() {
		return pension;
	}

	public void setPension(Integer pension) {
		this.pension = pension;
	}

	public STEmployeeType getEmpType() {
		return empType;
	}

	public void setEmpType(STEmployeeType empType) {
		this.empType = empType;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getVacation() {
		return vacation;
	}

	public void setVacation(Integer vacation) {
		this.vacation = vacation;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name
				+ ", salary=" + salary + ", dateOfJoining=" + dateOfJoining
				+ ", isDeleted=" + isDeleted + ", dob=" + dob + ", fNamel="
				+ fNamel + ", lNSame=" + lNSame + ", empType=" + empType
				+ ", dailyRate=" + dailyRate + ", term=" + term
				+ ", hourlyRate=" + hourlyRate + ", stSalary=" + stSalary
				+ ", pension=" + pension + ", person=" + person + "]";
	}
}
