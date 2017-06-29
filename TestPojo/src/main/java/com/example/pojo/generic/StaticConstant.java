package com.example.pojo.generic;


final public class StaticConstant {

	public enum Gender{MALE, FEMALE, OTHER};
	public final static String DOB_SIMPLE_DATE_FORMAT= "dd/M/yyyy"; 
	public enum STEmployeeType {ST_CONTRACT, ST_FULL_TIME, ST_PART_TIME}
	public enum STEmployeeDiscriminatorValue {STContractEmployee, STFullTimeEmployee, STPartTimeEmployee}
	public enum JEmployeeDiscriminatorValue {JContractEmployee, JFullTimeEmployee, JPartTimeEmployee}
	public enum EmployeeDiscriminatorValue {ContractEmployee, FullTimeEmployee, PartTimeEmployee}
}

