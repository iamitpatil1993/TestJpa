package com.example.jpa.web.ingeritance.mappedsuperclass;

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
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.pojo.generic.StaticConstant;
import com.example.pojo.helloworldjpa.Employee;


public class MSCEmployeeRestHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MSCEmployeeRestHandler.class);
	}

	@EJB
	private MSCEmployeeTestBean mscEmployeeTestBean;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();
				
		
		//DOB_SIMPLE_DATE_FORMAT
		Employee employee = new Employee(null, null, Float.valueOf(request.getParameter("salary")), null, false);
		employee.setfNamel(request.getParameter("fname"));
		employee.setlNSame(request.getParameter("lname"));

		SimpleDateFormat dateFormat = new SimpleDateFormat(StaticConstant.DOB_SIMPLE_DATE_FORMAT);
		try {
			employee.setDob(dateFormat.parse(request.getParameter("dob")));
		} catch (ParseException e) {
			LOGGER.error("Error occured while parsig dob", e);
		}
		
		try {
			employee = mscEmployeeTestBean.addMSCEmployee(employee);
			
			printWriter.println("Employee Saved successfully ... Emploee Details are :: " + employee);
			printWriter.flush();
			response.flushBuffer();
			
		} catch (InsufficientDataException e) {
			e.printStackTrace();
			
			printWriter.println("insufficient data provided");
			printWriter.flush();
			response.flushBuffer();
		}
	}
	
	
	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		
		mscEmployeeTestBean.displayMSCEmployees();
		
	}

}
