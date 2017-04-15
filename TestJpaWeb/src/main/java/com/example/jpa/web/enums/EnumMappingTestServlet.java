package com.example.jpa.web.enums;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.enums.EnumMappingTestBean;

/**
 * Servlet implementation class EnumMappingTestServlet
 */
public class EnumMappingTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(EnumMappingTestServlet.class);
	}

	@EJB
	EnumMappingTestBean enumMappingTestBean;
	
    public EnumMappingTestServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		enumMappingTestBean.display(Integer.parseInt(request.getParameter("id")));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		enumMappingTestBean.create();
	}

}
