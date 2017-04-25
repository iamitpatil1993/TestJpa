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
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.Phone;
import com.example.pojo.Relative;
import com.example.pojo.User;

/**
 * Servlet implementation class UserPhoneRestHandler
 */
public class UserPhoneRestHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;

	@EJB
	private ListSortingTestBean listSortingTestBean;

	static {
		LOGGER = Logger.getLogger(UserPhoneRestHandler.class);
	}


	public UserPhoneRestHandler() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter writer = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {

			Phone phone = new Phone(null,
					request.getParameter("phoneNumber"),
					new User(Integer.parseInt(request.getParameter("userId"))));

			phone = listSortingTestBean.addPhone(phone);
			writer.println("phone added successfully : " + phone);

		} catch(InvalidDataException dataException) {
			writer.println("Error while adding phone : " + dataException.getMessage());
			dataException.printStackTrace();
		}
	}

}
