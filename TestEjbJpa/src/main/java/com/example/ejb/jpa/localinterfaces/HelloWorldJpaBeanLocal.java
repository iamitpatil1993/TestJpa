package com.example.ejb.jpa.localinterfaces;

import java.util.List;

import javax.ejb.Local;

import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.helloworldjpa.Employee;

@Local
public interface HelloWorldJpaBeanLocal {

	public Employee createEmployee(Employee employee) throws InsufficientDataException;
	public Employee findEmployee(Integer employeeId) throws InvalidDataException, InsufficientDataException;
	public Employee updateEmployee(Employee employee) throws InvalidDataException, InsufficientDataException;
	public boolean deleteEmployee(Integer employeeId) throws InvalidDataException, InsufficientDataException;
	public List<Employee> findAllEmployees();
	
}
