package com.example.jpa.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.temporal.TemporalMappingTestBean;

/**
 * Servlet implementation class TemporalMappingTestServlet
 */
public class TemporalMappingTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(TemporalMappingTestServlet.class);
	}

	@EJB
	TemporalMappingTestBean temporalMappingTestBean;
	
	
    public TemporalMappingTestServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		temporalMappingTestBean.display(Integer.parseInt(request.getParameter("id")));
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		temporalMappingTestBean.create();
	}

}
