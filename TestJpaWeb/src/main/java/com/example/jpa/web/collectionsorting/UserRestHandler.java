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
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.Phone;
import com.example.pojo.User;
import com.example.pojo.generic.StaticConstant.Gender;

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

		//listSortingTestBean.getUsers();
		//listSortingTestBean.getUsersByCriteriaAPI(request.getParameter("name"));
		//listSortingTestBean.displayPostMultiSelectUsingObjectArray(null != request.getParameter("userId") ? Integer.valueOf(request.getParameter("userId")) : null);
		//listSortingTestBean.displayPostMultiselectUsingTuple(null != request.getParameter("userId") ? Integer.valueOf(request.getParameter("userId")) : null);
		//listSortingTestBean.displayPostUsingConstructorExpression(null != request.getParameter("userId") ? Integer.valueOf(request.getParameter("userId")) : null);
		
		//listSortingTestBean.searchUserByPostTitleUsingNonCorelatedSubQuery(request.getParameter("title"));
		//listSortingTestBean.searchUserByPostTitleUsingCorelatedSubquery(request.getParameter("title"));
		listSortingTestBean.searchUserByPostTitleUsingCorelatedSubqueryWithPaentRootInSubQueryJoinClause(request.getParameter("title"));
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


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		try {

			User user = new User();
			user.setUserId(Integer.parseInt(request.getParameter("userId")));
			user.setName(request.getParameter("name"));
			user.setGender(Gender.valueOf(request.getParameter("gender")));
			user = listSortingTestBean.updateUser(user);
			writer.println("User updated successfully : " + user);

		} catch (InsufficientDataException e) {
			writer.println("Error while creating User : " + e.getMessage());
			e.printStackTrace();
		} catch (InvalidDataException e) {
			writer.println("Error while updating User : " + e.getMessage());
			e.printStackTrace();
		}
	}

	//Cannot reduce the visibility of the inherited method from HttpServlet, we can increase the visibility but can't reduce
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter writer = response.getWriter();

		try {
			listSortingTestBean.deleteUser(Integer.parseInt(request.getParameter("userId")));

		} catch (NumberFormatException e) {
			writer.println("Invalid number entered : " + request.getParameter("userId"));
			e.printStackTrace();
		} catch (InsufficientDataException e) {
			writer.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidDataException e) {
			writer.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
