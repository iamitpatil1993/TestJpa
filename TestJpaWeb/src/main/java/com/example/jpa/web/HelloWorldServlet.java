package com.example.jpa.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.ejb.jpa.localinterfaces.HelloWorldJpaBeanLocal;
import com.example.pojo.helloworldjpa.Employee;


public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(HelloWorldServlet.class);
	}

	@EJB
	HelloWorldJpaBeanLocal beanLocal;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();
		printWriter.append("Served at: ").append(request.getContextPath());

		if(!"Yes".equalsIgnoreCase(request.getParameter("isAll"))) {

			try {

				Integer employeeId = request.getParameter("employeeId") != null ? Integer.valueOf(request.getParameter("employeeId")) : null;
				Employee employee = beanLocal.findEmployee(employeeId);

				printWriter.println("Employee : " + employee);

			} catch (InsufficientDataException e) {
				e.printStackTrace();
				printWriter.println("Employee not provided");
			}
			catch (InvalidDataException e) {
				e.printStackTrace();
				printWriter.println("Invalid Employee Id, can't find employee with gien employeeId : " + request.getParameter("employeeId"));

			}	
		}
		else {

			List<Employee> employees = beanLocal.findAllEmployees();

			printWriter.println("Employee list is as below : ");

			for(Employee employee : employees)
				printWriter.println(employee);
		}
	}	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();
		printWriter.append("Served at: ").append(request.getContextPath());

		try {

			Employee employee = new Employee();
			employee.setName("Amit");
			employee.setSalary(24000f);
			employee.setDateOfJoining(new Date());

			employee = beanLocal.createEmployee(employee);

			printWriter.println("New Employee created successfully with EmployeeId : " + employee.getEmployeeId());

		} catch (InsufficientDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();
		printWriter.append("Served at: ").append(request.getContextPath());

		try {

			Integer employeeId = request.getParameter("employeeId") != null ? Integer.valueOf(request.getParameter("employeeId")) : null;
			String name = request.getParameter("name");
			Float salary = request.getParameter("salary") != null ? Float.valueOf(request.getParameter("salary")) : null;
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setSalary(salary);
			employee.setName(name);

			employee = beanLocal.updateEmployee(employee);

			printWriter.println("\n Employee updated : " + employee);

		} catch (InsufficientDataException e) {
			e.printStackTrace();
			printWriter.println("\nEmployee not provided");
		}
		catch (InvalidDataException e) {
			e.printStackTrace();
			printWriter.println("\nInvalid Employee Id, can't find employee with gien employeeId : " + request.getParameter("employeeId"));

		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();
		printWriter.append("Served at: ").append(request.getContextPath());

		try {

			Integer employeeId = request.getParameter("employeeId") != null ? Integer.valueOf(request.getParameter("employeeId")) : null;
			boolean deleteStaus = beanLocal.deleteEmployee(employeeId);

			printWriter.println("Employee with employeeId : " + (deleteStaus ? "deleted" : "not deleted") );

		} catch (InsufficientDataException e) {
			e.printStackTrace();
			printWriter.println("Employee not provided");
		}
		catch (InvalidDataException e) {
			e.printStackTrace();
			printWriter.println("Invalid Employee Id, can't find employee with gien employeeId : " + request.getParameter("employeeId"));

		}
	}
}
