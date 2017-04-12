package com.example.jpa.web.accesstype;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.example.ejb.jpa.localinterfaces.accesstypes.AccessTypeBeanLocal;

/**
 * Servlet implementation class AccessTypeServlet
 */
public class AccessTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(AccessTypeServlet.class);
	} 
	public AccessTypeServlet() {
		super();

	}

	//NOTE : Here we have used same business interface with two bean implementations, so we must provide bean name 
	//to tell contain which implementation of business interface to be injected
	@EJB(beanName = "FieldAccessTypeBean")
	private AccessTypeBeanLocal fieldAccessTypeBean; 

	@EJB(beanName="PropertyAccessTypeBean")
	private AccessTypeBeanLocal propertyAccessBean;

	@EJB(beanName="MixedAccessTypeBean")
	private AccessTypeBeanLocal mixedAccessTypeBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter printWriter = response.getWriter();

		switch (request.getParameter("accessType").toUpperCase()) {
		case "FIELD":
			if(null != fieldAccessTypeBean) {

				printWriter.println("fieldAccessTypeBean injected successfully");
				LOGGER.info("fieldAccessTypeBean injected successfully");
				fieldAccessTypeBean.read(Integer.parseInt(request.getParameter("id")));
			}
			else {
				printWriter.println("fieldAccessTypeBean injection failed");
				LOGGER.info("fieldAccessTypeBean injection failed");
			}

			break;

		case "PROPERTY":
			if(null != propertyAccessBean) {

				printWriter.println("propertyAccessBean injected successfully");
				LOGGER.info("propertyAccessBean injected successfully");
				propertyAccessBean.read(Integer.parseInt(request.getParameter("id")));
			}
			else {
				printWriter.println("propertyAccessBean injection failed");
				LOGGER.info("propertyAccessBean injection failed");
			}

			break;
		case "MIXED":
			if(null != mixedAccessTypeBean) {

				printWriter.println("mixedAccessTypeBean injected successfully");
				LOGGER.info("mixedAccessTypeBean injected successfully");
				mixedAccessTypeBean.read(Integer.parseInt(request.getParameter("id")));
			}
			else {
				printWriter.println("mixedAccessTypeBean injection failed");
				LOGGER.info("mixedAccessTypeBean injection failed");
			}

			break;
		default:
			printWriter.println("Invalid access type specified");
			LOGGER.info("Invalid access type specified");
			break;
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter printWriter = response.getWriter();
		switch (request.getParameter("accessType").toUpperCase()) {
		case "FIELD":
			if(null != fieldAccessTypeBean) {

				printWriter.println("fieldAccessTypeBean injected successfully");
				LOGGER.info("fieldAccessTypeBean injected successfully");
				fieldAccessTypeBean.create();
			}
			else {
				printWriter.println("fieldAccessTypeBean injection failed");
				LOGGER.info("fieldAccessTypeBean injection failed");
			}

			break;

		case "PROPERTY":
			if(null != propertyAccessBean) {

				printWriter.println("propertyAccessBean injected successfully");
				LOGGER.info("propertyAccessBean injected successfully");
				propertyAccessBean.create();
			}
			else {
				printWriter.println("propertyAccessBean injection failed");
				LOGGER.info("propertyAccessBean injection failed");
			}

			break;
		case "MIXED":
			if(null != mixedAccessTypeBean) {

				printWriter.println("mixedAccessTypeBean injected successfully");
				LOGGER.info("mixedAccessTypeBean injected successfully");
				mixedAccessTypeBean.create();
			}
			else {
				printWriter.println("mixedAccessTypeBean injection failed");
				LOGGER.info("mixedAccessTypeBean injection failed");
			}

			break;			
		default:
			printWriter.println("Invalid access type specified");
			LOGGER.info("Invalid access type specified");
			break;
		}
	}

}
