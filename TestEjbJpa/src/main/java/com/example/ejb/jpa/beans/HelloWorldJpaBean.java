package com.example.ejb.jpa.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.ejb.jpa.localinterfaces.HelloWorldJpaBeanLocal;
import com.example.pojo.helloworldjpa.Employee;

//Using local interface view
//beanName will defaults to class name i.e 'HelloWorldJpaBean'

///we are using 'JTA' transaction type and container managed EntityManager
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HelloWorldJpaBean implements  HelloWorldJpaBeanLocal {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(HelloWorldJpaBean.class);
	}

	//using container managed EntityManager
	//All injected resources must be non final, non-static, and should be private
	@PersistenceContext(name = "JPADB")
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee createEmployee(Employee employee) throws InsufficientDataException {

		if(null != employee) {

			//create new entity
			com.example.ejb.jpa.entities.Employee empEntity = new com.example.ejb.jpa.entities.Employee();
			empEntity.setName(employee.getName());
			empEntity.setSalary(employee.getSalary());
			empEntity.setDateOfJoining(employee.getDateOfJoining());

			//at this point persistence context for current transaction for current entity manager will get created.(Frst method call on em)
			//persists makes existing entity insatnce managed as opposed to merge, which creates new copy of entity and makes new copy managed
			em.persist(empEntity);

			employee.setEmployeeId(empEntity.getEmployeeId());
		}
		else {
			throw  new InsufficientDataException("employee instance is null");
		}

		return employee;
	}

	@Override
	//Not using transactions since, it is get call
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Employee findEmployee(Integer employeeId) throws InvalidDataException, InsufficientDataException {

		Employee employeePojo = null;

		if(null != employeeId) {

			com.example.ejb.jpa.entities.Employee employee  = em.find(com.example.ejb.jpa.entities.Employee.class, employeeId);

			//if em can't find entity with given primary key, it will return null, rather than throwing no singleresultException as in casse of Query.getSingleResult()
			//so, must apply null check before use
			if(null != employee) {

				employeePojo = new Employee();
				employeePojo.setName(employee.getName());
				employeePojo.setEmployeeId(employee.getEmployeeId());
				employeePojo.setSalary(employee.getSalary());
				employeePojo.setDateOfJoining(employee.getDateOfJoining());
			}
			else {
				throw new InvalidDataException("Invalide EmployeeId");
			}
		}
		else
		{
			throw new InsufficientDataException("Employee id not frovided");
		}
		return employeePojo;
	}

	@Override
	//Transaction required to perform this operation
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee updateEmployee(Employee employee) throws InvalidDataException, InsufficientDataException {
		
		if(null != employee) {

			com.example.ejb.jpa.entities.Employee employeeEntity  = em.find(com.example.ejb.jpa.entities.Employee.class, employee.getEmployeeId());

			//if em can't find entity with given primary key, it will return null, rather than throwing no singleresultException as in casse of Query.getSingleResult()
			//so, must apply null check before use
			if(null != employeeEntity) {

				//It will automatically update entity in current persistence context, which will get updated in DB at the transaction commit time
				employeeEntity.setName(employee.getName());
				employeeEntity.setSalary(employee.getSalary());
			}
			else {
				throw new InvalidDataException("Invalide EmployeeId");
			}
		}
		else
		{
			throw new InsufficientDataException("Employee id not frovided");
		}
		return employee;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean deleteEmployee(Integer employeeId) throws InvalidDataException, InsufficientDataException {

		boolean returnValue = false;

		if(null != employeeId) {

			//to delete entity, we need to first get that entity, make that entity managed, because entity manager remove() method takes entity as argument
			com.example.ejb.jpa.entities.Employee employee  = em.find(com.example.ejb.jpa.entities.Employee.class, employeeId);

			//if em can't find entity with given primary key, it will return null, rather than throwing no singleresultException as in case of Query.getSingleResult()
			//so, must apply null check before use
			if(null != employee) {

				//remove method will make entity detached from current transaction and will actually delete entity from database on transaction commit phase.
				//remove() method throws IllegalArgumentException - if the instance is not an entity or is a detached entity
				em.remove(employee);
				returnValue = true;
			}
			else {
				throw new InvalidDataException("Invalide EmployeeId");
			}
		}
		else
		{
			throw new InsufficientDataException("Employee id not frovided");
		}
		return returnValue;
	}

	@Override
	//no transaction required here, so no lock on data captured inside this method
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Employee> findAllEmployees() {
	
		//'Static' Query
		List<Employee> employees = em.createNamedQuery("Employee.findAll")
		.getResultList();
		
		return employees;
	}

}
