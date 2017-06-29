package com.example.ejb.jpa.localinterfaces.inheritance;

import javax.ejb.Local;

import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.generic.StaticConstant.EmployeeDiscriminatorValue;
import com.example.pojo.helloworldjpa.Employee;

@Local
public interface InheritanceEmployeeBeanLocal {

	public Employee addEmployee(Employee employee) throws InsufficientDataException;
	public Employee addContractEmployee(Employee employee) throws InsufficientDataException;
	public Employee addFullTimeEmployee(Employee employee) throws InsufficientDataException;
	public Employee addPartTimeEmployee(Employee employee) throws InsufficientDataException;
	public void findAll();
	public void findAllEmpType(EmployeeDiscriminatorValue employeeType) throws InvalidDataException;
	public void findAllCountByGroup();
}
