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
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.Relative;
import com.example.pojo.User;

/**
 * Servlet implementation class RelativeRestHandler
 */
public class RelativeRestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;
	
	@EJB
	private ListSortingTestBean listSortingTestBean;
	
	static {
		LOGGER = Logger.getLogger(RelativeRestHandler.class);
	}
	
	
	public RelativeRestHandler() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		if(request.getParameter("userId") != null) {
			try {
				listSortingTestBean.displayUserRelatives(Integer.parseInt(request.getParameter("userId")));
			} catch (NumberFormatException | InsufficientDataException e) {
				writer.println("Error while getting relatives : " + e.getMessage());
				e.printStackTrace();
			}	
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			
			Relative relative = new Relative(
					request.getParameter("firstName"), 
					request.getParameter("lastName"),
					new User(Integer.parseInt(request.getParameter("userId"))));
			
			relative = listSortingTestBean.addRelative(relative);
			writer.println("relative added successfully : " + relative);
			
		} catch(InvalidDataException dataException) {
			writer.println("Error while adding relative : " + dataException.getMessage());
			dataException.printStackTrace();
		}
	}
}
