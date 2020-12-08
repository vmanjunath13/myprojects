import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/ZipCode")

public class ZipCode extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayZipCode(request, response);
	}

	/* Display ZipCode Details of the Customer (Name and Usertype) */

	protected void displayZipCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try
        {  
			response.setContentType("text/html");
			if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);	
				session.setAttribute("msg_src", "ZipCode"); 
				session.setAttribute("login_msg", "Please Login to add items to cart");
				response.sendRedirect("Login");
				return;
			}
			String pType = request.getParameter("link");
			//System.out.println("pType:" + pType);	
			HttpSession session=request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Search " + pType + " near you!</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<form action='GoogleMapSearch' method='get'><div class='zipcode'>"
			+ "<input class='btnzip' style='height:30px;width:150px;text-align:center;margin-top:100px;color:black;' type='text' placeholder='Enter zip code' name='zipCode' maxlength='5' required>");
			pw.print("<input type='hidden' name='pType' value='" + pType + "'>");
			pw.print("<input style='margin-top:30px;height:30px;width:150px;text-align:center;' type='submit' name='button' value='Continue' class='btnbuy'></div></form>");
			pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");	        	
		}
		catch(Exception e)
		{
			System.out.print("Error occurred");
		}		
	}
}
