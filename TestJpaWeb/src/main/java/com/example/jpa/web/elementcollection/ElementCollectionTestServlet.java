package com.example.jpa.web.elementcollection;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.collectionsorting.ListSortingTestBean;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.PlacesLived;

public class ElementCollectionTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;

	@EJB
	private ListSortingTestBean sortingTestBean;

	static {
		LOGGER = Logger.getLogger(ElementCollectionTestServlet.class);
	}

	public ElementCollectionTestServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
			sortingTestBean.displayUserPlaces(Integer.parseInt(request.getParameter("userId")));
		} catch (NumberFormatException | InsufficientDataException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		try {

			PlacesLived lived = new PlacesLived(request.getParameter("state"),
					request.getParameter("city"), 
					request.getParameter("country"), 
					Integer.parseInt(request.getParameter("userId")));

			sortingTestBean.addPlace(lived);

		} catch (InvalidDataException e) {
			e.printStackTrace();
			response.getWriter().println(e.getMessage());
		}
	}

}
