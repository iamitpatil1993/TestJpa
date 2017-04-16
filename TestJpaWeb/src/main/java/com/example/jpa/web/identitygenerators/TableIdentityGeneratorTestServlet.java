package com.example.jpa.web.identitygenerators;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.identitygenerators.TableIdentityGeneratorTestBean;

/**
 * Servlet implementation class TableIdentityGeneratorTestServlet
 */
public class TableIdentityGeneratorTestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(TableIdentityGeneratorTestServlet.class);
	}

	@EJB
	private TableIdentityGeneratorTestBean tableIdentityGeneratorTestBean;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		tableIdentityGeneratorTestBean.create();
	}

}
