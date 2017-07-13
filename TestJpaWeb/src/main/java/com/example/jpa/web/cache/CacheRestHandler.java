package com.example.jpa.web.cache;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.cache.CacheTestBean;


public class CacheRestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;

	static {
		LOGGER = Logger.getLogger(CacheRestHandler.class);
	}
	
	@EJB
	private CacheTestBean cacheTestBean;

	
    public CacheRestHandler() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		LOGGER.info("--------Calling first time from handler ------------");
		LOGGER.info("\n\n");
		cacheTestBean.findConfigById(Integer.parseInt(request.getParameter("configId")));
		
		LOGGER.info("\n\n");
		LOGGER.info("--------Calling second time from handler ------------");
		LOGGER.info("\n\n");
		cacheTestBean.findConfigById(Integer.parseInt(request.getParameter("configId")));
	}

}
