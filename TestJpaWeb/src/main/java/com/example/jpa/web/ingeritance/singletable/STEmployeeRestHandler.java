package com.example.jpa.web.ingeritance.singletable;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.inheritance.mappedsuperlass.MSCEmployeeTestBean;
import com.example.ejb.jpa.beans.inheritance.singletable.STEmployeeBean;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.Person;
import com.example.pojo.generic.StaticConstant;
import com.example.pojo.generic.StaticConstant.STEmployeeDiscriminatorValue;
import com.example.pojo.generic.StaticConstant.STEmployeeType;
import com.example.pojo.helloworldjpa.Employee;

/**
 * Servlet implementation class STEmployeeRestHandler
 */
public class STEmployeeRestHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(STEmployeeRestHandler.class);
	}

	public STEmployeeRestHandler() {
		super();
	}

	@EJB
	private STEmployeeBean stEmployeeBean;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();

		Employee employee = new Employee();
		employee.setDailyRate(Integer.parseInt(request.getParameter("dailyRate")));

		SimpleDateFormat dateFormat = new SimpleDateFormat(StaticConstant.DOB_SIMPLE_DATE_FORMAT);

		try {
			employee.setDateOfJoining(dateFormat.parse(request.getParameter("joiningDate")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		employee.setTerm(Integer.parseInt(request.getParameter("term")));
		employee.setPerson(new Person(Integer.parseInt(request.getParameter("personId"))));
		employee.setEmpType(STEmployeeType.valueOf(request.getParameter("empType")));
		employee.setStSalary(Integer.parseInt(request.getParameter("salary")));
		employee.setPension(Integer.parseInt(request.getParameter("pension")));
		employee.setVacation(Integer.parseInt(request.getParameter("vacation")));
		employee.setHourlyRate(Integer.parseInt(request.getParameter("hourlyRate")));

		try {
			stEmployeeBean.addEmployee(employee);
			printWriter.println("Employee Saved Successfully :: " + employee);
			printWriter.flush();
			response.flushBuffer();
		} catch (InsufficientDataException e) {
			LOGGER.error(e.getMessage(), e);
			printWriter.println("Insifficient data provided ...");
			printWriter.flush();
			response.flushBuffer();
		}
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//stEmployeeBean.findAll();
		/*try {
			stEmployeeBean.findAllEmpType(STEmployeeDiscriminatorValue.valueOf(request.getParameter("empType")));
		} catch (InvalidDataException e) {
			PrintWriter printWriter = response.getWriter();
			printWriter.println("Invalid employee type :: " + request.getParameter("empType"));
			printWriter.flush();
			response.flushBuffer();
		}*/
		
		stEmployeeBean.findAllCountByGroup();
	}

}
