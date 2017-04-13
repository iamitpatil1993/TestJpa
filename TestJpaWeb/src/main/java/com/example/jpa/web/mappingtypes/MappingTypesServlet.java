package com.example.jpa.web.mappingtypes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ejb.jpa.beans.mappingtypes.MappingtypesBean;

import org.apache.log4j.Logger;

import com.example.jpa.web.accesstype.AccessTypeServlet;

public class MappingTypesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(MappingTypesServlet.class);
	} 

	@EJB
	private MappingtypesBean mappingtypesBean;
	
	public MappingTypesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
				
		PrintWriter printWriter = response.getWriter(); 
		if(null != mappingtypesBean) {

			printWriter.println("mappingtypesBean injected successfully");
			LOGGER.info("mappingtypesBean injected successfully");
			mappingtypesBean.read(new Integer(request.getParameter("id")));
		}
		else {
			printWriter.println("mappingtypesBean injection failed");
			LOGGER.info("mappingtypesBean injection failed");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

		PrintWriter printWriter = response.getWriter(); 
		if(null != mappingtypesBean) {

			printWriter.println("mappingtypesBean injected successfully");
			LOGGER.info("mappingtypesBean injected successfully");
			mappingtypesBean.create();
		}
		else {
			printWriter.println("mappingtypesBean injection failed");
			LOGGER.info("mappingtypesBean injection failed");
		}
	}

}
