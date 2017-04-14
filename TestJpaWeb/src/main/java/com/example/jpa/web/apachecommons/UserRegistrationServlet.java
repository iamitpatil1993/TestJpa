package com.example.jpa.web.apachecommons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.lobs.LargeObjectTestBean;
/*
 *Servlet Used for user registration.
 *Mostly used to demostrate the file upolad using apache commons fileUpload librarey
 * 
 */

public class UserRegistrationServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;
	private ServletFileUpload upload;

	static {
		LOGGER = Logger.getLogger(UserRegistrationServlet.class);
	}
	
	@EJB
	private LargeObjectTestBean largeObjectTestBean;

	@Override
	public void init() throws ServletException {
		super.init();

		LOGGER.info("Initializing diskFikeItem Factory and repository ...");
		// Create a factory for disk-based file items
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = getServletContext();
		File userDirFile = (File) servletContext.getAttribute("USER_DIR_FILE_HANDLE");
		fileItemFactory.setRepository(userDirFile);

		LOGGER.info("Initializing ServletFileUpload ...");
		// Create a new file upload handler
		upload = new ServletFileUpload(fileItemFactory);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		
		//Check request is multi-part 	
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		//If Multipast proceed with file upload 
		if(isMultipart) {

			try {
				// Parse the request
				List<FileItem> fileItems  = upload.parseRequest(request);

				if(null != fileItems && !fileItems.isEmpty()) {

					Iterator<FileItem> iterator = fileItems.iterator();
					while(iterator.hasNext()) {

						FileItem fileItem = iterator.next();
						if(!fileItem.isFormField()) {

							LOGGER.info("fileItem.isFormField() : " + fileItem.isFormField());
							LOGGER.info("FieldName : " + fileItem.getFieldName());
							LOGGER.info("FileName : " + fileItem.getName());
							LOGGER.info("File Size : " + fileItem.getSize());
							LOGGER.info("File Size : " + fileItem.getSize() + " bytes");
							LOGGER.info("File extension : " + FilenameUtils.getExtension(fileItem.getName()));
							
							
							Integer docId = largeObjectTestBean.uploadDocument(fileItem.get());
							
							LOGGER.info("File uploaded successfully with document id : " + docId);
						}
					}
				} else {
					printWriter.println("File not attached ... Please select file and then try again to upload");
				}
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			printWriter.println("Request encryption type is not multipart/form-data");
		}
	}


}
 