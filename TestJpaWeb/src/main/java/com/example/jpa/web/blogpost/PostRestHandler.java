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
import com.example.ejb.jpa.exceptions.InvalidDataException;
import com.example.pojo.User;
import com.example.pojo.blogpost.Post;

/**
 * Servlet implementation class PostRestHandler
 */
public class PostRestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER;

	@EJB
	private PostBean postBean;

	static {
		LOGGER = Logger.getLogger(PostRestHandler.class);
	}


	public PostRestHandler() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//postBean.displayPosts(Integer.parseInt(request.getParameter("userId")) );
		postBean.searchPost(null != request.getParameter("userId") ? Integer.parseInt(request.getParameter("userId")) : null, request.getParameter("title"), request.getParameter("text"), request.getParameter("postedBy"));
		//postBean.displayPostUsingCriteriaFetch();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		try {

			User user = new User();
			user.setUserId(Integer.parseInt(request.getParameter("userId")));

			Post post = new Post(request.getParameter("title"), request.getParameter("text"));
			post.setUser(user);

			post = postBean.addPost(post);
			writer.println("Post added successfully : " + post);

		} catch (InsufficientDataException e) {
			writer.println("Error while creating User : " + e.getMessage());
			e.printStackTrace();
		}
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter writer = response.getWriter();
		
		try {

			Post post = new Post(request.getParameter("title"), request.getParameter("text"));
			post.setPostId(Integer.parseInt(request.getParameter("postId")));

			post = postBean.updatePost(post);
			writer.println("Post updted successfully : " + post);

		} catch (InsufficientDataException e) {
			writer.println("Error while creating User : " + e.getMessage());
			e.printStackTrace();
		} 
	}
	
	//Cannot reduce the visibility of the inherited method from HttpServlet, we can increase the visibility but can't reduce
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		PrintWriter writer = response.getWriter();
		
		try {
			postBean.deletePost(Integer.parseInt(request.getParameter("postId")));
			
		} catch (NumberFormatException e) {
			writer.println("Invalid number entered : " + request.getParameter("postId"));
			e.printStackTrace();
		} catch (InsufficientDataException e) {
			writer.println(e.getMessage());
			e.printStackTrace();
		} catch (InvalidDataException e) {
			writer.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//SQL EXECUTED BY DELETE OPERATION OF POST, QUIE LARGE
/*			07:59:07,971 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select post0_.post_id as post1_16_1_, post0_.created_on as created2_16_1_, post0_.updated_on as updated3_16_1_, post0_.postedOn as postedOn16_1_, post0_.text as text16_1_, post0_.title as title16_1_, post0_.postedby_user_id as postedby7_16_1_, user1_.user_id as user1_2_0_, user1_.desk_id as desk4_2_0_, user1_.gender as gender2_0_, user1_.name as name2_0_ from post post0_ left outer join user user1_ on post0_.postedby_user_id=user1_.user_id where post0_.post_id=?
			07:59:07,986 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select parkinglot0_.parking_lot_id as parking1_17_0_, parkinglot0_.created_on as created2_17_0_, parkinglot0_.last_updated_on as last3_17_0_, parkinglot0_.parking_lot_user_id as parking4_17_0_ from parking_lot parkinglot0_ where parkinglot0_.parking_lot_user_id=?
			07:59:07,991 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select tags0_.post_id as post5_16_2_, tags0_.tag_id as tag1_2_, tags0_.tag_id as tag1_4_1_, tags0_.created_on as created2_4_1_, tags0_.updated_on as updated3_4_1_, tags0_.post_id as post5_4_1_, tags0_.tagedOn as tagedOn4_1_, tags0_.user_id as user6_4_1_, user1_.user_id as user1_2_0_, user1_.desk_id as desk4_2_0_, user1_.gender as gender2_0_, user1_.name as name2_0_ from tag tags0_ left outer join user user1_ on tags0_.user_id=user1_.user_id where tags0_.post_id=?
			07:59:07,997 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: select parkinglot0_.parking_lot_id as parking1_17_0_, parkinglot0_.created_on as created2_17_0_, parkinglot0_.last_updated_on as last3_17_0_, parkinglot0_.parking_lot_user_id as parking4_17_0_ from parking_lot parkinglot0_ where parkinglot0_.parking_lot_user_id=?
			07:59:08,007 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from tag where tag_id=?
			07:59:08,009 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from tag where tag_id=?
			07:59:08,010 INFO  [stdout] (http--0.0.0.0-8080-1) Hibernate: delete from post where post_id=?
*/
}
