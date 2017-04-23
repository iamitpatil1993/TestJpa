package com.example.jpa.web.collectionsorting;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.pojo.User;

/**
 * Servlet implementation class UserRestHandler
 */
public class UserRestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;
	
	@EJB
	private ListSortingTestBean listSortingTestBean;
	
	static {
		LOGGER = Logger.getLogger(UserRestHandler.class);
	}
	
	
	public UserRestHandler() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		try {
			
			User user = new User();
			user.setName(request.getParameter("name"));
			user = listSortingTestBean.createUser(user);
			writer.println("User created successfully : " + user);
			
		} catch (InsufficientDataException e) {
			writer.println("Error while creating User : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
