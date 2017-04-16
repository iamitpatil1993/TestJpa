package com.example.jpa.web.identitygenerators;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.identitygenerators.IdentityGeneratorTetBean;

/**
 * Servlet implementation class IdentityGeneratorTestServlet
 */
public class IdentityGeneratorTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(IdentityGeneratorTestServlet.class);
	}

	@EJB
	private IdentityGeneratorTetBean identityGeneratorTetBean;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		identityGeneratorTetBean.create();
		identityGeneratorTetBean.createFail();
	}

}
