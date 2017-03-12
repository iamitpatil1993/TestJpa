package com.example.jpa.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ejb.jpa.TestBean;
import com.example.pojo.App;



public class TestWeb extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private TestBean testBean;

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse resp)
			throws ServletException, IOException {

		PrintWriter printWriter = resp.getWriter();
		printWriter.println("Hello world from Serlet");


		if(testBean != null) {
			System.out.println("Bean injected successfully");
			printWriter.println("Bean injected successfully");
			
			testBean.foo();
			
			App app = new App("Test app 2");
			testBean.foo2(app);
		}
		else {
			System.out.println("Error while injecting bean");

			try {
				testBean = (TestBean) new InitialContext().lookup("java:global/TestJpaEar/TestEjbJpa-1.0/TestBeanClass!com.example.jpa.TestBean");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				printWriter.println("Error while injecting bean From catch");
				e.printStackTrace();
			}

			testBean.foo();
		}

	}


}
