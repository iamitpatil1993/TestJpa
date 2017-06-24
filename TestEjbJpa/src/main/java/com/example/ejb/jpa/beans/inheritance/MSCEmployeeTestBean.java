package com.example.ejb.jpa.beans.inheritance;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.entities.inheritance.mappedsuperclass.MSCEmployee;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.pojo.helloworldjpa.Employee;

@Stateless
public class MSCEmployeeTestBean {


	private static final Logger LOGGER;

	@PersistenceContext(unitName="JPADB")
	private EntityManager em;

	static {
		LOGGER = Logger.getLogger(MSCEmployeeTestBean.class);
	}

	public MSCEmployeeTestBean() {
		//Nothing to do here
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Employee addMSCEmployee(Employee employee) throws InsufficientDataException {

		if(null != employee) {

			MSCEmployee mscEmployee = new MSCEmployee();
			mscEmployee.setFirstName(employee.getfNamel());
			mscEmployee.setLastName(employee.getlNSame());
			mscEmployee.setDob(employee.getDob());
			mscEmployee.setSalary(Double.parseDouble(employee.getSalary().toString()));

			em.persist(mscEmployee);
			employee.setEmployeeId(mscEmployee.getEmployeeId());
			LOGGER.info("MSC Employee Savd successfully ... " + mscEmployee.getEmployeeId());
		}
		else {
			throw new InsufficientDataException("employee is null");
		}

		return employee;
	}

	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void displayMSCEmployees() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MSCEmployee> query = cb.createQuery(MSCEmployee.class);
		Root<MSCEmployee> mscEmployee = query.from(MSCEmployee.class);
		query.select(mscEmployee);
		
		TypedQuery<MSCEmployee> typedQuery = em.createQuery(query);
		List<MSCEmployee> mscEmployees = typedQuery.getResultList();
		
		LOGGER.info("MSCEMmployee Count :: " + mscEmployees.size());
		
		for (MSCEmployee mscEmployee2 : mscEmployees) {
			LOGGER.info(mscEmployee2);
		}
	}
	
}
