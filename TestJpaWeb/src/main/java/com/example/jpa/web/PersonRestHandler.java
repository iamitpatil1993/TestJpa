package com.example.jpa.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.PersonBean;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.pojo.Person;
import com.example.pojo.generic.StaticConstant;

/**
 * Servlet implementation class PersonRestHandler
 */
public class PersonRestHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(PersonRestHandler.class);
	}
	
	@EJB
	private PersonBean personBean;
    
    public PersonRestHandler() {
        super();
    
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter printWriter = response.getWriter();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(StaticConstant.DOB_SIMPLE_DATE_FORMAT);
		Date dob = null;
		try {
			dob = dateFormat.parse(request.getParameter("dob"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		Person person = new Person(request.getParameter("fname"), "lname", dob);
		
		try {
			person = personBean.addPerson(person);
			printWriter.println("person saved successfully :: " + person);
			printWriter.flush();
			response.flushBuffer();
		} catch (InsufficientDataException e) {
			LOGGER.error(e.getMessage(), e);
			printWriter.println("InsufficientData ...");
			printWriter.flush();
			response.flushBuffer();
		}
	}
}
