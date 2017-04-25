package com.example.jpa.web.collectionsorting;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.pojo.Phone;
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
			
			List<Phone> phones = new ArrayList<Phone>();
			String[] phnes = request.getParameterValues("phoneNumber");
			for(String phone : phnes) {
				phones.add(new Phone(null, phone, null));
			}
			
			User user = new User();
			user.setName(request.getParameter("name"));
			user.setPhones(phones);
			user = listSortingTestBean.createUser(user);
			writer.println("User created successfully : " + user);
			
		} catch (InsufficientDataException e) {
			writer.println("Error while creating User : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
