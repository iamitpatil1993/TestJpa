package com.example.ejb.jpa.beans.inheritance.singletable;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.inheritance.Person;
import com.example.ejb.jpa.entities.inheritance.singletablestrategy.STContractEmployee;
import com.example.ejb.jpa.entities.inheritance.singletablestrategy.STEmployee;
import com.example.ejb.jpa.entities.inheritance.singletablestrategy.STFullTimeEmployee;
import com.example.ejb.jpa.entities.inheritance.singletablestrategy.STPartTimeEmployee;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.ejb.jpa.localinterfaces.inheritance.InheritanceEmployeeBeanLocal;
import com.example.pojo.generic.StaticConstant.EmployeeDiscriminatorValue;
import com.example.pojo.generic.StaticConstant.STEmployeeDiscriminatorValue;
import com.example.pojo.helloworldjpa.Employee;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class STEmployeeBean implements InheritanceEmployeeBeanLocal {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(STEmployeeBean.class);
	}

	@PersistenceContext(name="JPADB")
	private EntityManager em;



	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee addEmployee(Employee employee) throws InsufficientDataException {

		LOGGER.info("Inside STEmployeeBean.addEmployee with :: " + employee);

		if(null != employee) {

			switch (employee.getEmpType()) {

			case ContractEmployee:

				employee = this.addContractEmployee(employee);
				break;

			case FullTimeEmployee:

				employee = this.addFullTimeEmployee(employee);
				break;
			case PartTimeEmployee:

				employee = this.addPartTimeEmployee(employee);
				break;

			default:
				throw new InsufficientDataException("Invalid Employee type :: " + employee.getEmpType());
			}	
		}
		else {
			throw new InsufficientDataException("Empoyee instance is null");
		}

		return employee;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee addContractEmployee(Employee employee) throws InsufficientDataException {

		if(employee.getPerson() != null) {

			Person empPerson = em.getReference(Person.class, employee.getPerson().getPersonId());
			if(null != empPerson) {

				STContractEmployee contractEmployee = new STContractEmployee();
				contractEmployee.setPerson(empPerson);
				contractEmployee.setDailyRate(employee.getDailyRate());
				contractEmployee.setJoinDate(employee.getDateOfJoining());
				contractEmployee.setTerm(employee.getTerm());

				em.persist(contractEmployee);
				employee.setEmployeeId(contractEmployee.getEmployeeId());

				LOGGER.info("Contract Employee Saved Successfully with id :: " + contractEmployee.getEmployeeId());
			}
			else  { 
				throw new InsufficientDataException("Invalid Employee personID :: " + employee.getPerson().getPersonId());
			}	
		}
		else {
			throw new InsufficientDataException("Invalid Employee personID :: " + employee.getPerson());
		}

		return employee;
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee addFullTimeEmployee(Employee employee) throws InsufficientDataException {

		if(employee.getPerson() != null) {

			Person empPerson = em.getReference(Person.class, employee.getPerson().getPersonId());
			if(null != empPerson) {

				STFullTimeEmployee stFullTimeEmployee = new STFullTimeEmployee();
				stFullTimeEmployee.setPerson(empPerson);
				stFullTimeEmployee.setSalary(employee.getStSalary());
				stFullTimeEmployee.setPension(employee.getPension());
				stFullTimeEmployee.setVacation(employee.getVacation());
				stFullTimeEmployee.setJoinDate(employee.getDateOfJoining());

				em.persist(stFullTimeEmployee);
				employee.setEmployeeId(stFullTimeEmployee.getEmployeeId());

				LOGGER.info("FullTime Employee Saved Successfully with id :: " + stFullTimeEmployee.getEmployeeId());
			}
			else  { 
				throw new InsufficientDataException("Invalid Employee personID :: " + employee.getPerson().getPersonId());
			}	
		}
		else {
			throw new InsufficientDataException("Invalid Employee personID :: " + employee.getPerson());
		}

		return employee;
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee addPartTimeEmployee(Employee employee) throws InsufficientDataException {

		if(employee.getPerson() != null) {

			Person empPerson = em.getReference(Person.class, employee.getPerson().getPersonId());
			if(null != empPerson) {

				STPartTimeEmployee STPartTimeEmployee = new STPartTimeEmployee();
				STPartTimeEmployee.setPerson(empPerson);
				STPartTimeEmployee.setVacation(employee.getVacation());
				STPartTimeEmployee.setHourlyRate(employee.getHourlyRate());
				STPartTimeEmployee.setJoinDate(employee.getDateOfJoining());

				em.persist(STPartTimeEmployee);
				employee.setEmployeeId(STPartTimeEmployee.getEmployeeId());

				LOGGER.info("PartTime Employee Saved Successfully with id :: " + STPartTimeEmployee.getEmployeeId());
			}
			else  { 
				throw new InsufficientDataException("Invalid Employee personID :: " + employee.getPerson().getPersonId());
			}	
		}
		else {
			throw new InsufficientDataException("Invalid Employee personID :: " + employee.getPerson());
		}

		return employee;
	}


	@Override
	public void findAll() {

		TypedQuery<STEmployee> typedQuery = em.createNamedQuery("STEmployee.findAll", STEmployee.class);
		List<STEmployee> stEmployees = typedQuery.getResultList();

		LOGGER.info("STEmployee found are :: " + stEmployees.size());
		for (STEmployee stEmployee : stEmployees) {
			LOGGER.info(stEmployee);
		}
	}

	@Override
	public void findAllEmpType(EmployeeDiscriminatorValue employeeType) throws InvalidDataException {

		TypedQuery<STEmployee> typedQuery = em.createNamedQuery("STEmployee.findAllEmployeeType", STEmployee.class)
				.setParameter("empType", employeeType.toString());
		List<STEmployee> stEmployees = typedQuery.getResultList();

		LOGGER.info("STEmployee of found are :: " + stEmployees.size() + " OF type :: " + employeeType);
		for (STEmployee stEmployee : stEmployees) {

			LOGGER.info("/n");
			switch (employeeType) {

			case ContractEmployee:

				LOGGER.info(((STContractEmployee)stEmployee).toString());
				break;

			case FullTimeEmployee:

				LOGGER.info(((STFullTimeEmployee)stEmployee).toString());
				break;

			case PartTimeEmployee:

				LOGGER.info(((STPartTimeEmployee)stEmployee).toString());
				break;

			default:
				throw new InvalidDataException("Invalide EMployee Type :: " + employeeType);
			}
		}
	}


	@Override
	public void findAllCountByGroup() {


		List<Object[]> tuples = em.createNamedQuery("STEmployee.findAllCountByGroup").getResultList();

		for (Object[] objects : tuples) {
			LOGGER.info(objects[0] + " :: " + objects[1]);
		}
	}
}	
