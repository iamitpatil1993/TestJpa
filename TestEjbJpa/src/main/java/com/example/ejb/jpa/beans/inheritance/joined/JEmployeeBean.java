package com.example.ejb.jpa.beans.inheritance.joined;

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
import com.example.ejb.jpa.entities.inheritance.joined.JContractEmployee;
import com.example.ejb.jpa.entities.inheritance.joined.JEmployee;
import com.example.ejb.jpa.entities.inheritance.joined.JFullTimeEmployee;
import com.example.ejb.jpa.entities.inheritance.joined.JPartTimeEmployee;
import com.example.ejb.jpa.entities.inheritance.singletablestrategy.STEmployee;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.ejb.jpa.localinterfaces.inheritance.InheritanceEmployeeBeanLocal;
import com.example.pojo.generic.StaticConstant.EmployeeDiscriminatorValue;
import com.example.pojo.helloworldjpa.Employee;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JEmployeeBean implements InheritanceEmployeeBeanLocal {

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(JEmployeeBean.class);
	}

	@PersistenceContext(name="JPADB")
	private EntityManager em;


	@Override
	public Employee addEmployee(Employee employee)
			throws InsufficientDataException {

		LOGGER.info("Inside JEmployeeBean.addEmployee with :: " + employee);

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

				JContractEmployee contractEmployee = new JContractEmployee();
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
	public Employee addFullTimeEmployee(Employee employee) throws InsufficientDataException {

		if(employee.getPerson() != null) {

			Person empPerson = em.getReference(Person.class, employee.getPerson().getPersonId());
			if(null != empPerson) {

				JFullTimeEmployee jFullTimeEmployee = new JFullTimeEmployee();
				jFullTimeEmployee.setPerson(empPerson);
				jFullTimeEmployee.setSalary(employee.getStSalary());
				jFullTimeEmployee.setPension(employee.getPension());
				jFullTimeEmployee.setVacation(employee.getVacation());
				jFullTimeEmployee.setJoinDate(employee.getDateOfJoining());

				em.persist(jFullTimeEmployee);
				employee.setEmployeeId(jFullTimeEmployee.getEmployeeId());

				LOGGER.info("FullTime Employee Saved Successfully with id :: " + jFullTimeEmployee.getEmployeeId());
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
	public Employee addPartTimeEmployee(Employee employee) throws InsufficientDataException {

		if(employee.getPerson() != null) {

			Person empPerson = em.getReference(Person.class, employee.getPerson().getPersonId());
			if(null != empPerson) {

				JPartTimeEmployee jPartTimeEmployee = new JPartTimeEmployee();
				jPartTimeEmployee.setPerson(empPerson);
				jPartTimeEmployee.setVacation(employee.getVacation());
				jPartTimeEmployee.setHourlyRate(employee.getHourlyRate());
				jPartTimeEmployee.setJoinDate(employee.getDateOfJoining());

				em.persist(jPartTimeEmployee);
				employee.setEmployeeId(jPartTimeEmployee.getEmployeeId());

				LOGGER.info("PartTime Employee Saved Successfully with id :: " + jPartTimeEmployee.getEmployeeId());
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

		TypedQuery<JEmployee> typedQuery = em.createNamedQuery("JEmployee.findAll", JEmployee.class);
		List<JEmployee> stEmployees = typedQuery.getResultList();

		LOGGER.info("JEmployee found are :: " + stEmployees.size());
		for (JEmployee jEmployee : stEmployees) {
			LOGGER.info(jEmployee);
		}
	}


	@Override
	public void findAllEmpType(EmployeeDiscriminatorValue employeeType)
			throws InvalidDataException {

	
	}

	@Override
	public void findAllCountByGroup() {


	}

}
