package com.example.jpa.web.blogpost;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.ejb.jpa.beans.blogpost.PostBean;
import com.example.ejb.jpa.exceptions.InsufficientDataException;
import com.example.pojo.User;
import com.example.pojo.blogpost.Post;
import com.example.pojo.blogpost.Tag;

/**
 * Servlet implementation class TagRestHandler
 */
public class TagRestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;

	@EJB
	private PostBean postBean;

	static {
		LOGGER = Logger.getLogger(TagRestHandler.class);
	}


	public TagRestHandler() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		try {

			User user = new User();
			user.setUserId(Integer.parseInt(request.getParameter("userId")));

			Post post = new Post();
			post.setPostId(Integer.parseInt(request.getParameter("postId")));

			Tag tag  = new Tag(user, post);
			tag = postBean.tagUser(tag);
			writer.println("User tagged successfully : " + tag);

		} catch (InsufficientDataException e) {
			writer.println("Error while creating User : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
