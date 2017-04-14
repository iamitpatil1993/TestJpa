package com.example.jpa.web.utils;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


//Class used to initialize the servlet context for file upolad using apache commons
//Creates File object for directory where uploaded files will be stores
public class ServletContextInitilizer implements ServletContextListener {

	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(ServletContextInitilizer.class);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		LOGGER.info("Inside contextInitialized ... ");
		
		ServletContext context = servletContextEvent.getServletContext();
		String tempUploadDirName = context.getInitParameter("tempUpload.directory");
		
		File fileUploadDirFile = new File("/tmp" + File.separator + tempUploadDirName);
		if(!fileUploadDirFile.exists()) {
			LOGGER.info("dir not exists... creating one");
			fileUploadDirFile.mkdir();
		}
		
		context.setAttribute("USER_DIR_PATH", "/tmp" + File.separator + tempUploadDirName);
		context.setAttribute("USER_DIR_FILE_HANDLE", fileUploadDirFile);
		LOGGER.info("USER_DIR_PATH : " + context.getAttribute("USER_DIR_PATH"));
	}

}
